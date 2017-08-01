package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsAlertsOnRoute extends ActivityBase {
    private WazeSettingsView mAccidentSwitch;
    private WazeSettingsView mDangerZoneSwitch;
    private WazeSettingsView mHazardOnRoadSwitch;
    private WazeSettingsView mHazardOnShoulderSwitch;
    private WazeSettingsView mHeadlightReminderSwitch;
    private WazeSettingsView mOtherHazardSwitch;
    private WazeSettingsView mPoliceSwitch;
    private WazeSettingsView mSpeedCameraSwitch;
    private WazeSettingsView mWeatherHazardSwitch;

    class C26161 implements OnClickListener {

        class C26151 implements DialogInterface.OnClickListener {
            C26151() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    SettingsAlertsOnRoute.this.mDangerZoneSwitch.setValue(false);
                    ConfigManager.getInstance().setConfigValueBool(345, false);
                }
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TURN_OFF_DANGER_ZONE_ALERTS, "ACTION", which == 1 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM : "CANCEL");
            }
        }

        C26161() {
        }

        public void onClick(View v) {
            if (SettingsAlertsOnRoute.this.mDangerZoneSwitch.isOn()) {
                MsgBox.openConfirmDialogJavaCallback(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), NativeManager.getInstance().getLanguageString(ConfigManager.getInstance().getConfigValueInt(343) + DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_DANGER_ZONES_CONFIRMATION), false, new C26151(), NativeManager.getInstance().getLanguageString(357), NativeManager.getInstance().getLanguageString(344), -1, null, null, true, false, false);
                return;
            }
            SettingsAlertsOnRoute.this.mDangerZoneSwitch.setValue(true);
            ConfigManager.getInstance().setConfigValueBool(345, true);
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TURN_ON_DANGER_ZONE_ALERTS);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_alerts_on_route);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_ALERTS_ON_ROUTE);
        this.mPoliceSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRoutePolice);
        this.mAccidentSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteAccident);
        this.mHazardOnRoadSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteHazardOnRoad);
        this.mHazardOnShoulderSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteHazardOnShoulder);
        this.mWeatherHazardSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteWeatherHazard);
        this.mOtherHazardSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteOtherHazard);
        this.mSpeedCameraSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteSpeedCameras);
        this.mDangerZoneSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteDangerZone);
        this.mHeadlightReminderSwitch = (WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRouteHeadlightReminder);
        this.mPoliceSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_POLICE));
        this.mAccidentSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_ACCIDENT));
        this.mHazardOnRoadSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_HAZARD_ON_ROAD));
        this.mHazardOnShoulderSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_HARAZD_ON_SHOULDER));
        this.mWeatherHazardSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_WEATHER_HAZARD));
        this.mOtherHazardSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_OTHER_HAZARD));
        this.mSpeedCameraSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_SPEED_CAMERAS));
        this.mDangerZoneSwitch.setText(DisplayStrings.displayString(ConfigManager.getInstance().getConfigValueInt(343) + DisplayStrings.DS_NOTIFICATIONS_ON_ROUTE_DANGER_ZONES));
        if (ConfigValues.getBoolValue(416)) {
            this.mHeadlightReminderSwitch.setText(DisplayStrings.displayString(DisplayStrings.DS_NOTIFICATIONS_HEADLIGHT_REMINDER));
        } else {
            this.mHeadlightReminderSwitch.setVisibility(8);
        }
        this.mDangerZoneSwitch.setOnClickListener(new C26161());
        if ((ConfigManager.getInstance().getConfigValueBool(342) || ConfigManager.getInstance().getConfigValueBool(133)) && ConfigManager.getInstance().getConfigValueBool(344)) {
            this.mDangerZoneSwitch.setVisibility(0);
            this.mSpeedCameraSwitch.setPosition(0);
            return;
        }
        this.mDangerZoneSwitch.setVisibility(8);
        this.mSpeedCameraSwitch.setPosition(2);
    }

    private void updateConfigItems() {
        initializeSwitch(this.mPoliceSwitch, 257);
        initializeSwitch(this.mAccidentSwitch, 258);
        initializeSwitch(this.mHazardOnRoadSwitch, 259);
        initializeSwitch(this.mHazardOnShoulderSwitch, 260);
        initializeSwitch(this.mWeatherHazardSwitch, 261);
        initializeSwitch(this.mOtherHazardSwitch, 262);
        initializeSwitch(this.mSpeedCameraSwitch, 263);
        initializeSwitch(this.mDangerZoneSwitch, 345);
        initializeSwitch(this.mHeadlightReminderSwitch, 417);
    }

    private void initializeSwitch(WazeSettingsView switchView, int configIndex) {
        switchView.initToggleCallbackBoolean(configIndex, null);
        switchView.setValue(ConfigManager.getInstance().getConfigValueBool(configIndex));
    }

    protected void onResume() {
        super.onResume();
        updateConfigItems();
    }
}
