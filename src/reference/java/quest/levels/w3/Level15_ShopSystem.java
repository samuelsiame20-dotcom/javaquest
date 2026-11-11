package quest.levels.w3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** Reference solution for Room 15. Exists only to prove the lock can be opened. */
public class Level15_ShopSystem {

    public interface Product {

        String name();

        String category();

        int price();
    }

    public record Potion(String name, int price) implements Product {

        @Override
        public String category() {
            return "potion";
        }
    }

    public record Blade(String name, int price) implements Product {

        @Override
        public String category() {
            return "weapon";
        }
    }

    public record Sale(String product, String category, int quantity, int total) {
    }

    public abstract static class ShopException extends Exception {

        protected ShopException(String message) {
            super(message);
        }
    }

    public static class OutOfStockException extends ShopException {

        public OutOfStockException(String product, int requested, int available) {
            super("Not enough " + product + ": wanted " + requested + ", only " + available + " available");
        }
    }

    public static class NotEnoughGoldException extends ShopException {

        public NotEnoughGoldException(int price, int offered) {
            super("That costs " + price + " gold, but only " + offered + " was offered");
        }
    }

    public static class Shop<P extends Product> {

        private final Map<String, P> products = new LinkedHashMap<>();
        private final Map<String, Integer> quantities = new LinkedHashMap<>();
        private final List<Sale> sales = new ArrayList<>();

        public Shop() {
        }

        public void stock(P product, int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive: " + quantity);
            }
            products.putIfAbsent(product.name(), product);
            quantities.merge(product.name(), quantity, Integer::sum);
        }

        public int quantityOf(String name) {
            return quantities.getOrDefault(name, 0);
        }

        public int buy(String name, int quantity, int gold) throws ShopException {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive: " + quantity);
            }
            int available = quantityOf(name);
            if (available < quantity) {
                throw new OutOfStockException(name, quantity, available);
            }
            P product = products.get(name);
            int total = product.price() * quantity;
            if (gold < total) {
                throw new NotEnoughGoldException(total, gold);
            }
            quantities.put(name, available - quantity);
            sales.add(new Sale(name, product.category(), quantity, total));
            return gold - total;
        }

        public List<Sale> sales() {
            return List.copyOf(sales);
        }

        public List<P> catalogue() {
            return List.copyOf(products.values());
        }

        public Map<String, Integer> revenueByCategory() {
            return sales.stream().collect(
                    Collectors.groupingBy(Sale::category, TreeMap::new, Collectors.summingInt(Sale::total)));
        }

        public List<String> lowStock(int threshold) {
            return quantities.entrySet().stream()
                    .filter(entry -> entry.getValue() < threshold)
                    .map(Map.Entry::getKey)
                    .sorted()
                    .toList();
        }
    }
}
