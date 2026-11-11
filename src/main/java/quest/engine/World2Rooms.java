package quest.engine;

import static quest.engine.LevelRegistry.room;

import java.util.List;

/** World 2 - The Guild Hall (OOP). ICT261 Unit 1: OOP revision. */
final class World2Rooms {

    private static final String WORLD_2 = "World 2 - The Guild Hall";

    private World2Rooms() {
    }

    static List<Level> rooms() {
        return List.of(
                room(6, WORLD_2, "w2", "Level06_ForgeHero", "Forge a Hero",
                        """
                        Beyond the Vault lies the Guild Hall, where adventurers are not born but built.
                        The Forgemaster's anvil is cold: its blueprint for a Hero was wiped. Without it,
                        heroes come out nameless, with negative health, and nobody knows how many have
                        been made. Write the Hero class properly: guarded fields, two ways to forge one,
                        and a tally on the wall that every hero shares.
                        """,
                        "Write the Hero class: private fields, two constructors, getters, takeDamage, heal, "
                                + "and the static heroesForged() counter.",
                        List.of(
                                "Each hero needs its own name, max HP and current HP: instance fields. The tally "
                                        + "of all heroes belongs to the CLASS, not to any one hero: one static field.",
                                "Validate in the two-argument constructor and throw IllegalArgumentException for "
                                        + "bad input. The one-argument constructor should just call this(name, 100), "
                                        + "so the rules and the counting live in one place. Clamp with Math.max "
                                        + "and Math.min.",
                                """
                                private static int forged;   private final String name;
                                private final int maxHp;     private int hp;
                                Hero(name, maxHp): validate; set fields; hp = maxHp; forged++
                                Hero(name): this(name, 100)
                                takeDamage: amount < 0 -> throw; hp = max(0, hp - amount)
                                heal: if isAlive(): hp = min(maxHp, hp + amount)
                                """),
                        "ICT261 Unit 1 (OOP revision): classes and objects, constructors, overloading, "
                                + "encapsulation, static vs instance members, this(...).",
                        100),

                room(7, WORLD_2, "w2", "Level07_Bestiary", "Monster Bestiary",
                        """
                        The Guild's Bestiary is a living book: every creature that has ever crawled out of
                        the dungeon is catalogued inside. Its pages have come loose. Goblins hit like
                        slimes, trolls feel every blow, and dragons have forgotten they breathe fire.
                        Rebind the book so each creature is a Monster first, and then itself.
                        """,
                        "Finish Monster, then make Goblin, Troll and Dragon call super(...) and override "
                                + "what makes them different. totalAttack adds up a whole pack.",
                        List.of(
                                "A subclass gets all of Monster's code for free. Only write what's DIFFERENT, and "
                                        + "put @Override above each method you replace.",
                                "super(name, hp) must be the first line of a subclass constructor. Inside an "
                                        + "override, super.method() runs the parent's version, so a troll can "
                                        + "call super.takeHit(damage / 2). Overriding needs the exact same "
                                        + "parameter types; anything else is overloading, and calls through a "
                                        + "Monster variable will ignore it.",
                                """
                                Goblin(name): super(name, 30);   @Override attackDamage() -> 7
                                Troll():      super("Troll", 80); @Override takeHit(int d) -> super.takeHit(d / 2)
                                Dragon(name): super(name, 200); attackDamage -> 25
                                              describe -> super.describe() + " - breathes fire"
                                totalAttack:  sum of m.attackDamage() for each m in pack
                                """),
                        "ICT261 Unit 1 (OOP revision): inheritance, super(), method overriding, @Override, "
                                + "polymorphism in a loop, and overloading by mistake.",
                        100),

                room(8, WORLD_2, "w2", "Level08_Spellbook", "Spellbook",
                        """
                        The Spellbook hums on its lectern, but its spells have lost their words. Some spells
                        share a common binding (an abstract page they all build on); others are wild sparks
                        that only follow the book's contract. A few spells even belong to two schools at
                        once. Restore the contract, the binding and the spells, and the book will cast again.
                        """,
                        "Write Spell's default and static methods, BaseSpell, the three spells (Blizzard must "
                                + "also implement Channelled) and totalPower.",
                        List.of(
                                "An interface says WHAT a spell can do; an abstract class shares HOW, with fields "
                                        + "and code. A class extends one class but can implement many interfaces.",
                                "A default method has a body inside the interface and can call the interface's "
                                        + "own abstract methods (name(), power()). A static interface method is "
                                        + "called on the interface: Spell.bestAffordable(...). Add a second "
                                        + "interface with: class Blizzard extends BaseSpell implements Channelled.",
                                """
                                describe:       return name() + " (cost " + manaCost() + ", power " + power() + ")"
                                bestAffordable: best = null; for each s: if s.manaCost() <= mana
                                                    and (best == null or s.power() > best.power()): best = s
                                Blizzard(t):    super("Blizzard", 8 * t); keep t in a field; power = 12 * t
                                """),
                        "ICT261 Unit 1 (OOP revision): abstract classes vs interfaces, multiple interfaces, "
                                + "interface default and static methods, polymorphism.",
                        100),

                room(9, WORLD_2, "w2", "Level09_EnchantersForge", "The Enchanter's Forge",
                        """
                        The Enchanter refuses to make a FireSword class, a FrostSword class, a StormAxe
                        class, and so on forever. "A weapon HAS an enchantment," she snaps, "it doesn't
                        inherit one." Her forge binds runes to plain steel and can swap them at will. Learn
                        her craft: an enum that knows its own power, and a weapon built from parts.
                        """,
                        "Finish the Enchantment enum (bonus, adjective, beats) and the Weapon class, which "
                                + "holds an Enchantment in a field.",
                        List.of(
                                "Each enum constant already stores its own bonus and adjective: the getters just "
                                        + "return the fields. Weapon keeps an Enchantment field and ASKS it for "
                                        + "the bonus instead of hard-coding numbers.",
                                "A switch on 'this' inside the enum makes beats() neat: case FIRE -> other == FROST. "
                                        + "Composition means Weapon delegates: damage() is base + "
                                        + "enchantment.bonus(), and reforge() just replaces the field.",
                                """
                                beats:   switch (this) { FIRE -> other == FROST; FROST -> other == STORM;
                                                         STORM -> other == FIRE; NONE -> false }
                                Weapon(name, base): this(name, base, Enchantment.NONE)
                                damageAgainst(e): enchantment.beats(e) ? damage() * 2 : damage()
                                describe: enchantment.adjective() + " " + name
                                """),
                        "ICT261 Unit 1 (OOP revision): composition over inheritance (\"has-a\" vs \"is-a\"); "
                                + "enums with fields, constructors and methods.",
                        100),

                room(10, WORLD_2, "w2", "Level10_BattleSimulator", "BOSS: The Battle Simulator",
                        """
                        The Guild's final exam is the Battle Simulator: a sandpit where knights, mages and
                        berserkers fight to see which design holds up. Its arbiter only knows one thing
                        about any combatant: that it is a Fighter. Build the fighters, teach the interface
                        its defaults, and write the battle loop. The arbiter is watching for stalemates.
                        """,
                        "Fighter's default methods, Knight, Mage, Berserker and battle(). Rules are in the file.",
                        List.of(
                                "Every fighter shares the same shape, so write isAlive() and attack() once, as "
                                        + "defaults in Fighter. Then each class only adds what's special about it.",
                                "Knight HAS a Blade (composition). The shield: damage - 3, but never below 0. The "
                                        + "Mage overrides attack() to spend mana as it strikes. The Berserker "
                                        + "needs max HP in a field to work out HP lost. battle() talks only to "
                                        + "the Fighter interface, so it works for any pair.",
                                """
                                attack (default): target.takeHit(attackPower())
                                Knight.takeHit:   hp = max(0, hp - max(0, d - 3))
                                Mage.attack:      p = attackPower(); if p is a bolt: mana -= 10; target.takeHit(p)
                                battle: repeat 100 times: first.attack(second); if !second.isAlive() return first
                                                          second.attack(first); if !first.isAlive() return second
                                        return null
                                """),
                        "Everything from ICT261 Unit 1: interfaces with default methods, overriding, "
                                + "composition, encapsulation, and polymorphism through an interface type.",
                        250));
    }
}
