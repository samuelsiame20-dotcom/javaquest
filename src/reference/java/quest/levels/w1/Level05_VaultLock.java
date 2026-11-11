package quest.levels.w1;

/** Reference solution for Room 5. Exists only to prove the lock can be opened. */
public class Level05_VaultLock {

    public static boolean isValidCode(String code) {
        if (code == null || code.length() < 4 || code.length() > 8) {
            return false;
        }
        for (char c : code.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static int[] toDigits(String code) {
        int[] digits = new int[code.length()];
        for (int i = 0; i < code.length(); i++) {
            digits[i] = code.charAt(i) - '0';
        }
        return digits;
    }

    public static int turnsToOpen(int[] dials, int[] code) {
        int total = 0;
        for (int i = 0; i < dials.length; i++) {
            int distance = Math.abs(dials[i] - code[i]);
            total += Math.min(distance, 10 - distance);
        }
        return total;
    }

    public static String guide(int[] dials, int[] code) {
        StringBuilder guide = new StringBuilder();
        for (int i = 0; i < dials.length; i++) {
            int up = (code[i] - dials[i] + 10) % 10;
            int down = (dials[i] - code[i] + 10) % 10;
            if (up == 0) {
                guide.append('=');
            } else if (up <= down) {
                guide.append('+');
            } else {
                guide.append('-');
            }
        }
        return guide.toString();
    }
}
