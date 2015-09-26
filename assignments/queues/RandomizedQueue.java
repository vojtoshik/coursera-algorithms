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
 */
public class RandomizedQueue<T> {

    private int size;

    private Entry<T> randomCursor;

    private final static int STEP_LOWER_BOUND = 1;
    private final static int STEP_UPPER_BOUND = 10;

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(T item) {

        verifyEntryNotNull(item);

        Entry<T> newItem = new Entry(item);

        if (isEmpty()) {
            newItem.next = newItem;
            newItem.previous = newItem;

            randomCursor = newItem;
        } else {
            randomCursor = getRandomEntry(randomCursor);

            Entry<T> nextItem = randomCursor.next;
            randomCursor.next = newItem;

            newItem.previous = randomCursor;
            newItem.next = nextItem;

            nextItem.previous = newItem;
        }

        size++;
    }

    public T dequeue() {
        verifyQueueIsNotEmpty();

        Entry<T> itemToReturn = getRandomEntry(randomCursor);

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

    public T sample() {

        verifyQueueIsNotEmpty();

        randomCursor = getRandomEntry(randomCursor);
        return randomCursor.content;
    }

    public Iterator<T> iterator() {
        return new RandomizedIterator();
    }

    private Entry<T> getRandomEntry(Entry<T> cursor) {
        int steps = StdRandom.uniform(STEP_LOWER_BOUND, STEP_UPPER_BOUND);

        for (int i = 0; i < steps; i++) {
            cursor = cursor.next;
        }

        return cursor;
    }

    private void verifyEntryNotNull(T item) {
        if (item == null) {
            throw new NullPointerException("Nulls are not allowed");
        }
    }

    private void verifyQueueIsNotEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
    }

    private class Entry<T> {
        private Entry<T> previous;
        private Entry<T> next;

        private T content;

        public Entry(T content) {
            this.content = content;
        }
    }

    private class RandomizedIterator implements Iterator<T> {

        private RandomizedQueue<T> internalQueue;

        public RandomizedIterator() {
            internalQueue = new RandomizedQueue<>();
            Entry<T> currentCursor = randomCursor;

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
        public T next() {
            return internalQueue.dequeue();
        }
    }
}
