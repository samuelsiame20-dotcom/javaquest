package quest.levels.w1;

/** Reference solution for Room 1. Exists only to prove the lock can be opened. */
public class Level01_Gatekeeper {

    public static boolean canEnter(int age, boolean hasKey, boolean knowsPassword, boolean banned) {
        if (banned) {
            return false;
        }
        return age >= 16 && (hasKey || knowsPassword);
    }

    public static int toll(int age) {
        if (age < 12) {
            return 0;
        } else if (age < 65) {
            return 10;
        } else {
            return 5;
        }
    }
}
