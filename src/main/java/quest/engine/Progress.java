package quest.engine;

import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/** The player's saved state. {@link ProgressStore} reads and writes it. */
public final class Progress {

    private final SortedSet<Integer> cleared = new TreeSet<>();
    private final Map<Integer, Integer> hintsUsed = new TreeMap<>();
    private final Map<Integer, Integer> failedAttempts = new TreeMap<>();
    private int xp;
    private int streak;
    private int bestStreak;

    public boolean isCleared(int roomId) {
        return cleared.contains(roomId);
    }

    public SortedSet<Integer> cleared() {
        return Collections.unmodifiableSortedSet(cleared);
    }

    public int xp() {
        return xp;
    }

    /** Rooms cleared in a row with no failed attempts. */
    public int streak() {
        return streak;
    }

    public int bestStreak() {
        return bestStreak;
    }

    public int hintsUsed(int roomId) {
        return hintsUsed.getOrDefault(roomId, 0);
    }

    public void useHint(int roomId) {
        hintsUsed.merge(roomId, 1, Integer::sum);
    }

    public int failedAttempts(int roomId) {
        return failedAttempts.getOrDefault(roomId, 0);
    }

    public void recordFailedAttempt(int roomId) {
        failedAttempts.merge(roomId, 1, Integer::sum);
    }

    /** Marks a room cleared, adds its XP and updates the first-try streak. */
    public void clear(int roomId, int xpAwarded) {
        boolean firstTry = failedAttempts(roomId) == 0;
        cleared.add(roomId);
        xp += xpAwarded;
        streak = firstTry ? streak + 1 : 0;
        bestStreak = Math.max(bestStreak, streak);
    }

    // --- used by ProgressStore when loading -------------------------------------------------

    Map<Integer, Integer> hintsUsedView() {
        return Collections.unmodifiableMap(hintsUsed);
    }

    Map<Integer, Integer> failedAttemptsView() {
        return Collections.unmodifiableMap(failedAttempts);
    }

    void restore(int xp, int streak, int bestStreak) {
        this.xp = xp;
        this.streak = streak;
        this.bestStreak = Math.max(bestStreak, streak);
    }

    void restoreCleared(int roomId) {
        cleared.add(roomId);
    }

    void restoreHints(int roomId, int count) {
        hintsUsed.put(roomId, count);
    }

    void restoreFailedAttempts(int roomId, int count) {
        failedAttempts.put(roomId, count);
    }
}
