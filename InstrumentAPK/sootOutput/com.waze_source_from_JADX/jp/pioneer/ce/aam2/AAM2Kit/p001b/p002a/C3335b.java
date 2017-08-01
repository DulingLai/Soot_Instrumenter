package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class C3335b implements Creator {
    C3335b() {
    }

    public C3334a m81a(Parcel parcel) {
        C3334a c3334a = new C3334a();
        c3334a.f195a = parcel.readInt();
        c3334a.f196b = parcel.readInt();
        c3334a.f197c = parcel.readInt();
        c3334a.f198d = parcel.readInt();
        c3334a.f199f = parcel.readInt();
        c3334a.f200g = parcel.readInt();
        return c3334a;
    }

    public C3334a[] m82a(int i) {
        return new C3334a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m81a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m82a(i);
    }
}
