package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;

public class FacebookBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent $r2) throws  {
        String $r3 = $r2.getStringExtra(NativeProtocol.EXTRA_PROTOCOL_CALL_ID);
        String $r4 = $r2.getStringExtra(NativeProtocol.EXTRA_PROTOCOL_ACTION);
        if ($r3 != null && $r4 != null) {
            Bundle $r5 = $r2.getExtras();
            if (NativeProtocol.isErrorResult($r2)) {
                onFailedAppCall($r3, $r4, $r5);
            } else {
                onSuccessfulAppCall($r3, $r4, $r5);
            }
        }
    }

    protected void onSuccessfulAppCall(String appCallId, String action, Bundle extras) throws  {
    }

    protected void onFailedAppCall(String appCallId, String action, Bundle extras) throws  {
    }
}
