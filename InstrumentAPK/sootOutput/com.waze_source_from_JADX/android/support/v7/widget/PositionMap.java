package android.support.v7.widget;

import dalvik.annotation.Signature;
import java.util.ArrayList;

class PositionMap<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    static class ContainerHelpers {
        static final boolean[] EMPTY_BOOLEANS = new boolean[0];
        static final int[] EMPTY_INTS = new int[0];
        static final long[] EMPTY_LONGS = new long[0];
        static final Object[] EMPTY_OBJECTS = new Object[0];

        static int binarySearch(int[] r1, int r2, int r3) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.PositionMap.ContainerHelpers.binarySearch(int[], int, int):int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 6 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.PositionMap.ContainerHelpers.binarySearch(int[], int, int):int");
        }

        ContainerHelpers() throws  {
        }
    }

    public void put(@dalvik.annotation.Signature({"(ITE;)V"}) int r1, @dalvik.annotation.Signature({"(ITE;)V"}) E r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.PositionMap.put(int, java.lang.Object):void
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
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.PositionMap.put(int, java.lang.Object):void");
    }

    public PositionMap() throws  {
        this(10);
    }

    public PositionMap(int $i0) throws  {
        this.mGarbage = false;
        if ($i0 == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            $i0 = idealIntArraySize($i0);
            this.mKeys = new int[$i0];
            this.mValues = new Object[$i0];
        }
        this.mSize = 0;
    }

    public PositionMap<E> clone() throws  {
        PositionMap $r1 = null;
        try {
            PositionMap $r3 = (PositionMap) super.clone();
            $r1 = $r3;
            $r3.mKeys = (int[]) this.mKeys.clone();
            $r3.mValues = (Object[]) this.mValues.clone();
            return $r3;
        } catch (CloneNotSupportedException e) {
            return $r1;
        }
    }

    public E get(@Signature({"(I)TE;"}) int $i0) throws  {
        return get($i0, null);
    }

    public E get(@Signature({"(ITE;)TE;"}) int $i0, @Signature({"(ITE;)TE;"}) E $r1) throws  {
        $i0 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, $i0);
        return ($i0 < 0 || this.mValues[$i0] == DELETED) ? $r1 : this.mValues[$i0];
    }

    public void delete(int $i0) throws  {
        $i0 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, $i0);
        if ($i0 >= 0 && this.mValues[$i0] != DELETED) {
            this.mValues[$i0] = DELETED;
            this.mGarbage = true;
        }
    }

    public void remove(int $i0) throws  {
        delete($i0);
    }

    public void removeAt(int $i0) throws  {
        if (this.mValues[$i0] != DELETED) {
            this.mValues[$i0] = DELETED;
            this.mGarbage = true;
        }
    }

    public void removeAtRange(int $i0, int $i1) throws  {
        $i1 = Math.min(this.mSize, $i0 + $i1);
        while ($i0 < $i1) {
            removeAt($i0);
            $i0++;
        }
    }

    public void insertKeyRange(int keyStart, int count) throws  {
    }

    public void removeKeyRange(@Signature({"(", "Ljava/util/ArrayList", "<TE;>;II)V"}) ArrayList<E> arrayList, @Signature({"(", "Ljava/util/ArrayList", "<TE;>;II)V"}) int keyStart, @Signature({"(", "Ljava/util/ArrayList", "<TE;>;II)V"}) int count) throws  {
    }

    private void gc() throws  {
        int $i0 = this.mSize;
        int $i1 = 0;
        int[] $r1 = this.mKeys;
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

    public int keyAt(int $i0) throws  {
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

    public int indexOfKey(int $i0) throws  {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, $i0);
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

    public void append(@Signature({"(ITE;)V"}) int $i0, @Signature({"(ITE;)V"}) E $r1) throws  {
        if (this.mSize == 0 || $i0 > this.mKeys[this.mSize - 1]) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                gc();
            }
            int $i1 = this.mSize;
            if ($i1 >= this.mKeys.length) {
                int $i2 = idealIntArraySize($i1 + 1);
                int[] $r2 = new int[$i2];
                Object[] $r3 = new Object[$i2];
                System.arraycopy(this.mKeys, 0, $r2, 0, this.mKeys.length);
                System.arraycopy(this.mValues, 0, $r3, 0, this.mValues.length);
                this.mKeys = $r2;
                this.mValues = $r3;
            }
            this.mKeys[$i1] = $i0;
            this.mValues[$i1] = $r1;
            this.mSize = $i1 + 1;
            return;
        }
        put($i0, $r1);
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
            PositionMap $r2 = valueAt($i0);
            if ($r2 != this) {
                $r1.append($r2);
            } else {
                $r1.append("(this Map)");
            }
        }
        $r1.append('}');
        return $r1.toString();
    }

    static int idealByteArraySize(int $i0) throws  {
        for (int $i1 = 4; $i1 < 32; $i1++) {
            if ($i0 <= (1 << $i1) - 12) {
                return (1 << $i1) - 12;
            }
        }
        return $i0;
    }

    static int idealBooleanArraySize(int $i0) throws  {
        return idealByteArraySize($i0);
    }

    static int idealShortArraySize(int $i0) throws  {
        return idealByteArraySize($i0 * 2) / 2;
    }

    static int idealCharArraySize(int $i0) throws  {
        return idealByteArraySize($i0 * 2) / 2;
    }

    static int idealIntArraySize(int $i0) throws  {
        return idealByteArraySize($i0 * 4) / 4;
    }

    static int idealFloatArraySize(int $i0) throws  {
        return idealByteArraySize($i0 * 4) / 4;
    }

    static int idealObjectArraySize(int $i0) throws  {
        return idealByteArraySize($i0 * 4) / 4;
    }

    static int idealLongArraySize(int $i0) throws  {
        return idealByteArraySize($i0 * 8) / 8;
    }
}
