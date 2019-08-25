package com.michael.concurrency.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreTryAcquireExmaple1 {

    public static final int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    if(semaphore.tryAcquire()) {
                        test(threadNum);
                    }
                } catch (Exception e) {
                    log.error("exception" ,e);
                } finally {
                    /**
                     * 如果不打印下面这行结果是不一样的
                     * */
//                    log.error("semaphore.release();");
                    semaphore.release();
                }
            });
        }
        log.info("finish");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        log.info("threadNum: {}", threadNum);
        Thread.sleep(1000);
    }
}
