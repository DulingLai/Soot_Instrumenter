package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzaci;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzg {
    private final View CA;
    private final String CB;
    private final Set<Scope> Cx;
    private final int Cz;
    private final Set<Scope> Jk;
    private final Map<Api<?>, zza> Jl;
    private final zzaci Jm;
    private Integer Jn;
    private final Account f32P;
    private final String co;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza {
        public final boolean Jo;
        public final Set<Scope> fN;

        public zza(@Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;Z)V"}) Set<Scope> $r1, @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;Z)V"}) boolean $z0) throws  {
            zzab.zzag($r1);
            this.fN = Collections.unmodifiableSet($r1);
            this.Jo = $z0;
        }
    }

    public zzg(@Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) Account $r1, @Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) Set<Scope> $r2, @Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) Map<Api<?>, zza> $r8, @Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) int $i0, @Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) View $r3, @Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) String $r4, @Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) String $r5, @Signature({"(", "Landroid/accounts/Account;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/internal/zzg$zza;", ">;I", "Landroid/view/View;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzaci;", ")V"}) zzaci $r6) throws  {
        Map $r82;
        this.f32P = $r1;
        this.Cx = $r2 == null ? Collections.EMPTY_SET : Collections.unmodifiableSet($r2);
        if ($r8 == null) {
            $r82 = Collections.EMPTY_MAP;
        }
        this.Jl = $r82;
        this.CA = $r3;
        this.Cz = $i0;
        this.co = $r4;
        this.CB = $r5;
        this.Jm = $r6;
        HashSet $r7 = new HashSet(this.Cx);
        for (zza com_google_android_gms_common_internal_zzg_zza : this.Jl.values()) {
            $r7.addAll(com_google_android_gms_common_internal_zzg_zza.fN);
        }
        this.Jk = Collections.unmodifiableSet($r7);
    }

    public static zzg zzby(Context $r0) throws  {
        return new Builder($r0).zzarz();
    }

    public Account getAccount() throws  {
        return this.f32P;
    }

    @Deprecated
    public String getAccountName() throws  {
        return this.f32P != null ? this.f32P.name : null;
    }

    public Account zzavv() throws  {
        return this.f32P != null ? this.f32P : new Account("<<default account>>", "com.google");
    }

    public int zzawf() throws  {
        return this.Cz;
    }

    public Set<Scope> zzawg() throws  {
        return this.Cx;
    }

    public Set<Scope> zzawh() throws  {
        return this.Jk;
    }

    public Map<Api<?>, zza> zzawi() throws  {
        return this.Jl;
    }

    public String zzawj() throws  {
        return this.co;
    }

    public String zzawk() throws  {
        return this.CB;
    }

    public View zzawl() throws  {
        return this.CA;
    }

    public zzaci zzawm() throws  {
        return this.Jm;
    }

    public Integer zzawn() throws  {
        return this.Jn;
    }

    public Set<Scope> zzb(@Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;"}) Api<?> $r1) throws  {
        zza $r5 = (zza) this.Jl.get($r1);
        if ($r5 == null || $r5.fN.isEmpty()) {
            return this.Cx;
        }
        HashSet $r6 = new HashSet(this.Cx);
        $r6.addAll($r5.fN);
        return $r6;
    }

    public void zzd(Integer $r1) throws  {
        this.Jn = $r1;
    }
}
