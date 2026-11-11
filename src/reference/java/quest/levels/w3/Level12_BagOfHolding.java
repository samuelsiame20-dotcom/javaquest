package quest.levels.w3;

import java.util.ArrayList;
import java.util.List;

/** Reference solution for Room 12. Exists only to prove the lock can be opened. */
public class Level12_BagOfHolding {

    public interface Item {

        String name();

        int weight();
    }

    public record Potion(String name, int weight, int healing) implements Item {
    }

    public record Gem(String name, int weight, int value) implements Item, Comparable<Gem> {

        @Override
        public int compareTo(Gem other) {
            return Integer.compare(value, other.value);
        }
    }

    public static class Inventory<T extends Item> {

        private final int maxWeight;
        private final List<T> items = new ArrayList<>();
        private int totalWeight;

        public Inventory(int maxWeight) {
            this.maxWeight = maxWeight;
        }

        public boolean add(T item) {
            if (totalWeight + item.weight() > maxWeight) {
                return false;
            }
            items.add(item);
            totalWeight += item.weight();
            return true;
        }

        public int totalWeight() {
            return totalWeight;
        }

        public List<T> items() {
            return List.copyOf(items);
        }

        public T heaviest() {
            T heaviest = null;
            for (T item : items) {
                if (heaviest == null || item.weight() > heaviest.weight()) {
                    heaviest = item;
                }
            }
            return heaviest;
        }
    }

    public static <T> int copyAll(List<? extends T> from, List<? super T> to) {
        for (T item : from) {
            to.add(item);
        }
        return from.size();
    }

    public static <T extends Item & Comparable<T>> T best(List<T> items) {
        T best = null;
        for (T item : items) {
            if (best == null || item.compareTo(best) > 0) {
                best = item;
            }
        }
        return best;
    }
}
