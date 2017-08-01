package com.google.android.gms.people.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.server.FavaDiagnosticsEntity;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.internal.zzrm;
import com.google.android.gms.people.Autocomplete.AutocompleteOptions;
import com.google.android.gms.people.Autocomplete.AutocompleteResult;
import com.google.android.gms.people.Autocomplete.PreferredFieldsResult;
import com.google.android.gms.people.Graph.FetchBackUpDeviceContactInfoResult;
import com.google.android.gms.people.Graph.LoadAggregatedPeopleResult;
import com.google.android.gms.people.Graph.LoadCirclesResult;
import com.google.android.gms.people.Graph.LoadContactsGaiaIdsResult;
import com.google.android.gms.people.Graph.LoadOwnersResult;
import com.google.android.gms.people.Graph.LoadPeopleForAggregationResult;
import com.google.android.gms.people.Graph.LoadPeopleOptions;
import com.google.android.gms.people.Graph.LoadPeopleResult;
import com.google.android.gms.people.Graph.LoadPhoneNumbersResult;
import com.google.android.gms.people.GraphUpdate.AddCircleResult;
import com.google.android.gms.people.GraphUpdate.AddPeopleToCircleResult;
import com.google.android.gms.people.GraphUpdate.LoadAddToCircleConsentResult;
import com.google.android.gms.people.GraphUpdate.UpdatePersonCircleResult;
import com.google.android.gms.people.Images.LoadImageOptions;
import com.google.android.gms.people.Images.LoadImageResult;
import com.google.android.gms.people.Images.SetAvatarResult;
import com.google.android.gms.people.InternalApi.LoadPeopleForAspenOptions;
import com.google.android.gms.people.InternalApi.LoadPeopleForAspenResult;
import com.google.android.gms.people.Notifications.OnDataChanged;
import com.google.android.gms.people.People.BundleResult;
import com.google.android.gms.people.People.ReleasableResult;
import com.google.android.gms.people.PeopleConstants;
import com.google.android.gms.people.exp.ContactGaiaIdRawBuffer;
import com.google.android.gms.people.exp.PersonForAggregationRawBuffer;
import com.google.android.gms.people.identity.IdentityApi.GetOptions;
import com.google.android.gms.people.identity.IdentityApi.ListOptions;
import com.google.android.gms.people.identity.internal.AccountToken;
import com.google.android.gms.people.identity.internal.ParcelableGetOptions;
import com.google.android.gms.people.identity.internal.ParcelableListOptions;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.EmailDecoder;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.PhoneDecoder;
import com.google.android.gms.people.internal.autocomplete.AutocompletionImpl;
import com.google.android.gms.people.internal.autocomplete.ParcelableLoadAutocompleteResultsOptions;
import com.google.android.gms.people.internal.autocomplete.ParcelableLoadContactGroupFieldsOptions;
import com.google.android.gms.people.model.AggregatedPersonBuffer;
import com.google.android.gms.people.model.AutocompleteBuffer;
import com.google.android.gms.people.model.AvatarReference;
import com.google.android.gms.people.model.CircleBuffer;
import com.google.android.gms.people.model.ContactGaiaIdBuffer;
import com.google.android.gms.people.model.ContactGroupPreferredFieldsBuffer;
import com.google.android.gms.people.model.OwnerBuffer;
import com.google.android.gms.people.model.PersonBuffer;
import com.google.android.gms.people.model.PhoneNumberBuffer;
import com.google.android.gms.people.protomodel.FetchBackUpDeviceContactInfoResponse;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzn extends com.google.android.gms.common.internal.zzk<zzg> {
    private static volatile Bundle aQm;
    private static volatile Bundle aQn;
    public final String aQk;
    private final HashMap<OnDataChanged, zzaa> aQl = new HashMap();
    private Long aQo = null;
    public final String co;
    public final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzj {
        void zza(int i, Bundle bundle, Bundle bundle2) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzk {
        void zza(int i, Bundle bundle, Bundle bundle2) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzl extends ReleasableResult {
        ArrayList<AutocompletionImpl> zzcfa() throws ;

        int zzcfb() throws ;

        int zzcfc() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzd {
        void zza(zzl com_google_android_gms_people_internal_zzn_zzl) throws ;

        void zzft(Status status) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza implements LoadAggregatedPeopleResult {
        private final AggregatedPersonBuffer aQp;
        private final Status cp;

        public zza(Status $r1, AggregatedPersonBuffer $r2) throws  {
            this.cp = $r1;
            this.aQp = $r2;
        }

        public AggregatedPersonBuffer getAggregatedPeople() throws  {
            return this.aQp;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQp != null) {
                this.aQp.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzaa extends zza {
        private final zzrm<OnDataChanged> aQE;

        public zzaa(@Signature({"(", "Lcom/google/android/gms/internal/zzrm", "<", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", ">;)V"}) zzrm<OnDataChanged> $r1) throws  {
            this.aQE = $r1;
        }

        public void release() throws  {
            this.aQE.clear();
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 55) + String.valueOf($r4).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r3).append("\nbundle=").append($r4).toString());
            }
            if ($i0 != 0) {
                zzp.zzan("PeopleClient", "Non-success data changed callback received.");
            } else {
                this.aQE.zza(new zzh($r2.getString("account"), $r2.getString("pagegaiaid"), $r2.getInt("scope")));
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzab extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<FetchBackUpDeviceContactInfoResult> awc;

        public zzab(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<FetchBackUpDeviceContactInfoResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            String $r3;
            if (zzp.zzcfd()) {
                $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 57) + String.valueOf($r4).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r3).append("\nresponse=").append($r4).toString());
            }
            Status $r7 = zzn.zza($i0, null, $r1);
            $r2.setClassLoader(FetchBackUpDeviceContactInfoResponse.class.getClassLoader());
            FetchBackUpDeviceContactInfoResponse $r11 = (FetchBackUpDeviceContactInfoResponse) $r2.getParcelable("people_restore_fetch_info");
            $r3 = String.valueOf($r11);
            Log.d("PeopleRestore", new StringBuilder(String.valueOf($r3).length() + 13).append("Response is: ").append($r3).toString());
            this.awc.setResult(new zze($r7, $r11));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzac extends zza {
        private final zzj aQF;

        public zzac(zzj $r1) throws  {
            this.aQF = $r1;
        }

        public synchronized void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 57) + String.valueOf($r4).length()).append("GetById callback: status=").append($i0).append("\nresolution=").append($r3).append("\ncontent=").append($r4).toString());
            }
            this.aQF.zza($i0, $r1, $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzad extends zza {
        private final zzk aQG;

        public zzad(zzk $r1) throws  {
            this.aQG = $r1;
        }

        public synchronized void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 62) + String.valueOf($r4).length()).append("identityList callback: status=").append($i0).append("\nresolution=").append($r3).append("\ncontent=").append($r4).toString());
            }
            this.aQG.zza($i0, $r1, $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzae extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<BundleResult> awc;

        public zzae(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/People$BundleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<BundleResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 55) + String.valueOf($r4).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r3).append("\nbundle=").append($r4).toString());
            }
            this.awc.setResult(new zzaf(zzn.zza($i0, null, $r1), $r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzaf implements BundleResult {
        private final Bundle aQH;
        private final Status cp;

        public zzaf(Status $r1, Bundle $r2) throws  {
            this.cp = $r1;
            this.aQH = $r2;
        }

        public Bundle getBundle() throws  {
            return this.aQH;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzag extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<Result> awc;

        public zzag(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 55) + String.valueOf($r4).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r3).append("\nbundle=").append($r4).toString());
            }
            this.awc.setResult(new zzah(zzn.zza($i0, null, $r1)));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzah implements Result {
        private final Status cp;

        public zzah(Status $r1) throws  {
            this.cp = $r1;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzai extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadOwnersResult> awc;

        public zzai(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadOwnersResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            OwnerBuffer $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 54) + String.valueOf($r5).length()).append("Owner callback: status=").append($i0).append("\nresolution=").append($r4).append("\nholder=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r3 = new OwnerBuffer($r2);
            }
            this.awc.setResult(new zzaq($r8, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzaj extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadImageResult> awc;

        public zzaj(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadImageResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, ParcelFileDescriptor $r2, Bundle $r3) throws  {
            boolean $z0;
            int $i1 = 0;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 52) + String.valueOf($r5).length()).append("Avatar callback: status=").append($i0).append(" resolution=").append($r4).append(" pfd=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r3 != null) {
                $z0 = $r3.getBoolean("rewindable");
                $i0 = $r3.getInt("width");
                $i1 = $r3.getInt("height");
            } else {
                $i0 = 0;
                $z0 = false;
            }
            this.awc.setResult(new zzar($r8, $r2, $z0, $i0, $i1));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzak extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> awc;

        public zzak(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 55) + String.valueOf($r4).length()).append("People callback: status=").append($i0).append("\nresolution=").append($r3).append("\nholder=").append($r4).toString());
            }
            this.awc.setResult(new zzaw(zzn.zza($i0, null, $r1), zzn.zzbt($r2)));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzal extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadPeopleForAspenResult> awc;

        public zzal(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleForAspenResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            String $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 55) + String.valueOf($r5).length()).append("People callback: status=").append($i0).append("\nresolution=").append($r4).append("\nholder=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            PersonBuffer $r9 = zzn.zzbt($r2);
            if ($r2 != null) {
                $r3 = $r2.zzava().getString("pageToken");
            }
            this.awc.setResult(new zzav($r8, $r9, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzam extends zza {
        private final Context aQI;
        private final com.google.android.gms.internal.zzqk.zzb<LoadPhoneNumbersResult> awc;

        public zzam(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;", "Landroid/content/Context;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LoadPhoneNumbersResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;", "Landroid/content/Context;", ")V"}) Context $r2) throws  {
            this.awc = $r1;
            this.aQI = $r2;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            PhoneNumberBuffer $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 61) + String.valueOf($r5).length()).append("Phone number callback: status=").append($i0).append("\nresolution=").append($r4).append("\nholder=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r3 = new PhoneNumberBuffer($r2, this.aQI);
            }
            this.awc.setResult(new zzax($r8, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzan extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<Result> awc;

        public zzan(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 57) + String.valueOf($r4).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r3).append("\nresponse=").append($r4).toString());
            }
            this.awc.setResult(new zzi(zzn.zza($i0, null, $r1)));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzao extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<UpdatePersonCircleResult> awc;

        public zzao(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<UpdatePersonCircleResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            ArrayList $r9;
            ArrayList $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 55) + String.valueOf($r5).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r4).append("\nbundle=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r8.isSuccess()) {
                $r9 = $r2.getStringArrayList("added_circles");
                $r3 = $r2.getStringArrayList("removed_circles");
            } else {
                $r9 = null;
            }
            this.awc.setResult(new zzap($r8, $r9, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzap implements UpdatePersonCircleResult {
        private final List<String> aQJ;
        private final List<String> aQK;
        private final Status cp;

        public zzap(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Status $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r2, @Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r3) throws  {
            this.cp = $r1;
            this.aQJ = $r2;
            this.aQK = $r3;
        }

        public List<String> getAddedCircles() throws  {
            return this.aQJ;
        }

        public List<String> getRemovedCircles() throws  {
            return this.aQK;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzaq implements LoadOwnersResult {
        private final OwnerBuffer aQL;
        private final Status cp;

        public zzaq(Status $r1, OwnerBuffer $r2) throws  {
            this.cp = $r1;
            this.aQL = $r2;
        }

        public OwnerBuffer getOwners() throws  {
            return this.aQL;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQL != null) {
                this.aQL.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzar implements LoadImageResult {
        private final ParcelFileDescriptor VH;
        private final boolean aQM;
        private final Status cp;
        private final int zzaiq;
        private final int zzair;

        public zzar(Status $r1, ParcelFileDescriptor $r2, boolean $z0, int $i0, int $i1) throws  {
            this.cp = $r1;
            this.VH = $r2;
            this.aQM = $z0;
            this.zzaiq = $i0;
            this.zzair = $i1;
        }

        public int getHeight() throws  {
            return this.zzair;
        }

        public ParcelFileDescriptor getParcelFileDescriptor() throws  {
            return this.VH;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public int getWidth() throws  {
            return this.zzaiq;
        }

        public boolean isRewindable() throws  {
            return this.aQM;
        }

        public void release() throws  {
            if (this.VH != null) {
                IOUtils.closeQuietly(this.VH);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzas implements com.google.android.gms.people.internal.agg.zzd.zzd {
        private final com.google.android.gms.internal.zzqk.zzb<LoadAggregatedPeopleResult> awc;

        public zzas(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadAggregatedPeopleResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, AggregatedPersonBuffer $r2) throws  {
            this.awc.setResult(new zza(zzn.zza($i0, null, $r1), $r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzat extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadPeopleForAggregationResult> awc;

        public zzat(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleForAggregationResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder[] $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf(Arrays.toString($r2));
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 56) + String.valueOf($r4).length()).append("People callback: status=").append($i0).append("\nresolution=").append($r3).append("\nholders=").append($r4).toString());
            }
            Status $r7 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                this.awc.setResult(new zzau($r7, new PersonForAggregationRawBuffer($r2[0], new PhoneDecoder(zzn.aQn), new EmailDecoder(zzn.aQm)), new ContactGaiaIdRawBuffer($r2[1])));
                return;
            }
            this.awc.setResult(new zzau($r7, null, null));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzau implements LoadPeopleForAggregationResult {
        private final PersonForAggregationRawBuffer aQN;
        private final ContactGaiaIdRawBuffer aQO;
        private final Status cp;

        public zzau(Status $r1, PersonForAggregationRawBuffer $r2, ContactGaiaIdRawBuffer $r3) throws  {
            this.cp = $r1;
            this.aQN = $r2;
            this.aQO = $r3;
        }

        public Bundle getEmailTypeMapBundle() throws  {
            return zzn.aQm;
        }

        public ContactGaiaIdRawBuffer getGaiaMap() throws  {
            return this.aQO;
        }

        public PersonForAggregationRawBuffer getPeople() throws  {
            return this.aQN;
        }

        public Bundle getPhoneTypeMapBundle() throws  {
            return zzn.aQn;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQN != null) {
                this.aQN.close();
            }
            if (this.aQO != null) {
                this.aQO.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzav implements LoadPeopleForAspenResult {
        private final PersonBuffer aQP;
        private final String aQQ;
        private final Status cp;

        public zzav(Status $r1, PersonBuffer $r2, String $r3) throws  {
            this.cp = $r1;
            this.aQP = $r2;
            this.aQQ = $r3;
        }

        public String getNextPageToken() throws  {
            return this.aQQ;
        }

        public PersonBuffer getPeople() throws  {
            return this.aQP;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQP != null) {
                this.aQP.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzaw implements LoadPeopleResult {
        private final PersonBuffer aQP;
        private final Status cp;

        public zzaw(Status $r1, PersonBuffer $r2) throws  {
            this.cp = $r1;
            this.aQP = $r2;
        }

        public PersonBuffer getPeople() throws  {
            return this.aQP;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQP != null) {
                this.aQP.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzax implements LoadPhoneNumbersResult {
        private final PhoneNumberBuffer aQR;
        private final Status cp;

        public zzax(Status $r1, PhoneNumberBuffer $r2) throws  {
            this.cp = $r1;
            this.aQR = $r2;
        }

        public PhoneNumberBuffer getPhoneNumbers() throws  {
            return this.aQR;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQR != null) {
                this.aQR.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzay implements PreferredFieldsResult {
        private final ContactGroupPreferredFieldsBuffer aQS;
        private final Status cp;

        public zzay(Status $r1, ContactGroupPreferredFieldsBuffer $r2) throws  {
            this.cp = $r1;
            this.aQS = $r2;
        }

        public ContactGroupPreferredFieldsBuffer getPreferredFields() throws  {
            return this.aQS;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQS != null) {
                this.aQS.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzb extends zza {
        private final zzd aQq;

        public zzb(zzd $r1) throws  {
            this.aQq = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            Status $r5 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r2.setClassLoader(getClass().getClassLoader());
                this.aQq.zza(new zzm($r2.getParcelableArrayList("autocomplete_autocompletions"), $r5, $r2.getInt("autocomplete_callback_number"), $r2.getInt("autocomplete_callback_total")));
                return;
            }
            this.aQq.zzft($r5);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzc implements AutocompleteResult {
        private final AutocompleteBuffer aQe;
        private final Status cp;

        public zzc(Status $r1, AutocompleteBuffer $r2) throws  {
            this.cp = $r1;
            this.aQe = $r2;
        }

        public AutocompleteBuffer getAutocompleteEntries() throws  {
            return this.aQe;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQe != null) {
                this.aQe.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zze implements FetchBackUpDeviceContactInfoResult {
        private final FetchBackUpDeviceContactInfoResponse aQr;
        private final Status cp;

        public zze(Status $r1, FetchBackUpDeviceContactInfoResponse $r2) throws  {
            this.cp = $r1;
            this.aQr = $r2;
        }

        public FetchBackUpDeviceContactInfoResponse getResponse() throws  {
            return this.aQr;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzf implements LoadCirclesResult {
        private final CircleBuffer aQs;
        private final Status cp;

        public zzf(Status $r1, CircleBuffer $r2) throws  {
            this.cp = $r1;
            this.aQs = $r2;
        }

        public CircleBuffer getCircles() throws  {
            return this.aQs;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQs != null) {
                this.aQs.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzg implements LoadContactsGaiaIdsResult {
        private final ContactGaiaIdBuffer aQt;
        private final Status cp;

        public zzg(Status $r1, ContactGaiaIdBuffer $r2) throws  {
            this.cp = $r1;
            this.aQt = $r2;
        }

        public ContactGaiaIdBuffer getContactsGaiaIds() throws  {
            return this.aQt;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
            if (this.aQt != null) {
                this.aQt.close();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzh implements com.google.android.gms.internal.zzrm.zzb<OnDataChanged> {
        private final String aMp;
        private final int aQu;
        private final String mAccount;

        public zzh(String $r1, String $r2, int $i0) throws  {
            this.mAccount = $r1;
            this.aMp = $r2;
            this.aQu = $i0;
        }

        public void zzata() throws  {
        }

        public void zzb(OnDataChanged $r1) throws  {
            $r1.onDataChanged(this.mAccount, this.aMp, this.aQu);
        }

        public /* synthetic */ void zzy(Object $r1) throws  {
            zzb((OnDataChanged) $r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzi implements Result {
        private final Status cp;

        public zzi(Status $r1) throws  {
            this.cp = $r1;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzm implements zzl {
        private final ArrayList<AutocompletionImpl> aQv;
        private final int aQw;
        private final int aQx;
        private final Status cp;

        public zzm(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/people/internal/autocomplete/AutocompletionImpl;", ">;", "Lcom/google/android/gms/common/api/Status;", "II)V"}) ArrayList<AutocompletionImpl> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/people/internal/autocomplete/AutocompletionImpl;", ">;", "Lcom/google/android/gms/common/api/Status;", "II)V"}) Status $r2, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/people/internal/autocomplete/AutocompletionImpl;", ">;", "Lcom/google/android/gms/common/api/Status;", "II)V"}) int $i0, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/people/internal/autocomplete/AutocompletionImpl;", ">;", "Lcom/google/android/gms/common/api/Status;", "II)V"}) int $i1) throws  {
            this.aQv = $r1;
            this.cp = $r2;
            this.aQw = $i0;
            this.aQx = $i1;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public void release() throws  {
        }

        public ArrayList<AutocompletionImpl> zzcfa() throws  {
            return this.aQv;
        }

        public int zzcfb() throws  {
            return this.aQw;
        }

        public int zzcfc() throws  {
            return this.aQx;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzn extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<AddCircleResult> awc;

        public zzn(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<AddCircleResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            String $r4;
            String $r3 = null;
            if (zzp.zzcfd()) {
                $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 55) + String.valueOf($r5).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r4).append("\nbundle=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 == null) {
                $r4 = null;
            } else {
                $r4 = $r2.getString("circle_id");
            }
            if ($r2 != null) {
                $r3 = $r2.getString("circle_name");
            }
            this.awc.setResult(new zzo($r8, $r4, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzo implements AddCircleResult {
        private final String KN;
        private final String aQy;
        private final Status cp;

        public zzo(Status $r1, String $r2, String $r3) throws  {
            this.cp = $r1;
            this.KN = $r2;
            this.aQy = $r3;
        }

        public String getCircleId() throws  {
            return this.KN;
        }

        public String getCircleName() throws  {
            return this.aQy;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzp extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<AddPeopleToCircleResult> awc;

        public zzp(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<AddPeopleToCircleResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            String $r4;
            String $r5;
            String[] $r3 = null;
            if (zzp.zzcfd()) {
                $r4 = String.valueOf($r1);
                $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 55) + String.valueOf($r5).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r4).append("\nbundle=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 == null) {
                $r4 = null;
            } else {
                $r4 = $r2.getString("circle_id");
            }
            if ($r2 == null) {
                $r5 = null;
            } else {
                $r5 = $r2.getString("circle_name");
            }
            if ($r2 != null) {
                $r3 = $r2.getStringArray("added_people");
            }
            this.awc.setResult(new zzq($r8, $r4, $r5, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzq implements AddPeopleToCircleResult {
        private final String KN;
        private final String aQy;
        private final String[] aQz;
        private final Status cp;

        public zzq(Status $r1, String $r2, String $r3, String[] $r4) throws  {
            this.cp = $r1;
            this.KN = $r2;
            this.aQy = $r3;
            this.aQz = $r4;
        }

        public String getCircleId() throws  {
            return this.KN;
        }

        public String getCircleName() throws  {
            return this.aQy;
        }

        public String[] getPeopleQualifiedIds() throws  {
            return this.aQz;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzr extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadAddToCircleConsentResult> awc;

        public zzr(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$LoadAddToCircleConsentResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadAddToCircleConsentResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            String $r4;
            String $r5;
            boolean $z0;
            String $r3 = null;
            if (zzp.zzcfd()) {
                $r4 = String.valueOf($r1);
                $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 55) + String.valueOf($r5).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r4).append("\nbundle=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $z0 = $r2.getBoolean("circles.first_time_add_need_consent");
                $r4 = $r2.getString("circles.first_time_add_text");
                $r5 = $r2.getString("circles.first_time_add_title_text");
                $r3 = $r2.getString("circles.first_time_add_ok_text");
            } else {
                $z0 = false;
                $r5 = null;
                $r4 = null;
            }
            this.awc.setResult(new zzs($r8, $z0, $r4, $r5, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzs implements LoadAddToCircleConsentResult {
        private final boolean aQA;
        private final String aQB;
        private final String aQC;
        private final Status cp;
        private final String tF;

        public zzs(Status $r1, boolean $z0, String $r2, String $r3, String $r4) throws  {
            this.cp = $r1;
            this.aQA = $z0;
            this.aQB = $r2;
            this.tF = $r3;
            this.aQC = $r4;
        }

        public String getConsentButtonText() throws  {
            return this.aQC;
        }

        public String getConsentHtml() throws  {
            return this.aQB;
        }

        public String getConsentTitleText() throws  {
            return this.tF;
        }

        public boolean getShowConsent() throws  {
            return this.aQA;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzt extends zza {
        private final com.google.android.gms.people.internal.agg.zzd aQD;

        public zzt(com.google.android.gms.people.internal.agg.zzd $r1) throws  {
            this.aQD = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder[] $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf(Arrays.toString($r2));
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 56) + String.valueOf($r4).length()).append("People callback: status=").append($i0).append("\nresolution=").append($r3).append("\nholders=").append($r4).toString());
            }
            this.aQD.zza(zzn.zzj($i0, $r1), $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzu extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<AutocompleteResult> awc;

        public zzu(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<AutocompleteResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            AutocompleteBuffer $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 61) + String.valueOf($r5).length()).append("Autocomplete callback: status=").append($i0).append("\nresolution=").append($r4).append("\nholder=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r3 = new AutocompleteBuffer($r2);
            }
            this.awc.setResult(new zzc($r8, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzv extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<SetAvatarResult> awc;

        public zzv(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<SetAvatarResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            String $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 55) + String.valueOf($r5).length()).append("Bundle callback: status=").append($i0).append("\nresolution=").append($r4).append("\nbundle=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r3 = $r2.getString("avatarurl");
            }
            this.awc.setResult(new zzw($r8, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzw implements SetAvatarResult {
        private final Status cp;
        private final String zzad;

        public zzw(Status $r1, String $r2) throws  {
            this.cp = $r1;
            this.zzad = $r2;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public String getUrl() throws  {
            return this.zzad;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzx extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadCirclesResult> awc;

        public zzx(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadCirclesResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            CircleBuffer $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 56) + String.valueOf($r5).length()).append("Circles callback: status=").append($i0).append("\nresolution=").append($r4).append("\nholder=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r3 = new CircleBuffer($r2);
            }
            this.awc.setResult(new zzf($r8, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzy extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<PreferredFieldsResult> awc;

        public zzy(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<PreferredFieldsResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            ContactGroupPreferredFieldsBuffer $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 78) + String.valueOf($r5).length()).append("Contact group preferred field callback: status=").append($i0).append("\nresolution=").append($r4).append("\nholder=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r3 = new ContactGroupPreferredFieldsBuffer($r2);
            }
            this.awc.setResult(new zzay($r8, $r3));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzz extends zza {
        private final com.google.android.gms.internal.zzqk.zzb<LoadContactsGaiaIdsResult> awc;

        public zzz(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LoadContactsGaiaIdsResult> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int $i0, Bundle $r1, DataHolder $r2) throws  {
            ContactGaiaIdBuffer $r3 = null;
            if (zzp.zzcfd()) {
                String $r4 = String.valueOf($r1);
                String $r5 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r4).length() + 55) + String.valueOf($r5).length()).append("GaiaId callback: status=").append($i0).append("\nresolution=").append($r4).append("\nholder=").append($r5).toString());
            }
            Status $r8 = zzn.zza($i0, null, $r1);
            if ($r2 != null) {
                $r3 = new ContactGaiaIdBuffer($r2);
            }
            this.awc.setResult(new zzg($r8, $r3));
        }
    }

    public zzn(Context $r1, Looper $r2, ConnectionCallbacks $r3, OnConnectionFailedListener $r4, String $r5, com.google.android.gms.common.internal.zzg $r6) throws  {
        super($r1.getApplicationContext(), $r2, 5, $r6, $r3, $r4);
        this.mContext = $r1;
        this.aQk = $r5;
        this.co = $r6.zzawj();
    }

    private static Status zza(int $i0, String $r0, Bundle $r1) throws  {
        return new Status($i0, $r0, zzaw($r1));
    }

    private void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleForAspenResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", ")V"}) String $r5) throws  {
        zzbrq();
        zzal $r6 = new zzal($r1);
        try {
            zzcev().zzb($r6, $r2, $r3, $r4, $i0, $r5);
        } catch (RemoteException e) {
            $r6.zza(8, null, null);
        }
    }

    private void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) Collection<String> $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) long $l1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r6, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i4) throws  {
        zzbrq();
        zzak $r7 = new zzak($r1);
        try {
            zzcev().zza((zzf) $r7, $r2, $r3, $r4, (List) com.google.android.gms.common.util.zzb.zzj($r5), $i0, $z0, $l1, $r6, $i2, $i3, $i4);
        } catch (RemoteException e) {
            $r7.zza(8, null, null);
        }
    }

    private static PendingIntent zzaw(Bundle $r0) throws  {
        return $r0 == null ? null : (PendingIntent) $r0.getParcelable("pendingIntent");
    }

    private static PersonBuffer zzbt(DataHolder $r0) throws  {
        return $r0 == null ? null : new PersonBuffer($r0, new PhoneDecoder(aQn), new EmailDecoder(aQm));
    }

    private synchronized long zzcew() throws  {
        if (this.aQo == null) {
            zzcex();
        }
        return this.aQo.longValue();
    }

    private synchronized void zzcex() throws  {
        this.aQo = Long.valueOf(zzq.zzdk(getContext()).nextLong());
    }

    private static ConnectionResult zzj(int $i0, Bundle $r0) throws  {
        return new ConnectionResult($i0, zzaw($r0));
    }

    public void disconnect() throws  {
        synchronized (this.aQl) {
            if (isConnected()) {
                for (zzaa $r6 : this.aQl.values()) {
                    $r6.release();
                    try {
                        zzcev().zza((zzf) $r6, false, null, null, 0);
                    } catch (Throwable $r8) {
                        zzp.zzc("PeopleClient", "Failed to unregister listener", $r8);
                    } catch (Throwable $r10) {
                        zzp.zzc("PeopleClient", "PeopleService is in unexpected state", $r10);
                    }
                }
            }
            this.aQl.clear();
        }
        super.disconnect();
    }

    public boolean isSyncToContactsEnabled() throws RemoteException {
        zzbrq();
        return zzcev().isSyncToContactsEnabled();
    }

    public com.google.android.gms.common.internal.zzr zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;J)", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadImageResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;J)", "Lcom/google/android/gms/common/internal/zzr;"}) long $l0) throws  {
        zzbrq();
        zzf $r2 = new zzaj($r1);
        try {
            return zzcev().zzb($r2, $l0, true);
        } catch (RemoteException e) {
            $r2.zza(8, null, null, null);
            return null;
        }
    }

    public com.google.android.gms.common.internal.zzr zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadImageResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) AvatarReference $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) LoadImageOptions $r3) throws  {
        zzbrq();
        zzf $r4 = new zzaj($r1);
        try {
            return zzcev().zza($r4, $r2, ParcelableLoadImageOptions.zza($r3));
        } catch (RemoteException e) {
            $r4.zza(8, null, null, null);
            return null;
        }
    }

    public com.google.android.gms.common.internal.zzr zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadImageResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) int $i1) throws  {
        zzbrq();
        zzf $r3 = new zzaj($r1);
        try {
            return zzcev().zzb($r3, $r2, $i0, $i1);
        } catch (RemoteException e) {
            $r3.zza(8, null, null, null);
            return null;
        }
    }

    public com.google.android.gms.common.internal.zzr zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadPhoneNumbersResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) Bundle $r3) throws  {
        zzbrq();
        zzf $r4 = new zzam($r1, this.mContext);
        try {
            return zzcev().zzb($r4, $r2, null, $r3);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
            return null;
        }
    }

    public com.google.android.gms.common.internal.zzr zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadImageResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/internal/zzr;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/internal/zzr;"}) int $i0) throws  {
        zzaj $r4 = new zzaj($r1);
        try {
            return zzcev().zzc($r4, $r2, $r3, $i0);
        } catch (RemoteException e) {
            $r4.zza(8, null, null, null);
            return null;
        }
    }

    public com.google.android.gms.common.internal.zzr zza(zzd $r1, String $r2, String $r3, long $l0, int $i1) throws  {
        zzbrq();
        zzf $r4 = new zzb($r1);
        try {
            return zzcev().zza($r4, $r2, new ParcelableLoadAutocompleteResultsOptions($i1, $l0, $r3));
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
            return null;
        }
    }

    public zzaa zza(GoogleApiClient $r1, OnDataChanged $r2) throws  {
        synchronized (this.aQl) {
            if (this.aQl.containsKey($r2)) {
                zzaa $r6 = (zzaa) this.aQl.get($r2);
                return $r6;
            }
            $r6 = new zzaa($r1.zzw($r2));
            this.aQl.put($r2, $r6);
            return $r6;
        }
    }

    protected void zza(int $i0, IBinder $r1, Bundle $r2, int $i1) throws  {
        if ($i0 == 0 && $r2 != null) {
            zzav($r2.getBundle("post_init_configuration"));
        }
        super.zza($i0, $r1, $r2 == null ? null : $r2.getBundle("post_init_resolution"), $i1);
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/People$BundleResult;", ">;", "Landroid/os/Bundle;", ")V"}) com.google.android.gms.internal.zzqk.zzb<BundleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/People$BundleResult;", ">;", "Landroid/os/Bundle;", ")V"}) Bundle $r2) throws  {
        zzbrq();
        zzf $r3 = new zzae($r1);
        try {
            zzcev().zzb($r3, $r2);
        } catch (RemoteException e) {
            $r3.zza(8, null, null);
        }
    }

    public void zza(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)V"}) com.google.android.gms.internal.zzqk.zzb<com.google.android.gms.common.api.Result> r21, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)V"}) com.google.android.gms.people.model.AutocompleteBuffer r22, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)V"}) int r23, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)V"}) int r24, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)V"}) long r25) throws  {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
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
        r20 = this;
        r0 = r20;
        r0.zzbrq();
        r0 = r22;
        r7 = r0.isClosed();
        if (r7 != 0) goto L_0x0046;
    L_0x000d:
        r7 = 1;
    L_0x000e:
        r8 = "AutocompleteBuffer is released.";
        com.google.android.gms.common.internal.zzab.zzb(r7, r8);
        r10 = 0;
        r9 = (r25 > r10 ? 1 : (r25 == r10 ? 0 : -1));
        if (r9 != 0) goto L_0x0069;
    L_0x0019:
        r0 = r20;
        r25 = r0.zzcew();
    L_0x001f:
        r12 = new com.google.android.gms.people.internal.zzn$zzag;
        r0 = r21;
        r12.<init>(r0);
        r0 = r20;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r13 = r0.zzcev();	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r0 = r22;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r14 = r0.zzbfb();	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r0 = r13;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r1 = r12;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r2 = r14;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r3 = r23;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r4 = r24;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r5 = r25;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r0.zza(r1, r2, r3, r4, r5);	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        if (r23 < 0) goto L_0x006a;
    L_0x0040:
        r0 = r20;
        r0.zzcex();
        return;
    L_0x0046:
        r7 = 0;
        goto L_0x000e;
    L_0x0048:
        r15 = move-exception;
        r16 = 8;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r17 = 0;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r18 = 0;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r0 = r16;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r1 = r17;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r2 = r18;	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        r12.zza(r0, r1, r2);	 Catch:{ RemoteException -> 0x0048, Throwable -> 0x0060 }
        if (r23 < 0) goto L_0x006b;
    L_0x005a:
        r0 = r20;
        r0.zzcex();
        return;
    L_0x0060:
        r19 = move-exception;
        if (r23 < 0) goto L_0x0068;
    L_0x0063:
        r0 = r20;
        r0.zzcex();
    L_0x0068:
        throw r19;
    L_0x0069:
        goto L_0x001f;
    L_0x006a:
        return;
    L_0x006b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.internal.zzn.zza(com.google.android.gms.internal.zzqk$zzb, com.google.android.gms.people.model.AutocompleteBuffer, int, int, long):void");
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")V"}) com.google.android.gms.internal.zzqk.zzb<AutocompleteResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")V"}) AutocompleteOptions $r3) throws  {
        zzbrq();
        zzu $r4 = new zzu($r1);
        try {
            zzg $r8 = zzcev();
            String $r5 = $r3.account;
            String $r6 = $r3.pageId;
            boolean $z0 = $r3.isDirectorySearch;
            String $r7 = $r3.directoryAccountType;
            int $i0 = $r3.autocompleteType;
            int $i1 = $r3.searchOptions;
            int $i2 = $r3.numberOfResults;
            $r8.zza((zzf) $r4, $r5, $r6, $z0, $r7, $r2, $i0, $i1, $i2, $r3.useAndroidContactFallback);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)V"}) com.google.android.gms.internal.zzqk.zzb<SetAvatarResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)V"}) Uri $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)V"}) boolean $z0) throws  {
        zzbrq();
        zzv $r5 = new zzv($r1);
        try {
            zzcev().zza((zzf) $r5, $r2, $r3, $r4, $z0);
        } catch (RemoteException e) {
            $r5.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")V"}) LoadPeopleOptions $r4) throws  {
        if ($r4 == null) {
            $r4 = LoadPeopleOptions.aLW;
        }
        zza((com.google.android.gms.internal.zzqk.zzb) $r1, $r2, $r3, $r4.getCircleId(), $r4.getQualifiedIds(), $r4.getProjection(), $r4.isPeopleOnly(), $r4.getChangedSince(), $r4.getQuery(), $r4.getSearchFields(), $r4.getSortOrder(), $r4.getExtraColumns());
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleForAspenResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")V"}) LoadPeopleForAspenOptions $r4) throws  {
        if ($r4 == null) {
            $r4 = LoadPeopleForAspenOptions.aMa;
        }
        zza((com.google.android.gms.internal.zzqk.zzb) $r1, $r2, $r3, $r4.getQuery(), $r4.getPageSize(), $r4.getPageToken());
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
        zzbrq();
        zzf $r5 = new zzag($r1);
        try {
            zzcev().zza($r5, $r2, $r3, $r4);
        } catch (RemoteException e) {
            $r5.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Z)V"}) com.google.android.gms.internal.zzqk.zzb<LoadCirclesResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Z)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Z)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Z)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Z)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Z)V"}) String $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        zzbrq();
        zzx $r6 = new zzx($r1);
        try {
            zzcev().zza((zzf) $r6, $r2, $r3, $r4, $i0, $r5, $z0);
        } catch (RemoteException e) {
            $r6.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) com.google.android.gms.internal.zzqk.zzb<LoadPeopleForAggregationResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) int $i1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) int $i2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) String $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) boolean $z1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) int $i3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IZII", "Ljava/lang/String;", "ZII)V"}) int $i4) throws  {
        zzbrq();
        zzat $r6 = new zzat($r1);
        try {
            zzcev().zza((zzf) $r6, $r2, $r3, $r4, $i0, $z0, $i1, $i2, $r5, $z1, $i3, $i4);
        } catch (RemoteException e) {
            $r6.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")V"}) Boolean $r6, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")V"}) String $r7) throws  {
        zzbrq();
        zzag $r8 = new zzag($r1);
        try {
            zzcev().zza((zzf) $r8, $r2, $r3, $r4, $r5, PeopleConstants.booleanToTriState($r6), $r7);
        } catch (RemoteException e) {
            $r8.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) com.google.android.gms.internal.zzqk.zzb<AddCircleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        zzbrq();
        zzn $r6 = new zzn($r1);
        try {
            zzcev().zza((zzf) $r6, $r2, $r3, $r4, $r5, $z0);
        } catch (RemoteException e) {
            $r6.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<AddPeopleToCircleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r5) throws  {
        zzbrq();
        zzp $r6 = new zzp($r1);
        try {
            zzcev().zza((zzf) $r6, $r2, $r3, $r4, (List) $r5);
        } catch (RemoteException e) {
            $r6.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) com.google.android.gms.internal.zzqk.zzb<UpdatePersonCircleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) List<String> $r6, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) FavaDiagnosticsEntity $r7) throws  {
        zzbrq();
        zzao $r8 = new zzao($r1);
        try {
            zzcev().zza((zzf) $r8, $r2, $r3, $r4, (List) $r5, (List) $r6, $r7);
        } catch (RemoteException e) {
            $r8.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        zzbrq();
        zzag $r5 = new zzag($r1);
        try {
            zzcev().zza((zzf) $r5, $r2, $r3, $r4, $z0);
        } catch (RemoteException e) {
            $r5.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) com.google.android.gms.internal.zzqk.zzb<LoadAggregatedPeopleResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) boolean $z1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) int $i1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) String $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) boolean $z2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", "ZII", "Ljava/lang/String;", "ZI)V"}) int $i2) throws  {
        zzbrq();
        if ($i2 != 0 && TextUtils.isEmpty($r4)) {
            zzp.zzan("PeopleClient", "Ignoring custom sort order for all aggregation.");
            $i2 = 0;
        }
        com.google.android.gms.people.internal.agg.zzd $r10 = com.google.android.gms.people.internal.agg.zzd.zza(getContext(), new zzas($r1), $z0, $i1, aQm, aQn, $r4, $r5);
        zzt com_google_android_gms_people_internal_zzn_zzt = new zzt($r10);
        try {
            zzcev().zza((zzf) com_google_android_gms_people_internal_zzn_zzt, $r2, $r3, $r4, 7, $z1, $i0, $i1, $r5, $z2, $i2, 3);
        } catch (RemoteException e) {
            com_google_android_gms_people_internal_zzn_zzt.zza(8, null, null);
        }
        $r10.zzcfr();
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")V"}) String[] $r3) throws  {
        zzbrq();
        zzf $r4 = new zzag($r1);
        try {
            zzcev().zza($r4, $r2, $z0, $r3);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) com.google.android.gms.internal.zzqk.zzb<LoadOwnersResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) boolean $z1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;ZZ", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
        zzbrq();
        zzai $r4 = new zzai($r1);
        try {
            zzcev().zza((zzf) $r4, $z0, $z1, $r2, $r3, $i0);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public void zza(OnDataChanged $r1) throws RemoteException {
        synchronized (this.aQl) {
            try {
                zzbrq();
                if (this.aQl.containsKey($r1)) {
                    zzaa $r5 = (zzaa) this.aQl.get($r1);
                    $r5.release();
                    zzcev().zza((zzf) $r5, false, null, null, 0);
                    this.aQl.remove($r1);
                    return;
                }
                this.aQl.remove($r1);
            } catch (Throwable th) {
                this.aQl.remove($r1);
            }
        }
    }

    public void zza(zzaa $r1, String $r2, String $r3, int $i0) throws RemoteException {
        zzbrq();
        synchronized (this.aQl) {
            zzcev().zza((zzf) $r1, true, $r2, $r3, $i0);
        }
    }

    public <PersonType> void zza(@Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/people/internal/zzn$zzj;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")V"}) zzj $r1, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/people/internal/zzn$zzj;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")V"}) GetOptions $r2, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/people/internal/zzn$zzj;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")V"}) String... $r3) throws  {
        com.google.android.gms.common.internal.zzab.zzag($r3);
        zzbrq();
        zzf $r4 = new zzac($r1);
        try {
            zzcev().zza($r4, new AccountToken($r2.aMr.accountName, $r2.aMr.pageId), Arrays.asList($r3), new ParcelableGetOptions($r2));
        } catch (RemoteException e) {
            $r4.zza(8, null, new Bundle());
        }
    }

    public <PersonType> void zza(@Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/people/internal/zzn$zzk;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", ")V"}) zzk $r1, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/people/internal/zzn$zzk;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", ")V"}) ListOptions $r2) throws  {
        zzbrq();
        zzf $r3 = new zzad($r1);
        try {
            zzcev().zza($r3, new AccountToken($r2.aMr.accountName, $r2.aMr.pageId), new ParcelableListOptions($r2));
        } catch (RemoteException e) {
            $r3.zza(8, null, new Bundle());
        }
    }

    protected Bundle zzadn() throws  {
        Bundle $r1 = new Bundle();
        $r1.putString("social_client_application_id", this.aQk);
        $r1.putString("real_client_package_name", this.co);
        $r1.putBoolean("support_new_image_callback", true);
        return $r1;
    }

    public synchronized void zzav(Bundle $r1) throws  {
        if ($r1 != null) {
            com.google.android.gms.people.internal.agg.zzd.zzdc($r1.getBoolean("use_contactables_api", true));
            zzm.aQh.zzau($r1);
            aQm = $r1.getBundle("config.email_type_map");
            aQn = $r1.getBundle("config.phone_type_map");
        }
    }

    public com.google.android.gms.common.internal.zzr zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadImageResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/internal/zzr;"}) int $i1) throws  {
        zzaj $r4 = new zzaj($r1);
        try {
            return zzcev().zzb((zzf) $r4, $r2, $r3, $i0, $i1);
        } catch (RemoteException e) {
            $r4.zza(8, null, null, null);
            return null;
        }
    }

    public void zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) com.google.android.gms.internal.zzqk.zzb<LoadContactsGaiaIdsResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
        zzbrq();
        zzf $r4 = new zzz($r1);
        try {
            zzcev().zza($r4, $r2, $r3, $i0);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public void zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")V"}) String[] $r4) throws  {
        zzbrq();
        zzf $r5 = new zzan($r1);
        try {
            zzcev().zza($r5, $r2, $r3, $r4);
        } catch (RemoteException e) {
            $r5.zza(8, null, null);
        }
    }

    public void zzb(String $r1, String $r2, long $l0, boolean $z0, boolean $z1) throws RemoteException {
        zzbrq();
        zzcev().zza($r1, $r2, $l0, $z0, $z1);
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzok($r1);
    }

    protected void zzbrq() throws  {
        super.zzavw();
    }

    protected zzg zzcev() throws DeadObjectException {
        return (zzg) super.zzavx();
    }

    public void zzd(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        zzbrq();
        zzag $r4 = new zzag($r1);
        try {
            zzcev().zzb((zzf) $r4, $r2, null, $r3, $z0);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public void zzdb(boolean $z0) throws RemoteException {
        zzbrq();
        zzcev().zzdb($z0);
    }

    public void zze(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$LoadAddToCircleConsentResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LoadAddToCircleConsentResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$LoadAddToCircleConsentResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/GraphUpdate$LoadAddToCircleConsentResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        zzbrq();
        zzf $r4 = new zzr($r1);
        try {
            zzcev().zzb($r4, $r2, $r3);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public void zzf(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        zzbrq();
        zzf $r4 = new zzag($r1);
        try {
            zzcev().zzc($r4, $r2, $r3);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public void zzg(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<FetchBackUpDeviceContactInfoResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        zzbrq();
        zzab $r4 = new zzab($r1);
        try {
            zzcev().zze($r4, $r2, $r3);
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
        }
    }

    public com.google.android.gms.common.internal.zzr zzh(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<PreferredFieldsResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) String $r3) throws  {
        zzbrq();
        zzf $r4 = new zzy($r1);
        try {
            return zzcev().zza($r4, $r2, new ParcelableLoadContactGroupFieldsOptions($r3));
        } catch (RemoteException e) {
            $r4.zza(8, null, null);
            return null;
        }
    }

    public void zzh(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "I)V"}) com.google.android.gms.internal.zzqk.zzb<Result> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Result;", ">;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
        zzbrq();
        zzf $r3 = new zzag($r1);
        try {
            zzcev().zza($r3, $r2, $i0);
        } catch (RemoteException e) {
            $r3.zza(8, null, null);
        }
    }

    protected zzg zzok(IBinder $r1) throws  {
        return com.google.android.gms.people.internal.zzg.zza.zzoj($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.people.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.people.internal.IPeopleService";
    }

    public void zzu(Uri $r1) throws RemoteException {
        zzbrq();
        zzcev().zzt($r1);
    }

    public com.google.android.gms.common.internal.zzr zzw(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) com.google.android.gms.internal.zzqk.zzb<LoadImageResult> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/internal/zzr;"}) String $r2) throws  {
        zzbrq();
        zzf $r3 = new zzaj($r1);
        try {
            return zzcev().zzb($r3, $r2);
        } catch (RemoteException e) {
            $r3.zza(8, null, null, null);
            return null;
        }
    }
}
