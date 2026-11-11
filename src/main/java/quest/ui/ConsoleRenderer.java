package quest.ui;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import quest.engine.Level;
import quest.engine.Progress;
import quest.engine.RunResult;
import quest.engine.TestOutcome;

/**
 * Terminal renderer. Plain ASCII only (old Windows consoles mangle box-drawing characters),
 * with optional ANSI colours.
 */
public final class ConsoleRenderer implements Renderer {

    private static final int WIDTH = 66;
    private static final String RULE = "-".repeat(WIDTH);
    private static final String[] HINT_TIERS = {"nudge", "concept", "pseudo-code"};

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String DIM = "\u001B[2m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    private final PrintStream out;
    private final boolean color;

    public ConsoleRenderer(PrintStream out, boolean color) {
        this.out = out;
        this.color = color;
    }

    /**
     * Guesses whether the terminal understands ANSI colours. NO_COLOR or JAVAQUEST_COLOR=never
     * turn colours off; JAVAQUEST_COLOR=always forces them on.
     */
    public static boolean colorSupported() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("NO_COLOR")) {
            return false;
        }
        String forced = env.getOrDefault("JAVAQUEST_COLOR", "");
        if (forced.equalsIgnoreCase("always")) {
            return true;
        }
        if (forced.equalsIgnoreCase("never")) {
            return false;
        }
        String term = env.getOrDefault("TERM", "");
        return env.containsKey("WT_SESSION")            // Windows Terminal
                || env.containsKey("TERM_PROGRAM")      // VS Code and others
                || env.containsKey("TERMINAL_EMULATOR") // IntelliJ IDEA
                || "ON".equalsIgnoreCase(env.get("ConEmuANSI"))
                || !term.isEmpty() && !term.equals("dumb");
    }

    @Override
    public void welcome() {
        out.println();
        out.println(paint(CYAN + BOLD, "=".repeat(WIDTH)));
        out.println(paint(CYAN + BOLD, center("J A V A Q U E S T")));
        out.println(paint(CYAN, center("The Kernel Dungeon")));
        out.println(paint(CYAN + BOLD, "=".repeat(WIDTH)));
        out.println();
        paragraph("You wake on the cold floor of a server room. The fans hum. Every door is sealed "
                + "by a lock that only working Java code can open. Write the code, run the game, "
                + "and the doors open one by one.");
    }

    @Override
    public void status(Progress progress, int totalRooms) {
        out.println();
        out.println(paint(DIM, " XP " + progress.xp()
                + "  |  Streak " + progress.streak() + " (best " + progress.bestStreak() + ")"
                + "  |  Rooms cleared " + progress.cleared().size() + "/" + totalRooms));
    }

    @Override
    public void roomHeader(Level level) {
        String left = " ROOM " + level.id() + "  " + level.title();
        int gap = Math.max(2, WIDTH - left.length() - level.world().length());
        out.println(RULE);
        out.println(paint(BOLD, left) + " ".repeat(gap) + paint(DIM, level.world()));
        out.println(RULE);
    }

    @Override
    public void room(Level level, int xpIfClearedNow) {
        roomHeader(level);
        paragraph(level.story());
        out.println();
        field("YOUR TASK", level.task());
        field("EDIT", level.stubPath().replace('/', File.separatorChar));
        field("THEN RUN", "gradlew.bat quest");
        field("STUCK?", "gradlew.bat quest --args=\"--hint\"");
        field("READ", level.concept());
        field("REWARD", xpIfClearedNow + " XP");
        out.println();
    }

    @Override
    public void results(RunResult result) {
        out.println();
        for (TestOutcome outcome : result.outcomes()) {
            String tag = switch (outcome.status()) {
                case PASSED -> paint(GREEN, "[PASS] ");
                case FAILED -> paint(RED, "[FAIL] ");
                case NOT_STARTED -> paint(DIM, "[TODO] ");
                case TIMED_OUT -> paint(MAGENTA, "[TIME] ");
                case CRASHED -> paint(RED, "[CRASH]");
            };
            out.println("  " + tag + " " + outcome.name());
            if (outcome.status() != TestOutcome.Status.PASSED) {
                // JUnit puts "expected/but was" after " ==> "; give it its own line.
                for (String part : outcome.message().split(" ==> ", 2)) {
                    for (String line : wrap(part, WIDTH - 11)) {
                        out.println("           " + line);
                    }
                }
            }
        }
        out.println();
    }

    @Override
    public void lockRattles(RunResult result, int failedAttempts) {
        out.println(paint(YELLOW + BOLD, " The lock rattles...") + " " + result.passed() + " of "
                + result.total() + " tumblers turned.");
        paragraph("Fix one problem at a time (start with the first), save, and run gradlew.bat quest again.");
        out.println(paint(DIM, " Failed tries on this room: " + failedAttempts
                + ".  Stuck? gradlew.bat quest --args=\"--hint\""));
        out.println();
    }

    @Override
    public void doorOpens(Level level, int xpAwarded, boolean firstTry, Progress progress) {
        out.println(paint(GREEN + BOLD, " *** CLICK. The door swings open. ***"));
        out.println(" Room " + level.id() + " cleared!  " + paint(GREEN, "+" + xpAwarded + " XP")
                + "  (total " + progress.xp() + " XP)");
        if (firstTry) {
            out.println(" First try! Streak: " + progress.streak());
        } else {
            out.println(paint(DIM, " Streak reset. Clear the next room on your first try to start a new one."));
        }
        out.println();
    }

    @Override
    public void replayPassed(Level level) {
        out.println(paint(GREEN, " Room " + level.id() + " still opens.")
                + " Replays don't award XP, but the practice counts.");
        out.println();
    }

    @Override
    public void hints(Level level, int revealed, int xpIfClearedNow) {
        out.println();
        for (int tier = 1; tier <= revealed; tier++) {
            String name = tier <= HINT_TIERS.length ? HINT_TIERS[tier - 1] : "extra";
            out.println(paint(YELLOW + BOLD, " HINT " + tier + "/" + level.hints().size() + " (" + name + ")"));
            for (String line : level.hints().get(tier - 1).strip().split("\\R")) {
                // Keep line breaks AND indentation (pseudo-code relies on both); only wrap lines that are
                // too long, indenting the continuation so it reads as part of the same line.
                String body = line.stripLeading();
                if (body.isEmpty()) {
                    out.println();
                    continue;
                }
                String indent = "    " + line.substring(0, line.length() - body.length());
                List<String> wrapped = wrap(body, Math.max(20, WIDTH - indent.length() - 2));
                for (int i = 0; i < wrapped.size(); i++) {
                    out.println(indent + (i > 0 ? "  " : "") + wrapped.get(i));
                }
            }
            out.println();
        }
        if (revealed >= level.hints().size()) {
            paragraph("That's every hint for this room. Re-read the failure messages: they say exactly "
                    + "what was expected.");
        }
        if (xpIfClearedNow >= 0) {
            out.println(paint(DIM, " Clearing this room now is worth " + xpIfClearedNow + " XP (full value "
                    + level.xp() + ")."));
        }
        out.println();
    }

    @Override
    public void map(List<Level> levels, Progress progress, int currentRoomId) {
        out.println();
        out.println(paint(BOLD, " THE KERNEL DUNGEON"));
        String world = null;
        for (Level level : levels) {
            if (!level.world().equals(world)) {
                world = level.world();
                out.println();
                out.println(" " + paint(CYAN, world));
            }
            String mark;
            String note = "";
            if (progress.isCleared(level.id())) {
                mark = paint(GREEN, "[#]");
            } else if (level.id() == currentRoomId) {
                mark = paint(YELLOW + BOLD, "[>]");
                note = paint(YELLOW, "  <- you are here");
            } else {
                mark = paint(DIM, "[ ]");
            }
            out.println("   " + mark + " " + String.format("%2d", level.id()) + "  " + level.title() + note);
        }
        out.println();
        out.println(paint(DIM, " [#] cleared   [>] current room   [ ] locked"));
        out.println();
    }

    @Override
    public void victory(Progress progress) {
        out.println();
        out.println(paint(GREEN + BOLD, " Every room in the dungeon is cleared!"));
        out.println(" Total XP: " + progress.xp() + "   Best streak: " + progress.bestStreak());
        paragraph("Replay any room with gradlew.bat quest --args=\"--level N\". New rooms are still being dug.");
        out.println();
    }

    @Override
    public void message(String text) {
        paragraph(text);
        out.println();
    }

    @Override
    public void error(String text) {
        out.println();
        for (String line : wrap(text, WIDTH - 1)) {
            out.println(paint(RED, " " + line));
        }
        out.println();
    }

    @Override
    public void usage() {
        out.println();
        out.println(" How to play:");
        out.println("   gradlew.bat quest                      enter / test your current room");
        out.println("   gradlew.bat quest --args=\"--hint\"      reveal the next hint for the room");
        out.println("   gradlew.bat quest --args=\"--map\"       show the dungeon map");
        out.println("   gradlew.bat quest --args=\"--level 2\"   replay a room you've unlocked");
        out.println("   gradlew.bat quest --args=\"--no-color\"  turn colours off");
        out.println();
    }

    // --- helpers ----------------------------------------------------------------------------

    private String paint(String ansi, String text) {
        return color ? ansi + text + RESET : text;
    }

    private static String center(String text) {
        return " ".repeat(Math.max(0, (WIDTH - text.length()) / 2)) + text;
    }

    private void field(String label, String value) {
        String prefix = " " + String.format("%-10s", label) + " ";
        List<String> lines = wrap(value, WIDTH - prefix.length());
        for (int i = 0; i < lines.size(); i++) {
            out.println((i == 0 ? paint(YELLOW, prefix) : " ".repeat(prefix.length())) + lines.get(i));
        }
    }

    /** Prints text wrapped to the screen width; a blank line in the text starts a new paragraph. */
    private void paragraph(String text) {
        String[] paragraphs = text.strip().split("\\R\\s*\\R");
        for (int i = 0; i < paragraphs.length; i++) {
            if (i > 0) {
                out.println();
            }
            for (String line : wrap(paragraphs[i], WIDTH - 1)) {
                out.println(" " + line);
            }
        }
    }

    /** Greedy word wrap: fills each line with as many words as fit. */
    static List<String> wrap(String text, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (String word : text.strip().split("\\s+")) {
            if (word.isEmpty()) {
                continue;
            }
            if (!line.isEmpty() && line.length() + 1 + word.length() > width) {
                lines.add(line.toString());
                line.setLength(0);
            }
            if (!line.isEmpty()) {
                line.append(' ');
            }
            line.append(word);
        }
        if (!line.isEmpty() || lines.isEmpty()) {
            lines.add(line.toString());
        }
        return lines;
    }
}
