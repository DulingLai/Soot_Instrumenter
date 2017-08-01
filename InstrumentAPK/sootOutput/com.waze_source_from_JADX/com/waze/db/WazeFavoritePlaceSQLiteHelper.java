package com.waze.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;
import com.waze.Logger;
import com.waze.db.dataObj.FavoriteDb.FavoriteType;
import com.waze.widget.WazeAppWidgetLog;
import java.util.HashMap;

public class WazeFavoritePlaceSQLiteHelper extends CustomPathSQLiteOpenHelper {
    private static String DATABASE_DIR = null;
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private static final String FAVORITES_TABLE = "FAVORITES";
    private static final String PLACES_TABLE = "PLACES";

    public WazeFavoritePlaceSQLiteHelper(Context $r1) throws  {
        super($r1.getApplicationInfo().dataDir, "user.db", null, 1);
        DATABASE_DIR = $r1.getApplicationInfo().dataDir;
    }

    public void onCreate(SQLiteDatabase arg0) throws  {
    }

    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) throws  {
    }

    public HashMap<String, Location> getAllIntoMap() throws  {
        HashMap $r2 = new HashMap();
        SQLiteDatabase $r3 = getWritableDatabase();
        Cursor $r4 = $r3.rawQuery("SELECT  type, longitude, latitude FROM FAVORITES join PLACES on FAVORITES.place_id = PLACES.id where (FAVORITES.type = 1 or FAVORITES.type = 2)", null);
        if ($r4.moveToFirst()) {
            do {
                Location $r1 = new Location("History");
                $r1.setLongitude($r4.getDouble(1) / 1000000.0d);
                $r1.setLatitude($r4.getDouble(2) / 1000000.0d);
                $r2.put(FavoriteType.values()[Integer.parseInt($r4.getString(0))].toString(), $r1);
                WazeAppWidgetLog.d(FavoriteType.values()[Integer.parseInt($r4.getString(0))].toString() + "  " + $r1);
            } while ($r4.moveToNext());
        }
        $r3.close();
        Log.d(Logger.TAG, "HashMap<String, Location> getAllIntoMap()");
        WazeAppWidgetLog.d("HashMap<String, Location> getAllIntoMap()");
        return $r2;
    }
}
