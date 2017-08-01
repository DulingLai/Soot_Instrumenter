package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class C3334a implements Parcelable {
    public static final Creator f194e = new C3335b();
    public int f195a = 0;
    public int f196b;
    public int f197c;
    public int f198d;
    private int f199f = 0;
    private int f200g = 0;

    public int m75a() {
        return this.f195a;
    }

    public int m76b() {
        return this.f196b;
    }

    public int m77c() {
        return this.f197c;
    }

    public int m78d() {
        return this.f198d;
    }

    public int describeContents() {
        return 0;
    }

    public int m79e() {
        return this.f199f;
    }

    public int m80f() {
        return this.f200g;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f195a);
        parcel.writeInt(this.f196b);
        parcel.writeInt(this.f197c);
        parcel.writeInt(this.f198d);
        parcel.writeInt(this.f199f);
        parcel.writeInt(this.f200g);
    }
}
