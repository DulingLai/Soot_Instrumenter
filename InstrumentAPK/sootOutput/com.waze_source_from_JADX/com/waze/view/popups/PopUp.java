package com.waze.view.popups;

import android.content.Context;
import android.graphics.Rect;
import android.widget.FrameLayout;
import com.waze.LayoutManager;

public abstract class PopUp extends FrameLayout {
    public static int ANIMATION_DURATION = 300;
    private Context mContext = null;
    private int mTimer = 0;

    public abstract void hide();

    public abstract boolean onBackPressed();

    public PopUp(Context context, LayoutManager layoutManager) {
        super(context);
        this.mContext = context;
    }

    public int GetTimer() {
        return this.mTimer;
    }

    public void setPopUpTimer(int nTimer) {
        this.mTimer = nTimer;
    }

    public int GetHeight() {
        return (int) (((float) 185) * this.mContext.getResources().getDisplayMetrics().density);
    }

    public Rect getRect() {
        Rect rect = new Rect();
        getHitRect(rect);
        int[] loc = new int[2];
        getLocationInWindow(loc);
        rect.right += loc[0];
        rect.left += loc[0];
        rect.top += loc[1];
        rect.bottom += loc[1];
        return rect;
    }

    public void setShiftEffect(boolean bOnLeft, float shift) {
    }

    public void setPageIndicatorShown(boolean shown) {
    }

    public void onOrientationChanged() {
    }
}
