package com.waze.reports;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.abaltatech.mcp.weblink.sdk.widgets.WLContextUtils;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.map.CanvasFont;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.DurationTimePickDialog;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class EditOpeningHoursFragment extends Fragment {
    public static final String OPENING_HOURS = "opening_hours";
    private static final String TAG = EditOpeningHoursFragment.class.getName();
    private static String friday;
    private static String monday;
    private static String saturday;
    private static String sunday = null;
    private static String thursday;
    private static String tuesday;
    private static String wednesday;
    private LayoutInflater inflater;
    private Button mAddButton;
    private WazeSettingsView mAllDayCheck;
    private ArrayList<OpeningHours> mArrOpeningHours;
    private int[] mDayButtonIds = new int[]{C1283R.id.editOpeningHoursAdd1, C1283R.id.editOpeningHoursAdd2, C1283R.id.editOpeningHoursAdd3, C1283R.id.editOpeningHoursAdd4, C1283R.id.editOpeningHoursAdd5, C1283R.id.editOpeningHoursAdd6, C1283R.id.editOpeningHoursAdd7};
    private boolean[] mDayButtonPressed = new boolean[]{false, false, false, false, false, false, false};
    private String[] mDayLetter;
    private EditText mEditTextFrom;
    private EditText mEditTextTo;
    private ViewGroup mLinesContainer;
    private NativeManager mNm;
    private SettingsTitleText mOpeningHours;
    private View f92r;

    class C24421 implements OnClickListener {
        C24421() {
        }

        public void onClick(View v) {
            ((EditPlaceFlowActivity) EditOpeningHoursFragment.this.getActivity()).doneOpeningHours(EditOpeningHoursFragment.this.mArrOpeningHours);
        }
    }

    class C24432 implements OnTouchListener {
        C24432() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 1) {
                Button dayButton = (Button) v;
                int d = ((Integer) v.getTag()).intValue();
                EditOpeningHoursFragment.this.mDayButtonPressed[d] = !EditOpeningHoursFragment.this.mDayButtonPressed[d];
                dayButton.setPressed(EditOpeningHoursFragment.this.mDayButtonPressed[d]);
                EditOpeningHoursFragment.this.setAddButtonEnabledState();
            }
            return true;
        }
    }

    class C24463 implements OnClickListener {

        class C24452 implements OnDismissListener {
            C24452() {
            }

            public void onDismiss(DialogInterface dialog) {
                EditOpeningHoursFragment.this.refreashPressedState();
            }
        }

        C24463() {
        }

        @TargetApi(11)
        public void onClick(View v) {
            Context wrapper;
            final EditText toSet = (EditText) v;
            String curTime = toSet.getText().toString();
            int hour = Integer.parseInt(curTime.substring(0, curTime.indexOf(58)));
            int minute = Integer.parseInt(curTime.substring(curTime.indexOf(58) + 1));
            if (VERSION.SDK_INT < 11) {
                wrapper = new ContextThemeWrapper(EditOpeningHoursFragment.this.getActivity(), WLContextUtils.DefaultContextMenuThemeInPresentationMode);
            } else {
                wrapper = new ContextThemeWrapper(EditOpeningHoursFragment.this.getActivity(), 16973935);
            }
            String title = "Set time";
            if (v.getId() == C1283R.id.editOpeningHoursAddFrom) {
                title = EditOpeningHoursFragment.this.mNm.getLanguageString(DisplayStrings.DS_OPENING_TIME);
            } else if (v.getId() == C1283R.id.editOpeningHoursAddTo) {
                title = EditOpeningHoursFragment.this.mNm.getLanguageString(DisplayStrings.DS_CLOSING_TIME);
            } else {
                title = EditOpeningHoursFragment.this.mNm.getLanguageString(DisplayStrings.DS_TIME);
            }
            Dialog pick = new DurationTimePickDialog(wrapper, new OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    toSet.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(hourOfDay), Integer.valueOf(minute)}));
                }
            }, hour, minute * 5, true, 5, title);
            pick.show();
            pick.setOnDismissListener(new C24452());
        }
    }

    class C24474 implements SettingsToggleCallback {
        C24474() {
        }

        public void onToggle(boolean bIsChecked) {
            boolean z = true;
            float f = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            EditOpeningHoursFragment.this.mEditTextTo.setEnabled(!bIsChecked);
            EditOpeningHoursFragment.this.mEditTextTo.setAlpha(bIsChecked ? CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR : 1.0f);
            EditOpeningHoursFragment.this.mEditTextTo.setText(bIsChecked ? "00:00" : "19:00");
            EditText access$400 = EditOpeningHoursFragment.this.mEditTextFrom;
            if (bIsChecked) {
                z = false;
            }
            access$400.setEnabled(z);
            access$400 = EditOpeningHoursFragment.this.mEditTextFrom;
            if (!bIsChecked) {
                f = 1.0f;
            }
            access$400.setAlpha(f);
            EditOpeningHoursFragment.this.mEditTextFrom.setText(bIsChecked ? "00:00" : "10:00");
        }
    }

    class C24485 implements OnClickListener {
        C24485() {
        }

        public void onClick(View v) {
            int i = 1;
            if (EditOpeningHoursFragment.this.mDayButtonPressed[0] || EditOpeningHoursFragment.this.mDayButtonPressed[1] || EditOpeningHoursFragment.this.mDayButtonPressed[2] || EditOpeningHoursFragment.this.mDayButtonPressed[3] || EditOpeningHoursFragment.this.mDayButtonPressed[4] || EditOpeningHoursFragment.this.mDayButtonPressed[5] || EditOpeningHoursFragment.this.mDayButtonPressed[6]) {
                OpeningHours oh = new OpeningHours();
                int[] iArr;
                int i2;
                int[] iArr2;
                if (OpeningHours.FIRST_DAY_IS_SUNDAY) {
                    oh.days[0] = EditOpeningHoursFragment.this.mDayButtonPressed[0] ? 1 : 0;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[1]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[1] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[2]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[2] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[3]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[3] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[4]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[4] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[5]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[5] = i2;
                    iArr2 = oh.days;
                    if (!EditOpeningHoursFragment.this.mDayButtonPressed[6]) {
                        i = 0;
                    }
                    iArr2[6] = i;
                } else {
                    oh.days[1] = EditOpeningHoursFragment.this.mDayButtonPressed[0] ? 1 : 0;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[1]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[2] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[2]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[3] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[3]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[4] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[4]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[5] = i2;
                    iArr = oh.days;
                    if (EditOpeningHoursFragment.this.mDayButtonPressed[5]) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    iArr[6] = i2;
                    iArr2 = oh.days;
                    if (!EditOpeningHoursFragment.this.mDayButtonPressed[6]) {
                        i = 0;
                    }
                    iArr2[0] = i;
                }
                oh.from = EditOpeningHoursFragment.this.mEditTextFrom.getText().toString();
                oh.to = EditOpeningHoursFragment.this.mEditTextTo.getText().toString();
                EditOpeningHoursFragment.this.mArrOpeningHours.add(oh);
                EditOpeningHoursFragment.this.addLine(oh);
            }
        }
    }

    public void setOpeningHours(ArrayList<OpeningHours> arrOpHrs) {
        this.mArrOpeningHours = arrOpHrs;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int d;
        super.onCreateView(inflater, container, savedInstanceState);
        this.mNm = NativeManager.getInstance();
        if (savedInstanceState != null) {
            for (d = 0; d < 7; d++) {
                this.mDayButtonPressed[d] = savedInstanceState.getBoolean(TAG + ".mDayButtonPressed." + d, false);
            }
            this.mArrOpeningHours = savedInstanceState.getParcelableArrayList(TAG + ".mArrOpeningHours");
        }
        if (sunday == null) {
            SimpleDateFormat formatLetterDay = new SimpleDateFormat("EEEEE", new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV()));
            Calendar c = Calendar.getInstance();
            c.set(7, 1);
            sunday = formatLetterDay.format(c.getTime());
            c.set(7, 2);
            monday = formatLetterDay.format(c.getTime());
            c.set(7, 3);
            tuesday = formatLetterDay.format(c.getTime());
            c.set(7, 4);
            wednesday = formatLetterDay.format(c.getTime());
            c.set(7, 5);
            thursday = formatLetterDay.format(c.getTime());
            c.set(7, 6);
            friday = formatLetterDay.format(c.getTime());
            c.set(7, 7);
            saturday = formatLetterDay.format(c.getTime());
        }
        if (OpeningHours.FIRST_DAY_IS_SUNDAY) {
            this.mDayLetter = new String[]{sunday, monday, tuesday, wednesday, thursday, friday, saturday};
        } else {
            this.mDayLetter = new String[]{monday, tuesday, wednesday, thursday, friday, saturday, sunday};
        }
        this.inflater = inflater;
        this.f92r = inflater.inflate(C1283R.layout.edit_place_opening_hours, container, false);
        setUpFragment();
        if (this.mArrOpeningHours == null) {
            this.mArrOpeningHours = new ArrayList();
        }
        Iterator it = this.mArrOpeningHours.iterator();
        while (it.hasNext()) {
            addLine((OpeningHours) it.next());
        }
        if (this.mArrOpeningHours.isEmpty()) {
            this.mOpeningHours.setVisibility(8);
            if (savedInstanceState == null) {
                for (d = 0; d < 5; d++) {
                    this.mDayButtonPressed[d] = true;
                }
            }
        }
        refreashPressedState();
        setAddButtonEnabledState();
        return this.f92r;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int d = 0; d < 7; d++) {
            outState.putBoolean(TAG + ".mDayButtonPressed." + d, this.mDayButtonPressed[d]);
        }
        outState.putParcelableArrayList(TAG + ".mArrOpeningHours", this.mArrOpeningHours);
    }

    private void setUpFragment() {
        TitleBar titleBar = (TitleBar) this.f92r.findViewById(C1283R.id.theTitleBar);
        titleBar.init(getActivity(), this.mNm.getLanguageString(DisplayStrings.DS_EDIT_PLACE), 0);
        titleBar.setCloseImageResource(C1283R.drawable.confirm_icon);
        titleBar.setOnClickCloseListener(new C24421());
        this.mOpeningHours = (SettingsTitleText) this.f92r.findViewById(C1283R.id.editOpeningHoursTitle);
        this.mOpeningHours.setText(this.mNm.getLanguageString(DisplayStrings.DS_OPENING_HOURS));
        ((SettingsTitleText) this.f92r.findViewById(C1283R.id.editOpeningHoursAddTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_ADD_HOURS));
        OnTouchListener onTouchListener = new C24432();
        for (int d = 0; d < 7; d++) {
            Button dayButton = (Button) this.f92r.findViewById(this.mDayButtonIds[d]);
            dayButton.setText(this.mDayLetter[d]);
            dayButton.setTag(Integer.valueOf(d));
            dayButton.setOnTouchListener(onTouchListener);
        }
        OnClickListener onTimeClickListener = new C24463();
        this.mEditTextFrom = (EditText) this.f92r.findViewById(C1283R.id.editOpeningHoursAddFrom);
        this.mEditTextFrom.setOnClickListener(onTimeClickListener);
        this.mEditTextFrom.setText("10:00");
        this.mEditTextTo = (EditText) this.f92r.findViewById(C1283R.id.editOpeningHoursAddTo);
        this.mEditTextTo.setOnClickListener(onTimeClickListener);
        this.mEditTextTo.setText("19:00");
        ((WazeTextView) this.f92r.findViewById(C1283R.id.editOpeningHoursTextTo)).setText(" " + this.mNm.getLanguageString(DisplayStrings.DS_HOURS_TO) + " ");
        this.mAllDayCheck = (WazeSettingsView) this.f92r.findViewById(C1283R.id.editOpeningHoursAllDayCheck);
        this.mAllDayCheck.setText(this.mNm.getLanguageString(DisplayStrings.DS_OPEN_24_HOURS));
        this.mAllDayCheck.setValue(false);
        this.mAllDayCheck.setOnChecked(new C24474());
        this.mAddButton = (Button) this.f92r.findViewById(C1283R.id.editOpeningHoursAddButton);
        this.mAddButton.setText(this.mNm.getLanguageString(22));
        this.mAddButton.setOnClickListener(new C24485());
        this.mLinesContainer = (ViewGroup) this.f92r.findViewById(C1283R.id.editOpeningHoursLinesContainer);
        setAddButtonEnabledState();
    }

    void setAddButtonEnabledState() {
        boolean z = false;
        Button button = this.mAddButton;
        if (this.mDayButtonPressed[0] || this.mDayButtonPressed[1] || this.mDayButtonPressed[2] || this.mDayButtonPressed[3] || this.mDayButtonPressed[4] || this.mDayButtonPressed[5] || this.mDayButtonPressed[6]) {
            z = true;
        }
        button.setEnabled(z);
    }

    public void refreashPressedState() {
        for (int d = 0; d < 7; d++) {
            ((Button) this.f92r.findViewById(this.mDayButtonIds[d])).setPressed(this.mDayButtonPressed[d]);
        }
    }

    protected void addLine(final OpeningHours oh) {
        final View newLine = this.inflater.inflate(C1283R.layout.opening_hours_line, this.mLinesContainer, false);
        ((WazeTextView) newLine.findViewById(C1283R.id.editOpeningHoursLineDays)).setText(oh.getDaysString());
        ((WazeTextView) newLine.findViewById(C1283R.id.editOpeningHoursLineHours)).setText(oh.getHoursString());
        newLine.findViewById(C1283R.id.editOpeningHoursLineClear).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditOpeningHoursFragment.this.mLinesContainer.removeView(newLine);
                EditOpeningHoursFragment.this.mArrOpeningHours.remove(oh);
                EditOpeningHoursFragment.this.mOpeningHours.setVisibility(EditOpeningHoursFragment.this.mArrOpeningHours.isEmpty() ? 8 : 0);
            }
        });
        newLine.setTag(oh);
        this.mLinesContainer.addView(newLine);
        newLine.getLayoutParams().height = (int) (64.0f * getResources().getDisplayMetrics().density);
        this.mOpeningHours.setVisibility(0);
    }
}
