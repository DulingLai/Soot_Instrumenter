package com.waze.view.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ILocationSensorListener;
import com.waze.LayoutManager;
import com.waze.LocationFactory;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.NativeManager.StringResultListener;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.map.CanvasFont;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.reports.SpeedLimitReport;
import com.waze.reports.SpeedLimitReport.Listener;
import com.waze.rtalerts.RTAlertsNativeManager;
import com.waze.settings.SettingsMapActivity;
import com.waze.settings.SettingsSpeedometerActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.WazeTextView;

public class SpeedometerView extends FrameLayout {
    private static final float ANIMATION_SCALE = 1.9f;
    private static final int ANIMATION_TIME = 300;
    private static final int ANIMATION_X_OFFSET = PixelMeasure.dp(-30);
    private static final int ANIMATION_Y_OFFSET = PixelMeasure.dp(29);
    private static final int ANIMATION_Y_OFFSET_APPEARING = PixelMeasure.dp(65);
    private static final int DELAY_TIME = 2000;
    private static final int TICK = 10;
    private Runnable colorArcTask = new C30146();
    boolean mColorFaster;
    float mColorFrom;
    float mColorTo;
    SpeedometerColoredView mColoredView;
    private int mCurrentMaxSpeedMMSec = -1;
    private int mCurrentMaxSpeedRoad;
    boolean mFirstOver = true;
    private LayoutInflater mInflater;
    long mLastBeep = -1;
    int mLastMaxSpeed = -1;
    int mLastSpeed = -1;
    float mLastSweep = 0.0f;
    ILocationSensorListener mLocProv;
    int mMovingColor = 1;
    boolean mShouldStop = true;
    boolean mSpeedLimitAlert = false;
    boolean mSpeedLimitDebug = false;
    boolean mSpeedLimitEnabled = false;
    int mSpeedLimitOffset = 0;
    boolean mSpeedLimitUserAlways = false;
    boolean mSpeedLimitUserEnabled = false;
    TextView mSpeedText;
    TextView mSpeedUnits;
    WazeTextView mSpeedWarn;
    View mSpeedWarnLayout;
    WazeTextView mSpeedWarnOverlay;
    View mSpeedometer;
    boolean mSpeedometerEnabled = false;
    boolean mSpeedometerShownOnce = false;
    boolean mWarningShown = false;
    private Runnable reduceAnimation = new C30135();
    Runnable setlocationEvent;

    class C30031 implements Runnable {
        C30031() {
        }

        public void run() {
            Logger.i("Speedometer: initializing speedometer: registering to location events");
            if (NativeManager.IsAppStarted()) {
                Logger.i("Speedometer: Native App already started");
                SpeedometerView.this.mLocProv = LocationFactory.getInstance();
                SpeedometerView.this.mLocProv.initSpeedometer(SpeedometerView.this);
                SpeedometerView.this.updateConfig();
                return;
            }
            Logger.i("Speedometer: Native App not started yet");
            SpeedometerView.this.postDelayed(SpeedometerView.this.setlocationEvent, 200);
        }
    }

    class C30114 implements OnTouchListener {
        boolean inflated = false;
        public float scaleX;
        public float scaleY;

