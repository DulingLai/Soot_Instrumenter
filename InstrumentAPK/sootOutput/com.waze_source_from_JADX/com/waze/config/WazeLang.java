package com.waze.config;

import com.waze.AppService;
import com.waze.ResManager;
import com.waze.widget.WazeAppWidgetLog;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class WazeLang {
    private static String mDirName = "/waze/";
    private static String mFileName = ResManager.mLangPrefix;
    private static HashMap<String, String> mMap = null;

    public static void load(String $r0) throws  {
        if (mMap == null) {
            String $r4 = AppService.getExternalStoragePath();
            WazeAppWidgetLog.d("Loading lang file" + $r4 + "," + mDirName + "," + mFileName + $r0);
            Scanner $r2 = new Scanner(new FileReader(new File($r4, mDirName + mFileName + $r0)));
            mMap = new HashMap();
            while ($r2.hasNextLine()) {
                $r4 = $r2.nextLine();
                try {
                    String[] $r8 = $r4.split("=");
                    mMap.put($r8[0], $r8[1]);
                } catch (Exception $r10) {
                    String $r6;
                    if ($r10 == null) {
                        $r6 = "";
                    } else {
                        try {
                            $r6 = $r10.getMessage();
                        } catch (Exception $r11) {
                            if ($r11 == null) {
                                $r0 = "";
                            } else {
                                $r0 = $r11.getMessage();
                            }
                            WazeAppWidgetLog.e("Failed to load lang file [" + $r0 + "]");
                            return;
                        }
                    }
                    WazeAppWidgetLog.e("Failed to load lang file [" + $r0 + "], line: " + $r4 + " Exception: " + $r6);
                }
            }
        }
    }

    public static String getLang(String $r1) throws  {
        if (mMap == null) {
            return $r1;
        }
        String $r3 = (String) mMap.get($r1);
        return $r3 == null ? $r1 : $r3;
    }
}
