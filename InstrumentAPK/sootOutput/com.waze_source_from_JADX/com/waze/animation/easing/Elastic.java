package com.waze.animation.easing;

import com.waze.FriendsBarFragment;

public class Elastic implements AnimationEasing {
    private double mAmplitude = 0.0d;
    private double mPeriod = 0.0d;

    public Elastic(double $d0, double $d1) throws  {
        this.mAmplitude = $d0;
        this.mPeriod = $d1;
    }

    public double easeIn(double $d0, double $d1, double $d2, double $d3) throws  {
        return easeIn($d0, $d1, $d2, $d3, this.mAmplitude, this.mPeriod);
    }

    public double easeIn(double $d2, double $d3, double $d0, double $d1, double $d4, double $d5) throws  {
        if ($d2 == 0.0d) {
            return $d3;
        }
        $d2 /= $d1;
        if ($d2 == FriendsBarFragment.END_LOCATION_POSITION) {
            return $d3 + $d0;
        }
        if ($d5 <= 0.0d) {
            $d5 = $d1 * 0.3d;
        }
        if ($d4 <= 0.0d || $d4 < Math.abs($d0)) {
            $d4 = $d0;
            $d0 = $d5 / 4.0d;
        } else {
            $d0 = ($d5 / 6.283185307179586d) * Math.asin($d0 / $d4);
        }
        double $d6 = $d2 - FriendsBarFragment.END_LOCATION_POSITION;
        return $d3 + (-((Math.pow(2.0d, 10.0d * $d6) * $d4) * Math.sin(((($d6 * $d1) - $d0) * 6.283185307179586d) / $d5)));
    }

    public double easeOut(double $d0, double $d1, double $d2, double $d3) throws  {
        return easeOut($d0, $d1, $d2, $d3, this.mAmplitude, this.mPeriod);
    }

    public double easeOut(double $d2, double $d3, double $d0, double $d1, double $d4, double $d5) throws  {
        if ($d2 == 0.0d) {
            return $d3;
        }
        $d2 /= $d1;
        if ($d2 == FriendsBarFragment.END_LOCATION_POSITION) {
            return $d3 + $d0;
        }
        double $d6;
        if ($d5 <= 0.0d) {
            $d5 = $d1 * 0.3d;
        }
        if ($d4 <= 0.0d || $d4 < Math.abs($d0)) {
            $d4 = $d0;
            $d6 = $d5 / 4.0d;
        } else {
            $d6 = ($d5 / 6.283185307179586d) * Math.asin($d0 / $d4);
        }
        return $d3 + (((Math.pow(2.0d, -10.0d * $d2) * $d4) * Math.sin(((($d2 * $d1) - $d6) * 6.283185307179586d) / $d5)) + $d0);
    }

    public double easeInOut(double $d0, double $d1, double $d2, double $d3) throws  {
        return easeInOut($d0, $d1, $d2, $d3, this.mAmplitude, this.mPeriod);
    }

    public double easeInOut(double $d2, double $d3, double $d0, double $d1, double $d4, double $d5) throws  {
        if ($d2 == 0.0d) {
            return $d3;
        }
        double $d6 = $d2 / ($d1 / 2.0d);
        if ($d6 == 2.0d) {
            return $d3 + $d0;
        }
        if ($d5 <= 0.0d) {
            $d5 = $d1 * 0.44999999999999996d;
        }
        if ($d4 <= 0.0d || $d4 < Math.abs($d0)) {
            $d4 = $d0;
            $d2 = $d5 / 4.0d;
        } else {
            $d2 = ($d5 / 6.283185307179586d) * Math.asin($d0 / $d4);
        }
        if ($d6 < FriendsBarFragment.END_LOCATION_POSITION) {
            $d6 -= FriendsBarFragment.END_LOCATION_POSITION;
            return $d3 + (-0.5d * ((Math.pow(2.0d, 10.0d * $d6) * $d4) * Math.sin(((($d6 * $d1) - $d2) * 6.283185307179586d) / $d5)));
        }
        $d6 -= FriendsBarFragment.END_LOCATION_POSITION;
        return $d3 + ((((Math.pow(2.0d, -10.0d * $d6) * $d4) * Math.sin(((($d6 * $d1) - $d2) * 6.283185307179586d) / $d5)) * 0.5d) + $d0);
    }
}
