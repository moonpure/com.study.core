package com.study.core.sort;

/**
 * 插入排序
 */
public class InsertSort {
    public void sort(int[] array) {
        //控制拿去每一个元素
        for(int i=1;i<array.length;i++){
            //比较次数
            for (int j=i;j>=1;j--){
                //是否小于前面的元素
                if (array[j]<array[j-1]){
                    int temp = 0;
                    temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }else {
                    //continue 与 break
                    break;
                }
            }
        }
    }
    //排序算法
    public static <E extends Comparable<E>> void sortG(E[] arr) {
        for (int i = 1; i < arr.length; i++) {
            E tmp = arr[i];
            int j;
            for (j = i; j - 1 >= 0; j--) {
                if (tmp.compareTo(arr[j - 1]) < 0)
                    arr[j]=arr[j-1];
                else
                    break;
            }
            arr[j]=tmp;
        }
    }

}