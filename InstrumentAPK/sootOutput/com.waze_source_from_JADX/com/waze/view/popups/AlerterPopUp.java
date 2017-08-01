package com.waze.view.popups;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.settings.SettingsAlertsOnRoute;
import com.waze.settings.SettingsPowerSaving;
import com.waze.view.anim.AxisFlipper;
import com.waze.view.button.PillButton;
import com.waze.view.text.WazeTextView;
import com.waze.view.timer.TimerBar;

public class AlerterPopUp extends PopUp {
    public static final String POWER_SAVING_ICON_NAME = "battery_mode_icon";
    private String mDistance;
    private boolean mIsPowerSavingAlerter;
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;
    private TimerBar mTimerBar;
    private boolean mTimerSet = false;
    private String mTitle;

    class C30511 implements AnimationListener {

        class C30501 implements Runnable {
            C30501() {
            }

            public void run() {
                AppService.getNativeManager().AlerterClosedNTV();
            }
        }

        C30511() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            View layout = AlerterPopUp.this.findViewById(C1283R.id.alerterColorLayout);
            LayoutParams p = layout.getLayoutParams();
            p.height = -2;
            layout.setLayoutParams(p);
            AlerterPopUp.this.mLayoutManager.dismiss(AlerterPopUp.this);
            AlerterPopUp.this.mLayoutManager.showSpotifyButton();
            NativeManager.Post(new C30501());
            if (AlerterPopUp.this.mIsPowerSavingAlerter) {
                NativeManager.getInstance().getNavBarManager().onPowerSavingNotificationDismissed();
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class C30522 implements Runnable {
        C30522() {
        }

        public void run() {
            AppService.getNativeManager().AlerterThumbsUpNTV();
        }
    }

    class C30533 implements Runnable {
        C30533() {
        }

        public void run() {
            AppService.getNativeManager().AlerterNotThereNTV();
        }
    }

    class C30544 implements OnClickListener {
        C30544() {
        }

        public void onClick(View v) {
            AlerterPopUp.this.onNotThere();
        }
    }

    class C30555 implements OnClickListener {
        C30555() {
        }

        public void onClick(View v) {
            AlerterPopUp.this.onThumbsUp();
        }
    }

    class C30566 implements OnClickListener {
        C30566() {
        }

        public void onClick(View v) {
            AlerterPopUp.this.onClose();
            if (AlerterPopUp.this.mIsPowerSavingAlerter) {
                AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsPowerSaving.class));
            }
        }
    }

    class C30577 implements Interpolator {
        C30577() {
        }

        public float getInterpolation(float input) {
            if (((double) input) < 0.1d) {
                return input * 10.0f;
            }
            if (((double) input) < 0.15d) {
                return 1.0f;
            }
            if (((double) input) < 0.25d) {
                return (0.25f - input) * 10.0f;
            }
            if (((double) input) < 0.3d) {
                return 0.0f;
            }
            if (((double) input) < 0.4d) {
                return (input - BottomSheet.DISABLED_ALPHA) * 10.0f;
            }
            if (((double) input) >= 0.85d) {
                return ((double) input) < 0.95d ? (PillButton.PRESSED_SCALE_DOWN - input) * 10.0f : 0.0f;
            } else {
                return 1.0f;
            }
        }
    }

    class C30588 implements OnClickListener {
        C30588() {
        }

