package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.identity.models.ImageReference;
import com.google.android.gms.people.identity.models.PersonReference;
import dalvik.annotation.Signature;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class PersonReferenceImpl extends AbstractSafeParcelable implements PersonReference {
    public static final Creator<PersonReferenceImpl> CREATOR = new zzz();
    String Gu;
    final Set<Integer> aOu;
    ImageReferenceImpl aQd;
    String mName;
    final int mVersionCode;

    public PersonReferenceImpl() throws  {
        this.aOu = new HashSet();
        this.mVersionCode = 1;
    }

    PersonReferenceImpl(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/identity/internal/models/ImageReferenceImpl;", ")V"}) Set<Integer> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/identity/internal/models/ImageReferenceImpl;", ")V"}) int $i0, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/identity/internal/models/ImageReferenceImpl;", ")V"}) String $r2, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/identity/internal/models/ImageReferenceImpl;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/identity/internal/models/ImageReferenceImpl;", ")V"}) ImageReferenceImpl $r4) throws  {
        this.aOu = $r1;
        this.mVersionCode = $i0;
        this.mName = $r2;
        this.Gu = $r3;
        this.aQd = $r4;
    }

    public /* synthetic */ ImageReference getAvatarReference() throws  {
        return zzcep();
    }

    public String getQualifiedId() throws  {
        return this.Gu;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzz.zza(this, $r1, $i0);
    }

    public PersonReferenceImpl zzc(ImageReferenceImpl $r1) throws  {
        this.aQd = $r1;
        return this;
    }

    public ImageReferenceImpl zzcep() throws  {
        return this.aQd;
    }

    public PersonReferenceImpl zzqv(String $r1) throws  {
        this.mName = $r1;
        return this;
    }

    public PersonReferenceImpl zzqw(String $r1) throws  {
        this.Gu = $r1;
        return this;
    }
}
