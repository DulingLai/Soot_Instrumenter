package android.support.v4.util;

import dalvik.annotation.Signature;
import java.util.Map;

public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    int indexOf(java.lang.Object r1, int r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.util.SimpleArrayMap.indexOf(java.lang.Object, int):int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.SimpleArrayMap.indexOf(java.lang.Object, int):int");
    }

    int indexOfNull() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.util.SimpleArrayMap.indexOfNull():int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.SimpleArrayMap.indexOfNull():int");
    }

    public V put(@dalvik.annotation.Signature({"(TK;TV;)TV;"}) K r1, @dalvik.annotation.Signature({"(TK;TV;)TV;"}) V r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.util.SimpleArrayMap.put(java.lang.Object, java.lang.Object):V
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.SimpleArrayMap.put(java.lang.Object, java.lang.Object):V");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void allocArrays(int r14) throws  {
        /*
        r13 = this;
        r0 = 8;
        if (r14 != r0) goto L_0x004a;
    L_0x0004:
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-enter(r1);
        r2 = mTwiceBaseCache;	 Catch:{ Throwable -> 0x0045 }
        if (r2 == 0) goto L_0x0037;
    L_0x000b:
        r2 = mTwiceBaseCache;	 Catch:{ Throwable -> 0x0045 }
        r13.mArray = r2;	 Catch:{ Throwable -> 0x0045 }
        r0 = 0;
        r3 = r2[r0];	 Catch:{ Throwable -> 0x0045 }
        r5 = r3;
        r5 = (java.lang.Object[]) r5;	 Catch:{ Throwable -> 0x0045 }
        r4 = r5;
        r4 = (java.lang.Object[]) r4;	 Catch:{ Throwable -> 0x0045 }
        mTwiceBaseCache = r4;	 Catch:{ Throwable -> 0x0045 }
        r0 = 1;
        r3 = r2[r0];	 Catch:{ Throwable -> 0x0045 }
        r7 = r3;
        r7 = (int[]) r7;	 Catch:{ Throwable -> 0x0045 }
        r6 = r7;
        r6 = (int[]) r6;	 Catch:{ Throwable -> 0x0045 }
        r13.mHashes = r6;	 Catch:{ Throwable -> 0x0045 }
        r0 = 1;
        r8 = 0;
        r2[r0] = r8;	 Catch:{ Throwable -> 0x0045 }
        r0 = 0;
        r8 = 0;
        r2[r0] = r8;	 Catch:{ Throwable -> 0x0045 }
        r14 = mTwiceBaseCacheSize;	 Catch:{ Throwable -> 0x0045 }
        r14 = r14 + -1;
        mTwiceBaseCacheSize = r14;	 Catch:{ Throwable -> 0x0045 }
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0045 }
        return;
    L_0x0037:
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0045 }
    L_0x003a:
        r6 = new int[r14];
        r13.mHashes = r6;
        r14 = r14 << 1;
        r2 = new java.lang.Object[r14];
        r13.mArray = r2;
        return;
    L_0x0045:
        r9 = move-exception;
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0045 }
        throw r9;
    L_0x004a:
        r0 = 4;
        if (r14 != r0) goto L_0x003a;
    L_0x004d:
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-enter(r1);
        r2 = mBaseCache;	 Catch:{ Throwable -> 0x0080 }
        if (r2 == 0) goto L_0x0085;
    L_0x0054:
        r2 = mBaseCache;	 Catch:{ Throwable -> 0x0080 }
        r13.mArray = r2;	 Catch:{ Throwable -> 0x0080 }
        r0 = 0;
        r3 = r2[r0];	 Catch:{ Throwable -> 0x0080 }
        r10 = r3;
        r10 = (java.lang.Object[]) r10;	 Catch:{ Throwable -> 0x0080 }
        r4 = r10;
        r4 = (java.lang.Object[]) r4;	 Catch:{ Throwable -> 0x0080 }
        mBaseCache = r4;	 Catch:{ Throwable -> 0x0080 }
        r0 = 1;
        r3 = r2[r0];	 Catch:{ Throwable -> 0x0080 }
        r11 = r3;
        r11 = (int[]) r11;	 Catch:{ Throwable -> 0x0080 }
        r6 = r11;
        r6 = (int[]) r6;	 Catch:{ Throwable -> 0x0080 }
        r13.mHashes = r6;	 Catch:{ Throwable -> 0x0080 }
        r0 = 1;
        r8 = 0;
        r2[r0] = r8;	 Catch:{ Throwable -> 0x0080 }
        r0 = 0;
        r8 = 0;
        r2[r0] = r8;	 Catch:{ Throwable -> 0x0080 }
        r14 = mBaseCacheSize;	 Catch:{ Throwable -> 0x0080 }
        r14 = r14 + -1;
        mBaseCacheSize = r14;	 Catch:{ Throwable -> 0x0080 }
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0080 }
        return;
    L_0x0080:
        r12 = move-exception;
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0080 }
        throw r12;
    L_0x0085:
        r1 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0080 }
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.SimpleArrayMap.allocArrays(int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void freeArrays(int[] r7, java.lang.Object[] r8, int r9) throws  {
        /*
        r0 = r7.length;
        r1 = 8;
        if (r0 != r1) goto L_0x0034;
    L_0x0005:
        r2 = android.support.v4.util.ArrayMap.class;
        monitor-enter(r2);
        r0 = mTwiceBaseCacheSize;	 Catch:{ Throwable -> 0x002f }
        r1 = 10;
        if (r0 >= r1) goto L_0x002b;
    L_0x000e:
        r3 = mTwiceBaseCache;	 Catch:{ Throwable -> 0x002f }
        r1 = 0;
        r8[r1] = r3;	 Catch:{ Throwable -> 0x002f }
        r1 = 1;
        r8[r1] = r7;	 Catch:{ Throwable -> 0x002f }
        r9 = r9 << 1;
        r9 = r9 + -1;
    L_0x001a:
        r1 = 2;
        if (r9 < r1) goto L_0x0023;
    L_0x001d:
        r4 = 0;
        r8[r9] = r4;	 Catch:{ Throwable -> 0x002f }
        r9 = r9 + -1;
        goto L_0x001a;
    L_0x0023:
        mTwiceBaseCache = r8;	 Catch:{ Throwable -> 0x002f }
        r9 = mTwiceBaseCacheSize;	 Catch:{ Throwable -> 0x002f }
        r9 = r9 + 1;
        mTwiceBaseCacheSize = r9;	 Catch:{ Throwable -> 0x002f }
    L_0x002b:
        r2 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x002f }
        return;
    L_0x002f:
        r5 = move-exception;
        r2 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x002f }
        throw r5;
    L_0x0034:
        r0 = r7.length;
        r1 = 4;
        if (r0 != r1) goto L_0x0067;
    L_0x0038:
        r2 = android.support.v4.util.ArrayMap.class;
        monitor-enter(r2);
        r0 = mBaseCacheSize;	 Catch:{ Throwable -> 0x0062 }
        r1 = 10;
        if (r0 >= r1) goto L_0x005e;
    L_0x0041:
        r3 = mBaseCache;	 Catch:{ Throwable -> 0x0062 }
        r1 = 0;
        r8[r1] = r3;	 Catch:{ Throwable -> 0x0062 }
        r1 = 1;
        r8[r1] = r7;	 Catch:{ Throwable -> 0x0062 }
        r9 = r9 << 1;
        r9 = r9 + -1;
    L_0x004d:
        r1 = 2;
        if (r9 < r1) goto L_0x0056;
    L_0x0050:
        r4 = 0;
        r8[r9] = r4;	 Catch:{ Throwable -> 0x0062 }
        r9 = r9 + -1;
        goto L_0x004d;
    L_0x0056:
        mBaseCache = r8;	 Catch:{ Throwable -> 0x0062 }
        r9 = mBaseCacheSize;	 Catch:{ Throwable -> 0x0062 }
        r9 = r9 + 1;
        mBaseCacheSize = r9;	 Catch:{ Throwable -> 0x0062 }
    L_0x005e:
        r2 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x0062 }
        return;
    L_0x0062:
        r6 = move-exception;
        r2 = android.support.v4.util.ArrayMap.class;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x0062 }
        throw r6;
    L_0x0067:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.SimpleArrayMap.freeArrays(int[], java.lang.Object[], int):void");
    }

    public SimpleArrayMap() throws  {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    public SimpleArrayMap(int $i0) throws  {
        if ($i0 == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays($i0);
        }
        this.mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap $r1) throws  {
        this();
        if ($r1 != null) {
            putAll($r1);
        }
    }

    public void clear() throws  {
        if (this.mSize != 0) {
            freeArrays(this.mHashes, this.mArray, this.mSize);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
        }
    }

    public void ensureCapacity(int $i0) throws  {
        if (this.mHashes.length < $i0) {
            int[] $r2 = this.mHashes;
            Object[] $r1 = this.mArray;
            allocArrays($i0);
            if (this.mSize > 0) {
                System.arraycopy($r2, 0, this.mHashes, 0, this.mSize);
                System.arraycopy($r1, 0, this.mArray, 0, this.mSize << 1);
            }
            freeArrays($r2, $r1, this.mSize);
        }
    }

    public boolean containsKey(Object $r1) throws  {
        return indexOfKey($r1) >= 0;
    }

    public int indexOfKey(Object $r1) throws  {
        return $r1 == null ? indexOfNull() : indexOf($r1, $r1.hashCode());
    }

    int indexOfValue(Object $r1) throws  {
        int $i0 = this.mSize * 2;
        Object[] $r2 = this.mArray;
        int $i1;
        if ($r1 == null) {
            for ($i1 = 1; $i1 < $i0; $i1 += 2) {
                if ($r2[$i1] == null) {
                    return $i1 >> 1;
                }
            }
        } else {
            for ($i1 = 1; $i1 < $i0; $i1 += 2) {
                if ($r1.equals($r2[$i1])) {
                    return $i1 >> 1;
                }
            }
        }
        return -1;
    }

    public boolean containsValue(Object $r1) throws  {
        return indexOfValue($r1) >= 0;
    }

    public V get(@Signature({"(", "Ljava/lang/Object;", ")TV;"}) Object $r1) throws  {
        int $i0 = indexOfKey($r1);
        return $i0 >= 0 ? this.mArray[($i0 << 1) + 1] : null;
    }

    public K keyAt(@Signature({"(I)TK;"}) int $i0) throws  {
        return this.mArray[$i0 << 1];
    }

    public V valueAt(@Signature({"(I)TV;"}) int $i0) throws  {
        return this.mArray[($i0 << 1) + 1];
    }

    public V setValueAt(@Signature({"(ITV;)TV;"}) int $i0, @Signature({"(ITV;)TV;"}) V $r1) throws  {
        $i0 = ($i0 << 1) + 1;
        Object $r2 = this.mArray[$i0];
        this.mArray[$i0] = $r1;
        return $r2;
    }

    public boolean isEmpty() throws  {
        return this.mSize <= 0;
    }

    public void putAll(@Signature({"(", "Landroid/support/v4/util/SimpleArrayMap", "<+TK;+TV;>;)V"}) SimpleArrayMap<? extends K, ? extends V> $r1) throws  {
        int $i0 = $r1.mSize;
        ensureCapacity(this.mSize + $i0);
        if (this.mSize != 0) {
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                put($r1.keyAt($i1), $r1.valueAt($i1));
            }
        } else if ($i0 > 0) {
            System.arraycopy($r1.mHashes, 0, this.mHashes, 0, $i0);
            System.arraycopy($r1.mArray, 0, this.mArray, 0, $i0 << 1);
            this.mSize = $i0;
        }
    }

    public V remove(@Signature({"(", "Ljava/lang/Object;", ")TV;"}) Object $r1) throws  {
        int $i0 = indexOfKey($r1);
        if ($i0 >= 0) {
            return removeAt($i0);
        }
        return null;
    }

    public V removeAt(@Signature({"(I)TV;"}) int $i0) throws  {
        int $i1 = 8;
        Object $r3 = this.mArray[($i0 << 1) + 1];
        if (this.mSize <= 1) {
            freeArrays(this.mHashes, this.mArray, this.mSize);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            return $r3;
        } else if (this.mHashes.length <= 8 || this.mSize >= this.mHashes.length / 3) {
            this.mSize--;
            if ($i0 < this.mSize) {
                System.arraycopy(this.mHashes, $i0 + 1, this.mHashes, $i0, this.mSize - $i0);
                System.arraycopy(this.mArray, ($i0 + 1) << 1, this.mArray, $i0 << 1, (this.mSize - $i0) << 1);
            }
            this.mArray[this.mSize << 1] = null;
            this.mArray[(this.mSize << 1) + 1] = null;
            return $r3;
        } else {
            if (this.mSize > 8) {
                $i1 = this.mSize + (this.mSize >> 1);
            }
            int[] $r2 = this.mHashes;
            Object[] $r1 = this.mArray;
            allocArrays($i1);
            this.mSize--;
            if ($i0 > 0) {
                System.arraycopy($r2, 0, this.mHashes, 0, $i0);
                System.arraycopy($r1, 0, this.mArray, 0, $i0 << 1);
            }
            if ($i0 >= this.mSize) {
                return $r3;
            }
            System.arraycopy($r2, $i0 + 1, this.mHashes, $i0, this.mSize - $i0);
            System.arraycopy($r1, ($i0 + 1) << 1, this.mArray, $i0 << 1, (this.mSize - $i0) << 1);
            return $r3;
        }
    }

    public int size() throws  {
        return this.mSize;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof Map)) {
            return false;
        }
        Map $r2 = (Map) $r1;
        if (size() != $r2.size()) {
            return false;
        }
        int $i0 = 0;
        while ($i0 < this.mSize) {
            try {
                $r1 = keyAt($i0);
                Object $r3 = valueAt($i0);
                Object $r4 = $r2.get($r1);
                if ($r3 == null) {
                    if ($r4 != null || !$r2.containsKey($r1)) {
                        return false;
                    }
                } else if (!$r3.equals($r4)) {
                    return false;
                }
                $i0++;
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e2) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() throws  {
        int[] $r2 = this.mHashes;
        Object[] $r1 = this.mArray;
        int $i3 = 0;
        int $i4 = 0;
        int $i5 = 1;
        int $i0 = this.mSize;
        while ($i4 < $i0) {
            Object $r3 = $r1[$i5];
            $i3 += ($r3 == null ? 0 : $r3.hashCode()) ^ $r2[$i4];
            $i4++;
            $i5 += 2;
        }
        return $i3;
    }

    public String toString() throws  {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder $r1 = new StringBuilder(this.mSize * 28);
        $r1.append('{');
        for (int $i0 = 0; $i0 < this.mSize; $i0++) {
            if ($i0 > 0) {
                $r1.append(", ");
            }
            SimpleArrayMap $r2 = keyAt($i0);
            if ($r2 != this) {
                $r1.append($r2);
            } else {
                $r1.append("(this Map)");
            }
            $r1.append('=');
            $r2 = valueAt($i0);
            if ($r2 != this) {
                $r1.append($r2);
            } else {
                $r1.append("(this Map)");
            }
        }
        $r1.append('}');
        return $r1.toString();
    }
}