        C30114() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            boolean outside;
            if (new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom()).contains(v.getLeft() + ((int) event.getX()), v.getTop() + ((int) event.getY()))) {
                outside = false;
            } else {
                outside = true;
            }
            if (action == 0 || (this.inflated && (action == 1 || outside))) {
                float startScale;
                float endScale;
                if (action == 0) {
                    startScale = 1.0f;
                    endScale = LayoutManager.REPORT_SCALE_ON_PRESS;
                    this.scaleX = event.getX();
                    this.scaleY = event.getY();
                    this.inflated = true;
                } else {
                    startScale = LayoutManager.REPORT_SCALE_ON_PRESS;
                    endScale = 1.0f;
                    this.inflated = false;
                }
                ScaleAnimation sa = new ScaleAnimation(startScale, endScale, startScale, endScale, 0, this.scaleX, 0, this.scaleY);
                sa.setDuration(100);
                sa.setInterpolator(new AccelerateDecelerateInterpolator());
                sa.setFillAfter(true);
                SpeedometerView.this.startAnimation(sa);
            }
            return false;
        }
    }

    class C30135 implements Runnable {

        class C30121 extends AnimationEndListener {
            C30121() {
            }

            public void onAnimationEnd(Animation animation) {
                SpeedometerView.this.mSpeedWarnOverlay.setVisibility(8);
            }
        }

        C30135() {
        }

        public void run() {
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(new ScaleAnimation(SpeedometerView.ANIMATION_SCALE, 1.0f, SpeedometerView.ANIMATION_SCALE, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            as.addAnimation(new TranslateAnimation((float) SpeedometerView.ANIMATION_X_OFFSET, 0.0f, (float) SpeedometerView.ANIMATION_Y_OFFSET, 0.0f));
            as.setDuration(300);
            as.setInterpolator(new DecelerateInterpolator());
            SpeedometerView.this.findViewById(C1283R.id.speedLimitWarnLayout).startAnimation(as);
            if (SpeedometerView.this.usMode()) {
                AlphaAnimation overlayAa = new AlphaAnimation(1.0f, 0.0f);
                overlayAa.setDuration(300);
                overlayAa.setAnimationListener(new C30121());
                SpeedometerView.this.mSpeedWarnOverlay.setVisibility(0);
                SpeedometerView.this.mSpeedWarnOverlay.startAnimation(overlayAa);
            }
        }
    }

    class C30146 implements Runnable {
        C30146() {
        }

        public void run() {
            if (!SpeedometerView.this.mShouldStop) {
                SpeedometerView.this.mColoredView.setColor(SpeedometerView.this.mMovingColor);
                SpeedometerView.this.mColoredView.setSweep(SpeedometerView.this.mColorFrom);
                SpeedometerView.this.mColoredView.invalidate();
                SpeedometerView speedometerView;
                if (SpeedometerView.this.mColorFaster) {
                    if (SpeedometerView.this.mColorFrom < SpeedometerView.this.mColorTo) {
                        speedometerView = SpeedometerView.this;
                        speedometerView.mColorFrom += 3.0f;
                    } else {
                        SpeedometerView.this.mShouldStop = true;
                    }
                } else if (SpeedometerView.this.mColorFrom > SpeedometerView.this.mColorTo) {
                    speedometerView = SpeedometerView.this;
                    speedometerView.mColorFrom -= 3.0f;
                } else {
                    SpeedometerView.this.mShouldStop = true;
                }
            }
            if (!SpeedometerView.this.mShouldStop) {
                SpeedometerView.this.postDelayed(SpeedometerView.this.colorArcTask, 10);
            }
        }
    }

    public SpeedometerView(Context context) {
        super(context);
        init(context);
    }

    public SpeedometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpeedometerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mInflater.inflate(C1283R.layout.speedometer, this);
        this.mSpeedometer = findViewById(C1283R.id.Speedometer);
        this.mColoredView = (SpeedometerColoredView) findViewById(C1283R.id.SpeedometerColoredView);
        this.mSpeedWarnLayout = findViewById(C1283R.id.speedLimitWarnLayout);
        this.mSpeedWarn = (WazeTextView) findViewById(C1283R.id.speedLimitWarn);
        this.mSpeedWarnOverlay = (WazeTextView) findViewById(C1283R.id.speedLimitWarnUsOverlay);
        this.mSpeedometer.setVisibility(8);
        this.mSpeedWarnLayout.setVisibility(8);
        this.mSpeedWarnOverlay.setVisibility(8);
        this.mSpeedText = (TextView) findViewById(C1283R.id.speedText);
        this.mSpeedUnits = (TextView) findViewById(C1283R.id.speedUnits);
        this.setlocationEvent = new C30031();
        RunnableExecutor confUpdateEvent = new RunnableExecutor(this) {
            public void event() {
                SpeedometerView.this.updateConfig();
            }
        };
        SettingsSpeedometerActivity.registerOnConfChange(confUpdateEvent);
        SettingsMapActivity.registerOnConfChange(confUpdateEvent);
        this.mColoredView.invalidate();
        post(this.setlocationEvent);
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_TAPPED);
                if (SpeedometerView.this.mSpeedLimitEnabled) {
                    NativeManager.getInstance().savePoiPosition(false);
                    SimpleBottomSheetValue[] values = new SimpleBottomSheetValue[2];
                    int reportDs = DisplayStrings.DS_SPEEDOMETER_MENU_ADD;
                    if (SpeedometerView.this.mCurrentMaxSpeedRoad > 0) {
                        reportDs = DisplayStrings.DS_SPEEDOMETER_MENU_REPORT;
                    }
                    values[0] = new SimpleBottomSheetValue(0, DisplayStrings.displayString(reportDs), context.getResources().getDrawable(C1283R.drawable.list_icon_speedometer));
                    values[1] = new SimpleBottomSheetValue(1, DisplayStrings.displayString(DisplayStrings.DS_SPEEDOMETER_MENU_SETTINGS), context.getResources().getDrawable(C1283R.drawable.list_icon_settings_general));
                    final SimpleBottomSheet speedBottomSheet = new SimpleBottomSheet(context, Mode.COLUMN_TEXT_ICON, (int) DisplayStrings.DS_SPEEDOMETER_MENU_TITLE, values, null);
                    speedBottomSheet.setListener(new SimpleBottomSheetListener() {

                        class C30081 implements StringResultListener {
                            C30081() {
                            }

                            public void onResult(final String address) {
                                AppService.getActiveActivity().post(new Runnable() {

                                    class C30061 implements Listener {

                                        class C30051 implements Runnable {
                                            C30051() {
                                            }

                                            public void run() {
                                                MyWazeNativeManager mwnm = MyWazeNativeManager.getInstance();
                                                AppService.getMainActivity().getLayoutMgr().showSpeedLimitReport(mwnm.isRandomUserNTV(), mwnm.FoursquareEnabledNTV(), NativeManager.getInstance().isClosureEnabledNTV());
                                            }
                                        }

                                        C30061() {
                                        }

                                        public void onSpeedLimitReport(int speedMMSec) {
                                            if (speedMMSec > 0) {
                                                String description = null;
                                                NativeManager nativeManager = NativeManager.getInstance();
                                                if (speedMMSec > 0) {
                                                    int speed = nativeManager.mathToSpeedUnitNTV(speedMMSec);
                                                    String units = nativeManager.speedUnitNTV();
                                                    description = DisplayStrings.displayStringF(DisplayStrings.DS_SPEED_LIMITS_COMMENT_PD_PS, Integer.valueOf(speed), units);
                                                }
                                                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_SPEEDLIMIT_TAPPED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, description);
                                                RTAlertsNativeManager.getInstance().reportMapIssue(description, 17);
                                            } else if (speedMMSec == 0) {
                                                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_OTHER_TAPPED);
                                                SpeedometerView.this.post(new C30051());
                                            }
                                        }
                                    }

                                    public void run() {
                                        new SpeedLimitReport(context, address, true, SpeedometerView.this.mCurrentMaxSpeedMMSec, new C30061()).show();
                                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_SPEED_MENU_SHOWN);
                                    }
                                });
                            }
                        }

                        public void onComplete(SimpleBottomSheetValue value) {
                            if (value.id == 0) {
                                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_REPORT_TAPPED);
                                NativeManager.getInstance().getPoiAddress(new C30081());
                            } else if (value.id == 1) {
                                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_SETTINGS_TAPPED);
                                context.startActivity(new Intent(context, SettingsSpeedometerActivity.class));
                            }
                            speedBottomSheet.dismiss();
                        }
                    });
                    if (SpeedometerView.this.mCurrentMaxSpeedRoad > 0) {
                        View extraView = SpeedometerView.this.mInflater.inflate(C1283R.layout.speed_bottom_sheed_extra, null);
                        TextView tvCurrentSpeed = (TextView) extraView.findViewById(C1283R.id.speedText);
                        TextView tvLimit = (TextView) extraView.findViewById(C1283R.id.speedLimitWarn);
                        if (SpeedometerView.this.usMode()) {
                            tvLimit.setBackgroundResource(C1283R.drawable.speedlimit_us_notext);
                        }
                        tvCurrentSpeed.setText(SpeedometerView.this.mSpeedText.getText());
                        tvLimit.setText(Integer.toString(SpeedometerView.this.mCurrentMaxSpeedRoad));
                        int size = 13;
                        if (tvLimit.getText().length() <= 2) {
                            size = 20;
                        }
                        tvLimit.setTextSize(1, (float) size);
                        speedBottomSheet.addExtraView(extraView);
                    }
                    speedBottomSheet.show();
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_MENU_SHOWN);
                }
            }
        });
        setOnTouchListener(new C30114());
    }

    public void onLogin() {
        updateConfig();
    }

    private void updateConfig() {
        this.mSpeedometerEnabled = ConfigValues.getBoolValue(110);
        this.mSpeedLimitEnabled = ConfigValues.getBoolValue(100);
        String userPreference = ConfigValues.getStringValue(111);
        if (userPreference.equals("always")) {
            this.mSpeedLimitUserEnabled = true;
            this.mSpeedLimitUserAlways = true;
        } else if (userPreference.equals("yes") || userPreference.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
            this.mSpeedLimitUserEnabled = true;
            this.mSpeedLimitUserAlways = false;
        } else {
            this.mSpeedLimitUserEnabled = false;
            this.mSpeedLimitUserAlways = false;
        }
        this.mSpeedLimitAlert = ConfigValues.getBoolValue(112);
        this.mSpeedLimitDebug = ConfigValues.getBoolValue(115);
        this.mSpeedLimitOffset = ConfigValues.getIntValue(113);
        Logger.d("SpeedometerView: Updated config: mSpeedometerEnabled=" + this.mSpeedometerEnabled + "; mSpeedLimitEnabled=" + this.mSpeedLimitEnabled + "; mSpeedLimitUserEnabled=" + this.mSpeedLimitUserEnabled + "; mSpeedLimitUserAlways=" + this.mSpeedLimitUserAlways + "; mSpeedLimitAlert=" + this.mSpeedLimitAlert + "; mSpeedLimitOffset=" + this.mSpeedLimitOffset + "; mSpeedLimitDebug=" + this.mSpeedLimitDebug);
        if (this.mSpeedLimitUserEnabled) {
            this.mColoredView.setVisibility(0);
        } else {
            this.mColoredView.setVisibility(8);
        }
    }

    public void updateSpeedometer(int speed, String units, int maxSpeedRoad, int maxSpeedMMSec) {
        this.mShouldStop = true;
        this.mCurrentMaxSpeedRoad = maxSpeedRoad;
        this.mCurrentMaxSpeedMMSec = maxSpeedMMSec;
        removeCallbacks(this.colorArcTask);
        if ((speed != -1 || this.mSpeedometerShownOnce) && this.mSpeedometerEnabled) {
            this.mLastSpeed = speed;
            if (speed < 0) {
                this.mSpeedText.setText("--");
            } else {
                this.mSpeedText.setText(String.valueOf(speed));
            }
            if (NativeManager.getInstance().inWalkingModeNTV()) {
                this.mSpeedometer.setVisibility(8);
                return;
            }
            this.mSpeedUnits.setText(units);
            if (speed > 0) {
                this.mSpeedometer.setVisibility(0);
                this.mSpeedometerShownOnce = true;
            }
            if (maxSpeedRoad <= 0 || !((this.mSpeedLimitDebug || this.mSpeedLimitEnabled) && this.mSpeedLimitUserEnabled)) {
                this.mColoredView.setVisibility(8);
                hideLimitWarnings();
                return;
            }
            this.mColoredView.setVisibility(0);
            this.mSpeedWarn.setText(String.valueOf(maxSpeedRoad));
            this.mSpeedWarnOverlay.setText(String.valueOf(maxSpeedRoad));
            int size = 13;
            if (maxSpeedRoad < 100) {
                size = 20;
            }
            this.mSpeedWarn.setTextSize(1, (float) size);
            this.mSpeedWarnOverlay.setTextSize(1, (float) size);
            int maxSpeedWithOffset;
            if (this.mSpeedLimitOffset > 0) {
                maxSpeedWithOffset = maxSpeedRoad + ((this.mSpeedLimitOffset * maxSpeedRoad) / 100);
            } else {
                maxSpeedWithOffset = maxSpeedRoad - this.mSpeedLimitOffset;
            }
            SpeedometerColoredView speedometerColoredView;
            if (speed >= maxSpeedRoad) {
                if (this.mFirstOver) {
                    this.mLastSweep = 1.0f;
                    this.mFirstOver = false;
                    this.mSpeedText.setTextColor(getResources().getColor(C1283R.color.RedSweet));
                }
                if (!this.mWarningShown && speed > maxSpeedWithOffset) {
                    this.mWarningShown = true;
                    showWarning(true);
                    playBeep(maxSpeedRoad);
                }
                SpeedometerColoredView speedometerColoredView2;
                if (speed >= maxSpeedRoad * 2) {
                    speedometerColoredView = this.mColoredView;
                    speedometerColoredView2 = this.mColoredView;
                    speedometerColoredView.setFull(true, 2);
                    this.mColoredView.setColor(this.mMovingColor);
                    this.mColoredView.setSweep(0.0f);
                    this.mColoredView.invalidate();
                    return;
                } else if (speed == maxSpeedRoad) {
                    speedometerColoredView = this.mColoredView;
                    speedometerColoredView2 = this.mColoredView;
                    speedometerColoredView.setFull(true, 1);
                    this.mColoredView.setColor(this.mMovingColor);
                    this.mColoredView.setSweep(0.0f);
                    this.mColoredView.invalidate();
                    return;
                } else {
                    speedometerColoredView = this.mColoredView;
                    speedometerColoredView2 = this.mColoredView;
                    speedometerColoredView.setFull(true, 1);
                    speedometerColoredView = this.mColoredView;
                    this.mMovingColor = 2;
                }
            } else {
                if (!this.mFirstOver) {
                    this.mLastSweep = 359.0f;
                    this.mFirstOver = true;
                    this.mWarningShown = false;
                }
                this.mColoredView.setFull(false, 0);
                speedometerColoredView = this.mColoredView;
                this.mMovingColor = 1;
                this.mSpeedText.setTextColor(getResources().getColor(C1283R.color.White));
                if (this.mSpeedWarnLayout.getVisibility() == 0 || this.mSpeedWarnLayout.getAnimation() != null) {
                    if (speed < maxSpeedRoad && !(this.mSpeedLimitUserEnabled && this.mSpeedLimitUserAlways)) {
                        hideLimitWarnings();
                    }
                } else if (maxSpeedRoad >= 0 && this.mSpeedLimitUserEnabled && this.mSpeedLimitUserAlways) {
                    showWarning(false);
                }
            }
            float maxAngle = (float) (((speed % maxSpeedRoad) * 360) / maxSpeedRoad);
            if (speed % maxSpeedRoad == 0 && speed > 0) {
                maxAngle = 359.0f;
            }
            this.mColorFrom = this.mLastSweep;
            this.mColorTo = maxAngle;
            this.mLastMaxSpeed = maxSpeedRoad;
            if (maxAngle >= this.mLastSweep) {
                this.mColorFaster = true;
            } else {
                this.mColorFaster = false;
            }
            this.mShouldStop = false;
            this.mLastSweep = maxAngle;
            postDelayed(this.colorArcTask, 10);
            return;
        }
        Logger.d("SpeedometerView: Not shown. (speed == " + speed + " && !mSpeedometerShownOnce=" + this.mSpeedometerShownOnce + " ) || !mSpeedometerEnabled=" + this.mSpeedometerEnabled);
        this.mSpeedometer.setVisibility(8);
        this.mColoredView.setVisibility(8);
        hideLimitWarnings();
        this.mSpeedometerShownOnce = false;
    }

    private void hideLimitWarnings() {
        this.mSpeedText.setTextColor(getResources().getColor(C1283R.color.White));
        this.mSpeedWarnLayout.setVisibility(8);
        this.mSpeedWarnLayout.clearAnimation();
        removeCallbacks(this.reduceAnimation);
        this.mFirstOver = true;
        this.mWarningShown = false;
    }

    private void playBeep(int maxSpeed) {
        if (this.mSpeedLimitAlert) {
            long timeDiff = System.currentTimeMillis() - this.mLastBeep;
            if ((this.mLastMaxSpeed != maxSpeed && timeDiff >= 30000) || (this.mLastMaxSpeed == maxSpeed && timeDiff >= 180000)) {
                this.mLocProv.playSpeedometerSound();
                this.mLastBeep = System.currentTimeMillis();
            }
        }
    }

    private void showWarning(boolean animate) {
        boolean wasVisible = this.mSpeedWarnLayout.getVisibility() == 0;
        this.mSpeedWarnLayout.setVisibility(0);
        if (usMode()) {
            this.mSpeedWarn.setBackgroundResource(C1283R.drawable.speedlimit_us_notext);
        } else {
            this.mSpeedWarn.setBackgroundResource(C1283R.drawable.speedlimit_world);
        }
        if (animate) {
            AnimationSet as = new AnimationSet(true);
            AlphaAnimation overlayAa;
            if (wasVisible) {
                as.addAnimation(new ScaleAnimation(1.0f, ANIMATION_SCALE, 1.0f, ANIMATION_SCALE, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                as.addAnimation(new TranslateAnimation(0.0f, (float) ANIMATION_X_OFFSET, 0.0f, (float) ANIMATION_Y_OFFSET));
                as.setInterpolator(new AccelerateInterpolator());
                if (usMode()) {
                    overlayAa = new AlphaAnimation(0.0f, 1.0f);
                    overlayAa.setDuration(300);
                    overlayAa.setFillAfter(true);
                    this.mSpeedWarnOverlay.setVisibility(0);
                    this.mSpeedWarnOverlay.startAnimation(overlayAa);
                }
            } else {
                as.addAnimation(new ScaleAnimation(0.0f, ANIMATION_SCALE, 0.0f, ANIMATION_SCALE, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                as.addAnimation(new TranslateAnimation((float) ANIMATION_X_OFFSET, (float) ANIMATION_X_OFFSET, (float) ANIMATION_Y_OFFSET_APPEARING, (float) ANIMATION_Y_OFFSET));
                as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                as.setInterpolator(new OvershootInterpolator());
                if (usMode()) {
                    overlayAa = new AlphaAnimation(1.0f, 1.0f);
                    overlayAa.setDuration(300);
                    overlayAa.setFillAfter(true);
                    this.mSpeedWarnOverlay.setVisibility(0);
                    this.mSpeedWarnOverlay.startAnimation(overlayAa);
                }
            }
            as.setFillAfter(true);
            as.setDuration(300);
            findViewById(C1283R.id.speedLimitWarnLayout).startAnimation(as);
            postDelayed(this.reduceAnimation, 2300);
        }
    }

    private boolean usMode() {
        return ConfigValues.getStringValue(101).equals("us");
    }
}
