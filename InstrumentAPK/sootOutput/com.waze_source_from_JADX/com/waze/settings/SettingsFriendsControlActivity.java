package com.waze.settings;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class SettingsFriendsControlActivity extends ActivityBase {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_FRIENDS_BUTTON_ON_MAP_SETTINGS_TITLE);
        NativeManager nativeManager = NativeManager.getInstance();
        ConfigManager mCm = ConfigManager.getInstance();
        int selected = getIntent().getIntExtra("selected", 0);
        final SettingValueAdapter adapter = new SettingValueAdapter(this);
        SettingsValue[] values = new SettingsValue[SettingsMapActivity.FRIEND_CONTROL_VALUES.length];
        for (int i = 0; i < SettingsMapActivity.FRIEND_CONTROL_VALUES.length; i++) {
            boolean z;
            values[i] = new SettingsValue(SettingsMapActivity.FRIEND_CONTROL_VALUES[i], nativeManager.getLanguageString(SettingsMapActivity.FRIEND_CONTROL_OPTIONS[i]), false);
            SettingsValue settingsValue = values[i];
            if (i == selected) {
                z = true;
            } else {
                z = false;
            }
            settingsValue.isSelected = z;
        }
        adapter.setValues(values);
        ListView list = (ListView) findViewById(C1283R.id.settingsValueList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

            class C26841 implements Runnable {
                C26841() {
                }

                public void run() {
                    AppService.getMainActivity().getLayoutMgr().SetControlsVisibilty(false);
                }
            }

            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                adapter.setSelected(arg1, position);
                ConfigValues.setStringValue(97, SettingsMapActivity.FRIEND_CONTROL_VALUES[position]);
                MainActivity.registerOnResumeEvent(new C26841());
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_SHOW_FRIENDS_BUTTON_TOGGLE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CHANGED_TO, SettingsMapActivity.FRIEND_CONTROL_VALUES[position]).send();
                SettingsFriendsControlActivity.this.finish();
            }
        });
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(list);
        }
    }
}
