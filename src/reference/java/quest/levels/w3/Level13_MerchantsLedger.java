package quest.levels.w3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** Reference solution for Room 13. Exists only to prove the lock can be opened. */
public class Level13_MerchantsLedger {

    public record Sale(String merchant, String item, int price) {
    }

    public static final class Coin {

        private final String realm;
        private final int year;

        public Coin(String realm, int year) {
            this.realm = realm;
            this.year = year;
        }

        public String realm() {
            return realm;
        }

        public int year() {
            return year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coin other = (Coin) o;
            return year == other.year && realm.equals(other.realm);
        }

        @Override
        public int hashCode() {
            return Objects.hash(realm, year);
        }
    }

    public static int distinctCoins(List<Coin> purse) {
        return new HashSet<>(purse).size();
    }

    public static List<String> uniqueItems(List<Sale> sales) {
        LinkedHashSet<String> items = new LinkedHashSet<>();
        for (Sale sale : sales) {
            items.add(sale.item());
        }
        return new ArrayList<>(items);
    }

    public static Map<String, Integer> totalsByMerchant(List<Sale> sales) {
        Map<String, Integer> totals = new TreeMap<>();
        for (Sale sale : sales) {
            totals.merge(sale.merchant(), sale.price(), Integer::sum);
        }
        return totals;
    }

    public static List<String> pricyItems(List<Sale> sales, int minPrice) {
        return sales.stream()
                .filter(sale -> sale.price() >= minPrice)
                .map(Sale::item)
                .distinct()
                .toList();
    }

    public static Map<String, Long> salesPerMerchant(List<Sale> sales) {
        return sales.stream().collect(Collectors.groupingBy(Sale::merchant, Collectors.counting()));
    }
}
