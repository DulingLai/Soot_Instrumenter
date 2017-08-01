package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class C3389b implements Creator {
    C3389b() {
    }

    public C3388a m456a(Parcel parcel) {
        C3388a c3388a = new C3388a();
        c3388a.f355a = parcel.readInt();
        c3388a.f356b = parcel.readInt();
        c3388a.f357c = parcel.readInt();
        c3388a.f358d = parcel.readInt();
        c3388a.f359f = parcel.readInt();
        c3388a.f360g = parcel.readInt();
        return c3388a;
    }

    public C3388a[] m457a(int i) {
        return new C3388a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m456a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m457a(i);
    }
}
