package com.waze.reports;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.map.CanvasFont;
import com.waze.map.NativeCanvasRenderer;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.roadrecording.RoadRecordingPopUp;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.button.ReportMenuButton;
import com.waze.view.drawables.GrowingCircleDrawable;
import java.util.ArrayList;
import java.util.Iterator;

public class ReportMenu extends Fragment {
    private static final int ANIMATION_DURATION = 300;
    private static final int APPEAR_FADE_DURATION = 150;
    public static final int BG_COLOR_ACCIDENT = -4078653;
    public static final int BG_COLOR_CAMERA = -7614027;
    public static final int BG_COLOR_CHAT = -4598636;
    public static final int BG_COLOR_CLOSURE = -28305;
    public static final int BG_COLOR_DEBUG = -15775;
    public static final int BG_COLOR_GAS_PRICES = -9719662;
    public static final int BG_COLOR_HAZARD = -15775;
    public static final int BG_COLOR_MAP_PROBLEM = -1052689;
    public static final int BG_COLOR_PLACES = -4152624;
    public static final int BG_COLOR_POLICE = -7943985;
    public static final int BG_COLOR_TRAFFIC = -1411464;
    private static final int BUTTON_FOCUSED_Y_LOCATION_PERCENT = 15;
    private static final int BUTTON_FOCUS_ANIM_DURATION = 400;
    private static final int CLOSE_ANIM_DURATION = 250;
    private static final int CLOSE_LATENCY_GAP = 20;
    private static final int DISAPPEAR_FADE_DURATION = 100;
    private static final int ELEMENT_HEIGHT_DP = 118;
    private static final int ELEMENT_MIN_MARGINS_DP = 13;
    private static final int ELEMENT_WIDTH_DP = 100;
    private static final int EST_NUM_OF_BUTTONS = 16;
    public static final int GAS_PRICE_REPORT = 4000;
    private static final long GPS_FREASH = 30000;
    private static final int OPEN_ANIM_DURATION = 300;
    private static final int OPEN_LATENCY_GAP = 20;
    public static final int RESULT_CLOSE_REPORT_MENU = 4001;
    private static final int SCATTER_DURATION = 300;
    private static final int SCATTER_TO_DEGREES = 150;
    private boolean disableAnimation = false;
    protected boolean inMenu = false;
    private boolean isClosureEnabled;
    protected boolean isFirstImage = true;
    private boolean isFoursquareEnabled;
    private boolean isRandomUser;
    public volatile boolean isVisible = false;
    private LayoutManager layoutManager;
    private View mBackgroundView;
    private ReportMenuButton mClickedRmb = null;
    private ArrayList<ReportMenuButton> mClipViews = new ArrayList(20);
    private View mCloseButton;
    private Context mCtx;
    private float mDensity;
    private GrowingCircleDrawable mGrowingCircleDrawable;
    private boolean mIsVertical;
    private int mMenuMarginLeft = 0;
    private int mMenuMarginTop = 0;
    private int mRealItemHeight = 0;
    private int mRealItemWidth = 0;
    private RoadRecordingPopUp mRoadRecordingPopUp;
    private ArrayList<TextView> mTextViews = new ArrayList(20);
    private TextView mTvTitle;
    private ViewGroup mainLayout;
    private ViewGroup mainLayoutToAnimate;
    private MyWazeNativeManager myWazeNativeManager;
    private NativeManager nativeManager;
    private View f85r;
    private ReportForm reportForm;

