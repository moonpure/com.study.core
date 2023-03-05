package com.study.core.sort;

import java.io.Serial;
import java.io.Serializable;

public class BasicSort implements Serializable {

    @Serial
    private static final long serialVersionUID = -1661928303715867750L;

    //     1.获取原序列的最大位多少
//      @param arrays
    public void sort(int[] arrays) {

//        获取最大位数
        int max = 0;
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] > max) {
                max = arrays[i];
            }
        }

//        获取字符串长度，所以把int类型转字符串类型
        int maxLength = (max + "").length();
//      定义二维数组，大小10，表示10个桶，每一个桶则是一个数组
//       [[],[],[],[],[]...]
        int[][] bucket = new int[10][arrays.length];

//        辅助数组
        int[] bucketElementCount = new int[10];

//        循环获取无序数列
        for (int j = 0; j < arrays.length; j++) {
            int locationElement = arrays[j] % 10;

//            放入桶中
            bucket[locationElement][bucketElementCount[locationElement]] = arrays[j];
            bucketElementCount[locationElement]++;
        }

//        遍历每一个桶，讲元素存放原数组中
        int index = 0;
        for (int k = 0; k < bucketElementCount.length; k++) {
            if (bucketElementCount[k] != 0) {
                for (int l = 0; l < bucketElementCount[k]; l++) {
                    arrays[index++] = bucket[k][l];
                }
            }
            bucketElementCount[k] = 0;
        }
//        第一轮针对个位数进行比较
        for (int j = 0; j < arrays.length; j++) {
            int locationElement = arrays[j] / 1 % 10;

            bucket[locationElement][bucketElementCount[locationElement]] = arrays[j];
            bucketElementCount[locationElement]++;
        }

//        取出来按照桶的顺序放回原数组中
        int indx = 0;
        for (int k = 0; k < bucketElementCount.length; k++) {
            if (bucketElementCount[k] != 0) {
                for (int l = 0; l < bucketElementCount[k]; l++) {
                    arrays[indx++] = bucket[k][l];
                }
            }
            bucketElementCount[k] = 0;
        }

//        判断十位数
        for (int j = 0; j < arrays.length; j++) {
            int locationElement = arrays[j] / 10 % 10;

            bucket[locationElement][bucketElementCount[locationElement]] = arrays[j];
            bucketElementCount[locationElement]++;
        }

//        取出来按照桶的顺序放回原数组中
        indx = 0;
        for (int k = 0; k < bucketElementCount.length; k++) {
            if (bucketElementCount[k] != 0) {
                for (int l = 0; l < bucketElementCount[k]; l++) {
                    arrays[indx++] = bucket[k][l];
                }
            }
            bucketElementCount[k] = 0;
        }

//        获取百位数比较
        for (int j = 0; j < arrays.length; j++) {
            int locationElement = arrays[j] / 100 % 10;

            bucket[locationElement][bucketElementCount[locationElement]] = arrays[j];
            bucketElementCount[locationElement]++;
        }

//        取出来按照桶的顺序放回原数组中
        indx = 0;
        for (int k = 0; k < bucketElementCount.length; k++) {
            if (bucketElementCount[k] != 0) {
                for (int l = 0; l < bucketElementCount[k]; l++) {
                    arrays[indx++] = bucket[k][l];
                }
            }
            bucketElementCount[k] = 0;
        }
    }
}