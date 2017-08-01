package com.waze.pioneer;

import android.util.Log;
import com.waze.AppService;
import com.waze.BuildConfig;
import com.waze.Logger;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2MainUnitSpecInfo;
import jp.pioneer.ce.aam2.AAM2Kit_Lib.PioneerKitWrapper;
import jp.pioneer.mbg.pioneerkit.ExtCertifiedInfo;
import jp.pioneer.mbg.pioneerkit.ExtDeviceSpecInfo;
import jp.pioneer.mbg.pioneerkit.ExtLocation;
import jp.pioneer.mbg.pioneerkit.IExtLocationListener;
import jp.pioneer.mbg.pioneerkit.IExtRequiredListener;

public final class PioneerManager implements IExtRequiredListener, IExtLocationListener {
    private static final boolean ENABLED = true;
    private static final boolean LOCATION_ENABLED = true;
    private static final String TAG = "WAZE_PIONEER";
    private static PioneerManager mInstance = null;
    private static boolean mIsOldSdk = true;
    long lastFix = 0;
    private boolean mCertified = false;
    private ExtDeviceSpecInfo mDeviceInfo = null;
    private AAM2MainUnitSpecInfo mNewDeviceInfo = null;
    private boolean mStarted = false;
    private boolean mWaiting = false;

    class C23123 implements Runnable {
        C23123() {
        }

        public void run() {
            PioneerNativeManager.onPioneerConnected();
        }
    }

    class C23145 implements Runnable {
        C23145() {
        }

        public void run() {
            PioneerNativeManager.create();
            PioneerManager.this._notify();
        }
    }

    public static PioneerManager create() {
        if (mInstance == null) {
            mInstance = new PioneerManager();
            Log.d(TAG, "Pioneer app mode enabled - will start when Waze will be fully initialized.");
            NativeManager.registerOnAppStartedEvent(new RunnableExecutor(AppService.getInstance()) {
                public void event() {
                    PioneerManager.start();
                }
            });
        }
        return mInstance;
    }

    public static PioneerManager getInstance() {
        create();
        return mInstance;
    }

    public static void start() {
        Logger.ii("Starting Pioneer manager");
        create();
        mInstance.initNativeLayer();
        PioneerKitWrapper.pStartPioneerKit(AppService.getAppContext(), mInstance);
    }

    public static void stop() {
        if (mInstance != null && mInstance.mStarted) {
            Logger.ii("Stopping Pioneer manager");
            PioneerKitWrapper.pStopPioneerKit(AppService.getAppContext(), mInstance);
            mInstance.mStarted = false;
        }
    }

    public static boolean isActive() {
        return mInstance != null && mInstance.mStarted && mInstance.mCertified;
    }

    public static boolean isExtLocationActive() {
        boolean locationValid;
        if (mInstance == null || mInstance.mDeviceInfo == null || mInstance.mDeviceInfo.getLocationDevice() == 0) {
            locationValid = false;
        } else {
            locationValid = true;
        }
        if (mInstance != null && mInstance.mStarted && locationValid) {
            return true;
        }
        return false;
    }

    public void onReceiveParkingInfo(boolean state) {
        Log.d(TAG, "Received parking event: " + state);
    }

    public void onReceiveParkingSwitch(boolean b) {
    }

    public void onReceiveDriveStopping(boolean b) {
    }

    public void onStartAdvancedAppMode(ExtDeviceSpecInfo info) {
        Log.d(TAG, "Received start advanced app mode event. Pointers #: " + info.getPointerNum() + ". Location device: " + info.getLocationDevice());
        this.mStarted = true;
        this.mDeviceInfo = info;
        mIsOldSdk = true;
        AppService.getMainActivity().cancelSplash();
        ActivityBase.setGlobalOrientation(0);
        onPioneerConnected();
        if (this.mDeviceInfo.getLocationDevice() != 0) {
            Log.d(TAG, "Taking location from Pioneer. Current device: " + this.mDeviceInfo.getLocationDevice());
            NativeManager mgr = NativeManager.getInstance();
            mgr.stopLocation();
            mgr.setGpsSourceName("PioneerV1");
            PioneerKitWrapper.pRegisterLocationListener(mInstance);
            return;
        }
        Log.w(TAG, "External location is not ready or disabled. Device: " + (this.mDeviceInfo != null ? Integer.valueOf(this.mDeviceInfo.getLocationDevice()) : "Unavailable"));
    }

    public void onStopAdvancedAppMode() {
        Log.d(TAG, "Received stop advanced app mode event.");
        NativeManager mgr = NativeManager.getInstance();
        if (!NativeManager.isShuttingDown()) {
            mgr.setGpsSourceName(null);
            mgr.startLocation();
            ActivityBase.setGlobalOrientation(2);
        }
        this.mStarted = false;
    }

    public ExtCertifiedInfo onRequireCertification() {
        Log.d(TAG, "Received require certification event.");
        return new ExtCertifiedInfo("Waze", BuildConfig.APPLICATION_ID, "bbf0f5f5ee2aad9ab239ac9a93085760");
    }

    public void onCertifiedResult(boolean result) {
        Log.d(TAG, "Received certified result event event: " + result);
        this.mCertified = result;
    }

    public void onReceiveLocationInfo(final ExtLocation loc) {
        NativeManager.Post(new Runnable() {
            public void run() {
                NativeLocation ntvLoc = PioneerManager.this.getNativeLocation(loc);
                PioneerNativeManager.getInstance().LocationCallbackNTV(ntvLoc.mGpsTime, ntvLoc.mLatitude, ntvLoc.mLongtitude, ntvLoc.mAltitude, ntvLoc.mSpeed, ntvLoc.mSteering, ntvLoc.mAccuracy);
            }
        });
    }

    private NativeLocation getNativeLocation(ExtLocation aLocation) {
        NativeLocation lNativeLoc = new NativeLocation();
        lNativeLoc.mLongtitude = (int) (aLocation.getLongitude() * 1000000.0d);
        lNativeLoc.mLatitude = (int) (aLocation.getLatitude() * 1000000.0d);
        lNativeLoc.mAltitude = (int) Math.round(aLocation.getAltitude());
        lNativeLoc.mGpsTime = (int) (aLocation.getTime() / 1000);
        lNativeLoc.mGpsTime = (int) (System.currentTimeMillis() / 1000);
        lNativeLoc.mSpeed = (int) (aLocation.getSpeed() * 1000.0f);
        lNativeLoc.mSteering = (int) aLocation.getBearing();
        lNativeLoc.mAccuracy = aLocation.getAccuracy();
        return lNativeLoc;
    }

    private void onPioneerConnected() {
        Log.d(TAG, "Pioneer connected - post event");
        final Runnable event = new C23123();
        if (NativeManager.IsAppStarted()) {
            NativeManager.Post(event);
        } else {
            NativeManager.registerOnAppStartedEvent(new RunnableExecutor(AppService.getInstance()) {
                public void event() {
                    NativeManager.Post(event);
                }
            });
        }
    }

    private void initNativeLayer() {
        NativeManager.Post(new C23145());
        _wait();
    }

    private synchronized void _wait() {
        while (this.mWaiting) {
            try {
                wait();
            } catch (Exception ex) {
                Logger.ee(TAG, ex);
            }
        }
    }

    private synchronized void _notify() {
        this.mWaiting = false;
        notifyAll();
    }

    private PioneerManager() {
    }
}
