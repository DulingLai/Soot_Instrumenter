package com.waze.reports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.waze.C1283R;
import com.waze.ifs.ui.CameraPreview;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;

public class ChitchatReport extends ReportForm {
    private static final int REPORT_TYPE = 0;
    private CameraPreview mPreview;

    public ChitchatReport(Context context, ReportMenu reportMenu) {
        super(context, reportMenu, DisplayStrings.DS_MAP_CHAT_REPORT_TITLE);
        this.mNumOfButtons = 0;
        this.mLayoutResId = C1283R.layout.report_map_note_content;
        initLayout();
    }

    protected void onSend() {
        super.onSend();
    }

    protected void initLayout() {
        super.initLayout();
        findViewById(C1283R.id.reportSend).setEnabled(false);
        findViewById(C1283R.id.reportSend).setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.alert_icon_chit_chat;
    }

    protected int getReportType() {
        return 0;
    }

    protected int[] getReportSubtypes() {
        return null;
    }

    protected int[] getButtonResourceIds() {
        return null;
    }

    protected int[] getButtonDisplayStrings() {
        return null;
    }

    protected void returnFromEditText() {
        super.returnFromEditText();
        if (this.mEditText.getText() != null && !this.mEditText.getText().toString().isEmpty()) {
            findViewById(C1283R.id.reportSend).setEnabled(true);
            findViewById(C1283R.id.reportSend).setAlpha(1.0f);
        }
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        if (this.imageFilename != null) {
            findViewById(C1283R.id.reportSend).setEnabled(true);
            findViewById(C1283R.id.reportSend).setAlpha(1.0f);
        }
    }
}
