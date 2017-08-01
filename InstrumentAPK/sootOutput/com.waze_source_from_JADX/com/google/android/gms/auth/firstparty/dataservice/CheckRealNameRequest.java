package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class CheckRealNameRequest extends AbstractSafeParcelable {
    public static final CheckRealNameRequestCreator CREATOR = new CheckRealNameRequestCreator();
    AppDescription callingAppDescription;
    String firstName;
    String lastName;
    final int version;

    public CheckRealNameRequest() throws  {
        this.version = 1;
    }

    CheckRealNameRequest(int $i0, AppDescription $r1, String $r2, String $r3) throws  {
        this.version = $i0;
        this.callingAppDescription = $r1;
        this.firstName = $r2;
        this.lastName = $r3;
    }

    public AppDescription getCallingAppDescription() throws  {
        return this.callingAppDescription;
    }

    public String getFirstName() throws  {
        return this.firstName;
    }

    public String getLastName() throws  {
        return this.lastName;
    }

    public CheckRealNameRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.callingAppDescription = $r1;
        return this;
    }

    public CheckRealNameRequest setFirstName(String $r1) throws  {
        this.firstName = $r1;
        return this;
    }

    public CheckRealNameRequest setLastName(String $r1) throws  {
        this.lastName = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        CheckRealNameRequestCreator.zza(this, $r1, $i0);
    }
}
