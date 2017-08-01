package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class PasswordCheckRequest extends AbstractSafeParcelable {
    public static final PasswordCheckRequestCreator CREATOR = new PasswordCheckRequestCreator();
    String accountName;
    String go;
    String gp;
    AppDescription hx;
    String password;
    final int version;

    PasswordCheckRequest(int $i0, String $r1, String $r2, String $r3, String $r4, AppDescription $r5) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.password = $r2;
        this.go = $r3;
        this.gp = $r4;
        this.hx = $r5;
    }

    public PasswordCheckRequest(String $r1, String $r2) throws  {
        this.version = 1;
        this.accountName = $r1;
        this.password = $r2;
    }

    public String getAccountName() throws  {
        return this.accountName;
    }

    public AppDescription getCallingAppDescription() throws  {
        return this.hx;
    }

    public String getFirstName() throws  {
        return this.go;
    }

    public String getLastName() throws  {
        return this.gp;
    }

    public String getPassword() throws  {
        return this.password;
    }

    public PasswordCheckRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.hx = $r1;
        return this;
    }

    public PasswordCheckRequest setFirstName(String $r1) throws  {
        this.go = $r1;
        return this;
    }

    public PasswordCheckRequest setLastName(String $r1) throws  {
        this.gp = $r1;
        return this;
    }

    public PasswordCheckRequest setPassword(String $r1) throws  {
        this.password = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PasswordCheckRequestCreator.zza(this, $r1, $i0);
    }
}
