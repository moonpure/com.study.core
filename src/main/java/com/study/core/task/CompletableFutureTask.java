package com.study.core.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class CompletableFutureTask {
    ForkJoinPool forkJoinPool =ForkJoinPool.commonPool();
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
}
