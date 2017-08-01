package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

public abstract class C3398k extends Binder implements C3397j {
    public C3398k() {
        attachInterface(this, "jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
    }

    public static C3397j m590b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
        return (queryLocalInterface == null || !(queryLocalInterface instanceof C3397j)) ? new C3399l(iBinder) : (C3397j) queryLocalInterface;
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
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4291a(C3395h.m502a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 2:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4300b(C3395h.m502a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 3:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4287a(parcel.readStrongBinder(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 4:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4286a(parcel.readStrongBinder());
                parcel2.writeNoException();
                return true;
            case 5:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4295a(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 6:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4294a();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 7:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4297b();
                parcel2.writeNoException();
                return true;
            case 8:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                c = mo4306c();
                parcel2.writeNoException();
                parcel2.writeString(c);
                return true;
            case 9:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                i3 = mo4312d();
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 10:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4292a(z);
                parcel2.writeNoException();
                return true;
            case 11:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4319e();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 12:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4284a(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 13:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                i3 = mo4321f();
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 14:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4298b(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 15:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4307c(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 16:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4326g();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 17:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4327h();
                parcel2.writeNoException();
                return true;
            case 18:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4330i();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 19:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4296a(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 20:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4305b(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 21:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4331j();
                parcel2.writeNoException();
                return true;
            case 22:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4301b(z);
                parcel2.writeNoException();
                return true;
            case 23:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4293a(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            case 24:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4311c(z);
                parcel2.writeNoException();
                return true;
            case 25:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4314d(z);
                parcel2.writeNoException();
                return true;
            case 26:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4318e(z);
                parcel2.writeNoException();
                return true;
            case 27:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4322f(z);
                parcel2.writeNoException();
                return true;
            case 28:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4325g(z);
                parcel2.writeNoException();
                return true;
            case 29:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4333k();
                parcel2.writeNoException();
                return true;
            case 30:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4310c(C3395h.m502a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 31:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4288a(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 32:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4309c(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 33:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                l = mo4335l();
                parcel2.writeNoException();
                parcel2.writeStringList(l);
                return true;
            case 34:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4328h(z);
                parcel2.writeNoException();
                return true;
            case 35:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4329i(z);
                parcel2.writeNoException();
                return true;
            case 36:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4337m();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 37:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4303b(parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 38:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                readLong = parcel.readLong();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4285a(readLong, z);
                parcel2.writeNoException();
                return true;
            case 39:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                readLong = parcel.readLong();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4299b(readLong, z);
                parcel2.writeNoException();
                return true;
            case 40:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4308c(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 41:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4302b(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            case 42:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4283a(parcel.readByte(), parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            case 43:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4289a(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 44:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4316d(parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 45:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4313d(C3395h.m502a(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 46:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4317e(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 47:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4338n();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 48:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4315d(parcel.readInt());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 49:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4320e(parcel.readInt());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 50:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4339o();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 51:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4340p();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 52:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                mo4341q();
                parcel2.writeNoException();
                return true;
            case 53:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4332j(parcel.readInt() != 0);
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 54:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4304b(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 55:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4342r();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 56:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4334k(z);
                parcel2.writeNoException();
                return true;
            case 57:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4343s();
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 58:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4336l(z);
                parcel2.writeNoException();
                return true;
            case 59:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                a = mo4323f(parcel.readString());
                parcel2.writeNoException();
                if (a) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 60:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                l = mo4344t();
                parcel2.writeNoException();
                parcel2.writeStringList(l);
                return true;
            case 61:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                c = mo4324g(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(c);
                return true;
            case 62:
                parcel.enforceInterface("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                String readString = parcel.readString();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo4290a(readString, z);
                parcel2.writeNoException();
                return true;
            case 1598968902:
                parcel2.writeString("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
