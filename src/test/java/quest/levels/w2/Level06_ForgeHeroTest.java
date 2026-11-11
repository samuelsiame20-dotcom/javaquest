package quest.levels.w2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w2.Level06_ForgeHero.Hero;

/** The lock for Room 6. */
@Timeout(2)
@DisplayName("Room 6 - Forge a Hero")
class Level06_ForgeHeroTest {

    @Test
    @DisplayName("forges a hero with full HP and private fields")
    void constructorAndEncapsulation() {
        Hero hero = new Hero("Ada", 120);

        assertEquals("Ada", hero.getName(), "new Hero(\"Ada\", 120).getName() should be \"Ada\".");
        assertEquals(120, hero.getMaxHp(), "new Hero(\"Ada\", 120).getMaxHp() should be 120.");
        assertEquals(120, hero.getHp(), "A new hero should start with full HP: getHp() should equal getMaxHp().");
        for (Field field : Hero.class.getDeclaredFields()) {
            assertTrue(field.isSynthetic() || Modifier.isPrivate(field.getModifiers()),
                    "Field '" + field.getName() + "' should be private: encapsulation means other classes "
                            + "go through getters and methods, never straight to the field.");
        }
    }

    @Test
    @DisplayName("overloaded constructor gives 100 HP")
    void overloadedConstructor() {
        Hero hero = new Hero("Bo");

        assertEquals("Bo", hero.getName(), "new Hero(\"Bo\").getName() should be \"Bo\".");
        assertEquals(100, hero.getMaxHp(), "new Hero(\"Bo\") should have 100 max HP. Tip: let it call the "
                + "other constructor with this(name, 100) instead of repeating the code.");
        assertEquals(100, hero.getHp(), "new Hero(\"Bo\") should start with 100 HP.");
    }

    @Test
    @DisplayName("rejects invalid heroes and negative damage")
    void validation() {
        assertThrows(IllegalArgumentException.class, () -> new Hero("  ", 50),
                "new Hero(\"  \", 50) should throw IllegalArgumentException: a blank name isn't a name. "
                        + "Check name == null || name.isBlank().");
        assertThrows(IllegalArgumentException.class, () -> new Hero(null, 50),
                "new Hero(null, 50) should throw IllegalArgumentException, not NullPointerException: check "
                        + "for null first.");
        assertThrows(IllegalArgumentException.class, () -> new Hero("Cy", 0),
                "new Hero(\"Cy\", 0) should throw IllegalArgumentException: max HP must be positive.");
        assertThrows(IllegalArgumentException.class, () -> new Hero("Cy", 50).takeDamage(-5),
                "takeDamage(-5) should throw IllegalArgumentException: negative damage would be healing.");
    }

    @Test
    @DisplayName("keeps HP between 0 and max")
    void hpStaysInRange() {
        Hero hero = new Hero("Dee", 100);

        hero.takeDamage(30);
        hero.heal(50);
        assertEquals(100, hero.getHp(), "After takeDamage(30) then heal(50), HP should be 100, not 120: "
                + "heal() should clamp at max HP.");
        hero.takeDamage(150);
        assertEquals(0, hero.getHp(), "takeDamage(150) should clamp HP at 0, not go negative. Check your if.");
        assertFalse(hero.isAlive(), "A hero with 0 HP should not be alive.");
        hero.heal(20);
        assertEquals(0, hero.getHp(), "heal(20) on a fallen hero should do nothing: check isAlive() first.");
    }

    @Test
    @DisplayName("counts every hero once, whichever constructor forged it")
    void staticCounter() {
        int before = Hero.heroesForged();
        new Hero("Eve", 80);
        new Hero("Fin");

        assertEquals(before + 2, Hero.heroesForged(),
                "Forging 2 heroes should raise heroesForged() by exactly 2. The counter must be a static "
                        + "field (one shared copy, not one per hero). If it went up by 3, the one-argument "
                        + "constructor counts twice: let only the two-argument constructor do the counting.");
    }
}
