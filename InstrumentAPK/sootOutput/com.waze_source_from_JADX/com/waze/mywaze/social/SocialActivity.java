package com.waze.mywaze.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.FacebookCallback;
import com.waze.mywaze.MyWazeNativeManager.FacebookSettings;
import com.waze.mywaze.MyWazeNativeManager.FoursquareCallback;
import com.waze.mywaze.MyWazeNativeManager.FoursquareSettings;
import com.waze.mywaze.MyWazeNativeManager.LinkedinCallback;
import com.waze.mywaze.MyWazeNativeManager.LinkedinSettings;
import com.waze.mywaze.MyWazeNativeManager.TwitterCallback;
import com.waze.mywaze.MyWazeNativeManager.TwitterSettings;
import com.waze.settings.SettingsDialogListener;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SocialActivity extends ActivityBase implements FacebookCallback, FoursquareCallback, LinkedinCallback, TwitterCallback {
    private static String[] GROUP_ICONS_OPTIONS = null;
    public static final String[] GROUP_ICONS_VALUES = new String[]{"All", "following", "main"};
    private static String[] POPUP_REPORTS_OPTIONS = null;
    public static final String[] POPUP_REPORTS_VALUES = new String[]{"none", "following", "main"};
    public static final String screenName = "SocialActivity";
    private WazeSettingsView PMView;
    private WazeSettingsView iconsView;
    private ConfigManager mCm;
    private int mIconsVal;
    private int mReportsVal;
    private NativeManager nativeManager = AppService.getNativeManager();
    private WazeSettingsView pingView;
    private WazeSettingsView reportsView;

    class C20191 implements GetIndex {
        C20191() throws  {
        }

        public int fromConfig() throws  {
            SocialActivity.this.mReportsVal = ConfigManager.getOptionIndex(SocialActivity.POPUP_REPORTS_VALUES, SocialActivity.this.mCm.getConfigValueString(206), 0);
            return SocialActivity.this.mReportsVal;
        }
    }

    class C20202 implements SettingsDialogListener {
        C20202() throws  {
        }

        public void onComplete(int $i0) throws  {
            SocialActivity.this.mReportsVal = $i0;
            SocialActivity.this.mCm.setConfigValueString(206, SocialActivity.POPUP_REPORTS_VALUES[SocialActivity.this.mReportsVal]);
            SocialActivity.this.reportsView.setValueText(SocialActivity.POPUP_REPORTS_OPTIONS[$i0]);
        }
    }

    class C20213 implements GetIndex {
        C20213() throws  {
        }

        public int fromConfig() throws  {
            SocialActivity.this.mIconsVal = ConfigManager.getOptionIndex(SocialActivity.GROUP_ICONS_VALUES, SocialActivity.this.mCm.getConfigValueString(207), 0);
            return SocialActivity.this.mIconsVal;
        }
    }

    class C20224 implements SettingsDialogListener {
        C20224() throws  {
        }

        public void onComplete(int $i0) throws  {
            SocialActivity.this.mIconsVal = $i0;
            SocialActivity.this.mCm.setConfigValueString(207, SocialActivity.GROUP_ICONS_VALUES[SocialActivity.this.mIconsVal]);
            SocialActivity.this.iconsView.setValueText(SocialActivity.GROUP_ICONS_OPTIONS[SocialActivity.this.mIconsVal]);
        }
    }

    class C20235 implements SettingsToggleCallback {
        C20235() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            MyWazeNativeManager.getInstance().allowPings($z0);
            MyWazeNativeManager.getInstance().OnSettingChange_SetVisibilty();
        }
    }

    class C20246 implements SettingsToggleCallback {
        C20246() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            MyWazeNativeManager.getInstance().SetallowPM($z0);
            MyWazeNativeManager.getInstance().OnSettingChange_SetVisibilty();
        }
    }

    class C20257 implements OnClickListener {
        C20257() throws  {
        }

        public void onClick(View v) throws  {
            MyWazeNativeManager.getInstance().getFacebookSettings(SocialActivity.this);
        }
    }

    class C20268 implements OnClickListener {
        C20268() throws  {
        }

        public void onClick(View v) throws  {
            MyWazeNativeManager.getInstance().getTwitterSettings(SocialActivity.this);
        }
    }

    class C20279 implements OnClickListener {
        C20279() throws  {
        }

        public void onClick(View v) throws  {
            MyWazeNativeManager.getInstance().getLinkedinSettings(SocialActivity.this);
        }
    }

    static String[] getPopupReportsOptions() throws  {
        if (POPUP_REPORTS_OPTIONS == null) {
            POPUP_REPORTS_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(165), NativeManager.getInstance().getLanguageString(406), NativeManager.getInstance().getLanguageString(175)};
        }
        return POPUP_REPORTS_OPTIONS;
    }

    static String[] getGroupIconsOptions() throws  {
        if (GROUP_ICONS_OPTIONS == null) {
            GROUP_ICONS_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(84), NativeManager.getInstance().getLanguageString(174), NativeManager.getInstance().getLanguageString(175)};
        }
        return GROUP_ICONS_OPTIONS;
    }

    public void updateConfigItems() throws  {
        this.reportsView.initSelectionNoTranslation(this, new C20191(), 166, POPUP_REPORTS_OPTIONS, POPUP_REPORTS_VALUES, new C20202());
        this.iconsView.initSelectionNoTranslation(this, new C20213(), 167, GROUP_ICONS_OPTIONS, GROUP_ICONS_VALUES, new C20224());
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.social);
        this.mCm = ConfigManager.getInstance();
        getPopupReportsOptions();
        getGroupIconsOptions();
        ((TitleBar) findViewById(C1283R.id.socialTitle)).init(this, 158);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SOCIAL_NETWORKS);
        ((SettingsTitleText) findViewById(C1283R.id.SocialTitleText)).setText(this.nativeManager.getLanguageString(161));
        ((SettingsTitleText) findViewById(C1283R.id.SocialChitChatTitleText)).setText(this.nativeManager.getLanguageString(163));
        ((TextView) findViewById(C1283R.id.SocialBodyText)).setText(this.nativeManager.getLanguageString(154));
        ((SettingsTitleText) findViewById(C1283R.id.SocialGroupTitleText)).setText(this.nativeManager.getLanguageString(83));
        this.reportsView = (WazeSettingsView) findViewById(C1283R.id.settingsGroupsReports);
        this.iconsView = (WazeSettingsView) findViewById(C1283R.id.settingsGroupsIcons);
        this.pingView = (WazeSettingsView) findViewById(C1283R.id.enablePing);
        this.PMView = (WazeSettingsView) findViewById(C1283R.id.enablePM);
        MyWazeNativeManager.getInstance().GetAllowPM(this);
        MyWazeNativeManager.getInstance().GetAllowPing(this);
        this.PMView.setText(this.nativeManager.getLanguageString(155));
        this.pingView.setText(this.nativeManager.getLanguageString(156));
        this.pingView.setOnChecked(new C20235());
        this.PMView.setOnChecked(new C20246());
        WazeSettingsView $r9 = (WazeSettingsView) findViewById(C1283R.id.socialFacebook);
        if (MyWazeNativeManager.getInstance().FacebookEnabledNTV()) {
            $r9.setOnClickListener(new C20257());
            $r9.setText(this.nativeManager.getLanguageString(396));
        } else {
            $r9.setVisibility(8);
        }
        $r9 = (WazeSettingsView) findViewById(C1283R.id.socialTwitter);
        if (MyWazeNativeManager.getInstance().TwitterEnabledNTV()) {
            $r9.setOnClickListener(new C20268());
            $r9.setText(this.nativeManager.getLanguageString(159));
        } else {
            $r9.setVisibility(8);
        }
        $r9 = (WazeSettingsView) findViewById(C1283R.id.socialLinked);
        if (MyWazeNativeManager.getInstance().LinkedinEnabledNTV()) {
            $r9.setOnClickListener(new C20279());
            $r9.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_LINKEDIN));
            return;
        }
        $r9.setVisibility(8);
    }

    public void onFacebookSettings(FacebookSettings $r1) throws  {
        Intent $r2 = new Intent(this, FacebookActivity.class);
        $r2.putExtra("com.waze.mywaze.facebooksettings", $r1);
        startActivityForResult($r2, 0);
    }

    public void onFoursquareSettings(FoursquareSettings $r1) throws  {
        Intent $r2 = new Intent(this, FoursquareActivity.class);
        $r2.putExtra("com.waze.mywaze.foursquaresettings", $r1);
        startActivityForResult($r2, 0);
    }

    public void onTwitterSettings(TwitterSettings $r1) throws  {
        Intent $r2 = new Intent(this, TwitterActivity.class);
        $r2.putExtra("com.waze.mywaze.twittersettings", $r1);
        startActivityForResult($r2, 0);
    }

    public void onLinkedinSettings(LinkedinSettings $r1) throws  {
        Intent $r2 = new Intent(this, LinkedInActivity.class);
        $r2.putExtra("com.waze.mywaze.linkedinsettings", $r1);
        startActivityForResult($r2, 0);
    }

    public void SetAllowPing(boolean $z0) throws  {
        ((WazeSettingsView) findViewById(C1283R.id.enablePing)).setValue($z0);
    }

    public void SetAllowPM(boolean $z0) throws  {
        ((WazeSettingsView) findViewById(C1283R.id.enablePM)).setValue($z0);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    public void onResume() throws  {
        updateConfigItems();
        super.onResume();
    }
}
