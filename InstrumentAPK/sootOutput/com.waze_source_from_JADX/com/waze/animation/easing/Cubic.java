package com.waze.animation.easing;

import com.waze.FriendsBarFragment;

public class Cubic implements AnimationEasing {
    public double easeOut(double $d3, double $d0, double $d1, double $d2) throws  {
        $d3 = ($d3 / $d2) - FriendsBarFragment.END_LOCATION_POSITION;
        return (((($d3 * $d3) * $d3) + FriendsBarFragment.END_LOCATION_POSITION) * $d1) + $d0;
    }

    public double easeIn(double $d3, double $d0, double $d1, double $d2) throws  {
        $d3 /= $d2;
        return ((($d1 * $d3) * $d3) * $d3) + $d0;
    }

    public double easeInOut(double $d3, double $d0, double $d1, double $d2) throws  {
        $d3 /= $d2 / 2.0d;
        if ($d3 < FriendsBarFragment.END_LOCATION_POSITION) {
            return (((($d1 / 2.0d) * $d3) * $d3) * $d3) + $d0;
        }
        $d3 -= 2.0d;
        return (($d1 / 2.0d) * ((($d3 * $d3) * $d3) + 2.0d)) + $d0;
    }
}
