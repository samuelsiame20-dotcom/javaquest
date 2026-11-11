package quest.levels.w1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** The lock for Room 4. */
@Timeout(2)
@DisplayName("Room 4 - Scroll Decoder")
class Level04_ScrollDecoderTest {

    @Test
    @DisplayName("reverses the order of the words")
    void reverseWords() {
        assertEquals("returns light the", Level04_ScrollDecoder.reverseWords("the light returns"),
                "reverseWords(\"the light returns\") should be \"returns light the\": reverse the words, "
                        + "not the letters. Try split(\" \") into an array, then walk it backwards.");
        assertEquals("alone", Level04_ScrollDecoder.reverseWords("alone"),
                "reverseWords(\"alone\") should be \"alone\": one word stays as it is.");
    }

    @Test
    @DisplayName("copes with messy spacing and blank scrolls")
    void reverseWordsMessySpacing() {
        assertEquals("dark the", Level04_ScrollDecoder.reverseWords("  the   dark  "),
                "reverseWords(\"  the   dark  \") should be \"dark the\": trim() the ends and split on "
                        + "\"\\\\s+\" (one or more spaces) so empty words don't sneak in.");
        assertEquals("", Level04_ScrollDecoder.reverseWords("   "),
                "reverseWords(\"   \") should be \"\": a blank scroll has no words.");
    }

    @Test
    @DisplayName("counts vowels in any case")
    void countVowels() {
        assertEquals(5, Level04_ScrollDecoder.countVowels("Ancient RUNE"),
                "countVowels(\"Ancient RUNE\") should be 5 (A, i, e, U, E). Upper-case vowels count too: "
                        + "toLowerCase() first, or check both cases.");
        assertEquals(0, Level04_ScrollDecoder.countVowels("crypt"), "countVowels(\"crypt\") should be 0: "
                + "y is not a vowel here.");
        assertEquals(0, Level04_ScrollDecoder.countVowels(""), "countVowels(\"\") should be 0.");
    }

    @Test
    @DisplayName("compresses runs of repeated runes")
    void compress() {
        assertEquals("a3b1c2", Level04_ScrollDecoder.compress("aaabcc"),
                "compress(\"aaabcc\") should be \"a3b1c2\". Build the answer with a StringBuilder, and "
                        + "don't forget to add the LAST run after the loop ends.");
        assertEquals("z1", Level04_ScrollDecoder.compress("z"), "compress(\"z\") should be \"z1\".");
        assertEquals("", Level04_ScrollDecoder.compress(""),
                "compress(\"\") should be \"\": check for an empty string before reading charAt(0).");
    }

    @Test
    @DisplayName("spots palindromes, ignoring case and punctuation")
    void isPalindrome() {
        assertTrue(Level04_ScrollDecoder.isPalindrome("Never odd, or even!"),
                "isPalindrome(\"Never odd, or even!\") should be true: keep only letters "
                        + "(Character.isLetter), lower-case them, then compare with the reverse.");
        assertFalse(Level04_ScrollDecoder.isPalindrome("Dungeon"), "isPalindrome(\"Dungeon\") should be false.");
        assertTrue(Level04_ScrollDecoder.isPalindrome(""), "isPalindrome(\"\") should be true: an empty "
                + "phrase reads the same both ways. Compare strings with equals(), not ==.");
    }
}
