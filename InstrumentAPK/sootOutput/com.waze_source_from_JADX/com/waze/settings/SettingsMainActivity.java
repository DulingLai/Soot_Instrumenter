package com.waze.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeSoundManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.audioextension.spotify.SpotifyManager;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolUserData;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.VideoActivity;
import com.waze.install.InstallNativeManager;
import com.waze.install.InstallNativeManager.VideoUrlListener;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.social.SocialActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.profile.MyProfileActivity;
import com.waze.profile.TempUserProfileActivity;
import com.waze.settings.SettingsNativeManager.SettingsValuesListener;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.share.SpreadTheWordActivity;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsMainActivity extends ActivityBase {
    protected static final int ACTIVITY_CODE = 1000;
    private static final int[] MAP_DISPLAY_ICONS = new int[]{C1283R.drawable.list_icon_display_2d, C1283R.drawable.list_icon_display_3d, C1283R.drawable.list_icon_display_auto};
    public static String[] MAP_DISPLAY_OPTIONS = null;
    public static final String[] MAP_DISPLAY_VALUES = new String[]{AnalyticsEvents.ANALYTICS_EVENT_2D, "3D manual", "Auto"};
    private static final int[] MAP_MODE_ICONS = new int[]{C1283R.drawable.list_icon_mode_day, C1283R.drawable.list_icon_mode_auto, C1283R.drawable.list_icon_mode_night};
    public static String[] MAP_MODE_OPTIONS = null;
    private static final String[] MAP_MODE_VALUES = new String[]{"day", "", "night"};
    protected static final int REFRESH_RC = 1001;
    private static final int VOICE_SEARCH_REQ = 1002;
    private WazeSettingsView avoidTollView;
    protected String editVideoUrl;
    private boolean mAvoidTolls;
    ConfigManager mCm;
    private boolean mIsChanged = false;
    private NativeManager mNativeManager;
    private WazeSettingsView mVoiceSearch;
    private WazeSettingsView mapDisplay;
    int mapDisplaySelection;
    private WazeSettingsView navLanguageView;
    private WazeSettingsView saveBatteryView;
    protected boolean showGuidedTour = true;
    protected boolean showHowToEditMap = true;
    private WazeSettingsView sound;
    int soundSelection;

    class C27041 implements GetIndex {
        C27041() {
        }

        public int fromConfig() {
            String val = SettingsMainActivity.this.mCm.getConfigValueString(102);
            SettingsMainActivity.this.mapDisplaySelection = ConfigManager.getOptionIndex(SettingsMainActivity.MAP_DISPLAY_VALUES, val, 2);
            return SettingsMainActivity.this.mapDisplaySelection;
        }
    }

    class C27052 implements SettingsDialogListener {
        C27052() {
        }

        public void onComplete(int position) {
            SettingsMainActivity.this.mapDisplaySelection = position;
            SettingsMainActivity.this.mCm.setConfigValueString(102, SettingsMainActivity.MAP_DISPLAY_VALUES[position]);
            SettingsMainActivity.this.mapDisplay.setValueText(SettingsMainActivity.MAP_DISPLAY_OPTIONS[position]);
            SettingsMainActivity.this.mCm.setMapOrientation(SettingsMainActivity.MAP_DISPLAY_VALUES[position]);
        }
    }

    class C27063 implements GetIndex {
        C27063() {
        }

        public int fromConfig() {
            String val = SettingsMainActivity.this.mCm.getConfigValueString(126);
            SettingsMainActivity.this.soundSelection = SettingsSound.getSoundSelectionFromConfig();
            return SettingsMainActivity.this.soundSelection;
        }
    }

    class C27074 implements SettingsDialogListener {
        C27074() {
        }

        public void onComplete(int position) {
            SettingsMainActivity.this.soundSelection = position;
            SettingsSound.setSoundValInConfig(SettingsMainActivity.this.soundSelection);
            SettingsMainActivity.this.sound.setValueText(SettingsSound.getSoundOptionBySelection(SettingsMainActivity.this.soundSelection));
        }
    }

    class C27095 implements SettingsValuesListener {
        C27095() {
        }

        public void onComplete(final SettingsValue[] values) {
            SettingsMainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (values == null) {
                        SettingsMainActivity.this.navLanguageView.setVisibility(8);
                        return;
                    }
                    boolean someValueSelected = false;
                    for (SettingsValue value : values) {
                        if (value.isSelected) {
                            SettingsMainActivity.this.navLanguageView.setValueText(value.display);
                            someValueSelected = true;
                            break;
                        }
                    }
                    if (!someValueSelected && values.length > 0) {
                        SettingsMainActivity.this.navLanguageView.setValueText(values[0].display);
                    }
                }
            });
        }
    }

    class C27106 implements OnCheckedChangeListener {
        C27106() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsMainActivity.this.mAvoidTolls = isChecked;
            SettingsMainActivity.this.mIsChanged = true;
        }
    }

    class C27128 implements OnClickListener {
        C27128() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_JOIN_RW);
            SettingsMainActivity.this.setResult(5);
            SettingsMainActivity.this.finish();
        }
    }

    class C27139 implements OnClickListener {
        C27139() {
        }

        public void onClick(View v) {
            SettingsMainActivity.this.startActivityForResult(new Intent(SettingsMainActivity.this, SettingsPowerSaving.class), 321);
        }
    }

    public static String[] getMapModeOptions() {
        if (MAP_MODE_OPTIONS == null) {
            MAP_MODE_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(112), NativeManager.getInstance().getLanguageString(110), NativeManager.getInstance().getLanguageString(111)};
        }
        return MAP_MODE_OPTIONS;
    }

    public static String[] getMapDisplayOptions() {
        if (MAP_DISPLAY_OPTIONS == null) {
            MAP_DISPLAY_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(DisplayStrings.DS_2D), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_3D), NativeManager.getInstance().getLanguageString(110)};
        }
        return MAP_DISPLAY_OPTIONS;
    }

    public void updateConfigItems() {
        boolean isCarpoolOn = true;
        this.mapDisplay.initSelectionNoTranslation(this, new C27041(), 373, MAP_DISPLAY_OPTIONS, MAP_DISPLAY_VALUES, new C27052());
        this.soundSelection = SettingsSound.getSoundSelectionFromConfig();
        this.sound.initSelectionNoTranslation(this, new C27063(), 140, SettingsSound.SOUND_OPTIONS, SettingsSound.SOUND_VALUES, new C27074());
        SettingsSound.setSoundValInConfig(this.soundSelection);
        this.editVideoUrl = this.mCm.getConfigValueString(202);
        if (this.mCm.getConfigValueBool(204)) {
            this.showGuidedTour = true;
        } else {
            this.showGuidedTour = false;
            ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch1)).setVisibility(8);
        }
        if (this.mCm.getConfigValueBool(205)) {
            this.showHowToEditMap = true;
        } else {
            this.showHowToEditMap = false;
            ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch2)).setVisibility(8);
        }
        SettingsNativeManager.getInstance().getNavigationGuidanceTypes(new C27095());
        if (this.mCm.getConfigValueBool(129)) {
            this.mAvoidTolls = this.mCm.getConfigValueBool(141);
            this.avoidTollView.setValue(this.mAvoidTolls);
            this.avoidTollView.initToggleCallbackBoolean(141, new C27106());
        } else {
            this.avoidTollView.setVisibility(8);
        }
        if (!(this.mCm.getConfigValueBool(1) && MyWazeNativeManager.getInstance().HasSocialInfoNTV())) {
            isCarpoolOn = false;
        }
        if (isCarpoolOn) {
            final CarpoolUserData profile = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
            final WazeSettingsView ddsv = (WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsCarpoolProfile);
            if (profile != null) {
                ddsv.initDrillDownAnalytics(DisplayStrings.DS_SETTINGS_CARPOOL, new Runnable() {
                    public void run() {
                        if (profile == null || !profile.didFinishOnboarding()) {
                            SettingsMainActivity.this.carpoolNotOnboarded(ddsv);
                            return;
                        }
                        SettingsMainActivity.this.startActivityForResult(new Intent(SettingsMainActivity.this, SettingsCarpoolActivity.class), 1000);
                    }
                }, AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_RW_DETAILS);
                findViewById(C1283R.id.settingsMainSettingsCarpoolProfileNew).setVisibility(8);
                return;
            }
            carpoolNotOnboarded(ddsv);
            return;
        }
        findViewById(C1283R.id.settingsMainSettingsCarpoolFrame).setVisibility(8);
    }

    private void carpoolNotOnboarded(WazeSettingsView ddsv) {
        ddsv.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_BECOME_CARPOOL_DRIVER));
        ddsv.setOnClickListener(new C27128());
        ((TextView) findViewById(C1283R.id.settingsMainSettingsCarpoolProfileNew)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_MY_WAZE_NEW));
        findViewById(C1283R.id.settingsMainSettingsCarpoolProfileNew).setVisibility(0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1002) {
            SettingsVoiceSearchActivity.updateVoiceSearchCaption(this.mVoiceSearch);
        }
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onDestroy() {
        if (this.mIsChanged) {
            DriveToNativeManager.getInstance().reroute(true);
        }
        super.onDestroy();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_main);
        SettingsNativeManager.getInstance();
        this.mCm = ConfigManager.getInstance();
        this.mNativeManager = NativeManager.getInstance();
        getMapDisplayOptions();
        getMapModeOptions();
        SettingsSound.getSoundOptions();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 90);
        ((SettingsTitleText) findViewById(C1283R.id.settingsMainMapText)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_QUICK_SETTINGS));
        ((SettingsTitleText) findViewById(C1283R.id.settingsMainSettingsText)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_MAIN_SETTINGS_ADVANCED_HEADER));
        ((SettingsTitleText) findViewById(C1283R.id.settingsHelpSettingsText)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_HELP));
        this.mapDisplay = (WazeSettingsView) findViewById(C1283R.id.settingsMainMapDisplay);
        this.sound = (WazeSettingsView) findViewById(C1283R.id.settingsMainSoundMode);
        this.navLanguageView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralNavigationGuidance);
        this.navLanguageView.setKeyText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_VOICE_DIRECTIONS));
        this.saveBatteryView = (WazeSettingsView) findViewById(C1283R.id.settingsSaveBattery);
        if (ConfigManager.getInstance().getConfigValueBool(419)) {
            this.saveBatteryView.setVisibility(0);
            this.saveBatteryView.setText(DisplayStrings.displayString(DisplayStrings.DS_SAVE_BATTERY_MODE_TITLE));
            this.saveBatteryView.setOnClickListener(new C27139());
            ((TextView) findViewById(C1283R.id.settingsSaveBatteryNew)).setText(DisplayStrings.displayString(DisplayStrings.DS_SAVE_BATTERY_MODE_NEW));
        } else {
            this.saveBatteryView.setVisibility(8);
        }
        this.navLanguageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SettingsMainActivity.this.startActivityForResult(new Intent(SettingsMainActivity.this, SettingsNavigationGuidanceActivity.class), 0);
            }
        });
        this.navLanguageView.setValueText("");
        this.mVoiceSearch = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralSearchByVoice);
        this.mVoiceSearch.setKeyText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SEARCH_BY_VOICE));
        SettingsVoiceSearchActivity.updateVoiceSearchCaption(this.mVoiceSearch);
        if (NativeSoundManager.getInstance().isAsrV2Enabled()) {
            this.mVoiceSearch.setVisibility(8);
        }
        this.mVoiceSearch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SEARCH_BY_VOICE).send();
                SettingsMainActivity.this.startActivityForResult(new Intent(SettingsMainActivity.this, SettingsVoiceSearchActivity.class), 1002);
            }
        });
        this.avoidTollView = (WazeSettingsView) findViewById(C1283R.id.settingsNavigationAvoidTollRoads);
        this.avoidTollView.setText(NativeManager.getInstance().getLanguageString(147));
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsGeneral)).initDrillDown(this, 129, SettingsGeneralActivity.class, 1000);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsSocial)).initDrillDown(this, 158, SocialActivity.class, 1000);
        if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
            ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsAccountAndLogin)).initDrillDown(this, DisplayStrings.DS_ACCOUNT_AND_SETTINGS, TempUserProfileActivity.class, 1000);
        } else {
            ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsAccountAndLogin)).initDrillDown(this, DisplayStrings.DS_ACCOUNT_AND_SETTINGS, MyProfileActivity.class, 1000);
        }
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsMap)).initDrillDown(this, DisplayStrings.DS_DISPLAY_AND_MAP_SETTINGS, SettingsMapActivity.class, 1000);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsSound)).initDrillDown(this, DisplayStrings.DS_SOUND_AND_VOICE, SettingsSoundActivity.class, 1000);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsCalendar)).initDrillDown(this, DisplayStrings.DS_CALENDAR_REMINDER_OPTIONS_TITLE, null, 0);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsNotifications)).initDrillDown(this, DisplayStrings.DS_NOTIFICATIONS, null, 0);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsGas)).initDrillDown(this, DisplayStrings.DS_GAS_STATIONS, SettingsGasActivity.class, 1000);
        if (NativeManager.getInstance().ShouldDisplayGasInSettings()) {
            findViewById(C1283R.id.settingsMainSettingsGas).setVisibility(0);
        }
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsNavigation)).initDrillDown(this, DisplayStrings.DS_NAVIGATION, SettingsNavigationActivity.class, 1000);
        WazeSettingsView speed = (WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsSpeedometer);
        if (this.mCm.getConfigValueBool(115) || this.mCm.getConfigValueBool(100)) {
            speed.initDrillDown(this, DisplayStrings.DS_SPEEDOMETER, SettingsSpeedometerActivity.class, 1000);
        } else {
            speed.setVisibility(8);
        }
        WazeSettingsView spotify = (WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsSpotify);
        if (SpotifyManager.getInstance().featureEnabled()) {
            SpotifyManager.getInstance();
            if (SpotifyManager.appInstalled()) {
                spotify.initDrillDown(this, DisplayStrings.DS_SPOTIFY, SettingsSpotifyActivity.class, 1000);
                ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsSpread)).initDrillDown(this, DisplayStrings.DS_SPREAD_THE_WORD, SpreadTheWordActivity.class, 1000);
                ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsAbout)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_ABOUT_SETTING_MENU_ITEM));
                findViewById(C1283R.id.settingsMainSettingsAbout).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ConfigManager.getInstance().aboutClick();
                    }
                });
                if (this.showGuidedTour) {
                    ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch1)).setVisibility(8);
                } else {
                    ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch1)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_WATCH_THE_GUIDED_TOUR));
                }
                if (this.showHowToEditMap) {
                    if (this.mCm.getConfigValueBool(205)) {
                        ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch2)).setVisibility(8);
                    } else {
                        ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch2)).setText(this.mNativeManager.getLanguageString(1111));
                    }
                }
                ((WazeSettingsView) findViewById(C1283R.id.settingsHelpAsk)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_HELP_CENTER));
                ((WazeSettingsView) findViewById(C1283R.id.settingsHelpSendLogs)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_SEND_LOGS));
                findViewById(C1283R.id.settingsHelpWatch1).setOnClickListener(new OnClickListener() {

                    class C27031 implements VideoUrlListener {
                        C27031() {
                        }

                        public void onComplete(String url) {
                            Intent intent = new Intent(SettingsMainActivity.this, VideoActivity.class);
                            intent.putExtra("url", url);
                            SettingsMainActivity.this.startActivity(intent);
                        }
                    }

                    public void onClick(View v) {
                        DisplayMetrics metrics = new DisplayMetrics();
                        SettingsMainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        new InstallNativeManager().getVideoUrl(false, metrics.widthPixels, metrics.heightPixels, new C27031());
                    }
                });
                findViewById(C1283R.id.settingsHelpWatch2).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (SettingsMainActivity.this.editVideoUrl != null) {
                            Intent intent = new Intent(SettingsMainActivity.this, VideoActivity.class);
                            intent.putExtra("url", SettingsMainActivity.this.editVideoUrl);
                            intent.putExtra("landscape", true);
                            SettingsMainActivity.this.startActivity(intent);
                        }
                    }
                });
                findViewById(C1283R.id.settingsHelpAsk).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ConfigManager.getInstance().helpAskQuestion();
                    }
                });
                findViewById(C1283R.id.settingsHelpSendLogs).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ConfigManager.getInstance().sendLogsClick();
                    }
                });
                findViewById(C1283R.id.settingsMainSettingsCalendar).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        SettingsMainActivity.this.startActivityForResult(new Intent(SettingsMainActivity.this, SettingsCalendarActivity.class), 0);
                    }
                });
                findViewById(C1283R.id.settingsMainSettingsNotifications).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (AppService.isNetworkAvailable()) {
                            SettingsMainActivity.this.startActivityForResult(new Intent(SettingsMainActivity.this, SettingsNotificationActivity.class), 0);
                            return;
                        }
                        MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NO_NETWORK_CONNECTION), NativeManager.getInstance().getLanguageString(252), false);
                    }
                });
            }
        }
        spotify.setVisibility(8);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsSpread)).initDrillDown(this, DisplayStrings.DS_SPREAD_THE_WORD, SpreadTheWordActivity.class, 1000);
        ((WazeSettingsView) findViewById(C1283R.id.settingsMainSettingsAbout)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_ABOUT_SETTING_MENU_ITEM));
        findViewById(C1283R.id.settingsMainSettingsAbout).setOnClickListener(/* anonymous class already generated */);
        if (this.showGuidedTour) {
            ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch1)).setVisibility(8);
        } else {
            ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch1)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_WATCH_THE_GUIDED_TOUR));
        }
        if (this.showHowToEditMap) {
            if (this.mCm.getConfigValueBool(205)) {
                ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch2)).setVisibility(8);
            } else {
                ((WazeSettingsView) findViewById(C1283R.id.settingsHelpWatch2)).setText(this.mNativeManager.getLanguageString(1111));
            }
        }
        ((WazeSettingsView) findViewById(C1283R.id.settingsHelpAsk)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_HELP_CENTER));
        ((WazeSettingsView) findViewById(C1283R.id.settingsHelpSendLogs)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_SEND_LOGS));
        findViewById(C1283R.id.settingsHelpWatch1).setOnClickListener(/* anonymous class already generated */);
        findViewById(C1283R.id.settingsHelpWatch2).setOnClickListener(/* anonymous class already generated */);
        findViewById(C1283R.id.settingsHelpAsk).setOnClickListener(/* anonymous class already generated */);
        findViewById(C1283R.id.settingsHelpSendLogs).setOnClickListener(/* anonymous class already generated */);
        findViewById(C1283R.id.settingsMainSettingsCalendar).setOnClickListener(/* anonymous class already generated */);
        findViewById(C1283R.id.settingsMainSettingsNotifications).setOnClickListener(/* anonymous class already generated */);
    }

    public void onResume() {
        updateConfigItems();
        super.onResume();
    }

    public void finish() {
        super.finish();
        overridePendingTransition(C1283R.anim.stack_up, C1283R.anim.slide_down_bottom);
    }
}
