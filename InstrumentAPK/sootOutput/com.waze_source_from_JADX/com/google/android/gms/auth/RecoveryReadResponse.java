package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class RecoveryReadResponse extends AbstractSafeParcelable {
    public static final RecoveryReadResponseCreator CREATOR = new RecoveryReadResponseCreator();
    public String mAction;
    public String mAllowedOptions;
    public List<Country> mCountryList;
    public String mError;
    public String mPhoneCountryCode;
    public String mPhoneNumber;
    public String mSecondaryEmail;
    final int mVersionCode;

    public RecoveryReadResponse() throws  {
        this.mVersionCode = 1;
    }

    RecoveryReadResponse(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<Country> $r4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7) throws  {
        this.mVersionCode = $i0;
        this.mSecondaryEmail = $r1;
        this.mPhoneNumber = $r2;
        this.mPhoneCountryCode = $r3;
        this.mCountryList = $r4;
        this.mError = $r5;
        this.mAction = $r6;
        this.mAllowedOptions = $r7;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        RecoveryReadResponseCreator.zza(this, $r1, $i0);
    }
}
