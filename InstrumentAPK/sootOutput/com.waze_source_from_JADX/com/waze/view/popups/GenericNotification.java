package com.waze.view.popups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.carpool.CarpoolDrive;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.navbar.NavBar;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository$ImageRepositoryListener;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;
import com.waze.view.anim.AxisFlipper;
import com.waze.view.button.PillButton;
import com.waze.view.drawables.MultiContactsBitmap;

public class GenericNotification extends PopUp {
    private boolean mIsShown = false;
    LayoutManager mLayoutManager;
    private Runnable mOnClose;

    class C31522 extends AnimationEndListener {
        C31522() {
        }

        public void onAnimationEnd(Animation animation) {
            GenericNotification.this.mLayoutManager.dismiss(GenericNotification.this);
            NavBar navBar = GenericNotification.this.mLayoutManager.getNavBar();
            if (navBar != null) {
                navBar.setNextEnabled(true);
            }
            if (GenericNotification.this.mOnClose != null) {
                AppService.Post(GenericNotification.this.mOnClose);
                GenericNotification.this.mOnClose = null;
            }
        }
    }

    GenericNotification(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.generic_notification, this);
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    void setButton2(int bgResId, String content, boolean enabled, OnClickListener onClick) {
        GenericTakeover.setButton(bgResId, content, enabled, findViewById(C1283R.id.genNotificationButton2), (TextView) findViewById(C1283R.id.genNotificationButton2Text), onClick);
    }

    void setButton2Gone() {
        findViewById(C1283R.id.genNotificationButton2).setVisibility(8);
    }

    void setButton1(int bgResId, String content, boolean enabled, OnClickListener onClick) {
        GenericTakeover.setButton(bgResId, content, enabled, findViewById(C1283R.id.genNotificationButton1), (TextView) findViewById(C1283R.id.genNotificationButton1Text), onClick);
    }

    void setButton1Gone() {
        findViewById(C1283R.id.genNotificationButton1).setVisibility(8);
    }

    void setText(String text) {
        ((TextView) findViewById(C1283R.id.genNotificationText)).setText(text);
    }

    void setTitle(String text) {
        TextView title = (TextView) findViewById(C1283R.id.genNotificationTitle);
        title.setText(text);
        title.setVisibility(0);
    }

    public void setIcon(int iconId) {
        ((ImageView) findViewById(C1283R.id.genNotificationImage)).setImageResource(iconId);
    }

    void setIcon(Drawable d) {
        ((ImageView) findViewById(C1283R.id.genNotificationImage)).setImageDrawable(d);
    }

    public void setFrameVisible(boolean visible) {
        ((FrameLayout) findViewById(C1283R.id.genNotificationImageFrame)).setForeground(visible ? getResources().getDrawable(C1283R.drawable.trip_picture_frame) : null);
    }

    public void setUserImage(String userImageUrl) {
        ImageView image = (ImageView) findViewById(C1283R.id.genNotificationImage);
        image.setImageResource(C1283R.drawable.user_image_placeholder);
        ImageRepository.instance.getImage(userImageUrl, 1, image, null, AppService.getActiveActivity());
    }

    public void setUserImages(CarpoolDrive drive) {
        if (drive != null) {
            final ImageView image = (ImageView) findViewById(C1283R.id.genNotificationImage);
            image.setImageResource(C1283R.drawable.user_image_placeholder);
            new MultiContactsBitmap(new ImageRepository$ImageRepositoryListener() {
                public void onImageRetrieved(Bitmap bitmap) {
                    image.setImageDrawable(new CircleShaderDrawable(bitmap, 0));
                }
            }, getResources(), C1283R.drawable.default_avatar).buildBitmap(drive);
        }
    }

    protected void setDrawTimerButton1() {
        PillButton button = (PillButton) findViewById(C1283R.id.genNotificationButton1);
        button.setDrawTimer();
        button.setPerformTapOnEnd(false);
    }

    protected void stopCloseTimerButton1() {
        ((PillButton) findViewById(C1283R.id.genNotificationButton1)).stopTimer();
    }

    protected void setCloseTimerButton1(int iTimeOut) {
        PillButton button = (PillButton) findViewById(C1283R.id.genNotificationButton1);
        button.setTimeoutMilliSec(iTimeOut);
        button.startTimer();
    }

    protected void setCloseTimerButton2(int iTimeOut) {
        PillButton button = (PillButton) findViewById(C1283R.id.genNotificationButton2);
        button.setTimeoutMilliSec(iTimeOut);
        button.setDrawTimer();
        button.startTimer();
    }

    void stopCloseTimer() {
        ((PillButton) findViewById(C1283R.id.genNotificationButton2)).stopTimer();
    }

    void setCloseTimer(int iTimeOut) {
        PillButton button = (PillButton) findViewById(C1283R.id.genNotificationButton2);
        button.setTimeoutMilliSec(iTimeOut);
        button.startTimer();
    }

    public void hide() {
        if (this.mIsShown) {
            this.mIsShown = false;
            stopCloseTimer();
            AxisFlipper rotation = new AxisFlipper(0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -90.0d, 0.5d, 0.0d);
            rotation.setDuration(400);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AnticipateInterpolator());
            rotation.setAnimationListener(new C31522());
            startAnimation(rotation);
            this.mLayoutManager.showSpotifyButton();
        }
    }

    public void quickHide() {
        this.mIsShown = false;
        stopCloseTimer();
        this.mLayoutManager.dismiss(this);
        NavBar navBar = this.mLayoutManager.getNavBar();
        if (navBar != null) {
            navBar.setNextEnabled(true);
        }
        if (this.mOnClose != null) {
            this.mOnClose.run();
            this.mOnClose = null;
        }
    }

    void init() {
    }

    void show() {
        if (this.mIsShown) {
            quickHide();
        }
        this.mLayoutManager.hideSpotifyButton();
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(3, C1283R.id.NavBarLayout);
        layoutParams.alignWithParent = true;
        this.mIsShown = true;
        this.mLayoutManager.addView(this, layoutParams);
        if (this.mLayoutManager.getNavBar() != null) {
            this.mLayoutManager.getNavBar().setNextEnabled(false);
        }
        Animation animation = getAnimation();
        if (animation != null) {
            animation.setAnimationListener(null);
            clearAnimation();
        }
        AxisFlipper rotation = new AxisFlipper(0.0d, 0.0d, 0.0d, 0.0d, -90.0d, 0.0d, 0.5d, 0.0d);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new OvershootInterpolator());
        startAnimation(rotation);
    }

    public void setOnClose(Runnable runnable) {
        this.mOnClose = runnable;
    }
}
