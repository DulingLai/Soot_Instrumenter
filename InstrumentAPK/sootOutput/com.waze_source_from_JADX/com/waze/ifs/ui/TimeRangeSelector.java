package com.waze.ifs.ui;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.Utils;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.DurationTimePickDialog;

public class TimeRangeSelector {
    private static int MIN_INTERVAL = 5;
    private boolean mCanceled = true;
    private Context mContext;
    private boolean mCurrentStart = true;
    private TimePickerDialog mDialog_do_not_access_directly;
    private Button mEndButton;
    private int mEndHours;
    private int mEndMinutes;
    private Button mStartButton;
    private int mStartHours;
    private int mStartMinutes;

    class C17563 implements OnClickListener {
        C17563() throws  {
        }

        public void onClick(View v) throws  {
            if (!TimeRangeSelector.this.mCurrentStart) {
                TimeRangeSelector.this.getDialog().findViewById(C1283R.id.timeRangeSelectorLabelTo).playSoundEffect(0);
                TimeRangeSelector.this.mCurrentStart = true;
            }
            TimeRangeSelector.this.setPressed(TimeRangeSelector.this.mStartButton, true);
            TimeRangeSelector.this.setPressed(TimeRangeSelector.this.mEndButton, false);
            TimeRangeSelector.this.getDialog().updateTime(TimeRangeSelector.this.mStartHours, TimeRangeSelector.this.mStartMinutes);
        }
    }

    class C17574 implements OnClickListener {
        C17574() throws  {
        }

        public void onClick(View v) throws  {
            if (TimeRangeSelector.this.mCurrentStart) {
                TimeRangeSelector.this.getDialog().findViewById(C1283R.id.timeRangeSelectorLabelTo).playSoundEffect(0);
                TimeRangeSelector.this.mCurrentStart = false;
            }
            TimeRangeSelector.this.setPressed(TimeRangeSelector.this.mStartButton, false);
            TimeRangeSelector.this.setPressed(TimeRangeSelector.this.mEndButton, true);
            TimeRangeSelector.this.getDialog().updateTime(TimeRangeSelector.this.mEndHours, TimeRangeSelector.this.mEndMinutes);
        }
    }

    class C17585 implements OnTouchListener {
        C17585() throws  {
        }

        public boolean onTouch(View $r1, MotionEvent event) throws  {
            $r1.performClick();
            return true;
        }
    }

    public TimeRangeSelector(Context $r1, int $i0, int $i1) throws  {
        if (VERSION.SDK_INT > 23) {
            MIN_INTERVAL = 1;
        }
        this.mContext = $r1;
        this.mStartHours = Utils.getHours($i0);
        this.mStartMinutes = Utils.getMinutes($i0);
        this.mEndHours = Utils.getHours($i1);
        this.mEndMinutes = Utils.getMinutes($i1);
    }

