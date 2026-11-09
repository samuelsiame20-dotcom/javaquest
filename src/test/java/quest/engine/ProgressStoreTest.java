package quest.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProgressStoreTest {

    @TempDir
    Path dir;

    @Test
    void missingFileMeansNewGame() {
        Progress progress = new ProgressStore(dir.resolve("progress.properties")).load();

        assertEquals(0, progress.xp());
        assertEquals(0, progress.streak());
        assertTrue(progress.cleared().isEmpty());
    }

    @Test
    void saveThenLoadRoundTrips() {
        ProgressStore store = new ProgressStore(dir.resolve("progress.properties"));
        Progress original = new Progress();
        original.clear(0, 20);              // first try -> streak 1
        original.recordFailedAttempt(1);
        original.recordFailedAttempt(1);
        original.useHint(1);
        original.clear(1, 100);             // not first try -> streak 0, best stays 1
        original.useHint(2);
        original.recordFailedAttempt(2);

        store.save(original);
        Progress loaded = store.load();

        assertEquals(120, loaded.xp());
        assertEquals(Set.of(0, 1), loaded.cleared());
        assertEquals(0, loaded.streak());
        assertEquals(1, loaded.bestStreak());
        assertEquals(1, loaded.hintsUsed(1));
        assertEquals(2, loaded.failedAttempts(1));
        assertEquals(1, loaded.hintsUsed(2));
        assertEquals(1, loaded.failedAttempts(2));
        assertEquals(0, loaded.hintsUsed(3));
    }

    @Test
    void saveCreatesMissingFoldersAndLeavesNoTempFile() {
        Path file = dir.resolve("nested/deeper/progress.properties");

        new ProgressStore(file).save(new Progress());

        assertTrue(Files.exists(file));
        assertFalse(Files.exists(file.resolveSibling("progress.properties.tmp")));
    }

    @Test
    void damagedValuesAreSkippedNotFatal() throws IOException {
        Path file = dir.resolve("progress.properties");
        Files.writeString(file, """
                xp=lots
                streak=-4
                cleared=0, 2,x,,-1
                hints.1=2
                hints.abc=3
                attempts.2=-7
                """);

        Progress progress = new ProgressStore(file).load();

        assertEquals(0, progress.xp());
        assertEquals(0, progress.streak());
        assertEquals(Set.of(0, 2), progress.cleared());
        assertEquals(2, progress.hintsUsed(1));
        assertEquals(0, progress.failedAttempts(2));
    }
}
