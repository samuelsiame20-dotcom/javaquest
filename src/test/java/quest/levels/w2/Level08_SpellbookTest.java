package quest.levels.w2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w2.Level08_Spellbook.Blizzard;
import quest.levels.w2.Level08_Spellbook.Channelled;
import quest.levels.w2.Level08_Spellbook.Fireball;
import quest.levels.w2.Level08_Spellbook.Spark;
import quest.levels.w2.Level08_Spellbook.Spell;

/** The lock for Room 8. */
@Timeout(2)
@DisplayName("Room 8 - Spellbook")
class Level08_SpellbookTest {

    @Test
    @DisplayName("a fireball gets its name and cost from the abstract BaseSpell")
    void fireball() {
        Spell fireball = new Fireball();

        assertEquals("Fireball", fireball.name(), "new Fireball().name() should be \"Fireball\": pass it to "
                + "super(...) so BaseSpell stores it.");
        assertEquals(10, fireball.manaCost(), "new Fireball().manaCost() should be 10.");
        assertEquals(30, fireball.power(), "new Fireball().power() should be 30.");
    }

    @Test
    @DisplayName("every spell shares the default describe()")
    void defaultDescribe() {
        assertEquals("Fireball (cost 10, power 30)", new Fireball().describe(),
                "Fireball's describe() should be \"Fireball (cost 10, power 30)\". Write it ONCE, as the "
                        + "default method in Spell, using name(), manaCost() and power().");
        assertEquals("Spark (cost 2, power 5)", new Spark().describe(),
                "Spark's describe() should be \"Spark (cost 2, power 5)\": Spark gets the same default method "
                        + "even though it doesn't extend BaseSpell.");
    }

    @Test
    @DisplayName("a blizzard is a Spell AND a Channelled spell")
    void blizzardHasTwoInterfaces() {
        Spell blizzard = new Blizzard(3);

        assertEquals("Blizzard", blizzard.name(), "new Blizzard(3).name() should be \"Blizzard\".");
        assertEquals(24, blizzard.manaCost(), "new Blizzard(3).manaCost() should be 8 x 3 = 24: work out the "
                + "cost before passing it to super(...).");
        assertEquals(36, blizzard.power(), "new Blizzard(3).power() should be 12 x 3 = 36.");
        assertTrue(blizzard instanceof Channelled, "Blizzard should also implement Channelled: "
                + "class Blizzard extends BaseSpell implements Channelled. One class, one parent, many interfaces.");
        assertEquals(3, ((Channelled) blizzard).turns(), "new Blizzard(3).turns() should be 3.");
    }

    @Test
    @DisplayName("Spell.bestAffordable picks the strongest spell you can pay for")
    void bestAffordable() {
        Spell spark = new Spark();
        Spell fireball = new Fireball();
        Spell blizzard = new Blizzard(2);   // cost 16, power 24
        List<Spell> book = List.of(spark, fireball, blizzard);

        assertSame(fireball, Spell.bestAffordable(book, 12),
                "With 12 mana, bestAffordable should pick Fireball (power 30, cost 10). Blizzard costs 16, "
                        + "too much: skip spells you can't afford.");
        assertSame(spark, Spell.bestAffordable(book, 2), "With 2 mana, only Spark (cost 2) is affordable: "
                + "\"at most mana\" means <=.");
        assertNull(Spell.bestAffordable(book, 1), "With 1 mana nothing is affordable, so return null.");
    }

    @Test
    @DisplayName("adds up the power of a mixed spellbook")
    void totalPower() {
        List<Spell> book = List.of(new Spark(), new Fireball(), new Blizzard(1));

        assertEquals(47, Level08_Spellbook.totalPower(book),
                "totalPower(spark, fireball, blizzard(1)) should be 5 + 30 + 12 = 47: call power() on each Spell.");
    }
}
