package com.waze.reports;

import android.content.Context;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.strings.DisplayStrings;

public class HazardReport extends ReportForm {
    private static final int ON_ROAD = 0;
    private static final int ON_ROAD_CAR_STOPPED = 23;
    private static final int ON_ROAD_CONSTRUCTION = 22;
    private static final int ON_ROAD_OBJECT = 3;
    private static final int ON_ROAD_POT_HOLE = 4;
    private static final int ON_ROAD_ROAD_KILL = 5;
    private static final int ON_SHOULDER = 1;
    private static final int ON_SHOULDER_ANIMALS = 7;
    private static final int ON_SHOULDER_CAR_STOPPED = 6;
    private static final int ON_SHOULDER_MISSING_SIGN = 8;
    private static final int REPORT_TYPE = 5;
    private static final int WEATHER = 2;
    private static final int WEATHER_FOG = 9;
    private static final int WEATHER_HAIL = 10;
    private static final int WEATHER_HEAVY_RAIN = 13;
    private static final int WEATHER_HEAVY_SNOW = 21;
    private int selected;

    public HazardReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, 414);
        this.selected = -1;
        this.mNumOfButtons = 3;
        initLayout();
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.alert_icon_hazard;
    }

    protected void onButtonClicked(int buttonIndex) {
        super.onButtonClicked(buttonIndex);
        if (buttonIndex == 0) {
            this.selected = 0;
            ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(191));
            String objectOnRoad = this.nativeManager.getLanguageString(DisplayStrings.DS_OBJECT_ON_ROAD);
            String construction = this.nativeManager.getLanguageString(360);
            String pothole = this.nativeManager.getLanguageString(DisplayStrings.DS_POTHOLE);
            String carStopped = this.nativeManager.getLanguageString(DisplayStrings.DS_CAR_STOPPED_ON_ROAD);
            String roadkill = this.nativeManager.getLanguageString(DisplayStrings.DS_ROADKILL);
            int i = 5;
            i = 5;
            int i2 = 415;
            showGridSubmenu(i2, new String[]{objectOnRoad, construction, pothole, carStopped, roadkill}, new int[]{C1283R.drawable.icon_report_hazard_object, C1283R.drawable.icon_report_hazard_construction, C1283R.drawable.icon_report_hazard_pothole, C1283R.drawable.icon_report_hazard_stopped, C1283R.drawable.icon_report_hazard_roadkill}, new int[]{3, 22, 4, 23, 5});
        }
        if (buttonIndex == 1) {
            this.selected = 1;
            ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(190));
            carStopped = this.nativeManager.getLanguageString(192);
            String animals = this.nativeManager.getLanguageString(194);
            String missingSign = this.nativeManager.getLanguageString(DisplayStrings.DS_MISSING_SIGN);
            i = 3;
            i = 3;
            i2 = 416;
            showGridSubmenu(i2, new String[]{carStopped, animals, missingSign}, new int[]{C1283R.drawable.icon_report_hazard_stopped, C1283R.drawable.icon_report_hazard_animals, C1283R.drawable.icon_report_hazard_missingsign}, new int[]{6, 7, 8});
        }
        if (buttonIndex == 2) {
            this.selected = 2;
            ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(189));
            String fog = this.nativeManager.getLanguageString(DisplayStrings.DS_FOG);
            String hail = this.nativeManager.getLanguageString(413);
            String flood = this.nativeManager.getLanguageString(402);
            String ice = this.nativeManager.getLanguageString(434);
            String[] options = new String[]{fog, hail, flood, ice};
            i = 4;
            i = 4;
            showGridSubmenu(DisplayStrings.DS_WEATHER_HAZARD, options, new int[]{C1283R.drawable.icon_hazard_weather_fog, C1283R.drawable.icon_hazard_weather_hail, C1283R.drawable.icon_hazard_weather_flood, C1283R.drawable.icon_hazard_weather_ice}, new int[]{9, 10, 13, 21});
        }
    }

    protected void onButtonUnselected(int buttonIndex) {
        super.onButtonUnselected(buttonIndex);
        ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(this.mTitle));
    }

    protected int getReportSubtype() {
        if (this.mSubSelected == -1) {
            return this.selected;
        }
        return this.mSubSelected;
    }

    protected int getReportType() {
        return 5;
    }

    protected int[] getButtonResourceIds() {
        return new int[]{C1283R.drawable.icon_report_hazard_on_road, C1283R.drawable.icon_report_hazard_on_shoulder, C1283R.drawable.icon_report_hazard_weather};
    }

    protected int[] getButtonDisplayStrings() {
        return new int[]{191, 190, 189};
    }

    protected int[] getReportSubtypes() {
        return null;
    }
}
