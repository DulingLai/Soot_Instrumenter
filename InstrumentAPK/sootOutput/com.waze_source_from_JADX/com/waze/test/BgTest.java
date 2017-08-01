package com.waze.test;

import com.waze.AppService;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import java.util.TimerTask;

public final class BgTest {
    private static final long PERIODIC_TEST_COUNT = 100;
    private static final long PERIODIC_TEST_PERIOD = 4000;
    private static final int STATE_BACKGROUND = 1;
    private static final int STATE_BACKGROUND_ROTATED = 3;
    private static final int STATE_FOREGROUND = 0;
    private static final int STATE_FOREGROUND_ROTATED = 2;
    private static final int STATE_ORIENTATION_LANDSCAPE = 5;
    private static final int STATE_ORIENTATION_PORTRAIT = 4;
    public static final int TEST_BACKGROUND_ONLY = 0;
    public static final int TEST_BACKGROUND_ORIENTATION = 1;
    public static final int TEST_ORIENTATION_ONLY = 2;
    private static IPeriodicTester mBgOnlyTest = new C29283();
    private static IPeriodicTester mBgOrientationTest = new C29272();
    private static int mCurrentState = 0;
    private static IPeriodicTester mOrientationOnlyTest = new C29294();
    private static final int mTest = 1;

    public interface IPeriodicTester {
        void tester();
    }

    static class C29272 implements IPeriodicTester {
        C29272() {
        }

        public void tester() {
            switch (BgTest.mCurrentState) {
                case 0:
                    ActivityBase.setGlobalOrientation(0);
                    BgTest.mCurrentState = 2;
                    return;
                case 1:
                    ActivityBase.setGlobalOrientation(1);
                    BgTest.mCurrentState = 3;
                    return;
                case 2:
                    AppService.ShowHomeWindow(-1);
                    BgTest.mCurrentState = 1;
                    return;
                case 3:
                    AppService.ShowMainActivityWindow(0);
                    BgTest.mCurrentState = 0;
                    return;
                default:
                    return;
            }
        }
    }

    static class C29283 implements IPeriodicTester {
        C29283() {
        }

        public void tester() {
            switch (BgTest.mCurrentState) {
                case 0:
                    AppService.ShowHomeWindow(-1);
                    BgTest.mCurrentState = 1;
                    return;
                case 1:
                    AppService.ShowMainActivityWindow(0);
                    BgTest.mCurrentState = 0;
                    return;
                default:
                    return;
            }
        }
    }

    static class C29294 implements IPeriodicTester {
        C29294() {
        }

        public void tester() {
            switch (BgTest.mCurrentState) {
                case 0:
                case 4:
                    ActivityBase.setGlobalOrientation(0);
                    BgTest.mCurrentState = 5;
                    return;
                case 5:
                    ActivityBase.setGlobalOrientation(1);
                    BgTest.mCurrentState = 4;
                    return;
                default:
                    return;
            }
        }
    }

    public static void startPeriodicTest() {
        NativeManager mgr = NativeManager.getInstance();
        IPeriodicTester test = null;
        switch (1) {
            case 0:
                test = mBgOnlyTest;
                break;
            case 1:
                test = mBgOrientationTest;
                break;
            case 2:
                test = mOrientationOnlyTest;
                break;
        }
        final IPeriodicTester finalTest = test;
        mgr.getTimer().schedule(new TimerTask() {
            int mCount = 0;

            public void run() {
                if (((long) this.mCount) >= BgTest.PERIODIC_TEST_COUNT) {
                    cancel();
                    return;
                }
                this.mCount++;
                finalTest.tester();
            }
        }, PERIODIC_TEST_PERIOD, PERIODIC_TEST_PERIOD);
    }
}
