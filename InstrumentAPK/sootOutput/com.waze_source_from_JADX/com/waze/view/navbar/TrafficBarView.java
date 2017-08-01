package com.waze.view.navbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.rtalerts.RTAlertsNativeManager;
import com.waze.share.ShareNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;

public class TrafficBarView extends RelativeLayout implements OnClickListener {
    private boolean ASCMode = false;
    private boolean animatingVisibility = false;
    private final Runnable disappearifyTheEnforcementBarTip = new C30321();
    private int eventualVisibility;
    private boolean justShown = false;
    private View mBg;
    private int mCurPercent = 0;
    private int mCurTime = 0;
    private CurrentlyActive mCurrBar = null;
    private Runnable mDisapearRunnable;
    private View mDriver;
    private View mEnforcementTip;
    private boolean mFirstTime = true;
    private TrafficBarColoredJamView mJam;
    private TextView mTipEnforcementDistance;
    private TextView mTipEnforcementLabel;
    private View mTipFrame;
    private boolean mTipIsAnimating = false;
    private boolean mTipIsShowing = true;
    private TextView mTipTrafficTime;
    private TextView mTipTraficTitle;
    private View mTrafficTip;

    class C30321 implements Runnable {
        C30321() {
        }

        public void run() {
            TrafficBarView.this.disappearifyTheTip();
        }
    }

    class C30332 implements Runnable {
        C30332() {
        }

        public void run() {
            TrafficBarView.this.disappearifyTheTip();
        }
    }

