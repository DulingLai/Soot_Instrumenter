package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.people.data.AudienceMember;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzzw extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzzw {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzzw {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public zzd getView() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    this.zzahn.transact(4, $r1, $r2, 0);
                    $r2.readException();
                    zzd $r4 = com.google.android.gms.dynamic.zzd.zza.zzin($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void refreshButton() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    this.zzahn.transact(7, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setAnalyticsStartView(String $r1, int $i0) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    $r2.writeString($r1);
                    $r2.writeInt($i0);
                    this.zzahn.transact(9, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void setShowProgressIndicator(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(8, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setSize(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    $r1.writeInt($i0);
                    this.zzahn.transact(5, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setType(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    $r1.writeInt($i0);
                    this.zzahn.transact(6, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void zza(String $r1, String $r2, AudienceMember $r3, String $r4, zzzx $r5) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    $r6.writeString($r1);
                    $r6.writeString($r2);
                    if ($r3 != null) {
                        $r6.writeInt(1);
                        $r3.writeToParcel($r6, 0);
                    } else {
                        $r6.writeInt(0);
                    }
                    $r6.writeString($r4);
                    $r6.writeStrongBinder($r5 != null ? $r5.asBinder() : null);
                    this.zzahn.transact(3, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zzb(zzd $r1, zzd $r2, zzd $r3) throws RemoteException {
                IBinder $r4 = null;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    if ($r3 != null) {
                        $r4 = $r3.asBinder();
                    }
                    $r5.writeStrongBinder($r4);
                    this.zzahn.transact(2, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.plus.dynamite.IAddToCirclesButton");
        }

        public static zzzw zzot(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
            return ($r1 == null || !($r1 instanceof zzzw)) ? new zza($r0) : (zzzw) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Object $r3 = null;
            switch ($i0) {
                case 2:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    zzb(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    String $r8 = $r1.readString();
                    String $r9 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r3 = (AudienceMember) AudienceMember.CREATOR.createFromParcel($r1);
                    }
                    zza($r8, $r9, (AudienceMember) $r3, $r1.readString(), com.google.android.gms.internal.zzzx.zza.zzou($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    zzd $r5 = getView();
                    $r2.writeNoException();
                    if ($r5 != null) {
                        $r3 = $r5.asBinder();
                    }
                    $r2.writeStrongBinder((IBinder) $r3);
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    setSize($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    setType($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    refreshButton();
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    setShowProgressIndicator($r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    setAnalyticsStartView($r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.plus.dynamite.IAddToCirclesButton");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    zzd getView() throws RemoteException;

    void refreshButton() throws RemoteException;

    void setAnalyticsStartView(String str, int i) throws RemoteException;

    void setShowProgressIndicator(boolean z) throws RemoteException;

    void setSize(int i) throws RemoteException;

    void setType(int i) throws RemoteException;

    void zza(String str, String str2, AudienceMember audienceMember, String str3, zzzx com_google_android_gms_internal_zzzx) throws RemoteException;

    void zzb(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2, zzd com_google_android_gms_dynamic_zzd3) throws RemoteException;
}
