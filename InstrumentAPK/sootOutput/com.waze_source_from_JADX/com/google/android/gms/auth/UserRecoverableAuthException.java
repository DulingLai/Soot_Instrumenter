package com.google.android.gms.auth;

import android.content.Intent;

/* compiled from: dalvik_source_com.waze.apk */
public class UserRecoverableAuthException extends GoogleAuthException {
    private final Intent mIntent;

    public UserRecoverableAuthException(String $r1, Intent $r2) throws  {
        super($r1);
        this.mIntent = $r2;
    }

    public Intent getIntent() throws  {
        return this.mIntent == null ? null : new Intent(this.mIntent);
    }
}
