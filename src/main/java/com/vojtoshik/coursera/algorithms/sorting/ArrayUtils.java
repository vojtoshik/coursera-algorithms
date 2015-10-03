package com.vojtoshik.coursera.algorithms.sorting;

import java.util.Random;

/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class ArrayUtils {

    /**
     * Shuffles array of elements and does exactly N - 1 exchanges, where N is the size of arrayToShuffle
     *
     * @param arrayToShuffle
     */
    public static void shuffle(Object[] arrayToShuffle) {
        Random random = new Random();

        for (int i = 1; i < arrayToShuffle.length; i++) {
            swap(arrayToShuffle, i, random.nextInt(i));
        }
    }

    /**
     * Exchanges values which are located under {@code index1} and {@code index2}
     *
     * @param array
     * @param index1
     * @param index2
     */
    public static void swap(Object[] array, int index1, int index2) {
        Object tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}
