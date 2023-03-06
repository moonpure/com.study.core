package com.study.core.task;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static org.junit.jupiter.api.Assertions.*;

class ForkJoinTaskTest {

    @Test
    void compute() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();//实现ForkJoin 就必须有ForkJoinPool的支持
        ForkJoinTask<Long> task = new ForkJoinWork(0L, 1000L);//参数为起始值与结束值
        Long invoke = forkJoinPool.invoke(task);
        System.out.println("和:" +invoke);
    }
}