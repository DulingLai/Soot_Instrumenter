package com.waze.mywaze;

import java.io.Serializable;

public class Group implements Serializable {
    private static final long serialVersionUID = 52477108445134890L;
    public String icon;
    public boolean isSelected;
    public String name;
    public String url;

    public Group(String $r1, String $r2, String $r3, boolean $z0) throws  {
        this.icon = $r1;
        this.name = $r2;
        this.url = $r3;
        this.isSelected = $z0;
    }
}
