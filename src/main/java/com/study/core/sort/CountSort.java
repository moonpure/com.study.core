package com.study.core.sort;

public class CountSort {
    public int[] sort(int[] a){
        int b[] = new int[a.length];
        int max = a[0],min = a[0];
        for(int i:a){
            if(i>max){
                max=i;
            }
            if(i<min){
                min=i;
            }
        }//这里k的大小是要排序的数组中，元素大小的极值差+1
        int k=max-min+1;
        int c[]=new int[k];
        for(int i=0;i<a.length;++i){
            c[a[i]-min]+=1;//优化过的地方，减小了数组c的大小
        }
        for(int i=1;i<c.length;++i){
            c[i]=c[i]+c[i-1];
        }
        for(int i=a.length-1;i>=0;--i){
            b[--c[a[i]-min]]=a[i];//按存取的方式取出c的元素
        }
        return b;
    }
}
