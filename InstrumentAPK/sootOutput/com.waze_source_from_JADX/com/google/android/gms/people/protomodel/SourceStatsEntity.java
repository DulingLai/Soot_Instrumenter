package com.google.android.gms.people.protomodel;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public class SourceStatsEntity extends AbstractSafeParcelable implements SourceStats {
    public static final Creator<SourceStatsEntity> CREATOR = new zzc();
    private final Integer aUJ;
    public final int mVersionCode;
    private final String zzcun;

    SourceStatsEntity(int $i0, String $r1, Integer $r2) throws  {
        this.zzcun = $r1;
        this.aUJ = $r2;
        this.mVersionCode = $i0;
    }

    public SourceStatsEntity(SourceStats $r1) throws  {
        this($r1.getSource(), $r1.getNumContacts(), false);
    }

    SourceStatsEntity(String $r1, Integer $r2, boolean z) throws  {
        this(1, $r1, $r2);
    }

    public static int zza(SourceStats $r0) throws  {
        return zzaa.hashCode($r0.getSource(), $r0.getNumContacts());
    }

    public static boolean zza(SourceStats $r0, SourceStats $r1) throws  {
        return zzaa.equal($r0.getSource(), $r1.getSource()) && zzaa.equal($r0.getNumContacts(), $r1.getNumContacts());
    }

    public boolean equals(Object $r1) throws  {
        return !($r1 instanceof SourceStats) ? false : this == $r1 ? true : zza(this, (SourceStats) $r1);
    }

    public /* synthetic */ Object freeze() throws  {
        return zzcgg();
    }

    public Integer getNumContacts() throws  {
        return this.aUJ;
    }

    public String getSource() throws  {
        return this.zzcun;
    }

    public int hashCode() throws  {
        return zza(this);
    }

    public boolean isDataValid() throws  {
        return true;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }

    public SourceStats zzcgg() throws  {
        return this;
    }
}
