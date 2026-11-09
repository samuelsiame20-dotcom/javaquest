package quest.ui;

import java.util.List;
import quest.engine.Level;
import quest.engine.Progress;
import quest.engine.RunResult;

/**
 * Everything the player sees goes through this interface. The engine decides WHAT happens;
 * a renderer decides HOW it looks. ConsoleRenderer is the terminal version; Phase 3 adds JavaFX.
 */
public interface Renderer {

    /** The big banner shown on the very first run. */
    void welcome();

    /** One-line summary: XP, streak, rooms cleared. */
    void status(Progress progress, int totalRooms);

    /** Room number, title and world. */
    void roomHeader(Level level);

    /** Full room intro: header, story, task, file to edit, reward. */
    void room(Level level, int xpIfClearedNow);

    /** One line per test: PASS / FAIL / TODO / TIME / CRASH, with the message underneath. */
    void results(RunResult result);

    /** Shown after a failed try of an uncleared room. */
    void lockRattles(RunResult result, int failedAttempts);

    /** Shown when a room is cleared for the first time. */
    void doorOpens(Level level, int xpAwarded, boolean firstTry, Progress progress);

    /** Shown when an already-cleared room still passes. */
    void replayPassed(Level level);

    /**
     * Shows hints 1..revealed for a room.
     *
     * @param xpIfClearedNow the reward after hint penalties, or -1 if the room is already cleared
     */
    void hints(Level level, int revealed, int xpIfClearedNow);

    /** The dungeon map. {@code currentRoomId} is -1 when every room is cleared. */
    void map(List<Level> levels, Progress progress, int currentRoomId);

    /** Every room cleared. */
    void victory(Progress progress);

    void message(String text);

    void error(String text);

    void usage();
}
