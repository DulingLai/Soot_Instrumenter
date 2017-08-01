package com.waze.carpool;

import android.os.Bundle;
import com.waze.C1283R;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import com.waze.view.web.SimpleWebActivity;

public class CarpoolTermsActivity extends SimpleWebActivity {
    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setRequestedOrientation(1);
        TitleBar $r3 = (TitleBar) findViewById(C1283R.id.webTitle);
        $r3.init(this, DisplayStrings.DS_CARPOOL_TERMS_TITLE);
        $r3.setCloseVisibility(false);
        loadUrl(CarpoolNativeManager.getInstance().configGetRideWithTermsUrlNTV());
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_TOS_SHOWN);
    }
}
