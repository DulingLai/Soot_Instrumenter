package com.waze.utils;

import android.content.Context;
import com.waze.AppUUID;

public class StatsData {
    public String EventName;
    public String[] EventValue;
    public String UUID;
    public String mKey;
    public String mSession;

    public StatsData(Context mcontext, String Name, String[] Params) {
        this.EventName = Name;
        this.EventValue = Params;
        this.UUID = AppUUID.getInstallationUUID(mcontext);
    }

    public String buildCmd(boolean bIsAds) {
        String cmd;
        int sValuesSize = 0;
        if (this.EventValue != null) {
            sValuesSize = this.EventValue.length;
        }
        if (bIsAds) {
            cmd = "Stats," + this.UUID + "," + "-1" + "," + this.EventName;
        } else {
            cmd = "Stats," + this.UUID + "," + "-1" + "," + this.EventName + "," + sValuesSize;
        }
        if (this.EventValue == null) {
            return cmd + ",0";
        }
        for (String str : this.EventValue) {
            cmd = cmd + "," + str;
        }
        return cmd;
    }
}
