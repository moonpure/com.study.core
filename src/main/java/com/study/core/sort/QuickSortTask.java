package com.study.core.sort;

import java.util.concurrent.RecursiveAction;

public class QuickSortTask<T extends Comparable<T>> extends RecursiveAction {

    private final T[] array;
    private  final int left;
    private final int right;

    public QuickSortTask(T[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        int pivot = partition(array, left, right);
        QuickSortTask<T> task1 = null;
        QuickSortTask<T> task2 = null;
        if (pivot - left > 1) {
            task1 = new QuickSortTask<>(array, left, pivot-1);
            task1.fork();
        }
        if (right - pivot > 1) {
            task2 = new QuickSortTask<>(array, pivot+1, right);
            task2.fork();
        }
        if (task1 != null && !task1.isDone()) {
            task1.join();
        }
        if (task2 != null && !task2.isDone()) {
            task2.join();
        }
    }

    public  int partition(T[] a, int left, int right) {
        dealPivot(a,  left, right);
        T pivot = a[left];
        while (left < right) {
            while (left < right && a[right].compareTo( pivot)>=0) {
                right--;
            }
            swap(a, left, right);
            while (left < right && a[left].compareTo(pivot)<=0) {
                left++;
            }
            swap(a, left, right);
        }
        return left;
    }

    public  void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    /**
     * 处理枢纽值,三数取中
     *
     * @param arr  排序数组
     * @param left 左位置
     * @param right 右位置
     */
    private  void dealPivot(T[] arr, int left, int right) {
        if(right-left+1<3)
        {
            return;
        }
        int mid = (left + right) / 2;
        if (arr[left].compareTo(arr[mid])>0) {
            swap(arr, left, mid);
        }
        if (arr[left].compareTo(arr[right])>0) {
            swap(arr, left, right);
        }
        if (arr[right].compareTo(arr[mid])<0) {
            swap(arr, right, mid);
        }
        swap(arr, right - 1, mid);
    }

}