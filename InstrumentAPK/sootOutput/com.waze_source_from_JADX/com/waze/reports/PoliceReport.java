package com.waze.reports;

import android.content.Context;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.rtalerts.RTAlertsNativeManager;
import com.waze.strings.DisplayStrings;

public class PoliceReport extends ReportForm {
    private static final int HIDDEN = 1;
    private static final int OTHER_SIDE = -1;
    private static final int REPORT_TYPE = 1;
    private static final int VISIBLE = 0;
    private RTAlertsNativeManager alertsMgr = new RTAlertsNativeManager();
    private boolean mIsPoliceSubtypesAllowed = this.alertsMgr.isPoliceSubtypesAllowed();

    public PoliceReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, 225);
        if (this.mIsPoliceSubtypesAllowed) {
            this.mNumOfButtons = 3;
        } else {
            this.mNumOfButtons = 2;
        }
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
        if ((NativeManager.getInstance().isEnforcementPoliceEnabledNTV() == 2 && getReportSubtype() == 1) || NativeManager.getInstance().isEnforcementPoliceEnabledNTV() == 0) {
            return C1283R.drawable.later_police_french;
        }
        return C1283R.drawable.alert_icon_police;
    }

    protected int getReportType() {
        return 1;
    }

    protected int[] getReportSubtypes() {
        if (this.mIsPoliceSubtypesAllowed) {
            return new int[]{0, 1, -1};
        }
        return new int[]{0, -1};
    }

    protected int[] getButtonResourceIds() {
        int icon_report_police_other_side;
        if (this.nativeManager.getIsDriveOnLeft()) {
            icon_report_police_other_side = C1283R.drawable.icon_report_police_other_side_uk;
        } else {
            icon_report_police_other_side = C1283R.drawable.icon_report_police_other_side;
        }
        if (this.mIsPoliceSubtypesAllowed) {
            return new int[]{C1283R.drawable.icon_report_police_visible, C1283R.drawable.icon_report_police_hidden, icon_report_police_other_side};
        }
        return new int[]{C1283R.drawable.icon_report_police_visible, icon_report_police_other_side};
    }

    protected int[] getButtonDisplayStrings() {
        if (this.mIsPoliceSubtypesAllowed) {
            return new int[]{DisplayStrings.DS_VISIBLE, 424, DisplayStrings.DS_OTHER_LANE};
        }
        return new int[]{225, DisplayStrings.DS_OTHER_LANE};
    }
}
