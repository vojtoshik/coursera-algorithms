package com.vojtoshik.coursera.algorithms.sorting;

import java.util.Comparator;

/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class QuickSort {

    /**
     * Traditional implementation of quick sort which doesn't work that efficient (in fact, it uses quadratic time) for
     * inputs that have a lot of equal elements.
     *
     * @param array
     * @param comparator
     * @param <T>
     */
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        // we need this as quick sort may degrade to O(n^2) for some specific-designed inputs
        ArrayUtils.shuffle(array);
        sort(array, comparator, 0, array.length - 1);
    }

    /**
     * Implementation which uses 3-way partitioning technique which is extremely efficient for inputs that have a lot
     * of equal elements.
     *
     * @param array
     * @param comparator
     * @param <T>
     */
    public static <T> void sortWith3WayPartitioning(T[] array, Comparator<T> comparator) {

        // we need this as quick sort may degrade to O(n^2) for some specific-designed inputs
//        ArrayUtils.shuffle(array);
        sortWith3WayPartitioning(array, comparator, 0, array.length - 1);
    }

    private static <T> void sort(T[] array, Comparator<T> comparator, int leftBound, int rightBound) {
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
                ArrayUtils.swap(array, leftIndex, rightIndex);
            }
        }

        sort(array, comparator, leftBound, rightIndex);
        sort(array, comparator, rightIndex + 1, rightBound);
    }

    private static <T> void sortWith3WayPartitioning(T[] array, Comparator<T> comparator, int leftBound, int rightBound) {

        if (leftBound >= rightBound) {
            return;
        }

        int index = leftBound;
        int lowerThanIndex = leftBound;
        int greaterThanIndex = rightBound;

        T mediumValue = array[leftBound];

        while (index <= greaterThanIndex) {
            int cmp = comparator.compare(array[index], mediumValue);

            if (cmp < 0) {
                ArrayUtils.swap(array, index++, lowerThanIndex++);
            } else if (cmp > 0) {
                ArrayUtils.swap(array, index, greaterThanIndex--);
            } else {
                index++;
            }
        }

        sortWith3WayPartitioning(array, comparator, leftBound, lowerThanIndex - 1);
        sortWith3WayPartitioning(array, comparator, greaterThanIndex + 1, rightBound);
    }
}
