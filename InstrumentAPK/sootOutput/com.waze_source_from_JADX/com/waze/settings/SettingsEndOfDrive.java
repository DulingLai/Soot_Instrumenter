package com.waze.settings;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsEndOfDrive extends ActivityBase {
    private static final int CUSTOM_MESSAGE_CHAR_LIMIT = 80;
    public static final String EXTRA_AUTO_ENABLE_SWITCH = "auto_enable_switch_extra";
    private WazeSettingsView mCustomMessageSetting;
    private WazeSettingsView mEndOfDriveSetting;

    class C26821 implements InputFilter {
        C26821() {
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                    return "";
                }
            }
            return null;
        }
    }

    class C26832 implements OnCheckedChangeListener {
        C26832() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsEndOfDrive.this.mCustomMessageSetting.setEnabled(isChecked);
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_SETTING_CLICKED).addParam("ACTION", isChecked ? "ENABLE" : AnalyticsEvents.ANALYTICS_EVENT_VALUE_DISABLE).send();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_drive_reminder);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_END_OF_DRIVE_SETTINGS_TITLE);
        this.mEndOfDriveSetting = (WazeSettingsView) findViewById(C1283R.id.settingsEndOfDriveReminder);
        this.mCustomMessageSetting = (WazeSettingsView) findViewById(C1283R.id.settingsCustomMessage);
        this.mEndOfDriveSetting.setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_SETTINGS_SWITCH_TITLE));
        this.mCustomMessageSetting.setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_SETTINGS_CUSTOM_MESSAGE_TITLE));
        ((TextView) findViewById(C1283R.id.endOfDriveText)).setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_SETTINGS_SWITCH_DESCRIPTION));
        ((TextView) findViewById(C1283R.id.customMessageText)).setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_SETTINGS_CUSTOM_MESSAGE_DESCRIPTION));
        this.mCustomMessageSetting.setHintForEditText(DisplayStrings.displayString(DisplayStrings.DS_TAP_TO_ADD));
        this.mCustomMessageSetting.setCharacterLimitForEditText(80);
        this.mCustomMessageSetting.getEdit().setFilters(new InputFilter[]{new C26821()});
        if (getIntent() != null && getIntent().hasExtra(EXTRA_AUTO_ENABLE_SWITCH) && getIntent().getBooleanExtra(EXTRA_AUTO_ENABLE_SWITCH, false)) {
            ConfigManager.getInstance().setConfigValueBool(351, true);
        }
    }

    private void updateConfigItems() {
        this.mEndOfDriveSetting.setValue(ConfigManager.getInstance().getConfigValueBool(351));
        this.mEndOfDriveSetting.initToggleCallbackBoolean(351, new C26832());
        this.mCustomMessageSetting.setValueText(ConfigManager.getInstance().getConfigValueString(352));
        this.mCustomMessageSetting.setEnabled(ConfigManager.getInstance().getConfigValueBool(351));
    }

    protected void onDestroy() {
        ConfigManager.getInstance().setConfigValueString(352, this.mCustomMessageSetting.getValueText().toString());
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        updateConfigItems();
    }
}
