package com.waze.carpool;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.waze.C1283R;
import com.waze.carpool.CarpoolRidesFragment.ISetTitle;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class CarpoolRidesActivity extends ActivityBase implements ISetTitle {
    public static final String DISPLAY_FRAGMENT_NUMBER = "DISPLAY_FRAGMENT_NUMBER";
    public static final String INT_VIEW_MODE = "INT_VIEW_MODE";
    private CarpoolRidesFragment mCarpoolRidesFragment;
    private Fragment mFragment;
    int mIntentMode = 0;
    private TitleBar mTitleBar;

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        Intent $r3 = getIntent();
        if ($r3 != null) {
            this.mIntentMode = $r3.getIntExtra("INT_VIEW_MODE", this.mIntentMode);
        }
        setContentView(C1283R.layout.fragment_activity_w_title);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.fragmentActivityTitle);
        handleCarpoolRidesFragment();
        getFragmentManager().beginTransaction().add(C1283R.id.fragmentActivityFragment, this.mFragment).commit();
    }

    private void handleCarpoolRidesFragment() throws  {
        this.mCarpoolRidesFragment = new CarpoolRidesFragment();
        this.mCarpoolRidesFragment.setIntentMode(this.mIntentMode);
        this.mCarpoolRidesFragment.setISetTitle(this);
        this.mFragment = this.mCarpoolRidesFragment;
    }

    public void onBackPressed() throws  {
        if (this.mCarpoolRidesFragment == null || !this.mCarpoolRidesFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void setTitleBar(int $i0) throws  {
        if ($i0 == 2) {
            this.mTitleBar.init(this, DisplayStrings.DS_ALL_RIDES_HISTORY_TITLE);
        } else {
            this.mTitleBar.init(this, DisplayStrings.DS_ALL_RIDES_TITLE);
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        } else if ($i1 == 10) {
            this.mCarpoolRidesFragment.setupActivity();
        } else {
            super.onActivityResult($i0, $i1, $r1);
        }
    }
}
