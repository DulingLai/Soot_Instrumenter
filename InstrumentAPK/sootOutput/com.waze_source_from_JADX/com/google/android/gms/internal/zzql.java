package com.google.android.gms.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzql extends zzrh implements OnCancelListener {
    protected final GoogleApiAvailability CH;
    protected boolean Dl;
    private ConnectionResult Dm;
    private int Dn;
    private final Handler Do;
    protected boolean mStarted;

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza implements Runnable {
        final /* synthetic */ zzql Dp;

        private zza(zzql $r1) throws  {
            this.Dp = $r1;
        }

        @MainThread
        public void run() throws  {
            if (!this.Dp.mStarted) {
                return;
            }
            if (this.Dp.Dm.hasResolution()) {
                this.Dp.FM.startActivityForResult(GoogleApiActivity.zzb(this.Dp.getActivity(), this.Dp.Dm.getResolution(), this.Dp.Dn, false), 1);
            } else if (this.Dp.CH.isUserResolvableError(this.Dp.Dm.getErrorCode())) {
                this.Dp.CH.zza(this.Dp.getActivity(), this.Dp.FM, this.Dp.Dm.getErrorCode(), 2, this.Dp);
            } else if (this.Dp.Dm.getErrorCode() == 18) {
                Dialog $r8 = this.Dp.CH.zza(this.Dp.getActivity(), this.Dp);
                final Dialog dialog = $r8;
                this.Dp.CH.zza(this.Dp.getActivity().getApplicationContext(), new com.google.android.gms.internal.zzrc.zza(this) {
                    final /* synthetic */ zza Dr;

                    public void zzasl() throws  {
                        this.Dr.Dp.zzask();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                zzql $r1 = this.Dp;
                zzql $r11 = this.Dp;
                ConnectionResult $r2 = $r11.Dm;
                $r11 = this.Dp;
                $r1.zza($r2, $r11.Dn);
            }
        }
    }

    protected zzql(zzri $r1) throws  {
        this($r1, GoogleApiAvailability.getInstance());
    }

    zzql(zzri $r1, GoogleApiAvailability $r2) throws  {
        super($r1);
        this.Dn = -1;
        this.Do = new Handler(Looper.getMainLooper());
        this.CH = $r2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r8, int r9, android.content.Intent r10) throws  {
        /*
        r7 = this;
        r0 = 1;
        switch(r8) {
            case 1: goto L_0x0027;
            case 2: goto L_0x000c;
            default: goto L_0x0004;
        };
    L_0x0004:
        goto L_0x0005;
    L_0x0005:
        r0 = 0;
    L_0x0006:
        if (r0 == 0) goto L_0x003f;
    L_0x0008:
        r7.zzask();
        return;
    L_0x000c:
        r1 = r7.CH;
        r2 = r7.getActivity();
        r8 = r1.isGooglePlayServicesAvailable(r2);
        if (r8 != 0) goto L_0x004a;
    L_0x0018:
        r3 = r7.Dm;
        r9 = r3.getErrorCode();
        r4 = 18;
        if (r9 != r4) goto L_0x0006;
    L_0x0022:
        r4 = 18;
        if (r8 != r4) goto L_0x0006;
    L_0x0026:
        return;
    L_0x0027:
        r4 = -1;
        if (r9 == r4) goto L_0x0006;
    L_0x002a:
        if (r9 != 0) goto L_0x0005;
    L_0x002c:
        if (r10 == 0) goto L_0x0047;
    L_0x002e:
        r5 = "<<ResolutionFailureErrorDetail>>";
        r4 = 13;
        r8 = r10.getIntExtra(r5, r4);
    L_0x0036:
        r3 = new com.google.android.gms.common.ConnectionResult;
        r6 = 0;
        r3.<init>(r8, r6);
        r7.Dm = r3;
        goto L_0x0005;
    L_0x003f:
        r3 = r7.Dm;
        r8 = r7.Dn;
        r7.zza(r3, r8);
        return;
    L_0x0047:
        r8 = 13;
        goto L_0x0036;
    L_0x004a:
        r0 = 0;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzql.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) throws  {
        zza(new ConnectionResult(13, null), this.Dn);
        zzask();
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if ($r1 != null) {
            this.Dl = $r1.getBoolean("resolving_error", false);
            if (this.Dl) {
                this.Dn = $r1.getInt("failed_client_id", -1);
                this.Dm = new ConnectionResult($r1.getInt("failed_status"), (PendingIntent) $r1.getParcelable("failed_resolution"));
            }
        }
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putBoolean("resolving_error", this.Dl);
        if (this.Dl) {
            $r1.putInt("failed_client_id", this.Dn);
            $r1.putInt("failed_status", this.Dm.getErrorCode());
            $r1.putParcelable("failed_resolution", this.Dm.getResolution());
        }
    }

    public void onStart() throws  {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() throws  {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zza(ConnectionResult connectionResult, int i) throws ;

    protected abstract void zzasf() throws ;

    protected void zzask() throws  {
        this.Dn = -1;
        this.Dl = false;
        this.Dm = null;
        zzasf();
    }

    public void zzb(ConnectionResult $r1, int $i0) throws  {
        if (!this.Dl) {
            this.Dl = true;
            this.Dn = $i0;
            this.Dm = $r1;
            this.Do.post(new zza());
        }
    }
}
