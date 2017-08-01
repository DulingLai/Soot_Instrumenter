package com.waze;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.CanvasFont;
import com.waze.navigate.DriveToNativeManager;
import com.waze.view.dialogs.TimeToParkFeedbackDialog;
import com.waze.view.navbar.NavBarSoundButton;

public class BottomBarOnTouchListener implements OnTouchListener {
    private NavResultsFragment fragment;
    private final View mBar;
    float mDownY = 0.0f;
    boolean mFragmentAttached = false;
    private final View mFrame;
    int mHeight = 0;
    private boolean mIsOpen = false;
    boolean mIsTouching = false;
    final LayoutManager mLayoutManager;
    private final View mMenu;
    private final NavBarSoundButton mNavBarSoundButton;
    private IOnBottomBarClose mOnCloseListener;
    private final View mRightButton;
    private final View mShadow;
    private final View mTTPLLine;
    private final View mTouchFrame;

    class C11001 implements AnimationListener {
        C11001() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            TranslateAnimation $r2 = new TranslateAnimation(0.0f, 0.0f, (float) (-BottomBarOnTouchListener.this.mHeight), (float) (-BottomBarOnTouchListener.this.mHeight));
            $r2.setDuration(1000);
            $r2.setRepeatCount(-1);
            BottomBarOnTouchListener.this.mBar.startAnimation($r2);
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }
    }

    class C11012 implements AnimatorListener {
        C11012() throws  {
        }

        public void onAnimationStart(Animator animation) throws  {
        }

        public void onAnimationEnd(Animator animation) throws  {
            BottomBarOnTouchListener.this.mNavBarSoundButton.setTranslationY((float) (-BottomBarOnTouchListener.this.mHeight));
        }

        public void onAnimationCancel(Animator animation) throws  {
        }

        public void onAnimationRepeat(Animator animation) throws  {
        }
    }

    class C11023 implements Runnable {
        C11023() throws  {
        }

        public void run() throws  {
            BottomBarOnTouchListener.this.mNavBarSoundButton.animateSoundButton(true, true);
        }
    }

    class C11055 implements AnimatorListener {
        C11055() throws  {
        }

        public void onAnimationStart(Animator animation) throws  {
        }

        public void onAnimationEnd(Animator animation) throws  {
            BottomBarOnTouchListener.this.mNavBarSoundButton.setTranslationY(0.0f);
        }

        public void onAnimationCancel(Animator animation) throws  {
        }

        public void onAnimationRepeat(Animator animation) throws  {
        }
    }

    interface IOnBottomBarClose {
        void onClose() throws ;
    }

    public boolean isOpen() throws  {
        return this.mIsOpen;
    }

    public BottomBarOnTouchListener(LayoutManager $r1) throws  {
        this.mLayoutManager = $r1;
        RelativeLayout $r2 = $r1.getMainLayout();
        this.mFrame = $r2.findViewById(C1283R.id.mainNavResFrame);
        this.mBar = $r2.findViewById(C1283R.id.MainBottomBarLayout);
        this.mShadow = $r2.findViewById(C1283R.id.MainBottomBarShadow);
        this.mRightButton = $r2.findViewById(C1283R.id.mainBottomBarRight);
        this.mMenu = $r2.findViewById(C1283R.id.mainBottomBarMenuButton);
        this.mTouchFrame = $r2.findViewById(C1283R.id.tooltipFrameForTouchEvents);
        this.mTTPLLine = $r2.findViewById(C1283R.id.lblTimeToPark);
        this.mNavBarSoundButton = (NavBarSoundButton) $r2.findViewById(C1283R.id.navBarSoundButton);
    }

    public void setOnCloseListener(IOnBottomBarClose $r1) throws  {
        this.mOnCloseListener = $r1;
    }

    public boolean onTouch(View $r1, MotionEvent $r2) throws  {
        boolean $z0 = NativeManager.getInstance().isNavigatingNTV();
        if (($r1 == this.mLayoutManager.getBottomBar() || this.mLayoutManager.getMainBottomBarRightButtonRelevantToNav($r1)) && (this.mIsOpen || !$z0)) {
            return false;
        }
        if ($r1 == this.mTouchFrame && !this.mIsOpen) {
            return false;
        }
        int $i1 = $r2.getAction();
        if ($i1 == 0) {
            this.mIsTouching = true;
            this.mDownY = $r2.getY();
            if (!this.mIsOpen) {
                triggerOpen();
            }
            setBarBgColor();
            return true;
        } else if ($i1 == 1 || $i1 == 3) {
            this.mIsTouching = false;
            $z0 = false;
            if ($r2.getEventTime() - $r2.getDownTime() < ((long) ViewConfiguration.getLongPressTimeout())) {
                $z0 = true;
                Rect rect = new Rect();
                View view = this.mTTPLLine;
                $r1 = view;
                view.getGlobalVisibleRect(rect);
                if (rect.contains((int) $r2.getRawX(), ((int) $r2.getRawY()) + this.mHeight)) {
                    new TimeToParkFeedbackDialog("ETA").show();
                }
            }
            if (this.mHeight > 0 && this.mFragmentAttached) {
                $f0 = $r2.getY();
                $f1 = this.mDownY;
                $f0 -= $f1;
                if (this.mIsOpen) {
                    if ($z0 || $f0 >= 10.0f) {
                        finishAnimationDown($f0);
                    } else {
                        finishAnimationUp((float) (-this.mHeight), 0);
                    }
                } else if ($z0 || $f0 <= -10.0f) {
                    finishAnimationUp($f0, 0);
                } else {
                    finishAnimationDown((float) this.mHeight);
                }
            }
            return true;
        } else if ($i1 != 2) {
            return false;
        } else {
            this.mIsTouching = true;
            if (this.mHeight > 0) {
                $f0 = $r2.getY();
                $f1 = this.mDownY;
                $f0 -= $f1;
                if (this.mIsOpen) {
                    setViewPositionsDown($f0);
                } else {
                    setViewPositionsUp($f0);
                }
            }
            return true;
        }
    }

    private void setBarBgColor() throws  {
        int $i0;
        if (DriveToNativeManager.getInstance().isDayMode()) {
            $i0 = C1283R.color.bottom_bar_button_background_day;
        } else {
            $i0 = C1283R.color.bottom_bar_button_background_night;
        }
        this.mBar.setBackgroundColor(this.mBar.getResources().getColor($i0));
    }

    private void finishAnimationUp(float $f0, int $i2) throws  {
        float $f1;
        String $r17;
        if ($f0 < ((float) (-this.mHeight))) {
            $f0 = (float) (-this.mHeight);
            $f1 = 1.0f;
        } else {
            $f1 = (-$f0) / ((float) this.mHeight);
        }
        int $i0 = (int) (250.0f * (1.0f - $f1));
        int i = $i2;
        TranslateAnimation $r3 = new TranslateAnimation(0.0f, 0.0f, ((float) this.mHeight) + $f0, 0.0f);
        $r3.setDuration((long) $i0);
        $r3.setStartOffset((long) $i2);
        $r3.setInterpolator(new DecelerateInterpolator(1.25f));
        $r3.setFillBefore(true);
        View $r4 = this.mFrame;
        $r4.startAnimation($r3);
        $r3 = new TranslateAnimation(0.0f, 0.0f, $f0, (float) (-this.mHeight));
        $r3.setDuration((long) $i0);
        $r3.setStartOffset((long) $i2);
        $r3.setInterpolator(new DecelerateInterpolator(1.25f));
        $r3.setFillBefore(true);
        $r3.setFillAfter(true);
        $r4 = this.mBar;
        $r4.startAnimation($r3);
        $r3.setAnimationListener(new C11001());
        $r3 = new TranslateAnimation(0.0f, 0.0f, $f0, (float) (-this.mHeight));
        $r3.setDuration((long) $i0);
        $r3.setStartOffset((long) $i2);
        DecelerateInterpolator $r2 = new DecelerateInterpolator(1.25f);
        $r3.setInterpolator($r2);
        $r3.setFillBefore(true);
        $r3.setFillAfter(true);
        $r4 = this.mShadow;
        $r4.startAnimation($r3);
        NavBarSoundButton navBarSoundButton = this.mNavBarSoundButton;
        NavBarSoundButton $r6 = navBarSoundButton;
        navBarSoundButton.setTranslationY($f0);
        navBarSoundButton = this.mNavBarSoundButton;
        navBarSoundButton.animate().setInterpolator($r2).setDuration((long) $i0).setStartDelay((long) $i2).translationY((float) (-this.mHeight)).setListener(new C11012());
        Animation alphaAnimation = new AlphaAnimation($f1, 1.0f);
        alphaAnimation.setDuration((long) $i0);
        alphaAnimation.setStartOffset((long) $i2);
        alphaAnimation.setFillBefore(true);
        alphaAnimation.setFillAfter(true);
        $r4 = this.mTouchFrame;
        View $r42 = $r4;
        $r4.startAnimation(alphaAnimation);
        $r4 = this.mShadow;
        int $i4 = $r4.getContext().getResources().getDimensionPixelSize(C1283R.dimen.mainBottomBarButtonWidth);
        $f0 = $f1 * ((float) $i4);
        if ($i2 > 0) {
            $i2 += 100;
        }
        $r3 = new TranslateAnimation($f0, (float) $i4, 0.0f, 0.0f);
        $r3.setDuration((long) $i0);
        $r3.setStartOffset((long) $i2);
        $r3.setInterpolator(new AccelerateInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
        $r3.setFillBefore(true);
        $r3.setFillAfter(true);
        $r4 = this.mRightButton;
        $r4.startAnimation($r3);
        $r3 = new TranslateAnimation(-$f0, (float) (-$i4), 0.0f, 0.0f);
        $r3.setDuration((long) $i0);
        $r3.setStartOffset((long) $i2);
        $r3.setInterpolator(new AccelerateInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
        $r3.setFillBefore(true);
        $r3.setFillAfter(true);
        $r4 = this.mMenu;
        $r4.startAnimation($r3);
        $r4 = this.mTouchFrame;
        $r42 = $r4;
        $r4.setOnTouchListener(this);
        this.mIsOpen = true;
        LayoutManager $r12 = this.mLayoutManager;
        $r12.getBottomBar().setOpen(true);
        if (i > 0) {
            this.mNavBarSoundButton.postDelayed(new C11023(), (long) i);
        } else {
            this.mNavBarSoundButton.animateSoundButton(true, true);
        }
        boolean $z0 = true;
        $r12 = this.mLayoutManager;
        if ($r12.getNavResults() != null) {
            $r12 = this.mLayoutManager;
            $z0 = $r12.getNavResults().isShownFirstTime();
        }
        AnalyticsBuilder $r16 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ETA_SHOWN).addParam("TYPE", $z0 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_NEW_DRIVE : "WHILE_DRIVING");
        if (this.fragment == null) {
            $r17 = "unknown";
        } else {
            NavResultsFragment $r15 = this.fragment;
            $r17 = $r15.withStop() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE;
        }
        $r16.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_WITH_STOP, $r17).send();
    }

    private void finishAnimationDown(float $f0) throws  {
        float $f1;
        if ($f0 > ((float) this.mHeight)) {
            $f0 = (float) this.mHeight;
            $f1 = 1.0f;
        } else {
            $f1 = $f0 / ((float) this.mHeight);
        }
        int $i0 = (int) (300.0f * (1.0f - $f1));
        TranslateAnimation $r3 = new TranslateAnimation(0.0f, 0.0f, $f0, (float) this.mHeight);
        $r3.setDuration((long) $i0);
        $r3.setInterpolator(new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
        $r3.setFillAfter(true);
        View $r4 = this.mFrame;
        $r4.startAnimation($r3);
        boolean $z0 = DriveToNativeManager.getInstance().isDayMode();
        $r4 = this.mTouchFrame;
        Resources $r7 = $r4.getContext().getResources();
        final boolean z = $z0;
        final Resources resources = $r7;
        $r3.setAnimationListener(new AnimationListener() {

            class C11031 implements Runnable {
                C11031() throws  {
                }

                public void run() throws  {
                    BottomBarOnTouchListener.this.mOnCloseListener.onClose();
                    BottomBarOnTouchListener.this.setClosed();
                }
            }

            public void onAnimationStart(Animation animation) throws  {
                BottomBarOnTouchListener.this.mLayoutManager.getBottomBar().closeTimeToParkMessage();
                BottomBarOnTouchListener.this.mLayoutManager.getMainLayout().setBackgroundColor(z ? resources.getColor(C1283R.color.PassiveGrey) : resources.getColor(C1283R.color.DarkBlue));
            }

            public void onAnimationEnd(Animation animation) throws  {
                C11031 $r2 = new C11031();
                if (BottomBarOnTouchListener.this.mLayoutManager.isPaused()) {
                    BottomBarOnTouchListener.this.mLayoutManager.addOnResume($r2);
                } else {
                    $r2.run();
                }
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }
        });
        Animation alphaAnimation = new AlphaAnimation(1.0f - $f1, 0.0f);
        alphaAnimation.setDuration((long) ($i0 / 2));
        alphaAnimation.setFillAfter(true);
        $r4 = this.mTouchFrame;
        View $r42 = $r4;
        $r4.startAnimation(alphaAnimation);
        $r3 = new TranslateAnimation(0.0f, 0.0f, $f0 - ((float) this.mHeight), 0.0f);
        $r3.setDuration((long) $i0);
        $r3.setInterpolator(new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
        $r4 = this.mBar;
        $r4.startAnimation($r3);
        OvershootInterpolator $r2 = new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR);
        $r3 = new TranslateAnimation(0.0f, 0.0f, $f0 - ((float) this.mHeight), 0.0f);
        $r3.setDuration((long) $i0);
        $r3.setInterpolator($r2);
        $r4 = this.mShadow;
        $r4.startAnimation($r3);
        this.mNavBarSoundButton.setTranslationY($f0 - ((float) this.mHeight));
        NavBarSoundButton $r9 = this.mNavBarSoundButton;
        $r9.animate().setStartDelay(0).setInterpolator($r2).setDuration((long) $i0).translationY(0.0f).setListener(new C11055());
        $f0 = (1.0f - $f1) * ((float) $r7.getDimensionPixelSize(C1283R.dimen.mainBottomBarButtonWidth));
        $r3 = new TranslateAnimation($f0, 0.0f, 0.0f, 0.0f);
        $r3.setDuration((long) $i0);
        $r3.setInterpolator(new DecelerateInterpolator());
        $r3.setFillBefore(true);
        $r4 = this.mRightButton;
        $r4.startAnimation($r3);
        $r3 = new TranslateAnimation(-$f0, 0.0f, 0.0f, 0.0f);
        $r3.setDuration((long) $i0);
        $r3.setInterpolator(new DecelerateInterpolator());
        $r3.setFillBefore(true);
        $r4 = this.mMenu;
        $r4.startAnimation($r3);
        this.mNavBarSoundButton.animateSoundButton(false, true);
        this.mIsOpen = false;
        this.mFragmentAttached = false;
        this.fragment = null;
    }

    private void animateTranslationInPlace(View $r1, float $f0, float $f1) throws  {
        TranslateAnimation $r2 = new TranslateAnimation($f0, $f0, $f1, $f1);
        $r2.setFillBefore(true);
        $r2.setFillAfter(true);
        $r1.startAnimation($r2);
    }

    private void setViewPositionsUp(float $f0) throws  {
        if ($f0 > 0.0f) {
            $f0 = 0.0f;
        }
        if ($f0 < ((float) (-this.mHeight))) {
            $f0 = (float) (-this.mHeight);
        }
        animateTranslationInPlace(this.mFrame, 0.0f, ((float) this.mHeight) + $f0);
        animateTranslationInPlace(this.mBar, 0.0f, $f0);
        animateTranslationInPlace(this.mShadow, 0.0f, $f0);
        this.mNavBarSoundButton.setTranslationY($f0);
        this.mNavBarSoundButton.adjustSoundButtonAnimationPhase((-$f0) / ((float) this.mHeight));
        $f0 = (-$f0) / ((float) this.mHeight);
        AlphaAnimation $r1 = new AlphaAnimation($f0, $f0);
        $r1.setFillAfter(true);
        this.mTouchFrame.startAnimation($r1);
        $f0 *= (float) this.mFrame.getContext().getResources().getDimensionPixelSize(C1283R.dimen.mainBottomBarButtonWidth);
        animateTranslationInPlace(this.mRightButton, $f0, 0.0f);
        animateTranslationInPlace(this.mMenu, -$f0, 0.0f);
    }

    private void setViewPositionsDown(float $f0) throws  {
        if ($f0 < 0.0f) {
            $f0 = 0.0f;
        }
        if ($f0 > ((float) this.mHeight)) {
            $f0 = (float) this.mHeight;
        }
        animateTranslationInPlace(this.mFrame, 0.0f, $f0);
        animateTranslationInPlace(this.mBar, 0.0f, $f0 - ((float) this.mHeight));
        animateTranslationInPlace(this.mShadow, 0.0f, $f0 - ((float) this.mHeight));
        this.mNavBarSoundButton.setTranslationY($f0 - ((float) this.mHeight));
        this.mNavBarSoundButton.adjustSoundButtonAnimationPhase(1.0f - ($f0 / ((float) this.mHeight)));
        $f0 = 1.0f - ($f0 / ((float) this.mHeight));
        AlphaAnimation $r1 = new AlphaAnimation($f0, $f0);
        $r1.setFillAfter(true);
        this.mTouchFrame.startAnimation($r1);
        $f0 *= (float) this.mFrame.getContext().getResources().getDimensionPixelSize(C1283R.dimen.mainBottomBarButtonWidth);
        animateTranslationInPlace(this.mRightButton, $f0, 0.0f);
        animateTranslationInPlace(this.mMenu, -$f0, 0.0f);
    }

    private void triggerOpen() throws  {
        NativeManager.getInstance().getNavBarManager().showNavigationResult();
        this.mLayoutManager.closeShareTooltip();
        doSetup();
    }

    private void doSetup() throws  {
        this.mLayoutManager.getBottomBar().stopMessages();
        this.mTouchFrame.setBackgroundColor(-1728053248);
        AlphaAnimation $r1 = new AlphaAnimation(0.0f, 0.0f);
        $r1.setFillBefore(true);
        $r1.setFillAfter(true);
        this.mTouchFrame.startAnimation($r1);
        this.mTouchFrame.setVisibility(0);
    }

    public void animateUp(int $i0) throws  {
        doSetup();
        setBarBgColor();
        finishAnimationUp(0.0f, $i0);
    }

    public void animateIntoPlace() throws  {
        doSetup();
        setBarBgColor();
        finishAnimationUp((float) (-this.mHeight), 0);
    }

    public void animateDown() throws  {
        finishAnimationDown(0.0f);
    }

    public void animateNavResOnMove(final NavResultsFragment $r1) throws  {
        this.mFrame.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                BottomBarOnTouchListener.this.mFrame.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                BottomBarOnTouchListener.this.mHeight = $r1.getView().getMeasuredHeight();
                BottomBarOnTouchListener.this.mFragmentAttached = true;
                if (BottomBarOnTouchListener.this.mIsTouching) {
                    BottomBarOnTouchListener.this.animateTranslationInPlace(BottomBarOnTouchListener.this.mFrame, 0.0f, (float) BottomBarOnTouchListener.this.mHeight);
                } else {
                    BottomBarOnTouchListener.this.animateUp(0);
                }
            }
        });
    }

    public void onLayoutChanged(final NavResultsFragment $r1) throws  {
        this.mFrame.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                BottomBarOnTouchListener.this.mFrame.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if ($r1.getView() != null) {
                    int $i0 = BottomBarOnTouchListener.this.mHeight;
                    BottomBarOnTouchListener.this.mHeight = $r1.getView().getMeasuredHeight();
                    this = this;
                    if (BottomBarOnTouchListener.this.mIsOpen) {
                        this = this;
                        if ($i0 != BottomBarOnTouchListener.this.mHeight) {
                            this = this;
                            Animation $r5 = BottomBarOnTouchListener.this.mFrame.getAnimation();
                            if ($r5 == null || $r5.hasEnded()) {
                                BottomBarOnTouchListener.this.animateIntoPlace();
                            } else if ($r5.hasStarted()) {
                                $i0 = (int) ((((long) BottomBarOnTouchListener.this.mHeight) * (AnimationUtils.currentAnimationTimeMillis() - $r5.getStartTime())) / $r5.getDuration());
                                BottomBarOnTouchListener.this.finishAnimationUp((float) ((-BottomBarOnTouchListener.this.mHeight) + $i0), 0);
                            } else {
                                BottomBarOnTouchListener.this.finishAnimationUp(null, 0);
                            }
                        }
                    }
                }
            }
        });
    }

    public void fragmentAttached(NavResultsFragment $r1) throws  {
        this.fragment = $r1;
        View $r2 = $r1.getView();
        if ($r2 != null) {
            this.mHeight = $r2.getMeasuredHeight();
            this.mFragmentAttached = true;
        }
    }

    public void setClosed() throws  {
        this.mLayoutManager.onNavResAnimateOutDone();
        this.mLayoutManager.getMainLayout().setBackgroundDrawable(null);
        this.mTouchFrame.setBackgroundColor(0);
        this.mTouchFrame.setVisibility(8);
        this.mTouchFrame.clearAnimation();
        this.mLayoutManager.getBottomBar().continueMessages();
        this.mBar.setBackgroundColor(0);
        this.mLayoutManager.getBottomBar().setOpen(false);
        this.mNavBarSoundButton.clearAnimation();
        this.mNavBarSoundButton.setTranslationY(0.0f);
        this.mNavBarSoundButton.animateSoundButton(false, false);
        this.mIsOpen = false;
    }

    public boolean isOpened() throws  {
        return this.mIsOpen;
    }
}
