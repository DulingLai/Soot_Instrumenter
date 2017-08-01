package com.waze.view.bottom;

import com.waze.AppService;
import com.waze.NativeManager;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.view.navbar.BottomBar;

public class BottomNotification {
    public static final int NEARBY_MESSAGE_TYPE_POINTS = 3;
    public static final int NEARBY_MESSAGE_TYPE_REPORTS = 2;
    public static final int NEARBY_MESSAGE_TYPE_UNKNOWN = 0;
    public static final int NEARBY_MESSAGE_TYPE_WAZERS = 1;
    private static BottomNotification s_inst;
    private BottomBar mBottomBar;
    private boolean mInitialized = false;

    class C29713 implements Runnable {
        C29713() {
        }

        public void run() {
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().getLayoutMgr().displayBottomPrivacyMessage();
            }
        }
    }

    class C29735 implements Runnable {
        C29735() {
        }

        public void run() {
            BottomNotification.this.mBottomBar.closeNotif();
        }
    }

    private native void initNTV();

    public static BottomNotification getInstance() {
        if (s_inst == null) {
            s_inst = new BottomNotification();
        }
        return s_inst;
    }

    private BottomNotification() {
    }

    public void init() {
        if (!this.mInitialized) {
            initNTV();
            this.mInitialized = true;
        }
    }

    public void setBottomBar(BottomBar bar) {
        this.mBottomBar = bar;
    }

    public void postMessage(final String message, final int timeout) {
        AppService.Post(new Runnable() {
            public void run() {
                if (BottomNotification.this.mBottomBar != null) {
                    BottomNotification.this.mBottomBar.setShortMessage(message, timeout);
                }
            }
        });
    }

    public void postNearbyMessage(String message, String messageBody, int type, int timeout) {
        final boolean isRandomUser = MyWazeNativeManager.getInstance().isGuestUserNTV();
        final boolean isFoursquareEnabled = MyWazeNativeManager.getInstance().FoursquareEnabledNTV();
        final boolean isClosureEnabled = NativeManager.getInstance().isClosureEnabledNTV();
        final String str = message;
        final String str2 = messageBody;
        final int i = type;
        final int i2 = timeout;
        AppService.Post(new Runnable() {
            public void run() {
                if (BottomNotification.this.mBottomBar != null) {
                    BottomNotification.this.mBottomBar.setNearByMessage(str, str2, i, i2, isRandomUser, isFoursquareEnabled, isClosureEnabled);
                }
            }
        });
    }

    public void displayBottomPrivacyMessage() {
        AppService.Post(new C29713());
    }

    public void postLongMessagePoints(String title, String message, int points, int timeout) {
        final String str = title;
        final String str2 = message;
        final int i = points;
        final int i2 = timeout;
        AppService.Post(new Runnable() {
            public void run() {
                if (BottomNotification.this.mBottomBar != null) {
                    BottomNotification.this.mBottomBar.setLongMessagePoints(str, str2, i, i2);
                }
            }
        });
    }

    public void closeBottom() {
        AppService.Post(new C29735());
    }

    public void setWalkToCarMinutes(final int minutes) {
        AppService.Post(new Runnable() {
            public void run() {
                BottomNotification.this.mBottomBar.setParkingTime(minutes);
            }
        });
    }
}
