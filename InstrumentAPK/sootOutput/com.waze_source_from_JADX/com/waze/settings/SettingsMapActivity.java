package com.waze.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.ConfigManager.IConfigUpdater;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.profile.CarsActivity;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class SettingsMapActivity extends ActivityBase implements IConfigUpdater {
    protected static final int ACTIVITY_CODE = 1000;
    private static final int DISPLAY_MAP_INDEX = 0;
    private static final int FRIENDS_DISPLAY_MODE = 1001;
    public static String[] FRIEND_CONTROL_OPTIONS = null;
    public static final String[] FRIEND_CONTROL_VALUES = new String[]{LayoutManager.FRIENDS_CONTROL_MODE_OFF, LayoutManager.FRIENDS_CONTROL_MODE_MAP_CONTROLS, "always"};
    private static final int MAP_COLORS_CODE = 1000;
    public static final String[] MAP_COLORS_ICONS = new String[]{"schema12", "schema8"};
    public static String[] MAP_COLORS_OPTIONS = null;
    public static final String[] MAP_COLORS_VALUES = new String[]{"12", "8"};
    private static final int[] MAP_MODE_ICONS = new int[]{C1283R.drawable.list_icon_mode_day, C1283R.drawable.list_icon_mode_auto, C1283R.drawable.list_icon_mode_night};
    private static final String[] MAP_MODE_VALUES = new String[]{"day", "", "night"};
    private static List<RunnableExecutor> mConfUpdatedEvents = new ArrayList();
    private static Object mEventsSync = new Object();
    public static final String screenName = "SettingsAdvanced";
    private WazeSettingsView NorthUpView;
    private WazeSettingsView autoZoomView;
    private WazeSettingsView displayView;
    private ConfigManager mCm;
    private List<ConfigItem> mConfigItems;
    private int mFriendsControlIndex;
    private WazeSettingsView mFriendsView;
    private int mMapColorIndex;
    private NativeManager mNativeManager;
    private boolean mSpeedometerConfigVal;
    private WazeSettingsView mapColors;
    int mapDisplaySelection;
    private WazeSettingsView mapMode;
    String mapModePrevVal;
    int mapModeSelection;
    private WazeSettingsView searchOnMapView;
    private WazeSettingsView speedometerView;

    class C27141 implements OnCheckedChangeListener {
        C27141() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsMapActivity.this.mSpeedometerConfigVal = isChecked;
        }
    }

    class C27152 implements GetIndex {
        C27152() {
        }

        public int fromConfig() {
            SettingsMapActivity.this.mapModePrevVal = SettingsMapActivity.this.mCm.getConfigValueString(120);
            SettingsMapActivity.this.mapModeSelection = ConfigManager.getOptionIndex(SettingsMapActivity.MAP_MODE_VALUES, SettingsMapActivity.this.mapModePrevVal, 1);
            return SettingsMapActivity.this.mapModeSelection;
        }
    }

    class C27163 implements SettingsDialogListener {
        C27163() {
        }

        public void onComplete(int position) {
            SettingsMapActivity.this.mapModeSelection = position;
            SettingsMapActivity.this.mCm.setConfigValueString(120, SettingsMapActivity.MAP_MODE_VALUES[position]);
            SettingsMapActivity.this.mapMode.setValueText(SettingsMainActivity.MAP_MODE_OPTIONS[position]);
            SettingsMapActivity.this.mapMode.setIcon(SettingsMapActivity.MAP_MODE_ICONS[position]);
            SettingsMapActivity.this.mCm.setMapSkin(SettingsMapActivity.MAP_MODE_VALUES[position], SettingsMapActivity.this.mapModePrevVal);
        }
    }

    class C27174 implements OnClickListener {
        C27174() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(SettingsMapActivity.this, SettingsMapColorsActivity.class);
            intent.putExtra("selected", SettingsMapActivity.this.mMapColorIndex);
            SettingsMapActivity.this.startActivityForResult(intent, 1000);
        }
    }

    class C27207 implements SettingsToggleCallback {
        C27207() {
        }

        public void onToggle(boolean bOn) {
            ConfigManager.getInstance().setConfigValueBool(316, bOn);
        }
    }

    class C27218 implements OnClickListener {
        C27218() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(SettingsMapActivity.this, SettingsFriendsControlActivity.class);
            intent.putExtra("selected", SettingsMapActivity.this.mFriendsControlIndex);
            SettingsMapActivity.this.startActivityForResult(intent, 1001);
        }
    }

    static String[] getMapColorsOptions() {
        if (MAP_COLORS_OPTIONS == null) {
            MAP_COLORS_OPTIONS = new String[]{DisplayStrings.displayString(DisplayStrings.DS_DEFAULT), DisplayStrings.displayString(81)};
        }
        return MAP_COLORS_OPTIONS;
    }

    static String[] getFriendControlOptions() {
        if (FRIEND_CONTROL_OPTIONS == null) {
            FRIEND_CONTROL_OPTIONS = new String[]{DisplayStrings.displayString(DisplayStrings.DS_FRIENDS_BUTTON_ON_MAP_SETTINGS_DONT_SHOW), DisplayStrings.displayString(DisplayStrings.DS_FRIENDS_BUTTON_ON_MAP_SETTINGS_SHOW_WITH_MAP_CONTROLS), DisplayStrings.displayString(DisplayStrings.DS_FRIENDS_BUTTON_ON_MAP_SETTINGS_ALWAYS_SHOW)};
        }
        return FRIEND_CONTROL_OPTIONS;
    }

    public static void registerOnConfChange(RunnableExecutor aEvent) {
        synchronized (mEventsSync) {
            mConfUpdatedEvents.add(aEvent);
        }
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
        this.mSpeedometerConfigVal = this.mCm.getConfigValueBool(110);
        this.speedometerView.setValue(this.mSpeedometerConfigVal);
        this.speedometerView.initToggleCallbackBoolean(110, new C27141());
        String val = this.mCm.getConfigValueString(119);
        ConfigManager configManager = this.mCm;
        this.mMapColorIndex = ConfigManager.getOptionIndex(MAP_COLORS_VALUES, val, 0);
        this.mapColors.setValueText(MAP_COLORS_OPTIONS[this.mMapColorIndex]);
        val = this.mCm.getConfigValueString(97);
        configManager = this.mCm;
        this.mFriendsControlIndex = ConfigManager.getOptionIndex(FRIEND_CONTROL_VALUES, val, 1);
        this.mFriendsView.setValueText(FRIEND_CONTROL_OPTIONS[this.mFriendsControlIndex]);
        this.mapMode.initSelectionNoTranslation(this, new C27152(), 109, SettingsMainActivity.MAP_MODE_OPTIONS, MAP_MODE_VALUES, new C27163());
        this.mapMode.setIcon(MAP_MODE_ICONS[this.mapModeSelection]);
    }

    public void updateConfigItems(List<ConfigItem> configItems) {
        boolean z;
        WazeSettingsView wazeSettingsView = this.displayView;
        if (((ConfigItem) configItems.get(0)).getBooleanValue()) {
            z = false;
        } else {
            z = true;
        }
        wazeSettingsView.setValue(z);
        this.displayView.initToggleCallback("SettingsAdvanced", this.mConfigItems, 0, null);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_map);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_DISPLAY_AND_MAP_SETTINGS);
        SettingsMainActivity.getMapDisplayOptions();
        SettingsMainActivity.getMapModeOptions();
        this.mConfigItems = new ArrayList();
        this.mConfigItems.add(new ConfigItem("Map Icons", "Show on screen on tap", ""));
        this.mConfigItems.add(new ConfigItem("Alternative Routes", "Show Suggested", ""));
        this.mConfigItems.add(new ConfigItem("Routing", "Auto zoom", ""));
        ConfigManager.getInstance().getConfig(this, this.mConfigItems, "SettingsAdvanced");
        this.mCm = ConfigManager.getInstance();
        this.mNativeManager = NativeManager.getInstance();
        this.mapMode = (WazeSettingsView) findViewById(C1283R.id.settingsMainMapMode);
        getMapColorsOptions();
        getFriendControlOptions();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_DISPLAY_SETTINGS);
        this.mapColors = (WazeSettingsView) findViewById(C1283R.id.settingsMapColors);
        this.mapColors.setKeyText(NativeManager.getInstance().getLanguageString(118));
        this.mapColors.setOnClickListener(new C27174());
        ((WazeSettingsView) findViewById(C1283R.id.settingsAdvancedShowOnMap)).initDrillDown(this, DisplayStrings.DS_SHOW_ON_MAP_MENU_ITEM, SettingsShowOnMapActivity.class, 1000);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMyCar)).initDrillDown(this, DisplayStrings.DS_CAR_ON_MAP, CarsActivity.class, 1000);
        ((WazeSettingsView) findViewById(C1283R.id.settingsAlertsOnRoute)).initDrillDown(this, DisplayStrings.DS_ALERTS_ON_ROUTE, SettingsAlertsOnRoute.class, 1000);
        this.speedometerView = (WazeSettingsView) findViewById(C1283R.id.settingsMapSpeedometer);
        if (ConfigValues.getBoolValue(100)) {
            this.speedometerView.setVisibility(8);
        } else {
            this.speedometerView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SPEEDOMETER));
        }
        this.displayView = (WazeSettingsView) findViewById(C1283R.id.settingsAdvancedDisplay);
        final NativeManager nm = NativeManager.getInstance();
        this.displayView.setText(nm.getLanguageString(DisplayStrings.DS_ALWAYS_SHOW_MAP_CONTROLS));
        this.displayView.setReverseBooleanSwitch(true);
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
        this.searchOnMapView = (WazeSettingsView) findViewById(C1283R.id.settingsSearchOnMap);
        this.searchOnMapView.setValue(ConfigManager.getInstance().getConfigValueBool(316));
        this.searchOnMapView.setText(DisplayStrings.displayString(DisplayStrings.DS_SEARCH_ON_MAP));
        this.searchOnMapView.setOnChecked(new C27207());
        if (!ConfigManager.getInstance().getConfigValueBool(315)) {
            this.searchOnMapView.setVisibility(8);
        }
        ((WazeSettingsView) findViewById(C1283R.id.settingsAdvancedDataTransfer)).initDrillDown(this, 100, SettingsDataTransferActivity.class, 1000);
        this.mFriendsView = (WazeSettingsView) findViewById(C1283R.id.settingsFriendsControl);
        this.mFriendsView.setText(DisplayStrings.displayString(119));
        this.mFriendsView.setOnClickListener(new C27218());
    }

    protected void onResume() {
        super.onResume();
        updateConfigItems();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
