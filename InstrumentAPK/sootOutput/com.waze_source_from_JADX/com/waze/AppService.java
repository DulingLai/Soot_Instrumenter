package com.waze;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.waze.Logger.LogCatMonitor;
import com.waze.NativeManager.UIEvent;
import com.waze.audioextension.spotify.SpotifyManager;
import com.waze.auto.AutoUtils;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ShutdownManager;
import com.waze.map.MapViewWrapper;
import com.waze.pioneer.PioneerManager;
import com.waze.weblink.WeblinkManager;
import java.io.File;
import java.util.concurrent.Executor;

public final class AppService extends Service implements Executor {
    public static final String AMAZON_MARKET_APP_LINK = "http://www.amazon.com/gp/mas/dl/android?p=com.waze";
    public static final boolean APPLICATION_TEST_MODE = false;
    public static final String GOOGLE_MARKET_APP_LINK = "market://details?id=com.waze";
    private static final String LOG_TAG = "WAZE_Service";
    public static final String MARKET_APP_LINK = "market://details?id=com.waze";
    public static final int MSG_OPEN_BROWSER_FOR_UPGRADE = 4;
    public static final int MSG_RESTART_APPLICATION = 5;
    public static final int MSG_SHOW_CAMERA_PREVIEW = 3;
    public static final int MSG_SHOW_CONTACTS = 6;
    public static final int MSG_SHOW_DIALER_SCREEN = 2;
    public static final int MSG_SHOW_HOME_SCREEN = 1;
    public static final int MSG_SHOW_MAIN_ACTIVITY = 0;
    public static final int POLICE_ENABLE = 1;
    public static final String SHOW_CONTACTS_URI = "content://contacts/people/";
    private static final boolean WAZE_LOGCAT_MONITOR_ENABLED = false;
    public static final int WAZE_NOTIFICATION_GENERIC = 2;
    private static final int WAZE_NOTIFICATION_RUNNING = 1;
    public static final String WAZE_UPGRADE_URL = "m.waze.com";
    private static MapViewWrapper activeMapViewWrapper = null;
    private static long createdTime;
    private static ActivityBase mActiveActivity = null;
    private static ConnEventReceiver mConnEventReceiver = null;
    private static Notification mCurrentNotification;
    private static String mDirectoryPath = null;
    private static boolean mFirstRun = false;
    private static AppService mInstance = null;
    public static LogCatMonitor mLogCatMonitor = null;
    private static volatile MainActivity mMainActivity;
    private static NativeManager mNativeManager = null;
    private static PowerManager mPowerManager;
    private static ActivityBase mPrevActivity = null;
    private static ServiceMsgDispatcher mServiceMsgDispatcher = null;
    private static String mUrl = null;
    private EndCallListener mEndCallListener;
    private final RunnableExecutor mOnStartApp = new RunnableExecutor(this) {
        public void event() throws  {
            if (NativeManager.getInstance().isDebug()) {
                NativeManager.getInstance().SetWebViewDebug(true);
            }
            AppService.mConnEventReceiver = new ConnEventReceiver();
            AppService.getAppContext().registerReceiver(AppService.mConnEventReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    };
    private StandbyManager mStandbyManager;

    static class C10972 implements Runnable {
        C10972() throws  {
        }

        public void run() throws  {
            AppService.mMainActivity.setContentView(AppService.mMainActivity.getLayoutMgr().getMainLayout());
        }
    }

    private class EndCallListener extends PhoneStateListener {

        class C10991 implements Runnable {
            C10991() throws  {
            }

            public void run() throws  {
                Intent $r1 = new Intent(AppService.getAppContext(), FreeMapAppActivity.class);
                $r1.setAction("android.intent.action.MAIN");
                $r1.addCategory("android.intent.category.LAUNCHER");
                $r1.setFlags(268435456);
                Log.i("EndCallListener", "Starting free map activity");
                AppService.this.startActivity($r1);
            }
        }

        private EndCallListener() throws  {
        }

        public void onCallStateChanged(int $i0, String incomingNumber) throws  {
            Log.i("EndCallListener", "onCallStateChanged: " + $i0);
            if (1 == $i0) {
            }
            if (2 == $i0) {
                NativeManager $r3 = NativeManager.getInstance();
                boolean $z0 = $r3.getInitializedStatus();
                String str = "EndCallListener";
                Log.i(str, "isInitialized = " + $z0 + ", isNavigating = " + $r3.isNavigatingNTV());
                if ($r3.getInitializedStatus() && $r3.isNavigatingNTV()) {
                    int $i1 = $r3.getReturnToWazeFromPhoneTimeoutNTV();
                    Log.i("EndCallListener", "Timeout value = " + $i1);
                    if ($i1 >= 0) {
                        AppService.getServiceMsgDispatcher().postDelayed(new C10991(), (long) ($i1 * 1000));
                    }
                }
            }
            if ($i0 != 0) {
            }
        }
    }

    private static final class ServiceMsgDispatcher extends Handler {
        private ServiceMsgDispatcher() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 0:
                    AppService.ShowMainActivityHandler();
                    return;
                case 1:
                    AppService.ShowHomeHandler();
                    return;
                case 2:
                    AppService.ShowDialerHandler();
                    return;
                case 3:
                    AppService.ShowCameraPreviewHandler();
                    return;
                case 4:
                    AppService.OpenBrowserForUpgradeHandler();
                    return;
                case 5:
                    AppService.RestartApplicationHandler();
                    return;
                case 6:
                    AppService.ShowContactsHandler();
                    return;
                default:
                    return;
            }
        }
    }

