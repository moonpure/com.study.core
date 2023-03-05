package com.study.core.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemapherTaskTest {

    @Test
    void run() {
        SemapherTask.run();
        try {


            Thread.sleep(10000);
        }
        catch (Exception ex)
        {

        }
    }
}