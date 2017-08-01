package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzsu extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzsu {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzsu {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(zzst $r1) throws RemoteException {
                IBinder $r2 = null;
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.common.internal.service.ICommonService");
                    if ($r1 != null) {
                        $r2 = $r1.asBinder();
                    }
                    $r3.writeStrongBinder($r2);
                    this.zzahn.transact(1, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }
        }

        public static zzsu zzhc(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonService");
            return ($r1 == null || !($r1 instanceof zzsu)) ? new zza($r0) : (zzsu) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.internal.service.ICommonService");
                    zza(com.google.android.gms.internal.zzst.zza.zzhb($r1.readStrongBinder()));
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.service.ICommonService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(zzst com_google_android_gms_internal_zzst) throws RemoteException;
}
