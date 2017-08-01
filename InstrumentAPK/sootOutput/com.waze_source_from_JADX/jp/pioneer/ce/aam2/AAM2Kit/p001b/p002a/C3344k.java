package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

public abstract class C3344k extends Binder implements C3343j {
    public C3344k() {
        attachInterface(this, "jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
    }

    public static C3343j m221b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
        return (queryLocalInterface == null || !(queryLocalInterface instanceof C3343j)) ? new C3345l(iBinder) : (C3343j) queryLocalInterface;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        boolean z = false;
        boolean a;
        int i3;
        String c;
        List l;
        long readLong;
        switch (i) {
            case 1:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4180a(C3341h.m128a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 2:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4190b(C3341h.m128a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 3:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4176a(parcel.readStrongBinder(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 4:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4175a(parcel.readStrongBinder());
                parcel2.writeNoException();
                return true;
            case 5:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4185a(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 6:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4184a();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 7:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4187b();
                parcel2.writeNoException();
                return true;
            case 8:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                c = mo4196c();
                parcel2.writeNoException();
                parcel2.writeString(c);
                return true;
            case 9:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                i3 = mo4202d();
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 10:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4181a(z);
                parcel2.writeNoException();
                return true;
            case 11:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4209e();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 12:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4173a(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 13:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                i3 = mo4211f();
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 14:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4188b(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 15:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4197c(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 16:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4216g();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 17:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4217h();
                parcel2.writeNoException();
                return true;
            case 18:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4220i();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 19:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4186a(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 20:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4195b(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 21:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4221j();
                parcel2.writeNoException();
                return true;
            case 22:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4191b(z);
                parcel2.writeNoException();
                return true;
            case 23:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4183a(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            case 24:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4201c(z);
                parcel2.writeNoException();
                return true;
            case 25:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4204d(z);
                parcel2.writeNoException();
                return true;
            case 26:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4208e(z);
                parcel2.writeNoException();
                return true;
            case 27:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4212f(z);
                parcel2.writeNoException();
                return true;
            case 28:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4215g(z);
                parcel2.writeNoException();
                return true;
            case 29:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4223k();
                parcel2.writeNoException();
                return true;
            case 30:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4200c(C3341h.m128a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 31:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4177a(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 32:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4199c(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 33:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                l = mo4225l();
                parcel2.writeNoException();
                parcel2.writeStringList(l);
                return true;
            case 34:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4218h(z);
                parcel2.writeNoException();
                return true;
            case 35:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4219i(z);
                parcel2.writeNoException();
                return true;
            case 36:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4228m();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 37:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4193b(parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 38:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                readLong = parcel.readLong();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4174a(readLong, z);
                parcel2.writeNoException();
                return true;
            case 39:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                readLong = parcel.readLong();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4189b(readLong, z);
                parcel2.writeNoException();
                return true;
            case 40:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4198c(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 41:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4192b(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            case 42:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4172a(parcel.readByte(), parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            case 43:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4178a(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 44:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4206d(parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 45:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4203d(C3341h.m128a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 46:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4207e(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 47:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4230n();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 48:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4205d(parcel.readInt());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 49:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4210e(parcel.readInt());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 50:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4232o();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 51:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4233p();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 52:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                mo4234q();
                parcel2.writeNoException();
                return true;
            case 53:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4222j(parcel.readInt() != 0);
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 54:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4194b(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 55:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4235r();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 56:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4224k(z);
                parcel2.writeNoException();
                return true;
            case 57:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4236s();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 58:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4226l(z);
                parcel2.writeNoException();
                return true;
            case 59:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                a = mo4213f(parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 60:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                l = mo4237t();
                parcel2.writeNoException();
                parcel2.writeStringList(l);
                return true;
            case 61:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                c = mo4214g(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(c);
                return true;
            case 62:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                String readString = parcel.readString();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4179a(readString, z);
                parcel2.writeNoException();
                return true;
            case 63:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4182a(z, parcel.readString());
                parcel2.writeNoException();
                return true;
            case 64:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4227m(z);
                parcel2.writeNoException();
                return true;
            case 65:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4229n(z);
                parcel2.writeNoException();
                return true;
            case 66:
                parcel.enforceInterface("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4231o(z);
                parcel2.writeNoException();
                return true;
            case 1598968902:
                parcel2.writeString("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
