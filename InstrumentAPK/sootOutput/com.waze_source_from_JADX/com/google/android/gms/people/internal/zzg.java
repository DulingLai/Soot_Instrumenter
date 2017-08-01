package com.google.android.gms.people.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.server.FavaDiagnosticsEntity;
import com.google.android.gms.people.identity.internal.AccountToken;
import com.google.android.gms.people.identity.internal.ParcelableGetOptions;
import com.google.android.gms.people.identity.internal.ParcelableListOptions;
import com.google.android.gms.people.internal.autocomplete.ParcelableLoadAutocompleteResultsOptions;
import com.google.android.gms.people.internal.autocomplete.ParcelableLoadContactGroupFieldsOptions;
import com.google.android.gms.people.model.AvatarReference;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzg extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzg {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzg {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public boolean isSyncToContactsEnabled() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    this.zzahn.transact(16, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public Bundle zza(zzf $r1, boolean $z0, String $r2, String $r3, int $i0) throws RemoteException {
                Bundle $r4 = null;
                byte $b1 = (byte) 0;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($z0) {
                        $b1 = (byte) 1;
                    }
                    $r5.writeInt($b1);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    $r5.writeInt($i0);
                    this.zzahn.transact(11, $r5, $r6, 0);
                    $r6.readException();
                    if ($r6.readInt() != 0) {
                        $r4 = (Bundle) Bundle.CREATOR.createFromParcel($r6);
                    }
                    $r6.recycle();
                    $r5.recycle();
                    return $r4;
                } catch (Throwable th) {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public Bundle zza(String $r1, String $r2, long $l0, boolean $z0) throws RemoteException {
                byte $b1 = (byte) 0;
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    $r3.writeLong($l0);
                    if ($z0) {
                        $b1 = (byte) 1;
                    }
                    $r3.writeInt($b1);
                    this.zzahn.transact(26, $r3, $r4, 0);
                    $r4.readException();
                    Bundle $r8 = $r4.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public Bundle zza(String $r1, String $r2, long $l0, boolean $z0, boolean $z1) throws RemoteException {
                byte $b1 = (byte) 1;
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    $r3.writeLong($l0);
                    $r3.writeInt($z0 ? (byte) 1 : (byte) 0);
                    if (!$z1) {
                        $b1 = (byte) 0;
                    }
                    $r3.writeInt($b1);
                    this.zzahn.transact(205, $r3, $r4, 0);
                    $r4.readException();
                    Bundle $r8 = $r4.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzr zza(zzf $r1, DataHolder $r2, int $i0, int $i1, long $l2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeInt($i0);
                    $r3.writeInt($i1);
                    $r3.writeLong($l2);
                    this.zzahn.transact(DisplayStrings.DS_SENDING_REPORT___, $r3, $r4, 0);
                    $r4.readException();
                    zzr $r6 = com.google.android.gms.common.internal.zzr.zza.zzgs($r4.readStrongBinder());
                    return $r6;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzr zza(zzf $r1, AccountToken $r2, ParcelableListOptions $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(DisplayStrings.DS_SENDING_REPORT_FAILED, $r4, $r5, 0);
                    $r5.readException();
                    zzr $r7 = com.google.android.gms.common.internal.zzr.zza.zzgs($r5.readStrongBinder());
                    return $r7;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public zzr zza(zzf $r1, AvatarReference $r2, ParcelableLoadImageOptions $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(DisplayStrings.DS_OAVAILABLE_TO_ALLU, $r4, $r5, 0);
                    $r5.readException();
                    zzr $r7 = com.google.android.gms.common.internal.zzr.zza.zzgs($r5.readStrongBinder());
                    return $r7;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public zzr zza(zzf $r1, String $r2, int $i0) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    $r3.writeInt($i0);
                    this.zzahn.transact(DisplayStrings.DS_OBJECT_ON_ROAD, $r3, $r4, 0);
                    $r4.readException();
                    zzr $r6 = com.google.android.gms.common.internal.zzr.zza.zzgs($r4.readStrongBinder());
                    return $r6;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzr zza(zzf $r1, String $r2, ParcelableLoadAutocompleteResultsOptions $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(DisplayStrings.DS_SELECT_NONE, $r4, $r5, 0);
                    $r5.readException();
                    zzr $r7 = com.google.android.gms.common.internal.zzr.zza.zzgs($r5.readStrongBinder());
                    return $r7;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public zzr zza(zzf $r1, String $r2, ParcelableLoadContactGroupFieldsOptions $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(DisplayStrings.DS_ADD_PEOPLE, $r4, $r5, 0);
                    $r5.readException();
                    zzr $r7 = com.google.android.gms.common.internal.zzr.zza.zzgs($r5.readStrongBinder());
                    return $r7;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public zzr zza(zzf $r1, String $r2, String $r3, boolean $z0, String $r4, String $r5, int $i0, int $i1, int $i2, boolean $z1) throws RemoteException {
                byte $b3 = (byte) 1;
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeInt($z0 ? (byte) 1 : (byte) 0);
                    $r6.writeString($r4);
                    $r6.writeString($r5);
                    $r6.writeInt($i0);
                    $r6.writeInt($i1);
                    $r6.writeInt($i2);
                    if (!$z1) {
                        $b3 = (byte) 0;
                    }
                    $r6.writeInt($b3);
                    this.zzahn.transact(DisplayStrings.DS_NO_WAZEQ_TAP, $r6, $r7, 0);
                    $r7.readException();
                    zzr $r9 = com.google.android.gms.common.internal.zzr.zza.zzgs($r7.readStrongBinder());
                    return $r9;
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzf $r1, long $l0, boolean $z0) throws RemoteException {
                byte $b1 = (byte) 0;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r2.writeLong($l0);
                    if ($z0) {
                        $b1 = (byte) 1;
                    }
                    $r2.writeInt($b1);
                    this.zzahn.transact(6, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(zzf $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(302, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) AccountToken $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) List<String> $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) ParcelableGetOptions $r4) throws RemoteException {
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r5.writeInt(1);
                        $r2.writeToParcel($r5, 0);
                    } else {
                        $r5.writeInt(0);
                    }
                    $r5.writeStringList($r3);
                    if ($r4 != null) {
                        $r5.writeInt(1);
                        $r4.writeToParcel($r5, 0);
                    } else {
                        $r5.writeInt(0);
                    }
                    this.zzahn.transact(DisplayStrings.DS_NO, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(24, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, int $i0, int $i1) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    $r3.writeInt($i0);
                    $r3.writeInt($i1);
                    this.zzahn.transact(5, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(25, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, int $i0) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    $r4.writeInt($i0);
                    this.zzahn.transact(403, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, int $i0, int $i1) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    $r4.writeInt($i0);
                    $r4.writeInt($i1);
                    this.zzahn.transact(29, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, Uri $r4) throws RemoteException {
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    if ($r4 != null) {
                        $r5.writeInt(1);
                        $r4.writeToParcel($r5, 0);
                    } else {
                        $r5.writeInt(0);
                    }
                    this.zzahn.transact(13, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, Uri $r4, boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    if ($r4 != null) {
                        $r5.writeInt(1);
                        $r4.writeToParcel($r5, 0);
                    } else {
                        $r5.writeInt(0);
                    }
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r5.writeInt($b0);
                    this.zzahn.transact(18, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, Bundle $r4) throws RemoteException {
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    if ($r4 != null) {
                        $r5.writeInt(1);
                        $r4.writeToParcel($r5, 0);
                    } else {
                        $r5.writeInt(0);
                    }
                    this.zzahn.transact(1102, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4) throws RemoteException {
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    $r5.writeString($r4);
                    this.zzahn.transact(204, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, int $i0, String $r5) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeInt($i0);
                    $r6.writeString($r5);
                    this.zzahn.transact(3, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, int $i0, String $r5, boolean $z0) throws RemoteException {
                byte $b1 = (byte) 0;
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeInt($i0);
                    $r6.writeString($r5);
                    if ($z0) {
                        $b1 = (byte) 1;
                    }
                    $r6.writeInt($b1);
                    this.zzahn.transact(19, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, int $i0, boolean $z0, int $i1, int $i2, String $r5) throws RemoteException {
                byte $b3 = (byte) 0;
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeInt($i0);
                    if ($z0) {
                        $b3 = (byte) 1;
                    }
                    $r6.writeInt($b3);
                    $r6.writeInt($i1);
                    $r6.writeInt($i2);
                    $r6.writeString($r5);
                    this.zzahn.transact(202, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, int $i0, boolean $z0, int $i1, int $i2, String $r5, boolean $z1) throws RemoteException {
                byte $b3 = (byte) 1;
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeInt($i0);
                    $r6.writeInt($z0 ? (byte) 1 : (byte) 0);
                    $r6.writeInt($i1);
                    $r6.writeInt($i2);
                    $r6.writeString($r5);
                    if (!$z1) {
                        $b3 = (byte) 0;
                    }
                    $r6.writeInt($b3);
                    this.zzahn.transact(203, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, int $i0, boolean $z0, int $i1, int $i2, String $r5, boolean $z1, int $i3, int $i4) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeInt($i0);
                    $r6.writeInt($z0 ? (byte) 1 : (byte) 0);
                    $r6.writeInt($i1);
                    $r6.writeInt($i2);
                    $r6.writeString($r5);
                    $r6.writeInt($z1 ? (byte) 1 : (byte) 0);
                    $r6.writeInt($i3);
                    $r6.writeInt($i4);
                    this.zzahn.transact(402, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, String $r5) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeString($r5);
                    this.zzahn.transact(27, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, String $r5, int $i0, String $r6) throws RemoteException {
                Parcel $r7 = Parcel.obtain();
                Parcel $r8 = Parcel.obtain();
                try {
                    $r7.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r7.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r7.writeString($r2);
                    $r7.writeString($r3);
                    $r7.writeString($r4);
                    $r7.writeString($r5);
                    $r7.writeInt($i0);
                    $r7.writeString($r6);
                    this.zzahn.transact(303, $r7, $r8, 0);
                    $r8.readException();
                } finally {
                    $r8.recycle();
                    $r7.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, String $r5, boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeString($r5);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r6.writeInt($b0);
                    this.zzahn.transact(DisplayStrings.DS_YOUR_PASSWORDS_ARE_NOT_IDENTICAL, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r5) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeStringList($r5);
                    this.zzahn.transact(28, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) long $l1) throws RemoteException {
                byte $b2 = (byte) 0;
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeStringList($r5);
                    $r6.writeInt($i0);
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r6.writeInt($b2);
                    $r6.writeLong($l1);
                    this.zzahn.transact(4, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) long $l1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String $r6, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) int $i2) throws RemoteException {
                Parcel $r7 = Parcel.obtain();
                Parcel $r8 = Parcel.obtain();
                try {
                    $r7.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r7.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r7.writeString($r2);
                    $r7.writeString($r3);
                    $r7.writeString($r4);
                    $r7.writeStringList($r5);
                    $r7.writeInt($i0);
                    $r7.writeInt($z0 ? (byte) 1 : (byte) 0);
                    $r7.writeLong($l1);
                    $r7.writeString($r6);
                    $r7.writeInt($i2);
                    this.zzahn.transact(21, $r7, $r8, 0);
                    $r8.readException();
                } finally {
                    $r8.recycle();
                    $r7.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) long $l1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String $r6, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) int $i2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) int $i3) throws RemoteException {
                Parcel $r7 = Parcel.obtain();
                Parcel $r8 = Parcel.obtain();
                try {
                    $r7.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r7.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r7.writeString($r2);
                    $r7.writeString($r3);
                    $r7.writeString($r4);
                    $r7.writeStringList($r5);
                    $r7.writeInt($i0);
                    $r7.writeInt($z0 ? (byte) 1 : (byte) 0);
                    $r7.writeLong($l1);
                    $r7.writeString($r6);
                    $r7.writeInt($i2);
                    $r7.writeInt($i3);
                    this.zzahn.transact(401, $r7, $r8, 0);
                    $r8.readException();
                } finally {
                    $r8.recycle();
                    $r7.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) long $l1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String $r6, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int $i4) throws RemoteException {
                Parcel $r7 = Parcel.obtain();
                Parcel $r8 = Parcel.obtain();
                try {
                    $r7.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r7.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r7.writeString($r2);
                    $r7.writeString($r3);
                    $r7.writeString($r4);
                    $r7.writeStringList($r5);
                    $r7.writeInt($i0);
                    $r7.writeInt($z0 ? (byte) 1 : (byte) 0);
                    $r7.writeLong($l1);
                    $r7.writeString($r6);
                    $r7.writeInt($i2);
                    $r7.writeInt($i3);
                    $r7.writeInt($i4);
                    this.zzahn.transact(404, $r7, $r8, 0);
                    $r8.readException();
                } finally {
                    $r8.recycle();
                    $r7.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r6) throws RemoteException {
                Parcel $r7 = Parcel.obtain();
                Parcel $r8 = Parcel.obtain();
                try {
                    $r7.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r7.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r7.writeString($r2);
                    $r7.writeString($r3);
                    $r7.writeString($r4);
                    $r7.writeStringList($r5);
                    $r7.writeStringList($r6);
                    this.zzahn.transact(14, $r7, $r8, 0);
                    $r8.readException();
                } finally {
                    $r8.recycle();
                    $r7.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) zzf $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String $r4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) List<String> $r6, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) FavaDiagnosticsEntity $r7) throws RemoteException {
                Parcel $r8 = Parcel.obtain();
                Parcel $r9 = Parcel.obtain();
                try {
                    $r8.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r8.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r8.writeString($r2);
                    $r8.writeString($r3);
                    $r8.writeString($r4);
                    $r8.writeStringList($r5);
                    $r8.writeStringList($r6);
                    if ($r7 != null) {
                        $r8.writeInt(1);
                        $r7.writeToParcel($r8, 0);
                    } else {
                        $r8.writeInt(0);
                    }
                    this.zzahn.transact(23, $r8, $r9, 0);
                    $r9.readException();
                } finally {
                    $r9.recycle();
                    $r8.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    $r5.writeString($r4);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r5.writeInt($b0);
                    this.zzahn.transact(7, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, boolean $z0, int $i0) throws RemoteException {
                byte $b1 = (byte) 0;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    $r5.writeString($r4);
                    if ($z0) {
                        $b1 = (byte) 1;
                    }
                    $r5.writeInt($b1);
                    $r5.writeInt($i0);
                    this.zzahn.transact(9, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String $r4, boolean $z0, int $i0, int $i1) throws RemoteException {
                byte $b2 = (byte) 0;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    $r5.writeString($r4);
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r5.writeInt($b2);
                    $r5.writeInt($i0);
                    $r5.writeInt($i1);
                    this.zzahn.transact(201, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, String $r3, String[] $r4) throws RemoteException {
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    $r5.writeStringArray($r4);
                    this.zzahn.transact(DisplayStrings.DS_OTOUCH_TO_ADDU_NEW, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(zzf $r1, String $r2, boolean $z0, String[] $r3) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r4.writeInt($b0);
                    $r4.writeStringArray($r3);
                    this.zzahn.transact(10, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(zzf $r1, boolean $z0, boolean $z1, String $r2, String $r3) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeInt($z0 ? (byte) 1 : (byte) 0);
                    if (!$z1) {
                        $b0 = (byte) 0;
                    }
                    $r4.writeInt($b0);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(2, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(zzf $r1, boolean $z0, boolean $z1, String $r2, String $r3, int $i0) throws RemoteException {
                byte $b1 = (byte) 1;
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeInt($z0 ? (byte) 1 : (byte) 0);
                    if (!$z1) {
                        $b1 = (byte) 0;
                    }
                    $r4.writeInt($b1);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    $r4.writeInt($i0);
                    this.zzahn.transact(305, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public zzr zzb(zzf $r1, long $l0, boolean $z0) throws RemoteException {
                byte $b1 = (byte) 0;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r2.writeLong($l0);
                    if ($z0) {
                        $b1 = (byte) 1;
                    }
                    $r2.writeInt($b1);
                    this.zzahn.transact(DisplayStrings.DS_NOTEC, $r2, $r3, 0);
                    $r3.readException();
                    zzr $r5 = com.google.android.gms.common.internal.zzr.zza.zzgs($r3.readStrongBinder());
                    return $r5;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public zzr zzb(zzf $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(DisplayStrings.DS_NOT_NOW, $r3, $r4, 0);
                    $r4.readException();
                    zzr $r6 = com.google.android.gms.common.internal.zzr.zza.zzgs($r4.readStrongBinder());
                    return $r6;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzr zzb(zzf $r1, String $r2, int $i0, int $i1) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    $r3.writeInt($i0);
                    $r3.writeInt($i1);
                    this.zzahn.transact(DisplayStrings.DS_NOTEC__IN_THIS_VERSION_YOU_CAN, $r3, $r4, 0);
                    $r4.readException();
                    zzr $r6 = com.google.android.gms.common.internal.zzr.zza.zzgs($r4.readStrongBinder());
                    return $r6;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzr zzb(zzf $r1, String $r2, String $r3, int $i0, int $i1) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    $r4.writeInt($i0);
                    $r4.writeInt($i1);
                    this.zzahn.transact(DisplayStrings.DS_NOT_THERE, $r4, $r5, 0);
                    $r5.readException();
                    zzr $r7 = com.google.android.gms.common.internal.zzr.zza.zzgs($r5.readStrongBinder());
                    return $r7;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public zzr zzb(zzf $r1, String $r2, String $r3, Bundle $r4) throws RemoteException {
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    if ($r4 != null) {
                        $r5.writeInt(1);
                        $r4.writeToParcel($r5, 0);
                    } else {
                        $r5.writeInt(0);
                    }
                    this.zzahn.transact(DisplayStrings.DS_WELCOME_BACK_PS, $r5, $r6, 0);
                    $r6.readException();
                    zzr $r8 = com.google.android.gms.common.internal.zzr.zza.zzgs($r6.readStrongBinder());
                    return $r8;
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zzb(zzf $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(304, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzb(zzf $r1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(101, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zzb(zzf $r1, String $r2, String $r3, int $i0) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    $r4.writeInt($i0);
                    this.zzahn.transact(301, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zzb(zzf $r1, String $r2, String $r3, String $r4, int $i0, String $r5) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeString($r2);
                    $r6.writeString($r3);
                    $r6.writeString($r4);
                    $r6.writeInt($i0);
                    $r6.writeString($r5);
                    this.zzahn.transact(22, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zzb(zzf $r1, String $r2, String $r3, String $r4, boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeString($r2);
                    $r5.writeString($r3);
                    $r5.writeString($r4);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r5.writeInt($b0);
                    this.zzahn.transact(DisplayStrings.DS_SENDING_UPDATE_FAILED, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public Bundle zzbp(String $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    this.zzahn.transact(12, $r3, $r4, 0);
                    $r4.readException();
                    Bundle $r8 = $r4.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public Bundle zzbq(String $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    this.zzahn.transact(17, $r3, $r4, 0);
                    $r4.readException();
                    Bundle $r8 = $r4.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public Bundle zzc(String $r1, String $r2, long $l0) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    $r3.writeLong($l0);
                    this.zzahn.transact(20, $r3, $r4, 0);
                    $r4.readException();
                    Bundle $r8 = $r4.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzr zzc(zzf $r1, String $r2, String $r3, int $i0) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    $r4.writeInt($i0);
                    this.zzahn.transact(DisplayStrings.DS_NO_WAZEQ, $r4, $r5, 0);
                    $r5.readException();
                    zzr $r7 = com.google.android.gms.common.internal.zzr.zza.zzgs($r5.readStrongBinder());
                    return $r7;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zzc(zzf $r1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(102, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zzd(zzf $r1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(1101, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zzdb(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(15, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void zze(zzf $r1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(DisplayStrings.DS_PSS_WORK, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public Bundle zzt(Uri $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(8, $r2, $r3, 0);
                    $r3.readException();
                    Bundle $r7 = $r3.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zzg zzoj(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.people.internal.IPeopleService");
            return ($r1 == null || !($r1 instanceof zzg)) ? new zza($r0) : (zzg) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Bundle $r13;
            zzr $r21;
            switch ($i0) {
                case 2:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0, $r1.readInt() != 0, $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.createStringArrayList(), $r1.readInt(), $r1.readInt() != 0, $r1.readLong());
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readLong(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r13 = zzt($r1.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r13 != null) {
                        $r2.writeInt(1);
                        $r13.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt() != 0, $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 10:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readInt() != 0, $r1.createStringArray());
                    $r2.writeNoException();
                    return true;
                case 11:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r13 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0, $r1.readString(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    if ($r13 != null) {
                        $r2.writeInt(1);
                        $r13.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 12:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r13 = zzbp($r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    if ($r13 != null) {
                        $r2.writeInt(1);
                        $r13.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 13:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 14:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), (List) $r1.createStringArrayList(), (List) $r1.createStringArrayList());
                    $r2.writeNoException();
                    return true;
                case 15:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzdb($r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 16:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    boolean $z0 = isSyncToContactsEnabled();
                    $r2.writeNoException();
                    $r2.writeInt($z0 ? 1 : 0);
                    return true;
                case 17:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r13 = zzbq($r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    if ($r13 != null) {
                        $r2.writeInt(1);
                        $r13.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 18:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 19:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readString(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 20:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r13 = zzc($r1.readString(), $r1.readString(), $r1.readLong());
                    $r2.writeNoException();
                    if ($r13 != null) {
                        $r2.writeInt(1);
                        $r13.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 21:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), (List) $r1.createStringArrayList(), $r1.readInt(), $r1.readInt() != 0, $r1.readLong(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 22:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 23:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), (List) $r1.createStringArrayList(), (List) $r1.createStringArrayList(), $r1.readInt() != 0 ? (FavaDiagnosticsEntity) FavaDiagnosticsEntity.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 24:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 25:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 26:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r13 = zza($r1.readString(), $r1.readString(), $r1.readLong(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    if ($r13 != null) {
                        $r2.writeInt(1);
                        $r13.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 27:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 28:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), (List) $r1.createStringArrayList());
                    $r2.writeNoException();
                    return true;
                case 29:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 101:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 102:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzc(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 201:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt() != 0, $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 202:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readInt() != 0, $r1.readInt(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 203:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readInt() != 0, $r1.readInt(), $r1.readInt(), $r1.readString(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 204:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 205:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r13 = zza($r1.readString(), $r1.readString(), $r1.readLong(), $r1.readInt() != 0, $r1.readInt() != 0);
                    $r2.writeNoException();
                    if ($r13 != null) {
                        $r2.writeInt(1);
                        $r13.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 301:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 302:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 303:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 304:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 305:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0, $r1.readInt() != 0, $r1.readString(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 401:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.createStringArrayList(), $r1.readInt(), $r1.readInt() != 0, $r1.readLong(), $r1.readString(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 402:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readInt() != 0, $r1.readInt(), $r1.readInt(), $r1.readString(), $r1.readInt() != 0, $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 403:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 404:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), (List) $r1.createStringArrayList(), $r1.readInt(), $r1.readInt() != 0, $r1.readLong(), $r1.readString(), $r1.readInt(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case DisplayStrings.DS_NO /*501*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0 ? (AccountToken) AccountToken.CREATOR.createFromParcel($r1) : null, (List) $r1.createStringArrayList(), $r1.readInt() != 0 ? (ParcelableGetOptions) ParcelableGetOptions.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case DisplayStrings.DS_NOTEC__IN_THIS_VERSION_YOU_CAN /*502*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_NOTEC /*503*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readLong(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_NOT_NOW /*504*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_NOT_THERE /*505*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_NO_WAZEQ /*506*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zzc(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_NO_WAZEQ_TAP /*507*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt() != 0, $r1.readString(), $r1.readString(), $r1.readInt(), $r1.readInt(), $r1.readInt(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_OAVAILABLE_TO_ALLU /*508*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0 ? (AvatarReference) AvatarReference.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (ParcelableLoadImageOptions) ParcelableLoadImageOptions.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_OBJECT_ON_ROAD /*509*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_SENDING_REPORT_FAILED /*601*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0 ? (AccountToken) AccountToken.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (ParcelableListOptions) ParcelableListOptions.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_SENDING_REPORT___ /*602*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readInt() != 0 ? (DataHolder) DataHolder.CREATOR.createFromParcel($r1) : null, $r1.readInt(), $r1.readInt(), $r1.readLong());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_SENDING_UPDATE_FAILED /*603*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case DisplayStrings.DS_YOUR_PASSWORDS_ARE_NOT_IDENTICAL /*701*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readString(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 1101:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zzd(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 1102:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case DisplayStrings.DS_WELCOME_BACK_PS /*1201*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zzb(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_SELECT_NONE /*1301*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readInt() != 0 ? (ParcelableLoadAutocompleteResultsOptions) ParcelableLoadAutocompleteResultsOptions.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_ADD_PEOPLE /*1302*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    $r21 = zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readInt() != 0 ? (ParcelableLoadContactGroupFieldsOptions) ParcelableLoadContactGroupFieldsOptions.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r21 != null ? $r21.asBinder() : null);
                    return true;
                case DisplayStrings.DS_PSS_WORK /*1401*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zze(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case DisplayStrings.DS_OTOUCH_TO_ADDU_NEW /*1402*/:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleService");
                    zza(com.google.android.gms.people.internal.zzf.zza.zzoi($r1.readStrongBinder()), $r1.readString(), $r1.readString(), $r1.createStringArray());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.people.internal.IPeopleService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean isSyncToContactsEnabled() throws RemoteException;

    Bundle zza(zzf com_google_android_gms_people_internal_zzf, boolean z, String str, String str2, int i) throws RemoteException;

    Bundle zza(String str, String str2, long j, boolean z) throws RemoteException;

    Bundle zza(String str, String str2, long j, boolean z, boolean z2) throws RemoteException;

    zzr zza(zzf com_google_android_gms_people_internal_zzf, DataHolder dataHolder, int i, int i2, long j) throws RemoteException;

    zzr zza(zzf com_google_android_gms_people_internal_zzf, AccountToken accountToken, ParcelableListOptions parcelableListOptions) throws RemoteException;

    zzr zza(zzf com_google_android_gms_people_internal_zzf, AvatarReference avatarReference, ParcelableLoadImageOptions parcelableLoadImageOptions) throws RemoteException;

    zzr zza(zzf com_google_android_gms_people_internal_zzf, String str, int i) throws RemoteException;

    zzr zza(zzf com_google_android_gms_people_internal_zzf, String str, ParcelableLoadAutocompleteResultsOptions parcelableLoadAutocompleteResultsOptions) throws RemoteException;

    zzr zza(zzf com_google_android_gms_people_internal_zzf, String str, ParcelableLoadContactGroupFieldsOptions parcelableLoadContactGroupFieldsOptions) throws RemoteException;

    zzr zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, boolean z, String str3, String str4, int i, int i2, int i3, boolean z2) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, long j, boolean z) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, Bundle bundle) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) AccountToken accountToken, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) List<String> list, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Lcom/google/android/gms/people/identity/internal/AccountToken;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/people/identity/internal/ParcelableGetOptions;", ")V"}) ParcelableGetOptions parcelableGetOptions) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, int i, int i2) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, int i) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, int i, int i2) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, Uri uri) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, Uri uri, boolean z) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, Bundle bundle) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, int i, String str4) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, int i, String str4, boolean z) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, int i, boolean z, int i2, int i3, String str4) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, int i, boolean z, int i2, int i3, String str4, boolean z2) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, int i, boolean z, int i2, int i3, String str4, boolean z2, int i4, int i5) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, String str4) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, String str4, int i, String str5) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, String str4, boolean z) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String str2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String str3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> list) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) String str, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) String str2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) String str3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) List<String> list, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) int i, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) boolean z, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ)V"}) long j) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String str2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String str3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) List<String> list, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) int i, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) boolean z, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) long j, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) String str4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "I)V"}) int i2) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String str, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String str2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String str3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) List<String> list, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) int i, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) boolean z, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) long j, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) String str4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) int i2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "II)V"}) int i3) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String str, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String str2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String str3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) List<String> list, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int i, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) boolean z, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) long j, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) String str4, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int i2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int i3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;IZJ", "Ljava/lang/String;", "III)V"}) int i4) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String str2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String str3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> list, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> list2) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) zzf com_google_android_gms_people_internal_zzf, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String str2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) String str3, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) List<String> list, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) List<String> list2, @Signature({"(", "Lcom/google/android/gms/people/internal/zzf;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")V"}) FavaDiagnosticsEntity favaDiagnosticsEntity) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, boolean z) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, boolean z, int i) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, boolean z, int i, int i2) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String[] strArr) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, String str, boolean z, String[] strArr) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, boolean z, boolean z2, String str, String str2) throws RemoteException;

    void zza(zzf com_google_android_gms_people_internal_zzf, boolean z, boolean z2, String str, String str2, int i) throws RemoteException;

    zzr zzb(zzf com_google_android_gms_people_internal_zzf, long j, boolean z) throws RemoteException;

    zzr zzb(zzf com_google_android_gms_people_internal_zzf, String str) throws RemoteException;

    zzr zzb(zzf com_google_android_gms_people_internal_zzf, String str, int i, int i2) throws RemoteException;

    zzr zzb(zzf com_google_android_gms_people_internal_zzf, String str, String str2, int i, int i2) throws RemoteException;

    zzr zzb(zzf com_google_android_gms_people_internal_zzf, String str, String str2, Bundle bundle) throws RemoteException;

    void zzb(zzf com_google_android_gms_people_internal_zzf, Bundle bundle) throws RemoteException;

    void zzb(zzf com_google_android_gms_people_internal_zzf, String str, String str2) throws RemoteException;

    void zzb(zzf com_google_android_gms_people_internal_zzf, String str, String str2, int i) throws RemoteException;

    void zzb(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, int i, String str4) throws RemoteException;

    void zzb(zzf com_google_android_gms_people_internal_zzf, String str, String str2, String str3, boolean z) throws RemoteException;

    Bundle zzbp(String str, String str2) throws RemoteException;

    Bundle zzbq(String str, String str2) throws RemoteException;

    Bundle zzc(String str, String str2, long j) throws RemoteException;

    zzr zzc(zzf com_google_android_gms_people_internal_zzf, String str, String str2, int i) throws RemoteException;

    void zzc(zzf com_google_android_gms_people_internal_zzf, String str, String str2) throws RemoteException;

    void zzd(zzf com_google_android_gms_people_internal_zzf, String str, String str2) throws RemoteException;

    void zzdb(boolean z) throws RemoteException;

    void zze(zzf com_google_android_gms_people_internal_zzf, String str, String str2) throws RemoteException;

    Bundle zzt(Uri uri) throws RemoteException;
}
