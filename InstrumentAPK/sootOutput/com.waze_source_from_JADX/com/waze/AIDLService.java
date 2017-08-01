package com.waze;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.waze.IServiceAIDL.Stub;

public class AIDLService extends Service {
    static String mAppName = null;
    static String mSecret = null;
    private final Stub mBinder = new C10941();

    class C10941 extends Stub {
        C10941() throws  {
        }

        public int getPid() throws  {
            return C10941.getCallingPid();
        }

        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws  {
        }

        public void sendKey(String $r1) throws  {
            if ($r1 != null && !$r1.equals("")) {
                AIDLService.mSecret = $r1;
                AIDLService.mAppName = AIDLService.this.getApplicationContext().getPackageManager().getNameForUid(Binder.getCallingUid());
            }
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) throws  {
        return 1;
    }

    public boolean onUnbind(Intent intent) throws  {
        mAppName = null;
        return true;
    }

    public void onCreate() throws  {
        super.onCreate();
    }

    public IBinder onBind(Intent intent) throws  {
        return this.mBinder;
    }

    public void onRebind(Intent $r1) throws  {
        super.onRebind($r1);
    }

    public void onDestroy() throws  {
        super.onDestroy();
    }

    public void Stop() throws  {
        stopSelf();
    }
}
