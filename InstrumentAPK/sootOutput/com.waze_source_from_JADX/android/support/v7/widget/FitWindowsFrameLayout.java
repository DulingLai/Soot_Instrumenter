package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class FitWindowsFrameLayout extends FrameLayout implements FitWindowsViewGroup {
    private OnFitSystemWindowsListener mListener;

    public FitWindowsFrameLayout(Context $r1) throws  {
        super($r1);
    }

    public FitWindowsFrameLayout(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public void setOnFitSystemWindowsListener(OnFitSystemWindowsListener $r1) throws  {
        this.mListener = $r1;
    }

    protected boolean fitSystemWindows(Rect $r1) throws  {
        if (this.mListener != null) {
            this.mListener.onFitSystemWindows($r1);
        }
        return super.fitSystemWindows($r1);
    }
}
