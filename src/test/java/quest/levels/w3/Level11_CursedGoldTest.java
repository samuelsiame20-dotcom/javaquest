package quest.levels.w3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;
import quest.levels.w3.Level11_CursedGold.DuplicateLootException;
import quest.levels.w3.Level11_CursedGold.Entry;
import quest.levels.w3.Level11_CursedGold.ImportReport;
import quest.levels.w3.Level11_CursedGold.LedgerNotFoundException;

/** The lock for Room 11. */
@Timeout(2)
@DisplayName("Room 11 - Cursed Gold")
class Level11_CursedGoldTest {

    /** A reader that remembers whether it was closed. */
    private static final class TrackingReader extends StringReader {
        boolean closed;

        TrackingReader(String text) {
            super(text);
        }

        @Override
        public void close() {
            closed = true;
            super.close();
        }
    }

    /** A reader whose disk is on fire: every read fails. */
    private static final class FailingReader extends Reader {
        boolean closed;

        @Override
        public int read(char[] buffer, int offset, int length) throws IOException {
            throw new IOException("disk on fire");
        }

        @Override
        public void close() {
            closed = true;
        }
    }

    @Test
    @DisplayName("parses good lines and rejects bad ones with the right exception")
    void parseLine() {
        assertEquals(new Entry("GEM-07", 340), Level11_CursedGold.parseLine("GEM-07 | 340"),
                "parseLine(\"GEM-07 | 340\") should be Entry(GEM-07, 340). Split on \"\\\\|\" (| is special in "
                        + "regex) and trim() each field.");
        assertThrows(NumberFormatException.class, () -> Level11_CursedGold.parseLine("RING-02 | lots"),
                "parseLine(\"RING-02 | lots\") should throw NumberFormatException: Integer.parseInt does that for you.");
        assertThrows(IllegalArgumentException.class, () -> Level11_CursedGold.parseLine("just one field"),
                "parseLine(\"just one field\") should throw IllegalArgumentException: check the field count "
                        + "BEFORE reading parts[1], or you'll get ArrayIndexOutOfBoundsException.");
        assertThrows(IllegalArgumentException.class, () -> Level11_CursedGold.parseLine("COIN-99 | 0"),
                "parseLine(\"COIN-99 | 0\") should throw IllegalArgumentException: values must be 1..9999. "
                        + "Use 'throw new IllegalArgumentException(...)' yourself.");
        assertThrows(IllegalArgumentException.class, () -> Level11_CursedGold.parseLine("COIN-99 | 10000"),
                "parseLine(\"COIN-99 | 10000\") should throw IllegalArgumentException: 9999 is the maximum.");
    }

    @Test
    @DisplayName("a duplicate code throws the custom unchecked exception")
    void duplicateLoot() {
        Map<String, Integer> ledger = new LinkedHashMap<>();
        Level11_CursedGold.addEntry(ledger, new Entry("GEM-07", 340));

        DuplicateLootException e = assertThrows(DuplicateLootException.class,
                () -> Level11_CursedGold.addEntry(ledger, new Entry("GEM-07", 120)),
                "Adding GEM-07 twice should throw DuplicateLootException. Check containsKey, then throw.");
        assertTrue(e.getMessage() != null && e.getMessage().contains("GEM-07"),
                "The DuplicateLootException message should name the code GEM-07: pass a message to super(...).");
        assertEquals(Map.of("GEM-07", 340), ledger, "The ledger should be unchanged after a duplicate: keep the "
                + "first value (340), not the second.");
    }

    @Test
    @DisplayName("skips and reports each bad line, then keeps importing")
    void skipAndReport() throws IOException {
        String ledger = """
                GEM-07 | 340
                RING-02 | lots
                CROWN-01 | 900

                GEM-07 | 120
                COIN-99 | 0
                just one field
                CUP-03 | 75
                """;

        ImportReport report = Level11_CursedGold.importLedger(new StringReader(ledger));

        assertEquals(List.of("GEM-07", "CROWN-01", "CUP-03"), List.copyOf(report.accepted().keySet()),
                "Accepted codes should be GEM-07, CROWN-01, CUP-03 in file order. CUP-03 comes AFTER the bad lines: "
                        + "catch the problem inside the loop so one bad line doesn't stop the import. "
                        + "A LinkedHashMap keeps insertion order.");
        assertEquals(4, report.problems().size(), "There should be 4 problems (lines 2, 5, 6, 7); the blank "
                + "line 4 is ignored, not a problem. Got: " + report.problems());
        List<String> expectedStarts = List.of("line 2: ", "line 5: ", "line 6: ", "line 7: ");
        for (int i = 0; i < 4; i++) {
            assertTrue(report.problems().get(i).startsWith(expectedStarts.get(i)),
                    "Problem " + (i + 1) + " should start with \"" + expectedStarts.get(i) + "\" but was \""
                            + report.problems().get(i) + "\". Count every line, including blank ones, starting at 1.");
        }
    }

    @Test
    @DisplayName("closes the reader even when reading fails (try-with-resources)")
    void closesTheReader() {
        TrackingReader good = new TrackingReader("GEM-07 | 340\n");
        try {
            Level11_CursedGold.importLedger(good);
        } catch (IOException e) {
            throw new AssertionError("Reading a StringReader shouldn't fail", e);
        }
        assertTrue(good.closed, "importLedger should close the reader when it finishes. "
                + "try (BufferedReader in = new BufferedReader(source)) { ... } closes it for you.");

        FailingReader broken = new FailingReader();
        assertThrows(IOException.class, () -> Level11_CursedGold.importLedger(broken),
                "If reading fails, the IOException should come out of importLedger (it declares throws IOException).");
        assertTrue(broken.closed, "The reader must be closed even when reading throws: that's exactly what "
                + "try-with-resources guarantees, with no finally block needed.");
    }

    @Test
    @DisplayName("translates a missing file into the checked LedgerNotFoundException")
    void importFile(@TempDir Path dir) throws Exception {
        Path missing = dir.resolve("missing-ledger.txt");
        LedgerNotFoundException e = assertThrows(LedgerNotFoundException.class,
                () -> Level11_CursedGold.importFile(missing),
                "importFile on a missing file should throw LedgerNotFoundException. Catch the low-level "
                        + "exception (NoSuchFileException or FileNotFoundException) and throw your own instead.");
        assertTrue(e.getMessage() != null && e.getMessage().contains("missing-ledger.txt"),
                "The LedgerNotFoundException message should name the file, so the player knows what's missing.");
        assertInstanceOf(IOException.class, e.getCause(),
                "Keep the original exception as the cause (pass it to super(message, cause)): developers need it "
                        + "in the logs even though the player only sees the friendly message.");

        Path real = dir.resolve("ledger.txt");
        Files.writeString(real, "GEM-07 | 340\nCUP-03 | 75\n");
        assertEquals(2, Level11_CursedGold.importFile(real).accepted().size(),
                "importFile on a real ledger with 2 good lines should accept 2 entries.");
    }
}
