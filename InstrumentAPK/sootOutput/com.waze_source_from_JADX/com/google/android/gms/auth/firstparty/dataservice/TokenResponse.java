package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.TokenData;
import com.google.android.gms.auth.TokenData.zza;
import com.google.android.gms.auth.firstparty.shared.CaptchaChallenge;
import com.google.android.gms.auth.firstparty.shared.ScopeDetail;
import com.google.android.gms.auth.firstparty.shared.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class TokenResponse extends AbstractSafeParcelable {
    public static final TokenResponseCreator CREATOR = new TokenResponseCreator();
    Account account;
    @Deprecated
    String accountName;
    String firstName;
    @Deprecated
    String gN;
    String gs;
    String gu;
    CaptchaChallenge gv;
    String hH;
    String hJ;
    String hK;
    boolean hL;
    boolean hM;
    boolean hN;
    boolean hO;
    List<ScopeDetail> hP;
    boolean hQ;
    PostSignInData hR;
    String hS;
    TokenData hT;
    Bundle hU;
    String hq;
    String hv;
    String lastName;
    int title;
    final int version;

    public TokenResponse() throws  {
        this.hU = new Bundle();
        this.version = 6;
        this.hP = new ArrayList();
    }

    TokenResponse(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r7, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r8, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) boolean $z1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) boolean $z2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) boolean $z3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) CaptchaChallenge $r9, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) List<ScopeDetail> $r18, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r10, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r11, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) boolean $z4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) int $i1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) PostSignInData $r12, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) Account $r13, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r14, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) TokenData $r15, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) Bundle $r16, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "ZZZZ", "Lcom/google/android/gms/auth/firstparty/shared/CaptchaChallenge;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "ZI", "Lcom/google/android/gms/auth/firstparty/dataservice/PostSignInData;", "Landroid/accounts/Account;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/TokenData;", "Landroid/os/Bundle;", "Ljava/lang/String;", ")V"}) String $r17) throws  {
        List $r182;
        this.hU = new Bundle();
        this.version = $i0;
        this.gs = $r2;
        this.gN = $r3;
        this.hJ = $r4;
        this.gu = $r5;
        this.hK = $r6;
        this.firstName = $r7;
        this.lastName = $r8;
        this.hL = $z0;
        this.hM = $z1;
        this.hN = $z2;
        this.hO = $z3;
        this.gv = $r9;
        if ($r18 == null) {
            $r182 = r22;
            ArrayList r22 = new ArrayList();
        }
        this.hP = $r182;
        this.hv = $r10;
        this.hq = $r11;
        this.hQ = $z4;
        this.title = $i1;
        this.hR = $r12;
        this.hS = $r14;
        this.hU = $r16;
        this.hH = $r17;
        if ($r13 != null) {
            setAccount($r13);
        } else {
            setAccountName($r1);
        }
        if ($r3 != null) {
            zza(new zza().zzem($r3).zzadv());
        } else {
            zza($r15);
        }
    }

    public Account getAccount() throws  {
        return this.account;
    }

    @Deprecated
    public String getAccountName() throws  {
        Account $r1 = getAccount();
        return $r1 == null ? null : $r1.name;
    }

    public CaptchaChallenge getCaptchaChallenge() throws  {
        return this.gv;
    }

    public String getDetail() throws  {
        return this.gu;
    }

    @Nullable
    public String getDmStatus() throws  {
        return this.hS;
    }

    public String getFirstName() throws  {
        return this.firstName;
    }

    public String getLastName() throws  {
        return this.lastName;
    }

    public String getPicasaUser() throws  {
        return this.hK;
    }

    public PostSignInData getPostSignInData() throws  {
        return this.hR;
    }

    public String getRopRevision() throws  {
        return this.hq;
    }

    public String getRopText() throws  {
        return this.hv;
    }

    public List<ScopeDetail> getScopeData() throws  {
        return Collections.unmodifiableList(this.hP);
    }

    public String getSignInUrl() throws  {
        return this.hJ;
    }

    public Status getStatus() throws  {
        return Status.fromWireCode(this.gs);
    }

    public int getTitle() throws  {
        return this.title;
    }

    public String getToken() throws  {
        return this.gN;
    }

    public boolean hasTitle() throws  {
        return this.hQ;
    }

    public boolean isBrowserSignInSuggested() throws  {
        return this.hO;
    }

    public boolean isEsMobileServiceEnabled() throws  {
        return this.hN;
    }

    public boolean isGPlusServiceAllowed() throws  {
        return this.hL;
    }

    public boolean isGPlusServiceEnabled() throws  {
        return this.hM;
    }

    public TokenResponse setAccount(Account $r1) throws  {
        this.account = (Account) zzab.zzb((Object) $r1, (Object) "Account can't be null.");
        this.accountName = $r1.name;
        return this;
    }

    @Deprecated
    public TokenResponse setAccountName(@Deprecated String $r0) throws  {
        if (!TextUtils.isEmpty($r0)) {
            return setAccount(new Account($r0, "com.google"));
        }
        this.accountName = null;
        this.account = null;
        return this;
    }

    public TokenResponse setBrowserSignInSuggested(boolean $z0) throws  {
        this.hO = $z0;
        return this;
    }

    public TokenResponse setCaptchaChallenge(CaptchaChallenge $r1) throws  {
        this.gv = $r1;
        return this;
    }

    public TokenResponse setDetail(String $r1) throws  {
        this.gu = $r1;
        return this;
    }

    public TokenResponse setDmStatus(String $r1) throws  {
        this.hS = $r1;
        return this;
    }

    public TokenResponse setEsMobileServiceEnabled(boolean $z0) throws  {
        this.hN = $z0;
        return this;
    }

    public TokenResponse setFirstName(String $r1) throws  {
        this.firstName = $r1;
        return this;
    }

    public TokenResponse setGPlusServiceAllowed(boolean $z0) throws  {
        this.hL = $z0;
        return this;
    }

    public TokenResponse setGPlusServiceEnabled(boolean $z0) throws  {
        this.hM = $z0;
        return this;
    }

    public TokenResponse setHasTitle(boolean $z0) throws  {
        this.hQ = $z0;
        return this;
    }

    public TokenResponse setLastName(String $r1) throws  {
        this.lastName = $r1;
        return this;
    }

    public TokenResponse setPicasaUser(String $r1) throws  {
        this.hK = $r1;
        return this;
    }

    public TokenResponse setPostSignInData(PostSignInData $r1) throws  {
        this.hR = $r1;
        return this;
    }

    public TokenResponse setRopRevision(String $r1) throws  {
        this.hq = $r1;
        return this;
    }

    public TokenResponse setRopText(String $r1) throws  {
        this.hv = $r1;
        return this;
    }

    public TokenResponse setScopeData(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/shared/ScopeDetail;", ">;)", "Lcom/google/android/gms/auth/firstparty/dataservice/TokenResponse;"}) List<ScopeDetail> $r1) throws  {
        this.hP.clear();
        this.hP.addAll($r1);
        return this;
    }

    public TokenResponse setSignInUrl(String $r1) throws  {
        this.hJ = $r1;
        return this;
    }

    public TokenResponse setStatus(Status $r1) throws  {
        this.gs = ((Status) zzab.zzag($r1)).getWire();
        return this;
    }

    public TokenResponse setTitle(int $i0) throws  {
        this.title = $i0;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        TokenResponseCreator.zza(this, $r1, $i0);
    }

    public TokenResponse zza(TokenData $r1) throws  {
        if ($r1 == null) {
            this.gN = null;
            this.hT = null;
            return this;
        }
        this.gN = $r1.getToken();
        this.hT = $r1;
        return this;
    }
}
