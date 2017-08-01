package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;

/* compiled from: dalvik_source_com.waze.apk */
public class zzas {
    private static final String[] zzafy = new String[]{"/aclk", "/pcs/click"};
    private String zzafu = "googleads.g.doubleclick.net";
    private String zzafv = "/pagead/ads";
    private String zzafw = "ad.doubleclick.net";
    private String[] zzafx = new String[]{".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private zzan zzafz;

    public zzas(zzan $r1) throws  {
        this.zzafz = $r1;
    }

    private Uri zza(Uri $r1, Context $r2, String $r3, boolean $z0) throws zzat {
        try {
            boolean $z1 = zzb($r1);
            if ($z1) {
                if ($r1.toString().contains("dc_ms=")) {
                    throw new zzat("Parameter already exists: dc_ms");
                }
            } else if ($r1.getQueryParameter("ms") != null) {
                throw new zzat("Query parameter already exists: ms");
            }
            $r3 = $z0 ? this.zzafz.zzb($r2, $r3) : this.zzafz.zzb($r2);
            return $z1 ? zzb($r1, "dc_ms", $r3) : zza($r1, "ms", $r3);
        } catch (UnsupportedOperationException e) {
            throw new zzat("Provided Uri is not in a valid state");
        }
    }

    private Uri zza(Uri $r1, String $r2, String $r3) throws UnsupportedOperationException {
        String $r4 = $r1.toString();
        int $i0 = $r4.indexOf("&adurl");
        int $i1 = $i0;
        if ($i0 == -1) {
            $i1 = $r4.indexOf("?adurl");
        }
        return $i1 != -1 ? Uri.parse(new StringBuilder($r4.substring(0, $i1 + 1)).append($r2).append("=").append($r3).append("&").append($r4.substring($i1 + 1)).toString()) : $r1.buildUpon().appendQueryParameter($r2, $r3).build();
    }

    private Uri zzb(Uri $r1, String $r2, String $r3) throws  {
        String $r4 = $r1.toString();
        int $i0 = $r4.indexOf(";adurl");
        if ($i0 != -1) {
            return Uri.parse(new StringBuilder($r4.substring(0, $i0 + 1)).append($r2).append("=").append($r3).append(";").append($r4.substring($i0 + 1)).toString());
        }
        String $r6 = $r1.getEncodedPath();
        $i0 = $r4.indexOf($r6);
        return Uri.parse(new StringBuilder($r4.substring(0, $r6.length() + $i0)).append(";").append($r2).append("=").append($r3).append(";").append($r4.substring($r6.length() + $i0)).toString());
    }

    public Uri zza(Uri $r1, Context $r2) throws zzat {
        return zza($r1, $r2, null, false);
    }

    public void zza(MotionEvent $r1) throws  {
        this.zzafz.zza($r1);
    }

    public boolean zza(Uri $r1) throws  {
        if ($r1 == null) {
            throw new NullPointerException();
        }
        try {
            return $r1.getHost().equals(this.zzafu) ? $r1.getPath().equals(this.zzafv) : false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public zzan zzaw() throws  {
        return this.zzafz;
    }

    public Uri zzb(Uri $r1, Context $r2) throws zzat {
        try {
            return zza($r1, $r2, $r1.getQueryParameter("ai"), true);
        } catch (UnsupportedOperationException e) {
            throw new zzat("Provided Uri is not in a valid state");
        }
    }

    public void zzb(String $r1, String $r2) throws  {
        this.zzafu = $r1;
        this.zzafv = $r2;
    }

    public boolean zzb(Uri $r1) throws  {
        if ($r1 == null) {
            throw new NullPointerException();
        }
        try {
            return $r1.getHost().equals(this.zzafw);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean zzc(android.net.Uri r9) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0022 in list []
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
        r8 = this;
        if (r9 != 0) goto L_0x0008;
    L_0x0002:
        r0 = new java.lang.NullPointerException;
        r0.<init>();
        throw r0;
    L_0x0008:
        r1 = r9.getHost();	 Catch:{ NullPointerException -> 0x001f }
        r2 = r8.zzafx;	 Catch:{ NullPointerException -> 0x001f }
        r3 = r2.length;	 Catch:{ NullPointerException -> 0x001f }
        r4 = 0;
    L_0x0010:
        if (r4 >= r3) goto L_0x0022;	 Catch:{ NullPointerException -> 0x001f }
    L_0x0012:
        r5 = r2[r4];	 Catch:{ NullPointerException -> 0x001f }
        r6 = r1.endsWith(r5);	 Catch:{ NullPointerException -> 0x001f }
        if (r6 == 0) goto L_0x001c;
    L_0x001a:
        r7 = 1;
        return r7;
    L_0x001c:
        r4 = r4 + 1;
        goto L_0x0010;
    L_0x001f:
        r0 = move-exception;
        r7 = 0;
        return r7;
    L_0x0022:
        r7 = 0;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzas.zzc(android.net.Uri):boolean");
    }

    public boolean zzd(Uri $r1) throws  {
        if (!zzc($r1)) {
            return false;
        }
        for (String $r3 : zzafy) {
            if ($r1.getPath().endsWith($r3)) {
                return true;
            }
        }
        return false;
    }

    public void zzk(String $r1) throws  {
        this.zzafx = $r1.split(",");
    }
}
