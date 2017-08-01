package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.CaptchaChallenge;
import com.google.android.gms.auth.firstparty.shared.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountNameCheckResponse extends AbstractSafeParcelable {
    public static final AccountNameCheckResponseCreator CREATOR = new AccountNameCheckResponseCreator();
    String gs;
    List<String> gt;
    String gu;
    CaptchaChallenge gv;
    final int version;

    AccountNameCheckResponse(@Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", ")V"}) List<String> $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", ")V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", ")V"}) CaptchaChallenge $r4) throws  {
        this.version = $i0;
        this.gs = $r1;
        this.gt = $r2;
        this.gu = $r3;
        this.gv = $r4;
    }

    public AccountNameCheckResponse(Status $r1) throws  {
        this($r1, Collections.EMPTY_LIST);
    }

    public AccountNameCheckResponse(@Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Status $r1, @Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) CaptchaChallenge $r3, @Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r4) throws  {
        this.version = 1;
        this.gs = ((Status) zzab.zzag($r1)).getWire();
        this.gu = $r2;
        this.gv = $r3;
        this.gt = Collections.unmodifiableList(new ArrayList($r4));
    }

    public AccountNameCheckResponse(@Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Status $r1, @Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r3) throws  {
        this($r1, $r2, null, $r3);
    }

    public AccountNameCheckResponse(@Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Status $r1, @Signature({"(", "Lcom/google/android/gms/auth/firstparty/shared/Status;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r2) throws  {
        this($r1, null, $r2);
    }

    public List<String> getAccountNameSuggestions() throws  {
        return this.gt;
    }

    public CaptchaChallenge getCaptchaChallenge() throws  {
        return this.gv;
    }

    public String getDetail() throws  {
        return this.gu;
    }

    public Status getStatus() throws  {
        return Status.fromWireCode(this.gs);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountNameCheckResponseCreator.zza(this, $r1, $i0);
    }
}
