package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzrc extends BroadcastReceiver {
    private final zza FC;
    protected Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza {
        public abstract void zzasl() throws ;
    }

    public zzrc(zza $r1) throws  {
        this.FC = $r1;
    }

    public void onReceive(Context context, Intent $r2) throws  {
        Uri $r3 = $r2.getData();
        String $r4 = null;
        if ($r3 != null) {
            $r4 = $r3.getSchemeSpecificPart();
        }
        if ("com.google.android.gms".equals($r4)) {
            this.FC.zzasl();
            unregister();
        }
    }

    public void setContext(Context $r1) throws  {
        this.mContext = $r1;
    }

    public synchronized void unregister() throws  {
        if (this.mContext != null) {
            this.mContext.unregisterReceiver(this);
        }
        this.mContext = null;
    }
}
