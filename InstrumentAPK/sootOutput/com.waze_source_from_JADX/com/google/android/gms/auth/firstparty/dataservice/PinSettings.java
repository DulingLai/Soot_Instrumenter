package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class PinSettings extends AbstractSafeParcelable {
    public static final PinSettingsCreator CREATOR = new PinSettingsCreator();
    public final int length;
    public final String recoveryUrl;
    public final String resetUrl;
    public final String setupUrl;
    public final String status;
    final int version;

    PinSettings(int $i0, String $r1, String $r2, String $r3, String $r4, int $i1) throws  {
        this.version = $i0;
        this.status = $r1;
        this.resetUrl = $r2;
        this.setupUrl = $r3;
        this.recoveryUrl = $r4;
        this.length = $i1;
    }

    public PinSettings(String $r1, String $r2, String $r3, String $r4, int $i0) throws  {
        this(2, $r1, $r2, $r3, $r4, $i0);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PinSettingsCreator.zza(this, $r1, $i0);
    }
}
