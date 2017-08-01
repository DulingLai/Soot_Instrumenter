package com.google.android.gms.auth.firstparty.proximity.data;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class Permit extends AbstractSafeParcelable {
    public static final String CHANNEL_BLUETOOTH_CLASSIC = "bluetooth_classic";
    public static final PermitCreator CREATOR = new PermitCreator();
    public static final String TYPE_UNLOCK = "unlock";
    final String ia;
    final PermitAccess ib;
    List<PermitAccess> ic;
    final Map<String, PermitAccess> ie;
    List<String> f29if;
    final Set<String> ig;
    final int mVersion;
    final String zzbgd;
    final String zzcft;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private String ia;
        private PermitAccess ib;
        private Map<String, PermitAccess> ie = new HashMap();
        private Set<String> ig = new HashSet();
        private String zzbgd;
        private String zzcft;

        public Builder addAllowedChannel(String $r1) throws  {
            this.ig.add($r1);
            return this;
        }

        public Builder addRequesterAccess(PermitAccess $r1) throws  {
            this.ie.put($r1.getId(), $r1);
            return this;
        }

        public Permit build() throws  {
            return new Permit();
        }

        public Builder setAccountId(String $r1) throws  {
            this.ia = $r1;
            return this;
        }

        public Builder setId(String $r1) throws  {
            this.zzbgd = $r1;
            return this;
        }

        public Builder setLicense(PermitAccess $r1) throws  {
            this.ib = $r1;
            return this;
        }

        public Builder setType(String $r1) throws  {
            this.zzcft = $r1;
            return this;
        }
    }

    Permit(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) PermitAccess $r4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<PermitAccess> $r5, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r6) throws  {
        this($i0, $r1, $r2, $r3, $r4, zzy($r5), (Set) new HashSet($r6));
    }

    private Permit(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) PermitAccess $r4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Map<String, PermitAccess> $r5, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String> $r6) throws  {
        this.mVersion = $i0;
        this.zzbgd = zzab.zzgy($r1);
        this.ia = zzab.zzgy($r2);
        this.zzcft = zzab.zzgy($r3);
        this.ib = (PermitAccess) zzab.zzag($r4);
        this.ie = $r5 == null ? new HashMap() : new HashMap($r5);
        this.ig = $r6 == null ? new HashSet() : new HashSet($r6);
    }

    private Permit(Builder $r1) throws  {
        this(1, $r1.zzbgd, $r1.ia, $r1.zzcft, $r1.ib, $r1.ie, $r1.ig);
    }

    private static Map<String, PermitAccess> zzy(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;"}) List<PermitAccess> $r0) throws  {
        HashMap $r1 = new HashMap();
        for (PermitAccess $r4 : $r0) {
            $r1.put($r4.getId(), $r4);
        }
        return $r1;
    }

    public void addAllowedChannel(String $r1) throws  {
        this.ig.add($r1);
    }

    public void addRequesterAccess(PermitAccess $r1) throws  {
        this.ie.put($r1.getId(), $r1);
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof Permit)) {
            return false;
        }
        Permit $r2 = (Permit) $r1;
        return TextUtils.equals(this.ia, $r2.ia) ? TextUtils.equals(this.zzbgd, $r2.zzbgd) ? TextUtils.equals(this.zzcft, $r2.zzcft) ? this.ib.equals($r2.ib) ? this.ig.equals($r2.ig) ? this.ie.equals($r2.ie) : false : false : false : false : false;
    }

    public String getAccountId() throws  {
        return this.ia;
    }

    public List<String> getAllowedChannels() throws  {
        return Collections.unmodifiableList(new ArrayList(this.ig));
    }

    public String getId() throws  {
        return this.zzbgd;
    }

    public PermitAccess getLicense() throws  {
        return this.ib;
    }

    public PermitAccess getRequesterAccessById(String $r1) throws  {
        return (PermitAccess) this.ie.get($r1);
    }

    public List<PermitAccess> getRequesterAccesses() throws  {
        return Collections.unmodifiableList(new ArrayList(this.ie.values()));
    }

    public List<PermitAccess> getRequesterAccesses(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/firstparty/proximity/data/PermitAccess;", ">;"}) String $r1) throws  {
        ArrayList $r2 = new ArrayList();
        for (PermitAccess $r7 : this.ie.values()) {
            if (TextUtils.equals($r1, $r7.getType())) {
                $r2.add($r7);
            }
        }
        return Collections.unmodifiableList($r2);
    }

    public String getType() throws  {
        return this.zzcft;
    }

    public boolean hasAllowedChannel(String $r1) throws  {
        return this.ig.contains($r1);
    }

    public int hashCode() throws  {
        return ((((((((((this.zzbgd.hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.ia.hashCode()) * 31) + this.zzcft.hashCode()) * 31) + this.ig.hashCode()) * 31) + this.ib.hashCode()) * 31) + this.ie.hashCode();
    }

    public void removeAllowedChannel(String $r1) throws  {
        this.ig.remove($r1);
    }

    public PermitAccess removeRequesterAccess(String $r1) throws  {
        return (PermitAccess) this.ie.remove($r1);
    }

    public String toString() throws  {
        return String.format("Permit{ mVersion=%d, mId='%s', mAccountId='%s', mType='%s',  mLicense=%s, mAllowedChannels=%s, mRequesterAccesses=%s}", new Object[]{Integer.valueOf(this.mVersion), this.zzbgd, this.ia, this.zzcft, this.ib, this.ig, this.ie});
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        this.ic = new ArrayList(this.ie.values());
        this.f29if = new ArrayList(this.ig);
        PermitCreator.zza(this, $r1, $i0);
    }
}
