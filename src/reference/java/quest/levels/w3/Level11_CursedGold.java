package quest.levels.w3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Reference solution for Room 11. Exists only to prove the lock can be opened. */
public class Level11_CursedGold {

    public record Entry(String code, int value) {
    }

    public record ImportReport(Map<String, Integer> accepted, List<String> problems) {
    }

    public static class DuplicateLootException extends RuntimeException {

        public DuplicateLootException(String code) {
            super("Duplicate loot code " + code);
        }
    }

    public static class LedgerNotFoundException extends Exception {

        public LedgerNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static Entry parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Expected 'CODE | value' but found " + parts.length + " field(s)");
        }
        int value = Integer.parseInt(parts[1].trim());
        if (value < 1 || value > 9999) {
            throw new IllegalArgumentException("Value " + value + " is outside 1..9999");
        }
        return new Entry(parts[0].trim(), value);
    }

    public static void addEntry(Map<String, Integer> ledger, Entry entry) {
        if (ledger.containsKey(entry.code())) {
            throw new DuplicateLootException(entry.code());
        }
        ledger.put(entry.code(), entry.value());
    }

    public static ImportReport importLedger(Reader source) throws IOException {
        Map<String, Integer> accepted = new LinkedHashMap<>();
        List<String> problems = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(source)) {
            String line;
            int number = 0;
            while ((line = in.readLine()) != null) {
                number++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    addEntry(accepted, parseLine(line));
                } catch (DuplicateLootException | IllegalArgumentException e) {
                    problems.add("line " + number + ": " + e.getMessage());
                }
            }
        }
        return new ImportReport(accepted, problems);
    }

    public static ImportReport importFile(Path file) throws LedgerNotFoundException, IOException {
        Reader reader;
        try {
            reader = Files.newBufferedReader(file);
        } catch (NoSuchFileException | FileNotFoundException e) {
            throw new LedgerNotFoundException("Ledger file not found: " + file.getFileName(), e);
        }
        return importLedger(reader);
    }
}
