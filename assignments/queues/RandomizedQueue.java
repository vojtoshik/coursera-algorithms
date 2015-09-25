import java.util.Iterator;

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

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(T item) {
        return null;
    }

    public T dequeue() {
        return null;
    }

    public T sample() {
        return null;
    }

    public Iterator<T> iterator() {
        return null;
    }

    private class Entry<T> {
        private Entry<T> previous;
        private Entry<T> next;

        private T content;

        public Entry(Entry previous, Entry next, T content) {
            this.previous = previous;
            this.next = next;
            this.content = content;
        }
    }
}
