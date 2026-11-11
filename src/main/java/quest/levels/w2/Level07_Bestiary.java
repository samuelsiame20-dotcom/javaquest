package quest.levels.w2;

import java.util.List;

/**
 * ROOM 7 - MONSTER BESTIARY
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>The subclasses already say {@code extends Monster}. Your job: give each one the right
 * {@code super(...)} call, and OVERRIDE the methods that make it different (add them yourself,
 * with {@code @Override} on each).
 */
public class Level07_Bestiary {

    /** Any creature of the dungeon. */
    public static class Monster {

        // TODO: add private fields.

        public Monster(String name, int hp) {
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

        /** An ordinary monster hits for 5. */
        public int attackDamage() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Loses that much HP, never going below 0. */
        public void takeHit(int damage) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** For example {@code "Slime (10 HP)"}. */
        public String describe() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** A goblin starts with 30 HP and hits for 7. */
    public static class Goblin extends Monster {

        public Goblin(String name) {
            super(name, 0); // TODO: a goblin starts with 30 HP. Fix this call (it must stay the first line).
            throw new UnsupportedOperationException("TODO");
        }

        // TODO: override attackDamage().
    }

    /** A troll is always named "Troll", starts with 80 HP, and its thick skin halves every hit (rounding down). */
    public static class Troll extends Monster {

        public Troll() {
            super("TODO", 0); // TODO: fix this call.
            throw new UnsupportedOperationException("TODO");
        }

        // TODO: override takeHit(int damage). Reuse Monster's version with super.takeHit(...).
    }

    /** A dragon starts with 200 HP, hits for 25, and its description ends with " - breathes fire". */
    public static class Dragon extends Monster {

        public Dragon(String name) {
            super(name, 0); // TODO: fix this call.
            throw new UnsupportedOperationException("TODO");
        }

        // TODO: override attackDamage() and describe(). Reuse Monster's describe() with super.describe().
    }

    /** The total attack damage of a whole pack of monsters, whatever kinds they are. */
    public static int totalAttack(List<Monster> pack) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
