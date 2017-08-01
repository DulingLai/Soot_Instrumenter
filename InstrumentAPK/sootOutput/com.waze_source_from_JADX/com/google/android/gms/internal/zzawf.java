package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawf implements zzavh {
    private final zzavp bXC;
    private final zzaun bXE;
    private final zzavo bXt;

    /* compiled from: dalvik_source_com.waze.apk */
    static abstract class zzb {
        final boolean bZu;
        final boolean bZv;
        final String name;

        protected zzb(String $r1, boolean $z0, boolean $z1) throws  {
            this.name = $r1;
            this.bZu = $z0;
            this.bZv = $z1;
        }

        abstract void zza(zzawl com_google_android_gms_internal_zzawl, Object obj) throws IOException, IllegalAccessException;

        abstract void zza(zzawn com_google_android_gms_internal_zzawn, Object obj) throws IOException, IllegalAccessException;

        abstract boolean zzde(Object obj) throws IOException, IllegalAccessException;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza<T> extends zzavg<T> {
        private final zzavt<T> bZa;
        private final Map<String, zzb> bZt;

        private zza(@Signature({"(", "Lcom/google/android/gms/internal/zzavt", "<TT;>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawf$zzb;", ">;)V"}) zzavt<T> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavt", "<TT;>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawf$zzb;", ">;)V"}) Map<String, zzb> $r2) throws  {
            this.bZa = $r1;
            this.bZt = $r2;
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) T $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
                return;
            }
            $r1.hK();
            try {
                for (zzb $r7 : this.bZt.values()) {
                    if ($r7.zzde($r2)) {
                        $r1.zzyo($r7.name);
                        $r7.zza($r1, (Object) $r2);
                    }
                }
                $r1.hL();
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }

        public T zzb(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")TT;"}) com.google.android.gms.internal.zzawl r17) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x003e in list [B:18:0x0044, B:24:0x0051]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r16 = this;
            r0 = r17;
            r1 = r0.hC();
            r2 = com.google.android.gms.internal.zzawm.NULL;
            if (r1 != r2) goto L_0x0011;
        L_0x000a:
            r0 = r17;
            r0.nextNull();
            r3 = 0;
            return r3;
        L_0x0011:
            r0 = r16;
            r4 = r0.bZa;
            r5 = r4.hv();
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r0.beginObject();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x001e:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r6 = r0.hasNext();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            if (r6 == 0) goto L_0x0058;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x0026:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r7 = r0.nextName();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r0 = r16;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r8 = r0.bZt;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r9 = r8.get(r7);	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r11 = r9;
            r11 = (com.google.android.gms.internal.zzawf.zzb) r11;
            r10 = r11;
            if (r10 == 0) goto L_0x003e;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x003a:
            r6 = r10.bZv;
            if (r6 != 0) goto L_0x004b;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x003e:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r0.skipValue();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            goto L_0x001e;
        L_0x0044:
            r12 = move-exception;
            r13 = new com.google.android.gms.internal.zzavd;
            r13.<init>(r12);
            throw r13;
        L_0x004b:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r10.zza(r0, r5);	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            goto L_0x001e;
        L_0x0051:
            r14 = move-exception;
            r15 = new java.lang.AssertionError;
            r15.<init>(r14);
            throw r15;
        L_0x0058:
            r0 = r17;
            r0.endObject();
            return r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawf.zza.zzb(com.google.android.gms.internal.zzawl):T");
        }
    }

    public zzawf(zzavo $r1, zzaun $r2, zzavp $r3) throws  {
        this.bXt = $r1;
        this.bXE = $r2;
        this.bXC = $r3;
    }

    private zzavg<?> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Lcom/google/android/gms/internal/zzawk", "<*>;)", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Lcom/google/android/gms/internal/zzawk", "<*>;)", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) Field $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Lcom/google/android/gms/internal/zzawk", "<*>;)", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) zzawk<?> $r3) throws  {
        zzavi $r6 = (zzavi) $r2.getAnnotation(zzavi.class);
        if ($r6 != null) {
            zzavg $r7 = zzawa.zza(this.bXt, $r1, $r3, $r6);
            if ($r7 != null) {
                return $r7;
            }
        }
        return $r1.zza((zzawk) $r3);
    }

    private zzb zza(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawk", "<*>;ZZ)", "Lcom/google/android/gms/internal/zzawf$zzb;"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawk", "<*>;ZZ)", "Lcom/google/android/gms/internal/zzawf$zzb;"}) Field $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawk", "<*>;ZZ)", "Lcom/google/android/gms/internal/zzawf$zzb;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawk", "<*>;ZZ)", "Lcom/google/android/gms/internal/zzawf$zzb;"}) zzawk<?> $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawk", "<*>;ZZ)", "Lcom/google/android/gms/internal/zzawf$zzb;"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawk", "<*>;ZZ)", "Lcom/google/android/gms/internal/zzawf$zzb;"}) boolean $z1) throws  {
        final zzauo com_google_android_gms_internal_zzauo = $r1;
        final Field field = $r2;
        final zzawk<?> com_google_android_gms_internal_zzawk_ = $r4;
        final boolean zzk = zzavu.zzk($r4.hN());
        return new zzb(this, $r3, $z0, $z1) {
            final zzavg<?> bZn = this.bZs.zza(com_google_android_gms_internal_zzauo, field, com_google_android_gms_internal_zzawk_);
            final /* synthetic */ zzawf bZs;

            void zza(zzawl $r1, Object $r2) throws IOException, IllegalAccessException {
                Object $r3 = this.bZn.zzb($r1);
                if ($r3 != null || !zzk) {
                    field.set($r2, $r3);
                }
            }

            void zza(zzawn $r1, Object $r2) throws IOException, IllegalAccessException {
                new zzawi(com_google_android_gms_internal_zzauo, this.bZn, com_google_android_gms_internal_zzawk_.hO()).zza($r1, field.get($r2));
            }

            public boolean zzde(Object $r1) throws IOException, IllegalAccessException {
                return !this.bZu ? false : field.get($r1) != $r1;
            }
        };
    }

    static List<String> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzaun;", "Ljava/lang/reflect/Field;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) zzaun $r0, @Signature({"(", "Lcom/google/android/gms/internal/zzaun;", "Ljava/lang/reflect/Field;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) Field $r1) throws  {
        zzavj $r5 = (zzavj) $r1.getAnnotation(zzavj.class);
        LinkedList $r2 = new LinkedList();
        if ($r5 == null) {
            $r2.add($r0.zzc($r1));
            return $r2;
        }
        $r2.add($r5.value());
        for (String $r3 : $r5.ht()) {
            $r2.add($r3);
        }
        return $r2;
    }

    private Map<String, zzb> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawf$zzb;", ">;"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawf$zzb;", ">;"}) zzawk<?> $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzawf$zzb;", ">;"}) Class<?> $r4) throws  {
        LinkedHashMap $r2 = new LinkedHashMap();
        if ($r4.isInterface()) {
            return $r2;
        }
        Type $r5 = $r3.hO();
        Class $r42;
        while ($r42 != Object.class) {
            for (Field $r7 : $r42.getDeclaredFields()) {
                boolean $z0 = zza($r7, true);
                boolean z = $z0;
                boolean $z2 = zza($r7, false);
                if ($z0 || $z2) {
                    String $r13;
                    $r7.setAccessible(true);
                    Type $r8 = zzavn.zza($r3.hO(), $r42, $r7.getGenericType());
                    List $r10 = zzd($r7);
                    zzb $r11 = null;
                    int $i2 = 0;
                    while ($i2 < $r10.size()) {
                        $r13 = (String) $r10.get($i2);
                        if ($i2 != 0) {
                            z = false;
                        }
                        zzb $r15 = (zzb) $r2.put($r13, zza($r1, $r7, $r13, zzawk.zzl($r8), z, $z2));
                        if ($r11 != null) {
                            $r15 = $r11;
                        }
                        $i2++;
                        $r11 = $r15;
                    }
                    if ($r11 != null) {
                        $r13 = String.valueOf($r5);
                        String $r17 = $r11.name;
                        throw new IllegalArgumentException(new StringBuilder((String.valueOf($r13).length() + 37) + String.valueOf($r17).length()).append($r13).append(" declares multiple JSON fields named ").append($r17).toString());
                    }
                }
            }
            zzawk $r14 = zzawk.zzl(zzavn.zza($r3.hO(), $r42, $r42.getGenericSuperclass()));
            zzawk $r32 = $r14;
            $r42 = $r14.hN();
        }
        return $r2;
    }

    static boolean zza(Field $r0, boolean $z0, zzavp $r1) throws  {
        return ($r1.zza($r0.getType(), $z0) || $r1.zza($r0, $z0)) ? false : true;
    }

    private List<String> zzd(@Signature({"(", "Ljava/lang/reflect/Field;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) Field $r1) throws  {
        return zza(this.bXE, $r1);
    }

    public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
        Class $r3 = $r2.hN();
        return !Object.class.isAssignableFrom($r3) ? null : new zza(this.bXt.zzb($r2), zza($r1, (zzawk) $r2, $r3));
    }

    public boolean zza(Field $r1, boolean $z0) throws  {
        return zza($r1, $z0, this.bXC);
    }
}
