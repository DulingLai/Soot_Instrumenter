package com.waze.planned_drive;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.menus.SideMenuAutoCompleteRecycler;
import com.waze.menus.SideMenuAutoCompleteRecycler.AutoCompleteAdHandler;
import com.waze.menus.SideMenuAutoCompleteRecycler.Mode;
import com.waze.menus.SideMenuSearchBar;
import com.waze.menus.SideMenuSearchBar.SearchBarActionListener;
import com.waze.strings.DisplayStrings;
import java.util.List;

public class PlannedDriveAlternateFromActivity extends ActivityBase {
    public static final int CHANGE_DESTINATION_MODE = 1;
    public static final int CHANGE_ORIGIN_MODE = 0;
    public static final String MODE = "mode";
    private SideMenuAutoCompleteRecycler mAutoCompleteRecycler;
    private SideMenuSearchBar mSearchBar;

    class C23301 implements AutoCompleteAdHandler {
        C23301() {
        }

        public void hideKeyboard() {
        }

        public void setSearchTerm(String searchTerm, boolean isVoiceSearch) {
            PlannedDriveAlternateFromActivity.this.mSearchBar.setSearchTerm(searchTerm, isVoiceSearch);
        }

        public void closeSideMenu() {
        }
    }

    class C23312 implements SearchBarActionListener {
        C23312() {
        }

        public void onCancelClick() {
            PlannedDriveAlternateFromActivity.this.finish();
        }

        public void onSpeechButtonClick() {
        }

        public void onSearchTextChanged(String searchText) {
            PlannedDriveAlternateFromActivity.this.mAutoCompleteRecycler.beginSearchTerm(searchText);
        }

        public void onSearchButtonClick() {
            PlannedDriveAlternateFromActivity.this.mAutoCompleteRecycler.openSearchScreen();
        }

        public void onAddClick() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.alternate_from_activity_layout);
        int modeInt = getIntent().getIntExtra(MODE, 0);
        Mode mode = modeInt == 0 ? Mode.PlannedDriveSelectOrigin : Mode.PlannedDriveSelectDestination;
        this.mSearchBar = (SideMenuSearchBar) findViewById(C1283R.id.searchBar);
        this.mAutoCompleteRecycler = (SideMenuAutoCompleteRecycler) findViewById(C1283R.id.autocompleteRecycler);
        this.mSearchBar.setHint(NativeManager.getInstance().getLanguageString(modeInt == 0 ? DisplayStrings.DS_FUTURE_DRIVES_PLAN_CHANGE_ORIGIN : DisplayStrings.DS_FUTURE_DRIVES_PLAN_CHANGE_DESTINATION));
        this.mAutoCompleteRecycler.setMode(mode);
        this.mAutoCompleteRecycler.setAdHandler(new C23301());
        this.mAutoCompleteRecycler.loadHistory();
        this.mAutoCompleteRecycler.setDisplayingCategoryBar(false);
        this.mSearchBar.setSearchBarActionListener(new C23312());
        this.mSearchBar.showCancelButton(0, null);
        this.mSearchBar.enableFocus(false);
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.blue_status));
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_SHOWN).addParam("TYPE", "PLANNED_DRIVE").addParam("ADD_STOP", AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1234) {
            finish();
        } else if (resultCode == -1) {
            List<String> matches = data.getStringArrayListExtra("android.speech.extra.RESULTS");
            if (matches.size() > 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VOICE_SEARCH_RECOGNIZED);
                this.mSearchBar.setSearchTerm((String) matches.get(0), true);
            }
        }
    }
}
