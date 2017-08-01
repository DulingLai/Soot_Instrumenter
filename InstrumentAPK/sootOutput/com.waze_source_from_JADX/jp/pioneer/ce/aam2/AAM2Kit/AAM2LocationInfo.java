package jp.pioneer.ce.aam2.AAM2Kit;

public class AAM2LocationInfo {
    int f170a = -1;
    private double f171b = 0.0d;
    private double f172c = 0.0d;
    private boolean f173d = false;
    private double f174e = 0.0d;
    private boolean f175f = false;
    private float f176g = 0.0f;
    private boolean f177h = false;
    private float f178i = 0.0f;
    private long f179j = 0;
    private boolean f180k = false;
    private boolean f181l = false;

    public void m52a(double d) {
        this.f171b = d;
    }

    public void m53a(float f) {
        this.f176g = f;
        this.f175f = true;
    }

    public void m54a(int i) {
        this.f170a = i;
        this.f181l = true;
    }

    public void m55a(long j) {
        this.f179j = j;
    }

    public void m56a(boolean z) {
        this.f180k = z;
    }

    public void m57b(double d) {
        this.f172c = d;
    }

    public void m58b(float f) {
        float f2 = f;
        while (f2 < 0.0f) {
            f2 += 360.0f;
        }
        while (f2 >= 360.0f) {
            f2 -= 360.0f;
        }
        this.f178i = f2;
        this.f177h = true;
    }

    public void m59c(double d) {
        this.f174e = d;
        this.f173d = true;
    }

    public int getAccuracy() {
        return this.f170a;
    }

    public double getAltitude() {
        return this.f174e;
    }

    public float getBearing() {
        return this.f178i;
    }

    public double getLatitude() {
        return this.f171b;
    }

    public double getLongitude() {
        return this.f172c;
    }

    public float getSpeed() {
        return this.f176g;
    }

    public long getTime() {
        return this.f179j;
    }

    public boolean isRealGpsTime() {
        return this.f180k;
    }
}
