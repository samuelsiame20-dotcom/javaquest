package quest.levels.w1;

/**
 * ROOM 1 - THE GATEKEEPER
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 */
public class Level01_Gatekeeper {

    /**
     * Decides whether a traveller may pass the gate. The rules:
     * <ul>
     *   <li>a banned traveller never passes, whatever else is true</li>
     *   <li>a traveller must be 16 or older</li>
     *   <li>a traveller must carry a key OR know the password (either one is enough)</li>
     * </ul>
     *
     * @return true if the traveller may enter
     */
    public static boolean canEnter(int age, boolean hasKey, boolean knowsPassword, boolean banned) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * The gold the gatekeeper charges for passing:
     * <ul>
     *   <li>under 12: 0 gold</li>
     *   <li>12 to 64 (inclusive): 10 gold</li>
     *   <li>65 and over: 5 gold</li>
     * </ul>
     *
     * @return the toll in gold
     */
    public static int toll(int age) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
