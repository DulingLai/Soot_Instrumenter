package com.waze.reports;

import android.view.View;
import android.view.View.OnClickListener;
import com.waze.ConfigManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;

class ReportMenu$12 implements OnClickListener {
    final /* synthetic */ ReportMenu this$0;

    ReportMenu$12(ReportMenu this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_CLICK, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_DEBUG);
        ConfigManager.getInstance().sendLogsClick();
        this.this$0.close();
    }
}
