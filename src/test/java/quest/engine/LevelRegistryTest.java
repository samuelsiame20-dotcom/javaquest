package quest.engine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Guards the content rules from PLAN.md so a bad registry entry fails CI, not the player. */
class LevelRegistryTest {

    private final List<Level> levels = LevelRegistry.all();

    @Test
    void roomIdsStartAtZeroAndCountUpByOne() {
        for (int i = 0; i < levels.size(); i++) {
            assertEquals(i, levels.get(i).id(), "room at position " + i + " has the wrong id");
        }
    }

    @Test
    void everyRoomHasItsFilesAndTestClass() {
        for (Level level : levels) {
            assertTrue(Files.exists(Path.of(level.stubPath())), "missing stub file " + level.stubPath());
            assertDoesNotThrow(() -> Class.forName(level.testClass()), "missing test class " + level.testClass());
        }
    }

    @Test
    void everyRoomFollowsTheContentRules() {
        for (Level level : levels) {
            String room = "room " + level.id() + ": ";
            assertEquals(3, level.hints().size(), room + "needs exactly 3 hints (nudge, concept, pseudo-code)");
            assertTrue(level.story().strip().split("\\s+").length <= 120, room + "story is over 120 words");
            assertTrue(level.xp() > 0, room + "xp must be positive");
            assertFalse(level.task().isBlank(), room + "task is blank");
            assertFalse(level.concept().isBlank(), room + "concept note is blank");
        }
    }

    @Test
    void newGameStartsAtRoomZeroWithEverythingElseLocked() {
        Progress fresh = new Progress();

        assertEquals(0, LevelRegistry.currentRoom(fresh).orElseThrow().id());
        assertTrue(LevelRegistry.isUnlocked(levels.get(0), fresh));
        assertFalse(LevelRegistry.isUnlocked(levels.get(1), fresh));
    }

    @Test
    void clearingARoomUnlocksTheNextOne() {
        Progress progress = new Progress();
        progress.clear(0, 20);
        progress.clear(1, 100);

        assertEquals(2, LevelRegistry.currentRoom(progress).orElseThrow().id());
        assertTrue(LevelRegistry.isUnlocked(levels.get(1), progress), "cleared rooms stay replayable");
        assertTrue(LevelRegistry.isUnlocked(levels.get(2), progress));
        assertFalse(LevelRegistry.isUnlocked(levels.get(3), progress));
    }

    @Test
    void noCurrentRoomOnceEverythingIsCleared() {
        Progress progress = new Progress();
        levels.forEach(level -> progress.clear(level.id(), level.xp()));

        assertTrue(LevelRegistry.currentRoom(progress).isEmpty());
    }
}
