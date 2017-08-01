package com.waze.config;

import android.content.Context;
import android.os.Environment;
import com.waze.ResManager;

public class WazePreferences {
    private static WazeConfig mConfig = null;
    private static String mDirName = "/data/com.waze/";
    private static String mFileName = ResManager.mPrefFile;

    public static void load() throws  {
        if (mConfig == null) {
            mConfig = new WazeConfig(Environment.getDataDirectory() + mDirName + mFileName);
            mConfig.load();
        }
    }

    public static void loadFromPackage(Context $r0) throws  {
        if (mConfig == null) {
            mConfig = new WazeConfig($r0, ResManager.mResDir, mFileName);
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
        if (mConfig != null) {
            return mConfig.getProperty($r0, $r1);
        }
        return $r1;
    }
}
