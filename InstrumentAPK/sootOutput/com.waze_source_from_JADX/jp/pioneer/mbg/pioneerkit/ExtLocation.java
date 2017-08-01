package jp.pioneer.mbg.pioneerkit;

public class ExtLocation {
    int f342a = -1;
    private double f343b = 0.0d;
    private double f344c = 0.0d;
    private boolean f345d = false;
    private double f346e = 0.0d;
    private boolean f347f = false;
    private float f348g = 0.0f;
    private boolean f349h = false;
    private float f350i = 0.0f;
    private long f351j = 0;
    private boolean f352k = false;
    private boolean f353l = false;

    public int getAccuracy() {
        return this.f342a;
    }

    public double getAltitude() {
        return this.f346e;
    }

    public float getBearing() {
        return this.f350i;
    }

    public double getLatitude() {
        return this.f343b;
    }

    public double getLongitude() {
        return this.f344c;
    }

    public float getSpeed() {
        return this.f348g;
    }

    public long getTime() {
        return this.f351j;
    }

    public boolean isRealGpsTime() {
        return this.f352k;
    }

    public void setAccuracy(int i) {
        this.f342a = i;
        this.f353l = true;
    }

    public void setAltitude(double d) {
        this.f346e = d;
        this.f345d = true;
    }

    public void setBearing(float f) {
        float f2 = f;
        while (f2 < 0.0f) {
            f2 += 360.0f;
        }
        while (f2 >= 360.0f) {
            f2 -= 360.0f;
        }
        this.f350i = f2;
        this.f349h = true;
    }

    public void setLatitude(double d) {
        this.f343b = d;
    }

    public void setLongitude(double d) {
        this.f344c = d;
    }

    public void setRealGpsTime(boolean z) {
        this.f352k = z;
    }

    public void setSpeed(float f) {
        this.f348g = f;
        this.f347f = true;
    }

    public void setTime(long j) {
        this.f351j = j;
    }
}
