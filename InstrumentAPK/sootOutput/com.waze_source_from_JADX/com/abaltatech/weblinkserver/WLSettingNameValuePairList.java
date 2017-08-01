package com.abaltatech.weblinkserver;

import com.abaltatech.mcs.logger.MCSLogger;
import com.facebook.internal.ServerProtocol;
import java.util.ArrayList;
import java.util.Iterator;

public class WLSettingNameValuePairList extends ArrayList<WLSettingNameValuePair> {
    private static final String TAG = "WLSettingNameValuePair";
    private static final long serialVersionUID = -2390568351900633027L;

    public WLSettingNameValuePairList(int $i0) throws  {
        super($i0);
    }

    public WLSettingNameValuePairList(WLSettingNameValuePairList $r1) throws  {
        super($r1 == null ? 0 : $r1.size());
        if ($r1 != null) {
            Iterator $r3 = $r1.iterator();
            while ($r3.hasNext()) {
                WLSettingNameValuePair $r5 = (WLSettingNameValuePair) $r3.next();
                add(new WLSettingNameValuePair($r5.getName(), $r5.getValue()));
            }
        }
    }

    public WLSettingNameValuePair findSetting(String $r1, boolean $z0) throws  {
        Iterator $r2 = iterator();
        while ($r2.hasNext()) {
            int $i0;
            WLSettingNameValuePair $r4 = (WLSettingNameValuePair) $r2.next();
            String $r5 = $r4.getName();
            if ($z0) {
                $i0 = $r5.compareToIgnoreCase($r1);
                continue;
            } else {
                $i0 = $r5.compareTo($r1);
                continue;
            }
            if ($i0 == 0) {
                return $r4;
            }
        }
        return null;
    }

    public String findSettingValue(String $r1, boolean $z0) throws  {
        Iterator $r2 = iterator();
        while ($r2.hasNext()) {
            int $i0;
            WLSettingNameValuePair $r4 = (WLSettingNameValuePair) $r2.next();
            String $r5 = $r4.getName();
            if ($z0) {
                $i0 = $r5.compareToIgnoreCase($r1);
                continue;
            } else {
                $i0 = $r5.compareTo($r1);
                continue;
            }
            if ($i0 == 0) {
                return $r4.getValue();
            }
        }
        return null;
    }

    public int getSettingValue(String $r1, boolean $z0, int $i0) throws  {
        String $r3 = findSettingValue($r1, $z0);
        if ($r3 == null) {
            return $i0;
        }
        try {
            $i0 = Integer.parseInt($r3);
            return $i0;
        } catch (NumberFormatException $r2) {
            MCSLogger.log(TAG, "Unable to parse integer from setting with name '" + $r1 + "' and value '" + $r3 + "'", $r2);
            return $i0;
        }
    }

    public boolean getSettingValue(String $r1, boolean $z0, boolean $z1) throws  {
        String $r2 = findSettingValue($r1, $z0);
        if ($r2 == null) {
            return $z1;
        }
        if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase($r2)) {
            return true;
        }
        if ("false".equalsIgnoreCase($r2)) {
            return false;
        }
        MCSLogger.log(TAG, "Unable to parse boolean from setting with name '" + $r1 + "' and value '" + $r2 + "'");
        return $z1;
    }
}