    private void initLayout(android.view.LayoutInflater r70) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:60:0x0460
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r69 = this;
        r0 = r69;
        r4 = r0.f85r;
        r5 = 2131691675; // 0x7f0f089b float:1.9012429E38 double:1.053195624E-314;
        r4 = r4.findViewById(r5);
        r0 = r69;
        r0.mBackgroundView = r4;
        r6 = new com.waze.view.drawables.GrowingCircleDrawable;
        r6.<init>();
        r0 = r69;
        r0.mGrowingCircleDrawable = r6;
        r7 = android.os.Build.VERSION.SDK_INT;
        r5 = 16;
        if (r7 < r5) goto L_0x00dd;
    L_0x001e:
        r0 = r69;
        r4 = r0.mBackgroundView;
        r0 = r69;
        r8 = r0.mGrowingCircleDrawable;
        r10 = r8;
        r10 = (android.graphics.drawable.Drawable) r10;
        r9 = r10;
        r4.setBackground(r9);
    L_0x002d:
        r0 = r69;
        r4 = r0.f85r;
        r5 = 2131691676; // 0x7f0f089c float:1.901243E38 double:1.0531956246E-314;
        r4 = r4.findViewById(r5);
        r12 = r4;
        r12 = (android.view.ViewGroup) r12;
        r11 = r12;
        r11.removeAllViews();
        r0 = r69;
        r13 = r0.mCtx;
        r14 = r13.getResources();
        r15 = r14.getConfiguration();
        r7 = r15.orientation;
        r5 = 1;
        if (r7 != r5) goto L_0x00ef;
    L_0x0050:
        r16 = 1;
    L_0x0052:
        r0 = r16;
        r1 = r69;
        r1.mIsVertical = r0;
        r0 = r69;
        r0 = r0.layoutManager;
        r17 = r0;
        r4 = r0.getReportMenuContainer();
        r18 = r4.getMeasuredWidth();
        r7 = r4.getMeasuredHeight();
        r0 = r69;
        r0 = r0.mDensity;
        r19 = r0;
        r20 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r19 = r20 * r19;
        r0 = r19;
        r0 = (int) r0;
        r21 = r0;
        r0 = r69;
        r0 = r0.mDensity;
        r19 = r0;
        r20 = 1122762752; // 0x42ec0000 float:118.0 double:5.54718504E-315;
        r19 = r20 * r19;
        r0 = r19;
        r0 = (int) r0;
        r22 = r0;
        goto L_0x008e;
    L_0x008b:
        goto L_0x002d;
    L_0x008e:
        r0 = r69;
        r23 = r0.getStatusBarHeight();
        r23 = r7 - r23;
        r0 = r69;
        r0 = r0.mIsVertical;
        r16 = r0;
        if (r16 == 0) goto L_0x0467;
    L_0x009e:
        goto L_0x00a2;
    L_0x009f:
        goto L_0x0052;
    L_0x00a2:
        r5 = 2130903364; // 0x7f030144 float:1.7413544E38 double:1.052806147E-314;
        r0 = r70;
        r4 = r0.inflate(r5, r11);
        r7 = r18 / r21;
        r16 = 0;
        r5 = 4;
        if (r7 <= r5) goto L_0x00b6;
    L_0x00b2:
        r7 = r7 + -2;
        r16 = 1;
    L_0x00b6:
        r24 = r7 * r21;
        r24 = r18 - r24;
    L_0x00ba:
        r5 = 1;
        if (r7 <= r5) goto L_0x00f2;
    L_0x00bd:
        r0 = r24;
        r0 = (float) r0;
        r19 = r0;
        r0 = r69;
        r0 = r0.mDensity;
        r25 = r0;
        r20 = 1095761920; // 0x41500000 float:13.0 double:5.413783207E-315;
        r0 = r25;
        r1 = r20;
        r0 = r0 * r1;
        r25 = r0;
        r26 = (r19 > r25 ? 1 : (r19 == r25 ? 0 : -1));
        if (r26 >= 0) goto L_0x00f2;
    L_0x00d6:
        r7 = r7 + -1;
        r24 = r7 * r21;
        r24 = r18 - r24;
        goto L_0x00ba;
    L_0x00dd:
        r0 = r69;
        r4 = r0.mBackgroundView;
        r0 = r69;
        r8 = r0.mGrowingCircleDrawable;
        r27 = r8;
        r27 = (android.graphics.drawable.Drawable) r27;
        r9 = r27;
        r4.setBackgroundDrawable(r9);
        goto L_0x008b;
    L_0x00ef:
        r16 = 0;
        goto L_0x009f;
    L_0x00f2:
        if (r7 > 0) goto L_0x00fc;
    L_0x00f4:
        r28 = "ReportMenu: initLayout: num lines = 0; setting to 1";
        r0 = r28;
        com.waze.Logger.m38e(r0);
        r7 = 1;
    L_0x00fc:
        if (r16 == 0) goto L_0x0464;
    L_0x00fe:
        r26 = 1;
    L_0x0100:
        r21 = r26 + r7;
        r21 = r24 / r21;
        r0 = r21;
        r1 = r69;
        r1.mMenuMarginLeft = r0;
        r0 = r69;
        r0 = r0.mMenuMarginLeft;
        r21 = r0;
        r21 = r21 * 2;
        r0 = r18;
        r1 = r21;
        r0 = r0 - r1;
        r18 = r0;
        r0 = r0 / r7;
        r18 = r0;
        r1 = r69;
        r1.mRealItemWidth = r0;
        r0 = r22;
        r1 = r69;
        r1.mRealItemHeight = r0;
        r5 = 16;
        r18 = r5 / r7;
        r0 = r18;
        r1 = r22;
        r0 = r0 * r1;
        r18 = r0;
        r0 = r23;
        r1 = r18;
        if (r0 <= r1) goto L_0x0143;
    L_0x0137:
        r5 = 16;
        r18 = r5 / r7;
        r18 = r23 / r18;
        r0 = r18;
        r1 = r69;
        r1.mRealItemHeight = r0;
    L_0x0143:
        r5 = 2131691681; // 0x7f0f08a1 float:1.901244E38 double:1.053195627E-314;
        r29 = r4.findViewById(r5);
        r31 = r29;
        r31 = (android.widget.RelativeLayout) r31;
        r30 = r31;
        r0 = r30;
        r0.removeAllViews();
        r0 = r69;
        r0 = r0.mClipViews;
        r32 = r0;
        r0.clear();
        r0 = r69;
        r0 = r0.mTextViews;
        r32 = r0;
        r0.clear();
        r33 = new com.waze.reports.ReportMenu$ReportMenuOrganizer;
        r34 = r33;
        r0 = r69;
        r0 = r0.mIsVertical;
        r16 = r0;
        r0 = r33;
        r1 = r69;
        r2 = r30;
        r3 = r16;
        r0.<init>(r1, r2, r7, r3);
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3720; // 0xe88 float:5.213E-42 double:1.838E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r37 = new com.waze.reports.ReportMenu$3;
        r0 = r37;
        r1 = r69;
        r0.<init>(r1);
        r39 = r37;
        r39 = (android.view.View.OnClickListener) r39;
        r38 = r39;
        r5 = 2130838316; // 0x7f02032c float:1.728161E38 double:1.052774009E-314;
        r40 = -1411464; // 0xffffffffffea7678 float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
        r35 = com.waze.NativeManager.getInstance();
        r0 = r35;
        r7 = r0.isEnforcementPoliceEnabledNTV();
        if (r7 == 0) goto L_0x052c;
    L_0x01b6:
        r16 = 1;
    L_0x01b8:
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3721; // 0xe89 float:5.214E-42 double:1.8384E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        if (r16 == 0) goto L_0x0533;
    L_0x01c8:
        r7 = 2130838308; // 0x7f020324 float:1.7281595E38 double:1.052774005E-314;
    L_0x01cb:
        r41 = new com.waze.reports.ReportMenu$4;
        r0 = r41;
        r1 = r69;
        r0.<init>(r1);
        r42 = r41;
        r42 = (android.view.View.OnClickListener) r42;
        r38 = r42;
        r5 = -7943985; // 0xffffffffff86c8cf float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r38;
        r0.addReportButton(r1, r7, r5, r2);
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3722; // 0xe8a float:5.216E-42 double:1.839E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r43 = new com.waze.reports.ReportMenu$5;
        r0 = r43;
        r1 = r69;
        r0.<init>(r1);
        r44 = r43;
        r44 = (android.view.View.OnClickListener) r44;
        r38 = r44;
        r5 = 2130838281; // 0x7f020309 float:1.728154E38 double:1.0527739915E-314;
        r40 = -4078653; // 0xffffffffffc1c3c3 float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3723; // 0xe8b float:5.217E-42 double:1.8394E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r45 = new com.waze.reports.ReportMenu$6;
        r0 = r45;
        r1 = r69;
        r0.<init>(r1);
        r46 = r45;
        r46 = (android.view.View.OnClickListener) r46;
        r38 = r46;
        r5 = 2130838293; // 0x7f020315 float:1.7281564E38 double:1.0527739974E-314;
        r40 = -15775; // 0xffffffffffffc261 float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r16 = r0.isGasUpdateable();
        if (r16 == 0) goto L_0x0270;
    L_0x024d:
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3724; // 0xe8c float:5.218E-42 double:1.84E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r38 = getGasPriceOCL();
        r5 = 2130838292; // 0x7f020314 float:1.7281562E38 double:1.052773997E-314;
        r40 = -9719662; // 0xffffffffff6bb092 float:-3.1328538E38 double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
    L_0x0270:
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3719; // 0xe87 float:5.211E-42 double:1.8374E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r47 = new com.waze.reports.ReportMenu$7;
        r0 = r47;
        r1 = r69;
        r0.<init>(r1);
        r48 = r47;
        r48 = (android.view.View.OnClickListener) r48;
        r38 = r48;
        r5 = 2130838286; // 0x7f02030e float:1.728155E38 double:1.052773994E-314;
        r40 = -4598636; // 0xffffffffffb9d494 float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3725; // 0xe8d float:5.22E-42 double:1.8404E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r49 = new com.waze.reports.ReportMenu$8;
        r0 = r49;
        r1 = r69;
        r0.<init>(r1);
        r50 = r49;
        r50 = (android.view.View.OnClickListener) r50;
        r38 = r50;
        r5 = 2130838306; // 0x7f020322 float:1.728159E38 double:1.052774004E-314;
        r40 = -1052689; // 0xffffffffffefefef float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3726; // 0xe8e float:5.221E-42 double:1.841E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r51 = new com.waze.reports.ReportMenu$9;
        r0 = r51;
        r1 = r69;
        r0.<init>(r1);
        r52 = r51;
        r52 = (android.view.View.OnClickListener) r52;
        r38 = r52;
        r5 = 2130838307; // 0x7f020323 float:1.7281593E38 double:1.0527740043E-314;
        r40 = -4152624; // 0xffffffffffc0a2d0 float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
        r35 = com.waze.NativeManager.getInstance();
        r0 = r35;
        r16 = r0.isEnforcementAlertsEnabledNTV();
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3727; // 0xe8f float:5.223E-42 double:1.8414E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        if (r16 == 0) goto L_0x053b;
    L_0x0314:
        r7 = 2130838313; // 0x7f020329 float:1.7281605E38 double:1.0527740073E-314;
    L_0x0317:
        r53 = new com.waze.reports.ReportMenu$10;
        r0 = r53;
        r1 = r69;
        r0.<init>(r1);
        r54 = r53;
        r54 = (android.view.View.OnClickListener) r54;
        r38 = r54;
        r5 = -7614027; // 0xffffffffff8bd1b5 float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r38;
        r0.addReportButton(r1, r7, r5, r2);
        r0 = r69;
        r0 = r0.isClosureEnabled;
        r16 = r0;
        if (r16 == 0) goto L_0x0367;
    L_0x033a:
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3728; // 0xe90 float:5.224E-42 double:1.842E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r55 = new com.waze.reports.ReportMenu$11;
        r0 = r55;
        r1 = r69;
        r0.<init>(r1);
        r56 = r55;
        r56 = (android.view.View.OnClickListener) r56;
        r38 = r56;
        r5 = 2130838287; // 0x7f02030f float:1.7281552E38 double:1.0527739944E-314;
        r40 = -28305; // 0xffffffffffff916f float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
    L_0x0367:
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r16 = r0.isDebug();
        if (r16 == 0) goto L_0x03a0;
    L_0x0373:
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 3731; // 0xe93 float:5.228E-42 double:1.8434E-320;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r57 = new com.waze.reports.ReportMenu$12;
        r0 = r57;
        r1 = r69;
        r0.<init>(r1);
        r58 = r57;
        r58 = (android.view.View.OnClickListener) r58;
        r38 = r58;
        r5 = 2130838291; // 0x7f020313 float:1.728156E38 double:1.0527739964E-314;
        r40 = -15775; // 0xffffffffffffc261 float:NaN double:NaN;
        r0 = r34;
        r1 = r36;
        r2 = r40;
        r3 = r38;
        r0.addReportButton(r1, r5, r2, r3);
    L_0x03a0:
        r0 = r34;
        r0.reIndentLastLine();
        r5 = 2131691674; // 0x7f0f089a float:1.9012427E38 double:1.0531956236E-314;
        r29 = r4.findViewById(r5);
        r59 = r29;
        r59 = (android.view.ViewGroup) r59;
        r11 = r59;
        r0 = r69;
        r0.mainLayoutToAnimate = r11;
        r60 = new com.waze.reports.ReportMenu$13;
        r61 = r60;
        r0 = r60;
        r1 = r69;
        r0.<init>(r1);
        r62 = r61;
        r62 = (android.view.View.OnClickListener) r62;
        r38 = r62;
        r0 = r30;
        r1 = r38;
        r0.setOnClickListener(r1);
        r5 = 2131691682; // 0x7f0f08a2 float:1.9012443E38 double:1.0531956276E-314;
        r29 = r4.findViewById(r5);
        r0 = r29;
        r1 = r69;
        r1.mCloseButton = r0;
        r0 = r69;
        r0 = r0.mCloseButton;
        r29 = r0;
        if (r29 == 0) goto L_0x0424;
    L_0x03e3:
        r0 = r69;
        r0 = r0.mCloseButton;
        r29 = r0;
        r5 = 8;
        r0 = r29;
        r0.setVisibility(r5);
        r0 = r69;
        r0 = r0.mCloseButton;
        r29 = r0;
        r64 = r29;
        r64 = (com.waze.view.text.WazeTextView) r64;
        r63 = r64;
        r0 = r69;
        r0 = r0.nativeManager;
        r35 = r0;
        r5 = 354; // 0x162 float:4.96E-43 double:1.75E-321;
        r0 = r35;
        r36 = r0.getLanguageString(r5);
        r0 = r63;
        r1 = r36;
        r0.setText(r1);
        r0 = r69;
        r0 = r0.mCloseButton;
        r29 = r0;
        r65 = r61;
        r65 = (android.view.View.OnClickListener) r65;
        r38 = r65;
        r0 = r29;
        r1 = r38;
        r0.setOnClickListener(r1);
    L_0x0424:
        r5 = 2131691679; // 0x7f0f089f float:1.9012437E38 double:1.053195626E-314;
        r4 = r4.findViewById(r5);
        r67 = r4;
        r67 = (android.widget.TextView) r67;
        r66 = r67;
        r0 = r66;
        r1 = r69;
        r1.mTvTitle = r0;
        r5 = 3718; // 0xe86 float:5.21E-42 double:1.837E-320;
        r36 = com.waze.strings.DisplayStrings.displayString(r5);
        if (r36 == 0) goto L_0x0543;
    L_0x043f:
        r0 = r36;
        r16 = r0.isEmpty();
        if (r16 != 0) goto L_0x0543;
    L_0x0447:
        r16 = 1;
    L_0x0449:
        if (r16 == 0) goto L_0x0546;
    L_0x044b:
        r0 = r69;
        r0 = r0.mTvTitle;
        r66 = r0;
        r1 = r36;
        r0.setText(r1);
    L_0x0456:
        r68 = new com.waze.reports.ReportMenu$14;
        r0 = r68;
        r1 = r69;
        r0.<init>(r1);
        return;
        goto L_0x0464;
    L_0x0461:
        goto L_0x0100;
    L_0x0464:
        r26 = 2;
        goto L_0x0461;
    L_0x0467:
        r5 = 2130903363; // 0x7f030143 float:1.7413542E38 double:1.0528061463E-314;
        r0 = r70;
        r4 = r0.inflate(r5, r11);
        r5 = 3718; // 0xe86 float:5.21E-42 double:1.837E-320;
        r36 = com.waze.strings.DisplayStrings.displayString(r5);
        if (r36 == 0) goto L_0x04c9;
    L_0x0478:
        r0 = r36;
        r16 = r0.isEmpty();
        if (r16 != 0) goto L_0x04c9;
    L_0x0480:
        r16 = 1;
    L_0x0482:
        if (r16 == 0) goto L_0x0497;
    L_0x0484:
        r0 = r69;
        r0 = r0.mDensity;
        r19 = r0;
        r20 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r19 = r20 * r19;
        r0 = r19;
        r7 = (int) r0;
        r0 = r23;
        r0 = r0 - r7;
        r23 = r0;
    L_0x0497:
        r7 = r23 / r22;
        r16 = 0;
        r5 = 2;
        if (r7 <= r5) goto L_0x04a2;
    L_0x049e:
        r7 = r7 + -1;
        r16 = 1;
    L_0x04a2:
        r24 = r7 * r22;
        r24 = r23 - r24;
    L_0x04a6:
        r5 = 1;
        if (r7 <= r5) goto L_0x04cc;
    L_0x04a9:
        r0 = r24;
        r0 = (float) r0;
        r19 = r0;
        r0 = r69;
        r0 = r0.mDensity;
        r25 = r0;
        r20 = 1095761920; // 0x41500000 float:13.0 double:5.413783207E-315;
        r0 = r25;
        r1 = r20;
        r0 = r0 * r1;
        r25 = r0;
        r26 = (r19 > r25 ? 1 : (r19 == r25 ? 0 : -1));
        if (r26 >= 0) goto L_0x04cc;
    L_0x04c2:
        r7 = r7 + -1;
        r24 = r7 * r22;
        r24 = r23 - r24;
        goto L_0x04a6;
    L_0x04c9:
        r16 = 0;
        goto L_0x0482;
    L_0x04cc:
        if (r7 > 0) goto L_0x04d6;
    L_0x04ce:
        r28 = "ReportMenu: initLayout: num lines = 0; setting to 1";
        r0 = r28;
        com.waze.Logger.m38e(r0);
        r7 = 1;
    L_0x04d6:
        if (r16 == 0) goto L_0x0525;
    L_0x04d8:
        r26 = 1;
    L_0x04da:
        r22 = r26 + r7;
        r22 = r24 / r22;
        r0 = r22;
        r1 = r69;
        r1.mMenuMarginTop = r0;
        r0 = r69;
        r0 = r0.mMenuMarginTop;
        r22 = r0;
        r22 = r22 * 2;
        r0 = r23;
        r1 = r22;
        r0 = r0 - r1;
        r23 = r0;
        r0 = r0 / r7;
        r23 = r0;
        r1 = r69;
        r1.mRealItemHeight = r0;
        r0 = r21;
        r1 = r69;
        r1.mRealItemWidth = r0;
        r5 = 16;
        r23 = r5 / r7;
        r0 = r23;
        r1 = r21;
        r0 = r0 * r1;
        r23 = r0;
        r0 = r18;
        r1 = r23;
        if (r0 <= r1) goto L_0x0143;
    L_0x0511:
        r5 = 16;
        r23 = r5 / r7;
        r0 = r18;
        r1 = r23;
        r0 = r0 / r1;
        r18 = r0;
        goto L_0x0520;
    L_0x051d:
        goto L_0x0143;
    L_0x0520:
        r1 = r69;
        r1.mRealItemWidth = r0;
        goto L_0x051d;
    L_0x0525:
        r26 = 2;
        goto L_0x04da;
        goto L_0x052c;
    L_0x0529:
        goto L_0x01b8;
    L_0x052c:
        r16 = 0;
        goto L_0x0529;
        goto L_0x0533;
    L_0x0530:
        goto L_0x01cb;
    L_0x0533:
        r7 = 2130838913; // 0x7f020581 float:1.7282822E38 double:1.0527743037E-314;
        goto L_0x0530;
        goto L_0x053b;
    L_0x0538:
        goto L_0x0317;
    L_0x053b:
        r7 = 2130837802; // 0x7f02012a float:1.7280568E38 double:1.052773755E-314;
        goto L_0x0538;
        goto L_0x0543;
    L_0x0540:
        goto L_0x0449;
    L_0x0543:
        r16 = 0;
        goto L_0x0540;
    L_0x0546:
        r0 = r69;
        r0 = r0.mTvTitle;
        r66 = r0;
        goto L_0x0550;
    L_0x054d:
        goto L_0x0456;
    L_0x0550:
        r5 = 8;
        r0 = r66;
        r0.setVisibility(r5);
        goto L_0x054d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.reports.ReportMenu.initLayout(android.view.LayoutInflater):void");
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle savedInstanceState) throws  {
        setRetainInstance(true);
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            this.isRandomUser = savedInstanceState.getBoolean("isRandomUser");
            this.isFoursquareEnabled = savedInstanceState.getBoolean("isFoursquareEnabled");
            this.isClosureEnabled = savedInstanceState.getBoolean("isClosureEnabled");
        }
        this.layoutManager = ((MainActivity) getActivity()).getLayoutMgr();
        this.mCtx = getActivity();
        this.mDensity = this.mCtx.getResources().getDisplayMetrics().density;
        this.f85r = $r1.inflate(C1283R.layout.report_menu, $r2, false);
        this.mainLayout = (ViewGroup) this.f85r.findViewById(C1283R.id.reportMainLayout);
        this.nativeManager = AppService.getNativeManager();
        this.myWazeNativeManager = MyWazeNativeManager.getInstance();
        View $r10 = this.layoutManager.getReportMenuContainer();
        if ($r10.getMeasuredHeight() == 0 || $r10.getMeasuredWidth() == 0) {
            $r10.getViewTreeObserver().addOnGlobalLayoutListener(new 1(this, $r10, $r1));
        } else {
            initLayout($r1);
        }
        return this.f85r;
    }

    public void onResume() throws  {
        super.onResume();
        NativeCanvasRenderer.OnMainCanvasOverlayShown();
    }

    public void onPause() throws  {
        super.onPause();
        NativeCanvasRenderer.OnMainCanvasOverlayHidden();
    }

    private Context getContextForReport() throws  {
        if (this.mCtx == null) {
            this.mCtx = getActivity();
        }
        if (this.mCtx == null) {
            this.mCtx = AppService.getActiveActivity();
        }
        return this.mCtx;
    }

    public void showTrafficJamReport() throws  {
        Context $r2 = getContextForReport();
        if ($r2 != null) {
            TrafficJamReport $r1 = new TrafficJamReport($r2, this);
            setReportForm($r1);
            ReportMenuButton $r3 = null;
            Iterator $r5 = this.mClipViews.iterator();
            while ($r5.hasNext()) {
                ReportMenuButton $r7 = (ReportMenuButton) $r5.next();
                if (C1283R.drawable.icon_report_traffic == $r7.getImageResId()) {
                    $r3 = $r7;
                    break;
                }
            }
            flipView($r3, false);
            this.mClickedRmb = $r3;
            $r1.setButton(this.mClickedRmb.getImageResId(), this.mClickedRmb.getBackgroundColor());
            this.mClickedRmb.setVisibility(4);
        }
    }

    public void showPoliceReport() throws  {
        Context $r2 = getContextForReport();
        if ($r2 != null) {
            PoliceReport $r1 = new PoliceReport($r2, this);
            setReportForm($r1);
            ReportMenuButton $r3 = null;
            Iterator $r5 = this.mClipViews.iterator();
            while ($r5.hasNext()) {
                ReportMenuButton $r7 = (ReportMenuButton) $r5.next();
                if (C1283R.drawable.icon_report_police == $r7.getImageResId()) {
                    $r3 = $r7;
                    break;
                }
            }
            flipView($r3, false);
            this.mClickedRmb = $r3;
            $r1.setButton(this.mClickedRmb.getImageResId(), this.mClickedRmb.getBackgroundColor());
            this.mClickedRmb.setVisibility(4);
        }
    }

    public void showHazardReport() throws  {
        Context $r2 = getContextForReport();
        if ($r2 != null) {
            HazardReport $r1 = new HazardReport($r2, this);
            setReportForm($r1);
            ReportMenuButton $r3 = null;
            Iterator $r5 = this.mClipViews.iterator();
            while ($r5.hasNext()) {
                ReportMenuButton $r7 = (ReportMenuButton) $r5.next();
                if (C1283R.drawable.icon_report_hazard == $r7.getImageResId()) {
                    $r3 = $r7;
                    break;
                }
            }
            flipView($r3, false);
            this.mClickedRmb = $r3;
            $r1.setButton(this.mClickedRmb.getImageResId(), this.mClickedRmb.getBackgroundColor());
            this.mClickedRmb.setVisibility(4);
        }
    }

    public void setRoadRecording(RoadRecordingPopUp $r1) throws  {
        this.mRoadRecordingPopUp = $r1;
    }

    public MapIssueReport showMapProblemReport() throws  {
        Context $r1 = getContextForReport();
        if ($r1 == null) {
            return null;
        }
        MapIssueReport $r2 = new MapIssueReport($r1, this);
        setReportForm($r2);
        ReportMenuButton $r3 = null;
        Iterator $r5 = this.mClipViews.iterator();
        while ($r5.hasNext()) {
            ReportMenuButton $r7 = (ReportMenuButton) $r5.next();
            if (C1283R.drawable.icon_report_map_editor == $r7.getImageResId()) {
                $r3 = $r7;
                break;
            }
        }
        flipView($r3, false);
        this.mClickedRmb = $r3;
        $r2.setButton(this.mClickedRmb.getImageResId(), this.mClickedRmb.getBackgroundColor());
        this.mClickedRmb.setVisibility(4);
        return $r2;
    }

    public void showClosureReport() throws  {
        Context $r2 = getContextForReport();
        if ($r2 != null) {
            ClosureReport $r1 = new ClosureReport($r2, this);
            setReportForm($r1);
            ReportMenuButton $r3 = null;
            Iterator $r5 = this.mClipViews.iterator();
            while ($r5.hasNext()) {
                ReportMenuButton $r7 = (ReportMenuButton) $r5.next();
                if (C1283R.drawable.icon_report_closure == $r7.getImageResId()) {
                    $r3 = $r7;
                    break;
                }
            }
            flipView($r3, false);
            this.mClickedRmb = $r3;
            $r1.setButton(this.mClickedRmb.getImageResId(), this.mClickedRmb.getBackgroundColor());
            this.mClickedRmb.setVisibility(4);
            ClosureMap.launch(this.mCtx, $r1, this.layoutManager, false);
        }
    }

    static OnClickListener getGasPriceOCL() throws  {
        return new 2();
    }

    public static void showGasPriceReport() throws  {
        getGasPriceOCL().onClick(null);
    }

    public void onBackPressed() throws  {
        if (!this.inMenu) {
            close();
        } else if (this.reportForm != null && !this.reportForm.goBack()) {
            removeReportForm();
        }
    }

    public void removeReportForm() throws  {
        if (this.inMenu) {
            ViewGroup $r7 = (ViewGroup) this.f85r.findViewById(C1283R.id.reportMenuViewReport);
            if (this.reportForm != null) {
                this.reportForm.stop();
                if (this.disableAnimation) {
                    $r7.removeView(this.reportForm);
                    this.reportForm = null;
                } else {
                    TextView $r19;
                    Animation $r21;
                    Animation alphaAnimation;
                    int $i2 = this.reportForm.disappearify(0, 100, this.mClickedRmb, new 15(this, $r7));
                    int[] $r4 = new int[2];
                    ReportMenuButton reportMenuButton = this.mClickedRmb;
                    ReportMenuButton $r10 = reportMenuButton;
                    reportMenuButton.getLocationInWindow($r4);
                    Context $r12 = this.mCtx;
                    int $i0 = $r12.getResources().getDisplayMetrics().widthPixels;
                    $r12 = this.mCtx;
                    $i0 += $r12.getResources().getDisplayMetrics().heightPixels;
                    int $i3 = 0;
                    while (true) {
                        ArrayList $r15 = this.mClipViews;
                        if ($i3 >= $r15.size()) {
                            break;
                        }
                        $r15 = this.mClipViews;
                        ArrayList $r152 = $r15;
                        $r10 = (ReportMenuButton) $r15.get($i3);
                        $r10.setEnabled(true);
                        $r10.setClickable(true);
                        if ($r10 != this.mClickedRmb) {
                            int $i1;
                            $r10.setOpen();
                            int[] $r3 = new int[2];
                            $r10.getLocationInWindow($r3);
                            int i = $r3[0] - $r4[0];
                            int i2 = $r3[1] - $r4[1];
                            int abs = Math.abs(i) + Math.abs(i2);
                            if (i != 0) {
                                $i1 = i * $i0;
                                i = $i1;
                                i = $i1 / abs;
                            }
                            if (i2 != 0) {
                                $i1 = i2 * $i0;
                                i2 = $i1;
                                i2 = $i1 / abs;
                            }
                            Animation $r2 = $i1;
                            $i1 = new AnimationSet(true);
                            $r2.addAnimation(new TranslateAnimation((float) i, 0.0f, (float) i2, 0.0f));
                            $r2.addAnimation(new RotateAnimation(75.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                            $r2.setDuration(300);
                            $r2.setInterpolator(new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                            $r2.setStartOffset((long) $i2);
                            $r2.setFillAfter(true);
                            $r15 = this.mTextViews;
                            $r152 = $r15;
                            ((TextView) $r15.get($i3)).clearAnimation();
                            $r10.clearAnimation();
                            ((View) $r10.getParent()).startAnimation($r2);
                        } else {
                            $r15 = this.mTextViews;
                            $r152 = $r15;
                            $r19 = (TextView) $r15.get($i3);
                            $r21 = alphaAnimation;
                            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                            $r21.setStartOffset((long) $i2);
                            $r21.setDuration(150);
                            $r21.setFillBefore(true);
                            $r19.startAnimation($r21);
                        }
                        $i3++;
                    }
                    $r21 = alphaAnimation;
                    alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    $r21.setDuration(150);
                    TextView textView = this.mTvTitle;
                    $r19 = textView;
                    textView.startAnimation($r21);
                }
            }
            this.layoutManager.removeDelayedReportButton();
            this.inMenu = false;
        }
    }

    public void closeFromReportForm() throws  {
        boolean $z0 = this.inMenu;
        this = this;
        if ($z0) {
            ViewGroup $r3 = (ViewGroup) this.f85r.findViewById(C1283R.id.reportMenuViewReport);
            if (this.reportForm != null) {
                this.reportForm.stop();
                $z0 = this.disableAnimation;
                this = this;
                if ($z0) {
                    $r3.removeView(this.reportForm);
                    this.reportForm = null;
                    this.layoutManager.removeReportMenu();
                } else {
                    this.reportForm.disappearify(0, 100, null, new 16(this, $r3));
                    Iterator $r7 = this.mClipViews.iterator();
                    while ($r7.hasNext()) {
                        ReportMenuButton $r9 = (ReportMenuButton) $r7.next();
                        $r9.setEnabled(true);
                        $r9.setClickable(true);
                    }
                }
            }
            this.layoutManager.removeDelayedReportButton();
            this.inMenu = false;
        }
    }

    public void setMenuButtonDrawable(Drawable drawable) throws  {
    }

    protected void close() throws  {
        if (VERSION.SDK_INT < 21 || this.disableAnimation || this.mainLayout.getVisibility() == 8) {
            this.layoutManager.removeReportMenu();
        } else if (this.reportForm != null) {
            $r4 = r13;
            r13 = new AlphaAnimation(1.0f, 0.0f);
            $r4.setDuration(200);
            $r4.setFillAfter(true);
            $r4.setAnimationListener(new 17(this));
            this.mainLayout.startAnimation($r4);
        } else {
            int $i0 = 0;
            int $i1 = 0;
            while (true) {
                ArrayList $r6 = this.mClipViews;
                if ($i1 < $r6.size()) {
                    18 $r7 = null;
                    $r6 = this.mClipViews;
                    if ($i1 == $r6.size() - 1) {
                        $r7 = r0;
                        18 18 = new 18(this);
                    }
                    $r6 = this.mClipViews;
                    ArrayList $r62 = $r6;
                    ReportMenuButton $r9 = (ReportMenuButton) $r6.get($i1);
                    $r9.startCloseAnimation($i0, 250, $r7);
                    $i0 += 20;
                    $r9.invalidate();
                    $r6 = this.mTextViews;
                    $r62 = $r6;
                    TextView $r10 = (TextView) $r6.get($i1);
                    $r4 = r13;
                    r13 = new AlphaAnimation(1.0f, 0.0f);
                    long $l3 = (long) $i0;
                    $r4.setStartOffset($l3);
                    $r4.setDuration(100);
                    $r4.setFillAfter(true);
                    $r10.startAnimation($r4);
                    $i1++;
                } else {
                    View $r11 = this.mBackgroundView;
                    ViewPropertyAnimatorHelper.initAnimation($r11).alpha(0.0f);
                    $r4 = r13;
                    r13 = new AlphaAnimation(1.0f, 0.0f);
                    $r4.setDuration(100);
                    $r4.setFillAfter(true);
                    TextView $r102 = this.mTvTitle;
                    $r102.startAnimation($r4);
                    return;
                }
            }
        }
    }

    public void setDelayedReport(ReportMenuButton $r1) throws  {
        AlphaAnimation $r2 = r6;
        AlphaAnimation r6 = new AlphaAnimation(1.0f, 0.0f);
        $r2.setDuration(200);
        $r2.setFillAfter(true);
        $r2.setAnimationListener(new 19(this));
        this.mainLayout.startAnimation($r2);
        this.layoutManager.setDelayedReport(this, $r1);
    }

    @Deprecated
    public void toggleRoadPaving(@Deprecated boolean currentState) throws  {
    }

    public void showRoadRecording() throws  {
        this.mRoadRecordingPopUp = new RoadRecordingPopUp(this.mCtx, this);
        this.mRoadRecordingPopUp.open();
    }

    public void open(boolean $z0) throws  {
        if (this.mainLayout == null) {
            Logger.m38e("ReportMenu:open: mainLayout is null");
            return;
        }
        this.isVisible = true;
        this.mainLayout.clearAnimation();
        if (this.layoutManager.getNavBar() != null) {
            this.layoutManager.getNavBar().setAlertMode(true);
        }
        this.mBackgroundView.setAlpha(1.0f);
        this.f85r.getViewTreeObserver().addOnGlobalLayoutListener(new 20(this, $z0));
    }

    public int getDelayedReportButtonResource() throws  {
        if (((ViewGroup) this.f85r.findViewById(C1283R.id.reportMenuViewReport)).getChildCount() <= 0 || this.reportForm == null) {
            return C1283R.drawable.closure_blackbar_icon;
        }
        return this.reportForm.getDelayedReportButtonResource();
    }

    public void onRemove() throws  {
        this.isVisible = false;
        this.layoutManager.restoreReportButton();
        if (this.layoutManager.getNavBar() != null) {
            this.layoutManager.getNavBar().setAlertMode(false);
        }
        this.nativeManager.restorePoiFocus();
    }

    public void onActivityResult(Activity $r1, int $i0, int $i1, Intent $r2) throws  {
        if (this.reportForm != null) {
            this.reportForm.onActivityResult($r1, $i0, $i1, $r2);
        }
        if ($i1 == RESULT_CLOSE_REPORT_MENU) {
            close();
        }
    }

    public void onOrientationChanged(int $i0) throws  {
        int $i1 = -1;
        if (this.mClickedRmb != null) {
            $i1 = this.mClickedRmb.getImageResId();
        }
        this.mClickedRmb = null;
        ViewGroup $r4 = (ViewGroup) this.f85r.findViewById(C1283R.id.reportMenuViewReport);
        $r4.removeAllViews();
        ((ViewGroup) this.f85r.findViewById(C1283R.id.reportMenuViewMenu)).removeAllViews();
        initLayout((LayoutInflater) this.mCtx.getSystemService("layout_inflater"));
        this.mGrowingCircleDrawable.setLevel(10000);
        int $i2 = 0;
        while (true) {
            ArrayList $r10 = this.mClipViews;
            if ($i2 >= $r10.size()) {
                break;
            }
            $r10 = this.mClipViews;
            ArrayList $r102 = $r10;
            ((ReportMenuButton) $r10.get($i2)).skipAnimation();
            $i2++;
        }
        if (this.reportForm != null) {
            ReportForm $r11 = this.reportForm;
            $r11.beforeOrientationChanged();
            $r11 = this.reportForm;
            $r11.stop();
            $r11 = this.reportForm;
            $r11.removeAllViews();
            $r11 = this.reportForm;
            ReportForm $r112 = $r11;
            $r11.onOrientationChanged($i0);
            $r11 = this.reportForm;
            $r11.afterOrientationChanged();
            $r11 = this.reportForm;
            $r11.stop();
            $r4.addView(this.reportForm);
            $i2 = 0;
            while (true) {
                $r10 = this.mClipViews;
                if ($i2 >= $r10.size()) {
                    break;
                }
                $r10 = this.mClipViews;
                $r102 = $r10;
                ReportMenuButton $r2 = (ReportMenuButton) $r10.get($i2);
                $r2.setEnabled(false);
                $r2.setClickable(false);
                if ($i1 == $r2.getImageResId()) {
                    this.mClickedRmb = $r2;
                    this.mClickedRmb.setVisibility(4);
                } else {
                    $r2.startCloseAnimation(0, 0, null);
                    $r2.postInvalidate();
                }
                $r10 = this.mTextViews;
                $r102 = $r10;
                TextView $r12 = (TextView) $r10.get($i2);
                Animation $r1 = r0;
                Animation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
                $r1.setFillAfter(true);
                $r12.startAnimation($r1);
                $i2++;
            }
            if (this.mClickedRmb != null) {
                this.reportForm.setButton(this.mClickedRmb.getImageResId(), this.mClickedRmb.getBackgroundColor());
            }
        }
        if (this.mRoadRecordingPopUp != null) {
            RoadRecordingPopUp roadRecordingPopUp = this.mRoadRecordingPopUp;
            RoadRecordingPopUp $r13 = roadRecordingPopUp;
            roadRecordingPopUp.onOrientationChanged($i0);
        }
    }

    public void setReportForm(ReportForm $r1) throws  {
        this.reportForm = $r1;
    }

    public void flipView(ReportMenuButton $r1) throws  {
        flipView($r1, true);
    }

    private void flipView(ReportMenuButton $r1, boolean $z0) throws  {
        if (this.reportForm != null && this.f85r != null) {
            ((ViewGroup) this.f85r.findViewById(C1283R.id.reportMenuViewReport)).addView(this.reportForm, new LayoutParams(-1, -1));
            this.mClickedRmb = $r1;
            if (this.mClickedRmb != null) {
                this.mClickedRmb.skipAnimation();
            }
            ArrayList $r15;
            ArrayList $r152;
            TextView $r18;
            Animation $r20;
            Animation alphaAnimation;
            if (this.disableAnimation || $r1 == null || !$z0) {
                $r15 = this.mClipViews;
                Iterator $r21 = $r15.iterator();
                while ($r21.hasNext()) {
                    $r1 = (ReportMenuButton) $r21.next();
                    $r1.setEnabled(false);
                    $r1.setClickable(false);
                    if ($r1 != this.mClickedRmb) {
                        $r1.setClosed();
                    }
                }
                $r15 = this.mTextViews;
                $r152 = $r15;
                $r21 = $r15.iterator();
                while ($r21.hasNext()) {
                    $r18 = (TextView) $r21.next();
                    $r20 = alphaAnimation;
                    alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    $r20.setDuration(0);
                    $r20.setFillAfter(true);
                    $r18.startAnimation($r20);
                }
                this.reportForm.start();
            } else {
                int[] $r5 = new int[2];
                ReportMenuButton reportMenuButton = this.mClickedRmb;
                $r1 = reportMenuButton;
                reportMenuButton.getLocationInWindow($r5);
                Context $r12 = this.mCtx;
                int $i0 = $r12.getResources().getDisplayMetrics().widthPixels;
                $r12 = this.mCtx;
                $i0 += $r12.getResources().getDisplayMetrics().heightPixels;
                int $i2 = 0;
                while (true) {
                    $r15 = this.mClipViews;
                    if ($i2 >= $r15.size()) {
                        break;
                    }
                    $r15 = this.mClipViews;
                    $r152 = $r15;
                    $r1 = (ReportMenuButton) $r15.get($i2);
                    $r1.setEnabled(false);
                    $r1.setClickable(false);
                    if ($r1 != this.mClickedRmb) {
                        int $i1;
                        int[] $r4 = new int[2];
                        $r1.getLocationInWindow($r4);
                        int $i3 = $r4[0] - $r5[0];
                        int i = $r4[1] - $r5[1];
                        int abs = Math.abs($i3) + Math.abs(i);
                        if ($i3 != 0) {
                            $i1 = $i3 * $i0;
                            $i3 = $i1;
                            $i3 = $i1 / abs;
                        }
                        if (i != 0) {
                            $i1 = i * $i0;
                            i = $i1;
                            i = $i1 / abs;
                        }
                        Animation $r3 = $i1;
                        $i1 = new AnimationSet(true);
                        $r3.addAnimation(new TranslateAnimation(0.0f, (float) ($i3 * 2), 0.0f, (float) (i * 2)));
                        $r3.addAnimation(new RotateAnimation(0.0f, 150.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                        $r3.setDuration(300);
                        $r3.setInterpolator(new AccelerateInterpolator());
                        $r3.setFillAfter(true);
                        $r15 = this.mTextViews;
                        $r152 = $r15;
                        ((TextView) $r15.get($i2)).clearAnimation();
                        ((View) $r1.getParent()).startAnimation($r3);
                    } else {
                        $r15 = this.mTextViews;
                        $r152 = $r15;
                        $r18 = (TextView) $r15.get($i2);
                        $r20 = alphaAnimation;
                        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                        $r20.setDuration(100);
                        $r20.setFillAfter(true);
                        $r18.startAnimation($r20);
                    }
                    $i2++;
                }
                $r20 = alphaAnimation;
                alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
                $r20.setDuration(10000);
                $r20.setFillAfter(true);
                reportMenuButton = this.mClickedRmb;
                $r1 = reportMenuButton;
                reportMenuButton.startAnimation($r20);
                $r20 = alphaAnimation;
                alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                $r20.setDuration(100);
                $r20.setFillAfter(true);
                TextView textView = this.mTvTitle;
                $r18 = textView;
                textView.startAnimation($r20);
                this.reportForm.appearify(150, 150, this.mClickedRmb);
            }
            this.inMenu = true;
        }
    }

    public ReportForm getReportForm() throws  {
        return this.reportForm;
    }

    int getStatusBarHeight() throws  {
        int $i0 = this.mCtx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if ($i0 > 0) {
            return this.mCtx.getResources().getDimensionPixelSize($i0);
        }
        return 0;
    }

    public void setRevealOrigin(int $i0, int $i1) throws  {
        if (this.mGrowingCircleDrawable != null) {
            this.mGrowingCircleDrawable.setOrigin($i0, $i1);
        }
    }

    public LayoutManager getLayoutManager() throws  {
        return this.layoutManager;
    }
}
