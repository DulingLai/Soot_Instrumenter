package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class CheckServerAuthResult extends AbstractSafeParcelable {
    public static final Creator<CheckServerAuthResult> CREATOR = new zzc();
    final boolean bgt;
    final List<Scope> bgu;
    final int mVersionCode;

    CheckServerAuthResult(@Signature({"(IZ", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)V"}) int $i0, @Signature({"(IZ", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)V"}) boolean $z0, @Signature({"(IZ", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)V"}) List<Scope> $r1) throws  {
        this.mVersionCode = $i0;
        this.bgt = $z0;
        this.bgu = $r1;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }
}
