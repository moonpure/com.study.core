package com.study.core.task;

import java.util.concurrent.*;

public class PhaserTask {
    private static final ExecutorService threadPool = new ThreadPoolExecutor(
            10, 20,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000));

    public static void phaserLatch() {
        final Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> doLatchHandler(phaser));
        }
        System.out.println(Thread.currentThread().getName() + " wait");
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println(Thread.currentThread().getName() + "线程执行完毕");
    }

    private static void doLatchHandler(Phaser phaser) {
        synchronized (phaser) {
            System.out.println(Thread.currentThread().getName() + "running");
            phaser.arrive();
        }
    }

    public static void runBarrier() {
        Phaser phaser = new Phaser(6);
        for (int i = 0; i < 5; i++) {

            threadPool.execute(() -> doBarrier(phaser));
        }
        System.out.println("=========" + Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();
        System.out.println("***" + Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();
        System.out.println("##########" + Thread.currentThread().getName());
    }

    private static void doBarrier(Phaser phaser) {
        System.out.println("=========" + Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();
        System.out.println("***" + Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();
        System.out.println("##########" + Thread.currentThread().getName());
    }

    public static void phaserRegister() {
        // 定义一个分片 parties 为0 的 Phaser
        final Phaser phaser = new Phaser(6);
        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                System.out.println("注册:" + Thread.currentThread().getName());
               // phaser.register();
                phaser.arriveAndAwaitAdvance();
                System.out.println("执行完成" + Thread.currentThread().getName());
            });

        }
        // 休眠以确保其他子线程顺利调用 register()
        // 主线程调用 register()
        System.out.println("注册:" + Thread.currentThread().getName());
      //  phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.println("执行完成" + Thread.currentThread().getName());
    }

}
