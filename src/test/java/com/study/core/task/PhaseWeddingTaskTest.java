package com.study.core.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhaseWeddingTaskTest {

    @Test
    void run() {
        PhaseWeddingTask.run();
        try {


            Thread.sleep(10000);
        }
        catch (Exception ex)
        {

        }
    }
}