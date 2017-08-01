package com.waze.navbar;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.BoundService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.NavBarManager;
import com.waze.NavBarManager.DoublePosition;
import com.waze.Utils;
import com.waze.navigate.DriveToNativeManager;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;
import com.waze.view.navbar.BottomBar;
import com.waze.view.navbar.NavBarText;

public class NavBar extends RelativeLayout implements INavBarViewCallbacks {
    public static final int[] instImagesLeft = new int[]{0, C1283R.drawable.big_direction_left, C1283R.drawable.big_direction_right, C1283R.drawable.big_direction_exit_left, C1283R.drawable.big_direction_exit_right, C1283R.drawable.big_direction_forward, C1283R.drawable.big_directions_roundabout_uk, C1283R.drawable.big_directions_roundabout_uk, C1283R.drawable.big_directions_roundabout_uk_l, C1283R.drawable.big_directions_roundabout_uk_l, C1283R.drawable.big_directions_roundabout_uk_s, C1283R.drawable.big_directions_roundabout_uk_s, C1283R.drawable.big_directions_roundabout_r_uk, C1283R.drawable.big_directions_roundabout_r_uk, C1283R.drawable.big_directions_roundabout_u_uk, C1283R.drawable.big_directions_roundabout_u_uk, C1283R.drawable.big_direction_end, C1283R.drawable.big_direction_exit_left, C1283R.drawable.big_direction_exit_right, 0, C1283R.drawable.big_directions_uturn_uk, 0, 0, 0, 0, 0, C1283R.drawable.big_direction_stop};
    public static final int[] instImagesRight = new int[]{0, C1283R.drawable.big_direction_left, C1283R.drawable.big_direction_right, C1283R.drawable.big_direction_exit_left, C1283R.drawable.big_direction_exit_right, C1283R.drawable.big_direction_forward, C1283R.drawable.big_directions_roundabout, C1283R.drawable.big_directions_roundabout, C1283R.drawable.big_directions_roundabout_l, C1283R.drawable.big_directions_roundabout_l, C1283R.drawable.big_directions_roundabout_s, C1283R.drawable.big_directions_roundabout_s, C1283R.drawable.big_directions_roundabout_r, C1283R.drawable.big_directions_roundabout_r, C1283R.drawable.big_directions_roundabout_u, C1283R.drawable.big_directions_roundabout_u, C1283R.drawable.big_direction_end, C1283R.drawable.big_direction_exit_left, C1283R.drawable.big_direction_exit_right, 0, C1283R.drawable.big_directions_uturn, 0, 0, 0, 0, 0, C1283R.drawable.big_direction_stop};
    private boolean IsNextInstruction = false;
    private int NextInstruction;
    private InstructionGeometry NextInstructionGeometry;
    BottomBar bottomBar;
    private DriveToNativeManager dtnm;
    private int[] instImages;
    public boolean instructionSent = false;
    private int lastInstruction = -1;
    private ImageView mArrowImage;
    View mBoxView;
    ImageView mImgDirection;
    LayoutManager mLayoutMgr;
    private NavBarManager mNavBarManager;
    private boolean mNextDisplayed = false;
    View mNextView;
    private boolean mShouldShowNextView;
    private INavBarViewEvents mSubView;
    private boolean mThenHiddenForAlerter;
    View mTopView;
    TextView mTvDirection;
    TextView mTvDistance;
    NavBarText mTvStreet;
    private boolean mbDisableNext = false;
    private boolean mbInAlertMode = false;
    private boolean mbInCondensedMode = false;
    private boolean mbNavListFragmentShowing = false;
    private boolean mbNightMode;
    private NativeManager nativeManager;
    private Runnable onSubViewHidden;
    private float scale;
    private boolean shouldRestoreSubView = false;
    private boolean shown;
    TextView thenDirection;
    TextView thenText;

    class C20341 implements OnClickListener {
        C20341() throws  {
        }

        public void onClick(View v) throws  {
            NavBar.this.toggleNavListFragment();
        }
    }

    public interface INavListDismissed {
        void onDismiss() throws ;
    }

    class C20352 implements INavListDismissed {
        C20352() throws  {
        }

