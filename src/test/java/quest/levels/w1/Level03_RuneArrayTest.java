package quest.levels.w1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** The lock for Room 3. */
@Timeout(2)
@DisplayName("Room 3 - Rune Array")
class Level03_RuneArrayTest {

    @Test
    @DisplayName("finds the strongest rune")
    void strongest() {
        assertEquals(9, Level03_RuneArray.strongest(new int[] {3, 9, 2, 7}),
                "strongest({3, 9, 2, 7}) should be 9.");
        assertEquals(5, Level03_RuneArray.strongest(new int[] {5}),
                "strongest({5}) should be 5: with one rune, that rune is the strongest.");
        assertEquals(8, Level03_RuneArray.strongest(new int[] {1, 4, 8}),
                "strongest({1, 4, 8}) should be 8: the last rune counts too. Does your loop reach the end?");
    }

    @Test
    @DisplayName("finds the strongest rune when every rune is cursed (negative)")
    void strongestAllNegative() {
        assertEquals(-1, Level03_RuneArray.strongest(new int[] {-4, -1, -9}),
                "strongest({-4, -1, -9}) should be -1. If you got 0, your 'best so far' started at 0, which "
                        + "isn't even in the array. Start it at runes[0].");
    }

    @Test
    @DisplayName("finds the position of the weakest rune")
    void weakestIndex() {
        assertEquals(1, Level03_RuneArray.weakestIndex(new int[] {4, 2, 8, 2}),
                "weakestIndex({4, 2, 8, 2}) should be 1: 2 is at index 1 and index 3, and ties go to the FIRST. "
                        + "Replace only on strictly smaller (<, not <=).");
        assertEquals(0, Level03_RuneArray.weakestIndex(new int[] {7}), "weakestIndex({7}) should be 0.");
        assertEquals(2, Level03_RuneArray.weakestIndex(new int[] {5, 4, 3}),
                "weakestIndex({5, 4, 3}) should be 2: return the index, not the value, and check the last rune.");
    }

    @Test
    @DisplayName("finds where a power first appears")
    void indexOfFound() {
        assertEquals(1, Level03_RuneArray.indexOf(new int[] {4, 7, 1, 7}, 7),
                "indexOf({4, 7, 1, 7}, 7) should be 1: return the FIRST match. Stop as soon as you find it.");
        assertEquals(0, Level03_RuneArray.indexOf(new int[] {4, 7, 1, 7}, 4),
                "indexOf({4, 7, 1, 7}, 4) should be 0: indexes start at 0.");
    }

    @Test
    @DisplayName("returns -1 when the power isn't there")
    void indexOfMissing() {
        assertEquals(-1, Level03_RuneArray.indexOf(new int[] {4, 7, 1}, 9),
                "indexOf({4, 7, 1}, 9) should be -1: 9 isn't there. Return -1 only AFTER checking every rune.");
        assertEquals(-1, Level03_RuneArray.indexOf(new int[] {}, 9),
                "indexOf({}, 9) should be -1: an empty array contains nothing.");
    }
}
