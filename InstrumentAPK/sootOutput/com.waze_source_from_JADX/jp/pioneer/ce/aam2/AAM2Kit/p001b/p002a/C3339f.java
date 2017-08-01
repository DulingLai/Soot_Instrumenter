package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class C3339f implements Creator {
    C3339f() {
    }

    public C3338e m100a(Parcel parcel) {
        C3338e c3338e = new C3338e();
        c3338e.f215a = parcel.readInt();
        c3338e.f216b = parcel.readInt();
        return c3338e;
    }

    public C3338e[] m101a(int i) {
        return new C3338e[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m100a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m101a(i);
    }
}
