package com.waze.settings;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingsNativeManager.SettingsValuesListener;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class SettingsLanguageActivity extends ActivityBase {
    private SettingsValue[] languages = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 130);
        final SettingValueAdapter adapter = new SettingValueAdapter(this);
        final SettingsNativeManager nativeManager = SettingsNativeManager.getInstance();
        final ListView list = (ListView) findViewById(C1283R.id.settingsValueList);
        nativeManager.getLanguages(new SettingsValuesListener() {
            public void onComplete(SettingsValue[] languages) {
                SettingsLanguageActivity.this.languages = languages;
                adapter.setValues(languages);
                list.invalidateViews();
            }
        });
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                if (SettingsLanguageActivity.this.languages != null) {
                    nativeManager.setLanguage(SettingsLanguageActivity.this.languages[position].value);
                    SettingsLanguageActivity.this.setResult(-1);
                    SettingsLanguageActivity.this.finish();
                }
            }
        });
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(list);
        }
    }
}
