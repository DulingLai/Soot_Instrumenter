package com.waze.settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.ConfigManager.IConfigUpdater;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class SettingsAdvancedActivity extends ActivityBase implements IConfigUpdater {
    protected static final int ACTIVITY_CODE = 1000;
    private static final int AUTO_LEARN_INDEX = 1;
    private static final int DISPLAY_MAP_INDEX = 0;
    public static final String screenName = "SettingsAdvanced";
    private WazeSettingsView NorthUpView;
    private WazeSettingsView TargetedAdsView;
    private WazeSettingsView autoZoomView;
    private WazeSettingsView displayView;
    private List<ConfigItem> mConfigItems;
    private NativeManager mNativeManager = AppService.getNativeManager();

    class C26143 implements SettingsToggleCallback {
        C26143() {
        }

        public void onToggle(boolean bIsChecked) {
            ConfigManager.getInstance().setConfigSwitchValue(0, bIsChecked);
        }
    }

    public void updateConfigItems(List<ConfigItem> configItems) {
        this.displayView.setValue(((ConfigItem) configItems.get(0)).getBooleanValue());
        this.displayView.initToggleCallback("SettingsAdvanced", this.mConfigItems, 0, null);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("waze", "start settings activity");
        setContentView(C1283R.layout.settings_advanced);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 96);
        this.mConfigItems = new ArrayList();
        this.mConfigItems.add(new ConfigItem("Map Icons", "Show on screen on tap", ""));
        this.mConfigItems.add(new ConfigItem("Alternative Routes", "Show Suggested", ""));
        this.mConfigItems.add(new ConfigItem("Routing", "Auto zoom", ""));
        ConfigManager.getInstance().getConfig(this, this.mConfigItems, "SettingsAdvanced");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_ADVANCED);
        this.displayView = (WazeSettingsView) findViewById(C1283R.id.settingsAdvancedDisplay);
        final NativeManager nm = NativeManager.getInstance();
        this.displayView.setText(nm.getLanguageString(97));
        this.NorthUpView = (WazeSettingsView) findViewById(C1283R.id.settingsAdvancedNorthUp);
        this.NorthUpView.setText(nm.getLanguageString(99));
        this.NorthUpView.setText(nm.getLanguageString(99));
        this.NorthUpView.setValue(nm.getNorthUp());
        this.NorthUpView.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean bIsChecked) {
                if (bIsChecked) {
                    nm.setNorthUp(1);
                } else {
                    nm.setNorthUp(0);
                }
            }
        });
        this.autoZoomView = (WazeSettingsView) findViewById(C1283R.id.settingsAdvancedAutoZoom);
        this.autoZoomView.setValue(nm.getAutoZoom());
        this.autoZoomView.setText(DisplayStrings.displayString(98));
        this.autoZoomView.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean bOn) {
                nm.setAutoZoom(bOn ? 1 : 0);
            }
        });
        ((WazeSettingsView) findViewById(C1283R.id.settingsAdvancedDataTransfer)).initDrillDown(this, 100, SettingsDataTransferActivity.class, 1000);
        this.TargetedAdsView = (WazeSettingsView) findViewById(C1283R.id.settingsAdvancedOptOutOfTargetedAds);
        this.TargetedAdsView.setText(nm.getLanguageString(DisplayStrings.DS_ADVANCED_SETTINGS_TARGETED_ADS));
        this.TargetedAdsView.setValue(ConfigManager.getInstance().getConfigSwitchValue(0));
        this.TargetedAdsView.setOnChecked(new C26143());
        this.TargetedAdsView.setVisibility(8);
        TextView explain = (TextView) findViewById(C1283R.id.settingsAdvancedOptOutOfTargetedAdsExplain);
        explain.setText(nm.getLanguageString(DisplayStrings.DS_ADVANCED_SETTINGS_TARGETED_ADS_EXPLANATION));
        explain.setVisibility(8);
    }
}
