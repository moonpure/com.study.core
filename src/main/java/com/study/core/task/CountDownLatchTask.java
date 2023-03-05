package com.study.core.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CountDownLatchTask {
  private   final static CountDownLatch countLatch = new CountDownLatch(10);
    /**
     * 线程池
     */
    private static final ExecutorService threadPool = new ThreadPoolExecutor(
            10, 20,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000));
    public static void  latchRun()
    {
        try {
            List<Integer> resultList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                final int ini=i;
                threadPool.execute(() -> {
                    try {
                        resultList.add(doHandler(ini));
                    } finally {
                        countLatch.countDown();
                    }
                });
            }

        } catch (Exception e) {
            System.out.println("发生异常:"+e.getMessage());
        }

            try {
                System.out.println("门卫等待员工上班中...");
                //主线程阻塞等待计数器归零
                countLatch.await();
                System.out.println("员工都来了,门卫去休息了");
            } catch (InterruptedException e) {
                System.out.println("发生异常:"+e.getMessage());
            }



    }
private static Integer doHandler(  int i) {
        try {
            System.out.println("子线程" + Thread.currentThread().getName() + "正在赶路");
            Thread.sleep(500);
            System.out.println("子线程" + Thread.currentThread().getName() + "到公司了");
            return i;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }




}
