package com.waze.settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.facebook.appevents.AppEventsConstants;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsShowOnMapActivity extends ActivityBase {
    private WazeSettingsView accidentsView;
    private WazeSettingsView chitchatsView;
    private boolean enableToggleConstruction;
    private WazeSettingsView hazardsView;
    private ConfigManager mCm;
    private NativeManager mNativeManager;
    private boolean mShowOthers;
    private boolean mShowRoadGoodies;
    private boolean mShowSpeedCams;
    private boolean mShowTrafficLoad;
    private WazeSettingsView policeView;
    private boolean[] reportTypes;
    private WazeSettingsView roadConstructionsView;
    private WazeSettingsView roadGoodiesView;
    private WazeSettingsView speedcamsView;
    private WazeSettingsView trafficJamsView;
    private WazeSettingsView trafficLoadsView;
    private WazeSettingsView wazersView;

    class C27541 implements OnCheckedChangeListener {
        C27541() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsShowOnMapActivity.this.mShowOthers = isChecked;
        }
    }

    class C27552 implements OnCheckedChangeListener {
        C27552() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsShowOnMapActivity.this.mShowTrafficLoad = isChecked;
        }
    }

    class C27563 implements OnCheckedChangeListener {
        C27563() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsShowOnMapActivity.this.mShowSpeedCams = isChecked;
        }
    }

    class C27574 implements OnCheckedChangeListener {
        C27574() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsShowOnMapActivity.this.mShowRoadGoodies = isChecked;
        }
    }

    public void updateConfigItems() {
        String[] reportTypesStrings = this.mCm.getConfigValueString(109).split("-");
        this.enableToggleConstruction = this.mCm.getConfigValueBool(99);
        if (!this.enableToggleConstruction) {
            this.roadConstructionsView.setVisibility(8);
        }
        this.reportTypes = new boolean[6];
        for (int i = 0; i < 6; i++) {
            this.reportTypes[i] = true;
        }
        this.chitchatsView.setValue(true);
        this.policeView.setValue(true);
        this.accidentsView.setValue(true);
        this.trafficJamsView.setValue(true);
        this.hazardsView.setValue(true);
        this.roadConstructionsView.setValue(true);
        for (String reportTypeStr : reportTypesStrings) {
            if (reportTypeStr.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                this.reportTypes[0] = false;
                this.chitchatsView.setValue(false);
            } else if (reportTypeStr.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                this.reportTypes[1] = false;
                this.policeView.setValue(false);
            } else if (reportTypeStr.equals("2")) {
                this.reportTypes[2] = false;
                this.accidentsView.setValue(false);
            } else if (reportTypeStr.equals("3")) {
                this.reportTypes[3] = false;
                this.trafficJamsView.setValue(false);
            } else if (reportTypeStr.equals("5")) {
                this.reportTypes[4] = false;
                this.hazardsView.setValue(false);
            } else if (reportTypeStr.equals("7")) {
                this.reportTypes[5] = false;
                this.roadConstructionsView.setValue(false);
            }
        }
        this.mShowOthers = this.mCm.getConfigValueBool(106);
        this.wazersView.setValue(this.mShowOthers);
        this.wazersView.initToggleCallbackBoolean(106, new C27541());
        this.mShowTrafficLoad = this.mCm.getConfigValueBool(108);
        this.trafficLoadsView.setValue(this.mShowTrafficLoad);
        this.trafficLoadsView.initToggleCallbackBoolean(108, new C27552());
        this.mShowSpeedCams = this.mCm.getConfigValueBool(107);
        this.speedcamsView.setValue(this.mShowSpeedCams);
        this.speedcamsView.initToggleCallbackBoolean(107, new C27563());
        this.mShowRoadGoodies = this.mCm.getConfigValueBool(116);
        this.roadGoodiesView.setValue(this.mShowRoadGoodies);
        this.roadGoodiesView.initToggleCallbackBoolean(116, new C27574());
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("waze", "start settings activity show on map");
        setContentView(C1283R.layout.settings_show_on_map);
        this.mCm = ConfigManager.getInstance();
        this.mNativeManager = NativeManager.getInstance();
        if (savedInstanceState != null) {
            this.reportTypes = savedInstanceState.getBooleanArray(getClass().getName() + ".reportTypes");
        }
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 114);
        this.wazersView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapWazers);
        this.wazersView.setText(NativeManager.getInstance().getLanguageString(117));
        this.trafficLoadsView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapTrafficLoads);
        this.trafficLoadsView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TRAFFIC_LAYER_OCOLOR_CODEDU));
        this.speedcamsView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapCameras);
        if (!NativeManager.getInstance().isEnforcementAlertsEnabledNTV()) {
            this.speedcamsView.setVisibility(8);
        }
        this.speedcamsView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SPEED_CAMS));
        this.roadGoodiesView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapGoodies);
        this.roadGoodiesView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ROAD_GOODIES));
        this.chitchatsView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapChitChat);
        createCheckbox(163, this.chitchatsView, 0);
        this.policeView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapPolice);
        createCheckbox(225, this.policeView, 1);
        if (NativeManager.getInstance().isEnforcementPoliceEnabledNTV() == 0) {
            this.policeView.setVisibility(8);
        }
        this.accidentsView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapAccidents);
        createCheckbox(229, this.accidentsView, 2);
        this.trafficJamsView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapTrafficJams);
        createCheckbox(DisplayStrings.DS_TRAFFIC_JAMS_, this.trafficJamsView, 3);
        this.hazardsView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapHazard);
        createCheckbox(115, this.hazardsView, 4);
        this.roadConstructionsView = (WazeSettingsView) findViewById(C1283R.id.settingsShowOnMapRoadConstructions);
        createCheckbox(1003, this.roadConstructionsView, 5);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray(getClass().getName() + ".reportTypes", this.reportTypes);
    }

    private void createCheckbox(int regStr, WazeSettingsView checkboxView, final int index) {
        checkboxView.setText(this.mNativeManager.getLanguageString(regStr));
        checkboxView.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean bIsChecked) {
                if (SettingsShowOnMapActivity.this.reportTypes != null) {
                    SettingsShowOnMapActivity.this.reportTypes[index] = bIsChecked;
                    SettingsShowOnMapActivity.this.mCm.setConfigValueString(109, SettingsShowOnMapActivity.this.createReportTypesString());
                }
            }
        });
    }

    public void onResume() {
        updateConfigItems();
        super.onResume();
    }

    protected String createReportTypesString() {
        String finalString = "";
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    if (!this.reportTypes[i]) {
                        finalString = finalString + "0-";
                        break;
                    }
                    break;
                case 1:
                    if (!this.reportTypes[i]) {
                        finalString = finalString + "1-";
                        break;
                    }
                    break;
                case 2:
                    if (!this.reportTypes[i]) {
                        finalString = finalString + "2-";
                        break;
                    }
                    break;
                case 3:
                    if (!this.reportTypes[i]) {
                        finalString = finalString + "3-";
                        break;
                    }
                    break;
                case 4:
                    if (!this.reportTypes[i]) {
                        finalString = finalString + "5-";
                        break;
                    }
                    break;
                case 5:
                    if (!this.reportTypes[i]) {
                        finalString = finalString + "7-";
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
        if (finalString.endsWith("-")) {
            return finalString.substring(0, finalString.length() - 1);
        }
        return finalString;
    }
}
