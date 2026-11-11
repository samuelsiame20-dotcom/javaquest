package quest.levels.w3;

import java.util.List;
import java.util.Map;

/**
 * ROOM 15 - BOSS: THE SHOP SYSTEM
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>Everything from World 3 meets here: checked exceptions with a shared parent, a generic class
 * with a bounded type, choosing collections, and streams for the reports.
 */
public class Level15_ShopSystem {

    /** Given: anything a shop can sell. */
    public interface Product {

        String name();

        String category();

        int price();
    }

    /** Given. */
    public record Potion(String name, int price) implements Product {

        @Override
        public String category() {
            return "potion";
        }
    }

    /** Given. */
    public record Blade(String name, int price) implements Product {

        @Override
        public String category() {
            return "weapon";
        }
    }

    /** Given: one completed purchase. {@code total} is price x quantity. */
    public record Sale(String product, String category, int quantity, int total) {
    }

    /** Given: the parent of every shop problem. CHECKED (extends Exception), so callers must handle it. */
    public abstract static class ShopException extends Exception {

        protected ShopException(String message) {
            super(message);
        }
    }

    /** The message must mention the product name, how many were wanted, and how many are available. */
    public static class OutOfStockException extends ShopException {

        public OutOfStockException(String product, int requested, int available) {
            super("TODO"); // TODO: build the real message here (super(...) must stay the first line).
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** The message must mention the total price and the gold offered. */
    public static class NotEnoughGoldException extends ShopException {

        public NotEnoughGoldException(int price, int offered) {
            super("TODO"); // TODO: build the real message here.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /** A shop that sells one kind of product. A Shop&lt;Potion&gt; only stocks potions. */
    public static class Shop<P extends Product> {

        // TODO: add private fields. Think about which collections fit: look-up by name, keep order, ...

        public Shop() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /**
         * Adds stock. Stocking a product that's already on the shelf (same name) adds to its quantity.
         *
         * @throws IllegalArgumentException if quantity is not positive
         */
        public void stock(P product, int quantity) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** How many are left; 0 for a product never stocked. */
        public int quantityOf(String name) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /**
         * Buys {@code quantity} of a product and returns the change. Checks, in this order:
         * quantity is positive (IllegalArgumentException); there's enough stock (OutOfStockException;
         * an unknown product has 0 available); there's enough gold (NotEnoughGoldException).
         * If any check fails, NOTHING changes. On success the sale is recorded.
         */
        public int buy(String name, int quantity, int gold) throws ShopException {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Completed sales, oldest first. */
        public List<Sale> sales() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Every product ever stocked, in the order first stocked. A Shop&lt;Potion&gt; returns Potions. */
        public List<P> catalogue() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Total gold taken per category, categories A-Z. Use a stream over the sales. */
        public Map<String, Integer> revenueByCategory() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Names of products with fewer than {@code threshold} left (sold-out ones too), A-Z. */
        public List<String> lowStock(int threshold) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }
}
