package quest.levels.w1;

/** Reference solution for Room 4. Exists only to prove the lock can be opened. */
public class Level04_ScrollDecoder {

    public static String reverseWords(String scroll) {
        String trimmed = scroll.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        String[] words = trimmed.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) {
                result.append(' ');
            }
        }
        return result.toString();
    }

    public static int countVowels(String text) {
        int count = 0;
        for (char c : text.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) >= 0) {
                count++;
            }
        }
        return count;
    }

    public static String compress(String runes) {
        if (runes.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        char current = runes.charAt(0);
        int run = 1;
        for (int i = 1; i < runes.length(); i++) {
            if (runes.charAt(i) == current) {
                run++;
            } else {
                result.append(current).append(run);
                current = runes.charAt(i);
                run = 1;
            }
        }
        return result.append(current).append(run).toString();
    }

    public static boolean isPalindrome(String phrase) {
        StringBuilder letters = new StringBuilder();
        for (char c : phrase.toCharArray()) {
            if (Character.isLetter(c)) {
                letters.append(Character.toLowerCase(c));
            }
        }
        String forwards = letters.toString();
        return forwards.equals(letters.reverse().toString());
    }
}
