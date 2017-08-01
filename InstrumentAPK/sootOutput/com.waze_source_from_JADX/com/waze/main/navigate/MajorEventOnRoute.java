package com.waze.main.navigate;

public class MajorEventOnRoute {
    public int alertRouteId;
    public int alertType;
    public String description;
    public String dueto;
    public String duration;
    public String reported;
    public String thanked;
    public String time;

    public MajorEventOnRoute(int $i0, int $i1, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6) throws  {
        this.alertRouteId = $i0;
        this.alertType = $i1;
        this.description = $r1;
        this.time = $r2;
        this.dueto = $r3;
        this.duration = $r4;
        this.thanked = $r5;
        this.reported = $r6;
    }
}
