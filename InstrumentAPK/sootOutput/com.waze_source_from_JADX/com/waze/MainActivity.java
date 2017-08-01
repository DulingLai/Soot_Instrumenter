package com.waze;

import android.app.PendingIntent.CanceledException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;
import android.widget.ViewAnimator;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.waze.NativeManager.IOnUserNameResult;
import com.waze.NativeManager.TransportationSdkDetails;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.audioextension.spotify.SpotifyManager;
import com.waze.auto.AutoUtils;
import com.waze.carpool.CarpoolOnboardingManager;
import com.waze.carpool.CarpoolRidesFragment;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityBase.IncomingHandler;
import com.waze.ifs.ui.EditTextDialogActivity;
import com.waze.install.InstallPickAccountActivity;
import com.waze.install.SmartLockManager;
import com.waze.location.LocationPermissionActivity;
import com.waze.map.MapViewWrapper;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.social.AddFriendsPopup;
import com.waze.navigate.social.AddFromActivity;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.pioneer.PioneerManager;
import com.waze.profile.AccountSignInDetails;
import com.waze.profile.ImageTaker;
import com.waze.profile.MeetYourWazerPopup;
import com.waze.profile.MyProfileActivity;
import com.waze.profile.NameYourWazerPopup;
import com.waze.profile.PasswordRecoveryPopup;
import com.waze.profile.TempUserProfileActivity;
import com.waze.routes.RoutesActivity;
import com.waze.settings.SettingsDialogListener;
import com.waze.settings.SettingsSound;
import com.waze.settings.SettingsUtils;
import com.waze.share.NameYourselfView;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.utils.CarGasTypeManager;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.layout.SwipeableLayout;
import com.waze.view.map.ProgressAnimation;
import com.waze.voice.CustomPromptManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class MainActivity extends ActivityBase implements IOnUserNameResult {
    public static final int ADDRESS_OPTIONS_CODE = 32778;
    public static final int ADD_STOP_RESULT_CODE = 2;
    public static final int ASK_LOCATION_HISTORY = 5;
    public static final int CALENDAR_REQUEST_CODE = 32789;
    public static final int CAPTURE_IMAGE_CODE = 32770;
    public static final int CARPOOL_REGISTER = 4;
    public static final String EXTRA_REFRESH_ORIENTATION = "EXTRA_REFRESH_ORIENTATION";
    public static final long INITIAL_HEAP_SIZE = 4096;
    public static final int LAYOUT_REQUEST_CODE_MASK = 32768;
    private static final int MAIN_THREAD_PRIORITY = -8;
    public static final int MYFRIENDS_CODE = 32781;
    public static final int MYWAZE_CODE = 32772;
    public static final int NAVBAR_ADD_PLACE_PHOTO_CODE = 32785;
    public static final int NAVIGATE_CODE = 32771;
    public static final int NAVIGATION_LIST_CODE = 32777;
    public static final int NEARBY_GAS_STATIONS_CODE = 32780;
    public static final int POI_POPUP_INFO_REQUEST_CODE = 32791;
    public static final int RECORD_SOUND_CODE = 32769;
    public static final int RELOAD_SEARCH_CODE = 32783;
    public static final int REPORT_ADD_PLACE_PHOTO_CODE = 32784;
    public static final int REPORT_GROUPS_CODE = 32776;
    public static final int REPORT_MENU_PICK_ONE = 32788;
    public static final int REPORT_MENU_SPEECH_REQUEST_CODE = 32790;
    public static final int REQUEST_ADD_DRIVE_SHARE = 32786;
    public static final int RESULT_BECOME_CARPOOLER = 5;
    public static final int RESULT_RESET_CARPOOL_PANE = 7;
    public static final int RESULT_WEBVIEW_ERR = 6;
    public static final int RTALERTS_REQUEST_CODE = 32773;
    public static final int SEARCH_MAP_CODE = 32779;
    public static final int SETTINGS_CODE = 32775;
    public static final int SHARE_CODE = 32774;
    public static final int SHARE_PROCESS_CODE_MASK = 65536;
    public static final int SHARE_REQUEST_CONTACTS_ACCESS = 2;
    public static final int SHARE_REQUEST_LOGIN = 1;
    public static final int SHARE_REQUEST_OPEN_ACTIVITY = 32;
    public static final int SHARE_REQUEST_SHARE_CURRENT_LOCATION = 64;
    public static final int SHARE_REQUEST_SHARE_DRIVE = 16;
    public static final int SHARE_REQUEST_SHARE_HOME = 128;
    public static final int SHARE_REQUEST_SHARE_WORK = 256;
    public static final int SPEECHTT_EXTERNAL_REQUEST_CODE = 4096;
    public static final long SPLASH_DISPLAY_TIMEOUT = 250;
    public static final boolean TEST_PNG = false;
    public static final int TTS_DATA_CHECK_CODE = 8192;
    public static final int TTS_DATA_INSTALL_CODE = 16384;
    public static final int VERIFY_EVENT_CODE = 32782;
    public static boolean bIsRegisteringForCalendar = false;
    public static boolean bReportMapShownAnalytics = false;
    public static boolean bSignupSkipped = false;
    public static boolean bToOpenAccountPopup = false;
    public static boolean bToOpenMeetYourWazer = false;
    public static boolean bToOpenPasswordRecovery = false;
    private static int mBoundIndex = -1;
    public static boolean mIsBound = false;
    private static ArrayList<ITrackOrientation> mOrientationTrackers = new ArrayList(4);
    private static ArrayList<Runnable> mResumeEvents = new ArrayList(10);
    private static TransportationSdkDetails mSDK = null;
    public static String sQuestionID = null;
    private boolean IsLayoutInitialized = false;
    private boolean bIsSearchClicked = false;
    private boolean bIsSentAppType = false;
    private AccountSignInDetails mAccountSignIn = null;
    private AddFriendsPopup mAddFriendsPopup = null;
    private AddressItem mAddressToDrive = null;
    private long mCurrentSessionNumber;
    private GoogleAnalyticsTracker mGoogleAnalyticsTracker = null;
    private ImageTaker mImageTaker;
    private volatile boolean mIsRunning = false;
    private AddressItem mLastAddressItem = null;
    private LayoutManager mLayoutMgr = null;
    private MeetYourWazerPopup mMeetYourWazerPopup = null;
    private NameYourWazerPopup mNameWazerPopup = null;
    private NameYourselfView mNameYourselfView;
    private Runnable mOpenVenueNoAnswerRunnable;
    private int mOrientation;
    private PasswordRecoveryPopup mPasswordRecoveryPopup = null;
    private boolean mPromptSmartLockOnResume;
    private volatile boolean mResumeProgressShow = false;
    private String mSmartLockAnalyticsType;
    private TransportationAppData mTransportationData = null;
    private ViewAnimator mViewAnimator = null;

    class C11791 implements Runnable {
        C11791() throws  {
        }

        public void run() throws  {
            MainActivity.this.setRequestedOrientation(2);
        }
    }

    class C11834 extends RunnableExecutor {
        C11834() throws  {
        }

        public void event() throws  {
            CarGasTypeManager.getInstance().reloadCarAndGasData(null);
        }
    }

    class C11845 implements OnClickListener {
        C11845() throws  {
        }

        public void onClick(View v) throws  {
            try {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TRANSPORTATION_BUTTON_CLICKED);
                MainActivity.this.mTransportationData.communicate_app_callback.send();
            } catch (CanceledException e) {
            }
        }
    }

    class C11856 implements OnDismissListener {
        C11856() throws  {
        }

        public void onDismiss(DialogInterface dialog) throws  {
            NativeManager.getInstance().unregisterOnUserNameResultListerner(MainActivity.this);
            if (MainActivity.bIsRegisteringForCalendar) {
                MainActivity.bIsRegisteringForCalendar = false;
                NativeManager.getInstance().CalendaRequestAccessNTV();
                MainActivity.this.mLayoutMgr.refreshAddressItems();
            }
        }
    }

    class C11867 implements Runnable {
        C11867() throws  {
        }

        public void run() throws  {
            if (AppService.getMainView() != null) {
                AppService.getMainView().onPause();
            }
        }
    }

    class C11889 implements Runnable {
        C11889() throws  {
        }

        public void run() throws  {
            AppService.getNativeManager().startTripServerNavigationNTV();
        }
    }

    public interface ITrackOrientation {
        void onOrientationChanged(int i) throws ;
    }

    protected class OnAgreeMsgSmsService implements DialogInterface.OnClickListener {
        protected OnAgreeMsgSmsService() throws  {
        }

        public void onClick(DialogInterface $r1, int $i0) throws  {
            $r1.cancel();
            if ($i0 == 1) {
                Utils.setTransportationLong(MainActivity.mSDK.PackageNames[MainActivity.mBoundIndex], System.currentTimeMillis());
                MainActivity.this.StartBoundService();
                return;
            }
            NativeManager.getInstance().StopWaze();
        }
    }

    protected class OnAgreeMsgSmsServiceSpotify implements DialogInterface.OnClickListener {
        protected OnAgreeMsgSmsServiceSpotify() throws  {
        }

        public void onClick(DialogInterface $r1, int $i0) throws  {
            $r1.cancel();
            if ($i0 == 1) {
                Utils.setTransportationLong(MainActivity.mSDK.PackageNames[MainActivity.mBoundIndex], System.currentTimeMillis());
                MainActivity.this.StartBoundService();
                if (!SpotifyManager.getInstance().isConnected()) {
                    Log.d("SpotifyManager", "Bound services starting but not connected");
                    SpotifyManager.getInstance().setAuthorizeOnConnect(true);
                    SpotifyManager.getInstance().init();
                    return;
                }
                return;
            }
            SpotifyManager.getInstance().onAuthenticationDeclined("Waze agreement declined");
        }
    }

    protected class OnOkMsgSmsService implements DialogInterface.OnClickListener {

        class C11891 implements DriveToNavigateCallback {
            C11891() throws  {
            }

            public void navigateCallback(int $i0) throws  {
                if ($i0 == 0) {
                    MainActivity.this.mAddressToDrive = null;
                }
            }
        }

        protected OnOkMsgSmsService() throws  {
        }

        public void onClick(DialogInterface $r1, int $i0) throws  {
            $r1.cancel();
            if ($i0 == 1 && MainActivity.this.mAddressToDrive != null) {
                DriveToNativeManager.getInstance().navigate(MainActivity.this.mAddressToDrive, new C11891());
            }
        }
    }

    private final class ProgressToast extends ToastThread {
        private ProgressAnimation mProgressAnimation = null;

        public ProgressToast() throws  {
            super("Progress Toast");
        }

        public Toast show() throws  {
            MainActivity $r1 = MainActivity.this;
            View $r4 = $r1.getLayoutInflater().inflate(C1283R.layout.progress_bar, (ViewGroup) $r1.findViewById(C1283R.id.progress_layout_root));
            this.mProgressAnimation = (ProgressAnimation) $r4.findViewById(C1283R.id.progress_bar_resume);
            this.mProgressAnimation.start();
            Toast $r2 = new Toast($r1);
            $r2.setGravity(16, 0, 0);
            $r2.setDuration(1);
            $r2.setView($r4);
            $r2.show();
            return $r2;
        }

        protected void cancel() throws  {
            super.cancel();
        }

        public void stopToast() throws  {
            Log.d(Logger.TAG, "Cancelling progress toast");
            if (this.mProgressAnimation != null) {
                this.mProgressAnimation.stop();
            }
            super.stopToast();
        }
    }

    public void RunTransportationCheck() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:32:0x0174
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
        r40 = this;
        r0 = r40;
        r8 = r0.IsLayoutInitialized;
        if (r8 != 0) goto L_0x0012;
    L_0x0006:
        r9 = 1;
        r0 = r40;
        r0.IsLayoutInitialized = r9;
        r0 = r40;
        r10 = r0.mLayoutMgr;
        r10.initSwipeLayouts();
    L_0x0012:
        r0 = r40;
        r11 = com.waze.BoundService.getTrasnportationAppData(r0);
        r0 = r40;
        r0.mTransportationData = r11;
        r0 = r40;
        r11 = r0.mTransportationData;
        if (r11 == 0) goto L_0x01e9;
    L_0x0022:
        r8 = mIsBound;
        if (r8 != 0) goto L_0x01e9;
    L_0x0026:
        r12 = com.waze.NativeManager.getInstance();
        r13 = r12.configGetTransportationDetailsNTV();
        if (r13 == 0) goto L_0x010d;
    L_0x0030:
        r14 = 0;
    L_0x0031:
        r15 = r13.PackageNames;
        r0 = r15.length;
        r16 = r0;
        if (r14 >= r0) goto L_0x010d;
    L_0x0038:
        r0 = r40;
        r11 = r0.mTransportationData;
        r0 = r11.communicate_app_name;
        r17 = r0;
        r15 = r13.PackageNames;
        r18 = r15[r14];
        r0 = r17;
        r1 = r18;
        r8 = r0.equals(r1);
        if (r8 == 0) goto L_0x01e6;
    L_0x004e:
        mBoundIndex = r14;
        mSDK = r13;
        r0 = r40;
        r8 = r0.bIsSentAppType;
        if (r8 != 0) goto L_0x006c;
    L_0x0058:
        r12 = com.waze.NativeManager.getInstance();
        r0 = r40;
        r11 = r0.mTransportationData;
        r0 = r11.communicate_app_name;
        r17 = r0;
        r12.SetAppTypeNTV(r0);
        r9 = 1;
        r0 = r40;
        r0.bIsSentAppType = r9;
    L_0x006c:
        r0 = r13.Scopes;
        r19 = r0;
        r16 = mBoundIndex;
        r16 = r19[r16];
        if (r16 <= 0) goto L_0x01dc;
    L_0x0076:
        r15 = r13.PackageNames;
        r16 = mBoundIndex;
        r17 = r15[r16];
        r0 = r17;
        r20 = com.waze.Utils.getTransportationLong(r0);
        r22 = java.lang.System.currentTimeMillis();
        r26 = 90;
        r0 = r26;
        r24 = com.waze.Utils.convertDaysToMilliseconds(r0);
        r20 = r22 - r20;
        r28 = (r20 > r24 ? 1 : (r20 == r24 ? 0 : -1));
        if (r28 <= 0) goto L_0x0198;
    L_0x0094:
        r15 = r13.PackageNames;
        r16 = mBoundIndex;
        r17 = r15[r16];
        r29 = "spotify";
        r0 = r17;
        r1 = r29;
        r8 = r0.contains(r1);
        if (r8 == 0) goto L_0x0113;
    L_0x00a7:
        r12 = com.waze.NativeManager.getInstance();
        r9 = 3686; // 0xe66 float:5.165E-42 double:1.821E-320;
        r17 = r12.getLanguageString(r9);
        r30 = com.waze.ConfigManager.getInstance();
        r9 = 433; // 0x1b1 float:6.07E-43 double:2.14E-321;
        r0 = r30;
        r18 = r0.getConfigValueString(r9);
        r9 = 1;
        r0 = new java.lang.Object[r9];
        r31 = r0;
        r9 = 0;
        r31[r9] = r18;
        r0 = r17;
        r1 = r31;
        r17 = java.lang.String.format(r0, r1);
        r32 = com.waze.MsgBox.getInstance();
        r12 = com.waze.NativeManager.getInstance();
        r9 = 3685; // 0xe65 float:5.164E-42 double:1.8206E-320;
        r18 = r12.getLanguageString(r9);
        r33 = new com.waze.MainActivity$OnAgreeMsgSmsServiceSpotify;
        r0 = r33;
        r1 = r40;
        r0.<init>();
        r12 = com.waze.NativeManager.getInstance();
        r9 = 277; // 0x115 float:3.88E-43 double:1.37E-321;
        r34 = r12.getLanguageString(r9);
        r12 = com.waze.NativeManager.getInstance();
        r9 = 504; // 0x1f8 float:7.06E-43 double:2.49E-321;
        r35 = r12.getLanguageString(r9);
        r9 = 1;
        r36 = -1;
        r0 = r32;
        r1 = r18;
        r2 = r17;
        r3 = r9;
        r4 = r33;
        r5 = r34;
        r6 = r35;
        r7 = r36;
        r0.OpenConfirmDialogCustomTimeoutCbJava(r1, r2, r3, r4, r5, r6, r7);
    L_0x010d:
        r0 = r40;
        r0.UpdateTransportationButton();
        return;
    L_0x0113:
        r12 = com.waze.NativeManager.getInstance();
        r9 = 2645; // 0xa55 float:3.706E-42 double:1.307E-320;
        r17 = r12.getLanguageString(r9);
        r9 = 3;
        r0 = new java.lang.Object[r9];
        r31 = r0;
        r13 = mSDK;
        r15 = r13.AppNames;
        r18 = r15[r14];
        r9 = 0;
        r31[r9] = r18;
        r13 = mSDK;
        r15 = r13.AppNames;
        r18 = r15[r14];
        r9 = 1;
        r31[r9] = r18;
        r12 = com.waze.NativeManager.getInstance();
        r18 = r12.GetSDKLearnMoreURLNTV();
        r9 = 2;
        r31[r9] = r18;
        r0 = r17;
        r1 = r31;
        r17 = java.lang.String.format(r0, r1);
        r32 = com.waze.MsgBox.getInstance();
        goto L_0x014f;
    L_0x014c:
        goto L_0x010d;
    L_0x014f:
        r12 = com.waze.NativeManager.getInstance();
        r9 = 2644; // 0xa54 float:3.705E-42 double:1.3063E-320;
        r18 = r12.getLanguageString(r9);
        r37 = new com.waze.MainActivity$OnAgreeMsgSmsService;
        r0 = r37;
        r1 = r40;
        r0.<init>();
        r12 = com.waze.NativeManager.getInstance();
        goto L_0x016a;
    L_0x0167:
        goto L_0x010d;
    L_0x016a:
        r9 = 277; // 0x115 float:3.88E-43 double:1.37E-321;
        r34 = r12.getLanguageString(r9);
        goto L_0x0178;
    L_0x0171:
        goto L_0x010d;
        goto L_0x0178;
    L_0x0175:
        goto L_0x010d;
    L_0x0178:
        r12 = com.waze.NativeManager.getInstance();
        r9 = 278; // 0x116 float:3.9E-43 double:1.374E-321;
        r35 = r12.getLanguageString(r9);
        r9 = 1;
        r36 = -1;
        r0 = r32;
        r1 = r18;
        r2 = r17;
        r3 = r9;
        r4 = r37;
        r5 = r34;
        r6 = r35;
        r7 = r36;
        r0.OpenConfirmDialogCustomTimeoutCbJava(r1, r2, r3, r4, r5, r6, r7);
        goto L_0x014c;
    L_0x0198:
        r0 = r40;
        r0.StartBoundService();
        r0 = r40;
        r11 = r0.mTransportationData;
        r0 = r11.communicate_app_name;
        r17 = r0;
        r29 = "spotify";
        r0 = r17;
        r1 = r29;
        r8 = r0.contains(r1);
        if (r8 == 0) goto L_0x01cc;
    L_0x01b2:
        r12 = com.waze.NativeManager.getInstance();
        r8 = r12.isNavigatingNTV();
        if (r8 == 0) goto L_0x010d;
    L_0x01bc:
        r12 = com.waze.NativeManager.getInstance();
        r38 = r12.getNavBarManager();
        if (r38 == 0) goto L_0x010d;
    L_0x01c6:
        r0 = r38;
        r0.sendLatest();
        goto L_0x0167;
    L_0x01cc:
        r0 = r40;
        r10 = r0.mLayoutMgr;
        r13 = mSDK;
        r15 = r13.AppNames;
        r17 = r15[r14];
        r0 = r17;
        r10.showTransportationLayerTooltip(r0);
        goto L_0x0171;
    L_0x01dc:
        r0 = r40;
        r0.StartBoundService();
        goto L_0x0175;
        goto L_0x01e6;
    L_0x01e3:
        goto L_0x0031;
    L_0x01e6:
        r14 = r14 + 1;
        goto L_0x01e3;
    L_0x01e9:
        r8 = mIsBound;
        if (r8 == 0) goto L_0x010d;
    L_0x01ed:
        r0 = r40;
        r11 = r0.mTransportationData;
        r0 = r11.communicate_app_name;
        r17 = r0;
        r29 = "spotify";
        r0 = r17;
        r1 = r29;
        r8 = r0.contains(r1);
        if (r8 != 0) goto L_0x010d;
    L_0x0202:
        r12 = com.waze.NativeManager.getInstance();
        r13 = r12.configGetTransportationDetailsNTV();
        if (r13 == 0) goto L_0x010d;
    L_0x020c:
        r15 = r13.PackageNames;
        r14 = mBoundIndex;
        r17 = r15[r14];
        r29 = "spotify";
        r0 = r17;
        r1 = r29;
        r8 = r0.contains(r1);
        if (r8 == 0) goto L_0x010d;
    L_0x021f:
        r39 = com.waze.audioextension.spotify.SpotifyManager.getInstance();
        r0 = r39;
        r0.disconnect();
        r9 = 0;
        mIsBound = r9;
        goto L_0x022f;
    L_0x022c:
        goto L_0x010d;
    L_0x022f:
        r0 = r40;
        r0.RunTransportationCheck();
        goto L_0x022c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.MainActivity.RunTransportationCheck():void");
    }

    public void onCreate(android.os.Bundle r31) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:30:0x01b3 in {4, 5, 8, 15, 19, 20, 24, 28, 31, 34, 35} preds:[]
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
        r30 = this;
        com.waze.AppService.getServiceMsgDispatcher();
        r0 = r30;
        com.waze.AppService.setMainActivity(r0);
        r0 = r30;
        r1 = r31;
        super.onCreate(r1);
        r3 = com.waze.install.SmartLockManager.getInstance();
        r0 = r30;
        r4 = r0.getApplicationContext();
        r3.initialize(r4);
        r5 = 1;
        r0 = r30;
        r0.requestWindowFeature(r5);
        r5 = 1;
        r0 = r30;
        r0.mIsMainActivity = r5;
        r6 = com.waze.NativeManager.IsAppStarted();
        if (r6 != 0) goto L_0x0050;
    L_0x002d:
        r0 = r30;
        r7 = r0.getResources();
        r8 = r7.getConfiguration();
        r9 = r8.orientation;
        r5 = 1;
        if (r9 != r5) goto L_0x0192;
    L_0x003c:
        r5 = 1;
        r0 = r30;
        r0.setRequestedOrientation(r5);
    L_0x0042:
        r10 = new com.waze.MainActivity$1;
        r0 = r30;
        r10.<init>();
        r11 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r30;
        r0.postDelayed(r10, r11);
    L_0x0050:
        r0 = r30;
        r7 = r0.getResources();
        r8 = r7.getConfiguration();
        r9 = r8.orientation;
        r0 = r30;
        r0.mOrientation = r9;
        r13 = new com.waze.CustomExceptionHandler;
        r13.<init>();
        java.lang.Thread.setDefaultUncaughtExceptionHandler(r13);
        r14 = com.google.android.apps.analytics.GoogleAnalyticsTracker.getInstance();
        r0 = r30;
        r0.mGoogleAnalyticsTracker = r14;
        r0 = r30;
        r14 = r0.mGoogleAnalyticsTracker;
        r15 = "UA-24084788-1";
        r0 = r30;
        r14.start(r15, r0);
        r16 = new com.waze.LayoutManager;
        r0 = r16;
        r1 = r30;
        r0.<init>(r1);
        r0 = r16;
        r1 = r30;
        r1.mLayoutMgr = r0;
        r0 = r30;
        r0 = r0.mLayoutMgr;
        r16 = r0;
        r17 = r0.getMainLayout();
        r0 = r30;
        r1 = r17;
        r0.setContentView(r1);
        r18 = com.waze.WazeApplication.startSW;
        r15 = "MainActivity setContentView";
        r5 = 0;
        r0 = r18;
        r0.startDelta(r15, r5);
        r0 = r30;
        r0.startApp();
        r0 = r30;
        r0 = r0.mLayoutMgr;
        r16 = r0;
        r0.registerCenterOnMeListener();
        r19 = com.google.android.gms.common.GoogleApiAvailability.getInstance();
        r0 = r19;
        r1 = r30;
        r9 = r0.isGooglePlayServicesAvailable(r1);
        if (r9 != 0) goto L_0x01be;
    L_0x00c1:
        r15 = "WAZE";
        r20 = "MainActivity: Registering to GCM for token";
        r0 = r20;
        android.util.Log.e(r15, r0);
        r21 = new android.content.Intent;
        r22 = com.waze.push.RegistrationIntentService.class;
        r0 = r21;
        r1 = r30;
        r2 = r22;
        r0.<init>(r1, r2);
        r0 = r30;
        r1 = r21;
        r0.startService(r1);
    L_0x00de:
        r23 = com.waze.social.facebook.FacebookManager.getInstance();
        r0 = r23;
        r1 = r30;
        r0.initialize(r1);
        r18 = com.waze.WazeApplication.startSW;
        r15 = "MainActivity onCreate ENDED";
        r5 = 0;
        r0 = r18;
        r0.startDelta(r15, r5);
        r0 = r30;
        r21 = r0.getIntent();
        if (r21 == 0) goto L_0x01c8;
    L_0x00fb:
        r15 = "AnalyticsType";
        r0 = r21;
        r24 = r0.getStringExtra(r15);
        if (r24 == 0) goto L_0x011c;
    L_0x0105:
        r15 = "LH";
        r0 = r24;
        r6 = r0.equals(r15);
        if (r6 == 0) goto L_0x011c;
    L_0x010f:
        r15 = "PUSH_MESSAGE_LAUNCHED";
        r20 = "VAUE";
        r25 = "LOCATION_HISTORY";
        r0 = r20;
        r1 = r25;
        com.waze.analytics.Analytics.log(r15, r0, r1);
    L_0x011c:
        r15 = "ButtonName";
        r0 = r21;
        r24 = r0.getStringExtra(r15);
        if (r24 == 0) goto L_0x0163;
    L_0x0126:
        r15 = "NotificationType";
        r0 = r21;
        r26 = r0.getStringExtra(r15);
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r0 = r27;
        r1 = r26;
        r27 = r0.append(r1);
        r15 = "|";
        r0 = r27;
        r27 = r0.append(r15);
        r0 = r27;
        r1 = r24;
        r27 = r0.append(r1);
        goto L_0x0152;
    L_0x014f:
        goto L_0x00de;
    L_0x0152:
        r0 = r27;
        r24 = r0.toString();
        r15 = "PUSH_MESSAGE_BUTTON_CLICKED";
        r20 = "VAUE|TYPE";
        r0 = r20;
        r1 = r24;
        com.waze.analytics.Analytics.log(r15, r0, r1);
    L_0x0163:
        r15 = "EXTRA_REFRESH_ORIENTATION";
        r0 = r21;
        r6 = r0.hasExtra(r15);
        if (r6 == 0) goto L_0x01c9;
    L_0x016d:
        r5 = -1;
        r0 = r30;
        r0.mOrientation = r5;
        r5 = 1;
        r0 = r30;
        r0.mIsRunning = r5;
        r0 = r30;
        r7 = r0.getResources();
        r8 = r7.getConfiguration();
        r9 = r8.orientation;
        r0 = r30;
        r0.handleOrientationChange(r9);
        r0 = r30;
        r0 = r0.mLayoutMgr;
        r16 = r0;
        r0.adjustReportAndSpeedometer();
        return;
    L_0x0192:
        r0 = r30;
        r28 = r0.getWindowManager();
        r0 = r28;
        r29 = r0.getDefaultDisplay();
        r0 = r29;
        r9 = r0.getRotation();
        r5 = 3;
        if (r9 != r5) goto L_0x01b7;
    L_0x01a7:
        goto L_0x01ab;
    L_0x01a8:
        goto L_0x0042;
    L_0x01ab:
        r5 = 8;
        r0 = r30;
        r0.setRequestedOrientation(r5);
        goto L_0x01a8;
        goto L_0x01b7;
    L_0x01b4:
        goto L_0x0042;
    L_0x01b7:
        r5 = 0;
        r0 = r30;
        r0.setRequestedOrientation(r5);
        goto L_0x01b4;
    L_0x01be:
        r15 = "WAZE";
        r20 = "MainActivity: Google play services is unavailable. Cannot register to GCM and receive a token";
        r0 = r20;
        android.util.Log.e(r15, r0);
        goto L_0x014f;
    L_0x01c8:
        return;
    L_0x01c9:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.MainActivity.onCreate(android.os.Bundle):void");
    }

    static {
        Logger.create();
    }

    private void startApp() throws  {
        if (NativeManager.getInstance() == null) {
            NativeManager.Start();
            AppService.start(this);
            NativeManager.getInstance().setUpdateHandler(NativeManager.UH_LOGIN_DONE, this.mHandler);
            NativeManager.getInstance().setUpdateHandler(NativeManager.UH_NAVIGATION_STATE_CHANGED, this.mHandler);
        }
    }

    public void OpenAddressPreview(AddressItem $r1, String $r2, String $r3, String $r4, int $i0, String $r5, String $r6, boolean $z0) throws  {
        if ($r2 == null || $r2.isEmpty()) {
            Intent intent = new Intent(this, AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
            if ($z0) {
                intent.putExtra(PublicMacros.CLEAR_ADS_CONTEXT, true);
            }
            startActivityForResult(intent, 1);
            return;
        }
        DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        NativeManager.getInstance().AutoCompletePlaceClicked(null, $r2, null, null, $r3, false, $r4, false, $i0, $r5, $r6);
        this.mLastAddressItem = $r1;
        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
        final AddressItem addressItem = $r1;
        final boolean z = $z0;
        this.mOpenVenueNoAnswerRunnable = new Runnable() {
            public void run() throws  {
                DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, MainActivity.this.mHandler);
                NativeManager.getInstance().CloseProgressPopup();
                MainActivity.this.mLastAddressItem = null;
                Intent $r1 = new Intent(MainActivity.this, AddressPreviewActivity.class);
                $r1.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
                if (z) {
                    $r1.putExtra(PublicMacros.CLEAR_ADS_CONTEXT, true);
                }
                MainActivity.this.startActivityForResult($r1, 1);
            }
        };
        postDelayed(this.mOpenVenueNoAnswerRunnable, (long) NativeManager.getInstance().getVenueGetTimeout());
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
            NativeManager.getInstance().CloseProgressPopup();
            cancel(this.mOpenVenueNoAnswerRunnable);
            AddressItem $r7 = (AddressItem) $r1.getData().getSerializable("address_item");
            if (this.mLastAddressItem != null) {
                $r7.setType(this.mLastAddressItem.getType());
                $r7.setId(this.mLastAddressItem.getId());
                this.mLastAddressItem = null;
            }
            DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            if ($r7.getLocationX().equals(Integer.valueOf(-1))) {
                if ($r7.getLocationY().equals(Integer.valueOf(-1))) {
                    if (this.mOpenVenueNoAnswerRunnable == null) {
                        return true;
                    }
                    this.mOpenVenueNoAnswerRunnable.run();
                    this.mOpenVenueNoAnswerRunnable = null;
                    return true;
                }
            }
            this.mOpenVenueNoAnswerRunnable = null;
            Intent intent = new Intent(this, AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, $r7);
            startActivityForResult(intent, 1);
            return true;
        }
        if ($r1.what == NativeManager.UH_LOGIN_DONE) {
            NativeManager $r3 = NativeManager.getInstance();
            int $i0 = NativeManager.UH_LOGIN_DONE;
            IncomingHandler $r2 = this.mHandler;
            $r3.unsetUpdateHandler($i0, $r2);
            AnalyticsBuilder $r14 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MAP_SHOWN_AND_READY);
            AnalyticsBuilder analyticsBuilder = $r14;
            analyticsBuilder.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_UP_TIME, AppService.timeSinceCreated()).send();
            Log.i("MainActivity", "Map shown and ready");
            SpotifyManager.getInstance();
            CustomPromptManager.getInstance().initialize(this);
            SmartLockManager.getInstance().checkSmartLockForExistingUser();
            this.mCurrentSessionNumber = ConfigManager.getInstance().getConfigValueLong(201);
            getLayoutMgr().initSearchOnMap();
            cancelSplash();
            LayoutManager $r18 = this.mLayoutMgr;
            $r18.onLogin();
            if (LocationSensorListener.permissionsMissing()) {
                ActivityBase.closeAllActivities();
                startActivity(new Intent(this, LocationPermissionActivity.class));
            }
        }
        if ($r1.what == NativeManager.UH_NAVIGATION_STATE_CHANGED) {
            boolean $z0 = $r1.getData().getBoolean("is_navigating");
            $r18 = this.mLayoutMgr;
            LayoutManager $r182 = $r18;
            $r18.onNavigationStateChanged($z0);
        }
        return super.myHandleMessage($r1);
    }

    public void promptForSmartLockWhenResumed(String $r1) throws  {
        this.mPromptSmartLockOnResume = true;
        this.mSmartLockAnalyticsType = $r1;
    }

    public void onResume() throws  {
        boolean $z0 = true;
        ProgressToast $r3 = null;
        Log.w("WAZE DEBUG", "ON RESUME. Task id: " + getTaskId());
        super.onResume();
        if (this.mLayoutMgr != null) {
            this.mLayoutMgr.onResume();
        }
        if (AppService.getMainView() != null) {
            if (VERSION.SDK_INT >= 21) {
                if (getLayoutMgr().isSwipeableLayoutOpened()) {
                    getWindow().setStatusBarColor(getResources().getColor(C1283R.color.blue_status));
                } else {
                    getWindow().setStatusBarColor(-16777216);
                }
            }
            if (this.mPromptSmartLockOnResume) {
                this.mPromptSmartLockOnResume = false;
                SmartLockManager.getInstance().saveCredentials(this, this.mSmartLockAnalyticsType);
                this.mSmartLockAnalyticsType = null;
            }
            AppService.getMainView().onResume();
            this.mIsRunning = true;
            NativeManager.onMainResume();
            if (this.mResumeProgressShow && (AppService.getPrevActivity() == this || AppService.getPrevActivity() == null)) {
                $r3 = new ProgressToast();
                $r3.start();
                SystemClock.sleep(20);
            }
            IntentManager.ParseIntentDataFlags(this);
            if (AppService.IsAppRunning()) {
                IntentManager.HandleIntent(this, true);
            }
            if (this.mResumeProgressShow && $r3 != null) {
                $r3.stopToast();
            }
            postOnResumeEvents();
            if (bToOpenMeetYourWazer) {
                openMeetYourWazer();
            }
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(AppService.getAppContext()) == 0) {
                AdsTracking.getAdsTrackingData();
            }
            RunnableExecutor c11823 = new RunnableExecutor(AppService.getInstance()) {

                class C11811 implements Runnable {
                    C11811() throws  {
                    }

                    public void run() throws  {
                        MainActivity.this.RunTransportationCheck();
                    }
                }

                public void event() throws  {
                    MainActivity.this.runOnUiThread(new C11811());
                }
            };
            c11823 = new C11834();
            if (NativeManager.IsAppStarted()) {
                c11823.run();
                c11823.run();
            } else {
                NativeManager.registerOnAppStartedEvent(c11823);
                NativeManager.registerOnAppStartedEvent(c11823);
            }
            if (bToOpenAccountPopup) {
                bToOpenAccountPopup = false;
            } else if (bReportMapShownAnalytics && areAllSignUpDialogsClosed()) {
                MyWazeNativeManager $r14 = MyWazeNativeManager.getInstance();
                if (!($r14.getWasLoginSuccess() || $r14.getWasFbTokenSet())) {
                    $z0 = false;
                }
                if (bReportMapShownAnalytics && ($z0 || bSignupSkipped)) {
                    bReportMapShownAnalytics = false;
                    bSignupSkipped = false;
                    reportMapShown();
                }
            }
            if (this.mLayoutMgr != null) {
                this.mLayoutMgr.updatePoiPopupIfNeeded();
            }
        }
    }

    public boolean openSpotifyApp() throws  {
        if (!mIsBound) {
            return false;
        }
        if (this.mTransportationData == null) {
            return false;
        }
        if (!this.mTransportationData.communicate_app_name.contains("spotify")) {
            return false;
        }
        try {
            this.mTransportationData.communicate_app_callback.send();
            return true;
        } catch (CanceledException e) {
            return false;
        }
    }

    public void UpdateTransportationButton() throws  {
        if (this.mTransportationData == null || !mIsBound) {
            findViewById(C1283R.id.TransportationLayout).setVisibility(8);
            return;
        }
        if (this.mTransportationData.communicate_app_name.contains("spotify")) {
            findViewById(C1283R.id.TransportationLayout).setVisibility(8);
        } else {
            findViewById(C1283R.id.TransportationLayout).setVisibility(0);
        }
        if (this.mTransportationData.communicate_app_icon != null) {
            findViewById(C1283R.id.transportationButton).setBackgroundDrawable(this.mTransportationData.communicate_app_icon);
        }
        if (this.mTransportationData.communicate_app_callback != null) {
            findViewById(C1283R.id.transportationButton).setOnClickListener(new C11845());
        }
    }

    public boolean areAllSignUpDialogsClosed() throws  {
        return (this.mNameWazerPopup == null || !this.mNameWazerPopup.isShowing()) && (this.mAccountSignIn == null || !this.mAccountSignIn.isShowing());
    }

    public boolean IsAccountDetailsShown() throws  {
        return this.mAccountSignIn != null;
    }

    public boolean IsNameYourWazerShown() throws  {
        return this.mNameWazerPopup != null;
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        if (this.mLayoutMgr.isPopupsShown()) {
            this.mLayoutMgr.callCloseAllPopups(1);
        }
        return super.onKeyDown($i0, $r1);
    }

    public void onFacebookTokenSet() throws  {
        if (this.mAccountSignIn != null) {
            this.mAccountSignIn.onFacebookTokenSet();
        }
    }

    public void SetUserUpdateResult(boolean $z0) throws  {
        if (this.mNameWazerPopup != null && this.mNameWazerPopup.isShowing()) {
            this.mNameWazerPopup.OnUpdateResult($z0);
        }
        if (this.mPasswordRecoveryPopup != null && this.mPasswordRecoveryPopup.isShowing()) {
            this.mPasswordRecoveryPopup.OnUpdateResult($z0);
        } else if ((this.mAccountSignIn == null || !this.mAccountSignIn.isShowing()) && this.mIsRunning && bReportMapShownAnalytics && $z0) {
            bReportMapShownAnalytics = false;
            bSignupSkipped = false;
            reportMapShown();
        }
    }

    private void reportMapShown() throws  {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_MAP_SHOWN, null, null, true);
        NativeManager.getInstance().SetSocialIsFirstTime(false);
    }

    public void onUserNameResult(int $i0, String $r1) throws  {
        if (this.mNameWazerPopup == null || !this.mNameWazerPopup.isShowing()) {
            if (this.mAccountSignIn != null) {
                this.mAccountSignIn.dismiss();
                this.mAccountSignIn = null;
            }
            DisableOrientation();
            if (this.mLayoutMgr.isSplashVisible()) {
                this.mLayoutMgr.cancelSplash();
            }
            this.mNameWazerPopup = new NameYourWazerPopup(this);
            this.mNameWazerPopup.getWindow().setSoftInputMode(32);
            this.mNameWazerPopup.show();
            this.mNameWazerPopup.onUserNameResult($i0, $r1);
            this.mNameWazerPopup.setOnDismissListener(new C11856());
            return;
        }
        this.mNameWazerPopup.onUserNameResult($i0, $r1);
        if (this.mAccountSignIn != null) {
            this.mAccountSignIn.dismiss();
            this.mAccountSignIn = null;
        }
    }

    public void SetMyWazeData(MyWazeData $r1) throws  {
        Intent $r2 = new Intent(this, RequestPermissionActivity.class);
        $r2.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.READ_CONTACTS"});
        startActivityForResult($r2, 0);
        if (this.mAccountSignIn != null && this.mAccountSignIn.isShowing()) {
            this.mAccountSignIn.SetMyWazeData($r1);
        } else if (this.mPasswordRecoveryPopup != null && this.mPasswordRecoveryPopup.isShowing()) {
            this.mPasswordRecoveryPopup.SetMyWazeData($r1);
        }
    }

    public void onPause() throws  {
        super.onPause();
        if (AppService.getMainView() != null) {
            if (this.bIsSearchClicked) {
                this.bIsSearchClicked = false;
                new Handler().postDelayed(new C11867(), 300);
            } else {
                AppService.getMainView().onPause();
            }
        }
        this.mLayoutMgr.onPause();
        this.mIsRunning = false;
        NativeManager $r4 = AppService.getNativeManager();
        if ($r4 != null) {
            $r4.asrCancel(AnalyticsEvents.f50xedcf1877);
        }
        if ($r4 != null && !NativeManager.IsAppStarted()) {
            cancelSplash();
        }
    }

    protected void onNewIntent(Intent $r1) throws  {
        super.onNewIntent($r1);
        setIntent($r1);
    }

    protected void onStart() throws  {
        super.onStart();
    }

    public void onStop() throws  {
        super.onStop();
    }

    protected void onDestroy() throws  {
        Logger.m43w("Destroying main activity");
        NativeManager.getInstance().unregisterOnUserNameResultListerner(this);
        NativeManager.getInstance().unsetUpdateHandler(NativeManager.UH_LOGIN_DONE, this.mHandler);
        NativeManager.getInstance().unsetUpdateHandler(NativeManager.UH_NAVIGATION_STATE_CHANGED, this.mHandler);
        if (CarpoolOnboardingManager.wasOnboardingStarted()) {
            CarpoolOnboardingManager.getInstance().resetOnboardingManager();
        }
        if (AppService.getMainActivity() == this) {
            AppService.setMainActivity(null);
        }
        super.onDestroy();
    }

    public void setNameYourselfView(NameYourselfView $r1) throws  {
        this.mNameYourselfView = $r1;
        if (this.mNameYourselfView != null) {
            this.mNameYourselfView.adjustForKeyboard(true);
        }
    }

    public void onBackPressed() throws  {
        if (this.mNameYourselfView != null) {
            this.mNameYourselfView.close();
        } else {
            this.mLayoutMgr.onBackPressed();
        }
    }

    private void handleOrientationChange(int $i0) throws  {
        Log.i(Logger.TAG, "Configuration changed: " + $i0);
        if (this.mOrientation == $i0) {
            return;
        }
        if (!AutoUtils.isAutoMode() || (AutoUtils.isAutoMode() && $i0 != 1)) {
            this.mOrientation = $i0;
            this.mLayoutMgr.getMainLayout().requestLayout();
            this.mLayoutMgr.onOrientationChanged(this.mOrientation);
            if (NativeManager.IsAppStarted()) {
                AppService.getNativeManager().notifyOrientationChanged();
            }
            Iterator $r7 = mOrientationTrackers.iterator();
            while ($r7.hasNext()) {
                ((ITrackOrientation) $r7.next()).onOrientationChanged(this.mOrientation);
            }
        }
    }

    public void onConfigurationChanged(final Configuration $r1) throws  {
        if (IsRunning()) {
            handleOrientationChange($r1.orientation);
        } else {
            registerOnResumeEvent(new Runnable() {
                public void run() throws  {
                    MainActivity.this.handleOrientationChange($r1.orientation);
                }
            });
        }
        super.onConfigurationChanged($r1);
    }

    public void dismissNavResultsDialog(boolean $z0) throws  {
        this.mLayoutMgr.closeNavResults($z0);
    }

    public void scheduleStartNavigation() throws  {
        NativeManager.Post(new C11889());
    }

    public void dismissMenus() throws  {
        if (this.mLayoutMgr != null) {
            this.mLayoutMgr.closeSwipeableLayout();
        }
    }

    public void openSoundActions() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_MUTE_TOGGLE);
        SettingsSound.getSoundOptions();
        NativeManager $r2 = NativeManager.getInstance();
        int $i0 = SettingsSound.getSoundSelectionFromConfig();
        SettingsUtils.showSubmenuStyle(AppService.getMainActivity(), $r2.getLanguageString(140), SettingsSound.SOUND_OPTIONS, $i0, new SettingsDialogListener() {
            public void onComplete(int $i0) throws  {
                SettingsSound.setSoundValInConfig($i0);
                MainActivity.this.mLayoutMgr.adjustSoundButton();
            }
        }, C1283R.style.CustomPopupLargeText);
    }

    public void NotifyNativeManagerWithGcmToken() throws  {
        boolean $z0 = NativeManager.setPushToken(true);
        if ($z0) {
            Logger.m41i("RegistrationIntentService:notifyNativeManager: GCM Registration notifying native manager, app started=" + $z0);
            return;
        }
        Logger.m41i("RegistrationIntentService:notifyNativeManager: GCM Registration notifying native manager, app not started yet, delaying");
        postDelayed(new Runnable() {
            public void run() throws  {
                MainActivity.this.NotifyNativeManagerWithGcmToken();
            }
        }, 1000);
    }

    public boolean onCreateOptionsMenu(Menu aMenu) throws  {
        this.mLayoutMgr.openMainMenu();
        return false;
    }

    public boolean onSearchRequested() throws  {
        startNavigateActivity();
        return false;
    }

    public void setImageTaker(ImageTaker $r1) throws  {
        this.mImageTaker = $r1;
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1 && $i0 != 1234) {
            this.mLayoutMgr.closeSwipeableLayout();
        }
        SmartLockManager.getInstance().credentialsResolutionActivityResult($i0, $i1, $r1, null);
        if (this.mLayoutMgr != null) {
            this.mLayoutMgr.adjustSoundButton();
        }
        if ($i0 == 73520) {
        }
        if ($i0 == 401985 && $i1 == -1) {
            postDelayed(new Runnable() {
                public void run() throws  {
                    FriendsActivity.OpenFriendsActivityOrLogin(MainActivity.this);
                }
            }, 1500);
        }
        if (($i0 == 222 || $i0 == 223) && this.mImageTaker != null) {
            this.mImageTaker.onActivityResult($i0, $i1, $r1);
            if ($i1 == -1 && this.mImageTaker.getImagePath() != null) {
                File file = new File(this.mImageTaker.getImagePath());
                NativeManager.getInstance().UploadProfileImage(file.getAbsolutePath());
            }
        }
        if ($i0 == 16777217) {
            if ($i1 == 0) {
                if (!NativeManager.getInstance().isNavigatingNTV()) {
                    this.mLayoutMgr.openRightSideToRidesListImmediate();
                }
            } else if ($i1 == 10) {
                RightSideMenu $r17 = this.mLayoutMgr.getRightSideMenu();
                if ($r17 != null) {
                    CarpoolRidesFragment $r18 = $r17.getCarpoolRidesFragment();
                    if ($r18 != null && $r18.isAdded()) {
                        $r18.setupActivity();
                    }
                }
            }
        }
        if ($i0 == 5000 && $i1 == -1 && this.mMeetYourWazerPopup != null) {
            MeetYourWazerPopup meetYourWazerPopup = this.mMeetYourWazerPopup;
            MeetYourWazerPopup $r10 = meetYourWazerPopup;
            meetYourWazerPopup.onActivityResult($i0, $i1, $r1);
        }
        if ($i0 == 222 || $i0 == 223) {
            this.mLayoutMgr.informProfilePictureChanged();
            DisableOrientation();
            if (this.mAccountSignIn != null) {
                AccountSignInDetails accountSignInDetails = this.mAccountSignIn;
                AccountSignInDetails $r11 = accountSignInDetails;
                accountSignInDetails.onActivityResult($i0, $i1, $r1);
            }
        }
        if (($i0 == 1 || $i0 == 32775) && $i1 == -1 && this.mLayoutMgr.isSwipeableLayoutOpened()) {
            this.mLayoutMgr.closeSwipeableLayout();
        }
        if ($i1 == 3) {
            this.mLayoutMgr.closeSwipeableLayout();
        }
        if ((65536 & $i0) != 0) {
            if (($i0 & 2) != 0 && $i1 == -1) {
                NativeManager.bToUploadContacts = true;
                AddressBookImpl.getInstance().performSync(true, null);
                ShareUtility.shareLocationOrDrive(this, $i0);
            } else if (($i0 & 1) != 0 && $i1 == -1) {
                NativeManager.bToUploadContacts = true;
                final int i = $i0;
                postDelayed(new Runnable() {
                    public void run() throws  {
                        ShareUtility.shareLocationOrDrive(MainActivity.this, i);
                    }
                }, 1500);
            }
        }
        if ($i0 == 32786 && $i1 == -1 && $r1 != null && $r1.hasExtra(AddFromActivity.INTENT_SELECTED)) {
            this.mLayoutMgr.updateNavResultShare((ArrayList) $r1.getExtras().getSerializable(AddFromActivity.INTENT_SELECTED));
        }
        if ($i0 == 1001) {
            this.mLayoutMgr.refreshRecentsNavigationList();
            if ($i1 == -1 || $i1 == 5) {
                this.mLayoutMgr.closeSwipeableLayout();
            }
        }
        if ($i0 == 1234) {
            if ($i1 == -1) {
                ArrayList $r15 = $r1.getStringArrayListExtra("android.speech.extra.RESULTS");
                if ($r15.size() > 0) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VOICE_SEARCH_RECOGNIZED);
                    this.mLayoutMgr.setNavigateSearchTerm((String) $r15.get(0), true);
                }
            } else if ($i1 == 0) {
                this.mLayoutMgr.cancelVoiceSearch();
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LISTENING_CANCELLED).send();
            } else {
                this.mLayoutMgr.cancelVoiceSearch();
            }
        }
        if ($i0 == 1000) {
            scheduleStartNavigation();
        }
        if ($i1 == 3 || $i0 == 4000) {
            this.mLayoutMgr.onActivityResult(this, $i0, $i1, $r1);
        } else if ($i1 == 5) {
            openCarpoolRightSideMenu();
        } else if ($i1 == 7) {
            this.mLayoutMgr.closeSwipeableLayout();
        } else {
            if ($i0 == 5715) {
                this.mLayoutMgr.resumeNavResultCounter();
            }
            if ($i0 == 1337) {
                SpotifyManager.getInstance().onAuthorizationResponseSSO($i1, $r1);
            }
            super.onActivityResult($i0, $i1, $r1);
            NativeManager $r9 = AppService.getNativeManager();
            if ($i0 == 512) {
                this.mLayoutMgr.onActivityResult(this, $i0, $i1, $r1);
            }
            if ($i0 == 4096) {
                $r9.getSpeechttManager().OnResultsExternal($i1, $r1);
            }
            if ($i1 == 32782) {
                this.mAddressToDrive = (AddressItem) $r1.getExtras().getSerializable(PublicMacros.ADDRESS_ITEM);
                MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_THANKS), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_EVENTS_SHOW_UP_IN_NAVIGATE_TEXT), true, new OnOkMsgSmsService(), NativeManager.getInstance().getLanguageString(378), NativeManager.getInstance().getLanguageString(375), -1);
            }
            if ($i0 == 8192) {
                $r9.getTtsManager().onDataCheckResult($i1, $r1);
            }
            if ($i0 == 16384) {
                $r9.getTtsManager().onDataInstallResult($i1, $r1);
            }
            if ((32768 & $i0) > 0) {
                this.mLayoutMgr.onActivityResult(this, $i0, $i1, $r1);
            }
            if ($i0 == 7781) {
                this.mLayoutMgr.refreshRecentsNavigationList();
            }
            if ($i0 == 32789 && $i1 == -1) {
                this.mLayoutMgr.openMainMenu();
                this.mLayoutMgr.refreshRecentsNavigationList();
            }
        }
    }

    public void openCarpoolRightSideMenu() throws  {
        this.mLayoutMgr.openRightSide();
    }

    public void openCarpoolSideMenu() throws  {
        post(new Runnable() {
            public void run() throws  {
                MainActivity.this.mLayoutMgr.openRightSide();
            }
        });
    }

    public void closeMenus() throws  {
        this.mLayoutMgr.closeSwipe();
    }

    protected void StartBoundService() throws  {
        if (BoundService.getInstance() != null) {
            BoundService.getInstance().InitDetails(mBoundIndex, mSDK);
            mIsBound = true;
            UpdateTransportationButton();
            BoundService.getInstance().SendAgreement(true);
        }
    }

    public static void registerOnResumeEvent(Runnable $r0) throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 == null || !$r1.IsRunning()) {
            mResumeEvents.add($r0);
        } else {
            $r0.run();
        }
    }

    private void postOnResumeEvents() throws  {
        while (mResumeEvents.size() > 0) {
            ((Runnable) mResumeEvents.remove(0)).run();
        }
    }

    public boolean IsRunning() throws  {
        return this.mIsRunning;
    }

    public void setResumeProgressShow(boolean $z0) throws  {
        this.mResumeProgressShow = $z0;
    }

    public MapViewWrapper getMainView() throws  {
        return this.mLayoutMgr.getAppView();
    }

    ViewAnimator getAnimator() throws  {
        return this.mViewAnimator;
    }

    public LayoutManager getLayoutMgr() throws  {
        return this.mLayoutMgr;
    }

    public void EnableOrientation() throws  {
        setRequestedOrientation(2);
    }

    public void DisableOrientation() throws  {
        setRequestedOrientation(1);
    }

    public void cancelSplash() throws  {
        if (!PioneerManager.isActive() && this.mMeetYourWazerPopup == null) {
            setRequestedOrientation(2);
        }
        if (this.mLayoutMgr.isSplashVisible()) {
            this.mLayoutMgr.cancelSplash();
        }
    }

    public void startEditTextDialog(int $i0, long $l1, long $l2) throws  {
        Intent $r1 = new Intent(this, EditTextDialogActivity.class);
        $r1.putExtra(EditTextDialogActivity.ARG_NATIVE, true);
        $r1.putExtra(EditTextDialogActivity.ARG_TITLE_DS, $i0);
        $r1.putExtra(EditTextDialogActivity.ARG_CALLBACK, $l1);
        $r1.putExtra(EditTextDialogActivity.ARG_CONTEXT, $l2);
        $r1.putExtra(EditTextDialogActivity.ARG_SPEECH, true);
        startActivity($r1);
    }

    public void startStopPointActivity(boolean stopPointMessage) throws  {
        ((SwipeableLayout) findViewById(C1283R.id.mainContentWrapper)).openSwipe(true);
    }

    public void speechRecognitionClicked(View v) throws  {
        Log.d(Logger.TAG, "SR pressed");
        Analytics.log("VOICE_SEARCH");
        try {
            Intent $r2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
            $r2.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
            startActivityForResult($r2, 1234);
        } catch (Exception e) {
        }
    }

    public void SearchBarClicked(View v) throws  {
        boolean $z0 = false;
        this.bIsSearchClicked = true;
        if (getResources().getConfiguration().orientation == 1) {
            v = findViewById(C1283R.id.SearchBarLayout);
        } else {
            v = findViewById(C1283R.id.SearchBarLayout_ls);
            $z0 = true;
        }
        AnimationUtils.openNavigateScreen(v, new AnimationListener() {

            class C11781 implements Runnable {
                C11781() throws  {
                }

                public void run() throws  {
                    if (MainActivity.this.getResources().getConfiguration().orientation == 1) {
                        AnimationUtils.SearchBarBackAnimation(MainActivity.this.findViewById(C1283R.id.SearchBarLayout));
                    } else {
                        AnimationUtils.SearchBarBackAnimation(MainActivity.this.findViewById(C1283R.id.SearchBarLayout_ls));
                    }
                    MainActivity.this.mLayoutMgr.getMainLayout().requestLayout();
                }
            }

            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation animation) throws  {
                MainActivity.this.startNavigateActivityWithFade();
                v.postDelayed(new C11781(), 200);
            }
        }, $z0);
    }

    public void startNavigateActivityWithFade() throws  {
        ((SwipeableLayout) findViewById(C1283R.id.mainContentWrapper)).openSwipe(true);
    }

    public void startNavigateActivity() throws  {
        this.mLayoutMgr.openMainMenu();
    }

    public void startRoutesActivity() throws  {
        startActivityForResult(new Intent(this, RoutesActivity.class), 1000);
    }

    public void startProfileActivity() throws  {
        if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
            startActivityForResult(new Intent(this, TempUserProfileActivity.class), MYWAZE_CODE);
        } else {
            startActivityForResult(new Intent(this, MyProfileActivity.class), MYWAZE_CODE);
        }
    }

    public void startRegisterActivity() throws  {
        Intent $r1 = new Intent(this, PhoneRegisterActivity.class);
        $r1.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
        $r1.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
        startActivityForResult($r1, 1);
    }

    public void openConflictingActivity() throws  {
        startActivity(new Intent(this, InstallPickAccountActivity.class));
    }

    public void openMeetYourWazer() throws  {
        if (this.mLayoutMgr.isSplashVisible()) {
            this.mLayoutMgr.cancelSplash();
        }
        if (this.mMeetYourWazerPopup == null) {
            this.mMeetYourWazerPopup = new MeetYourWazerPopup(this);
        }
        if (!this.mMeetYourWazerPopup.isShowing()) {
            DisableOrientation();
            this.mMeetYourWazerPopup.show();
        }
    }

    public void openPasswordRecovery() throws  {
        if (this.mPasswordRecoveryPopup == null) {
            this.mPasswordRecoveryPopup = new PasswordRecoveryPopup(this);
        }
        DisableOrientation();
        this.mPasswordRecoveryPopup.show();
    }

    public void openAddFriendPopup() throws  {
        if (NativeManager.getInstance().GetShowAddFriendsNTV()) {
            if (this.mAddFriendsPopup == null) {
                this.mAddFriendsPopup = new AddFriendsPopup(this);
            }
            DisableOrientation();
            this.mAddFriendsPopup.show();
            return;
        }
        NativeManager.getInstance().signup_finished();
    }

    public long getCurrentSessionNumber() throws  {
        return this.mCurrentSessionNumber;
    }

    public void SendPickUp(String $r1) throws  {
        String $r4 = NativeManager.getInstance().getLanguageString(38);
        $r1 = (NativeManager.getInstance().getLanguageString(39) + "\n" + NativeManager.getInstance().getLanguageString(40) + "\n" + NativeManager.getInstance().getLanguageString(41) + "\n") + $r1;
        Intent $r2 = new Intent("android.intent.action.SEND");
        $r2.setType("text/plain");
        $r2.putExtra("android.intent.extra.SUBJECT", $r4);
        $r2.putExtra("exit_on_sent", true);
        $r2.putExtra("android.intent.extra.TEXT", $r1);
        startActivity(Intent.createChooser($r2, NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_PICK_UP_TITLE_SEND)));
    }

    public void SendMessageToNumber(String $r1, String sNumberToSend) throws  {
        sNumberToSend = NativeManager.getInstance().getLanguageString(38);
        $r1 = (NativeManager.getInstance().getLanguageString(39) + "\n" + NativeManager.getInstance().getLanguageString(40) + "\n" + NativeManager.getInstance().getLanguageString(41) + "\n") + $r1;
        Intent $r3 = new Intent("android.intent.action.SEND");
        $r3.setType("text/plain");
        $r3.putExtra("android.intent.extra.SUBJECT", sNumberToSend);
        $r3.putExtra("android.intent.extra.TEXT", $r1);
        startActivity(Intent.createChooser($r3, NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_PICK_UP_TITLE_SEND)));
    }

    public void addOrientationTracker(ITrackOrientation $r1) throws  {
        mOrientationTrackers.add($r1);
    }

    public void removeOrientationTracker(ITrackOrientation $r1) throws  {
        mOrientationTrackers.remove($r1);
    }

    public static boolean IsSDKBound() throws  {
        return mIsBound;
    }

    public boolean isSpotifyBound() throws  {
        if (!mIsBound) {
            return false;
        }
        if (mBoundIndex < 0) {
            return false;
        }
        TransportationSdkDetails $r2 = NativeManager.getInstance().configGetTransportationDetailsNTV();
        if ($r2 == null) {
            return false;
        }
        if (mBoundIndex < $r2.PackageNames.length) {
            return $r2.PackageNames[mBoundIndex].contains("spotify");
        } else {
            return false;
        }
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        try {
            super.onSaveInstanceState($r1);
        } catch (IllegalStateException $r2) {
            Logger.m38e("MainActivity: an exception occurred while exec onSaveInstanceState: " + $r2.getMessage());
        }
    }
}
