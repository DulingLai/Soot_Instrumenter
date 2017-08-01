package com.google.android.gms.people.protomodel;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class FetchBackUpDeviceContactInfoResponseEntity extends AbstractSafeParcelable implements FetchBackUpDeviceContactInfoResponse {
    public static final Creator<FetchBackUpDeviceContactInfoResponseEntity> CREATOR = new zzb();
    private final List<BackedUpContactsPerDeviceEntity> aUH;
    private List<BackedUpContactsPerDevice> aUI;
    public final int mVersionCode;

    FetchBackUpDeviceContactInfoResponseEntity(@Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/BackedUpContactsPerDeviceEntity;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/BackedUpContactsPerDeviceEntity;", ">;)V"}) List<BackedUpContactsPerDeviceEntity> $r1) throws  {
        this.aUH = $r1;
        this.mVersionCode = $i0;
    }

    FetchBackUpDeviceContactInfoResponseEntity(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/BackedUpContactsPerDevice;", ">;Z)V"}) List<BackedUpContactsPerDevice> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/BackedUpContactsPerDevice;", ">;Z)V"}) boolean $z0) throws  {
        this.mVersionCode = 1;
        if ($z0) {
            this.aUI = $r1;
            if ($r1 != null) {
                this.aUH = new ArrayList($r1.size());
                for (BackedUpContactsPerDevice $r6 : $r1) {
                    this.aUH.add((BackedUpContactsPerDeviceEntity) $r6);
                }
                return;
            }
            this.aUH = null;
            return;
        }
        this.aUI = $r1;
        if ($r1 != null) {
            this.aUH = new ArrayList($r1.size());
            for (BackedUpContactsPerDevice $r62 : $r1) {
                this.aUH.add(new BackedUpContactsPerDeviceEntity($r62));
            }
            return;
        }
        this.aUH = null;
    }

    public static int zza(FetchBackUpDeviceContactInfoResponse $r0) throws  {
        return zzaa.hashCode($r0.getContactsPerDevice());
    }

    public static boolean zza(FetchBackUpDeviceContactInfoResponse $r0, FetchBackUpDeviceContactInfoResponse $r1) throws  {
        return zzaa.equal($r0.getContactsPerDevice(), $r1.getContactsPerDevice());
    }

    public boolean equals(Object $r1) throws  {
        return !($r1 instanceof FetchBackUpDeviceContactInfoResponse) ? false : this == $r1 ? true : zza(this, (FetchBackUpDeviceContactInfoResponse) $r1);
    }

    public /* synthetic */ Object freeze() throws  {
        return zzcgf();
    }

    public List<BackedUpContactsPerDevice> getContactsPerDevice() throws  {
        if (this.aUI == null && this.aUH != null) {
            this.aUI = new ArrayList(this.aUH.size());
            for (BackedUpContactsPerDevice $r5 : this.aUH) {
                this.aUI.add($r5);
            }
        }
        return this.aUI;
    }

    public int hashCode() throws  {
        return zza(this);
    }

    public boolean isDataValid() throws  {
        return true;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }

    public FetchBackUpDeviceContactInfoResponse zzcgf() throws  {
        return this;
    }
}
