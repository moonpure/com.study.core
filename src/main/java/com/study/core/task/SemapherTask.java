package com.study.core.task;

import java.util.concurrent.*;

public class SemapherTask {
    private static Semaphore semaphore = new Semaphore(3);
    private static final ExecutorService threadPool = new ThreadPoolExecutor(
            10, 20,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000));
    public static void run()
    {
        for(int i = 0;i< 50;i++){
            threadPool.execute(() -> doHandler());
        }
    }
    private static void  doHandler()
    {
        System.out.println("欢迎 " + Thread.currentThread().getName() + " 来到停车场");
        // 判断是否允许停车
        if(semaphore.availablePermits() == 0) {
            System.out.println("车位不足，请耐心等待");
        }
        try {
            // 尝试获取
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " 进入停车场");
            Thread.sleep(500);// 模拟车辆在停车场停留的时间
            System.out.println(Thread.currentThread().getName() + " 驶出停车场");
            semaphore.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
