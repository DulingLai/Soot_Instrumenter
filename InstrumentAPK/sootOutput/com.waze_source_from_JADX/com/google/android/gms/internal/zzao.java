package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.internal.zzae.zza;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzao implements zzan {
    protected MotionEvent zzafd;
    protected LinkedList<MotionEvent> zzafe = new LinkedList();
    protected long zzaff = 0;
    protected long zzafg = 0;
    protected long zzafh = 0;
    protected long zzafi = 0;
    protected long zzafj = 0;
    private boolean zzafk = false;
    protected DisplayMetrics zzafl;

    protected zzao(Context $r1) throws  {
        zzak.zzar();
        try {
            this.zzafl = $r1.getResources().getDisplayMetrics();
        } catch (UnsupportedOperationException e) {
            this.zzafl = new DisplayMetrics();
            this.zzafl.density = 1.0f;
        }
    }

    private java.lang.String zza(android.content.Context r8, java.lang.String r9, boolean r10) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0012 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r7 = this;
        r0 = 1;
        if (r10 == 0) goto L_0x0018;
    L_0x0003:
        r1 = r7.zzd(r8);	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
        r2 = 1;
        r7.zzafk = r2;
    L_0x000a:
        if (r1 == 0) goto L_0x0012;	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
    L_0x000c:
        r3 = r1.iJ();	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
        if (r3 != 0) goto L_0x001d;	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
    L_0x0012:
        r2 = 5;	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
        r9 = java.lang.Integer.toString(r2);	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
        return r9;
    L_0x0018:
        r1 = r7.zzc(r8);	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
        goto L_0x000a;
    L_0x001d:
        r10 = zzb(r10);	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
        if (r10 != 0) goto L_0x0028;	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
    L_0x0023:
        r9 = com.google.android.gms.internal.zzak.zza(r1, r9, r0);	 Catch:{ NoSuchAlgorithmException -> 0x002a, UnsupportedEncodingException -> 0x0031, Throwable -> 0x0038 }
        return r9;
    L_0x0028:
        r0 = 0;
        goto L_0x0023;
    L_0x002a:
        r4 = move-exception;
        r2 = 7;
        r9 = java.lang.Integer.toString(r2);
        return r9;
    L_0x0031:
        r5 = move-exception;
        r2 = 7;
        r9 = java.lang.Integer.toString(r2);
        return r9;
    L_0x0038:
        r6 = move-exception;
        r2 = 3;
        r9 = java.lang.Integer.toString(r2);
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzao.zza(android.content.Context, java.lang.String, boolean):java.lang.String");
    }

    private void zzav() throws  {
        if (((Boolean) Flags.zzbce.get()).booleanValue()) {
            StackTraceElement[] $r5 = new Throwable().getStackTrace();
            int $i1 = $r5.length - 1;
            int $i0 = 0;
            while ($i1 >= 0) {
                $i0++;
                if ($r5[$i1].toString().startsWith("com.google.android.ads.") || $r5[$i1].toString().startsWith("com.google.android.gms.")) {
                    break;
                }
                $i1--;
            }
            this.zzafj = (long) $i0;
        }
    }

    private static boolean zzb(boolean $z0) throws  {
        return !((Boolean) Flags.zzbbw.get()).booleanValue() ? true : ((Boolean) Flags.zzbcf.get()).booleanValue() && $z0;
    }

    public void zza(int $i0, int $i1, int $i2) throws  {
        if (this.zzafd != null) {
            this.zzafd.recycle();
        }
        long $l3 = (long) $i2;
        float $f0 = (float) $i0;
        float $f1 = this.zzafl;
        DisplayMetrics $r2 = $f1;
        $f0 *= $f1.density;
        float $f12 = (float) $i1;
        $f1 = this.zzafl;
        $r2 = $f1;
        this.zzafd = MotionEvent.obtain(0, $l3, 1, $f0, $f12 * $f1.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
    }

    public void zza(MotionEvent $r1) throws  {
        if (this.zzafk) {
            this.zzafi = 0;
            this.zzafh = 0;
            this.zzafg = 0;
            this.zzaff = 0;
            this.zzafj = 0;
            Iterator $r3 = this.zzafe.iterator();
            while ($r3.hasNext()) {
                ((MotionEvent) $r3.next()).recycle();
            }
            this.zzafe.clear();
            this.zzafd = null;
            this.zzafk = false;
        }
        switch ($r1.getAction()) {
            case 0:
                this.zzaff++;
                return;
            case 1:
                this.zzafd = MotionEvent.obtain($r1);
                LinkedList $r2 = this.zzafe;
                MotionEvent $r12 = this.zzafd;
                $r2.add($r12);
                if (this.zzafe.size() > 6) {
                    ((MotionEvent) this.zzafe.remove()).recycle();
                }
                this.zzafh++;
                zzav();
                return;
            case 2:
                long $l2 = (long) ($r1.getHistorySize() + 1);
                this.zzafg += $l2;
                return;
            case 3:
                this.zzafi++;
                return;
            default:
                return;
        }
    }

    public String zzb(Context $r1) throws  {
        return zza($r1, null, false);
    }

    public String zzb(Context $r1, String $r2) throws  {
        return zza($r1, $r2, true);
    }

    protected abstract zza zzc(Context context) throws ;

    protected abstract zza zzd(Context context) throws ;
}
