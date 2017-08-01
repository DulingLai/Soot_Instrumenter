package com.waze.main.navigate;

public class EventOnRoute {
    public int alertId;
    public int alertRouteId;
    public int alertSubtype;
    public int alertType;
    public int end;
    public int percentage;
    public int severity;
    public int start;

    public EventOnRoute(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7) throws  {
        this.alertId = $i0;
        this.alertRouteId = $i1;
        this.alertType = $i2;
        this.alertSubtype = $i3;
        this.severity = $i4;
        this.start = $i5;
        this.end = $i6;
        this.percentage = $i7;
    }
}
