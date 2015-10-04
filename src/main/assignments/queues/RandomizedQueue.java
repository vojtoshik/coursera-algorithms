import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from
 * items in the data structure. Create a generic data type RandomizedQueue that implements the following API.
 *
 * <i>Corner cases</i>
 * The order of two or more iterators to the same randomized queue must be mutually independent; each iterator must
 * maintain its own random order.
 * Throw a {@link java.lang.NullPointerException} if the client attempts to add a null item.
 * Throw a {@link java.util.NoSuchElementException} if the client attempts to sample or dequeue an item from an empty
 * randomized queue.
 * Throw a {@link java.lang.UnsupportedOperationException} if the client calls the remove() method in the iterator.
 * Throw a {@link java.util.NoSuchElementException} if the client calls the next() method in the iterator and there are no
 * more items to return.
 *
 * <i>Performance requirements</i>
 * Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in
 * constant amortized time. That is, any sequence of M randomized queue operations (starting from an empty queue) should
 * take at most cM steps in the worst case, for some constant c. A randomized queue containing N items must use at most
 * 48N + 192 bytes of memory. Additionally, your iterator implementation must support operations next() and hasNext()
 * in constant worst-case time; and construction in linear time; you may (and will need to) use a linear amount of extra
 * memory per iterator.
 *
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int STEP_LOWER_BOUND = 1;
    private static final int STEP_UPPER_BOUND = 10;

    private int size;
    private Entry<Item> randomCursor;

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {

        verifyEntryNotNull(item);

        Entry<Item> newItem = new Entry<>(item);

        if (isEmpty()) {
            newItem.next = newItem;
            newItem.previous = newItem;

            randomCursor = newItem;
        } else {
            randomCursor = getRandomEntry(randomCursor);

            Entry<Item> nextItem = randomCursor.next;
            randomCursor.next = newItem;

            newItem.previous = randomCursor;
            newItem.next = nextItem;

            nextItem.previous = newItem;
        }

        size++;
    }

    public Item dequeue() {
        verifyQueueIsNotEmpty();

        Entry<Item> itemToReturn = getRandomEntry(randomCursor);

        if (size == 1) {
            randomCursor = null;
        } else {
            randomCursor = itemToReturn.previous;
            randomCursor.next = itemToReturn.next;
            itemToReturn.next.previous = randomCursor;
        }

        size--;
        return itemToReturn.content;
    }

    public Item sample() {

        verifyQueueIsNotEmpty();

        randomCursor = getRandomEntry(randomCursor);
        return randomCursor.content;
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private Entry<Item> getRandomEntry(Entry<Item> startPoint) {
        int steps = StdRandom.uniform(STEP_LOWER_BOUND, STEP_UPPER_BOUND);

        Entry<Item> cursor = startPoint;

        for (int i = 0; i < steps; i++) {
            cursor = cursor.next;
        }

        return cursor;
    }

    private void verifyEntryNotNull(Item item) {
        if (item == null) {
            throw new NullPointerException("Nulls are not allowed");
        }
    }

    private void verifyQueueIsNotEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
    }

    private class Entry<Item> {
        private Entry<Item> previous;
        private Entry<Item> next;

        private Item content;

        public Entry(Item content) {
            this.content = content;
        }
    }

    private class RandomizedIterator implements Iterator<Item> {

        private RandomizedQueue<Item> internalQueue;

        public RandomizedIterator() {
            internalQueue = new RandomizedQueue<>();
            Entry<Item> currentCursor = randomCursor;

            for (int i = 0; i < size; i++) {
                internalQueue.enqueue(currentCursor.content);
                currentCursor = currentCursor.next;
            }
        }

        @Override
        public boolean hasNext() {
            return !internalQueue.isEmpty();
        }

        @Override
        public Item next() {
            return internalQueue.dequeue();
        }
    }
}
