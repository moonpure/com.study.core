package com.study.core.sort;

public class MergeSort {
    public void sort(int[] array, int left, int right, int[] temp) {
        if (left < right) {
//            求出中间值
            int mid = (left + right) / 2;
//            向左边分解
            sort(array, left, mid, temp);
//            向右边分解
            sort(array, mid + 1, right, temp);
//            合并数据
            sum(array, left, right, mid, temp);
        }
    }

    /**
     * 合并元素
     *
     * @param array
     * @param left
     * @param right
     * @param mid
     * @param temp
     */
    private void sum(int[] array, int left, int right, int mid, int[] temp) {
        int i = left;
        int j = mid + 1;
//        指向临时数组下标
        int t = 0;
//        开始循环比较左右两遍数组元素比较
        while (i <= mid && j <= right) {

            if (array[i] <= array[j]) {
                temp[t] = array[i];
                t++;
                i++;
            } else {
                temp[t] = array[j];
                t++;
                j++;
            }
        }
//        把剩余的元素直接存放在临时数组中
        while (i <= mid) {
            temp[t] = array[i];
            t++;
            i++;
        }
        while (j <= right) {
            temp[t] = array[j];
            t++;
            j++;
        }

//        临时数组中的元素拷贝至原数组中
        int tempIndex = left;
        int k = 0;
        while (tempIndex <= right) {
            array[tempIndex] = temp[k];
            k++;
            tempIndex++;
        }
    }

    public  <T extends Comparable<? super T>> void sortG(T[] a, T[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            sortG(a, tmpArray, left, center);
            sortG(a, tmpArray, center + 1, right);
            merge(a, tmpArray, left, center + 1, right);
        }
    }

    /**
     * 合并左右数据方法
     *
     * @param a               :原数组
     * @param tmpArray        : 临时数组
     * @param leftPos         ：左边开始下标
     * @param rightPos：右边开始下标
     * @param rightEnd：右边结束下标
     * @param <T>：元素泛型
     */
    private  <T extends Comparable<? super T>> void merge(T[] a, T[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
        //合并操作
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (a[leftPos].compareTo(a[rightPos]) <= 0) {
                tmpArray[tmpPos++] = a[leftPos++];
            } else {
                tmpArray[tmpPos++] = a[rightPos++];
            }
        }
        // 复制前半部分
        while (leftPos <= leftEnd) {
            tmpArray[tmpPos++] = a[leftPos++];
        }
        //复制后半部分
        while (rightPos <= rightEnd) {
            tmpArray[tmpPos++] = a[rightPos++];
        }
        // 回写原数组
        for (int i = 0; i < numElements; i++, rightEnd--) {
            a[rightEnd] = tmpArray[rightEnd];
        }
    }


}
