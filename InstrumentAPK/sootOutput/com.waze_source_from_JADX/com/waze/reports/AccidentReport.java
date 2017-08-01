package com.waze.reports;

import android.content.Context;
import com.waze.C1283R;
import com.waze.strings.DisplayStrings;

public class AccidentReport extends ReportForm {
    private static final int MAJOR = 1;
    private static final int MINOR = 0;
    private static final int OTHER_SIDE = -1;
    private static final int REPORT_TYPE = 2;

    public AccidentReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, 187);
        this.mNumOfButtons = 3;
        initLayout();
    }

    protected void onButtonClicked(int buttonIndex) {
        super.onButtonClicked(buttonIndex);
        this.myLane = getReportSubtypes()[buttonIndex] != -1;
    }

    protected void onButtonUnselected(int buttonIndex) {
        super.onButtonUnselected(buttonIndex);
        boolean z = this.myLane || getReportSubtypes()[buttonIndex] == -1;
        this.myLane = z;
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.alert_icon_accident;
    }

    protected int[] getReportSubtypes() {
        return new int[]{0, 1, -1};
    }

    protected int getReportType() {
        return 2;
    }

    protected int[] getButtonResourceIds() {
        int icon_other_side;
        if (this.nativeManager.getIsDriveOnLeft()) {
            icon_other_side = C1283R.drawable.icon_accident_other_side_uk;
        } else {
            icon_other_side = C1283R.drawable.icon_accident_other_side;
        }
        return new int[]{C1283R.drawable.icon_accident_minor, C1283R.drawable.icon_accident_major, icon_other_side};
    }

    protected int[] getButtonDisplayStrings() {
        return new int[]{185, 186, DisplayStrings.DS_OTHER_LANE};
    }
}
