package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavs<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = (!zzavs.class.desiredAssertionStatus());
    private static final Comparator<Comparable> bYy = new C07861();
    Comparator<? super K> bJJ;
    final zzd<K, V> bYA;
    private zza bYB;
    private zzb bYC;
    zzd<K, V> bYz;
    int modCount;
    int size;

    /* compiled from: dalvik_source_com.waze.apk */
    static class C07861 implements Comparator<Comparable> {
        C07861() throws  {
        }

        public /* synthetic */ int compare(Object $r1, Object $r2) throws  {
            return zza((Comparable) $r1, (Comparable) $r2);
        }

        public int zza(Comparable $r1, Comparable $r2) throws  {
            return $r1.compareTo($r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private abstract class zzc<T> implements Iterator<T> {
        final /* synthetic */ zzavs bYD;
        zzd<K, V> bYG;
        zzd<K, V> bYH;
        int bYI;

        private zzc(zzavs $r1) throws  {
            this.bYD = $r1;
            this.bYG = this.bYD.bYA.bYG;
            this.bYH = null;
            this.bYI = this.bYD.modCount;
        }

        public final boolean hasNext() throws  {
            return this.bYG != this.bYD.bYA;
        }

        final zzd<K, V> hx() throws  {
            zzd $r1 = this.bYG;
            if ($r1 == this.bYD.bYA) {
                throw new NoSuchElementException();
            } else if (this.bYD.modCount != this.bYI) {
                throw new ConcurrentModificationException();
            } else {
                this.bYG = $r1.bYG;
                this.bYH = $r1;
                return $r1;
            }
        }

        public final void remove() throws  {
            if (this.bYH == null) {
                throw new IllegalStateException();
            }
            this.bYD.zza(this.bYH, true);
            this.bYH = null;
            this.bYI = this.bYD.modCount;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class zza extends AbstractSet<Entry<K, V>> {
        final /* synthetic */ zzavs bYD;

        /* compiled from: dalvik_source_com.waze.apk */
        class C07871 extends zzc<Entry<K, V>> {
            final /* synthetic */ zza bYE;

            C07871(zza $r1) throws  {
                this.bYE = $r1;
                super();
            }

            public Entry<K, V> next() throws  {
                return hx();
            }
        }

        zza(zzavs $r1) throws  {
            this.bYD = $r1;
        }

        public void clear() throws  {
            this.bYD.clear();
        }

        public boolean contains(Object $r2) throws  {
            return ($r2 instanceof Entry) && this.bYD.zzc((Entry) $r2) != null;
        }

        public Iterator<Entry<K, V>> iterator() throws  {
            return new C07871(this);
        }

        public boolean remove(Object $r2) throws  {
            if (!($r2 instanceof Entry)) {
                return false;
            }
            zzd $r1 = this.bYD.zzc((Entry) $r2);
            if ($r1 == null) {
                return false;
            }
            this.bYD.zza($r1, true);
            return true;
        }

        public int size() throws  {
            return this.bYD.size;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    final class zzb extends AbstractSet<K> {
        final /* synthetic */ zzavs bYD;

        /* compiled from: dalvik_source_com.waze.apk */
        class C07881 extends zzc<K> {
            final /* synthetic */ zzb bYF;

            C07881(zzb $r1) throws  {
                this.bYF = $r1;
                super();
            }

            public K next() throws  {
                return hx().bJX;
            }
        }

        zzb(zzavs $r1) throws  {
            this.bYD = $r1;
        }

        public void clear() throws  {
            this.bYD.clear();
        }

        public boolean contains(Object $r1) throws  {
            return this.bYD.containsKey($r1);
        }

        public Iterator<K> iterator() throws  {
            return new C07881(this);
        }

        public boolean remove(Object $r1) throws  {
            return this.bYD.zzdd($r1) != null;
        }

        public int size() throws  {
            return this.bYD.size;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zzd<K, V> implements Entry<K, V> {
        V aVj;
        final K bJX;
        zzd<K, V> bYG;
        zzd<K, V> bYJ;
        zzd<K, V> bYK;
        zzd<K, V> bYL;
        zzd<K, V> bYM;
        int height;

        zzd() throws  {
            this.bJX = null;
            this.bYM = this;
            this.bYG = this;
        }

        zzd(@Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;TK;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) zzd<K, V> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;TK;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) K $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;TK;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) zzd<K, V> $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;TK;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) zzd<K, V> $r4) throws  {
            this.bYJ = $r1;
            this.bJX = $r2;
            this.height = 1;
            this.bYG = $r3;
            this.bYM = $r4;
            $r4.bYG = this;
            $r3.bYM = this;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Entry)) {
                return false;
            }
            Entry $r2 = (Entry) $r1;
            if (this.bJX == null) {
                if ($r2.getKey() != null) {
                    return false;
                }
            } else if (!this.bJX.equals($r2.getKey())) {
                return false;
            }
            if (this.aVj == null) {
                if ($r2.getValue() != null) {
                    return false;
                }
            } else if (!this.aVj.equals($r2.getValue())) {
                return false;
            }
            return true;
        }

        public K getKey() throws  {
            return this.bJX;
        }

        public V getValue() throws  {
            return this.aVj;
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = this.bJX == null ? 0 : this.bJX.hashCode();
            if (this.aVj != null) {
                $i0 = this.aVj.hashCode();
            }
            return $i1 ^ $i0;
        }

        public zzd<K, V> hy() throws  {
            for (zzd $r2 = $r1.bYK; $r2 != null; $r2 = $r2.bYK) {
                $r1 = $r2;
            }
            return $r1;
        }

        public zzd<K, V> hz() throws  {
            for (zzd $r2 = $r1.bYL; $r2 != null; $r2 = $r2.bYL) {
                $r1 = $r2;
            }
            return $r1;
        }

        public V setValue(@Signature({"(TV;)TV;"}) V $r1) throws  {
            Object r2 = this.aVj;
            this.aVj = $r1;
            return r2;
        }

        public String toString() throws  {
            String $r2 = String.valueOf(this.bJX);
            String $r3 = String.valueOf(this.aVj);
            return new StringBuilder((String.valueOf($r2).length() + 1) + String.valueOf($r3).length()).append($r2).append("=").append($r3).toString();
        }
    }

    public zzavs() throws  {
        this(bYy);
    }

    public zzavs(@Signature({"(", "Ljava/util/Comparator", "<-TK;>;)V"}) Comparator<? super K> $r1) throws  {
        Comparator $r12;
        this.size = 0;
        this.modCount = 0;
        this.bYA = new zzd();
        if ($r1 == null) {
            $r12 = bYy;
        }
        this.bJJ = $r12;
    }

    private boolean equal(Object $r1, Object $r2) throws  {
        return $r1 == $r2 || ($r1 != null && $r1.equals($r2));
    }

    private void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) zzd<K, V> $r1) throws  {
        int $i0 = 0;
        zzd $r5 = $r1.bYK;
        zzd $r2 = $r1.bYL;
        zzd $r3 = $r2.bYK;
        zzd $r4 = $r2.bYL;
        $r1.bYL = $r3;
        if ($r3 != null) {
            $r3.bYJ = $r1;
        }
        zza((zzd) $r1, $r2);
        $r2.bYK = $r1;
        $r1.bYJ = $r2;
        $r1.height = Math.max($r5 != null ? $r5.height : 0, $r3 != null ? $r3.height : 0) + 1;
        int $i1 = $r1.height;
        if ($r4 != null) {
            $i0 = $r4.height;
        }
        $r2.height = Math.max($i1, $i0) + 1;
    }

    private void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) zzd<K, V> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) zzd<K, V> $r2) throws  {
        zzd $r3 = $r1.bYJ;
        $r1.bYJ = null;
        if ($r2 != null) {
            $r2.bYJ = $r3;
        }
        if ($r3 == null) {
            this.bYz = $r2;
        } else if ($r3.bYK == $r1) {
            $r3.bYK = $r2;
        } else if ($assertionsDisabled || $r3.bYL == $r1) {
            $r3.bYL = $r2;
        } else {
            throw new AssertionError();
        }
    }

    private void zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;)V"}) zzd<K, V> $r1) throws  {
        int $i0 = 0;
        zzd $r2 = $r1.bYK;
        zzd $r5 = $r1.bYL;
        zzd $r3 = $r2.bYK;
        zzd $r4 = $r2.bYL;
        $r1.bYK = $r4;
        if ($r4 != null) {
            $r4.bYJ = $r1;
        }
        zza((zzd) $r1, $r2);
        $r2.bYL = $r1;
        $r1.bYJ = $r2;
        $r1.height = Math.max($r5 != null ? $r5.height : 0, $r4 != null ? $r4.height : 0) + 1;
        int $i1 = $r1.height;
        if ($r3 != null) {
            $i0 = $r3.height;
        }
        $r2.height = Math.max($i1, $i0) + 1;
    }

    private void zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;Z)V"}) zzd<K, V> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;Z)V"}) boolean $z0) throws  {
        zzd $r12;
        while ($r12 != null) {
            zzd $r2 = $r12.bYK;
            zzd $r3 = $r12.bYL;
            int $i1 = $r2 != null ? $r2.height : 0;
            int $i2 = $r3 != null ? $r3.height : 0;
            int $i0 = $i1 - $i2;
            zzd $r4;
            if ($i0 == -2) {
                $r2 = $r3.bYK;
                $r4 = $r3.bYL;
                $i0 = ($r2 != null ? $r2.height : 0) - ($r4 != null ? $r4.height : 0);
                if ($i0 == -1 || ($i0 == 0 && !$z0)) {
                    zza($r12);
                } else if ($assertionsDisabled || $i0 == 1) {
                    zzb($r3);
                    zza($r12);
                } else {
                    throw new AssertionError();
                }
                if ($z0) {
                    return;
                }
            } else if ($i0 == 2) {
                $r3 = $r2.bYK;
                $r4 = $r2.bYL;
                $i0 = ($r3 != null ? $r3.height : 0) - ($r4 != null ? $r4.height : 0);
                if ($i0 == 1 || ($i0 == 0 && !$z0)) {
                    zzb($r12);
                } else if ($assertionsDisabled || $i0 == -1) {
                    zza($r2);
                    zzb($r12);
                } else {
                    throw new AssertionError();
                }
                if ($z0) {
                    return;
                }
            } else if ($i0 == 0) {
                $r12.height = $i1 + 1;
                if ($z0) {
                    return;
                }
            } else if ($assertionsDisabled || $i0 == -1 || $i0 == 1) {
                $r12.height = Math.max($i1, $i2) + 1;
                if (!$z0) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            $r12 = $r12.bYJ;
        }
    }

    public void clear() throws  {
        this.bYz = null;
        this.size = 0;
        this.modCount++;
        zzd $r1 = this.bYA;
        $r1.bYM = $r1;
        $r1.bYG = $r1;
    }

    public boolean containsKey(Object $r1) throws  {
        return zzdc($r1) != null;
    }

    public Set<Entry<K, V>> entrySet() throws  {
        zza $r1 = this.bYB;
        if ($r1 != null) {
            return $r1;
        }
        $r1 = new zza(this);
        this.bYB = $r1;
        return $r1;
    }

    public V get(@Signature({"(", "Ljava/lang/Object;", ")TV;"}) Object $r1) throws  {
        zzd $r2 = zzdc($r1);
        return $r2 != null ? $r2.aVj : null;
    }

    public Set<K> keySet() throws  {
        zzb $r1 = this.bYC;
        if ($r1 != null) {
            return $r1;
        }
        $r1 = new zzb(this);
        this.bYC = $r1;
        return $r1;
    }

    public V put(@Signature({"(TK;TV;)TV;"}) K $r1, @Signature({"(TK;TV;)TV;"}) V $r2) throws  {
        if ($r1 == null) {
            throw new NullPointerException("key == null");
        }
        zzd $r3 = zza((Object) $r1, true);
        Object $r12 = $r3.aVj;
        $r3.aVj = $r2;
        return $r12;
    }

    public V remove(@Signature({"(", "Ljava/lang/Object;", ")TV;"}) Object $r1) throws  {
        zzd $r2 = zzdd($r1);
        return $r2 != null ? $r2.aVj : null;
    }

    public int size() throws  {
        return this.size;
    }

    zzd<K, V> zza(@Signature({"(TK;Z)", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;"}) K $r1, @Signature({"(TK;Z)", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;"}) boolean $z0) throws  {
        int $i0;
        zzd $r7;
        Comparator comparator = this.bJJ;
        this = this;
        zzd $r3 = this.bYz;
        if ($r3 != null) {
            Comparable $r5 = comparator == bYy ? (Comparable) $r1 : null;
            while (true) {
                $i0 = $r5 != null ? $r5.compareTo($r3.bJX) : comparator.compare($r1, $r3.bJX);
                if ($i0 == 0) {
                    return $r3;
                }
                $r7 = $i0 < 0 ? $r3.bYK : $r3.bYL;
                if ($r7 == null) {
                    break;
                }
                $r3 = $r7;
            }
        } else {
            $i0 = 0;
        }
        if (!$z0) {
            return null;
        }
        zzd $r11;
        $r7 = this.bYA;
        if ($r3 != null) {
            $r11 = new zzd($r3, $r1, $r7, $r7.bYM);
            if ($i0 < 0) {
                $r3.bYK = $r11;
            } else {
                $r3.bYL = $r11;
            }
            zzb($r3, true);
        } else if (comparator != bYy || ($r1 instanceof Comparable)) {
            $r11 = new zzd($r3, $r1, $r7, $r7.bYM);
            this.bYz = $r11;
        } else {
            throw new ClassCastException(String.valueOf($r1.getClass().getName()).concat(" is not Comparable"));
        }
        this.size++;
        this.modCount++;
        return $r11;
    }

    void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;Z)V"}) zzd<K, V> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;Z)V"}) boolean $z0) throws  {
        int $i1 = 0;
        if ($z0) {
            $r1.bYM.bYG = $r1.bYG;
            $r1.bYG.bYM = $r1.bYM;
        }
        zzd $r2 = $r1.bYK;
        zzd $r3 = $r1.bYL;
        zzd $r4 = $r1.bYJ;
        if ($r2 == null || $r3 == null) {
            if ($r2 != null) {
                zza((zzd) $r1, $r2);
                $r1.bYK = null;
            } else if ($r3 != null) {
                zza((zzd) $r1, $r3);
                $r1.bYL = null;
            } else {
                zza((zzd) $r1, null);
            }
            zzb($r4, false);
            this.size--;
            this.modCount++;
            return;
        }
        int $i0;
        $r2 = $r2.height > $r3.height ? $r2.hz() : $r3.hy();
        zza($r2, false);
        $r3 = $r1.bYK;
        if ($r3 != null) {
            $i0 = $r3.height;
            $r2.bYK = $r3;
            $r3.bYJ = $r2;
            $r1.bYK = null;
        } else {
            $i0 = 0;
        }
        $r3 = $r1.bYL;
        if ($r3 != null) {
            $i1 = $r3.height;
            $r2.bYL = $r3;
            $r3.bYJ = $r2;
            $r1.bYL = null;
        }
        $r2.height = Math.max($i0, $i1) + 1;
        zza((zzd) $r1, $r2);
    }

    zzd<K, V> zzc(@Signature({"(", "Ljava/util/Map$Entry", "<**>;)", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;"}) Entry<?, ?> $r1) throws  {
        zzd $r3 = zzdc($r1.getKey());
        boolean $z0 = $r3 != null && equal($r3.aVj, $r1.getValue());
        return $z0 ? $r3 : null;
    }

    zzd<K, V> zzdc(@Signature({"(", "Ljava/lang/Object;", ")", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;"}) Object $r1) throws  {
        if ($r1 == null) {
            return null;
        }
        try {
            return zza($r1, false);
        } catch (ClassCastException e) {
            return null;
        }
    }

    zzd<K, V> zzdd(@Signature({"(", "Ljava/lang/Object;", ")", "Lcom/google/android/gms/internal/zzavs$zzd", "<TK;TV;>;"}) Object $r1) throws  {
        zzd $r2 = zzdc($r1);
        if ($r2 == null) {
            return $r2;
        }
        zza($r2, true);
        return $r2;
    }
}
