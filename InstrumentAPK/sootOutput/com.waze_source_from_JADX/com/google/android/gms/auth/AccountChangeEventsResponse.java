package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountChangeEventsResponse extends AbstractSafeParcelable {
    public static final Creator<AccountChangeEventsResponse> CREATOR = new zzc();
    final int mVersion;
    final List<AccountChangeEvent> zzalu;

    AccountChangeEventsResponse(@Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;)V"}) List<AccountChangeEvent> $r1) throws  {
        this.mVersion = $i0;
        this.zzalu = (List) zzab.zzag($r1);
    }

    public AccountChangeEventsResponse(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/AccountChangeEvent;", ">;)V"}) List<AccountChangeEvent> $r1) throws  {
        this.mVersion = 1;
        this.zzalu = (List) zzab.zzag($r1);
    }

    public List<AccountChangeEvent> getEvents() throws  {
        return this.zzalu;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }
}
