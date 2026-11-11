package quest.levels.w2;

/** Reference solution for Room 9. Exists only to prove the lock can be opened. */
public class Level09_EnchantersForge {

    public enum Enchantment {
        NONE(0, "plain"),
        FIRE(5, "burning"),
        FROST(3, "frozen"),
        STORM(8, "crackling");

        private final int bonus;
        private final String adjective;

        Enchantment(int bonus, String adjective) {
            this.bonus = bonus;
            this.adjective = adjective;
        }

        public int bonus() {
            return bonus;
        }

        public String adjective() {
            return adjective;
        }

        public boolean beats(Enchantment other) {
            return switch (this) {
                case FIRE -> other == FROST;
                case FROST -> other == STORM;
                case STORM -> other == FIRE;
                case NONE -> false;
            };
        }
    }

    public static class Weapon {

        private final String name;
        private final int baseDamage;
        private Enchantment enchantment;

        public Weapon(String name, int baseDamage) {
            this(name, baseDamage, Enchantment.NONE);
        }

        public Weapon(String name, int baseDamage, Enchantment enchantment) {
            this.name = name;
            this.baseDamage = baseDamage;
            this.enchantment = enchantment;
        }

        public String name() {
            return name;
        }

        public Enchantment enchantment() {
            return enchantment;
        }

        public int damage() {
            return baseDamage + enchantment.bonus();
        }

        public int damageAgainst(Enchantment enemy) {
            return enchantment.beats(enemy) ? damage() * 2 : damage();
        }

        public void reforge(Enchantment newEnchantment) {
            this.enchantment = newEnchantment;
        }

        public String describe() {
            return enchantment.adjective() + " " + name;
        }
    }
}
