package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzq extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzq {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzq {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public Account getAccount() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.common.internal.IAccountAccessor");
                    this.zzahn.transact(2, $r1, $r2, 0);
                    $r2.readException();
                    Account $r6 = $r2.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r2) : null;
                    $r2.recycle();
                    $r1.recycle();
                    return $r6;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public static zzq zzgr(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            return ($r1 == null || !($r1 instanceof zzq)) ? new zza($r0) : (zzq) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 2:
                    $r1.enforceInterface("com.google.android.gms.common.internal.IAccountAccessor");
                    Account $r3 = getAccount();
                    $r2.writeNoException();
                    if ($r3 != null) {
                        $r2.writeInt(1);
                        $r3.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.IAccountAccessor");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    Account getAccount() throws RemoteException;
}
