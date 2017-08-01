package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import java.lang.reflect.Field;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzuh {
    private static zzui abY;
    private static final zza abZ = new C08621();
    public static final zzb aca = new C08632();
    public static final zzb acb = new C08643();
    public static final zzb acc = new C08654();
    public static final zzb acd = new C08665();
    public static final zzb ace = new C08676();
    private final Context acf;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08621 implements zza {
        C08621() throws  {
        }

        public int zzd(Context $r1, String $r2, boolean $z0) throws  {
            return zzuh.zzd($r1, $r2, $z0);
        }

        public int zzu(Context $r1, String $r2) throws  {
            return zzuh.zzu($r1, $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzb {

        /* compiled from: dalvik_source_com.waze.apk */
        public interface zza {
            int zzd(Context context, String str, boolean z) throws ;

            int zzu(Context context, String str) throws ;
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static class zzb {
            public int ach = 0;
            public int aci = 0;
            public int acj = 0;
        }

        zzb zza(Context context, String str, zza com_google_android_gms_internal_zzuh_zzb_zza) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08632 implements zzb {
        C08632() throws  {
        }

        public zzb zza(Context $r1, String $r2, zza $r3) throws  {
            zzb $r4 = new zzb();
            $r4.aci = $r3.zzd($r1, $r2, true);
            if ($r4.aci != 0) {
                $r4.acj = 1;
                return $r4;
            }
            $r4.ach = $r3.zzu($r1, $r2);
            if ($r4.ach == 0) {
                return $r4;
            }
            $r4.acj = -1;
            return $r4;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08643 implements zzb {
        C08643() throws  {
        }

        public zzb zza(Context $r1, String $r2, zza $r3) throws  {
            zzb $r4 = new zzb();
            $r4.ach = $r3.zzu($r1, $r2);
            if ($r4.ach != 0) {
                $r4.acj = -1;
                return $r4;
            }
            $r4.aci = $r3.zzd($r1, $r2, true);
            if ($r4.aci == 0) {
                return $r4;
            }
            $r4.acj = 1;
            return $r4;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08654 implements zzb {
        C08654() throws  {
        }

        public zzb zza(Context $r1, String $r2, zza $r3) throws  {
            zzb $r4 = new zzb();
            $r4.ach = $r3.zzu($r1, $r2);
            $r4.aci = $r3.zzd($r1, $r2, true);
            if ($r4.ach == 0 && $r4.aci == 0) {
                $r4.acj = 0;
                return $r4;
            } else if ($r4.ach >= $r4.aci) {
                $r4.acj = -1;
                return $r4;
            } else {
                $r4.acj = 1;
                return $r4;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08665 implements zzb {
        C08665() throws  {
        }

        public zzb zza(Context $r1, String $r2, zza $r3) throws  {
            zzb $r4 = new zzb();
            $r4.ach = $r3.zzu($r1, $r2);
            $r4.aci = $r3.zzd($r1, $r2, true);
            if ($r4.ach == 0 && $r4.aci == 0) {
                $r4.acj = 0;
                return $r4;
            } else if ($r4.aci >= $r4.ach) {
                $r4.acj = 1;
                return $r4;
            } else {
                $r4.acj = -1;
                return $r4;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08676 implements zzb {
        C08676() throws  {
        }

        public zzb zza(Context $r1, String $r2, zza $r3) throws  {
            zzb $r4 = new zzb();
            $r4.ach = $r3.zzu($r1, $r2);
            if ($r4.ach != 0) {
                $r4.aci = $r3.zzd($r1, $r2, false);
            } else {
                $r4.aci = $r3.zzd($r1, $r2, true);
            }
            if ($r4.ach == 0 && $r4.aci == 0) {
                $r4.acj = 0;
                return $r4;
            } else if ($r4.aci >= $r4.ach) {
                $r4.acj = 1;
                return $r4;
            } else {
                $r4.acj = -1;
                return $r4;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08687 implements zza {
        final /* synthetic */ int acg;

        C08687(int $i0) throws  {
            this.acg = $i0;
        }

        public int zzd(Context context, String str, boolean z) throws  {
            return 0;
        }

        public int zzu(Context context, String str) throws  {
            return this.acg;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza extends Exception {
        private zza(String $r1) throws  {
            super($r1);
        }

        private zza(String $r1, Throwable $r2) throws  {
            super($r1, $r2);
        }
    }

    private zzuh(Context $r1) throws  {
        this.acf = (Context) zzab.zzag($r1);
    }

    public static zzuh zza(Context $r0, zzb $r1, String $r2) throws zza {
        zzb $r4 = $r1.zza($r0, $r2, abZ);
        String str = "DynamiteModule";
        Log.i(str, new StringBuilder((String.valueOf($r2).length() + 68) + String.valueOf($r2).length()).append("Considering local module ").append($r2).append(":").append($r4.ach).append(" and remote module ").append($r2).append(":").append($r4.aci).toString());
        if ($r4.acj == 0 || (($r4.acj == -1 && $r4.ach == 0) || ($r4.acj == 1 && $r4.aci == 0))) {
            throw new zza("No acceptable module found. Local version is " + $r4.ach + " and remote version is " + $r4.aci + FileUploadSession.SEPARATOR);
        } else if ($r4.acj == -1) {
            return zzw($r0, $r2);
        } else {
            if ($r4.acj == 1) {
                try {
                    return zza($r0, $r2, $r4.aci);
                } catch (zza $r7) {
                    String $r6 = "Failed to load remote module: ";
                    String $r9 = String.valueOf($r7.getMessage());
                    Log.w("DynamiteModule", $r9.length() != 0 ? $r6.concat($r9) : new String("Failed to load remote module: "));
                    if ($r4.ach != 0) {
                        if ($r1.zza($r0, $r2, new C08687($r4.ach)).acj == -1) {
                            return zzw($r0, $r2);
                        }
                    }
                    throw new zza("Remote load failed. No local fallback found.", $r7);
                }
            }
            throw new zza("VersionPolicy returned invalid code:" + $r4.acj);
        }
    }

    private static zzuh zza(Context $r0, String $r1, int $i0) throws zza {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf($r1).length() + 51).append("Selected remote version of ").append($r1).append(", version >= ").append($i0).toString());
        zzui $r4 = zzcn($r0);
        if ($r4 == null) {
            throw new zza("Failed to create IDynamiteLoader.");
        }
        try {
            zzd $r6 = $r4.zza(zze.zzan($r0), $r1, $i0);
            if (zze.zzae($r6) != null) {
                return new zzuh((Context) zze.zzae($r6));
            }
            throw new zza("Failed to load remote module.");
        } catch (RemoteException $r8) {
            throw new zza("Failed to load remote module.", $r8);
        }
    }

    private static com.google.android.gms.internal.zzui zzcn(android.content.Context r17) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x006c in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = com.google.android.gms.internal.zzuh.class;
        monitor-enter(r1);
        r2 = abY;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        if (r2 == 0) goto L_0x000d;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
    L_0x0007:
        r2 = abY;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r1 = com.google.android.gms.internal.zzuh.class;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        monitor-exit(r1);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        return r2;
    L_0x000d:
        r3 = com.google.android.gms.common.GoogleApiAvailabilityLight.getInstance();	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r0 = r17;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r4 = r3.isGooglePlayServicesAvailable(r0);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        if (r4 == 0) goto L_0x001e;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
    L_0x0019:
        r1 = com.google.android.gms.internal.zzuh.class;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        monitor-exit(r1);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r5 = 0;
        return r5;
    L_0x001e:
        r6 = "com.google.android.gms";	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r7 = 3;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r0 = r17;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r17 = r0.createPackageContext(r6, r7);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r0 = r17;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r8 = r0.getClassLoader();	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r6 = "com.google.android.gms.chimera.container.DynamiteLoaderImpl";	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r9 = r8.loadClass(r6);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r10 = r9.newInstance();	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r12 = r10;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r12 = (android.os.IBinder) r12;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r11 = r12;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r2 = com.google.android.gms.internal.zzui.zza.zzio(r11);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        if (r2 == 0) goto L_0x006c;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
    L_0x0041:
        abY = r2;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r1 = com.google.android.gms.internal.zzuh.class;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        monitor-exit(r1);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        return r2;
    L_0x0047:
        r13 = move-exception;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r1 = com.google.android.gms.internal.zzuh.class;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        monitor-exit(r1);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        throw r13;
    L_0x004c:
        r14 = move-exception;
        r15 = "Failed to load IDynamiteLoader from GmsCore: ";	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r16 = r14.getMessage();	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r0 = r16;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r16 = java.lang.String.valueOf(r0);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r0 = r16;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r4 = r0.length();	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        if (r4 == 0) goto L_0x0071;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
    L_0x0061:
        r0 = r16;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r15 = r15.concat(r0);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
    L_0x0067:
        r6 = "DynamiteModule";	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        android.util.Log.e(r6, r15);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
    L_0x006c:
        r1 = com.google.android.gms.internal.zzuh.class;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        monitor-exit(r1);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r5 = 0;
        return r5;
    L_0x0071:
        r15 = new java.lang.String;	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r6 = "Failed to load IDynamiteLoader from GmsCore: ";	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        r15.<init>(r6);	 Catch:{ Exception -> 0x004c, Throwable -> 0x0047 }
        goto L_0x0067;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzuh.zzcn(android.content.Context):com.google.android.gms.internal.zzui");
    }

    public static int zzd(Context $r0, String $r1, boolean $z0) throws  {
        zzui $r2 = zzcn($r0);
        if ($r2 == null) {
            return 0;
        }
        try {
            return $r2.zza(zze.zzan($r0), $r1, $z0);
        } catch (RemoteException $r4) {
            $r1 = "Failed to retrieve remote module version: ";
            String $r5 = String.valueOf($r4.getMessage());
            Log.w("DynamiteModule", $r5.length() != 0 ? $r1.concat($r5) : new String("Failed to retrieve remote module version: "));
            return 0;
        }
    }

    public static int zzu(Context $r0, String $r1) throws  {
        String $r3;
        try {
            ClassLoader $r2 = $r0.getApplicationContext().getClassLoader();
            $r3 = String.valueOf("com.google.android.gms.dynamite.descriptors.");
            String $r4 = String.valueOf("ModuleDescriptor");
            Class $r7 = $r2.loadClass(new StringBuilder(((String.valueOf($r3).length() + 1) + String.valueOf($r1).length()) + String.valueOf($r4).length()).append($r3).append($r1).append(FileUploadSession.SEPARATOR).append($r4).toString());
            Field $r8 = $r7.getDeclaredField("MODULE_ID");
            Field $r9 = $r7.getDeclaredField("MODULE_VERSION");
            if ($r8.get(null).equals($r1)) {
                return $r9.getInt(null);
            }
            $r3 = String.valueOf($r8.get(null));
            Log.e("DynamiteModule", new StringBuilder((String.valueOf($r3).length() + 51) + String.valueOf($r1).length()).append("Module descriptor id '").append($r3).append("' didn't match expected id '").append($r1).append("'").toString());
            return 0;
        } catch (Exception $r11) {
            $r1 = "Failed to load module descriptor class: ";
            $r3 = String.valueOf($r11.getMessage());
            if ($r3.length() != 0) {
                $r1 = $r1.concat($r3);
            } else {
                String str = new String("Failed to load module descriptor class: ");
            }
            Log.e("DynamiteModule", $r1);
            return 0;
        }
    }

    public static int zzv(Context $r0, String $r1) throws  {
        return zzd($r0, $r1, false);
    }

    private static zzuh zzw(Context $r0, String $r1) throws  {
        String $r2 = "Selected local version of ";
        $r1 = String.valueOf($r1);
        Log.i("DynamiteModule", $r1.length() != 0 ? $r2.concat($r1) : new String("Selected local version of "));
        return new zzuh($r0.getApplicationContext());
    }

    public Context zzbjc() throws  {
        return this.acf;
    }

    public IBinder zzii(String $r1) throws zza {
        ReflectiveOperationException $r8;
        String $r9;
        try {
            return (IBinder) this.acf.getClassLoader().loadClass($r1).newInstance();
        } catch (ClassNotFoundException e) {
            $r8 = e;
            $r9 = "Failed to instantiate module class: ";
            $r1 = String.valueOf($r1);
            throw new zza($r1.length() != 0 ? $r9.concat($r1) : new String("Failed to instantiate module class: "), $r8);
        } catch (InstantiationException e2) {
            $r8 = e2;
            $r9 = "Failed to instantiate module class: ";
            $r1 = String.valueOf($r1);
            if ($r1.length() != 0) {
            }
            throw new zza($r1.length() != 0 ? $r9.concat($r1) : new String("Failed to instantiate module class: "), $r8);
        } catch (IllegalAccessException e3) {
            $r8 = e3;
            $r9 = "Failed to instantiate module class: ";
            $r1 = String.valueOf($r1);
            if ($r1.length() != 0) {
            }
            throw new zza($r1.length() != 0 ? $r9.concat($r1) : new String("Failed to instantiate module class: "), $r8);
        }
    }
}
