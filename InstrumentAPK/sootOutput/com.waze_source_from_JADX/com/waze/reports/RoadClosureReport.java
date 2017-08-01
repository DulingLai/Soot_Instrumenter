package com.waze.reports;

import android.content.Context;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.strings.DisplayStrings;

public class RoadClosureReport extends ReportForm {
    private static final int CONSTRUCTION = 1;
    private static final int EVENT = 2;
    private static final int HAZARD = 0;
    private static final int NONE = -1;
    private static final int REPORT_TYPE = 12;
    private int selected;
    private int subSelected;

    public RoadClosureReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, DisplayStrings.DS_CLOSURE);
        this.selected = -1;
        this.subSelected = -1;
        this.mNumOfButtons = 3;
        initLayout();
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.icon_report_closure;
    }

    protected void onButtonClicked(int buttonIndex) {
        super.onButtonClicked(buttonIndex);
        if (buttonIndex == 0) {
            this.selected = 0;
            ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(414));
        }
        if (buttonIndex == 1) {
            this.selected = 1;
            ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(360));
        }
        if (buttonIndex == 2) {
            this.selected = 2;
            ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_EVENT));
        }
    }

    protected void initLayout() {
        this.mIsSelectLaneActive = false;
        this.mIsDurationActive = true;
        this.mIsAddDetailsActive = false;
        this.mIsTakePictureActive = true;
        super.initLayout();
    }

    protected void onButtonUnselected(int buttonIndex) {
        super.onButtonUnselected(buttonIndex);
        ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(this.mTitle));
    }

    protected int getReportSubtype() {
        return this.selected;
    }

    protected int getDuration() {
        return this.subSelected;
    }

    protected int getReportType() {
        return 12;
    }

    protected int[] getButtonResourceIds() {
        return new int[]{C1283R.drawable.icon_report_hazard_object, C1283R.drawable.icon_report_hazard_construction, C1283R.drawable.icon_report_closure_event};
    }

    protected int[] getButtonDisplayStrings() {
        return new int[]{414, 360, DisplayStrings.DS_EVENT};
    }

    protected int[] getReportSubtypes() {
        return null;
    }
}
