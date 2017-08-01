package com.google.android.gms.plus.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.server.FavaDiagnosticsEntity;
import com.google.android.gms.common.server.response.SafeParcelResponse;
import dalvik.annotation.Signature;
import java.util.List;

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

            public String getAccountName() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.zzahn.transact(5, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String getAuthCode() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.zzahn.transact(41, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public zzr zza(zzb $r1, int $i0, int $i1, int $i2, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeInt($i0);
                    $r3.writeInt($i1);
                    $r3.writeInt($i2);
                    $r3.writeString($r2);
                    this.zzahn.transact(16, $r3, $r4, 0);
                    $r4.readException();
                    zzr $r6 = com.google.android.gms.common.internal.zzr.zza.zzgs($r4.readStrongBinder());
                    return $r6;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(SafeParcelResponse $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(4, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(zzb $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(8, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(zzb $r1, int $i0, String $r2, Uri $r3, String $r4, String $r5) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeInt($i0);
                    $r6.writeString($r2);
                    if ($r3 != null) {
                        $r6.writeInt(1);
                        $r3.writeToParcel($r6, 0);
                    } else {
                        $r6.writeInt(0);
                    }
                    $r6.writeString($r4);
                    $r6.writeString($r5);
                    this.zzahn.transact(14, $r6, $r7, 0);
                    $r7.readException();
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void zza(zzb $r1, Uri $r2, Bundle $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(9, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(zzb $r1, SafeParcelResponse $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(45, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zzb $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(1, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zzb $r1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(2, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/plus/internal/zzb;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) zzb $r1, @Signature({"(", "Lcom/google/android/gms/plus/internal/zzb;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeStringList($r2);
                    this.zzahn.transact(34, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(String $r1, FavaDiagnosticsEntity $r2, FavaDiagnosticsEntity $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r4.writeString($r1);
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(46, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public String zzaem() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.zzahn.transact(43, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void zzb(zzb $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(19, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzb(zzb $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(3, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzc(zzb $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(18, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzcgq() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.zzahn.transact(6, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean zzcgr() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.zzahn.transact(42, $r1, $r2, 0);
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

            public void zzd(zzb $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(40, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zze(zzb $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(44, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzsc(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    $r2.writeString($r1);
                    this.zzahn.transact(17, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zze zzoy(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.plus.internal.IPlusService");
            return ($r1 == null || !($r1 instanceof zze)) ? new zza($r0) : (zze) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            IBinder $r3 = null;
            String $r5;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zzb(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza($r1.readInt() != 0 ? (SafeParcelResponse) SafeParcelResponse.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    $r5 = getAccountName();
                    $r2.writeNoException();
                    $r2.writeString($r5);
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zzcgq();
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 14:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readInt(), $r1.readString(), $r1.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel($r1) : null, $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 16:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zzr $r15 = zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readInt(), $r1.readInt(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    if ($r15 != null) {
                        $r3 = $r15.asBinder();
                    }
                    $r2.writeStrongBinder($r3);
                    return true;
                case 17:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zzsc($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 18:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zzc(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 19:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zzb(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 34:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), (List) $r1.createStringArrayList());
                    $r2.writeNoException();
                    return true;
                case 40:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zzd(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 41:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    $r5 = getAuthCode();
                    $r2.writeNoException();
                    $r2.writeString($r5);
                    return true;
                case 42:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    boolean $z0 = zzcgr();
                    $r2.writeNoException();
                    $r2.writeInt($z0 ? 1 : 0);
                    return true;
                case 43:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    $r5 = zzaem();
                    $r2.writeNoException();
                    $r2.writeString($r5);
                    return true;
                case 44:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zze(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 45:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza(com.google.android.gms.plus.internal.zzb.zza.zzov($r1.readStrongBinder()), $r1.readInt() != 0 ? (SafeParcelResponse) SafeParcelResponse.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 46:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    zza($r1.readString(), $r1.readInt() != 0 ? (FavaDiagnosticsEntity) FavaDiagnosticsEntity.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (FavaDiagnosticsEntity) FavaDiagnosticsEntity.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.plus.internal.IPlusService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    String getAccountName() throws RemoteException;

    String getAuthCode() throws RemoteException;

    zzr zza(zzb com_google_android_gms_plus_internal_zzb, int i, int i2, int i3, String str) throws RemoteException;

    void zza(SafeParcelResponse safeParcelResponse) throws RemoteException;

    void zza(zzb com_google_android_gms_plus_internal_zzb) throws RemoteException;

    void zza(zzb com_google_android_gms_plus_internal_zzb, int i, String str, Uri uri, String str2, String str3) throws RemoteException;

    void zza(zzb com_google_android_gms_plus_internal_zzb, Uri uri, Bundle bundle) throws RemoteException;

    void zza(zzb com_google_android_gms_plus_internal_zzb, SafeParcelResponse safeParcelResponse) throws RemoteException;

    void zza(zzb com_google_android_gms_plus_internal_zzb, String str) throws RemoteException;

    void zza(zzb com_google_android_gms_plus_internal_zzb, String str, String str2) throws RemoteException;

    void zza(@Signature({"(", "Lcom/google/android/gms/plus/internal/zzb;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) zzb com_google_android_gms_plus_internal_zzb, @Signature({"(", "Lcom/google/android/gms/plus/internal/zzb;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> list) throws RemoteException;

    void zza(String str, FavaDiagnosticsEntity favaDiagnosticsEntity, FavaDiagnosticsEntity favaDiagnosticsEntity2) throws RemoteException;

    String zzaem() throws RemoteException;

    void zzb(zzb com_google_android_gms_plus_internal_zzb) throws RemoteException;

    void zzb(zzb com_google_android_gms_plus_internal_zzb, String str) throws RemoteException;

    void zzc(zzb com_google_android_gms_plus_internal_zzb, String str) throws RemoteException;

    void zzcgq() throws RemoteException;

    boolean zzcgr() throws RemoteException;

    void zzd(zzb com_google_android_gms_plus_internal_zzb, String str) throws RemoteException;

    void zze(zzb com_google_android_gms_plus_internal_zzb, String str) throws RemoteException;

    void zzsc(String str) throws RemoteException;
}
