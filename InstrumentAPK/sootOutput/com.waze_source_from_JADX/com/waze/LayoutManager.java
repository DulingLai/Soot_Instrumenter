package com.waze;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.NativeManager.CenteredOnMeListener;
import com.waze.WzWebView.WebViewBackCallback;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.animation.easing.AnimationEasingManager;
import com.waze.animation.easing.AnimationEasingManager.EaseType;
import com.waze.animation.easing.AnimationEasingManager.EasingCallback;
import com.waze.animation.easing.Bounce;
import com.waze.audioextension.spotify.SpotifyManager;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolMessage;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.CarpoolRidePickupMeetingDetails;
import com.waze.carpool.CarpoolRide;
import com.waze.carpool.CarpoolTripDialog;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.CarpoolsRidesLoader;
import com.waze.carpool.CarpoolsRidesLoader.CarpoolRidesLoaderListener;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.main.navigate.NavigationListFragment;
import com.waze.map.CanvasFont;
import com.waze.map.MapViewWrapper;
import com.waze.menus.MainSideMenu;
import com.waze.menus.SearchOnMapView;
import com.waze.menus.SearchOnMapView.SearchOnMapProvider;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navbar.NavBar;
import com.waze.navbar.NavBar.INavListDismissed;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.notificationbar.NotificationBar;
import com.waze.reports.ClosureMap;
import com.waze.reports.ClosureReport;
import com.waze.reports.ReportMenu;
import com.waze.rtalerts.RTAlertsAlertData;
import com.waze.rtalerts.RTAlertsCommentData;
import com.waze.rtalerts.RTAlertsConsts;
import com.waze.rtalerts.RTAlertsNativeManager;
import com.waze.rtalerts.RTAlertsThumbsUpData;
import com.waze.settings.SettingsSound;
import com.waze.settings.SettingsVoiceCommandsActivity;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.user.UserData;
import com.waze.utils.DisplayUtils;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.bottom.BottomNotificationIcon;
import com.waze.view.button.ReportMenuButton;
import com.waze.view.layout.SwipeableLayout;
import com.waze.view.layout.SwipeableLayout.TransitionDoneListener;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.map.SpeedometerView;
import com.waze.view.navbar.BottomBar;
import com.waze.view.navbar.NavBarSoundButton;
import com.waze.view.navbar.TrafficBarView;
import com.waze.view.popups.AlertTicker;
import com.waze.view.popups.AlerterPopUp;
import com.waze.view.popups.BonusWebPopUP;
import com.waze.view.popups.CarGasSettingsPopUp;
import com.waze.view.popups.CarpoolMessagePopup;
import com.waze.view.popups.DueToPopUp;
import com.waze.view.popups.EndOfDriveReminderPopUp;
import com.waze.view.popups.EndOfDriveReminderPopUp.EndOfDriveReminderPopupListener;
import com.waze.view.popups.EtaUpdatePopUp;
import com.waze.view.popups.FriendsOnlinePopUp;
import com.waze.view.popups.ManualRidePopup;
import com.waze.view.popups.MessageTicker;
import com.waze.view.popups.PickupCanceledPopUp;
import com.waze.view.popups.PickupOfferPopUp;
import com.waze.view.popups.PoiPopUp;
import com.waze.view.popups.PopUp;
import com.waze.view.popups.PopupCloseReason;
import com.waze.view.popups.SpotifyPopup;
import com.waze.view.popups.SystemMessageWeb;
import com.waze.view.popups.ThumbsUpPopUP;
import com.waze.view.popups.TrafficDetectionPopUp;
import com.waze.view.popups.UpcomingCarpoolBar;
import com.waze.view.popups.UpdateGasPopup;
import com.waze.view.popups.UserPopUp;
import com.waze.voice.AsrDialogView;
import com.waze.voice.AsrSpeechRecognizer;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Semaphore;

public final class LayoutManager {
    public static final String FRIENDS_CONTROL_MODE_ALWAYS = "always";
    public static final String FRIENDS_CONTROL_MODE_MAP_CONTROLS = "map controls";
    public static final String FRIENDS_CONTROL_MODE_OFF = "off";
    public static final float REPORT_SCALE_ON_PRESS = 1.05f;
    public static final String SHARED_PREF_CARPOOL_ENABLED_DATA = "carpoolEnabled";
    public static final String SHARED_PREF_CARPOOL_STATE_KEY = "CarpoolState";
    public static final float WAZE_LAYOUT_EDIT_HEIGHT = 50.0f;
    public static final float WAZE_LAYOUT_EDIT_SIDE_MARGIN = 2.0f;
    public static final int WAZE_LAYOUT_EDIT_TYPE_SIMPLE = 0;
    public static final int WAZE_LAYOUT_EDIT_TYPE_VOICE = 1;
    public static boolean isDueToPopupShown = false;
    private static boolean mSpotifyButtonShown = false;
    private final int FILL_PARENT;
    private boolean bIsAlertPopUpOpen = false;
    private boolean bIsClosureRunning = false;
    private boolean bIsFriendsBarShown = false;
    private boolean bIsFriendsBarShownFirstTime = true;
    private boolean bIsWaitingFriendsShown = false;
    private ActivityBase context;
    private ReportMenu delayedReportMenu = null;
    private LayoutInflater inflater;
    private boolean isSearchBarVisible = false;
    private boolean isSplashVisible = false;
    private AlertTicker mAlertTicker;
    private AlerterPopUp mAlerterPopUp;
    private MapViewWrapper mAppView;
    private AsrDialogView mAsrDialogView;
    private ImageView mAsrMapButtonView;
    private Runnable mAutoCloseTransportationTooltipRunnable;
    private BottomBar mBottomBar;
    private View mBottomBarLayout;
    private BottomBarOnTouchListener mBottomBarOnTouchListener;
    private View mBottomBarRightButtonLayout;
    private CarGasSettingsPopUp mCarGasSettingsPopup;
    private boolean mCarpoolAvailable = false;
    private ImageView mCarpoolNotificationDot;
    private TextView mCurrentStreetName;
    private View mCurrentStreetNameShadow;
    private boolean mCurrentStreetNameShown;
    private View mEditBoxView;
    private FriendsBarFragment mFriendsBarFragment;
    private boolean mHasRidewithDot;
    private boolean mIsFollowActive = false;
    private boolean mIsShowingReportAndSpeedometer;
    private boolean mLogin = false;
    private RelativeLayout mMainLayout;
    private MainSideMenu mMainSideMenu;
    private NavBar mNavBar;
    private NavBarSoundButton mNavBarSoundButton;
    private INavListDismissed mNavListDismissedLissener = null;
    private NavigationListFragment mNavListFrag = null;
    private NavResultsFragment mNavResFrag = null;
    private boolean mNeedRefreshCarpoolPanel;
    private NotificationBar mNotifBar = null;
    private FriendUserData[] mNotifyFriends;
    private ArrayList<Runnable> mOnPopupsClosed = new ArrayList(4);
    private ArrayList<Runnable> mOnResume = new ArrayList(8);
    private boolean mOrientationEventPending = false;
    PoiPopUp mPoiPopUp;
    private PopupsFragment mPopupsFragment;
    private View mReportButtonFrame;
    private boolean mRequestedFriendsBarVisible = false;
    private RightSideMenu mRightSideMenu;
    private SearchOnMapView mSearchOnMapView;
    private boolean mShouldShowTrafficBar = true;
    private int mShowDogfoodWarning = 1;
    private int mShowFriendsTip = 1;
    private SpeedometerView mSpeedometerView;
    private SwipeableLayout mSwipeableLayout;
    private MessageTicker mTicker = null;
    private TooltipManager mTooltipManager = new TooltipManager(this);
    private TrafficBarView mTrafficBarView = null;
    private TextView mTransportationLayerLabel;
    private RelativeLayout mTransportationLayerTooltip;
    UpcomingCarpoolBar mUpcomingCarpoolBar;
    UserPopUp mUserPopUp;
    private WzWebView mWebView;
    private ClosureMap m_closureMap;
    private boolean mbPaused = true;
    private final int[][] menuImageResIds;
    private boolean navbarThenHiddenForAlerter = false;
    private boolean pendingAddStop;
    private volatile boolean popupShown = false;
    private List<PopUp> popups = new ArrayList();
    private volatile ReportMenu reportMenu;
    private Semaphore sm = new Semaphore(0);

    class C11551 implements WebViewBackCallback {
        C11551() throws  {
        }

        public boolean onBackEvent(KeyEvent $r1) throws  {
            return LayoutManager.this.mAppView.getMapView().onKeyDown(4, $r1);
        }
    }

    class C11572 implements Runnable {
        C11572() throws  {
        }

        public void run() throws  {
            EditBox $r1 = LayoutManager.this.getEditBox();
            if ($r1 != null) {
                LayoutManager.this.ShowSoftInput($r1);
            }
        }
    }

    class AnonymousClass34 implements AnimationListener {
        final /* synthetic */ View val$zoomControls;

        AnonymousClass34(View $r2) throws  {
            this.val$zoomControls = $r2;
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            this.val$zoomControls.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }
    }

    class AnonymousClass35 implements AnimationListener {
        final /* synthetic */ View val$friendsControl;

        AnonymousClass35(View $r2) throws  {
            this.val$friendsControl = $r2;
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            this.val$friendsControl.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }
    }

    class C11593 implements Runnable {

        class C11581 implements SearchOnMapProvider {
            C11581() throws  {
            }

            public List<AddressItem> getRecents() throws  {
                return LayoutManager.this.mMainSideMenu.getRecents();
            }
        }

        C11593() throws  {
        }

        public void run() throws  {
            if (LayoutManager.this.isSearchOnMapEnabled() && LayoutManager.this.mSearchOnMapView == null) {
                LayoutManager.this.mSearchOnMapView = new SearchOnMapView(LayoutManager.this.context);
                LayoutParams $r1 = new LayoutParams(-1, -1);
                $r1.addRule(3, C1283R.id.notificationsLayout);
                LayoutManager.this.mSearchOnMapView.setLayoutParams($r1);
                LayoutManager.this.mSwipeableLayout.addView(LayoutManager.this.mSearchOnMapView);
                LayoutManager.this.mSearchOnMapView.setVisibility(8);
                LayoutManager.this.initViewOrder();
                LayoutManager.this.mSearchOnMapView.setSearchOnMapProvider(new C11581());
                LayoutManager.this.setPopupShown();
            }
        }
    }

    class C11625 implements AnimationListener {

        class C11611 implements Runnable {
            C11611() throws  {
            }

            public void run() throws  {
                C11625.this.cleanAnim();
            }
        }

        C11625() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            if (LayoutManager.this.mbPaused) {
                LayoutManager.this.mOnResume.add(new C11611());
            } else {
                cleanAnim();
            }
        }

