package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleAccountData extends AbstractSafeParcelable {
    public static final GoogleAccountDataCreator CREATOR = new GoogleAccountDataCreator();
    public Account account;
    @Deprecated
    String accountName;
    public String firstName;
    boolean gO;
    public String lastName;
    public List<String> services;
    final int version;

    GoogleAccountData(@Signature({"(I", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) boolean $z0, @Signature({"(I", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) List<String> $r2, @Signature({"(I", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r4, @Signature({"(I", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) Account $r5) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.gO = $z0;
        this.services = $r2;
        this.firstName = $r3;
        this.lastName = $r4;
        if ($r5 != null || TextUtils.isEmpty($r1)) {
            this.account = $r5;
        } else {
            this.account = new Account($r1, "com.google");
        }
    }

    public GoogleAccountData(@Signature({"(", "Landroid/accounts/Account;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Account $r1, @Signature({"(", "Landroid/accounts/Account;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Landroid/accounts/Account;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<String> $r2, @Signature({"(", "Landroid/accounts/Account;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Landroid/accounts/Account;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
        this.version = 2;
        this.account = $r1;
        this.gO = $z0;
        this.services = $r2 == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(new ArrayList($r2));
        this.firstName = $r3;
        this.lastName = $r4;
    }

    @Deprecated
    public GoogleAccountData(@Deprecated @Signature({"(", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r1, @Deprecated @Signature({"(", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Deprecated @Signature({"(", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<String> $r2, @Deprecated @Signature({"(", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Deprecated @Signature({"(", "Ljava/lang/String;", "Z", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
        this(new Account($r1, "com.google"), $z0, (List) $r2, $r3, $r4);
    }

    public Account getAccount() throws  {
        return this.account;
    }

    @Deprecated
    public String getAccountName() throws  {
        return this.account == null ? this.accountName : this.account.name;
    }

    public List<String> getServices() throws  {
        return this.services;
    }

    public boolean isBrowserFlowRequired() throws  {
        return this.gO;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        GoogleAccountDataCreator.zza(this, $r1, $i0);
    }
}
