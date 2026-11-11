package quest.levels.w3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w3.Level12_BagOfHolding.Gem;
import quest.levels.w3.Level12_BagOfHolding.Inventory;
import quest.levels.w3.Level12_BagOfHolding.Item;
import quest.levels.w3.Level12_BagOfHolding.Potion;

/** The lock for Room 12. */
@Timeout(2)
@DisplayName("Room 12 - Bag of Holding")
class Level12_BagOfHoldingTest {

    @Test
    @DisplayName("holds items up to its weight limit")
    void weightLimit() {
        Inventory<Potion> bag = new Inventory<>(10);

        assertTrue(bag.add(new Potion("Minor", 4, 10)), "Adding a 4-weight potion to an empty 10-limit bag should work.");
        assertTrue(bag.add(new Potion("Major", 5, 30)), "Adding 5 more (total 9) should work.");
        assertFalse(bag.add(new Potion("Giant", 3, 60)), "Adding 3 more (total 12) should be refused: it's over the limit of 10.");
        assertEquals(9, bag.totalWeight(), "After a refused add, totalWeight() should still be 9: don't add it anyway.");
        assertTrue(bag.add(new Potion("Drop", 1, 1)), "Adding 1 more (total exactly 10) should work: \"within the limit\" includes 10.");
    }

    @Test
    @DisplayName("lists items in order without letting anyone change the bag")
    void itemsAreReadOnly() {
        Inventory<Potion> bag = new Inventory<>(10);
        Potion minor = new Potion("Minor", 4, 10);
        Potion major = new Potion("Major", 5, 30);
        bag.add(minor);
        bag.add(major);

        assertEquals(List.of(minor, major), bag.items(), "items() should list Minor then Major, in the order added.");
        try {
            bag.items().add(new Potion("Sneaky", 1, 0));
        } catch (UnsupportedOperationException expected) {
            // a read-only list refusing the change is fine
        }
        assertEquals(2, bag.items().size(), "Adding to the list from items() must not change the bag (it skipped "
                + "the weight check!). Return List.copyOf(...) or Collections.unmodifiableList(...), not your own list.");
    }

    @Test
    @DisplayName("finds the heaviest item, typed as the bag's own kind")
    void heaviest() {
        Inventory<Gem> pouch = new Inventory<>(100);
        assertNull(pouch.heaviest(), "An empty bag's heaviest() should be null.");
        Gem opal = new Gem("Opal", 6, 300);
        Gem ruby = new Gem("Ruby", 9, 500);
        Gem jade = new Gem("Jade", 9, 200);
        pouch.add(opal);
        pouch.add(ruby);
        pouch.add(jade);

        Gem heaviest = pouch.heaviest();   // no cast needed: the bag knows it holds Gems
        assertSame(ruby, heaviest, "heaviest() should be Ruby (weight 9). Jade is also 9 but comes later: "
                + "on a tie keep the first, so replace only on strictly heavier (>).");
    }

    @Test
    @DisplayName("copyAll moves potions into any list that can hold them (PECS)")
    void copyAll() {
        List<Potion> potions = List.of(new Potion("Minor", 4, 10), new Potion("Major", 5, 30));
        List<Item> satchel = new ArrayList<>(List.of(new Gem("Opal", 6, 300)));
        List<Object> chest = new ArrayList<>();

        assertEquals(2, Level12_BagOfHolding.copyAll(potions, satchel), "copyAll(potions, satchel) should return 2.");
        assertEquals(3, satchel.size(), "The satchel already held a gem: copyAll should ADD to it (1 + 2 = 3), not replace it.");
        assertEquals(2, Level12_BagOfHolding.copyAll(potions, chest), "copyAll(potions, chest) should return 2: "
                + "a List<Object> can consume Potions too. That's why 'to' is List<? super T>.");
        assertEquals(potions, chest, "The chest should hold the potions in the same order.");
    }

    @Test
    @DisplayName("best() uses compareTo to find the most valuable gem")
    void bestGem() {
        Gem opal = new Gem("Opal", 6, 300);
        Gem ruby = new Gem("Ruby", 9, 500);
        Gem garnet = new Gem("Garnet", 2, 500);

        assertTrue(ruby.compareTo(opal) > 0, "ruby.compareTo(opal) should be positive: Ruby (500) is worth more "
                + "than Opal (300). Integer.compare(value, other.value) does this safely.");
        assertTrue(opal.compareTo(ruby) < 0, "opal.compareTo(ruby) should be negative.");
        assertEquals(0, ruby.compareTo(garnet), "ruby.compareTo(garnet) should be 0: same value (500).");
        assertSame(ruby, Level12_BagOfHolding.best(List.of(opal, ruby, garnet)),
                "best(opal, ruby, garnet) should be Ruby: Garnet ties at 500 but comes later. Use compareTo, not "
                        + "the value field: best() must work for ANY Comparable item.");
        assertNull(Level12_BagOfHolding.best(new ArrayList<Gem>()), "best() of an empty list should be null.");
    }
}
