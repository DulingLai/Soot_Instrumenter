package com.google.android.gms.auth.api.signin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.auth.api.signin.internal.zzi;

/* compiled from: dalvik_source_com.waze.apk */
public final class RevocationBoundService extends Service {
    public IBinder onBind(Intent $r1) throws  {
        if ("com.google.android.gms.auth.api.signin.RevocationBoundService.disconnect".equals($r1.getAction())) {
            if (Log.isLoggable("RevocationService", 2)) {
                Log.v("RevocationService", "RevocationBoundService handling disconnect.");
            }
            return new zzi(this);
        }
        String $r2 = "Unknown action sent to RevocationBoundService: ";
        String $r3 = String.valueOf($r1.getAction());
        Log.w("RevocationService", $r3.length() != 0 ? $r2.concat($r3) : new String("Unknown action sent to RevocationBoundService: "));
        return null;
    }
}
