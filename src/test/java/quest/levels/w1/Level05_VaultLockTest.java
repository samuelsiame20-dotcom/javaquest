package quest.levels.w1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** The lock for Room 5 (World 1 boss). */
@Timeout(2)
@DisplayName("Room 5 - BOSS: The Vault Lock")
class Level05_VaultLockTest {

    @Test
    @DisplayName("accepts only 4 to 8 digit codes")
    void validCodes() {
        assertTrue(Level05_VaultLock.isValidCode("1234"), "isValidCode(\"1234\") should be true.");
        assertTrue(Level05_VaultLock.isValidCode("00000000"), "isValidCode(\"00000000\") should be true: 8 "
                + "digits is the maximum, and it's allowed (<=, not <).");
        assertFalse(Level05_VaultLock.isValidCode("123"), "isValidCode(\"123\") should be false: too short.");
        assertFalse(Level05_VaultLock.isValidCode("123456789"),
                "isValidCode(\"123456789\") should be false: 9 digits is too long.");
        assertFalse(Level05_VaultLock.isValidCode("12a4"), "isValidCode(\"12a4\") should be false: 'a' is not "
                + "a digit. Check every character with Character.isDigit.");
        assertFalse(Level05_VaultLock.isValidCode(null), "isValidCode(null) should be false: check for null "
                + "BEFORE calling length(), or you'll get a NullPointerException.");
    }

    @Test
    @DisplayName("turns a code into digits")
    void toDigits() {
        assertArrayEquals(new int[] {4, 0, 7, 1}, Level05_VaultLock.toDigits("4071"),
                "toDigits(\"4071\") should be {4, 0, 7, 1}. A char '7' is not the number 7: try "
                        + "Character.getNumericValue(c) or c - '0'.");
    }

    @Test
    @DisplayName("counts turns when no dial needs to wrap")
    void turnsWithoutWrapping() {
        assertEquals(3, Level05_VaultLock.turnsToOpen(new int[] {1, 2}, new int[] {3, 1}),
                "turnsToOpen({1, 2}, {3, 1}) should be 3: 1 to 3 is 2 turns, 2 to 1 is 1 turn. "
                        + "Use Math.abs for the distance.");
        assertEquals(0, Level05_VaultLock.turnsToOpen(new int[] {5, 5}, new int[] {5, 5}),
                "turnsToOpen({5, 5}, {5, 5}) should be 0: the vault is already open.");
    }

    @Test
    @DisplayName("takes the short way round when a dial can wrap")
    void turnsWithWrapping() {
        assertEquals(4, Level05_VaultLock.turnsToOpen(new int[] {9, 0}, new int[] {1, 8}),
                "turnsToOpen({9, 0}, {1, 8}) should be 4: 9 to 1 is 2 turns going up through 0, and 0 to 8 "
                        + "is 2 turns going down. Each dial takes min(distance, 10 - distance).");
        assertEquals(5, Level05_VaultLock.turnsToOpen(new int[] {0}, new int[] {5}),
                "turnsToOpen({0}, {5}) should be 5: halfway round is 5 either way.");
    }

    @Test
    @DisplayName("writes a turning guide")
    void guide() {
        assertEquals("=+-+", Level05_VaultLock.guide(new int[] {1, 9, 5, 3}, new int[] {1, 1, 2, 8}),
                "guide({1, 9, 5, 3}, {1, 1, 2, 8}) should be \"=+-+\": dial 1 is right; 9 to 1 is shorter "
                        + "going up; 5 to 2 is shorter going down; 3 to 8 is a tie (5 each way), and ties go up. "
                        + "Going up from d to c takes (c - d + 10) % 10 turns.");
    }
}
