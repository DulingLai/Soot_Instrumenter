package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzw;

/* compiled from: dalvik_source_com.waze.apk */
public interface zze extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zze {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zze {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(int $i0, Account $r1, zzd $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    $r3.writeInt($i0);
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(8, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(AuthAccountRequest $r1, zzd $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(ResolveAccountRequest $r1, zzw $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(5, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zzq $r1, int $i0, boolean $z0) throws RemoteException {
                byte $b1 = (byte) 0;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r2.writeInt($i0);
                    if ($z0) {
                        $b1 = (byte) 1;
                    }
                    $r2.writeInt($b1);
                    this.zzahn.transact(9, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(CheckServerAuthResult $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(RecordConsentRequest $r1, zzd $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(10, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(SignInRequest $r1, zzd $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(12, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzaik(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    $r1.writeInt($i0);
                    this.zzahn.transact(7, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void zzb(zzd $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(11, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzdf(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(4, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public static zze zzqg(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
            return ($r1 == null || !($r1 instanceof zze)) ? new zza($r0) : (zze) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            boolean $z0 = false;
            Parcelable $r3 = null;
            switch ($i0) {
                case 2:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1.readInt() != 0) {
                        $r3 = (AuthAccountRequest) AuthAccountRequest.CREATOR.createFromParcel($r1);
                    }
                    zza((AuthAccountRequest) $r3, com.google.android.gms.signin.internal.zzd.zza.zzqf($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1.readInt() != 0) {
                        $r3 = (CheckServerAuthResult) CheckServerAuthResult.CREATOR.createFromParcel($r1);
                    }
                    zza((CheckServerAuthResult) $r3);
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    zzdf($r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1.readInt() != 0) {
                        $r3 = (ResolveAccountRequest) ResolveAccountRequest.CREATOR.createFromParcel($r1);
                    }
                    zza((ResolveAccountRequest) $r3, com.google.android.gms.common.internal.zzw.zza.zzgx($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    zzaik($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    $i0 = $r1.readInt();
                    if ($r1.readInt() != 0) {
                        $r3 = (Account) Account.CREATOR.createFromParcel($r1);
                    }
                    zza($i0, (Account) $r3, com.google.android.gms.signin.internal.zzd.zza.zzqf($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    zzq $r9 = com.google.android.gms.common.internal.zzq.zza.zzgr($r1.readStrongBinder());
                    $i0 = $r1.readInt();
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    zza($r9, $i0, $z0);
                    $r2.writeNoException();
                    return true;
                case 10:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1.readInt() != 0) {
                        $r3 = (RecordConsentRequest) RecordConsentRequest.CREATOR.createFromParcel($r1);
                    }
                    zza((RecordConsentRequest) $r3, com.google.android.gms.signin.internal.zzd.zza.zzqf($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 11:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    zzb(com.google.android.gms.signin.internal.zzd.zza.zzqf($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 12:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    if ($r1.readInt() != 0) {
                        $r3 = (SignInRequest) SignInRequest.CREATOR.createFromParcel($r1);
                    }
                    zza((SignInRequest) $r3, com.google.android.gms.signin.internal.zzd.zza.zzqf($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.signin.internal.ISignInService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(int i, Account account, zzd com_google_android_gms_signin_internal_zzd) throws RemoteException;

    void zza(AuthAccountRequest authAccountRequest, zzd com_google_android_gms_signin_internal_zzd) throws RemoteException;

    void zza(ResolveAccountRequest resolveAccountRequest, zzw com_google_android_gms_common_internal_zzw) throws RemoteException;

    void zza(zzq com_google_android_gms_common_internal_zzq, int i, boolean z) throws RemoteException;

    void zza(CheckServerAuthResult checkServerAuthResult) throws RemoteException;

    void zza(RecordConsentRequest recordConsentRequest, zzd com_google_android_gms_signin_internal_zzd) throws RemoteException;

    void zza(SignInRequest signInRequest, zzd com_google_android_gms_signin_internal_zzd) throws RemoteException;

    void zzaik(int i) throws RemoteException;

    void zzb(zzd com_google_android_gms_signin_internal_zzd) throws RemoteException;

    void zzdf(boolean z) throws RemoteException;
}
