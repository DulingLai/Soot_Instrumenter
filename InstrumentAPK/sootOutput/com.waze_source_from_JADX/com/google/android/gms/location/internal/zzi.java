package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.location.ActivityRecognitionRequest;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GestureRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzf;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzi extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzi {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzi {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(long $l0, boolean $z0, PendingIntent $r1) throws RemoteException {
                byte $b1 = (byte) 1;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r2.writeLong($l0);
                    if (!$z0) {
                        $b1 = (byte) 0;
                    }
                    $r2.writeInt($b1);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(5, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(PendingIntent $r1, zzre $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(65, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(PendingIntent $r1, zzh $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    $r4.writeString($r3);
                    this.zzahn.transact(2, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(Location $r1, int $i0) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    $r2.writeInt($i0);
                    this.zzahn.transact(26, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(ActivityRecognitionRequest $r1, PendingIntent $r2, zzre $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeStrongBinder($r3 != null ? $r3.asBinder() : null);
                    this.zzahn.transact(70, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(GeofencingRequest $r1, PendingIntent $r2, zzh $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeStrongBinder($r3 != null ? $r3.asBinder() : null);
                    this.zzahn.transact(57, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(GestureRequest $r1, PendingIntent $r2, zzre $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeStrongBinder($r3 != null ? $r3.asBinder() : null);
                    this.zzahn.transact(60, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(LocationRequest $r1, PendingIntent $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
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
                    this.zzahn.transact(9, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(LocationRequest $r1, zzf $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
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

            public void zza(LocationRequest $r1, zzf $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    $r4.writeString($r3);
                    this.zzahn.transact(20, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(LocationSettingsRequest $r1, zzj $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    $r4.writeString($r3);
                    this.zzahn.transact(63, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zza(LocationRequestInternal $r1, PendingIntent $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
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
                    this.zzahn.transact(53, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(LocationRequestInternal $r1, zzf $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(52, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(LocationRequestUpdateData $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(59, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(zzg $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(67, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(zzh $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(4, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zzf $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(10, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) List<ParcelableGeofence> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) PendingIntent $r2, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) zzh $r3, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) String $r4) throws RemoteException {
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r5.writeTypedList($r1);
                    if ($r2 != null) {
                        $r5.writeInt(1);
                        $r2.writeToParcel($r5, 0);
                    } else {
                        $r5.writeInt(0);
                    }
                    $r5.writeStrongBinder($r3 != null ? $r3.asBinder() : null);
                    $r5.writeString($r4);
                    this.zzahn.transact(1, $r5, $r6, 0);
                    $r6.readException();
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void zza(String[] $r1, zzh $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r4.writeStringArray($r1);
                    $r4.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    $r4.writeString($r3);
                    this.zzahn.transact(3, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void zzb(PendingIntent $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(6, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzb(PendingIntent $r1, zzre $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(66, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public Location zzbsh() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.zzahn.transact(7, $r1, $r2, 0);
                    $r2.readException();
                    Location $r6 = $r2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel($r2) : null;
                    $r2.recycle();
                    $r1.recycle();
                    return $r6;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void zzc(PendingIntent $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(11, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzc(PendingIntent $r1, zzre $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(61, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzc(Location $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(13, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzce(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(12, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void zzd(PendingIntent $r1, zzre $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(68, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zze(PendingIntent $r1, zzre $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    this.zzahn.transact(69, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public ActivityRecognitionResult zzkg(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r2.writeString($r1);
                    this.zzahn.transact(64, $r2, $r3, 0);
                    $r3.readException();
                    ActivityRecognitionResult $r7 = $r3.readInt() != 0 ? (ActivityRecognitionResult) ActivityRecognitionResult.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public Location zzkh(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r2.writeString($r1);
                    this.zzahn.transact(21, $r2, $r3, 0);
                    $r3.readException();
                    Location $r7 = $r3.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public LocationAvailability zzki(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r2.writeString($r1);
                    this.zzahn.transact(34, $r2, $r3, 0);
                    $r3.readException();
                    LocationAvailability $r7 = $r3.readInt() != 0 ? (LocationAvailability) LocationAvailability.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zzi zzkz(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
            return ($r1 == null || !($r1 instanceof zzi)) ? new zza($r0) : (zzi) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            boolean $z0 = false;
            Location $r18;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.createTypedArrayList(ParcelableGeofence.CREATOR), $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.location.internal.zzh.zza.zzky($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.location.internal.zzh.zza.zzky($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.createStringArray(), com.google.android.gms.location.internal.zzh.zza.zzky($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(com.google.android.gms.location.internal.zzh.zza.zzky($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readLong(), $r1.readInt() != 0, $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzb($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r18 = zzbsh();
                    $r2.writeNoException();
                    if ($r18 != null) {
                        $r2.writeInt(1);
                        $r18.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (LocationRequest) LocationRequest.CREATOR.createFromParcel($r1) : null, com.google.android.gms.location.zzf.zza.zzkv($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (LocationRequest) LocationRequest.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 10:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(com.google.android.gms.location.zzf.zza.zzkv($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 11:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzc($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 12:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    zzce($z0);
                    $r2.writeNoException();
                    return true;
                case 13:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzc($r1.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 20:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (LocationRequest) LocationRequest.CREATOR.createFromParcel($r1) : null, com.google.android.gms.location.zzf.zza.zzkv($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 21:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    $r18 = zzkh($r1.readString());
                    $r2.writeNoException();
                    if ($r18 != null) {
                        $r2.writeInt(1);
                        $r18.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 26:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel($r1) : null, $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 34:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    LocationAvailability $r27 = zzki($r1.readString());
                    $r2.writeNoException();
                    if ($r27 != null) {
                        $r2.writeInt(1);
                        $r27.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 52:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (LocationRequestInternal) LocationRequestInternal.CREATOR.createFromParcel($r1) : null, com.google.android.gms.location.zzf.zza.zzkv($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 53:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (LocationRequestInternal) LocationRequestInternal.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 57:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (GeofencingRequest) GeofencingRequest.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.location.internal.zzh.zza.zzky($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 59:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (LocationRequestUpdateData) LocationRequestUpdateData.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 60:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (GestureRequest) GestureRequest.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.internal.zzre.zza.zzgk($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 61:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzc($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.internal.zzre.zza.zzgk($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 63:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (LocationSettingsRequest) LocationSettingsRequest.CREATOR.createFromParcel($r1) : null, com.google.android.gms.location.internal.zzj.zza.zzla($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 64:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    ActivityRecognitionResult $r15 = zzkg($r1.readString());
                    $r2.writeNoException();
                    if ($r15 != null) {
                        $r2.writeInt(1);
                        $r15.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 65:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.internal.zzre.zza.zzgk($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 66:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzb($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.internal.zzre.zza.zzgk($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 67:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(com.google.android.gms.location.internal.zzg.zza.zzkx($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 68:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzd($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.internal.zzre.zza.zzgk($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 69:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zze($r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.internal.zzre.zza.zzgk($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 70:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza($r1.readInt() != 0 ? (ActivityRecognitionRequest) ActivityRecognitionRequest.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null, com.google.android.gms.internal.zzre.zza.zzgk($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException;

    void zza(PendingIntent pendingIntent, zzre com_google_android_gms_internal_zzre) throws RemoteException;

    void zza(PendingIntent pendingIntent, zzh com_google_android_gms_location_internal_zzh, String str) throws RemoteException;

    void zza(Location location, int i) throws RemoteException;

    void zza(ActivityRecognitionRequest activityRecognitionRequest, PendingIntent pendingIntent, zzre com_google_android_gms_internal_zzre) throws RemoteException;

    void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzh com_google_android_gms_location_internal_zzh) throws RemoteException;

    void zza(GestureRequest gestureRequest, PendingIntent pendingIntent, zzre com_google_android_gms_internal_zzre) throws RemoteException;

    void zza(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException;

    void zza(LocationRequest locationRequest, zzf com_google_android_gms_location_zzf) throws RemoteException;

    void zza(LocationRequest locationRequest, zzf com_google_android_gms_location_zzf, String str) throws RemoteException;

    void zza(LocationSettingsRequest locationSettingsRequest, zzj com_google_android_gms_location_internal_zzj, String str) throws RemoteException;

    void zza(LocationRequestInternal locationRequestInternal, PendingIntent pendingIntent) throws RemoteException;

    void zza(LocationRequestInternal locationRequestInternal, zzf com_google_android_gms_location_zzf) throws RemoteException;

    void zza(LocationRequestUpdateData locationRequestUpdateData) throws RemoteException;

    void zza(zzg com_google_android_gms_location_internal_zzg) throws RemoteException;

    void zza(zzh com_google_android_gms_location_internal_zzh, String str) throws RemoteException;

    void zza(zzf com_google_android_gms_location_zzf) throws RemoteException;

    void zza(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) List<ParcelableGeofence> list, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) PendingIntent pendingIntent, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) zzh com_google_android_gms_location_internal_zzh, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/location/internal/ParcelableGeofence;", ">;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/location/internal/zzh;", "Ljava/lang/String;", ")V"}) String str) throws RemoteException;

    void zza(String[] strArr, zzh com_google_android_gms_location_internal_zzh, String str) throws RemoteException;

    void zzb(PendingIntent pendingIntent) throws RemoteException;

    void zzb(PendingIntent pendingIntent, zzre com_google_android_gms_internal_zzre) throws RemoteException;

    Location zzbsh() throws RemoteException;

    void zzc(PendingIntent pendingIntent) throws RemoteException;

    void zzc(PendingIntent pendingIntent, zzre com_google_android_gms_internal_zzre) throws RemoteException;

    void zzc(Location location) throws RemoteException;

    void zzce(boolean z) throws RemoteException;

    void zzd(PendingIntent pendingIntent, zzre com_google_android_gms_internal_zzre) throws RemoteException;

    void zze(PendingIntent pendingIntent, zzre com_google_android_gms_internal_zzre) throws RemoteException;

    ActivityRecognitionResult zzkg(String str) throws RemoteException;

    Location zzkh(String str) throws RemoteException;

    LocationAvailability zzki(String str) throws RemoteException;
}
