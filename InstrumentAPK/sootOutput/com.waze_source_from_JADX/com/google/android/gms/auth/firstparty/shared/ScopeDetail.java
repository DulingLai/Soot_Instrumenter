package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class ScopeDetail extends AbstractSafeParcelable {
    public static final ScopeDetailCreator CREATOR = new ScopeDetailCreator();
    String description;
    public FACLData friendPickerData;
    String gu;
    String hz;
    String iK;
    String iL;
    List<String> iM;
    final int version;

    ScopeDetail(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) String $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) String $r4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) String $r5, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) List<String> $r6, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", ")V"}) FACLData $r7) throws  {
        this.version = $i0;
        this.description = $r1;
        this.gu = $r2;
        this.iK = $r3;
        this.iL = $r4;
        this.hz = $r5;
        this.iM = $r6;
        this.friendPickerData = $r7;
    }

    public ScopeDetail(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r5, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) FACLData $r6, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/FACLData;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r7) throws  {
        this.version = 1;
        this.hz = $r1;
        this.description = $r2;
        this.gu = $r3;
        this.iK = $r4;
        this.iL = $r5;
        this.friendPickerData = $r6;
        this.iM = new ArrayList();
        this.iM.addAll($r7);
    }

    public String getDescription() throws  {
        return this.description;
    }

    public String getDetail() throws  {
        return this.gu;
    }

    public FACLData getFriendPickerData() throws  {
        return this.friendPickerData;
    }

    public String getIconBase64() throws  {
        return this.iK;
    }

    public String getPaclPickerBase64() throws  {
        return this.iL;
    }

    public String getService() throws  {
        return this.hz;
    }

    public List<String> getUnmodifiableWarnings() throws  {
        return Collections.unmodifiableList(this.iM);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ScopeDetailCreator.zza(this, $r1, $i0);
    }
}
