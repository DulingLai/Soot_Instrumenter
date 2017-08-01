package com.waze;

import android.graphics.PointF;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import com.waze.map.CanvasFont;
import com.waze.view.button.ReportMenuButton;

class MenuButtonOnTouchListener implements OnTouchListener {
    public static final int ACTIVE_DISTANCE_DP = 25;
    public static final int[] ANGLES = new int[]{180, 225, 270};
    public static final int ANGULAR_ACTIVE_DISTANCE_DEG = 15;
    public static final int ANGULAR_MAX_DISTANCE_DEG = 20;
    public static final int BUTTONS_ANIM_DURATION_MS = 150;
    public static final int BUTTONS_DISTANCE_DP = 120;
    public static final int FINGER_DISTANCE_DP = 5;
    public static final int HAZARD_IDX = 2;
    public static final float MIN_ALPHA = 0.6f;
    public static final float MIN_SCALE = 0.6f;
    public static final int MOVE_DISTANCE_DP = 70;
    public static final int POLICE_IDX = 1;
    public static final int TRAFFIC_IDX = 0;
    private int mActiveButtonIdx = -1;
    private boolean mAreButtonsAnimating = false;
    private boolean mAreSwipeButtonsVisible = false;
    private PointF[] mButtonLocations = new PointF[3];
    private Interpolator mButtonScaleInterpolator = new DecelerateInterpolator();
    private View[] mButtonViews = new View[3];
    private int mCenterX;
    private int mCenterY;
    private float mDensity;
    private final LayoutManager mLayoutManager;
    private final View mMainReportButton;
    private final View mMainReportButtonShadow;
    Runnable mOnLongPressRunnable;
    private float mTouchX;
    private float mTouchY;

    class C11901 implements Runnable {
        C11901() throws  {
        }

        public void run() throws  {
            MenuButtonOnTouchListener.this.showSwipeButtons();
        }
    }

    class C11912 implements Runnable {
        C11912() throws  {
        }

        public void run() throws  {
            MenuButtonOnTouchListener.this.mAreButtonsAnimating = false;
        }
    }

    class C11923 implements Runnable {
        C11923() throws  {
        }

        public void run() throws  {
            MenuButtonOnTouchListener.this.mAreButtonsAnimating = false;
        }
    }

