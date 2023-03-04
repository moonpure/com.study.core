package com.study.core.sort;

public class HeapSort {

    public void sort(int[] array) {
        //这里元素的索引是从0开始的,所以最后一个非叶子结点array.length/2 - 1
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length);  //调整堆
        }

        // 上述逻辑，建堆结束
        // 下面，开始排序逻辑
        for (int j = array.length - 1; j > 0; j--) {
            // 元素交换,作用是去掉大顶堆
            // 把大顶堆的根元素，放到数组的最后；换句话说，就是每一次的堆调整之后，都会有一个元素到达自己的最终位置
            swap(array, 0, j);
            // 元素交换之后，毫无疑问，最后一个元素无需再考虑排序问题了。
            // 接下来我们需要排序的，就是已经去掉了部分元素的堆了，这也是为什么此方法放在循环里的原因
            // 而这里，实质上是自上而下，自左向右进行调整的
            adjustHeap(array, 0, j);
        }
    }

    /**
     * 整个堆排序最关键的地方
     *
     * @param array  待组堆
     * @param i      起始结点
     * @param length 堆的长度
     */
    private void adjustHeap(int[] array, int i, int length) {
        // 先把当前元素取出来，因为当前元素可能要一直移动
        int temp = array[i];
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {  //2*i+1为左子树i的左子树(因为i是从0开始的),2*k+1为k的左子树
            // 让k先指向子节点中最大的节点
            if (k + 1 < length && array[k] < array[k + 1]) {  //如果有右子树,并且右子树大于左子树
                k++;
            }
            //如果发现结点(左右子结点)大于根结点，则进行值的交换
            if (array[k] > temp) {
                swap(array, i, k);
                // 如果子节点更换了，那么，以子节点为根的子树会受到影响,所以，循环对子节点所在的树继续进行判断
                i = k;
            } else {  //不用交换，直接终止循环
                break;
            }
        }
    }

    /**
     * 交换元素
     *
     * @param arr
     * @param a   元素的下标
     * @param b   元素的下标
     */
    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public <E extends Comparable<E>> void sortG(E arr[]) {
        if (arr.length <= 1) return;
        //从第一个非叶子节点的元素开始进行堆整理。
        /*arr.length-2解释：
         * 因为某个节点的双亲是(i-1)/2,所以设该i就是数组中的最后一个元素，即arr.lenth-1，所以其双亲为(arr.length-1-1)/2
         */
        for (int i = (arr.length - 2) / 2; i >= 0; i--) {
            sitDown(arr, i, arr.length);
        }
        //将元素添加到原数组，并且不断构建堆
        for (int i = arr.length - 1; i >= 0; i--) {
            swapG(arr, 0, i);
            sitDown(arr, 0, i);
        }
    }

    private <E extends Comparable<E>> void sitDown(E[] arr, int i, int n) {
        while (2 * i + 1 < n) {
            int left = 2 * i + 1;
            //找到左右孩子最大的那个索引的下标，最终的left指向的就是要交换的那个孩子的索引
            if (left + 1 < n && arr[left].compareTo(arr[left + 1]) < 0)
                left++;

            //如果其比最大的那个还要大，则不需要交换了，直接退出
            if (arr[i].compareTo(arr[left]) >= 0)
                break;
            //否则执行交换逻辑
            swapG(arr, i, left);
            //让i等于应该交换的那个元素的下标
            i = left;
        }
    }

    private <E extends Comparable<E>> void swapG(E[] arr, int l, int index) {
        E tmp = arr[l];
        arr[l] = arr[index];
        arr[index] = tmp;
    }
}