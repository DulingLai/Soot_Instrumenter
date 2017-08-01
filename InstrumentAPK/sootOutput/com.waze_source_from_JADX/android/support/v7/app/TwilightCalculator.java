package android.support.v7.app;

import com.waze.FriendsBarFragment;
import com.waze.LayoutManager;

class TwilightCalculator {
    private static final float ALTIDUTE_CORRECTION_CIVIL_TWILIGHT = -0.10471976f;
    private static final float C1 = 0.0334196f;
    private static final float C2 = 3.49066E-4f;
    private static final float C3 = 5.236E-6f;
    public static final int DAY = 0;
    private static final float DEGREES_TO_RADIANS = 0.017453292f;
    private static final float J0 = 9.0E-4f;
    public static final int NIGHT = 1;
    private static final float OBLIQUITY = 0.4092797f;
    private static final long UTC_2000 = 946728000000L;
    private static TwilightCalculator sInstance;
    public int state;
    public long sunrise;
    public long sunset;

    TwilightCalculator() throws  {
    }

    static TwilightCalculator getInstance() throws  {
        if (sInstance == null) {
            sInstance = new TwilightCalculator();
        }
        return sInstance;
    }

    public void calculateTwilight(long $l0, double $d0, double $d1) throws  {
        float $f0 = ((float) ($l0 - UTC_2000)) / 8.64E7f;
        float $f1 = 6.24006f + (0.01720197f * $f0);
        double $d2 = (1.796593063d + (((((double) $f1) + (0.03341960161924362d * Math.sin((double) $f1))) + (3.4906598739326E-4d * Math.sin((double) (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * $f1)))) + (5.236000106378924E-6d * Math.sin((double) (3.0f * $f1))))) + 3.141592653589793d;
        $d1 = (-$d1) / 360.0d;
        $d1 = ((((double) (J0 + ((float) Math.round(((double) ($f0 - J0)) - $d1)))) + $d1) + (0.0053d * Math.sin((double) $f1))) + (-0.0069d * Math.sin(2.0d * $d2));
        $d2 = Math.asin(Math.sin($d2) * Math.sin(0.4092797040939331d));
        $d0 *= 0.01745329238474369d;
        double $d3 = Math.sin(-0.10471975803375244d);
        double $d4 = Math.sin($d0) * Math.sin($d2);
        double d = $d4;
        $d0 = ($d3 - $d4) / (Math.cos($d0) * Math.cos($d2));
        if ($d0 >= FriendsBarFragment.END_LOCATION_POSITION) {
            this.state = 1;
            this.sunset = -1;
            this.sunrise = -1;
        } else if ($d0 <= -1.0d) {
            this.state = 0;
            this.sunset = -1;
            this.sunrise = -1;
        } else {
            $d4 = Math.acos($d0) / 6.283185307179586d;
            $d0 = $d4;
            $f1 = (float) $d4;
            $d4 = (double) $f1;
            $d4 = ($d4 + $d1) * 8.64E7d;
            $d0 = $d4;
            this.sunset = Math.round($d4) + UTC_2000;
            $d4 = ($d1 - ((double) $f1)) * 8.64E7d;
            $d0 = $d4;
            this.sunrise = Math.round($d4) + UTC_2000;
            if (this.sunrise >= $l0 || this.sunset <= $l0) {
                this.state = 1;
            } else {
                this.state = null;
            }
        }
    }
}
