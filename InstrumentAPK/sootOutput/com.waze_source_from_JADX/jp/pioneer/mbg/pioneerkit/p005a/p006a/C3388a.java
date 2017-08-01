package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class C3388a implements Parcelable {
    public static final Creator f354e = new C3389b();
    public int f355a = 0;
    public int f356b;
    public int f357c;
    public int f358d;
    private int f359f = 0;
    private int f360g = 0;

    public int m450a() {
        return this.f355a;
    }

    public int m451b() {
        return this.f356b;
    }

    public int m452c() {
        return this.f357c;
    }

    public int m453d() {
        return this.f358d;
    }

    public int describeContents() {
        return 0;
    }

    public int m454e() {
        return this.f359f;
    }

    public int m455f() {
        return this.f360g;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f355a);
        parcel.writeInt(this.f356b);
        parcel.writeInt(this.f357c);
        parcel.writeInt(this.f358d);
        parcel.writeInt(this.f359f);
        parcel.writeInt(this.f360g);
    }
}
