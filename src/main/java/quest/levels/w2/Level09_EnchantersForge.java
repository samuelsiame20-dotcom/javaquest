package quest.levels.w2;

/**
 * ROOM 9 - THE ENCHANTER'S FORGE
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>A weapon HAS an enchantment (composition). It does not extend one: there are no
 * "FireSword" or "FrostAxe" subclasses, so an enchantment can be swapped at runtime.
 */
public class Level09_EnchantersForge {

    /** An enum with fields: each constant carries its own bonus and adjective. */
    public enum Enchantment {
        NONE(0, "plain"),
        FIRE(5, "burning"),
        FROST(3, "frozen"),
        STORM(8, "crackling");

        private final int bonus;
        private final String adjective;

        // Given: an enum constructor runs once per constant, when the enum is first used.
        Enchantment(int bonus, String adjective) {
            this.bonus = bonus;
            this.adjective = adjective;
        }

        public int bonus() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public String adjective() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /**
         * FIRE beats FROST, FROST beats STORM, STORM beats FIRE.
         * NONE beats nothing, and nothing beats NONE.
         */
        public boolean beats(Enchantment other) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** A weapon built FROM an enchantment, not derived from one. */
    public static class Weapon {

        // TODO: add private fields, including one that holds the Enchantment.

        /** An unenchanted weapon (Enchantment.NONE). */
        public Weapon(String name, int baseDamage) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public Weapon(String name, int baseDamage, Enchantment enchantment) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public String name() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public Enchantment enchantment() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Base damage plus the enchantment's bonus. */
        public int damage() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Double damage if this weapon's enchantment beats the enemy's, otherwise normal damage. */
        public int damageAgainst(Enchantment enemy) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Swaps the enchantment. Same weapon, new behaviour: that's what composition buys you. */
        public void reforge(Enchantment newEnchantment) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** The adjective, a space, then the name, e.g. {@code "burning Sword"} or {@code "plain Axe"}. */
        public String describe() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }
}
