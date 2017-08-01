package com.google.android.gms.common.util;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import dalvik.annotation.Signature;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class zze {
    public static <K, V> Map<K, V> zza(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V $r1, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K $r2, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V $r3, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K $r4, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V $r5, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K $r6, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V $r7, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K $r8, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V $r9, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K $r10, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V $r11) throws  {
        ArrayMap $r13 = new ArrayMap(6);
        $r13.put($r0, $r1);
        $r13.put($r2, $r3);
        $r13.put($r4, $r5);
        $r13.put($r6, $r7);
        $r13.put($r8, $r9);
        $r13.put($r10, $r11);
        return Collections.unmodifiableMap($r13);
    }

    public static <T> Set<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r2) throws  {
        zza $r4 = new zza(3);
        $r4.add($r0);
        $r4.add($r1);
        $r4.add($r2);
        return Collections.unmodifiableSet($r4);
    }

    public static <T> Set<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r2, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r3) throws  {
        zza $r5 = new zza(4);
        $r5.add($r0);
        $r5.add($r1);
        $r5.add($r2);
        $r5.add($r3);
        return Collections.unmodifiableSet($r5);
    }

    private static <K, V> void zza(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">([TK;[TV;)V"}) K[] $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">([TK;[TV;)V"}) V[] $r1) throws  {
        if ($r0.length != $r1.length) {
            int $i1 = $r0.length;
            throw new IllegalArgumentException("Key and values array lengths not equal: " + $i1 + " != " + $r1.length);
        }
    }

    public static <T> List<T> zzah(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)", "Ljava/util/List", "<TT;>;"}) T $r0) throws  {
        return Collections.singletonList($r0);
    }

    public static <T> Set<T> zzai(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)", "Ljava/util/Set", "<TT;>;"}) T $r0) throws  {
        return Collections.singleton($r0);
    }

    public static <T> Set<T> zzayw() throws  {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> zzayx() throws  {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> zzb(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">([TK;[TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K[] $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">([TK;[TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V[] $r1) throws  {
        int $i1 = 0;
        zza($r0, $r1);
        int $i0 = $r0.length;
        switch ($i0) {
            case 0:
                return zzayx();
            case 1:
                return zze($r0[0], $r1[0]);
            default:
                Map $r3;
                if ($i0 <= 32) {
                    $r3 = r5;
                    ArrayMap r5 = new ArrayMap($i0);
                } else {
                    Object $r32 = r6;
                    HashMap r6 = new HashMap($i0, 1.0f);
                }
                while ($i1 < $i0) {
                    $r3.put($r0[$i1], $r1[$i1]);
                    $i1++;
                }
                return Collections.unmodifiableMap($r3);
        }
    }

    public static <T> List<T> zzc(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;)", "Ljava/util/List", "<TT;>;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;)", "Ljava/util/List", "<TT;>;"}) T $r1) throws  {
        ArrayList $r3 = new ArrayList(2);
        $r3.add($r0);
        $r3.add($r1);
        return Collections.unmodifiableList($r3);
    }

    public static <T> Set<T> zzc(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)", "Ljava/util/Set", "<TT;>;"}) T... $r0) throws  {
        switch ($r0.length) {
            case 0:
                return zzayw();
            case 1:
                return zzai($r0[0]);
            case 2:
                return zzd($r0[0], $r0[1]);
            case 3:
                return zza($r0[0], $r0[1], $r0[2]);
            case 4:
                return zza($r0[0], $r0[1], $r0[2], $r0[3]);
            default:
                AbstractSet $r1;
                if ($r0.length <= 32) {
                    $r1 = r8;
                    AbstractSet r8 = new zza(Arrays.asList($r0));
                } else {
                    $r1 = r9;
                    AbstractSet r9 = new HashSet(Arrays.asList($r0));
                }
                return Collections.unmodifiableSet($r1);
        }
    }

    public static <T> Set<T> zzd(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;)", "Ljava/util/Set", "<TT;>;"}) T $r1) throws  {
        zza $r3 = new zza(2);
        $r3.add($r0);
        $r3.add($r1);
        return Collections.unmodifiableSet($r3);
    }

    public static <K, V> Map<K, V> zze(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) K $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(TK;TV;)", "Ljava/util/Map", "<TK;TV;>;"}) V $r1) throws  {
        return Collections.singletonMap($r0, $r1);
    }

    public static boolean zzk(@Nullable @Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r0) throws  {
        return $r0 == null ? true : $r0.isEmpty();
    }
}
