package com.abaltatech.weblinkserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

class WLService extends Service {
    private final IBinder m_binder = new WebLinkBinder();

    public class WebLinkBinder extends Binder {
    }

    public int onStartCommand(Intent intent, int flags, int startId) throws  {
        return 1;
    }

    WLService() throws  {
    }

    public void onCreate() throws  {
        WLServer.getInstance();
    }

    public void onDestroy() throws  {
        if (WLServer.getInstance().isStarted()) {
            WLServer.getInstance().stopServer();
        }
    }

    public IBinder onBind(Intent intent) throws  {
        return this.m_binder;
    }

    public boolean onUnbind(Intent intent) throws  {
        if (!WLServer.getInstance().isStarted()) {
            stopSelf();
        }
        return false;
    }
}
