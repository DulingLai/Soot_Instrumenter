package com.google.android.gms.location.internal;

import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;

/* compiled from: dalvik_source_com.waze.apk */
public class NlpTestingRequest extends AbstractSafeParcelable {
    public static final NlpTestingRequestCreator CREATOR = new NlpTestingRequestCreator();
    public static final long TRIGGER_ACTIVE_COLLECTION = 1;
    public static final long TRIGGER_SENSOR_COLLECTION = 2;
    public static final long TRIGGER_SMART_COLLECTION = 4;
    private final long awm;
    private final int mVersionCode;

    NlpTestingRequest(int $i0, long $l1) throws  {
        this.mVersionCode = $i0;
        this.awm = $l1;
    }

    public NlpTestingRequest(long $l0) throws  {
        this(1, $l0);
    }

    public static NlpTestingRequest fromIntent(Intent $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        byte[] $r2 = $r0.getByteArrayExtra("com.google.android.gms.location.internal.EXTRA_NLP_TESTING_REQUEST");
        return $r2 != null ? (NlpTestingRequest) zzc.zza($r2, CREATOR) : null;
    }

    public long getTriggers() throws  {
        return this.awm;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public boolean hasTriggers(long $l0) throws  {
        return (this.awm & $l0) > 0;
    }

    public Intent toTestingIntent() throws  {
        Intent $r1 = new Intent("com.google.android.location.internal.intent.action.NLP_TESTING");
        $r1.putExtra("com.google.android.gms.location.internal.EXTRA_NLP_TESTING_REQUEST", zzc.zza(this));
        return $r1;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        NlpTestingRequestCreator.zza(this, $r1, $i0);
    }
}
