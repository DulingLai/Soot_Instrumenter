package com.waze.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingsNativeManager.SettingsValuesListener;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsGeneralActivity extends ActivityBase {
    public static int INTENT_DONT_SCROLL = 0;
    public static String INTENT_SCROLL_TO = "scroll_to";
    public static int INTENT_SCROLL_TO_SPOTIFY = 1;
    private static final int PHONE_STATE_PERMISSION_REQUEST_CODE = 50284;
    private static final int[] RADIUS_VALUES_KM = new int[]{-1, -2, 5, 25, 50, 100, 200};
    private static final int[] RADIUS_VALUES_MILES = new int[]{-1, -2, 8, 40, 80, 160, 320};
    private static String[] UNIT_OPTIONS;
    private static final String[] UNIT_VALUES = new String[]{"imperial", "metric"};
    private WazeSettingsView keepBackLightView;
    private WazeSettingsView languageView;
    ConfigManager mCm;
    private WazeSettingsView mEndOfDriveReminderView;
    private boolean mIsBacklightOn;
    private NativeManager mNativeManager;
    private boolean mReturnToWaze;
    private int mScrollTo = INTENT_DONT_SCROLL;
    private int mUnitSelection;
    private SettingsNativeManager nativeManager;
    int radiusValueSelected = -1;
    private WazeSettingsView radiusView;
    private WazeSettingsView returnToWazeView;
    private String unitStr;
    private String unitVal;
    private WazeSettingsView unitView;

    class C26931 implements GetIndex {
        C26931() {
        }

        public int fromConfig() {
            SettingsGeneralActivity.this.mUnitSelection = ConfigManager.getOptionIndex(SettingsGeneralActivity.UNIT_VALUES, SettingsGeneralActivity.this.mCm.getConfigValueString(200), 1);
            return SettingsGeneralActivity.this.mUnitSelection;
        }
    }

    class C26942 implements SettingsDialogListener {
        C26942() {
        }

        public void onComplete(int position) {
            SettingsGeneralActivity.this.mUnitSelection = position;
            SettingsGeneralActivity.this.mCm.setConfigValueString(200, SettingsGeneralActivity.UNIT_VALUES[position]);
            SettingsGeneralActivity.this.unitView.setValueText(SettingsGeneralActivity.UNIT_OPTIONS[position]);
            SettingsGeneralActivity.this.updateConfigItems();
        }
    }

    class C26953 implements OnCheckedChangeListener {
        C26953() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsGeneralActivity.this.mIsBacklightOn = isChecked;
        }
    }

    class C26964 implements OnCheckedChangeListener {
        C26964() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsGeneralActivity.this.mReturnToWaze = isChecked;
            if (isChecked && ActivityCompat.checkSelfPermission(SettingsGeneralActivity.this, "android.permission.READ_PHONE_STATE") != 0) {
                SettingsGeneralActivity.this.mReturnToWaze = false;
                SettingsGeneralActivity.this.returnToWazeView.setValue(false);
                SettingsGeneralActivity.this.mCm.setConfigValueBool(122, false);
                ActivityCompat.requestPermissions(SettingsGeneralActivity.this, new String[]{"android.permission.READ_PHONE_STATE"}, SettingsGeneralActivity.PHONE_STATE_PERMISSION_REQUEST_CODE);
            }
        }
    }

    class C26996 implements OnClickListener {
        C26996() {
        }

        public void onClick(View v) {
            SettingsGeneralActivity.this.startActivityForResult(new Intent(SettingsGeneralActivity.this, SettingsLanguageActivity.class), 0);
        }
    }

    class C27007 implements SettingsValuesListener {
        C27007() {
        }

        public void onComplete(SettingsValue[] languages) {
            for (SettingsValue language : languages) {
                if (language.isSelected) {
                    SettingsGeneralActivity.this.languageView.setValueText(language.display);
                    return;
                }
            }
        }
    }

    static String[] getMapModeOptions() {
        if (UNIT_OPTIONS == null) {
            UNIT_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(DisplayStrings.DS_MILE), NativeManager.getInstance().getLanguageString(133)};
        }
        return UNIT_OPTIONS;
    }

    public void updateConfigItems() {
        int value;
        String radiusValueStr;
        this.unitView.initSelectionNoTranslation(this, new C26931(), 128, UNIT_OPTIONS, UNIT_VALUES, new C26942());
        this.unitStr = UNIT_OPTIONS[this.mUnitSelection];
        this.unitVal = UNIT_VALUES[this.mUnitSelection];
        this.mIsBacklightOn = this.mCm.getConfigValueBool(121);
        this.keepBackLightView.setValue(this.mIsBacklightOn);
        this.keepBackLightView.initToggleCallbackBoolean(121, new C26953());
        this.mReturnToWaze = this.mCm.getConfigValueBool(122);
        this.returnToWazeView.setValue(this.mReturnToWaze);
        this.returnToWazeView.initToggleCallbackBoolean(122, new C26964());
        int[] tmpRadiuses = RADIUS_VALUES_KM;
        if (this.unitVal.equals("imperial")) {
            tmpRadiuses = RADIUS_VALUES_MILES;
        }
        final int[] radiuses = tmpRadiuses;
        try {
            value = Integer.parseInt(this.mCm.getConfigValueString(196));
        } catch (NumberFormatException e) {
            value = -1;
        }
        if (value == -1) {
            radiusValueStr = NativeManager.getInstance().getLanguageString(291);
            this.radiusValueSelected = 0;
        } else if (value == -2) {
            radiusValueStr = NativeManager.getInstance().getLanguageString(131);
            this.radiusValueSelected = 1;
        } else {
            int i = 0;
            if (this.unitVal.equals("imperial")) {
                while (i < RADIUS_VALUES_MILES.length && RADIUS_VALUES_MILES[i] != value) {
                    i++;
                }
            } else {
                while (i < RADIUS_VALUES_KM.length && RADIUS_VALUES_KM[i] != value) {
                    i++;
                }
            }
            if (i < RADIUS_VALUES_MILES.length) {
                value = RADIUS_VALUES_KM[i];
                this.radiusValueSelected = i;
            }
            radiusValueStr = value + " " + this.unitStr;
        }
        this.radiusView.setValueText(radiusValueStr);
        this.radiusView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final String[] displayOptions = new String[radiuses.length];
                int i = 0;
                for (int option : radiuses) {
                    if (option == -1) {
                        displayOptions[i] = SettingsGeneralActivity.this.mNativeManager.getLanguageString(291);
                    } else if (option == -2) {
                        displayOptions[i] = SettingsGeneralActivity.this.mNativeManager.getLanguageString(131);
                    } else {
                        displayOptions[i] = "" + SettingsGeneralActivity.RADIUS_VALUES_KM[i] + " " + SettingsGeneralActivity.this.unitStr;
                    }
                    i++;
                }
                SettingsGeneralActivity.this.radiusView.showSubmenu(SettingsGeneralActivity.this, SettingsGeneralActivity.this.mNativeManager.getLanguageString(SettingsGeneralActivity.this.getString(C1283R.string.events_radius)), displayOptions, SettingsGeneralActivity.this.radiusValueSelected, new SettingsDialogListener() {
                    public void onComplete(int position) {
                        SettingsGeneralActivity.this.radiusValueSelected = position;
                        SettingsGeneralActivity.this.radiusView.setValueText(SettingsGeneralActivity.this.mNativeManager.getLanguageString(displayOptions[position]));
                        SettingsGeneralActivity.this.mCm.setConfigValueString(196, String.valueOf(radiuses[position]));
                    }
                });
            }
        });
        this.mEndOfDriveReminderView.setValueText(ConfigManager.getInstance().getConfigValueBool(351) ? this.mNativeManager.getLanguageString(DisplayStrings.DS_ON) : this.mNativeManager.getLanguageString(DisplayStrings.DS_OFF));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PHONE_STATE_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == 0) {
            this.mReturnToWaze = true;
            this.returnToWazeView.setValue(true);
            this.mCm.setConfigValueBool(122, true);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        int i;
        int i2 = 0;
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_general);
        this.mCm = ConfigManager.getInstance();
        this.mNativeManager = NativeManager.getInstance();
        getMapModeOptions();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 129);
        Intent intent = getIntent();
        if (intent != null) {
            this.mScrollTo = intent.getIntExtra(INTENT_SCROLL_TO, INTENT_DONT_SCROLL);
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_GENERAL);
        this.unitView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralUnit);
        this.keepBackLightView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralKeepBackLight);
        this.keepBackLightView.setText(this.mNativeManager.getLanguageString(134));
        this.returnToWazeView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralReturnToCall);
        this.mEndOfDriveReminderView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralDriveReminder);
        this.mEndOfDriveReminderView.setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_END_OF_DRIVE_SETTINGS_TITLE));
        this.mEndOfDriveReminderView.initDrillDown(this, DisplayStrings.DS_END_OF_DRIVE_SETTINGS_TITLE, SettingsEndOfDrive.class, 1000);
        this.mEndOfDriveReminderView.setValueText(ConfigManager.getInstance().getConfigValueBool(351) ? this.mNativeManager.getLanguageString(DisplayStrings.DS_ON) : this.mNativeManager.getLanguageString(DisplayStrings.DS_OFF));
        boolean driveReminderAvailable = ConfigManager.getInstance().getConfigValueBool(354);
        WazeSettingsView wazeSettingsView = this.mEndOfDriveReminderView;
        if (driveReminderAvailable) {
            i = 0;
        } else {
            i = 8;
        }
        wazeSettingsView.setVisibility(i);
        View findViewById = findViewById(C1283R.id.driveReminderShadow);
        if (!driveReminderAvailable) {
            i2 = 8;
        }
        findViewById.setVisibility(i2);
        ((TextView) findViewById(C1283R.id.ReturnToWazeExplainationText)).setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_TAKES_YOU_BACK_TO_WAZE));
        this.returnToWazeView.setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_KEEP_WAZE_ON_TOP));
        this.languageView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralLanguage);
        this.languageView.setKeyText(this.mNativeManager.getLanguageString(130));
        this.radiusView = (WazeSettingsView) findViewById(C1283R.id.settingsGeneralRadius);
        this.radiusView.setKeyText(this.mNativeManager.getLanguageString(132));
        this.languageView.setOnClickListener(new C26996());
        this.nativeManager = SettingsNativeManager.getInstance();
        this.nativeManager.getLanguages(new C27007());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        updateConfigItems();
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onResume() {
        updateConfigItems();
        super.onResume();
    }
}
