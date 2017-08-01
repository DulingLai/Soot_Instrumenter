package com.waze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.waze.ifs.ui.ActivityBase;

public class StandbyManager extends BroadcastReceiver {
    private static StandbyManager mInstance = null;
    private Context mContext;

    public void onReceive(Context aContext, Intent $r2) throws  {
        boolean $z0 = true;
        if (AppService.IsAppRunning() && $r2 != null) {
            String $r4 = $r2.getAction();
            byte $b0 = (byte) -1;
            Log.w(Logger.TAG, "Screen broadcast receiver got action: " + $r4);
            if ("android.intent.action.SCREEN_ON".equals($r4)) {
                $b0 = (byte) 1;
            }
            if ("android.intent.action.SCREEN_OFF".equals($r4)) {
                $b0 = (byte) 0;
            }
            if ($b0 >= (byte) 0) {
                if ($b0 != (byte) 1) {
                    $z0 = false;
                }
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        ActivityBase.setScreenState($z0);
                    }
                });
            }
        }
    }

    public static StandbyManager start(Context $r0) throws  {
        if (mInstance == null) {
            mInstance = new StandbyManager();
            mInstance.mContext = $r0;
        }
        IntentFilter $r1 = new IntentFilter("android.intent.action.SCREEN_ON");
        $r1.addAction("android.intent.action.SCREEN_OFF");
        $r0.registerReceiver(mInstance, $r1);
        return mInstance;
    }

    public void stop() throws  {
        this.mContext.unregisterReceiver(mInstance);
    }

    private StandbyManager() throws  {
    }
}
