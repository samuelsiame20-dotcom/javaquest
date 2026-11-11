package quest.levels.w3;

import java.util.List;

/**
 * ROOM 12 - BAG OF HOLDING
 *
 * <p>Run {@code gradlew.bat quest} to read the story and try the lock.
 * Replace each {@code throw new UnsupportedOperationException("TODO");} with your own code.
 *
 * <p>The generic signatures are given: they ARE the lesson, so read them carefully.
 */
public class Level12_BagOfHolding {

    /** Given: anything that fits in a bag. */
    public interface Item {

        String name();

        int weight();
    }

    /** Given: a potion is an Item. */
    public record Potion(String name, int weight, int healing) implements Item {
    }

    /** A gem is an Item that can be compared with other gems. */
    public record Gem(String name, int weight, int value) implements Item, Comparable<Gem> {

        /** Natural order: a MORE valuable gem is "greater" (positive result). */
        @Override
        public int compareTo(Gem other) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /**
     * A bag that holds one kind of item, up to a weight limit. {@code T extends Item} is a BOUNDED type:
     * only Items fit, so inside this class you can call {@code item.weight()}.
     */
    public static class Inventory<T extends Item> {

        // TODO: add private fields.

        public Inventory(int maxWeight) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** Adds the item if the total weight stays within the limit. Returns whether it was added. */
        public boolean add(T item) {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        public int totalWeight() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** The items in the order they were added. The bag must NOT be changeable through this list. */
        public List<T> items() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }

        /** The heaviest item (the first one on a tie), or null if the bag is empty. */
        public T heaviest() {
            // TODO: replace this line with your code.
            throw new UnsupportedOperationException("TODO");
        }
    }

    /**
     * PECS, "Producer Extends, Consumer Super": {@code from} PRODUCES T's (so {@code ? extends T}),
     * {@code to} CONSUMES them (so {@code ? super T}). Appends every item of {@code from} to {@code to}
     * and returns how many were copied.
     */
    public static <T> int copyAll(List<? extends T> from, List<? super T> to) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * MULTIPLE BOUNDS: T must be an Item AND comparable to itself. Returns the greatest item by its
     * natural order (compareTo); the first one on a tie; null if the list is empty.
     */
    public static <T extends Item & Comparable<T>> T best(List<T> items) {
        // TODO: replace this line with your code.
        throw new UnsupportedOperationException("TODO");
    }
}
