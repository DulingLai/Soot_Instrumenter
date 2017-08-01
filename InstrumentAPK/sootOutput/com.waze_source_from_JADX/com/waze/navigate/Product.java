package com.waze.navigate;

public class Product {
    public String currency;
    public String[] formats;
    public String icon;
    public String[] labels;
    public int lastUpdated;
    public String name;
    public float[] prices;
    public String providerId;
    public String updatedBy;
    public String venueId;

    public Product(String $r1, String $r2, String $r3, String $r4, String[] $r5, String[] $r6, float[] $r7, int $i0, String $r8, String $r9) throws  {
        this.providerId = $r1;
        this.venueId = $r2;
        this.name = $r3;
        this.icon = $r4;
        this.labels = $r5;
        this.formats = $r6;
        this.prices = $r7;
        this.lastUpdated = $i0;
        this.updatedBy = $r8;
        this.currency = $r9;
    }
}
