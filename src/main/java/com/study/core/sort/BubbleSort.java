package com.study.core.sort;

import java.util.Comparator;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public void sort(int[] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
//            控制比较次数,三者交换，实现排序
            for (int j = 0; j < arrays.length - 1 - i; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    int temp = 0;//类似空桶
                    temp = arrays[j]; //A桶中水倒入空桶C中
                    arrays[j] = arrays[j + 1];//把B桶的水倒入A桶中
                    arrays[j + 1] = temp;//把C桶的水倒入B桶中
                }
            }

        }
    }
    /**
     * 设置一个标志，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。
     *
     * @param a
     * @return
     */
    public void bubbleSort2(int[] a) {
        int j;
        int n = a.length;
        boolean flag = true;// 发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
        while (flag) {
            flag = false;// 每次开始排序前，都设置flag为未排序过
            for (j = 1; j < n; j++) {
                if (a[j - 1] > a[j]) {// 前面的数字大于后面的数字就交换
                    // 交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    // 表示交换过数据;
                    flag = true;
                }
            }
            n--;// 减小一次排序的尾边界
        } // end while
    }
    /**
     * 设置一个标志，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。
     *
     * @param a
     * @return
     *
     */
    public  void bubbleSort3(int[] a) {
        int j;
        int n = a.length;
        boolean flag = true;// 发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
        while (flag) {
            flag = false;// 每次开始排序前，都设置flag为未排序过
            for (j = 1; j < n; j++) {
                if (a[j - 1] > a[j]) {// 前面的数字大于后面的数字就交换
                    // 交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    // 表示交换过数据;
                    flag = true;
                }
            }
            n--;// 减小一次排序的尾边界
        } // end while
    }// end
    /**
     * 使用Comparable接口的泛型冒泡排序
     */
    public <T extends Comparable<T>> void sortG(T[] list) {
        for (int i = 1; i < list.length; i++) {
            for (int j = 0; j < list.length - i; j++)
                if (list[j].compareTo(list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
        }
    }

    /**
     * 使用Comparator接口的泛型冒泡排序
     */
    public static <E> void sortG(E[] list, Comparator<? super E> comparator) {
        for (int i = 1; i < list.length; i++) {
            for (int j = 0; j < list.length - i; j++)
                if (comparator.compare(list[j], list[j + 1]) > 0) {
                    E temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
        }
    }

}
