package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.zzg;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleSignInAccount extends AbstractSafeParcelable {
    public static final Creator<GoogleSignInAccount> CREATOR = new zza();
    private static Comparator<Scope> fD = new C06701();
    public static Clock fv = zzg.zzayy();
    private String cr;
    private String eT;
    List<Scope> ei;
    private String fA;
    private String fB;
    private String fC;
    private String fw;
    private Uri fx;
    private String fy;
    private long fz;
    final int versionCode;
    private String zzbgd;

    /* compiled from: dalvik_source_com.waze.apk */
    class C06701 implements Comparator<Scope> {
        C06701() throws  {
        }

        public /* synthetic */ int compare(Object $r1, Object $r2) throws  {
            return zza((Scope) $r1, (Scope) $r2);
        }

        public int zza(Scope $r1, Scope $r2) throws  {
            return $r1.zzasc().compareTo($r2.zzasc());
        }
    }

    GoogleSignInAccount(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Uri $r5, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) long $l1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<Scope> $r8, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r9, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/String;", "J", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r10) throws  {
        this.versionCode = $i0;
        this.zzbgd = $r1;
        this.eT = $r2;
        this.fw = $r3;
        this.cr = $r4;
        this.fx = $r5;
        this.fy = $r6;
        this.fz = $l1;
        this.fA = $r7;
        this.ei = $r8;
        this.fB = $r9;
        this.fC = $r10;
    }

    public static GoogleSignInAccount zza(@Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) String $r0, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) String $r2, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) String $r3, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) String $r4, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) String $r5, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) Uri $r6, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) Long $r10, @NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) String $r7, @NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/lang/Long;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;"}) Set<Scope> $r8) throws  {
        if ($r10 == null) {
            long $l0 = fv.currentTimeMillis() / 1000;
            long j = $l0;
            $r10 = Long.valueOf($l0);
        }
        return new GoogleSignInAccount(3, $r0, $r1, $r2, $r3, $r6, null, $r10.longValue(), zzab.zzgy($r7), new ArrayList((Collection) zzab.zzag($r8)), $r4, $r5);
    }

    private JSONObject zzaeh() throws  {
        JSONObject $r1 = new JSONObject();
        try {
            if (getId() != null) {
                $r1.put("id", getId());
            }
            if (getIdToken() != null) {
                $r1.put("tokenId", getIdToken());
            }
            if (getEmail() != null) {
                $r1.put("email", getEmail());
            }
            if (getDisplayName() != null) {
                $r1.put("displayName", getDisplayName());
            }
            if (getGivenName() != null) {
                $r1.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                $r1.put("familyName", getFamilyName());
            }
            if (getPhotoUrl() != null) {
                $r1.put("photoUrl", getPhotoUrl().toString());
            }
            if (getServerAuthCode() != null) {
                $r1.put("serverAuthCode", getServerAuthCode());
            }
            $r1.put("expirationTime", this.fz);
            $r1.put("obfuscatedIdentifier", zzaee());
            JSONArray $r4 = new JSONArray();
            Collections.sort(this.ei, fD);
            for (Scope zzasc : this.ei) {
                $r4.put(zzasc.zzasc());
            }
            $r1.put("grantedScopes", $r4);
            return $r1;
        } catch (JSONException $r10) {
            throw new RuntimeException($r10);
        }
    }

    @Nullable
    public static GoogleSignInAccount zzep(@Nullable String $r0) throws JSONException {
        if (TextUtils.isEmpty($r0)) {
            return null;
        }
        JSONObject $r2 = new JSONObject($r0);
        $r0 = $r2.optString("photoUrl", null);
        Uri $r3 = !TextUtils.isEmpty($r0) ? Uri.parse($r0) : null;
        long $l0 = Long.parseLong($r2.getString("expirationTime"));
        HashSet hashSet = new HashSet();
        JSONArray $r4 = $r2.getJSONArray("grantedScopes");
        int $i1 = $r4.length();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            hashSet.add(new Scope($r4.getString($i2)));
        }
        return zza($r2.optString("id"), $r2.optString("tokenId", null), $r2.optString("email", null), $r2.optString("displayName", null), $r2.optString("givenName", null), $r2.optString("familyName", null), $r3, Long.valueOf($l0), $r2.getString("obfuscatedIdentifier"), hashSet).zzeq($r2.optString("serverAuthCode", null));
    }

    public boolean equals(Object $r1) throws  {
        return !($r1 instanceof GoogleSignInAccount) ? false : ((GoogleSignInAccount) $r1).zzaef().equals(zzaef());
    }

    @Nullable
    public String getDisplayName() throws  {
        return this.cr;
    }

    @Nullable
    public String getEmail() throws  {
        return this.fw;
    }

    @Nullable
    public String getFamilyName() throws  {
        return this.fC;
    }

    @Nullable
    public String getGivenName() throws  {
        return this.fB;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() throws  {
        return new HashSet(this.ei);
    }

    @Nullable
    public String getId() throws  {
        return this.zzbgd;
    }

    @Nullable
    public String getIdToken() throws  {
        return this.eT;
    }

    @Nullable
    public Uri getPhotoUrl() throws  {
        return this.fx;
    }

    @Nullable
    public String getServerAuthCode() throws  {
        return this.fy;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }

    public boolean zza() throws  {
        return fv.currentTimeMillis() / 1000 >= this.fz - 300;
    }

    public long zzaed() throws  {
        return this.fz;
    }

    @NonNull
    public String zzaee() throws  {
        return this.fA;
    }

    public String zzaef() throws  {
        return zzaeh().toString();
    }

    public String zzaeg() throws  {
        JSONObject $r1 = zzaeh();
        $r1.remove("serverAuthCode");
        return $r1.toString();
    }

    public GoogleSignInAccount zzeq(String $r1) throws  {
        this.fy = $r1;
        return this;
    }
}
