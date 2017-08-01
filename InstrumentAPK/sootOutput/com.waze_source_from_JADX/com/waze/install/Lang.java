package com.waze.install;

import java.io.Serializable;

public class Lang implements Serializable {
    private static final long serialVersionUID = 1;
    private String lable;
    private int value;

    public Lang(String $r1, int $i0) throws  {
        this.value = $i0;
        this.lable = $r1;
    }

    public void setValue(int $i0) throws  {
        this.value = $i0;
    }

    public int getValue() throws  {
        return this.value;
    }

    public void setLable(String $r1) throws  {
        this.lable = $r1;
    }

    public String getLable() throws  {
        return this.lable;
    }

    public static Lang[] getLangArray(Object[] $r0) throws  {
        Lang[] $r1 = new Lang[$r0.length];
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r1[$i0] = (Lang) $r0[$i0];
        }
        return $r1;
    }
}
