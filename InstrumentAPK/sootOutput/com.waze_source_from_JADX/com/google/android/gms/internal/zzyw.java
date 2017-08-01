package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.people.Graph;
import com.google.android.gms.people.Graph.FetchBackUpDeviceContactInfoResult;
import com.google.android.gms.people.Graph.LoadAggregatedPeopleOptions;
import com.google.android.gms.people.Graph.LoadAggregatedPeopleResult;
import com.google.android.gms.people.Graph.LoadCirclesOptions;
import com.google.android.gms.people.Graph.LoadCirclesResult;
import com.google.android.gms.people.Graph.LoadContactsGaiaIdsOptions;
import com.google.android.gms.people.Graph.LoadContactsGaiaIdsResult;
import com.google.android.gms.people.Graph.LoadOwnersOptions;
import com.google.android.gms.people.Graph.LoadOwnersResult;
import com.google.android.gms.people.Graph.LoadPeopleForAggregationResult;
import com.google.android.gms.people.Graph.LoadPeopleOptions;
import com.google.android.gms.people.Graph.LoadPeopleResult;
import com.google.android.gms.people.Graph.LoadPhoneNumbersResult;
import com.google.android.gms.people.People.zza;
import com.google.android.gms.people.exp.ContactGaiaIdRawBuffer;
import com.google.android.gms.people.exp.PersonForAggregationRawBuffer;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.internal.zzo;
import com.google.android.gms.people.model.AggregatedPersonBuffer;
import com.google.android.gms.people.model.CircleBuffer;
import com.google.android.gms.people.model.ContactGaiaIdBuffer;
import com.google.android.gms.people.model.OwnerBuffer;
import com.google.android.gms.people.model.PersonBuffer;
import com.google.android.gms.people.model.PhoneNumberBuffer;
import com.google.android.gms.people.protomodel.FetchBackUpDeviceContactInfoResponse;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzyw implements Graph {
    public PendingResult<LoadPeopleForAggregationResult> expLoadPeopleForAggregation(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) LoadAggregatedPeopleOptions $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("expLoadPeopleForAggregation", $r2, $r3, $r4);
        }
        if ($r4 == null) {
            $r4 = LoadAggregatedPeopleOptions.aLD;
        }
        final String str = $r2;
        final String str2 = $r3;
        final LoadAggregatedPeopleOptions loadAggregatedPeopleOptions = $r4;
        return $r1.zzc(new zza<LoadPeopleForAggregationResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, str, str2, loadAggregatedPeopleOptions.getQuery(), loadAggregatedPeopleOptions.getSearchFields(), loadAggregatedPeopleOptions.isPeopleOnly(), loadAggregatedPeopleOptions.getProjection(), loadAggregatedPeopleOptions.getExtraColumns(), loadAggregatedPeopleOptions.getFilterGaiaId(), loadAggregatedPeopleOptions.isIncludeEvergreenPeople(), loadAggregatedPeopleOptions.getSortOrder(), loadAggregatedPeopleOptions.getFilterGaiaEdgeTypes());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzga($r1);
            }

            protected LoadPeopleForAggregationResult zzga(final Status $r1) throws  {
                return new LoadPeopleForAggregationResult(this) {
                    final /* synthetic */ C08998 aSQ;

                    public Bundle getEmailTypeMapBundle() throws  {
                        return null;
                    }

                    public ContactGaiaIdRawBuffer getGaiaMap() throws  {
                        return null;
                    }

                    public PersonForAggregationRawBuffer getPeople() throws  {
                        return null;
                    }

                    public Bundle getPhoneTypeMapBundle() throws  {
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

    public PendingResult<FetchBackUpDeviceContactInfoResult> fetchBackUpDeviceContactInfo(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;"}) final String $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("fetchBackUpDeviceContactInfo", $r2, $r3);
        }
        return $r1.zzc(new zza<FetchBackUpDeviceContactInfoResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzg(this, $r2, $r3);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfw($r1);
            }

            protected FetchBackUpDeviceContactInfoResult zzfw(final Status $r1) throws  {
                return new FetchBackUpDeviceContactInfoResult(this) {
                    final /* synthetic */ C08872 aSF;

                    public FetchBackUpDeviceContactInfoResponse getResponse() throws  {
                        return null;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }
                };
            }
        });
    }

    public PendingResult<LoadAggregatedPeopleResult> loadAggregatedPeople(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) LoadAggregatedPeopleOptions $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadAggregatedPeople", $r2, $r3, $r4);
        }
        if ($r4 == null) {
            $r4 = LoadAggregatedPeopleOptions.aLD;
        }
        final String str = $r2;
        final String str2 = $r3;
        final LoadAggregatedPeopleOptions loadAggregatedPeopleOptions = $r4;
        return $r1.zzc(new zza<LoadAggregatedPeopleResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza(this, str, str2, loadAggregatedPeopleOptions.isIncludeInvisible(), loadAggregatedPeopleOptions.getQuery(), loadAggregatedPeopleOptions.isPeopleOnly(), loadAggregatedPeopleOptions.getProjection(), loadAggregatedPeopleOptions.getExtraColumns(), loadAggregatedPeopleOptions.getFilterGaiaId(), loadAggregatedPeopleOptions.isIncludeEvergreenPeople(), loadAggregatedPeopleOptions.getSortOrder());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfz($r1);
            }

            protected LoadAggregatedPeopleResult zzfz(final Status $r1) throws  {
                return new LoadAggregatedPeopleResult(this) {
                    final /* synthetic */ C08977 aSP;

                    public AggregatedPersonBuffer getAggregatedPeople() throws  {
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

    public PendingResult<LoadCirclesResult> loadCircles(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) LoadCirclesOptions $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadCircles", $r2, $r3, $r4);
        }
        if ($r4 == null) {
            $r4 = LoadCirclesOptions.aLN;
        }
        final String str = $r2;
        final String str2 = $r3;
        final LoadCirclesOptions loadCirclesOptions = $r4;
        return $r1.zzc(new zza<LoadCirclesResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, str, str2, loadCirclesOptions.getFilterCircleId(), loadCirclesOptions.getFilterCircleType(), loadCirclesOptions.getFilterCircleNamePrefix(), loadCirclesOptions.isGetVisibility());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfx($r1);
            }

            protected LoadCirclesResult zzfx(final Status $r1) throws  {
                return new LoadCirclesResult(this) {
                    final /* synthetic */ C08935 aSL;

                    public CircleBuffer getCircles() throws  {
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

    public PendingResult<LoadContactsGaiaIdsResult> loadContactsGaiaIds(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;"}) LoadContactsGaiaIdsOptions $r2) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadContactsGaiaIds", $r2);
        }
        if ($r2 == null) {
            $r2 = LoadContactsGaiaIdsOptions.aLS;
        }
        return $r1.zzc(new zza<LoadContactsGaiaIdsResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzb((zzb) this, $r2.getFilterContactInfo(), $r2.getFilterGaiaId(), $r2.getFilterGaiaEdgeTypes());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgb($r1);
            }

            protected LoadContactsGaiaIdsResult zzgb(final Status $r1) throws  {
                return new LoadContactsGaiaIdsResult(this) {
                    final /* synthetic */ C09019 aSS;

                    public ContactGaiaIdBuffer getContactsGaiaIds() throws  {
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

    public PendingResult<LoadOwnersResult> loadOwner(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) final String $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadOwner", $r2, $r3);
        }
        return $r1.zzc(new zza<LoadOwnersResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, true, true, $r2, $r3, 0);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfv($r1);
            }

            protected LoadOwnersResult zzfv(final Status $r1) throws  {
                return new LoadOwnersResult(this) {
                    final /* synthetic */ C08914 aSJ;

                    public OwnerBuffer getOwners() throws  {
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

    public PendingResult<LoadOwnersResult> loadOwners(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadOwnersOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadOwnersOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) LoadOwnersOptions $r2) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadOwners", $r2);
        }
        if ($r2 == null) {
            $r2 = LoadOwnersOptions.aLU;
        }
        return $r1.zzc(new zza<LoadOwnersResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, false, $r2.isIncludePlusPages(), null, null, $r2.getSortOrder());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfv($r1);
            }

            protected LoadOwnersResult zzfv(final Status $r1) throws  {
                return new LoadOwnersResult(this) {
                    final /* synthetic */ C08851 aSE;

                    public OwnerBuffer getOwners() throws  {
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

    public PendingResult<LoadPeopleResult> loadPeople(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) LoadPeopleOptions $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadPeople", $r2, $r3, $r4);
        }
        final String str = $r2;
        final String str2 = $r3;
        final LoadPeopleOptions loadPeopleOptions = $r4;
        return $r1.zzc(new zza<LoadPeopleResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, str, str2, loadPeopleOptions);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfy($r1);
            }

            protected LoadPeopleResult zzfy(final Status $r1) throws  {
                return new LoadPeopleResult(this) {
                    final /* synthetic */ C08956 aSN;

                    public PersonBuffer getPeople() throws  {
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

    public PendingResult<LoadPhoneNumbersResult> loadPhoneNumbers(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;"}) final Bundle $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadPhoneNumbers", $r2, zzo.zzax($r3));
        }
        return $r1.zzc(new zza<LoadPhoneNumbersResult>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, $r2, $r3);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgc($r1);
            }

            protected LoadPhoneNumbersResult zzgc(final Status $r1) throws  {
                return new LoadPhoneNumbersResult(this) {
                    final /* synthetic */ AnonymousClass10 aST;

                    public PhoneNumberBuffer getPhoneNumbers() throws  {
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

    public PendingResult<Result> restoreBackedUpDeviceContacts(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String[] $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("restoreBackedUpDeviceContacts", $r2, $r3);
        }
        final String str = $r2;
        final String str2 = $r3;
        final String[] strArr = $r4;
        return $r1.zzc(new zza<Result>(this, $r1) {
            final /* synthetic */ zzyw aSD;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzb((zzb) this, str, str2, strArr);
            }

            protected Result zzb(final Status $r1) throws  {
                return new Result(this) {
                    final /* synthetic */ C08893 aSH;

                    public Status getStatus() throws  {
                        return $r1;
                    }
                };
            }
        });
    }
}
