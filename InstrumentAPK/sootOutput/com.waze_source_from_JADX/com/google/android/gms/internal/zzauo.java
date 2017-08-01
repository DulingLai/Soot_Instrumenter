package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzauo {
    private final ThreadLocal<Map<zzawk<?>, zza<?>>> bXq;
    private final Map<zzawk<?>, zzavg<?>> bXr;
    private final List<zzavh> bXs;
    private final zzavo bXt;
    private final boolean bXu;
    private final boolean bXv;
    private final boolean bXw;
    private final boolean bXx;
    final zzaus bXy;
    final zzavb bXz;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07681 implements zzaus {
        final /* synthetic */ zzauo bXA;

        C07681(zzauo $r1) throws  {
            this.bXA = $r1;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07692 implements zzavb {
        final /* synthetic */ zzauo bXA;

        C07692(zzauo $r1) throws  {
            this.bXA = $r1;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07703 extends zzavg<Number> {
        final /* synthetic */ zzauo bXA;

        C07703(zzauo $r1) throws  {
            this.bXA = $r1;
        }

        public void zza(zzawn $r1, Number $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
                return;
            }
            this.bXA.zzr($r2.doubleValue());
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zze($r1);
        }

        public Double zze(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return Double.valueOf($r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07714 extends zzavg<Number> {
        final /* synthetic */ zzauo bXA;

        C07714(zzauo $r1) throws  {
            this.bXA = $r1;
        }

        public void zza(zzawn $r1, Number $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
                return;
            }
            this.bXA.zzr((double) $r2.floatValue());
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzf($r1);
        }

        public Float zzf(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return Float.valueOf((float) $r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07725 extends zzavg<Number> {
        final /* synthetic */ zzauo bXA;

        C07725(zzauo $r1) throws  {
            this.bXA = $r1;
        }

        public void zza(zzawn $r1, Number $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
            } else {
                $r1.zzyp($r2.toString());
            }
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return Long.valueOf($r1.nextLong());
            }
            $r1.nextNull();
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class zza<T> extends zzavg<T> {
        private zzavg<T> bXB;

        zza() throws  {
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzavg", "<TT;>;)V"}) zzavg<T> $r1) throws  {
            if (this.bXB != null) {
                throw new AssertionError();
            }
            this.bXB = $r1;
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) T $r2) throws IOException {
            if (this.bXB == null) {
                throw new IllegalStateException();
            }
            this.bXB.zza($r1, $r2);
        }

        public T zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")TT;"}) zzawl $r1) throws IOException {
            if (this.bXB != null) {
                return this.bXB.zzb($r1);
            }
            throw new IllegalStateException();
        }
    }

    public zzauo() throws  {
        this(zzavp.bYm, zzaum.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, zzave.DEFAULT, Collections.emptyList());
    }

    zzauo(@Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) zzavp $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) zzaun $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) Map<Type, zzauq<?>> $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) boolean $z1, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) boolean $z2, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) boolean $z3, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) boolean $z4, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) boolean $z5, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) zzave $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzavp;", "Lcom/google/android/gms/internal/zzaun;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;ZZZZZZ", "Lcom/google/android/gms/internal/zzave;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) List<zzavh> $r5) throws  {
        zzauo com_google_android_gms_internal_zzauo = this;
        this.bXq = new ThreadLocal();
        this.bXr = Collections.synchronizedMap(new HashMap());
        this.bXy = new C07681(this);
        this.bXz = new C07692(this);
        this.bXt = new zzavo($r3);
        this.bXu = $z0;
        this.bXw = $z2;
        this.bXv = $z3;
        this.bXx = $z4;
        ArrayList $r12 = new ArrayList();
        $r12.add(zzawj.cap);
        $r12.add(zzawe.bYW);
        $r12.add($r1);
        $r12.addAll($r5);
        $r12.add(zzawj.bZW);
        $r12.add(zzawj.bZL);
        $r12.add(zzawj.bZF);
        $r12.add(zzawj.bZH);
        $r12.add(zzawj.bZJ);
        $r12.add(zzawj.zza(Long.TYPE, Long.class, zza($r4)));
        $r12.add(zzawj.zza(Double.TYPE, Double.class, zzef($z5)));
        $r12.add(zzawj.zza(Float.TYPE, Float.class, zzeg($z5)));
        $r12.add(zzawj.bZQ);
        $r12.add(zzawj.bZS);
        $r12.add(zzawj.bZY);
        $r12.add(zzawj.caa);
        $r12.add(zzawj.zza(BigDecimal.class, zzawj.bZU));
        $r12.add(zzawj.zza(BigInteger.class, zzawj.bZV));
        $r12.add(zzawj.cac);
        $r12.add(zzawj.cae);
        $r12.add(zzawj.cai);
        $r12.add(zzawj.can);
        $r12.add(zzawj.cag);
        $r12.add(zzawj.bZC);
        $r12.add(zzavz.bYW);
        $r12.add(zzawj.cal);
        $r12.add(zzawh.bYW);
        $r12.add(zzawg.bYW);
        $r12.add(zzawj.caj);
        $r12.add(zzavx.bYW);
        $r12.add(zzawj.bZA);
        $r12.add(new zzavy(this.bXt));
        $r12.add(new zzawd(this.bXt, $z1));
        $r12.add(new zzawa(this.bXt));
        $r12.add(zzawj.caq);
        $r12.add(new zzawf(this.bXt, $r2, $r1));
        this.bXs = Collections.unmodifiableList($r12);
    }

    private zzavg<Number> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzave;", ")", "Lcom/google/android/gms/internal/zzavg", "<", "Ljava/lang/Number;", ">;"}) zzave $r1) throws  {
        return $r1 == zzave.DEFAULT ? zzawj.bZM : new C07725(this);
    }

    private static void zza(Object $r0, zzawl $r1) throws  {
        if ($r0 != null) {
            try {
                if ($r1.hC() != zzawm.END_DOCUMENT) {
                    throw new zzauv("JSON document was not fully consumed.");
                }
            } catch (Throwable $r5) {
                throw new zzavd($r5);
            } catch (Throwable $r7) {
                throw new zzauv($r7);
            }
        }
    }

    private zzavg<Number> zzef(@Signature({"(Z)", "Lcom/google/android/gms/internal/zzavg", "<", "Ljava/lang/Number;", ">;"}) boolean $z0) throws  {
        return $z0 ? zzawj.bZO : new C07703(this);
    }

    private zzavg<Number> zzeg(@Signature({"(Z)", "Lcom/google/android/gms/internal/zzavg", "<", "Ljava/lang/Number;", ">;"}) boolean $z0) throws  {
        return $z0 ? zzawj.bZN : new C07714(this);
    }

    private void zzr(double $d0) throws  {
        if (Double.isNaN($d0) || Double.isInfinite($d0)) {
            throw new IllegalArgumentException($d0 + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    public String toString() throws  {
        return "{serializeNulls:" + this.bXu + "factories:" + this.bXs + ",instanceCreators:" + this.bXt + "}";
    }

    public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzavh;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzavh $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzavh;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
        boolean $z0 = false;
        if (!this.bXs.contains($r1)) {
            $z0 = true;
        }
        for (zzavh $r6 : this.bXs) {
            if ($z0) {
                zzavg $r7 = $r6.zza(this, $r2);
                if ($r7 != null) {
                    return $r7;
                }
            } else if ($r6 == $r1) {
                $z0 = true;
            }
        }
        String $r9 = String.valueOf($r2);
        throw new IllegalArgumentException(new StringBuilder(String.valueOf($r9).length() + 22).append("GSON cannot serialize ").append($r9).toString());
    }

    public <T> com.google.android.gms.internal.zzavg<T> zza(@dalvik.annotation.Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) com.google.android.gms.internal.zzawk<T> r26) throws  {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r25 = this;
        r0 = r25;
        r2 = r0.bXr;
        r0 = r26;
        r3 = r2.get(r0);
        r5 = r3;
        r5 = (com.google.android.gms.internal.zzavg) r5;
        r4 = r5;
        if (r4 == 0) goto L_0x0011;
    L_0x0010:
        return r4;
    L_0x0011:
        r0 = r25;
        r6 = r0.bXq;
        r3 = r6.get();
        r7 = r3;
        r7 = (java.util.Map) r7;
        r2 = r7;
        r8 = 0;
        if (r2 != 0) goto L_0x00d6;
    L_0x0020:
        r9 = new java.util.HashMap;
        r9.<init>();
        r0 = r25;
        r6 = r0.bXq;
        r6.set(r9);
        r2 = r9;
        r8 = 1;
    L_0x002e:
        r0 = r26;
        r3 = r2.get(r0);
        r10 = r3;
        r10 = (com.google.android.gms.internal.zzauo.zza) r10;
        r4 = r10;
        if (r4 != 0) goto L_0x00d7;
    L_0x003a:
        r11 = new com.google.android.gms.internal.zzauo$zza;	 Catch:{ Throwable -> 0x00c6 }
        r11.<init>();	 Catch:{ Throwable -> 0x00c6 }
        r0 = r26;	 Catch:{ Throwable -> 0x00c6 }
        r2.put(r0, r11);	 Catch:{ Throwable -> 0x00c6 }
        r0 = r25;	 Catch:{ Throwable -> 0x00c6 }
        r12 = r0.bXs;	 Catch:{ Throwable -> 0x00c6 }
        r13 = r12.iterator();	 Catch:{ Throwable -> 0x00c6 }
    L_0x004c:
        r14 = r13.hasNext();	 Catch:{ Throwable -> 0x00c6 }
        if (r14 == 0) goto L_0x0087;	 Catch:{ Throwable -> 0x00c6 }
    L_0x0052:
        r3 = r13.next();	 Catch:{ Throwable -> 0x00c6 }
        r16 = r3;	 Catch:{ Throwable -> 0x00c6 }
        r16 = (com.google.android.gms.internal.zzavh) r16;	 Catch:{ Throwable -> 0x00c6 }
        r15 = r16;	 Catch:{ Throwable -> 0x00c6 }
        r0 = r25;	 Catch:{ Throwable -> 0x00c6 }
        r1 = r26;	 Catch:{ Throwable -> 0x00c6 }
        r4 = r15.zza(r0, r1);	 Catch:{ Throwable -> 0x00c6 }
        if (r4 == 0) goto L_0x004c;	 Catch:{ Throwable -> 0x00c6 }
    L_0x0066:
        r11.zza(r4);	 Catch:{ Throwable -> 0x00c6 }
        r0 = r25;	 Catch:{ Throwable -> 0x00c6 }
        r0 = r0.bXr;	 Catch:{ Throwable -> 0x00c6 }
        r17 = r0;	 Catch:{ Throwable -> 0x00c6 }
        r1 = r26;	 Catch:{ Throwable -> 0x00c6 }
        r0.put(r1, r4);	 Catch:{ Throwable -> 0x00c6 }
        r0 = r26;
        r2.remove(r0);
        if (r8 == 0) goto L_0x00d8;
    L_0x007b:
        r0 = r25;
        r6 = r0.bXq;
        r6.remove();
        goto L_0x0086;
    L_0x0083:
        goto L_0x002e;
    L_0x0086:
        return r4;
    L_0x0087:
        r18 = new java.lang.IllegalArgumentException;	 Catch:{ Throwable -> 0x00c6 }
        r0 = r26;	 Catch:{ Throwable -> 0x00c6 }
        r19 = java.lang.String.valueOf(r0);	 Catch:{ Throwable -> 0x00c6 }
        r20 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00c6 }
        r0 = r19;	 Catch:{ Throwable -> 0x00c6 }
        r21 = java.lang.String.valueOf(r0);	 Catch:{ Throwable -> 0x00c6 }
        r0 = r21;	 Catch:{ Throwable -> 0x00c6 }
        r22 = r0.length();	 Catch:{ Throwable -> 0x00c6 }
        r22 = r22 + 19;	 Catch:{ Throwable -> 0x00c6 }
        r0 = r20;	 Catch:{ Throwable -> 0x00c6 }
        r1 = r22;	 Catch:{ Throwable -> 0x00c6 }
        r0.<init>(r1);	 Catch:{ Throwable -> 0x00c6 }
        r23 = "GSON cannot handle ";	 Catch:{ Throwable -> 0x00c6 }
        r0 = r20;	 Catch:{ Throwable -> 0x00c6 }
        r1 = r23;	 Catch:{ Throwable -> 0x00c6 }
        r20 = r0.append(r1);	 Catch:{ Throwable -> 0x00c6 }
        r0 = r20;	 Catch:{ Throwable -> 0x00c6 }
        r1 = r19;	 Catch:{ Throwable -> 0x00c6 }
        r20 = r0.append(r1);	 Catch:{ Throwable -> 0x00c6 }
        r0 = r20;	 Catch:{ Throwable -> 0x00c6 }
        r19 = r0.toString();	 Catch:{ Throwable -> 0x00c6 }
        r0 = r18;	 Catch:{ Throwable -> 0x00c6 }
        r1 = r19;	 Catch:{ Throwable -> 0x00c6 }
        r0.<init>(r1);	 Catch:{ Throwable -> 0x00c6 }
        throw r18;	 Catch:{ Throwable -> 0x00c6 }
    L_0x00c6:
        r24 = move-exception;
        r0 = r26;
        r2.remove(r0);
        if (r8 == 0) goto L_0x00d5;
    L_0x00ce:
        r0 = r25;
        r6 = r0.bXq;
        r6.remove();
    L_0x00d5:
        throw r24;
    L_0x00d6:
        goto L_0x0083;
    L_0x00d7:
        return r4;
    L_0x00d8:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzauo.zza(com.google.android.gms.internal.zzawk):com.google.android.gms.internal.zzavg<T>");
    }

    public zzawn zza(Writer $r1) throws IOException {
        if (this.bXw) {
            $r1.write(")]}'\n");
        }
        zzawn $r2 = new zzawn($r1);
        if (this.bXx) {
            $r2.setIndent("  ");
        }
        $r2.zzek(this.bXu);
        return $r2;
    }

    public <T> T zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauu;", "Ljava/lang/Class", "<TT;>;)TT;"}) zzauu $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauu;", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r2) throws zzavd {
        return zzavu.zzo($r2).cast(zza($r1, (Type) $r2));
    }

    public <T> T zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauu;", "Ljava/lang/reflect/Type;", ")TT;"}) zzauu $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauu;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws zzavd {
        return $r1 == null ? null : zza(new zzawb($r1), $r2);
    }

    public <T> T zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawl;", "Ljava/lang/reflect/Type;", ")TT;"}) zzawl $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawl;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws zzauv, zzavd {
        boolean $z0 = true;
        boolean $z1 = $r1.isLenient();
        $r1.setLenient(true);
        try {
            $r1.hC();
            $z0 = false;
            Object $r5 = zza(zzawk.zzl($r2)).zzb($r1);
            $r1.setLenient($z1);
            return $r5;
        } catch (Throwable $r6) {
            if ($z0) {
                $r1.setLenient($z1);
                return null;
            }
            throw new zzavd($r6);
        } catch (Throwable $r9) {
            throw new zzavd($r9);
        } catch (Throwable $r10) {
            throw new zzavd($r10);
        } catch (Throwable th) {
            $r1.setLenient($z1);
        }
    }

    public <T> T zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/io/Reader;", "Ljava/lang/reflect/Type;", ")TT;"}) Reader $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/io/Reader;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws zzauv, zzavd {
        zzawl $r3 = new zzawl($r1);
        Object $r4 = zza($r3, $r2);
        zza($r4, $r3);
        return $r4;
    }

    public <T> T zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ")TT;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws zzavd {
        return $r1 == null ? null : zza(new StringReader($r1), $r2);
    }

    public void zza(zzauu $r1, zzawn $r2) throws zzauv {
        boolean $z0 = $r2.isLenient();
        $r2.setLenient(true);
        boolean $z1 = $r2.hY();
        $r2.zzej(this.bXv);
        boolean $z2 = $r2.hZ();
        $r2.zzek(this.bXu);
        try {
            zzavv.zzb($r1, $r2);
            $r2.setLenient($z0);
            $r2.zzej($z1);
            $r2.zzek($z2);
        } catch (Throwable $r4) {
            throw new zzauv($r4);
        } catch (Throwable th) {
            $r2.setLenient($z0);
            $r2.zzej($z1);
            $r2.zzek($z2);
        }
    }

    public void zza(zzauu $r1, Appendable $r2) throws zzauv {
        try {
            zza($r1, zza(zzavv.zza($r2)));
        } catch (IOException $r6) {
            throw new RuntimeException($r6);
        }
    }

    public void zza(Object $r1, Type $r2, zzawn $r3) throws zzauv {
        zzavg $r5 = zza(zzawk.zzl($r2));
        boolean $z0 = $r3.isLenient();
        $r3.setLenient(true);
        boolean $z1 = $r3.hY();
        $r3.zzej(this.bXv);
        boolean $z2 = $r3.hZ();
        $r3.zzek(this.bXu);
        try {
            $r5.zza($r3, $r1);
            $r3.setLenient($z0);
            $r3.zzej($z1);
            $r3.zzek($z2);
        } catch (Throwable $r6) {
            throw new zzauv($r6);
        } catch (Throwable th) {
            $r3.setLenient($z0);
            $r3.zzej($z1);
            $r3.zzek($z2);
        }
    }

    public void zza(Object $r1, Type $r2, Appendable $r3) throws zzauv {
        try {
            zza($r1, $r2, zza(zzavv.zza($r3)));
        } catch (Throwable $r7) {
            throw new zzauv($r7);
        }
    }

    public String zzb(zzauu $r1) throws  {
        Appendable $r3 = new StringWriter();
        zza($r1, $r3);
        return $r3.toString();
    }

    public String zzc(Object $r1, Type $r2) throws  {
        Appendable $r4 = new StringWriter();
        zza($r1, $r2, $r4);
        return $r4.toString();
    }

    public String zzcx(Object $r1) throws  {
        return $r1 == null ? zzb(zzauw.bXK) : zzc($r1, $r1.getClass());
    }

    public <T> T zze(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r2) throws zzavd {
        return zzavu.zzo($r2).cast(zza($r1, (Type) $r2));
    }

    public <T> zzavg<T> zzj(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) Class<T> $r1) throws  {
        return zza(zzawk.zzq($r1));
    }
}
