package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: dalvik_source_com.waze.apk */
abstract class zzsr<R extends Result> extends com.google.android.gms.internal.zzqk.zza<R, zzss> {

    /* compiled from: dalvik_source_com.waze.apk */
    static abstract class zza extends zzsr<Status> {
        public zza(GoogleApiClient $r1) throws  {
            super($r1);
        }

        public /* synthetic */ Result zzb(Status $r1) throws  {
            return zzd($r1);
        }

        public Status zzd(Status $r1) throws  {
            return $r1;
        }
    }

    public zzsr(GoogleApiClient $r1) throws  {
        super(zzso.API, $r1);
    }
}
