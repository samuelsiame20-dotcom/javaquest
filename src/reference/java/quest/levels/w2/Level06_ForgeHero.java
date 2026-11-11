package quest.levels.w2;

/** Reference solution for Room 6. Exists only to prove the lock can be opened. */
public class Level06_ForgeHero {

    public static class Hero {

        private static int forged = 0;

        private final String name;
        private final int maxHp;
        private int hp;

        public Hero(String name, int maxHp) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("A hero needs a name");
            }
            if (maxHp <= 0) {
                throw new IllegalArgumentException("Max HP must be positive, was " + maxHp);
            }
            this.name = name;
            this.maxHp = maxHp;
            this.hp = maxHp;
            forged++;
        }

        public Hero(String name) {
            this(name, 100);
        }

        public String getName() {
            return name;
        }

        public int getHp() {
            return hp;
        }

        public int getMaxHp() {
            return maxHp;
        }

        public boolean isAlive() {
            return hp > 0;
        }

        public void takeDamage(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Damage can't be negative: " + amount);
            }
            hp = Math.max(0, hp - amount);
        }

        public void heal(int amount) {
            if (isAlive()) {
                hp = Math.min(maxHp, hp + amount);
            }
        }

        public static int heroesForged() {
            return forged;
        }
    }
}
