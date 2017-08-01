package com.waze.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SectionIndexer;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingValueAdapter;
import com.waze.settings.SettingsValue;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.Arrays;

public class ChooseCountryPhoneActivity extends ActivityBase {
    private SettingsValue[] countries = null;
    private IndexedSettingValueAdapter mAdapter;

    class C22391 implements OnClickListener {
        C22391() {
        }

        public void onClick(View v) {
            ChooseCountryPhoneActivity.this.setResult(0);
            ChooseCountryPhoneActivity.this.finish();
        }
    }

    class C22402 implements OnItemClickListener {
        C22402() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            if (ChooseCountryPhoneActivity.this.countries != null) {
                if (ChooseCountryPhoneActivity.this.mAdapter.getSelectedItem() != null) {
                    ChooseCountryPhoneActivity.this.mAdapter.getSelectedItem().isSelected = false;
                }
                ChooseCountryPhoneActivity.this.countries[position].isSelected = true;
                Intent intent = new Intent();
                intent.putExtra("index", Integer.parseInt(ChooseCountryPhoneActivity.this.countries[position].value));
                ChooseCountryPhoneActivity.this.setResult(-1, intent);
                ChooseCountryPhoneActivity.this.finish();
            }
        }
    }

    public static class IndexedSettingValueAdapter extends SettingValueAdapter implements SectionIndexer {
        private static int MAX_SECTIONS = 64;
        int _numSections = 0;
        int[] _positionForSection = new int[MAX_SECTIONS];
        int[] _sectionForPosition;
        String[] _sections = new String[MAX_SECTIONS];

        public IndexedSettingValueAdapter(Context context) {
            super(context);
        }

        public void setValues(SettingsValue[] values) {
            this._sectionForPosition = new int[values.length];
            int lastSection = -1;
            for (int i = 0; i < values.length; i++) {
                char section = values[i].display.charAt(0);
                if (section != lastSection) {
                    this._sections[this._numSections] = "" + section;
                    this._positionForSection[this._numSections] = i;
                    lastSection = section;
                    this._numSections++;
                }
                this._sectionForPosition[i] = this._numSections - 1;
                if (this._numSections == MAX_SECTIONS) {
                    break;
                }
            }
            this._sections = (String[]) Arrays.copyOf(this._sections, this._numSections);
            this._positionForSection = Arrays.copyOf(this._positionForSection, this._numSections);
            super.setValues(values);
        }

        public int getPositionForSection(int sectionIndex) {
            if (sectionIndex < this._positionForSection.length) {
                return this._positionForSection[sectionIndex];
            }
            return -1;
        }

        public int getSectionForPosition(int position) {
            if (position < this._sectionForPosition.length) {
                return this._sectionForPosition[position];
            }
            return -1;
        }

        public Object[] getSections() {
            return this._sections;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_SELECT_COUNTRY);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).setOnClickCloseListener(new C22391());
        this.mAdapter = new IndexedSettingValueAdapter(this);
        ListView list = (ListView) findViewById(C1283R.id.settingsValueList);
        this.countries = PhoneInputView.getCountryCodes();
        list.setAdapter(this.mAdapter);
        this.mAdapter.setValues(this.countries);
        list.invalidateViews();
        list.setOnItemClickListener(new C22402());
    }
}
