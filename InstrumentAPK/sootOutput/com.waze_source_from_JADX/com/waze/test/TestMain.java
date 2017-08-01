package com.waze.test;

import com.waze.AppService;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;

public final class TestMain {
    public static void scheduleStart() {
        NativeManager.registerOnAppStartedEvent(new RunnableExecutor(null) {
            public void event() {
                if (!AppService.isFirstRun()) {
                    TestMain.start();
                }
            }
        });
    }

    public static void start() {
        BgTest.startPeriodicTest();
    }
}
