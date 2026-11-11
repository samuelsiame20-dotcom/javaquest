package quest.levels.w3;

import java.util.List;
import java.util.Map;

/**
 * ROOM 13 - THE MERCHANT'S LEDGER
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>Each method is a "pick the right collection" puzzle: List, Set or Map, and which kind.
 */
public class Level13_MerchantsLedger {

    /** Given: one sale at the market. */
    public record Sale(String merchant, String item, int price) {
    }

    /**
     * A coin from a realm, minted in a year. A plain class, NOT a record, because records write
     * equals() and hashCode() for you, and here that's your job.
     */
    public static final class Coin {

        // TODO: add private final fields.

        public Coin(String realm, int year) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public String realm() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public int year() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        // TODO: override equals(Object) and hashCode(). Two coins are equal when realm AND year match.
    }

    /** How many DIFFERENT coins are in the purse (same realm and year = the same coin). */
    public static int distinctCoins(List<Coin> purse) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /** Every item sold, each listed once, in the order it was FIRST sold. */
    public static List<String> uniqueItems(List<Sale> sales) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /** Total takings per merchant, with merchants in alphabetical order. */
    public static Map<String, Integer> totalsByMerchant(List<Sale> sales) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Items sold for at least {@code minPrice}, each listed once, in the order first sold.
     * Write it as ONE stream pipeline: filter, map, distinct, toList.
     */
    public static List<String> pricyItems(List<Sale> sales, int minPrice) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * How many sales each merchant made. Write it as a stream with
     * {@code Collectors.groupingBy(..., Collectors.counting())}.
     */
    public static Map<String, Long> salesPerMerchant(List<Sale> sales) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
