package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.reporting.OptInRequest;
import com.google.android.gms.location.reporting.ReportingState;
import com.google.android.gms.location.reporting.UploadRequest;
import com.google.android.gms.location.reporting.UploadRequestResult;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzxn extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzxn {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzxn {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public int zza(Account $r1, PlaceReport $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.reporting.internal.IReportingService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(5, $r3, $r4, 0);
                    $r4.readException();
                    int $i0 = $r4.readInt();
                    return $i0;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public int zza(OptInRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.reporting.internal.IReportingService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(6, $r2, $r3, 0);
                    $r3.readException();
                    int $i0 = $r3.readInt();
                    return $i0;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public UploadRequestResult zza(UploadRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.reporting.internal.IReportingService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    UploadRequestResult $r7 = $r3.readInt() != 0 ? (UploadRequestResult) UploadRequestResult.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public int zzbc(long $l0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.location.reporting.internal.IReportingService");
                    $r1.writeLong($l0);
                    this.zzahn.transact(4, $r1, $r2, 0);
                    $r2.readException();
                    int $i1 = $r2.readInt();
                    return $i1;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public ReportingState zzf(Account $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.reporting.internal.IReportingService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(1, $r2, $r3, 0);
                    $r3.readException();
                    ReportingState $r7 = $r3.readInt() != 0 ? (ReportingState) ReportingState.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public int zzg(Account $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.reporting.internal.IReportingService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    int $i0 = $r3.readInt();
                    return $i0;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zzxn zzli(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.location.reporting.internal.IReportingService");
            return ($r1 == null || !($r1 instanceof zzxn)) ? new zza($r0) : (zzxn) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.location.reporting.internal.IReportingService");
                    ReportingState $r6 = zzf($r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.location.reporting.internal.IReportingService");
                    $i0 = zzg($r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.location.reporting.internal.IReportingService");
                    UploadRequestResult $r11 = zza($r1.readInt() != 0 ? (UploadRequest) UploadRequest.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r11 != null) {
                        $r2.writeInt(1);
                        $r11.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.location.reporting.internal.IReportingService");
                    $i0 = zzbc($r1.readLong());
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.location.reporting.internal.IReportingService");
                    $i0 = zza($r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (PlaceReport) PlaceReport.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.location.reporting.internal.IReportingService");
                    $i0 = zza($r1.readInt() != 0 ? (OptInRequest) OptInRequest.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.location.reporting.internal.IReportingService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    int zza(Account account, PlaceReport placeReport) throws RemoteException;

    int zza(OptInRequest optInRequest) throws RemoteException;

    UploadRequestResult zza(UploadRequest uploadRequest) throws RemoteException;

    int zzbc(long j) throws RemoteException;

    ReportingState zzf(Account account) throws RemoteException;

    int zzg(Account account) throws RemoteException;
}
