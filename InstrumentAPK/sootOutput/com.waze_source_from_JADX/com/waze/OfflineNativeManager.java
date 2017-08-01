package com.waze;

import android.content.Context;
import android.location.Location;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import com.waze.messages.QuestionData;
import com.waze.navigate.DriveToNativeManager;
import com.waze.push.Alerter;
import com.waze.widget.rt.RealTimeManager;

public final class OfflineNativeManager {
    private static OfflineNativeManager mInstance = null;
    private static boolean mOfflineMode = false;
    private Context mContext;

    private native QuestionData HandleCommandNTV(String str, int i, int i2, int i3, boolean z) throws ;

    private native QuestionData HandleParamsForMsgReadNTV(String str, boolean z) throws ;

    private native QuestionData HandleParamsForPushMsgsNTV(String str, boolean z) throws ;

    private native void InitOfflineNativeManagerNTV() throws ;

    private native void InitOfflineNativeManagerWhileAppIsNotRunningNTV(String str, String str2) throws ;

    private native boolean accessToCalendarAllowedNTV() throws ;

    private native String formatLocationCommandNTV(int i, int i2) throws ;

    private native String getCalendarUpdateProtobufNTV() throws ;

    private native String langGetIntNTV(int i) throws ;

    private native String langGetNTV(String str) throws ;

    public native String getExcludedCalendarsNTV() throws ;

    public native boolean isParkingDetectionExperimentEnabledNTV() throws ;

    public native int mathDistanceNTV(int i, int i2, int i3, int i4) throws ;

    public static OfflineNativeManager getInstance(Context $r0) throws  {
        mOfflineMode = true;
        if (mInstance == null) {
            mInstance = new OfflineNativeManager($r0);
        }
        return mInstance;
    }

    public static OfflineNativeManager existingInstance() throws  {
        return mInstance;
    }

    public static boolean isOfflineMode() throws  {
        return mOfflineMode;
    }

    public static Context getContext() throws  {
        if (mInstance != null) {
            return mInstance.mContext;
        }
        return null;
    }

    public String getLanguageString(int $i0) throws  {
        return langGetIntNTV($i0);
    }

    public String getLanguageString(String $r1) throws  {
        return langGetNTV($r1);
    }

    private OfflineNativeManager(Context $r1) throws  {
        this.mContext = $r1;
        if (!NativeManager.IsAppStarted()) {
            NativeManager.LoadNativeLib();
            InitOfflineNativeManagerWhileAppIsNotRunningNTV(this.mContext.getFilesDir().getParent() + "/", AppService.getExternalStoragePath($r1) + "/waze/");
        }
        InitOfflineNativeManagerNTV();
    }

    public QuestionData HandleCommandForPush(String $r1, int $i0, int $i1, int $i2) throws  {
        return HandleCommandNTV($r1, $i0, $i1, $i2, AppService.IsAppRunning());
    }

    public void updateCalendarEvents() throws  {
        DriveToNativeManager.getInstance();
        String $r2 = getCalendarUpdateProtobufNTV();
        if ($r2 != null) {
            RealTimeManager.getInstance().sendOfflineCommand(this.mContext, $r2);
        }
    }

    public void sendOfflineLocation() throws  {
        Alerter.instance();
        Location $r2 = Alerter.getBestLocation(this.mContext);
        if ($r2 != null) {
            String $r3 = formatLocationCommandNTV((int) ($r2.getLatitude() * 1000000.0d), (int) ($r2.getLongitude() * 1000000.0d));
            if ($r3 != null) {
                Logger.m38e("OfflineNativeManager.sendOfflineLocation - sending " + $r3);
                RealTimeManager.getInstance().sendOfflineCommand(this.mContext, $r3);
                return;
            }
            Logger.m36d("OfflineNativeManager.sendOfflineLocation - command is null");
            return;
        }
        Logger.m38e("OfflineNativeManager.sendOfflineLocation - location is null");
    }

    private String getInstallationUUID() throws  {
        return AppUUID.getInstallationUUID(this.mContext);
    }

    public boolean calendarAccessEnabled() throws  {
        return ContextCompat.checkSelfPermission(this.mContext, "android.permission.READ_CALENDAR") == 0;
    }

    public String getPushInstallationUUID() throws  {
        return Secure.getString(this.mContext.getContentResolver(), "android_id");
    }

    public boolean accessToCalendarAllowed() throws  {
        return ContextCompat.checkSelfPermission(this.mContext, "android.permission.READ_CALENDAR") != 0 ? false : accessToCalendarAllowedNTV();
    }

    public QuestionData HandleParamsForPushMsgs(String $r1) throws  {
        return HandleParamsForPushMsgsNTV($r1, AppService.IsAppRunning());
    }

    public QuestionData HandleParamsForMsgRead(String $r1) throws  {
        return HandleParamsForMsgReadNTV($r1, AppService.IsAppRunning());
    }
}