        public void onClick(View v) {
            AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsAlertsOnRoute.class));
            AlerterPopUp.this.hide();
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LIGHTS_ALERT_ACTION).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "SETTINGS").send();
        }
    }

    class C30599 implements OnClickListener {
        C30599() {
        }

        public void onClick(View v) {
            AlerterPopUp.this.hide();
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LIGHTS_ALERT_ACTION).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_INFO_THANKS).send();
        }
    }

    public AlerterPopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    private void init() {
        View me = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.alerter_popup, this, false);
        addView(me, -1, -2);
        this.mTimerBar = (TimerBar) me.findViewById(C1283R.id.alerterTimerBar);
        if (VERSION.SDK_INT >= 16) {
            ((ViewGroup) findViewById(C1283R.id.alerterColorLayout)).getLayoutTransition().enableTransitionType(4);
        }
    }

    private void setTitle(String title) {
        this.mTitle = title;
        TextView textView = (TextView) findViewById(C1283R.id.alerterTitleText);
        if (title == null) {
            textView.setText("");
        } else {
            textView.setText(title);
        }
        if (this.mIsPowerSavingAlerter) {
            textView.setTextColor(getResources().getColor(C1283R.color.White));
        }
    }

    private void setDistance(String distanceStr) {
        this.mDistance = distanceStr;
        TextView textView = (TextView) findViewById(C1283R.id.alerterLocationText);
        if (distanceStr != null) {
            textView.setVisibility(0);
            textView.setText(NativeManager.getInstance().getLanguageString(distanceStr));
            return;
        }
        textView.setVisibility(8);
    }

    private void setIcon(String iconName) {
        ImageView image = (ImageView) findViewById(C1283R.id.alerterIcon);
        if (iconName != null) {
            image.setImageResource(ResManager.GetDrawableId(iconName.toLowerCase()));
        }
        if (this.mIsPowerSavingAlerter) {
            image.setScaleX(0.85f);
            image.setScaleY(0.85f);
        }
    }

    public void dismiss() {
        if (this.mIsShown) {
            this.mIsShown = false;
            this.mTimerSet = false;
            this.mTimerBar.stop();
            this.mLayoutManager.RefreshBar(MyWazeNativeManager.getInstance().getNumberOfFriendsOnline(), MyWazeNativeManager.getInstance().getNumberOfFriendsPending());
            AxisFlipper rotation = new AxisFlipper(0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -90.0d, 0.5d, 0.0d);
            rotation.setDuration(400);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AnticipateInterpolator());
            rotation.setAnimationListener(new C30511());
            startAnimation(rotation);
        }
    }

    public void hide() {
        CarpoolNativeManager.getInstance().openCarpoolTakeoverIfNecessary();
        dismiss();
    }

    public void setCloseTime(int timer) {
        if (!this.mTimerSet) {
            View layout = findViewById(C1283R.id.alerterColorLayout);
            int height = layout.getMeasuredHeight();
            if (height > 0) {
                LayoutParams p = layout.getLayoutParams();
                p.height = height;
                layout.setLayoutParams(p);
            }
            this.mTimerBar.setTime(timer);
            this.mTimerBar.start();
            this.mTimerBar.setVisibility(0);
            this.mTimerSet = true;
        }
    }

    public void update(String titleStr, String iconName, String distanceStr) {
        if (!this.mIsPowerSavingAlerter) {
            if (!(titleStr == null || titleStr.isEmpty())) {
                setTitle(titleStr);
            }
            setDistance(distanceStr);
            setIcon(iconName);
        }
    }

    private void onThumbsUp() {
        NativeManager.Post(new C30522());
        hide();
    }

    private void onNotThere() {
        NativeManager.Post(new C30533());
        hide();
    }

    private void onClose() {
        hide();
    }

    public void show(String titleStr, String iconName, String distanceStr, boolean is_cancelable, boolean can_send_thumbs_up, int num_thumbs_up, int color, boolean close_only) {
        boolean z;
        View notThereBut;
        View thumbUpBut;
        View closeBut;
        final String str;
        if (iconName != null) {
            if (iconName.equals(POWER_SAVING_ICON_NAME)) {
                z = true;
                this.mIsPowerSavingAlerter = z;
                setDistance(distanceStr);
                setTitle(titleStr);
                setIcon(iconName);
                this.mTimerBar.reset();
                this.mTimerBar.setVisibility(8);
                if (color == 0 || color == 16777215) {
                    color = -6379606;
                }
                setColor(-16777216 | color);
                notThereBut = findViewById(C1283R.id.alerterNotThereButton);
                if (is_cancelable || close_only) {
                    notThereBut.setEnabled(false);
                    notThereBut.setVisibility(8);
                } else {
                    notThereBut.setVisibility(0);
                    notThereBut.setEnabled(true);
                    notThereBut.setOnClickListener(new C30544());
                }
                thumbUpBut = findViewById(C1283R.id.alerterThumbsUpButton);
                if (can_send_thumbs_up || close_only) {
                    thumbUpBut.setVisibility(8);
                } else {
                    thumbUpBut.setVisibility(0);
                    thumbUpBut.setOnClickListener(new C30555());
                    TextView thumbUpText = (TextView) findViewById(C1283R.id.alerterThumbsUpButtonText);
                    String text = "";
                    if (num_thumbs_up > 0) {
                        text = text + num_thumbs_up;
                    }
                    thumbUpText.setText(text);
                }
                closeBut = findViewById(C1283R.id.alerterCloseButton);
                if (close_only) {
                    closeBut.setVisibility(8);
                } else {
                    closeBut.setVisibility(0);
                    if (this.mIsPowerSavingAlerter) {
                        findViewById(C1283R.id.alerterCloseButtonText).setBackgroundResource(C1283R.drawable.takeover_setting_grey);
                    }
                    closeBut.setOnClickListener(new C30566());
                }
                if (!this.mIsShown) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
                    layoutParams.addRule(3, C1283R.id.NavBarLayout);
                    this.mLayoutManager.addView(this, layoutParams);
                    AxisFlipper rotation = new AxisFlipper(0.0d, 0.0d, 0.0d, 0.0d, -90.0d, 0.0d, 0.5d, 0.0d);
                    rotation.setDuration(500);
                    rotation.setFillAfter(true);
                    rotation.setInterpolator(new OvershootInterpolator());
                    startAnimation(rotation);
                }
                if (iconName != null) {
                    if (iconName.equals("headlights_off")) {
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LIGHTS_ALERT_SHOWN).send();
                        setIcon("headlights_off");
                        ImageView overlay = (ImageView) findViewById(C1283R.id.alerterIconOverlay);
                        overlay.setVisibility(0);
                        overlay.setImageResource(C1283R.drawable.headlights_on);
                        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setRepeatMode(1);
                        anim.setRepeatCount(-1);
                        anim.setDuration(1700);
                        anim.setInterpolator(new C30577());
                        overlay.startAnimation(anim);
                        ((WazeTextView) findViewById(C1283R.id.alerterNotThereButtonText)).setBackgroundResource(C1283R.drawable.takeover_setting_grey);
                        notThereBut.setVisibility(0);
                        notThereBut.setEnabled(true);
                        notThereBut.setOnClickListener(new C30588());
                        thumbUpBut.setVisibility(0);
                        thumbUpBut.setOnClickListener(new C30599());
                        this.mTimerBar.setOnEndRunnable(new Runnable() {
                            public void run() {
                                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LIGHTS_ALERT_ACTION).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "TIMEOUT").send();
                            }
                        });
                        ((TextView) findViewById(C1283R.id.alerterTitleText)).setTextColor(getResources().getColor(C1283R.color.White));
                    }
                }
                str = iconName;
                findViewById(C1283R.id.alerterLayout).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        AlerterPopUp.this.mLayoutManager.hideAlerterPopup();
                        if (str != null && str.equals("headlights_off")) {
                            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LIGHTS_ALERT_ACTION).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "CLOSE").send();
                        }
                    }
                });
                this.mIsShown = true;
            }
        }
        z = false;
        this.mIsPowerSavingAlerter = z;
        setDistance(distanceStr);
        setTitle(titleStr);
        setIcon(iconName);
        this.mTimerBar.reset();
        this.mTimerBar.setVisibility(8);
        color = -6379606;
        setColor(-16777216 | color);
        notThereBut = findViewById(C1283R.id.alerterNotThereButton);
        if (is_cancelable) {
        }
        notThereBut.setEnabled(false);
        notThereBut.setVisibility(8);
        thumbUpBut = findViewById(C1283R.id.alerterThumbsUpButton);
        if (can_send_thumbs_up) {
        }
        thumbUpBut.setVisibility(8);
        closeBut = findViewById(C1283R.id.alerterCloseButton);
        if (close_only) {
            closeBut.setVisibility(8);
        } else {
            closeBut.setVisibility(0);
            if (this.mIsPowerSavingAlerter) {
                findViewById(C1283R.id.alerterCloseButtonText).setBackgroundResource(C1283R.drawable.takeover_setting_grey);
            }
            closeBut.setOnClickListener(new C30566());
        }
        if (this.mIsShown) {
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams2.addRule(3, C1283R.id.NavBarLayout);
            this.mLayoutManager.addView(this, layoutParams2);
            AxisFlipper rotation2 = new AxisFlipper(0.0d, 0.0d, 0.0d, 0.0d, -90.0d, 0.0d, 0.5d, 0.0d);
            rotation2.setDuration(500);
            rotation2.setFillAfter(true);
            rotation2.setInterpolator(new OvershootInterpolator());
            startAnimation(rotation2);
        }
        if (iconName != null) {
            if (iconName.equals("headlights_off")) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LIGHTS_ALERT_SHOWN).send();
                setIcon("headlights_off");
                ImageView overlay2 = (ImageView) findViewById(C1283R.id.alerterIconOverlay);
                overlay2.setVisibility(0);
                overlay2.setImageResource(C1283R.drawable.headlights_on);
                AlphaAnimation anim2 = new AlphaAnimation(0.0f, 1.0f);
                anim2.setRepeatMode(1);
                anim2.setRepeatCount(-1);
                anim2.setDuration(1700);
                anim2.setInterpolator(new C30577());
                overlay2.startAnimation(anim2);
                ((WazeTextView) findViewById(C1283R.id.alerterNotThereButtonText)).setBackgroundResource(C1283R.drawable.takeover_setting_grey);
                notThereBut.setVisibility(0);
                notThereBut.setEnabled(true);
                notThereBut.setOnClickListener(new C30588());
                thumbUpBut.setVisibility(0);
                thumbUpBut.setOnClickListener(new C30599());
                this.mTimerBar.setOnEndRunnable(/* anonymous class already generated */);
                ((TextView) findViewById(C1283R.id.alerterTitleText)).setTextColor(getResources().getColor(C1283R.color.White));
            }
        }
        str = iconName;
        findViewById(C1283R.id.alerterLayout).setOnClickListener(/* anonymous class already generated */);
        this.mIsShown = true;
    }

    private void setColor(int color) {
        float[] hsl = new float[3];
        ColorUtils.colorToHSL(color, hsl);
        hsl[2] = hsl[2] * 0.85f;
        int darkColor = ColorUtils.HSLToColor(hsl);
        ColorUtils.colorToHSL(color, hsl);
        hsl[2] = Math.min(1.0f, hsl[2] * 1.1f);
        int brightColor = ColorUtils.HSLToColor(hsl);
        findViewById(C1283R.id.alerterColorLayout).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{darkColor, brightColor}));
        this.mTimerBar.setTrackColor(brightColor);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setTitle(this.mTitle);
        setDistance(this.mDistance);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
