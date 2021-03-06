package com.waze.reports;

import android.view.View;
import android.view.View.OnClickListener;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.view.button.ReportMenuButton;

class ReportMenu$7 implements OnClickListener {
    final /* synthetic */ ReportMenu this$0;

    ReportMenu$7(ReportMenu this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_CLICK, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CHAT);
        if (ReportMenu.access$200(this.this$0)) {
            ReportMenu.access$300(this.this$0).randomUserMsg();
            return;
        }
        this.this$0.setReportForm(new ChitchatReport(ReportMenu.access$100(this.this$0), this.this$0));
        this.this$0.flipView((ReportMenuButton) v);
    }
}
