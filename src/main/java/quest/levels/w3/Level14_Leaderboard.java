package quest.levels.w3;

import java.util.Comparator;
import java.util.List;

/**
 * ROOM 14 - THE LEADERBOARD
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>Comparable = a class's ONE natural order (compareTo). Comparator = any number of OTHER orders,
 * built separately.
 */
public class Level14_Leaderboard {

    /** One run through the dungeon. Its natural order is the main leaderboard order. */
    public record Score(String player, int points, int seconds) implements Comparable<Score> {

        /**
         * Leaderboard order, best first: MORE points first; on equal points, FEWER seconds first;
         * then player name A-Z. Negative means "this comes before other".
         */
        @Override
        public int compareTo(Score other) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** A NEW list in leaderboard (natural) order. The input list must not be changed. */
    public static List<Score> ranked(List<Score> scores) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /** Names of the top {@code n} players in leaderboard order; everyone if there are fewer than n. */
    public static List<String> topNames(List<Score> scores, int n) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /** The speedrun board: FEWEST seconds first; on a tie, MORE points first. */
    public static Comparator<Score> speedrunOrder() {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /** A NEW list sorted by player name A-Z, ignoring upper/lower case. */
    public static List<Score> byName(List<Score> scores) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
