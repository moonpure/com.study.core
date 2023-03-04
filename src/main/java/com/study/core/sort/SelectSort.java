package com.study.core.sort;

public class SelectSort {
    public static void sort( int[] arr) {
        for (int i=0;i<arr.length;i++){
            for (int j=arr.length-1;j>i;j--){
                if (arr[j]<arr[i]){
                    int temp = 0;
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }
}