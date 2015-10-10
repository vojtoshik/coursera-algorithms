import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Remarkably, it is possible to solve the problem much faster than the brute-force solution. Given a point p, the
 * following method determines whether p participates in a set of 4 or more collinear points.
 * <ul>
 *     <li>Think of p as the origin.</li>
 *     <li>For each other point q, determine the slope it makes with p.</li>
 *     <li>Sort the points according to the slopes they makes with p.</li>
 *     <li>Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so,
 *     these points, together with p, are collinear.</li>
 * </ul>
 *
 * Applying this method for each of the N points in turn yields an efficient algorithm to the problem. The algorithm
 * solves the problem because points that have equal slopes with respect to p are collinear, and sorting brings such
 * points together. The algorithm is fast because the bottleneck operation is sorting.
 *
 * The method segments() should include each maximal line segment containing 4 (or more) points exactly once. For
 * example, if 5 points appear on a line segment in the order p→q→r→s→t, then do not include the subsegments p→s or q→t.
 *
 * <i>Corner cases</i>
 * Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point
 * in the array is null. Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a
 * repeated point.
 *
 * <i>Performance requirement</i>
 * The order of growth of the running time of your program should be N2 log N in the worst case and it should use space
 * proportional to N plus the number of line segments returned. FastCollinearPoints should work properly even if the
 * input has 5 or more collinear points.
 *
 * <i>A fly in the ointment</i>
 * {@link FastCollinearPoints} and {@link BruteCollinearPoints} have quite a lot of common code, that I would love to
 * move into separate classes, but it's not allowed because of restrictions specified in problem description (it's not
 * allowed to have any other classes except BruteCollinearPoints, FastCollinearPoints and Point and it's prohibited to
 * change API of those classes)
 *
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class FastCollinearPoints {

    private List<LineSegment> segmentsList = new List<>();

    public FastCollinearPoints(Point[] inputPoints) {
        validateInputData(inputPoints);

        // we do this to avoid modifications to original array (for example, by sorting)
        Point[] points = copy(inputPoints);

        sort(points, (o1, o2) -> o1.compareTo(o2));
        checkForDuplicates(points);

        if (points.length < 4) {
            return;
        }

        searchSegments(points);
    }

    public int numberOfSegments() {
        return segmentsList.getItemsCount();
    }

    public LineSegment[] segments() {

        LineSegment[] result = new LineSegment[segmentsList.getItemsCount()];
        int index = 0;

        for (LineSegment l : segmentsList) {
            result[index++] = l;
        }

        return result;
    }

    private void searchSegments(Point[] originalPoints) {
        for (int i = 0; i < originalPoints.length; i++) {
            Point currentRelativePoint = originalPoints[i];
            Point[] points = copy(originalPoints);

            sort(points, currentRelativePoint.slopeOrder());

            int indexOfCurrentSlope = 0;
            double currentSlope = currentRelativePoint.slopeTo(points[indexOfCurrentSlope]);

            for (int j = indexOfCurrentSlope + 1; j < points.length; j++) {
                if (currentRelativePoint.slopeTo(points[j]) != currentSlope) {
                    checkSegment(currentRelativePoint, points, indexOfCurrentSlope, j - 1);

                    indexOfCurrentSlope = j;
                    currentSlope = currentRelativePoint.slopeTo(points[j]);
                }
            }

            checkSegment(currentRelativePoint, points, indexOfCurrentSlope, points.length - 1);
        }
    }

    private void checkSegment(Point centralPoint, Point[] points, int startPoint, int endPoint) {
        if (endPoint - startPoint + 1 < 3) {
            return;
        }

        Point lastPoint = centralPoint;

        for (int i = startPoint; i <= endPoint; i++) {
            if (points[i].compareTo(centralPoint) < 0) {
                return;
            }

            if (points[i].compareTo(lastPoint) > 0) {
                lastPoint = points[i];
            }
        }

        segmentsList.add(new LineSegment(centralPoint, lastPoint));
    }

    private Point[] copy(Point[] inputPoints) {

        Point[] pointsCopy = new Point[inputPoints.length];
        for (int i = 0; i < inputPoints.length; i++) {
            pointsCopy[i] = inputPoints[i];
        }

        return pointsCopy;
    }

    private void checkForDuplicates(Point[] points) {

        for (int i = 1; i < points.length; i++) {

            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException("Duplicates are not allowed");
            }
        }
    }

    private void throwExceptionIfNull(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    private void validateInputData(Point[] points) {
        throwExceptionIfNull(points);

        for (int i = 0; i < points.length; i++) {
            throwExceptionIfNull(points[i]);
        }
    }

    private <T> void sort(T[] array, Comparator<T> comparator) {
        StdRandom.shuffle(array);
        sort(array, comparator, 0, array.length - 1);
    }

    private <T> void sort(T[] array, Comparator<T> comparator, int leftBound, int rightBound) {
        if (leftBound >= rightBound) {
            return;
        }

        int leftIndex = leftBound - 1;
        int rightIndex = rightBound + 1;

        T mediumValue = array[leftBound + (rightBound - leftBound) / 2];

        while (leftIndex < rightIndex) {

            do {
                leftIndex++;
            } while (comparator.compare(array[leftIndex], mediumValue) < 0);

            do {
                rightIndex--;
            } while (comparator.compare(array[rightIndex], mediumValue) > 0);

            if (leftIndex < rightIndex) {
                swap(array, leftIndex, rightIndex);
            }
        }

        sort(array, comparator, leftBound, rightIndex);
        sort(array, comparator, rightIndex + 1, rightBound);
    }

    private void swap(Object[] array, int index1, int index2) {
        Object tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }

    /**
     * I have to put all auxiliary classes here because of restriction mentioned in header. To avoid even bigger nesting
     * I put them all as children to {@link FastCollinearPoints}
     *
     * @param <T>
     */
    private class ListEntry<T> {

        private T payload;
        private ListEntry<T> next;
    }

    private class List<T> implements Iterable<T> {

        private int itemsCount = 0;

        private ListEntry<T> head;
        private ListEntry<T> tail;

        public void add(T item) {
            ListEntry<T> newEntry = new ListEntry<>();
            newEntry.payload = item;

            if (head == null) {
                head = newEntry;
                tail = newEntry;
            } else {
                tail.next = newEntry;
                tail = newEntry;
            }

            itemsCount++;
        }

        public int getItemsCount() {
            return itemsCount;
        }

        @Override
        public Iterator<T> iterator() {
            return new ListIterator<>(head);
        }
    }

    private class ListIterator<T> implements Iterator<T> {

        private ListEntry<T> cursor;

        public ListIterator(ListEntry<T> head) {
            cursor = head;
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public T next() {
            if (cursor == null) {
                throw new NoSuchElementException("I'm exhausted!");
            }

            T entry = cursor.payload;
            cursor = cursor.next;

            return entry;
        }
    }
}
