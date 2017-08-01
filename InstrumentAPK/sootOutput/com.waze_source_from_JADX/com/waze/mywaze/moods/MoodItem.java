package com.waze.mywaze.moods;

import android.graphics.drawable.Drawable;

public class MoodItem {
    public String caption;
    public boolean enabled;
    public boolean first;
    public Drawable image;
    public boolean last;
    public String mood;
    public boolean title;

    public MoodItem(String $r1, String $r2, Drawable $r3, boolean $z0, boolean $z1, boolean $z2, boolean $z3) throws  {
        this.mood = $r1;
        this.caption = $r2;
        this.image = $r3;
        this.title = $z0;
        this.enabled = $z1;
        this.first = $z2;
        this.last = $z3;
    }
}
