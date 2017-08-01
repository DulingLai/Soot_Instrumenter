package com.waze.settings;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingsNativeManager.SettingsSearchLangListener;
import com.waze.settings.SettingsNativeManager.SettingsSearchVoiceDataValuesListener;
import com.waze.settings.SettingsNativeManager.StringsToHashMap;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.util.HashMap;

public class SettingsVoiceSearchActivity extends ActivityBase {
    public static final String SEARCH_VOICE_LANG_AUTO = "auto";
    private View mDisabledView;
    private StringsToHashMap mLangData;
    ListView mLangList;
    private HashMap<String, Integer> mLangPosMap;
    private SettingsValue[] mLanguageValues = null;
    private SettingValueAdapter mListAdapter;
    String mSearchTypeConfigVal;
    private SettingsNativeManager mSnm;
    private WazeSettingsView mVoiceSearchType;

    class C27871 implements OnClickListener {
        C27871() {
        }

        public void onClick(View v) {
        }
    }

    class C27882 implements SettingsSearchVoiceDataValuesListener {
        C27882() {
        }

        public void onComplete(StringsToHashMap data) {
            SettingsVoiceSearchActivity.this.mLangData = data;
            if (data.keys == null || data.keys.length == 0 || data.values == null || data.values.length == 0 || data.keys.length != data.values.length) {
                Logger.e("SettingsVoiceSearchActivity: receeved invalid search languages data");
            }
            SettingsVoiceSearchActivity.this.mLanguageValues = new SettingsValue[data.values.length];
            SettingsVoiceSearchActivity.this.mLangPosMap = new HashMap(data.values.length);
            for (int i = 0; i < data.values.length; i++) {
                SettingsVoiceSearchActivity.this.mLanguageValues[i] = new SettingsValue(data.keys[i], data.values[i], false);
                SettingsVoiceSearchActivity.this.mLangPosMap.put(data.keys[i], Integer.valueOf(i));
            }
            SettingsVoiceSearchActivity.this.mListAdapter.setValues(SettingsVoiceSearchActivity.this.mLanguageValues);
            SettingsVoiceSearchActivity.this.mLangList.invalidateViews();
        }
    }

    class C27893 implements OnItemClickListener {
        C27893() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            if (SettingsVoiceSearchActivity.this.mLanguageValues != null) {
                SettingsVoiceSearchActivity.this.mListAdapter.setSelected(arg1, position);
                SettingsVoiceSearchActivity.this.mSnm.setSearchVoice(SettingsVoiceSearchActivity.this.mLanguageValues[position].value);
                SettingsVoiceSearchActivity.updateVoiceSearchCaption(SettingsVoiceSearchActivity.this.mVoiceSearchType);
            }
        }
    }

    class C27914 implements SettingsSearchLangListener {

        class C27901 implements OnCheckedChangeListener {
            C27901() {
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsVoiceSearchActivity.this.searchTypeState(isChecked, true);
            }
        }

        C27914() {
        }

        public void onComplete(String fallback) {
            if (fallback == null || fallback.isEmpty()) {
                Logger.e("SettingsVoiceSearchActivity: Config for search language null or empty. Defaulting to auto");
                SettingsVoiceSearchActivity.this.mSearchTypeConfigVal = SettingsVoiceSearchActivity.SEARCH_VOICE_LANG_AUTO;
            } else {
                SettingsVoiceSearchActivity.this.mSearchTypeConfigVal = fallback;
            }
            boolean on = SettingsVoiceSearchActivity.this.mSearchTypeConfigVal.equalsIgnoreCase(SettingsVoiceSearchActivity.SEARCH_VOICE_LANG_AUTO);
            SettingsVoiceSearchActivity.this.mVoiceSearchType.setValue(on);
            SettingsVoiceSearchActivity.this.mVoiceSearchType.initToggleCallbackBoolean(110, new C27901());
            SettingsVoiceSearchActivity.this.searchTypeState(on, false);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSnm = SettingsNativeManager.getInstance();
        setContentView(C1283R.layout.settings_toggle_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_SEARCH_BY_VOICE);
        this.mVoiceSearchType = (WazeSettingsView) findViewById(C1283R.id.settingsVoiceSearchType);
        this.mVoiceSearchType.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SEARCH_BY_VOICE_AUTO_LONG));
        this.mListAdapter = new SettingValueAdapter(this);
        this.mLangList = (ListView) findViewById(C1283R.id.settingsValueList);
        ((WazeTextView) findViewById(C1283R.id.VoiceSearchExplainationText)).setText(DisplayStrings.displayString(DisplayStrings.DS_SEARCH_BY_VOICE_AUTO_FOOTER));
        this.mDisabledView = findViewById(C1283R.id.disableView);
        this.mDisabledView.setOnClickListener(new C27871());
        this.mSnm.getSearchLanguagesOptions(new C27882());
        this.mLangList.setAdapter(this.mListAdapter);
        this.mLangList.setOnItemClickListener(new C27893());
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(this.mLangList);
        }
    }

    public void onResume() {
        updateConfigItems();
        super.onResume();
    }

    public void updateConfigItems() {
        this.mSnm.getSearchLanguage(new C27914());
    }

    private void searchTypeState(boolean enabled, final boolean changed) {
        if (enabled) {
            this.mDisabledView.setVisibility(0);
            if (changed) {
                this.mSnm.setSearchVoice(SEARCH_VOICE_LANG_AUTO);
            }
        } else {
            this.mDisabledView.setVisibility(8);
            int pos = getPosition(this.mSearchTypeConfigVal);
            if (pos == -1) {
                this.mSnm.getDefaultSearchLanguage(new SettingsSearchLangListener() {
                    public void onComplete(String fallback) {
                        int pos1 = SettingsVoiceSearchActivity.this.getPosition(fallback);
                        if (pos1 == -1) {
                            pos1 = SettingsVoiceSearchActivity.this.mLangList.getFirstVisiblePosition();
                        }
                        SettingsVoiceSearchActivity.this.paintPosition(pos1);
                        if (changed) {
                            SettingsVoiceSearchActivity.this.mSnm.setSearchVoice(SettingsVoiceSearchActivity.this.mLanguageValues[pos1].value);
                        }
                    }
                });
            } else {
                paintPosition(pos);
            }
        }
        updateVoiceSearchCaption(this.mVoiceSearchType);
    }

    private int getPosition(String key) {
        if (key != null && !key.isEmpty() && this.mLangPosMap.containsKey(key)) {
            return ((Integer) this.mLangPosMap.get(key)).intValue();
        }
        Logger.e("SettingsVoiceSearchActivity: received empty/invalid key " + key + " for search language");
        return -1;
    }

    private void paintPosition(int pos) {
        this.mLanguageValues[pos].isSelected = true;
        this.mLangList.invalidateViews();
    }

    public static void updateVoiceSearchCaption(final WazeSettingsView v) {
        SettingsNativeManager.getInstance().getSearchLanguageCaption(true, new SettingsSearchLangListener() {
            public void onComplete(String caption) {
                if (caption != null && !caption.isEmpty()) {
                    v.setValueText(caption);
                }
            }
        });
    }
}
