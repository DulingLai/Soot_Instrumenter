package com.waze.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.NativeSoundManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingsNativeManager.SettingsValuesListener;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.settings.WazeSettingsView.OnSeekBarChangeListenerBasic;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.List;

public class SettingsSoundActivity extends ActivityBase {
    private static String[] VIBRATE_MODE_STRINGS = null;
    private static final String[] VIBRATE_MODE_VALUES = new String[]{"default", "on", LayoutManager.FRIENDS_CONTROL_MODE_OFF};
    private static final int VOICE_SEARCH_REQ = 10010;
    ConfigManager mCm;
    private List<ConfigItem> mConfigItems;
    private WazeSettingsView mCustomPrompts;
    private WazeSettingsView mMuteDuringCalls;
    NativeManager mNatMgr;
    private WazeSettingsView mPromptsSoundVolumeBar = null;
    private int mPromptsVolume;
    private WazeSettingsView mSettingsVibrateMode;
    private boolean mSpeakerChkBox;
    private WazeSettingsView mSpeakerChkBoxView = null;
    private int mVibrateSelection;
    private WazeSettingsView mVoiceSearch;
    private WazeSettingsView navLanguageView;
    private SettingsNativeManager settingsNativeManager;

    class C27631 implements OnCheckedChangeListener {
        C27631() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsSoundActivity.this.mSpeakerChkBox = isChecked;
            SettingsSoundActivity.this.setRoute2Speaker(isChecked);
        }
    }

    class C27642 implements GetIndex {
        C27642() {
        }

        public int fromConfig() {
            SettingsSoundActivity.this.mVibrateSelection = ConfigManager.getOptionIndex(SettingsSoundActivity.VIBRATE_MODE_VALUES, SettingsSoundActivity.this.mCm.getConfigValueString(124), 1);
            return SettingsSoundActivity.this.mVibrateSelection;
        }
    }

    class C27653 implements SettingsDialogListener {
        C27653() {
        }

        public void onComplete(int position) {
            SettingsSoundActivity.this.mVibrateSelection = position;
            SettingsSoundActivity.this.mCm.setConfigValueString(124, SettingsSoundActivity.VIBRATE_MODE_VALUES[position]);
            SettingsSoundActivity.this.mSettingsVibrateMode.setValueText(SettingsSoundActivity.VIBRATE_MODE_STRINGS[position]);
            SettingsSoundActivity.this.updateConfigItems();
        }
    }

    class C27664 implements OnClickListener {
        C27664() {
        }

        public void onClick(View v) {
            SettingsSoundActivity.this.startActivityForResult(new Intent(SettingsSoundActivity.this, SettingsNavigationGuidanceActivity.class), 0);
        }
    }

    class C27675 implements OnClickListener {
        C27675() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SEARCH_BY_VOICE).send();
            SettingsSoundActivity.this.startActivityForResult(new Intent(SettingsSoundActivity.this, SettingsVoiceSearchActivity.class), SettingsSoundActivity.VOICE_SEARCH_REQ);
        }
    }

    class C27686 implements OnClickListener {
        C27686() {
        }

        public void onClick(View v) {
            SettingsSoundActivity.this.startActivityForResult(new Intent(SettingsSoundActivity.this, SettingsCustomPrompts.class), 5421);
        }
    }

    class C27697 extends OnSeekBarChangeListenerBasic {
        C27697() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress) {
            SettingsSoundActivity.this.mPromptsVolume = progress;
            SettingsSoundActivity.this.setPromptsVolume(progress);
        }
    }

    class C27708 implements SettingsValuesListener {
        C27708() {
        }

        public void onComplete(SettingsValue[] values) {
            if (values == null) {
                SettingsSoundActivity.this.navLanguageView.setVisibility(8);
                return;
            }
            boolean someValueSelected = false;
            for (SettingsValue value : values) {
                if (value.isSelected) {
                    SettingsSoundActivity.this.navLanguageView.setValueText(value.display);
                    someValueSelected = true;
                    break;
                }
            }
            if (!someValueSelected && values.length > 0) {
                SettingsSoundActivity.this.navLanguageView.setValueText(values[0].display);
            }
        }
    }

    static String[] getVibrateModeStrings() {
        if (VIBRATE_MODE_STRINGS == null) {
            VIBRATE_MODE_STRINGS = new String[]{DisplayStrings.displayString(DisplayStrings.DS_PUSH_NOTIF_VIBRATE_DEFAULT), DisplayStrings.displayString(DisplayStrings.DS_PUSH_NOTIF_VIBRATE_ON), DisplayStrings.displayString(DisplayStrings.DS_PUSH_NOTIF_VIBRATE_OFF)};
        }
        return VIBRATE_MODE_STRINGS;
    }

    public void updateConfigItems() {
        this.mSpeakerChkBox = this.mCm.getConfigValueBool(157);
        this.mSpeakerChkBoxView.setValue(this.mSpeakerChkBox);
        this.mSpeakerChkBoxView.initToggleCallbackBoolean(157, new C27631());
        this.mMuteDuringCalls.setValue(this.mCm.getConfigValueBool(156));
        this.mMuteDuringCalls.initToggleCallbackBoolean(156, null);
        this.mPromptsVolume = this.mCm.getConfigValueInt(158);
        this.mPromptsSoundVolumeBar.setProgress(Integer.valueOf(this.mPromptsVolume));
        this.mSettingsVibrateMode.initSelectionNoTranslation(this, new C27642(), DisplayStrings.DS_PUSH_NOTIF_VIBRATE, VIBRATE_MODE_STRINGS, VIBRATE_MODE_VALUES, new C27653());
    }

    protected void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        this.mNatMgr = NativeManager.getInstance();
        this.mCm = ConfigManager.getInstance();
        setContentView(C1283R.layout.settings_sound);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_SOUND_AND_VOICE);
        this.settingsNativeManager = SettingsNativeManager.getInstance();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SOUND);
        this.navLanguageView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralNavigationGuidance);
        this.navLanguageView.setKeyText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_VOICE_DIRECTIONS));
        this.navLanguageView.setOnClickListener(new C27664());
        this.mVoiceSearch = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralSearchByVoice);
        this.mVoiceSearch.setKeyText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SEARCH_BY_VOICE));
        SettingsVoiceSearchActivity.updateVoiceSearchCaption(this.mVoiceSearch);
        if (NativeSoundManager.getInstance().isAsrV2Enabled()) {
            this.mVoiceSearch.setVisibility(8);
        }
        this.mVoiceSearch.setOnClickListener(new C27675());
        this.mSpeakerChkBoxView = (WazeSettingsView) findViewById(C1283R.id.settingsRoute2Speaker);
        this.mSpeakerChkBoxView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SOUND_ROUTE_2_SPEAKER));
        this.mMuteDuringCalls = (WazeSettingsView) findViewById(C1283R.id.settingsMuteDuringCalls);
        this.mMuteDuringCalls.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_MUTE_WAZE_DURING_CALLS));
        this.mCustomPrompts = (WazeSettingsView) findViewById(C1283R.id.settingsCustomPrompts);
        this.mCustomPrompts.setText(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_TITLE));
        this.mCustomPrompts.setValueText(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_SETTING_DESCRIPTION));
        ((TextView) findViewById(C1283R.id.settingsCustomPromptsNew)).setText(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_NEW));
        WazeSettingsView wazeSettingsView = this.mCustomPrompts;
        if (ConfigManager.getInstance().getConfigValueBool(414)) {
            i = 0;
        } else {
            i = 8;
        }
        wazeSettingsView.setVisibility(i);
        this.mCustomPrompts.setOnClickListener(new C27686());
        NativeManager nm = NativeManager.getInstance();
        this.mPromptsSoundVolumeBar = (WazeSettingsView) findViewById(C1283R.id.settingsPromptsVolume);
        this.mPromptsSoundVolumeBar.setText(nm.getLanguageString(DisplayStrings.DS_APP_VOLUME));
        this.mPromptsSoundVolumeBar.setOnSeekBarChangeListener(new C27697());
        this.mPromptsSoundVolumeBar.initSeekBarBaloonTip();
        getVibrateModeStrings();
        this.mSettingsVibrateMode = (WazeSettingsView) findViewById(C1283R.id.settingsVibrate);
        if (this.mCm.getConfigValueBool(176) && !NativeSoundManager.getInstance().isAsrV2Enabled()) {
            findViewById(C1283R.id.settingsMainSettingsVoice).setVisibility(0);
            ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsVoice)).initDrillDown(this, DisplayStrings.DS_VOICE_COMMANDS, SettingsVoiceCommandsActivity.class, 1000);
        }
    }

    protected void setRoute2Speaker(boolean value) {
        NativeSoundManager.getInstance().routeSoundToSpeaker(value);
    }

    protected void setPromptsVolume(int level) {
        NativeSoundManager nsm = NativeSoundManager.getInstance();
        this.mCm.setConfigValueLong(158, (long) level);
        nsm.setVolume(level);
    }

    protected void onResume() {
        super.onResume();
        updateConfigItems();
        this.settingsNativeManager.getNavigationGuidanceTypes(new C27708());
    }

    protected void onDestroy() {
        int vibrationVal = SettingsNativeManager.getInstance().getVibrationConfigNTV();
        SharedPreferences prefs = getSharedPreferences(SettingsNativeManager.SETTINGS_NOTIFICATION_CONFIG_NAME, 0);
        prefs.edit().putString(SettingsNativeManager.VIBRATE_VALUE, Integer.toString(vibrationVal)).apply();
        prefs.edit().commit();
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_SEARCH_REQ) {
            SettingsVoiceSearchActivity.updateVoiceSearchCaption(this.mVoiceSearch);
        }
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
