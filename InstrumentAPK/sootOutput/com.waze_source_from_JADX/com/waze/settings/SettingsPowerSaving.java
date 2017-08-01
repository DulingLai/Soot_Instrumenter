package com.waze.settings;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsPowerSaving extends ActivityBase {
    public static final String ACTION_TAG = "battery_save";
    private WazeSettingsView mSaveWhenChargingSetting;
    private WazeSettingsView mShowNotificationSetting;
    private TitleBar mTitleBar;
    private WazeSettingsView mUseBatterySaveSetting;

    class C27511 implements SettingsToggleCallback {
        C27511() {
        }

        public void onToggle(boolean bOn) {
            SettingsPowerSaving.this.mShowNotificationSetting.setEnabled(bOn);
            SettingsPowerSaving.this.mSaveWhenChargingSetting.setEnabled(bOn);
            ConfigManager.getInstance().setConfigValueBool(427, bOn);
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_BATTERY_SAVER_SETTING_CLICKED).addParam("ACTION", bOn ? "ENABLE" : AnalyticsEvents.ANALYTICS_EVENT_VALUE_DISABLE).send();
        }
    }

    class C27522 implements SettingsToggleCallback {
        C27522() {
        }

        public void onToggle(boolean bOn) {
            ConfigManager.getInstance().setConfigValueBool(429, bOn);
        }
    }

    class C27533 implements SettingsToggleCallback {
        C27533() {
        }

        public void onToggle(boolean bOn) {
            ConfigManager.getInstance().setConfigValueBool(428, bOn);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_power_saving);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        this.mUseBatterySaveSetting = (WazeSettingsView) findViewById(C1283R.id.settingsUserBatterSaveMode);
        this.mShowNotificationSetting = (WazeSettingsView) findViewById(C1283R.id.settingsShowNotification);
        this.mSaveWhenChargingSetting = (WazeSettingsView) findViewById(C1283R.id.settingsUseWhenCharging);
        setDisplayStrings();
        boolean isEnabled = ConfigManager.getInstance().getConfigValueBool(427);
        this.mUseBatterySaveSetting.setValue(isEnabled);
        this.mShowNotificationSetting.setValue(ConfigManager.getInstance().getConfigValueBool(429));
        this.mSaveWhenChargingSetting.setValue(ConfigManager.getInstance().getConfigValueBool(428));
        this.mShowNotificationSetting.setEnabled(isEnabled);
        this.mSaveWhenChargingSetting.setEnabled(isEnabled);
        this.mUseBatterySaveSetting.setOnChecked(new C27511());
        this.mShowNotificationSetting.setOnChecked(new C27522());
        this.mSaveWhenChargingSetting.setOnChecked(new C27533());
    }

    private void setDisplayStrings() {
        this.mTitleBar.init((Activity) this, (int) DisplayStrings.DS_SAVE_BATTERY_MODE_SETTINGS_TITLE);
        this.mUseBatterySaveSetting.setText(DisplayStrings.displayString(DisplayStrings.DS_SAVE_BATTERY_MODE_SETTINGS_USE_SAVE_MODE));
        this.mShowNotificationSetting.setText(DisplayStrings.displayString(DisplayStrings.DS_SAVE_BATTERY_MODE_SETTINGS_SHOW_NOTIFICATION));
        this.mSaveWhenChargingSetting.setText(DisplayStrings.displayString(DisplayStrings.DS_SAVE_BATTERY_MODE_SETTINGS_USE_WHEN_CHARGING));
        ((TextView) findViewById(C1283R.id.settingsBatteryDetails)).setText(DisplayStrings.displayString(DisplayStrings.DS_SAVE_BATTERY_MODE_SETTINGS_DESCRIPTION));
    }
}