        private void cleanAnim() throws  {
            LayoutManager.this.context.getFragmentManager().beginTransaction().remove(LayoutManager.this.mNavListFrag).commit();
            LayoutManager.this.mNavListFrag = null;
            LayoutManager.this.mNavListDismissedLissener.onDismiss();
            LayoutManager.this.mSwipeableLayout.setSwipeEnabled(true);
            LayoutManager.this.showSpotifyButton();
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }
    }

    class AnonymousClass65 implements OnGlobalLayoutListener {
        final /* synthetic */ View val$container;
        final /* synthetic */ int val$orientation;

        AnonymousClass65(View $r2, int $i0) throws  {
            this.val$container = $r2;
            this.val$orientation = $i0;
        }

        public void onGlobalLayout() throws  {
            int $i0 = this.val$container.getMeasuredWidth();
            int $i1 = this.val$container.getMeasuredHeight();
            if ($i0 != 0 && $i1 != 0) {
                if (this.val$orientation == 1 && $i0 > $i1) {
                    return;
                }
                if (this.val$orientation != 2 || $i0 >= $i1) {
                    this.val$container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if (LayoutManager.this.reportMenu != null) {
                        LayoutManager.this.reportMenu.onOrientationChanged(this.val$orientation);
                    }
                    if (LayoutManager.this.delayedReportMenu != null) {
                        LayoutManager.this.delayedReportMenu.onOrientationChanged(this.val$orientation);
                    }
                }
            }
        }
    }

    class C11646 implements OnClickListener {
        C11646() throws  {
        }

        public void onClick(View v) throws  {
            if (LayoutManager.this.getTooltipManager() != null) {
                LayoutManager.this.getTooltipManager().closeTooltip(true, 6);
            }
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_PANEL_OPENING).addParam("TYPE", "BUTTON_CLICKED").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, LayoutManager.this.mHasRidewithDot ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_WITH_DOT : AnalyticsEvents.ANALYTICS_EVENT_VALUE_WITHOUT_DOT).send();
            LayoutManager.this.openRightSideFromButton(true);
        }
    }

    class C11657 implements OnClickListener {
        C11657() throws  {
        }

        public void onClick(View v) throws  {
            AppService.getMainActivity().openSoundActions();
            LayoutManager.this.setSoundIcon();
        }
    }

    class C11678 implements Runnable {

        class C11661 implements CarpoolRidesLoaderListener {
            C11661() throws  {
            }

            public void onCarpoolRidesLoaded(int totalRides) throws  {
                LayoutManager.this.setRidewithDot();
            }
        }

        C11678() throws  {
        }

        public void run() throws  {
            new CarpoolsRidesLoader(new C11661()).start();
        }
    }

    class C11689 implements Runnable {
        C11689() throws  {
        }

        public void run() throws  {
            LayoutManager.this.mNavBarSoundButton.adjustSoundButton();
        }
    }

    public static class WazeRect {
        public int maxx;
        public int maxy;
        public int minx;
        public int miny;

        WazeRect(int $i0, int $i1, int $i2, int $i3) throws  {
            this.minx = $i0;
            this.maxx = $i2;
            this.miny = $i1;
            this.maxy = $i3;
        }
    }

    public void SetControlsVisibilty(boolean r46) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:39:0x01f5 in {2, 5, 10, 15, 16, 20, 25, 28, 33, 38, 40, 43, 46, 52, 57, 59, 66, 67, 68} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r45 = this;
        r0 = r45;
        r9 = r0.mLogin;
        if (r9 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = r45;
        r10 = r0.mSearchOnMapView;
        if (r10 == 0) goto L_0x0016;
    L_0x000d:
        r0 = r45;
        r10 = r0.mSearchOnMapView;
        r0 = r46;
        r10.setIsShowingControls(r0);
    L_0x0016:
        r11 = com.waze.AppService.getAppContext();
        r12 = r11.getResources();
        r13 = r12.getConfiguration();
        r14 = r13.orientation;
        r15 = com.waze.AppService.getMainActivity();
        if (r15 == 0) goto L_0x01f9;
    L_0x002a:
        r9 = r15.isSpotifyBound();
        if (r9 == 0) goto L_0x01f9;
    L_0x0030:
        r9 = 1;
    L_0x0031:
        r16 = 1;
        r0 = r16;
        if (r14 != r0) goto L_0x003d;
    L_0x0037:
        r17 = com.waze.MainActivity.mIsBound;
        if (r17 == 0) goto L_0x003f;
    L_0x003b:
        if (r9 != 0) goto L_0x003f;
    L_0x003d:
        r46 = 0;
    L_0x003f:
        r16 = 97;
        r0 = r16;
        r18 = com.waze.config.ConfigValues.getStringValue(r0);
        r19 = com.waze.NativeSoundManager.getInstance();
        r0 = r19;
        r9 = r0.isAsrV2Enabled();
        if (r9 == 0) goto L_0x022f;
    L_0x0053:
        if (r46 == 0) goto L_0x01fb;
    L_0x0055:
        r0 = r45;
        r0 = r0.mAsrMapButtonView;
        r20 = r0;
        r16 = 80;
        r0 = r16;
        r14 = com.waze.utils.PixelMeasure.dp(r0);
        r14 = -r14;
        r0 = (float) r14;
        r21 = r0;
        r0 = r20;
        r1 = r21;
        r0.setTranslationX(r1);
        r0 = r45;
        r0 = r0.mAsrMapButtonView;
        r20 = r0;
        r16 = 0;
        r0 = r20;
        r1 = r16;
        r0.setVisibility(r1);
        r0 = r45;
        r0 = r0.mAsrMapButtonView;
        r20 = r0;
        r22 = com.waze.view.anim.ViewPropertyAnimatorHelper.initAnimation(r0);
        r23 = 0;
        r0 = r22;
        r1 = r23;
        r22 = r0.translationX(r1);
        r24 = 0;
        r0 = r22;
        r1 = r24;
        r0.setListener(r1);
    L_0x009a:
        r0 = r45;
        r0 = r0.mSwipeableLayout;
        r25 = r0;
        r16 = 2131691000; // 0x7f0f05f8 float:1.901106E38 double:1.0531952906E-314;
        r0 = r25;
        r1 = r16;
        r26 = r0.findViewById(r1);
        if (r46 != 0) goto L_0x0243;
    L_0x00ad:
        r0 = r26;
        r14 = r0.getVisibility();
        if (r14 != 0) goto L_0x0243;
    L_0x00b5:
        r27 = new android.view.animation.AnimationSet;
        r16 = 1;
        r0 = r27;
        r1 = r16;
        r0.<init>(r1);
        r28 = new android.view.animation.TranslateAnimation;
        r16 = 1;
        r23 = 0;
        r29 = 1;
        r30 = -1090519040; // 0xffffffffbf000000 float:-0.5 double:NaN;
        r31 = 1;
        r32 = 0;
        r33 = 1;
        r34 = 0;
        r0 = r28;
        r1 = r16;
        r2 = r23;
        r3 = r29;
        r4 = r30;
        r5 = r31;
        r6 = r32;
        r7 = r33;
        r8 = r34;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r0 = r27;
        r1 = r28;
        r0.addAnimation(r1);
        r35 = new android.view.animation.AlphaAnimation;
        r23 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r30 = 0;
        r0 = r35;
        r1 = r23;
        r2 = r30;
        r0.<init>(r1, r2);
        r0 = r27;
        r1 = r35;
        r0.addAnimation(r1);
        r36 = 250; // 0xfa float:3.5E-43 double:1.235E-321;
        r0 = r27;
        r1 = r36;
        r0.setDuration(r1);
        r38 = new android.view.animation.AccelerateInterpolator;
        r0 = r38;
        r0.<init>();
        r0 = r27;
        r1 = r38;
        r0.setInterpolator(r1);
        r0 = r26;
        r1 = r27;
        r0.startAnimation(r1);
        r39 = new com.waze.LayoutManager$34;
        r0 = r39;
        r1 = r45;
        r2 = r26;
        r0.<init>(r2);
        r0 = r27;
        r1 = r39;
        r0.setAnimationListener(r1);
    L_0x0136:
        r0 = r45;
        r0 = r0.mSwipeableLayout;
        r25 = r0;
        r16 = 2131691002; // 0x7f0f05fa float:1.9011064E38 double:1.0531952916E-314;
        r0 = r25;
        r1 = r16;
        r26 = r0.findViewById(r1);
        r40 = "off";
        r0 = r18;
        r1 = r40;
        r9 = r0.equals(r1);
        if (r9 == 0) goto L_0x02ce;
    L_0x0153:
        r46 = 0;
    L_0x0155:
        r0 = r45;
        r9 = r0.mLogin;
        if (r9 == 0) goto L_0x0169;
    L_0x015b:
        r0 = r45;
        r0 = r0.mFriendsBarFragment;
        r41 = r0;
        r9 = r0.isFriendsBarVisible();
        if (r9 == 0) goto L_0x0169;
    L_0x0167:
        r46 = 0;
    L_0x0169:
        if (r46 != 0) goto L_0x02e6;
    L_0x016b:
        r0 = r26;
        r14 = r0.getVisibility();
        if (r14 != 0) goto L_0x02e6;
    L_0x0173:
        r27 = new android.view.animation.AnimationSet;
        r16 = 1;
        r0 = r27;
        r1 = r16;
        r0.<init>(r1);
        r28 = new android.view.animation.TranslateAnimation;
        r16 = 1;
        r23 = 0;
        r29 = 1;
        r30 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r31 = 1;
        r32 = 0;
        r33 = 1;
        r34 = 0;
        r0 = r28;
        r1 = r16;
        r2 = r23;
        r3 = r29;
        r4 = r30;
        r5 = r31;
        r6 = r32;
        r7 = r33;
        r8 = r34;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r0 = r27;
        r1 = r28;
        r0.addAnimation(r1);
        r35 = new android.view.animation.AlphaAnimation;
        r23 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r30 = 0;
        r0 = r35;
        r1 = r23;
        r2 = r30;
        r0.<init>(r1, r2);
        r0 = r27;
        r1 = r35;
        r0.addAnimation(r1);
        r36 = 250; // 0xfa float:3.5E-43 double:1.235E-321;
        r0 = r27;
        r1 = r36;
        r0.setDuration(r1);
        r38 = new android.view.animation.AccelerateInterpolator;
        r0 = r38;
        r0.<init>();
        r0 = r27;
        r1 = r38;
        r0.setInterpolator(r1);
        r0 = r26;
        r1 = r27;
        r0.startAnimation(r1);
        r42 = new com.waze.LayoutManager$35;
        r0 = r42;
        r1 = r45;
        r2 = r26;
        r0.<init>(r2);
        r0 = r27;
        r1 = r42;
        r0.setAnimationListener(r1);
        return;
        goto L_0x01f9;
    L_0x01f6:
        goto L_0x0031;
    L_0x01f9:
        r9 = 0;
        goto L_0x01f6;
    L_0x01fb:
        r0 = r45;
        r0 = r0.mAsrMapButtonView;
        r20 = r0;
        r22 = com.waze.view.anim.ViewPropertyAnimatorHelper.initAnimation(r0);
        r16 = 80;
        r0 = r16;
        r14 = com.waze.utils.PixelMeasure.dp(r0);
        r14 = -r14;
        r0 = (float) r14;
        r21 = r0;
        r0 = r22;
        r1 = r21;
        r22 = r0.translationX(r1);
        r0 = r45;
        r0 = r0.mAsrMapButtonView;
        r20 = r0;
        r43 = com.waze.view.anim.ViewPropertyAnimatorHelper.createGoneWhenDoneListener(r0);
        goto L_0x0227;
    L_0x0224:
        goto L_0x009a;
    L_0x0227:
        r0 = r22;
        r1 = r43;
        r0.setListener(r1);
        goto L_0x0224;
    L_0x022f:
        r0 = r45;
        r0 = r0.mAsrMapButtonView;
        r20 = r0;
        goto L_0x0239;
    L_0x0236:
        goto L_0x009a;
    L_0x0239:
        r16 = 8;
        r0 = r20;
        r1 = r16;
        r0.setVisibility(r1);
        goto L_0x0236;
    L_0x0243:
        if (r46 == 0) goto L_0x0136;
    L_0x0245:
        r0 = r26;
        r14 = r0.getVisibility();
        r16 = 8;
        r0 = r16;
        if (r14 != r0) goto L_0x0136;
    L_0x0251:
        r27 = new android.view.animation.AnimationSet;
        r16 = 1;
        r0 = r27;
        r1 = r16;
        r0.<init>(r1);
        r28 = new android.view.animation.TranslateAnimation;
        r16 = 1;
        r23 = -1090519040; // 0xffffffffbf000000 float:-0.5 double:NaN;
        r29 = 1;
        r30 = 0;
        r31 = 1;
        r32 = 0;
        r33 = 1;
        r34 = 0;
        r0 = r28;
        r1 = r16;
        r2 = r23;
        r3 = r29;
        r4 = r30;
        r5 = r31;
        r6 = r32;
        r7 = r33;
        r8 = r34;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r0 = r27;
        r1 = r28;
        r0.addAnimation(r1);
        r35 = new android.view.animation.AlphaAnimation;
        r23 = 0;
        r30 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r35;
        r1 = r23;
        r2 = r30;
        r0.<init>(r1, r2);
        r0 = r27;
        r1 = r35;
        r0.addAnimation(r1);
        r36 = 250; // 0xfa float:3.5E-43 double:1.235E-321;
        r0 = r27;
        r1 = r36;
        r0.setDuration(r1);
        r44 = new android.view.animation.DecelerateInterpolator;
        r0 = r44;
        r0.<init>();
        r0 = r27;
        r1 = r44;
        r0.setInterpolator(r1);
        r0 = r26;
        r1 = r27;
        r0.startAnimation(r1);
        goto L_0x02c4;
    L_0x02c1:
        goto L_0x0136;
    L_0x02c4:
        r16 = 0;
        r0 = r26;
        r1 = r16;
        r0.setVisibility(r1);
        goto L_0x02c1;
    L_0x02ce:
        r40 = "always";
        r0 = r18;
        r1 = r40;
        r9 = r0.equals(r1);
        if (r9 == 0) goto L_0x02e5;
    L_0x02da:
        goto L_0x02de;
    L_0x02db:
        goto L_0x0155;
    L_0x02de:
        r46 = 1;
        goto L_0x02e4;
    L_0x02e1:
        goto L_0x0155;
    L_0x02e4:
        goto L_0x02db;
    L_0x02e5:
        goto L_0x02e1;
    L_0x02e6:
        if (r46 == 0) goto L_0x036d;
    L_0x02e8:
        r0 = r26;
        r14 = r0.getVisibility();
        r16 = 8;
        r0 = r16;
        if (r14 != r0) goto L_0x036e;
    L_0x02f4:
        r27 = new android.view.animation.AnimationSet;
        r16 = 1;
        r0 = r27;
        r1 = r16;
        r0.<init>(r1);
        r28 = new android.view.animation.TranslateAnimation;
        r16 = 1;
        r23 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r29 = 1;
        r30 = 0;
        r31 = 1;
        r32 = 0;
        r33 = 1;
        r34 = 0;
        r0 = r28;
        r1 = r16;
        r2 = r23;
        r3 = r29;
        r4 = r30;
        r5 = r31;
        r6 = r32;
        r7 = r33;
        r8 = r34;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r0 = r27;
        r1 = r28;
        r0.addAnimation(r1);
        r35 = new android.view.animation.AlphaAnimation;
        r23 = 0;
        r30 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r35;
        r1 = r23;
        r2 = r30;
        r0.<init>(r1, r2);
        r0 = r27;
        r1 = r35;
        r0.addAnimation(r1);
        r36 = 250; // 0xfa float:3.5E-43 double:1.235E-321;
        r0 = r27;
        r1 = r36;
        r0.setDuration(r1);
        r44 = new android.view.animation.DecelerateInterpolator;
        r0 = r44;
        r0.<init>();
        r0 = r27;
        r1 = r44;
        r0.setInterpolator(r1);
        r0 = r26;
        r1 = r27;
        r0.startAnimation(r1);
        r16 = 0;
        r0 = r26;
        r1 = r16;
        r0.setVisibility(r1);
        return;
    L_0x036d:
        return;
    L_0x036e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.LayoutManager.SetControlsVisibilty(boolean):void");
    }

    public void onOrientationChanged(int r48) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:48:0x0261 in {5, 8, 13, 14, 17, 22, 25, 30, 31, 34, 39, 40, 45, 47, 49, 54} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r47 = this;
        r4 = com.waze.AppService.getMainActivity();
        if (r4 == 0) goto L_0x000c;
    L_0x0006:
        r5 = r4.IsRunning();
        if (r5 != 0) goto L_0x0012;
    L_0x000c:
        r6 = 1;
        r0 = r47;
        r0.mOrientationEventPending = r6;
        return;
    L_0x0012:
        r0 = r47;
        r7 = r0.getReportMenuContainer();
        r8 = r7.getViewTreeObserver();
        r9 = new com.waze.LayoutManager$65;
        r0 = r47;
        r1 = r48;
        r9.<init>(r7, r1);
        r8.addOnGlobalLayoutListener(r9);
        r0 = r47;
        r10 = r0.mFriendsBarFragment;
        if (r10 == 0) goto L_0x0045;
    L_0x002e:
        r0 = r47;
        r10 = r0.mFriendsBarFragment;
        r11 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r12 = r11.getNumberOfFriendsOnline();
        r11 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r13 = r11.getNumberOfFriendsPending();
        r10.populateViewWithFriends(r12, r13);
    L_0x0045:
        r0 = r47;
        r14 = r0.mNavResFrag;
        if (r14 == 0) goto L_0x00ab;
    L_0x004b:
        r0 = r47;
        r14 = r0.mNavResFrag;
        r0 = r47;
        r15 = r0.mNavResFrag;
        r7 = r15.getView();
        r14.stopTimer(r7);
        r0 = r47;
        r14 = r0.mNavResFrag;
        r5 = r14.isAdded();
        if (r5 == 0) goto L_0x0090;
    L_0x0064:
        r16 = r4.getFragmentManager();
        r0 = r16;
        r17 = r0.beginTransaction();
        r0 = r47;
        r14 = r0.mNavResFrag;
        r0 = r17;
        r17 = r0.detach(r14);
        r0 = r47;
        r14 = r0.mNavResFrag;
        r0 = r17;
        r17 = r0.attach(r14);
        r0 = r17;
        r0.commit();
        r16 = r4.getFragmentManager();
        r0 = r16;
        r0.executePendingTransactions();
    L_0x0090:
        r0 = r47;
        r14 = r0.mNavResFrag;
        r0 = r48;
        r14.onOrientationChanged(r0);
        r18 = new com.waze.LayoutManager$66;
        r0 = r18;
        r1 = r47;
        r0.<init>();
        r19 = 10;
        r0 = r18;
        r1 = r19;
        r4.postDelayed(r0, r1);
    L_0x00ab:
        r0 = r47;
        r0 = r0.mPopupsFragment;
        r21 = r0;
        if (r21 == 0) goto L_0x00bc;
    L_0x00b3:
        r0 = r47;
        r0 = r0.mPopupsFragment;
        r21 = r0;
        r0.onOrientationChanged();
    L_0x00bc:
        r0 = r47;
        r5 = r0.isManualRidePopupShowing();
        if (r5 == 0) goto L_0x00e6;
    L_0x00c4:
        r23 = com.waze.view.popups.ManualRidePopup.class;
        r0 = r47;
        r1 = r23;
        r22 = r0.findPopup(r1);
        r25 = r22;
        r25 = (com.waze.view.popups.ManualRidePopup) r25;
        r24 = r25;
        if (r24 == 0) goto L_0x00e6;
    L_0x00d6:
        r6 = 0;
        r0 = r24;
        r0.hide(r6);
        r0 = r24;
        r0.init();
        r0 = r24;
        r0.reshow();
    L_0x00e6:
        r0 = r47;
        r0 = r0.mMainLayout;
        r26 = r0;
        r6 = 2131690978; // 0x7f0f05e2 float:1.9011015E38 double:1.05319528E-314;
        r0 = r26;
        r7 = r0.findViewById(r6);
        if (r7 == 0) goto L_0x00fc;
    L_0x00f7:
        r6 = 8;
        r7.setVisibility(r6);
    L_0x00fc:
        r0 = r47;
        r0 = r0.mBottomBar;
        r27 = r0;
        if (r27 == 0) goto L_0x0127;
    L_0x0104:
        r0 = r47;
        r0 = r0.mBottomBar;
        r27 = r0;
        r1 = r48;
        r0.onOrientationChanged(r1);
        r28 = com.waze.NativeManager.getInstance();
        r0 = r28;
        r5 = r0.isNavigatingNTV();
        r0 = r47;
        r0 = r0.mBottomBar;
        r27 = r0;
        if (r5 != 0) goto L_0x0265;
    L_0x0121:
        r5 = 1;
    L_0x0122:
        r0 = r27;
        r0.setShowDefaultMessage(r5);
    L_0x0127:
        r0 = r47;
        r0 = r0.context;
        r29 = r0;
        r30 = r0.getResources();
        r6 = 2131361805; // 0x7f0a000d float:1.8343373E38 double:1.0530326467E-314;
        r0 = r30;
        r12 = r0.getDimensionPixelSize(r6);
        r0 = r47;
        r0 = r0.context;
        r29 = r0;
        r30 = r0.getResources();
        r6 = 2131361803; // 0x7f0a000b float:1.8343369E38 double:1.0530326457E-314;
        r0 = r30;
        r13 = r0.getDimensionPixelSize(r6);
        r0 = r47;
        r7 = r0.mBottomBarLayout;
        r31 = r7.getLayoutParams();
        r0 = r31;
        r0.height = r12;
        r0 = r47;
        r7 = r0.mBottomBarRightButtonLayout;
        r31 = r7.getLayoutParams();
        r0 = r31;
        r0.width = r13;
        r0 = r47;
        r0 = r0.mMainLayout;
        r26 = r0;
        r6 = 2131691019; // 0x7f0f060b float:1.9011098E38 double:1.0531953E-314;
        r0 = r26;
        r7 = r0.findViewById(r6);
        r31 = r7.getLayoutParams();
        r0 = r31;
        r0.width = r13;
        r28 = com.waze.AppService.getNativeManager();
        if (r28 == 0) goto L_0x018f;
    L_0x0182:
        r0 = r28;
        r32 = r0.getNavBarManager();
        r0 = r32;
        r1 = r48;
        r0.onOrientationChanged(r1);
    L_0x018f:
        r6 = 2;
        r0 = r48;
        if (r0 != r6) goto L_0x0267;
    L_0x0194:
        r0 = r47;
        r0 = r0.mMainLayout;
        r26 = r0;
        r6 = 2131691009; // 0x7f0f0601 float:1.9011078E38 double:1.053195295E-314;
        r0 = r26;
        r7 = r0.findViewById(r6);
        r31 = r7.getLayoutParams();
        r34 = r31;
        r34 = (android.widget.RelativeLayout.LayoutParams) r34;
        r33 = r34;
        r0 = r47;
        r0 = r0.mMainLayout;
        r26 = r0;
        r30 = r0.getResources();
        r0 = r30;
        r35 = r0.getDisplayMetrics();
        r0 = r35;
        r0 = r0.density;
        r36 = r0;
        r37 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r36 = r37 * r36;
        r0 = r36;
        r12 = (int) r0;
        r0 = r33;
        r0.topMargin = r12;
        r0 = r33;
        r7.setLayoutParams(r0);
        r0 = r47;
        r5 = r0.mHasRidewithDot;
        if (r5 == 0) goto L_0x01fb;
    L_0x01da:
        r0 = r47;
        r0 = r0.mCarpoolNotificationDot;
        r38 = r0;
        r31 = r0.getLayoutParams();
        r40 = r31;
        r40 = (android.widget.FrameLayout.LayoutParams) r40;
        r39 = r40;
        r6 = 19;
        r0 = r39;
        r0.gravity = r6;
        r0 = r47;
        r0 = r0.mCarpoolNotificationDot;
        r38 = r0;
        r1 = r39;
        r0.setLayoutParams(r1);
    L_0x01fb:
        r0 = r47;
        r0 = r0.mSwipeableLayout;
        r41 = r0;
        r6 = 2131691000; // 0x7f0f05f8 float:1.901106E38 double:1.0531952906E-314;
        r0 = r41;
        r7 = r0.findViewById(r6);
        r6 = 8;
        r7.setVisibility(r6);
        r0 = r47;
        r0 = r0.mSwipeableLayout;
        r41 = r0;
        r6 = 2131691002; // 0x7f0f05fa float:1.9011064E38 double:1.0531952916E-314;
        r0 = r41;
        r7 = r0.findViewById(r6);
        r6 = 8;
        r7.setVisibility(r6);
    L_0x0223:
        r42 = com.waze.view.popups.SpotifyPopup.getInstance();
        r0 = r42;
        r5 = r0.isShown();
        if (r5 != 0) goto L_0x0244;
    L_0x022f:
        r43 = com.waze.audioextension.spotify.SpotifyManager.getInstance();
        r0 = r43;
        r5 = r0.SpotifyAvailable();
        if (r5 == 0) goto L_0x0244;
    L_0x023b:
        r42 = com.waze.view.popups.SpotifyPopup.getInstance();
        r0 = r42;
        r0.onOrientationChanged();
    L_0x0244:
        r0 = r47;
        r1 = r48;
        r0.ChangeSearchBarOrientation(r1);
        r0 = r47;
        r0.onSkinChanged();
        r0 = r47;
        r0.updateControlsVisibilty();
        r0 = r47;
        r0 = r0.mTooltipManager;
        r44 = r0;
        r1 = r48;
        r0.onOrientationChanged(r1);
        return;
        goto L_0x0265;
    L_0x0262:
        goto L_0x0122;
    L_0x0265:
        r5 = 0;
        goto L_0x0262;
    L_0x0267:
        r6 = 1;
        r0 = r48;
        if (r0 != r6) goto L_0x0223;
    L_0x026c:
        r0 = r47;
        r0 = r0.mMainLayout;
        r26 = r0;
        r6 = 2131691009; // 0x7f0f0601 float:1.9011078E38 double:1.053195295E-314;
        r0 = r26;
        r7 = r0.findViewById(r6);
        goto L_0x027f;
    L_0x027c:
        goto L_0x0223;
    L_0x027f:
        r31 = r7.getLayoutParams();
        r45 = r31;
        r45 = (android.widget.RelativeLayout.LayoutParams) r45;
        r33 = r45;
        r0 = r47;
        r0 = r0.mMainLayout;
        r26 = r0;
        r30 = r0.getResources();
        r0 = r30;
        r35 = r0.getDisplayMetrics();
        r0 = r35;
        r0 = r0.density;
        r36 = r0;
        r37 = 1125515264; // 0x43160000 float:150.0 double:5.56078426E-315;
        r36 = r37 * r36;
        r0 = r36;
        r12 = (int) r0;
        r0 = r33;
        r0.topMargin = r12;
        r0 = r33;
        r7.setLayoutParams(r0);
        r0 = r47;
        r5 = r0.mHasRidewithDot;
        if (r5 == 0) goto L_0x0223;
    L_0x02b6:
        r0 = r47;
        r0 = r0.mCarpoolNotificationDot;
        r38 = r0;
        r31 = r0.getLayoutParams();
        r46 = r31;
        r46 = (android.widget.FrameLayout.LayoutParams) r46;
        r39 = r46;
        r6 = 81;
        r0 = r39;
        r0.gravity = r6;
        r0 = r47;
        r0 = r0.mCarpoolNotificationDot;
        r38 = r0;
        r1 = r39;
        r0.setLayoutParams(r1);
        goto L_0x027c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.LayoutManager.onOrientationChanged(int):void");
    }

    public LayoutManager(ActivityBase $r1) throws  {
        $r5 = new int[2][];
        $r5[0] = new int[]{C1283R.drawable.main_menu_icon_night_blue, C1283R.drawable.main_menu_icon_night_grey};
        $r5[1] = new int[]{C1283R.drawable.main_menu_icon_blue, C1283R.drawable.main_menu_icon_grey};
        this.menuImageResIds = $r5;
        this.mAutoCloseTransportationTooltipRunnable = new Runnable() {
            public void run() throws  {
                LayoutManager.this.hideTransportationLayerTooltip();
            }
        };
        this.mIsShowingReportAndSpeedometer = true;
        this.mMainLayout = null;
        this.mAppView = null;
        this.mWebView = null;
        this.mEditBoxView = null;
        this.context = null;
        this.FILL_PARENT = -1;
        this.pendingAddStop = false;
        this.context = $r1;
        init();
    }

    public RelativeLayout getMainLayout() throws  {
        return this.mMainLayout;
    }

    public TooltipManager getTooltipManager() throws  {
        return this.mTooltipManager;
    }

    public MapViewWrapper getAppView() throws  {
        return this.mAppView;
    }

    public WzWebView getWebView() throws  {
        return this.mWebView;
    }

    public EditBox getEditBox() throws  {
        if (this.mEditBoxView != null) {
            return (EditBox) this.mEditBoxView.findViewWithTag(EditBox.WAZE_EDITBOX_TAG);
        }
        return null;
    }

    public void SetClosureMap(ClosureMap $r1) throws  {
        this.m_closureMap = $r1;
    }

    public void ClosureMapEnableButton(int $i0) throws  {
        this.m_closureMap.EnableNextButton($i0);
    }

    public void SetInterruptTime(int $i0) throws  {
        this.mPopupsFragment.setInterruptTime($i0);
    }

    public void setAsrDialogView(AsrDialogView $r1) throws  {
        this.mAsrDialogView = $r1;
    }

    public void showCarGasSettingsPopup() throws  {
        if (this.mCarGasSettingsPopup == null) {
            this.mCarGasSettingsPopup = new CarGasSettingsPopUp(AppService.getMainActivity());
            this.mCarGasSettingsPopup.setLayoutParams(new LayoutParams(-1, -1));
            this.mCarGasSettingsPopup.setVisibility(8);
            this.mSwipeableLayout.addView(this.mCarGasSettingsPopup);
        }
        this.mCarGasSettingsPopup.show();
    }

    public void hideCarGasSettingsPopup() throws  {
        if (this.mCarGasSettingsPopup != null) {
            this.mCarGasSettingsPopup.hide();
        }
    }

    public WzWebView CreateWebView(int $i0) throws  {
        this.mWebView = new WzWebView(this.context, $i0);
        this.mWebView.setBackCallback(new C11551());
        return this.mWebView;
    }

    public void ShowWebView(String $r1, WazeRect $r2, int $i0) throws  {
        Logger.m36d("LayoutManager.ShowWebView() aUrl=" + $r1);
        if (this.mWebView != null) {
            this.mSwipeableLayout.removeView(this.mWebView);
            this.mWebView.destroy();
            this.mWebView = null;
        }
        CreateWebView($i0);
        this.mWebView.clearView();
        LayoutParams $r3 = new LayoutParams(($r2.maxx - $r2.minx) + 1, ($r2.maxy - $r2.miny) + 1);
        $r3.leftMargin = $r2.minx;
        $r3.topMargin = $r2.miny;
        this.mSwipeableLayout.addView(this.mWebView, $r3);
        this.mWebView.setVisibility(0);
        this.mSwipeableLayout.bringChildToFront(this.mWebView);
        this.mSwipeableLayout.requestLayout();
        this.mWebView.loadUrl($r1);
        this.mWebView.requestFocus();
    }

    public void ResizeWebView(WazeRect $r1) throws  {
        if (this.mWebView != null) {
            int $i0 = ($r1.maxy - $r1.miny) + 1;
            LayoutParams $r4 = (LayoutParams) this.mWebView.getLayoutParams();
            $r4.width = ($r1.maxx - $r1.minx) + 1;
            $r4.height = $i0;
            $r4.leftMargin = $r1.minx;
            $r4.topMargin = $r1.miny;
            this.mWebView.setLayoutParams($r4);
            this.mSwipeableLayout.requestLayout();
        }
    }

    public void HideWebView() throws  {
        if (this.mWebView != null) {
            HideSoftInput(this.mWebView);
            this.mWebView.setVisibility(8);
            this.mSwipeableLayout.removeView(this.mWebView);
            this.mWebView.destroy();
            this.mWebView = null;
            this.mSwipeableLayout.requestLayout();
            System.gc();
        }
    }

    public EditBox CreateEditBox(int $i0) throws  {
        switch ($i0) {
            case 0:
                this.mEditBoxView = new EditBox(this.context);
                break;
            case 1:
                this.mEditBoxView = View.inflate(this.context, C1283R.layout.editbox_voice, null);
                break;
            default:
                this.mEditBoxView = new EditBox(this.context);
                break;
        }
        return getEditBox();
    }

    public void ShowEditBox(int aTopMargin, int $i1) throws  {
        if (this.mEditBoxView == null) {
            CreateEditBox($i1);
        }
        LayoutParams $r2 = new LayoutParams(-1, -1);
        $r2.leftMargin = 0;
        $r2.rightMargin = 0;
        $r2.topMargin = 0;
        this.mSwipeableLayout.addView(this.mEditBoxView, $r2);
        this.mEditBoxView.setVisibility(0);
        this.mSwipeableLayout.bringChildToFront(this.mEditBoxView);
        this.mSwipeableLayout.requestLayout();
        this.mEditBoxView.requestFocus();
        hideSpotifyButton();
        this.mEditBoxView.postDelayed(new C11572(), 100);
    }

    public void HideEditBox() throws  {
        if (this.mEditBoxView != null) {
            getEditBox().HideSoftInput();
            this.mSwipeableLayout.removeView(this.mEditBoxView);
            this.mSwipeableLayout.requestLayout();
            this.mEditBoxView = null;
            showSpotifyButton();
        }
    }

    public boolean isSideMenuShown() throws  {
        return this.mMainSideMenu.getVisibility() == 0;
    }

    public boolean canGoBack() throws  {
        if (this.mAsrDialogView != null) {
            return true;
        }
        if (this.mMainSideMenu == null) {
            return false;
        }
        if (!this.mMainSideMenu.canReactToBackButton() && ((this.mPopupsFragment == null || !this.mPopupsFragment.isVisible()) && ((this.reportMenu == null || !this.reportMenu.isAdded()) && (this.mNavResFrag == null || !this.mNavResFrag.isVisible())))) {
            if (this.mNavListFrag == null) {
                return false;
            }
            if (!this.mNavListFrag.isVisible()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSwipeableLayoutOpened() throws  {
        return this.mSwipeableLayout.isOpened();
    }

    public void closeSwipeableLayout() throws  {
        if (this.mRightSideMenu != null) {
            this.mRightSideMenu.setVisibility(8);
        }
        if (this.mMainSideMenu != null) {
            this.mMainSideMenu.snapToNormalMode();
        }
        this.mAppView.getMapView().requestFocus();
        if (this.mSearchOnMapView != null && this.mSearchOnMapView.isShowingSearchResults()) {
            this.mSearchOnMapView.reactToBackButton();
        }
    }

    public void unsetSideMenuSearchUpdateHandler() throws  {
        if (this.mMainSideMenu != null) {
            this.mMainSideMenu.unsetSearchAddHandler();
        }
    }

    public boolean shouldIgnoreTakeOver() throws  {
        return this.mSearchOnMapView != null && this.mSearchOnMapView.isShowingSearchResults();
    }

    private boolean isSearchOnMapEnabled() throws  {
        return ConfigManager.getInstance().getConfigValueBool(315);
    }

    public void initSearchOnMap() throws  {
        this.mMainLayout.postDelayed(new C11593(), 1000);
    }

    public void setIsNavigating(boolean $z0) throws  {
        if (this.mSearchOnMapView != null) {
            this.mSearchOnMapView.setIsNavigating($z0);
        }
    }

    public void refreshCarpoolPanel() throws  {
        if (this.mbPaused) {
            this.mNeedRefreshCarpoolPanel = true;
        } else if (this.mRightSideMenu != null) {
            this.mRightSideMenu.setupCarpoolFragment(false);
        } else {
            setRightSideMenu();
            refreshCarpoolPanel();
        }
    }

    public boolean onBackPressed() throws  {
        AsrDialogView $r1 = this.mAsrDialogView;
        this = this;
        if ($r1 != null) {
            this.mAsrDialogView.handleBackClick();
            return true;
        } else if (this.mRightSideMenu != null && this.mRightSideMenu.getVisibility() == 0 && this.mRightSideMenu.reactToBackButton()) {
            return true;
        } else {
            if (this.mMainSideMenu.reactToBackButton()) {
                return true;
            }
            if (this.mSwipeableLayout.isOpened()) {
                if (this.mSwipeableLayout.isAnimating()) {
                    return true;
                }
                this.mSwipeableLayout.getActionProvider().close();
                return true;
            } else if (this.mNavResFrag != null && this.mNavResFrag.isVisible()) {
                this.mNavResFrag.onBackPressed();
                return true;
            } else if (this.mNavListFrag != null && this.mNavListFrag.isVisible()) {
                removeNavListFragment();
                return true;
            } else if (this.reportMenu != null && this.reportMenu.isAdded()) {
                this.reportMenu.onBackPressed();
                return true;
            } else if (this.mPopupsFragment == null || !this.mPopupsFragment.isVisible()) {
                int $i0 = popupsSize();
                if ($i0 > 0 && ((PopUp) this.popups.get($i0 - 1)).onBackPressed()) {
                    return true;
                }
                TooltipManager $r13 = this.mTooltipManager;
                if ($r13.mIsToolTipDisplayed) {
                    $r13 = this.mTooltipManager;
                    $r13.closeTooltip();
                    return true;
                }
                MapViewWrapper $r14 = this.mAppView;
                if ($r14.hasDetailsPopUpInstance()) {
                    $r14 = this.mAppView;
                    $r14.closeDetailsPopup();
                    return true;
                }
                if (this.mSearchOnMapView != null) {
                    SearchOnMapView $r15 = this.mSearchOnMapView;
                    if ($r15.reactToBackButton()) {
                        return true;
                    }
                }
                if (isShowingCarGasSettings()) {
                    CarGasSettingsPopUp $r16 = this.mCarGasSettingsPopup;
                    $r16.reactToBackButton();
                    return true;
                }
                $r14 = this.mAppView;
                $r14.getMapView().requestFocus();
                return false;
            } else {
                this.mPopupsFragment.onBackPressed();
                return true;
            }
        }
    }

    public boolean isShowingSearchOnMapResults() throws  {
        return this.mSearchOnMapView != null && this.mSearchOnMapView.isShowingSearchResults();
    }

    public boolean isShowingCarGasSettings() throws  {
        return this.mCarGasSettingsPopup != null && this.mCarGasSettingsPopup.getVisibility() == 0;
    }

    public void showCandidateRideForRoute(CarpoolDrive $r1, CarpoolRide ride) throws  {
        if (this.mNavResFrag == null || !this.mNavResFrag.isShownFirstTime()) {
            PickupOfferPopUp $r3 = new PickupOfferPopUp(this.context, this);
            int $i0 = ConfigValues.getIntValue(9);
            hideSpotifyButton();
            $r3.show($r1, this.context, $i0);
        } else if (NativeManager.getInstance().isMovingNTV()) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_DONT_SHOW_WHILE_DRIVING, AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r1.getRide().getId());
        } else {
            this.mNavResFrag.showCandidateRideForRoute($r1);
        }
    }

    public void hideCandidateRideForRoute() throws  {
        PickupOfferPopUp $r2 = (PickupOfferPopUp) findPopup(PickupOfferPopUp.class);
        if ($r2 != null) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_HIDDEN_ROUTE_CANCELED, AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r2.getDrive().getRide().getId());
            $r2.hide();
            showSpotifyButton();
        }
    }

    public void showNavListFragment(final INavListDismissed $r1) throws  {
        if (isPaused()) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.showNavListFragment($r1);
                }
            });
            return;
        }
        this.mNavListFrag = new NavigationListFragment();
        this.context.getFragmentManager().beginTransaction().add(C1283R.id.mainMenuFrame, this.mNavListFrag).commit();
        this.mNavListDismissedLissener = $r1;
        this.mSwipeableLayout.setSwipeEnabled(false);
    }

    public void removeNavListFragment() throws  {
        if (this.mNavListFrag != null) {
            this.mNavListFrag.animateOut(new C11625());
        }
    }

    public void restoreReportButton() throws  {
    }

    public void onNavigationStateChanged(boolean $z0) throws  {
        boolean $z1 = DriveToNativeManager.getInstance().isDayMode();
        this.mCarpoolAvailable = CarpoolNativeManager.getInstance().configIsOnNTV();
        setMenuButtonsImages($z0, $z1, this.mCarpoolAvailable);
        this.mBottomBar.setShowDefaultMessage(!$z0);
        this.mBottomBar.setNavigating($z0);
    }

    private void setMenuButtonsImages(boolean $z0, boolean $z1, boolean $z2) throws  {
        byte $b1;
        byte $b0 = (byte) 1;
        ImageView $r3 = (ImageView) this.mMainLayout.findViewById(C1283R.id.mainBottomBarMenuButtonImage);
        if ($z0) {
            $b1 = (byte) 1;
        } else {
            $b1 = (byte) 0;
        }
        if (!$z1) {
            $b0 = (byte) 0;
        }
        $r3.setImageResource(this.menuImageResIds[$b0][$b1]);
        if ($z0) {
            stopGlowAnimation();
        }
        $r3 = (ImageView) this.mMainLayout.findViewById(C1283R.id.mainBottomBarRightIcon);
        $r3.setVisibility(0);
        View $r2 = this.mMainLayout.findViewById(C1283R.id.mainBottomBarSep2);
        if ($z2) {
            this.mBottomBarRightButtonLayout.setOnClickListener(new C11646());
            $r2.setVisibility(0);
            if ($z1) {
                $r3.setImageResource(C1283R.drawable.carpool_icon);
                return;
            } else {
                $r3.setImageResource(C1283R.drawable.carpool_icon_night);
                return;
            }
        }
        $r2.setVisibility(8);
        if ($z0) {
            $r3.setImageResource(C1283R.drawable.eta_big_arrows_enable);
            this.mBottomBarRightButtonLayout.setBackgroundDrawable(this.mBottomBar.getBackground().getConstantState().newDrawable().mutate());
            $r2 = this.mBottomBarRightButtonLayout;
            BottomBarOnTouchListener $r11 = this.mBottomBarOnTouchListener;
            $r2.setOnTouchListener($r11);
            this.mBottomBarRightButtonLayout.setOnClickListener(null);
            return;
        }
        Drawable $r9 = this.mMainLayout.findViewById(C1283R.id.bottomBarStaticMessage).getBackground();
        if ($r9 instanceof ColorDrawable) {
            this.mBottomBarRightButtonLayout.setBackgroundColor(((ColorDrawable) $r9).getColor());
        }
        if (this.mNavBarSoundButton != null) {
            this.mNavBarSoundButton.setSmallSoundButtonVisibility(8);
        }
        this.mBottomBarRightButtonLayout.setOnClickListener(new C11657());
        setSoundIcon();
    }

    private void setSoundIcon() throws  {
        if (!NativeManager.getInstance().isNavigatingNTV()) {
            this.mCarpoolAvailable = CarpoolNativeManager.getInstance().configIsOnNTV();
            if (!this.mCarpoolAvailable) {
                ImageView $r5 = (ImageView) this.mMainLayout.findViewById(C1283R.id.mainBottomBarRightIcon);
                boolean $z0 = DriveToNativeManager.getInstance().isDayMode();
                int $i0 = SettingsSound.getSoundSelectionFromConfig();
                if ($i0 == 0 && $z0) {
                    $r5.setImageResource(C1283R.drawable.sounds_on);
                } else if ($i0 == 0) {
                    $r5.setImageResource(C1283R.drawable.sounds_on_night);
                } else if ($i0 == 1 && $z0) {
                    $r5.setImageResource(C1283R.drawable.sounds_alerts_only);
                } else if ($i0 == 1) {
                    $r5.setImageResource(C1283R.drawable.sounds_alerts_only_night);
                } else if ($i0 == 2 && $z0) {
                    $r5.setImageResource(C1283R.drawable.sounds_mute);
                } else if ($i0 == 2) {
                    $r5.setImageResource(C1283R.drawable.sounds_mute_night);
                }
                $r5.setVisibility(0);
            }
        }
    }

    public void onLogin() throws  {
        this.mCarpoolAvailable = CarpoolNativeManager.getInstance().configIsOnNTV();
        if (this.mBottomBar != null) {
            this.mBottomBar.setCarpoolEnabled(this.mCarpoolAvailable);
        }
        SharedPreferences $r4 = AppService.getAppContext().getSharedPreferences(SHARED_PREF_CARPOOL_STATE_KEY, 0);
        if (this.mCarpoolAvailable) {
            AppService.Post(new C11678(), 2000);
            this.mSwipeableLayout.setSwipeRightEnabled(true);
            this.mSwipeableLayout.setSwipeRightOpenEnabled(true);
            $r4.edit().putBoolean(SHARED_PREF_CARPOOL_ENABLED_DATA, true).apply();
        } else {
            this.mSwipeableLayout.setSwipeRightEnabled(false);
            this.mSwipeableLayout.setSwipeRightOpenEnabled(false);
            $r4.edit().putBoolean(SHARED_PREF_CARPOOL_ENABLED_DATA, false).apply();
        }
        $r4.edit().commit();
        NativeManager.getInstance().onAppActive();
        AppService.Post(new C11689());
        this.mSpeedometerView.onLogin();
        this.mLogin = true;
        updateControlsVisibilty();
    }

    public void setRightSideMenu() throws  {
        if (this.mRightSideMenu == null && (this.mCarpoolAvailable || !this.mLogin)) {
            Logger.m43w("LayoutManager: Carpool available; setting up right side menu");
            this.mSwipeableLayout.setRightSwipeAdditionalTouchView(this.mBottomBarRightButtonLayout);
            RightSideMenu $r1 = new RightSideMenu(this.context);
            $r1.setVisibility(8);
            this.mMainLayout.addView($r1, 0, new LayoutParams(-1, -1));
            this.mRightSideMenu = $r1;
            this.mSwipeableLayout.setRightSwipeListener(this.mRightSideMenu);
            this.mRightSideMenu.setSwipeableLayoutActionProvider(this.mSwipeableLayout.getActionProvider());
            this.mSwipeableLayout.setSwipeRightEnabled(true);
            if (this.mCarpoolAvailable) {
                setMenuButtonsImages(NativeManager.getInstance().isNavigatingNTV(), DriveToNativeManager.getInstance().isDayMode(), this.mCarpoolAvailable);
            }
        } else if (this.mRightSideMenu != null && !this.mCarpoolAvailable) {
            Logger.m43w("LayoutManager: Carpool not available; removing right side menu");
            this.mMainLayout.removeView(this.mRightSideMenu);
            this.mSwipeableLayout.setRightSwipeListener(null);
            this.mSwipeableLayout.setSwipeRightEnabled(false);
            this.mSwipeableLayout.setSwipeRightOpenEnabled(false);
            this.mRightSideMenu = null;
            this.mBottomBarRightButtonLayout.setOnClickListener(null);
            this.mSwipeableLayout.setRightSwipeAdditionalTouchView(null);
            setMenuButtonsImages(NativeManager.getInstance().isNavigatingNTV(), DriveToNativeManager.getInstance().isDayMode(), this.mCarpoolAvailable);
        }
    }

    public void registerCenterOnMeListener() throws  {
        NativeManager.getInstance().setCenteredOnMeListener(new CenteredOnMeListener() {

            class C11541 implements Runnable {
                C11541() throws  {
                }

                public void run() throws  {
                    LayoutManager.this.adjustReportAndSpeedometer();
                }
            }

            public void onCenteredOnMeChanged(boolean isCenteredOnMe) throws  {
                AppService.Post(new C11541());
            }
        });
    }

    public void setRidewithDot() throws  {
        boolean $z0 = CarpoolNativeManager.getInstance().needUserAttention();
        if ($z0 && !this.mHasRidewithDot) {
            this.mHasRidewithDot = true;
            FrameLayout.LayoutParams $r4 = (FrameLayout.LayoutParams) this.mCarpoolNotificationDot.getLayoutParams();
            if (AppService.getAppResources().getConfiguration().orientation == 1) {
                $r4.gravity = 81;
            } else {
                $r4.gravity = 19;
            }
            this.mCarpoolNotificationDot.setLayoutParams($r4);
            this.mCarpoolNotificationDot.setVisibility(0);
            this.mCarpoolNotificationDot.invalidate();
        } else if (!$z0 && this.mHasRidewithDot) {
            this.mHasRidewithDot = false;
            this.mCarpoolNotificationDot.setVisibility(8);
            this.mCarpoolNotificationDot.invalidate();
        }
    }

    public BottomBar getBottomBar() throws  {
        return this.mBottomBar;
    }

    public boolean getMainBottomBarRightButtonRelevantToNav(View $r1) throws  {
        return this.mCarpoolAvailable && this.mBottomBarRightButtonLayout == $r1;
    }

    public void setStreetNameShown(boolean $z0) throws  {
        byte $b0 = (byte) 0;
        this.mCurrentStreetNameShown = $z0;
        if (!this.mCurrentStreetName.getText().toString().isEmpty()) {
            byte $b1;
            TextView $r1 = this.mCurrentStreetName;
            if ($z0) {
                $b1 = (byte) 0;
            } else {
                $b1 = (byte) 8;
            }
            $r1.setVisibility($b1);
            View $r4 = this.mCurrentStreetNameShadow;
            if (!$z0) {
                $b0 = (byte) 8;
            }
            $r4.setVisibility($b0);
        }
    }

    public void setCurrentStreetName(String $r1) throws  {
        if ($r1 == null || $r1.isEmpty()) {
            this.mCurrentStreetName.setText("");
            this.mCurrentStreetName.setVisibility(8);
            this.mCurrentStreetNameShadow.setVisibility(8);
            return;
        }
        this.mCurrentStreetName.setText($r1);
        if (this.mCurrentStreetNameShown) {
            this.mCurrentStreetName.setVisibility(0);
            this.mCurrentStreetNameShadow.setVisibility(0);
        }
    }

    public void setStreetNameColors(int $i0, int $i1) throws  {
        ((GradientDrawable) this.mCurrentStreetName.getBackground()).setColor($i0);
        this.mCurrentStreetName.setTextColor($i1);
    }

    void enterAddressCandidateToPoi(String $r1, String $r2, String $r3, String $r4, AddressItem $r5) throws  {
        if (this.mPoiPopUp != null) {
            this.mPoiPopUp.enterAddressCandidateToPoi($r1, $r2, $r3, $r4, $r5);
        }
    }

    public boolean isPoiTemplateLoaded() throws  {
        return this.mPoiPopUp != null && this.mPoiPopUp.isPoiTemplateLoaded();
    }

    public int getBottomBarHeight() throws  {
        int $i0 = this.context.getResources().getDimensionPixelSize(C1283R.dimen.mainBottomBarHeight);
        int $i1 = 0;
        if (!(this.mNavResFrag == null || !this.mNavResFrag.isVisible() || this.mNavResFrag.getView() == null)) {
            $i1 = this.mNavResFrag.getView().getMeasuredHeight();
        }
        return $i0 + $i1;
    }

    public void displayEndOfDriveReminder() throws  {
        boolean $z0 = ConfigManager.getInstance().getConfigValueBool(350);
        boolean $z1 = ConfigManager.getInstance().getConfigValueBool(351);
        boolean $z2 = false;
        byte $b0 = (byte) -1;
        if (!$z1 && !$z0) {
            ConfigManager.getInstance().setConfigValueBool(350, true);
            $b0 = (byte) 0;
            $z2 = true;
        } else if ($z1) {
            if (!$z0) {
                ConfigManager.getInstance().setConfigValueBool(350, true);
            }
            $b0 = (byte) 1;
            $z2 = true;
        }
        if ($z2) {
            final EndOfDriveReminderPopUp $r1 = r7;
            EndOfDriveReminderPopUp r7 = new EndOfDriveReminderPopUp(this.context);
            $r1.setLayoutParams(new LayoutParams(-1, -1));
            $r1.setMode($b0);
            $r1.setListener(new EndOfDriveReminderPopupListener() {
                public void onReminderPopupDismissed() throws  {
                    LayoutManager.this.mMainLayout.removeView($r1);
                }
            });
            this.mMainLayout.addView($r1);
            $r1.show();
        }
    }

    public int getNavBarTopHeight() throws  {
        int $i0 = 0;
        int $i1 = 0;
        if (this.mNotifBar != null && this.mNotifBar.getView().getVisibility() == 0) {
            $i1 = this.mNotifBar.getView().getMeasuredHeight();
        }
        if (this.mNavBar != null && this.mNavBar.getVisibility() == 0) {
            $i0 = this.mNavBar.getMeasuredHeight();
        }
        return $i1 + $i0;
    }

    public int getCurrentStreetHeight() throws  {
        int $i0 = this.mBottomBarLayout.getTop();
        int $i1 = this.mCurrentStreetName.getTop();
        int $i2 = this.mBottomBarLayout.getMeasuredHeight();
        return ($i0 == 0 || $i1 == 0 || !this.mCurrentStreetName.isShown()) ? $i2 : $i2 + ($i0 - $i1);
    }

    public void setMapIsDark(boolean $z0) throws  {
        int $i1;
        int $i0 = 0;
        float[] $r1 = new float[2];
        if ($z0) {
            $i1 = 1065353216;
        } else {
            $i1 = 0;
        }
        $r1[0] = (float) $i1;
        if (!$z0) {
            $i0 = 1065353216;
        }
        $r1[1] = (float) $i0;
        ObjectAnimator $r2 = ObjectAnimator.ofFloat(this, "darkMapAnimated", $r1);
        $r2.setDuration(200);
        $r2.setInterpolator(new AccelerateDecelerateInterpolator());
        $r2.start();
        View $r5 = this.mMainLayout.findViewById(C1283R.id.mainReportSwipeableButton);
        if ($z0) {
            $r5.setEnabled(false);
            this.mFriendsBarFragment.setFriendsBarVisibilty(false);
            this.mTrafficBarView.setVisibility(8);
            return;
        }
        $r5.setEnabled(true);
        SetFriendsBarVisibilty(this.mRequestedFriendsBarVisible);
        if (this.mShouldShowTrafficBar && this.mTrafficBarView.canShowInternal()) {
            this.mTrafficBarView.setVisibility(0);
        }
    }

    public void setDarkMapAnimated(float $f0) throws  {
        LightingColorFilter $r4;
        LightingColorFilter $r3 = null;
        int $i1 = (int) ((127.0f * $f0) + 128.0f);
        int $i0 = (int) ((119.0f * $f0) + 136.0f);
        LightingColorFilter $r1 = new LightingColorFilter(((($i0 << 8) | $i0) | ($i0 << 16)) | -16777216, 0);
        View $r2 = this.mReportButtonFrame;
        if ($f0 == 1.0f) {
            $r4 = null;
        } else {
            $r4 = $r1;
        }
        DisplayUtils.setChildColorMatrix($r2, $r4, 255, $i1);
        TextView $r5 = this.mCurrentStreetName;
        if ($f0 == 1.0f) {
            $r4 = null;
        } else {
            $r4 = $r1;
        }
        DisplayUtils.setChildColorMatrix($r5, $r4, 255, $i1);
        $r2 = this.mSwipeableLayout.findViewById(C1283R.id.main_speedometer);
        if ($f0 != 1.0f) {
            $r3 = $r1;
        }
        DisplayUtils.setChildColorMatrix($r2, $r3, 255, $i1);
    }

    public MainSideMenu getMainSideMenu() throws  {
        return this.mMainSideMenu;
    }

    public boolean areCarpoolTipsShown() throws  {
        return this.mTooltipManager == null ? false : this.mTooltipManager.areCarpoolTipsShown();
    }

    public void initSwipeLayouts() throws  {
        MainSideMenu $r1 = new MainSideMenu(this.context);
        $r1.setVisibility(8);
        this.mMainLayout.addView($r1, new LayoutParams(-1, -1));
        this.mSwipeableLayout.bringToFront();
        this.mMainLayout.findViewById(C1283R.id.mainTouchToCloseView).bringToFront();
        this.mMainSideMenu = $r1;
        this.mSwipeableLayout.setLeftSwipeListener(this.mMainSideMenu);
        this.mMainSideMenu.setSwipeableLayoutActionProvider(this.mSwipeableLayout.getActionProvider());
        setRightSideMenu();
        refreshRecentsNavigationList();
    }

    private void init() throws  {
        this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        this.mMainLayout = (RelativeLayout) this.inflater.inflate(C1283R.layout.main, null);
        this.mAppView = (MapViewWrapper) this.mMainLayout.findViewById(C1283R.id.mainMainView);
        this.mTrafficBarView = (TrafficBarView) this.mMainLayout.findViewById(C1283R.id.main_trafficBar);
        this.mBottomBar = (BottomBar) this.mMainLayout.findViewById(C1283R.id.mainBottomBar);
        this.mBottomBarLayout = this.mMainLayout.findViewById(C1283R.id.MainBottomBarLayout);
        this.mBottomBarRightButtonLayout = this.mBottomBarLayout.findViewById(C1283R.id.mainBottomBarRight);
        this.mCarpoolNotificationDot = (ImageView) this.mMainLayout.findViewById(C1283R.id.mainBottomBarCarpoolNotificationDot);
        SpotifyPopup.getInstance(AppService.getAppContext(), this);
        this.mMainLayout.findViewById(C1283R.id.mainAsrPopup).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                NativeManager.getInstance().asrCancel(AnalyticsEvents.f50xedcf1877);
            }
        });
        MarginLayoutParams $r13 = (LayoutParams) this.mMainLayout.findViewById(C1283R.id.NavBarLayout).getLayoutParams();
        $r13.bottomMargin--;
        this.mSwipeableLayout = (SwipeableLayout) this.mMainLayout.findViewById(C1283R.id.mainContentWrapper);
        this.mNavBarSoundButton = (NavBarSoundButton) this.mSwipeableLayout.findViewById(C1283R.id.navBarSoundButton);
        this.mAsrMapButtonView = (ImageView) this.mSwipeableLayout.findViewById(C1283R.id.btnAsrMapButton);
        this.mAsrMapButtonView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                AsrSpeechRecognizer.getInstance().beginSpeechSession();
            }
        });
        this.mTransportationLayerTooltip = (RelativeLayout) this.mSwipeableLayout.findViewById(C1283R.id.transportationLayoutTooltip);
        this.mTransportationLayerLabel = (TextView) this.mSwipeableLayout.findViewById(C1283R.id.lblTransportationTooltipLabel);
        this.mTransportationLayerTooltip.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                LayoutManager.this.hideTransportationLayerTooltip();
            }
        });
        this.mMainLayout.findViewById(C1283R.id.mainAsrPopup).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                NativeManager.getInstance().asrCancel(AnalyticsEvents.f50xedcf1877);
            }
        });
        this.mPopupsFragment = new PopupsFragment();
        this.mPopupsFragment.setLayoutManager(this.context, this);
        this.mFriendsBarFragment = (FriendsBarFragment) this.context.getFragmentManager().findFragmentById(C1283R.id.friendsbar_fragment);
        FriendsBarFragment friendsBarFragment = this.mFriendsBarFragment;
        FriendsBarFragment $r23 = friendsBarFragment;
        friendsBarFragment.setLayoutManager(this);
        this.mFriendsBarFragment.setFriendsBarVisibilty(false);
        View $r4 = this.mMainLayout.findViewById(C1283R.id.mainReportSwipeableButton);
        this.mReportButtonFrame = this.mMainLayout.findViewById(C1283R.id.mainReportButtonShadow);
        this.mSpeedometerView = (SpeedometerView) this.mMainLayout.findViewById(C1283R.id.main_speedometer);
        $r4.setOnTouchListener(new MenuButtonOnTouchListener(this, $r4, this.mReportButtonFrame));
        $r4 = this.mMainLayout.findViewById(C1283R.id.mainBottomBarMenuButton);
        $r4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                LayoutManager.this.mTooltipManager.closeTooltip(false, 0);
                if (LayoutManager.this.isPopupsShown()) {
                    LayoutManager.this.callCloseAllPopups(1);
                }
                LayoutManager.this.openMainMenu();
            }
        });
        SwipeableLayout $r14 = this.mSwipeableLayout;
        $r14.setLeftSwipeAdditionalTouchView($r4);
        this.mMainLayout.findViewById(C1283R.id.mainDelayedReportButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                LayoutManager.this.openDelayedReportMenu();
            }
        });
        this.mBottomBar.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) throws  {
            }
        });
        this.mBottomBarOnTouchListener = new BottomBarOnTouchListener(this);
        this.mBottomBar.setOnTouchListener(this.mBottomBarOnTouchListener);
        this.mBottomBarOnTouchListener.setOnCloseListener(new IOnBottomBarClose() {
            public void onClose() throws  {
                if (LayoutManager.this.mNavResFrag != null) {
                    LayoutManager.this.mNavResFrag.dismissMe(false);
                }
            }
        });
        this.mMainLayout.findViewById(C1283R.id.notificationBar).setVisibility(8);
        this.mMainLayout.findViewById(C1283R.id.NavBarLayout).setVisibility(8);
        this.mCurrentStreetName = (TextView) this.mSwipeableLayout.findViewById(C1283R.id.main_currentStreetName);
        this.mCurrentStreetNameShadow = this.mSwipeableLayout.findViewById(C1283R.id.main_currentStreetNameShadow);
        ImageView $r9 = (ImageView) this.mMainLayout.findViewById(C1283R.id.mainBottomBarRightIcon);
        if (AppService.getAppContext().getSharedPreferences(SHARED_PREF_CARPOOL_STATE_KEY, 0).getBoolean(SHARED_PREF_CARPOOL_ENABLED_DATA, false)) {
            $r9.setImageResource(C1283R.drawable.carpool_icon);
        } else {
            $r9.setImageResource(C1283R.drawable.sounds_on);
        }
        $r9.setVisibility(0);
        setSplash();
        BottomNotificationIcon $r33 = (BottomNotificationIcon) this.mMainLayout.findViewById(C1283R.id.mainBottomBarNotificationIconPortrait);
        BottomBar bottomBar = this.mBottomBar;
        BottomBar $r8 = bottomBar;
        bottomBar.initAsMain($r33);
        initViewOrder();
        if (mSpotifyButtonShown) {
            showSpotifyButton();
        }
    }

    private void initViewOrder() throws  {
        for (Object $r1 : new View[]{this.mMainLayout.findViewById(C1283R.id.notificationsLayout), this.mMainLayout.findViewById(C1283R.id.NavBarLayout), this.mMainLayout.findViewById(C1283R.id.tooltipFrameForTouchEvents), this.mBottomBarLayout, this.mMainLayout.findViewById(C1283R.id.MainBottomBarShadow), this.mNavBarSoundButton, this.mMainLayout.findViewById(C1283R.id.navBarSoundButtonExpandedTouchView), this.mMainLayout.findViewById(C1283R.id.navBarSoundButtonMinimizedTouchView), this.mMainLayout.findViewById(C1283R.id.mainBottomBarNotificationIconPortrait), this.mSearchOnMapView, this.mMainLayout.findViewById(C1283R.id.mainNavResFrame), this.mMainLayout.findViewById(C1283R.id.main_popupsFragment), this.mMainLayout.findViewById(C1283R.id.mainMenuFrame)}) {
            if ($r1 != null) {
                ((View) $r1).bringToFront();
            }
        }
        this.mMainLayout.findViewById(C1283R.id.SpotifyButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED);
                LayoutManager.this.openSpotifyPopup();
            }
        });
    }

    public void refreshRecentsNavigationList() throws  {
        if (this.mMainSideMenu != null) {
            this.mMainSideMenu.refreshRecentsNavigationList();
        }
    }

    public boolean isNavListReadyForDisplay() throws  {
        return this.mMainSideMenu != null && this.mMainSideMenu.isNavListReadyForDisplay();
    }

    public void closeShareTooltip() throws  {
        this.mTooltipManager.closeTooltip(false, 1);
    }

    public void SearchBarChangeStatus(boolean $z0) throws  {
        if (!isPopupsShown()) {
            View $r2 = this.mMainLayout.findViewById(C1283R.id.SearchBarLayout);
            View $r3 = this.mMainLayout.findViewById(C1283R.id.SearchBarLayout_ls);
            if ($z0) {
                this.isSearchBarVisible = true;
                ((TextView) this.mMainLayout.findViewById(C1283R.id.SearchBarText)).setText(NativeManager.getInstance().getLanguageString(26));
                ((TextView) this.mMainLayout.findViewById(C1283R.id.SearchBarText_ls)).setText(NativeManager.getInstance().getLanguageString(26));
                if (AppService.getAppResources().getConfiguration().orientation == 1) {
                    slideToBottomWithFade($r2);
                    $r3.setVisibility(8);
                    return;
                }
                $r2.setVisibility(8);
                slideToBottomWithFade($r3);
                return;
            }
            this.isSearchBarVisible = false;
            if (AppService.getAppResources().getConfiguration().orientation == 1) {
                slideToOutWithFade($r2);
                $r3.setVisibility(8);
                return;
            }
            slideToOutWithFade($r3);
            $r2.setVisibility(8);
        }
    }

    public void openPoi(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, boolean $z0, String $r1, int $i6, int $i7, String $r2, String $r3) throws  {
        if (shouldIgnoreTakeOver()) {
            NativeManager.getInstance().CloseDarkView();
            return;
        }
        PopupsFragment $r6 = this.mPopupsFragment;
        if ($r6.isAdded()) {
            final int i = $i0;
            final int i2 = $i1;
            final int i3 = $i2;
            final int i4 = $i3;
            final int i5 = $i4;
            final int i6 = $i5;
            final boolean z = $z0;
            final String str = $r1;
            final int i7 = $i6;
            final int i8 = $i7;
            final String str2 = $r2;
            final String str3 = $r3;
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openPoi(i, i2, i3, i4, i5, i6, z, str, i7, i8, str2, str3);
                }
            });
            return;
        }
        FriendsBarFragment $r8 = this.mFriendsBarFragment;
        $r8.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        if (isCarpoolTickerVisible()) {
            closeCarpoolTicker();
        }
        if (isSpotifyButtonShown()) {
            hideSpotifyButton();
        }
        $r6 = this.mPopupsFragment;
        PopupsFragment $r62 = $r6;
        $r6.openPoi($i0, $i1, $i2, $i3, $i4, $i5, $z0, $r1, $i6, $i7, $r2, $r3);
    }

    public void showTransportationLayerTooltip(final String $r1) throws  {
        this.mSwipeableLayout.postDelayed(new Runnable() {
            public void run() throws  {
                if ((LayoutManager.this.mBottomBarOnTouchListener == null || !LayoutManager.this.mBottomBarOnTouchListener.isOpened()) && AppService.getActiveActivity() == AppService.getMainActivity()) {
                    LayoutManager.this.mTransportationLayerTooltip.setVisibility(0);
                    LayoutManager.this.mTransportationLayerTooltip.setScaleX(0.0f);
                    LayoutManager.this.mTransportationLayerTooltip.setScaleY(0.0f);
                    ViewPropertyAnimatorHelper.initAnimation(LayoutManager.this.mTransportationLayerTooltip).scaleX(1.0f).scaleY(1.0f).setListener(null);
                    LayoutManager.this.mTransportationLayerLabel.setText(String.format(Locale.US, DisplayStrings.displayString(DisplayStrings.DS_TRANSPORTATION_LAYER_TOOLTIP_TITLE), new Object[]{$r1}));
                    LayoutManager.this.mTransportationLayerTooltip.postDelayed(LayoutManager.this.mAutoCloseTransportationTooltipRunnable, 5000);
                    return;
                }
                LayoutManager.this.showTransportationLayerTooltip($r1);
            }
        }, 3500);
    }

    public void hideTransportationLayerTooltip() throws  {
        this.mTransportationLayerTooltip.removeCallbacks(this.mAutoCloseTransportationTooltipRunnable);
        this.mTransportationLayerTooltip.setPivotX((float) this.mTransportationLayerTooltip.getMeasuredWidth());
        this.mTransportationLayerTooltip.setPivotY(((float) this.mTransportationLayerTooltip.getMeasuredHeight()) / WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        ViewPropertyAnimatorHelper.initAnimation(this.mTransportationLayerTooltip).scaleX(0.0f).scaleY(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mTransportationLayerTooltip));
    }

    public void SetDarkViewOffset(int x, int y) throws  {
    }

    @SuppressLint({"NewApi"})
    void makeEasing(View $r1, double finalX) throws  {
        final View view = $r1;
        new AnimationEasingManager(new EasingCallback() {
            public void onValueChanged(double $d0, double oldValue) throws  {
                view.setTranslationY((float) $d0);
            }

            public void onStarted(double value) throws  {
                view.setTranslationY(0.0f);
            }

            public void onFinished(double value) throws  {
                view.setTranslationY(200.0f);
            }
        }).start(Bounce.class, EaseType.EaseOut, 0.0d, 200.0d, 2500);
    }

    public void slideToBottom(View $r1) throws  {
        $r1.setVisibility(0);
        Animation $r2 = AnimationUtils.loadAnimation(this.context, C1283R.anim.slide_in_top);
        $r2.setDuration(200);
        $r2.setInterpolator(new DecelerateInterpolator());
        $r1.setAnimation($r2);
    }

    public void slideToBottomWithFade(View $r1) throws  {
        $r1.setVisibility(0);
        Animation $r2 = AnimationUtils.loadAnimation(this.context, C1283R.anim.slide_in_top_with_fade);
        $r2.setDuration(100);
        $r1.setAnimation($r2);
    }

    public void slideToOut(View $r1) throws  {
        Animation $r2 = AnimationUtils.loadAnimation(this.context, C1283R.anim.slide_out_top);
        $r2.setDuration(200);
        $r2.setInterpolator(new DecelerateInterpolator());
        $r1.setAnimation($r2);
    }

    public void slideToOutWithFade(View $r1) throws  {
        Animation $r2 = AnimationUtils.loadAnimation(this.context, C1283R.anim.slide_out_top_with_fade);
        $r2.setDuration(100);
        $r1.setAnimation($r2);
        $r1.setVisibility(8);
    }

    public void SetPopUpInterrupt(boolean $z0) throws  {
        this.mPopupsFragment.setPopUpInterrupt($z0);
    }

    public void OpenSwipePopups() throws  {
        if (this.mbPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.OpenSwipePopups();
                }
            });
        } else if (this.mPopupsFragment.isAdded()) {
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.OpenSwipePopups();
                }
            });
        } else {
            RefreshBar(MyWazeNativeManager.getInstance().getNumberOfFriendsOnline(), MyWazeNativeManager.getInstance().getNumberOfFriendsPending());
            if (this.mPopupsFragment.hasSwipePopups()) {
                SearchBarChangeStatus(false);
                if (isAlerterShown()) {
                    AlerterPopUp $r9 = this.mAlerterPopUp;
                    $r9.hide();
                    this.mAlerterPopUp = null;
                }
                ActivityBase $r10 = this.context;
                $r10.getFragmentManager().beginTransaction().add(C1283R.id.main_popupsFragment, this.mPopupsFragment).commit();
                $r10 = this.context;
                $r10.getFragmentManager().executePendingTransactions();
                this.mSwipeableLayout.setSwipeOpenEnabled(false);
                if ((NativeManager.getInstance().isNavigatingNTV() || NativeManager.getInstance().isNearNTV()) && this.mNavBar != null) {
                    this.mNavBar.setAlertMode(true, true);
                }
                FriendsBarFragment $r16 = this.mFriendsBarFragment;
                $r16.disappearAllVisibilty();
                this.mTrafficBarView.setVisibility(8);
                this.mPopupsFragment.openSwipePopups();
                Animation animationSet = new AnimationSet(false);
                animationSet = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
                animationSet.setDuration(300);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.addAnimation(animationSet);
                animationSet = new ScaleAnimation(1.0f, 1.0f, 1.025f, 1.0f);
                animationSet.setDuration(100);
                animationSet.setStartOffset(300);
                animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
                animationSet.setFillBefore(true);
                animationSet.addAnimation(animationSet);
                this.mPopupsFragment.getView().startAnimation(animationSet);
                View $r20 = this.mMainLayout.findViewById(C1283R.id.mainTouchToCloseView);
                $r20.setVisibility(0);
                final View view = $r20;
                $r20.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent $r2) throws  {
                        if (LayoutManager.this.mPopupsFragment != null && LayoutManager.this.mPopupsFragment.isVisible()) {
                            int $i0 = LayoutManager.this.mPopupsFragment.getPagerBottom();
                            Rect $r5 = LayoutManager.this.mPopupsFragment.getPageRect();
                            if ($i0 == 0 && $r5 == null) {
                                LayoutManager.this.mPopupsFragment.onBackPressed();
                                view.setOnTouchListener(null);
                                view.setVisibility(8);
                                return false;
                            }
                            int $i1 = LayoutManager.this.mMainLayout.findViewById(C1283R.id.main_popupsFragment).getTop();
                            float $f0 = $r2.getY();
                            float $f1 = $r2.getX();
                            if ($f0 < ((float) ($i0 + $i1)) && $f0 > ((float) $i1) && $f1 > ((float) $r5.left) && $f1 < ((float) $r5.right)) {
                                return false;
                            }
                            LayoutManager.this.mPopupsFragment.onBackPressed();
                        }
                        view.setOnTouchListener(null);
                        view.setVisibility(8);
                        return false;
                    }
                });
            }
        }
    }

    public void preparePoi(int $i0, String $r1) throws  {
        if (shouldIgnoreTakeOver()) {
            NativeManager.getInstance().CloseDarkView();
            return;
        }
        if (this.mPoiPopUp == null) {
            this.mPoiPopUp = new PoiPopUp(this.context, this);
        }
        this.mPoiPopUp.prepare($i0, $r1);
    }

    public boolean isPoiPreloaded(int $i0) throws  {
        return this.mPoiPopUp == null ? false : this.mPoiPopUp.isPreloaded($i0);
    }

    public void closePoi() throws  {
        if (this.mPoiPopUp != null) {
            this.mPoiPopUp.hide();
        }
    }

    public void ShowBonusWebPopup(int $i0, String $r1, int $i1, int $i2) throws  {
        new BonusWebPopUP(this.context, this).show($i0, $r1, $i1, $i2);
    }

    public void openUserPopup(UserData $r1, int $i0, int $i1) throws  {
        if (this.mUserPopUp == null) {
            this.mUserPopUp = new UserPopUp(this.context, this);
        }
        this.mUserPopUp.show($r1, $i0, $i1);
    }

    public void UpdateUserPopup(int $i0, int $i1) throws  {
        if (this.mUserPopUp == null) {
            this.mUserPopUp = new UserPopUp(this.context, this);
        }
        this.mUserPopUp.update($i0, $i1);
    }

    public void closeUserPopup() throws  {
        if (this.mUserPopUp != null) {
            this.mUserPopUp.hide();
        }
    }

    public void onSpotifyClose() throws  {
        this.mPopupsFragment.setLastPageNumber(-1);
        showSpotifyButton();
    }

    public void callCloseAllPopups(int $i0) throws  {
        callCloseAllPopups($i0, PopupCloseReason.popup_close_reason_user_click.ordinal());
    }

    public void callCloseAllPopups(int $i0, int $i1) throws  {
        NativeManager $r1 = NativeManager.getInstance();
        if (this.mPopupsFragment.getLastPageNumber() >= 0) {
            NativeManager.getInstance().PopupAction(PopupAction.popup_hidden.ordinal(), this.mPopupsFragment.getLastPageNumber(), 0, $i1);
        }
        this.mPopupsFragment.setLastPageNumber(-1);
        $r1.CloseAllPopups($i0);
        showSpotifyButton();
    }

    public void preCloseAllPopups(int $i0) throws  {
        if (this.mPopupsFragment.getLastPageNumber() >= 0) {
            NativeManager.getInstance().PopupAction(PopupAction.popup_hidden.ordinal(), this.mPopupsFragment.getLastPageNumber(), 0, $i0);
            this.mPopupsFragment.setLastPageNumber(-1);
        }
    }

    public void doneCloseAllPopups() throws  {
        if (this.mbPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.doneCloseAllPopups();
                }
            });
            return;
        }
        View $r5 = this.mMainLayout.findViewById(C1283R.id.mainTouchToCloseView);
        $r5.setOnTouchListener(null);
        $r5.setVisibility(8);
        RefreshBar(MyWazeNativeManager.getInstance().getNumberOfFriendsOnline(), MyWazeNativeManager.getInstance().getNumberOfFriendsPending());
        Animation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation $r1) throws  {
                if (LayoutManager.this.mbPaused) {
                    final Animation animation = $r1;
                    LayoutManager.this.mOnResume.add(new Runnable() {
                        public void run() throws  {
                            AnonymousClass29.this.onAnimationEnd(animation);
                        }
                    });
                    return;
                }
                LayoutManager.this.mPopupsFragment.doneCloseAllPopups();
                LayoutManager.this.context.getFragmentManager().beginTransaction().remove(LayoutManager.this.mPopupsFragment).commit();
                LayoutManager.this.mSwipeableLayout.setSwipeOpenEnabled(true);
                LayoutManager.this.context.getFragmentManager().executePendingTransactions();
                if (LayoutManager.this.mOnPopupsClosed.isEmpty()) {
                    if (LayoutManager.this.mNavBar != null) {
                        LayoutManager.this.mNavBar.setAlertMode(false);
                    }
                    LayoutManager.this.RefreshBar(MyWazeNativeManager.getInstance().getNumberOfFriendsOnline(), MyWazeNativeManager.getInstance().getNumberOfFriendsPending());
                    return;
                }
                LayoutManager.this.context.getFragmentManager().executePendingTransactions();
                while (!LayoutManager.this.mOnPopupsClosed.isEmpty()) {
                    ((Runnable) LayoutManager.this.mOnPopupsClosed.remove(0)).run();
                }
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }
        });
        PopupsFragment $r9 = this.mPopupsFragment;
        $r5 = $r9.getView();
        if ($r5 != null) {
            $r5.startAnimation(translateAnimation);
        }
        showSpotifyButton();
    }

    public void openAlertPopup(RTAlertsAlertData $r1, int $i0, int $i1, String $r2, int $i2) throws  {
        if (this.mPopupsFragment.isAdded()) {
            final RTAlertsAlertData rTAlertsAlertData = $r1;
            final int i = $i0;
            final int i2 = $i1;
            final String str = $r2;
            final int i3 = $i2;
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openAlertPopup(rTAlertsAlertData, i, i2, str, i3);
                }
            });
            return;
        }
        if (isCarpoolTickerVisible()) {
            closeCarpoolTicker();
        }
        if (isSpotifyButtonShown()) {
            hideSpotifyButton();
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openAlertPopup($r1, $i0, $i1, $r2, $i2);
    }

    public void openSpotifyPopup() throws  {
        Log.d("SpotifyManager", "openSpotifyPopup");
        if (SpotifyManager.getInstance().needsAuthorization()) {
            Log.d("SpotifyManager", "openSpotifyPopup needsAuthorization");
            SpotifyManager.getInstance().authorize();
            return;
        }
        SpotifyManager.getInstance().fetchSugegstedContent();
        if (this.mPopupsFragment.isAdded()) {
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openSpotifyPopup();
                }
            });
            return;
        }
        if (isCarpoolTickerVisible()) {
            closeCarpoolTicker();
        }
        if (isSpotifyButtonShown()) {
            hideSpotifyButton();
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openSpotifyPopup();
        OpenSwipePopups();
    }

    public void closeFriendsOnlinePopup() throws  {
        PopUp $r1 = findPopup(FriendsOnlinePopUp.class);
        if ($r1 != null) {
            $r1.hide();
        }
    }

    public void closeSpotifyPopup() throws  {
        SpotifyPopup.getInstance().dismiss();
    }

    public void openPingPopup(RTAlertsAlertData $r1, boolean $z0, String $r2, int $i0) throws  {
        if (this.mPopupsFragment.isAdded()) {
            final RTAlertsAlertData rTAlertsAlertData = $r1;
            final boolean z = $z0;
            final String str = $r2;
            final int i = $i0;
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openPingPopup(rTAlertsAlertData, z, str, i);
                }
            });
            return;
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openPingPopup($r1, $z0, $r2, $i0);
        hideSpotifyButton();
    }

    public void showFriendsTooltip() throws  {
        if (AppService.getMainActivity() != null && AppService.getMainActivity().IsRunning() && this.mShowFriendsTip > 0) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    try {
                        if (AppService.getMainActivity() != null && NativeManager.getInstance() != null && AppService.getMainActivity().areAllSignUpDialogsClosed()) {
                            LayoutManager.this.mShowFriendsTip = ConfigManager.getInstance().checkConfigDisplayCounter(4, true);
                            if (LayoutManager.this.mShowFriendsTip > 0) {
                                try {
                                    LayoutManager.this.mTooltipManager.showToolTip(6, 0, null, 0, 0);
                                } catch (Exception $r6) {
                                    Log.e(Logger.TAG, String.format("failed showing friendsTooltip. Error: %s    stack: %s", new Object[]{$r6.getMessage(), $r6.getStackTrace()}));
                                }
                            }
                        }
                    } catch (Exception $r10) {
                        Log.d(Logger.TAG, String.format("Haven't queued showFriendsTooltip request. Environment is not fully initialized yet. Error: %s    stack: %s", new Object[]{$r10.getMessage(), $r10.getStackTrace()}));
                    }
                }
            }, 5000);
        }
    }

    public void RefreshBar(int $i0, int $i1) throws  {
        updateUserOnlineFriends($i0);
        View $r2 = this.mSwipeableLayout.findViewById(C1283R.id.mainFriendsControls);
        if ($r2.getVisibility() == 0) {
            $r2.setVisibility(0);
        }
        if (MainActivity.IsSDKBound()) {
            this.mFriendsBarFragment.setFriendsBarVisibilty(false);
        } else if ((MyWazeNativeManager.getInstance().getFacebookLoggedInNTV() || MyWazeNativeManager.getInstance().getContactLoggedInNTV()) && !this.isSplashVisible && !isAlerterShown() && !isPopupsShown() && !this.mPopupsFragment.isAdded() && !NativeManager.getInstance().GetShowScreenIconsNTV() && !isControlsVisible()) {
            this.mFriendsBarFragment.setFriendsBarVisibilty(true);
            showFriendsTooltip();
            this.mFriendsBarFragment.populateViewWithFriends($i0, $i1);
        } else if (MyWazeNativeManager.getInstance().getFacebookLoggedInNTV() || MyWazeNativeManager.getInstance().getContactLoggedInNTV() || this.isSplashVisible || isAlerterShown() || isPopupsShown() || this.mPopupsFragment.isAdded() || NativeManager.getInstance().GetShowScreenIconsNTV() || isControlsVisible()) {
            this.mFriendsBarFragment.setFriendsBarVisibilty(false);
        } else {
            showFriendsTooltip();
            this.mFriendsBarFragment.setNoFriends();
        }
    }

    public void SetFriendsBarVisibilty(boolean $z0) throws  {
        if (MainActivity.IsSDKBound()) {
            this.mFriendsBarFragment.setFriendsBarVisibilty(false);
            return;
        }
        this.mRequestedFriendsBarVisible = $z0;
        if (!$z0 || isAlerterShown() || isPopupsShown() || this.mPopupsFragment.isAdded()) {
            this.mFriendsBarFragment.setFriendsBarVisibilty(false);
            return;
        }
        this.mFriendsBarFragment.setFriendsBarVisibilty(true);
        this.mFriendsBarFragment.populateViewWithFriends(MyWazeNativeManager.getInstance().getNumberOfFriendsOnline(), MyWazeNativeManager.getInstance().getNumberOfFriendsPending());
    }

    public boolean isControlsVisible() throws  {
        boolean $z0 = false;
        if (this.mSearchOnMapView != null) {
            $z0 = this.mSearchOnMapView.isShowingSearchResults();
        }
        return ($z0 || this.mSwipeableLayout.findViewById(C1283R.id.mainZoomControls).getVisibility() != 0) ? $z0 : true;
    }

    public void updateControlsVisibilty() throws  {
        SetControlsVisibilty(this.mSwipeableLayout.findViewById(C1283R.id.mainFriendsControls).getVisibility() == 0);
    }

    public void openCommentPopup(final RTAlertsCommentData $r1, final String $r2, final int $i0) throws  {
        if (this.mPopupsFragment.isAdded()) {
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openCommentPopup($r1, $r2, $i0);
                }
            });
            return;
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openCommentPopup($r1, $r2, $i0);
    }

    public void openThumbsUpPopup(final RTAlertsThumbsUpData $r1, final String $r2, final int $i0) throws  {
        if (this.mPopupsFragment.isAdded()) {
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openThumbsUpPopup($r1, $r2, $i0);
                }
            });
            return;
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openThumbsUpPopup($r1, $r2, $i0);
    }

    public void openBeepPopup(final RTAlertsThumbsUpData $r1, final String $r2, final int $i0) throws  {
        if (this.mPopupsFragment.isAdded()) {
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openBeepPopup($r1, $r2, $i0);
                }
            });
            return;
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openBeepPopup($r1, $r2, $i0);
    }

    public void openSharePopup(FriendUserData $r1, int $i0, String $r2, String $r3) throws  {
        if (this.mPopupsFragment.isAdded()) {
            final FriendUserData friendUserData = $r1;
            final int i = $i0;
            final String str = $r2;
            final String str2 = $r3;
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openSharePopup(friendUserData, i, str, str2);
                }
            });
            return;
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openSharePopup($r1, $i0, $r2, $r3);
        if (this.mNavResFrag != null) {
            this.mNavResFrag.updateData();
        }
    }

    public void openAlertTicker(int $i0, String $r1, String $r2, int $i1) throws  {
        this.mAlertTicker = new AlertTicker(this.context, this);
        if (isCarpoolTickerVisible()) {
            Logger.m43w("LayoutManager: Not showing ticker of type " + $i0 + " because carpool ticker is shown");
            return;
        }
        if (this.mAlertTicker.IsShown()) {
            this.mAlertTicker.ChangeImage(true);
        }
        this.mAlertTicker.setVisibility(0);
        this.mAlertTicker.show($i0, $r2, $r1, $i1);
        if (this.mNavBar != null && this.mNavBar.isSubViewDisplayed()) {
            hideAlertTicker();
        }
        hideSpotifyButton();
    }

    UpcomingCarpoolBar getUpcomingCarpoolBar() throws  {
        if (this.mUpcomingCarpoolBar == null) {
            this.mUpcomingCarpoolBar = new UpcomingCarpoolBar(this.context, this);
        }
        return this.mUpcomingCarpoolBar;
    }

    public void openUpcomingCarpoolBar(CarpoolDrive $r1) throws  {
        UpcomingCarpoolBar $r2 = getUpcomingCarpoolBar();
        if ((this.mPopupsFragment == null || (!this.mPopupsFragment.isPoiShowing() && !this.mPopupsFragment.isVisible())) && !isAlerterShown() && !isPopUpShown(CarpoolMessagePopup.class)) {
            if (!(this.mSearchOnMapView == null || !this.mSearchOnMapView.isSearchBarVisibile() || $r2.isShown())) {
                this.mSearchOnMapView.setIsShowingCarpoolBanner(true);
            }
            $r2.show($r1);
            CloseAllAlertTickers();
        }
    }

    public void closeCarpoolTicker() throws  {
        Logger.m36d("Manual rides: closing carpool ticker");
        if (this.mUpcomingCarpoolBar != null && this.mUpcomingCarpoolBar.isShown()) {
            this.mUpcomingCarpoolBar.hide();
        }
    }

    public void onCarpoolTickerClosed() throws  {
        if (this.mSearchOnMapView != null) {
            this.mSearchOnMapView.setIsShowingCarpoolBanner(false);
        }
    }

    public boolean isCarpoolTickerVisible() throws  {
        return this.mUpcomingCarpoolBar != null && this.mUpcomingCarpoolBar.isShown();
    }

    public int GetAlertTickerType() throws  {
        if (this.mAlertTicker == null || !this.mAlertTicker.IsShown()) {
            return -1;
        }
        return this.mAlertTicker.getType();
    }

    public void CloseAlertTicker() throws  {
        CloseAlertTicker(0);
    }

    public void CloseAlertTicker(int $i0) throws  {
        if (this.mAlertTicker != null && this.mAlertTicker.IsShown()) {
            this.mAlertTicker.RemoveAlertTicker($i0);
            this.mAlertTicker.Refresh();
            if (!this.mAlertTicker.IsShown()) {
                this.mAlertTicker = null;
            }
        }
        if (this.mNavBar != null && this.mNavBar.isSubViewDisplayed()) {
            this.mNavBar.setOnSubViewHidden(null);
        }
    }

    public void CloseAllAlertTickersOfType(int $i0) throws  {
        if ($i0 != 4) {
            if (this.mAlertTicker != null && this.mAlertTicker.IsShown()) {
                this.mAlertTicker.RemoveAllAlertTickersOfType($i0);
                this.mAlertTicker.Refresh();
                if (!this.mAlertTicker.IsShown()) {
                    this.mAlertTicker = null;
                }
            }
            if (this.mNavBar != null && this.mNavBar.isSubViewDisplayed()) {
                this.mNavBar.setOnSubViewHidden(null);
                return;
            }
            return;
        }
        Logger.m36d("Manual rides: closing carpool ticker");
        if (this.mUpcomingCarpoolBar != null) {
            this.mUpcomingCarpoolBar.hide();
            this.mUpcomingCarpoolBar.setShouldShow(false);
        }
    }

    public void CloseAllAlertTickers() throws  {
        if (this.mAlertTicker != null && this.mAlertTicker.IsShown()) {
            this.mAlertTicker.RemoveAllAlertTickers();
            this.mAlertTicker.Refresh();
            if (!this.mAlertTicker.IsShown()) {
                this.mAlertTicker = null;
            }
        }
        if (this.mNavBar != null && this.mNavBar.isSubViewDisplayed()) {
            this.mNavBar.setOnSubViewHidden(null);
        }
    }

    public boolean hideAlertTicker() throws  {
        if (this.mAlertTicker == null || !this.mAlertTicker.IsShown()) {
            return false;
        }
        this.mAlertTicker.setVisibility(8);
        showSpotifyButton();
        if (this.mNavBar != null && this.mNavBar.isSubViewDisplayed()) {
            this.mNavBar.setOnSubViewHidden(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.reshowAlertTicker();
                }
            });
        }
        return true;
    }

    public boolean reshowAlertTicker() throws  {
        if (this.mAlertTicker == null) {
            return false;
        }
        if (!this.mAlertTicker.IsShown()) {
            return false;
        }
        this.mAlertTicker.setVisibility(0);
        hideSpotifyButton();
        return true;
    }

    public void closeThumbsUpPopup() throws  {
        PopUp $r2 = this.mPopupsFragment.findPopupOfType(ThumbsUpPopUP.class);
        if ($r2 != null) {
            $r2.hide();
        }
    }

    public void showTrafficDetectionPopup(boolean $z0, boolean $z1, boolean $z2) throws  {
        new TrafficDetectionPopUp(this.context, this, $z0, $z1, $z2).show();
    }

    public void showEtaUpdatePopUp(int $i0, String $r1, String $r2, String $r3, int $i1) throws  {
        if (shouldIgnoreTakeOver()) {
            NativeManager.getInstance().CloseDarkView();
        } else {
            new EtaUpdatePopUp(this.context, this).show($i0, $r1, $r2, $r3, $i1);
        }
    }

    public void showPickupCanceledPopUp(String $r1, String $r2, int $i0, AddressItem $r3) throws  {
        new PickupCanceledPopUp(this.context, this).show($r1, $r2, this.context, $i0, $r3);
    }

    public void informProfilePictureChanged() throws  {
        if (this.mMainSideMenu != null) {
            this.mMainSideMenu.informProfilePictureChanged();
        }
    }

    public void updateUserData() throws  {
        if (this.mMainSideMenu != null) {
            this.mMainSideMenu.updateUserData();
        }
    }

    public void updateUserOnlineFriends(int $i0) throws  {
        if (this.mMainSideMenu != null) {
            this.mMainSideMenu.updateUserOnlineFriends($i0);
        }
    }

    public boolean reportMenuShown() throws  {
        return this.reportMenu != null && this.reportMenu.isVisible;
    }

    public boolean isAlerterShown() throws  {
        return this.mAlerterPopUp != null && this.mAlerterPopUp.isShown();
    }

    public void showNotificationMessage(String $r1, String $r2, int $i0) throws  {
        if (this.mNotifBar == null) {
            this.mNotifBar = new NotificationBar(this.mMainLayout.findViewById(C1283R.id.notificationBar), this.context);
        }
        this.mNotifBar.showMessage($r1, $r2, $i0);
    }

    public boolean isShowingNotificationBar() throws  {
        return this.mMainLayout.findViewById(C1283R.id.notificationBar).getVisibility() == 0;
    }

    public void showMessageTicker(String $r1, String $r2, String $r3, int $i0) throws  {
        if (this.mTicker == null) {
            this.mTicker = new MessageTicker(this.context, this);
        }
        this.mTicker.show($r1, $r2, $r3, $i0);
    }

    public void showDueToPopup(String $r1, boolean $z0) throws  {
        new DueToPopUp(this.context, this, $r1, $z0).show(true);
    }

    public void showAlerterPopup(String $r1, String $r2, String $r3, boolean $z0, boolean $z1, int $i0, int $i1, boolean $z2) throws  {
        if (SpotifyPopup.getInstance().isShown()) {
            closeSpotifyPopup();
        } else if (this.mNavBar != null && this.mNavBar.isSubViewDisplayed()) {
            return;
        } else {
            if (this.mPopupsFragment != null) {
                PopupsFragment $r9 = this.mPopupsFragment;
                if ($r9.isVisible()) {
                    return;
                }
            }
        }
        if (isCarpoolTickerVisible()) {
            closeCarpoolTicker();
        }
        if (isSpotifyButtonShown()) {
            hideSpotifyButton();
        }
        this.mAlerterPopUp = new AlerterPopUp(this.context, this);
        this.mAlerterPopUp.show($r1, $r2, $r3, $z0, $z1, $i0, $i1, $z2);
        if (getNavBar() != null) {
            getNavBar().setThenHiddenForAlerter(true);
        } else {
            this.navbarThenHiddenForAlerter = true;
        }
        RefreshBar(MyWazeNativeManager.getInstance().getNumberOfFriendsOnline(), MyWazeNativeManager.getInstance().getNumberOfFriendsPending());
    }

    public void showManualRidePopup(CarpoolDrive $r1, CarpoolRidePickupMeetingDetails $r2, int $i0) throws  {
        callCloseAllPopups(1);
        new ManualRidePopup(this.context, this).show($r1, $r2, $i0);
    }

    public void hideManualRidePopup() throws  {
        hidePopUp(ManualRidePopup.class);
    }

    public void collapseManualRidePopupToTicker() throws  {
        hidePopUp(ManualRidePopup.class);
    }

    public boolean isManualRidePopupShowing() throws  {
        return isPopUpShown(ManualRidePopup.class);
    }

    public void showFullScreenRidePopup(CarpoolDrive $r1, CarpoolRidePickupMeetingDetails $r2, int $i0) throws  {
        new CarpoolTripDialog(AppService.getMainActivity(), $r1, $r2, $i0, false).show();
    }

    public void showRiderMessagePopup(final CarpoolRide $r1, final CarpoolUserData $r2, final CarpoolMessage $r3) throws  {
        callCloseAllPopups(1);
        if (this.mUpcomingCarpoolBar != null && this.mUpcomingCarpoolBar.isShown()) {
            this.mUpcomingCarpoolBar.hide();
        }
        AppService.Post(new Runnable() {
            public void run() throws  {
                new CarpoolMessagePopup(LayoutManager.this.context, LayoutManager.this).show($r1, $r2, $r3);
            }
        }, 300);
    }

    public void showFriendOnTheWayPopup(FriendUserData friendLocation, int iTimeOut) throws  {
    }

    public void updateAlerterPopup(String $r1, String $r2, String $r3) throws  {
        if (isAlerterShown()) {
            this.mAlerterPopUp.update($r1, $r2, $r3);
        }
    }

    public void setAlerterPopupCloseTime(int $i0) throws  {
        if (isAlerterShown()) {
            this.mAlerterPopUp.setCloseTime($i0);
        }
    }

    public void hideAlerterPopup() throws  {
        if (isAlerterShown()) {
            this.mAlerterPopUp.hide();
            this.mAlerterPopUp = null;
            RefreshBar(MyWazeNativeManager.getInstance().getNumberOfFriendsOnline(), MyWazeNativeManager.getInstance().getNumberOfFriendsPending());
            showSpotifyButton();
            if (getNavBar() != null) {
                getNavBar().setThenHiddenForAlerter(false);
            } else {
                this.navbarThenHiddenForAlerter = false;
            }
        }
    }

    protected void openVoiceControlTip() throws  {
        NativeManager $r2 = AppService.getNativeManager();
        final Dialog $r1 = new Dialog(AppService.getActiveActivity(), C1283R.style.Dialog);
        AppService.getActiveActivity().setDialog($r1);
        $r1.setContentView(C1283R.layout.voice_control_tip);
        ((TextView) $r1.findViewById(C1283R.id.voiceTipButtonText1)).setText($r2.getLanguageString(292));
        ((TextView) $r1.findViewById(C1283R.id.voiceTipButtonText2)).setText($r2.getLanguageString((int) DisplayStrings.DS_ENABLE));
        ((TextView) $r1.findViewById(C1283R.id.voiceTipTitle)).setText($r2.getLanguageString((int) DisplayStrings.DS_DRIVE_SAFE));
        ((TextView) $r1.findViewById(C1283R.id.voiceTipText)).setText($r2.getLanguageString((int) DisplayStrings.DS_PASSING_YOUR_HAND));
        $r1.findViewById(C1283R.id.voiceTipButton1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                try {
                    $r1.cancel();
                    $r1.dismiss();
                } catch (Exception e) {
                }
            }
        });
        $r1.findViewById(C1283R.id.voiceTipButton2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                try {
                    MainActivity $r3 = AppService.getMainActivity();
                    $r3.startActivity(new Intent($r3, SettingsVoiceCommandsActivity.class));
                    $r1.cancel();
                    $r1.dismiss();
                } catch (Exception e) {
                }
            }
        });
        $r1.show();
    }

    public void openVoicePopup() throws  {
        this.mMainLayout.findViewById(C1283R.id.mainAsrPopup).setVisibility(0);
        if (DriveToNativeManager.getInstance().isDayMode()) {
            this.mMainLayout.findViewById(C1283R.id.mainAsrPopup).setBackgroundResource(C1283R.drawable.voice_command_day);
        } else {
            this.mMainLayout.findViewById(C1283R.id.mainAsrPopup).setBackgroundResource(C1283R.drawable.voice_command_night);
        }
        changeVoicePopupState(false);
    }

    public void adjustReportAndSpeedometer() throws  {
        if (this.mReportButtonFrame != null && this.mSpeedometerView != null) {
            boolean $z0 = NativeManager.getInstance().isCenteredOnMe();
            if ($z0 && !this.mIsShowingReportAndSpeedometer) {
                this.mIsShowingReportAndSpeedometer = true;
                this.mReportButtonFrame.setVisibility(0);
                this.mSpeedometerView.setVisibility(0);
                ViewPropertyAnimatorHelper.initAnimation(this.mReportButtonFrame).translationX(0.0f).setListener(null);
                ViewPropertyAnimatorHelper.initAnimation(this.mSpeedometerView).translationX(0.0f).setListener(null);
                showSpotifyButton();
            } else if (!$z0 && this.mIsShowingReportAndSpeedometer) {
                this.mIsShowingReportAndSpeedometer = false;
                ViewPropertyAnimatorHelper.initAnimation(this.mReportButtonFrame).translationX((float) this.mReportButtonFrame.getMeasuredWidth()).setListener(ViewPropertyAnimatorHelper.createInvisibleWhenDoneListener(this.mReportButtonFrame));
                ViewPropertyAnimatorHelper.initAnimation(this.mSpeedometerView).translationX((float) (-this.mSpeedometerView.getMeasuredWidth())).setListener(ViewPropertyAnimatorHelper.createInvisibleWhenDoneListener(this.mSpeedometerView));
                hideSpotifyButton();
            }
        }
    }

    public void changeVoicePopupState(boolean state) throws  {
        TextView $r4 = (TextView) this.mMainLayout.findViewById(C1283R.id.mainAsrText1);
        $r4.setText(AppService.getNativeManager().getLanguageString((int) DisplayStrings.DS_WAIT));
        $r4.setBackgroundResource(C1283R.drawable.status_red);
        ProgressBar $r6 = (ProgressBar) this.mMainLayout.findViewById(C1283R.id.mainAsrProgress);
        $r4.startAnimation(AnimationUtils.loadAnimation(this.context, C1283R.anim.flicker));
        $r4.setVisibility(0);
        $r6.setVisibility(8);
    }

    public void closeVoicePopup() throws  {
        this.mMainLayout.findViewById(C1283R.id.mainAsrPopup).setVisibility(8);
        ((TextView) this.mMainLayout.findViewById(C1283R.id.mainAsrText1)).clearAnimation();
    }

    public void openNavResults(String $r1, String $r2, String $r3, String $r4, String $r5, int $i0, String $r6, int $i1, boolean $z0, boolean $z1, String $r7, String $r8, int $i2, String $r9, String $r10, boolean $z2, int $i3, boolean $z3, int $i4, boolean $z4, String $r11, String $r12, String $r13, int $i5, int $i6, String $r14, String $r15, String $r16, FriendUserData[] $r17, boolean $z5, boolean $z6, boolean $z7, boolean $z8, boolean $z9, boolean $z10) throws  {
        final FriendUserData[] friendUserDataArr = $r17;
        final String str = $r1;
        final String str2 = $r2;
        final String str3 = $r3;
        final String str4 = $r4;
        final String str5 = $r5;
        final int i = $i0;
        final String str6 = $r6;
        final int i2 = $i1;
        final boolean z = $z0;
        final boolean z2 = $z1;
        final String str7 = $r7;
        final String str8 = $r8;
        final int i3 = $i2;
        final String str9 = $r9;
        final String str10 = $r10;
        final boolean z3 = $z2;
        final int i4 = $i3;
        final boolean z4 = $z3;
        final int i5 = $i4;
        final boolean z5 = $z4;
        final String str11 = $r11;
        final String str12 = $r12;
        final String str13 = $r13;
        final int i6 = $i5;
        final int i7 = $i6;
        final String str14 = $r14;
        final String str15 = $r15;
        final String str16 = $r16;
        final boolean z6 = $z5;
        final boolean z7 = $z6;
        final boolean z8 = $z7;
        final boolean z9 = $z8;
        final boolean z10 = $z9;
        final boolean z11 = $z10;
        AnonymousClass44 anonymousClass44 = new Runnable() {
            public void run() throws  {
                LayoutManager $r9 = LayoutManager.this;
                $r9.stopGlowAnimation();
                $r9 = LayoutManager.this;
                if ($r9.pendingAddStop) {
                    LayoutManager.this.pendingAddStop = false;
                    AppService.getMainActivity().startStopPointActivity(true);
                    return;
                }
                LayoutManager.this.mNotifyFriends = friendUserDataArr;
                $r9 = LayoutManager.this;
                if ($r9.mNavResFrag == null) {
                    LayoutManager.this.mNavResFrag = new NavResultsFragment();
                }
                $r9 = LayoutManager.this;
                $r9.mNavResFrag.setData(str, str2, str3, str4, str5, i, str6, i2, z, z2, str7, str8, i3, str9, str10, z3, i4, z4, i5, z5, str11, str12, str13, i6, i7, str14, str15, str16, friendUserDataArr, z6, z7, z8, z9, z10, z11);
                $r9 = LayoutManager.this;
                BottomBarOnTouchListener $r21 = $r9.mBottomBarOnTouchListener;
                $r9 = LayoutManager.this;
                $r21.onLayoutChanged($r9.mNavResFrag);
            }
        };
        if (this.mbPaused) {
            ArrayList arrayList = this.mOnResume;
            ArrayList $r19 = arrayList;
            arrayList.add(anonymousClass44);
            return;
        }
        anonymousClass44.run();
    }

    void animateNavResIn(final boolean $z0) throws  {
        final View $r2 = this.mMainLayout.findViewById(C1283R.id.mainNavResFrame);
        $r2.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                if (LayoutManager.this.mNavResFrag.getView() != null) {
                    $r2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    LayoutManager.this.mBottomBarOnTouchListener.fragmentAttached(LayoutManager.this.mNavResFrag);
                    LayoutManager.this.mBottomBarOnTouchListener.animateUp($z0 ? (short) 0 : (short) 200);
                }
            }
        });
    }

    public void closeNavResults(boolean $z0) throws  {
        if (this.mbPaused) {
            final boolean z = $z0;
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.closeNavResults(z);
                }
            });
            return;
        }
        if (this.mNavResFrag == null || !this.mNavResFrag.isAdded() || this.mNavResFrag.getView() == null) {
            $z0 = false;
        }
        if ($z0) {
            this.mNavResFrag.clear();
            this.mBottomBarOnTouchListener.animateDown();
            return;
        }
        Animation $r5 = this.mBottomBarLayout.getAnimation();
        if ($r5 != null) {
            $r5.setAnimationListener(null);
            this.mBottomBarLayout.clearAnimation();
        }
        this.mMainLayout.findViewById(C1283R.id.MainBottomBarShadow).clearAnimation();
        this.mMainLayout.findViewById(C1283R.id.mainBottomBarMenuButton).clearAnimation();
        this.mBottomBarRightButtonLayout.clearAnimation();
        ((View) getNavProgress().getParent()).setVisibility(8);
        this.mBottomBarOnTouchListener.setClosed();
        getBottomBar().closeTimeToParkMessage();
        this.mNavBarSoundButton.setTranslationY(0.0f);
    }

    void onNavResAnimateOutDone() throws  {
        if (this.mNavResFrag != null && this.mNavResFrag.isAdded()) {
            this.mNavResFrag.clear();
            this.context.getFragmentManager().beginTransaction().remove(this.mNavResFrag).commit();
        }
    }

    public void resumeNavResultCounter() throws  {
        if (this.mNavResFrag != null) {
            this.mNavResFrag.resumeCounter();
        }
    }

    public void navResultsLayoutChanged() throws  {
        if (this.mNavResFrag != null) {
            this.mBottomBarOnTouchListener.onLayoutChanged(this.mNavResFrag);
        }
    }

    public FriendUserData[] getNotifyFriends() throws  {
        return this.mNotifyFriends;
    }

    public void updateNavResultPopup(int $i0, String $r1, String $r2, boolean $z0) throws  {
        if (this.mNavResFrag != null && this.mNavResFrag.isAdded()) {
            this.mNavResFrag.updateData($i0, $r1, $r2, $z0);
        }
    }

    public void navResOpenEtaScreen(final boolean $z0, boolean delayed) throws  {
        AnonymousClass47 $r1 = new Runnable() {
            public void run() throws  {
                boolean $z0 = false;
                LayoutManager.this.mTooltipManager.closeTooltip(false, 0);
                LayoutManager.this.mTooltipManager.closeTooltip(false, 6);
                LayoutManager.this.mTooltipManager.closeTooltip(false, 8);
                LayoutManager.this.mTooltipManager.closeTooltip(false, 9);
                if (LayoutManager.this.mNavResFrag != null) {
                    if (!LayoutManager.this.mNavResFrag.isAdded()) {
                        LayoutManager.this.context.getFragmentManager().beginTransaction().add(C1283R.id.mainNavResFrame, LayoutManager.this.mNavResFrag).commit();
                        LayoutManager.this.context.getFragmentManager().executePendingTransactions();
                        $z0 = true;
                    } else if (!LayoutManager.this.mNavResFrag.isVisible()) {
                        $z0 = true;
                    }
                    LayoutManager.this.mNavResFrag.updateData();
                    if (!$z0) {
                        return;
                    }
                    if (LayoutManager.this.mBottomBarOnTouchListener.mIsTouching) {
                        LayoutManager.this.mBottomBarOnTouchListener.animateNavResOnMove(LayoutManager.this.mNavResFrag);
                    } else {
                        LayoutManager.this.animateNavResIn($z0);
                    }
                }
            }
        };
        if (this.mbPaused) {
            this.mOnResume.add($r1);
        } else {
            $r1.run();
        }
    }

    public void updateNavResultShare(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ArrayList<PersonBase> $r1) throws  {
        if (this.mNavResFrag != null) {
            this.mNavResFrag.updateShare($r1);
        }
    }

    public void navResPause() throws  {
        if (this.mNavResFrag != null) {
            this.mNavResFrag.pauseCounter();
        }
    }

    public void navResResume() throws  {
        if (this.mNavResFrag != null) {
            this.mNavResFrag.resumeCounter();
        }
    }

    public static void OpenAboutPopup(String $r0) throws  {
        final Dialog $r1 = new Dialog(AppService.getActiveActivity(), C1283R.style.Dialog);
        $r1.setContentView(C1283R.layout.about);
        ((WebView) $r1.findViewById(C1283R.id.aboutText)).loadData($r0, "text/html; charset=utf-8", "UTF-8");
        $r1.findViewById(C1283R.id.CloseButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                $r1.cancel();
                $r1.dismiss();
            }
        });
        $r1.show();
    }

    public void OpenImageView(Drawable $r1) throws  {
        final Dialog $r2 = new Dialog(this.context, C1283R.style.Dialog);
        $r2.setContentView(C1283R.layout.image_view);
        ((TextView) $r2.findViewById(C1283R.id.CloseButtonText)).setText(AppService.getNativeManager().getLanguageString(354));
        ((ImageView) $r2.findViewById(C1283R.id.Image)).setImageDrawable($r1);
        $r2.findViewById(C1283R.id.CloseButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                $r2.cancel();
                $r2.dismiss();
            }
        });
        $r2.show();
    }

    public void OpenSystemMessageWebPopUp(String $r1) throws  {
        SystemMessageWeb.getInstance(this.context, this).show($r1);
    }

    public void cancelSplash() throws  {
        this.mAppView.getMapView().setBackgroundDrawable(null);
        this.mReportButtonFrame.setVisibility(0);
        this.isSplashVisible = false;
        startGlowAnimation();
    }

    private void startGlowAnimation() throws  {
        View $r5 = this.mMainLayout.findViewById(C1283R.id.mainBottomBarMenuButtonGlow);
        if ($r5.getAnimation() == null) {
            $r5.setVisibility(0);
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 4.0f, 0.0f, 4.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            scaleAnimation.setDuration(5000);
            scaleAnimation.setInterpolator(new AccelerateInterpolator());
            scaleAnimation.setRepeatCount(-1);
            scaleAnimation.setRepeatMode(1);
            Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(5000);
            alphaAnimation.setInterpolator(new AccelerateInterpolator());
            alphaAnimation.setRepeatCount(-1);
            alphaAnimation.setRepeatMode(1);
            alphaAnimation = new AnimationSet(false);
            alphaAnimation.addAnimation(scaleAnimation);
            alphaAnimation.addAnimation(alphaAnimation);
            $r5.startAnimation(alphaAnimation);
        }
    }

    private void stopGlowAnimation() throws  {
        View $r1 = this.mMainLayout.findViewById(C1283R.id.mainBottomBarMenuButtonGlow);
        $r1.clearAnimation();
        $r1.setVisibility(8);
    }

    public void displayBottomPrivacyMessage() throws  {
    }

    public void dismiss(final PopUp $r1) throws  {
        if (this.mbPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.dismiss($r1);
                }
            });
            return;
        }
        popupsRemove($r1);
        this.mSwipeableLayout.removeView($r1);
        this.mSwipeableLayout.requestLayout();
        showSpotifyButton();
    }

    public void addView(PopUp $r1) throws  {
        addView($r1, null, false);
    }

    public void addView(PopUp $r1, LayoutParams $r2) throws  {
        addView($r1, $r2, false);
    }

    public void addView(final PopUp $r1, final LayoutParams $r2, boolean $z0) throws  {
        if (this.mbPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.addView($r1, $r2);
                }
            });
            return;
        }
        int $i0;
        if ($r1.getParent() != null) {
            this.mSwipeableLayout.removeView($r1);
        }
        popupsAdd($r1);
        if ($z0) {
            $i0 = this.mSwipeableLayout.indexOfChild(this.mSwipeableLayout.findViewById(C1283R.id.mainZoomControls)) + 1;
        } else {
            $i0 = this.mSwipeableLayout.indexOfChild(this.mSwipeableLayout.findViewById(C1283R.id.mainAsrPopup));
        }
        if ($r2 == null) {
            this.mSwipeableLayout.addView($r1, $i0, new LayoutParams(-1, -1));
            return;
        }
        this.mSwipeableLayout.addView($r1, $i0, $r2);
    }

    public void popupsAdd(PopUp $r1) throws  {
        if (!this.popups.contains($r1)) {
            this.popups.add($r1);
        }
        setPopupShown();
    }

    public void popupsRemove(PopUp $r1) throws  {
        if (this.popups.contains($r1)) {
            this.popups.remove($r1);
        }
        if ($r1 == this.mAlerterPopUp) {
            this.mAlerterPopUp = null;
        }
        if ($r1 == this.mPoiPopUp) {
            this.mPoiPopUp = null;
        }
        if ($r1 == this.mUserPopUp && !this.mUserPopUp.isShowing()) {
            this.mUserPopUp = null;
        }
        setPopupShown();
    }

    private void popupsDismissAll() throws  {
        while (this.popups.size() > 0) {
            PopUp $r3 = (PopUp) this.popups.get(0);
            ((PopUp) this.popups.remove(0)).hide();
        }
        setPopupShown();
    }

    private int popupsSize() throws  {
        return this.popups.size();
    }

    public boolean isPopupsShown() throws  {
        return this.popupShown;
    }

    void setPopupShown() throws  {
        this.popupShown = this.popups.size() > 0;
        if (this.mTooltipManager.mIsToolTipDisplayed) {
            this.popupShown = true;
        }
        SetFriendsBarVisibilty(this.mRequestedFriendsBarVisible);
    }

    @Nullable
    private PopUp findPopup(Class $r1) throws  {
        for (PopUp $r5 : this.popups) {
            if ($r1.isInstance($r5)) {
                return $r5;
            }
        }
        return null;
    }

    private boolean isPopUpShown(Class $r1) throws  {
        PopUp $r2 = findPopup($r1);
        return $r2 != null && $r2.isShown();
    }

    private void hidePopUp(Class $r1) throws  {
        PopUp $r2 = findPopup($r1);
        if ($r2 != null) {
            $r2.hide();
        }
    }

    public void openMainMenu() throws  {
        if (this.mbPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openMainMenu();
                }
            });
        } else if (this.mSwipeableLayout.isOpened()) {
            this.mSwipeableLayout.getActionProvider().close();
        } else {
            stopGlowAnimation();
            AppService.getNativeManager().asrCancel(AnalyticsEvents.f50xedcf1877);
            if (isNavListReadyForDisplay()) {
                this.mSwipeableLayout.openSwipe(true);
            }
        }
    }

    public void openLeftSideToAutocomplete(final boolean $z0) throws  {
        this.mSwipeableLayout.openSwipe(true, false, new TransitionDoneListener() {
            public void done() throws  {
                LayoutManager.this.mMainSideMenu.openAutocomplete($z0);
            }
        });
    }

    public void openRightSideToRidesListImmediate() throws  {
        if (this.mSwipeableLayout.getMeasuredWidth() == 0) {
            DisplayUtils.runOnLayout(this.mSwipeableLayout, new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openRightSideToRidesListImmediate();
                }
            });
        } else if (!this.mSwipeableLayout.isOpened()) {
            openRightSide();
        }
    }

    public void openRightSide() throws  {
        if (isPaused()) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openRightSideFromButton(false);
                }
            });
        } else {
            openRightSideFromButton(false);
        }
    }

    public void openRightSideFromButton(boolean $z0) throws  {
        if (this.mCarpoolAvailable) {
            if (!$z0) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_PANEL_OPENING).addParam("TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CARPOOL_AUTO_PANNED).send();
            }
            refreshCarpoolPanel();
            this.mSwipeableLayout.openSwipe(false);
        }
    }

    public void closeSwipe() throws  {
        if (this.mSwipeableLayout.isOpened()) {
            this.mSwipeableLayout.getActionProvider().close();
        }
    }

    public NavBar getNavBar() throws  {
        return this.mNavBar;
    }

    public NavResultsFragment getNavResults() throws  {
        return this.mNavResFrag;
    }

    public ProgressAnimation getNavProgress() throws  {
        return (ProgressAnimation) this.mMainLayout.findViewById(C1283R.id.mainBottomBarProgress);
    }

    public void openRightSwipeableLayout() throws  {
        this.mSwipeableLayout.transitionToRightSwipableLayout();
    }

    public NavBar createNavBar() throws  {
        this.mNavBar = (NavBar) this.mMainLayout.findViewById(C1283R.id.NavBarLayout);
        this.mNavBar.init(this, this.mBottomBar);
        this.mNavBar.setThenHiddenForAlerter(this.navbarThenHiddenForAlerter);
        this.navbarThenHiddenForAlerter = false;
        return this.mNavBar;
    }

    public void removeDelayedReportButton() throws  {
        ReportMenuButton $r3 = (ReportMenuButton) this.mMainLayout.findViewById(C1283R.id.mainDelayedReportButton);
        $r3.clearAnimation();
        $r3.setVisibility(8);
    }

    public void openReportMenu(boolean $z0, boolean $z1, boolean $z2) throws  {
        if (this.mbPaused) {
            final boolean z = $z0;
            final boolean z2 = $z1;
            final boolean z3 = $z2;
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.openReportMenu(z, z2, z3);
                }
            });
            return;
        }
        if (isPopupsShown()) {
            callCloseAllPopups(1);
        }
        if (this.mUpcomingCarpoolBar != null && this.mUpcomingCarpoolBar.isShown()) {
            this.mUpcomingCarpoolBar.hide();
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_BUTTON);
        AppService.getNativeManager().asrCancel(AnalyticsEvents.f50xedcf1877);
        removeDelayedReportButton();
        this.mMainLayout.findViewById(C1283R.id.mainMenuFrame).setVisibility(0);
        if (this.delayedReportMenu != null) {
            this.context.getFragmentManager().beginTransaction().remove(this.delayedReportMenu).commit();
            this.delayedReportMenu = null;
        }
        InitReportMenu($z0, $z1, $z2);
        this.reportMenu.removeReportForm();
        setClosureRunning(false);
        addReportMenuFragment();
        this.reportMenu.setRevealOrigin(this.mReportButtonFrame.getLeft() + (this.mReportButtonFrame.getWidth() / 2), this.mReportButtonFrame.getTop() + (this.mReportButtonFrame.getHeight() / 2));
        this.reportMenu.open(false);
        hideSpotifyButton();
    }

    public void addReportMenuFragment() throws  {
        if (this.mbPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.addReportMenuFragment();
                }
            });
        } else if (!this.reportMenu.isAdded()) {
            this.context.getFragmentManager().beginTransaction().add(C1283R.id.mainMenuFrame, this.reportMenu).commit();
            this.context.getFragmentManager().executePendingTransactions();
        }
    }

    public View getReportMenuContainer() throws  {
        return this.mMainLayout.findViewById(C1283R.id.mainMenuFrame);
    }

    public void InitReportMenu(boolean $z0, boolean $z1, boolean $z2) throws  {
        if (this.reportMenu == null) {
            this.reportMenu = new ReportMenu();
            Bundle $r1 = new Bundle();
            $r1.putBoolean("isRandomUser", $z0);
            $r1.putBoolean("isFoursquareEnabled", $z1);
            $r1.putBoolean("isClosureEnabled", $z2);
            this.reportMenu.setArguments($r1);
        }
    }

    public void OpenFriendsOnlinePopUp(final int $i0) throws  {
        if (this.mPopupsFragment.isAdded()) {
            this.mOnPopupsClosed.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.OpenFriendsOnlinePopUp($i0);
                }
            });
            return;
        }
        this.mFriendsBarFragment.disappearAllVisibilty();
        this.mTrafficBarView.setVisibility(8);
        this.mPopupsFragment.openFriendsOnlinePopUp($i0);
    }

    public void OpenUpdatePricesPopUp(long $l0, long $l1) throws  {
        UpdateGasPopup $r1 = new UpdateGasPopup(this.context, this);
        $r1.show($l0, $l1);
        $r1.setCloseTime(15);
    }

    public void showTrafficJamReport(boolean $z0, boolean $z1, boolean $z2) throws  {
        InitReportMenu($z0, $z1, $z2);
        addReportMenuFragment();
        this.reportMenu.setRevealOrigin(this.mReportButtonFrame.getLeft() + (this.mReportButtonFrame.getWidth() / 2), this.mReportButtonFrame.getTop() + (this.mReportButtonFrame.getHeight() / 2));
        this.reportMenu.open(false);
        this.reportMenu.showTrafficJamReport();
    }

    public void showPoliceReport(boolean $z0, boolean $z1, boolean $z2) throws  {
        InitReportMenu($z0, $z1, $z2);
        addReportMenuFragment();
        this.reportMenu.setRevealOrigin(this.mReportButtonFrame.getLeft() + (this.mReportButtonFrame.getWidth() / 2), this.mReportButtonFrame.getTop() + (this.mReportButtonFrame.getHeight() / 2));
        this.reportMenu.open(false);
        this.reportMenu.showPoliceReport();
    }

    public void showHazardReport(boolean $z0, boolean $z1, boolean $z2) throws  {
        InitReportMenu($z0, $z1, $z2);
        addReportMenuFragment();
        this.reportMenu.setRevealOrigin(this.mReportButtonFrame.getLeft() + (this.mReportButtonFrame.getWidth() / 2), this.mReportButtonFrame.getTop() + (this.mReportButtonFrame.getHeight() / 2));
        this.reportMenu.open(false);
        this.reportMenu.showHazardReport();
    }

    public void showGasReport() throws  {
        ReportMenu.showGasPriceReport();
    }

    public void showMapProblemReport(boolean $z0, boolean $z1, boolean $z2) throws  {
        InitReportMenu($z0, $z1, $z2);
        this.reportMenu.setRevealOrigin(this.mReportButtonFrame.getLeft() + (this.mReportButtonFrame.getWidth() / 2), this.mReportButtonFrame.getTop() + (this.mReportButtonFrame.getHeight() / 2));
        this.reportMenu.open(false);
        this.reportMenu.showMapProblemReport();
    }

    public void showSpeedLimitReport(boolean $z0, boolean $z1, boolean $z2) throws  {
        InitReportMenu($z0, $z1, $z2);
        addReportMenuFragment();
        this.reportMenu.setRevealOrigin(this.mReportButtonFrame.getLeft() + (this.mReportButtonFrame.getWidth() / 2), this.mReportButtonFrame.getTop() + (this.mReportButtonFrame.getHeight() / 2));
        this.reportMenu.open(false);
        this.reportMenu.showMapProblemReport().setSpeedSelection();
    }

    public void OpenClosure(boolean $z0, boolean $z1, boolean $z2, boolean bIsServerRequest) throws  {
        InitReportMenu($z0, $z1, $z2);
        addReportMenuFragment();
        this.reportMenu.setRevealOrigin(this.mReportButtonFrame.getLeft() + (this.mReportButtonFrame.getWidth() / 2), this.mReportButtonFrame.getTop() + (this.mReportButtonFrame.getHeight() / 2));
        this.reportMenu.open(false);
        this.reportMenu.showClosureReport();
    }

    public void OpenPave(boolean $z0, boolean $z1, boolean $z2) throws  {
        InitReportMenu($z0, $z1, $z2);
        this.reportMenu.showRoadRecording();
    }

    private void openDelayedReportMenu() throws  {
        if (this.delayedReportMenu == null) {
            removeDelayedReportButton();
            return;
        }
        View $r4 = this.mMainLayout.findViewById(C1283R.id.mainDelayedReportButton);
        this.mMainLayout.findViewById(C1283R.id.mainMenuFrame).setVisibility(0);
        this.reportMenu = this.delayedReportMenu;
        this.context.getFragmentManager().beginTransaction().show(this.reportMenu).commit();
        this.context.getFragmentManager().executePendingTransactions();
        this.reportMenu.setRevealOrigin($r4.getLeft() + ($r4.getWidth() / 2), $r4.getTop() + ($r4.getHeight() / 2));
        this.reportMenu.open(true);
        if (IsClosureRunning() && (this.reportMenu.getReportForm() instanceof ClosureReport)) {
            Intent intent = new Intent(this.context, ClosureMap.class);
            ClosureMap.SetReportForm((ClosureReport) this.reportMenu.getReportForm());
            final Intent intent2 = intent;
            this.mMainLayout.postDelayed(new Runnable() {
                public void run() throws  {
                    AppService.getMainActivity().startActivityForResult(intent2, 1);
                }
            }, 250);
        }
        this.delayedReportMenu = null;
        this.mSwipeableLayout.postDelayed(new Runnable() {
            public void run() throws  {
                LayoutManager.this.removeDelayedReportButton();
            }
        }, 200);
    }

    public ReportMenuButton getDelayedReportButton() throws  {
        return (ReportMenuButton) this.mMainLayout.findViewById(C1283R.id.mainDelayedReportButton);
    }

    public void openDelayedReportAfterClosure() throws  {
        if (this.delayedReportMenu == null) {
            removeDelayedReportButton();
            return;
        }
        this.reportMenu = this.delayedReportMenu;
        View $r3 = this.mMainLayout.findViewById(C1283R.id.mainDelayedReportButton);
        this.reportMenu.setRevealOrigin($r3.getLeft() + ($r3.getWidth() / 2), $r3.getTop() + ($r3.getHeight() / 2));
        this.delayedReportMenu.open(true);
        this.context.getFragmentManager().beginTransaction().show(this.delayedReportMenu).commit();
    }

    public boolean IsClosureRunning() throws  {
        return this.bIsClosureRunning;
    }

    public void setClosureRunning(boolean $z0) throws  {
        this.bIsClosureRunning = $z0;
    }

    private void openRoadRecordingMenu() throws  {
        if (this.delayedReportMenu != null) {
            this.reportMenu = this.delayedReportMenu;
            this.context.getFragmentManager().beginTransaction().show(this.delayedReportMenu).commit();
            this.context.getFragmentManager().executePendingTransactions();
            this.delayedReportMenu.showRoadRecording();
        }
    }

    public void setDelayedReport(ReportMenu $r1, ReportMenuButton $r2) throws  {
        if ($r1 != null && $r2 != null) {
            this.delayedReportMenu = $r1;
            final ReportMenuButton $r8 = (ReportMenuButton) this.mMainLayout.findViewById(C1283R.id.mainDelayedReportButton);
            $r8.setBackgroundColor($r2.getBackgroundColor());
            $r8.setImageResource($r2.getImageResId());
            int[] $r4 = new int[2];
            int[] $r3 = new int[2];
            if (VERSION.SDK_INT < 19 || !$r2.isAttachedToWindow()) {
                ((View) $r2.getParent()).getLocationOnScreen($r4);
                $r4[0] = $r4[0] + $r2.getLeft();
                $r4[1] = $r4[1] + $r2.getTop();
            } else {
                $r2.getLocationInWindow($r4);
            }
            $r8.setVisibility(0);
            $r8.getLocationInWindow($r3);
            $r8.skipAnimation();
            TranslateAnimation $r5 = new TranslateAnimation((float) ($r4[0] - $r3[0]), 0.0f, (float) ($r4[1] - $r3[1]), 0.0f);
            $r5.setInterpolator(new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
            $r5.setStartOffset(100);
            $r5.setFillBefore(true);
            $r5.setDuration(350);
            $r8.startAnimation($r5);
            final ReportMenuButton reportMenuButton = $r2;
            $r5.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) throws  {
                    reportMenuButton.setVisibility(8);
                }

                public void onAnimationEnd(Animation animation) throws  {
                    com.waze.view.anim.AnimationUtils.pulseAnimation($r8);
                }

                public void onAnimationRepeat(Animation animation) throws  {
                }
            });
        }
    }

    @Deprecated
    public void removeRoadPavingTab() throws  {
    }

    @Deprecated
    public void setRoadPavingTab(@Deprecated ReportMenu reportMenu) throws  {
    }

    private InputMethodManager getInputMethodManager() throws  {
        return (InputMethodManager) this.context.getSystemService("input_method");
    }

    public void HideSoftInput(View $r1) throws  {
        getInputMethodManager().hideSoftInputFromWindow($r1.getWindowToken(), 0);
    }

    public void ShowSoftInput(View $r1) throws  {
        getInputMethodManager().restartInput($r1);
        getInputMethodManager().showSoftInput($r1, 2);
    }

    private void setSplash() throws  {
        NativeManager $r1 = AppService.getNativeManager();
        if ($r1 == null) {
            this.isSplashVisible = true;
            return;
        }
        cancelSplash();
        $r1.getNavBarManager().restore(this);
    }

    public void onActivityResult(Activity $r1, int $i0, int $i1, Intent $r2) throws  {
        if ($i1 == 3) {
            if (this.reportMenu != null) {
                removeReportMenu();
            }
            closeUserPopup();
        }
        if ($i0 == MainActivity.RECORD_SOUND_CODE || $i0 == MainActivity.CAPTURE_IMAGE_CODE || $i0 == MainActivity.REPORT_GROUPS_CODE || $i0 == MainActivity.REPORT_MENU_PICK_ONE || $i0 == MainActivity.REPORT_MENU_SPEECH_REQUEST_CODE || $i0 == MainActivity.REPORT_ADD_PLACE_PHOTO_CODE || $i0 == 4000) {
            if (this.reportMenu != null) {
                this.reportMenu.onActivityResult($r1, $i0, $i1, $r2);
            }
        } else if ($i0 == MainActivity.NAVIGATE_CODE || $i0 == MainActivity.RTALERTS_REQUEST_CODE || $i0 == MainActivity.MYWAZE_CODE || $i0 == MainActivity.SHARE_CODE || $i0 == MainActivity.SETTINGS_CODE || $i0 == MainActivity.NAVIGATION_LIST_CODE || $i0 == MainActivity.MYFRIENDS_CODE || $i0 == MainActivity.ADDRESS_OPTIONS_CODE || $i0 == 512) {
            if ($i1 == -1 || $i1 == 1 || $i1 == 2) {
            }
            if ($i0 != MainActivity.MYWAZE_CODE && $i1 == 1) {
                this.mSwipeableLayout.openSwipe(true);
            }
            if ($i1 == 2) {
                this.pendingAddStop = true;
            }
        } else if ($i0 == MainActivity.NAVBAR_ADD_PLACE_PHOTO_CODE) {
            this.mNavBar.onActivityResult($r1, $i0, $i1, $r2);
        }
        if ($i0 == MainActivity.RTALERTS_REQUEST_CODE) {
            if ($i1 == 1001) {
                RTAlertsNativeManager.getInstance().showAlertPopUp($r2.getIntExtra(RTAlertsConsts.RTALERTS_POPUP_ALERT_ID, -1));
                if (this.reportMenu != null) {
                    removeReportMenu();
                }
            }
            if ($i1 == 1002) {
                callCloseAllPopups(1);
            }
        }
        if ($i0 != MainActivity.POI_POPUP_INFO_REQUEST_CODE) {
            return;
        }
        if (this.mPoiPopUp != null) {
            this.mPoiPopUp.onPreviewActivityResult($i0, $i1, $r2);
        } else {
            Logger.m38e("onPreviewActivityResult is called when no active popup instance!");
        }
    }

    public void setNavigateSearchTerm(String $r1, boolean $z0) throws  {
        if (this.mSearchOnMapView == null || !this.mSearchOnMapView.isWaitingForVoice()) {
            this.mMainSideMenu.setSearchTerm($r1, $z0);
        } else {
            this.mSearchOnMapView.setSearchTerm($r1, $z0);
        }
    }

    public void setAutoCompleteSearchTerm(final String $r1, final boolean $z0) throws  {
        if (this.mSearchOnMapView == null || !this.mSearchOnMapView.isSearchBarVisibile()) {
            this.mSwipeableLayout.openSwipe(true, new TransitionDoneListener() {
                public void done() throws  {
                    LayoutManager.this.mMainSideMenu.setSearchTerm($r1, $z0);
                }
            });
            return;
        }
        this.mSearchOnMapView.onSpeechButtonClick();
        this.mSearchOnMapView.setSearchTerm($r1, $z0);
    }

    public void cancelVoiceSearch() throws  {
        if (this.mSearchOnMapView != null) {
            this.mSearchOnMapView.cancelVoiceSearch();
        }
    }

    public void initSwipeableLayoutViews() throws  {
        this.mSwipeableLayout.postDelayed(new Runnable() {

            class C11631 implements Runnable {
                C11631() throws  {
                }

                public void run() throws  {
                    LayoutManager.this.mMainSideMenu.setVisibility(8);
                    if (LayoutManager.this.mRightSideMenu != null) {
                        LayoutManager.this.mRightSideMenu.setVisibility(8);
                    }
                }
            }

            public void run() throws  {
                LayoutManager.this.mMainSideMenu.setVisibility(0);
                if (LayoutManager.this.mRightSideMenu != null) {
                    LayoutManager.this.mRightSideMenu.setVisibility(0);
                }
                LayoutManager.this.mSwipeableLayout.postDelayed(new C11631(), 100);
            }
        }, 100);
    }

    public void removeReportMenu() throws  {
        if (this.mbPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() throws  {
                    LayoutManager.this.removeReportMenu();
                }
            });
            return;
        }
        if (this.reportMenu != null) {
            this.reportMenu.onRemove();
            if (this.delayedReportMenu == this.reportMenu) {
                this.context.getFragmentManager().beginTransaction().hide(this.delayedReportMenu).commit();
                this.mMainLayout.findViewById(C1283R.id.mainMenuFrame).setVisibility(8);
            } else {
                this.context.getFragmentManager().beginTransaction().remove(this.reportMenu).commit();
                removeDelayedReportButton();
            }
            this.reportMenu = null;
        }
        if (!(this.mUpcomingCarpoolBar == null || !this.mUpcomingCarpoolBar.shouldShow() || this.mUpcomingCarpoolBar.isShown())) {
            this.mUpcomingCarpoolBar.reshow();
        }
        showSpotifyButton();
    }

    public void ChangeSearchBarOrientation(int $i0) throws  {
        if (!this.isSearchBarVisible) {
            return;
        }
        if ($i0 == 1) {
            this.mMainLayout.findViewById(C1283R.id.SearchBarLayout).setVisibility(0);
            this.mMainLayout.findViewById(C1283R.id.SearchBarLayout_ls).setVisibility(8);
            ((TextView) this.mMainLayout.findViewById(C1283R.id.SearchBarText)).setText(NativeManager.getInstance().getLanguageString(26));
            return;
        }
        this.mMainLayout.findViewById(C1283R.id.SearchBarLayout).setVisibility(8);
        this.mMainLayout.findViewById(C1283R.id.SearchBarLayout_ls).setVisibility(0);
        ((TextView) this.mMainLayout.findViewById(C1283R.id.SearchBarText_ls)).setText(NativeManager.getInstance().getLanguageString(26));
    }

    public void onMainMenuClosed() throws  {
    }

    public void onPause() throws  {
        this.mbPaused = true;
        this.mBottomBar.setPaused(true);
    }

    public void onResume() throws  {
        this.mbPaused = false;
        this.mBottomBar.setPaused(false);
        if (this.mOrientationEventPending) {
            onOrientationChanged(AppService.getAppContext().getResources().getConfiguration().orientation);
            this.mOrientationEventPending = false;
        }
        while (!this.mOnResume.isEmpty()) {
            ((Runnable) this.mOnResume.remove(0)).run();
        }
        if (this.mNeedRefreshCarpoolPanel && this.mRightSideMenu != null) {
            this.mNeedRefreshCarpoolPanel = false;
            this.mRightSideMenu.setupCarpoolFragment(false);
        }
    }

    public boolean isPaused() throws  {
        return this.mbPaused;
    }

    public void addOnResume(Runnable $r1) throws  {
        this.mOnResume.add($r1);
    }

    public void onLanguageInitialized() throws  {
        NativeManager $r1 = AppService.getNativeManager();
        $r1.getLanguageString((int) DisplayStrings.DS_SLIDE_TO_NAVIGATE);
        $r1.getLanguageString((int) DisplayStrings.DS_REPORT_TRAFFIC);
        ((TextView) this.mMainLayout.findViewById(C1283R.id.mainAsrText1)).setText($r1.getLanguageString((int) DisplayStrings.DS_WAIT));
        ((TextView) this.mMainLayout.findViewById(C1283R.id.mainAsrText2)).setText($r1.getLanguageString((int) DisplayStrings.DS_TAP_TO_CANCEL));
    }

    public boolean isAnyMenuOpen() throws  {
        return isSwipeableLayoutOpened() || reportMenuShown();
    }

    public void displayVoiceError() throws  {
        TextView $r4 = (TextView) this.mMainLayout.findViewById(C1283R.id.mainAsrText1);
        $r4.setText(AppService.getNativeManager().getLanguageString((int) DisplayStrings.DS_NO_CONNECTION));
        $r4.setBackgroundResource(C1283R.drawable.status_red);
        $r4.clearAnimation();
    }

    public void displayVoiceWait() throws  {
        ((TextView) this.mMainLayout.findViewById(C1283R.id.mainAsrText1)).setText(AppService.getNativeManager().getLanguageString((int) DisplayStrings.DS_WAIT));
    }

    public void displayVoiceListening() throws  {
        TextView $r4 = (TextView) this.mMainLayout.findViewById(C1283R.id.mainAsrText1);
        $r4.setText(AppService.getNativeManager().getLanguageString((int) DisplayStrings.DS_LISTENINGPPP));
        $r4.setBackgroundResource(C1283R.drawable.status_green);
    }

    public boolean isSplashVisible() throws  {
        return this.isSplashVisible;
    }

    public void SetPoiAction(String $r1) throws  {
        if (shouldIgnoreTakeOver()) {
            NativeManager.getInstance().CloseDarkView();
            return;
        }
        if (this.mPoiPopUp == null) {
            this.mPoiPopUp = new PoiPopUp(this.context, this);
        }
        this.mPoiPopUp.SetAction($r1);
    }

    public void openPowerSavingNotificationAlerter() throws  {
        showAlerterPopup("", AlerterPopUp.POWER_SAVING_ICON_NAME, DisplayStrings.displayString(DisplayStrings.DS_SAVE_BATTERY_MODE_ALERT_TEXT), true, false, 0, 2153116, true);
        setAlerterPopupCloseTime(10);
    }

    public void updatePoiPopupIfNeeded() throws  {
        if (this.mPoiPopUp != null) {
            this.mPoiPopUp.callJavascriptUpdateClient();
        }
    }

    public void adjustSoundButton() throws  {
        this.mNavBarSoundButton.adjustSoundButton();
        setSoundIcon();
    }

    public void onSkinChanged() throws  {
        boolean $z2;
        int $i0;
        boolean $z0 = DriveToNativeManager.getInstance().isDayMode();
        boolean $z1 = NativeManager.getInstance().isNavigatingNTV();
        this.mCarpoolAvailable = CarpoolNativeManager.getInstance().configIsOnNTV();
        Resources $r5 = this.context.getResources();
        View $r7 = this.mMainLayout.findViewById(C1283R.id.mainBottomBarMenuButton);
        ImageView imageView = (ImageView) this.mMainLayout.findViewById(C1283R.id.mainBottomBarMenuButtonImage);
        View $r8 = this.mMainLayout.findViewById(C1283R.id.bottomBarNotif);
        View $r10 = this.mMainLayout.findViewById(C1283R.id.bottomBarStaticMessage);
        TextView $r12 = (TextView) this.mMainLayout.findViewById(C1283R.id.bottomBarEta);
        TextView $r13 = (TextView) this.mMainLayout.findViewById(C1283R.id.bottomBarTimeToDest);
        TextView $r14 = (TextView) this.mMainLayout.findViewById(C1283R.id.bottomBarDistanceToDest);
        View $r11 = this.mMainLayout.findViewById(C1283R.id.mainBottomBarSep1);
        View $r15 = this.mMainLayout.findViewById(C1283R.id.mainBottomBarSep2);
        BottomBar $r16 = this.mBottomBar;
        if ($z1) {
            $z2 = false;
        } else {
            $z2 = true;
        }
        $r16.setShowDefaultMessage($z2);
        if (this.mSearchOnMapView != null) {
            SearchOnMapView $r17 = this.mSearchOnMapView;
            $r17.onDayNightChange();
        }
        NavBarSoundButton $r18 = this.mNavBarSoundButton;
        $r18.adjustSoundButton();
        if ($z0) {
            $i0 = C1283R.drawable.bottom_bar_button_background_day;
        } else {
            $i0 = C1283R.drawable.bottom_bar_button_background_night;
        }
        int $i1 = $r5.getColor(C1283R.color.BlueGrey);
        int $i2 = $r5.getColor(C1283R.color.blue_bg);
        com.waze.view.anim.AnimationUtils.viewBgInit($r7, $r5.getDrawable($i0), $i2, $i1);
        com.waze.view.anim.AnimationUtils.viewBgInit(this.mBottomBarRightButtonLayout, $r5.getDrawable($i0), $i2, $i1);
        com.waze.view.anim.AnimationUtils.viewBgInit(this.mBottomBar, $r5.getDrawable($i0), $i2, $i1);
        if ($z0) {
            $i0 = $r5.getColor(C1283R.color.bottom_bar_notification_background_day);
        } else {
            $i0 = $r5.getColor(C1283R.color.bottom_bar_notification_background_night);
        }
        com.waze.view.anim.AnimationUtils.viewBgInit($r8, $i0, $i2, $i1);
        $r10.setBackgroundColor($i0);
        setMenuButtonsImages($z1, $z0, this.mCarpoolAvailable);
        if ($z0) {
            $i1 = $r5.getColor(C1283R.color.bottom_bar_eta_text_day);
        } else {
            $i1 = $r5.getColor(C1283R.color.bottom_bar_eta_text_night);
        }
        $r12.setTextColor($i1);
        $r13.setTextColor($i1);
        $r14.setTextColor($i1);
        if ($z0) {
            $i1 = $r5.getColor(C1283R.color.bottom_bar_sep_day);
        } else {
            $i1 = $r5.getColor(C1283R.color.bottom_bar_sep_night);
        }
        $r11.setBackgroundColor($i1);
        $r15.setBackgroundColor($i1);
        TrafficBarView $r20 = this.mTrafficBarView;
        $r20.setDayMode($z0);
        FriendsBarFragment $r21 = this.mFriendsBarFragment;
        $r21.setDayMode($z0);
        ((View) getNavProgress().getParent()).setBackgroundColor($r5.getColor($z0 ? C1283R.color.White : C1283R.color.DarkBlueAlt));
    }

    public void trafficBarSetHidden(boolean $z0) throws  {
        byte $b0 = (byte) 0;
        if ($z0) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        this.mShouldShowTrafficBar = $z0;
        if (this.mShouldShowTrafficBar && this.mTrafficBarView.canShowInternal()) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        TrafficBarView $r1 = this.mTrafficBarView;
        if (!$z0) {
            $b0 = (byte) 8;
        }
        $r1.setVisibility($b0);
    }

    public void trafficBarSet(int $i0, int $i1, int[] $r1, int[] $r2, int $i2) throws  {
        boolean $z1 = true;
        if ($r2 != null) {
            if (this.mShouldShowTrafficBar && this.mTrafficBarView.canShow($i0, $i1)) {
                this.mTrafficBarView.setVisibility(0);
            } else {
                this.mTrafficBarView.setVisibility(8);
                $z1 = false;
            }
        } else if (this.mShouldShowTrafficBar) {
            this.mTrafficBarView.setVisibility(0);
        }
        if ($z1) {
            this.mTrafficBarView.setTime($i0, $i1, $r1, $r2, $i2);
        }
    }

    public void trafficBarShowPopUp(int $i0) throws  {
        this.mTrafficBarView.appearifyTheTrafficBarTip($i0);
    }

    public void trafficBarClose() throws  {
        if (this.mTrafficBarView != null) {
            this.mTrafficBarView.reset();
            this.mTrafficBarView.setVisibility(8);
        }
    }

    public RightSideMenu getRightSideMenu() throws  {
        return this.mRightSideMenu;
    }

    public void refreshAddressItems() throws  {
        this.mMainSideMenu.refreshAddressItems();
    }

    public boolean shouldShowCarpoolBar() throws  {
        return this.mUpcomingCarpoolBar != null && this.mUpcomingCarpoolBar.shouldShow();
    }

    public UpcomingCarpoolBar getCarpoolBar() throws  {
        return this.mUpcomingCarpoolBar;
    }

    public void onNavSptotifyButton() throws  {
        View $r2 = this.mMainLayout.findViewById(C1283R.id.SpotifyButton);
        LayoutParams $r4 = (LayoutParams) $r2.getLayoutParams();
        $r4.setMargins(0, (int) (-35.0f * this.mMainLayout.getResources().getDisplayMetrics().density), 0, 0);
        $r2.setLayoutParams($r4);
        $r2.bringToFront();
    }

    public void onStopNavSptotifyButton() throws  {
        View $r2 = this.mMainLayout.findViewById(C1283R.id.SpotifyButton);
        LayoutParams $r4 = (LayoutParams) $r2.getLayoutParams();
        $r4.setMargins(0, (int) (35.0f * this.mMainLayout.getResources().getDisplayMetrics().density), 0, 0);
        $r2.setLayoutParams($r4);
    }

    public void SpotifyButtonMarginTop(int $i0) throws  {
        View $r2 = this.mMainLayout.findViewById(C1283R.id.SpotifyButton);
        LayoutParams $r4 = (LayoutParams) $r2.getLayoutParams();
        $r4.setMargins(0, (int) (((float) $i0) * this.mMainLayout.getResources().getDisplayMetrics().density), 0, 0);
        $r2.setLayoutParams($r4);
    }

    public boolean isSpotifyButtonShown() throws  {
        return this.mMainLayout.findViewById(C1283R.id.SpotifyButton).getVisibility() == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showSpotifyButton() throws  {
        /*
        r30 = this;
        r2 = 1;
        r3 = com.waze.audioextension.spotify.SpotifyManager.getInstance();
        r4 = r3.shouldShowSpotifyButton();
        if (r4 != 0) goto L_0x0014;
    L_0x000b:
        r5 = "SpotifyManager: ";
        r6 = "showSpotifyButton shouldShowSpotifyButton is flase";
        com.waze.Logger.d_(r5, r6);
        return;
    L_0x0014:
        r4 = 0;
        r8 = com.waze.view.popups.PickupOfferPopUp.class;
        r0 = r30;
        r7 = r0.findPopup(r8);
        r10 = r7;
        r10 = (com.waze.view.popups.PickupOfferPopUp) r10;
        r9 = r10;
        if (r9 == 0) goto L_0x0027;
    L_0x0023:
        r4 = r9.isShown();
    L_0x0027:
        r11 = com.waze.AppService.getAppContext();
        r12 = r11.getResources();
        r13 = r12.getConfiguration();
        r14 = r13.orientation;
        r15 = 0;
        r0 = r30;
        r0 = r0.mSearchOnMapView;
        r16 = r0;
        if (r16 == 0) goto L_0x0048;
    L_0x003e:
        r0 = r30;
        r0 = r0.mSearchOnMapView;
        r16 = r0;
        r15 = r0.isShowingSearchResults();
    L_0x0048:
        if (r15 != 0) goto L_0x00a6;
    L_0x004a:
        r0 = r30;
        r0 = r0.mTooltipManager;
        r17 = r0;
        r0 = r0.mIsToolTipDisplayed;
        r18 = r0;
        if (r18 != 0) goto L_0x00a6;
    L_0x0056:
        r0 = r30;
        r18 = r0.isAlerterShown();
        if (r18 != 0) goto L_0x00a6;
    L_0x005e:
        r19 = com.waze.view.popups.SpotifyPopup.getInstance();
        r0 = r19;
        r18 = r0.isShown();
        if (r18 != 0) goto L_0x00a6;
    L_0x006a:
        r0 = r30;
        r0 = r0.mAlertTicker;
        r20 = r0;
        if (r20 == 0) goto L_0x007e;
    L_0x0072:
        r0 = r30;
        r0 = r0.mAlertTicker;
        r20 = r0;
        r18 = r0.IsShown();
        if (r18 != 0) goto L_0x00a6;
    L_0x007e:
        r0 = r30;
        r0 = r0.mNavBar;
        r21 = r0;
        if (r21 == 0) goto L_0x0098;
    L_0x0086:
        r0 = r30;
        r0 = r0.mNavBar;
        r21 = r0;
        r18 = r0.isCondenseMode();
        if (r18 == 0) goto L_0x0098;
    L_0x0092:
        r22 = 1;
        r0 = r22;
        if (r14 == r0) goto L_0x00a6;
    L_0x0098:
        r0 = r30;
        r18 = r0.reportMenuShown();
        if (r18 != 0) goto L_0x00a6;
    L_0x00a0:
        r18 = isDueToPopupShown;
        if (r18 != 0) goto L_0x00a6;
    L_0x00a4:
        if (r4 == 0) goto L_0x018d;
    L_0x00a6:
        r23 = new java.lang.StringBuilder;
        r0 = r23;
        r0.<init>();
        r5 = "showSpotifyButton ShowingSearchResults=";
        r0 = r23;
        r23 = r0.append(r5);
        r0 = r23;
        r23 = r0.append(r15);
        r5 = " toolTipShow =";
        r0 = r23;
        r23 = r0.append(r5);
        r0 = r30;
        r0 = r0.mTooltipManager;
        r17 = r0;
        r15 = r0.mIsToolTipDisplayed;
        r0 = r23;
        r23 = r0.append(r15);
        r5 = "isAlerterShown=";
        r0 = r23;
        r23 = r0.append(r5);
        r0 = r30;
        r15 = r0.isAlerterShown();
        r0 = r23;
        r23 = r0.append(r15);
        r5 = "SpotifyPopupShown=";
        r0 = r23;
        r23 = r0.append(r5);
        r19 = com.waze.view.popups.SpotifyPopup.getInstance();
        r0 = r19;
        r15 = r0.isShown();
        r0 = r23;
        r23 = r0.append(r15);
        r5 = " alertTicker=";
        r0 = r23;
        r23 = r0.append(r5);
        r0 = r30;
        r0 = r0.mAlertTicker;
        r20 = r0;
        if (r20 == 0) goto L_0x0189;
    L_0x010e:
        r0 = r30;
        r0 = r0.mAlertTicker;
        r20 = r0;
        r15 = r0.IsShown();
        if (r15 == 0) goto L_0x0189;
    L_0x011a:
        r15 = 1;
    L_0x011b:
        r0 = r23;
        r23 = r0.append(r15);
        r5 = " mNavBar=";
        r0 = r23;
        r23 = r0.append(r5);
        r0 = r30;
        r0 = r0.mNavBar;
        r21 = r0;
        if (r21 == 0) goto L_0x018b;
    L_0x0131:
        r0 = r30;
        r0 = r0.mNavBar;
        r21 = r0;
        r15 = r0.isCondenseMode();
        if (r15 == 0) goto L_0x018b;
    L_0x013d:
        r22 = 1;
        r0 = r22;
        if (r14 != r0) goto L_0x018b;
    L_0x0143:
        r0 = r23;
        r23 = r0.append(r2);
        r5 = " reportMenuShown=";
        r0 = r23;
        r23 = r0.append(r5);
        r0 = r30;
        r2 = r0.reportMenuShown();
        r0 = r23;
        r23 = r0.append(r2);
        r5 = " isDueToPopupShown=";
        r0 = r23;
        r23 = r0.append(r5);
        r2 = isDueToPopupShown;
        r0 = r23;
        r23 = r0.append(r2);
        r5 = " pickupPopupSHown=";
        r0 = r23;
        r23 = r0.append(r5);
        r0 = r23;
        r23 = r0.append(r4);
        r0 = r23;
        r24 = r0.toString();
        r5 = "SpotifyManager: ";
        r0 = r24;
        com.waze.Logger.d_(r5, r0);
        return;
    L_0x0189:
        r15 = 0;
        goto L_0x011b;
    L_0x018b:
        r2 = 0;
        goto L_0x0143;
    L_0x018d:
        r0 = r30;
        r0 = r0.mMainLayout;
        r25 = r0;
        r22 = 2131690993; // 0x7f0f05f1 float:1.9011045E38 double:1.053195287E-314;
        r0 = r25;
        r1 = r22;
        r26 = r0.findViewById(r1);
        r22 = 0;
        r0 = r26;
        r1 = r22;
        r0.setVisibility(r1);
        r0 = r26;
        r0.bringToFront();
        r0 = r26;
        r27 = com.waze.view.anim.ViewPropertyAnimatorHelper.initAnimation(r0);
        r28 = 0;
        r0 = r27;
        r1 = r28;
        r27 = r0.translationX(r1);
        r29 = 0;
        r0 = r27;
        r1 = r29;
        r0.setListener(r1);
        r22 = 1;
        mSpotifyButtonShown = r22;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.LayoutManager.showSpotifyButton():void");
    }

    public void hideSpotifyButton() throws  {
        View $r2 = this.mMainLayout.findViewById(C1283R.id.SpotifyButton);
        ViewPropertyAnimatorHelper.initAnimation($r2).translationX((float) $r2.getMeasuredWidth()).setListener(ViewPropertyAnimatorHelper.createInvisibleWhenDoneListener($r2));
        mSpotifyButtonShown = false;
    }

    public void setDueToShown(boolean $z0) throws  {
        if (this.mSearchOnMapView != null) {
            this.mSearchOnMapView.setIsShowingTopView($z0);
        }
    }
}
