package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.IBinder;
import android.os.Parcel;
import java.util.List;

class C3345l implements C3343j {
    private IBinder f218a;

    C3345l(IBinder iBinder) {
        this.f218a = iBinder;
    }

    public void mo4172a(byte b, byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeByte(b);
            obtain.writeByteArray(bArr);
            this.f218a.transact(42, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4173a(int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f218a.transact(12, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4174a(long j, boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeLong(j);
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(38, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4175a(IBinder iBinder) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeStrongBinder(iBinder);
            this.f218a.transact(4, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4176a(IBinder iBinder, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeStrongBinder(iBinder);
            obtain.writeString(str);
            this.f218a.transact(3, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4177a(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f218a.transact(31, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4178a(String str, int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeInt(i);
            this.f218a.transact(43, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4179a(String str, boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(62, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4180a(C3340g c3340g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3340g != null ? c3340g.asBinder() : null);
            obtain.writeString(str);
            this.f218a.transact(1, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4181a(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(10, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4182a(boolean z, String str) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            obtain.writeString(str);
            this.f218a.transact(63, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4183a(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeByteArray(bArr);
            this.f218a.transact(23, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4184a() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(6, obtain, obtain2, 0);
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

    public boolean mo4185a(String str, String str2) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            this.f218a.transact(5, obtain, obtain2, 0);
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

    public boolean mo4186a(String str, String str2, String str3) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            this.f218a.transact(19, obtain, obtain2, 0);
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
        return this.f218a;
    }

    public void mo4187b() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(7, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4188b(int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f218a.transact(14, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4189b(long j, boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeLong(j);
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(39, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4190b(C3340g c3340g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3340g != null ? c3340g.asBinder() : null);
            obtain.writeString(str);
            this.f218a.transact(2, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4191b(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(22, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4192b(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeByteArray(bArr);
            this.f218a.transact(41, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4193b(String str) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f218a.transact(37, obtain, obtain2, 0);
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

    public boolean mo4194b(String str, String str2) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            this.f218a.transact(54, obtain, obtain2, 0);
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

    public boolean mo4195b(String str, String str2, String str3) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            this.f218a.transact(20, obtain, obtain2, 0);
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

    public String mo4196c() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(8, obtain, obtain2, 0);
            obtain2.readException();
            String readString = obtain2.readString();
            return readString;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4197c(int i) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f218a.transact(15, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4198c(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f218a.transact(40, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4199c(String str, String str2, String str3) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            this.f218a.transact(32, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4200c(C3340g c3340g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3340g != null ? c3340g.asBinder() : null);
            obtain.writeString(str);
            this.f218a.transact(30, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4201c(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(24, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public int mo4202d() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(9, obtain, obtain2, 0);
            obtain2.readException();
            int readInt = obtain2.readInt();
            return readInt;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4203d(C3340g c3340g, String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeStrongBinder(c3340g != null ? c3340g.asBinder() : null);
            obtain.writeString(str);
            this.f218a.transact(45, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4204d(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(25, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4205d(int i) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f218a.transact(48, obtain, obtain2, 0);
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

    public boolean mo4206d(String str) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f218a.transact(44, obtain, obtain2, 0);
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

    public void mo4207e(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f218a.transact(46, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4208e(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(26, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4209e() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(11, obtain, obtain2, 0);
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

    public boolean mo4210e(int i) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeInt(i);
            this.f218a.transact(49, obtain, obtain2, 0);
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

    public int mo4211f() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(13, obtain, obtain2, 0);
            obtain2.readException();
            int readInt = obtain2.readInt();
            return readInt;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4212f(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(27, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4213f(String str) {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f218a.transact(59, obtain, obtain2, 0);
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

    public String mo4214g(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeString(str);
            this.f218a.transact(61, obtain, obtain2, 0);
            obtain2.readException();
            String readString = obtain2.readString();
            return readString;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4215g(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(28, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4216g() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(16, obtain, obtain2, 0);
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

    public void mo4217h() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(17, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4218h(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(34, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4219i(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(35, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4220i() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(18, obtain, obtain2, 0);
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

    public void mo4221j() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(21, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4222j(boolean z) {
        boolean z2 = true;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            obtain.writeInt(z ? 1 : 0);
            this.f218a.transact(53, obtain, obtain2, 0);
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

    public void mo4223k() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(29, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4224k(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(56, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public List mo4225l() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(33, obtain, obtain2, 0);
            obtain2.readException();
            List createStringArrayList = obtain2.createStringArrayList();
            return createStringArrayList;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4226l(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(58, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public void mo4227m(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(64, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4228m() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(36, obtain, obtain2, 0);
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

    public void mo4229n(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(65, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4230n() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(47, obtain, obtain2, 0);
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

    public void mo4231o(boolean z) {
        int i = 0;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            if (z) {
                i = 1;
            }
            obtain.writeInt(i);
            this.f218a.transact(66, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4232o() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(50, obtain, obtain2, 0);
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

    public boolean mo4233p() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(51, obtain, obtain2, 0);
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

    public void mo4234q() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(52, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public boolean mo4235r() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(55, obtain, obtain2, 0);
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

    public boolean mo4236s() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(57, obtain, obtain2, 0);
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

    public List mo4237t() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.ISppControl");
            this.f218a.transact(60, obtain, obtain2, 0);
            obtain2.readException();
            List createStringArrayList = obtain2.createStringArrayList();
            return createStringArrayList;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
