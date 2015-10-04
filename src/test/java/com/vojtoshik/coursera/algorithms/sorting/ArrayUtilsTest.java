package com.vojtoshik.coursera.algorithms.sorting;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class ArrayUtilsTest {

    @Test
    public void testSwap() {
        Integer[] testArray = {2, 3};

        ArrayUtils.swap(testArray, 0, 1);
        assertEquals(Integer.valueOf(3), testArray[0]);
        assertEquals(Integer.valueOf(2), testArray[1]);
    }

    @Test
    public void testShuffle() {

        int numberOfElements = 100;
        Random random = new Random();

        Integer[] original = new Integer[numberOfElements];

        for (int i = 0; i < numberOfElements; i++) {
            original[i] = random.nextInt();
        }

        Integer[] arrayToShuffle = Arrays.<Integer>copyOf(original, numberOfElements);

        ArrayUtils.shuffle(arrayToShuffle);

        assertEquals(numberOfElements, arrayToShuffle.length);
        assertNotEquals(original, arrayToShuffle);

        Arrays.sort(original);
        Arrays.sort(arrayToShuffle);

        assertEquals(original, arrayToShuffle);
    }
}