    class C30364 implements AnimationListener {
        C30364() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            TrafficBarView.this.mTipIsAnimating = false;
            TrafficBarView.this.mTipFrame.setVisibility(8);
        }
    }

    class C30375 implements AnimationListener {
        C30375() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            super.setVisibility(TrafficBarView.this.eventualVisibility);
            TrafficBarView.this.animatingVisibility = false;
        }
    }

    private enum CurrentlyActive {
        traffic,
        enforcement
    }

    public TrafficBarView(Context context) {
        super(context);
        initMembers(context);
    }

    public TrafficBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMembers(context);
    }

    public TrafficBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initMembers(context);
    }

    private void initMembers(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C1283R.layout.traffic_bar, this);
        this.mBg = findViewById(C1283R.id.trafficBarBg);
        this.mJam = (TrafficBarColoredJamView) findViewById(C1283R.id.trafficBarJam);
        this.mDriver = findViewById(C1283R.id.trafficBarDriver);
        this.mTipFrame = findViewById(C1283R.id.trafficBarTipFrame);
        this.mTrafficTip = findViewById(C1283R.id.trafficBarTip);
        this.mEnforcementTip = findViewById(C1283R.id.trafficBarTipEnforcement);
        this.mTipEnforcementLabel = (TextView) findViewById(C1283R.id.trafficBarEnforcementLabel);
        this.mTipEnforcementDistance = (TextView) findViewById(C1283R.id.trafficBarEnforcementLabelDistance);
        this.mTipTraficTitle = (TextView) findViewById(C1283R.id.trafficBarTipTitle);
        this.mTipTrafficTime = (TextView) findViewById(C1283R.id.trafficBarTipTime);
        this.mBg.setOnClickListener(this);
        this.mTrafficTip.setOnClickListener(this);
        this.mEnforcementTip.setOnClickListener(this);
        initTip();
    }

    public void onClick(View v) {
        if (this.ASCMode) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_AVERAGE_SPEED_CAM_TAP_ON_BAR, "PERCENT|ETA", this.mCurPercent + "|" + ShareNativeManager.getInstance().getEtaNTV());
        } else {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_JAM_CROSS_TIME_CLICKED, "TIME_TO_CROSS|ETA", this.mCurTime + "|" + ShareNativeManager.getInstance().getEtaNTV());
        }
        if (!this.mTipIsAnimating) {
            if (this.mTipIsShowing) {
                disappearifyTheTip();
            } else {
                appearifyTheTip(0);
            }
        }
    }

    private void initTip() {
        this.mTipFrame.measure(-2, -2);
        this.mDriver.measure(-2, -2);
        this.mEnforcementTip.measure(-2, -2);
        LayoutParams p = (LayoutParams) this.mTipFrame.getLayoutParams();
        p.bottomMargin = (this.mDriver.getMeasuredHeight() / 2) - (this.mTrafficTip.getMeasuredHeight() / 2);
        this.mTipFrame.setLayoutParams(p);
        this.mTipFrame.setVisibility(0);
        this.mTrafficTip.setVisibility(0);
        this.mEnforcementTip.setVisibility(0);
    }

    public void appearifyTheTrafficBarTip(int timeout) {
        appearifyTheTip(timeout);
    }

    private void appearifyTheTip(final int timeout) {
        if (this.mDisapearRunnable == null) {
            this.mDisapearRunnable = new C30332();
        }
        if (this.mTipIsShowing) {
            removeCallbacks(this.mDisapearRunnable);
            if (timeout > 0) {
                postDelayed(this.mDisapearRunnable, (long) timeout);
                return;
            }
            return;
        }
        if (this.mTipEnforcementDistance.getVisibility() == 0) {
            this.mTipEnforcementLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_AVERAGE_SPEED_ENFORCEMENT_ZONE_BAR_POPUP));
        }
        this.mTipIsAnimating = true;
        this.mTipFrame.setVisibility(0);
        this.mTipFrame.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            class C30341 implements AnimationListener {
                C30341() {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    TrafficBarView.this.mTipIsAnimating = false;
                    TrafficBarView.this.mTipIsShowing = true;
                    if (timeout > 0) {
                        TrafficBarView.this.postDelayed(TrafficBarView.this.mDisapearRunnable, (long) timeout);
                    }
                }
            }

            public void onGlobalLayout() {
                TrafficBarView.this.mTipFrame.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                AnimationSet as = new AnimationSet(true);
                as.setDuration(200);
                as.setFillBefore(true);
                as.setInterpolator(new DecelerateInterpolator());
                as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                as.addAnimation(new TranslateAnimation(0, (float) (-PixelMeasure.dp(10)), 0, 0.0f, 0, 0.0f, 0, 0.0f));
                as.setAnimationListener(new C30341());
                TrafficBarView.this.mTipFrame.startAnimation(as);
            }
        });
    }

    private void disappearifyTheTip() {
        this.mTipIsAnimating = true;
        this.mTipIsShowing = false;
        if (this.mDisapearRunnable != null) {
            removeCallbacks(this.mDisapearRunnable);
        }
        AnimationSet as = new AnimationSet(true);
        as.setDuration(200);
        as.setFillBefore(true);
        as.setInterpolator(new AccelerateInterpolator());
        as.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        as.addAnimation(new TranslateAnimation(0, 0.0f, 0, (float) (-PixelMeasure.dp(10)), 0, 0.0f, 0, 0.0f));
        as.setAnimationListener(new C30364());
        this.mTipFrame.startAnimation(as);
    }

    public void setTime(int current_percent, int total_time, int[] levels, int[] lengths, int distanceLeft) {
        if (this.mCurrBar == CurrentlyActive.traffic && (levels == null || lengths == null)) {
            Logger.d("TrafficBarView: Cannot show enforcement bar because traffic bar is active");
            return;
        }
        this.mCurPercent = current_percent;
        this.mCurTime = total_time;
        if (canShowInternal()) {
            int bottomMargin;
            int driverHeight = this.mDriver.getHeight();
            if (this.mCurPercent >= 0) {
                bottomMargin = ((this.mJam.getMeasuredHeight() * this.mCurPercent) / 100) - (driverHeight / 2);
            } else {
                bottomMargin = ((this.mJam.getMeasuredHeight() * this.mCurPercent) / 1000) - (driverHeight / 2);
            }
            if (this.justShown) {
                this.justShown = false;
            } else {
                this.mDriver.setVisibility(0);
            }
            LayoutParams p = (LayoutParams) this.mDriver.getLayoutParams();
            p.bottomMargin = bottomMargin;
            this.mDriver.setLayoutParams(p);
            if (levels == null || lengths == null) {
                this.mTrafficTip.setVisibility(8);
                this.mCurrBar = CurrentlyActive.enforcement;
                if (distanceLeft == -1) {
                    prepareCameraEnforcementZoneBar();
                } else {
                    this.mJam.setColorsEnforcement();
                }
                if (this.mFirstTime) {
                    this.mFirstTime = false;
                    this.mEnforcementTip.setVisibility(0);
                    this.mEnforcementTip.postDelayed(this.disappearifyTheEnforcementBarTip, 10000);
                    this.mTipEnforcementLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_AVERAGE_SPEED_ENFORCEMENT_ZONE_BAR_START_POPUP));
                }
                if (distanceLeft != -1) {
                    this.mTipEnforcementDistance.setText(RTAlertsNativeManager.getInstance().formatDistanceNTV(distanceLeft));
                    this.mTipEnforcementDistance.setVisibility(0);
                    this.ASCMode = true;
                    return;
                }
                this.ASCMode = false;
                return;
            }
            this.ASCMode = false;
            this.mEnforcementTip.setVisibility(8);
            this.mTrafficTip.setVisibility(0);
            this.mFirstTime = true;
            this.mCurrBar = CurrentlyActive.traffic;
            prepareJamBar(current_percent, total_time, levels, lengths);
        }
    }

    private void prepareCameraEnforcementZoneBar() {
        if (!isInEditMode()) {
            this.mTipEnforcementLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ENFORCEMENT_ZONE_ANDROID));
            this.mTipEnforcementDistance.setVisibility(8);
        }
        this.mJam.setColorsEnforcement();
    }

    private void prepareJamBar(int current_percent, int total_time, int[] levels, int[] lengths) {
        if (!isInEditMode()) {
            this.mTipTraficTitle.setText(DisplayStrings.displayString(DisplayStrings.DS_TRAFFIC_BAR_TITLE));
            this.mTipTrafficTime.setText(DisplayStrings.displayStringF(DisplayStrings.DS_TRAFFIC_BAR_TIME, Integer.valueOf((total_time / 60) + 1)));
        }
        this.mJam.setColors(levels, lengths, current_percent);
    }

    public boolean canShow(int curPercent, int curTime) {
        return curPercent > -100 && curPercent < 100 && curTime > 0;
    }

    public boolean canShowInternal() {
        return this.mCurPercent > -100 && this.mCurPercent < 100 && this.mCurTime > 0;
    }

    public void reset() {
        this.mTipIsShowing = true;
        this.mTipIsAnimating = false;
        this.mCurPercent = 0;
        this.mCurTime = 0;
        this.mCurrBar = null;
        this.mTrafficTip.setVisibility(0);
        this.mTrafficTip.clearAnimation();
        this.mEnforcementTip.setVisibility(0);
        this.mEnforcementTip.clearAnimation();
        this.mFirstTime = true;
    }

    public void setDayMode(boolean bIsDay) {
        if (bIsDay) {
            this.mBg.setBackgroundResource(C1283R.drawable.traffic_bar);
            this.mTipFrame.setBackgroundResource(C1283R.drawable.traffic_bar_popup);
            this.mTipTraficTitle.setTextColor(-7105645);
            this.mTipTrafficTime.setTextColor(-10263709);
            this.mTipEnforcementLabel.setTextColor(-10263709);
            this.mTipEnforcementDistance.setTextColor(-10263709);
            return;
        }
        this.mBg.setBackgroundResource(C1283R.drawable.traffic_bar_night);
        this.mTipFrame.setBackgroundResource(C1283R.drawable.traffic_bar_popup_night);
        this.mTipTraficTitle.setTextColor(-7685425);
        this.mTipTrafficTime.setTextColor(-1);
        this.mTipEnforcementLabel.setTextColor(-1);
        this.mTipEnforcementDistance.setTextColor(-1);
    }

    public void setVisibility(int visibility) {
        this.eventualVisibility = visibility;
        if (!this.animatingVisibility && getVisibility() != visibility) {
            this.animatingVisibility = true;
            AnimationListener listener = new C30375();
            AnimationSet as = new AnimationSet(true);
            as.setDuration(200);
            as.setFillBefore(true);
            if (visibility == 0) {
                as.setInterpolator(new DecelerateInterpolator());
                as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                as.addAnimation(new TranslateAnimation(0, (float) (-PixelMeasure.dp(10)), 0, 0.0f, 0, 0.0f, 0, 0.0f));
                as.setAnimationListener(listener);
                this.mDriver.setVisibility(8);
                this.justShown = true;
                if (!this.ASCMode) {
                    appearifyTheTip(0);
                }
            } else {
                if (this.mTipFrame.getVisibility() != 8) {
                    disappearifyTheTip();
                }
                as.setInterpolator(new AccelerateInterpolator());
                as.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                as.addAnimation(new TranslateAnimation(0, 0.0f, 0, (float) (-PixelMeasure.dp(10)), 0, 0.0f, 0, 0.0f));
                as.setAnimationListener(listener);
            }
            startAnimation(as);
        }
    }
}
