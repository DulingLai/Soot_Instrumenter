package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzbq extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzbq {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzbq {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public AccountChangeEventsResponse getAccountChangeEvents(AccountChangeEventsRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    AccountChangeEventsResponse $r7 = $r3.readInt() != 0 ? (AccountChangeEventsResponse) AccountChangeEventsResponse.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public Bundle zza(Account $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(7, $r2, $r3, 0);
                    $r3.readException();
                    Bundle $r7 = $r3.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public Bundle zza(Account $r1, String $r2, Bundle $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    if ($r1 != null) {
                        $r4.writeInt(1);
                        $r1.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    $r4.writeString($r2);
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(5, $r4, $r5, 0);
                    $r5.readException();
                    $r3 = $r5.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r5) : null;
                    $r5.recycle();
                    $r4.recycle();
                    return $r3;
                } catch (Throwable th) {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public Bundle zza(Bundle $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(6, $r2, $r3, 0);
                    $r3.readException();
                    $r1 = $r3.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r1;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public Bundle zza(String $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    $r3.writeString($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                    $r2 = $r4.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r4) : null;
                    $r4.recycle();
                    $r3.recycle();
                    return $r2;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public Bundle zza(String $r1, String $r2, Bundle $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    $r4.writeString($r1);
                    $r4.writeString($r2);
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.zzahn.transact(1, $r4, $r5, 0);
                    $r5.readException();
                    $r3 = $r5.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r5) : null;
                    $r5.recycle();
                    $r4.recycle();
                    return $r3;
                } catch (Throwable th) {
                    $r5.recycle();
                    $r4.recycle();
                }
            }
        }

        public static zzbq zza(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.auth.IAuthManagerService");
            return ($r1 == null || !($r1 instanceof zzbq)) ? new zza($r0) : (zzbq) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Bundle $r7;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.auth.IAuthManagerService");
                    $r7 = zza($r1.readString(), $r1.readString(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r2.writeInt(1);
                        $r7.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.auth.IAuthManagerService");
                    $r7 = zza($r1.readString(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r2.writeInt(1);
                        $r7.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.auth.IAuthManagerService");
                    AccountChangeEventsResponse $r9 = getAccountChangeEvents($r1.readInt() != 0 ? (AccountChangeEventsRequest) AccountChangeEventsRequest.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r9 != null) {
                        $r2.writeInt(1);
                        $r9.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.auth.IAuthManagerService");
                    $r7 = zza($r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null, $r1.readString(), $r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r2.writeInt(1);
                        $r7.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.auth.IAuthManagerService");
                    $r7 = zza($r1.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r2.writeInt(1);
                        $r7.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.auth.IAuthManagerService");
                    $r7 = zza($r1.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r2.writeInt(1);
                        $r7.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.auth.IAuthManagerService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    AccountChangeEventsResponse getAccountChangeEvents(AccountChangeEventsRequest accountChangeEventsRequest) throws RemoteException;

    Bundle zza(Account account) throws RemoteException;

    Bundle zza(Account account, String str, Bundle bundle) throws RemoteException;

    Bundle zza(Bundle bundle) throws RemoteException;

    Bundle zza(String str, Bundle bundle) throws RemoteException;

    Bundle zza(String str, String str2, Bundle bundle) throws RemoteException;
}
