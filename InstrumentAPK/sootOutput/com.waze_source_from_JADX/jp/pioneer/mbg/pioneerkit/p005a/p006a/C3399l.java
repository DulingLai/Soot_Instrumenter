package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.IBinder;
import android.os.Parcel;
import java.util.List;

class C3399l implements C3397j {
    private IBinder f378a;

    C3399l(IBinder iBinder) {
        this.f378a = iBinder;
    }

    public void mo4283a(byte b, byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeByte(b);
            obtain.writeByteArray(bArr);
            this.f378a.transact(42, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4284a(int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f378a.transact(12, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4285a(long j, boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeLong(j);
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(38, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4286a(IBinder iBinder) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeStrongBinder(iBinder);
            this.f378a.transact(4, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4287a(IBinder iBinder, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeStrongBinder(iBinder);
            obtain.writeString(str);
            this.f378a.transact(3, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4288a(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f378a.transact(31, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4289a(String str, int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeInt(i);
            this.f378a.transact(43, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4290a(String str, boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(62, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4291a(C3394g c3394g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3394g != null ? c3394g.asBinder() : null);
            obtain.writeString(str);
            this.f378a.transact(1, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4292a(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(10, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4293a(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeByteArray(bArr);
            this.f378a.transact(23, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4294a() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(6, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4295a(String str, String str2) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            this.f378a.transact(5, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4296a(String str, String str2, String str3) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            this.f378a.transact(19, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public IBinder asBinder() {
        return this.f378a;
    }

    public void mo4297b() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(7, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4298b(int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f378a.transact(14, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4299b(long j, boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeLong(j);
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(39, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4300b(C3394g c3394g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3394g != null ? c3394g.asBinder() : null);
            obtain.writeString(str);
            this.f378a.transact(2, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4301b(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(22, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4302b(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeByteArray(bArr);
            this.f378a.transact(41, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4303b(String str) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f378a.transact(37, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4304b(String str, String str2) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            this.f378a.transact(54, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4305b(String str, String str2, String str3) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            this.f378a.transact(20, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public String mo4306c() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(8, obtain, obtain2, 0);
            obtain2.readException();
            String readString = obtain2.readString();
            return readString;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4307c(int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f378a.transact(15, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4308c(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f378a.transact(40, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4309c(String str, String str2, String str3) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            this.f378a.transact(32, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4310c(C3394g c3394g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3394g != null ? c3394g.asBinder() : null);
            obtain.writeString(str);
            this.f378a.transact(30, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4311c(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(24, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public int mo4312d() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(9, obtain, obtain2, 0);
            obtain2.readException();
            int readInt = obtain2.readInt();
            return readInt;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4313d(C3394g c3394g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3394g != null ? c3394g.asBinder() : null);
            obtain.writeString(str);
            this.f378a.transact(45, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4314d(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(25, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4315d(int i) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f378a.transact(48, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4316d(String str) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f378a.transact(44, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4317e(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f378a.transact(46, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4318e(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(26, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4319e() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(11, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4320e(int i) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f378a.transact(49, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public int mo4321f() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(13, obtain, obtain2, 0);
            obtain2.readException();
            int readInt = obtain2.readInt();
            return readInt;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4322f(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(27, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4323f(String str) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f378a.transact(59, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public String mo4324g(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f378a.transact(61, obtain, obtain2, 0);
            obtain2.readException();
            String readString = obtain2.readString();
            return readString;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4325g(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(28, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4326g() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(16, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4327h() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(17, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4328h(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(34, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4329i(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(35, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4330i() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(18, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4331j() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(21, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4332j(boolean z) {
        boolean z2 = true;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            obtain.writeInt(z ? 1 : 0);
            this.f378a.transact(53, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() == 0) {
                z2 = false;
            }
            obtain2.recycle();
            obtain.recycle();
            return z2;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4333k() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(29, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4334k(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(56, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public List mo4335l() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(33, obtain, obtain2, 0);
            obtain2.readException();
            List createStringArrayList = obtain2.createStringArrayList();
            return createStringArrayList;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4336l(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f378a.transact(58, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4337m() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(36, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4338n() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(47, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4339o() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(50, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4340p() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(51, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4341q() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(52, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4342r() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(55, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4343s() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(57, obtain, obtain2, 0);
            obtain2.readException();
            if (obtain2.readInt() != 0) {
                z = true;
            }
            obtain2.recycle();
            obtain.recycle();
            return z;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public List mo4344t() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.ISppControl");
            this.f378a.transact(60, obtain, obtain2, 0);
            obtain2.readException();
            List createStringArrayList = obtain2.createStringArrayList();
            return createStringArrayList;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
