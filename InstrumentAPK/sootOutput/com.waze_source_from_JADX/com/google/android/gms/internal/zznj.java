package com.google.android.gms.internal;

import com.google.android.gms.auth.api.proxy.ProxyApi.SpatulaHeaderResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class zznj implements SpatulaHeaderResult {
    private Status cp;
    private String mHeader;

    public zznj(Status $r1) throws  {
        this.cp = (Status) zzab.zzag($r1);
    }

    public zznj(String $r1) throws  {
        this.mHeader = (String) zzab.zzag($r1);
        this.cp = Status.CQ;
    }

    public String getSpatulaHeader() throws  {
        return this.mHeader;
    }

    public Status getStatus() throws  {
        return this.cp;
    }
}
