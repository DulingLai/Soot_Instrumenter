package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ValidateAccountCredentialsResponse extends AbstractSafeParcelable {
    public static final ValidateAccountCredentialsResponseCreator CREATOR = new ValidateAccountCredentialsResponseCreator();
    public static final int STATUS_ERROR_INVALID_CREDENTIALS = 3;
    public static final int STATUS_ERROR_NETWORK = 2;
    public static final int STATUS_ERROR_UNKNOWN = 1;
    public static final int STATUS_OK = 0;
    public final String accountId;
    public final int status;
    final int version;

    public ValidateAccountCredentialsResponse(int $i0) throws  {
        this(1, $i0, null);
    }

    ValidateAccountCredentialsResponse(int $i0, int $i1, String $r1) throws  {
        this.version = $i0;
        this.status = $i1;
        this.accountId = $r1;
    }

    public ValidateAccountCredentialsResponse(String $r1) throws  {
        this(1, 0, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ValidateAccountCredentialsResponseCreator.zza(this, $r1, $i0);
    }
}
