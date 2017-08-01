package com.waze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CloseIntent extends BroadcastReceiver {
    public static final String ActionName = "Eliran_Close_Intent";

    public void onReceive(Context arg0, Intent arg1) throws  {
        try {
            NativeManager.getInstance().StopWaze();
        } catch (Throwable th) {
        }
    }

    public static void Register(Context $r0) throws  {
        IntentFilter $r1 = new IntentFilter();
        $r1.addAction(ActionName);
        $r1.addCategory("android.intent.category.DEFAULT");
        $r0.registerReceiver(new CloseIntent(), $r1);
    }
}
