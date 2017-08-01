package com.waze.config;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class WazeConfig {
    public static final String LOG_TAG = "WAZE_CONFIG";
    private String mFileName = null;
    private InputStream mFileStream = null;
    private HashMap<String, String> mMap = null;

    WazeConfig(String $r1) throws  {
        this.mFileName = $r1;
        this.mMap = new HashMap();
    }

    WazeConfig(Context $r1, String $r2, String $r3) throws  {
        try {
            this.mFileStream = $r1.getAssets().open($r2 + $r3);
            this.mMap = new HashMap();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to load config file " + this.mFileName);
        }
    }

    void load() throws  {
        try {
            Scanner $r4;
            File $r2 = new File(this.mFileName);
            if ($r2.exists()) {
                $r4 = new Scanner(new FileReader($r2));
            } else {
                $r4 = new Scanner(this.mFileStream);
            }
            this.mMap = new HashMap();
            while ($r4.hasNextLine()) {
                String[] $r7 = $r4.nextLine().split(": ");
                if ($r7.length > 1) {
                    this.mMap.put($r7[0], $r7[1]);
                }
            }
            Log.d(LOG_TAG, "config file " + this.mFileName + " Loaded");
        } catch (Exception e) {
            this.mMap = null;
            Log.e(LOG_TAG, "Failed to load config file " + this.mFileName);
        }
    }

    String getProperty(String $r1) throws  {
        if (this.mMap == null) {
            load();
        }
        if (this.mMap != null) {
            return (String) this.mMap.get($r1);
        }
        return null;
    }

    String getProperty(String $r1, String $r2) throws  {
        $r1 = getProperty($r1);
        return $r1 == null ? $r2 : $r1;
    }
}
