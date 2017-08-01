package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class C3392e implements Parcelable {
    public static final Creator f374c = new C3393f();
    public int f375a = 0;
    public int f376b = 0;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f375a);
        parcel.writeInt(this.f376b);
    }
}
