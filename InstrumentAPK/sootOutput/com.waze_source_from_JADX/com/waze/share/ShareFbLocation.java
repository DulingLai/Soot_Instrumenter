package com.waze.share;

public class ShareFbLocation implements Comparable<ShareFbLocation> {
    public String address;
    public String description;
    public float distance;
    public String id;
    public double latitude;
    public double longitude;
    public String name;

    public int compareTo(ShareFbLocation another) {
        return new Float(this.distance).compareTo(new Float(another.distance));
    }
}
