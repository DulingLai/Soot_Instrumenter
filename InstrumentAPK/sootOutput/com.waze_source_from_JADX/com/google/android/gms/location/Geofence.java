package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.location.internal.ParcelableGeofence;

/* compiled from: dalvik_source_com.waze.apk */
public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private int auD = 0;
        private long auE = Long.MIN_VALUE;
        private short auF = (short) -1;
        private double auG;
        private double auH;
        private float auI;
        private int auJ = 0;
        private int auK = -1;
        private String zzbvk = null;

        public Geofence build() throws  {
            if (this.zzbvk == null) {
                throw new IllegalArgumentException("Request ID not set.");
            } else if (this.auD == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            } else if ((this.auD & 4) != 0 && this.auK < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            } else if (this.auE == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            } else if (this.auF == (short) -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            } else if (this.auJ < 0) {
                throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            } else {
                return new ParcelableGeofence(this.zzbvk, this.auD, (short) 1, this.auG, this.auH, this.auI, this.auE, this.auJ, this.auK);
            }
        }

        public Builder setCircularRegion(double $d0, double $d1, float $f0) throws  {
            this.auF = (short) 1;
            this.auG = $d0;
            this.auH = $d1;
            this.auI = $f0;
            return this;
        }

        public Builder setExpirationDuration(long $l0) throws  {
            if ($l0 < 0) {
                this.auE = -1;
                return this;
            }
            this.auE = SystemClock.elapsedRealtime() + $l0;
            return this;
        }

        public Builder setLoiteringDelay(int $i0) throws  {
            this.auK = $i0;
            return this;
        }

        public Builder setNotificationResponsiveness(int $i0) throws  {
            this.auJ = $i0;
            return this;
        }

        public Builder setRequestId(String $r1) throws  {
            this.zzbvk = $r1;
            return this;
        }

        public Builder setTransitionTypes(int $i0) throws  {
            this.auD = $i0;
            return this;
        }
    }

    String getRequestId() throws ;
}
