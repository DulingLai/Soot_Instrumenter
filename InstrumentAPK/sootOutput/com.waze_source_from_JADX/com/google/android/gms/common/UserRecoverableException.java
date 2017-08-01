package com.google.android.gms.common;

import android.content.Intent;

/* compiled from: dalvik_source_com.waze.apk */
public class UserRecoverableException extends Exception {
    private final Intent mIntent;

    public UserRecoverableException(String $r1, Intent $r2) throws  {
        super($r1);
        this.mIntent = $r2;
    }

    public Intent getIntent() throws  {
        return new Intent(this.mIntent);
    }
}
