package quest.levels.w2;

/**
 * ROOM 10 - BOSS: THE BATTLE SIMULATOR
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>Everything from World 2 meets here: classes and encapsulation, an interface with default
 * methods, overriding, composition (a knight HAS a blade), and polymorphism ({@link #battle} works
 * for any two fighters).
 *
 * <p>HP never goes below 0 for any fighter.
 */
public class Level10_BattleSimulator {

    /** Given: a simple value type. Records get a constructor, accessors, equals and toString for free. */
    public record Blade(String name, int damage) {
    }

    /** Anything that can fight. */
    public interface Fighter {

        String name();

        int hp();

        /** How much damage this fighter's NEXT attack would deal. */
        int attackPower();

        /** Receive a hit of {@code damage} (before any armour). */
        void takeHit(int damage);

        /** Default: alive while HP is above 0. */
        default boolean isAlive() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Default: hit the target with this fighter's attack power. */
        default void attack(Fighter target) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** A knight attacks with a blade's damage. A shield absorbs 3 of every hit (a hit never heals). */
    public static class Knight implements Fighter {

        // TODO: add private fields (the Blade is one of them).

        public Knight(String name, int hp, Blade blade) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public String name() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int hp() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int attackPower() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public void takeHit(int damage) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /**
     * A mage casts a bolt (20 damage, costs 10 mana) while mana lasts, then bonks with a staff
     * (4 damage, free). Override attack() so it also spends the mana.
     */
    public static class Mage implements Fighter {

        // TODO: add private fields.

        public Mage(String name, int hp, int mana) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public int mana() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public String name() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int hp() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int attackPower() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public void takeHit(int damage) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        // TODO: override attack(Fighter target).
    }

    /** A berserker starts at full HP and hits harder when hurt: 10 + (HP lost) / 10. */
    public static class Berserker implements Fighter {

        // TODO: add private fields.

        public Berserker(String name, int maxHp) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public String name() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int hp() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public int attackPower() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        @Override
        public void takeHit(int damage) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /**
     * Fights until one falls: {@code first} attacks, then {@code second} (if still alive), and so on.
     * Returns the winner, or {@code null} if both are still standing after 100 rounds.
     */
    public static Fighter battle(Fighter first, Fighter second) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
