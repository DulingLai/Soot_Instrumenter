package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder.Builder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd<T extends SafeParcelable> extends AbstractDataBuffer<T> {
    private static final String[] GQ = new String[]{"data"};
    private final Creator<T> GR;

    public zzd(@Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/os/Parcelable$Creator", "<TT;>;)V"}) DataHolder $r1, @Signature({"(", "Lcom/google/android/gms/common/data/DataHolder;", "Landroid/os/Parcelable$Creator", "<TT;>;)V"}) Creator<T> $r2) throws  {
        super($r1);
        this.GR = $r2;
    }

    public static <T extends SafeParcelable> void zza(@Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Lcom/google/android/gms/common/data/DataHolder$Builder;", "TT;)V"}) Builder $r0, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Lcom/google/android/gms/common/data/DataHolder$Builder;", "TT;)V"}) T $r1) throws  {
        Parcel $r2 = Parcel.obtain();
        $r1.writeToParcel($r2, 0);
        ContentValues $r3 = new ContentValues();
        $r3.put("data", $r2.marshall());
        $r0.withRow($r3);
        $r2.recycle();
    }

    public static Builder zzavd() throws  {
        return DataHolder.builder(GQ);
    }

    public /* synthetic */ Object get(int $i0) throws  {
        return zzib($i0);
    }

    public T zzib(@Signature({"(I)TT;"}) int $i0) throws  {
        byte[] $r3 = this.DW.getByteArray("data", $i0, this.DW.zzic($i0));
        Parcel $r4 = Parcel.obtain();
        $r4.unmarshall($r3, 0, $r3.length);
        $r4.setDataPosition(0);
        SafeParcelable $r7 = (SafeParcelable) this.GR.createFromParcel($r4);
        $r4.recycle();
        return $r7;
    }
}
