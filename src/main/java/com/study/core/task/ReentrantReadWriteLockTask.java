package com.study.core.task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTask {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public void read(Lock lock) throws InterruptedException {
        lock.lock();
        Thread.sleep(1000);
        System.out.println("read");
        lock.unlock();
    }

    public void write(Lock lock) throws InterruptedException {
        lock.lock();
        Thread.sleep(1000);
        System.out.println("write");
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockTask t = new ReentrantReadWriteLockTask();
        Runnable read = () -> {
            try {
                t.read(readLock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable write = () -> {
            try {
                t.write(writeLock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 20; i++) {
            new Thread(read).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(write).start();
        }
    }
}


