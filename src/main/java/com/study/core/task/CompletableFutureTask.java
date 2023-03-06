package com.study.core.task;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureTask {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public void run() {
        //1.异步执行任务，并返回结果：supplyAsync 异步处理，并返回结果，默认使用ForkJoinPool.commonPool()线程池，
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "HELLO");
        // 自定义线程池
        future = CompletableFuture.supplyAsync(() -> "hello", threadPool);
        //2.异步执行任务，不返回结果：runAsync
        CompletableFuture.runAsync(() -> System.out.println("HELLO WORLD !"));
        CompletableFuture.runAsync(() -> System.out.println("HELLO WORLD !"), threadPool);
        //3.依赖单一阶段：thenApply thenApplyAsync
        future = CompletableFuture.supplyAsync(() -> "HELLO").thenApply(a -> {
            return a + " lili!";
        });
        //4.组合与撰写：thenCompose() ，thenCombine()，thenCombineAsync.
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello")
                .thenCompose(res -> CompletableFuture.supplyAsync(() -> res + " lili"))
                .thenCompose(res -> CompletableFuture.supplyAsync(() -> res + " lucy"));
        // 执行结果： =====> hello lili lucy
        // mian线程下同步执行。

        f1 = CompletableFuture.supplyAsync(() -> "hello")
                .thenCompose(res -> CompletableFuture.supplyAsync(() -> res + " lili"))
                .thenCompose(res -> CompletableFuture.supplyAsync(() -> res + " lucy"))
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> " how are you!"), (a, b) -> a + b);
        //String dd=f1.get();
        // 执行结果： =====> hello lili lucy how are you!
        //5.依赖两个任务中的一个：applyToEither()，那个任务先结束，就依赖那个任务。
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "lucy";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "lili";
        }), a -> "hello " + a);
        // 执行结果： ret ====> hello lili 如果下面sleep改成3s，执行结果：ret ====> hello lucy
        //6. 消费型，依赖单阶段：thenAccept()，thenAcceptAsync()
        CompletableFuture<Void> futureVoid = CompletableFuture.supplyAsync(() -> "hello")
                .thenAcceptAsync(a -> {
                    a = a + " lucy !";
                });
          // 执行结果：ret ======> hello lucy ! 而且是异步的，不会阻塞主线程，下面的end是先打印出来的
        //7.消费型，依赖两个任务都完成：thenAcceptBoth()，thenAcceptBothAsync()
        CompletableFuture.supplyAsync(() -> "hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " lili"), (a, b) -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        // 执行结果：=======>hello lili
        //8.消费型：acceptEither() 依赖两个任务中先执行结束的那个
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "lucy";
        }).acceptEither(CompletableFuture.supplyAsync(() -> "lili"), a -> {

        });
        // 执行结果：hello lili
        //9.消费型，无论正常，还是异常都会消费处理，而且不会吞掉异常 whenComplete()，whenCompleteAsync()
        future = CompletableFuture.supplyAsync(() -> {
            if (ThreadLocalRandom.current().nextInt(2) < 2) {
                throw new RuntimeException("error");
            }
            return "hello";
        }).whenComplete((a, e) -> {

        });

        // 执行结果：ret -> null lili!  而且打印两次错误日志，一次是log打印，一次是get的时候。
        //10.产出型，无论正常还是异常都是处理，并返回结果。handle，handleAsync
        future = CompletableFuture.supplyAsync(() -> "hello")
                .handle((a, e) -> a + " lili!");
// 执行结果：ret ==> hello lili!
        //11.产出型，异常时候进行处理，并产出，有点像try-catch()，exceptionally()
        CompletableFuture<Object> f =
                CompletableFuture.supplyAsync(() -> "Hello")
                        .thenApplyAsync(res -> res + " World")
                        .thenApplyAsync(
                                res -> {
                                    throw new RuntimeException(" test has error");
                                    //  return res + "!";
                                })
                        .exceptionally(
                                e -> {

                                    return "出异常了。。";
                                });

// 执行结果：ret ====> 出异常了。。
// 假如不抛出异常，执行结果：ret ====> Hello World!
        //12. 无关性任务，互相依赖，allOf
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture<String> f4 = CompletableFuture.supplyAsync(() -> "world");
        CompletableFuture<String> f5 =
                CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "!";
                        });

        // 使用allOf方法 f3 f4 f5 都执行结束之前一直阻塞
        CompletableFuture.allOf(f3, f4, f5).join();
        try {
            System.out.println(f3.get());
            System.out.println(f4.get());
            System.out.println(f5.get());
            List<String> r = Stream.of(f3, f4, f5).map(CompletableFuture::join).collect(Collectors.toList());

            System.out.println(r);
        } catch (Exception ex) {
        }
    }
      // 执行结果：hello
      // world
      // !
      // [hello, world, !]
      // 而且要等f1，f2,f3 三个任务都结束，不然会一直阻塞。
}