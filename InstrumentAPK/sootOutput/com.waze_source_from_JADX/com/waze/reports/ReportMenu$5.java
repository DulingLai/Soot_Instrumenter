package com.waze.reports;

import android.view.View;
import android.view.View.OnClickListener;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.view.button.ReportMenuButton;

class ReportMenu$5 implements OnClickListener {
    final /* synthetic */ ReportMenu this$0;

    ReportMenu$5(ReportMenu this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_CLICK, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ACCIDENT);
        this.this$0.setReportForm(new AccidentReport(ReportMenu.access$100(this.this$0), this.this$0));
        this.this$0.flipView((ReportMenuButton) v);
    }
}
