package com.waze.reports;

import android.content.Intent;
import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.LocationFactory;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.RequestPermissionActivity;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.strings.DisplayStrings;

class ReportMenu$9 implements OnClickListener {
    final /* synthetic */ ReportMenu this$0;

    ReportMenu$9(ReportMenu this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        if (AppService.isCameraAvailable()) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_CLICK, "TYPE", "PLACE");
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_REPORT_MENU_PLACE_CLICKED, null, null);
            if (NativeManager.getInstance().hasNetworkNTV()) {
                Location loc = LocationFactory.getInstance().getLastLocation();
                if (loc == null || System.currentTimeMillis() - loc.getTime() > 30000) {
                    MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.f128x47989372), false);
                    return;
                }
                Intent addPlaceIntent = new Intent(ReportMenu.access$400(this.this$0), AddPlaceFlowActivity.class);
                Intent permissionsIntent = new Intent(ReportMenu.access$400(this.this$0), RequestPermissionActivity.class);
                permissionsIntent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.CAMERA"});
                permissionsIntent.putExtra(RequestPermissionActivity.INT_ON_GRANTED, addPlaceIntent);
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().startActivityForResult(permissionsIntent, 0);
                }
                ReportMenu.access$500(this.this$0).removeReportMenu();
                return;
            }
            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_OFFLINE_REPORT_PLACE_TITLE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_OFFLINE_REPORT_PLACE_BODY), false);
        }
    }
}