        public void onDismiss() throws  {
            NavBar.this.mbNavListFragmentShowing = false;
            if (NavBar.this.mNextDisplayed) {
                NavBar.this.showNextView();
            }
        }
    }

    class C20363 extends AnimationEndListener {
        C20363() throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            LayoutTransition $r2 = NavBar.this.getLayoutTransition();
            NavBar.this.setLayoutTransition(null);
            NavBar.this.mNextView.setVisibility(8);
            NavBar.this.setLayoutTransition($r2);
        }
    }

    class C20374 implements AnimationListener {
        C20374() throws  {
        }

        public void onAnimationEnd(Animation arg0) throws  {
            NavBar.this.adjustImages();
        }

        public void onAnimationRepeat(Animation arg0) throws  {
        }

        public void onAnimationStart(Animation arg0) throws  {
        }
    }

    class C20385 implements OnGlobalLayoutListener {
        C20385() throws  {
        }

        public void onGlobalLayout() throws  {
            NavBar.this.mTvStreet.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            NavBar.this.adjustImages();
        }
    }

    public NavBar(Context $r1) throws  {
        super($r1);
        inflate($r1);
    }

    public NavBar(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        inflate($r1);
    }

    public NavBar(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        inflate($r1);
    }

    @TargetApi(21)
    public NavBar(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super($r1, $r2, $i0, $i1);
        inflate($r1);
    }

    private void inflate(Context $r1) throws  {
        LayoutInflater.from($r1).inflate(C1283R.layout.navbar, this);
        this.mTopView = findViewById(C1283R.id.navBarTop);
        this.mTvStreet = (NavBarText) findViewById(C1283R.id.navBarLine);
        this.thenText = (TextView) findViewById(C1283R.id.navBarThenText);
        this.mTvDirection = (TextView) findViewById(C1283R.id.navBarDirectionText);
        this.mImgDirection = (ImageView) findViewById(C1283R.id.navBarDirection);
        this.thenDirection = (TextView) findViewById(C1283R.id.navBarThenDirection);
        this.mNextView = findViewById(C1283R.id.navBarThen);
        this.mBoxView = findViewById(C1283R.id.navBarBox);
    }

    public void init(LayoutManager $r1, BottomBar $r2) throws  {
        this.dtnm = DriveToNativeManager.getInstance();
        this.nativeManager = AppService.getNativeManager();
        this.mNavBarManager = this.nativeManager.getNavBarManager();
        this.scale = getResources().getDisplayMetrics().density;
        this.mLayoutMgr = $r1;
        setBottomBar($r2);
        this.instImages = instImagesRight;
        findViewById(C1283R.id.navBarSubViewFrame).bringToFront();
        this.mTopView.bringToFront();
        if (this.mArrowImage == null) {
            this.mArrowImage = new ImageView(getContext());
            LayoutParams $r3 = new LayoutParams(-2, -2);
            $r3.addRule(11);
            $r3.addRule(8, C1283R.id.navBarTop);
            $r3.bottomMargin = PixelMeasure.dp(8);
            $r3.rightMargin = PixelMeasure.dp(8);
            this.mArrowImage.setLayoutParams($r3);
            this.mArrowImage.setImageResource(C1283R.drawable.navlist_directions);
            addView(this.mArrowImage);
        }
        this.mTopView.setOnClickListener(new C20341());
        this.thenText.setText(this.nativeManager.getLanguageString(236));
        NavBarText navBarText = this.mTvStreet;
        NavBarText $r16 = navBarText;
        navBarText.setNavBar(this);
        this.mTvStreet.setText(new String());
        this.mTvDistance = (TextView) findViewById(C1283R.id.navBarDistance);
        this.shown = false;
        if (VERSION.SDK_INT >= 16) {
            getLayoutTransition().enableTransitionType(4);
        }
        if (VERSION.SDK_INT >= 16) {
            ((ViewGroup) this.mTopView).getLayoutTransition().enableTransitionType(4);
        }
    }

    public void toggleNavListFragment() throws  {
        if (this.mbNavListFragmentShowing) {
            this.mLayoutMgr.removeNavListFragment();
            return;
        }
        this.mbNavListFragmentShowing = true;
        this.mLayoutMgr.hideSpotifyButton();
        hideNextView();
        this.mLayoutMgr.showNavListFragment(new C20352());
    }

    void hideNextView() throws  {
        this.mShouldShowNextView = false;
        if (this.mNextView.getVisibility() != 8) {
            if (this.mNextView.getAnimation() != null) {
                this.mNextView.getAnimation().setAnimationListener(null);
                this.mNextView.clearAnimation();
            }
            TranslateAnimation $r1 = r0;
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
            $r1.setDuration(100);
            this.mNextView.startAnimation($r1);
            $r1.setAnimationListener(new C20363());
        }
    }

    public boolean showingNextView() throws  {
        return this.mNextView.getVisibility() == 0;
    }

    void showNextView() throws  {
        this.mShouldShowNextView = true;
        if (this.mNextView.getVisibility() != 0 && !this.mThenHiddenForAlerter) {
            if (this.mNextView.getAnimation() != null) {
                this.mNextView.getAnimation().setAnimationListener(null);
                this.mNextView.clearAnimation();
            }
            LayoutTransition $r4 = getLayoutTransition();
            setLayoutTransition(null);
            this.mNextView.setVisibility(0);
            setLayoutTransition($r4);
            Animation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
            translateAnimation.setDuration(100);
            this.mNextView.startAnimation(translateAnimation);
        }
    }

    public void setBottomBar(BottomBar $r1) throws  {
        this.bottomBar = $r1;
    }

    public BottomBar bottomBar() throws  {
        return this.bottomBar;
    }

    public void clear() throws  {
        this.mImgDirection.setImageResource(0);
        this.mBoxView.setVisibility(8);
        this.mTvDirection.setText("");
        this.mNextView.setVisibility(8);
        this.thenDirection.setText("");
        this.mTvDistance.setText("");
        this.mTvStreet.setText("");
    }

    public void show() throws  {
        byte $b0 = (byte) 8;
        boolean $z0 = this.mNavBarManager.isNearingDestNTV();
        if (this.shown) {
            byte b;
            this.bottomBar.show();
            View $r4 = findViewById(C1283R.id.navBarSubViewFrame);
            if ($z0) {
                b = (byte) 0;
            } else {
                b = (byte) 8;
            }
            $r4.setVisibility(b);
            $r4 = findViewById(C1283R.id.navBarShadow);
            if (!$z0) {
                $b0 = (byte) 0;
            }
            $r4.setVisibility($b0);
            return;
        }
        LayoutManager $r10;
        this.shown = true;
        if (this.mArrowImage != null) {
            this.mArrowImage.setVisibility(0);
        }
        setMode();
        adjustImages();
        setVisibility(0);
        findViewById(C1283R.id.navBarSubViewFrame).setVisibility($z0 ? (byte) 0 : (byte) 8);
        $r4 = findViewById(C1283R.id.navBarShadow);
        if (!$z0) {
            $b0 = (byte) 0;
        }
        $r4.setVisibility($b0);
        if (this.mSubView != null && $z0) {
            INavBarViewEvents $r6 = this.mSubView;
            $r6.onUpdate();
        }
        Animation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        translateAnimation.setDuration(500);
        translateAnimation.setAnimationListener(new C20374());
        translateAnimation.setFillAfter(true);
        startAnimation(translateAnimation);
        setVisibility(0);
        findViewById(C1283R.id.navBarTop).setVisibility(0);
        findViewById(C1283R.id.navBarShadow).setVisibility(0);
        if (this.IsNextInstruction) {
            if (this.NextInstructionGeometry != null) {
                setNextInstructionGeometry(this.NextInstructionGeometry);
            } else {
                setNextInstruction(this.NextInstruction);
            }
            this.IsNextInstruction = false;
        }
        if (AppService.getMainActivity() == null) {
            this.bottomBar.show();
        } else {
            $r10 = this.mLayoutMgr;
            if ($r10.getBottomBar().canHideNotif()) {
                $r10 = this.mLayoutMgr;
                $r10.getBottomBar().setToShown();
            } else {
                this.bottomBar.show();
            }
        }
        if (this.mLayoutMgr != null) {
            $r10 = this.mLayoutMgr;
            $r10.onNavSptotifyButton();
        }
    }

    public void hide() throws  {
        if (this.shown) {
            this.shown = false;
            this.mNextDisplayed = false;
            this.shouldRestoreSubView = false;
            this.bottomBar.hide(true);
            findViewById(C1283R.id.navBarTop).setVisibility(8);
            findViewById(C1283R.id.navBarShadow).setVisibility(8);
            this.mNextView.setVisibility(8);
            if (this.mArrowImage != null) {
                this.mArrowImage.setVisibility(8);
            }
            if (this.mbInAlertMode) {
                setAlertMode(false);
            }
            Log.i("NearingDest", "About to hide nearing dest from nav bar hide");
            hideNearingDestination(false);
            setVisibility(8);
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().getLayoutMgr().onStopNavSptotifyButton();
            }
            if (BoundService.mIsRunning && BoundService.getInstance() != null) {
                BoundService.getInstance().SendNextInstructionUpdate(0);
            }
        }
    }

    public void unhide() throws  {
        if (!this.shown) {
            this.shown = true;
            this.bottomBar.unhide();
            setVisibility(0);
        }
    }

    public void setInstructionGeometry(InstructionGeometry $r1, int $i0) throws  {
        this.lastInstruction = $i0;
        if (BoundService.mIsRunning && BoundService.getInstance() != null) {
            this.instructionSent = true;
            BoundService.getInstance().SendNextInstructionUpdate($i0);
        }
        if ($r1 != null) {
            this.mImgDirection.setImageDrawable(new InstructionGeometryDrawable($r1));
            this.mBoxView.setVisibility(0);
        } else {
            this.mBoxView.setVisibility(8);
        }
        this.mTvDirection.setPadding(0, 0, 0, 0);
        this.IsNextInstruction = false;
    }

    public void setNextInstructionGeometry(InstructionGeometry $r1) throws  {
        if ($r1 != null) {
            if (this.mNextView.getVisibility() == 0 || !this.shown) {
                this.IsNextInstruction = true;
                this.NextInstructionGeometry = $r1;
            } else {
                if (!(this.mbInCondensedMode || this.mbNavListFragmentShowing || this.mbDisableNext)) {
                    showNextView();
                }
                this.mNextDisplayed = true;
            }
            this.thenDirection.setBackgroundDrawable(new InstructionGeometryDrawable($r1));
            this.thenDirection.setPadding(0, 0, 0, 0);
            return;
        }
        hideNextView();
        this.mNextDisplayed = false;
    }

    public void setInstruction(int $i0) throws  {
        this.lastInstruction = $i0;
        if (BoundService.mIsRunning && BoundService.getInstance() != null) {
            this.instructionSent = true;
            BoundService.getInstance().SendNextInstructionUpdate($i0);
        }
        $i0 = this.instImages[$i0];
        if ($i0 != 0) {
            this.mImgDirection.setImageResource($i0);
            this.mBoxView.setVisibility(0);
        } else {
            this.mBoxView.setVisibility(8);
        }
        this.mTvDirection.setPadding(0, 0, 0, 0);
        this.IsNextInstruction = false;
    }

    public void setNextInstruction(int $i0) throws  {
        if ($i0 >= this.instImages.length || this.instImages[$i0] == 0) {
            hideNextView();
            this.mNextDisplayed = false;
            return;
        }
        if (this.mNextView.getVisibility() == 0 || !this.shown) {
            this.IsNextInstruction = true;
            this.NextInstruction = $i0;
        } else {
            if (!(this.mbInCondensedMode || this.mbNavListFragmentShowing || this.mbDisableNext)) {
                showNextView();
            }
            this.mNextDisplayed = true;
        }
        this.thenDirection.setBackgroundResource(this.instImages[$i0]);
        this.thenDirection.setPadding(0, 0, 0, 0);
    }

    public void setExit(int $i0) throws  {
        String $r1 = $i0 > 0 ? String.valueOf($i0) : "";
        if (BoundService.mIsRunning && BoundService.getInstance() != null && $i0 > 0) {
            BoundService.getInstance().SendInstructionExitNumberUpdate($i0);
        }
        this.mTvDirection.setText($r1);
    }

    public void setNextExit(int $i0) throws  {
        this.thenDirection.setText($i0 > 0 ? String.valueOf($i0) : "");
    }

    public void setDistance(String $r1, String $r2, int $i0) throws  {
        if ($r1 == null) {
            this.mTvDistance.setVisibility(8);
            return;
        }
        this.mTvDistance.setVisibility(0);
        this.mTvDistance.setText($r1 + " " + $r2);
        if (BoundService.mIsRunning && BoundService.getInstance() != null && $i0 > 0) {
            if (!(this.instructionSent || this.lastInstruction == -1)) {
                BoundService.getInstance().SendNextInstructionUpdate(this.lastInstruction);
                this.instructionSent = true;
            }
            BoundService.getInstance().SendDistanceStringUpdate($i0, this.mTvDistance.getText().toString());
        }
    }

    public void setNextDistance(String distString) throws  {
    }

    public void setStreet(String $r1) throws  {
        this.mTvStreet.setText($r1);
        this.mTvStreet.getViewTreeObserver().addOnGlobalLayoutListener(new C20385());
    }

    public void set_outline_position(DoublePosition[] $r1) throws  {
        if (BoundService.mIsRunning && BoundService.getInstance() != null) {
            String $r3 = Utils.buildJsonFromDoublePoints($r1);
            if ($r3 != null) {
                BoundService.getInstance().SendRouteGeometry($r3);
            }
        }
    }

    public boolean setDistStr(String $r1) throws  {
        boolean $z0 = false;
        if ($r1 == null) {
            return false;
        }
        String[] $r2 = $r1.split(" ");
        if ($r2.length == 2) {
            this.bottomBar.setDistance($r2[0], $r2[1]);
            $z0 = true;
        }
        return $z0;
    }

    public void setThenHiddenForAlerter(boolean $z0) throws  {
        this.mThenHiddenForAlerter = $z0;
        if (this.mNextView == null) {
            return;
        }
        if (this.mShouldShowNextView && !$z0) {
            this.mNextView.setVisibility(0);
        } else if (this.mShouldShowNextView) {
            this.mNextView.setVisibility(8);
        }
    }

    public boolean setTimeStr(String $r1, int $i0) throws  {
        if ($r1 == null) {
            return false;
        }
        if (BoundService.mIsRunning && BoundService.getInstance() != null) {
            BoundService.getInstance().SendETAUpdate($i0);
        }
        this.bottomBar.setTime($r1, "");
        return true;
    }

    public boolean setEtaStr(String $r1) throws  {
        boolean $z0 = false;
        if ($r1 == null) {
            return false;
        }
        $r1 = $r1.trim();
        int $i0 = $r1.indexOf(" ");
        if ($i0 > 0) {
            this.bottomBar.setETA($r1.substring($i0 + 1));
            $z0 = true;
        }
        return $z0;
    }

    public boolean setOffline(boolean $z0) throws  {
        this.bottomBar.setOffline($z0);
        return true;
    }

    public void setDrivingDirection(boolean $z0) throws  {
        int[] $r1;
        if ($z0) {
            $r1 = instImagesLeft;
        } else {
            $r1 = instImagesRight;
        }
        this.instImages = $r1;
        if (BoundService.mIsRunning && BoundService.getInstance() != null) {
            BoundService.getInstance().SendLeftRightLaneUpdate($z0);
        }
    }

    public void setSkin(boolean $z0) throws  {
        this.mbNightMode = $z0;
        adjustImages();
        if (this.mSubView != null) {
            this.mSubView.setSkin($z0);
        }
        this.bottomBar.setMode($z0);
    }

    public void onOrientationChanged() throws  {
        setMode();
    }

    public void adjustImages() throws  {
        if (this.shown) {
            byte $b1;
            NavBarText $r1 = this.mTvStreet;
            if (this.mbInCondensedMode) {
                $b1 = (byte) 1;
            } else {
                $b1 = (byte) 2;
            }
            $r1.setMaxLines($b1);
            if (this.mbInCondensedMode) {
                this.mTvStreet.setTextSize(2, 20.0f);
                return;
            }
            boolean $z0;
            if (this.mTvStreet.getLineCount() > 1) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            this.mTvStreet.setTextSize(2, $z0 ? 22.0f : 26.0f);
        }
    }

    public void onReady(INavBarViewEvents ev) throws  {
        animateSubViewIn();
        this.mLayoutMgr.hideAlerterPopup();
    }

    public void onDone(INavBarViewEvents ev) throws  {
        animateSubViewOut();
    }

    public boolean isCondenseMode() throws  {
        return this.shown && this.mbInCondensedMode;
    }

    private void animateSubViewIn() throws  {
        if (this.mbInCondensedMode) {
            if (this.mNextDisplayed) {
                showNextView();
            }
            findViewById(C1283R.id.navBarSubViewFrame).setVisibility(0);
            findViewById(C1283R.id.navBarShadow).setVisibility(8);
            return;
        }
        setAlertMode(true);
        if (this.mNextDisplayed) {
            showNextView();
        }
        findViewById(C1283R.id.navBarSubViewFrame).setVisibility(0);
        findViewById(C1283R.id.navBarShadow).setVisibility(8);
    }

    private void animateSubViewOut() throws  {
        setAlertMode(false);
        forceHideNearingDest();
        findViewById(C1283R.id.navBarSubViewFrame).setVisibility(8);
        if (this.shown) {
            findViewById(C1283R.id.navBarShadow).setVisibility(0);
        }
        this.mSubView.getView().clearAnimation();
    }

    public void setAlertMode(boolean $z0) throws  {
        setAlertMode($z0, false);
    }

    public void setAlertMode(boolean $z0, boolean $z1) throws  {
        LayoutTransition $r1 = getLayoutTransition();
        if ($z1) {
            setLayoutTransition(null);
        }
        this.mbInAlertMode = $z0;
        setMode();
        if ($z1) {
            setLayoutTransition($r1);
        }
    }

    public void setMode() throws  {
        boolean $z0 = false;
        if (AppService.getAppContext().getResources().getConfiguration().orientation == 2) {
            $z0 = true;
        }
        if (this.mNavBarManager.isNearingDestNTV() || this.mbInAlertMode || $z0) {
            if (!this.mbInCondensedMode) {
                toCondensedMode();
            }
        } else if (this.mbInCondensedMode) {
            toExpandedMode();
        }
        this.mImgDirection.postInvalidate();
    }

    private void toExpandedMode() throws  {
        this.mTopView.setMinimumHeight((int) (90.0f * this.scale));
        LinearLayout.LayoutParams $r3 = (LinearLayout.LayoutParams) this.mBoxView.getLayoutParams();
        $r3.width = (int) (this.scale * 70.0f);
        $r3.height = (int) (this.scale * 70.0f);
        this.mBoxView.setLayoutParams($r3);
        if (this.mNextDisplayed) {
            showNextView();
        }
        ((LinearLayout) findViewById(C1283R.id.navBarBox2)).setOrientation(1);
        this.mTvStreet.setMaxLines(2);
        this.mTvDirection.setTextSize(1, 16.0f);
        this.mTvDistance.setTextSize(1, 28.0f);
        this.mbInCondensedMode = false;
        adjustImages();
        this.mLayoutMgr.showSpotifyButton();
    }

    private void toCondensedMode() throws  {
        View $r0 = this;
        this.mTopView.setMinimumHeight((int) (this.scale * 45.0f));
        if (VERSION.SDK_INT >= 16) {
            $r0 = $r0;
            ((ViewGroup) $r0.mBoxView).getLayoutTransition().enableTransitionType(4);
        }
        View $r1 = $r0;
        $r0 = $r1;
        LinearLayout.LayoutParams $r5 = (LinearLayout.LayoutParams) $r1.mBoxView.getLayoutParams();
        $r5.width = (int) ($r0.scale * 45.0f);
        $r5.height = (int) ($r0.scale * 45.0f);
        $r1 = $r0;
        $r0 = $r1;
        $r1.mBoxView.setLayoutParams($r5);
        $r0.hideNextView();
        LinearLayout $r6 = (LinearLayout) $r0.findViewById(C1283R.id.navBarBox2);
        if (VERSION.SDK_INT >= 16) {
            $r6.getLayoutTransition().enableTransitionType(4);
        }
        $r6.setOrientation(0);
        $r0.mTvStreet.setMaxLines(1);
        $r0.mTvDirection.setTextSize(1, 12.0f);
        $r0.mTvDistance.setTextSize(1, 20.0f);
        $r0.mbInCondensedMode = true;
        $r0.adjustImages();
        if (AppService.getAppContext().getResources().getConfiguration().orientation != 2) {
            LayoutManager $r12 = $r0.mLayoutMgr;
            $r12.hideSpotifyButton();
        }
    }

    public void showNearingDestination() throws  {
        if (this.mLayoutMgr.isCarpoolTickerVisible()) {
            Logger.m38e("Manual ride: wanted to show nearing dest even though ticker is open");
        } else if (!this.mLayoutMgr.isAlerterShown()) {
            this.mLayoutMgr.hideSpotifyButton();
            this.mLayoutMgr.closeSpotifyPopup();
            removeNearingDest();
            NearingDest $r1 = new NearingDest(getContext());
            ((FrameLayout) findViewById(C1283R.id.navBarSubViewFrame)).addView($r1);
            Log.i("GilTestDest", "Nearing dest shown!");
            $r1.setCallbacks(this);
            $r1.nearingDestinationSetup();
            $r1.setSkin(this.mbNightMode);
            this.mSubView = $r1;
            this.mSubView.onShow();
            setMode();
        }
    }

    private void removeNearingDest() throws  {
        Log.i("GilTestDest", "Remove nearing dest called");
        if (!(this.mSubView == null || ((NearingDest) this.mSubView).getParent() == null)) {
            NearingDest $r2 = (NearingDest) this.mSubView;
            ((ViewGroup) $r2.getParent()).removeView($r2);
            Log.i("GilTestDest", "Nearing dest hidden");
        }
        forceHideNearingDest();
        this.mbInAlertMode = false;
        setMode();
    }

    private void forceHideNearingDest() throws  {
        FrameLayout $r2 = (FrameLayout) findViewById(C1283R.id.navBarSubViewFrame);
        if ($r2.getChildCount() > 1) {
            int $i0 = 0;
            while ($i0 < $r2.getChildCount()) {
                if ($r2.getChildAt($i0) == null || !($r2.getChildAt($i0) instanceof NearingDest)) {
                    $i0++;
                } else {
                    Log.i("GilTestDest", "Nearing dest force hidden");
                    $r2.removeView($r2.getChildAt($i0));
                    return;
                }
            }
        }
    }

    public void hideNearingDestination(boolean $z0) throws  {
        if (this.mSubView == null || !(this.mSubView instanceof NearingDest)) {
            forceHideNearingDest();
            return;
        }
        this.shouldRestoreSubView = false;
        this.mSubView.onHide();
        this.mLayoutMgr.showSpotifyButton();
        if ($z0) {
            animateSubViewOut();
        } else {
            removeNearingDest();
            this.mSubView = null;
            findViewById(C1283R.id.navBarSubViewFrame).setVisibility(8);
            if (this.shown) {
                findViewById(C1283R.id.navBarShadow).setVisibility(0);
            }
        }
        if (this.onSubViewHidden != null) {
            this.onSubViewHidden.run();
        }
    }

    public int getNavBarHeight() throws  {
        return getHeight();
    }

    public void onActivityResult(Activity $r1, int $i0, int $i1, Intent $r2) throws  {
        if (this.mSubView != null) {
            this.mSubView.onActivityResult($r1, $i0, $i1, $r2);
        }
    }

    public boolean isSubViewDisplayed() throws  {
        return this.mNavBarManager.isNearingDestNTV();
    }

    public boolean isShown() throws  {
        return this.shown;
    }

    public void setOnSubViewHidden(Runnable $r1) throws  {
        this.onSubViewHidden = $r1;
    }

    public void setNextEnabled(boolean $z0) throws  {
        if (this.mNextDisplayed && this.mbDisableNext && $z0 && !this.mbInCondensedMode && !this.mbNavListFragmentShowing) {
            showNextView();
        }
        if (!(!this.mNextDisplayed || this.mbDisableNext || $z0)) {
            hideNextView();
        }
        if ($z0) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        this.mbDisableNext = $z0;
    }
}
