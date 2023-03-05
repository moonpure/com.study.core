package com.study.core.sort;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTaskTest {

    @Test
    void compute() {
        Integer[] a = {4,2,1,4,7,5,3,8,2,7,1,78,89,6,5,4,8,5};
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MergeSortTask<Integer> task = new MergeSortTask<>(Integer.class,a, 0, a.length-1);
        Future<Void> result = forkJoinPool.submit(task);
        try {
            result.get();
            for (int n : a) {
                System.out.print(n + " ");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}