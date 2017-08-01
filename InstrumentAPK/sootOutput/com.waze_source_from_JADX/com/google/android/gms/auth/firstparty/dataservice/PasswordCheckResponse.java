package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class PasswordCheckResponse extends AbstractSafeParcelable {
    public static final PasswordCheckResponseCreator CREATOR = new PasswordCheckResponseCreator();
    String gu;
    String hy;
    String status;
    final int version;

    PasswordCheckResponse(int $i0, String $r1, String $r2, String $r3) throws  {
        this.version = $i0;
        this.status = $r1;
        this.hy = $r2;
        this.gu = $r3;
    }

    public PasswordCheckResponse(Status $r1) throws  {
        this($r1, null, null);
    }

    public PasswordCheckResponse(Status $r1, String $r2, String $r3) throws  {
        this.version = 1;
        this.status = ((Status) zzab.zzag($r1)).getWire();
        this.hy = $r2;
        this.gu = $r3;
    }

    public String getDetail() throws  {
        return this.gu;
    }

    public String getPasswordStrength() throws  {
        return this.hy;
    }

    public Status getStatus() throws  {
        return Status.fromWireCode(this.status);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PasswordCheckResponseCreator.zza(this, $r1, $i0);
    }
}
