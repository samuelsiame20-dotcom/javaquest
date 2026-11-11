package quest.levels.w2;

/**
 * ROOM 6 - FORGE A HERO
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code,
 * and add the fields the Hero needs.
 */
public class Level06_ForgeHero {

    /** A hero with a name and hit points (HP). All fields must be private. */
    public static class Hero {

        // TODO: add private fields (instance fields for each hero, and one static counter for all heroes).

        /**
         * Forges a hero with full HP. Counts towards {@link #heroesForged()}.
         *
         * @throws IllegalArgumentException if name is null or blank, or maxHp is not positive
         */
        public Hero(String name, int maxHp) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Overloaded constructor: a hero with 100 max HP. Counts once towards heroesForged(). */
        public Hero(String name) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public String getName() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public int getHp() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public int getMaxHp() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** True while HP is above 0. */
        public boolean isAlive() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /**
         * Loses HP, but never below 0.
         *
         * @throws IllegalArgumentException if amount is negative
         */
        public void takeDamage(int amount) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Gains HP, but never above max HP. A fallen hero (0 HP) can't be healed. */
        public void heal(int amount) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** How many heroes have been forged so far, by either constructor. Shared by ALL heroes. */
        public static int heroesForged() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }
}
