package com.google.android.gms.auth.api.credentials.internal;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.Status;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzd implements CredentialRequestResult {
    private final Status cp;
    private final Credential fb;

    public zzd(Status $r1, Credential $r2) throws  {
        this.cp = $r1;
        this.fb = $r2;
    }

    public static zzd zzk(Status $r0) throws  {
        return new zzd($r0, null);
    }

    public Credential getCredential() throws  {
        return this.fb;
    }

    public Status getStatus() throws  {
        return this.cp;
    }
}
