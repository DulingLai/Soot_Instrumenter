package com.google.android.gms.auth.api.credentials.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzl extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzl {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzl {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(zzk $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(4, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(zzk $r1, CredentialRequest $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
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

            public void zza(zzk $r1, DeleteRequest $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
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

            public void zza(zzk $r1, GeneratePasswordRequest $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
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

            public void zza(zzk $r1, SaveRequest $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
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
        }

        public static zzl zzcr(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
            return ($r1 == null || !($r1 instanceof zzl)) ? new zza($r0) : (zzl) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            AbstractSafeParcelable $r3 = null;
            zzk $r5;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r5 = com.google.android.gms.auth.api.credentials.internal.zzk.zza.zzcq($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (CredentialRequest) CredentialRequest.CREATOR.createFromParcel($r1);
                    }
                    zza($r5, (CredentialRequest) $r3);
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r5 = com.google.android.gms.auth.api.credentials.internal.zzk.zza.zzcq($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (SaveRequest) SaveRequest.CREATOR.createFromParcel($r1);
                    }
                    zza($r5, (SaveRequest) $r3);
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r5 = com.google.android.gms.auth.api.credentials.internal.zzk.zza.zzcq($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (DeleteRequest) DeleteRequest.CREATOR.createFromParcel($r1);
                    }
                    zza($r5, (DeleteRequest) $r3);
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    zza(com.google.android.gms.auth.api.credentials.internal.zzk.zza.zzcq($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    $r5 = com.google.android.gms.auth.api.credentials.internal.zzk.zza.zzcq($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (GeneratePasswordRequest) GeneratePasswordRequest.CREATOR.createFromParcel($r1);
                    }
                    zza($r5, (GeneratePasswordRequest) $r3);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(zzk com_google_android_gms_auth_api_credentials_internal_zzk) throws RemoteException;

    void zza(zzk com_google_android_gms_auth_api_credentials_internal_zzk, CredentialRequest credentialRequest) throws RemoteException;

    void zza(zzk com_google_android_gms_auth_api_credentials_internal_zzk, DeleteRequest deleteRequest) throws RemoteException;

    void zza(zzk com_google_android_gms_auth_api_credentials_internal_zzk, GeneratePasswordRequest generatePasswordRequest) throws RemoteException;

    void zza(zzk com_google_android_gms_auth_api_credentials_internal_zzk, SaveRequest saveRequest) throws RemoteException;
}
