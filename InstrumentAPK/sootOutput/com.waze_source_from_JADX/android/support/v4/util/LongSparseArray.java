package android.support.v4.util;

import dalvik.annotation.Signature;

public class LongSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public void put(@dalvik.annotation.Signature({"(JTE;)V"}) long r1, @dalvik.annotation.Signature({"(JTE;)V"}) E r3) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.util.LongSparseArray.put(long, java.lang.Object):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 5 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LongSparseArray.put(long, java.lang.Object):void");
    }

    public LongSparseArray() throws  {
        this(10);
    }

    public LongSparseArray(int $i0) throws  {
        this.mGarbage = false;
        if ($i0 == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            $i0 = ContainerHelpers.idealLongArraySize($i0);
            this.mKeys = new long[$i0];
            this.mValues = new Object[$i0];
        }
        this.mSize = 0;
    }

    public LongSparseArray<E> clone() throws  {
        LongSparseArray $r1 = null;
        try {
            LongSparseArray $r3 = (LongSparseArray) super.clone();
            $r1 = $r3;
            $r3.mKeys = (long[]) this.mKeys.clone();
            $r3.mValues = (Object[]) this.mValues.clone();
            return $r3;
        } catch (CloneNotSupportedException e) {
            return $r1;
        }
    }

    public E get(@Signature({"(J)TE;"}) long $l0) throws  {
        return get($l0, null);
    }

    public E get(@Signature({"(JTE;)TE;"}) long $l0, @Signature({"(JTE;)TE;"}) E $r1) throws  {
        int $i1 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, $l0);
        return ($i1 < 0 || this.mValues[$i1] == DELETED) ? $r1 : this.mValues[$i1];
    }

    public void delete(long $l0) throws  {
        int $i1 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, $l0);
        if ($i1 >= 0 && this.mValues[$i1] != DELETED) {
            this.mValues[$i1] = DELETED;
            this.mGarbage = true;
        }
    }

    public void remove(long $l0) throws  {
        delete($l0);
    }

    public void removeAt(int $i0) throws  {
        if (this.mValues[$i0] != DELETED) {
            this.mValues[$i0] = DELETED;
            this.mGarbage = true;
        }
    }

    private void gc() throws  {
        int $i0 = this.mSize;
        int $i1 = 0;
        long[] $r1 = this.mKeys;
        Object[] $r3 = this.mValues;
        for (int $i2 = 0; $i2 < $i0; $i2++) {
            Object $r2 = $r3[$i2];
            if ($r2 != DELETED) {
                if ($i2 != $i1) {
                    $r1[$i1] = $r1[$i2];
                    $r3[$i1] = $r2;
                    $r3[$i2] = null;
                }
                $i1++;
            }
        }
        this.mGarbage = false;
        this.mSize = $i1;
    }

    public int size() throws  {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public long keyAt(int $i0) throws  {
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[$i0];
    }

    public E valueAt(@Signature({"(I)TE;"}) int $i0) throws  {
        if (this.mGarbage) {
            gc();
        }
        return this.mValues[$i0];
    }

    public void setValueAt(@Signature({"(ITE;)V"}) int $i0, @Signature({"(ITE;)V"}) E $r1) throws  {
        if (this.mGarbage) {
            gc();
        }
        this.mValues[$i0] = $r1;
    }

    public int indexOfKey(long $l0) throws  {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, $l0);
    }

    public int indexOfValue(@Signature({"(TE;)I"}) E $r1) throws  {
        if (this.mGarbage) {
            gc();
        }
        for (int $i0 = 0; $i0 < this.mSize; $i0++) {
            if (this.mValues[$i0] == $r1) {
                return $i0;
            }
        }
        return -1;
    }

    public void clear() throws  {
        int $i0 = this.mSize;
        Object[] $r1 = this.mValues;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r1[$i1] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(@Signature({"(JTE;)V"}) long $l0, @Signature({"(JTE;)V"}) E $r1) throws  {
        if (this.mSize == 0 || $l0 > this.mKeys[this.mSize - 1]) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                gc();
            }
            int $i1 = this.mSize;
            if ($i1 >= this.mKeys.length) {
                int $i2 = ContainerHelpers.idealLongArraySize($i1 + 1);
                long[] $r2 = new long[$i2];
                Object[] $r3 = new Object[$i2];
                System.arraycopy(this.mKeys, 0, $r2, 0, this.mKeys.length);
                Object $r6 = this.mValues;
                Object[] $r7 = this.mValues;
                System.arraycopy($r6, 0, $r3, 0, $r7.length);
                this.mKeys = $r2;
                this.mValues = $r3;
            }
            this.mKeys[$i1] = $l0;
            this.mValues[$i1] = $r1;
            this.mSize = $i1 + 1;
            return;
        }
        put($l0, $r1);
    }

    public String toString() throws  {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder $r1 = new StringBuilder(this.mSize * 28);
        $r1.append('{');
        for (int $i0 = 0; $i0 < this.mSize; $i0++) {
            if ($i0 > 0) {
                $r1.append(", ");
            }
            $r1.append(keyAt($i0));
            $r1.append('=');
            LongSparseArray $r2 = valueAt($i0);
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
