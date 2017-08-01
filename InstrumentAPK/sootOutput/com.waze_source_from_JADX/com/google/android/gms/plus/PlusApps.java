package com.google.android.gms.plus;

import android.accounts.Account;
import android.content.Intent;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.util.zzq;

/* compiled from: dalvik_source_com.waze.apk */
public final class PlusApps {
    public static final String ACTION_MANAGE_APP = "com.google.android.gms.plus.action.MANAGE_APP";
    public static final String EXTRA_ACCOUNT = "com.google.android.gms.plus.ACCOUNT";
    public static final String EXTRA_APP_HAS_CONN_READ = "com.google.android.gms.plus.APP_HAS_CONN_READ";
    public static final String EXTRA_APP_ICON_URL = "com.google.android.gms.plus.APP_ICON_URL";
    public static final String EXTRA_APP_ID = "com.google.android.gms.plus.APP_ID";
    public static final String EXTRA_APP_IS_ASPEN = "com.google.android.gms.plus.APP_IS_ASPEN";
    public static final String EXTRA_APP_IS_FITNESS = "com.google.android.gms.plus.APP_IS_FITNESS";
    public static final String EXTRA_APP_NAME = "com.google.android.gms.plus.APP_NAME";
    public static final String EXTRA_APP_PACKAGE = "com.google.android.gms.plus.APP_PACKAGE";
    public static final int RESULT_ERROR = 1;

    private PlusApps() throws  {
    }

    @Deprecated
    public static Intent getIntent(@Deprecated Account $r0, @Deprecated String $r1, @Deprecated String $r2, @Deprecated String $r3, @Deprecated String $r4) throws  {
        return getIntent($r0, $r1, $r2, $r3, $r4, true);
    }

    public static Intent getIntent(Account $r0, String $r1, String $r2, String $r3, String $r4, boolean $z0) throws  {
        Intent $r5 = new Intent(ACTION_MANAGE_APP);
        $r5.putExtra(EXTRA_ACCOUNT, $r0);
        $r5.putExtra(EXTRA_APP_ID, $r1);
        $r5.putExtra(EXTRA_APP_PACKAGE, $r2);
        $r5.putExtra(EXTRA_APP_NAME, $r3);
        $r5.putExtra(EXTRA_APP_ICON_URL, $r4);
        $r5.putExtra(EXTRA_APP_IS_ASPEN, $z0);
        return $r5;
    }

    public static String hexAppIdToLong(String $r0) throws  {
        zzab.zzgy($r0);
        zzab.zzbn($r0.startsWith("e"));
        return Long.toString(zzq.zzhg($r0.substring(1)));
    }

    public static String longAppIdToHex(String $r0) throws  {
        zzab.zzgy($r0);
        String $r1 = "e";
        $r0 = String.valueOf(Long.toHexString(Long.parseLong($r0)));
        return $r0.length() != 0 ? $r1.concat($r0) : new String("e");
    }
}
