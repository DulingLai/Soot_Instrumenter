package com.waze.reports;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.navigate.DriveToNativeManager;
import com.waze.strings.DisplayStrings;

public class ClosureReport extends ReportForm {
    private static final int CONSTRUCTION = 1;
    private static final int EVENT = 2;
    private static final int HAZARD = 0;
    private static final int NONE = -1;
    private static final int REPORT_TYPE = 12;
    private DriveToNativeManager mDtnMgr = DriveToNativeManager.getInstance();
    private int mNumSuggestions;
    private String[] mSuggestions;
    private int reportStage = 1;
    private int selected = -1;

    private final class OnButtonClickListener implements OnClickListener {
        private final int index;

        private OnButtonClickListener(int index) {
            this.index = index;
        }

        public void onClick(View v) {
            ClosureReport.this.onButtonClicked(this.index);
        }
    }

    public ClosureReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, DisplayStrings.DS_SELECT_PLACE);
        if (this.reportStage == 1) {
            this.mTitle = DisplayStrings.DS_SELECT_PLACE;
        } else {
            this.mTitle = DisplayStrings.DS_CLOSURE;
        }
        this.mNumOfButtons = 3;
        this.mLayoutResId = -1;
        initLayout();
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.closurepin_selected;
    }

    protected void initLayout() {
        this.mAreSendButtonsActive = true;
        this.mIsSelectLaneActive = false;
        this.mIsDurationActive = true;
        this.mIsAddDetailsActive = false;
        this.mIsTakePictureActive = true;
        super.initLayout();
        ViewGroup content = (ViewGroup) findViewById(C1283R.id.reportGenericExtArea);
        this.inflater.inflate(C1283R.layout.report_closure_content, content);
        if (this.reportStage == 1) {
            findViewById(C1283R.id.reportGenericSelectorArea).setVisibility(8);
            findViewById(C1283R.id.reportGenericDetailsArea).setVisibility(8);
            content.setVisibility(0);
            buildSuggestions();
            return;
        }
        findViewById(C1283R.id.reportGenericSelectorArea).setVisibility(0);
        findViewById(C1283R.id.reportGenericDetailsArea).setVisibility(0);
        content.setVisibility(8);
    }

    protected void buildSuggestions() {
        ViewGroup container = (ViewGroup) findViewById(C1283R.id.reportClosureLayout);
        container.removeAllViews();
        this.mSuggestions = null;
        this.mNumSuggestions = this.mSuggestions == null ? 0 : this.mSuggestions.length;
        int i = 0;
        while (i < this.mNumSuggestions) {
            buildSuggestion(container, this.mSuggestions[i], null).setOnClickListener(new OnButtonClickListener(i));
            i++;
        }
        View goToMap = buildSuggestion(container, this.nativeManager.getLanguageString(DisplayStrings.DS_CLOSURE_REPORT_OPEN_MAP), null);
        goToMap.setOnClickListener(new OnButtonClickListener(i));
        if (this.mNumSuggestions == 0) {
            goToMap.performClick();
        }
    }

    private View buildSuggestion(ViewGroup container, String string, Drawable image) {
        View v = this.inflater.inflate(C1283R.layout.report_gas_station_line, container, false);
        ((TextView) v.findViewById(C1283R.id.reportGasStationText)).setText(this.nativeManager.getLanguageString(string));
        if (image != null) {
            ((ImageView) v.findViewById(C1283R.id.reportGasStationIcon)).setImageDrawable(image);
        } else {
            v.findViewById(C1283R.id.reportGasStationIcon).setVisibility(8);
        }
        container.addView(v);
        ((LayoutParams) v.getLayoutParams()).weight = 1.0f;
        return v;
    }

    public void toStage2() {
        this.reportStage = 2;
        findViewById(C1283R.id.reportGenericSelectorArea).setVisibility(0);
        findViewById(C1283R.id.reportGenericDetailsArea).setVisibility(0);
        findViewById(C1283R.id.reportGenericExtArea).setVisibility(8);
    }

    protected void onButtonClicked(int buttonIndex) {
        super.onButtonClicked(buttonIndex);
        if (this.reportStage != 1) {
            if (buttonIndex == 0) {
                this.selected = 0;
                ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(414));
            }
            if (buttonIndex == 1) {
                this.selected = 1;
                ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(360));
            }
            if (buttonIndex == 2) {
                this.selected = 2;
                ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_EVENT));
            }
        } else if (buttonIndex < this.mNumSuggestions) {
            toStage2();
        } else {
            stop();
            ClosureMap.launch(this.mCtx, this, this.mReportMenu.getLayoutManager(), false);
        }
    }

    protected void onButtonUnselected(int buttonIndex) {
        super.onButtonUnselected(buttonIndex);
        ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(this.mTitle));
    }

    protected int getReportSubtype() {
        return this.selected;
    }

    protected int getReportType() {
        return 12;
    }

    protected int[] getButtonResourceIds() {
        return new int[]{C1283R.drawable.icon_report_hazard_on_road, C1283R.drawable.icon_report_hazard_construction, C1283R.drawable.icon_report_closure_event};
    }

    protected int[] getButtonDisplayStrings() {
        return new int[]{414, 360, DisplayStrings.DS_EVENT};
    }

    protected int[] getReportSubtypes() {
        return null;
    }
}
