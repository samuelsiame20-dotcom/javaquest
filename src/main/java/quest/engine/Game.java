package quest.engine;

/**
 * Entry point for {@code gradlew.bat quest}.
 *
 * <p>Phase 0: prints the welcome screen only. Phase 1 adds argument parsing
 * (--hint, --map, --level N), the level runner and saved progress.
 */
public final class Game {

    private static final String WELCOME = """

            ==============================================
                         J A V A Q U E S T
                        The Kernel Dungeon
            ==============================================

            You wake on the cold floor of a server room.
            The fans hum. Every door is sealed by a lock
            that only working Java code can open.

            The dungeon is still being dug (Phase 0).
            The first rooms open in Phase 1.

            Commands:
              gradlew.bat quest                    play / test your current room
              gradlew.bat quest --args="--hint"    reveal a hint (costs XP)
              gradlew.bat quest --args="--map"     show the dungeon map
              gradlew.bat quest --args="--level 5" replay an unlocked room
            """;

    private Game() {
    }

    public static void main(String[] args) {
        System.out.println(WELCOME);
        if (args.length > 0) {
            System.out.println("  (\"" + String.join(" ", args)
                    + "\" isn't wired up yet - it arrives in Phase 1.)");
            System.out.println();
        }
    }
}
