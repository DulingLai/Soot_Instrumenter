package com.waze;

import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;

public class ConfigItem {
    private String category = null;
    private String name = null;
    private String value = null;

    public ConfigItem(String $r1, String $r2, String $r3) throws  {
        this.category = $r1;
        this.name = $r2;
        this.value = $r3;
    }

    public ConfigItem(String $r1, String $r2, boolean $z0) throws  {
        this.category = $r1;
        this.name = $r2;
        setBooleanValue($z0);
    }

    public String getCategory() throws  {
        return this.category;
    }

    public void setCategory(String $r1) throws  {
        this.category = $r1;
    }

    public String getName() throws  {
        return this.name;
    }

    public void setName(String $r1) throws  {
        this.name = $r1;
    }

    public String getStringValue() throws  {
        return this.value;
    }

    public void setStringValue(String $r1) throws  {
        this.value = $r1;
    }

    public int getIntValue(int $i0) throws  {
        return TextUtils.isEmpty(this.value) ? $i0 : Integer.parseInt(this.value);
    }

    public void setIntValue(int $i0) throws  {
        this.value = Integer.toString($i0);
    }

    public boolean getBooleanValue() throws  {
        return this.value.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES) || this.value.equalsIgnoreCase("yes");
    }

    public void setBooleanValue(boolean $z0) throws  {
        String $r1;
        if ($z0) {
            $r1 = "yes";
        } else {
            $r1 = "no";
        }
        this.value = $r1;
    }

    public String toString() throws  {
        return "Category: " + this.category + ", Name: " + this.name + " Value: " + this.value;
    }
}
