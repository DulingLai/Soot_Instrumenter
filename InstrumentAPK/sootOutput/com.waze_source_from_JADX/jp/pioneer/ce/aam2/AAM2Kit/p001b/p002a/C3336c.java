package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class C3336c implements Parcelable {
    public static final Creator f201b = new C3337d();
    byte f202a = (byte) 0;
    private double f203c = 0.0d;
    private double f204d = 0.0d;
    private boolean f205e = false;
    private double f206f = 0.0d;
    private boolean f207g = false;
    private float f208h = 0.0f;
    private boolean f209i = false;
    private float f210j = 0.0f;
    private boolean f211k = false;
    private byte f212l = (byte) 0;
    private long f213m = 0;

    public long m90a() {
        return this.f213m;
    }

    public byte m91b() {
        return this.f202a;
    }

    public double m92c() {
        return this.f203c;
    }

    public double m93d() {
        return this.f204d;
    }

    public int describeContents() {
        return 0;
    }

    public double m94e() {
        return this.f206f;
    }

    public float m95f() {
        return this.f208h;
    }

    public float m96g() {
        return this.f210j;
    }

    public byte m97h() {
        return this.f212l;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.f203c);
        parcel.writeDouble(this.f204d);
        parcel.writeDouble(this.f206f);
        parcel.writeFloat(this.f210j);
        parcel.writeFloat(this.f208h);
        parcel.writeByte(this.f202a);
        parcel.writeLong(this.f213m);
        parcel.writeByte(this.f212l);
    }
}
