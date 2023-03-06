package com.study.core.task;

import java.util.Random;
import java.util.concurrent.*;

public class PhaseWeddingTask {
    private static final ExecutorService threadPool = new ThreadPoolExecutor(
            10, 20,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000));
      private   static Random r = new Random();
    private   static MarriagePhaser phaser = new MarriagePhaser();

      private   static void milliSleep(int milli) {
            try {
                TimeUnit.MILLISECONDS.sleep(milli);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void run() {
            // 7 人婚礼
            phaser.bulkRegister(7);
            // 客人参加婚礼
            for (int i = 0; i < 5; i++) {
                threadPool.execute(new Person("p" + i));
            }
            // 新婚夫妇参加婚礼
            threadPool.execute(new Person("新郎"));
            threadPool.execute(new Person("新娘"));
        }

        static class MarriagePhaser extends Phaser {
            // 当7个线程都到达栅栏，才调用这个
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                switch (phase) {
                    case 0:
                        System.out.println("所有人到齐了！" + registeredParties);
                        return false;
                    case 1:
                        System.out.println("所有人吃完了！" + registeredParties);
                        return false;
                    case 2:
                        System.out.println("所有人离开了！" + registeredParties);
                        return false;
                    case 3:
                        System.out.println("婚礼结束！新郎新娘抱抱！" + registeredParties);
                        return true;
                    default:
                        return true;
                }
            }
        }

        static class Person implements Runnable {
            String name;

            public Person(String name) {
                this.name = name;
            }

            public void arrive() {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 到达现场！\n", name);
                // 某个线程到达栅栏，就停住，直到所有线程都完成，才推倒栅栏，继续往前走
                phaser.arriveAndAwaitAdvance();
            }

            public void eat() {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 吃完!\n", name);
                phaser.arriveAndAwaitAdvance();
            }

            public void leave() {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 离开！\n", name);
                phaser.arriveAndAwaitAdvance();
            }

            private void hug() {
                if (name.equals("新郎") || name.equals("新娘")) {
                    milliSleep(r.nextInt(1000));
                    System.out.printf("%s 洞房！\n", name);
                    phaser.arriveAndAwaitAdvance();
                } else {
                    phaser.arriveAndAwaitAdvance();
                    // 其他线程解注册
                   // phaser.arriveAndDeregister();
                    //phaser.register()
                }
            }

            @Override
            public void run() {
                arrive();
                System.out.println("所有人到齐了！----------------");
                eat();
                System.out.println("所有人吃完了！----------------");
                leave();
                System.out.println("所有人离开了！----------------");
                hug();
                System.out.println("婚礼结束！新郎新娘抱抱！--------");
            }
        }
    }