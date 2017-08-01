package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.internal.zzae.zza;
import com.google.android.gms.internal.zzae.zzd;
import dalvik.annotation.Signature;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzax {
    private static final String TAG = zzax.class.getSimpleName();
    protected static final Object zzagr = new Object();
    private static GoogleApiAvailabilityLight zzagt = null;
    private volatile boolean zzafn = false;
    protected Context zzagf;
    private ExecutorService zzagg;
    private DexClassLoader zzagh;
    private zzau zzagi;
    private byte[] zzagj;
    private volatile AdvertisingIdClient zzagk = null;
    private Future zzagl = null;
    private volatile zza zzagm = null;
    private Future zzagn = null;
    private zzam zzago;
    private GoogleApiClient zzagp = null;
    protected boolean zzagq = false;
    protected boolean zzags = false;
    protected boolean zzagu = false;
    private Map<Pair<String, String>, zzbo> zzagv;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08141 implements Runnable {
        final /* synthetic */ zzax zzagw;

        C08141(zzax $r1) throws  {
            this.zzagw = $r1;
        }

        public void run() throws  {
            this.zzagw.zzcm();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08152 implements Runnable {
        final /* synthetic */ zzax zzagw;

        C08152(zzax $r1) throws  {
            this.zzagw = $r1;
        }

        public void run() throws  {
            this.zzagw.zzco();
        }
    }

    private zzax(Context $r1) throws  {
        this.zzagf = $r1;
        this.zzagv = new HashMap();
    }

    public static zzax zza(Context $r0, String $r1, String $r2, boolean $z0) throws  {
        zzax $r3 = new zzax($r0);
        try {
            if ($r3.zzc($r1, $r2, $z0)) {
                return $r3;
            }
        } catch (zzaw e) {
        }
        return null;
    }

    @NonNull
    private File zza(String $r1, File $r2, String $r3) throws zzau.zza, IOException {
        File $r4 = new File(String.format("%s/%s.jar", new Object[]{$r2, $r3}));
        if ($r4.exists()) {
            return $r4;
        }
        byte[] $r7 = this.zzagi.zzc(this.zzagj, $r1);
        $r4.createNewFile();
        FileOutputStream $r8 = new FileOutputStream($r4);
        $r8.write($r7, 0, $r7.length);
        $r8.close();
        return $r4;
    }

    private void zza(File $r1) throws  {
        if ($r1.exists()) {
            $r1.delete();
            return;
        }
        Log.d(TAG, String.format("File %s not found. No need for deletion", new Object[]{$r1.getAbsolutePath()}));
    }

    private void zza(File $r1, String $r2) throws  {
        Throwable $r15;
        FileInputStream $r9;
        File $r4 = new File(String.format("%s/%s.tmp", new Object[]{$r1, $r2}));
        if (!$r4.exists()) {
            File $r3 = new File(String.format("%s/%s.dex", new Object[]{$r1, $r2}));
            if ($r3.exists()) {
                FileInputStream $r7 = null;
                long $l0 = $r3.length();
                if ($l0 > 0) {
                    byte[] $r8 = (int) $l0;
                    int $i2 = $r8;
                    byte[] $r82 = new byte[$r8];
                    try {
                        FileInputStream fileInputStream = new FileInputStream($r3);
                        try {
                            if (fileInputStream.read($r82) <= 0) {
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e) {
                                    }
                                }
                                zza($r3);
                                return;
                            }
                            try {
                                zzawz com_google_android_gms_internal_zzae_zzd = new zzd();
                                com_google_android_gms_internal_zzae_zzd.zzeu = VERSION.SDK.getBytes();
                                com_google_android_gms_internal_zzae_zzd.zzet = $r2.getBytes();
                                zzau $r12 = this.zzagi;
                                $r82 = $r12.zzd(this.zzagj, $r82).getBytes();
                                com_google_android_gms_internal_zzae_zzd.data = $r82;
                                com_google_android_gms_internal_zzae_zzd.zzes = zzak.zzg($r82);
                                $r4.createNewFile();
                                FileOutputStream fileOutputStream = new FileOutputStream($r4);
                                $r82 = zzawz.zzf(com_google_android_gms_internal_zzae_zzd);
                                fileOutputStream.write($r82, 0, $r82.length);
                                fileOutputStream.close();
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e2) {
                                    }
                                }
                                zza($r3);
                            } catch (Throwable $r20) {
                                $r7 = fileInputStream;
                                $r15 = $r20;
                                if ($r7 != null) {
                                    try {
                                        $r7.close();
                                    } catch (IOException e3) {
                                    }
                                }
                                zza($r3);
                                throw $r15;
                            }
                        } catch (IOException e4) {
                            if ($r9 != null) {
                                try {
                                    $r9.close();
                                } catch (IOException e5) {
                                }
                            }
                            zza($r3);
                        } catch (NoSuchAlgorithmException e6) {
                            if ($r9 != null) {
                                $r9.close();
                            }
                            zza($r3);
                        } catch (zzau.zza e7) {
                            if ($r9 != null) {
                                $r9.close();
                            }
                            zza($r3);
                        }
                    } catch (IOException e8) {
                        $r9 = null;
                        if ($r9 != null) {
                            $r9.close();
                        }
                        zza($r3);
                    } catch (NoSuchAlgorithmException e9) {
                        $r9 = null;
                        if ($r9 != null) {
                            $r9.close();
                        }
                        zza($r3);
                    } catch (zzau.zza e10) {
                        $r9 = null;
                        if ($r9 != null) {
                            $r9.close();
                        }
                        zza($r3);
                    } catch (Throwable th) {
                        $r15 = th;
                        if ($r7 != null) {
                            $r7.close();
                        }
                        zza($r3);
                        throw $r15;
                    }
                }
            }
        }
    }

    private boolean zzb(java.io.File r26, java.lang.String r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x004a in list []
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
        r25 = this;
        r3 = new java.io.File;
        r5 = 2;
        r4 = new java.lang.Object[r5];
        r5 = 0;
        r4[r5] = r26;
        r5 = 1;
        r4[r5] = r27;
        r7 = "%s/%s.tmp";
        r6 = java.lang.String.format(r7, r4);
        r3.<init>(r6);
        r8 = r3.exists();
        if (r8 != 0) goto L_0x001c;
    L_0x001a:
        r5 = 0;
        return r5;
    L_0x001c:
        r9 = new java.io.File;
        r5 = 2;
        r4 = new java.lang.Object[r5];
        r5 = 0;
        r4[r5] = r26;
        r5 = 1;
        r4[r5] = r27;
        r7 = "%s/%s.dex";
        r6 = java.lang.String.format(r7, r4);
        r9.<init>(r6);
        r8 = r9.exists();
        if (r8 != 0) goto L_0x010f;
    L_0x0036:
        r10 = r3.length();	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r13 = 0;
        r12 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1));
        if (r12 > 0) goto L_0x004a;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
    L_0x0040:
        r0 = r25;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.zza(r3);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r5 = 0;
        return r5;
    L_0x0047:
        r15 = move-exception;
        r5 = 0;
        return r5;
    L_0x004a:
        r0 = (int) r10;
        r16 = r0;
        r0 = new byte[r0];
        r17 = r0;
        r18 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r18;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.<init>(r3);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r18;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r1 = r17;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r16 = r0.read(r1);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        if (r16 > 0) goto L_0x0075;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
    L_0x0062:
        r27 = TAG;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r7 = "Cannot read the cache data.";	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r27;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        android.util.Log.d(r0, r7);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r25;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.zza(r3);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r5 = 0;
        return r5;
    L_0x0072:
        r19 = move-exception;
        r5 = 0;
        return r5;
    L_0x0075:
        r0 = r17;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r20 = com.google.android.gms.internal.zzae.zzd.zzd(r0);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r6 = new java.lang.String;
        r0 = r20;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r0.zzet;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r17 = r0;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r6.<init>(r0);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r27;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r8 = r0.equals(r6);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        if (r8 == 0) goto L_0x00c0;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
    L_0x008e:
        r0 = r20;
        r0 = r0.zzes;
        r17 = r0;
        r0 = r20;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r0.data;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r21 = r0;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r21 = com.google.android.gms.internal.zzak.zzg(r0);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r17;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r1 = r21;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r8 = java.util.Arrays.equals(r0, r1);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        if (r8 == 0) goto L_0x00c0;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
    L_0x00a8:
        r0 = r20;
        r0 = r0.zzeu;
        r17 = r0;
        r27 = android.os.Build.VERSION.SDK;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r27;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r21 = r0.getBytes();	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r17;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r1 = r21;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r8 = java.util.Arrays.equals(r0, r1);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        if (r8 != 0) goto L_0x00ca;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
    L_0x00c0:
        r0 = r25;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.zza(r3);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r5 = 0;
        return r5;
    L_0x00c7:
        r22 = move-exception;
        r5 = 0;
        return r5;
    L_0x00ca:
        r0 = r25;
        r0 = r0.zzagi;
        r23 = r0;
        r0 = r25;
        r0 = r0.zzagj;
        r17 = r0;
        r27 = new java.lang.String;
        r0 = r20;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r0.data;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r21 = r0;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r27;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r1 = r21;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.<init>(r1);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r23;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r1 = r17;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r2 = r27;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r17 = r0.zzc(r1, r2);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r9.createNewFile();	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r24 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r24;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.<init>(r9);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r17;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r0.length;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r16 = r0;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r5 = 0;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r24;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r1 = r17;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r2 = r16;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.write(r1, r5, r2);	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0 = r24;	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r0.close();	 Catch:{ IOException -> 0x0047, NoSuchAlgorithmException -> 0x0072, zza -> 0x00c7 }
        r5 = 1;
        return r5;
    L_0x010f:
        r5 = 0;
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzax.zzb(java.io.File, java.lang.String):boolean");
    }

    private void zzc(boolean $z0) throws  {
        this.zzafn = $z0;
        if ($z0) {
            this.zzagl = this.zzagg.submit(new C08141(this));
        }
    }

    private boolean zzc(String $r1, String $r2, boolean $z0) throws zzaw {
        this.zzagg = Executors.newCachedThreadPool();
        zzc($z0);
        zzcp();
        zzcn();
        this.zzagi = new zzau(null);
        try {
            this.zzagj = this.zzagi.zzl($r1);
            $z0 = zzm($r2);
            this.zzago = new zzam(this);
            return $z0;
        } catch (zzau.zza $r7) {
            throw new zzaw($r7);
        }
    }

    private void zzcm() throws  {
        if (this.zzagk == null) {
            try {
                AdvertisingIdClient $r2 = new AdvertisingIdClient(this.zzagf);
                $r2.start();
                this.zzagk = $r2;
            } catch (GooglePlayServicesNotAvailableException e) {
                this.zzagk = null;
            } catch (IOException e2) {
                this.zzagk = null;
            } catch (GooglePlayServicesRepairableException e3) {
                this.zzagk = null;
            }
        }
    }

    private void zzco() throws  {
        if (this.zzags) {
            try {
                PackageInfo $r4 = this.zzagf.getPackageManager().getPackageInfo(this.zzagf.getPackageName(), 0);
                this.zzagm = com.google.android.gms.gass.internal.zza.zzg(this.zzagf, this.zzagf.getPackageName(), Integer.toString($r4.versionCode));
            } catch (NameNotFoundException e) {
            }
        }
    }

    private void zzcp() throws  {
        boolean $z0 = true;
        zzagt = GoogleApiAvailabilityLight.getInstance();
        this.zzagq = zzagt.getApkVersion(this.zzagf) > 0;
        if (zzagt.isGooglePlayServicesAvailable(this.zzagf) != 0) {
            $z0 = false;
        }
        this.zzags = $z0;
        if (this.zzagf.getApplicationContext() != null) {
            this.zzagp = new Builder(this.zzagf).addApi(ClearcutLogger.API).build();
        }
        Flags.initialize(this.zzagf);
    }

    private boolean zzm(String $r1) throws zzaw {
        File $r3;
        String $r7;
        File $r8;
        Object[] $r12;
        try {
            $r3 = this.zzagf.getCacheDir();
            File $r4 = $r3;
            if ($r3 == null) {
                $r3 = this.zzagf.getDir("dex", 0);
                $r4 = $r3;
                if ($r3 == null) {
                    try {
                        throw new zzaw();
                    } catch (Throwable $r16) {
                        throw new zzaw($r16);
                    }
                }
            }
            $r3 = $r4;
            $r7 = zzav.zzax();
            $r8 = zza($r1, $r4, $r7);
            zzb($r4, $r7);
            this.zzagh = new DexClassLoader($r8.getAbsolutePath(), $r4.getAbsolutePath(), null, this.zzagf.getClassLoader());
            zza($r8);
            zza($r4, $r7);
            $r12 = new Object[2];
            $r12[0] = $r4;
            $r12[1] = $r7;
            zzn(String.format("%s/%s.dex", $r12));
            return true;
        } catch (FileNotFoundException $r6) {
            throw new zzaw($r6);
        } catch (Throwable $r14) {
            throw new zzaw($r14);
        } catch (Throwable $r15) {
            throw new zzaw($r15);
        } catch (Throwable th) {
            zza($r8);
            zza($r3, $r7);
            $r12 = new Object[2];
            $r12[0] = $r3;
            $r12[1] = $r7;
            zzn(String.format("%s/%s.dex", $r12));
        }
    }

    private void zzn(String $r1) throws  {
        zza(new File($r1));
    }

    public Context getContext() throws  {
        return this.zzagf;
    }

    public GoogleApiClient getGoogleApiClient() throws  {
        return this.zzagp;
    }

    public boolean zza(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Class;", ">;)Z"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Class;", ">;)Z"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Class;", ">;)Z"}) List<Class> $r3) throws  {
        if (this.zzagv.containsKey(new Pair($r1, $r2))) {
            return false;
        }
        this.zzagv.put(new Pair($r1, $r2), new zzbo(this, $r1, $r2, $r3));
        return true;
    }

    public int zzat() throws  {
        zzam $r1 = zzcj();
        return $r1 != null ? $r1.zzat() : Integer.MIN_VALUE;
    }

    public Method zzc(String $r1, String $r2) throws  {
        zzbo $r6 = (zzbo) this.zzagv.get(new Pair($r1, $r2));
        return $r6 == null ? null : $r6.zzcy();
    }

    public ExecutorService zzcd() throws  {
        return this.zzagg;
    }

    public DexClassLoader zzce() throws  {
        return this.zzagh;
    }

    public zzau zzcf() throws  {
        return this.zzagi;
    }

    public byte[] zzcg() throws  {
        return this.zzagj;
    }

    public boolean zzch() throws  {
        return this.zzagq;
    }

    public boolean zzci() throws  {
        return this.zzagu;
    }

    public zzam zzcj() throws  {
        return this.zzago;
    }

    public zza zzck() throws  {
        return this.zzagm;
    }

    public Future zzcl() throws  {
        return this.zzagn;
    }

    void zzcn() throws  {
        if (((Boolean) Flags.zzbcf.get()).booleanValue()) {
            this.zzagn = this.zzagg.submit(new C08152(this));
        }
    }

    public AdvertisingIdClient zzcq() throws  {
        if (!this.zzafn) {
            return null;
        }
        if (this.zzagk != null) {
            return this.zzagk;
        }
        if (this.zzagl != null) {
            try {
                this.zzagl.get(2000, TimeUnit.MILLISECONDS);
                this.zzagl = null;
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
            } catch (TimeoutException e3) {
                this.zzagl.cancel(true);
            }
        }
        return this.zzagk;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzcr() throws  {
        /*
        r5 = this;
        r0 = zzagr;
        monitor-enter(r0);
        r1 = r5.zzagu;	 Catch:{ Throwable -> 0x001b }
        if (r1 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001b }
        return;
    L_0x0009:
        r1 = r5.zzags;	 Catch:{ Throwable -> 0x001b }
        if (r1 == 0) goto L_0x001e;
    L_0x000d:
        r2 = r5.zzagp;	 Catch:{ Throwable -> 0x001b }
        if (r2 == 0) goto L_0x001e;
    L_0x0011:
        r2 = r5.zzagp;	 Catch:{ Throwable -> 0x001b }
        r2.connect();	 Catch:{ Throwable -> 0x001b }
        r3 = 1;
        r5.zzagu = r3;	 Catch:{ Throwable -> 0x001b }
    L_0x0019:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001b }
        return;
    L_0x001b:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001b }
        throw r4;
    L_0x001e:
        r3 = 0;
        r5.zzagu = r3;	 Catch:{ Throwable -> 0x001b }
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzax.zzcr():void");
    }

    public void zzcs() throws  {
        synchronized (zzagr) {
            if (this.zzagu && this.zzagp != null) {
                this.zzagp.disconnect();
                this.zzagu = false;
            }
        }
    }
}
