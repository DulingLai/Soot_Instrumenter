package com.waze.utils;

import android.content.Intent;
import java.util.Set;

public final class IntentUtils {
    public static String[] getExtrasKeys(Intent intent) {
        Set<String> ks = intent.getExtras().keySet();
        if (ks.size() <= 0) {
            return null;
        }
        String[] ka = new String[ks.size()];
        ks.toArray(ka);
        return ka;
    }
}
