package quest.levels.w3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import quest.levels.w3.Level15_ShopSystem.Blade;
import quest.levels.w3.Level15_ShopSystem.NotEnoughGoldException;
import quest.levels.w3.Level15_ShopSystem.OutOfStockException;
import quest.levels.w3.Level15_ShopSystem.Potion;
import quest.levels.w3.Level15_ShopSystem.Product;
import quest.levels.w3.Level15_ShopSystem.Sale;
import quest.levels.w3.Level15_ShopSystem.Shop;
import quest.levels.w3.Level15_ShopSystem.ShopException;

/** The lock for Room 15 (World 3 boss). */
@Timeout(2)
@DisplayName("Room 15 - BOSS: The Shop System")
class Level15_ShopSystemTest {

    private static Shop<Product> openShop() {
        Shop<Product> shop = new Shop<>();
        shop.stock(new Potion("Elixir", 30), 3);
        shop.stock(new Blade("Dagger", 50), 2);
        return shop;
    }

    @Test
    @DisplayName("sells stock, gives change and records the sale")
    void happyPath() throws ShopException {
        Shop<Product> shop = openShop();

        assertEquals(40, shop.buy("Elixir", 2, 100), "Buying 2 Elixirs (30 each) with 100 gold should give 40 change.");
        assertEquals(1, shop.quantityOf("Elixir"), "After selling 2 of 3 Elixirs, 1 should be left.");
        assertEquals(List.of(new Sale("Elixir", "potion", 2, 60)), shop.sales(),
                "The sale should be recorded as Sale(Elixir, potion, 2, 60).");
        assertEquals(0, shop.quantityOf("Moonstone"), "quantityOf a product never stocked should be 0, not null or a crash.");
    }

    @Test
    @DisplayName("refuses to sell more than it has, and changes nothing")
    void outOfStock() {
        Shop<Product> shop = openShop();

        OutOfStockException e = assertThrows(OutOfStockException.class, () -> shop.buy("Dagger", 7, 1000),
                "Buying 7 Daggers when 2 are left should throw OutOfStockException.");
        String message = e.getMessage();
        assertTrue(message.contains("Dagger") && message.contains("7") && message.contains("2"),
                "The OutOfStockException message should mention Dagger, 7 wanted and 2 available, but was \""
                        + message + "\". Build it in the super(...) call.");
        assertEquals(2, shop.quantityOf("Dagger"), "A failed purchase must change nothing: still 2 Daggers. "
                + "Do ALL the checks before you change anything.");
        assertEquals(List.of(), shop.sales(), "A failed purchase must not record a sale.");
        assertThrows(OutOfStockException.class, () -> shop.buy("Moonstone", 1, 1000),
                "Buying a product the shop never stocked should throw OutOfStockException (0 available).");
    }

    @Test
    @DisplayName("refuses a buyer who can't pay, and rejects nonsense quantities")
    void notEnoughGoldAndValidation() {
        Shop<Product> shop = openShop();

        ShopException e = assertThrows(NotEnoughGoldException.class, () -> shop.buy("Dagger", 2, 60),
                "2 Daggers cost 100; offering 60 should throw NotEnoughGoldException.");
        assertTrue(e.getMessage().contains("100") && e.getMessage().contains("60"),
                "The NotEnoughGoldException message should mention the price 100 and the 60 offered, but was \""
                        + e.getMessage() + "\".");
        assertEquals(2, shop.quantityOf("Dagger"), "Not enough gold: the Daggers must stay on the shelf.");
        assertThrows(IllegalArgumentException.class, () -> shop.buy("Dagger", 0, 100),
                "buy(..., 0, ...) should throw IllegalArgumentException: you can't buy zero of something.");
        assertThrows(IllegalArgumentException.class, () -> shop.stock(new Potion("Tonic", 5), -1),
                "stock(..., -1) should throw IllegalArgumentException.");
    }

    @Test
    @DisplayName("reports revenue per category with a stream")
    void revenueByCategory() throws ShopException {
        Shop<Product> shop = openShop();
        shop.buy("Elixir", 1, 30);
        shop.buy("Dagger", 2, 100);
        shop.buy("Elixir", 2, 60);

        Map<String, Integer> revenue = shop.revenueByCategory();
        assertEquals(Map.of("potion", 90, "weapon", 100), revenue,
                "Revenue should be potion 90, weapon 100. Try collect(Collectors.groupingBy(Sale::category, "
                        + "TreeMap::new, Collectors.summingInt(Sale::total))).");
        assertEquals(List.of("potion", "weapon"), List.copyOf(revenue.keySet()), "Categories should be in A-Z order.");
    }

    @Test
    @DisplayName("a Shop<Potion> keeps its type, restocks and reports low stock")
    void genericShopAndLowStock() throws ShopException {
        Shop<Potion> apothecary = new Shop<>();
        apothecary.stock(new Potion("Tonic", 5), 4);
        apothecary.stock(new Potion("Antidote", 12), 1);
        apothecary.stock(new Potion("Tonic", 5), 2);
        apothecary.buy("Antidote", 1, 12);

        assertEquals(6, apothecary.quantityOf("Tonic"), "Stocking Tonic twice (4 + 2) should give 6, not 2.");
        List<Potion> shelf = apothecary.catalogue();   // a Shop<Potion> hands back Potions, no casts
        assertEquals(List.of("Tonic", "Antidote"), shelf.stream().map(Potion::name).toList(),
                "catalogue() should list Tonic, Antidote: each product once, in first-stocked order.");
        assertEquals(List.of("Antidote", "Tonic"), apothecary.lowStock(10),
                "lowStock(10) should be [Antidote, Tonic] in A-Z order: Antidote is sold out (0) and still counts.");
        assertEquals(List.of("Antidote"), apothecary.lowStock(1), "lowStock(1) should be [Antidote]: fewer than 1 means 0.");
    }
}
