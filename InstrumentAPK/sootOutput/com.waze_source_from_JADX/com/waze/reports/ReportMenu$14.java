package com.waze.reports;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.rtalerts.RTAlertsMenu;

class ReportMenu$14 implements OnClickListener {
    final /* synthetic */ ReportMenu this$0;

    class C25271 implements Runnable {

        class C25261 implements Runnable {
            C25261() {
            }

            public void run() {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "REPORTS");
                Intent intent = new Intent(ReportMenu.access$400(ReportMenu$14.this.this$0), RTAlertsMenu.class);
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().startActivityForResult(intent, MainActivity.RTALERTS_REQUEST_CODE);
                }
            }
        }

        C25271() {
        }

        public void run() {
            AppService.Post(new C25261());
        }
    }

    ReportMenu$14(ReportMenu this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        NativeManager.Post(new C25271());
    }
}
