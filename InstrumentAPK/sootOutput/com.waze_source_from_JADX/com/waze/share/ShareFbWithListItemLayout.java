package com.waze.share;

import android.content.Context;
import android.util.AttributeSet;
import com.waze.ifs.ui.CheckedRelativeLayout;

public class ShareFbWithListItemLayout extends CheckedRelativeLayout {
    public ShareFbWithListItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isChecked() {
        return super.isChecked();
    }

    public void setChecked(boolean arg0) {
        super.setChecked(arg0);
    }

    public void toggle() {
        super.toggle();
    }
}
