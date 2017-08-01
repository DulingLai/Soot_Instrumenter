package com.google.android.gms.auth;

import android.app.PendingIntent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class RecoveryDecision extends AbstractSafeParcelable {
    public static final RecoveryDecisionCreator CREATOR = new RecoveryDecisionCreator();
    public boolean isRecoveryInfoNeeded;
    public boolean isRecoveryInterstitialAllowed;
    final int mVersionCode;
    public PendingIntent recoveryIntent;
    public PendingIntent recoveryIntentWithoutIntro;
    public boolean showRecoveryInterstitial;

    public RecoveryDecision() throws  {
        this.mVersionCode = 1;
    }

    RecoveryDecision(int $i0, PendingIntent $r1, boolean $z0, boolean $z1, boolean $z2, PendingIntent $r2) throws  {
        this.mVersionCode = $i0;
        this.recoveryIntent = $r1;
        this.showRecoveryInterstitial = $z0;
        this.isRecoveryInfoNeeded = $z1;
        this.isRecoveryInterstitialAllowed = $z2;
        this.recoveryIntentWithoutIntro = $r2;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        RecoveryDecisionCreator.zza(this, $r1, $i0);
    }
}
