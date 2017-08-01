package com.waze.ifs.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.EasingInterpolators;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class UserTooltipView extends FrameLayout {
    private ImageView mBottomArrow;
    private boolean mForceTop;
    protected View mMainContainer;
    private Runnable mOnClick;
    private Runnable mOnClose;
    private Runnable mOnCloseButton;
    private String mStatType;
    private long mTimeToDisplay;
    private TextView mTitleLabel;
    private boolean mTooltipHideInitiated;
    private ImageView mTopArrow;

    class C17591 implements OnClickListener {
        C17591() throws  {
        }

        public void onClick(View v) throws  {
            if (UserTooltipView.this.mStatType != null) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FTE_CLICKED, "TYPE|ACTION", UserTooltipView.this.mStatType + "|" + AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_ACTION_TARGET);
            }
            if (UserTooltipView.this.mOnClick != null) {
                UserTooltipView.this.mOnClick.run();
            }
            UserTooltipView.this.hideTooltip();
        }
    }

    class C17602 implements OnClickListener {
        C17602() throws  {
        }

        public void onClick(View v) throws  {
            if (UserTooltipView.this.mStatType != null) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FTE_CLICKED, "TYPE|ACTION", UserTooltipView.this.mStatType + "|" + "X");
            }
            if (UserTooltipView.this.mOnCloseButton != null) {
                UserTooltipView.this.mOnCloseButton.run();
            }
            if (UserTooltipView.this.mOnClose != null) {
                UserTooltipView.this.mOnClose.run();
            }
            UserTooltipView.this.hideTooltip();
        }
    }

    class C17613 implements Runnable {
        C17613() throws  {
        }

        public void run() throws  {
            if (UserTooltipView.this.mOnClose != null) {
                UserTooltipView.this.mOnClose.run();
            }
            UserTooltipView.this.hideTooltip();
        }
    }

    class C17624 implements Runnable {
        C17624() throws  {
        }

        public void run() throws  {
            if (UserTooltipView.this.getParent() != null) {
                ((ViewGroup) UserTooltipView.this.getParent()).removeView(UserTooltipView.this);
            }
        }
    }

    public UserTooltipView(Context $r1) throws  {
        this($r1, null);
    }

    public UserTooltipView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public UserTooltipView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        View $r3 = LayoutInflater.from(getContext()).inflate(C1283R.layout.nav_list_menu_tooltip, null);
        this.mTitleLabel = (TextView) $r3.findViewById(C1283R.id.lblNavListTooltip);
        this.mTopArrow = (ImageView) $r3.findViewById(C1283R.id.imgTooltipTopArrow);
        this.mBottomArrow = (ImageView) $r3.findViewById(C1283R.id.imgTooltipBottomArrow);
        this.mMainContainer = $r3;
        this.mMainContainer.setLayoutParams(new LayoutParams(-1, PixelMeasure.dp(120)));
        this.mMainContainer.findViewById(C1283R.id.tooltipActionTouchArea).setOnClickListener(new C17591());
        this.mMainContainer.findViewById(C1283R.id.btnCloseTooltipTouchArea).setOnClickListener(new C17602());
        addView($r3);
    }

    public void setup(String $r1, long $l0, String $r2, boolean $z0) throws  {
        this.mTitleLabel.setText(Html.fromHtml($r1));
        this.mTimeToDisplay = $l0;
        this.mStatType = $r2;
        this.mForceTop = $z0;
    }

    public void centerOn(int $i0, int $i1, int $i2, int $i3) throws  {
        byte $b8;
        byte $b4 = (byte) 0;
        int $i5 = PixelMeasure.dp(8);
        int $i6 = PixelMeasure.dp(24);
        boolean $z0 = this.mForceTop || ($i1 + $i3) + $i5 >= $i2;
        ImageView $r1 = this.mTopArrow;
        if ($z0) {
            $b8 = (byte) 8;
        } else {
            $b8 = (byte) 0;
        }
        $r1.setVisibility($b8);
        $r1 = this.mBottomArrow;
        if (!$z0) {
            $b4 = (byte) 8;
        }
        $r1.setVisibility($b4);
        if ($z0) {
            $i1 = ($i1 - $i5) - $i3;
        } else {
            $i1 += $i5;
        }
        this.mMainContainer.setTranslationY((float) $i1);
        if ($z0) {
            $r1 = this.mBottomArrow;
        } else {
            $r1 = this.mTopArrow;
        }
        $i0 -= $i6 / 2;
        $r1.setTranslationX((float) $i0);
        this.mMainContainer.setPivotX((float) $i0);
        this.mMainContainer.setPivotY($z0 ? (float) $i3 : 0.0f);
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        if (super.dispatchTouchEvent($r1)) {
            return true;
        }
        if (this.mStatType != null) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FTE_CLICKED, "TYPE|ACTION", this.mStatType + "|" + "BG");
        }
        if (this.mOnClose != null) {
            this.mOnClose.run();
        }
        hideTooltip();
        return false;
    }

    public void showTooltip() throws  {
        this.mMainContainer.setAlpha(0.0f);
        this.mMainContainer.setScaleX(0.0f);
        this.mMainContainer.setScaleY(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this.mMainContainer, 300, EasingInterpolators.BOUNCE_OUT).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setListener(null);
        if (this.mStatType != null) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FTE_SHOWN, "TYPE|STYLE", this.mStatType + "|" + AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_STYLE_BUBBLE);
        }
        if (this.mTimeToDisplay > 0) {
            postDelayed(new C17613(), this.mTimeToDisplay);
        }
    }

    public void hideTooltip() throws  {
        if (!this.mTooltipHideInitiated) {
            this.mTooltipHideInitiated = true;
            hideTooltipImpl();
        }
    }

    protected void hideTooltipImpl() throws  {
        ViewPropertyAnimatorHelper.initAnimation(this.mMainContainer).alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C17624()));
    }

    public static UserTooltipView showUserTooltip(Activity $r0, View $r1, int $i2, String $r2, long $l0, String $r3, boolean $z0) throws  {
        Rect $r4 = new Rect();
        if ($r1.getGlobalVisibleRect($r4, null)) {
            UserTooltipView $r7 = new UserTooltipView($r0);
            $r7.setup($r2, $l0, $r3, $z0);
            ViewGroup.LayoutParams $r5 = new ViewGroup.LayoutParams(-1, -1);
            $r7.setLayoutParams($r5);
            $r0.addContentView($r7, $r5);
            DisplayMetrics $r9 = $r0.getResources().getDisplayMetrics();
            Rect rect = new Rect();
            $r0.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int $i1 = $i2 + rect.top;
            $i2 = $r4.centerX();
            $i1 = $r4.centerY() - $i1;
            int $i3 = MeasureSpec.makeMeasureSpec($r9.widthPixels, Integer.MIN_VALUE);
            int $i4 = MeasureSpec.makeMeasureSpec($r9.heightPixels, Integer.MIN_VALUE);
            View view = $r7.mMainContainer;
            $r1 = view;
            view.measure($i3, $i4);
            view = $r7.mMainContainer;
            $i3 = view.getMeasuredHeight();
            view = $r7.mMainContainer;
            view.getMeasuredWidth();
            $r7.centerOn($i2, $i1, $r9.heightPixels, $i3);
            $r7.showTooltip();
            return $r7;
        }
        Logger.m43w("UserTooltipView: Button was not visible!");
        return null;
    }

    public static void repositionUserTooltip(UserTooltipView $r0, Activity $r1, View $r2, int $i1, boolean forceTop) throws  {
        Rect $r3 = new Rect();
        if ($r2.getGlobalVisibleRect($r3, null)) {
            DisplayMetrics $r6 = $r1.getResources().getDisplayMetrics();
            Rect $r4 = new Rect();
            $r1.getWindow().getDecorView().getWindowVisibleDisplayFrame($r4);
            int $i0 = $i1 + $r4.top;
            $i1 = $r3.centerX();
            $i0 = $r3.centerY() - $i0;
            $r0.mMainContainer.measure(MeasureSpec.makeMeasureSpec($r6.widthPixels, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec($r6.heightPixels, Integer.MIN_VALUE));
            int $i2 = $r0.mMainContainer.getMeasuredHeight();
            $r0.mMainContainer.getMeasuredWidth();
            $r0.centerOn($i1, $i0, $r6.heightPixels, $i2);
            return;
        }
        Logger.m43w("UserTooltipView: Button was not visible!");
    }

    public void setOnClick(Runnable $r1) throws  {
        this.mOnClick = $r1;
    }

    public void setOnClose(Runnable $r1) throws  {
        this.mOnClose = $r1;
    }

    public void setOnCloseButton(Runnable $r1) throws  {
        this.mOnCloseButton = $r1;
    }

    public void setCarpoolStyle() throws  {
        View $r1 = findViewById(C1283R.id.tooltipMainContainer);
        $r1.setBackgroundResource(C1283R.drawable.carpool_tooltip_bg);
        this.mTopArrow.setImageResource(C1283R.drawable.carpool_tooltip_arrow_top);
        this.mBottomArrow.setImageResource(C1283R.drawable.carpool_tooltip_arrow_bottom);
        RelativeLayout.LayoutParams $r4 = (RelativeLayout.LayoutParams) $r1.getLayoutParams();
        $r4.width = -2;
        $r4.addRule(11);
        $r4.setMargins(PixelMeasure.dp(8), 0, PixelMeasure.dp(8), 0);
        $r1.setLayoutParams($r4);
    }

    public void removeCloseButton() throws  {
        findViewById(C1283R.id.sepCloseTooltip).setVisibility(8);
        findViewById(C1283R.id.btnCloseTooltipTouchArea).setVisibility(8);
    }

    public TextView rightSideText(String $r1) throws  {
        findViewById(C1283R.id.btnCloseTooltip).setVisibility(8);
        TextView $r3 = (TextView) findViewById(C1283R.id.lblNavListTooltipRight);
        $r3.setVisibility(0);
        $r3.setText($r1);
        return $r3;
    }

    public TextView getTextView() throws  {
        return this.mTitleLabel;
    }

    public void reportTimeout() throws  {
        if (this.mStatType != null) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FTE_CLICKED, "TYPE|ACTION", this.mStatType + "|" + "TIMEOUT");
        }
    }
}
