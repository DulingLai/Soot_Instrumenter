package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class WebSetupConfig extends AbstractSafeParcelable {
    public static final WebSetupConfigCreator CREATOR = new WebSetupConfigCreator();
    public final String url;
    final int version;

    WebSetupConfig(int $i0, String $r1) throws  {
        this.version = $i0;
        this.url = $r1;
    }

    public WebSetupConfig(String $r1) throws  {
        this(1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        WebSetupConfigCreator.zza(this, $r1, $i0);
    }
}
