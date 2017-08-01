package com.waze.config;

import android.os.Environment;

public class WazeUserPreferences {
    private static WazeConfig mConfig = null;
    private static String mDirName = "/data/com.waze/";
    private static String mFileName = "user";

    public static void load() throws  {
        if (mConfig == null) {
            mConfig = new WazeConfig(Environment.getDataDirectory() + mDirName + mFileName);
            mConfig.load();
        }
    }

    public static String getProperty(String $r0) throws  {
        if (mConfig == null) {
            load();
        }
        return mConfig.getProperty($r0);
    }

    public static String getProperty(String $r0, String $r1) throws  {
        if (mConfig == null) {
            load();
        }
        return mConfig == null ? $r1 : mConfig.getProperty($r0, $r1);
    }
}
