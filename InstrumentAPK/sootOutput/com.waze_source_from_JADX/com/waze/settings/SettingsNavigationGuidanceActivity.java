package com.waze.settings;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingsNativeManager.SettingsVoiceDataValuesListener;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;
import com.waze.voice.VoiceData;
import java.util.ArrayList;
import java.util.List;

public class SettingsNavigationGuidanceActivity extends ActivityBase {
    public static final String EXTRA_FILTER_ONLY_PROMPTS = "filter_only_prompts";
    private SettingsValue[] mLanguageValues = null;

    protected void onCreate(Bundle savedInstanceState) {
        boolean filterOnlyPrompts = false;
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getBooleanExtra(EXTRA_FILTER_ONLY_PROMPTS, false)) {
            filterOnlyPrompts = true;
        }
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_VOICE_DIRECTIONS);
        final SettingValueAdapter adapter = new SettingValueAdapter(this);
        final SettingsNativeManager nativeManager = SettingsNativeManager.getInstance();
        final ListView list = (ListView) findViewById(C1283R.id.settingsValueList);
        nativeManager.getVoices(new SettingsVoiceDataValuesListener() {
            public void onComplete(VoiceData[] languages) {
                List<SettingsValue> settingsValues = new ArrayList();
                int i = 0;
                while (i < languages.length) {
                    if (!filterOnlyPrompts || TextUtils.isEmpty(languages[i].SecondLine)) {
                        SettingsValue settingsValue = new SettingsValue(languages[i].Name, languages[i].Name, languages[i].bIsSelected);
                        settingsValue.display2 = languages[i].SecondLine;
                        settingsValue.index = i;
                        settingsValues.add(settingsValue);
                    }
                    i++;
                }
                SettingsNavigationGuidanceActivity.this.mLanguageValues = new SettingsValue[settingsValues.size()];
                settingsValues.toArray(SettingsNavigationGuidanceActivity.this.mLanguageValues);
                adapter.setValues(SettingsNavigationGuidanceActivity.this.mLanguageValues);
                list.invalidateViews();
            }
        });
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                if (SettingsNavigationGuidanceActivity.this.mLanguageValues != null) {
                    if (!filterOnlyPrompts && ConfigManager.getInstance().getConfigValueBool(415)) {
                        ConfigManager.getInstance().setConfigValueBool(415, false);
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_SWITCH_OFF).send();
                    }
                    nativeManager.setVoice(SettingsNavigationGuidanceActivity.this.mLanguageValues[position].index);
                    SettingsNavigationGuidanceActivity.this.setResult(-1);
                    SettingsNavigationGuidanceActivity.this.finish();
                }
            }
        });
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(list);
        }
    }
}