    public MenuButtonOnTouchListener(LayoutManager $r1, View $r2, View $r3) throws  {
        MenuButtonOnTouchListener menuButtonOnTouchListener = this;
        this.mLayoutManager = $r1;
        this.mMainReportButton = $r2;
        this.mMainReportButtonShadow = $r3;
        this.mOnLongPressRunnable = new C11901();
        this.mDensity = $r2.getResources().getDisplayMetrics().density;
        this.mButtonViews[0] = this.mMainReportButtonShadow.findViewById(C1283R.id.mainReportButtonSwipeTraffic);
        this.mButtonViews[1] = this.mMainReportButtonShadow.findViewById(C1283R.id.mainReportButtonSwipePolice);
        this.mButtonViews[2] = this.mMainReportButtonShadow.findViewById(C1283R.id.mainReportButtonSwipeHazard);
        for (View $r22 : this.mButtonViews) {
            ((ReportMenuButton) $r22).skipAnimation();
        }
        float $f0 = this.mDensity * 120.0f;
        double $d0 = (double) ANGLES[0];
        double d = $d0;
        d = Math.toRadians($d0);
        float $f1 = (float) Math.cos(d);
        float f = $f1 * $f0;
        $f1 = (float) Math.sin(d);
        this.mButtonLocations[0] = new PointF(f, $f1 * $f0);
        $d0 = (double) ANGLES[1];
        d = Math.toRadians($d0);
        $f1 = (float) Math.cos(d);
        f = $f1 * $f0;
        $f1 = (float) Math.sin(d);
        this.mButtonLocations[1] = new PointF(f, $f1 * $f0);
        $d0 = (double) ANGLES[2];
        d = Math.toRadians($d0);
        $f1 = (float) Math.cos(d);
        this.mButtonLocations[2] = new PointF($f1 * $f0, ((float) Math.sin(d)) * $f0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouch(android.view.View r47, android.view.MotionEvent r48) throws  {
        /*
        r46 = this;
        r0 = r48;
        r9 = r0.getAction();
        if (r9 != 0) goto L_0x00b9;
    L_0x0008:
        r0 = r48;
        r10 = r0.getX();
        r0 = r46;
        r0.mTouchX = r10;
        r0 = r48;
        r10 = r0.getY();
        r0 = r46;
        r0.mTouchY = r10;
        r0 = r46;
        r11 = r0.mMainReportButton;
        r9 = r11.getLeft();
        r0 = r46;
        r11 = r0.mMainReportButton;
        r12 = r11.getWidth();
        r12 = r12 / 2;
        r9 = r9 + r12;
        r0 = r46;
        r0.mCenterX = r9;
        r0 = r46;
        r11 = r0.mMainReportButton;
        r9 = r11.getTop();
        r0 = r46;
        r11 = r0.mMainReportButton;
        r12 = r11.getHeight();
        r12 = r12 / 2;
        r9 = r9 + r12;
        r0 = r46;
        r0.mCenterY = r9;
        r13 = new android.view.animation.ScaleAnimation;
        r0 = r46;
        r9 = r0.mCenterX;
        r10 = (float) r9;
        r0 = r46;
        r9 = r0.mCenterY;
        r14 = (float) r9;
        r15 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r16 = 1065772646; // 0x3f866666 float:1.05 double:5.265616507E-315;
        r17 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r18 = 1065772646; // 0x3f866666 float:1.05 double:5.265616507E-315;
        r19 = 0;
        r20 = 0;
        r0 = r13;
        r1 = r15;
        r2 = r16;
        r3 = r17;
        r4 = r18;
        r5 = r19;
        r6 = r10;
        r7 = r20;
        r8 = r14;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r21 = 100;
        r0 = r21;
        r13.setDuration(r0);
        r23 = new android.view.animation.AccelerateDecelerateInterpolator;
        r0 = r23;
        r0.<init>();
        r0 = r23;
        r13.setInterpolator(r0);
        r19 = 1;
        r0 = r19;
        r13.setFillAfter(r0);
        r0 = r46;
        r11 = r0.mMainReportButtonShadow;
        r11.startAnimation(r13);
        r0 = r46;
        r0 = r0.mOnLongPressRunnable;
        r24 = r0;
        r9 = android.view.ViewConfiguration.getLongPressTimeout();
        r0 = (long) r9;
        r25 = r0;
        r0 = r47;
        r1 = r24;
        r2 = r25;
        r0.postDelayed(r1, r2);
        r19 = -1;
        r0 = r19;
        r1 = r46;
        r1.mActiveButtonIdx = r0;
        r19 = 1;
        return r19;
    L_0x00b9:
        r19 = 2;
        r0 = r19;
        if (r9 != r0) goto L_0x013a;
    L_0x00bf:
        r0 = r46;
        r0 = r0.mAreSwipeButtonsVisible;
        r27 = r0;
        if (r27 != 0) goto L_0x0121;
    L_0x00c7:
        r0 = r46;
        r10 = r0.mTouchX;
        r0 = r48;
        r14 = r0.getX();
        r10 = r10 - r14;
        r0 = r46;
        r14 = r0.mTouchY;
        r0 = r48;
        r28 = r0.getY();
        r28 = r14 - r28;
        r0 = r46;
        r14 = r0.mDensity;
        r15 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r14 = r14 * r15;
        r29 = java.lang.Math.abs(r10);
        r30 = (r29 > r14 ? 1 : (r29 == r14 ? 0 : -1));
        if (r30 > 0) goto L_0x010f;
    L_0x00ee:
        r0 = r28;
        r29 = java.lang.Math.abs(r0);
        r30 = (r29 > r14 ? 1 : (r29 == r14 ? 0 : -1));
        if (r30 > 0) goto L_0x010f;
    L_0x00f8:
        r10 = r10 * r10;
        r0 = r28;
        r1 = r28;
        r0 = r0 * r1;
        r28 = r0;
        r10 = r10 + r0;
        r0 = (double) r10;
        r31 = r0;
        r31 = java.lang.Math.sqrt(r0);
        r0 = (double) r14;
        r33 = r0;
        r30 = (r31 > r33 ? 1 : (r31 == r33 ? 0 : -1));
        if (r30 <= 0) goto L_0x0121;
    L_0x010f:
        r0 = r46;
        r0 = r0.mOnLongPressRunnable;
        r24 = r0;
        r0 = r47;
        r1 = r24;
        r0.removeCallbacks(r1);
        r0 = r46;
        r0.showSwipeButtons();
    L_0x0121:
        r0 = r46;
        r0 = r0.mAreSwipeButtonsVisible;
        r27 = r0;
        if (r27 == 0) goto L_0x013a;
    L_0x0129:
        r0 = r48;
        r10 = r0.getX();
        r0 = r48;
        r14 = r0.getY();
        r0 = r46;
        r0.moveButtonsOnTouch(r10, r14);
    L_0x013a:
        r19 = 1;
        r0 = r19;
        if (r9 == r0) goto L_0x014c;
    L_0x0140:
        r19 = 3;
        r0 = r19;
        if (r9 == r0) goto L_0x014c;
    L_0x0146:
        r19 = 4;
        r0 = r19;
        if (r9 != r0) goto L_0x02ba;
    L_0x014c:
        r0 = r46;
        r0 = r0.mOnLongPressRunnable;
        r24 = r0;
        r0 = r47;
        r1 = r24;
        r0.removeCallbacks(r1);
        r0 = r46;
        r0.removeSwipeButtons();
        r0 = r48;
        r25 = r0.getEventTime();
        r0 = r48;
        r35 = r0.getDownTime();
        r0 = r25;
        r2 = r35;
        r0 = r0 - r2;
        r25 = r0;
        r9 = android.view.ViewConfiguration.getLongPressTimeout();
        r0 = (long) r9;
        r35 = r0;
        r30 = (r25 > r35 ? 1 : (r25 == r35 ? 0 : -1));
        if (r30 >= 0) goto L_0x01b7;
    L_0x017c:
        r37 = "REPORT_BUTTON";
        r38 = "TYPE";
        r39 = "NORMAL";
        r0 = r37;
        r1 = r38;
        r2 = r39;
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r0 = r46;
        r0 = r0.mLayoutManager;
        r40 = r0;
        r41 = r0.getTooltipManager();
        r19 = 0;
        r20 = 5;
        r0 = r41;
        r1 = r19;
        r2 = r20;
        r0.closeTooltip(r1, r2);
        r42 = com.waze.AppService.getNativeManager();
        r19 = 1;
        r0 = r42;
        r1 = r19;
        r0.savePoiPosition(r1);
        r0 = r46;
        r0.animateReportButtonAway();
    L_0x01b4:
        r19 = 1;
        return r19;
    L_0x01b7:
        r0 = r46;
        r9 = r0.mActiveButtonIdx;
        if (r9 < 0) goto L_0x026c;
    L_0x01bd:
        r42 = com.waze.AppService.getNativeManager();
        r19 = 0;
        r0 = r42;
        r1 = r19;
        r0.savePoiPosition(r1);
        r0 = r46;
        r0.animateReportButtonAway();
        r43 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r0 = r43;
        r27 = r0.isGuestUserNTV();
        r0 = r43;
        r44 = r0.FoursquareEnabledNTV();
        r42 = com.waze.NativeManager.getInstance();
        r0 = r42;
        r45 = r0.isClosureEnabledNTV();
        r0 = r46;
        r9 = r0.mActiveButtonIdx;
        if (r9 != 0) goto L_0x0216;
    L_0x01ef:
        goto L_0x01f3;
    L_0x01f0:
        goto L_0x01b4;
    L_0x01f3:
        r37 = "REPORT_BUTTON";
        r38 = "TYPE";
        r39 = "QUICK_JAM";
        r0 = r37;
        r1 = r38;
        r2 = r39;
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r0 = r46;
        r0 = r0.mLayoutManager;
        r40 = r0;
        goto L_0x020c;
    L_0x0209:
        goto L_0x01b4;
    L_0x020c:
        r1 = r27;
        r2 = r44;
        r3 = r45;
        r0.showTrafficJamReport(r1, r2, r3);
        goto L_0x01b4;
    L_0x0216:
        r0 = r46;
        r9 = r0.mActiveButtonIdx;
        r19 = 1;
        r0 = r19;
        if (r9 != r0) goto L_0x0243;
    L_0x0220:
        r37 = "REPORT_BUTTON";
        r38 = "TYPE";
        r39 = "QUICK_POLICE";
        r0 = r37;
        r1 = r38;
        r2 = r39;
        com.waze.analytics.Analytics.log(r0, r1, r2);
        goto L_0x0233;
    L_0x0230:
        goto L_0x01b4;
    L_0x0233:
        r0 = r46;
        r0 = r0.mLayoutManager;
        r40 = r0;
        r1 = r27;
        r2 = r44;
        r3 = r45;
        r0.showPoliceReport(r1, r2, r3);
        goto L_0x01f0;
    L_0x0243:
        r0 = r46;
        r9 = r0.mActiveButtonIdx;
        r19 = 2;
        r0 = r19;
        if (r9 != r0) goto L_0x01b4;
    L_0x024d:
        r37 = "REPORT_BUTTON";
        r38 = "TYPE";
        r39 = "QUICK_HAZARD";
        r0 = r37;
        r1 = r38;
        r2 = r39;
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r0 = r46;
        r0 = r0.mLayoutManager;
        r40 = r0;
        r1 = r27;
        r2 = r44;
        r3 = r45;
        r0.showHazardReport(r1, r2, r3);
        goto L_0x0209;
    L_0x026c:
        r13 = new android.view.animation.ScaleAnimation;
        r0 = r46;
        r9 = r0.mCenterX;
        goto L_0x0276;
    L_0x0273:
        goto L_0x0230;
    L_0x0276:
        r10 = (float) r9;
        r0 = r46;
        r9 = r0.mCenterY;
        r14 = (float) r9;
        r15 = 1065772646; // 0x3f866666 float:1.05 double:5.265616507E-315;
        r16 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r17 = 1065772646; // 0x3f866666 float:1.05 double:5.265616507E-315;
        r18 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r19 = 0;
        r20 = 0;
        r0 = r13;
        r1 = r15;
        r2 = r16;
        r3 = r17;
        r4 = r18;
        r5 = r19;
        r6 = r10;
        r7 = r20;
        r8 = r14;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r21 = 100;
        r0 = r21;
        r13.setDuration(r0);
        r23 = new android.view.animation.AccelerateDecelerateInterpolator;
        r0 = r23;
        r0.<init>();
        r0 = r23;
        r13.setInterpolator(r0);
        r0 = r46;
        r0 = r0.mMainReportButtonShadow;
        r47 = r0;
        r0.startAnimation(r13);
        goto L_0x0273;
    L_0x02ba:
        r19 = 0;
        return r19;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.MenuButtonOnTouchListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    private void animateReportButtonAway() throws  {
    }

    private void moveButtonsOnTouch(float $f1, float $f2) throws  {
        float $f0;
        $f1 -= ((float) this.mCenterX) - (this.mDensity * 10.0f);
        $f2 -= (float) this.mCenterY;
        if (Math.sqrt((double) (($f1 * $f1) + ($f2 * $f2))) < ((double) (this.mDensity * 120.0f))) {
            $f0 = 1.0f;
        } else {
            $f0 = 8.0f;
        }
        moveButtonOnTouch($f1, $f2, 0, $f0);
        moveButtonOnTouch($f1, $f2, 1, $f0);
        moveButtonOnTouch($f1, $f2, 2, $f0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void moveButtonOnTouch(float r38, float r39, int r40, float r41) throws  {
        /*
        r37 = this;
        r0 = r37;
        r1 = r38;
        r2 = r39;
        r3 = r40;
        r9 = r0.getDistance(r1, r2, r3);
        r11 = (float) r9;
        r0 = r37;
        r1 = r38;
        r2 = r39;
        r3 = r40;
        r9 = r0.getAngleDiff(r1, r2, r3);
        r12 = (float) r9;
        r14 = 1097859072; // 0x41700000 float:15.0 double:5.424144515E-315;
        r13 = r12 - r14;
        r14 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r13 = r13 / r14;
        r14 = 0;
        r15 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1));
        if (r15 >= 0) goto L_0x0029;
    L_0x0028:
        r13 = 0;
    L_0x0029:
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r15 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1));
        if (r15 <= 0) goto L_0x0033;
    L_0x0030:
        r13 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
    L_0x0033:
        r0 = r37;
        r0 = r0.mButtonViews;
        r16 = r0;
        r17 = r16[r40];
        r14 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r15 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r15 < 0) goto L_0x0045;
    L_0x0042:
        r41 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
    L_0x0045:
        r0 = r37;
        r0 = r0.mDensity;
        r18 = r0;
        r1 = r41;
        r0 = r0 * r1;
        r18 = r0;
        r14 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r0 = r18;
        r0 = r0 * r14;
        r18 = r0;
        r0 = r37;
        r0 = r0.mDensity;
        r19 = r0;
        r1 = r41;
        r0 = r0 * r1;
        r19 = r0;
        r14 = 1116471296; // 0x428c0000 float:70.0 double:5.51610112E-315;
        r0 = r19;
        r0 = r0 * r14;
        r19 = r0;
        r0 = r37;
        r0 = r0.mDensity;
        r20 = r0;
        r41 = r20 * r41;
        r14 = 1103626240; // 0x41c80000 float:25.0 double:5.45263811E-315;
        r0 = r41;
        r0 = r0 * r14;
        r41 = r0;
        r15 = (r11 > r41 ? 1 : (r11 == r41 ? 0 : -1));
        if (r15 >= 0) goto L_0x0121;
    L_0x007f:
        r14 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r15 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r15 >= 0) goto L_0x0121;
    L_0x0086:
        r15 = (r11 > r18 ? 1 : (r11 == r18 ? 0 : -1));
        if (r15 >= 0) goto L_0x0101;
    L_0x008a:
        r41 = r13;
        if (r40 != 0) goto L_0x008e;
    L_0x008e:
        r0 = r37;
        r0 = r0.mButtonScaleInterpolator;
        r21 = r0;
        r1 = r41;
        r41 = r0.getInterpolation(r1);
        r0 = r37;
        r0 = r0.mButtonLocations;
        r22 = r0;
        r23 = r22[r40];
        r0 = r23;
        r13 = r0.x;
        r12 = r13 - r38;
        r0 = r23;
        r13 = r0.y;
        r0 = r39;
        r13 = r13 - r0;
        r23 = new android.graphics.PointF;
        r0 = r41;
        r12 = r12 * r0;
        r38 = r12 + r38;
        r41 = r13 * r41;
        r39 = r41 + r39;
        r0 = r23;
        r1 = r38;
        r2 = r39;
        r0.<init>(r1, r2);
        r24 = new android.view.animation.TranslateAnimation;
        r0 = r23;
        r0 = r0.x;
        r38 = r0;
        r0 = r23;
        r0 = r0.x;
        r39 = r0;
        goto L_0x00d5;
    L_0x00d2:
        goto L_0x008e;
    L_0x00d5:
        r0 = r23;
        r0 = r0.y;
        r41 = r0;
        r0 = r23;
        r13 = r0.y;
        r0 = r24;
        r1 = r38;
        r2 = r39;
        r3 = r41;
        r0.<init>(r1, r2, r3, r13);
        r25 = 1;
        r0 = r24;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r17;
        r1 = r24;
        r0.startAnimation(r1);
        r0 = r40;
        r1 = r37;
        r1.mActiveButtonIdx = r0;
        return;
    L_0x0101:
        r41 = r11 - r18;
        r12 = r19 - r18;
        r0 = r41;
        r0 = r0 / r12;
        r41 = r0;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r41 = r14 - r41;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r13 = r14 - r13;
        r0 = r41;
        r0 = r0 * r13;
        r41 = r0;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r41 = r14 - r41;
        if (r40 != 0) goto L_0x008e;
    L_0x0120:
        goto L_0x00d2;
    L_0x0121:
        r15 = (r11 > r19 ? 1 : (r11 == r19 ? 0 : -1));
        if (r15 >= 0) goto L_0x0233;
    L_0x0125:
        r12 = r11 - r41;
        r41 = r19 - r41;
        r41 = r12 / r41;
        r14 = 1053609164; // 0x3ecccccc float:0.39999998 double:5.20552092E-315;
        r12 = r14 * r41;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r12 = r14 - r12;
        r14 = 1053609164; // 0x3ecccccc float:0.39999998 double:5.20552092E-315;
        r41 = r14 * r41;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r41 = r14 - r41;
        r0 = r18;
        r11 = r11 - r0;
        r18 = r19 - r18;
        r0 = r18;
        r11 = r11 / r0;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r11 = r14 - r11;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r13 = r14 - r13;
        r13 = r11 * r13;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r13 = r14 - r13;
        if (r40 != 0) goto L_0x015a;
    L_0x015a:
        r0 = r37;
        r0 = r0.mButtonScaleInterpolator;
        r21 = r0;
        r13 = r0.getInterpolation(r13);
        r0 = r37;
        r0 = r0.mButtonLocations;
        r22 = r0;
        r23 = r22[r40];
        r0 = r23;
        r11 = r0.x;
        r18 = r11 - r38;
        r0 = r23;
        r11 = r0.y;
        r0 = r39;
        r11 = r11 - r0;
        r23 = new android.graphics.PointF;
        r0 = r18;
        r0 = r0 * r13;
        r18 = r0;
        r38 = r18 + r38;
        r13 = r11 * r13;
        r39 = r13 + r39;
        r0 = r23;
        r1 = r38;
        r2 = r39;
        r0.<init>(r1, r2);
        r26 = new android.view.animation.AnimationSet;
        r25 = 1;
        r0 = r26;
        r1 = r25;
        r0.<init>(r1);
        r27 = new android.view.animation.ScaleAnimation;
        r25 = 1;
        r14 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r28 = 1;
        r29 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r27;
        r1 = r12;
        r2 = r12;
        r3 = r12;
        r4 = r12;
        r5 = r25;
        r6 = r14;
        r7 = r28;
        r8 = r29;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r25 = 1;
        r0 = r27;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r26;
        r1 = r27;
        r0.addAnimation(r1);
        r24 = new android.view.animation.TranslateAnimation;
        r0 = r23;
        r0 = r0.x;
        r38 = r0;
        r0 = r23;
        r0 = r0.x;
        r39 = r0;
        r0 = r23;
        r13 = r0.y;
        r0 = r23;
        r12 = r0.y;
        r0 = r24;
        r1 = r38;
        r2 = r39;
        r0.<init>(r1, r2, r13, r12);
        r25 = 1;
        r0 = r24;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r26;
        r1 = r24;
        r0.addAnimation(r1);
        r30 = new android.view.animation.AlphaAnimation;
        r0 = r30;
        r1 = r41;
        r2 = r41;
        r0.<init>(r1, r2);
        r25 = 1;
        r0 = r30;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r26;
        r1 = r30;
        r0.addAnimation(r1);
        r25 = 1;
        r0 = r26;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r17;
        r1 = r26;
        r0.startAnimation(r1);
        r0 = r37;
        r0 = r0.mActiveButtonIdx;
        r31 = r0;
        r1 = r40;
        if (r0 != r1) goto L_0x02ff;
    L_0x022a:
        r25 = -1;
        r0 = r25;
        r1 = r37;
        r1.mActiveButtonIdx = r0;
        return;
    L_0x0233:
        r0 = r37;
        r0 = r0.mAreButtonsAnimating;
        r32 = r0;
        if (r32 != 0) goto L_0x0300;
    L_0x023b:
        r26 = new android.view.animation.AnimationSet;
        r25 = 1;
        r0 = r26;
        r1 = r25;
        r0.<init>(r1);
        r27 = new android.view.animation.ScaleAnimation;
        r14 = 1058642330; // 0x3f19999a float:0.6 double:5.230388065E-315;
        r29 = 1058642330; // 0x3f19999a float:0.6 double:5.230388065E-315;
        r33 = 1058642330; // 0x3f19999a float:0.6 double:5.230388065E-315;
        r34 = 1058642330; // 0x3f19999a float:0.6 double:5.230388065E-315;
        r25 = 1;
        r35 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r28 = 1;
        r36 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r27;
        r1 = r14;
        r2 = r29;
        r3 = r33;
        r4 = r34;
        r5 = r25;
        r6 = r35;
        r7 = r28;
        r8 = r36;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r25 = 1;
        r0 = r27;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r26;
        r1 = r27;
        r0.addAnimation(r1);
        r0 = r37;
        r0 = r0.mButtonLocations;
        r22 = r0;
        r23 = r22[r40];
        r24 = new android.view.animation.TranslateAnimation;
        r0 = r23;
        r0 = r0.x;
        r38 = r0;
        r0 = r23;
        r0 = r0.x;
        r39 = r0;
        r0 = r23;
        r0 = r0.y;
        r41 = r0;
        r0 = r23;
        r13 = r0.y;
        r0 = r24;
        r1 = r38;
        r2 = r39;
        r3 = r41;
        r0.<init>(r1, r2, r3, r13);
        r25 = 1;
        r0 = r24;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r26;
        r1 = r24;
        r0.addAnimation(r1);
        r30 = new android.view.animation.AlphaAnimation;
        r14 = 1058642330; // 0x3f19999a float:0.6 double:5.230388065E-315;
        r29 = 1058642330; // 0x3f19999a float:0.6 double:5.230388065E-315;
        r0 = r30;
        r1 = r29;
        r0.<init>(r14, r1);
        r25 = 1;
        r0 = r30;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r26;
        r1 = r30;
        r0.addAnimation(r1);
        r25 = 1;
        r0 = r26;
        r1 = r25;
        r0.setFillAfter(r1);
        r0 = r17;
        r1 = r26;
        r0.startAnimation(r1);
        r0 = r37;
        r0 = r0.mActiveButtonIdx;
        r31 = r0;
        r1 = r40;
        if (r0 != r1) goto L_0x0301;
    L_0x02f6:
        r25 = -1;
        r0 = r25;
        r1 = r37;
        r1.mActiveButtonIdx = r0;
        return;
    L_0x02ff:
        return;
    L_0x0300:
        return;
    L_0x0301:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.MenuButtonOnTouchListener.moveButtonOnTouch(float, float, int, float):void");
    }

    private double getDistance(float $f0, float $f1, int $i0) throws  {
        double $d0 = (double) (this.mButtonLocations[$i0].x - $f0);
        double $d1 = (double) (this.mButtonLocations[$i0].y - $f1);
        return Math.sqrt(($d0 * $d0) + ($d1 * $d1));
    }

    private double getAngleDiff(float $f0, float $f1, int $i0) throws  {
        double $d0 = Math.toDegrees(Math.atan((double) ($f1 / $f0)));
        if ($f0 < 0.0f) {
            $d0 += 180.0d;
        } else {
            $d0 += 360.0d;
        }
        return Math.abs($d0 - ((double) ANGLES[$i0]));
    }

    private void showSwipeButtons() throws  {
        if (!this.mAreSwipeButtonsVisible) {
            this.mAreSwipeButtonsVisible = true;
            this.mAreButtonsAnimating = true;
            this.mMainReportButton.postDelayed(new C11912(), 150);
            int $f1 = this.mCenterX;
            int $i0 = $f1;
            float $f12 = (float) $f1;
            $f1 = this.mCenterY;
            $i0 = $f1;
            ScaleAnimation scaleAnimation = new ScaleAnimation(LayoutManager.REPORT_SCALE_ON_PRESS, 1.0f, LayoutManager.REPORT_SCALE_ON_PRESS, 1.0f, 0, $f12, 0, (float) $f1);
            scaleAnimation.setDuration(100);
            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            this.mMainReportButtonShadow.startAnimation(scaleAnimation);
            animateButtonReveal(0);
            animateButtonReveal(1);
            animateButtonReveal(2);
        }
    }

    private void animateButtonReveal(int $i0) throws  {
        View $r4 = this.mButtonViews[$i0];
        $r4.setVisibility(0);
        AnimationSet $r3 = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 0.6f, 0.6f, 0.6f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        scaleAnimation.setDuration(150);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        $r3.addAnimation(scaleAnimation);
        PointF $r5 = this.mButtonLocations[$i0];
        Animation translateAnimation = new TranslateAnimation(0.0f, $r5.x, 0.0f, $r5.y);
        translateAnimation.setDuration(150);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        $r3.addAnimation(translateAnimation);
        translateAnimation = new AlphaAnimation(0.6f, 0.6f);
        translateAnimation.setDuration(150);
        translateAnimation.setFillAfter(true);
        $r3.addAnimation(translateAnimation);
        translateAnimation = new RotateAnimation(-30.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        translateAnimation.setDuration(150);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setFillAfter(true);
        $r3.addAnimation(translateAnimation);
        $r3.setFillAfter(true);
        $r4.startAnimation($r3);
    }

    private void removeSwipeButtons() throws  {
        if (this.mAreSwipeButtonsVisible) {
            this.mAreSwipeButtonsVisible = false;
            this.mAreButtonsAnimating = true;
            this.mMainReportButton.postDelayed(new C11923(), 150);
            animateButtonBack(0);
            animateButtonBack(2);
            animateButtonBack(1);
        }
    }

    private void animateButtonBack(int $i0) throws  {
        final View $r4 = this.mButtonViews[$i0];
        AnimationSet $r3 = new AnimationSet(false);
        $r3.addAnimation(new ScaleAnimation(0.6f, 0.6f, 0.6f, 0.6f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        PointF $r5 = this.mButtonLocations[$i0];
        Animation translateAnimation = new TranslateAnimation($r5.x, 0.0f, $r5.y, 0.0f);
        translateAnimation.setDuration(150);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        $r3.addAnimation(translateAnimation);
        $r3.addAnimation(new AlphaAnimation(0.6f, 0.6f));
        $r4.startAnimation($r3);
        $r3.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation animation) throws  {
                $r4.setVisibility(8);
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }
        });
    }
}