    private TimePickerDialog getDialog() throws  {
        if (this.mDialog_do_not_access_directly == null) {
            final C17531 $r1 = r0;
            C17531 c17531 = new DurationTimePickDialog(this.mContext, 16973939, null, this.mStartHours, this.mStartMinutes, NativeManager.getInstance().is24HrClock(), MIN_INTERVAL) {
                public void onTimeChanged(TimePicker view, int $i0, int $i1) throws  {
                    if (TimeRangeSelector.this.mCurrentStart) {
                        TimeRangeSelector.this.mStartHours = $i0;
                        TimeRangeSelector.this.mStartMinutes = TimeRangeSelector.MIN_INTERVAL * $i1;
                        TimeRangeSelector.this.updateButtonText(TimeRangeSelector.this.mStartButton, TimeRangeSelector.this.mStartHours, TimeRangeSelector.this.mStartMinutes);
                        return;
                    }
                    TimeRangeSelector.this.mEndHours = $i0;
                    TimeRangeSelector.this.mEndMinutes = TimeRangeSelector.MIN_INTERVAL * $i1;
                    TimeRangeSelector.this.updateButtonText(TimeRangeSelector.this.mEndButton, TimeRangeSelector.this.mEndHours, TimeRangeSelector.this.mEndMinutes);
                }
            };
            $r1.setOnShowListener(new OnShowListener() {

                class C17541 implements OnClickListener {
                    C17541() throws  {
                    }

                    public void onClick(View v) throws  {
                        TimeRangeSelector.this.mCanceled = true;
                        if (TimeRangeSelector.this.mStartHours > TimeRangeSelector.this.mEndHours || (TimeRangeSelector.this.mStartHours == TimeRangeSelector.this.mEndHours && TimeRangeSelector.this.mStartMinutes >= TimeRangeSelector.this.mEndMinutes)) {
                            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_TIME_RANGE_SELECTOR_ERROR_TITLE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_TIME_RANGE_SELECTOR_ERROR_TEXT), false);
                            return;
                        }
                        TimeRangeSelector.this.mCanceled = false;
                        TimeRangeSelector.this.mDialog_do_not_access_directly.dismiss();
                    }
                }

                public void onShow(DialogInterface dialog) throws  {
                    $r1.getButton(-1).setOnClickListener(new C17541());
                }
            });
            $r1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.mDialog_do_not_access_directly = $r1;
        }
        return this.mDialog_do_not_access_directly;
    }

    public void show() throws  {
        TimePickerDialog $r2 = getDialog();
        View $r6 = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(C1283R.layout.time_range_selector, null);
        ((TextView) $r6.findViewById(C1283R.id.timeRangeSelectorLabelTo)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_TIME_RANGE_SELECTOR_TO));
        if (AppService.getNativeManager().getLanguageRtl()) {
            this.mStartButton = (Button) $r6.findViewById(C1283R.id.timeRangeSelectorEndButton);
            this.mEndButton = (Button) $r6.findViewById(C1283R.id.timeRangeSelectorStartButton);
        } else {
            this.mStartButton = (Button) $r6.findViewById(C1283R.id.timeRangeSelectorStartButton);
            this.mEndButton = (Button) $r6.findViewById(C1283R.id.timeRangeSelectorEndButton);
        }
        updateButtonText(this.mStartButton, this.mStartHours, this.mStartMinutes);
        updateButtonText(this.mEndButton, this.mEndHours, this.mEndMinutes);
        if (NativeManager.getInstance().is24HrClock()) {
            this.mStartButton.setTextSize(1, 22.0f);
            this.mEndButton.setTextSize(1, 22.0f);
        }
        $r2.setCustomTitle($r6);
        Button $r11 = this.mStartButton;
        $r11.setOnClickListener(new C17563());
        this.mEndButton.setOnClickListener(new C17574());
        C17585 c17585 = new C17585();
        Button button = this.mStartButton;
        $r11 = button;
        button.setOnTouchListener(c17585);
        button = this.mEndButton;
        $r11 = button;
        button.setOnTouchListener(c17585);
        this.mCurrentStart = true;
        button = this.mStartButton;
        button.performClick();
        $r2.show();
    }

    private void updateButtonText(Button $r1, int $i0, int $i1) throws  {
        $r1.setText(Utils.formatTime(this.mContext, (($i0 * 60) + $i1) * 60));
    }

    public boolean finishedOk() throws  {
        return !this.mCanceled;
    }

    public int getStartTime() throws  {
        return ((this.mStartHours * 60) + this.mStartMinutes) * 60;
    }

    public int getEndTime() throws  {
        return ((this.mEndHours * 60) + this.mEndMinutes) * 60;
    }

    public void setOnDismissListener(OnDismissListener $r1) throws  {
        getDialog().setOnDismissListener($r1);
    }

    private void setPressed(Button $r1, boolean $z0) throws  {
        int $i0;
        $r1.setPressed($z0);
        if ($z0) {
            $i0 = -15444374;
        } else {
            $i0 = -7237231;
        }
        $r1.setTextColor($i0);
    }
}
