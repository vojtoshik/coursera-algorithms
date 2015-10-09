package com.vojtoshik.coursera.algorithms.sorting;

import java.util.Comparator;

/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class QuickSort {
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        sort(array, comparator, 0, array.length - 1);
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
}
