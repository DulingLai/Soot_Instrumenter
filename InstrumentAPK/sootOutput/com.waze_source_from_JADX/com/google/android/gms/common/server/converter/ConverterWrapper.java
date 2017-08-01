package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class ConverterWrapper extends AbstractSafeParcelable {
    public static final zza CREATOR = new zza();
    private final StringToIntConverter KS;
    private final int mVersionCode;

    ConverterWrapper(int $i0, StringToIntConverter $r1) throws  {
        this.mVersionCode = $i0;
        this.KS = $r1;
    }

    private ConverterWrapper(StringToIntConverter $r1) throws  {
        this.mVersionCode = 1;
        this.KS = $r1;
    }

    public static ConverterWrapper zza(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;)", "Lcom/google/android/gms/common/server/converter/ConverterWrapper;"}) FieldConverter<?, ?> $r0) throws  {
        if ($r0 instanceof StringToIntConverter) {
            return new ConverterWrapper((StringToIntConverter) $r0);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza $r2 = CREATOR;
        zza.zza(this, $r1, $i0);
    }

    StringToIntConverter zzaxq() throws  {
        return this.KS;
    }

    public FieldConverter<?, ?> zzaxr() throws  {
        if (this.KS != null) {
            return this.KS;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}
