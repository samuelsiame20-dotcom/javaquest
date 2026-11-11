package quest.levels.w2;

import java.util.List;

/** Reference solution for Room 7. Exists only to prove the lock can be opened. */
public class Level07_Bestiary {

    public static class Monster {

        private final String name;
        private int hp;

        public Monster(String name, int hp) {
            this.name = name;
            this.hp = hp;
        }

        public String getName() {
            return name;
        }

        public int getHp() {
            return hp;
        }

        public int attackDamage() {
            return 5;
        }

        public void takeHit(int damage) {
            hp = Math.max(0, hp - damage);
        }

        public String describe() {
            return name + " (" + hp + " HP)";
        }
    }

    public static class Goblin extends Monster {

        public Goblin(String name) {
            super(name, 30);
        }

        @Override
        public int attackDamage() {
            return 7;
        }
    }

    public static class Troll extends Monster {

        public Troll() {
            super("Troll", 80);
        }

        @Override
        public void takeHit(int damage) {
            super.takeHit(damage / 2);
        }
    }

    public static class Dragon extends Monster {

        public Dragon(String name) {
            super(name, 200);
        }

        @Override
        public int attackDamage() {
            return 25;
        }

        @Override
        public String describe() {
            return super.describe() + " - breathes fire";
        }
    }

    public static int totalAttack(List<Monster> pack) {
        int total = 0;
        for (Monster monster : pack) {
            total += monster.attackDamage();
        }
        return total;
    }
}
