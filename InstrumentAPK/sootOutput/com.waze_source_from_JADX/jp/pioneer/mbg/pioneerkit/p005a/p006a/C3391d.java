package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class C3391d implements Creator {
    C3391d() {
    }

    public C3390c m473a(Parcel parcel) {
        C3390c c3390c = new C3390c();
        c3390c.f363c = parcel.readDouble();
        c3390c.f364d = parcel.readDouble();
        c3390c.f366f = parcel.readDouble();
        c3390c.f370j = parcel.readFloat();
        c3390c.f368h = parcel.readFloat();
        c3390c.f362a = parcel.readByte();
        c3390c.f373m = parcel.readLong();
        c3390c.f372l = parcel.readByte();
        return c3390c;
    }

    public C3390c[] m474a(int i) {
        return new C3390c[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m473a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m474a(i);
    }
}
