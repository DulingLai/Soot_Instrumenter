package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class C3395h extends Binder implements C3394g {
    public C3395h() {
        attachInterface(this, "jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
    }

    public static C3394g m502a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
        return (queryLocalInterface == null || !(queryLocalInterface instanceof C3394g)) ? new C3396i(iBinder) : (C3394g) queryLocalInterface;
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        MotionEvent motionEvent = null;
        boolean z = false;
        switch (i) {
            case 1:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4260a(parcel.readInt());
                return true;
            case 2:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    motionEvent = (MotionEvent) MotionEvent.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4264a(motionEvent, z);
                return true;
            case 3:
                KeyEvent keyEvent;
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    keyEvent = (KeyEvent) KeyEvent.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4262a(keyEvent, z);
                return true;
            case 4:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    motionEvent = (MotionEvent) MotionEvent.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4272b(motionEvent, z);
                return true;
            case 5:
                C3392e c3392e;
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    c3392e = (C3392e) C3392e.f374c.createFromParcel(parcel);
                }
                mo4267a(c3392e);
                return true;
            case 6:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4268a(z);
                return true;
            case 7:
                C3390c c3390c;
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    c3390c = (C3390c) C3390c.f361b.createFromParcel(parcel);
                }
                mo4266a(c3390c);
                return true;
            case 8:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4271b(parcel.readInt());
                return true;
            case 9:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4273b(z);
                return true;
            case 10:
                C3388a c3388a;
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    c3388a = (C3388a) C3388a.f354e.createFromParcel(parcel);
                }
                mo4265a(c3388a);
                return true;
            case 11:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4258a();
                return true;
            case 12:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4276c(parcel.readInt());
                return true;
            case 13:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4269a(parcel.createByteArray());
                return true;
            case 14:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    motionEvent = (MotionEvent) MotionEvent.CREATOR.createFromParcel(parcel);
                }
                mo4263a(motionEvent);
                return true;
            case 15:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4270b();
                return true;
            case 16:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4274b(parcel.createByteArray());
                return true;
            case 17:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4259a(parcel.readByte(), parcel.createByteArray());
                return true;
            case 18:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4275c();
                return true;
            case 19:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4279d();
                return true;
            case 20:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4277c(z);
                return true;
            case 21:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4261a(parcel.readInt(), parcel.readInt());
                return true;
            case 22:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4281e();
                return true;
            case 23:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4280d(z);
                return true;
            case 24:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4282e(z);
                return true;
            case 25:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                mo4278c(parcel.createByteArray());
                return true;
            case 1598968902:
                parcel2.writeString("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
