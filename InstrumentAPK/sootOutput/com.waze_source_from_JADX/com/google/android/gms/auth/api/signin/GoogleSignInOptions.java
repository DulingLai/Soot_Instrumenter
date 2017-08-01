package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zze;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.spotify.android.appremote.internal.SdkRemoteClientConnector;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleSignInOptions extends AbstractSafeParcelable implements Optional {
    public static final Creator<GoogleSignInOptions> CREATOR = new zzb();
    public static final GoogleSignInOptions DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
    private static Comparator<Scope> fD = new C06711();
    public static final Scope fE = new Scope(Scopes.PROFILE);
    public static final Scope fF = new Scope("email");
    public static final Scope fG = new Scope("openid");
    private Account f28P;
    private final ArrayList<Scope> fH;
    private boolean fI;
    private final boolean fJ;
    private final boolean fK;
    private String fL;
    private String fM;
    final int versionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    class C06711 implements Comparator<Scope> {
        C06711() throws  {
        }

        public /* synthetic */ int compare(Object $r1, Object $r2) throws  {
            return zza((Scope) $r1, (Scope) $r2);
        }

        public int zza(Scope $r1, Scope $r2) throws  {
            return $r1.zzasc().compareTo($r2.zzasc());
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private Account f27P;
        private boolean fI;
        private boolean fJ;
        private boolean fK;
        private String fL;
        private String fM;
        private Set<Scope> fN = new HashSet();

        public Builder(@NonNull GoogleSignInOptions $r1) throws  {
            zzab.zzag($r1);
            this.fN = new HashSet($r1.fH);
            this.fJ = $r1.fJ;
            this.fK = $r1.fK;
            this.fI = $r1.fI;
            this.fL = $r1.fL;
            this.f27P = $r1.f28P;
            this.fM = $r1.fM;
        }

        private String zzes(String $r1) throws  {
            zzab.zzgy($r1);
            boolean $z0 = this.fL == null || this.fL.equals($r1);
            zzab.zzb($z0, (Object) "two different server client ids provided");
            return $r1;
        }

        public GoogleSignInOptions build() throws  {
            if (this.fI && (this.f27P == null || !this.fN.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(this.fN, this.f27P, this.fI, this.fJ, this.fK, this.fL, this.fM);
        }

        public Builder requestEmail() throws  {
            this.fN.add(GoogleSignInOptions.fF);
            return this;
        }

        public Builder requestId() throws  {
            this.fN.add(GoogleSignInOptions.fG);
            return this;
        }

        public Builder requestIdToken(String $r1) throws  {
            this.fI = true;
            this.fL = zzes($r1);
            return this;
        }

        public Builder requestProfile() throws  {
            this.fN.add(GoogleSignInOptions.fE);
            return this;
        }

        public Builder requestScopes(Scope $r1, Scope... $r2) throws  {
            this.fN.add($r1);
            this.fN.addAll(Arrays.asList($r2));
            return this;
        }

        public Builder requestServerAuthCode(String $r1) throws  {
            return requestServerAuthCode($r1, false);
        }

        public Builder requestServerAuthCode(String $r1, boolean $z0) throws  {
            this.fJ = true;
            this.fL = zzes($r1);
            this.fK = $z0;
            return this;
        }

        public Builder setAccount(Account $r1) throws  {
            this.f27P = (Account) zzab.zzag($r1);
            return this;
        }

        public Builder setAccountName(String $r1) throws  {
            this.f27P = new Account(zzab.zzgy($r1), "com.google");
            return this;
        }

        public Builder setHostedDomain(String $r1) throws  {
            this.fM = zzab.zzgy($r1);
            return this;
        }
    }

    GoogleSignInOptions(@Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) ArrayList<Scope> $r1, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Account $r2, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z1, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z2, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
        this.versionCode = $i0;
        this.fH = $r1;
        this.f28P = $r2;
        this.fI = $z0;
        this.fJ = $z1;
        this.fK = $z2;
        this.fL = $r3;
        this.fM = $r4;
    }

    private GoogleSignInOptions(@Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<Scope> $r1, @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Account $r2, @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z1, @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) boolean $z2, @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Landroid/accounts/Account;", "ZZZ", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
        this(2, new ArrayList($r1), $r2, $z0, $z1, $z2, $r3, $r4);
    }

    private JSONObject zzaeh() throws  {
        JSONObject $r1 = new JSONObject();
        try {
            JSONArray $r2 = new JSONArray();
            Collections.sort(this.fH, fD);
            Iterator $r5 = this.fH.iterator();
            while ($r5.hasNext()) {
                $r2.put(((Scope) $r5.next()).zzasc());
            }
            $r1.put(SdkRemoteClientConnector.EXTRA_SCOPES, $r2);
            if (this.f28P != null) {
                $r1.put("accountName", this.f28P.name);
            }
            $r1.put("idTokenRequested", this.fI);
            $r1.put("forceCodeForRefreshToken", this.fK);
            $r1.put("serverAuthRequested", this.fJ);
            if (!TextUtils.isEmpty(this.fL)) {
                $r1.put("serverClientId", this.fL);
            }
            if (TextUtils.isEmpty(this.fM)) {
                return $r1;
            }
            $r1.put("hostedDomain", this.fM);
            return $r1;
        } catch (JSONException $r9) {
            throw new RuntimeException($r9);
        }
    }

    @Nullable
    public static GoogleSignInOptions zzer(@Nullable String $r0) throws JSONException {
        if (TextUtils.isEmpty($r0)) {
            return null;
        }
        Account $r5;
        JSONObject $r2 = new JSONObject($r0);
        HashSet $r1 = new HashSet();
        JSONArray $r3 = $r2.getJSONArray(SdkRemoteClientConnector.EXTRA_SCOPES);
        int $i0 = $r3.length();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r1.add(new Scope($r3.getString($i1)));
        }
        $r0 = $r2.optString("accountName", null);
        if (TextUtils.isEmpty($r0)) {
            $r5 = null;
        } else {
            Account account = new Account($r0, "com.google");
        }
        return new GoogleSignInOptions($r1, $r5, $r2.getBoolean("idTokenRequested"), $r2.getBoolean("serverAuthRequested"), $r2.getBoolean("forceCodeForRefreshToken"), $r2.optString("serverClientId", null), $r2.optString("hostedDomain", null));
    }

    public boolean equals(java.lang.Object r15) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005a in list []
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
        r14 = this;
        if (r15 != 0) goto L_0x0004;
    L_0x0002:
        r0 = 0;
        return r0;
    L_0x0004:
        r2 = r15;	 Catch:{ ClassCastException -> 0x0074 }
        r2 = (com.google.android.gms.auth.api.signin.GoogleSignInOptions) r2;	 Catch:{ ClassCastException -> 0x0074 }
        r1 = r2;	 Catch:{ ClassCastException -> 0x0074 }
        r3 = r14.fH;	 Catch:{ ClassCastException -> 0x0074 }
        r4 = r3.size();	 Catch:{ ClassCastException -> 0x0074 }
        r3 = r1.zzaei();	 Catch:{ ClassCastException -> 0x0074 }
        r5 = r3.size();	 Catch:{ ClassCastException -> 0x0074 }
        if (r4 != r5) goto L_0x0077;
    L_0x0018:
        r3 = r14.fH;	 Catch:{ ClassCastException -> 0x0074 }
        r6 = r1.zzaei();	 Catch:{ ClassCastException -> 0x0074 }
        r7 = r3.containsAll(r6);	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 == 0) goto L_0x0079;
    L_0x0024:
        r8 = r14.f28P;
        if (r8 != 0) goto L_0x005a;	 Catch:{ ClassCastException -> 0x0074 }
    L_0x0028:
        r8 = r1.getAccount();	 Catch:{ ClassCastException -> 0x0074 }
        if (r8 != 0) goto L_0x007b;
    L_0x002e:
        r9 = r14.fL;	 Catch:{ ClassCastException -> 0x0074 }
        r7 = android.text.TextUtils.isEmpty(r9);	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 == 0) goto L_0x0067;	 Catch:{ ClassCastException -> 0x0074 }
    L_0x0036:
        r9 = r1.zzaem();	 Catch:{ ClassCastException -> 0x0074 }
        r7 = android.text.TextUtils.isEmpty(r9);	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 == 0) goto L_0x007d;
    L_0x0040:
        r7 = r14.fK;	 Catch:{ ClassCastException -> 0x0074 }
        r10 = r1.zzael();	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 != r10) goto L_0x007f;
    L_0x0048:
        r7 = r14.fI;	 Catch:{ ClassCastException -> 0x0074 }
        r10 = r1.zzaej();	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 != r10) goto L_0x0081;
    L_0x0050:
        r7 = r14.fJ;	 Catch:{ ClassCastException -> 0x0074 }
        r10 = r1.zzaek();	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 != r10) goto L_0x0083;
    L_0x0058:
        r0 = 1;
        return r0;
    L_0x005a:
        r8 = r14.f28P;	 Catch:{ ClassCastException -> 0x0074 }
        r11 = r1.getAccount();	 Catch:{ ClassCastException -> 0x0074 }
        r7 = r8.equals(r11);	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 == 0) goto L_0x0085;
    L_0x0066:
        goto L_0x002e;
    L_0x0067:
        r9 = r14.fL;	 Catch:{ ClassCastException -> 0x0074 }
        r12 = r1.zzaem();	 Catch:{ ClassCastException -> 0x0074 }
        r7 = r9.equals(r12);	 Catch:{ ClassCastException -> 0x0074 }
        if (r7 == 0) goto L_0x0087;
    L_0x0073:
        goto L_0x0040;
    L_0x0074:
        r13 = move-exception;
        r0 = 0;
        return r0;
    L_0x0077:
        r0 = 0;
        return r0;
    L_0x0079:
        r0 = 0;
        return r0;
    L_0x007b:
        r0 = 0;
        return r0;
    L_0x007d:
        r0 = 0;
        return r0;
    L_0x007f:
        r0 = 0;
        return r0;
    L_0x0081:
        r0 = 0;
        return r0;
    L_0x0083:
        r0 = 0;
        return r0;
    L_0x0085:
        r0 = 0;
        return r0;
    L_0x0087:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.GoogleSignInOptions.equals(java.lang.Object):boolean");
    }

    public Account getAccount() throws  {
        return this.f28P;
    }

    public Scope[] getScopeArray() throws  {
        return (Scope[]) this.fH.toArray(new Scope[this.fH.size()]);
    }

    public int hashCode() throws  {
        ArrayList $r1 = new ArrayList();
        Iterator $r3 = this.fH.iterator();
        while ($r3.hasNext()) {
            $r1.add(((Scope) $r3.next()).zzasc());
        }
        Collections.sort($r1);
        return new zze().zzu($r1).zzu(this.f28P).zzu(this.fL).zzar(this.fK).zzar(this.fI).zzar(this.fJ).zzaeu();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }

    public String zzaef() throws  {
        return zzaeh().toString();
    }

    public ArrayList<Scope> zzaei() throws  {
        return new ArrayList(this.fH);
    }

    public boolean zzaej() throws  {
        return this.fI;
    }

    public boolean zzaek() throws  {
        return this.fJ;
    }

    public boolean zzael() throws  {
        return this.fK;
    }

    public String zzaem() throws  {
        return this.fL;
    }

    public String zzaen() throws  {
        return this.fM;
    }
}
