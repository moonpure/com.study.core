package com.study.core.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTask {

        private int data;

        private final StampedLock lock = new StampedLock();

        public StampedLockTask(int data) {
            this.data = data;
        }

        public int read(int readTime) {
            long stamp = lock.tryOptimisticRead();
          //  log.info("optimistic read locking...{}", stamp);
            try {
                TimeUnit.SECONDS.sleep(readTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (lock.validate(stamp)) {
//               //stamp log.info("read finish...{}, data:{}", stamp, data);
                return data;
            }
            // 锁升级 - 读锁
           // log.info("updating to read lock... {}", stamp);
            try {
                stamp = lock.readLock();
                //log.info("read lock {}", stamp);
                TimeUnit.SECONDS.sleep(readTime);
               // log.info("read finish...{}, data:{}", stamp, data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
               // log.info("read unlock {}", stamp);
                lock.unlockRead(stamp);
            }
            return data;
        }

        public void write(int newData) {
            long stamp = lock.writeLock();
           // log.info("write lock {}", stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
                this.data = newData;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
               // log.info("write unlock {}", stamp);
                lock.unlockWrite(stamp);
            }
        }
    }
