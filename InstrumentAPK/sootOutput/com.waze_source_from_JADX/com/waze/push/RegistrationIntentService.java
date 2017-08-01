package com.waze.push;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.MainActivity;

public class RegistrationIntentService extends IntentService {
    private static final String TAG = "Waze";
    private static boolean mTokenReceived = false;
    protected boolean mLoginDone = false;

    public RegistrationIntentService() {
        super(TAG);
    }

    protected void onHandleIntent(Intent intent) {
        try {
            String token = InstanceID.getInstance(this).getToken("1005457359603", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Logger.i("RegistrationIntentService: GCM Registration Token: " + token);
            mTokenReceived = true;
            setRegistrationId(getApplicationContext(), token);
            MainActivity mainActivity = AppService.getMainActivity();
            if (mainActivity != null) {
                mainActivity.NotifyNativeManagerWithGcmToken();
            }
        } catch (Exception e) {
            Logger.e("RegistrationIntentService: GCM: An exception occurred in RegistrationIntentService while trying to handle intent", e);
        }
    }

    private static synchronized String setRegistrationId(Context context, String token) {
        String oldRegistrationId;
        synchronized (RegistrationIntentService.class) {
            Logger.i("RegistrationIntentService: Setting GCM Registration Token in perfs: " + token);
            SharedPreferences prefs = getPrefs(context);
            oldRegistrationId = prefs.getString("regId", "");
            int appVersion = getAppVersion(context);
            Editor editor = prefs.edit();
            editor.putString("regId", token);
            editor.putInt("appVersion", appVersion);
            editor.commit();
        }
        return oldRegistrationId;
    }

    public static synchronized String getRegistrationId(Context context) {
        String registrationId;
        synchronized (RegistrationIntentService.class) {
            SharedPreferences prefs = getPrefs(context);
            registrationId = prefs.getString("regId", "");
            Logger.i("RegistrationIntentService: Requesting GCM Registration Token in perfs: " + registrationId + "; toeknSet=" + mTokenReceived);
            int oldVersion = prefs.getInt("appVersion", Integer.MIN_VALUE);
            int newVersion = getAppVersion(context);
            if (!(oldVersion == Integer.MIN_VALUE || oldVersion == newVersion)) {
                Logger.d_("Push Service", "RegistrationIntentService: GCM: App version changed from " + oldVersion + " to " + newVersion + "; resetting registration id");
                setRegistrationId(context, "");
                registrationId = "";
            }
        }
        return registrationId;
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("com.waze.push", 0);
    }

    private static int getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("RegistrationIntentService: GCM Could not get package name: " + e);
        }
    }
}
