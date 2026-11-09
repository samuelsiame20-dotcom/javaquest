package quest.engine;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Optional;
import quest.ui.ConsoleRenderer;
import quest.ui.Renderer;

/**
 * Entry point for {@code gradlew.bat quest}. Decides what happens on each run; all output goes
 * through a {@link Renderer}.
 *
 * <p>The core loop is one command: running the game tries the current room's lock.
 * <ul>
 *   <li>every test still hits the TODO placeholder: show the room's story and task</li>
 *   <li>some tests fail: show what failed and count a failed try</li>
 *   <li>every test passes: award XP, unlock the next room, show its story</li>
 * </ul>
 */
public final class Game {

    private final ProgressStore store;
    private final Progress progress;
    private final LevelRunner runner;
    private final Renderer ui;

    Game(ProgressStore store, Progress progress, LevelRunner runner, Renderer ui) {
        this.store = store;
        this.progress = progress;
        this.runner = runner;
        this.ui = ui;
    }

    public static void main(String[] args) {
        int exitCode = run(args);
        System.out.flush();
        // Exit explicitly: a test that timed out can leave the player's infinite loop spinning
        // in a background thread, which would otherwise keep the game from ending.
        System.exit(exitCode);
    }

    static int run(String[] args) {
        Options options;
        try {
            options = Options.parse(args);
        } catch (IllegalArgumentException e) {
            Renderer plain = new ConsoleRenderer(System.out, false);
            plain.error(e.getMessage());
            plain.usage();
            return 2;
        }
        Renderer ui = new ConsoleRenderer(System.out, !options.noColor() && ConsoleRenderer.colorSupported());
        if (options.help()) {
            ui.usage();
            return 0;
        }

        ProgressStore store = ProgressStore.defaultStore();
        try {
            boolean firstVisit = !Files.exists(store.file());
            Progress progress = store.load();
            if (firstVisit) {
                ui.welcome();
            }
            return new Game(store, progress, new LevelRunner(), ui).execute(options);
        } catch (UncheckedIOException e) {
            ui.error(e.getMessage() + " (" + e.getCause().getMessage() + ")");
            return 1;
        }
    }

    int execute(Options options) {
        Optional<Level> current = LevelRegistry.currentRoom(progress);
        if (options.map()) {
            ui.map(LevelRegistry.all(), progress, current.map(Level::id).orElse(-1));
            return 0;
        }

        Level target;
        if (options.level() != null) {
            Optional<Level> chosen = LevelRegistry.byId(options.level());
            if (chosen.isEmpty()) {
                ui.error("There is no room " + options.level() + ". Rooms go from 0 to "
                        + (LevelRegistry.all().size() - 1) + ".");
                return 2;
            }
            if (!LevelRegistry.isUnlocked(chosen.get(), progress)) {
                ui.error("Room " + options.level() + " is still locked. Clear room "
                        + current.map(Level::id).orElse(0) + " first.");
                return 0;
            }
            target = chosen.get();
        } else if (current.isPresent()) {
            target = current.get();
        } else {
            ui.victory(progress);
            return 0;
        }

        ui.status(progress, LevelRegistry.all().size());
        if (options.hint()) {
            showHints(target);
        } else {
            tryLock(target);
        }
        return 0;
    }

    private void tryLock(Level level) {
        RunResult result = runner.run(level);
        if (result.total() == 0) {
            ui.error("Couldn't find the lock for this room (" + level.testClass()
                    + "). Was a test file renamed or deleted?");
            return;
        }
        int id = level.id();

        if (progress.isCleared(id)) {
            ui.roomHeader(level);
            ui.results(result);
            if (result.allPassed()) {
                ui.replayPassed(level);
            } else {
                ui.message("This room is already cleared, so your XP is safe. The lock is just showing "
                        + "that your code no longer passes.");
            }
            return;
        }

        if (result.notStarted()) {
            ui.room(level, xpIfClearedNow(level));
            return;
        }

        ui.roomHeader(level);
        ui.results(result);
        if (result.allPassed()) {
            boolean firstTry = progress.failedAttempts(id) == 0;
            int award = xpIfClearedNow(level);
            progress.clear(id, award);
            store.save(progress);
            ui.doorOpens(level, award, firstTry, progress);
            Optional<Level> next = LevelRegistry.currentRoom(progress);
            if (next.isPresent()) {
                ui.room(next.get(), xpIfClearedNow(next.get()));
            } else {
                ui.victory(progress);
            }
        } else {
            progress.recordFailedAttempt(id);
            store.save(progress);
            ui.lockRattles(result, progress.failedAttempts(id));
        }
    }

    private void showHints(Level level) {
        int id = level.id();
        if (progress.hintsUsed(id) < level.hints().size()) {
            progress.useHint(id);
            store.save(progress);
        }
        ui.roomHeader(level);
        ui.hints(level, progress.hintsUsed(id), progress.isCleared(id) ? -1 : xpIfClearedNow(level));
    }

    private int xpIfClearedNow(Level level) {
        int id = level.id();
        return Math.max(0, XpRules.award(level.xp(), progress.hintsUsed(id), progress.failedAttempts(id) == 0));
    }
}
