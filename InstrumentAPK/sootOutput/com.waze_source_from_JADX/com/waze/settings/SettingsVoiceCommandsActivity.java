package com.waze.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.ConfigManager.IConfigUpdater;
import com.waze.NativeManager;
import com.waze.RequestPermissionActivity;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class SettingsVoiceCommandsActivity extends ActivityBase implements IConfigUpdater {
    private static final String[] ACTIVATION_OPTIONS = new String[]{"3 finger tap", "3 fingers or wave", "3 fingers or wave twice"};
    private static final String[] ACTIVATION_VALUES = new String[]{"no", "yes", "twice"};
    private static final int ENABLE_INDEX = 0;
    private static final int PROXIMITY_INDEX = 1;
    public static final int REQUEST_CODE = 1000;
    private WazeSettingsView enableView;
    ArrayList<ConfigItem> mConfigItems;
    private boolean mGranted = false;
    private NativeManager nativeManager = AppService.getNativeManager();
    private WazeSettingsView proximityEnabledView;
    final String screenName = "SettingsVoice";

    class C27861 implements OnCheckedChangeListener {
        C27861() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsVoiceCommandsActivity.this.voiceState(isChecked);
        }
    }

    public void updateConfigItems(List<ConfigItem> configItems) {
        WazeSettingsView wazeSettingsView = this.enableView;
        boolean z = ConfigValues.getBoolValue(187) && this.mGranted;
        wazeSettingsView.setValue(z);
        this.enableView.initToggleCallbackBoolean(187, new C27861());
        this.proximityEnabledView.initSelection(this, "SettingsVoice", configItems, 142, ACTIVATION_OPTIONS, ACTIVATION_VALUES, 1);
        String activationValue = ((ConfigItem) configItems.get(1)).getStringValue();
        if (activationValue.equalsIgnoreCase("no")) {
            this.proximityEnabledView.setValueText(this.nativeManager.getLanguageString(DisplayStrings.DS_3_FINGER_TAP));
        } else if (activationValue.equalsIgnoreCase("yes")) {
            this.proximityEnabledView.setValueText(this.nativeManager.getLanguageString(DisplayStrings.DS_3_FINGERS_OR_WAVE));
        } else if (activationValue.equalsIgnoreCase("twice")) {
            this.proximityEnabledView.setValueText(this.nativeManager.getLanguageString(DisplayStrings.DS_3_FINGERS_OR_WAVE_TWICE));
        }
    }

    private void voiceState(boolean isChecked) {
        if (!this.mGranted && isChecked) {
            Intent permissionsIntent = new Intent(this, RequestPermissionActivity.class);
            permissionsIntent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.RECORD_AUDIO"});
            startActivityForResult(permissionsIntent, 1000);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1000) {
            return;
        }
        if (resultCode == -1) {
            this.mGranted = true;
            return;
        }
        this.mGranted = false;
        this.enableView.setValue(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_voice);
        if (ActivityCompat.checkSelfPermission(AppService.getAppContext(), "android.permission.RECORD_AUDIO") != 0) {
            this.mGranted = false;
        } else {
            this.mGranted = true;
        }
        this.mConfigItems = new ArrayList();
        this.mConfigItems.add(new ConfigItem("ASR", "Enabled", ""));
        this.mConfigItems.add(new ConfigItem("ASR", "Proximity activation", ""));
        ConfigManager.getInstance().getConfig(this, this.mConfigItems, "SettingsVoice");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_VOICE);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_VOICE_COMMANDS);
        this.enableView = (WazeSettingsView) findViewById(C1283R.id.settingsVoiceEnabled);
        this.enableView.setText((int) DisplayStrings.DS_ENABLE);
        this.proximityEnabledView = (WazeSettingsView) findViewById(C1283R.id.settingsVoiceActivation);
        ((TextView) findViewById(C1283R.id.settingsVoiceTextTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOU_CAN_USE_VOICE_COMMANDS_FOR_FOLLOWING_FUNCTIONSC));
        ((TextView) findViewById(C1283R.id.settingsVoiceText1)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_VOICE_COMMAND_BULLET1_TEXT));
        ((TextView) findViewById(C1283R.id.settingsVoiceText2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_VOICE_COMMAND_BULLET2_TEXT));
        ((TextView) findViewById(C1283R.id.settingsVoiceText3)).setText(this.nativeManager.getLanguageString(345));
        ((TextView) findViewById(C1283R.id.settingsVoiceText4)).setText(this.nativeManager.getLanguageString(335));
    }
}
