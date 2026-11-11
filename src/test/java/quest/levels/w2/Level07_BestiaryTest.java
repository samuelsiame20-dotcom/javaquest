package quest.levels.w2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w2.Level07_Bestiary.Dragon;
import quest.levels.w2.Level07_Bestiary.Goblin;
import quest.levels.w2.Level07_Bestiary.Monster;
import quest.levels.w2.Level07_Bestiary.Troll;

/** The lock for Room 7. */
@Timeout(2)
@DisplayName("Room 7 - Monster Bestiary")
class Level07_BestiaryTest {

    @Test
    @DisplayName("an ordinary monster hits for 5 and can't drop below 0 HP")
    void plainMonster() {
        Monster slime = new Monster("Slime", 10);

        assertEquals(5, slime.attackDamage(), "new Monster(\"Slime\", 10).attackDamage() should be 5.");
        assertEquals("Slime (10 HP)", slime.describe(), "describe() should be exactly \"Slime (10 HP)\".");
        slime.takeHit(25);
        assertEquals(0, slime.getHp(), "takeHit(25) on a 10 HP monster should leave 0 HP, not -15.");
    }

    @Test
    @DisplayName("a goblin passes its stats up with super()")
    void goblin() {
        Monster grik = new Goblin("Grik");

        assertEquals("Grik", grik.getName(), "new Goblin(\"Grik\").getName() should be \"Grik\": pass the name "
                + "on to super(...).");
        assertEquals(30, grik.getHp(), "A new goblin should have 30 HP: call super(name, 30).");
        assertEquals(7, grik.attackDamage(), "A goblin should hit for 7. Override attackDamage() in Goblin "
                + "(with @Override) instead of changing Monster's version.");
    }

    @Test
    @DisplayName("a troll's thick skin halves every hit, even through a Monster variable")
    void trollOverridesTakeHit() {
        Monster troll = new Troll();

        assertEquals("Troll (80 HP)", troll.describe(), "new Troll() should describe itself as \"Troll (80 HP)\".");
        troll.takeHit(20);
        assertEquals(70, troll.getHp(), "A troll hit for 20 should lose 10 (half). If it lost 20, your "
                + "takeHit is OVERLOADING instead of OVERRIDING: the parameter must be exactly (int damage), "
                + "and @Override would have caught it.");
        troll.takeHit(15);
        assertEquals(63, troll.getHp(), "A troll hit for 15 should lose 7 (half, rounded down by int division).");
    }

    @Test
    @DisplayName("a dragon builds on Monster's description with super.describe()")
    void dragon() {
        Monster smaug = new Dragon("Smaug");

        assertEquals(25, smaug.attackDamage(), "A dragon should hit for 25.");
        assertEquals("Smaug (200 HP) - breathes fire", smaug.describe(),
                "A dragon's describe() should be \"Smaug (200 HP) - breathes fire\". Reuse the parent: "
                        + "return super.describe() + \" - breathes fire\".");
        smaug.takeHit(50);
        assertEquals("Smaug (150 HP) - breathes fire", smaug.describe(),
                "After takeHit(50) the dragon's description should show 150 HP: build it from the current HP.");
    }

    @Test
    @DisplayName("adds up a mixed pack's attacks (polymorphism in a loop)")
    void totalAttack() {
        List<Monster> pack = List.of(new Goblin("Grik"), new Dragon("Smaug"), new Troll(), new Monster("Slime", 10));

        assertEquals(42, Level07_Bestiary.totalAttack(pack),
                "totalAttack(goblin, dragon, troll, slime) should be 7 + 25 + 5 + 5 = 42. Call attackDamage() "
                        + "on each Monster and Java runs the right override for each one. No instanceof needed.");
    }
}
