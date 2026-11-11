package quest.levels.w2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w2.Level09_EnchantersForge.Enchantment;
import quest.levels.w2.Level09_EnchantersForge.Weapon;

/** The lock for Room 9. */
@Timeout(2)
@DisplayName("Room 9 - The Enchanter's Forge")
class Level09_EnchantersForgeTest {

    @Test
    @DisplayName("each enchantment knows its bonus and who it beats")
    void enchantments() {
        assertEquals(5, Enchantment.FIRE.bonus(), "FIRE.bonus() should be 5: return the field set by the constructor.");
        assertEquals("crackling", Enchantment.STORM.adjective(), "STORM.adjective() should be \"crackling\".");
        assertTrue(Enchantment.FIRE.beats(Enchantment.FROST), "FIRE should beat FROST.");
        assertTrue(Enchantment.FROST.beats(Enchantment.STORM), "FROST should beat STORM.");
        assertTrue(Enchantment.STORM.beats(Enchantment.FIRE), "STORM should beat FIRE.");
        assertFalse(Enchantment.FROST.beats(Enchantment.FIRE), "FROST should NOT beat FIRE: beating only "
                + "goes one way round the circle. A switch on 'this' works well here.");
        assertFalse(Enchantment.FIRE.beats(Enchantment.NONE), "Nothing beats NONE.");
        assertFalse(Enchantment.NONE.beats(Enchantment.FROST), "NONE beats nothing.");
    }

    @Test
    @DisplayName("a weapon HAS an enchantment instead of extending one")
    void plainWeaponUsesComposition() {
        Weapon axe = new Weapon("Axe", 10);

        assertSame(Enchantment.NONE, axe.enchantment(), "new Weapon(\"Axe\", 10) should start with Enchantment.NONE.");
        assertEquals(10, axe.damage(), "An unenchanted axe with base damage 10 should deal 10.");
        assertEquals("plain Axe", axe.describe(), "describe() should be \"plain Axe\": adjective, space, name.");
        assertSame(Object.class, Weapon.class.getSuperclass(), "Weapon should not extend another class: "
                + "composition means a weapon HAS an Enchantment, it isn't one.");
        assertTrue(Arrays.stream(Weapon.class.getDeclaredFields()).map(Field::getType).anyMatch(Enchantment.class::equals),
                "Weapon should keep its enchantment in a field of type Enchantment.");
    }

    @Test
    @DisplayName("an enchanted weapon adds the enchantment's bonus")
    void enchantedWeapon() {
        Weapon sword = new Weapon("Sword", 12, Enchantment.FIRE);

        assertEquals(17, sword.damage(), "A Sword (12) with FIRE (+5) should deal 17. Ask the enchantment for "
                + "its bonus: enchantment.bonus().");
        assertEquals("burning Sword", sword.describe(), "describe() should be \"burning Sword\".");
        assertEquals("Sword", sword.name(), "name() should be \"Sword\".");
    }

    @Test
    @DisplayName("reforging swaps the enchantment at runtime")
    void reforge() {
        Weapon sword = new Weapon("Sword", 12, Enchantment.FIRE);

        sword.reforge(Enchantment.STORM);
        assertSame(Enchantment.STORM, sword.enchantment(), "After reforge(STORM) the enchantment should be STORM.");
        assertEquals(20, sword.damage(), "After reforge(STORM) the Sword should deal 12 + 8 = 20.");
        assertEquals("crackling Sword", sword.describe(), "After reforge(STORM) describe() should be \"crackling Sword\".");
    }

    @Test
    @DisplayName("deals double damage to an enchantment it beats")
    void damageAgainst() {
        Weapon sword = new Weapon("Sword", 12, Enchantment.FIRE);   // 17 damage

        assertEquals(34, sword.damageAgainst(Enchantment.FROST),
                "A FIRE sword (17) against FROST should deal 34: double, because FIRE beats FROST. Reuse beats().");
        assertEquals(17, sword.damageAgainst(Enchantment.STORM),
                "A FIRE sword (17) against STORM should deal 17: no double, because STORM beats FIRE, not the other way.");
        assertEquals(17, sword.damageAgainst(Enchantment.NONE), "Against NONE: normal damage, 17.");
    }
}
