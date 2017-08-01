package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzc {
    public static <T extends SafeParcelable> T zza(@Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Landroid/content/Intent;", "Ljava/lang/String;", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) Intent $r0, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Landroid/content/Intent;", "Ljava/lang/String;", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) String $r1, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Landroid/content/Intent;", "Ljava/lang/String;", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) Creator<T> $r2) throws  {
        byte[] $r3 = $r0.getByteArrayExtra($r1);
        return $r3 == null ? null : zza($r3, $r2);
    }

    public static <T extends SafeParcelable> T zza(@Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">([B", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) byte[] $r0, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">([B", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) Creator<T> $r1) throws  {
        zzab.zzag($r1);
        Parcel $r2 = Parcel.obtain();
        $r2.unmarshall($r0, 0, $r0.length);
        $r2.setDataPosition(0);
        SafeParcelable $r4 = (SafeParcelable) $r1.createFromParcel($r2);
        $r2.recycle();
        return $r4;
    }

    public static <T extends SafeParcelable> void zza(@Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(TT;", "Landroid/content/Intent;", "Ljava/lang/String;", ")V"}) T $r0, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(TT;", "Landroid/content/Intent;", "Ljava/lang/String;", ")V"}) Intent $r1, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(TT;", "Landroid/content/Intent;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        $r1.putExtra($r2, zza($r0));
    }

    public static <T extends SafeParcelable> void zza(@Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Ljava/lang/Iterable", "<TT;>;", "Landroid/content/Intent;", "Ljava/lang/String;", ")V"}) Iterable<T> $r0, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Ljava/lang/Iterable", "<TT;>;", "Landroid/content/Intent;", "Ljava/lang/String;", ")V"}) Intent $r1, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Ljava/lang/Iterable", "<TT;>;", "Landroid/content/Intent;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        ArrayList $r3 = new ArrayList();
        for (T zza : $r0) {
            $r3.add(zza(zza));
        }
        $r1.putExtra($r2, $r3);
    }

    public static <T extends SafeParcelable> byte[] zza(@Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(TT;)[B"}) T $r0) throws  {
        Parcel $r1 = Parcel.obtain();
        $r0.writeToParcel($r1, 0);
        byte[] $r2 = $r1.marshall();
        $r1.recycle();
        return $r2;
    }

    public static <T extends SafeParcelable> ArrayList<T> zzb(@Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Landroid/content/Intent;", "Ljava/lang/String;", "Landroid/os/Parcelable$Creator", "<TT;>;)", "Ljava/util/ArrayList", "<TT;>;"}) Intent $r0, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Landroid/content/Intent;", "Ljava/lang/String;", "Landroid/os/Parcelable$Creator", "<TT;>;)", "Ljava/util/ArrayList", "<TT;>;"}) String $r1, @Signature({"<T::", "Lcom/google/android/gms/common/internal/safeparcel/SafeParcelable;", ">(", "Landroid/content/Intent;", "Ljava/lang/String;", "Landroid/os/Parcelable$Creator", "<TT;>;)", "Ljava/util/ArrayList", "<TT;>;"}) Creator<T> $r2) throws  {
        ArrayList $r5 = (ArrayList) $r0.getSerializableExtra($r1);
        if ($r5 == null) {
            return null;
        }
        ArrayList $r3 = new ArrayList($r5.size());
        Iterator $r6 = $r5.iterator();
        while ($r6.hasNext()) {
            $r3.add(zza((byte[]) $r6.next(), $r2));
        }
        return $r3;
    }
}
