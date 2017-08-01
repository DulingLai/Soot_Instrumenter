package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.os.Binder;
import com.google.android.gms.auth.api.signin.internal.zzf.zza;
import com.google.android.gms.common.GooglePlayServicesUtilLight;

/* compiled from: dalvik_source_com.waze.apk */
public class zzi extends zza {
    private final Context mContext;

    public zzi(Context $r1) throws  {
        this.mContext = $r1;
    }

    private void zzaew() throws  {
        if (!GooglePlayServicesUtilLight.zzd(this.mContext, Binder.getCallingUid())) {
            throw new SecurityException("Calling UID " + Binder.getCallingUid() + " is not Google Play services.");
        }
    }

    private void zzaex() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0038 in list []
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
        r11 = this;
        r0 = r11.mContext;
        r1 = com.google.android.gms.auth.api.signin.internal.zzk.zzbc(r0);
        r2 = r1.zzafb();
        r3 = com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN;
        if (r2 == 0) goto L_0x0012;
    L_0x000e:
        r3 = r1.zzafc();
    L_0x0012:
        r4 = new com.google.android.gms.common.api.GoogleApiClient$Builder;
        r0 = r11.mContext;
        r4.<init>(r0);
        r5 = com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API;
        r4 = r4.addApi(r5, r3);
        r6 = r4.build();
        r7 = r6.blockingConnect();	 Catch:{ Throwable -> 0x003c }
        r8 = r7.isSuccess();	 Catch:{ Throwable -> 0x003c }
        if (r8 == 0) goto L_0x0034;
    L_0x002d:
        if (r2 == 0) goto L_0x0038;	 Catch:{ Throwable -> 0x003c }
    L_0x002f:
        r9 = com.google.android.gms.auth.api.Auth.GoogleSignInApi;	 Catch:{ Throwable -> 0x003c }
        r9.revokeAccess(r6);	 Catch:{ Throwable -> 0x003c }
    L_0x0034:
        r6.disconnect();
        return;
    L_0x0038:
        r6.clearDefaultAccountAndReconnect();	 Catch:{ Throwable -> 0x003c }
        goto L_0x0034;
    L_0x003c:
        r10 = move-exception;
        r6.disconnect();
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.zzi.zzaex():void");
    }

    public void zzaev() throws  {
        zzaew();
        zzaex();
    }
}
