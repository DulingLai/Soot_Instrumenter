package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

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

            public void zza(zza $r1, Account $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.account.IWorkAccountService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(3, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zza $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.account.IWorkAccountService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzaq(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.auth.account.IWorkAccountService");
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(1, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public static zzb zzck(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.account.IWorkAccountService");
            return ($r1 == null || !($r1 instanceof zzb)) ? new zza($r0) : (zzb) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.account.IWorkAccountService");
                    zzaq($r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.account.IWorkAccountService");
                    zza(com.google.android.gms.auth.account.zza.zza.zzcj($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.auth.account.IWorkAccountService");
                    zza(com.google.android.gms.auth.account.zza.zza.zzcj($r1.readStrongBinder()), $r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.account.IWorkAccountService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(zza com_google_android_gms_auth_account_zza, Account account) throws RemoteException;

    void zza(zza com_google_android_gms_auth_account_zza, String str) throws RemoteException;

    void zzaq(boolean z) throws RemoteException;
}
