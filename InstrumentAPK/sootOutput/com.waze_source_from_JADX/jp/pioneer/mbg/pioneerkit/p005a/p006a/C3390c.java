package jp.pioneer.mbg.pioneerkit.p005a.p006a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class C3390c implements Parcelable {
    public static final Creator f361b = new C3391d();
    byte f362a = (byte) 0;
    private double f363c = 0.0d;
    private double f364d = 0.0d;
    private boolean f365e = false;
    private double f366f = 0.0d;
    private boolean f367g = false;
    private float f368h = 0.0f;
    private boolean f369i = false;
    private float f370j = 0.0f;
    private boolean f371k = false;
    private byte f372l = (byte) 0;
    private long f373m = 0;

    public long m465a() {
        return this.f373m;
    }

    public byte m466b() {
        return this.f362a;
    }

    public double m467c() {
        return this.f363c;
    }

    public double m468d() {
        return this.f364d;
    }

    public int describeContents() {
        return 0;
    }

    public double m469e() {
        return this.f366f;
    }

    public float m470f() {
        return this.f368h;
    }

    public float m471g() {
        return this.f370j;
    }

    public byte m472h() {
        return this.f372l;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.f363c);
        parcel.writeDouble(this.f364d);
        parcel.writeDouble(this.f366f);
        parcel.writeFloat(this.f370j);
        parcel.writeFloat(this.f368h);
        parcel.writeByte(this.f362a);
        parcel.writeLong(this.f373m);
        parcel.writeByte(this.f372l);
    }
}
