package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class C3341h extends Binder implements C3340g {
    public C3341h() {
        attachInterface(this, "jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
    }

    public static C3340g m128a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
        return (queryLocalInterface == null || !(queryLocalInterface instanceof C3340g)) ? new C3342i(iBinder) : (C3340g) queryLocalInterface;
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        MotionEvent motionEvent = null;
        boolean z = false;
        switch (i) {
            case 1:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4148a(parcel.readInt());
                return true;
            case 2:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    motionEvent = (MotionEvent) MotionEvent.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4152a(motionEvent, z);
                return true;
            case 3:
                KeyEvent keyEvent;
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    keyEvent = (KeyEvent) KeyEvent.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4150a(keyEvent, z);
                return true;
            case 4:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    motionEvent = (MotionEvent) MotionEvent.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4160b(motionEvent, z);
                return true;
            case 5:
                C3338e c3338e;
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    c3338e = (C3338e) C3338e.f214c.createFromParcel(parcel);
                }
                mo4155a(c3338e);
                return true;
            case 6:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4156a(z);
                return true;
            case 7:
                C3336c c3336c;
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    c3336c = (C3336c) C3336c.f201b.createFromParcel(parcel);
                }
                mo4154a(c3336c);
                return true;
            case 8:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4159b(parcel.readInt());
                return true;
            case 9:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4161b(z);
                return true;
            case 10:
                C3334a c3334a;
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    c3334a = (C3334a) C3334a.f194e.createFromParcel(parcel);
                }
                mo4153a(c3334a);
                return true;
            case 11:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4146a();
                return true;
            case 12:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4164c(parcel.readInt());
                return true;
            case 13:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4157a(parcel.createByteArray());
                return true;
            case 14:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    motionEvent = (MotionEvent) MotionEvent.CREATOR.createFromParcel(parcel);
                }
                mo4151a(motionEvent);
                return true;
            case 15:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4158b();
                return true;
            case 16:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4162b(parcel.createByteArray());
                return true;
            case 17:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4147a(parcel.readByte(), parcel.createByteArray());
                return true;
            case 18:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4163c();
                return true;
            case 19:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4167d();
                return true;
            case 20:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4165c(z);
                return true;
            case 21:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4149a(parcel.readInt(), parcel.readInt());
                return true;
            case 22:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4169e();
                return true;
            case 23:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4168d(z);
                return true;
            case 24:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4170e(z);
                return true;
            case 25:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                mo4166c(parcel.createByteArray());
                return true;
            case 26:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                int f = mo4171f();
                parcel2.writeNoException();
                parcel2.writeInt(f);
                return true;
            case 1598968902:
                parcel2.writeString("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
