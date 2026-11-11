package quest.levels.w3;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * ROOM 11 - CURSED GOLD
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>A ledger line looks like {@code GEM-07 | 340}: a loot code, a '|', and a gold value.
 * Policy for bad lines: <b>skip and report</b>. One cursed line must not stop the whole import.
 */
public class Level11_CursedGold {

    /** Given: one parsed ledger line. */
    public record Entry(String code, int value) {
    }

    /** Given: what an import produced. */
    public record ImportReport(Map<String, Integer> accepted, List<String> problems) {
    }

    /** Thrown when a loot code appears twice. UNCHECKED: it extends RuntimeException. */
    public static class DuplicateLootException extends RuntimeException {

        /** The message must include the duplicated code. */
        public DuplicateLootException(String code) {
            // TODO: replace this line with your code (hint: super(...) sets the message).
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** Thrown when the ledger file doesn't exist. CHECKED: it extends Exception, so callers must handle it. */
    public static class LedgerNotFoundException extends Exception {

        public LedgerNotFoundException(String message, Throwable cause) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /**
     * Parses one line such as {@code "GEM-07 | 340"} (spaces around each field don't matter).
     *
     * @throws IllegalArgumentException if there aren't exactly 2 fields, or the value is outside 1..9999
     * @throws NumberFormatException    if the value isn't a whole number
     */
    public static Entry parseLine(String line) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Adds an entry to the ledger.
     *
     * @throws DuplicateLootException if the code is already in the ledger (the ledger is left unchanged)
     */
    public static void addEntry(Map<String, Integer> ledger, Entry entry) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Imports a whole ledger. Blank lines are ignored. Good lines go into {@code accepted}, in file
     * order. Each bad line (wrong format, bad number, out of range, or a duplicate code) is skipped and
     * one message added to {@code problems}, starting with {@code "line N: "} (lines count from 1).
     *
     * <p>Closes {@code source} when done, even if reading fails. Doesn't catch the IOException.
     */
    public static ImportReport importLedger(Reader source) throws IOException {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Imports a ledger file.
     *
     * @throws LedgerNotFoundException if the file doesn't exist: its message names the file and its
     *                                 cause is the original exception (exception translation)
     */
    public static ImportReport importFile(Path file) throws LedgerNotFoundException, IOException {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
