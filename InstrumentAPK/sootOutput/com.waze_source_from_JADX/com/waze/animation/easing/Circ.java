package com.waze.animation.easing;

import com.waze.FriendsBarFragment;

public class Circ implements AnimationEasing {
    public double easeOut(double $d3, double $d0, double $d1, double $d2) throws  {
        $d3 = ($d3 / $d2) - FriendsBarFragment.END_LOCATION_POSITION;
        return (Math.sqrt(FriendsBarFragment.END_LOCATION_POSITION - ($d3 * $d3)) * $d1) + $d0;
    }

    public double easeIn(double $d3, double $d0, double $d1, double $d2) throws  {
        $d3 /= $d2;
        return ((-$d1) * (Math.sqrt(FriendsBarFragment.END_LOCATION_POSITION - ($d3 * $d3)) - FriendsBarFragment.END_LOCATION_POSITION)) + $d0;
    }

    public double easeInOut(double $d3, double $d0, double $d1, double $d2) throws  {
        $d2 = $d3 / ($d2 / 2.0d);
        if ($d2 < FriendsBarFragment.END_LOCATION_POSITION) {
            return (((-$d1) / 2.0d) * (Math.sqrt(FriendsBarFragment.END_LOCATION_POSITION - ($d2 * $d2)) - FriendsBarFragment.END_LOCATION_POSITION)) + $d0;
        }
        $d3 = $d1 / 2.0d;
        $d1 = $d2 - 2.0d;
        return ($d3 * (Math.sqrt(FriendsBarFragment.END_LOCATION_POSITION - ($d1 * $d1)) + FriendsBarFragment.END_LOCATION_POSITION)) + $d0;
    }
}
