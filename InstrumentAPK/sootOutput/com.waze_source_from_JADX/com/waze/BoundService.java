package com.waze;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.waze.NativeManager.TransportationSdkDetails;
import com.waze.analytics.AnalyticsEvents;
import com.waze.main.navigate.NavigationItem;
import java.lang.ref.WeakReference;

public class BoundService extends Service {
    private static String LOG_TAG = "BoundService";
    static final int MSG_AGREEMENT = 7;
    static final int MSG_GET_DISTANCE = 1;
    static final int MSG_GET_ETA = 2;
    static final int MSG_GET_INSTRUCTION = 3;
    static final int MSG_GET_NEXT_TURN = 4;
    static final int MSG_GET_ROUTE = 0;
    static final int MSG_INIT_APP = 5;
    static final int MSG_INSTRUCTION_EXIT = 6;
    static final int MSG_INTERNAL_USING_BY_SDK = 9;
    static final int MSG_LANE_SIDE = 10;
    static final int MSG_NAVIGATION_ITEMS = 8;
    static TransportationSdkDetails mAppDetails = null;
    static String mBindAppName = null;
    static int mBoundPackageIndex = -1;
    static Messenger mCallerMessanger = null;
    static PendingIntent mCallingIntent = null;
    private static BoundService mInstance = null;
    public static boolean mIsRunning = false;
    static String mSecretKey = null;
    final Messenger mMessenger = new Messenger(new BoundServiceHandler(this));

    static class BoundServiceHandler extends Handler {
        private final WeakReference<BoundService> mService;

        public BoundServiceHandler(BoundService $r1) throws  {
            this.mService = new WeakReference($r1);
        }

        public void handleMessage(Message $r1) throws  {
            BoundService.mIsRunning = true;
            switch ($r1.what) {
                case 5:
                    if (AIDLService.mAppName != null && AIDLService.mSecret != null) {
                        BoundService.mBoundPackageIndex = -1;
                        BoundService.mSecretKey = AIDLService.mSecret;
                        BoundService.mCallerMessanger = $r1.replyTo;
                        BoundService.mBindAppName = AIDLService.mAppName;
                        BoundService.mCallingIntent = (PendingIntent) $r1.getData().getParcelable("Intent");
                        return;
                    }
                    return;
                default:
                    super.handleMessage($r1);
                    return;
            }
        }
    }

    private static boolean IsTransportationMode() throws  {
        return (mBindAppName == null || mBindAppName.isEmpty()) ? false : true;
    }

