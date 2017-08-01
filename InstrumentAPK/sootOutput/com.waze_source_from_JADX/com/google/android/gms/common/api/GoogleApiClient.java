package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzh;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzg.zza;
import com.google.android.gms.internal.zzacg;
import com.google.android.gms.internal.zzach;
import com.google.android.gms.internal.zzaci;
import com.google.android.gms.internal.zzqi;
import com.google.android.gms.internal.zzqk;
import com.google.android.gms.internal.zzrg;
import com.google.android.gms.internal.zzrm;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzsa;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class GoogleApiClient {
    private static final Set<GoogleApiClient> Cw = Collections.newSetFromMap(new WeakHashMap());
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private View CA;
        private String CB;
        private final Map<Api<?>, zza> CC;
        private final Map<Api<?>, ApiOptions> CD;
        private zzrg CE;
        private int CF;
        private OnConnectionFailedListener CG;
        private GoogleApiAvailability CH;
        private Api.zza<? extends zzach, zzaci> CI;
        private final ArrayList<ConnectionCallbacks> CJ;
        private final ArrayList<OnConnectionFailedListener> CK;
        private final Set<Scope> Cx;
        private final Set<Scope> Cy;
        private int Cz;
        private Account f30P;
        private String co;
        private final Context mContext;
        private Looper zzaih;

        public Builder(@NonNull Context $r1) throws  {
            this.Cx = new HashSet();
            this.Cy = new HashSet();
            this.CC = new ArrayMap();
            this.CD = new ArrayMap();
            this.CF = -1;
            this.CH = GoogleApiAvailability.getInstance();
            this.CI = zzacg.cb;
            this.CJ = new ArrayList();
            this.CK = new ArrayList();
            this.mContext = $r1;
            this.zzaih = $r1.getMainLooper();
            this.co = $r1.getPackageName();
            this.CB = $r1.getClass().getName();
        }

        public Builder(@NonNull Context $r1, @NonNull ConnectionCallbacks $r2, @NonNull OnConnectionFailedListener $r3) throws  {
            this($r1);
            zzab.zzb((Object) $r2, (Object) "Must provide a connected listener");
            this.CJ.add($r2);
            zzab.zzb((Object) $r3, (Object) "Must provide a connection failed listener");
            this.CK.add($r3);
        }

        private static <C extends zze, O> C zza(@Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TC;"}) Api.zza<C, O> $r0, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TC;"}) Object $r1, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TC;"}) Context $r2, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TC;"}) Looper $r3, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TC;"}) zzg $r4, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TC;"}) ConnectionCallbacks $r5, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TC;"}) OnConnectionFailedListener $r6) throws  {
            return $r0.zza($r2, $r3, $r4, $r1, $r5, $r6);
        }

        private Builder zza(@NonNull zzrg $r1, int $i0, @Nullable OnConnectionFailedListener $r2) throws  {
            zzab.zzb($i0 >= 0, (Object) "clientId must be non-negative");
            this.CF = $i0;
            this.CG = $r2;
            this.CE = $r1;
            return this;
        }

        private static <C extends Api.zzg, O> zzah zza(@Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zzg;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zzh", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")", "Lcom/google/android/gms/common/internal/zzah;"}) zzh<C, O> $r0, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zzg;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zzh", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")", "Lcom/google/android/gms/common/internal/zzah;"}) Object $r1, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zzg;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zzh", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")", "Lcom/google/android/gms/common/internal/zzah;"}) Context $r2, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zzg;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zzh", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")", "Lcom/google/android/gms/common/internal/zzah;"}) Looper $r3, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zzg;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zzh", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")", "Lcom/google/android/gms/common/internal/zzah;"}) zzg $r4, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zzg;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zzh", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")", "Lcom/google/android/gms/common/internal/zzah;"}) ConnectionCallbacks $r5, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zzg;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Api$zzh", "<TC;TO;>;", "Ljava/lang/Object;", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")", "Lcom/google/android/gms/common/internal/zzah;"}) OnConnectionFailedListener $r6) throws  {
            return new zzah($r2, $r3, $r0.zzarp(), $r5, $r6, $r4, $r0.zzv($r1));
        }

        private <O extends ApiOptions> void zza(@Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;I[", "Lcom/google/android/gms/common/api/Scope;", ")V"}) Api<O> $r1, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;I[", "Lcom/google/android/gms/common/api/Scope;", ")V"}) O $r2, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;I[", "Lcom/google/android/gms/common/api/Scope;", ")V"}) int $i0, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;I[", "Lcom/google/android/gms/common/api/Scope;", ")V"}) Scope... $r3) throws  {
            boolean $z0 = true;
            if ($i0 != 1) {
                if ($i0 == 2) {
                    $z0 = false;
                } else {
                    throw new IllegalArgumentException("Invalid resolution mode: '" + $i0 + "', use a constant from GoogleApiClient.ResolutionMode");
                }
            }
            HashSet $r5 = new HashSet($r1.zzari().zzt($r2));
            for (Scope $r4 : $r3) {
                $r5.add($r4);
            }
            this.CC.put($r1, new zza($r5, $z0));
        }

        private com.google.android.gms.common.api.GoogleApiClient zzasa() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:46:0x02cc in {7, 12, 13, 17, 20, 22, 26, 28, 29, 34, 37, 38, 40, 42, 44, 47} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
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
            r58 = this;
            r0 = r58;
            r14 = r0.zzarz();
            r15 = 0;
            r16 = r14.zzawi();
            r17 = new android.support.v4.util.ArrayMap;
            r0 = r17;
            r0.<init>();
            r18 = new android.support.v4.util.ArrayMap;
            r0 = r18;
            r0.<init>();
            r19 = new java.util.ArrayList;
            r0 = r19;
            r0.<init>();
            r0 = r58;
            r0 = r0.CD;
            r20 = r0;
            r21 = r0.keySet();
            r0 = r21;
            r22 = r0.iterator();
            r23 = 0;
        L_0x0032:
            r0 = r22;
            r24 = r0.hasNext();
            if (r24 == 0) goto L_0x019d;
        L_0x003a:
            r0 = r22;
            r25 = r0.next();
            r27 = r25;
            r27 = (com.google.android.gms.common.api.Api) r27;
            r26 = r27;
            r0 = r58;
            r0 = r0.CD;
            r20 = r0;
            r1 = r26;
            r25 = r0.get(r1);
            r28 = 0;
            r0 = r16;
            r1 = r26;
            r29 = r0.get(r1);
            if (r29 == 0) goto L_0x0076;
        L_0x005e:
            r0 = r16;
            r1 = r26;
            r29 = r0.get(r1);
            r31 = r29;
            r31 = (com.google.android.gms.common.internal.zzg.zza) r31;
            r30 = r31;
            r0 = r30;
            r0 = r0.Jo;
            r24 = r0;
            if (r24 == 0) goto L_0x015d;
        L_0x0074:
            r28 = 1;
        L_0x0076:
            r0 = r28;
            r32 = java.lang.Integer.valueOf(r0);
            r0 = r17;
            r1 = r26;
            r2 = r32;
            r0.put(r1, r2);
            r33 = new com.google.android.gms.internal.zzqn;
            r0 = r33;
            r1 = r26;
            r2 = r28;
            r0.<init>(r1, r2);
            r0 = r19;
            r1 = r33;
            r0.add(r1);
            r0 = r26;
            r24 = r0.zzarm();
            if (r24 == 0) goto L_0x0160;
        L_0x009f:
            r0 = r26;
            r34 = r0.zzark();
            r0 = r34;
            r35 = r0.getPriority();
            r36 = 1;
            r0 = r35;
            r1 = r36;
            if (r0 != r1) goto L_0x02d1;
        L_0x00b3:
            r23 = r26;
        L_0x00b5:
            r0 = r58;
            r0 = r0.mContext;
            r37 = r0;
            r0 = r58;
            r0 = r0.zzaih;
            r38 = r0;
            r0 = r34;
            r1 = r25;
            r2 = r37;
            r3 = r38;
            r4 = r14;
            r5 = r33;
            r6 = r33;
            r39 = zza(r0, r1, r2, r3, r4, r5, r6);
        L_0x00d2:
            r0 = r26;
            r40 = r0.zzarl();
            r0 = r18;
            r1 = r40;
            r2 = r39;
            r0.put(r1, r2);
            r0 = r39;
            r24 = r0.zzaer();
            if (r24 == 0) goto L_0x0194;
        L_0x00e9:
            goto L_0x00ed;
        L_0x00ea:
            goto L_0x0076;
        L_0x00ed:
            if (r15 == 0) goto L_0x019a;
        L_0x00ef:
            r41 = new java.lang.IllegalStateException;
            r0 = r26;
            r42 = r0.getName();
            r0 = r42;
            r42 = java.lang.String.valueOf(r0);
            r43 = r15.getName();
            r0 = r43;
            r43 = java.lang.String.valueOf(r0);
            r44 = new java.lang.StringBuilder;
            r0 = r42;
            r45 = java.lang.String.valueOf(r0);
            r0 = r45;
            r35 = r0.length();
            r35 = r35 + 21;
            r0 = r43;
            r45 = java.lang.String.valueOf(r0);
            r0 = r45;
            r46 = r0.length();
            r0 = r35;
            r1 = r46;
            r0 = r0 + r1;
            r35 = r0;
            r0 = r44;
            r1 = r35;
            r0.<init>(r1);
            goto L_0x0135;
        L_0x0132:
            goto L_0x00d2;
        L_0x0135:
            r0 = r44;
            r1 = r42;
            r44 = r0.append(r1);
            r47 = " cannot be used with ";
            r0 = r44;
            r1 = r47;
            r44 = r0.append(r1);
            r0 = r44;
            r1 = r43;
            r44 = r0.append(r1);
            r0 = r44;
            r42 = r0.toString();
            r0 = r41;
            r1 = r42;
            r0.<init>(r1);
            throw r41;
        L_0x015d:
            r28 = 2;
            goto L_0x00ea;
        L_0x0160:
            r0 = r26;
            r48 = r0.zzarj();
            r0 = r48;
            r35 = r0.getPriority();
            r36 = 1;
            r0 = r35;
            r1 = r36;
            if (r0 != r1) goto L_0x02d0;
        L_0x0174:
            r23 = r26;
        L_0x0176:
            r0 = r58;
            r0 = r0.mContext;
            r37 = r0;
            r0 = r58;
            r0 = r0.zzaih;
            r38 = r0;
            r0 = r48;
            r1 = r25;
            r2 = r37;
            r3 = r38;
            r4 = r14;
            r5 = r33;
            r6 = r33;
            r39 = zza(r0, r1, r2, r3, r4, r5, r6);
            goto L_0x0132;
        L_0x0194:
            r26 = r15;
            goto L_0x019a;
        L_0x0197:
            goto L_0x0032;
        L_0x019a:
            r15 = r26;
            goto L_0x0197;
        L_0x019d:
            if (r15 == 0) goto L_0x025f;
        L_0x019f:
            if (r23 == 0) goto L_0x020b;
        L_0x01a1:
            r41 = new java.lang.IllegalStateException;
            r42 = r15.getName();
            r0 = r42;
            r42 = java.lang.String.valueOf(r0);
            r0 = r23;
            r43 = r0.getName();
            r0 = r43;
            r43 = java.lang.String.valueOf(r0);
            r44 = new java.lang.StringBuilder;
            r0 = r42;
            r45 = java.lang.String.valueOf(r0);
            r0 = r45;
            r35 = r0.length();
            r35 = r35 + 21;
            r0 = r43;
            r45 = java.lang.String.valueOf(r0);
            r0 = r45;
            r46 = r0.length();
            r0 = r35;
            r1 = r46;
            r0 = r0 + r1;
            r35 = r0;
            r0 = r44;
            r1 = r35;
            r0.<init>(r1);
            r0 = r44;
            r1 = r42;
            r44 = r0.append(r1);
            r47 = " cannot be used with ";
            r0 = r44;
            r1 = r47;
            r44 = r0.append(r1);
            r0 = r44;
            r1 = r43;
            r44 = r0.append(r1);
            r0 = r44;
            r42 = r0.toString();
            r0 = r41;
            r1 = r42;
            r0.<init>(r1);
            throw r41;
        L_0x020b:
            r0 = r58;
            r0 = r0.f30P;
            r49 = r0;
            if (r49 != 0) goto L_0x02c5;
        L_0x0213:
            r24 = 1;
        L_0x0215:
            r36 = 1;
            r0 = r36;
            r0 = new java.lang.Object[r0];
            r50 = r0;
            r42 = r15.getName();
            r36 = 0;
            r50[r36] = r42;
            r47 = "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead";
            r0 = r24;
            r1 = r47;
            r2 = r50;
            com.google.android.gms.common.internal.zzab.zza(r0, r1, r2);
            r0 = r58;
            r0 = r0.Cx;
            r21 = r0;
            r0 = r58;
            r0 = r0.Cy;
            r51 = r0;
            r0 = r21;
            r1 = r51;
            r24 = r0.equals(r1);
            r36 = 1;
            r0 = r36;
            r0 = new java.lang.Object[r0];
            r50 = r0;
            r42 = r15.getName();
            r36 = 0;
            r50[r36] = r42;
            r47 = "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.";
            r0 = r24;
            r1 = r47;
            r2 = r50;
            com.google.android.gms.common.internal.zzab.zza(r0, r1, r2);
        L_0x025f:
            r0 = r18;
            r52 = r0.values();
            r36 = 1;
            r0 = r52;
            r1 = r36;
            r35 = com.google.android.gms.internal.zzqw.zza(r0, r1);
            goto L_0x0273;
        L_0x0270:
            goto L_0x0215;
        L_0x0273:
            r53 = new com.google.android.gms.internal.zzqw;
            r0 = r58;
            r0 = r0.mContext;
            r37 = r0;
            r54 = new java.util.concurrent.locks.ReentrantLock;
            r0 = r54;
            r0.<init>();
            r0 = r58;
            r0 = r0.zzaih;
            r38 = r0;
            r0 = r58;
            r0 = r0.CH;
            r55 = r0;
            r0 = r58;
            r0 = r0.CI;
            r48 = r0;
            r0 = r58;
            r0 = r0.CJ;
            r56 = r0;
            r0 = r58;
            r0 = r0.CK;
            r57 = r0;
            r0 = r58;
            r0 = r0.CF;
            r46 = r0;
            r0 = r53;
            r1 = r37;
            r2 = r54;
            r3 = r38;
            r4 = r14;
            r5 = r55;
            r6 = r48;
            r7 = r17;
            r8 = r56;
            r9 = r57;
            r10 = r18;
            r11 = r46;
            r12 = r35;
            r13 = r19;
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);
            return r53;
        L_0x02c5:
            r24 = 0;
            goto L_0x02cb;
        L_0x02c8:
            goto L_0x0176;
        L_0x02cb:
            goto L_0x0270;
            goto L_0x02d0;
        L_0x02cd:
            goto L_0x00b5;
        L_0x02d0:
            goto L_0x02c8;
        L_0x02d1:
            goto L_0x02cd;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.GoogleApiClient.Builder.zzasa():com.google.android.gms.common.api.GoogleApiClient");
        }

        private void zzi(GoogleApiClient $r1) throws  {
            zzqi.zza(this.CE).zza(this.CF, $r1, this.CG);
        }

        public Builder addApi(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<+", "Lcom/google/android/gms/common/api/Api$ApiOptions$NotRequiredOptions;", ">;)", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) Api<? extends NotRequiredOptions> $r1) throws  {
            zzab.zzb((Object) $r1, (Object) "Api must not be null");
            this.CD.put($r1, null);
            List $r4 = $r1.zzari().zzt(null);
            this.Cy.addAll($r4);
            this.Cx.addAll($r4);
            return this;
        }

        public <O extends HasOptions> Builder addApi(@NonNull @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions$HasOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) Api<O> $r1, @NonNull @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions$HasOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) O $r2) throws  {
            zzab.zzb((Object) $r1, (Object) "Api must not be null");
            zzab.zzb((Object) $r2, (Object) "Null options are not permitted for this Api");
            this.CD.put($r1, $r2);
            List $r5 = $r1.zzari().zzt($r2);
            this.Cy.addAll($r5);
            this.Cx.addAll($r5);
            return this;
        }

        public <O extends HasOptions> Builder addApiIfAvailable(@NonNull @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions$HasOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;[", "Lcom/google/android/gms/common/api/Scope;", ")", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) Api<O> $r1, @NonNull @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions$HasOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;[", "Lcom/google/android/gms/common/api/Scope;", ")", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) O $r2, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions$HasOptions;", ">(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;[", "Lcom/google/android/gms/common/api/Scope;", ")", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) Scope... $r3) throws  {
            zzab.zzb((Object) $r1, (Object) "Api must not be null");
            zzab.zzb((Object) $r2, (Object) "Null options are not permitted for this Api");
            this.CD.put($r1, $r2);
            zza($r1, $r2, 1, $r3);
            return this;
        }

        public Builder addApiIfAvailable(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<+", "Lcom/google/android/gms/common/api/Api$ApiOptions$NotRequiredOptions;", ">;[", "Lcom/google/android/gms/common/api/Scope;", ")", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) Api<? extends NotRequiredOptions> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<+", "Lcom/google/android/gms/common/api/Api$ApiOptions$NotRequiredOptions;", ">;[", "Lcom/google/android/gms/common/api/Scope;", ")", "Lcom/google/android/gms/common/api/GoogleApiClient$Builder;"}) Scope... $r2) throws  {
            zzab.zzb((Object) $r1, (Object) "Api must not be null");
            this.CD.put($r1, null);
            zza($r1, null, 1, $r2);
            return this;
        }

        public Builder addConnectionCallbacks(@NonNull ConnectionCallbacks $r1) throws  {
            zzab.zzb((Object) $r1, (Object) "Listener must not be null");
            this.CJ.add($r1);
            return this;
        }

        public Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener $r1) throws  {
            zzab.zzb((Object) $r1, (Object) "Listener must not be null");
            this.CK.add($r1);
            return this;
        }

        public Builder addScope(@NonNull Scope $r1) throws  {
            zzab.zzb((Object) $r1, (Object) "Scope must not be null");
            this.Cx.add($r1);
            return this;
        }

        public GoogleApiClient build() throws  {
            zzab.zzb(!this.CD.isEmpty(), (Object) "must call addApi() to add at least one API");
            GoogleApiClient $r2 = zzasa();
            synchronized (GoogleApiClient.Cw) {
                GoogleApiClient.Cw.add($r2);
            }
            if (this.CF < 0) {
                return $r2;
            }
            zzi($r2);
            return $r2;
        }

        public Builder enableAutoManage(@NonNull FragmentActivity $r1, int $i0, @Nullable OnConnectionFailedListener $r2) throws  {
            return zza(new zzrg($r1), $i0, $r2);
        }

        public Builder enableAutoManage(@NonNull FragmentActivity $r1, @Nullable OnConnectionFailedListener $r2) throws  {
            return enableAutoManage($r1, 0, $r2);
        }

        public Builder setAccount(Account $r1) throws  {
            this.f30P = $r1;
            return this;
        }

        public Builder setAccountName(String $r1) throws  {
            this.f30P = $r1 == null ? null : new Account($r1, "com.google");
            return this;
        }

        public Builder setGravityForPopups(int $i0) throws  {
            this.Cz = $i0;
            return this;
        }

        public Builder setHandler(@NonNull Handler $r1) throws  {
            zzab.zzb((Object) $r1, (Object) "Handler must not be null");
            this.zzaih = $r1.getLooper();
            return this;
        }

        public Builder setViewForPopups(@NonNull View $r1) throws  {
            zzab.zzb((Object) $r1, (Object) "View must not be null");
            this.CA = $r1;
            return this;
        }

        public Builder useDefaultAccount() throws  {
            return setAccountName("<<default account>>");
        }

        public zzg zzarz() throws  {
            zzaci $r6 = zzaci.bgm;
            if (this.CD.containsKey(zzacg.API)) {
                $r6 = (zzaci) this.CD.get(zzacg.API);
            }
            return new zzg(this.f30P, this.Cx, this.CC, this.Cz, this.CA, this.co, this.CB, $r6);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle) throws ;

        void onConnectionSuspended(int i) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface OnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult) throws ;
    }

    public static void dumpAll(String $r0, FileDescriptor $r1, PrintWriter $r2, String[] $r3) throws  {
        synchronized (Cw) {
            String $r5 = String.valueOf($r0).concat("  ");
            int $i0 = 0;
            for (GoogleApiClient $r9 : Cw) {
                $r2.append($r0).append("GoogleApiClient#").println($i0);
                $r9.dump($r5, $r1, $r2, $r3);
                $i0++;
            }
        }
    }

    public static Set<GoogleApiClient> zzarw() throws  {
        Set r2;
        synchronized (Cw) {
            r2 = Cw;
        }
        return r2;
    }

    public abstract ConnectionResult blockingConnect() throws ;

    public abstract ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) throws ;

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect() throws ;

    public abstract void connect() throws ;

    public void connect(int i) throws  {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect() throws ;

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws ;

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)", "Lcom/google/android/gms/common/ConnectionResult;"}) Api<?> api) throws ;

    public Context getContext() throws  {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() throws  {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)Z"}) Api<?> api) throws ;

    public abstract boolean isConnected() throws ;

    public abstract boolean isConnecting() throws ;

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) throws ;

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) throws ;

    public abstract void reconnect() throws ;

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) throws ;

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) throws ;

    public abstract void stopAutoManage(@NonNull FragmentActivity fragmentActivity) throws ;

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) throws ;

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) throws ;

    @NonNull
    public <C extends zze> C zza(@NonNull @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", ">(", "Lcom/google/android/gms/common/api/Api$zzc", "<TC;>;)TC;"}) zzc<C> com_google_android_gms_common_api_Api_zzc_C) throws  {
        throw new UnsupportedOperationException();
    }

    public void zza(ResultStore resultStore) throws  {
        throw new UnsupportedOperationException();
    }

    public void zza(zzsa com_google_android_gms_internal_zzsa) throws  {
        throw new UnsupportedOperationException();
    }

    public boolean zza(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)Z"}) Api<?> api) throws  {
        throw new UnsupportedOperationException();
    }

    public boolean zza(zzru com_google_android_gms_internal_zzru) throws  {
        throw new UnsupportedOperationException();
    }

    public void zzarx() throws  {
        throw new UnsupportedOperationException();
    }

    public void zzb(zzsa com_google_android_gms_internal_zzsa) throws  {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, R extends Result, T extends zzqk.zza<R, A>> T zzc(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T t) throws  {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, T extends zzqk.zza<? extends Result, A>> T zzd(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T t) throws  {
        throw new UnsupportedOperationException();
    }

    public <L> zzrm<L> zzw(@NonNull @Signature({"<", "L:Ljava/lang/Object;", ">(T", "L;", ")", "Lcom/google/android/gms/internal/zzrm", "<T", "L;", ">;"}) L l) throws  {
        throw new UnsupportedOperationException();
    }
}
