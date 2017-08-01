package com.google.android.gms.auth.frp;

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

            public boolean isChallengeRequired() throws RemoteException {
                boolean $z0 = true;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.auth.frp.IFrpService");
                    this.zzahn.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() == 0) {
                        $z0 = false;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean isChallengeSupported() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.auth.frp.IFrpService");
                    this.zzahn.transact(2, $r1, $r2, 0);
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

            public UnlockFactoryResetProtectionResponse unlockFactoryResetProtection(UnlockFactoryResetProtectionRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.frp.IFrpService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    UnlockFactoryResetProtectionResponse $r7 = $r3.readInt() != 0 ? (UnlockFactoryResetProtectionResponse) UnlockFactoryResetProtectionResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zza zzdc(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.frp.IFrpService");
            return ($r1 == null || !($r1 instanceof zza)) ? new zza($r0) : (zza) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            byte $b2 = (byte) 0;
            boolean $z0;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.frp.IFrpService");
                    $z0 = isChallengeRequired();
                    $r2.writeNoException();
                    $r2.writeInt($z0 ? (byte) 1 : (byte) 0);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.frp.IFrpService");
                    $z0 = isChallengeSupported();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.auth.frp.IFrpService");
                    UnlockFactoryResetProtectionResponse $r6 = unlockFactoryResetProtection($r1.readInt() != 0 ? (UnlockFactoryResetProtectionRequest) UnlockFactoryResetProtectionRequest.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.frp.IFrpService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean isChallengeRequired() throws RemoteException;

    boolean isChallengeSupported() throws RemoteException;

    UnlockFactoryResetProtectionResponse unlockFactoryResetProtection(UnlockFactoryResetProtectionRequest unlockFactoryResetProtectionRequest) throws RemoteException;
}
