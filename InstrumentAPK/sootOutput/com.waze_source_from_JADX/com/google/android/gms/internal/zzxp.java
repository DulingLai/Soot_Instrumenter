package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.internal.zzxn.zza;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.reporting.OptInRequest;
import com.google.android.gms.location.reporting.OptInResult;
import com.google.android.gms.location.reporting.ReportingState;
import com.google.android.gms.location.reporting.UploadRequest;
import com.google.android.gms.location.reporting.UploadRequestResult;

/* compiled from: dalvik_source_com.waze.apk */
public class zzxp extends zzk<zzxn> {
    public zzxp(Context $r1, Looper $r2, zzg $r3, ConnectionCallbacks $r4, OnConnectionFailedListener $r5) throws  {
        super($r1, $r2, 22, $r3, $r4, $r5);
    }

    int zza(Account $r1, PlaceReport $r2) throws RemoteException {
        zzavw();
        return ((zzxn) zzavx()).zza($r1, $r2);
    }

    int zza(OptInRequest $r1) throws RemoteException {
        zzavw();
        return OptInResult.sanitize(((zzxn) zzavx()).zza($r1));
    }

    UploadRequestResult zza(UploadRequest $r1) throws RemoteException {
        zzavw();
        if ($r1.getAccount() != null) {
            return ((zzxn) zzavx()).zza($r1);
        }
        throw new IllegalArgumentException();
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzlj($r1);
    }

    int zzbd(long $l0) throws RemoteException {
        zzavw();
        return ((zzxn) zzavx()).zzbc($l0);
    }

    ReportingState zzf(Account $r1) throws RemoteException {
        zzavw();
        return ((zzxn) zzavx()).zzf($r1);
    }

    int zzg(Account $r1) throws RemoteException {
        zzavw();
        return OptInResult.sanitize(((zzxn) zzavx()).zzg($r1));
    }

    protected zzxn zzlj(IBinder $r1) throws  {
        return zza.zzli($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.location.reporting.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.location.reporting.internal.IReportingService";
    }
}
