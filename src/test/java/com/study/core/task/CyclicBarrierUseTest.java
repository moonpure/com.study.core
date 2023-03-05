package com.study.core.task;

import org.junit.jupiter.api.Test;

class CyclicBarrierUseTest {

    @Test
    void run() {
        CyclicBarrierTask.run();
        try {


            Thread.sleep(10000);
        }
        catch (Exception ex)
        {

        }
    }
}