package com.google.android.gms.auth.firstparty.shared;

import android.graphics.Bitmap;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class CaptchaChallenge extends AbstractSafeParcelable {
    public static final CaptchaChallengeCreator CREATOR = new CaptchaChallengeCreator();
    String gN;
    String gs;
    Bitmap iu;
    final int version;

    CaptchaChallenge(int $i0, String $r1, String $r2, Bitmap $r3) throws  {
        this.version = $i0;
        this.gs = $r1;
        this.gN = $r2;
        this.iu = $r3;
    }

    public CaptchaChallenge(Status $r1) throws  {
        this($r1, null, null);
    }

    public CaptchaChallenge(Status $r1, String $r2, Bitmap $r3) throws  {
        this.version = 1;
        this.gs = ((Status) zzab.zzag($r1)).getWire();
        this.gN = $r2;
        this.iu = $r3;
    }

    public Bitmap getCaptcha() throws  {
        return this.iu;
    }

    public String getCaptchaToken() throws  {
        return this.gN;
    }

    public Status getStatus() throws  {
        return Status.fromWireCode(this.gs);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        CaptchaChallengeCreator.zza(this, $r1, $i0);
    }
}
