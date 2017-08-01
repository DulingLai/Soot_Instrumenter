package com.waze.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.messages.QuestionData;
import com.waze.messages.QuestionData.ParkingPosition;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import java.util.ArrayList;
import java.util.List;

public class ParkingPinsFeedbackDialog extends BottomSheet implements Adapter, OnCancelListener {
    public static final String AVAILABLE_TAG = "available";
    public static final String GPS_TAG = "gpsLock";
    public static final float LAMBDA = 0.001f;
    private static String[] TAGS = new String[]{null, GPS_TAG, AVAILABLE_TAG};
    private Position[] pins = null;

    private static class Position extends ParkingPosition {
        public String tag;

        Position(String tag, ParkingPosition pos) {
            this.latitude = pos.latitude;
            this.longitude = pos.longitude;
            this.parkingTime = pos.parkingTime;
            this.tag = tag;
        }
    }

    public ParkingPinsFeedbackDialog() {
        super(AppService.getMainActivity(), DisplayStrings.DS_PARKING_PINS_FEEDBACK_TITLE, Mode.COLUMN_TEXT_ICON);
        super.setAdapter(this);
        super.setCancelListener(this);
        this.pins = getPins();
    }

    public void show() {
        AnalyticsBuilder analytics = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PARKING_PINS_FEEDBACK_DISPLAYED);
        addParams(analytics);
        analytics.send();
        super.show();
    }

    private void addParams(AnalyticsBuilder analytics) {
        int i = 0;
        Context context = AppService.getAppContext();
        for (Position pos : this.pins) {
            String name = tagName(pos.tag);
            analytics.addParam("LATITUDE_" + name, pos.latitude);
            analytics.addParam("LONGTITUDE_" + name, pos.longitude);
            analytics.addParam("TIME_" + name, (long) pos.parkingTime);
        }
        int counter = 0;
        String[] strArr = TAGS;
        int length = strArr.length;
        while (i < length) {
            String tag = strArr[i];
            if (validPos(new Position(tag, QuestionData.ReadParking(context, tag)))) {
                counter++;
                analytics.addParam("PRESENT_" + tagName(tag), AnalyticsEvents.ANALYTICS_EVENT_VALUE_T);
            } else {
                analytics.addParam("PRESENT_" + tagName(tag), AnalyticsEvents.ANALYTICS_EVENT_VALUE_F);
            }
            i++;
        }
        analytics.addParam("OPTIONS_AVAILABLE", (long) counter);
    }

    private String tagName(String tag) {
        return tag == null ? "default" : tag;
    }

    public int getCount() {
        return this.pins.length + 1;
    }

    public void onConfigItem(int position, ItemDetails item) {
        if (position < this.pins.length) {
            int res = C1283R.drawable.list_icon_parking_feedback_blue;
            int ds = DisplayStrings.DS_PARKING_PINS_FEEDBACK_BLUE;
            Position pos = this.pins[position];
            if (pos.tag != null) {
                if (pos.tag.equals(AVAILABLE_TAG)) {
                    ds = DisplayStrings.DS_PARKING_PINS_FEEDBACK_PURPLE;
                    res = C1283R.drawable.list_icon_parking_feedback_purple;
                } else if (pos.tag.equals(GPS_TAG)) {
                    ds = DisplayStrings.DS_PARKING_PINS_FEEDBACK_ORANGE;
                    res = C1283R.drawable.list_icon_parking_feedback_yellow;
                }
            }
            item.setItem(ds, res);
            return;
        }
        item.setItem((int) DisplayStrings.DS_PARKING_PINS_FEEDBACK_NONE, (int) C1283R.drawable.list_icon_place_wrong_details);
    }

    public void onClick(int position) {
        AnalyticsBuilder analytics = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PARKING_PINS_FEEDBACK_CLICKED);
        addParams(analytics);
        if (position < this.pins.length) {
            analytics.addParam("SELECTED", tagName(this.pins[position].tag));
        } else {
            analytics.addParam("SELECTED", "none");
        }
        analytics.send();
        dismiss();
    }

    public void onCancel(DialogInterface dialog) {
        AnalyticsBuilder analytics = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PARKING_PINS_FEEDBACK_CLICKED);
        addParams(analytics);
        analytics.addParam("SELECTED", "cancel");
        analytics.send();
    }

    static boolean validPos(Position pos) {
        if (pos.parkingTime < 1 || pos.latitude == -1.0d || pos.longitude == -1.0d) {
            return false;
        }
        if (Math.abs(pos.latitude) >= 0.0010000000474974513d || Math.abs(pos.longitude) >= 0.0010000000474974513d) {
            return true;
        }
        return false;
    }

    public static Position[] getPins() {
        List<Position> pins = new ArrayList();
        Context context = AppService.getAppContext();
        for (String tag : TAGS) {
            Position pos = new Position(tag, QuestionData.ReadParking(context, tag));
            if (validPos(pos)) {
                pins.add(pos);
            }
        }
        return (Position[]) pins.toArray(new Position[pins.size()]);
    }

    public static boolean shouldShowParkingPinsFeedback() {
        return getPins().length > 0;
    }
}
