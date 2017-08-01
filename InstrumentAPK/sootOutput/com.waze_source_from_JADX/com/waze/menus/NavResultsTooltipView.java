package com.waze.menus;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.UserTooltipView;
import com.waze.strings.DisplayStrings;

public class NavResultsTooltipView extends UserTooltipView {
    private static final long MINIMUM_DISPLAY_TIME = 2000;
    private long mMinTimeToDisplay;
    private long mShowTime;

    class C19001 implements Runnable {
        C19001() throws  {
        }

        public void run() throws  {
            NavResultsTooltipView.this.hideTooltipImpl();
        }
    }

    public NavResultsTooltipView(Context $r1) throws  {
        super($r1);
    }

    public NavResultsTooltipView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public NavResultsTooltipView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    public void showTooltip() throws  {
        super.showTooltip();
        this.mShowTime = System.currentTimeMillis();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NAV_RESULTS_ADD_STOP_TIP_SHOWN);
    }

    public void setup(String $r1, long $l0, String $r2, boolean $z0) throws  {
        this.mMinTimeToDisplay = $l0;
        super.setup($r1, 0, $r2, $z0);
    }

    protected void hideTooltipImpl() throws  {
        long $l0 = System.currentTimeMillis() - this.mShowTime;
        if ($l0 < this.mMinTimeToDisplay) {
            postDelayed(new C19001(), this.mMinTimeToDisplay - $l0);
            return;
        }
        super.hideTooltipImpl();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NAV_RESULTS_ADD_STOP_TIP_CLOSED);
    }

    public static View showNavResultsAddStopTooltip(View $r0) throws  {
        ActivityBase $r3 = AppService.getActiveActivity();
        Rect $r1 = new Rect();
        if ($r0.getGlobalVisibleRect($r1, null)) {
            NavResultsTooltipView $r4 = new NavResultsTooltipView($r3);
            $r4.setup(DisplayStrings.displayString(DisplayStrings.DS_ETA_STOP_TOOLTOP), MINIMUM_DISPLAY_TIME, null, false);
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            $r4.setLayoutParams(layoutParams);
            $r3.addContentView($r4, layoutParams);
            int $i1 = $r1.centerX();
            int centerY = $r1.centerY() - ($r0.getHeight() / 2);
            DisplayMetrics $r7 = $r3.getResources().getDisplayMetrics();
            int $i2 = MeasureSpec.makeMeasureSpec($r7.widthPixels, Integer.MIN_VALUE);
            int $i3 = MeasureSpec.makeMeasureSpec($r7.heightPixels, Integer.MIN_VALUE);
            View view = $r4.mMainContainer;
            View $r8 = view;
            view.measure($i2, $i3);
            view = $r4.mMainContainer;
            $i2 = view.getMeasuredHeight();
            $r4.centerOn($i1, centerY, $r0.getHeight(), $i2);
            $r4.showTooltip();
            return $r4;
        }
        Logger.m43w("NavResultsTooltipView: Button was not visible!");
        return null;
    }
}
