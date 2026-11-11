package quest.levels.w3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Reference solution for Room 14. Exists only to prove the lock can be opened. */
public class Level14_Leaderboard {

    public record Score(String player, int points, int seconds) implements Comparable<Score> {

        @Override
        public int compareTo(Score other) {
            int byPoints = Integer.compare(other.points, points);
            if (byPoints != 0) {
                return byPoints;
            }
            int bySeconds = Integer.compare(seconds, other.seconds);
            if (bySeconds != 0) {
                return bySeconds;
            }
            return player.compareTo(other.player);
        }
    }

    public static List<Score> ranked(List<Score> scores) {
        List<Score> copy = new ArrayList<>(scores);
        Collections.sort(copy);
        return copy;
    }

    public static List<String> topNames(List<Score> scores, int n) {
        return ranked(scores).stream().limit(n).map(Score::player).toList();
    }

    public static Comparator<Score> speedrunOrder() {
        return Comparator.comparingInt(Score::seconds)
                .thenComparing(Comparator.comparingInt(Score::points).reversed());
    }

    public static List<Score> byName(List<Score> scores) {
        List<Score> copy = new ArrayList<>(scores);
        copy.sort(Comparator.comparing(Score::player, String.CASE_INSENSITIVE_ORDER));
        return copy;
    }
}
