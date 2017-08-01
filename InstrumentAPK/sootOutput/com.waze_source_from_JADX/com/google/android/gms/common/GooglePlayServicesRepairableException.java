package com.google.android.gms.common;

import android.content.Intent;

/* compiled from: dalvik_source_com.waze.apk */
public class GooglePlayServicesRepairableException extends UserRecoverableException {
    private final int ed;

    GooglePlayServicesRepairableException(int $i0, String $r1, Intent $r2) throws  {
        super($r1, $r2);
        this.ed = $i0;
    }

    public int getConnectionStatusCode() throws  {
        return this.ed;
    }
}
