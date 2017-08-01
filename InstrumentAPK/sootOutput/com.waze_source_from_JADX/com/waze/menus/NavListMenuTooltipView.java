package com.waze.menus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.UserTooltipView;
import com.waze.strings.DisplayStrings;

public class NavListMenuTooltipView extends UserTooltipView {
    private static final long MINIMUM_DISPLAY_TIME = 2000;
    private long mMinTimeToDisplay;
    private long mShowTime;

    class C18991 implements Runnable {
        C18991() throws  {
        }

        public void run() throws  {
            NavListMenuTooltipView.this.hideTooltipImpl();
        }
    }

    public NavListMenuTooltipView(Context $r1) throws  {
        super($r1);
    }

    public NavListMenuTooltipView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public NavListMenuTooltipView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    public void showTooltip() throws  {
        super.showTooltip();
        this.mShowTime = System.currentTimeMillis();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_MENU_TIP_SHOWN);
    }

    public void setup(String $r1, long $l0, String $r2, boolean $z0) throws  {
        this.mMinTimeToDisplay = $l0;
        super.setup($r1, 0, $r2, $z0);
    }

    protected void hideTooltipImpl() throws  {
        long $l0 = System.currentTimeMillis() - this.mShowTime;
        if ($l0 < this.mMinTimeToDisplay) {
            postDelayed(new C18991(), this.mMinTimeToDisplay - $l0);
            return;
        }
        super.hideTooltipImpl();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_MENU_TIP_CLOSED);
    }

    public static boolean showNavListTooltip(Activity $r0, View $r1, int $i0) throws  {
        Rect $r3 = new Rect();
        if ($r1.getGlobalVisibleRect($r3, null)) {
            NavListMenuTooltipView $r2 = new NavListMenuTooltipView($r0);
            $r2.setup(DisplayStrings.displayString(DisplayStrings.DS_NAVLIST_OPTIONS_TOOLTIP_TITLE), MINIMUM_DISPLAY_TIME, null, false);
            LayoutParams $r4 = new LayoutParams(-1, -1);
            $r2.setLayoutParams($r4);
            $r0.addContentView($r2, $r4);
            int $i1 = $r0.getResources().getDisplayMetrics().heightPixels - $i0;
            int $i2 = $r3.centerX();
            $i1 = $r3.centerY() - $i1;
            DisplayMetrics $r7 = $r0.getResources().getDisplayMetrics();
            int $i3 = MeasureSpec.makeMeasureSpec($r7.widthPixels, Integer.MIN_VALUE);
            int $i4 = MeasureSpec.makeMeasureSpec($r7.heightPixels, Integer.MIN_VALUE);
            View view = $r2.mMainContainer;
            $r1 = view;
            view.measure($i3, $i4);
            view = $r2.mMainContainer;
            $r2.centerOn($i2, $i1, $i0, view.getMeasuredHeight());
            $r2.showTooltip();
            return true;
        }
        Logger.m43w("NavListMenuTooltipView: Button was not visible!");
        return false;
    }
}
