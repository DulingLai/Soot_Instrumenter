package com.waze.ifs.ui;

import android.content.Context;
import android.widget.TextView;
import com.waze.C1283R;

public class TinyTooltip extends LayoutTooltip {
    private TextView mTextView = ((TextView) this.mView.findViewById(C1283R.id.tinyTooltipText));

    public TinyTooltip(Context $r1, String $r2) throws  {
        super($r1, C1283R.layout.tiny_tooltip, -1);
        this.mWindow.setTouchable(false);
        this.mWindow.setFocusable(false);
        this.mWindow.setOutsideTouchable(false);
        this.mTextView.setText($r2);
    }
}
