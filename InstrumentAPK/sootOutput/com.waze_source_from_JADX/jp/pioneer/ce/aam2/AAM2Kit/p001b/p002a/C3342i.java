package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.IBinder;
import android.os.Parcel;
import android.view.KeyEvent;
import android.view.MotionEvent;

class C3342i implements C3340g {
    private IBinder f217a;

    C3342i(IBinder iBinder) {
        this.f217a = iBinder;
    }

    public void mo4146a() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            this.f217a.transact(11, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4147a(byte b, byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeByte(b);
            obtain.writeByteArray(bArr);
            this.f217a.transact(17, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4148a(int i) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            this.f217a.transact(1, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4149a(int i, int i2) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            obtain.writeInt(i2);
            this.f217a.transact(21, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4150a(KeyEvent keyEvent, boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (keyEvent != null) {
                obtain.writeInt(1);
                keyEvent.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(3, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4151a(MotionEvent motionEvent) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (motionEvent != null) {
                obtain.writeInt(1);
                motionEvent.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f217a.transact(14, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4152a(MotionEvent motionEvent, boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (motionEvent != null) {
                obtain.writeInt(1);
                motionEvent.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(2, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4153a(C3334a c3334a) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (c3334a != null) {
                obtain.writeInt(1);
                c3334a.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f217a.transact(10, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4154a(C3336c c3336c) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (c3336c != null) {
                obtain.writeInt(1);
                c3336c.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f217a.transact(7, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4155a(C3338e c3338e) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (c3338e != null) {
                obtain.writeInt(1);
                c3338e.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f217a.transact(5, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4156a(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(6, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4157a(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeByteArray(bArr);
            this.f217a.transact(13, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public IBinder asBinder() {
        return this.f217a;
    }

    public void mo4158b() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            this.f217a.transact(15, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4159b(int i) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            this.f217a.transact(8, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4160b(MotionEvent motionEvent, boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (motionEvent != null) {
                obtain.writeInt(1);
                motionEvent.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(4, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4161b(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(9, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4162b(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeByteArray(bArr);
            this.f217a.transact(16, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4163c() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            this.f217a.transact(18, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4164c(int i) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            this.f217a.transact(12, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4165c(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(20, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4166c(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            obtain.writeByteArray(bArr);
            this.f217a.transact(25, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4167d() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            this.f217a.transact(19, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4168d(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(23, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4169e() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            this.f217a.transact(22, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4170e(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f217a.transact(24, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public int mo4171f() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.ce.aam2.AAM2Kit.common.aidl.IExtEventControl");
            this.f217a.transact(26, obtain, obtain2, 0);
            obtain2.readException();
            int readInt = obtain2.readInt();
            return readInt;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
