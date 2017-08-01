package com.google.android.gms.people.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzf extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzf {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzf {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(int $i0, Bundle $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
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
                    this.zzahn.transact(1, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void zza(int $i0, Bundle $r1, ParcelFileDescriptor $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
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
                    this.zzahn.transact(3, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void zza(int $i0, Bundle $r1, ParcelFileDescriptor $r2, Bundle $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
                    $r4.writeInt($i0);
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
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
                    this.zzahn.transact(5, $r4, null, 1);
                } finally {
                    $r4.recycle();
                }
            }

            public void zza(int $i0, Bundle $r1, DataHolder $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
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
                    this.zzahn.transact(2, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void zza(int $i0, Bundle $r1, DataHolder[] $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
                    $r3.writeInt($i0);
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeTypedArray($r2, 0);
                    this.zzahn.transact(4, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.people.internal.IPeopleCallbacks");
        }

        public static zzf zzoi(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
            return ($r1 == null || !($r1 instanceof zzf)) ? new zza($r0) : (zzf) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Bundle $r3 = null;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (DataHolder) DataHolder.CREATOR.createFromParcel($r1) : null);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel($r1) : null);
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
                    $i0 = $r1.readInt();
                    if ($r1.readInt() != 0) {
                        $r3 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    }
                    zza($i0, $r3, (DataHolder[]) $r1.createTypedArray(DataHolder.CREATOR));
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.people.internal.IPeopleCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(int i, Bundle bundle, Bundle bundle2) throws RemoteException;

    void zza(int i, Bundle bundle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void zza(int i, Bundle bundle, ParcelFileDescriptor parcelFileDescriptor, Bundle bundle2) throws RemoteException;

    void zza(int i, Bundle bundle, DataHolder dataHolder) throws RemoteException;

    void zza(int i, Bundle bundle, DataHolder[] dataHolderArr) throws RemoteException;
}
