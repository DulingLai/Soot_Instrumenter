package com.waze.animation.easing;

public class Bounce implements AnimationEasing {
    public double easeOut(double $d3, double $d0, double $d1, double $d2) throws  {
        $d3 /= $d2;
        if ($d3 < 0.36363636363636365d) {
            return (((7.5625d * $d3) * $d3) * $d1) + $d0;
        }
        if ($d3 < 0.7272727272727273d) {
            $d3 -= 0.5454545454545454d;
            return ((((7.5625d * $d3) * $d3) + 0.75d) * $d1) + $d0;
        } else if ($d3 < 0.9090909090909091d) {
            $d3 -= 0.8181818181818182d;
            return ((((7.5625d * $d3) * $d3) + 0.9375d) * $d1) + $d0;
        } else {
            $d3 -= 0.9545454545454546d;
            return ((((7.5625d * $d3) * $d3) + 0.984375d) * $d1) + $d0;
        }
    }

    public double easeIn(double $d0, double $d1, double $d2, double $d3) throws  {
        return ($d2 - easeOut($d3 - $d0, 0.0d, $d2, $d3)) + $d1;
    }

    public double easeInOut(double $d0, double $d1, double $d2, double $d3) throws  {
        if ($d0 < $d3 / 2.0d) {
            double $d02 = easeIn($d0 * 2.0d, 0.0d, $d2, $d3) * 0.5d;
            $d0 = $d02;
            return $d02 + $d1;
        }
        $d02 = (easeOut((2.0d * $d0) - $d3, 0.0d, $d2, $d3) * 0.5d) + (0.5d * $d2);
        $d0 = $d02;
        return $d02 + $d1;
    }
}
