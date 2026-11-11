package quest.levels.w2;

import java.util.List;

/** Reference solution for Room 8. Exists only to prove the lock can be opened. */
public class Level08_Spellbook {

    public interface Spell {

        String name();

        int manaCost();

        int power();

        default String describe() {
            return name() + " (cost " + manaCost() + ", power " + power() + ")";
        }

        static Spell bestAffordable(List<Spell> spells, int mana) {
            Spell best = null;
            for (Spell spell : spells) {
                if (spell.manaCost() <= mana && (best == null || spell.power() > best.power())) {
                    best = spell;
                }
            }
            return best;
        }
    }

    public interface Channelled {

        int turns();
    }

    public abstract static class BaseSpell implements Spell {

        private final String name;
        private final int manaCost;

        protected BaseSpell(String name, int manaCost) {
            this.name = name;
            this.manaCost = manaCost;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public int manaCost() {
            return manaCost;
        }
    }

    public static class Fireball extends BaseSpell {

        public Fireball() {
            super("Fireball", 10);
        }

        @Override
        public int power() {
            return 30;
        }
    }

    public static class Blizzard extends BaseSpell implements Channelled {

        private final int turns;

        public Blizzard(int turns) {
            super("Blizzard", 8 * turns);
            this.turns = turns;
        }

        @Override
        public int power() {
            return 12 * turns;
        }

        @Override
        public int turns() {
            return turns;
        }
    }

    public static class Spark implements Spell {

        @Override
        public String name() {
            return "Spark";
        }

        @Override
        public int manaCost() {
            return 2;
        }

        @Override
        public int power() {
            return 5;
        }
    }

    public static int totalPower(List<Spell> spells) {
        int total = 0;
        for (Spell spell : spells) {
            total += spell.power();
        }
        return total;
    }
}
