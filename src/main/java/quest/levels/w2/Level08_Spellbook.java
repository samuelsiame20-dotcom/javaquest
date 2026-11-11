package quest.levels.w2;

import java.util.List;

/**
 * ROOM 8 - SPELLBOOK
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>Two ways to share behaviour live here: the {@link Spell} INTERFACE (a contract, plus default and
 * static methods) and the {@link BaseSpell} ABSTRACT CLASS (shared fields and code for most spells).
 */
public class Level08_Spellbook {

    /** What every spell can do. */
    public interface Spell {

        String name();

        int manaCost();

        int power();

        /**
         * Default method: every spell gets this for free unless it overrides it.
         * For example {@code "Fireball (cost 10, power 30)"}.
         */
        default String describe() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /**
         * Static method: belongs to the interface itself, called as {@code Spell.bestAffordable(...)}.
         * Returns the most powerful spell costing at most {@code mana}; on a tie, the first one in the
         * list; {@code null} if none is affordable.
         */
        static Spell bestAffordable(List<Spell> spells, int mana) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** A second interface: spells that last for several turns. */
    public interface Channelled {

        int turns();
    }

    /** Shared state and code for spells that have a fixed name and mana cost. */
    public abstract static class BaseSpell implements Spell {

        // TODO: add private fields.

        protected BaseSpell(String name, int manaCost) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public String name() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int manaCost() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        // power() is left abstract: each spell decides its own.
    }

    /** "Fireball": costs 10 mana, power 30. */
    public static class Fireball extends BaseSpell {

        public Fireball() {
            super("TODO", 0); // TODO: fix this call.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int power() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /**
     * "Blizzard": lasts {@code turns} turns, costs 8 mana per turn and has power 12 per turn.
     * It is ALSO a {@link Channelled} spell: make it implement that interface too.
     */
    public static class Blizzard extends BaseSpell {

        public Blizzard(int turns) {
            super("TODO", 0); // TODO: fix this call.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int power() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** "Spark": costs 2 mana, power 5. Implements Spell directly, without BaseSpell. */
    public static class Spark implements Spell {

        @Override
        public String name() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int manaCost() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int power() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** Total power of every spell in the list. */
    public static int totalPower(List<Spell> spells) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
