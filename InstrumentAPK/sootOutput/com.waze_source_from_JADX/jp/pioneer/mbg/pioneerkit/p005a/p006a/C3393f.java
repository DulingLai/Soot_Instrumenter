package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class C3393f implements Creator {
    C3393f() {
    }

    public C3392e m475a(Parcel parcel) {
        C3392e c3392e = new C3392e();
        c3392e.f375a = parcel.readInt();
        c3392e.f376b = parcel.readInt();
        return c3392e;
    }

    public C3392e[] m476a(int i) {
        return new C3392e[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m475a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m476a(i);
    }
}
