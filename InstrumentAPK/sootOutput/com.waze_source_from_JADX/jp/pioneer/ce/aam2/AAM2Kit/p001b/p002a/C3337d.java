package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class C3337d implements Creator {
    C3337d() {
    }

    public C3336c m98a(Parcel parcel) {
        C3336c c3336c = new C3336c();
        c3336c.f203c = parcel.readDouble();
        c3336c.f204d = parcel.readDouble();
        c3336c.f206f = parcel.readDouble();
        c3336c.f210j = parcel.readFloat();
        c3336c.f208h = parcel.readFloat();
        c3336c.f202a = parcel.readByte();
        c3336c.f213m = parcel.readLong();
        c3336c.f212l = parcel.readByte();
        return c3336c;
    }

    public C3336c[] m99a(int i) {
        return new C3336c[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m98a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m99a(i);
    }
}
