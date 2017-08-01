package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.IBinder;
import android.os.Parcel;
import android.view.KeyEvent;
import android.view.MotionEvent;

class C3396i implements C3394g {
    private IBinder f377a;

    C3396i(IBinder iBinder) {
        this.f377a = iBinder;
    }

    public void mo4258a() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            this.f377a.transact(11, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4259a(byte b, byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeByte(b);
            obtain.writeByteArray(bArr);
            this.f377a.transact(17, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4260a(int i) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            this.f377a.transact(1, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4261a(int i, int i2) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            obtain.writeInt(i2);
            this.f377a.transact(21, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4262a(KeyEvent keyEvent, boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
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
            this.f377a.transact(3, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4263a(MotionEvent motionEvent) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (motionEvent != null) {
                obtain.writeInt(1);
                motionEvent.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f377a.transact(14, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4264a(MotionEvent motionEvent, boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
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
            this.f377a.transact(2, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4265a(C3388a c3388a) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (c3388a != null) {
                obtain.writeInt(1);
                c3388a.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f377a.transact(10, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4266a(C3390c c3390c) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (c3390c != null) {
                obtain.writeInt(1);
                c3390c.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f377a.transact(7, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4267a(C3392e c3392e) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (c3392e != null) {
                obtain.writeInt(1);
                c3392e.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.f377a.transact(5, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4268a(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f377a.transact(6, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4269a(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeByteArray(bArr);
            this.f377a.transact(13, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public IBinder asBinder() {
        return this.f377a;
    }

    public void mo4270b() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            this.f377a.transact(15, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4271b(int i) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            this.f377a.transact(8, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4272b(MotionEvent motionEvent, boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
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
            this.f377a.transact(4, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4273b(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f377a.transact(9, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4274b(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeByteArray(bArr);
            this.f377a.transact(16, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4275c() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            this.f377a.transact(18, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4276c(int i) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeInt(i);
            this.f377a.transact(12, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4277c(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f377a.transact(20, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4278c(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            obtain.writeByteArray(bArr);
            this.f377a.transact(25, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4279d() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            this.f377a.transact(19, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4280d(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f377a.transact(23, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4281e() {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            this.f377a.transact(22, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public void mo4282e(boolean z) {
        int i = 1;
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("jp.pioneer.mbg.pioneerkit.common.aidl.IExtEventControl");
            if (!z) {
                i = 0;
            }
            obtain.writeInt(i);
            this.f377a.transact(24, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
