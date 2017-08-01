package com.google.android.gms.internal;

import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.auth.api.proxy.ProxyResponse;
import com.google.android.gms.common.api.Status;

/* compiled from: dalvik_source_com.waze.apk */
class zzni implements ProxyResult {
    private Status cp;
    private ProxyResponse fu;

    public zzni(ProxyResponse $r1) throws  {
        this.fu = $r1;
        this.cp = Status.CQ;
    }

    public zzni(Status $r1) throws  {
        this.cp = $r1;
    }

    public ProxyResponse getResponse() throws  {
        return this.fu;
    }

    public Status getStatus() throws  {
        return this.cp;
    }
}
