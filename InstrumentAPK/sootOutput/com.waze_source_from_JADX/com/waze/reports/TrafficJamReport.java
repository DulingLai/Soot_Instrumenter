package com.waze.reports;

import android.content.Context;
import com.waze.C1283R;

public class TrafficJamReport extends ReportForm {
    private static final int HEAVY = 1;
    private static final int MODERATE = 0;
    private static final int REPORT_TYPE = 3;
    private static final int STANDSTILL = 2;

    public TrafficJamReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, 116);
        this.mNumOfButtons = 3;
        initLayout();
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.alert_icon_traffic_jam;
    }

    protected int[] getReportSubtypes() {
        return new int[]{0, 1, 2};
    }

    protected int getReportType() {
        return 3;
    }

    protected int[] getButtonResourceIds() {
        return new int[]{C1283R.drawable.icon_report_traffic_moderate, C1283R.drawable.icon_report_traffic_heavy, C1283R.drawable.icon_report_traffic_standstill};
    }

    protected int[] getButtonDisplayStrings() {
        return new int[]{182, 183, 184};
    }
}
