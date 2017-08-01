package com.google.android.gms.auth.api.signin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleSignInResult implements Result {
    private Status cp;
    private GoogleSignInAccount fO;

    public GoogleSignInResult(@Nullable GoogleSignInAccount $r1, @NonNull Status $r2) throws  {
        this.fO = $r1;
        this.cp = $r2;
    }

    @Nullable
    public GoogleSignInAccount getSignInAccount() throws  {
        return this.fO;
    }

    @NonNull
    public Status getStatus() throws  {
        return this.cp;
    }

    public boolean isSuccess() throws  {
        return this.cp.isSuccess();
    }
}
