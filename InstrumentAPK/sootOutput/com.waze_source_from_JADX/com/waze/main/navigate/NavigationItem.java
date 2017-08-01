package com.waze.main.navigate;

import com.waze.navbar.InstructionGeometry;

public class NavigationItem {
    public String address;
    public String distance;
    public String icon;
    public int instruction;
    public int instructionExit;
    public InstructionGeometry instructionGeometry;
    public String label;
    public int lat;
    public int lon;
    public int rotation;

    public NavigationItem(String $r1, String $r2, String $r3, String $r4, int $i0, int $i1, InstructionGeometry $r5, int $i2, int $i3, int $i4) throws  {
        this.label = $r1;
        this.distance = $r2;
        this.address = $r3;
        this.icon = $r4;
        this.instruction = $i0;
        this.instructionExit = $i1;
        this.instructionGeometry = $r5;
        this.lat = $i2;
        this.lon = $i3;
        this.rotation = $i4;
    }
}
