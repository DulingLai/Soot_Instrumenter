package com.waze.reports;

import android.content.Context;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.strings.DisplayStrings;

public class CameraReport extends ReportForm {
    private static final int FAKE = 2;
    private static final int REDLIGHT = 1;
    private static final int REPORT_TYPE = 10;
    private static final int SPEED = 0;

    public CameraReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, 342);
        this.mNumOfButtons = 3;
        this.mLayoutResId = C1283R.layout.report_speedcam_content;
        initLayout();
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.small_camera;
    }

    protected void initLayout() {
        this.mIsAddDetailsActive = false;
        this.mIsTakePictureActive = false;
        super.initLayout();
        buildButtons();
        ((TextView) findViewById(C1283R.id.reportSpeedCamText)).setText(this.nativeManager.getLanguageString(DisplayStrings.f119xaed913dd));
    }

    protected int[] getReportSubtypes() {
        return new int[]{0, 1, 2};
    }

    protected int[] getButtonResourceIds() {
        return new int[]{C1283R.drawable.icon_report_camera_speed, C1283R.drawable.icon_report_camera_trafficlight, C1283R.drawable.icon_report_camera_not_there};
    }

    protected int[] getButtonDisplayStrings() {
        return new int[]{198, 197, 196};
    }

    protected int getReportType() {
        return 10;
    }
}
