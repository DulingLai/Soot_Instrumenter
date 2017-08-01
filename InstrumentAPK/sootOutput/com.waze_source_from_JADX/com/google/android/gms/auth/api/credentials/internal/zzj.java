package com.google.android.gms.auth.api.credentials.internal;

import com.google.android.gms.auth.api.credentials.GeneratePasswordRequestResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements GeneratePasswordRequestResult {
    private Status cp;
    private String eI;

    public zzj(Status $r1, String $r2) throws  {
        this.eI = zzab.zzgy($r2);
        this.cp = (Status) zzab.zzag($r1);
    }

    public String getGeneratedPassword() throws  {
        return this.eI;
    }

    public Status getStatus() throws  {
        return this.cp;
    }
}
