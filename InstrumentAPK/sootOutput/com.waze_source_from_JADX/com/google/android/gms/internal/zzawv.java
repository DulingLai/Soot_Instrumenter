package com.google.android.gms.internal;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawv implements Cloneable {
    private static final zzaww cbv = new zzaww();
    private boolean cbw;
    private int[] cbx;
    private zzaww[] cby;
    private int mSize;

    zzawv() throws  {
        this(10);
    }

    zzawv(int $i0) throws  {
        this.cbw = false;
        $i0 = idealIntArraySize($i0);
        this.cbx = new int[$i0];
        this.cby = new zzaww[$i0];
        this.mSize = 0;
    }

    private int idealByteArraySize(int $i0) throws  {
        for (int $i1 = 4; $i1 < 32; $i1++) {
            if ($i0 <= (1 << $i1) - 12) {
                return (1 << $i1) - 12;
            }
        }
        return $i0;
    }

    private int idealIntArraySize(int $i0) throws  {
        return idealByteArraySize($i0 * 4) / 4;
    }

    private boolean zza(int[] $r1, int[] $r2, int $i0) throws  {
        for (int $i3 = 0; $i3 < $i0; $i3++) {
            if ($r1[$i3] != $r2[$i3]) {
                return false;
            }
        }
        return true;
    }

    private boolean zza(zzaww[] $r1, zzaww[] $r2, int $i0) throws  {
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (!$r1[$i1].equals($r2[$i1])) {
                return false;
            }
        }
        return true;
    }

    private int zzasl(int r1) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.internal.zzawv.zzasl(int):int
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawv.zzasl(int):int");
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return iz();
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof zzawv)) {
            return false;
        }
        zzawv $r2 = (zzawv) $r1;
        return size() != $r2.size() ? false : zza(this.cbx, $r2.cbx, this.mSize) && zza(this.cby, $r2.cby, this.mSize);
    }

    public int hashCode() throws  {
        int $i0 = 17;
        for (int $i1 = 0; $i1 < this.mSize; $i1++) {
            $i0 = ((($i0 * 31) + this.cbx[$i1]) * 31) + this.cby[$i1].hashCode();
        }
        return $i0;
    }

    public boolean isEmpty() throws  {
        return size() == 0;
    }

    public final zzawv iz() throws  {
        int $i0 = size();
        zzawv $r1 = new zzawv($i0);
        System.arraycopy(this.cbx, 0, $r1.cbx, 0, $i0);
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (this.cby[$i1] != null) {
                $r1.cby[$i1] = (zzaww) this.cby[$i1].clone();
            }
        }
        $r1.mSize = $i0;
        return $r1;
    }

    int size() throws  {
        return this.mSize;
    }

    void zza(int r1, com.google.android.gms.internal.zzaww r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.internal.zzawv.zza(int, com.google.android.gms.internal.zzaww):void
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawv.zza(int, com.google.android.gms.internal.zzaww):void");
    }

    zzaww zzasj(int $i0) throws  {
        $i0 = zzasl($i0);
        return ($i0 < 0 || this.cby[$i0] == cbv) ? null : this.cby[$i0];
    }

    zzaww zzask(int $i0) throws  {
        return this.cby[$i0];
    }
}
