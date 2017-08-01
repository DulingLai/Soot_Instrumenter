package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.plus.FirstPartyPeople.LoadPersonResult;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.model.people.PersonEntity;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzf extends zzk<zze> {
    private Person aYy;
    private final PlusSession aYz;

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zza implements LoadPeopleResult {
        private final String aQQ;
        private final PersonBuffer aYA;
        private final Status cp;

        public zza(Status $r1, DataHolder $r2, String $r3) throws  {
            this.cp = $r1;
            this.aQQ = $r3;
            this.aYA = $r2 != null ? new PersonBuffer($r2) : null;
        }

        public String getNextPageToken() throws  {
            return this.aQQ;
        }

        public PersonBuffer getPersonBuffer() throws  {
            return this.aYA;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aYA != null) {
                this.aYA.release();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zzb implements LoadPersonResult {
        private final Person aYB;
        private final Status cp;

        public zzb(Status $r1, Person $r2) throws  {
            this.cp = $r1;
            this.aYB = $r2;
        }

        public Person getPerson() throws  {
            return this.aYB;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zzc extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> awc;

        public zzc(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(DataHolder $r2, String $r1) throws  {
            Status $r6 = new Status($r2.getStatusCode(), null, $r2.zzava() != null ? (PendingIntent) $r2.zzava().getParcelable("pendingIntent") : null);
            if (!($r6.isSuccess() || $r2 == null)) {
                if (!$r2.isClosed()) {
                    $r2.close();
                }
                $r2 = null;
            }
            this.awc.setResult(new zza($r6, $r2, $r1));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zzd extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadPersonResult> awc;

        public zzd(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/FirstPartyPeople$LoadPersonResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPersonResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, PersonEntity $r1) throws  {
            this.awc.setResult(new zzb(new Status($i0, null, null), $r1));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zze extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<Status> awc;

        public zze(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r1) throws  {
            this.awc = $r1;
        }

        public void zzl(int $i0, Bundle $r1) throws  {
            this.awc.setResult(new Status($i0, null, $r1 != null ? (PendingIntent) $r1.getParcelable("pendingIntent") : null));
        }
    }

    public zzf(Context $r1, Looper $r2, zzg $r3, PlusSession $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
        super($r1, $r2, 2, $r3, $r5, $r6);
        this.aYz = $r4;
    }

    public static boolean zze(@Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)Z"}) Set<Scope> $r0) throws  {
        return $r0 != null ? $r0.isEmpty() ? false : ($r0.size() == 1 && $r0.contains(new Scope("plus_one_placeholder_scope"))) ? false : true : false;
    }

    public String getAccountName() throws  {
        zzavw();
        try {
            return ((zze) zzavx()).getAccountName();
        } catch (RemoteException $r5) {
            throw new IllegalStateException($r5);
        }
    }

    public zzr zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2) throws  {
        zzavw();
        zzc $r3 = new zzc($r1);
        try {
            return ((zze) zzavx()).zza($r3, 1, $i0, -1, $r2);
        } catch (RemoteException e) {
            $r3.zza(DataHolder.empty(8), null);
            return null;
        }
    }

    protected void zza(int $i0, IBinder $r1, Bundle $r2, int $i1) throws  {
        if ($i0 == 0 && $r2 != null && $r2.containsKey("loaded_person")) {
            this.aYy = PersonEntity.zzar($r2.getByteArray("loaded_person"));
        }
        super.zza($i0, $r1, $r2, $i1);
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r2) throws  {
        zzavw();
        zzb $r3 = new zzc($r1);
        try {
            ((zze) zzavx()).zza($r3, new ArrayList($r2));
        } catch (RemoteException e) {
            $r3.zza(DataHolder.empty(8), null);
        }
    }

    protected Bundle zzadn() throws  {
        Bundle $r2 = this.aYz.zzchc();
        $r2.putStringArray("request_visible_actions", this.aYz.zzcgw());
        $r2.putString("auth_package", this.aYz.zzcgy());
        return $r2;
    }

    public boolean zzaec() throws  {
        return zze(zzaws().zzb(Plus.API));
    }

    public void zzah(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1) throws  {
        zzavw();
        zzc $r2 = new zzc($r1);
        try {
            ((zze) zzavx()).zza($r2, 2, 1, -1, null);
        } catch (RemoteException e) {
            $r2.zza(DataHolder.empty(8), null);
        }
    }

    public void zzai(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r1) throws  {
        zzavw();
        zzcgq();
        zze $r2 = new zze($r1);
        try {
            ((zze) zzavx()).zzb($r2);
        } catch (RemoteException e) {
            $r2.zzl(8, null);
        }
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzoz($r1);
    }

    public void zzcgq() throws  {
        zzavw();
        this.aYy = null;
        try {
            ((zze) zzavx()).zzcgq();
        } catch (RemoteException $r4) {
            throw new IllegalStateException($r4);
        }
    }

    public Person zzcgs() throws  {
        zzavw();
        return this.aYy;
    }

    public void zze(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;[", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;[", "Ljava/lang/String;", ")V"}) String[] $r2) throws  {
        zza($r1, Arrays.asList($r2));
    }

    protected zze zzoz(IBinder $r1) throws  {
        return com.google.android.gms.plus.internal.zze.zza.zzoy($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.plus.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    public zzr zzx(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2) throws  {
        return zza($r1, 0, $r2);
    }

    public void zzy(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/FirstPartyPeople$LoadPersonResult;", ">;", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LoadPersonResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/plus/FirstPartyPeople$LoadPersonResult;", ">;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        zzavw();
        zzd $r3 = new zzd($r1);
        try {
            ((zze) zzavx()).zze($r3, $r2);
        } catch (RemoteException e) {
            $r3.zza(8, null);
        }
    }
}
