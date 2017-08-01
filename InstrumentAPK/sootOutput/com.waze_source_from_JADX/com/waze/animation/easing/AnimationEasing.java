package com.waze.animation.easing;

public interface AnimationEasing {
    double easeIn(double d, double d2, double d3, double d4) throws ;

    double easeInOut(double d, double d2, double d3, double d4) throws ;

    double easeOut(double d, double d2, double d3, double d4) throws ;
}
