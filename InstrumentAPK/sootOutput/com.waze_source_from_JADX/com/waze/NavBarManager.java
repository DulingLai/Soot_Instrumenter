package com.waze;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.main.navigate.NavigationItem;
import com.waze.navbar.InstructionGeometry;
import com.waze.navbar.NavBar;

public class NavBarManager {
    private boolean bIsAlertNavBar = false;
    private String bottomDist;
    private String bottomEta;
    private String bottomTime;
    private int currExit;
    private int currFollowers;
    private int currInst;
    private int distMeters;
    private String distUnit;
    private String distance;
    private int eta_minutes;
    private boolean isFollowCarpool;
    private boolean isOffline;
    private LayoutManager layoutManager;
    private boolean mDriveOnLeft;
    private NavigationUpdateListener mNavigationUpdateListener;
    private boolean mNightSkin;
    private int maxFollowers;
    private int mode;
    private NavBar navBar;
    private String nextDist;
    private int nextExit;
    private int nextInst;
    private String street;

    class C12421 implements Runnable {
        C12421() throws  {
        }

        public void run() throws  {
            NavBarManager.this.showNavigationResultNTV();
        }
    }

    class C12443 implements Runnable {
        C12443() throws  {
        }

        public void run() throws  {
            NavBarManager.this.showNavBar();
        }
    }

    static class C12465 implements Runnable {
        C12465() throws  {
        }

        public void run() throws  {
            if (NativeManager.getInstance() != null && NativeManager.getInstance().getNavBarManager() != null) {
                NativeManager.getInstance().getNavBarManager().updatePowerSavingConditionsNTV();
            }
        }
    }

    class C12487 implements Runnable {
        C12487() throws  {
        }

        public void run() throws  {
            if (NavBarManager.this.navBar != null) {
                NavBarManager.this.navBar.showNearingDestination();
            }
        }
    }

    class C12498 implements Runnable {
        C12498() throws  {
        }

        public void run() throws  {
            if (NavBarManager.this.navBar != null) {
                NavBarManager.this.navBar.hideNearingDestination(false);
            }
        }
    }

    class C12509 implements Runnable {
        C12509() throws  {
        }

        public void run() throws  {
            if (NavBarManager.this.navBar != null) {
                NavBarManager.this.navBar.setSkin(NavBarManager.this.mNightSkin);
            }
        }
    }

    public static class DoublePosition implements Parcelable {
        public static final Creator<DoublePosition> CREATOR = new C12511();
        public double lat;
        public double lon;

        static class C12511 implements Creator<DoublePosition> {
            C12511() throws  {
            }

            public DoublePosition createFromParcel(Parcel $r1) throws  {
                return new DoublePosition($r1);
            }

            public DoublePosition[] newArray(int $i0) throws  {
                return new DoublePosition[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        protected DoublePosition(Parcel $r1) throws  {
            this.lon = $r1.readDouble();
            this.lat = $r1.readDouble();
        }

        public void writeToParcel(Parcel $r1, int flags) throws  {
            $r1.writeDouble(this.lon);
            $r1.writeDouble(this.lat);
        }
    }

    public interface NavigationListListener {
        void onComplete(NavigationItem[] navigationItemArr) throws ;
    }

    public interface NavigationUpdateListener {
        void onCurrentDistanceChanged(String str, String str2, int i) throws ;

        void onCurrentInstructionChanged(int i) throws ;

        void onDistanceStringChanged(String str) throws ;

        void onEtaStringChanged(String str, int i) throws ;

        void onNavigationStateChanged(boolean z) throws ;

        void onNextInstructionChanged(int i) throws ;

        void onStreetNameChanged(String str) throws ;

        void onTimeStringChanged(String str) throws ;
    }

    private native void InitNavBarManagerNTV() throws ;

    private native NavigationItem[] getNavigationItemsNTV() throws ;

    private native void showNavigationResultNTV() throws ;

    private native void updatePowerSavingConditionsNTV() throws ;

    public native boolean isNearingDestNTV() throws ;

    public NavBarManager(LayoutManager $r1) throws  {
        this.layoutManager = $r1;
        InitNavBarManagerNTV();
    }

    public void showNavigationResult() throws  {
        NativeManager.Post(new C12421());
    }

    public void getNavigationItems(final NavigationListListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private NavigationItem[] items;

            public void event() throws  {
                try {
                    this.items = NavBarManager.this.getNavigationItemsNTV();
                } catch (Exception e) {
                    this.items = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.items);
            }
        });
    }