    public static TransportationAppData getTrasnportationAppData(Context $r0) throws  {
        if (!IsTransportationMode()) {
            return null;
        }
        TransportationAppData $r3 = new TransportationAppData();
        try {
            $r3.communicate_app_icon = $r0.getPackageManager().getApplicationIcon(mBindAppName);
            $r3.communicate_app_callback = mCallingIntent;
            $r3.communicate_app_name = mBindAppName;
            return $r3;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static BoundService getInstance() throws  {
        return mInstance;
    }

    public void InitDetails(int $i0, TransportationSdkDetails $r1) throws  {
        mBoundPackageIndex = $i0;
        mAppDetails = $r1;
    }

    private boolean ShouldSendData() throws  {
        return mCallerMessanger != null && mAppDetails != null && mBoundPackageIndex >= 0 && mAppDetails.Scopes[mBoundPackageIndex] > 0;
    }

    public void SendNextTurnUpdate(String $r1) throws  {
        if (ShouldSendData()) {
            Bundle $r2 = new Bundle();
            $r2.putByteArray(AnalyticsEvents.f75xdd342340, EncryptMessage($r1));
            Message $r4 = Message.obtain(null, 4);
            $r4.setData($r2);
            try {
                mCallerMessanger.send($r4);
            } catch (RemoteException e) {
            }
        }
    }

    public byte[] EncryptMessage(String $r1) throws  {
        if (mBoundPackageIndex <= -1 || mSecretKey == null) {
            return null;
        }
        return Utils.EncryptUsingSymetricKey($r1, mSecretKey);
    }

    public void SendRouteGeometry(String $r1) throws  {
        if (ShouldSendData()) {
            Bundle $r2 = new Bundle();
            $r2.putByteArray("GeoJson", EncryptMessage($r1));
            Message $r4 = Message.obtain(null, 0);
            $r4.setData($r2);
            try {
                mCallerMessanger.send($r4);
            } catch (RemoteException e) {
            }
        }
    }

    public void SendDistanceStringUpdate(int $i0, String $r1) throws  {
        if (ShouldSendData()) {
            Bundle $r2 = new Bundle();
            $r2.putByteArray("DISTANCE_METERS", EncryptMessage(Integer.toString($i0)));
            $r2.putByteArray("DISTANCE_DISPLAY", EncryptMessage($r1));
            Message $r5 = Message.obtain(null, 1);
            $r5.setData($r2);
            try {
                mCallerMessanger.send($r5);
            } catch (RemoteException e) {
            }
        }
    }

    public void SendLeftRightLaneUpdate(boolean $z0) throws  {
        if (ShouldSendData()) {
            Bundle $r1 = new Bundle();
            $r1.putByteArray("LEFT_LANE", EncryptMessage(Boolean.toString($z0)));
            Message $r4 = Message.obtain(null, 10);
            $r4.setData($r1);
            try {
                mCallerMessanger.send($r4);
            } catch (RemoteException e) {
            }
        }
    }

    public void SendETAUpdate(int $i0) throws  {
        if (ShouldSendData()) {
            Bundle $r1 = new Bundle();
            $r1.putByteArray("ETA_MINUTES", EncryptMessage(Integer.toString($i0)));
            Message $r4 = Message.obtain(null, 2);
            $r4.setData($r1);
            try {
                mCallerMessanger.send($r4);
            } catch (RemoteException e) {
            }
        }
    }

    public void SendNextInstructionUpdate(int $i0) throws  {
        if (ShouldSendData()) {
            Bundle $r1 = new Bundle();
            $r1.putByteArray("INSTRUCTION", EncryptMessage(Integer.toString($i0)));
            Message $r4 = Message.obtain(null, 3);
            $r4.setData($r1);
            try {
                mCallerMessanger.send($r4);
            } catch (RemoteException e) {
            }
        }
    }

    public void SendInstructionExitNumberUpdate(int $i0) throws  {
        if (ShouldSendData()) {
            Bundle $r1 = new Bundle();
            $r1.putByteArray("INSTRUCTION_EXIT", EncryptMessage(Integer.toString($i0)));
            Message $r4 = Message.obtain(null, 6);
            $r4.setData($r1);
            try {
                mCallerMessanger.send($r4);
            } catch (RemoteException e) {
            }
        }
    }

    public void SendNavigationItemsUpdate(NavigationItem[] $r1) throws  {
        if (ShouldSendData()) {
            SDKNavigationItem[] $r3 = Utils.CopyFromNavigationItemArray($r1);
            Bundle $r2 = new Bundle();
            $r2.putParcelableArray("NAVIGATION_ITEMS", $r3);
            Message $r4 = Message.obtain(null, 8);
            $r4.setData($r2);
            try {
                mCallerMessanger.send($r4);
            } catch (RemoteException e) {
            }
        }
    }

    public void SendAgreement(boolean $z0) throws  {
        if (mCallingIntent != null) {
            Bundle $r1 = new Bundle();
            $r1.putByteArray(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_AGREEMENT, EncryptMessage(Boolean.toString($z0)));
            Message $r5 = Message.obtain(null, 7);
            $r5.setData($r1);
            try {
                mCallerMessanger.send($r5);
            } catch (RemoteException e) {
            }
        }
    }

    public void onCreate() throws  {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");
        mInstance = this;
    }

    public IBinder onBind(Intent intent) throws  {
        Log.v(LOG_TAG, "in onBind");
        mIsRunning = true;
        return this.mMessenger.getBinder();
    }

    public void onRebind(Intent $r1) throws  {
        Log.v(LOG_TAG, "in onRebind");
        mIsRunning = true;
        super.onRebind($r1);
    }

    public boolean onUnbind(Intent intent) throws  {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    public void onDestroy() throws  {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
        mInstance = null;
        mIsRunning = false;
    }
}
