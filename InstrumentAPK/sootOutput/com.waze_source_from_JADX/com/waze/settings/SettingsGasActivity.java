package com.waze.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.appevents.AppEventsConstants;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.ConfigManager.IConfigUpdater;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingsNativeManager.SettingsValuesListener;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class SettingsGasActivity extends ActivityBase implements IConfigUpdater {
    private static final int SORT_BY_INDEX = 0;
    private static final String[] SORT_BY_OPTIONS = new String[]{"Price", "Distance", "Brand"};
    private static final String[] SORT_BY_VALUES = new String[]{AppEventsConstants.EVENT_PARAM_VALUE_NO, AppEventsConstants.EVENT_PARAM_VALUE_YES, "2"};
    private WazeSettingsView GasPopUpVisibilty;
    private WazeSettingsView gasSortBy;
    private List<ConfigItem> mConfigItems;
    private int preferedGasTypeIndex = -1;
    private int preferedStationIndex = 0;
    private WazeSettingsView preferredGasStations;
    private WazeSettingsView preferredGasType;
    private final String screenName = "SettingsGas";
    private SettingsNativeManager settingsNativeManager;
    private String[] stationStrings;
    private String[] typeStrings;

    class C26893 implements SettingsValuesListener {
        C26893() {
        }

        public void onComplete(SettingsValue[] values) {
            List<String> strings = new ArrayList();
            int counter = 0;
            for (SettingsValue settingsValue : values) {
                strings.add(settingsValue.value);
                if (settingsValue.isSelected) {
                    SettingsGasActivity.this.preferredGasType.setValueText(settingsValue.value);
                    SettingsGasActivity.this.preferedGasTypeIndex = counter;
                }
                counter++;
            }
            SettingsGasActivity.this.typeStrings = (String[]) strings.toArray(new String[strings.size()]);
        }
    }

    class C26925 implements SettingsToggleCallback {
        C26925() {
        }

        public void onToggle(boolean bOn) {
            NativeManager.getInstance().SetShowGasPricePopupAgain(bOn);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mConfigItems = new ArrayList();
        this.mConfigItems.add(new ConfigItem("Provider Search", "Gas stations sort", ""));
        ConfigManager.getInstance().getConfig(this, this.mConfigItems, "SettingsGas");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_GAS_STATIONS);
        final NativeManager mgr = NativeManager.getInstance();
        setContentView(C1283R.layout.settings_gas);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_GAS_STATIONS);
        this.gasSortBy = (WazeSettingsView) findViewById(C1283R.id.settingsGasSortBy);
        this.preferredGasType = (WazeSettingsView) findViewById(C1283R.id.settingsGasType);
        final String gasTypeStr = mgr.getLanguageString(123);
        this.preferredGasType.setKeyText(gasTypeStr);
        this.preferredGasStations = (WazeSettingsView) findViewById(C1283R.id.settingsGasStation);
        final String preferredStationStr = mgr.getLanguageString(122);
        this.preferredGasStations.setKeyText(preferredStationStr);
        this.settingsNativeManager = SettingsNativeManager.getInstance();
        this.settingsNativeManager.getPreferredGasStations(new SettingsValuesListener() {
            public void onComplete(SettingsValue[] values) {
                boolean found = false;
                List<String> strings = new ArrayList();
                int counter = 1;
                strings.add(mgr.getLanguageString(DisplayStrings.DS_ALL_STATIONS));
                for (SettingsValue settingsValue : values) {
                    strings.add(settingsValue.value);
                    if (settingsValue.isSelected) {
                        SettingsGasActivity.this.preferredGasStations.setValueText(settingsValue.value);
                        SettingsGasActivity.this.preferedStationIndex = counter;
                        found = true;
                    }
                    counter++;
                }
                SettingsGasActivity.this.stationStrings = (String[]) strings.toArray(new String[strings.size()]);
                if (!found) {
                    SettingsGasActivity.this.preferredGasStations.setValueText(mgr.getLanguageString(DisplayStrings.DS_ALL_STATIONS));
                }
            }
        });
        this.preferredGasStations.setOnClickListener(new OnClickListener() {

            class C26871 implements SettingsDialogListener {
                C26871() {
                }

                public void onComplete(int position) {
                    SettingsGasActivity.this.preferredGasStations.setValueText(NativeManager.getInstance().getLanguageString(SettingsGasActivity.this.stationStrings[position]));
                    SettingsGasActivity.this.settingsNativeManager.setPreferredStation(position);
                    SettingsGasActivity.this.preferedStationIndex = position;
                }
            }

            public void onClick(View v) {
                SettingsUtils.showSubmenu(SettingsGasActivity.this, preferredStationStr, SettingsGasActivity.this.stationStrings, SettingsGasActivity.this.preferedStationIndex, new C26871());
            }
        });
        this.settingsNativeManager.getPreferredGasType(new C26893());
        this.preferredGasType.setOnClickListener(new OnClickListener() {

            class C26901 implements SettingsDialogListener {
                C26901() {
                }

                public void onComplete(int position) {
                    SettingsGasActivity.this.preferredGasType.setValueText(NativeManager.getInstance().getLanguageString(SettingsGasActivity.this.typeStrings[position]));
                    SettingsGasActivity.this.settingsNativeManager.setPreferredType(position);
                    SettingsGasActivity.this.preferedGasTypeIndex = position;
                }
            }

            public void onClick(View v) {
                SettingsUtils.showSubmenu(SettingsGasActivity.this, gasTypeStr, SettingsGasActivity.this.typeStrings, SettingsGasActivity.this.preferedGasTypeIndex, new C26901());
            }
        });
        this.GasPopUpVisibilty = (WazeSettingsView) findViewById(C1283R.id.settingsGasPopUp);
        if (NativeManager.getInstance().IsGasPopUpFeatureEnabled()) {
            this.GasPopUpVisibilty.setVisibility(0);
            this.gasSortBy.setPosition(0);
        } else {
            this.GasPopUpVisibilty.setVisibility(8);
            this.gasSortBy.setPosition(2);
        }
        this.GasPopUpVisibilty.setValue(NativeManager.getInstance().getGasPopupVisibilty());
        this.GasPopUpVisibilty.setText(NativeManager.getInstance().getLanguageString(124));
        this.GasPopUpVisibilty.setOnChecked(new C26925());
    }

    public void updateConfigItems(List<ConfigItem> configItems) {
        this.gasSortBy.initSelection(this, "SettingsGas", configItems, DisplayStrings.DS_SORT_BY, SORT_BY_OPTIONS, SORT_BY_VALUES, 0);
    }
}
