package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class C3338e implements Parcelable {
    public static final Creator f214c = new C3339f();
    public int f215a = 0;
    public int f216b = 0;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f215a);
        parcel.writeInt(this.f216b);
    }
}
