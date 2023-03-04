package com.study.core.sort;

public class ShellSort {
    public  void sort( int[] array) {
//        实现增量变化
        for (int gap = array.length/2;gap>0;gap/=2){
            for (int i=gap;i<array.length;i++){
                for (int j=i-gap;j>=0;j-=gap){
                    if (array[j]>array[j+gap]){
                        int temp = 0;
                        temp = array[j];
                        array[j] = array[j+gap];
                        array[j+gap] = temp;
                    }
                }
            }
        }
    }
}