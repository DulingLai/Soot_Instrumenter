package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zza extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zza {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zza {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zzap(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.auth.account.IWorkAccountCallback");
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void zzb(Account $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.account.IWorkAccountCallback");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(1, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.auth.account.IWorkAccountCallback");
        }

        public static zza zzcj(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.account.IWorkAccountCallback");
            return ($r1 == null || !($r1 instanceof zza)) ? new zza($r0) : (zza) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.account.IWorkAccountCallback");
                    zzb($r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.account.IWorkAccountCallback");
                    zzap($r1.readInt() != 0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.account.IWorkAccountCallback");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zzap(boolean z) throws RemoteException;

    void zzb(Account account) throws RemoteException;
}
