package com.waze.settings;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class SettingsMapColorsActivity extends ActivityBase {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 118);
        NativeManager nativeManager = NativeManager.getInstance();
        final ConfigManager mCm = ConfigManager.getInstance();
        int selected = getIntent().getIntExtra("selected", 0);
        final SettingValueAdapter adapter = new SettingValueAdapter(this);
        SettingsValue[] values = new SettingsValue[SettingsMapActivity.MAP_COLORS_VALUES.length];
        int i = 0;
        while (i < SettingsMapActivity.MAP_COLORS_VALUES.length) {
            values[i] = new SettingsValue(SettingsMapActivity.MAP_COLORS_VALUES[i], nativeManager.getLanguageString(SettingsMapActivity.MAP_COLORS_OPTIONS[i]), false);
            values[i].icon = ResManager.GetSkinDrawable(SettingsMapActivity.MAP_COLORS_ICONS[i] + ResManager.mImageExtension);
            values[i].isSelected = i == selected;
            i++;
        }
        adapter.setValues(values);
        ListView list = (ListView) findViewById(C1283R.id.settingsValueList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                mCm.setSkinScheme(SettingsMapActivity.MAP_COLORS_VALUES[position]);
                adapter.setSelected(arg1, position);
                SettingsMapColorsActivity.this.finish();
            }
        });
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(list);
        }
    }
}
