package quest.levels.w3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w3.Level13_MerchantsLedger.Coin;
import quest.levels.w3.Level13_MerchantsLedger.Sale;

/** The lock for Room 13. */
@Timeout(2)
@DisplayName("Room 13 - The Merchant's Ledger")
class Level13_MerchantsLedgerTest {

    private static final List<Sale> MARKET = List.of(
            new Sale("Oswin", "lantern", 40),
            new Sale("Brana", "rope", 5),
            new Sale("Oswin", "map", 120),
            new Sale("Cato", "lantern", 45),
            new Sale("Brana", "map", 110),
            new Sale("Oswin", "rope", 6));

    @Test
    @DisplayName("coins with the same realm and year are equal, and hash the same")
    void coinEqualsAndHashCode() {
        Coin a = new Coin("Vel", 1203);
        Coin b = new Coin("Vel", 1203);

        assertEquals(a, b, "Two Vel 1203 coins should be equal. Without your own equals(), Java compares "
                + "identity (are they the SAME object?), not content.");
        assertEquals(a.hashCode(), b.hashCode(), "Equal coins must have equal hashCodes, or HashSet and HashMap "
                + "lose track of them. Objects.hash(realm, year) is the easy way.");
        assertNotEquals(a, new Coin("Vel", 1204), "Vel 1203 and Vel 1204 are different coins.");
        assertNotEquals(a, new Coin("Rhun", 1203), "Vel 1203 and Rhun 1203 are different coins.");
        assertFalse(a.equals(null), "A coin should not equal null: equals(null) must return false, not crash.");
        assertFalse(a.equals("Vel 1203"), "A coin should not equal a String: check getClass() or instanceof "
                + "before casting.");
        assertEquals(1, new HashSet<>(List.of(a, b)).size(), "A HashSet of two equal coins should hold 1 coin.");
    }

    @Test
    @DisplayName("Sets remove duplicates; a LinkedHashSet keeps first-seen order")
    void sets() {
        List<Coin> purse = List.of(new Coin("Vel", 1203), new Coin("Rhun", 1203), new Coin("Vel", 1203));
        assertEquals(2, Level13_MerchantsLedger.distinctCoins(purse),
                "distinctCoins should be 2: two of the three coins are the same Vel 1203. new HashSet<>(purse) "
                        + "does it, once equals and hashCode are right.");

        assertEquals(List.of("lantern", "rope", "map"), Level13_MerchantsLedger.uniqueItems(MARKET),
                "uniqueItems should be [lantern, rope, map]: each once, in first-sold order. A HashSet loses "
                        + "the order; a LinkedHashSet keeps it.");
    }

    @Test
    @DisplayName("a TreeMap totals takings with merchants in A-Z order")
    void totalsByMerchant() {
        Map<String, Integer> totals = Level13_MerchantsLedger.totalsByMerchant(MARKET);

        assertEquals(Map.of("Brana", 115, "Cato", 45, "Oswin", 166), totals,
                "Totals should be Brana 115, Cato 45, Oswin 166. map.merge(name, price, Integer::sum) adds up "
                        + "neatly, or use getOrDefault.");
        assertEquals(List.of("Brana", "Cato", "Oswin"), List.copyOf(totals.keySet()),
                "Merchants should come out in alphabetical order. Which Map keeps its keys sorted? (TreeMap)");
    }

    @Test
    @DisplayName("a stream picks out the pricy items")
    void pricyItems() {
        assertEquals(List.of("lantern", "map"), Level13_MerchantsLedger.pricyItems(MARKET, 40),
                "pricyItems(MARKET, 40) should be [lantern, map]: at least 40 includes 40 (>=), each item once "
                        + "(distinct). sales.stream().filter(...).map(Sale::item).distinct().toList()");
        assertEquals(List.of(), Level13_MerchantsLedger.pricyItems(MARKET, 500),
                "pricyItems(MARKET, 500) should be an empty list, not null.");
    }

    @Test
    @DisplayName("groupingBy counts sales per merchant")
    void salesPerMerchant() {
        assertEquals(Map.of("Oswin", 3L, "Brana", 2L, "Cato", 1L), Level13_MerchantsLedger.salesPerMerchant(MARKET),
                "salesPerMerchant should be Oswin 3, Brana 2, Cato 1. Use "
                        + "collect(Collectors.groupingBy(Sale::merchant, Collectors.counting())).");
        Set<String> none = Level13_MerchantsLedger.salesPerMerchant(List.of()).keySet();
        assertEquals(Set.of(), none, "No sales should give an empty map.");
    }
}
