package quest.engine;

/** How much XP clearing a room is worth. The game also uses this to preview the reward. */
public final class XpRules {

    private XpRules() {
    }

    /**
     * XP awarded when a room is cleared for the first time.
     *
     * @param baseXp    the room's full value (20 for the tutorial, 100 for a normal room)
     * @param hintsUsed how many hint tiers the player revealed for this room (0 to 3)
     * @param firstTry  true if the room is cleared with no failed attempts
     * @return XP to add (the game treats anything negative as 0)
     */
    public static int award(int baseXp, int hintsUsed, boolean firstTry) {
        // TODO(Sammy): decide the XP policy - see the note from Claude Code.
        // Placeholder until then: hints and first tries don't change the reward.
        return baseXp;
    }
}