    public void show() throws  {
        AppService.Post(new C12443());
    }

    public void showNavBar() throws  {
        if (this.navBar != null) {
            this.navBar.show();
        }
    }

    public void initialize(int $i0, int $i1) throws  {
        boolean $z1;
        boolean $z0 = true;
        if ($i0 != 0) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        this.mDriveOnLeft = $z1;
        if ($i1 == 0) {
            $z0 = false;
        }
        this.mNightSkin = $z0;
        this.bottomTime = null;
        this.bottomEta = null;
        this.bottomDist = null;
        this.street = null;
        this.nextDist = null;
        this.distance = null;
        this.distUnit = null;
        this.eta_minutes = -1;
        this.distMeters = -1;
        this.nextExit = -1;
        this.currExit = -1;
        this.nextInst = -1;
        this.currInst = -1;
        this.currFollowers = -1;
        this.maxFollowers = -1;
        this.isFollowCarpool = false;
        this.isOffline = false;
    }

    public int getNavBarHeight() throws  {
        return this.navBar == null ? 0 : this.navBar.getNavBarHeight();
    }

    public boolean hasTapOccurredInPeriod(long $l0) throws  {
        return ActivityBase.getTimeSinceLastTouch() <= $l0;
    }

    public boolean isShowingAlerter() throws  {
        return (this.navBar != null && this.navBar.isSubViewDisplayed()) || (this.layoutManager != null && this.layoutManager.isAlerterShown());
    }

    public boolean isChargingPhone() throws  {
        return PowerManager.Instance().getIsCharging() == 1;
    }

    public void onPowerSavingNotificationDismissed() throws  {
        updatePowerSavingConditions();
    }

