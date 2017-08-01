package com.waze.ifs.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import com.waze.C1283R;

public class CheckedRelativeLayout extends RelativeLayout implements Checkable {
    protected CheckedTextView mCheckedTextView = null;

    public CheckedRelativeLayout(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public boolean isChecked() throws  {
        return this.mCheckedTextView == null ? false : this.mCheckedTextView.isChecked();
    }

    public void setChecked(boolean $z0) throws  {
        if (this.mCheckedTextView != null) {
            this.mCheckedTextView.setChecked($z0);
        }
    }

    public void toggle() throws  {
        if (this.mCheckedTextView == null) {
            this.mCheckedTextView.toggle();
        }
    }

    protected void onFinishInflate() throws  {
        super.onFinishInflate();
        this.mCheckedTextView = (CheckedTextView) findViewById(C1283R.id.checked_text);
    }
}
