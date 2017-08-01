package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import dalvik.annotation.Signature;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zza {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza extends RuntimeException {
        public zza(String $r1, Parcel $r2) throws  {
            int $i0 = $r2.dataPosition();
            super(new StringBuilder(String.valueOf($r1).length() + 41).append($r1).append(" Parcel: pos=").append($i0).append(" size=").append($r2.dataSize()).toString());
        }
    }

    public static int zza(Parcel $r0, int $i0) throws  {
        return ($i0 & SupportMenu.CATEGORY_MASK) != SupportMenu.CATEGORY_MASK ? ($i0 >> 16) & SupportMenu.USER_MASK : $r0.readInt();
    }

    public static <T extends Parcelable> T zza(@Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) Parcel $r0, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) int $i0, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)TT;"}) Creator<T> $r1) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        Parcelable $r2 = (Parcelable) $r1.createFromParcel($r0);
        $r0.setDataPosition($i0 + $i1);
        return $r2;
    }

    private static void zza(Parcel $r0, int $i0, int $i1) throws  {
        $i0 = zza($r0, $i0);
        if ($i0 != $i1) {
            String $r2 = String.valueOf(Integer.toHexString($i0));
            throw new zza(new StringBuilder(String.valueOf($r2).length() + 46).append("Expected size ").append($i1).append(" got ").append($i0).append(" (0x").append($r2).append(")").toString(), $r0);
        }
    }

    private static void zza(Parcel $r0, int i, int $i1, int $i2) throws  {
        if ($i1 != $i2) {
            String $r2 = String.valueOf(Integer.toHexString($i1));
            throw new zza(new StringBuilder(String.valueOf($r2).length() + 46).append("Expected size ").append($i2).append(" got ").append($i1).append(" (0x").append($r2).append(")").toString(), $r0);
        }
    }

    public static void zza(Parcel $r0, int $i0, List $r1, ClassLoader $r2) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 != 0) {
            $r0.readList($r1, $r2);
            $r0.setDataPosition($i0 + $i1);
        }
    }

    public static double[] zzaa(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        double[] $r1 = $r0.createDoubleArray();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static BigDecimal[] zzab(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        int $i2 = $r0.readInt();
        BigDecimal[] $r3 = new BigDecimal[$i2];
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            byte[] $r4 = $r0.createByteArray();
            $r3[$i3] = new BigDecimal(new BigInteger($r4), $r0.readInt());
        }
        $r0.setDataPosition($i1 + $i0);
        return $r3;
    }

    public static String[] zzac(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        String[] $r1 = $r0.createStringArray();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static ArrayList<Integer> zzad(@Signature({"(", "Landroid/os/Parcel;", "I)", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;"}) Parcel $r0, @Signature({"(", "Landroid/os/Parcel;", "I)", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;"}) int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        ArrayList $r1 = new ArrayList();
        int $i2 = $r0.readInt();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            $r1.add(Integer.valueOf($r0.readInt()));
        }
        $r0.setDataPosition($i1 + $i0);
        return $r1;
    }

    public static ArrayList<String> zzae(@Signature({"(", "Landroid/os/Parcel;", "I)", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;"}) Parcel $r0, @Signature({"(", "Landroid/os/Parcel;", "I)", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;"}) int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        ArrayList $r1 = $r0.createStringArrayList();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static Parcel zzaf(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        Parcel $r1 = Parcel.obtain();
        $r1.appendFrom($r0, $i1, $i0);
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static Parcel[] zzag(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        int $i2 = $r0.readInt();
        Parcel[] $r1 = new Parcel[$i2];
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            int $i4 = $r0.readInt();
            if ($i4 != 0) {
                int $i5 = $r0.dataPosition();
                Parcel $r2 = Parcel.obtain();
                $r2.appendFrom($r0, $i5, $i4);
                $r1[$i3] = $r2;
                $r0.setDataPosition($i4 + $i5);
            } else {
                $r1[$i3] = null;
            }
        }
        $r0.setDataPosition($i1 + $i0);
        return $r1;
    }

    public static void zzb(Parcel $r0, int $i0) throws  {
        $r0.setDataPosition(zza($r0, $i0) + $r0.dataPosition());
    }

    public static <T> T[] zzb(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)[TT;"}) Parcel $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)[TT;"}) int $i0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)[TT;"}) Creator<T> $r1) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        Object[] $r2 = $r0.createTypedArray($r1);
        $r0.setDataPosition($i0 + $i1);
        return $r2;
    }

    public static <T> ArrayList<T> zzc(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)", "Ljava/util/ArrayList", "<TT;>;"}) Parcel $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)", "Ljava/util/ArrayList", "<TT;>;"}) int $i0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/os/Parcel;", "I", "Landroid/os/Parcelable$Creator", "<TT;>;)", "Ljava/util/ArrayList", "<TT;>;"}) Creator<T> $r1) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        ArrayList $r2 = $r0.createTypedArrayList($r1);
        $r0.setDataPosition($i0 + $i1);
        return $r2;
    }

    public static boolean zzc(Parcel $r0, int $i0) throws  {
        zza($r0, $i0, 4);
        return $r0.readInt() != 0;
    }

    public static Boolean zzd(Parcel $r0, int $i0) throws  {
        int $i1 = zza($r0, $i0);
        if ($i1 == 0) {
            return null;
        }
        zza($r0, $i0, $i1, 4);
        return Boolean.valueOf($r0.readInt() != 0);
    }

    public static int zzdy(Parcel $r0) throws  {
        return $r0.readInt();
    }

    public static int zzdz(Parcel $r0) throws  {
        int $i0 = zzdy($r0);
        int $i1 = zza($r0, $i0);
        int $i2 = $r0.dataPosition();
        if (zziv($i0) != 20293) {
            String $r2 = "Expected object header. Got 0x";
            String $r3 = String.valueOf(Integer.toHexString($i0));
            throw new zza($r3.length() != 0 ? $r2.concat($r3) : new String("Expected object header. Got 0x"), $r0);
        }
        $i0 = $i2 + $i1;
        if ($i0 >= $i2 && $i0 <= $r0.dataSize()) {
            return $i0;
        }
        throw new zza("Size read is invalid start=" + $i2 + " end=" + $i0, $r0);
    }

    public static byte zze(Parcel $r0, int $i0) throws  {
        zza($r0, $i0, 4);
        return (byte) $r0.readInt();
    }

    public static short zzf(Parcel $r0, int $i0) throws  {
        zza($r0, $i0, 4);
        return (short) $r0.readInt();
    }

    public static int zzg(Parcel $r0, int $i0) throws  {
        zza($r0, $i0, 4);
        return $r0.readInt();
    }

    public static Integer zzh(Parcel $r0, int $i0) throws  {
        int $i1 = zza($r0, $i0);
        if ($i1 == 0) {
            return null;
        }
        zza($r0, $i0, $i1, 4);
        return Integer.valueOf($r0.readInt());
    }

    public static long zzi(Parcel $r0, int $i0) throws  {
        zza($r0, $i0, 8);
        return $r0.readLong();
    }

    public static int zziv(int $i0) throws  {
        return SupportMenu.USER_MASK & $i0;
    }

    public static Long zzj(Parcel $r0, int $i0) throws  {
        int $i1 = zza($r0, $i0);
        if ($i1 == 0) {
            return null;
        }
        zza($r0, $i0, $i1, 8);
        return Long.valueOf($r0.readLong());
    }

    public static BigInteger zzk(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        byte[] $r2 = $r0.createByteArray();
        $r0.setDataPosition($i0 + $i1);
        return new BigInteger($r2);
    }

    public static float zzl(Parcel $r0, int $i0) throws  {
        zza($r0, $i0, 4);
        return $r0.readFloat();
    }

    public static Float zzm(Parcel $r0, int $i0) throws  {
        int $i1 = zza($r0, $i0);
        if ($i1 == 0) {
            return null;
        }
        zza($r0, $i0, $i1, 4);
        return Float.valueOf($r0.readFloat());
    }

    public static double zzn(Parcel $r0, int $i0) throws  {
        zza($r0, $i0, 8);
        return $r0.readDouble();
    }

    public static Double zzo(Parcel $r0, int $i0) throws  {
        int $i1 = zza($r0, $i0);
        if ($i1 == 0) {
            return null;
        }
        zza($r0, $i0, $i1, 8);
        return Double.valueOf($r0.readDouble());
    }

    public static BigDecimal zzp(Parcel $r0, int $i0) throws  {
        int $i1 = zza($r0, $i0);
        int $i2 = $r0.dataPosition();
        if ($i1 == 0) {
            return null;
        }
        byte[] $r2 = $r0.createByteArray();
        $i0 = $r0.readInt();
        $r0.setDataPosition($i1 + $i2);
        return new BigDecimal(new BigInteger($r2), $i0);
    }

    public static String zzq(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        String $r1 = $r0.readString();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static IBinder zzr(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        IBinder $r1 = $r0.readStrongBinder();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static Bundle zzs(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        Bundle $r1 = $r0.readBundle();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static byte[] zzt(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        byte[] $r1 = $r0.createByteArray();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static byte[][] zzu(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        int $i2 = $r0.readInt();
        byte[][] $r1 = new byte[$i2][];
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            $r1[$i3] = $r0.createByteArray();
        }
        $r0.setDataPosition($i1 + $i0);
        return $r1;
    }

    public static boolean[] zzv(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        boolean[] $r1 = $r0.createBooleanArray();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static int[] zzw(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        int[] $r1 = $r0.createIntArray();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static long[] zzx(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        long[] $r1 = $r0.createLongArray();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }

    public static BigInteger[] zzy(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        int $i2 = $r0.readInt();
        BigInteger[] $r2 = new BigInteger[$i2];
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            $r2[$i3] = new BigInteger($r0.createByteArray());
        }
        $r0.setDataPosition($i1 + $i0);
        return $r2;
    }

    public static float[] zzz(Parcel $r0, int $i0) throws  {
        $i0 = zza($r0, $i0);
        int $i1 = $r0.dataPosition();
        if ($i0 == 0) {
            return null;
        }
        float[] $r1 = $r0.createFloatArray();
        $r0.setDataPosition($i0 + $i1);
        return $r1;
    }
}
