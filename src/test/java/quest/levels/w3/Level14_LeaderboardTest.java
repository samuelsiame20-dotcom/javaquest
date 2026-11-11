package quest.levels.w3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w3.Level14_Leaderboard.Score;

/** The lock for Room 14. */
@Timeout(2)
@DisplayName("Room 14 - The Leaderboard")
class Level14_LeaderboardTest {

    private static final Score ADA = new Score("Ada", 900, 300);
    private static final Score BO = new Score("bo", 900, 250);
    private static final Score CY = new Score("Cy", 1200, 400);
    private static final Score DEE = new Score("Dee", 500, 120);

    @Test
    @DisplayName("compareTo puts more points first, then the faster run")
    void compareTo() {
        assertTrue(CY.compareTo(ADA) < 0, "Cy (1200 points) should come before Ada (900): compareTo should be "
                + "NEGATIVE when this comes first. More points first means comparing other.points to points.");
        assertTrue(ADA.compareTo(CY) > 0, "Ada (900) should come after Cy (1200): compareTo should be positive.");
        assertTrue(BO.compareTo(ADA) < 0, "bo and Ada both have 900 points; bo was faster (250 s vs 300 s), so bo "
                + "comes first.");
        assertEquals(0, ADA.compareTo(new Score("Ada", 900, 300)), "Identical scores should compare as 0.");
    }

    @Test
    @DisplayName("ranked() sorts a copy, leaving the original alone")
    void ranked() {
        List<Score> entries = new ArrayList<>(List.of(ADA, DEE, CY, BO));

        assertEquals(List.of(CY, BO, ADA, DEE), Level14_Leaderboard.ranked(entries),
                "ranked should be Cy, bo, Ada, Dee. Copy the list, then sort the copy (natural order uses compareTo).");
        assertEquals(List.of(ADA, DEE, CY, BO), entries, "ranked() must not reorder the list it was given: sort "
                + "new ArrayList<>(scores), not scores itself.");
    }

    @Test
    @DisplayName("topNames gives the first n names, or everyone")
    void topNames() {
        List<Score> entries = List.of(ADA, DEE, CY, BO);

        assertEquals(List.of("Cy", "bo"), Level14_Leaderboard.topNames(entries, 2), "topNames(entries, 2) should be [Cy, bo].");
        assertEquals(List.of("Cy", "bo", "Ada", "Dee"), Level14_Leaderboard.topNames(entries, 10),
                "topNames(entries, 10) with only 4 players should give all 4, not crash: limit to "
                        + "Math.min(n, size), or use stream().limit(n).");
        assertEquals(List.of(), Level14_Leaderboard.topNames(entries, 0), "topNames(entries, 0) should be empty.");
    }

    @Test
    @DisplayName("the speedrun comparator puts the fastest first")
    void speedrunOrder() {
        List<Score> entries = new ArrayList<>(List.of(ADA, new Score("Eli", 300, 120), CY, DEE));
        entries.sort(Level14_Leaderboard.speedrunOrder());

        assertEquals(List.of("Dee", "Eli", "Ada", "Cy"), entries.stream().map(Score::player).toList(),
                "Speedrun order should be Dee, Eli, Ada, Cy: fewest seconds first, and Dee beats Eli on the 120 s "
                        + "tie with more points. Try Comparator.comparingInt(Score::seconds).thenComparing(...).");
    }

    @Test
    @DisplayName("byName sorts A-Z, ignoring case")
    void byName() {
        assertEquals(List.of(ADA, BO, CY, DEE), Level14_Leaderboard.byName(List.of(DEE, CY, BO, ADA)),
                "byName should be Ada, bo, Cy, Dee. Plain String order puts every capital before 'b', so 'bo' "
                        + "would come last: use String.CASE_INSENSITIVE_ORDER.");
    }
}
