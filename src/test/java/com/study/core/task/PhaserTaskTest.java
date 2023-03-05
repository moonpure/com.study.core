package com.study.core.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhaserTaskTest {

    @Test
    void phaserLatch() {
        PhaserTask.phaserLatch();
    }
    @Test
    void runBarrier() {
        PhaserTask.runBarrier();
    }
    @Test
    void phaserRegister()
    {
        PhaserTask.phaserRegister();

    }
}