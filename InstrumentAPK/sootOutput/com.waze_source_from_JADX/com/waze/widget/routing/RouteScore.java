package com.waze.widget.routing;

public class RouteScore {
    public static RouteScoreType getScore(int time, int avegare) {
        if (((double) time) > ((double) avegare) * 1.1d) {
            return RouteScoreType.ROUTE_BAD;
        }
        if (time < avegare) {
            return RouteScoreType.ROUTE_GOOD;
        }
        return RouteScoreType.ROUTE_OK;
    }
}