    public IBinder onBind(Intent intent) throws  {
        return null;
    }

    public void onCreate() throws  {
        super.onCreate();
        WazeApplication.startSW.startDelta("Service onCreate", false);
        if (mMainActivity == null) {
            Log.e(LOG_TAG, "AppService.onCreate main activity is null");
            stopSelf();
            return;
        }
        mInstance = this;
        if (mCurrentNotification != null) {
            Log.d(LOG_TAG, "Setting the service to be foreground");
            mCurrentNotification = null;
        }
        mPowerManager = PowerManager.Create(getAppContext());
        getAppContext().registerReceiver(mPowerManager, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        PioneerManager.create();
        WeblinkManager.getInstance().initialize(getApplication());
        NativeManager.onServiceCreated();
        this.mStandbyManager = StandbyManager.start(getAppContext());
        RunnableExecutor $r10;
        if (NativeManager.IsAppStarted()) {
            $r10 = this.mOnStartApp;
            $r10.event();
        } else {
            $r10 = this.mOnStartApp;
            NativeManager.registerOnAppStartedEvent($r10);
        }
        startLogCatMonitor();
        Log.i(LOG_TAG, "Service Created. Instance: " + this);
        createdTime = SystemClock.elapsedRealtime();
    }

    public static long timeSinceCreated() throws  {
        return SystemClock.elapsedRealtime() - createdTime;
    }

    private void attachEndCallListener() throws  {
        if (this.mEndCallListener == null) {
            Log.i("AppService", "Registering phone listener");
            this.mEndCallListener = new EndCallListener();
            ((TelephonyManager) getSystemService("phone")).listen(this.mEndCallListener, 32);
        }
    }

    public void resumeWeblink() throws  {
        WeblinkManager.getInstance().requestActivation();
    }

    public void onTaskRemoved(Intent $r1) throws  {
        if (NativeManager.getInstance() != null) {
            NativeManager.getInstance().StopWaze();
            Log.i("AppService", "App was killed, stopping Waze");
        }
        super.onTaskRemoved($r1);
    }

    public static String getExternalStoragePath(Context $r0) throws  {
        if (mDirectoryPath != null) {
            return mDirectoryPath;
        }
        String $r2;
        if ($r0 == null) {
            $r2 = getAppContext().getFilesDir().getParent() + "/";
        } else {
            $r2 = $r0.getFilesDir().getParent() + "/";
        }
        File $r1 = new File($r2 + "waze");
        if (VERSION.SDK_INT >= 23) {
            if (!$r1.exists()) {
                if (IsAppRunning()) {
                    ResManager.CopyFilesToInternalMemory();
                } else if (ResManager.mUpgradeRun == (byte) 0) {
                    mDirectoryPath = Environment.getExternalStorageDirectory().getPath();
                    return mDirectoryPath;
                }
            }
            if ($r0 == null) {
                mDirectoryPath = getAppContext().getFilesDir().getParent();
            } else {
                mDirectoryPath = $r0.getFilesDir().getParent();
            }
        } else {
            mDirectoryPath = Environment.getExternalStorageDirectory().getPath();
        }
        return mDirectoryPath;
    }

    public static boolean isCameraAvailable() throws  {
        return getAppContext().getPackageManager().hasSystemFeature("android.hardware.camera.any");
    }

    public static String getExternalStoragePath() throws  {
        return getExternalStoragePath(null);
    }

    public void startForeground() throws  {
        if (mCurrentNotification != null) {
            startForeground(1, mCurrentNotification);
            return;
        }
        mCurrentNotification = SetNotification();
        startForeground(1, mCurrentNotification);
    }

    public void stopForeground() throws  {
        mCurrentNotification = null;
        stopForeground(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId) throws  {
        Log.i(LOG_TAG, "Start service is called " + mInstance);
        attachEndCallListener();
        return 2;
    }

    public void onDestroy() throws  {
        super.onDestroy();
        if (this.mStandbyManager != null) {
            this.mStandbyManager.stop();
        }
        if (mNativeManager != null) {
            mNativeManager.RestoreSystemSettings();
        }
        Log.w(LOG_TAG, "Service destroy.");
        ShutdownManager.onServiceDestroy();
    }

    public static void start(ActivityBase $r0) throws  {
        if (!IsInitialized()) {
            mNativeManager = NativeManager.getInstance();
            $r0.startService(new Intent($r0, AppService.class));
        }
    }

    public static NativeManager getNativeManager() throws  {
        return mNativeManager;
    }

    public void execute(Runnable $r1) throws  {
        Post($r1);
    }

    public static PowerManager getPowerManager() throws  {
        return mPowerManager;
    }

    @Nullable
    public static MainActivity getMainActivity() throws  {
        return mMainActivity;
    }

    public static boolean IsMainViewReady() throws  {
        return getMainView() != null && getMainView().getMapView().IsReady();
    }

    public static MapViewWrapper getMainView() throws  {
        if (mMainActivity != null) {
            return mMainActivity.getMainView();
        }
        return null;
    }

    public static Context getAppContext() throws  {
        if (mInstance != null) {
            return mInstance.getApplicationContext();
        }
        return mMainActivity != null ? mMainActivity.getApplicationContext() : null;
    }

    public static Resources getAppResources() throws  {
        return mInstance.getResources();
    }

    public static Display getDisplay() throws  {
        return ((WindowManager) getAppContext().getSystemService("window")).getDefaultDisplay();
    }

    public static boolean isFirstRun() throws  {
        return mFirstRun;
    }

    public static void setFirstRun() throws  {
        mFirstRun = true;
    }

    public static String getUrl() throws  {
        return mUrl;
    }

    public static void setUrl(String $r0) throws  {
        mUrl = $r0;
    }

    public static void ShowMainActivityWindow(long $l0) throws  {
        getServiceMsgDispatcher().sendEmptyMessageDelayed(0, $l0);
    }

    public static void RestartApplication() throws  {
        getServiceMsgDispatcher().sendEmptyMessage(5);
    }

    public static void OpenBrowserForUpgrade() throws  {
        getServiceMsgDispatcher().sendEmptyMessage(4);
    }

    public static void OpenBrowser(final String $r0) throws  {
        if (AutoUtils.isAutoMode()) {
            getNativeManager().OpenInternalBrowser("", $r0);
        } else {
            Post(new Runnable() {
                public void run() throws  {
                    try {
                        Intent $r1 = new Intent("android.intent.action.VIEW");
                        String $r2 = $r0;
                        if (!$r2.startsWith("http")) {
                            $r2 = "http://" + $r2;
                        }
                        Uri $r4 = Uri.parse($r2);
                        $r1.setFlags(268435456);
                        $r1.setData($r4);
                        AppService.mInstance.startActivity($r1);
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    public static void Post(Runnable $r0) throws  {
        getServiceMsgDispatcher().post($r0);
    }

    public static void Post(Runnable $r0, long $l0) throws  {
        getServiceMsgDispatcher().postDelayed($r0, $l0);
    }

    public static void Remove(Runnable $r0) throws  {
        getServiceMsgDispatcher().removeCallbacks($r0);
    }

    public static void ShowHomeWindow(long $l0) throws  {
        getServiceMsgDispatcher().sendEmptyMessage(1);
        if ($l0 >= 0) {
            getServiceMsgDispatcher().sendEmptyMessageDelayed(0, $l0);
        }
    }

    public static void ShowContacts() throws  {
        getServiceMsgDispatcher().sendEmptyMessage(6);
    }

    public static void ShowDilerWindow(long $l0) throws  {
        getServiceMsgDispatcher().sendEmptyMessage(2);
        if ($l0 >= 0) {
            getServiceMsgDispatcher().sendEmptyMessageDelayed(0, $l0);
        }
    }

    public static void ShowCameraPreviewWindow() throws  {
        getServiceMsgDispatcher().sendEmptyMessage(3);
    }

    public static void setMainLayout() throws  {
        mMainActivity.runOnUiThread(new C10972());
    }

    public static void setMainActivity(MainActivity $r0) throws  {
        mMainActivity = $r0;
    }

    public static void showActivity(Intent $r0) throws  {
        showActivity($r0, null);
    }

    public static void showActivity(Intent $r0, Activity $r2) throws  {
        if ($r0 != null) {
            if ($r2 == null) {
                $r2 = getActiveActivity();
            }
            if ($r2 != null) {
                $r2.startActivity($r0);
            }
        }
    }

    public static void setActiveActivity(ActivityBase $r0) throws  {
        Log.d(Logger.TAG, "Previous active activity: " + (mActiveActivity == null ? "null" : mActiveActivity.getClass().toString()));
        if (!(mActiveActivity == null || mActiveActivity == $r0)) {
            mActiveActivity.removeDialogs();
        }
        mPrevActivity = mActiveActivity;
        mActiveActivity = $r0;
        Log.d(Logger.TAG, "Current active activity: " + mActiveActivity.getClass().toString());
    }

    public static ActivityBase getActiveActivity() throws  {
        return mActiveActivity;
    }

    public static Activity getPrevActivity() throws  {
        return mPrevActivity;
    }

    public static AppService getInstance() throws  {
        return mInstance;
    }

    public static boolean IsInitialized() throws  {
        return mInstance != null;
    }

    public static boolean IsAppRunning() throws  {
        return IsInitialized() && mNativeManager != null && NativeManager.IsAppStarted();
    }

    public void onLowMemory() throws  {
        Log.w("Waze Service", "Low memory warning!!!");
        if (mNativeManager != null) {
            mNativeManager.PostUIMessage(UIEvent.UI_EVENT_LOW_MEMORY);
        }
    }

    public static void ShutDown() throws  {
        if (mInstance != null) {
            PioneerManager.stop();
            WeblinkManager.getInstance().terminate();
            SpotifyManager.getInstance().term();
            Context $r3 = getAppContext();
            if ($r3 != null) {
                if (mConnEventReceiver != null) {
                    $r3.unregisterReceiver(mConnEventReceiver);
                }
                if (mPowerManager != null) {
                    $r3.unregisterReceiver(mPowerManager);
                }
            }
            if (mLogCatMonitor != null) {
                mLogCatMonitor.Destroy();
            }
            ClearNotification();
            mInstance.stopSelf();
        }
    }

    public static boolean isNetworkAvailable() throws  {
        return ((ConnectivityManager) getAppContext().getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    private static void ShowContactsHandler() throws  {
        Intent $r0 = new Intent("android.intent.action.VIEW");
        $r0.setFlags(268435456);
        $r0.setData(Uri.parse(SHOW_CONTACTS_URI));
        mInstance.startActivity($r0);
    }

    private static void RestartApplicationHandler() throws  {
        IntentManager.RequestRestart(getAppContext());
        mNativeManager.ShutDown();
    }

    private static void OpenBrowserForUpgradeHandler() throws  {
        if (mInstance != null) {
            Intent $r0 = new Intent("android.intent.action.VIEW");
            Uri $r1 = Uri.parse(WAZE_UPGRADE_URL);
            $r0.setFlags(268435456);
            $r0.setData($r1);
            mInstance.startActivity($r0);
        }
    }

    private static void ShowMainActivityHandler() throws  {
        if (mInstance != null) {
            Intent $r0 = new Intent(getAppContext(), MainActivity.class);
            $r0.setFlags(268435456);
            mInstance.startActivity($r0);
        }
    }

    private static void ShowHomeHandler() throws  {
        if (mInstance != null) {
            Intent $r0 = new Intent("android.intent.action.MAIN");
            $r0.addCategory("android.intent.category.HOME");
            $r0.setFlags(268435456);
            mInstance.startActivity($r0);
        }
    }

    private static void ShowDialerHandler() throws  {
        if (mInstance != null) {
            Intent $r0 = new Intent("android.intent.action.DIAL");
            $r0.setFlags(268435456);
            mInstance.startActivity($r0);
        }
    }

    private static void ShowCameraPreviewHandler() throws  {
    }

    @TargetApi(16)
    private static Notification SetNotification() throws  {
        String $r1 = "Running. Tap to open.";
        String $r2 = "Switch off";
        if (IsAppRunning()) {
            $r1 = NativeManager.getInstance().getLanguageString("Running. Tap to open.");
            $r2 = NativeManager.getInstance().getLanguageString("Switch off");
        }
        long $l0 = System.currentTimeMillis();
        Context $r4 = getAppContext();
        CloseIntent.Register($r4);
        if (VERSION.SDK_INT >= 16) {
            Intent $r0 = new Intent($r4, FreeMapAppActivity.class);
            $r0.setAction("android.intent.action.MAIN");
            $r0.addCategory("android.intent.category.LAUNCHER");
            PendingIntent $r5 = PendingIntent.getActivity($r4, 0, $r0, 268435456);
            PendingIntent $r6 = PendingIntent.getBroadcast($r4, 1, new Intent($r4, CloseIntent.class), 268435456);
            Builder builder = new Builder($r4);
            builder.setContentIntent($r5);
            builder.setSmallIcon(C1283R.drawable.notification);
            builder.setTicker("Waze");
            builder.setColor($r4.getResources().getColor(C1283R.color.notification_circle_bg));
            builder.setContentTitle("Waze");
            builder.setContentText($r1);
            builder.setPriority(2);
            builder.addAction(C1283R.drawable.switch_off_icon, $r2, $r6);
            Notification $r9 = builder.build();
            $r9.flags |= 34;
            $r9.when = System.currentTimeMillis();
            ((NotificationManager) $r4.getSystemService("notification")).notify(1, $r9);
            return $r9;
        }
        $r0 = new Intent($r4, FreeMapAppActivity.class);
        $r0.setAction("android.intent.action.MAIN");
        $r0.addCategory("android.intent.category.LAUNCHER");
        $r9 = new Builder($r4).setContentIntent(PendingIntent.getActivity($r4, 0, $r0, 268435456)).setSmallIcon(C1283R.drawable.notification).setTicker("Waze").setWhen($l0).setContentText($r1).setContentTitle("Waze").setOngoing(true).build();
        $r9.flags |= 34;
        ((NotificationManager) $r4.getSystemService("notification")).notify(1, $r9);
        return $r9;
    }

    private static void ClearNotification() throws  {
        ((NotificationManager) getAppContext().getSystemService("notification")).cancel(1);
    }

    private void startLogCatMonitor() throws  {
    }

    public static ServiceMsgDispatcher getServiceMsgDispatcher() throws  {
        if (mServiceMsgDispatcher == null) {
            mServiceMsgDispatcher = new ServiceMsgDispatcher();
        }
        return mServiceMsgDispatcher;
    }

    public static void setActiveMapViewWrapper(MapViewWrapper $r0) throws  {
        activeMapViewWrapper = $r0;
    }

    public static MapViewWrapper getActiveMapViewWrapper() throws  {
        return activeMapViewWrapper;
    }
}
