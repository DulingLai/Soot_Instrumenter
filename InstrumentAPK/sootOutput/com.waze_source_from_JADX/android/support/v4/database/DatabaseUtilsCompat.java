package android.support.v4.database;

import android.text.TextUtils;

public final class DatabaseUtilsCompat {
    private DatabaseUtilsCompat() throws  {
    }

    public static String concatenateWhere(String $r0, String $r1) throws  {
        if (TextUtils.isEmpty($r0)) {
            return $r1;
        }
        if (TextUtils.isEmpty($r1)) {
            return $r0;
        }
        return "(" + $r0 + ") AND (" + $r1 + ")";
    }

    public static String[] appendSelectionArgs(String[] $r0, String[] $r1) throws  {
        if ($r0 == null || $r0.length == 0) {
            return $r1;
        }
        String[] $r2 = new String[($r0.length + $r1.length)];
        System.arraycopy($r0, 0, $r2, 0, $r0.length);
        System.arraycopy($r1, 0, $r2, $r0.length, $r1.length);
        return $r2;
    }
}
