package com.study.core.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTask {

        private static Lock lock = new ReentrantLock();

        private static  int count = 0;

        private static void inrc() {
            try {
                //加锁
                lock.lock();
                Thread.sleep(10);
                count++;
                //模拟重入锁
                dec();//2
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        private static void dec(){
            lock.lock();
            count--;
            lock.unlock();
        }

        public static void main(String[] args) throws InterruptedException {
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    inrc();//1
                }).start();
            }
            TimeUnit.SECONDS.sleep(3);
            System.out.println(count);
        }
    }
