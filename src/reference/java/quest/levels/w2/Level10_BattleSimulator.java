package quest.levels.w2;

/** Reference solution for Room 10. Exists only to prove the lock can be opened. */
public class Level10_BattleSimulator {

    public record Blade(String name, int damage) {
    }

    public interface Fighter {

        String name();

        int hp();

        int attackPower();

        void takeHit(int damage);

        default boolean isAlive() {
            return hp() > 0;
        }

        default void attack(Fighter target) {
            target.takeHit(attackPower());
        }
    }

    public static class Knight implements Fighter {

        private static final int SHIELD = 3;

        private final String name;
        private final Blade blade;
        private int hp;

        public Knight(String name, int hp, Blade blade) {
            this.name = name;
            this.hp = hp;
            this.blade = blade;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public int hp() {
            return hp;
        }

        @Override
        public int attackPower() {
            return blade.damage();
        }

        @Override
        public void takeHit(int damage) {
            hp = Math.max(0, hp - Math.max(0, damage - SHIELD));
        }
    }

    public static class Mage implements Fighter {

        private static final int BOLT = 20;
        private static final int BOLT_COST = 10;
        private static final int STAFF = 4;

        private final String name;
        private int hp;
        private int mana;

        public Mage(String name, int hp, int mana) {
            this.name = name;
            this.hp = hp;
            this.mana = mana;
        }

        public int mana() {
            return mana;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public int hp() {
            return hp;
        }

        @Override
        public int attackPower() {
            return mana >= BOLT_COST ? BOLT : STAFF;
        }

        @Override
        public void takeHit(int damage) {
            hp = Math.max(0, hp - damage);
        }

        @Override
        public void attack(Fighter target) {
            int power = attackPower();
            if (power == BOLT) {
                mana -= BOLT_COST;
            }
            target.takeHit(power);
        }
    }

    public static class Berserker implements Fighter {

        private final String name;
        private final int maxHp;
        private int hp;

        public Berserker(String name, int maxHp) {
            this.name = name;
            this.maxHp = maxHp;
            this.hp = maxHp;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public int hp() {
            return hp;
        }

        @Override
        public int attackPower() {
            return 10 + (maxHp - hp) / 10;
        }

        @Override
        public void takeHit(int damage) {
            hp = Math.max(0, hp - damage);
        }
    }

    public static Fighter battle(Fighter first, Fighter second) {
        for (int round = 0; round < 100; round++) {
            first.attack(second);
            if (!second.isAlive()) {
                return first;
            }
            second.attack(first);
            if (!first.isAlive()) {
                return second;
            }
        }
        return null;
    }
}
