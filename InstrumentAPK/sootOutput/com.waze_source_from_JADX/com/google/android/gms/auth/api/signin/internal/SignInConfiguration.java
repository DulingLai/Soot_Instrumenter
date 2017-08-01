package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public final class SignInConfiguration extends AbstractSafeParcelable {
    public static final Creator<SignInConfiguration> CREATOR = new zzj();
    private final String gb;
    private GoogleSignInOptions gc;
    final int versionCode;

    SignInConfiguration(int $i0, String $r1, GoogleSignInOptions $r2) throws  {
        this.versionCode = $i0;
        this.gb = zzab.zzgy($r1);
        this.gc = $r2;
    }

    public SignInConfiguration(String $r1, GoogleSignInOptions $r2) throws  {
        this(3, $r1, $r2);
    }

    public boolean equals(java.lang.Object r10) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0020 in list []
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
        r9 = this;
        if (r10 != 0) goto L_0x0004;
    L_0x0002:
        r0 = 0;
        return r0;
    L_0x0004:
        r2 = r10;	 Catch:{ ClassCastException -> 0x002d }
        r2 = (com.google.android.gms.auth.api.signin.internal.SignInConfiguration) r2;	 Catch:{ ClassCastException -> 0x002d }
        r1 = r2;	 Catch:{ ClassCastException -> 0x002d }
        r3 = r9.gb;	 Catch:{ ClassCastException -> 0x002d }
        r4 = r1.zzaey();	 Catch:{ ClassCastException -> 0x002d }
        r5 = r3.equals(r4);	 Catch:{ ClassCastException -> 0x002d }
        if (r5 == 0) goto L_0x0030;
    L_0x0014:
        r6 = r9.gc;
        if (r6 != 0) goto L_0x0020;	 Catch:{ ClassCastException -> 0x002d }
    L_0x0018:
        r6 = r1.zzaez();	 Catch:{ ClassCastException -> 0x002d }
        if (r6 != 0) goto L_0x0032;
    L_0x001e:
        r0 = 1;
        return r0;
    L_0x0020:
        r6 = r9.gc;	 Catch:{ ClassCastException -> 0x002d }
        r7 = r1.zzaez();	 Catch:{ ClassCastException -> 0x002d }
        r5 = r6.equals(r7);	 Catch:{ ClassCastException -> 0x002d }
        if (r5 == 0) goto L_0x0034;
    L_0x002c:
        goto L_0x001e;
    L_0x002d:
        r8 = move-exception;
        r0 = 0;
        return r0;
    L_0x0030:
        r0 = 0;
        return r0;
    L_0x0032:
        r0 = 0;
        return r0;
    L_0x0034:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.SignInConfiguration.equals(java.lang.Object):boolean");
    }

    public int hashCode() throws  {
        return new zze().zzu(this.gb).zzu(this.gc).zzaeu();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzj.zza(this, $r1, $i0);
    }

    public String zzaey() throws  {
        return this.gb;
    }

    public GoogleSignInOptions zzaez() throws  {
        return this.gc;
    }
}
