package android.support.v4.util;

import dalvik.annotation.Signature;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    protected V create(@Signature({"(TK;)TV;"}) K k) throws  {
        return null;
    }

    protected int sizeOf(@Signature({"(TK;TV;)I"}) K k, @Signature({"(TK;TV;)I"}) V v) throws  {
        return 1;
    }

    public void trimToSize(int r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:20:0x0051 in {7, 9, 12, 19, 21} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r22 = this;
    L_0x0000:
        monitor-enter(r22);
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r4 = r0.size;	 Catch:{ Throwable -> 0x003a }
        if (r4 < 0) goto L_0x0017;	 Catch:{ Throwable -> 0x003a }
    L_0x0007:
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r5 = r0.map;	 Catch:{ Throwable -> 0x003a }
        r6 = r5.isEmpty();	 Catch:{ Throwable -> 0x003a }
        if (r6 == 0) goto L_0x003d;	 Catch:{ Throwable -> 0x003a }
    L_0x0011:
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r4 = r0.size;	 Catch:{ Throwable -> 0x003a }
        if (r4 == 0) goto L_0x003d;	 Catch:{ Throwable -> 0x003a }
    L_0x0017:
        r7 = new java.lang.IllegalStateException;	 Catch:{ Throwable -> 0x003a }
        r8 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x003a }
        r8.<init>();	 Catch:{ Throwable -> 0x003a }
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r9 = r0.getClass();	 Catch:{ Throwable -> 0x003a }
        r10 = r9.getName();	 Catch:{ Throwable -> 0x003a }
        r8 = r8.append(r10);	 Catch:{ Throwable -> 0x003a }
        r11 = ".sizeOf() is reporting inconsistent results!";	 Catch:{ Throwable -> 0x003a }
        r8 = r8.append(r11);	 Catch:{ Throwable -> 0x003a }
        r10 = r8.toString();	 Catch:{ Throwable -> 0x003a }
        r7.<init>(r10);	 Catch:{ Throwable -> 0x003a }
        throw r7;	 Catch:{ Throwable -> 0x003a }
    L_0x003a:
        r12 = move-exception;	 Catch:{ Throwable -> 0x003a }
        monitor-exit(r22);	 Catch:{ Throwable -> 0x003a }
        throw r12;
    L_0x003d:
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r4 = r0.size;	 Catch:{ Throwable -> 0x003a }
        r0 = r23;	 Catch:{ Throwable -> 0x003a }
        if (r4 <= r0) goto L_0x004f;	 Catch:{ Throwable -> 0x003a }
    L_0x0045:
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r5 = r0.map;	 Catch:{ Throwable -> 0x003a }
        r6 = r5.isEmpty();	 Catch:{ Throwable -> 0x003a }
        if (r6 == 0) goto L_0x0055;	 Catch:{ Throwable -> 0x003a }
    L_0x004f:
        monitor-exit(r22);	 Catch:{ Throwable -> 0x003a }
        return;
        goto L_0x0055;
    L_0x0052:
        goto L_0x0000;
    L_0x0055:
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r5 = r0.map;	 Catch:{ Throwable -> 0x003a }
        r13 = r5.entrySet();	 Catch:{ Throwable -> 0x003a }
        r14 = r13.iterator();	 Catch:{ Throwable -> 0x003a }
        r15 = r14.next();	 Catch:{ Throwable -> 0x003a }
        r17 = r15;	 Catch:{ Throwable -> 0x003a }
        r17 = (java.util.Map.Entry) r17;	 Catch:{ Throwable -> 0x003a }
        r16 = r17;	 Catch:{ Throwable -> 0x003a }
        r0 = r16;	 Catch:{ Throwable -> 0x003a }
        r15 = r0.getKey();	 Catch:{ Throwable -> 0x003a }
        r0 = r16;	 Catch:{ Throwable -> 0x003a }
        r18 = r0.getValue();	 Catch:{ Throwable -> 0x003a }
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r5 = r0.map;	 Catch:{ Throwable -> 0x003a }
        r5.remove(r15);	 Catch:{ Throwable -> 0x003a }
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r4 = r0.size;	 Catch:{ Throwable -> 0x003a }
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r1 = r18;	 Catch:{ Throwable -> 0x003a }
        r19 = r0.safeSizeOf(r15, r1);	 Catch:{ Throwable -> 0x003a }
        r0 = r19;	 Catch:{ Throwable -> 0x003a }
        r4 = r4 - r0;	 Catch:{ Throwable -> 0x003a }
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r0.size = r4;	 Catch:{ Throwable -> 0x003a }
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r4 = r0.evictionCount;	 Catch:{ Throwable -> 0x003a }
        r4 = r4 + 1;	 Catch:{ Throwable -> 0x003a }
        r0 = r22;	 Catch:{ Throwable -> 0x003a }
        r0.evictionCount = r4;	 Catch:{ Throwable -> 0x003a }
        monitor-exit(r22);	 Catch:{ Throwable -> 0x003a }
        r20 = 1;
        r21 = 0;
        r0 = r22;
        r1 = r20;
        r2 = r18;
        r3 = r21;
        r0.entryRemoved(r1, r15, r2, r3);
        goto L_0x0052;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.trimToSize(int):void");
    }

    public LruCache(int $i0) throws  {
        if ($i0 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = $i0;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    public void resize(int $i0) throws  {
        if ($i0 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = $i0;
        }
        trimToSize($i0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(@dalvik.annotation.Signature({"(TK;)TV;"}) K r12) throws  {
        /*
        r11 = this;
        if (r12 != 0) goto L_0x000a;
    L_0x0002:
        r0 = new java.lang.NullPointerException;
        r1 = "key == null";
        r0.<init>(r1);
        throw r0;
    L_0x000a:
        monitor-enter(r11);
        r2 = r11.map;	 Catch:{ Throwable -> 0x002a }
        r3 = r2.get(r12);	 Catch:{ Throwable -> 0x002a }
        if (r3 == 0) goto L_0x001b;
    L_0x0013:
        r4 = r11.hitCount;	 Catch:{ Throwable -> 0x002a }
        r4 = r4 + 1;
        r11.hitCount = r4;	 Catch:{ Throwable -> 0x002a }
        monitor-exit(r11);	 Catch:{ Throwable -> 0x002a }
        return r3;
    L_0x001b:
        r4 = r11.missCount;	 Catch:{ Throwable -> 0x002a }
        r4 = r4 + 1;
        r11.missCount = r4;	 Catch:{ Throwable -> 0x002a }
        monitor-exit(r11);	 Catch:{ Throwable -> 0x002a }
        r3 = r11.create(r12);
        if (r3 != 0) goto L_0x002d;
    L_0x0028:
        r5 = 0;
        return r5;
    L_0x002a:
        r6 = move-exception;
        monitor-exit(r11);	 Catch:{ Throwable -> 0x002a }
        throw r6;
    L_0x002d:
        monitor-enter(r11);
        r4 = r11.createCount;	 Catch:{ Throwable -> 0x0053 }
        r4 = r4 + 1;
        r11.createCount = r4;	 Catch:{ Throwable -> 0x0053 }
        r2 = r11.map;	 Catch:{ Throwable -> 0x0053 }
        r7 = r2.put(r12, r3);	 Catch:{ Throwable -> 0x0053 }
        if (r7 == 0) goto L_0x0049;
    L_0x003c:
        r2 = r11.map;	 Catch:{ Throwable -> 0x0053 }
        r2.put(r12, r7);	 Catch:{ Throwable -> 0x0053 }
    L_0x0041:
        monitor-exit(r11);	 Catch:{ Throwable -> 0x0053 }
        if (r7 == 0) goto L_0x0056;
    L_0x0044:
        r8 = 0;
        r11.entryRemoved(r8, r12, r3, r7);
        return r7;
    L_0x0049:
        r4 = r11.size;	 Catch:{ Throwable -> 0x0053 }
        r9 = r11.safeSizeOf(r12, r3);	 Catch:{ Throwable -> 0x0053 }
        r4 = r4 + r9;
        r11.size = r4;	 Catch:{ Throwable -> 0x0053 }
        goto L_0x0041;
    L_0x0053:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ Throwable -> 0x0053 }
        throw r10;
    L_0x0056:
        r4 = r11.maxSize;
        r11.trimToSize(r4);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.get(java.lang.Object):V");
    }

    public final V put(@Signature({"(TK;TV;)TV;"}) K $r1, @Signature({"(TK;TV;)TV;"}) V $r2) throws  {
        if ($r1 == null || $r2 == null) {
            throw new NullPointerException("key == null || value == null");
        }
        Object $r5;
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf($r1, $r2);
            $r5 = this.map.put($r1, $r2);
            if ($r5 != null) {
                this.size -= safeSizeOf($r1, $r5);
            }
        }
        if ($r5 != null) {
            entryRemoved(false, $r1, $r5, $r2);
        }
        trimToSize(this.maxSize);
        return $r5;
    }

    public final V remove(@Signature({"(TK;)TV;"}) K $r1) throws  {
        if ($r1 == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            Object $r4 = this.map.remove($r1);
            if ($r4 != null) {
                this.size -= safeSizeOf($r1, $r4);
            }
        }
        if ($r4 == null) {
            return $r4;
        }
        entryRemoved(false, $r1, $r4, null);
        return $r4;
    }

    protected void entryRemoved(@Signature({"(ZTK;TV;TV;)V"}) boolean evicted, @Signature({"(ZTK;TV;TV;)V"}) K k, @Signature({"(ZTK;TV;TV;)V"}) V v, @Signature({"(ZTK;TV;TV;)V"}) V v2) throws  {
    }

    private int safeSizeOf(@Signature({"(TK;TV;)I"}) K $r1, @Signature({"(TK;TV;)I"}) V $r2) throws  {
        int $i0 = sizeOf($r1, $r2);
        if ($i0 >= 0) {
            return $i0;
        }
        throw new IllegalStateException("Negative size: " + $r1 + "=" + $r2);
    }

    public final void evictAll() throws  {
        trimToSize(-1);
    }

    public final synchronized int size() throws  {
        return this.size;
    }

    public final synchronized int maxSize() throws  {
        return this.maxSize;
    }

    public final synchronized int hitCount() throws  {
        return this.hitCount;
    }

    public final synchronized int missCount() throws  {
        return this.missCount;
    }

    public final synchronized int createCount() throws  {
        return this.createCount;
    }

    public final synchronized int putCount() throws  {
        return this.putCount;
    }

    public final synchronized int evictionCount() throws  {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() throws  {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() throws  {
        String $r3;
        int $i1 = 0;
        synchronized (this) {
            int $i0 = this.hitCount + this.missCount;
            if ($i0 != 0) {
                $i1 = (this.hitCount * 100) / $i0;
            }
            $r3 = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf($i1)});
        }
        return $r3;
    }
}
