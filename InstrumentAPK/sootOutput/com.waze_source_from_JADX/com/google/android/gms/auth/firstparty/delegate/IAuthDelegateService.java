package com.google.android.gms.auth.firstparty.delegate;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public interface IAuthDelegateService extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class Stub extends Binder implements IAuthDelegateService {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class Proxy implements IAuthDelegateService {
            private IBinder zzahn;

            Proxy(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public PendingIntent getConfirmCredentialsWorkflowIntent(ConfirmCredentialsWorkflowRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(4, $r2, $r3, 0);
                    $r3.readException();
                    PendingIntent $r7 = $r3.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public String getInterfaceDescriptor() throws  {
                return "com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService";
            }

            public PendingIntent getSetupAccountWorkflowIntent(SetupAccountWorkflowRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(1, $r2, $r3, 0);
                    $r3.readException();
                    PendingIntent $r7 = $r3.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public PendingIntent getTokenRetrievalWorkflowIntent(TokenWorkflowRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    PendingIntent $r7 = $r3.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public PendingIntent getUpdateCredentialsWorkflowIntent(UpdateCredentialsWorkflowRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    PendingIntent $r7 = $r3.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
        }

        public static IAuthDelegateService asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
            return ($r1 == null || !($r1 instanceof IAuthDelegateService)) ? new Proxy($r0) : (IAuthDelegateService) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            AbstractSafeParcelable $r3 = null;
            PendingIntent $r6;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1.readInt() != 0) {
                        $r3 = (SetupAccountWorkflowRequest) SetupAccountWorkflowRequest.CREATOR.createFromParcel($r1);
                    }
                    $r6 = getSetupAccountWorkflowIntent((SetupAccountWorkflowRequest) $r3);
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1.readInt() != 0) {
                        $r3 = (TokenWorkflowRequest) TokenWorkflowRequest.CREATOR.createFromParcel($r1);
                    }
                    $r6 = getTokenRetrievalWorkflowIntent((TokenWorkflowRequest) $r3);
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1.readInt() != 0) {
                        $r3 = (UpdateCredentialsWorkflowRequest) UpdateCredentialsWorkflowRequest.CREATOR.createFromParcel($r1);
                    }
                    $r6 = getUpdateCredentialsWorkflowIntent((UpdateCredentialsWorkflowRequest) $r3);
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    if ($r1.readInt() != 0) {
                        $r3 = (ConfirmCredentialsWorkflowRequest) ConfirmCredentialsWorkflowRequest.CREATOR.createFromParcel($r1);
                    }
                    $r6 = getConfirmCredentialsWorkflowIntent((ConfirmCredentialsWorkflowRequest) $r3);
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.firstparty.delegate.IAuthDelegateService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    PendingIntent getConfirmCredentialsWorkflowIntent(ConfirmCredentialsWorkflowRequest confirmCredentialsWorkflowRequest) throws RemoteException;

    PendingIntent getSetupAccountWorkflowIntent(SetupAccountWorkflowRequest setupAccountWorkflowRequest) throws RemoteException;

    PendingIntent getTokenRetrievalWorkflowIntent(TokenWorkflowRequest tokenWorkflowRequest) throws RemoteException;

    PendingIntent getUpdateCredentialsWorkflowIntent(UpdateCredentialsWorkflowRequest updateCredentialsWorkflowRequest) throws RemoteException;
}
