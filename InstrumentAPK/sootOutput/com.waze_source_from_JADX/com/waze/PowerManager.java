package com.waze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class PowerManager extends BroadcastReceiver {
    private static PowerManager mInstance = null;
    private volatile int mCurrentLevel = -1;
    private volatile int mIsCharging = -1;
    private volatile int mTemperature = -1;

    static PowerManager Instance() throws  {
        return mInstance;
    }

    static PowerManager Create(Context $r0) throws  {
        if (mInstance == null) {
            mInstance = new PowerManager();
            Instance().SetDataFromIntent($r0.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")));
        }
        return mInstance;
    }

    public void onReceive(Context context, Intent $r2) throws  {
        if ("android.intent.action.BATTERY_CHANGED".equals($r2.getAction())) {
            SetDataFromIntent($r2);
        }
    }

    public int getCurrentLevel() throws  {
        return this.mCurrentLevel;
    }

    public int getIsCharging() throws  {
        return this.mIsCharging;
    }

    public int getTemperature() throws  {
        return this.mTemperature;
    }

    private PowerManager() throws  {
    }

    private void SetDataFromIntent(Intent $r1) throws  {
        byte $b0 = (byte) 0;
        if ($r1 != null) {
            this.mCurrentLevel = ($r1.getIntExtra("level", 0) * 100) / $r1.getIntExtra("scale", 100);
            int $i2 = $r1.getIntExtra("status", -1);
            if ($i2 == 2 || $i2 == 5) {
                $b0 = (byte) 1;
            }
            this.mIsCharging = $b0;
            this.mTemperature = $r1.getIntExtra("temperature", 100) / 10;
        }
    }
}
