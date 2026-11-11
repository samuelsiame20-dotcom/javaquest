package quest.levels.w1;

/**
 * ROOM 5 - BOSS: THE VAULT LOCK
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>The vault has a row of dials, each showing a digit 0-9. Turning a dial "up" goes
 * 0, 1, ... 9, 0, 1 (it wraps round); turning "down" goes the other way.
 */
public class Level05_VaultLock {

    /**
     * A valid code is 4 to 8 characters long and every character is a digit. {@code null} is invalid.
     *
     * <p>Examples: "1234" and "00000000" are valid; "123", "12a4" and "" are not.
     */
    public static boolean isValidCode(String code) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Turns a valid code into its digits.
     *
     * <p>Example: {@code toDigits("4071")} is {@code {4, 0, 7, 1}}.
     */
    public static int[] toDigits(String code) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * The fewest single-step turns needed to make every dial show its code digit. Each dial can go
     * up or down, whichever is shorter. Both arrays have the same length.
     *
     * <p>Examples: dial 1 to 3 is 2 turns; dial 9 to 1 is 2 turns (up through 0);
     * dial 0 to 5 is 5 turns either way.
     */
    public static int turnsToOpen(int[] dials, int[] code) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * One character per dial: '=' if it's already right, '+' if turning up is the shorter way
     * (or a tie), '-' if turning down is shorter.
     *
     * <p>Example: {@code guide({1, 9, 5, 3}, {1, 1, 2, 8})} is {@code "=+-+"}.
     */
    public static String guide(int[] dials, int[] code) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
