package com.vojtoshik.coursera.algorithms.sorting;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ArrayUtilsTest {

    @Test
    public void testSwap() {
        Integer[] testArray = {2, 3};

        ArrayUtils.swap(testArray, 0, 1);
        assertEquals(Integer.valueOf(3), testArray[0]);
        assertEquals(Integer.valueOf(2), testArray[1]);
    }
}