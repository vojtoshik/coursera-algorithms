import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports adding and
 * removing items from either the front or the back of the data structure.
 *
 * <i>Corner cases</i>
 * Throw a {@link java.lang.NullPointerException} if the client attempts to add a null item.
 * Throw a {@link java.util.NoSuchElementException} if the client attempts to remove an item from an empty deque.
 * Throw a {@link java.lang.UnsupportedOperationException} if the client calls the remove() method in the iterator.
 * Throw a {@link java.util.NoSuchElementException} if the client calls the next() method in the iterator and there are
 * no more items to return.
 *
 * <i>Performance requirements</i>
 * Your deque implementation must support each deque operation in constant worst-case time. A deque containing N items
 * must use at most 48N + 192 bytes of memory and use space proportional to the number of items currently in the deque.
 * Additionally, your iterator implementation must support each operation (including construction) in constant
 * worst-case time.
 */
public class Deque<T> implements Iterable<T> {

    private int size;

    private Entry head;
    private Entry tail;

    public Deque() {}

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T item) {
        validateItem(item);

        Entry newEntry = new Entry(null, head, item);

        if (head != null) {
            head.setPrevious(newEntry);
        }

        head = newEntry;

        // if it's the first element - both head and tail has to pointed to it
        if (tail == null) {
            tail = newEntry;
        }

        size++;
    }

    public void addLast(T item) {
        validateItem(item);

        Entry newEntry = new Entry(tail, null, item);

        if (tail != null) {
            tail.setNext(newEntry);
        }

        tail = newEntry;

        // if it's first element in Deque - point head also to it
        if (head == null) {
            head = tail;
        }

        size++;
    }

    public T removeFirst() {
        verifyContainerIsNotEmpty();

        Entry entryToRemove = head;
        head = head.getNext();

        // we do this to avoid memory leaks
        if (head != null) {
            head.setPrevious(null);
            entryToRemove.setNext(null);
        } else {
            // that was last element - let's remove tail also
            tail = null;
        }

        size--;

        return entryToRemove.getContent();
    }

    public T removeLast() {
        verifyContainerIsNotEmpty();

        Entry entryToRemove = tail;
        tail = tail.getPrevious();

        // no memory leaks, ok?
        if (tail != null) {
            tail.setNext(null);
            entryToRemove.setPrevious(null);
        } else {
            // it was last element, so head has to be cut off also
            head = null;
        }

        size--;

        return entryToRemove.getContent();
    }

    public Iterator<T> iterator() {
        return new DequeIterator(head);
    }

    private void verifyContainerIsNotEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("We ran out of items!");
        }
    }

    private void validateItem(T item) {
        if (item == null) {
            throw new NullPointerException("Nulls are not allowed!");
        }
    }

    private class Entry {

        private Entry previous;
        private Entry next;

        private T content;

        public Entry(Entry previous, Entry next, T content) {
            this.previous = previous;
            this.next = next;

            this.content = content;
        }

        public Entry getPrevious() {
            return previous;
        }

        public void setPrevious(Entry previous) {
            this.previous = previous;
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }

        public T getContent() {
            return content;
        }
    }

    /**
     * Terrible iterator. Modifications of the Deque will be visible (except removing last element from Deque - this
     * won't be visible by iterator).
     */
    private class DequeIterator implements Iterator<T> {

        private Entry head;

        public DequeIterator(Entry head) {
            this.head = head;
        }

        @Override
        public boolean hasNext() {
            return head != null;
        }

        @Override
        public T next() {
            if (head == null) {
                throw new NoSuchElementException("No more items to iterate!");
            }

            T entry = head.getContent();
            head = head.getNext();

            return entry;
        }
    }
}