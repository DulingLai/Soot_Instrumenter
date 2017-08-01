package com.waze.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.facebook.appevents.AppEventsConstants;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class SettingsSpeedometerActivity extends ActivityBase {
    public static final int OFFSET_CODE = 1690;
    private static String[] OFFSET_OPTIONS = null;
    public static final String[] OFFSET_VALUES = new String[]{AppEventsConstants.EVENT_PARAM_VALUE_NO, "-5", "-10", "-15", "-20", "5", "10", "15"};
    public static final String SHOW_LIMIT_ALWAYS = "always";
    public static final String SHOW_LIMIT_EXCEEDING = "yes";
    public static final String SHOW_LIMIT_NO = "no";
    private static String[] SHOW_LIMIT_OPTIONS;
    private static final String[] SHOW_LIMIT_VALUES = new String[]{"no", "yes", "always", AppEventsConstants.EVENT_PARAM_VALUE_YES, AppEventsConstants.EVENT_PARAM_VALUE_NO};
    private static List<RunnableExecutor> mConfUpdatedEvents = new ArrayList();
    private static Object mEventsSync = new Object();
    private WazeSettingsView mAlert;
    private boolean mAlertConfigVal;
    private ConfigManager mCm;
    private int mLimitIndex;
    private NativeManager mNm;
    private WazeSettingsView mOffset;
    private String mOffsetConfigVal;
    private int mOffsetIndex;
    private WazeSettingsView mSpeedometer;
    private boolean mSpeedometerConfigVal;
    private WazeSettingsView mUserLimit;
    private String mUserLimitConfigVal;

    class C27711 implements OnCheckedChangeListener {
        C27711() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsSpeedometerActivity.this.speedometerState(isChecked);
        }
    }

    class C27722 implements OnCheckedChangeListener {
        C27722() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsSpeedometerActivity.this.mAlertConfigVal = isChecked;
        }
    }

    class C27733 implements OnClickListener {
        C27733() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(SettingsSpeedometerActivity.this, SettingsSpeedometerOffsetActivity.class);
            intent.putExtra("selected", SettingsSpeedometerActivity.this.mOffsetIndex);
            SettingsSpeedometerActivity.this.startActivityForResult(intent, 1690);
        }
    }

    class C27744 implements GetIndex {
        C27744() {
        }

        public int fromConfig() {
            SettingsSpeedometerActivity.this.mUserLimitConfigVal = SettingsSpeedometerActivity.this.mCm.getConfigValueString(111);
            SettingsSpeedometerActivity.this.mLimitIndex = ConfigManager.getOptionIndex(SettingsSpeedometerActivity.SHOW_LIMIT_VALUES, SettingsSpeedometerActivity.this.mUserLimitConfigVal, -1);
            if (SettingsSpeedometerActivity.this.mLimitIndex == 3) {
                SettingsSpeedometerActivity.this.mLimitIndex = 1;
            } else if (SettingsSpeedometerActivity.this.mLimitIndex == 4) {
                SettingsSpeedometerActivity.this.mLimitIndex = 0;
            }
            return SettingsSpeedometerActivity.this.mLimitIndex;
        }
    }

    class C27755 implements SettingsDialogListener {
        C27755() {
        }

        public void onComplete(int position) {
            SettingsSpeedometerActivity.this.mLimitIndex = position;
            SettingsSpeedometerActivity.this.mCm.setConfigValueString(111, SettingsSpeedometerActivity.SHOW_LIMIT_VALUES[position]);
            SettingsSpeedometerActivity.this.mUserLimit.setValueText(SettingsSpeedometerActivity.this.mNm.getLanguageString(SettingsSpeedometerActivity.getShowLimitOptions()[SettingsSpeedometerActivity.this.mLimitIndex]));
            SettingsSpeedometerActivity.this.speedometerState(SettingsSpeedometerActivity.this.mSpeedometerConfigVal);
        }
    }

    static String[] getOffsetOptions() {
        if (OFFSET_OPTIONS == null) {
            OFFSET_OPTIONS = new String[OFFSET_VALUES.length];
            for (int i = 0; i < OFFSET_VALUES.length; i++) {
                if (OFFSET_VALUES[i].equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                    OFFSET_OPTIONS[i] = DisplayStrings.displayString(3006);
                } else if (OFFSET_VALUES[i].startsWith("-")) {
                    OFFSET_OPTIONS[i] = DisplayStrings.displayStringF(DisplayStrings.DS_SPEEDOMETER_SETTINGS_OFFSET_PD_PS, Integer.valueOf(-Integer.parseInt(OFFSET_VALUES[i])), NativeManager.getInstance().speedUnitNTV());
                } else {
                    OFFSET_OPTIONS[i] = DisplayStrings.displayStringF(DisplayStrings.DS_SPEEDOMETER_SETTINGS_OFFSET_PD, Integer.valueOf(Integer.parseInt(OFFSET_VALUES[i])));
                }
            }
        }
        return OFFSET_OPTIONS;
    }

    static String[] getShowLimitOptions() {
        if (SHOW_LIMIT_OPTIONS == null) {
            SHOW_LIMIT_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SPEEDOMETER_SETTINGS_DONT_SHOW), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SPEEDOMETER_SETTINGS_SHOW_EXCEEDING), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SPEEDOMETER_SETTINGS_SHOW_ALWAYS)};
        }
        return SHOW_LIMIT_OPTIONS;
    }

    public static void registerOnConfChange(RunnableExecutor aEvent) {
        synchronized (mEventsSync) {
            mConfUpdatedEvents.add(aEvent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onDestroy() {
        synchronized (mEventsSync) {
            for (RunnableExecutor e : mConfUpdatedEvents) {
                e.run();
            }
        }
        super.onDestroy();
    }

    public void updateConfigItems() {
        int i = 0;
        this.mSpeedometerConfigVal = this.mCm.getConfigValueBool(110);
        this.mAlertConfigVal = this.mCm.getConfigValueBool(112);
        this.mUserLimitConfigVal = this.mCm.getConfigValueString(111);
        this.mOffsetConfigVal = String.valueOf(this.mCm.getConfigValueLong(113));
        this.mSpeedometer.setValue(this.mSpeedometerConfigVal);
        this.mSpeedometer.initToggleCallbackBoolean(110, new C27711());
        speedometerState(this.mSpeedometerConfigVal);
        this.mLimitIndex = SettingsUtils.findValueIndex(SHOW_LIMIT_VALUES, this.mUserLimitConfigVal);
        if (this.mLimitIndex == 3) {
            this.mLimitIndex = 1;
        } else if (this.mLimitIndex == 4) {
            this.mLimitIndex = 0;
        }
        this.mUserLimit.setValueText(this.mNm.getLanguageString(getShowLimitOptions()[this.mLimitIndex]));
        if (this.mSpeedometerConfigVal) {
            i = this.mLimitIndex;
        }
        limitState(i);
        this.mAlert.setValue(this.mAlertConfigVal);
        this.mAlert.initToggleCallbackBoolean(112, new C27722());
        this.mOffsetIndex = SettingsUtils.findValueIndex(OFFSET_VALUES, this.mOffsetConfigVal);
        this.mOffset.setValueText(this.mNm.getLanguageString(getOffsetOptions()[this.mOffsetIndex]));
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.mNm = NativeManager.getInstance();
        this.mCm = ConfigManager.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_speedometer);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_SPEEDOMETER);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_SETTINGS);
        this.mOffset = (WazeSettingsView) findViewById(C1283R.id.settingsSpeedometerOffset);
        this.mOffset.setKeyText(this.mNm.getLanguageString(3004));
        this.mOffset.setOnClickListener(new C27733());
        this.mSpeedometer = (WazeSettingsView) findViewById(C1283R.id.settingsSpeedometerSpeedometer);
        this.mSpeedometer.setText(this.mNm.getLanguageString(3001));
        this.mUserLimit = (WazeSettingsView) findViewById(C1283R.id.settingsSpeedometerLimit);
        this.mUserLimit.setText(this.mNm.getLanguageString(3002));
        this.mUserLimit.initSelectionNoTranslation(this, new C27744(), 3002, getShowLimitOptions(), SHOW_LIMIT_VALUES, new C27755());
        this.mAlert = (WazeSettingsView) findViewById(C1283R.id.settingsSpeedometerSound);
        this.mAlert.setText(this.mNm.getLanguageString(3003));
    }

    protected void onResume() {
        super.onResume();
        updateConfigItems();
    }

    private void speedometerState(boolean enabled) {
        this.mSpeedometerConfigVal = enabled;
        if (enabled) {
            this.mUserLimit.setVisibility(0);
            if (this.mLimitIndex != 0) {
                this.mAlert.setVisibility(0);
                this.mOffset.setVisibility(0);
                return;
            }
            this.mAlert.setVisibility(8);
            this.mOffset.setVisibility(8);
            return;
        }
        this.mUserLimit.setVisibility(8);
        this.mAlert.setVisibility(8);
        this.mOffset.setVisibility(8);
    }

    private void limitState(int index) {
        this.mUserLimitConfigVal = getShowLimitOptions()[index];
        if (index == 0) {
            this.mAlert.setVisibility(8);
            this.mOffset.setVisibility(8);
            return;
        }
        this.mAlert.setVisibility(0);
        this.mOffset.setVisibility(0);
    }
}
