package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.internal.view.SupportMenu;
import dalvik.annotation.Signature;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb {
    public static void zza(Parcel $r0, int $i0, byte $b1) throws  {
        zzb($r0, $i0, 4);
        $r0.writeInt($b1);
    }

    public static void zza(Parcel $r0, int $i0, double $d0) throws  {
        zzb($r0, $i0, 8);
        $r0.writeDouble($d0);
    }

    public static void zza(Parcel $r0, int $i0, float $f0) throws  {
        zzb($r0, $i0, 4);
        $r0.writeFloat($f0);
    }

    public static void zza(Parcel $r0, int $i0, long $l1) throws  {
        zzb($r0, $i0, 8);
        $r0.writeLong($l1);
    }

    public static void zza(Parcel $r0, int $i0, Bundle $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeBundle($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, IBinder $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeStrongBinder($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, Parcel $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.appendFrom($r1, 0, $r1.dataSize());
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, Parcelable $r1, int $i1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r1.writeToParcel($r0, $i1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, Boolean $r1, boolean $z0) throws  {
        byte $b1 = (byte) 0;
        if ($r1 != null) {
            zzb($r0, $i0, 4);
            if ($r1.booleanValue()) {
                $b1 = (byte) 1;
            }
            $r0.writeInt($b1);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, Double $r1, boolean $z0) throws  {
        if ($r1 != null) {
            zzb($r0, $i0, 8);
            $r0.writeDouble($r1.doubleValue());
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, Float $r1, boolean $z0) throws  {
        if ($r1 != null) {
            zzb($r0, $i0, 4);
            $r0.writeFloat($r1.floatValue());
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, Integer $r1, boolean $z0) throws  {
        if ($r1 != null) {
            zzb($r0, $i0, 4);
            $r0.writeInt($r1.intValue());
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, Long $r1, boolean $z0) throws  {
        if ($r1 != null) {
            zzb($r0, $i0, 8);
            $r0.writeLong($r1.longValue());
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, String $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeString($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, BigDecimal $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeByteArray($r1.unscaledValue().toByteArray());
            $r0.writeInt($r1.scale());
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, BigInteger $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeByteArray($r1.toByteArray());
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(@Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;Z)V"}) Parcel $r0, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;Z)V"}) int $i0, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;Z)V"}) List<Integer> $r1, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;Z)V"}) boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            int $i1 = $r1.size();
            $r0.writeInt($i1);
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                $r0.writeInt(((Integer) $r1.get($i2)).intValue());
            }
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, short $s1) throws  {
        zzb($r0, $i0, 4);
        $r0.writeInt($s1);
    }

    public static void zza(Parcel $r0, int $i0, boolean $z0) throws  {
        zzb($r0, $i0, 4);
        $r0.writeInt($z0 ? (byte) 1 : (byte) 0);
    }

    public static void zza(Parcel $r0, int $i0, byte[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeByteArray($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, double[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeDoubleArray($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, float[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeFloatArray($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, int[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeIntArray($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, long[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeLongArray($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static <T extends Parcelable> void zza(@Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I[TT;IZ)V"}) Parcel $r0, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I[TT;IZ)V"}) int $i0, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I[TT;IZ)V"}) T[] $r1, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I[TT;IZ)V"}) int $i1, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I[TT;IZ)V"}) boolean $z0) throws  {
        if ($r1 != null) {
            int $i2 = zzah($r0, $i0);
            $r0.writeInt($i0);
            for (Parcelable $r2 : $r1) {
                if ($r2 == null) {
                    $r0.writeInt(0);
                } else {
                    zza($r0, $r2, $i1);
                }
            }
            zzai($r0, $i2);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, String[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeStringArray($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, BigDecimal[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            int $i2 = zzah($r0, $i0);
            $i0 = $r1.length;
            $r0.writeInt($i0);
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r0.writeByteArray($r1[$i1].unscaledValue().toByteArray());
                $r0.writeInt($r1[$i1].scale());
            }
            zzai($r0, $i2);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, BigInteger[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            int $i2 = zzah($r0, $i0);
            $r0.writeInt($i0);
            for (BigInteger $r2 : $r1) {
                $r0.writeByteArray($r2.toByteArray());
            }
            zzai($r0, $i2);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, boolean[] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeBooleanArray($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zza(Parcel $r0, int $i0, byte[][] $r1, boolean $z0) throws  {
        if ($r1 != null) {
            int $i1 = zzah($r0, $i0);
            $r0.writeInt($i0);
            for (byte[] $r2 : $r1) {
                $r0.writeByteArray($r2);
            }
            zzai($r0, $i1);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    private static <T extends Parcelable> void zza(@Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "TT;I)V"}) Parcel $r0, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "TT;I)V"}) T $r1, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "TT;I)V"}) int $i0) throws  {
        int $i1 = $r0.dataPosition();
        $r0.writeInt(1);
        int $i2 = $r0.dataPosition();
        $r1.writeToParcel($r0, $i0);
        $i0 = $r0.dataPosition();
        $r0.setDataPosition($i1);
        $r0.writeInt($i0 - $i2);
        $r0.setDataPosition($i0);
    }

    private static int zzah(Parcel $r0, int $i0) throws  {
        $r0.writeInt(SupportMenu.CATEGORY_MASK | $i0);
        $r0.writeInt(0);
        return $r0.dataPosition();
    }

    private static void zzai(Parcel $r0, int $i0) throws  {
        int $i2 = $r0.dataPosition();
        int $i1 = $i2 - $i0;
        $r0.setDataPosition($i0 - 4);
        $r0.writeInt($i1);
        $r0.setDataPosition($i2);
    }

    public static void zzaj(Parcel $r0, int $i0) throws  {
        zzai($r0, $i0);
    }

    private static void zzb(Parcel $r0, int $i0, int $i1) throws  {
        if ($i1 >= SupportMenu.USER_MASK) {
            $r0.writeInt(SupportMenu.CATEGORY_MASK | $i0);
            $r0.writeInt($i1);
            return;
        }
        $r0.writeInt(($i1 << 16) | $i0);
    }

    public static void zzb(@Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)V"}) Parcel $r0, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)V"}) int $i0, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)V"}) List<String> $r1, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)V"}) boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeStringList($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zzc(Parcel $r0, int $i0, int $i1) throws  {
        zzb($r0, $i0, 4);
        $r0.writeInt($i1);
    }

    public static <T extends Parcelable> void zzc(@Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<TT;>;Z)V"}) Parcel $r0, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<TT;>;Z)V"}) int $i0, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<TT;>;Z)V"}) List<T> $r1, @Signature({"<T::", "Landroid/os/Parcelable;", ">(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<TT;>;Z)V"}) boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            int $i1 = $r1.size();
            $r0.writeInt($i1);
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                Parcelable $r3 = (Parcelable) $r1.get($i2);
                if ($r3 == null) {
                    $r0.writeInt(0);
                } else {
                    zza($r0, $r3, 0);
                }
            }
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zzd(@Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;Z)V"}) Parcel $r0, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;Z)V"}) int $i0, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;Z)V"}) List<Parcel> $r1, @Signature({"(", "Landroid/os/Parcel;", "I", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;Z)V"}) boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            int $i1 = $r1.size();
            $r0.writeInt($i1);
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                Parcel $r3 = (Parcel) $r1.get($i2);
                if ($r3 != null) {
                    $r0.writeInt($r3.dataSize());
                    $r0.appendFrom($r3, 0, $r3.dataSize());
                } else {
                    $r0.writeInt(0);
                }
            }
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static void zze(Parcel $r0, int $i0, List $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $i0 = zzah($r0, $i0);
            $r0.writeList($r1);
            zzai($r0, $i0);
        } else if ($z0) {
            zzb($r0, $i0, 0);
        }
    }

    public static int zzea(Parcel $r0) throws  {
        return zzah($r0, 20293);
    }
}
