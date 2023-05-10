package com.study.core.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class BubbleSortTest {

    @Test
    void sort() {
        int MIN_TRY_MERGE_SIZE = 4 << 10;
        System.out.println(MIN_TRY_MERGE_SIZE);
        BubbleSort bs=new BubbleSort();
        Integer[] arrays =new Integer[]{15,63,97,12,235,66};
        bs.sortG(arrays);
        System.out.println(Arrays.toString(arrays));
    }
}