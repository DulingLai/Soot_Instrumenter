package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzna extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzna {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzna {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(Account $r1, int $i0, zzmz $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.accountactivationstate.internal.IAccountActivationStateService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeInt($i0);
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(1, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public static zzna zzco(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.api.accountactivationstate.internal.IAccountActivationStateService");
            return ($r1 == null || !($r1 instanceof zzna)) ? new zza($r0) : (zzna) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.api.accountactivationstate.internal.IAccountActivationStateService");
                    zza($r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null, $r1.readInt(), com.google.android.gms.internal.zzmz.zza.zzcn($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.api.accountactivationstate.internal.IAccountActivationStateService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(Account account, int i, zzmz com_google_android_gms_internal_zzmz) throws RemoteException;
}
