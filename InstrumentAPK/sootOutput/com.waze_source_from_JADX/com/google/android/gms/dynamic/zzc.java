package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzc extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzc {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzc {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public Bundle getArguments() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(3, $r1, $r2, 0);
                    $r2.readException();
                    Bundle $r6 = $r2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r2) : null;
                    $r2.recycle();
                    $r1.recycle();
                    return $r6;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int getId() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(4, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean getRetainInstance() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(7, $r1, $r2, 0);
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

            public String getTag() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(8, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int getTargetRequestCode() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(10, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean getUserVisibleHint() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(11, $r1, $r2, 0);
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

            public zzd getView() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(12, $r1, $r2, 0);
                    $r2.readException();
                    zzd $r4 = com.google.android.gms.dynamic.zzd.zza.zzin($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean isAdded() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(13, $r1, $r2, 0);
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

            public boolean isDetached() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(14, $r1, $r2, 0);
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

            public boolean isHidden() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(15, $r1, $r2, 0);
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

            public boolean isInLayout() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(16, $r1, $r2, 0);
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

            public boolean isRemoving() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(17, $r1, $r2, 0);
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

            public boolean isResumed() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(18, $r1, $r2, 0);
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

            public boolean isVisible() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(19, $r1, $r2, 0);
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

            public void setHasOptionsMenu(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(21, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setMenuVisibility(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(22, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setRetainInstance(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(23, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setUserVisibleHint(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.zzahn.transact(24, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void startActivity(Intent $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(25, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void startActivityForResult(Intent $r1, int $i0) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public void zzac(zzd $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(20, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzad(zzd $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(27, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public zzd zzbiy() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(2, $r1, $r2, 0);
                    $r2.readException();
                    zzd $r4 = com.google.android.gms.dynamic.zzd.zza.zzin($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public zzc zzbiz() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(5, $r1, $r2, 0);
                    $r2.readException();
                    zzc $r4 = zza.zzim($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public zzd zzbja() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(6, $r1, $r2, 0);
                    $r2.readException();
                    zzd $r4 = com.google.android.gms.dynamic.zzd.zza.zzin($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public zzc zzbjb() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzahn.transact(9, $r1, $r2, 0);
                    $r2.readException();
                    zzc $r4 = zza.zzim($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static zzc zzim(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            return ($r1 == null || !($r1 instanceof zzc)) ? new zza($r0) : (zzc) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Object $r3 = null;
            boolean $z0 = false;
            zzd $r4;
            zzc $r7;
            boolean $z1;
            switch ($i0) {
                case 2:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $r4 = zzbiy();
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder((IBinder) $r3);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    Bundle $r6 = getArguments();
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $i0 = getId();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $r7 = zzbiz();
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r3 = $r7.asBinder();
                    }
                    $r2.writeStrongBinder((IBinder) $r3);
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $r4 = zzbja();
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder((IBinder) $r3);
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z0 = getRetainInstance();
                    $r2.writeNoException();
                    $r2.writeInt($z0 ? 1 : 0);
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    String $r8 = getTag();
                    $r2.writeNoException();
                    $r2.writeString($r8);
                    return true;
                case 9:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $r7 = zzbjb();
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r3 = $r7.asBinder();
                    }
                    $r2.writeStrongBinder((IBinder) $r3);
                    return true;
                case 10:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $i0 = getTargetRequestCode();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 11:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = getUserVisibleHint();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 12:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $r4 = getView();
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder((IBinder) $r3);
                    return true;
                case 13:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = isAdded();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 14:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = isDetached();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 15:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = isHidden();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 16:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = isInLayout();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 17:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = isRemoving();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 18:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = isResumed();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 19:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    $z1 = isVisible();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 20:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzac(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 21:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    setHasOptionsMenu($z0);
                    $r2.writeNoException();
                    return true;
                case 22:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    setMenuVisibility($z0);
                    $r2.writeNoException();
                    return true;
                case 23:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    setRetainInstance($z0);
                    $r2.writeNoException();
                    return true;
                case 24:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    setUserVisibleHint($z0);
                    $r2.writeNoException();
                    return true;
                case 25:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($r1.readInt() != 0) {
                        $r3 = (Intent) Intent.CREATOR.createFromParcel($r1);
                    }
                    startActivity((Intent) $r3);
                    $r2.writeNoException();
                    return true;
                case 26:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if ($r1.readInt() != 0) {
                        $r3 = (Intent) Intent.CREATOR.createFromParcel($r1);
                    }
                    startActivityForResult((Intent) $r3, $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 27:
                    $r1.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzad(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.dynamic.IFragmentWrapper");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    Bundle getArguments() throws RemoteException;

    int getId() throws RemoteException;

    boolean getRetainInstance() throws RemoteException;

    String getTag() throws RemoteException;

    int getTargetRequestCode() throws RemoteException;

    boolean getUserVisibleHint() throws RemoteException;

    zzd getView() throws RemoteException;

    boolean isAdded() throws RemoteException;

    boolean isDetached() throws RemoteException;

    boolean isHidden() throws RemoteException;

    boolean isInLayout() throws RemoteException;

    boolean isRemoving() throws RemoteException;

    boolean isResumed() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void setHasOptionsMenu(boolean z) throws RemoteException;

    void setMenuVisibility(boolean z) throws RemoteException;

    void setRetainInstance(boolean z) throws RemoteException;

    void setUserVisibleHint(boolean z) throws RemoteException;

    void startActivity(Intent intent) throws RemoteException;

    void startActivityForResult(Intent intent, int i) throws RemoteException;

    void zzac(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    void zzad(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    zzd zzbiy() throws RemoteException;

    zzc zzbiz() throws RemoteException;

    zzd zzbja() throws RemoteException;

    zzc zzbjb() throws RemoteException;
}
