package com.waze;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.google.android.gms.gcm.nts.GcmScheduler;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.web.SimpleWebActivity;

public class CalendarApprovedActivity extends ActivityBase {
    public static String EXTRA_CALLBACK = GcmScheduler.INTENT_PARAM_CALLBACK;
    public static String EXTRA_CONTEXT = "context";
    public static String EXTRA_GRANTED = "granted";

    protected void onResume() throws  {
        String $r3;
        super.onResume();
        long $l0 = getIntent().getLongExtra(EXTRA_CALLBACK, 0);
        long $l1 = getIntent().getLongExtra(EXTRA_CONTEXT, 0);
        boolean $z0 = getIntent().getBooleanExtra(EXTRA_GRANTED, false);
        if ($z0) {
            $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_OK;
        } else {
            $r3 = "DONT_ALLOW";
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CALENDAR_CONFIRMATION_POPUP_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, $r3);
        NativeManager $r4 = NativeManager.getInstance();
        $r4.requestCalendarAccessCallback($l0, $l1, $z0);
        if ($z0) {
            final NativeManager nativeManager = $r4;
            MsgBox.openConfirmDialogJavaCallback($r4.getLanguageString(DisplayStrings.DS_CALENDAR_SYNCED_TITLE), $r4.getLanguageString(DisplayStrings.DS_CALENDAR_SYNCED_BODY), true, new OnClickListener() {
                public void onClick(DialogInterface dialog, int $i0) throws  {
                    CalendarApprovedActivity.this.finish();
                    if ($i0 == 0) {
                        Intent $r2 = new Intent(CalendarApprovedActivity.this, SimpleWebActivity.class);
                        $r2.putExtra(SimpleWebActivity.EXTRA_URL, nativeManager.getCalendarLearnMoreUrl());
                        $r2.putExtra(SimpleWebActivity.EXTRA_TITLE, nativeManager.getLanguageString((int) DisplayStrings.DS_CALENDAR_SYNCED_LEARN_MORE_TITLE));
                        CalendarApprovedActivity.this.startActivityForResult($r2, 0);
                    }
                }
            }, $r4.getLanguageString(DisplayStrings.DS_CALENDAR_SYNCED_OK), $r4.getLanguageString(DisplayStrings.DS_CALENDAR_SYNCED_LEARN_MORE), -1);
            return;
        }
        finish();
    }

    protected void onActivityResult(int requestCode, int $i1, Intent data) throws  {
        setResult($i1);
        finish();
    }
}
