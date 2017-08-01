package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class WebSetupConfigRequest extends AbstractSafeParcelable {
    public static final WebSetupConfigRequestCreator CREATOR = new WebSetupConfigRequestCreator();
    public final AppDescription callingAppDescription;
    final int version;

    WebSetupConfigRequest(int $i0, AppDescription $r1) throws  {
        this.version = $i0;
        this.callingAppDescription = (AppDescription) zzab.zzag($r1);
    }

    public WebSetupConfigRequest(AppDescription $r1) throws  {
        this(1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        WebSetupConfigRequestCreator.zza(this, $r1, $i0);
    }
}
