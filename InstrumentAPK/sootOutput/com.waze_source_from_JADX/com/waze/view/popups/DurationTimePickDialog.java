package com.waze.view.popups;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.List;

public class DurationTimePickDialog extends TimePickerDialog {
    int _maxHour = 24;
    int _maxMinute = 60;
    int _minHour = 0;
    int _minMinute = 0;
    boolean _rangeSet = false;
    final int increment;
    final OnTimeSetListener mCallback;
    TimePicker mTimePicker;

    public DurationTimePickDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView, int increment, String title) {
        super(context, callBack, hourOfDay, minute / increment, is24HourView);
        this.mCallback = callBack;
        if (VERSION.SDK_INT > 23) {
            this.increment = 1;
        } else {
            this.increment = increment;
        }
        setTitle(title);
    }

    public DurationTimePickDialog(Context context, int theme, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView, int increment) {
        super(context, theme, callBack, hourOfDay, minute / increment, is24HourView);
        this.mCallback = callBack;
        if (VERSION.SDK_INT > 23) {
            this.increment = 1;
        } else {
            this.increment = increment;
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        if (this.mCallback != null && this.mTimePicker != null) {
            this.mTimePicker.clearFocus();
            this.mCallback.onTimeSet(this.mTimePicker, this.mTimePicker.getCurrentHour().intValue(), this.mTimePicker.getCurrentMinute().intValue() * this.increment);
        }
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        if (this._rangeSet) {
            int realMinute = minute * this.increment;
            if (hourOfDay < this._minHour || (hourOfDay == this._minHour && realMinute < this._minMinute)) {
                this.mTimePicker.setCurrentHour(Integer.valueOf(this._minHour));
                this.mTimePicker.setCurrentMinute(Integer.valueOf(this._minMinute / this.increment));
            }
            if (hourOfDay > this._maxHour || (hourOfDay == this._maxHour && realMinute > this._maxMinute)) {
                this.mTimePicker.setCurrentHour(Integer.valueOf(this._maxHour));
                this.mTimePicker.setCurrentMinute(Integer.valueOf(this._maxMinute / this.increment));
            }
        }
    }

    protected void onStop() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Class<?> rClass = Class.forName("com.android.internal.R$id");
            this.mTimePicker = (TimePicker) findViewById(rClass.getField("timePicker").getInt(null));
            NumberPicker mMinuteSpinner = (NumberPicker) this.mTimePicker.findViewById(rClass.getField("minute").getInt(null));
            mMinuteSpinner.setMinValue(0);
            mMinuteSpinner.setMaxValue((60 / this.increment) - 1);
            List<String> displayedValues = new ArrayList();
            int i = 0;
            while (i < 60) {
                displayedValues.add(String.format("%02d", new Object[]{Integer.valueOf(i)}));
                i += this.increment;
            }
            mMinuteSpinner.setDisplayedValues((String[]) displayedValues.toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTime(int hourOfDay, int minuteOfHour) {
        super.updateTime(hourOfDay, minuteOfHour / this.increment);
    }

    public void setRange(int minHour, int minMinute, int maxHour, int maxMinute) {
        this._rangeSet = true;
        this._minHour = minHour;
        this._minMinute = minMinute;
        this._maxHour = maxHour;
        this._maxMinute = maxMinute;
    }
}