    public void dimScreen(final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                boolean $z0;
                Log.i("GilTestPower", "Dimming screen to " + $i0 + "%");
                float $f0 = $i0 > 0 ? ((float) $i0) / 100.0f : -1.0f;
                if ($f0 > 0.0f) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                ActivityBase.setScreenIsDimmed($z0);
                Window $r4 = AppService.getActiveActivity().getWindow();
                LayoutParams $r5 = $r4.getAttributes();
                $r5.screenBrightness = $f0;
                $r4.setAttributes($r5);
            }
        });
    }

    public static void updatePowerSavingConditions() throws  {
        NativeManager.Post(new C12465());
    }

    public void set_mode(final int $i0) throws  {
        this.mode = $i0;
        AppService.Post(new Runnable() {
            public void run() throws  {
                boolean $z0 = true;
                if ($i0 != 0) {
                    if (NavBarManager.this.navBar == null && NavBarManager.this.layoutManager != null) {
                        NavBarManager.this.navBar = NavBarManager.this.layoutManager.createNavBar();
                        NavBarManager.this.navBar.setDrivingDirection(NavBarManager.this.mDriveOnLeft);
                        NavBarManager.this.navBar.setSkin(NavBarManager.this.mNightSkin);
                    }
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.clear();
                    }
                } else {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.hide();
                    }
                    if (NavBarManager.this.layoutManager != null) {
                        NavBarManager.this.layoutManager.hideCandidateRideForRoute();
                    }
                }
                if (NavBarManager.this.layoutManager != null) {
                    boolean $z1;
                    LayoutManager $r3 = NavBarManager.this.layoutManager;
                    if ($i0 == 1) {
                        $z1 = true;
                    } else {
                        $z1 = false;
                    }
                    $r3.setIsNavigating($z1);
                }
                if (NavBarManager.this.mNavigationUpdateListener != null) {
                    NavigationUpdateListener $r5 = NavBarManager.this.mNavigationUpdateListener;
                    if ($i0 == 0) {
                        $z0 = false;
                    }
                    $r5.onNavigationStateChanged($z0);
                }
            }
        });
    }

    public void AlertNavBar() throws  {
    }

    public void setWaypoint(boolean value) throws  {
    }

    public void showNearingDestination() throws  {
        Log.i("NearingDest", "About to show nearing dest from nav bar manager");
        AppService.Post(new C12487());
    }

    public void hideNearingDestination() throws  {
        Log.i("NearingDest", "About to hide nearing dest from nav bar manager");
        AppService.Post(new C12498());
    }

    public void set_skin(int $i0) throws  {
        boolean $z0;
        if ($i0 != 0) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        this.mNightSkin = $z0;
        AppService.Post(new C12509());
    }

    public void set_instruction(final int $i0) throws  {
        if (this.currInst != $i0) {
            this.currInst = $i0;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.setInstruction($i0);
                    }
                    if (NavBarManager.this.mNavigationUpdateListener != null) {
                        NavBarManager.this.mNavigationUpdateListener.onCurrentInstructionChanged($i0);
                    }
                }
            });
        }
    }

    public void set_next_instruction(final int $i0) throws  {
        if (this.nextInst != $i0) {
            this.nextInst = $i0;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.setNextInstruction($i0);
                    }
                    if (NavBarManager.this.mNavigationUpdateListener != null) {
                        NavBarManager.this.mNavigationUpdateListener.onNextInstructionChanged($i0);
                    }
                }
            });
        }
    }

    public void set_exit(final int $i0) throws  {
        if (this.currExit != $i0) {
            this.currExit = $i0;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.setExit($i0);
                    }
                }
            });
        }
    }

    public void set_next_exit(final int $i0) throws  {
        if (this.nextExit != $i0) {
            this.nextExit = $i0;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.setNextExit($i0);
                    }
                }
            });
        }
    }

    public void set_distance(final String $r1, final String $r2, final int $i0) throws  {
        if ($r1 == null || !$r1.equals(this.distance) || $r2 == null || !$r2.equals(this.distUnit)) {
            this.distance = $r1;
            this.distUnit = $r2;
            this.distMeters = $i0;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.setDistance($r1, $r2, $i0);
                    }
                    if (NavBarManager.this.mNavigationUpdateListener != null) {
                        NavBarManager.this.mNavigationUpdateListener.onCurrentDistanceChanged($r1, $r2, $i0);
                    }
                }
            });
        }
    }

    public void set_next_distance(final String $r1) throws  {
        if (!$r1.equals(this.nextDist)) {
            this.nextDist = $r1;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.setNextDistance($r1);
                    }
                }
            });
        }
    }

    public void set_street(final String $r1) throws  {
        if (!$r1.equals(this.street)) {
            this.street = $r1;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        NavBarManager.this.navBar.setStreet($r1);
                    }
                    if (NavBarManager.this.mNavigationUpdateListener != null) {
                        NavBarManager.this.mNavigationUpdateListener.onStreetNameChanged($r1);
                    }
                }
            });
        }
    }

    public void set_dist_str(final String $r1) throws  {
        if (!$r1.equals(this.bottomDist)) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null && NavBarManager.this.navBar.setDistStr($r1)) {
                        NavBarManager.this.bottomDist = $r1;
                    }
                    if (NavBarManager.this.mNavigationUpdateListener != null) {
                        NavBarManager.this.mNavigationUpdateListener.onDistanceStringChanged($r1);
                    }
                }
            });
        }
    }

    public void set_eta_str(final String $r1, final int $i0) throws  {
        if (!$r1.equals(this.bottomTime)) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        if (NavBarManager.this.navBar.setTimeStr($r1, $i0)) {
                            NavBarManager.this.bottomTime = $r1;
                        }
                        NavBarManager.this.eta_minutes = $i0;
                    }
                    if (NavBarManager.this.mNavigationUpdateListener != null) {
                        NavBarManager.this.mNavigationUpdateListener.onEtaStringChanged($r1, $i0);
                    }
                }
            });
        }
    }

    public void set_time_str(final String $r1) throws  {
        if (!$r1.equals(this.bottomEta)) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar != null) {
                        if (NavBarManager.this.isOffline) {
                            NavBarManager.this.bottomEta = $r1;
                        } else if (NavBarManager.this.navBar.setEtaStr($r1)) {
                            NavBarManager.this.bottomEta = $r1;
                        }
                    }
                    if (NavBarManager.this.mNavigationUpdateListener != null) {
                        NavBarManager.this.mNavigationUpdateListener.onTimeStringChanged($r1);
                    }
                }
            });
        }
    }

    public void set_offline(final boolean $z0) throws  {
        if (this.isOffline != $z0) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (NavBarManager.this.navBar == null) {
                        return;
                    }
                    if ($z0) {
                        if (NavBarManager.this.navBar.setOffline($z0)) {
                            NavBarManager.this.isOffline = $z0;
                        }
                    } else if (NavBarManager.this.navBar.setEtaStr(NavBarManager.this.bottomEta)) {
                        NavBarManager.this.isOffline = $z0;
                    }
                }
            });
        }
    }

    public void set_route_outline(final DoublePosition[] $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (NavBarManager.this.navBar != null) {
                    NavBarManager.this.navBar.set_outline_position($r1);
                }
            }
        });
    }

    public void set_followers_num(final int $i0, final int $i1, final boolean $z0) throws  {
        if (this.maxFollowers != $i1) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NavBarManager.this.currFollowers = $i0;
                    NavBarManager.this.maxFollowers = $i1;
                    NavBarManager.this.isFollowCarpool = $z0;
                }
            });
        }
    }

    public void setInstructionGeometry(final InstructionGeometry $r1, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (NavBarManager.this.navBar != null) {
                    NavBarManager.this.navBar.setInstructionGeometry($r1, $i0);
                }
            }
        });
    }

    public void setNextInstructionGeometry(final InstructionGeometry $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (NavBarManager.this.navBar != null) {
                    NavBarManager.this.navBar.setNextInstructionGeometry($r1);
                }
            }
        });
    }

    public void restore(LayoutManager $r1) throws  {
        this.layoutManager = $r1;
        this.navBar = null;
        if (this.mode != 0 && $r1 != null) {
            this.navBar = $r1.createNavBar();
            this.navBar.setDrivingDirection(this.mDriveOnLeft);
            this.navBar.setSkin(this.mNightSkin);
            this.navBar.clear();
            if (this.currInst != -1) {
                this.navBar.setInstruction(this.currInst);
            }
            if (this.nextInst != -1) {
                this.navBar.setNextInstruction(this.nextInst);
            }
            if (this.currExit != -1) {
                this.navBar.setExit(this.currExit);
            }
            if (this.nextExit != -1) {
                this.navBar.setNextExit(this.nextExit);
            }
            this.navBar.setDistance(this.distance, this.distUnit, this.distMeters);
            this.navBar.setNextDistance(this.nextDist);
            this.navBar.setStreet(this.street);
            this.navBar.setDistStr(this.bottomDist);
            this.navBar.setTimeStr(this.bottomTime, this.eta_minutes);
            this.navBar.setEtaStr(this.bottomEta);
            this.navBar.setOffline(this.isOffline);
            show();
        }
    }

    public void sendLatest() throws  {
        if (this.navBar != null) {
            this.navBar.instructionSent = false;
            this.navBar.setDistance(this.distance, this.distUnit, this.distMeters);
            this.navBar.setDistStr(this.bottomDist);
            this.navBar.setTimeStr(this.bottomTime, this.eta_minutes);
        }
    }

    public void setNavigationUpdateListener(NavigationUpdateListener $r1) throws  {
        this.mNavigationUpdateListener = $r1;
    }

    public NavBar getNavBar() throws  {
        return this.navBar;
    }

    public boolean getDriveOnLeft() throws  {
        return this.mDriveOnLeft;
    }

    public void onOrientationChanged(int orientation) throws  {
        if (this.navBar != null) {
            this.navBar.onOrientationChanged();
        }
    }
}
