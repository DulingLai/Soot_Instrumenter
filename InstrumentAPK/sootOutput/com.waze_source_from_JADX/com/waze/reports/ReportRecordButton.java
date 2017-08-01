package com.waze.reports;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.waze.pioneer.PioneerManager;

public class ReportRecordButton extends ImageButton {
    public ReportRecordButton(Context context) {
        super(context);
    }

    public ReportRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (PioneerManager.isActive() && visibility == 0) {
            setVisibility(4);
        }
    }
}
