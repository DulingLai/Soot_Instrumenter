package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzv extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzv {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzv {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public zzd zzawz() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.common.internal.IGoogleCertificatesApi");
                    this.zzahn.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    zzd $r4 = com.google.android.gms.dynamic.zzd.zza.zzin($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public zzd zzaxa() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.common.internal.IGoogleCertificatesApi");
                    this.zzahn.transact(2, $r1, $r2, 0);
                    $r2.readException();
                    zzd $r4 = com.google.android.gms.dynamic.zzd.zza.zzin($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public static zzv zzgw(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
            return ($r1 == null || !($r1 instanceof zzv)) ? new zza($r0) : (zzv) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            IBinder $r3 = null;
            zzd $r4;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
                    $r4 = zzawz();
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder($r3);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
                    $r4 = zzaxa();
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder($r3);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.IGoogleCertificatesApi");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    zzd zzawz() throws RemoteException;

    zzd zzaxa() throws RemoteException;
}
