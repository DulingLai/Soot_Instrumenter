package com.google.android.gms.people.identity.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzrq;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.identity.IdentityApi;
import com.google.android.gms.people.identity.IdentityApi.CustomPersonListResult;
import com.google.android.gms.people.identity.IdentityApi.CustomPersonResult;
import com.google.android.gms.people.identity.IdentityApi.GetOptions;
import com.google.android.gms.people.identity.IdentityApi.ListOptions;
import com.google.android.gms.people.identity.IdentityApi.PersonListResult;
import com.google.android.gms.people.identity.IdentityApi.PersonResult;
import com.google.android.gms.people.identity.PersonFactory;
import com.google.android.gms.people.identity.PersonFactory.ContactData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData;
import com.google.android.gms.people.identity.PersonFactory.ServiceData;
import com.google.android.gms.people.identity.PersonListFactory;
import com.google.android.gms.people.identity.PersonListFactory.PersonListItemFactory;
import com.google.android.gms.people.identity.models.Person;
import com.google.android.gms.people.identity.models.PersonReference;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.internal.zzn.zzj;
import com.google.android.gms.people.internal.zzn.zzk;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzq;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzh implements IdentityApi {
    private static final PersonFactory<Person> aND = new zzd();
    private static final PersonListFactory<PersonReference> aNE = new zzf();

    /* compiled from: dalvik_source_com.waze.apk */
    class C09721 implements CustomPersonResult<T> {
        final /* synthetic */ Status qw;

        C09721(Status $r1) throws  {
            this.qw = $r1;
        }

        public PendingResult<CustomPersonResult<T>> getNextPendingResult() throws  {
            return null;
        }

        public DataBuffer<T> getPersonBuffer() throws  {
            return null;
        }

        public Status getStatus() throws  {
            return this.qw;
        }

        public boolean isLocalResultComplete() throws  {
            return true;
        }

        public boolean isResultComplete() throws  {
            return true;
        }

        public void release() throws  {
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09732 implements CustomPersonListResult<T> {
        final /* synthetic */ Status qw;

        C09732(Status $r1) throws  {
            this.qw = $r1;
        }

        public PendingResult<CustomPersonListResult<T>> getNextPendingResult() throws  {
            return null;
        }

        public DataBuffer<T> getPersonBuffer() throws  {
            return null;
        }

        public Status getStatus() throws  {
            return this.qw;
        }

        public boolean isResultComplete() throws  {
            return true;
        }

        public void release() throws  {
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09753 extends zzrq<CustomPersonResult<Person>, PersonResult> {
        C09753(PendingResult $r1) throws  {
            super($r1);
        }

        protected PersonResult zza(@Signature({"(", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<", "Lcom/google/android/gms/people/identity/models/Person;", ">;)", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;"}) final CustomPersonResult<Person> $r1) throws  {
            return new PersonResult(this) {
                final /* synthetic */ C09753 aNG;

                public PendingResult<PersonResult> getNextPendingResult() throws  {
                    return $r1.getNextPendingResult() == null ? null : zzh.zzb($r1.getNextPendingResult());
                }

                public DataBuffer<Person> getPersonBuffer() throws  {
                    return $r1.getPersonBuffer();
                }

                public Status getStatus() throws  {
                    return $r1.getStatus();
                }

                public boolean isLocalResultComplete() throws  {
                    return $r1.isLocalResultComplete();
                }

                public boolean isResultComplete() throws  {
                    return $r1.isResultComplete();
                }

                public void release() throws  {
                }
            };
        }

        protected /* synthetic */ Result zzf(Result $r2) throws  {
            return zza((CustomPersonResult) $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09774 extends zzrq<CustomPersonListResult<PersonReference>, PersonListResult> {
        C09774(PendingResult $r1) throws  {
            super($r1);
        }

        protected PersonListResult zza(@Signature({"(", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<", "Lcom/google/android/gms/people/identity/models/PersonReference;", ">;)", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;"}) final CustomPersonListResult<PersonReference> $r1) throws  {
            return new PersonListResult(this) {
                final /* synthetic */ C09774 aNI;

                public PendingResult<PersonListResult> getNextPendingResult() throws  {
                    return $r1.getNextPendingResult() == null ? null : zzh.zzc($r1.getNextPendingResult());
                }

                public DataBuffer<PersonReference> getPersonBuffer() throws  {
                    return $r1.getPersonBuffer();
                }

                public Status getStatus() throws  {
                    return $r1.getStatus();
                }

                public boolean isResultComplete() throws  {
                    return $r1.isResultComplete();
                }

                public void release() throws  {
                }
            };
        }

        protected /* synthetic */ Result zzf(Result $r2) throws  {
            return zza((CustomPersonListResult) $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    final class zza<PersonType> extends com.google.android.gms.people.People.zza<CustomPersonResult<PersonType>> implements com.google.android.gms.people.identity.internal.zzb.zza, zzj {
        private final PersonFactory<PersonType> aNJ;
        private final Object[] aNK;
        private final GetOptions aNL;
        private final String[] aNM;
        private final Set<DataHolder> aNN = new HashSet();
        private final WeakReference<GoogleApiClient> aNO;
        private com.google.android.gms.internal.zzqk.zza<CustomPersonResult<PersonType>, zzn> aNP;
        private Status aNQ;
        private boolean aNR = false;
        private ArrayList<Bundle> aNS;
        private DataHolder aNT;
        private DataHolder aNU;
        private DataHolder aNV;
        private DataHolder aNW;
        private DataHolder aNX;
        private DataHolder aNY;
        private DataHolder aNZ;
        private DataHolder aOa;
        private DataHolder aOb;
        private boolean aOc = false;
        private Status aOd;
        private ContactData[] aOe;
        final /* synthetic */ zzh aOf;
        private Context mContext;

        /* compiled from: dalvik_source_com.waze.apk */
        class C09781 extends com.google.android.gms.internal.zzqk.zza<CustomPersonResult<PersonType>, zzn> {
            final /* synthetic */ zza aOg;

            C09781(zza $r1, Api $r2, GoogleApiClient $r3) throws  {
                this.aOg = $r1;
                super($r2, $r3);
            }

            protected void zza(zzn com_google_android_gms_people_internal_zzn) throws RemoteException {
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfr($r1);
            }

            protected CustomPersonResult<PersonType> zzfr(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;"}) Status $r1) throws  {
                return zzh.zzfn($r1);
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        class C09792 implements DataBuffer<PersonType> {
            final /* synthetic */ zza aOg;
            final /* synthetic */ com.google.android.gms.internal.zzqk.zza aOh;

            C09792(zza $r1, com.google.android.gms.internal.zzqk.zza $r2) throws  {
                this.aOg = $r1;
                this.aOh = $r2;
            }

            public void close() throws  {
                release();
            }

            public PersonType get(@Signature({"(I)TPersonType;"}) int $i0) throws  {
                return this.aOg.aNJ.build(this.aOg.mContext, this.aOg.aNK[$i0], this.aOg.aNS == null ? null : ServiceData.zzat((Bundle) this.aOg.aNS.get($i0)), this.aOg.aOe == null ? null : this.aOg.aOe[$i0], this.aOg.aNT == null ? null : OfflineDatabaseData.build(this.aOg.aNT, this.aOg.aNU, this.aOg.aNV, this.aOg.aNW, this.aOg.aNX, this.aOg.aNY, this.aOg.aNZ, this.aOg.aOa, this.aOg.aOb, $i0));
            }

            public int getCount() throws  {
                return this.aOg.aNK.length;
            }

            public boolean isClosed() throws  {
                return false;
            }

            public Iterator<PersonType> iterator() throws  {
                return new com.google.android.gms.common.data.zzb(this);
            }

            public void release() throws  {
                if (this.aOh != null) {
                    this.aOh.cancel();
                }
                for (DataHolder close : this.aOg.aNN) {
                    close.close();
                }
            }

            public Iterator<PersonType> singleRefIterator() throws  {
                return iterator();
            }

            public Bundle zzava() throws  {
                return null;
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        class C09803 implements CustomPersonResult<PersonType> {
            final /* synthetic */ zza aOg;
            final /* synthetic */ com.google.android.gms.internal.zzqk.zza aOh;
            final /* synthetic */ DataBuffer aOi;
            final /* synthetic */ boolean aOj;
            final /* synthetic */ boolean aOk;
            final /* synthetic */ Status qw;

            C09803(zza $r1, Status $r2, DataBuffer $r3, boolean $z0, boolean $z1, com.google.android.gms.internal.zzqk.zza $r4) throws  {
                this.aOg = $r1;
                this.qw = $r2;
                this.aOi = $r3;
                this.aOj = $z0;
                this.aOk = $z1;
                this.aOh = $r4;
            }

            public PendingResult<CustomPersonResult<PersonType>> getNextPendingResult() throws  {
                return this.aOh;
            }

            public DataBuffer<PersonType> getPersonBuffer() throws  {
                return this.aOi;
            }

            public Status getStatus() throws  {
                return this.qw;
            }

            public boolean isLocalResultComplete() throws  {
                return this.aOk;
            }

            public boolean isResultComplete() throws  {
                return this.aOj;
            }

            public void release() throws  {
                this.aOi.release();
            }
        }

        public zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")V"}) zzh $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")V"}) GoogleApiClient $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")V"}) GetOptions $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")V"}) PersonFactory<PersonType> $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")V"}) String[] $r5) throws  {
            this.aOf = $r1;
            super($r2);
            this.aNO = new WeakReference($r2);
            this.aNL = $r3;
            this.aNJ = $r4;
            this.aNP = this;
            this.aNM = $r5;
            this.aNK = new Object[$r5.length];
            for (int $i0 = 0; $i0 < this.aNK.length; $i0++) {
                this.aNK[$i0] = new Object();
            }
        }

        private void zzbs(DataHolder $r1) throws  {
            int $i1;
            Set[] $r2 = new Set[this.aNM.length];
            for ($i1 = 0; $i1 < $r2.length; $i1++) {
                $r2[$i1] = new HashSet();
                $r2[$i1].add(this.aNM[$i1]);
            }
            if ($r1 != null) {
                String $r6;
                HashMap $r7 = r15;
                HashMap r15 = new HashMap();
                for ($i1 = 0; $i1 < $r1.getCount(); $i1++) {
                    int $i2 = $r1.zzic($i1);
                    $r6 = $r1.getString(Endpoints.KEY_TARGET_GAIA_ID, $i1, $i2);
                    String $r8 = $r1.getString("contact_id", $i1, $i2);
                    Set $r5 = (Set) $r7.get($r6);
                    if ($r5 == null) {
                        $r5 = r0;
                        HashSet hashSet = new HashSet();
                        $r7.put($r6, $r5);
                    }
                    $r5.add($r8);
                }
                for (int $i0 = 0; $i0 < $r2.length; $i0++) {
                    if (zzq.zzrf(this.aNM[$i0])) {
                        Set<String> $r52 = (Set) $r7.get(zzq.zzra(this.aNM[$i0]));
                        if ($r52 != null) {
                            for (String $r62 : $r52) {
                                $r2[$i0].add(zzc.zznk($r62));
                            }
                        }
                    }
                }
            }
            Context $r11 = this.mContext;
            com.google.android.gms.people.identity.IdentityApi.zza $r13 = this.aNL;
            com.google.android.gms.people.identity.IdentityApi.zza $r12 = $r13;
            $r13 = $r13.aMr;
            zzb.zza((com.google.android.gms.people.identity.internal.zzb.zza) this, $r11, $r13.accountName, $r2);
        }

        private void zzcdb() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:45:0x01a7
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r32 = this;
            r0 = r32;
            r7 = r0.aNP;
            if (r7 != 0) goto L_0x0007;
        L_0x0006:
            return;
        L_0x0007:
            r0 = r32;
            r8 = r0.aNL;
            r9 = r8.useContactData;
            if (r9 == 0) goto L_0x0185;
        L_0x000f:
            r0 = r32;
            r10 = r0.aOd;
            if (r10 != 0) goto L_0x0185;
        L_0x0015:
            r10 = new com.google.android.gms.common.api.Status;
            r11 = 100;
            r10.<init>(r11);
        L_0x001c:
            r12 = r10.getStatusCode();
            r11 = 100;
            if (r12 == r11) goto L_0x01bb;
        L_0x0024:
            r9 = 1;
        L_0x0025:
            r0 = r32;
            r13 = r0.aOd;
            if (r13 == 0) goto L_0x01c1;
        L_0x002b:
            r14 = 1;
        L_0x002c:
            r0 = r32;
            r7 = r0.aNP;
            r0 = r32;
            r15 = r0.aNO;
            r16 = r15.get();
            r18 = r16;
            r18 = (com.google.android.gms.common.api.GoogleApiClient) r18;
            r17 = r18;
            if (r9 != 0) goto L_0x01c7;
        L_0x0040:
            if (r17 == 0) goto L_0x01c7;
        L_0x0042:
            r19 = new com.google.android.gms.people.identity.internal.zzh$zza$1;
            r20 = com.google.android.gms.people.People.API_1P;
            r0 = r19;
            r1 = r32;
            r2 = r20;
            r3 = r17;
            r0.<init>(r1, r2, r3);
            r0 = r17;
            r21 = r0.isConnected();
            if (r21 == 0) goto L_0x0060;
        L_0x0059:
            r0 = r17;
            r1 = r19;
            r0.zzd(r1);
        L_0x0060:
            r0 = r19;
            r1 = r32;
            r1.aNP = r0;
            r21 = com.google.android.gms.people.internal.zzp.zzcfd();
            if (r21 == 0) goto L_0x00c1;
        L_0x006c:
            r22 = java.lang.String.valueOf(r10);
            if (r9 == 0) goto L_0x01ce;
        L_0x0072:
            r23 = " (Final Result)";
        L_0x0074:
            r24 = new java.lang.StringBuilder;
            r0 = r22;
            r25 = java.lang.String.valueOf(r0);
            r0 = r25;
            r12 = r0.length();
            r12 = r12 + 8;
            r0 = r23;
            r25 = java.lang.String.valueOf(r0);
            r0 = r25;
            r26 = r0.length();
            r0 = r26;
            r12 = r12 + r0;
            r0 = r24;
            r0.<init>(r12);
            r27 = "Status: ";
            r0 = r24;
            r1 = r27;
            r24 = r0.append(r1);
            r0 = r24;
            r1 = r22;
            r24 = r0.append(r1);
            r0 = r24;
            r1 = r23;
            r24 = r0.append(r1);
            r0 = r24;
            r22 = r0.toString();
            r27 = "PeopleClient";
            r0 = r27;
            r1 = r22;
            com.google.android.gms.people.internal.zzp.zzal(r0, r1);
        L_0x00c1:
            r21 = com.google.android.gms.people.internal.zzp.zzcfe();
            if (r21 == 0) goto L_0x013d;
        L_0x00c7:
            r22 = java.lang.String.valueOf(r7);
            r24 = new java.lang.StringBuilder;
            r0 = r22;
            r23 = java.lang.String.valueOf(r0);
            r0 = r23;
            r12 = r0.length();
            r12 = r12 + 14;
            r0 = r24;
            r0.<init>(r12);
            r27 = "old callback: ";
            r0 = r24;
            r1 = r27;
            r24 = r0.append(r1);
            r0 = r24;
            r1 = r22;
            r24 = r0.append(r1);
            r0 = r24;
            r22 = r0.toString();
            r27 = "PeopleClient";
            r0 = r27;
            r1 = r22;
            com.google.android.gms.people.internal.zzp.zzbr(r0, r1);
            r0 = r19;
            r22 = java.lang.String.valueOf(r0);
            r24 = new java.lang.StringBuilder;
            r0 = r22;
            r23 = java.lang.String.valueOf(r0);
            r0 = r23;
            r12 = r0.length();
            r12 = r12 + 14;
            r0 = r24;
            r0.<init>(r12);
            r27 = "new callback: ";
            r0 = r24;
            r1 = r27;
            r24 = r0.append(r1);
            r0 = r24;
            r1 = r22;
            r24 = r0.append(r1);
            r0 = r24;
            r22 = r0.toString();
            r27 = "PeopleClient";
            r0 = r27;
            r1 = r22;
            com.google.android.gms.people.internal.zzp.zzbr(r0, r1);
        L_0x013d:
            r28 = new com.google.android.gms.people.identity.internal.zzh$zza$2;
            r0 = r28;
            r1 = r32;
            r2 = r19;
            r0.<init>(r1, r2);
            r0 = r32;
            r0 = r0.aNP;
            r29 = r0;
            if (r29 == 0) goto L_0x016f;
        L_0x0150:
            r0 = r32;
            r8 = r0.aNL;
            r0 = r8.callback;
            r30 = r0;
            if (r30 == 0) goto L_0x016f;
        L_0x015a:
            r0 = r32;
            r0 = r0.aNP;
            r29 = r0;
            r0 = r32;
            r8 = r0.aNL;
            r0 = r8.callback;
            r30 = r0;
            r0 = r29;
            r1 = r30;
            r0.setResultCallback(r1);
        L_0x016f:
            r31 = new com.google.android.gms.people.identity.internal.zzh$zza$3;
            r0 = r31;
            r1 = r32;
            r2 = r10;
            r3 = r28;
            r4 = r9;
            r5 = r14;
            r6 = r19;
            r0.<init>(r1, r2, r3, r4, r5, r6);
            r0 = r31;
            r7.zzc(r0);
            return;
        L_0x0185:
            r0 = r32;
            r8 = r0.aNL;
            r9 = r8.useWebData;
            if (r9 != 0) goto L_0x0195;
        L_0x018d:
            r0 = r32;
            r8 = r0.aNL;
            r9 = r8.useCachedData;
            if (r9 == 0) goto L_0x01b4;
        L_0x0195:
            r0 = r32;
            r9 = r0.aNR;
            if (r9 != 0) goto L_0x01ab;
        L_0x019b:
            r10 = new com.google.android.gms.common.api.Status;
            goto L_0x01a1;
        L_0x019e:
            goto L_0x001c;
        L_0x01a1:
            r11 = 100;
            r10.<init>(r11);
            goto L_0x019e;
            goto L_0x01ab;
        L_0x01a8:
            goto L_0x001c;
        L_0x01ab:
            r0 = r32;
            r10 = r0.aNQ;
            goto L_0x01a8;
            goto L_0x01b4;
        L_0x01b1:
            goto L_0x001c;
        L_0x01b4:
            r10 = com.google.android.gms.common.api.Status.CQ;
            goto L_0x01b1;
            goto L_0x01bb;
        L_0x01b8:
            goto L_0x0025;
        L_0x01bb:
            r9 = 0;
            goto L_0x01b8;
            goto L_0x01c1;
        L_0x01be:
            goto L_0x002c;
        L_0x01c1:
            r14 = 0;
            goto L_0x01be;
            goto L_0x01c7;
        L_0x01c4:
            goto L_0x0060;
        L_0x01c7:
            r19 = 0;
            goto L_0x01c4;
            goto L_0x01ce;
        L_0x01cb:
            goto L_0x0074;
        L_0x01ce:
            r23 = " (Staged Result)";
            goto L_0x01cb;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.zzh.zza.zzcdb():void");
        }

        public synchronized void zza(int $i0, Bundle $r1, Bundle $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf($r2);
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 57) + String.valueOf($r4).length()).append("GetById callback: status=").append($i0).append("\nresolution=").append($r3).append("\ncontent=").append($r4).toString());
            }
            try {
                $r2.setClassLoader(getClass().getClassLoader());
                this.aNQ = new Status($i0);
                this.aNS = $r2.getParcelableArrayList("get.server_blob");
                this.aNR = $r2.getBoolean("response_complete");
                DataHolder $r12 = (DataHolder) $r2.getParcelable("gaia_map");
                if ($r12 != null) {
                    GetOptions $r13 = this.aNL;
                    if ($r13.useContactData && !this.aOc) {
                        this.aOc = true;
                        zzbs($r12);
                    }
                    Set $r14 = this.aNN;
                    $r14.add($r12);
                }
                $r1 = $r2.getBundle("db");
                if ($r1 != null) {
                    for (String $r32 : $r1.keySet()) {
                        this.aNN.add((DataHolder) $r1.getParcelable($r32));
                    }
                    this.aNT = (DataHolder) $r1.getParcelable("people");
                    this.aNU = (DataHolder) $r1.getParcelable("people_address");
                    this.aNV = (DataHolder) $r1.getParcelable("people_email");
                    this.aNW = (DataHolder) $r1.getParcelable("people_phone");
                    this.aNX = (DataHolder) $r1.getParcelable("owner");
                    this.aNY = (DataHolder) $r1.getParcelable("owner_address");
                    this.aNZ = (DataHolder) $r1.getParcelable("owner_email");
                    this.aOa = (DataHolder) $r1.getParcelable("owner_phone");
                    this.aOb = (DataHolder) $r1.getParcelable("circles");
                }
                if (this.aNS != null) {
                    int $i02 = this.aNK;
                    Object[] $r19 = $i02;
                    zzab.zzbm($i02.length == this.aNS.size());
                }
                zzcdb();
            } catch (Throwable $r17) {
                zzp.zzc("PeopleClient", "GetById callback error:", $r17);
            }
        }

        public synchronized void zza(Status $r1, ContactData[] $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf(Arrays.toString($r2));
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 37) + String.valueOf($r4).length()).append("GetById CP2 callback: status=").append($r3).append(" result=").append($r4).toString());
            }
            try {
                this.aOd = $r1;
                this.aOe = $r2;
                if (this.aOe != null) {
                    zzab.zzbm(this.aNK.length == this.aOe.length);
                }
                zzcdb();
            } catch (Throwable $r8) {
                zzp.zzc("PeopleClient", "GetById CP2 callback error:", $r8);
            }
        }

        protected void zza(zzn $r1) throws RemoteException {
            this.mContext = $r1.getContext();
            if (this.aNL.aMr.accountName != null) {
                $r1.zza((zzj) this, this.aNL, this.aNM);
                return;
            }
            this.aNR = true;
            if (this.aNL.useCachedData || this.aNL.useWebData) {
                this.aNQ = Status.CS;
            } else {
                this.aNQ = Status.CQ;
            }
            zzbs(null);
        }

        protected /* synthetic */ Result zzb(Status $r1) throws  {
            return zzfr($r1);
        }

        protected CustomPersonResult<PersonType> zzfr(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;"}) Status $r1) throws  {
            return zzh.zzfn($r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    final class zzb<PersonRefType> extends com.google.android.gms.people.People.zza<CustomPersonListResult<PersonRefType>> implements com.google.android.gms.people.identity.internal.zzb.zza {
        private final Set<DataHolder> aNN = new HashSet();
        private final WeakReference<GoogleApiClient> aNO;
        private Status aNQ;
        private boolean aNR = false;
        private boolean aOc = false;
        private Status aOd;
        private ContactData[] aOe;
        final /* synthetic */ zzh aOf;
        private final PersonListFactory<PersonRefType> aOl;
        private final ListOptions aOm;
        private zzn aOn;
        private Bundle aOo;
        private com.google.android.gms.internal.zzqk.zzb<CustomPersonListResult<PersonRefType>> awc;
        private Context mContext;

        /* compiled from: dalvik_source_com.waze.apk */
        class C09811 extends com.google.android.gms.internal.zzqk.zza<CustomPersonListResult<PersonRefType>, zzn> {
            final /* synthetic */ zzb aOp;

            C09811(zzb $r1, Api $r2, GoogleApiClient $r3) throws  {
                this.aOp = $r1;
                super($r2, $r3);
            }

            protected void zza(zzn com_google_android_gms_people_internal_zzn) throws RemoteException {
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfs($r1);
            }

            protected CustomPersonListResult<PersonRefType> zzfs(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;"}) Status $r1) throws  {
                return zzh.zzfo($r1);
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        class C09822 implements DataBuffer<PersonRefType> {
            final /* synthetic */ com.google.android.gms.internal.zzqk.zza aOh;
            final /* synthetic */ zzb aOp;
            final /* synthetic */ PersonListItemFactory aOq;

            C09822(zzb $r1, PersonListItemFactory $r2, com.google.android.gms.internal.zzqk.zza $r3) throws  {
                this.aOp = $r1;
                this.aOq = $r2;
                this.aOh = $r3;
            }

            public void close() throws  {
                release();
            }

            public PersonRefType get(@Signature({"(I)TPersonRefType;"}) int $i0) throws  {
                return this.aOq.get($i0);
            }

            public int getCount() throws  {
                return this.aOq.getCount();
            }

            public boolean isClosed() throws  {
                return false;
            }

            public Iterator<PersonRefType> iterator() throws  {
                return new com.google.android.gms.common.data.zzb(this);
            }

            public void release() throws  {
                if (this.aOh != null) {
                    this.aOh.cancel();
                }
                for (DataHolder close : this.aOp.aNN) {
                    close.close();
                }
            }

            public Iterator<PersonRefType> singleRefIterator() throws  {
                return iterator();
            }

            public Bundle zzava() throws  {
                return null;
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        class C09833 implements CustomPersonListResult<PersonRefType> {
            final /* synthetic */ com.google.android.gms.internal.zzqk.zza aOh;
            final /* synthetic */ DataBuffer aOi;
            final /* synthetic */ boolean aOj;
            final /* synthetic */ zzb aOp;
            final /* synthetic */ Status qw;

            C09833(zzb $r1, Status $r2, DataBuffer $r3, boolean $z0, com.google.android.gms.internal.zzqk.zza $r4) throws  {
                this.aOp = $r1;
                this.qw = $r2;
                this.aOi = $r3;
                this.aOj = $z0;
                this.aOh = $r4;
            }

            public PendingResult<CustomPersonListResult<PersonRefType>> getNextPendingResult() throws  {
                return this.aOh;
            }

            public DataBuffer<PersonRefType> getPersonBuffer() throws  {
                return this.aOi;
            }

            public Status getStatus() throws  {
                return this.qw;
            }

            public boolean isResultComplete() throws  {
                return this.aOj;
            }

            public void release() throws  {
                this.aOi.release();
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        private class zza implements zzj {
            final /* synthetic */ zzb aOp;

            private zza(zzb $r1) throws  {
                this.aOp = $r1;
            }

            public void zza(int i, Bundle bundle, Bundle $r2) throws  {
                $r2.setClassLoader(getClass().getClassLoader());
                DataHolder $r6 = (DataHolder) $r2.getParcelable("gaia_map");
                boolean $z0 = $r2.getBoolean("response_complete");
                if ($r6 != null) {
                    this.aOp.aNN.add($r6);
                }
                if (this.aOp.aOm.useContactData && !this.aOp.aOc) {
                    if ($r6 != null || $z0) {
                        this.aOp.aOc = true;
                        this.aOp.zzbs($r6);
                    }
                }
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        private class zzb implements zzk {
            final /* synthetic */ zzb aOp;

            private zzb(zzb $r1) throws  {
                this.aOp = $r1;
            }

            public void zza(int $i0, Bundle bundle, Bundle $r2) throws  {
                $r2.setClassLoader(getClass().getClassLoader());
                this.aOp.aNQ = new Status($i0);
                this.aOp.aOo = $r2.getBundle("get.server_blob");
                this.aOp.aNR = $r2.getBoolean("response_complete");
                String[] $r8 = this.aOp.zzcdc();
                if (this.aOp.aOm.useContactData && !this.aOp.aOc && $r8 != null) {
                    if ($r8.length == 0) {
                        this.aOp.aOc = true;
                        this.aOp.zzbs(null);
                        return;
                    }
                    this.aOp.aOn.zza(new zza(), new com.google.android.gms.people.identity.IdentityApi.GetOptions.zza().zzb(this.aOp.aOm.aMr).zzcl(false).zzcm(false).zzcn(true).zzccu(), $r8);
                }
            }
        }

        public zzb(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)V"}) zzh $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)V"}) GoogleApiClient $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)V"}) ListOptions $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)V"}) PersonListFactory<PersonRefType> $r4) throws  {
            this.aOf = $r1;
            super($r2);
            this.aNO = new WeakReference($r2);
            this.aOm = $r3;
            this.aOl = $r4;
            this.awc = this;
        }

        private void zzbs(DataHolder $r1) throws  {
            Set $r2 = new HashSet();
            if ($r1 != null) {
                for (int $i0 = 0; $i0 < $r1.getCount(); $i0++) {
                    $r2.add(zzc.zznk($r1.getString("contact_id", $i0, $r1.zzic($i0))));
                }
            }
            zzb.zza((com.google.android.gms.people.identity.internal.zzb.zza) this, this.mContext, this.aOm.aMr.accountName, $r2);
        }

        private java.lang.String[] zzcdc() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:43:0x019c
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r38 = this;
            r6 = 0;
            r0 = r38;
            r7 = r0.awc;
            if (r7 != 0) goto L_0x0009;
        L_0x0007:
            r8 = 0;
            return r8;
        L_0x0009:
            r0 = r38;
            r9 = r0.aOm;
            r10 = r9.useContactData;
            if (r10 == 0) goto L_0x017a;
        L_0x0011:
            r0 = r38;
            r11 = r0.aOd;
            if (r11 != 0) goto L_0x017a;
        L_0x0017:
            r11 = new com.google.android.gms.common.api.Status;
            r12 = 100;
            r11.<init>(r12);
        L_0x001e:
            r13 = r11.getStatusCode();
            r12 = 100;
            if (r13 == r12) goto L_0x01b0;
        L_0x0026:
            r10 = 1;
        L_0x0027:
            r0 = r38;
            r7 = r0.awc;
            r0 = r38;
            r14 = r0.aNO;
            r15 = r14.get();
            r17 = r15;
            r17 = (com.google.android.gms.common.api.GoogleApiClient) r17;
            r16 = r17;
            if (r10 != 0) goto L_0x01b6;
        L_0x003b:
            if (r16 == 0) goto L_0x01b6;
        L_0x003d:
            r18 = new com.google.android.gms.people.identity.internal.zzh$zzb$1;
            r19 = com.google.android.gms.people.People.API_1P;
            r0 = r18;
            r1 = r38;
            r2 = r19;
            r3 = r16;
            r0.<init>(r1, r2, r3);
            r0 = r16;
            r20 = r0.isConnected();
            if (r20 == 0) goto L_0x005b;
        L_0x0054:
            r0 = r16;
            r1 = r18;
            r0.zzd(r1);
        L_0x005b:
            r0 = r18;
            r1 = r38;
            r1.awc = r0;
            r20 = com.google.android.gms.people.internal.zzp.zzcfd();
            if (r20 == 0) goto L_0x00bc;
        L_0x0067:
            r21 = java.lang.String.valueOf(r11);
            if (r10 == 0) goto L_0x01bd;
        L_0x006d:
            r22 = " (Final Result)";
        L_0x006f:
            r23 = new java.lang.StringBuilder;
            r0 = r21;
            r24 = java.lang.String.valueOf(r0);
            r0 = r24;
            r13 = r0.length();
            r13 = r13 + 8;
            r0 = r22;
            r24 = java.lang.String.valueOf(r0);
            r0 = r24;
            r25 = r0.length();
            r0 = r25;
            r13 = r13 + r0;
            r0 = r23;
            r0.<init>(r13);
            r26 = "Status: ";
            r0 = r23;
            r1 = r26;
            r23 = r0.append(r1);
            r0 = r23;
            r1 = r21;
            r23 = r0.append(r1);
            r0 = r23;
            r1 = r22;
            r23 = r0.append(r1);
            r0 = r23;
            r21 = r0.toString();
            r26 = "PeopleClient";
            r0 = r26;
            r1 = r21;
            com.google.android.gms.people.internal.zzp.zzal(r0, r1);
        L_0x00bc:
            r20 = com.google.android.gms.people.internal.zzp.zzcfe();
            if (r20 == 0) goto L_0x0138;
        L_0x00c2:
            r21 = java.lang.String.valueOf(r7);
            r23 = new java.lang.StringBuilder;
            r0 = r21;
            r22 = java.lang.String.valueOf(r0);
            r0 = r22;
            r13 = r0.length();
            r13 = r13 + 14;
            r0 = r23;
            r0.<init>(r13);
            r26 = "old callback: ";
            r0 = r23;
            r1 = r26;
            r23 = r0.append(r1);
            r0 = r23;
            r1 = r21;
            r23 = r0.append(r1);
            r0 = r23;
            r21 = r0.toString();
            r26 = "PeopleClient";
            r0 = r26;
            r1 = r21;
            com.google.android.gms.people.internal.zzp.zzbr(r0, r1);
            r0 = r18;
            r21 = java.lang.String.valueOf(r0);
            r23 = new java.lang.StringBuilder;
            r0 = r21;
            r22 = java.lang.String.valueOf(r0);
            r0 = r22;
            r13 = r0.length();
            r13 = r13 + 14;
            r0 = r23;
            r0.<init>(r13);
            r26 = "new callback: ";
            r0 = r23;
            r1 = r26;
            r23 = r0.append(r1);
            r0 = r23;
            r1 = r21;
            r23 = r0.append(r1);
            r0 = r23;
            r21 = r0.toString();
            r26 = "PeopleClient";
            r0 = r26;
            r1 = r21;
            com.google.android.gms.people.internal.zzp.zzbr(r0, r1);
        L_0x0138:
            r0 = r38;
            r0 = r0.aOl;
            r27 = r0;
            r0 = r38;
            r0 = r0.aOo;
            r28 = r0;
            r29 = com.google.android.gms.people.identity.PersonFactory.ServiceData.zzat(r0);
            r0 = r38;
            r0 = r0.aOe;
            r30 = r0;
            r8 = 0;
            r0 = r27;
            r1 = r29;
            r2 = r30;
            r31 = r0.buildList(r1, r2, r8);
            r32 = new java.util.HashSet;
            r0 = r32;
            r0.<init>();
        L_0x0160:
            r0 = r31;
            r13 = r0.getCount();
            if (r6 >= r13) goto L_0x01c0;
        L_0x0168:
            r0 = r31;
            r21 = r0.getQualifiedId(r6);
            if (r21 == 0) goto L_0x0177;
        L_0x0170:
            r0 = r32;
            r1 = r21;
            r0.add(r1);
        L_0x0177:
            r6 = r6 + 1;
            goto L_0x0160;
        L_0x017a:
            r0 = r38;
            r9 = r0.aOm;
            r10 = r9.useWebData;
            if (r10 != 0) goto L_0x018a;
        L_0x0182:
            r0 = r38;
            r9 = r0.aOm;
            r10 = r9.useCachedData;
            if (r10 == 0) goto L_0x01a9;
        L_0x018a:
            r0 = r38;
            r10 = r0.aNR;
            if (r10 != 0) goto L_0x01a0;
        L_0x0190:
            r11 = new com.google.android.gms.common.api.Status;
            goto L_0x0196;
        L_0x0193:
            goto L_0x001e;
        L_0x0196:
            r12 = 100;
            r11.<init>(r12);
            goto L_0x0193;
            goto L_0x01a0;
        L_0x019d:
            goto L_0x001e;
        L_0x01a0:
            r0 = r38;
            r11 = r0.aNQ;
            goto L_0x019d;
            goto L_0x01a9;
        L_0x01a6:
            goto L_0x001e;
        L_0x01a9:
            r11 = com.google.android.gms.common.api.Status.CQ;
            goto L_0x01a6;
            goto L_0x01b0;
        L_0x01ad:
            goto L_0x0027;
        L_0x01b0:
            r10 = 0;
            goto L_0x01ad;
            goto L_0x01b6;
        L_0x01b3:
            goto L_0x005b;
        L_0x01b6:
            r18 = 0;
            goto L_0x01b3;
            goto L_0x01bd;
        L_0x01ba:
            goto L_0x006f;
        L_0x01bd:
            r22 = " (Staged Result)";
            goto L_0x01ba;
        L_0x01c0:
            r33 = new com.google.android.gms.people.identity.internal.zzh$zzb$2;
            r0 = r33;
            r1 = r38;
            r2 = r31;
            r3 = r18;
            r0.<init>(r1, r2, r3);
            r34 = new com.google.android.gms.people.identity.internal.zzh$zzb$3;
            r0 = r34;
            r1 = r38;
            r2 = r11;
            r3 = r33;
            r4 = r10;
            r5 = r18;
            r0.<init>(r1, r2, r3, r4, r5);
            r0 = r34;
            r7.setResult(r0);
            r0 = r32;
            r6 = r0.size();
            r0 = new java.lang.String[r6];
            r35 = r0;
            r0 = r32;
            r1 = r35;
            r36 = r0.toArray(r1);
            r37 = r36;
            r37 = (java.lang.String[]) r37;
            r35 = r37;
            return r35;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.zzh.zzb.zzcdc():java.lang.String[]");
        }

        public void zza(Status $r1, ContactData[] $r2) throws  {
            if (zzp.zzcfd()) {
                String $r3 = String.valueOf($r1);
                String $r4 = String.valueOf(Arrays.toString($r2));
                zzp.zzal("PeopleClient", new StringBuilder((String.valueOf($r3).length() + 37) + String.valueOf($r4).length()).append("GetById CP2 callback: status=").append($r3).append(" result=").append($r4).toString());
            }
            try {
                this.aOd = $r1;
                this.aOe = $r2;
                zzcdc();
            } catch (Throwable $r7) {
                zzp.zzc("PeopleClient", "GetById CP2 callback error:", $r7);
            }
        }

        protected void zza(zzn $r1) throws RemoteException {
            this.aOn = $r1;
            this.mContext = $r1.mContext;
            if (this.aOm.aMr.accountName == null || !(this.aOm.useWebData || this.aOm.useCachedData)) {
                this.aNR = true;
                if (this.aOm.useCachedData || this.aOm.useWebData) {
                    this.aNQ = Status.CS;
                } else {
                    this.aNQ = Status.CQ;
                }
                zzbs(null);
                return;
            }
            $r1.zza(new zzb(), this.aOm);
        }

        protected /* synthetic */ Result zzb(Status $r1) throws  {
            return zzfs($r1);
        }

        protected CustomPersonListResult<PersonRefType> zzfs(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;"}) Status $r1) throws  {
            return zzh.zzfo($r1);
        }
    }

    private static PendingResult<PersonResult> zzb(@Signature({"(", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<", "Lcom/google/android/gms/people/identity/models/Person;", ">;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;", ">;"}) PendingResult<CustomPersonResult<Person>> $r0) throws  {
        return new C09753($r0);
    }

    private static PendingResult<PersonListResult> zzc(@Signature({"(", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<", "Lcom/google/android/gms/people/identity/models/PersonReference;", ">;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) PendingResult<CustomPersonListResult<PersonReference>> $r0) throws  {
        return new C09774($r0);
    }

    private static <T> CustomPersonResult<T> zzfn(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TT;>;"}) Status $r0) throws  {
        return new C09721($r0);
    }

    private static <T> CustomPersonListResult<T> zzfo(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TT;>;"}) Status $r0) throws  {
        return new C09732($r0);
    }

    public <PersonType> PendingResult<CustomPersonResult<PersonType>> getByIds(@Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) GoogleApiClient $r1, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) GetOptions $r2, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) PersonFactory<PersonType> $r3, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) String... $r4) throws  {
        for (String $r5 : $r4) {
            zzab.zzbn(!TextUtils.isEmpty($r5));
        }
        com.google.android.gms.internal.zzqk.zza $r7 = $r1.zzc(new zza(this, $r1, $r2, $r3, $r4));
        if ($r2 == null || $r2.callback == null) {
            return $r7;
        }
        $r7.setResultCallback($r2.callback);
        return $r7;
    }

    public PendingResult<PersonResult> getByIds(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;", ">;"}) GetOptions $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;", ">;"}) String... $r3) throws  {
        return zzb(getByIds($r1, $r2, aND, $r3));
    }

    public PendingResult<PersonListResult> list(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) ListOptions $r2) throws  {
        return zzc(list($r1, $r2, aNE));
    }

    public <PersonRefType> PendingResult<CustomPersonListResult<PersonRefType>> list(@Signature({"<PersonRefType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;>;"}) GoogleApiClient $r1, @Signature({"<PersonRefType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;>;"}) ListOptions $r2, @Signature({"<PersonRefType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;>;"}) PersonListFactory<PersonRefType> $r3) throws  {
        return $r1.zzc(new zzb(this, $r1, $r2, $r3));
    }

    public PendingResult<PersonListResult> listByEmail(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) ListOptions $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) String $r3) throws  {
        if (zzq.zzre($r3)) {
            $r3 = zzq.zzrc($r3);
        }
        return list($r1, com.google.android.gms.people.identity.IdentityApi.ListOptions.zza.zza($r2).zza(com.google.android.gms.people.identity.IdentityApi.zza.zza.zza($r2.aMr).zzmy(Endpoints.ENDPOINT_LIST_BY_EMAIL).zzbo("email", $r3)).zzccv());
    }

    public PendingResult<PersonListResult> listByPhone(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) ListOptions $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) String $r3) throws  {
        if (zzc.zzni($r3)) {
            $r3 = zzc.zznl($r3);
        }
        return list($r1, com.google.android.gms.people.identity.IdentityApi.ListOptions.zza.zza($r2).zza(com.google.android.gms.people.identity.IdentityApi.zza.zza.zza($r2.aMr).zzmy(Endpoints.ENDPOINT_LIST_BY_PHONE).zzbo("phone", $r3)).zzccv());
    }
}
