package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.SettingsLicensePlateActivity;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class SettingsNavigationActivity extends ActivityBase {
    private static String[] DIRT_ROADS_OPTIONS;
    private static String[] DIRT_ROADS_VALUES = new String[]{"Allow", "Don't allow", "Avoid long ones"};
    private static String[] ROUTE_TYPE_OPTIONS;
    private static String[] ROUTE_TYPE_VALUES = new String[]{"Fastest", "Shortest"};
    private static String[] vehicleCodes;
    private static String[] vehicleTypes;
    private WazeSettingsView avoidDangerousTurnsView;
    private WazeSettingsView avoidFerriesView;
    private WazeSettingsView avoidHighwaysView;
    private WazeSettingsView avoidTollView;
    private WazeSettingsView dirtRoadsView;
    private boolean mAvoidDangerousTurns;
    private boolean mAvoidFerries;
    private boolean mAvoidHighways;
    private boolean mAvoidRestricted;
    private boolean mAvoidTolls;
    ConfigManager mCm;
    private String mDirtRoadOption;
    private boolean mIsChanged = false;
    NativeManager mNm;
    private String mRouteTypeOption;
    private String mVehicleType;
    private SettingsTitleText moreTitle;
    private WazeSettingsView palestinianView;
    private WazeTextView restrictedNote;
    private SettingsTitleText restrictedTitle;
    private SettingsTitleText routingTitle;
    private WazeSettingsView typeView;
    private WazeSettingsView vehicleTypeView;

    class C27341 implements GetIndex {
        C27341() {
        }

        public int fromConfig() {
            return ConfigManager.getOptionIndex(SettingsNavigationActivity.DIRT_ROADS_VALUES, SettingsNavigationActivity.this.mDirtRoadOption, 1);
        }
    }

    class C27352 implements SettingsDialogListener {
        C27352() {
        }

        public void onComplete(int position) {
            SettingsNavigationActivity.this.mDirtRoadOption = SettingsNavigationActivity.DIRT_ROADS_VALUES[position];
            SettingsNavigationActivity.this.mCm.setConfigValueString(144, SettingsNavigationActivity.this.mDirtRoadOption);
            SettingsNavigationActivity.this.dirtRoadsView.setValueText(SettingsNavigationActivity.DIRT_ROADS_OPTIONS[position]);
        }
    }

    class C27363 implements GetIndex {
        C27363() {
        }

        public int fromConfig() {
            return ConfigManager.getOptionIndex(SettingsNavigationActivity.ROUTE_TYPE_VALUES, SettingsNavigationActivity.this.mRouteTypeOption, 0);
        }
    }

    class C27374 implements SettingsDialogListener {
        C27374() {
        }

        public void onComplete(int position) {
            SettingsNavigationActivity.this.mRouteTypeOption = SettingsNavigationActivity.ROUTE_TYPE_VALUES[position];
            SettingsNavigationActivity.this.mCm.setConfigValueString(139, SettingsNavigationActivity.this.mRouteTypeOption);
            SettingsNavigationActivity.this.typeView.setValueText(SettingsNavigationActivity.ROUTE_TYPE_OPTIONS[position]);
        }
    }

    class C27385 implements GetIndex {
        C27385() {
        }

        public int fromConfig() {
            return ConfigManager.getOptionIndex(SettingsNavigationActivity.vehicleCodes, SettingsNavigationActivity.this.mVehicleType, 0);
        }
    }

    class C27396 implements SettingsDialogListener {
        C27396() {
        }

        public void onComplete(int position) {
            SettingsNavigationActivity.this.mVehicleType = SettingsNavigationActivity.vehicleCodes[position];
            SettingsNavigationActivity.this.mCm.setConfigValueString(140, SettingsNavigationActivity.this.mVehicleType);
            ConfigManager.getInstance().setConfigValueString(336, SettingsNavigationActivity.this.mVehicleType);
            SettingsNavigationActivity.this.vehicleTypeView.setValueText(SettingsNavigationActivity.vehicleTypes[position]);
        }
    }

    class C27407 implements OnCheckedChangeListener {
        C27407() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsNavigationActivity.this.mAvoidHighways = isChecked;
            SettingsNavigationActivity.this.mIsChanged = true;
        }
    }

    class C27418 implements OnCheckedChangeListener {
        C27418() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsNavigationActivity.this.mAvoidFerries = isChecked;
            SettingsNavigationActivity.this.mIsChanged = true;
        }
    }

    class C27429 implements OnCheckedChangeListener {
        C27429() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsNavigationActivity.this.mAvoidDangerousTurns = isChecked;
            SettingsNavigationActivity.this.mIsChanged = true;
        }
    }

    static String[] getRouteTypeOptions() {
        if (ROUTE_TYPE_OPTIONS == null) {
            ROUTE_TYPE_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FASTEST), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SHORTEST)};
        }
        return ROUTE_TYPE_OPTIONS;
    }

    static String[] getDirtRoadOptions() {
        if (DIRT_ROADS_OPTIONS == null) {
            DIRT_ROADS_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ALLOW), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_DONT_ALLOW), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_AVOID_LONG_ONES)};
        }
        return DIRT_ROADS_OPTIONS;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateConfigItems() {
        this.mDirtRoadOption = this.mCm.getConfigValueString(144);
        this.dirtRoadsView.initSelectionNoTranslation(this, new C27341(), 150, DIRT_ROADS_OPTIONS, DIRT_ROADS_VALUES, new C27352());
        this.mRouteTypeOption = this.mCm.getConfigValueString(139);
        this.typeView.initSelectionNoTranslation(this, new C27363(), DisplayStrings.DS_TYPE, ROUTE_TYPE_OPTIONS, ROUTE_TYPE_VALUES, new C27374());
        this.mVehicleType = this.mCm.getConfigValueString(140);
        if (!this.mCm.getConfigValueBool(135)) {
            this.vehicleTypeView.setVisibility(8);
        } else if (vehicleTypes.length > 0) {
            this.vehicleTypeView.initSelection(this, new C27385(), 149, vehicleTypes, vehicleCodes, new C27396());
        } else {
            Logger.e("Empty vehicle type menu ");
        }
        this.mAvoidHighways = this.mCm.getConfigValueBool(142);
        this.avoidHighwaysView.setValue(this.mAvoidHighways);
        this.avoidHighwaysView.initToggleCallbackBoolean(142, new C27407());
        this.mAvoidFerries = this.mCm.getConfigValueBool(146);
        this.avoidFerriesView.setValue(this.mAvoidFerries);
        this.avoidFerriesView.initToggleCallbackBoolean(146, new C27418());
        if (this.mCm.getConfigValueBool(133)) {
            this.mAvoidRestricted = this.mCm.getConfigValueBool(145);
            this.palestinianView.setValue(this.mAvoidRestricted);
            this.restrictedTitle.setVisibility(0);
            findViewById(C1283R.id.RestrictedCommentLayout).setVisibility(0);
            this.palestinianView.setVisibility(0);
        } else {
            this.palestinianView.setVisibility(8);
            this.restrictedTitle.setVisibility(8);
            findViewById(C1283R.id.RestrictedCommentLayout).setVisibility(8);
        }
        if (this.mCm.getConfigValueBool(132)) {
            this.mAvoidDangerousTurns = this.mCm.getConfigValueBool(147);
            this.avoidDangerousTurnsView.setValue(this.mAvoidDangerousTurns);
            this.avoidDangerousTurnsView.initToggleCallbackBoolean(147, new C27429());
        } else {
            this.avoidDangerousTurnsView.setVisibility(8);
        }
        if (this.mCm.getConfigValueBool(129)) {
            this.mAvoidTolls = this.mCm.getConfigValueBool(141);
            this.avoidTollView.setValue(this.mAvoidTolls);
            this.avoidTollView.initToggleCallbackBoolean(141, new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SettingsNavigationActivity.this.mAvoidTolls = isChecked;
                    SettingsNavigationActivity.this.mIsChanged = true;
                }
            });
            return;
        }
        this.avoidTollView.setVisibility(8);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_navigation);
        this.mCm = ConfigManager.getInstance();
        this.mNm = NativeManager.getInstance();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_NAVIGATION);
        DriveToNativeManager dtnm = DriveToNativeManager.getInstance();
        getRouteTypeOptions();
        getDirtRoadOptions();
        String[] configGetVehicleTypes = dtnm.configGetVehicleTypesNTV();
        vehicleTypes = new String[(configGetVehicleTypes.length / 2)];
        vehicleCodes = new String[(configGetVehicleTypes.length / 2)];
        for (int i = 1; i < configGetVehicleTypes.length; i += 2) {
            vehicleTypes[i / 2] = NativeManager.getInstance().getLanguageString(configGetVehicleTypes[i - 1]);
            vehicleCodes[i / 2] = configGetVehicleTypes[i];
        }
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_NAVIGATION);
        this.routingTitle = (SettingsTitleText) findViewById(C1283R.id.RoutingTitleText);
        this.routingTitle.setText(NativeManager.getInstance().getFormattedString(DisplayStrings.DS_ROUTING, new Object[0]));
        this.moreTitle = (SettingsTitleText) findViewById(C1283R.id.MoreTitleText);
        this.moreTitle.setText(NativeManager.getInstance().getFormattedString(DisplayStrings.DS_MORE_OPTIONS, new Object[0]));
        this.typeView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationType);
        this.dirtRoadsView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationDirtRoads);
        this.vehicleTypeView = (WazeSettingsView) findViewById(C1283R.id.settingsVehicleType);
        this.avoidHighwaysView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationAvoidHighways);
        this.avoidHighwaysView.setText(NativeManager.getInstance().getLanguageString(146));
        this.avoidFerriesView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationAvoidFerries);
        this.avoidFerriesView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_AVOID_FERRIES));
        if (!this.mCm.getConfigValueBool(130)) {
            this.avoidFerriesView.setVisibility(8);
        }
        this.avoidTollView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationAvoidTollRoads);
        this.avoidTollView.setText(NativeManager.getInstance().getLanguageString(147));
        this.avoidDangerousTurnsView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationAvoidDangerousTurns);
        this.avoidDangerousTurnsView.setText(NativeManager.getInstance().getLanguageString(148));
        this.palestinianView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationPalestinian);
        this.palestinianView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NAVIGATION_SETTINGS_AVOID_DANGEROUS_AREAS));
        this.palestinianView.setOnClickListener(new OnClickListener() {

            class C27331 implements DialogInterface.OnClickListener {
                C27331() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    if (which == 1) {
                        SettingsNavigationActivity.this.mAvoidRestricted = false;
                    } else {
                        SettingsNavigationActivity.this.mAvoidRestricted = true;
                    }
                    SettingsNavigationActivity.this.palestinianView.setValue(SettingsNavigationActivity.this.mAvoidRestricted);
                    SettingsNavigationActivity.this.mCm.setConfigValueBool(145, SettingsNavigationActivity.this.mAvoidRestricted);
                }
            }

            public void onClick(View v) {
                if (SettingsNavigationActivity.this.mAvoidRestricted) {
                    MsgBox.openConfirmDialogJavaCallback(SettingsNavigationActivity.this.mNm.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), SettingsNavigationActivity.this.mNm.getLanguageString(DisplayStrings.DS_NAVIGATION_SETTINGS_AVOID_DANGEROUS_AREAS_CONFIRMATION), false, new C27331(), SettingsNavigationActivity.this.mNm.getLanguageString(357), SettingsNavigationActivity.this.mNm.getLanguageString(344), -1);
                    return;
                }
                SettingsNavigationActivity.this.mAvoidRestricted = true;
                SettingsNavigationActivity.this.palestinianView.setValue(SettingsNavigationActivity.this.mAvoidRestricted);
                SettingsNavigationActivity.this.mCm.setConfigValueBool(145, SettingsNavigationActivity.this.mAvoidRestricted);
            }
        });
        this.restrictedTitle = (SettingsTitleText) findViewById(C1283R.id.RestrictedTitleText);
        this.restrictedTitle.setText(NativeManager.getInstance().getFormattedString(DisplayStrings.DS_SETTINGS_RESTRICTED_AREAS_TITLE, new Object[0]));
        this.restrictedNote = (WazeTextView) findViewById(C1283R.id.RestrictedCommentText);
        this.restrictedNote.setText(NativeManager.getInstance().getFormattedString(DisplayStrings.DS_SETTINGS_RESTRICTED_AREAS_NOTE, new Object[0]));
        ((TextView) findViewById(C1283R.id.DifficultTurnsCommentText)).setText(DisplayStrings.displayString(DisplayStrings.DS_NAVIGATION_MENU_FOOTER_AVOID_DIFFICULT_TURNS));
        if (!this.mCm.getConfigValueBool(132)) {
            findViewById(C1283R.id.DifficultTurnsContainer).setVisibility(8);
        }
        WazeSettingsView svLicensePlate = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationLicensePlate);
        if (NativeManager.getInstance().isLicensePlateEnabledNTV()) {
            svLicensePlate.setVisibility(0);
        } else {
            svLicensePlate.setVisibility(8);
        }
        svLicensePlate.initDrillDown(this, DisplayStrings.DS_SETTINGS_LICENCE_PLATE_CELL, SettingsLicensePlateActivity.class, 1);
    }

    public void onResume() {
        updateConfigItems();
        super.onResume();
    }

    protected void onDestroy() {
        if (this.mIsChanged) {
            DriveToNativeManager.getInstance().reroute(false);
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        if (this.mIsChanged) {
            setResult(-1);
            finish();
        }
        super.onBackPressed();
    }
}
