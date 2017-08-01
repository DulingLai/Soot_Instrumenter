package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.RecoveryDecision;
import com.google.android.gms.auth.RecoveryReadResponse;
import com.google.android.gms.auth.RecoveryWriteRequest;
import com.google.android.gms.auth.RecoveryWriteResponse;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzbr extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzbr {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzbr {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public RecoveryDecision zza(String $r1, String $r2, boolean $z0, Bundle $r3) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.auth.IRecoveryService");
                    $r4.writeString($r1);
                    $r4.writeString($r2);
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r4.writeInt($b0);
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(1, $r4, $r5, 0);
                    $r5.readException();
                    RecoveryDecision $r9 = $r5.readInt() != 0 ? (RecoveryDecision) RecoveryDecision.CREATOR.createFromParcel($r5) : null;
                    $r5.recycle();
                    $r4.recycle();
                    return $r9;
                } catch (Throwable th) {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public RecoveryWriteResponse zza(RecoveryWriteRequest $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.auth.IRecoveryService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeString($r2);
                    this.zzahn.transact(3, $r3, $r4, 0);
                    $r4.readException();
                    RecoveryWriteResponse $r8 = $r4.readInt() != 0 ? (RecoveryWriteResponse) RecoveryWriteResponse.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public RecoveryReadResponse zzd(String $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.auth.IRecoveryService");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                    RecoveryReadResponse $r8 = $r4.readInt() != 0 ? (RecoveryReadResponse) RecoveryReadResponse.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public static zzbr zzb(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.auth.IRecoveryService");
            return ($r1 == null || !($r1 instanceof zzbr)) ? new zza($r0) : (zzbr) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Parcelable $r3 = null;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.auth.IRecoveryService");
                    String $r4 = $r1.readString();
                    String $r5 = $r1.readString();
                    boolean $z0 = $r1.readInt() != 0;
                    if ($r1.readInt() != 0) {
                        $r3 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    }
                    RecoveryDecision $r8 = zza($r4, $r5, $z0, (Bundle) $r3);
                    $r2.writeNoException();
                    if ($r8 != null) {
                        $r2.writeInt(1);
                        $r8.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.auth.IRecoveryService");
                    RecoveryReadResponse $r9 = zzd($r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    if ($r9 != null) {
                        $r2.writeInt(1);
                        $r9.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.auth.IRecoveryService");
                    if ($r1.readInt() != 0) {
                        $r3 = (RecoveryWriteRequest) RecoveryWriteRequest.CREATOR.createFromParcel($r1);
                    }
                    RecoveryWriteResponse $r11 = zza((RecoveryWriteRequest) $r3, $r1.readString());
                    $r2.writeNoException();
                    if ($r11 != null) {
                        $r2.writeInt(1);
                        $r11.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.auth.IRecoveryService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    RecoveryDecision zza(String str, String str2, boolean z, Bundle bundle) throws RemoteException;

    RecoveryWriteResponse zza(RecoveryWriteRequest recoveryWriteRequest, String str) throws RemoteException;

    RecoveryReadResponse zzd(String str, String str2) throws RemoteException;
}
