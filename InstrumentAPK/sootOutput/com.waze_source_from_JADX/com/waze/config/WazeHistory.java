package com.waze.config;

import android.content.Context;
import android.location.Location;
import com.waze.db.WazeFavoritePlaceSQLiteHelper;
import com.waze.widget.WazeAppWidgetLog;
import java.util.HashMap;

public class WazeHistory {
    private static final String FAVORITES = "F";
    private static final String HOME = "home";
    private static final String WORK = "work";
    private static HashMap<String, Location> mMap = null;
    private static WazeFavoritePlaceSQLiteHelper mWazeFavoritePalceSQLiteHelper;

    public static void load(Context $r0) throws  {
        try {
            mWazeFavoritePalceSQLiteHelper = new WazeFavoritePlaceSQLiteHelper($r0);
            mMap = mWazeFavoritePalceSQLiteHelper.getAllIntoMap();
        } catch (Exception $r1) {
            String $r4;
            if ($r1 == null) {
                $r4 = "";
            } else {
                $r4 = $r1.getMessage();
            }
            WazeAppWidgetLog.e("Failed to load history from DB [" + $r4 + "]");
        }
    }

    public static Location getEntry(String $r0) throws  {
        if (mMap == null) {
            return null;
        }
        WazeAppWidgetLog.d(" Location getEntry: " + mMap.get($r0));
        return (Location) mMap.get($r0);
    }

    public static void setEntry(String $r0, Location $r1) throws  {
        if (mMap == null) {
            mMap = new HashMap();
        }
        mMap.put($r0, $r1);
    }

    public static void removeEntry(String $r0) throws  {
        if (mMap != null) {
            removeEntryIfNecessary($r0, "Home", HOME);
            removeEntryIfNecessary($r0, "Work", WORK);
        }
    }

    private static void removeEntryIfNecessary(String $r0, String $r1, String $r2) throws  {
        if ($r0.equalsIgnoreCase(WazeLang.getLang($r1)) || $r0.equalsIgnoreCase(WazeLang.getLang($r2)) || $r0.equalsIgnoreCase($r1) || $r0.equalsIgnoreCase($r2)) {
            mMap.remove(WazeLang.getLang($r1));
            mMap.remove(WazeLang.getLang($r2));
            mMap.remove($r1);
            mMap.remove($r2);
        }
    }
}
