package com.google.android.gms.plus.circles;

import android.content.Intent;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public final class AddToCircleConsent {
    public static final String ACTION = "com.google.android.gms.plus.circles.ACTION_ADD_TO_CIRCLE_CONSENT";
    public static final String EXTRA_ACCOUNT_NAME = "com.google.android.gms.plus.circles.EXTRA_ACCOUNT_NAME";
    public static final String EXTRA_APPLICATION_ID = "com.google.android.gms.plus.circles.EXTRA_APPLICATION_ID";
    public static final String EXTRA_BUTTON_TEXT = "com.google.android.gms.plus.circles.EXTRA_BUTTON_TEXT";
    public static final String EXTRA_CONSENT_HTML = "com.google.android.gms.plus.circles.EXTRA_CONSENT_HTML";
    public static final String EXTRA_PAGE_ID = "com.google.android.gms.plus.circles.EXTRA_PAGE_ID";
    public static final String EXTRA_TITLE_TEXT = "com.google.android.gms.plus.circles.EXTRA_TITLE_TEXT";
    public static final int RESULT_ACCEPT_WRITE_FAILED = 1;
    public static final int RESULT_ACCEPT_WRITE_UNKNOWN = 1;

    private AddToCircleConsent() throws  {
    }

    public static final Intent createIntent(String $r0, String $r1, String $r2, String $r3, String $r4, int $i0) throws  {
        return new Intent(ACTION).setPackage("com.google.android.gms").putExtra(EXTRA_ACCOUNT_NAME, zzab.zzi($r0, "accountName")).putExtra(EXTRA_PAGE_ID, $r1).putExtra(EXTRA_CONSENT_HTML, zzab.zzi($r2, "consentHtml")).putExtra(EXTRA_TITLE_TEXT, zzab.zzi($r3, "titleText")).putExtra(EXTRA_BUTTON_TEXT, zzab.zzi($r4, "buttonText")).putExtra(EXTRA_APPLICATION_ID, $i0);
    }
}
