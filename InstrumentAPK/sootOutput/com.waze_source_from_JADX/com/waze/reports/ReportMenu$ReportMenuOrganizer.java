package com.waze.reports;

import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.waze.C1283R;
import com.waze.view.button.ReportMenuButton;
import com.waze.view.text.AutoResizeTextView;

class ReportMenu$ReportMenuOrganizer {
    private static final int ID_BASE = 100;
    final RelativeLayout _container;
    int _curNumOfItems = 0;
    final boolean _isVertical;
    int _lastButton = 0;
    int _lastFirstInLine = 0;
    View _lastLineViewToIndent = null;
    final int _menuSpacing;
    final int _numLines;
    final /* synthetic */ ReportMenu this$0;

    ReportMenu$ReportMenuOrganizer(ReportMenu this$0, RelativeLayout container, int numLines, boolean isVertical) {
        this.this$0 = this$0;
        this._container = container;
        this._numLines = numLines;
        this._isVertical = isVertical;
        this._menuSpacing = (int) ReportMenu.access$400(this$0).getResources().getDimension(C1283R.dimen.menuSpacing);
    }

    void addReportButton(String title, int imageResId, int color, OnClickListener ocl) {
        View button = buildReportButton(title, imageResId, color, ocl);
        button.setId(this._curNumOfItems + 100);
        LayoutParams lp = new LayoutParams(ReportMenu.access$1500(this.this$0), ReportMenu.access$1600(this.this$0));
        if (this._lastButton == 0) {
            lp.setMargins(this._isVertical ? ReportMenu.access$1700(this.this$0) : 0, this._isVertical ? (int) (ReportMenu.access$1800(this.this$0) * 17.0f) : ReportMenu.access$1900(this.this$0), 0, 0);
            this._lastFirstInLine = this._curNumOfItems + 100;
            this._lastLineViewToIndent = button;
        } else {
            if (this._curNumOfItems % this._numLines == 0) {
                lp.addRule(this._isVertical ? 5 : 6, this._lastFirstInLine);
                lp.addRule(this._isVertical ? 3 : 1, this._lastFirstInLine);
                lp.setMargins(this._isVertical ? 0 : this._menuSpacing, this._isVertical ? (int) (ReportMenu.access$1800(this.this$0) * 17.0f) : 0, 0, 0);
                this._lastFirstInLine = this._curNumOfItems + 100;
                this._lastLineViewToIndent = button;
            } else {
                lp.addRule(this._isVertical ? 6 : 5, this._lastButton);
                lp.addRule(this._isVertical ? 1 : 3, this._lastButton);
                lp.setMargins(this._isVertical ? this._menuSpacing : 0, 0, 0, 0);
            }
        }
        this._lastButton = this._curNumOfItems + 100;
        this._curNumOfItems++;
        this._container.addView(button, lp);
    }

    void reIndentLastLine() {
        int lastLinePlaces = this._curNumOfItems % this._numLines;
        if (lastLinePlaces != 0) {
            LayoutParams lp = (LayoutParams) this._lastLineViewToIndent.getLayoutParams();
            int addSpace = ((this._isVertical ? ReportMenu.access$1500(this.this$0) : ReportMenu.access$1600(this.this$0)) * (this._numLines - lastLinePlaces)) / 2;
            int i = this._isVertical ? addSpace : this._menuSpacing;
            if (this._isVertical) {
                addSpace = (int) (ReportMenu.access$1800(this.this$0) * 17.0f);
            }
            lp.setMargins(i, addSpace, 0, 0);
        }
    }

    View buildReportButton(String title, int imageResId, int color, OnClickListener ocl) {
        LinearLayout layout = new LinearLayout(ReportMenu.access$400(this.this$0));
        layout.setClickable(true);
        layout.setGravity(17);
        layout.setOrientation(1);
        layout.setClipChildren(false);
        layout.setClipToPadding(false);
        layout.setId(imageResId);
        ReportMenuButton rmb = new ReportMenuButton(ReportMenu.access$400(this.this$0));
        rmb.setImageResource(imageResId);
        rmb.setBackgroundColor(color);
        rmb.setOnClickListener(ocl);
        layout.addView(rmb, (int) (ReportMenu.access$1800(this.this$0) * 100.0f), (int) (ReportMenu.access$1800(this.this$0) * 105.0f));
        AutoResizeTextView artv = new AutoResizeTextView(new ContextThemeWrapper(ReportMenu.access$400(this.this$0), C1283R.style.MenuButton_v4));
        artv.setText(title);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
        lp.setMargins(0, (int) (-7.0f * ReportMenu.access$1800(this.this$0)), 0, 0);
        layout.addView(artv, lp);
        ReportMenu.access$1200(this.this$0).add(rmb);
        ReportMenu.access$1300(this.this$0).add(artv);
        return layout;
    }
}
