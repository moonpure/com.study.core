package com.study.core.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class BubbleSortTest {

    @Test
    void sort() {
        BubbleSort bs=new BubbleSort();
        Integer[] arrays =new Integer[]{15,63,97,12,235,66};
        bs.sortG(arrays);
        System.out.println(Arrays.toString(arrays));
    }
}