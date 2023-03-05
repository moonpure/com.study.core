package com.study.core.sort;

import java.lang.reflect.Array;
import java.util.concurrent.RecursiveTask;

public class MergeSortTask<T extends Comparable<T>> extends RecursiveTask<Void> {
    //不需要返回值的task继承RecursiveAction更好
    private final T[] array;
    private final int left;
    private final int right;
    private final Class<T> type;


    public MergeSortTask(Class<T> type, T[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.type = type;
    }

    @Override
    protected Void compute() {
        boolean isNeedSplit = right - left >= 1;
        if (isNeedSplit) {
            int mid = (left + right) / 2;
            MergeSortTask<T> mergeSortTask1 = new MergeSortTask<>(type, array, left, mid);
            MergeSortTask<T> mergeSortTask2 = new MergeSortTask<>(type, array, mid + 1, right);
            mergeSortTask1.fork();
            mergeSortTask2.fork();
            mergeSortTask1.join();
            mergeSortTask2.join();
            merge(array, left, mid, right);
        } else {
            int mid = (left + right) / 2;
            merge(array, left, mid, right);
        }
        return null;
    }

    private void merge(T[] a, int left, int mid, int right) {
        int len = right - left + 1;
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) Array.newInstance(type, len);
        // List<?>[] temp = new ArrayList<?>[len];
        int i = left;
        int j = mid + 1;
        int index = 0;
        while (i <= mid && j <= right) {
            temp[index++] = a[i].compareTo(a[j]) <= 0 ? a[i++] : a[j++];
        }
        while (i <= mid) {
            temp[index++] = a[i++];
        }
        while (j <= right) {
            temp[index++] = a[j++];
        }
        for (int k = 0; k < temp.length; k++) {
            a[left++] = temp[k];
        }
    }
}