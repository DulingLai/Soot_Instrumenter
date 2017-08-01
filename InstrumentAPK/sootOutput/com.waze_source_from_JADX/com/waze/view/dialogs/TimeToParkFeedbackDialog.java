package com.waze.view.dialogs;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import com.waze.AppService;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.navigate.DriveToNativeManager;
import com.waze.reports.VenueData;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;

public class TimeToParkFeedbackDialog extends BottomSheet implements Adapter, OnCancelListener {
    private static boolean autodetectedShown = false;
    private boolean autoDetected;
    private String statContext;

    public TimeToParkFeedbackDialog(String statContext) {
        this(statContext, false);
    }

    public TimeToParkFeedbackDialog(String statContext, boolean autoDetected) {
        super(AppService.getActiveActivity(), DisplayStrings.DS_TTP_FEEDBACK_TITLE, Mode.COLUMN_TEXT);
        this.autoDetected = false;
        this.statContext = null;
        this.statContext = statContext;
        this.autoDetected = autoDetected;
        super.setAdapter(this);
        if (ConfigValues.getBoolValue(266)) {
            super.setCancelListener(this);
        }
    }

    public void show() {
        if (!ConfigValues.getBoolValue(266)) {
            dismiss();
        } else if (this.autoDetected && autodetectedShown) {
            dismiss();
        } else {
            autodetectedShown = this.autoDetected;
            String subtitle = DriveToNativeManager.getInstance().getTimeToParkStrFormatNTV(DisplayStrings.DS_TTP_FEEDBACK_SUBTITLE_MIN_MAX_FORMAT_PD_PD, DisplayStrings.DS_TTP_FEEDBACK_SUBTITLE_MIN_FORMAT_PD);
            if (subtitle == null || subtitle.isEmpty()) {
                dismiss();
                return;
            }
            super.setSubtitle(subtitle);
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_TTP_FEEDBACK_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CONTEXT, this.statContext).send();
            super.show();
        }
    }

    public int getCount() {
        return 3;
    }

    public void onConfigItem(int position, ItemDetails item) {
        switch (position) {
            case 0:
                item.setItem((int) DisplayStrings.DS_TTP_FEEDBACK_GOOD);
                return;
            case 1:
                item.setItem((int) DisplayStrings.DS_TTP_FEEDBACK_LOW);
                return;
            case 2:
                item.setItem((int) DisplayStrings.DS_TTP_FEEDBACK_HIGH);
                return;
            default:
                return;
        }
    }

    public void onClick(int position) {
        String action = "";
        switch (position) {
            case 0:
                action = "GOOD";
                break;
            case 1:
                action = AnalyticsEvents.ANALYTICS_EVENT_VALUE_LOW;
                break;
            case 2:
                action = AnalyticsEvents.ANALYTICS_EVENT_VALUE_HIGH;
                break;
        }
        clickedStat(action);
        dismiss();
    }

    public void onCancel(DialogInterface dialog) {
        clickedStat("CANCEL");
    }

    private void clickedStat(String action) {
        VenueData dest = NativeManager.getInstance().getLastDestinationVenueDataNTV();
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_TTP_FEEDBACK_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CONTEXT, this.statContext).addParam("ACTION", action).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_TIME_ESTIMATE, DriveToNativeManager.getInstance().getTimeToParkStrFormatNTV(DisplayStrings.DS_TTP_FEEDBACK_STAT_MIN_MAX_FORMAT_PD_PD, DisplayStrings.DS_TTP_FEEDBACK_STAT_MIN_FORMAT_PD)).addParam("DEST_LON", (long) dest.longitude).addParam("DEST_LAT", (long) dest.latitude).send();
    }
}
