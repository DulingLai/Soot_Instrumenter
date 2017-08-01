package com.google.android.gms.people.protomodel;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class BackedUpContactsPerDeviceEntity extends AbstractSafeParcelable implements BackedUpContactsPerDevice {
    public static final Creator<BackedUpContactsPerDeviceEntity> CREATOR = new zza();
    private final List<SourceStatsEntity> aUE;
    private final String aUF;
    private List<SourceStats> aUG;
    public final int mVersionCode;
    private final String qy;

    BackedUpContactsPerDeviceEntity(@Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStatsEntity;", ">;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStatsEntity;", ">;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStatsEntity;", ">;", "Ljava/lang/String;", ")V"}) List<SourceStatsEntity> $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStatsEntity;", ">;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        this.qy = $r1;
        this.aUE = $r2;
        this.aUF = $r3;
        this.mVersionCode = $i0;
    }

    public BackedUpContactsPerDeviceEntity(BackedUpContactsPerDevice $r1) throws  {
        this($r1.getDeviceId(), $r1.getSourceStats(), $r1.getDeviceDisplayName(), false);
    }

    BackedUpContactsPerDeviceEntity(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStats;", ">;", "Ljava/lang/String;", "Z)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStats;", ">;", "Ljava/lang/String;", "Z)V"}) List<SourceStats> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStats;", ">;", "Ljava/lang/String;", "Z)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/people/protomodel/SourceStats;", ">;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        this.mVersionCode = 1;
        this.qy = $r1;
        this.aUF = $r3;
        if ($z0) {
            this.aUG = $r2;
            if ($r2 != null) {
                this.aUE = new ArrayList($r2.size());
                for (SourceStats $r8 : $r2) {
                    this.aUE.add((SourceStatsEntity) $r8);
                }
                return;
            }
            this.aUE = null;
            return;
        }
        this.aUG = $r2;
        if ($r2 != null) {
            this.aUE = new ArrayList($r2.size());
            for (SourceStats $r82 : $r2) {
                this.aUE.add(new SourceStatsEntity($r82));
            }
            return;
        }
        this.aUE = null;
    }

    public static int zza(BackedUpContactsPerDevice $r0) throws  {
        return zzaa.hashCode($r0.getDeviceId(), $r0.getSourceStats(), $r0.getDeviceDisplayName());
    }

    public static boolean zza(BackedUpContactsPerDevice $r0, BackedUpContactsPerDevice $r1) throws  {
        return zzaa.equal($r0.getDeviceId(), $r1.getDeviceId()) && zzaa.equal($r0.getSourceStats(), $r1.getSourceStats()) && zzaa.equal($r0.getDeviceDisplayName(), $r1.getDeviceDisplayName());
    }

    public boolean equals(Object $r1) throws  {
        return !($r1 instanceof BackedUpContactsPerDevice) ? false : this == $r1 ? true : zza(this, (BackedUpContactsPerDevice) $r1);
    }

    public /* synthetic */ Object freeze() throws  {
        return zzcge();
    }

    public String getDeviceDisplayName() throws  {
        return this.aUF;
    }

    public String getDeviceId() throws  {
        return this.qy;
    }

    public List<SourceStats> getSourceStats() throws  {
        if (this.aUG == null && this.aUE != null) {
            this.aUG = new ArrayList(this.aUE.size());
            for (SourceStats $r5 : this.aUE) {
                this.aUG.add($r5);
            }
        }
        return this.aUG;
    }

    public int hashCode() throws  {
        return zza(this);
    }

    public boolean isDataValid() throws  {
        return true;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }

    public BackedUpContactsPerDevice zzcge() throws  {
        return this;
    }
}
