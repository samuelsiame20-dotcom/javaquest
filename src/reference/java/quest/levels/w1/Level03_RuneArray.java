package quest.levels.w1;

/** Reference solution for Room 3. Exists only to prove the lock can be opened. */
public class Level03_RuneArray {

    public static int strongest(int[] runes) {
        int best = runes[0];
        for (int rune : runes) {
            if (rune > best) {
                best = rune;
            }
        }
        return best;
    }

    public static int weakestIndex(int[] runes) {
        int weakest = 0;
        for (int i = 1; i < runes.length; i++) {
            if (runes[i] < runes[weakest]) {
                weakest = i;
            }
        }
        return weakest;
    }

    public static int indexOf(int[] runes, int target) {
        for (int i = 0; i < runes.length; i++) {
            if (runes[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
