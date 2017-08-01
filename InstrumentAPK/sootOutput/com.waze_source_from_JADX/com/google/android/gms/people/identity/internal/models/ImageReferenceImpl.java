package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.identity.models.ImageReference;
import dalvik.annotation.Signature;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class ImageReferenceImpl extends AbstractSafeParcelable implements ImageReference {
    public static final Creator<ImageReferenceImpl> CREATOR = new zzl();
    final Set<Integer> aOu;
    String aOw;
    int bG;
    byte[] mData;
    final int mVersionCode;

    public ImageReferenceImpl() throws  {
        this.aOu = new HashSet();
        this.mVersionCode = 1;
    }

    ImageReferenceImpl(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "[B)V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "[B)V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "[B)V"}) int $i1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "[B)V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;II", "Ljava/lang/String;", "[B)V"}) byte[] $r3) throws  {
        this.aOu = $r1;
        this.mVersionCode = $i0;
        this.bG = $i1;
        this.aOw = $r2;
        this.mData = $r3;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzl.zza(this, $r1, $i0);
    }

    public ImageReferenceImpl zzabg(int $i0) throws  {
        this.aOu.add(Integer.valueOf(2));
        this.bG = $i0;
        return this;
    }

    public ImageReferenceImpl zznm(String $r1) throws  {
        this.aOw = $r1;
        return this;
    }
}
