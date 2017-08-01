package com.google.android.gms.plus.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.server.response.SafeParcelResponse;
import com.google.android.gms.plus.internal.model.people.PersonEntity;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzb extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzb {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzb {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(int $i0, Bundle $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    $r3.writeInt($i0);
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(1, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(int $i0, Bundle $r1, SafeParcelResponse $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    $r3.writeInt($i0);
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(5, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(int $i0, PersonEntity $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    $r2.writeInt($i0);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(9, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(DataHolder $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeString($r2);
                    this.zzahn.transact(4, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(DataHolder $r1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(6, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zzb(int $i0, Bundle $r1, ParcelFileDescriptor $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    $r3.writeInt($i0);
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzhh(Status $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(10, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzl(int $i0, Bundle $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    $r2.writeInt($i0);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(7, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzsa(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    $r2.writeString($r1);
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzsb(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    $r2.writeString($r1);
                    this.zzahn.transact(8, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.plus.internal.IPlusCallbacks");
        }

        public static zzb zzov(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
            return ($r1 == null || !($r1 instanceof zzb)) ? new zza($r0) : (zzb) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zzb($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zzsa($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zza($r1.readInt() != 0 ? (DataHolder) DataHolder.CREATOR.createFromParcel($r1) : null, $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (SafeParcelResponse) SafeParcelResponse.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zza($r1.readInt() != 0 ? (DataHolder) DataHolder.CREATOR.createFromParcel($r1) : null, $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zzl($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zzsb($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (PersonEntity) PersonEntity.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 10:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    zzhh($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.plus.internal.IPlusCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(int i, Bundle bundle, Bundle bundle2) throws RemoteException;

    void zza(int i, Bundle bundle, SafeParcelResponse safeParcelResponse) throws RemoteException;

    void zza(int i, PersonEntity personEntity) throws RemoteException;

    void zza(DataHolder dataHolder, String str) throws RemoteException;

    void zza(DataHolder dataHolder, String str, String str2) throws RemoteException;

    void zzb(int i, Bundle bundle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void zzhh(Status status) throws RemoteException;

    void zzl(int i, Bundle bundle) throws RemoteException;

    void zzsa(String str) throws RemoteException;

    void zzsb(String str) throws RemoteException;
}
