package quest.levels.w2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w2.Level10_BattleSimulator.Berserker;
import quest.levels.w2.Level10_BattleSimulator.Blade;
import quest.levels.w2.Level10_BattleSimulator.Fighter;
import quest.levels.w2.Level10_BattleSimulator.Knight;
import quest.levels.w2.Level10_BattleSimulator.Mage;

/** The lock for Room 10 (World 2 boss). */
@Timeout(2)
@DisplayName("Room 10 - BOSS: The Battle Simulator")
class Level10_BattleSimulatorTest {

    /** A practice dummy: records every hit it receives. Any class can join in by implementing Fighter. */
    private static final class Dummy implements Fighter {
        final List<Integer> hits = new ArrayList<>();

        @Override
        public String name() {
            return "Dummy";
        }

        @Override
        public int hp() {
            return 1000;
        }

        @Override
        public int attackPower() {
            return 0;
        }

        @Override
        public void takeHit(int damage) {
            hits.add(damage);
        }
    }

    @Test
    @DisplayName("a knight strikes with its blade and blocks 3 of every hit")
    void knight() {
        Knight knight = new Knight("Ser Ada", 30, new Blade("Longsword", 9));
        Dummy dummy = new Dummy();

        knight.attack(dummy);
        assertEquals(List.of(9), dummy.hits, "A knight with a 9-damage blade should hit the dummy for 9. The "
                + "default attack() in Fighter should call target.takeHit(attackPower()).");
        knight.takeHit(10);
        assertEquals(23, knight.hp(), "A knight hit for 10 should lose 7: the shield absorbs 3.");
        knight.takeHit(2);
        assertEquals(23, knight.hp(), "A knight hit for 2 should lose nothing: the shield absorbs it all, and "
                + "a hit must never HEAL (damage below 0 counts as 0).");
        knight.takeHit(100);
        assertEquals(0, knight.hp(), "HP should never go below 0.");
        assertFalse(knight.isAlive(), "A knight with 0 HP is not alive: the default isAlive() checks hp() > 0.");
    }

    @Test
    @DisplayName("a mage casts bolts until the mana runs out")
    void mage() {
        Mage mage = new Mage("Ivy", 40, 30);
        Dummy dummy = new Dummy();

        for (int i = 0; i < 5; i++) {
            mage.attack(dummy);
        }
        assertEquals(List.of(20, 20, 20, 4, 4), dummy.hits,
                "A mage with 30 mana should hit 20, 20, 20, then bonk 4, 4. Override attack() to deal "
                        + "attackPower() AND spend 10 mana on a bolt.");
        assertEquals(0, mage.mana(), "After 3 bolts, 30 mana should be 0, never negative.");
    }

    @Test
    @DisplayName("a berserker hits harder the more it's hurt")
    void berserker() {
        Berserker orm = new Berserker("Orm", 100);

        assertEquals(10, orm.attackPower(), "A berserker at full HP should hit for 10.");
        orm.takeHit(55);
        assertEquals(45, orm.hp(), "A berserker hit for 55 should have 45 HP left.");
        assertEquals(15, orm.attackPower(), "After losing 55 HP: 10 + 55 / 10 = 15 (int division rounds down). "
                + "Keep max HP in a field so you can work out the HP lost.");
    }

    @Test
    @DisplayName("battle: the first striker wins an even fight")
    void firstStrikerWins() {
        Knight a = new Knight("Ser A", 30, new Blade("Sword", 10));
        Knight b = new Knight("Ser B", 30, new Blade("Sword", 10));

        assertSame(a, Level10_BattleSimulator.battle(a, b),
                "Two identical knights: the one who attacks first should win. Each round, first attacks, then "
                        + "check if second fell BEFORE second gets to attack back.");
        assertEquals(0, b.hp(), "The loser should end on 0 HP.");
    }

    @Test
    @DisplayName("battle works for any two fighters, and spots a stalemate")
    void anyFightersAndStalemate() {
        Mage ivy = new Mage("Ivy", 40, 30);
        Berserker orm = new Berserker("Orm", 60);
        assertSame(ivy, Level10_BattleSimulator.battle(ivy, orm),
                "Mage Ivy (40 HP, 30 mana) attacking first should beat Berserker Orm (60 HP) with three bolts. "
                        + "battle() only uses the Fighter interface, so it works for every kind of fighter.");

        Knight weak = new Knight("Ser Weak", 30, new Blade("Twig", 2));
        Knight weaker = new Knight("Ser Weaker", 30, new Blade("Stick", 3));
        assertNull(Level10_BattleSimulator.battle(weak, weaker),
                "Two knights whose shields absorb every hit can never win: after 100 rounds battle() should "
                        + "return null instead of looping forever.");
    }
}
