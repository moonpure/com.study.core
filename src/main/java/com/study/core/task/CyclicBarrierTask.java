package com.study.core.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CyclicBarrierTask {
    private final static    CyclicBarrier   cyclicBarrier  = new CyclicBarrier(10,()->doHandler());
    /**
     * 线程池
     */
    private static final ExecutorService threadPool = new ThreadPoolExecutor(
            10, 20,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000));

    public static  void run() {

        List<Integer> resultList = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                final int ini = i;
                threadPool.execute(() -> {
                    try {
                        resultList.add(waitHandler(ini));
                    } finally {
                        try {
                            cyclicBarrier.await();
                        } catch (InterruptedException e) {
                            System.out.println("发生异常:" + e.getMessage());
                        } catch (BrokenBarrierException e) {
                            System.out.println("发生异常:" + e.getMessage());

                        }
                    }
                });
            }
            System.out.println("for end");

        } catch (Exception e) {
            System.out.println("发生异常:" + e.getMessage());
        }

    }

    private static Integer waitHandler(int i) {
        try {
            System.out.println("子线程" +
                    Thread.currentThread().getName() + "正在准备");
            Thread.sleep(500);
            System.out.println("子线程" +
                    Thread.currentThread().getName() + "准备好了");
            return i;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private static void  doHandler()
    {
        System.out.println("wait end,do begin");
    }

}
