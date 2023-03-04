package com.study.core.sort;

import java.util.Random;

/**
 * 快速排序
 */
public class QuickSort {
    public void sort(int list[], int l, int r) {
        if (l < r) {
            int mid = partition(list, l, r);
            sort(list, l, mid - 1);
            sort(list, mid + 1, r);
        }
    }

    private int partition(int list[], int l, int r) {
        int x = list[r];
        int i = l - 1;
        for (int j = l; j <= r - 1; j++) {
            if (list[j] <= x) {
                i = i + 1;
                int tmp = list[i];
                list[i] = list[j];
                list[j] = tmp;
            }
        }
        int tmp = list[i + 1];
        list[i + 1] = list[r];
        list[r] = tmp;
        return i + 1;
    }


    //单路快速排序，无法实现对数据全为一样的数组进行快排
    public <E extends Comparable<E>> void sortSingle(E arr[], int l, int r, Random random) {
        if (l >= r) return;
        int flagIndex = partitionG(arr, l, r, random); //flagIndex就是左边的都小于它，右边的都大于它
        sortSingle(arr, l, flagIndex - 1, random);//此处传参为index-1因为不对标志元素进行排序
        sortSingle(arr, flagIndex + 1, r, random);
    }

    //l+1~j是小于的区间 j~i-1是大于的，j是一个分界点i~r是还未处理的元素
    private <E extends Comparable<E>> int partitionG(E[] arr, int l, int r, Random random) {
//        避免当数组为完全有序时，调用系统栈次数为元素的个数，使其系统栈溢出
        int index = random.nextInt(r - l + 1) + l;
        swap(arr, l, index);
        int j = l;//j指得就是大于和小于的分界点
        E flag = arr[l];
        for (int i = l + 1; i <= r; i++) {
            if (arr[i].compareTo(flag) < 0) {
                j++;
                swap(arr, j, i);
            }
        }
        swap(arr, l, j);
        return j;
    }

    private <E extends Comparable<E>> void swap(E[] arr, int l, int index) {
        E tmp = arr[l];
        arr[l] = arr[index];
        arr[index] = tmp;
    }

    //二路快速排序，实现可以对完全相同数组的排序，应对所有问题，但是对有大量重复数据的性能不如三路快排
    public <E extends Comparable<E>> void Sort2Ways(E arr[], int l, int r, Random random) {
        if (l >= r) return;
        int flagIndex = partition2Ways(arr, l, r, random);
        Sort2Ways(arr, l, flagIndex - 1, random);//此处传参为index-1因为不对标志元素进行排序
        Sort2Ways(arr, flagIndex + 1, r, random);
    }

    private <E extends Comparable<E>> int partition2Ways(E[] arr, int l, int r, Random random) {
//        避免当数组为完全有序时，调用系统栈次数为元素的个数，使其系统栈溢出
        int index = random.nextInt(r - l + 1) + l;
        swap(arr, l, index);
        //就变在了这个while循环，i指的是要开始遍历的元素，j是大于区间的第一个元素
        int i = l + 1, j = r; //j默认指向最后一个元素
        //尽管是两层，但是是O(n)级别的
        //l+1~i-1是小于等于的元素 i~j-1是要扫描的元素，j~r是大于等于的元素
        while (true) {
            //寻找比目标元素大于等于的元素，用于放左边
            while (i <= j && arr[i].compareTo(arr[l]) < 0)
                i++;
            //寻找比目标元素小于等于的元素，用于放右边
            while (j >= i && arr[j].compareTo(arr[l]) > 0)
                j--;

            if (i >= j) break; //退出的条件

            //无论有没有最终都是将这两个元素交换一下，并且将等于flag的元素进行一次交换
            //性能低下体现在这里，当两个元素都等于flag的时候还是要进行一次交换
            //假设有三个等于flag的元素，，它就会把另外两个加入到大于和小于的区间里，大于和小于的区间元素越多，最后递归的深度就会越深，此时的性能就会越差
            swap(arr, i, j);
            i++;
            j--;
        }
        swap(arr, l, j);
        return j;
    }

    //三路快速排序,解决数据中包含大量重复数据。相当于对二路的一种优化，主要优化等于flag的部分
    /*
     * 三路快排的核心用法就是解决有特定类型情况的排序问题，如LeetCode的75题
     * 优化的核心就是防止相等的元素进入大于或小于的区间
     * */
    public <E extends Comparable<E>> void sort3Ways(E arr[], int l, int r, Random random) {
        if (l >= r) return;

        int index = random.nextInt(r - l + 1) + l;
        swap(arr, l, index);

        //[l+1,lt]是全部<flag的，[gt,r]是全部大于flag的，[lt+1,i-1]是全部等于的，[i,gt]是要处理的元素
        int lt = l, i = l + 1, gt = r + 1; //初始化的目的是让那两个空间为空
        E flag = arr[l];
        while (i < gt) { //循环结束的条件
            if (arr[i].compareTo(flag) < 0) {
                lt++;
                swap(arr, i, lt);
                i++;
            } else if (arr[i].compareTo(flag) > 0) {
                gt--;
                swap(arr, i, gt);
                //i++此处没有i++因为右面的元素都是未知元素，而左面的都是已经判断好了是小于flag的，所以只需要直接放到区间里直接判断即可
            } else {//arr[i]==flag
                i++;
            }
        }
        //将lt放到等于的那个区间里，此时[l,lt-1]小于的区间[lt,gt-1]等于的区间[gt,r]大于的区间
        swap(arr, l, lt);
        //核心改变处，只处理应该递归的那部分元素，而不是将全部元素都进去递归
        sort3Ways(arr, l, lt - 1, random);
        sort3Ways(arr, gt, r, random);
    }


    public int partitionQuick(int[] array, int low, int high) {
        // 取最后一个元素作为中心元素
        int pivot = array[high];
        // 定义指向比中心元素大的指针，首先指向第一个元素
        int pointer = low;
        // 遍历数组中的所有元素，将比中心元素大的放在右边，比中心元素小的放在左边
        for (int i = low; i < high; i++) {
            if (array[i] <= pivot) {
                // 将比中心元素小的元素和指针指向的元素交换位置
                // 如果第一个元素比中心元素小，这里就是自己和自己交换位置，指针和索引都向下一位移动
                // 如果元素比中心元素大，索引向下移动，指针指向这个较大的元素，直到找到比中心元素小的元素，并交换位置，指针向下移动
                int temp = array[i];
                array[i] = array[pointer];
                array[pointer] = temp;
                pointer++;
            }
        }
        // 将中心元素和指针指向的元素交换位置
        int temp = array[pointer];
        array[pointer] = array[high];
        array[high] = temp;
        return pointer;
    }
    /**
     * 处理枢纽值,三数取中
     *
     * @param arr
     * @param left
     * @param right
     */
    private  <E extends Comparable<E>> void dealPivot(E[] arr, int left, int right) {
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
    public void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // 获取划分子数组的位置
            int position = partitionQuick(array, low, high);
            // 左子数组递归调用
            quickSort(array, low, position - 1);
            // 右子数组递归调用
            quickSort(array, position + 1, high);
        }
    }
}
