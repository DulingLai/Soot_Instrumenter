package com.google.android.gms.people.internal.autocomplete;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.internal.zzrq;
import com.google.android.gms.people.Autocomplete.AutocompleteSession;
import com.google.android.gms.people.Autocomplete.Autocompletion;
import com.google.android.gms.people.Autocomplete.AutocompletionListener;
import com.google.android.gms.people.Autocomplete.ClientConfig;
import com.google.android.gms.people.Autocomplete.ContactGroup;
import com.google.android.gms.people.Autocomplete.LoadPhotoOptions;
import com.google.android.gms.people.Autocomplete.LoadPhotoResult;
import com.google.android.gms.people.Autocomplete.Person;
import com.google.android.gms.people.Autocomplete.Photo;
import com.google.android.gms.people.Autocomplete.PreferredFieldsResult;
import com.google.android.gms.people.Images.LoadImageOptions.Builder;
import com.google.android.gms.people.Images.LoadImageResult;
import com.google.android.gms.people.People;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.internal.zzn.zzd;
import com.google.android.gms.people.internal.zzn.zzl;
import com.google.android.gms.people.model.AvatarReference;
import com.google.android.gms.people.model.ContactGroupPreferredFieldsBuffer;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements AutocompleteSession {
    private static long aTZ = 0;
    private boolean aTT = false;
    private AutocompletionListener aTU;
    private ClientConfig aTV;
    private long aTW;
    private zza aTX;
    private com.google.android.gms.people.People.zza<zzl> aTY;
    public String mAccount;

    /* compiled from: dalvik_source_com.waze.apk */
    class C09882 extends zzrq<LoadImageResult, LoadPhotoResult> {
        C09882(PendingResult $r1) throws  {
            super($r1);
        }

        protected LoadPhotoResult zza(LoadImageResult $r1) throws  {
            return new LoadPhotoResult($r1.getStatus(), $r1.getParcelFileDescriptor(), $r1.isRewindable(), $r1.getWidth(), $r1.getHeight());
        }

        protected /* synthetic */ Result zzf(Result $r2) throws  {
            return zza((LoadImageResult) $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza implements zzd {
        private AutocompletionListener aTU;
        private boolean aUf = false;
        private volatile boolean aUg = false;
        private Set<Integer> aUh = new HashSet();
        private Map<Integer, List<Autocompletion>> aUi = new HashMap();
        private zza aUj;

        public zza(zza $r1, AutocompletionListener $r2) throws  {
            this.aUj = $r1;
            this.aTU = $r2;
        }

        private void onAutocompletionsAvailable(Autocompletion[] $r1, int $i0, int $i1) throws  {
            this.aTU.onAutocompletionsAvailable($r1, $i0, $i1);
            if ($i0 == $i1 - 1) {
                this.aUg = true;
            }
        }

        private List<Autocompletion> zzb(@Signature({"(", "Lcom/google/android/gms/people/internal/zzn$zzl;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/people/Autocomplete$Autocompletion;", ">;"}) zzl $r1) throws  {
            ArrayList $r2 = new ArrayList($r1.zzcfa().size());
            Iterator $r4 = $r1.zzcfa().iterator();
            while ($r4.hasNext()) {
                $r2.add((AutocompletionImpl) $r4.next());
            }
            return $r2;
        }

        public final synchronized void cancel() throws  {
            this.aUf = true;
        }

        public final boolean isFinished() throws  {
            return this.aUg;
        }

        public synchronized void zza(zzl $r1) throws  {
            if (!this.aUf) {
                if ($r1.zzcfb() == 0 || this.aUh.contains(Integer.valueOf($r1.zzcfb() - 1))) {
                    this.aUh.add(Integer.valueOf($r1.zzcfb()));
                    onAutocompletionsAvailable((Autocompletion[]) zzb($r1).toArray(new Autocompletion[0]), $r1.zzcfb(), $r1.zzcfc());
                    int $i0 = $r1.zzcfb() + 1;
                    while (true) {
                        if (!this.aUi.containsKey(Integer.valueOf($i0))) {
                            break;
                        }
                        onAutocompletionsAvailable((Autocompletion[]) ((List) this.aUi.get(Integer.valueOf($i0))).toArray(new Autocompletion[0]), $i0, $r1.zzcfc());
                        this.aUi.remove(Integer.valueOf($i0));
                        this.aUh.add(Integer.valueOf($i0));
                        $i0++;
                    }
                } else {
                    this.aUi.put(Integer.valueOf($r1.zzcfb()), zzb($r1));
                }
            }
        }

        public final void zzft(Status status) throws  {
        }
    }

    public zza(GoogleApiClient $r1, ClientConfig $r2, String $r3, AutocompletionListener $r4) throws  {
        this.mAccount = (String) zzab.zzag($r3);
        zzab.zzag($r1);
        this.aTU = (AutocompletionListener) zzab.zzag($r4);
        this.aTW = zzcgc();
        this.aTV = (ClientConfig) zzab.zzag($r2);
        this.aTY = null;
    }

    private PendingResult<LoadPhotoResult> zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/internal/autocomplete/PhotoImpl;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/internal/autocomplete/PhotoImpl;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) PhotoImpl $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/internal/autocomplete/PhotoImpl;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) LoadPhotoOptions $r3) throws  {
        zzab.zzag($r1);
        return zzb(People.ImageApi.loadByReference($r1, new AvatarReference($r2.getSource(), $r2.getLocation()), new Builder().setImageSize($r3.getImageSize()).setAvatarOptions($r3.getPhotoOptions()).build()));
    }

    private zza zza(GoogleApiClient $r1, final String $r2) throws  {
        zzab.zzag($r1);
        final zza $r3 = new zza(this, this.aTU);
        if (!(this.aTY == null || this.aTX.isFinished())) {
            this.aTY.cancel();
        }
        this.aTY = (com.google.android.gms.people.People.zza) $r1.zzc(new com.google.android.gms.people.People.zza<zzl>(this, $r1) {
            final /* synthetic */ zza aUb;

            protected void zza(zzn $r1) throws RemoteException {
                zza($r1.zza((zzd) $r3, this.aUb.mAccount, $r2, this.aUb.aTW, this.aUb.aTV.clientId));
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgl($r1);
            }

            protected zzl zzgl(final Status $r1) throws  {
                return new zzl(this) {
                    final /* synthetic */ C09871 aUc;

                    public Status getStatus() throws  {
                        return $r1;
                    }

                    public void release() throws  {
                    }

                    public ArrayList<AutocompletionImpl> zzcfa() throws  {
                        return null;
                    }

                    public int zzcfb() throws  {
                        return 0;
                    }

                    public int zzcfc() throws  {
                        return 0;
                    }
                };
            }
        });
        return $r3;
    }

    private static PendingResult<LoadPhotoResult> zzb(@Signature({"(", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) PendingResult<LoadImageResult> $r0) throws  {
        return new C09882($r0);
    }

    private synchronized void zzcgb() throws  {
        if (this.aTT) {
            throw new IllegalStateException("AutocompleteSession has already been closed.");
        }
    }

    private static synchronized long zzcgc() throws  {
        Class cls = zza.class;
        synchronized (cls) {
            try {
                long l1 = aTZ;
                aTZ++;
                return l1;
            } finally {
                cls = zza.class;
            }
        }
    }

    public synchronized void adjustQuery(GoogleApiClient $r1, String $r2) throws  {
        zzab.zzag($r1);
        zzab.zzag($r2);
        zzcgb();
        if (this.aTX != null) {
            this.aTX.cancel();
        }
        if (!$r2.isEmpty()) {
            this.aTX = zza($r1, $r2);
        }
    }

    public synchronized void close(GoogleApiClient $r1) throws  {
        zzab.zzag($r1);
        zzcgb();
        if (this.aTX != null) {
            this.aTX.cancel();
        }
        if (this.aTY != null) {
            this.aTY.cancel();
        }
        this.aTT = true;
    }

    public PendingResult<PreferredFieldsResult> getAllPreferredFields(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$ContactGroup;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$ContactGroup;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;"}) final ContactGroup $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "The GoogleApiClient cannot be null.");
        zzab.zzb((Object) $r2, (Object) "The ContactGroup cannot be null.");
        return $r1.zzc(new com.google.android.gms.people.People.zza<PreferredFieldsResult>(this, $r1) {
            final /* synthetic */ zza aUb;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzh((zzb) this, this.aUb.mAccount, $r2.getId().getId());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgm($r1);
            }

            protected PreferredFieldsResult zzgm(final Status $r1) throws  {
                return new PreferredFieldsResult(this) {
                    final /* synthetic */ C09903 aUe;

                    public ContactGroupPreferredFieldsBuffer getPreferredFields() throws  {
                        return null;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }

                    public void release() throws  {
                    }
                };
            }
        });
    }

    public PendingResult<LoadPhotoResult> loadPrimaryPhoto(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$Person;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$Person;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) Person $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$Person;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) LoadPhotoOptions $r3) throws  {
        zzab.zzb((Object) $r1, (Object) "The client cannot be null");
        zzab.zzb((Object) $r2, (Object) "The person cannot be null");
        zzab.zzb($r2 instanceof PersonImpl, (Object) "The person must be provided by the Autocomplete Session.");
        for (Photo $r5 : $r2.getPhotos()) {
            if ($r5.isDefault()) {
                return zza($r1, (PhotoImpl) $r5, $r3);
            }
        }
        return PendingResults.immediatePendingResult(LoadPhotoResult.FAILED_RESULT);
    }

    public void reportDisplay(GoogleApiClient $r1, Autocompletion $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "The client cannot be null");
        zzab.zzb((Object) $r2, (Object) "The autocompletion cannot be null");
    }

    public void reportSelection(GoogleApiClient $r1, Autocompletion $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "The client cannot be null");
        zzab.zzb((Object) $r2, (Object) "The autocompletion cannot be null");
    }

    public void reportSubmissionSave(GoogleApiClient $r1, Autocompletion $r2, String[] strArr) throws  {
        zzab.zzb((Object) $r1, (Object) "The client cannot be null");
        zzab.zzb((Object) $r2, (Object) "The autocompletion cannot be null");
    }

    public void reportSubmissionSend(GoogleApiClient $r1, Autocompletion $r2, String[] strArr) throws  {
        zzab.zzb((Object) $r1, (Object) "The client cannot be null");
        zzab.zzb((Object) $r2, (Object) "The autocompletion cannot be null");
    }

    public void startNewQuery(GoogleApiClient $r1) throws  {
        zzab.zzag($r1);
        adjustQuery($r1, "");
    }
}
