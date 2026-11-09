package quest.engine;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.StringJoiner;

/**
 * Loads and saves {@link Progress} as a {@code .properties} file:
 *
 * <pre>
 * xp=120
 * streak=1
 * bestStreak=2
 * cleared=0,1
 * hints.2=1
 * attempts.2=3
 * </pre>
 *
 * <p>Reading is forgiving: a missing file means a new game, and a damaged value is skipped
 * rather than crashing the game.
 */
public final class ProgressStore {

    private static final String HINTS = "hints.";
    private static final String ATTEMPTS = "attempts.";

    private final Path file;

    public ProgressStore(Path file) {
        this.file = file;
    }

    /** {@code %JAVAQUEST_HOME%\progress.properties} if that variable is set, else {@code ~/.javaquest/}. */
    public static ProgressStore defaultStore() {
        String home = System.getenv("JAVAQUEST_HOME");
        Path dir = home != null && !home.isBlank()
                ? Path.of(home)
                : Path.of(System.getProperty("user.home"), ".javaquest");
        return new ProgressStore(dir.resolve("progress.properties"));
    }

    public Path file() {
        return file;
    }

    public Progress load() {
        Progress progress = new Progress();
        if (!Files.exists(file)) {
            return progress;
        }
        Properties props = new Properties();
        try (Reader reader = Files.newBufferedReader(file)) {
            props.load(reader);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + file, e);
        }

        int streak = number(props.getProperty("streak"));
        progress.restore(number(props.getProperty("xp")), streak, number(props.getProperty("bestStreak")));
        for (String id : props.getProperty("cleared", "").split(",")) {
            int room = number(id);
            if (!id.isBlank() && room >= 0 && id.strip().equals(String.valueOf(room))) {
                progress.restoreCleared(room);
            }
        }
        for (String key : props.stringPropertyNames()) {
            if (key.startsWith(HINTS)) {
                restoreCount(key.substring(HINTS.length()), props.getProperty(key), progress::restoreHints);
            } else if (key.startsWith(ATTEMPTS)) {
                restoreCount(key.substring(ATTEMPTS.length()), props.getProperty(key),
                        progress::restoreFailedAttempts);
            }
        }
        return progress;
    }

    /** Writes to a temporary file first, then swaps it in, so a crash mid-save can't corrupt progress. */
    public void save(Progress progress) {
        Properties props = new Properties();
        props.setProperty("xp", String.valueOf(progress.xp()));
        props.setProperty("streak", String.valueOf(progress.streak()));
        props.setProperty("bestStreak", String.valueOf(progress.bestStreak()));
        StringJoiner cleared = new StringJoiner(",");
        progress.cleared().forEach(id -> cleared.add(String.valueOf(id)));
        props.setProperty("cleared", cleared.toString());
        progress.hintsUsedView().forEach((id, n) -> props.setProperty(HINTS + id, String.valueOf(n)));
        progress.failedAttemptsView().forEach((id, n) -> props.setProperty(ATTEMPTS + id, String.valueOf(n)));

        try {
            Path dir = file.toAbsolutePath().getParent();
            Files.createDirectories(dir);
            Path tmp = dir.resolve(file.getFileName() + ".tmp");
            try (Writer writer = Files.newBufferedWriter(tmp)) {
                props.store(writer, "JavaQuest progress - delete this file to start over");
            }
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save progress to " + file, e);
        }
    }

    private interface CountSetter {
        void set(int roomId, int count);
    }

    private static void restoreCount(String roomText, String countText, CountSetter setter) {
        int room = number(roomText);
        int count = number(countText);
        if (room >= 0 && count > 0 && roomText.strip().equals(String.valueOf(room))) {
            setter.set(room, count);
        }
    }

    /** Parses a non-negative number; anything missing, damaged or negative becomes 0. */
    private static int number(String text) {
        if (text == null) {
            return 0;
        }
        try {
            return Math.max(0, Integer.parseInt(text.strip()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
