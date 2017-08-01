package com.waze;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.waze.AdsTracking.AdsData;
import com.waze.EditBox.EditBoxCallback;
import com.waze.LayoutManager.WazeRect;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.audioextension.spotify.SpotifyManager;
import com.waze.autocomplete.PlaceData;
import com.waze.beacons.BeaconManager;
import com.waze.carpool.CarpoolDriverProfileActivity;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolOfferDriveActivity;
import com.waze.carpool.CarpoolOnboardingManager;
import com.waze.carpool.CarpoolRiderJoinRequest;
import com.waze.carpool.CarpoolRidesActivity;
import com.waze.carpool.EditCarActivity;
import com.waze.carpool.MissingPermissionsActivity;
import com.waze.carpool.MissingPermissionsActivity.MPType;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.async.UpdateHandlers;
import com.waze.ifs.async.Waiter;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityLifetimeHandler;
import com.waze.ifs.ui.OmniSelectionFragment;
import com.waze.ifs.ui.ProgressBarDialog;
import com.waze.ifs.ui.ShutdownManager;
import com.waze.inbox.InboxNativeManager;
import com.waze.install.InstallNativeManager;
import com.waze.install.SmartLockManager;
import com.waze.location.Position;
import com.waze.map.MapView;
import com.waze.map.MapViewWrapper;
import com.waze.map.NativeCanvasRenderer;
import com.waze.messages.MessagesNativeManager;
import com.waze.messages.QuestionData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddFavoriteActivity;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.ParkingSearchResultsActivity;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.navigate.social.AddFriendsActivity;
import com.waze.navigate.social.GmsWazeIdsMatchData;
import com.waze.navigate.social.ShareDriveActivity;
import com.waze.navigate.social.ShareDrivingFriendsActivity;
import com.waze.navigate.social.ShareHelpActivity;
import com.waze.now.GoogleNowAuthenticator;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.AuthenticateCallbackActivity;
import com.waze.phone.LoginOptionsActivity;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.planned_drive.PlannedDriveListActivity;
import com.waze.profile.CarsActivity;
import com.waze.profile.MyProfileActivity;
import com.waze.profile.TempUserProfileActivity;
import com.waze.push.Alerter;
import com.waze.push.RegistrationIntentService;
import com.waze.reports.AddPlaceFlowActivity;
import com.waze.reports.VenueData;
import com.waze.rtalerts.RTAlertsAlertData;
import com.waze.rtalerts.RTAlertsCommentData;
import com.waze.rtalerts.RTAlertsMenu;
import com.waze.rtalerts.RTAlertsNativeManager;
import com.waze.rtalerts.RTAlertsThumbsUpData;
import com.waze.sensor.VoiceActivator;
import com.waze.settings.SettingsAdvancedActivity;
import com.waze.settings.SettingsAlertsOnRoute;
import com.waze.settings.SettingsCalendarActivity;
import com.waze.settings.SettingsCarpoolActivity;
import com.waze.settings.SettingsCarpoolPaymentsActivity;
import com.waze.settings.SettingsCarpoolSeatsActivity;
import com.waze.settings.SettingsCarpoolWorkActivity;
import com.waze.settings.SettingsCustomPrompts;
import com.waze.settings.SettingsEndOfDrive;
import com.waze.settings.SettingsGeneralActivity;
import com.waze.settings.SettingsLanguageActivity;
import com.waze.settings.SettingsMainActivity;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.SettingsNavigationActivity;
import com.waze.settings.SettingsNavigationGuidanceActivity;
import com.waze.settings.SettingsNotificationActivity;
import com.waze.settings.SettingsPowerSaving;
import com.waze.settings.SettingsSoundActivity;
import com.waze.settings.SettingsSpotifyActivity;
import com.waze.share.EncouragementDialog;
import com.waze.share.ShareNativeManager;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.UserData;
import com.waze.utils.Base64;
import com.waze.utils.EditTextUtils;
import com.waze.utils.ImageRepository;
import com.waze.utils.PixelMeasure;
import com.waze.utils.Stopwatch;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import com.waze.view.bottom.BottomNotification;
import com.waze.view.dialogs.ParkingPinsFeedbackDialog;
import com.waze.view.popups.SpotifyPopup;
import com.waze.view.text.TypingLockListener;
import com.waze.voice.AsrSpeechRecognizer;
import com.waze.voice.WazeSpeechRecognizer;
import com.waze.widget.rt.RealTimeManager;
import dalvik.annotation.Signature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public final class NativeManager implements Executor {
    public static final int ADD_IMAGE_TYPE_CAR = 3;
    public static final int ADD_IMAGE_TYPE_PROFILE = 2;
    public static final int ADD_IMAGE_TYPE_VENUE = 1;
    public static final int ALERT_TICKER_TYPE_BEEPBEEP = 0;
    public static final int ALERT_TICKER_TYPE_LAST = 5;
    public static final int ALERT_TICKER_TYPE_MESSAGE = 1;
    public static final int ALERT_TICKER_TYPE_RIDEWITH = 4;
    public static final int ALERT_TICKER_TYPE_SHARE_DRIVE = 2;
    public static final int ALERT_TICKER_TYPE_SHARE_LOCATION = 3;
    private static final boolean ANALYTICS_DEBUG = false;
    public static final int CALENDAR_ACCESS_REQUEST_CODE = 7781;
    private static final long CAMERA_PREVIEW_TIMEOUT = 60000;
    private static final String CONFIG_VALUE_FTE_WITH_TEXT = "FTE_WITH_TEXT";
    private static final String CONFIG_VALUE_NO_FTE = "NO_FTE";
    private static final boolean CPU_PROFILER_ENABLED = false;
    private static final long GPS_DISABLED_WARNING_TIMEOUT = 3000;
    private static volatile boolean IsSyncValid = false;
    private static final boolean MEMORY_PROFILER_ENABLED = false;
    private static final long MEMORY_PROFILER_PERIOD = 5000;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 4561;
    public static final int NATIVE_MANAGER_NO = 4;
    public static final int NATIVE_MANAGER_YES = 3;
    private static final int NATIVE_THREAD_PRIORITY = -4;
    static final String PARKING_ICON_NAME = "category_group_parking.png";
    static final String PARKING_TITLE = "parking";
    private static final String PREFS_DB = "WAZE MAIN PREFS";
    private static final String PREFS_KEY_UNIQUE_ID = "Unique id";
    public static final String REFERRER_URL_PARAM_PREFIX = "ref=";
    private static final char[] SEKRIT = new String("WazeAndroid").toCharArray();
    private static final long STORAGE_SPACE_LOW_BOUND = 5000000;
    private static final boolean TEMPERATURE_PROFILER_ENABLED = false;
    private static final long TEMPERATURE_PROFILER_PERIOD = 120000;
    public static final int TOD_AFTERNOON = 1;
    public static final int TOD_EVENING = 2;
    public static final int TOD_MORNING = 0;
    public static final int TOD_NIGHT = 3;
    public static final int TOOLTIP_ARRIVAL_SENT = 4;
    public static final int TOOLTIP_CARPOOL_PROMO_STRIP = 10;
    public static final int TOOLTIP_CARPOOL_PROMO_TIP = 11;
    public static final int TOOLTIP_ETA = 7;
    public static final int TOOLTIP_ETA_UPDATE_SENT = 3;
    public static final int TOOLTIP_FRIENDS = 6;
    public static final int TOOLTIP_LAST = 12;
    public static final int TOOLTIP_MAIN_MENU = 0;
    public static final int TOOLTIP_NEW_VIEWER = 2;
    public static final int TOOLTIP_RIDEWITH_REQUESTS = 8;
    public static final int TOOLTIP_ROAD_CLOSURE = 5;
    public static final int TOOLTIP_SHARE = 1;
    public static final int TOOLTIP_UPCOMING_CARPOOL = 9;
    public static int UH_GAS_PRICE_UPDATED = TicketRoller.get(Type.Handler);
    public static int UH_LOGIN_DONE = TicketRoller.get(Type.Handler);
    public static int UH_NAVIGATION_STATE_CHANGED = TicketRoller.get(Type.Handler);
    public static int UH_SEARCH_VENUES = TicketRoller.get(Type.Handler);
    public static int UH_VENUE_ADD_IMAGE_RESULT = TicketRoller.get(Type.Handler);
    public static int UH_VENUE_STATUS = TicketRoller.get(Type.Handler);
    public static final String WAZE_URL_PATTERN = "waze://";
    private static final boolean WAZE_WIDGET_ENABLED = false;
    static boolean bIsCheck = true;
    public static boolean bToCreateAcc = false;
    public static boolean bToUploadContacts = false;
    private static volatile boolean mAppShutDownFlag = false;
    private static volatile boolean mAppStarted = false;
    private static ArrayList<RunnableExecutor> mAppStartedEvents = new ArrayList();
    private static volatile boolean mCanvasConditions = false;
    private static Vector<Runnable> mInitialLooperQueue = new Vector();
    private static NativeManager mInstance = null;
    public static String mInviteId = null;
    private static NativeCanvasRenderer mNativeCanvasRenderer = null;
    public static long mNativeStartTime = 0;
    private static NativeThread mNativeThread;
    private static volatile boolean mOglDataAvailable = false;
    private static final Stopwatch startSW = WazeApplication.startSW;
    private String ApiKey = null;
    private boolean bIsShutDown = false;
    public boolean bToForceLoginWithSocial = false;
    private UpdateHandlers handlers;
    private boolean isAllowTripDialog = true;
    private boolean isGasUpdateable = false;
    private KeyguardManager keyguardManager;
    public AdsData mAdsData = null;
    private boolean mAppCanvasBufReady = true;
    private boolean mAppInitializedFlag = false;
    private boolean mAppLooperReady = false;
    private int mAppMediaVolume = -1;
    private boolean mAppStartPrepared = false;
    private AddressItem mCalendarAI = null;
    private boolean mCalendarEventsDirty = true;
    private CenteredOnMeListener mCenteredOnMeListener;
    private boolean mFbAppNotInstallForce = false;
    private int mFeatures = -1;
    private boolean mIsCenteredOnMe = true;
    private boolean mIsMenuEnabled = true;
    private long mLastTemperatureSampleTime;
    private ILocationSensorListener mLocationListner;
    private NavBarManager mNavBarManager = null;
    private NotificationManager mNotificationManager;
    private ArrayList<IOnUserNameResult> mOnUserNameResultArray = new ArrayList(4);
    private final ArrayList<Runnable> mPriorityEventQueue = new ArrayList();
    private ArrayList<Message> mPriorityNativeEventQueue = new ArrayList();
    private String[] mProblematicGPUNames = null;
    private ProgressBarDialog mProgressBarCommon = null;
    private boolean mProgressBarLocked = false;
    private ResManager mResManager;
    private ResPrepareThread mResPrepareThread = null;
    private ArrayList<AddressItem> mSearchResults = null;
    private AddressItem mSharedAI = null;
    private boolean mShowRoutesWhenNavigationStarts;
    private SpeechttManagerBase mSpeechttManager;
    private final Runnable mStartAppEvent = new Runnable() {
        public void run() throws  {
            NativeManager.this.mTrafficStats = new SessionTrafficStats(AppService.getAppContext());
            NativeManager.startSW.startDelta("START", true);
            NativeManager.this.InitNativeManager();
            Stopwatch $r4 = Stopwatch.create("AppStartNTV");
            $r4.start();
            NativeManager.this.AppStartNTV(AppService.getUrl());
            IntentManager.HandleIntent(NativeManager.this.getMainActivity(), true);
            $r4.startDelta("AppStart TIME", false);
            NativeManager.this.postOnAppStartedEvents();
            NativeManager.startSW.startDelta("START ENDED", true);
            NativeManager.mAppStarted = true;
            int $i0 = QuestionData.GetGeoFencingWakeUpFlag();
            if ($i0 != -1) {
                if ($i0 == 0) {
                    ConfigManager.getInstance().setConfigValueBool(448, true);
                } else {
                    ConfigManager.getInstance().setConfigValueBool(449, true);
                }
                QuestionData.SetGeoFencingWakeUpFlag(AppService.getAppContext(), -1);
            }
            ApplicationInfo $r8 = AppService.getAppContext().getApplicationInfo();
            $i0 = $r8.flags & 2;
            $r8.flags = $i0;
            if ($i0 != 0) {
                NativeManager.this.runTests();
            }
        }
    };
    private int mSysValScreenTimeout;
    private int mSysValVolume;
    private SensorEventListener mTemperatureEventListener = null;
    private Timer mTimer;
    private NativeTimerManager mTimerManager;
    protected SessionTrafficStats mTrafficStats;
    private TtsManager mTtsManager;
    private UIMsgDispatcher mUIMsgDispatcher;
    private final Waiter mUrlHandlerWaiter = new Waiter();
    private NetworkInfo m_NetworkInfo = null;
    Handler search_handler = null;
    protected boolean shouldDisplayGasSettings = false;
    private Runnable shutDownEvent = new Runnable() {
        public void run() throws  {
            NativeManager.this.AppShutDownNTV();
        }
    };

    public interface CenteredOnMeListener {
        void onCenteredOnMeChanged(boolean z) throws ;
    }

    public interface IOnUserNameResult {
        void onUserNameResult(int i, String str) throws ;
    }

    public interface GasSettingsListener {
        void onComplete(boolean z) throws ;
    }

    class C12191 implements Runnable {
        C12191() throws  {
        }

        public void run() throws  {
            Logger.m36d("Orientation changed; Notifying native by calling notifyOrientationChangedNTV");
            NativeManager.this.notifyOrientationChangedNTV();
        }
    }

    class C12283 implements Runnable {
        C12283() throws  {
        }

        public void run() throws  {
            NativeManager.this.GetInviteRequestNTV(NativeManager.mInviteId);
        }
    }

    class C12327 implements Runnable {
        C12327() throws  {
        }

        public void run() throws  {
            LocationFactory.getInstance().UnregisterCompass();
        }
    }

    class C12338 implements Runnable {
        C12338() throws  {
        }

        public void run() throws  {
            LocationFactory.getInstance().RegisterCompass();
        }
    }

    class C12349 implements Runnable {
        C12349() throws  {
        }

        public void run() throws  {
            NativeManager.this.asrActivatedNTV();
        }
    }

    public static class AddressStrings {
        public String[] address;
        public String[] city;
        public int numResults;
        public int numToFilterTo;
        public String[] street;
    }

    public static class AdsActiveContext {
        public String event_info;
        public int pin_id;
        public int promo_id;
    }

    public interface AllIdsFromDBListener {
        void onComplete(GmsWazeIdsMatchData gmsWazeIdsMatchData) throws ;
    }

    private static final class CompatabilityWrapper {
        private CompatabilityWrapper() throws  {
        }

        public static String getManufacturer() throws  {
            return Build.MANUFACTURER;
        }
    }

    public interface DisplayNameListener {
        void onComplete(String str) throws ;
    }

    public static class DistanceAndUnits {
        public float distance;
        public String units;
    }

    public interface GetTitleListener {
        void onGetTitle(String str) throws ;
    }

    public interface IRefreshFriendsDrivingData {
        void onRefresh() throws ;
    }

    public interface IResultCode {
        void onResult(int i) throws ;
    }

    public interface IResultOk {
        void onResult(boolean z) throws ;
    }

    private final class NativeThread extends HandlerThread {
        public NativeThread(String $r2) throws  {
            super($r2);
        }

        protected void onLooperPrepared() throws  {
            Log.w(Logger.TAG, "Native thread is running");
            NativeManager.this.mUIMsgDispatcher = new UIMsgDispatcher();
            NativeManager.this.notifyCreate();
            NativeManager.mInstance.prepareAppStart();
            NativeManager.mInstance.mStartAppEvent.run();
        }
    }

    public static abstract class OnUrlHandleResult extends RunnableExecutor {
        public boolean result = false;
    }

    public static class PeopleAppData {
        public int friendship_suggest_count;
    }

    private static final class ResPrepareThread extends Thread {
        private ResPrepareThread() throws  {
        }

        public void run() throws  {
            Log.w(Logger.TAG, "Resources prepare thread start");
            ResManager.Prepare();
            Log.w(Logger.TAG, "Resources prepare thread finish");
        }
    }

    public static class SpeedLimit {
        public int roadType;
        public int[] speedLimits;
    }

    public static class SpeedLimits {
        public static final int DEFAULT_ROAD_TYPE = -1;
        public SpeedLimit[] speedLimits;
    }

    public interface StringResultListener {
        void onResult(String str) throws ;
    }

    public static class TransportationSdkDetails implements Serializable {
        private static final long serialVersionUID = 1;
        String[] AppNames;
        String[] PackageNames;
        int[] Scopes;
    }

    public enum UIEvent {
        UI_EVENT_START,
        UI_EVENT_FORCE_NEW_CANVAS,
        UI_EVENT_TOUCH,
        UI_EVENT_STARTUP_NOSDCARD,
        UI_EVENT_STARTUP_GPUERROR,
        UI_EVENT_LOW_MEMORY,
        UI_EVENT_SCREENSHOT,
        UI_EVENT_NATIVE,
        UI_EVENT_EMPTY,
        UI_EVENT_GENERIC_RUNNABLE,
        UI_PRIORITY_EVENT_NATIVE;

        public static UIEvent FromInt(int $i0) throws  {
            return values()[$i0];
        }

        public static int ToInt(UIEvent $r0) throws  {
            return $r0.ordinal();
        }
    }

    public static class UIMsgDispatcher extends Handler {
        public void handleMessage(Message $r1) throws  {
            if (!NativeManager.isShuttingDown()) {
                NativeManager $r3 = NativeManager.getInstance();
                if ($r3.getInitializedStatus()) {
                    handlePriorityEvents();
                }
                UIEvent $r4 = UIEvent.FromInt($r1.what);
                long $l0 = System.currentTimeMillis();
                String $r5 = "";
                switch ($r4) {
                    case UI_EVENT_EMPTY:
                    case UI_EVENT_START:
                    case UI_EVENT_SCREENSHOT:
                    case UI_EVENT_FORCE_NEW_CANVAS:
                    case UI_EVENT_TOUCH:
                    case UI_PRIORITY_EVENT_NATIVE:
                        break;
                    case UI_EVENT_GENERIC_RUNNABLE:
                        Runnable $r9 = (Runnable) $r1.obj;
                        $r5 = $r9.toString();
                        if ($r9 != null) {
                            $r9.run();
                            break;
                        }
                        break;
                    case UI_EVENT_STARTUP_GPUERROR:
                        $r3.appLayerShutDown();
                        break;
                    case UI_EVENT_STARTUP_NOSDCARD:
                        NativeManager.startApp();
                        break;
                    case UI_EVENT_LOW_MEMORY:
                        Logger.m43w(new String("Android system reported low memory !!! ") + new String("Memory usage native heap. Used: " + Debug.getNativeHeapAllocatedSize() + ". Free: " + Debug.getNativeHeapFreeSize() + ". Total: " + Debug.getNativeHeapSize()));
                        break;
                    case UI_EVENT_NATIVE:
                        boolean $z0 = true;
                        IMessageParam $r11 = (IMessageParam) $r1.obj;
                        if ($r11 != null) {
                            $z0 = $r11.IsActive();
                            $r5 = "Timer Event";
                        } else {
                            $r5 = "IO event";
                        }
                        if ($z0) {
                            int $i1 = $r1.arg1;
                            int $i4 = $r1.arg2;
                            $r3.NativeMsgDispatcherNTV($i1, $i4);
                            break;
                        }
                        break;
                    default:
                        break;
                }
                $l0 = System.currentTimeMillis() - $l0;
                if ($l0 > 500 && $r3.getInitializedStatus()) {
                    Logger.m43w("WAZE PROFILER EXCEPTIONAL TIME FOR " + $r5 + " HANDLING TIME: " + $l0);
                }
            }
        }

        private void handlePriorityEvents() throws  {
            NativeManager $r1 = NativeManager.getInstance();
            while (true) {
                Runnable $r2 = null;
                synchronized ($r1.mPriorityEventQueue) {
                    if (!$r1.mPriorityEventQueue.isEmpty()) {
                        $r2 = (Runnable) $r1.mPriorityEventQueue.remove(0);
                    }
                }
                if ($r2 != null) {
                    $r2.run();
                } else {
                    Message $r7 = null;
                    synchronized ($r1.mPriorityNativeEventQueue) {
                        if (!$r1.mPriorityNativeEventQueue.isEmpty()) {
                            $r7 = (Message) $r1.mPriorityNativeEventQueue.remove(0);
                        }
                    }
                    if ($r7 != null) {
                        UIEvent $r8 = UIEvent.FromInt($r7.what);
                        long $l0 = System.currentTimeMillis();
                        String $r9 = "";
                        switch (AnonymousClass298.$SwitchMap$com$waze$NativeManager$UIEvent[$r8.ordinal()]) {
                            case 10:
                                boolean $z0 = true;
                                IMessageParam $r14 = (IMessageParam) $r7.obj;
                                if ($r14 != null) {
                                    $z0 = $r14.IsActive();
                                    $r9 = "Timer Event";
                                } else {
                                    $r9 = "IO event";
                                }
                                if ($z0) {
                                    int $i1 = $r7.arg1;
                                    int $i4 = $r7.arg2;
                                    $r1.NativeMsgDispatcherNTV($i1, $i4);
                                    break;
                                }
                                break;
                            default:
                                Logger.m38e("Unknown priority event - " + $r8);
                                break;
                        }
                        $l0 = System.currentTimeMillis() - $l0;
                        if ($l0 > 500) {
                            Logger.m43w("WAZE PROFILER EXCEPTIONAL TIME FOR " + $r9 + " HANDLING TIME: " + $l0);
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private class UrlResultRequest implements Runnable {
        protected boolean handled = false;
        private boolean shouldDoGlobalDecode = false;
        protected String url;

        public UrlResultRequest(String $r2, boolean $z0) throws  {
            this.url = $r2;
            this.shouldDoGlobalDecode = $z0;
        }

        public void run() throws  {
            this.handled = NativeManager.this.UrlHandlerNTV(this.url, this.shouldDoGlobalDecode);
            NativeManager.this.mUrlHandlerWaiter._notify();
        }
    }

    public static class VenueCategory {
        public String icon;
        public String id;
        public String label;
        public String parent;
    }

    public static class VenueCategoryGroup {
        public String icon;
        public String id;
        public String label;
    }

    public static class VenueFieldPoints {
        public int categories;
        public int city;
        public int description;
        public int hours;
        public int house_number;
        public int images;
        public int location;
        public int name;
        public int phone;
        public int services;
        public int street;
        public int url;
    }

    public static class VenueFieldValidators {
        public String city;
        public String description;
        public String house_number;
        public String name;
        public String phone;
        public String street;
        public String url;
    }

    public static class VenueServices {
        public int count;
        public String[] icons;
        public String[] ids;
        public String[] names;
    }

    public interface intResultLListener {
        void onResult(int i) throws ;
    }

    private native boolean AccessToCalendarAllowedNTV() throws ;

    private native void AddContactToDBNTV(String str, long j, long j2) throws ;

    private native void AddGmsContactToDBNTV(String str, long j, String str2, long j2, long j3) throws ;

    private native void AddPopupNTV(int[] iArr, int[] iArr2) throws ;

    private native void AddToMeetingNTV(int[] iArr, int i, Object[] objArr, boolean z) throws ;

    private native void AppShutDownNTV() throws ;

    private native void AppStartNTV(String str) throws ;

    private native void AuthContactsNTV() throws ;

    private native void AuthPhoneNumberNTV(String str, String str2, int i, String str3, String str4) throws ;

    private native void AuthPinNTV(String str) throws ;

    private native void AutoCompleteAdsClickedNTV(String str, String str2, int i) throws ;

    private native void AutoCompleteAdsShownNTV(String str, String str2, int i) throws ;

    private native void BackLightMonitorResetNTV() throws ;

    private native void BeepClosedNTV(int i) throws ;

    private native boolean CalendarFeatureEnabledNTV() throws ;

    private native void CallbackNTV(int i, long j, long j2) throws ;

    private native void ClearClosureObjectNTV() throws ;

    private native void CloseAllPopupsNTV(int i) throws ;

    private native void CloseDarkViewNTV() throws ;

    private native void ClosedProperlyNTV(int i, String str) throws ;

    private native void ConnectivityChangedNTV(boolean z, int i, String str) throws ;

    private native void ContactUploadNTV() throws ;

    private native void CreateMeetingBulkNTV(String str, String str2, int i, int i2, int[] iArr, Object[] objArr, int[] iArr2, int i3, int i4, boolean z, Object[] objArr2, String str3, String str4, String str5, boolean z2, String str6) throws ;

    private native void CreateMeetingNTV(String str, String str2, int i, int i2, String str3, String str4, String str5, String str6) throws ;

    private native void DeleteAccountNTV() throws ;

    private native void DeleteContactsFromDataBaseNTV(long j) throws ;

    private native void DisconnectContactsNTV() throws ;

    private native void EditBoxCallbackNTV(int i, String str, long j) throws ;

    private native boolean EditBoxCheckTypingLockNTV() throws ;

    private native String GetAPIKeyNTV() throws ;

    private native GmsWazeIdsMatchData GetAllContactIdsFromDBNTV() throws ;

    private native int GetAutoCompleteFeaturesNTV() throws ;

    private native boolean GetAutoZoomNTV() throws ;

    private native int GetContactVersionFromDBNTV(long j) throws ;

    private native String GetContactsLastAccessTimeNTV() throws ;

    private native void GetInviteRequestNTV(String str) throws ;

    private native boolean GetIsDriveOnLeftSideNTV() throws ;

    private native boolean GetIsPushEnableNTV() throws ;

    private native String GetMapEditorURlNTV() throws ;

    private native String GetNavLinkNTV(int i, int i2) throws ;

    private native boolean GetNorthUpNTV() throws ;

    private native String GetRegionNTV() throws ;

    private native String GetTitleNTV(String str) throws ;

    private native String GetTripUnitsNTV() throws ;

    private native String GetWazeAutoCompleteAdsURLNTV() throws ;

    private native String GetWazeAutoCompleteURLNTV(String str) throws ;

    private native void InitNativeManagerNTV(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) throws ;

    private native void InviteToMeetingNTV(Object[] objArr, int[] iArr, int i, int i2) throws ;

    private native boolean IsContactExistInDBNTV(long j) throws ;

    private native boolean IsPickUpLaterNTV() throws ;

    private native boolean IsUpgradeNTV() throws ;

    private native void LogOutNTV() throws ;

    private native void NativeMsgDispatcherNTV(int i, int i2) throws ;

    private native void OpenAutoCompletePlaceNTV(String str, String str2, String str3, String str4, String str5, boolean z, String str6, boolean z2, int i, String str7, String str8) throws ;

    private native void OpenNavigateTipNTV() throws ;

    private native void OpenPopUpByIndexNTV(int i, int i2) throws ;

    private native void OpenRoutingSocketNTV() throws ;

    private native void OpenSearchSocketNTV() throws ;

    private native void RealtimeLoginNTV() throws ;

    private native void RealtimeLogoutNTV() throws ;

    private native void RemoveAllContactFromDBNTV() throws ;

    private native void RemoveContactFromDBNTV(long j) throws ;

    private native void SendAsrAudioBufferNTV(byte[] bArr, int i) throws ;

    private native void SendGoogleNowTokenNTV(String str) throws ;

    private native void SendPickUpRequestNTV() throws ;

    private native void SendShareMyRideNTV() throws ;

    private native void SetAllowSendMailNTV(boolean z) throws ;

    private native void SetAutoZoomNTV(int i) throws ;

    private native void SetContactsAccessNTV(boolean z) throws ;

    private native void SetContactsLastAccessTimeNTV(String str) throws ;

    private native void SetLast2DigitLicensePlateNTV(String str) throws ;

    private native void SetNeverShowGasPopAgainNTV(boolean z) throws ;

    private native void SetNorthUpNTV(int i) throws ;

    private native void SetPhoneIsFirstTimeNTV(boolean z) throws ;

    private native void SetPickUpLaterNTV(boolean z) throws ;

    private native void SetPushNotificationNTV(String str, boolean z) throws ;

    private native void SetSocialIsFirstTimeNTV(boolean z) throws ;

    private native void SetUpgradeRunNTV(byte b) throws ;

    private native void SetVoiceActionStrNTV(Object[] objArr) throws ;

    private native void SetparkedNTV(int i, int i2, int i3, String str, boolean z) throws ;

    private native void SignUplogAnalyticsStrNTV(String str, String str2, String str3, boolean z) throws ;

    private native void StartClosureObjectNTV(boolean z, int i, boolean z2) throws ;

    private native void StopFollowNTV() throws ;

    private native void SuggestUserNameInitNTV() throws ;

    private native void SuggestUserNameTerminateNTV(String str) throws ;

    private native void SuggsetUserNameRequestNTV(String str, String str2, String str3) throws ;

    private native void TakePictureCallbackNTV(int i) throws ;

    private native void UpdateContactsTimeInDBNTV(int[] iArr, long j) throws ;

    private native void UpdateSharePermissionsNTV(boolean z) throws ;

    private native void UploadProfileImageNTV(String str) throws ;

    private native boolean UrlHandlerNTV(String str, boolean z) throws ;

    private native void addPlaceToRecentNTV(String str, String str2, String str3, String str4, String str5, String str6) throws ;

    private native boolean asrCancelNTV(String str) throws ;

    private native void asrListenCallbackNTV(long j, Object[] objArr, float[] fArr) throws ;

    private native void callCallbackIntNTV(long j, int i) throws ;

    private native void editTextDialogCallbackNTV(String str, long j, long j2) throws ;

    private native void focusCanvasNTV(int i) throws ;

    private native void focusCanvasUserNTV(int i) throws ;

    private native String get2LastDigitLicensePlateNTV() throws ;

    private native String getCalendarLearnMoreUrlNTV() throws ;

    private native String getCoreVersionAndServerNTV() throws ;

    private native String getCoreVersionNTV() throws ;

    private native String getDisplayNameNTV() throws ;

    private native int getEditPlaceLocationRadiusNTV() throws ;

    private native String getPoiAddressNTV() throws ;

    private native int getPoiRoadTypeNTV() throws ;

    private native int getVenueGetTimeoutNTV() throws ;

    private native boolean isDebugNTV() throws ;

    private native boolean isGasPopUpFeatureEnabledNTV() throws ;

    private native boolean isGasPopUpShownNTV() throws ;

    private native boolean isGasUpdateableNTV() throws ;

    private native String langGetIntNTV(int i) throws ;

    private native String langGetNTV(String str) throws ;

    private native boolean langRtlNTV() throws ;

    private native void logAnalyticsFlushNTV() throws ;

    private native void logAnalyticsIntNTV(String str, String str2, int i) throws ;

    private native void logAnalyticsNTV(String str, boolean z, boolean z2) throws ;

    private native void logAnalyticsStrNTV(String str, String str2, String str3) throws ;

    private native DistanceAndUnits mathDistanceAndUnitsNTV(int i, int i2, int i3, int i4, boolean z, int i5) throws ;

    private native int mathDistanceNTV(int i, int i2, int i3, int i4) throws ;

    private native void notifyOrientationChangedNTV() throws ;

    private native void onAppActiveNTV() throws ;

    private native void onAppBackgroundNTV() throws ;

    private native void onAppForegroundNTV() throws ;

    private native void requestCalendarAccessCallbackNTV(long j, long j2, boolean z) throws ;

    private native void restorePoiFocusNTV() throws ;

    private native void savePoiPositionNTV(boolean z) throws ;

    private native void sendAlertRequestNTV(String str, String str2, String str3, String str4, String str5, int i, int i2, int i3, int i4) throws ;

    private native void setFriendsListBannerDismissedNTV() throws ;

    private native boolean shouldDisplayGasSettingsNTV() throws ;

    private native void test_alertTickerTypes() throws ;

    private native void test_timeOfDayType() throws ;

    private native void test_tooltipTypes() throws ;

    private native void test_venueFlagRequestType(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) throws ;

    private native void venueAddImageNTV(String str, int i) throws ;

    private native void venueCreateNTV(VenueData venueData, String str, String str2, boolean z) throws ;

    private native void venueDeleteImageNTV(String str, String str2) throws ;

    private native void venueFlagImageNTV(String str, String str2, int i) throws ;

    private native void venueFlagNTV(String str, int i, String str2, String str3) throws ;

    private native void venueGetNTV(String str, int i, boolean z) throws ;

    private native void venueLikeImageNTV(String str, String str2) throws ;

    private native void venueSaveNavNTV(VenueData venueData) throws ;

    private native void venueSearchNTV(int i, int i2) throws ;

    private native void venueUnlikeImageNTV(String str, String str2) throws ;

    private native void venueUpdateNTV(VenueData venueData, VenueData venueData2, String str, String str2) throws ;

    private native boolean wasFriendsListBannerDismissedNTV() throws ;

    public native void AlertPopUpClosedNTV() throws ;

    public native void AlerterClosedNTV() throws ;

    public native void AlerterNotThereNTV() throws ;

    public native void AlerterThumbsUpNTV() throws ;

    public native boolean AutocompleteIsMatchNTV(String str, String str2) throws ;

    public native void CalendaDenyAccessNTV() throws ;

    public native void CalendaRequestAccessNTV() throws ;

    public native boolean CalendarAuthUndeterminedNTV() throws ;

    public native void DownloadAlertVoiceNTV(int i) throws ;

    public native void EditorTrackToggleNewRoadsNTV() throws ;

    public native void FriendsOnlinePopUpClosedNTV() throws ;

    public native boolean GetAllowSendMailNTV() throws ;

    public native String GetSDKLearnMoreURLNTV() throws ;

    public native boolean GetShareDrivePermissionNTV() throws ;

    public native boolean GetShowAddFriendsNTV() throws ;

    public native boolean GetShowScreenIconsNTV() throws ;

    public native boolean GetSocialIsFirstTimeNTV() throws ;

    public native boolean HasClosureDataNTV() throws ;

    public native boolean IsAccessToContactsEnableNTV() throws ;

    public native boolean IsPublishFbPermissionsNTV() throws ;

    public native boolean IsPublishStreamFbPermissionsNTV() throws ;

    public native boolean IsTTsEnableNTV() throws ;

    public native boolean PickUpFeatureEnabledNTV() throws ;

    public native void PopupShownNTV(int i, int i2, int i3, int i4) throws ;

    public native boolean RandomUserMsgNTV() throws ;

    native boolean RealtimeDebugEnabledNTV() throws ;

    public native void RealtimeReportTrafficNTV(int i) throws ;

    public native void ReportAbusNTV(int i, int i2) throws ;

    public native boolean ReportAllowImagesNTV() throws ;

    public native boolean ReportAllowVoiceRecordingsNTV() throws ;

    public native void SetAppTypeNTV(String str) throws ;

    public native boolean ShareRideFeatureEnabledNTV() throws ;

    public native void TickerClosedNTV() throws ;

    public native boolean ValidateMobileTypeNTV() throws ;

    public native void asrActivatedNTV() throws ;

    public native boolean calendarAuthorizedNTV() throws ;

    public native SpeedLimits configGetSpeedLimitsNTV() throws ;

    public native TransportationSdkDetails configGetTransportationDetailsNTV() throws ;

    public native void disableCalendarNTV() throws ;

    public native void encouragementCallbackNTV(int i) throws ;

    public native void encouragementCloseCallbackNTV(long j) throws ;

    public native void externalPoiClosedNTV(boolean z) throws ;

    public native AddressStrings getAddressByLocationNTV(int i, int i2) throws ;

    public native int getAltRoutesCurrentRouteColorNTV() throws ;

    public native int getAltRoutesPinMinutesThresholdNTV() throws ;

    public native float getAltRoutesPinPercentThresholdNTV() throws ;

    public native String getCurMeetingNTV() throws ;

    public native VenueData getDestinationVenueDataNTV() throws ;

    public native String getDestinationVenueNTV() throws ;

    public native String getEncImagePathNTV(String str) throws ;

    public native String getExcludedCalendarsNTV() throws ;

    public native String[] getFbPermissionsNTV() throws ;

    public native VenueData getLastDestinationVenueDataNTV() throws ;

    public String getMD5() throws  {
        return null;
    }

    public native int getNumberOfFriendsToStopShowingFriendSuggestions() throws ;

    public native PeopleAppData getPeopleAppDataNTV(int i, boolean z) throws ;

    public native String[] getPublishFbPermissionsNTV() throws ;

    public native String getRTServerId() throws ;

    public native String getRelativeTimeStringNTV(long j, boolean z) throws ;

    public native int getReturnToWazeFromPhoneTimeoutNTV() throws ;

    public native String getServerCookie() throws ;

    public native int getServerSessionId() throws ;

    public native String getTimeOfDayGreetingNTV() throws ;

    public native int getTimeOfDayNTV() throws ;

    public native int getVenueMaxNameLengthNTV() throws ;

    public native boolean hasNetworkNTV() throws ;

    public native boolean inWalkingModeNTV() throws ;

    public native String isCategorySearchNTV(String str) throws ;

    public native boolean isClosureEnabledNTV() throws ;

    public native boolean isDrivingToMeetingNTV(String str) throws ;

    public native boolean isEditorIgnoreNewRoadsNTV() throws ;

    public native boolean isEnforcementAlertsEnabledNTV() throws ;

    public native int isEnforcementPoliceEnabledNTV() throws ;

    public native boolean isFollowActiveNTV() throws ;

    public native boolean isLicensePlateEnabledNTV() throws ;

    public native boolean isMeetingActiveNTV(String str) throws ;

    public native boolean isMovingNTV() throws ;

    public native boolean isNavigatingNTV() throws ;

    public native boolean isNearNTV() throws ;

    public native boolean isSkipConfirmWaypointNTV() throws ;

    public native boolean isStopPointNTV() throws ;

    public native boolean isTermsAccepted() throws ;

    public native void logAdsContextDisplayTimeNTV(String str) throws ;

    public native void logAnalyticsAdsContextClearNTV() throws ;

    public native void logAnalyticsAdsContextNTV(String str) throws ;

    public native void logAnalyticsAdsContextNavNTV(String str) throws ;

    public native void logAnalyticsAdsContextNavigationInitNTV() throws ;

    public native void logAnalyticsAdsContextSearchInitNTV(String str, int i, int i2, int i3, boolean z, String str2, String str3, String str4, String str5) throws ;

    public native AdsActiveContext logAnalyticsAdsGetActiveContextNTV() throws ;

    public native void logAnalyticsAdsSearchEventNTV(String str, String str2, int i, int i2, int i3, boolean z, String str3, String str4, String str5, String str6) throws ;

    public native int mathToSpeedUnitNTV(int i) throws ;

    public native void navigateMainGetCouponNTV() throws ;

    public native void navigateMainPlayStartNTV() throws ;

    public native void navigateRecalcAltRoutesNTV() throws ;

    public native void navigateToBonusPointNTV(int i, int i2, int i3) throws ;

    public native void navigateToExternalPoiNTV(int i, int i2, int i3, String str, String str2) throws ;

    public native void refreshMapNTV() throws ;

    public native void resetEventsNTV() throws ;

    public native void sendBeepBackNTV(int i) throws ;

    public native int sendBeepBeepNTV(int i, int i2, int i3, int i4) throws ;

    public native void sendCommentNTV(int i) throws ;

    public native void sendThumbsUpNTV(int i) throws ;

    public native void setExcludedCalendarsNTV(String str) throws ;

    public native void setGpsSourceNameNTV(String str) throws ;

    public native void setPeopleAppDataNTV(int i, boolean z, PeopleAppData peopleAppData) throws ;

    public native void showCommentsNTV(int i) throws ;

    public native void showGroupNTV(String str) throws ;

    public native String speedUnitNTV() throws ;

    public native void startTripServerNavigationNTV() throws ;

    public native void stopNavigationNTV() throws ;

    public native void stopTripServerNavigationNTV() throws ;

    public native void updateCalendarEventsNTV() throws ;

    public native VenueCategory[] venueProviderGetCategories() throws ;

    public native VenueCategoryGroup[] venueProviderGetCategoryGroups() throws ;

    public native VenueFieldPoints venueProviderGetFieldPoints() throws ;

    public native VenueFieldValidators venueProviderGetFieldValidators() throws ;

    public native VenueServices venueProviderGetServices() throws ;

    public native void wazeUiDetailsPopupClosedNTV() throws ;

    public native void wazeUiDetailsPopupGetPositionNTV(Integer num, Integer num2) throws ;

    public native void wazeUiDetailsPopupNextNTV() throws ;

    public native void wazeUiUserPopupClosedNTV() throws ;

    public void notifyOrientationChanged() throws  {
        Post(new C12191());
    }

    public void setUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.setUpdateHandler($i0, $r1);
    }

    public void unsetUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.unsetUpdateHandler($i0, $r1);
    }

    public static void postResultOk(final IResultOk $r0, final boolean $z0) throws  {
        if ($r0 != null) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    $r0.onResult($z0);
                }
            });
        }
    }

    public String getLanguageString(String $r1) throws  {
        return langGetNTV($r1);
    }

    public String getContactsLastAccessTime() throws  {
        if (!bToCreateAcc) {
            return "Temp";
        }
        bToCreateAcc = false;
        return null;
    }

    public String getInviteId() throws  {
        return mInviteId;
    }

    public String GetIsAggaragteFinished() throws  {
        return GetContactsLastAccessTimeNTV();
    }

    public void GetInviteData() throws  {
        if (mInviteId != null) {
            Post(new C12283());
        }
    }

    public void setContactsLastAccessTime(final String $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetContactsLastAccessTimeNTV($r1);
            }
        });
    }

    public void Set2LastDigitLicensePlate(final String $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_LICENSE_PLATE_CONFIGURED);
                NativeManager.this.SetLast2DigitLicensePlateNTV($r1);
            }
        });
    }

    public void setContactsAccess(final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetContactsAccessNTV($z0);
            }
        });
    }

    public String getApiKey() throws  {
        if (this.ApiKey == null) {
            this.ApiKey = GetAPIKeyNTV();
        }
        return this.ApiKey;
    }

    public int getAutoCompleteFeatures() throws  {
        if (this.mFeatures == -1) {
            this.mFeatures = GetAutoCompleteFeaturesNTV();
        }
        return this.mFeatures;
    }

    public String GetEditorUrl() throws  {
        return GetMapEditorURlNTV();
    }

    public void StopCompass() throws  {
        AppService.Post(new C12327());
    }

    public void StartCompass() throws  {
        if (IsAppStarted()) {
            AppService.Post(new C12338());
        } else {
            LocationSensorListener.bIsStartCompass = true;
        }
    }

    public boolean getIsDriveOnLeft() throws  {
        return GetIsDriveOnLeftSideNTV();
    }

    public String getLanguageString(int $i0) throws  {
        return langGetIntNTV($i0);
    }

    public String getFormattedString(int $i0, Object... $r1) throws  {
        return String.format(getLanguageString($i0), $r1);
    }

    public String getCoreVersion() throws  {
        return getCoreVersionNTV();
    }

    public String getCoreVersionAndServer() throws  {
        return getCoreVersionAndServerNTV();
    }

    public String[] getLanguageStrings(String[] $r1) throws  {
        int $i0 = $r1.length;
        String[] $r2 = new String[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r2[$i1] = langGetNTV($r1[$i1]);
        }
        return $r2;
    }

    public void asrActivated() throws  {
        if (isDisplayReadyForAsr()) {
            Post(new C12349());
        }
    }

    public void AddContactToDB(final String $r1, final long $l0, final long $l1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AddContactToDBNTV($r1, $l0, $l1);
            }
        });
    }

    public void AddGmsContactToDB(final String $r1, final long $l0, final String $r2, final long $l1, final long $l2) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AddGmsContactToDBNTV($r1, $l0, $r2, $l1, $l2);
            }
        });
    }

    public void UpdateContactsTimeInDB(final int[] $r1, final long $l0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.UpdateContactsTimeInDBNTV($r1, $l0);
            }
        });
    }

    public boolean IsContactExistInDB(long $l0) throws  {
        return IsContactExistInDBNTV($l0);
    }

    public String GetDefaultRegion() throws  {
        return GetRegionNTV();
    }

    public long GetContactVersionFromDB(long $l0) throws  {
        return (long) GetContactVersionFromDBNTV($l0);
    }

    public void RemoveContactFromDB(final long $l0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.RemoveContactFromDBNTV($l0);
            }
        });
    }

    public void RemoveAllContactsFromDB() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.RemoveAllContactFromDBNTV();
            }
        });
    }

    public void GetAllContactIdsFromDB(final AllIdsFromDBListener $r1) throws  {
        Post(new RunnableUICallback() {
            private GmsWazeIdsMatchData data;

            public void event() throws  {
                try {
                    this.data = NativeManager.this.GetAllContactIdsFromDBNTV();
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void SendPickUpRequest() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SendPickUpRequestNTV();
            }
        });
    }

    public void UpdateSharePermissions(final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.UpdateSharePermissionsNTV($z0);
            }
        });
    }

    public void DisconnectContacts() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.DisconnectContactsNTV();
            }
        });
    }

    public void UploadProfileImage(final String $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.UploadProfileImageNTV($r1);
            }
        });
    }

    public void ContactUpload() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.ContactUploadNTV();
            }
        });
    }

    public void AuthPhoneNumber(final String $r1, final String $r2, final int $i0, final String $r3) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AuthPhoneNumberNTV($r1, $r2, $i0, $r3, NativeManager.mInviteId);
            }
        });
    }

    public void AuthPin(final String $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AuthPinNTV($r1);
            }
        });
    }

    public void AuthContacts() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AuthContactsNTV();
            }
        });
    }

    public void AutoCompletePlaceClicked(String $r1, String $r2, String $r3, AddressItem $r4, String $r5, boolean $z0, String $r6, boolean $z1, int $i0, String $r7, String $r8) throws  {
        final AddressItem addressItem = $r4;
        final String str = $r2;
        final String str2 = $r1;
        final String str3 = $r3;
        final String str4 = $r5;
        final boolean z = $z0;
        final String str5 = $r6;
        final boolean z2 = $z1;
        final int i = $i0;
        final String str6 = $r7;
        final String str7 = $r8;
        Post(new Runnable() {
            public void run() throws  {
                String $r7 = null;
                if (addressItem != null) {
                    $r7 = addressItem.getMeetingId();
                    NativeManager.this.mCalendarAI = addressItem;
                    NativeManager.this.mCalendarAI.VanueID = str;
                } else {
                    NativeManager.this.mCalendarAI = null;
                }
                NativeManager.this.OpenAutoCompletePlaceNTV(str2, str, str3, $r7, str4, z, str5, z2, i, str6, str7);
            }
        });
    }

    public void OpenPopUpByIndex(final int $i0, final int $i1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.OpenPopUpByIndexNTV($i0, $i1);
            }
        });
    }

    public void Set_Parking(final int $i0, final int $i1, final int $i2, final String $r1, final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetparkedNTV($i0, $i1, $i2, $r1, $z0);
                QuestionData.SetParking(AppService.getAppContext(), ((double) $i0) / 1000000.0d, ((double) $i1) / 1000000.0d, $i2, $r1);
            }
        });
    }

    public void OpenTickersPopups(final int[] $r1, final int[] $r2) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AddPopupNTV($r1, $r2);
            }
        });
    }

    public void CloseAllPopups(final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.CloseAllPopupsNTV($i0);
            }
        });
    }

    public void PopupAction(int $i0, int $i1, int $i2) throws  {
        PopupAction($i0, $i1, $i2, 0);
    }

    public void PopupAction(final int $i0, final int $i1, final int $i2, final int $i3) throws  {
        if (IsNativeThread()) {
            PopupShownNTV($i0, $i1, $i2, $i3);
        } else {
            Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.PopupShownNTV($i0, $i1, $i2, $i3);
                }
            });
        }
    }

    public void AutoCompleteAdsShown(final String $r1, final String $r2, final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AutoCompleteAdsShownNTV($r1, $r2, $i0);
            }
        });
    }

    public void AutoCompleteAdsClicked(final String $r1, final String $r2, final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AutoCompleteAdsClickedNTV($r1, $r2, $i0);
            }
        });
    }

    public void RealtimeLogout() throws  {
        if (IsNativeThread()) {
            RealtimeLogoutNTV();
        } else {
            PostRunnable(new Runnable() {
                public void run() throws  {
                    NativeManager.this.RealtimeLogoutNTV();
                }
            });
        }
    }

    public void RealtimeLogin() throws  {
        if (IsNativeThread()) {
            RealtimeLoginNTV();
        } else {
            PostRunnable(new Runnable() {
                public void run() throws  {
                    NativeManager.this.RealtimeLoginNTV();
                }
            });
        }
    }

    public void SuggestUserNameInit() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SuggestUserNameInitNTV();
            }
        });
    }

    public void SuggestUserNameRequest(final String $r1, final String $r2, final String $r3) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SuggsetUserNameRequestNTV($r1, $r2, $r3);
            }
        });
    }

    public void InviteToMeeting(final Object[] $r1, final int[] $r2, final int $i0, final int $i1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.InviteToMeetingNTV($r1, $r2, $i0, $i1);
            }
        });
    }

    public void AddToMeeting(final int[] $r1, final int $i0, final Object[] $r2, final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.AddToMeetingNTV($r1, $i0, $r2, $z0);
            }
        });
    }

    public void CreateMeeting(final String $r1, final String $r2, final int $i0, final int $i1, final String $r3, final String $r4, final String $r5, final String $r6) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.CreateMeetingNTV($r1, $r2, $i0, $i1, $r3, $r4, $r5, $r6);
            }
        });
    }

    public void CreateMeetingBulk(final String $r1, final String $r2, final int $i0, final int $i1, final int[] $r3, final Object[] $r4, final int[] $r5, final int $i2, final int $i3, final boolean $z0, final Object[] $r6, final String $r7, final String $r8, final String $r9, final boolean $z1, final String $r10) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.CreateMeetingBulkNTV($r1, $r2, $i0, $i1, $r3, $r4, $r5, $i2, $i3, $z0, $r6, $r7, $r8, $r9, $z1, $r10);
            }
        });
    }

    public void SuggestUserNameTerminate(final String $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SuggestUserNameTerminateNTV($r1);
            }
        });
    }

    public void OpenSearchIntent() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.OpenSearchSocketNTV();
            }
        });
    }

    public void OpenRoutingIntent() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.OpenRoutingSocketNTV();
            }
        });
    }

    public void SetShowGasPricePopupAgain(final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetNeverShowGasPopAgainNTV($z0);
            }
        });
    }

    public void NativeManagerCallback(final int $i0, final long $l1, final long $l2) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.CallbackNTV($i0, $l1, $l2);
            }
        });
    }

    public void SendBeepBeep(int $i0, int $i1, int $i2, int $i3, IResultCode $r1) throws  {
        final int i = $i1;
        final int i2 = $i0;
        final int i3 = $i2;
        final int i4 = $i3;
        final IResultCode iResultCode = $r1;
        Post(new RunnableUICallback() {
            private int res;

            public void event() throws  {
                this.res = NativeManager.this.sendBeepBeepNTV(i, i2, i3, i4);
            }

            public void callback() throws  {
                if (iResultCode != null) {
                    iResultCode.onResult(this.res);
                }
            }
        });
    }

    public void onGLReady() throws  {
        notifyOglData();
    }

    public void SendShareMyRide() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SendShareMyRideNTV();
            }
        });
    }

    public void SendGoogleNowToken(final String $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SendGoogleNowTokenNTV($r1);
            }
        });
    }

    public void OpenNavigateTip() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.OpenNavigateTipNTV();
            }
        });
    }

    public void setMapIsDark(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.setMapIsDark($z0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot call setMapIsDark. Main activity is not available");
            }
        });
    }

    public void FriendsBarVisible(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.SetFriendsBarVisibilty($z0);
                        $r2.SetControlsVisibilty(!$z0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot call SetFriendsBarVisibility. Main activity is not available");
            }
        });
    }

    public void SaveImageToCache(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ImageRepository.instance.getImage($r1, true, null);
            }
        });
    }

    public void SearchBarVisible(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.SearchBarChangeStatus($z0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot Call SearchBarChangeStatus. Main activity is not available");
            }
        });
    }

    public void RefreshFriendsBar(final int $i0, final int $i1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.RefreshBar($i0, $i1);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open ping Popup. Main activity is not available");
            }
        });
    }

    public void SetWebViewDebug(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            @SuppressLint({"NewApi"})
            public void run() throws  {
                if (VERSION.SDK_INT >= 19) {
                    WebView.setWebContentsDebuggingEnabled($z0);
                }
            }
        });
    }

    public boolean getLanguageRtl() throws  {
        return langRtlNTV();
    }

    public boolean IsGasPopUpFeatureEnabled() throws  {
        return isGasPopUpFeatureEnabledNTV();
    }

    public boolean getGasPopupVisibilty() throws  {
        return isGasPopUpShownNTV();
    }

    public boolean isDebug() throws  {
        return isDebugNTV();
    }

    public void sendAlertRequest(final String $r1, final String $r2, final String $r3, final String $r4, final String $r5, final int $i0, final int $i1, final int $i2, final int $i3) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.sendAlertRequestNTV($r1, $r2, $r3, $r4, $r5, $i0, $i1, $i2, $i3);
            }
        });
    }

    public void encouragementCallback(final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.encouragementCallbackNTV($i0);
            }
        });
    }

    public void encouragementCloseCallback(final long $l0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.encouragementCloseCallbackNTV($l0);
            }
        });
    }

    public void savePoiPosition(final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.savePoiPositionNTV($z0);
            }
        });
    }

    public void getPoiAddress(final StringResultListener $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                $r1.onResult(NativeManager.this.getPoiAddressNTV());
            }
        });
    }

    public void getPoiRoadType(final intResultLListener $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                $r1.onResult(NativeManager.this.getPoiRoadTypeNTV());
            }
        });
    }

    public void SetSocialIsFirstTime(final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetSocialIsFirstTimeNTV($z0);
            }
        });
    }

    public void SetPhoneIsFirstTime(final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetPhoneIsFirstTimeNTV($z0);
            }
        });
    }

    public void restorePoiFocus() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.restorePoiFocusNTV();
            }
        });
    }

    public void ClearClosureObject() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.ClearClosureObjectNTV();
            }
        });
    }

    public void SetPickUpLater(final boolean $z0) throws  {
        AnonymousClass65 $r1 = new Runnable() {
            public void run() throws  {
                NativeManager.this.SetPickUpLaterNTV($z0);
            }
        };
        if (IsNativeThread()) {
            $r1.run();
        } else {
            Post($r1);
        }
    }

    public boolean IsPickUpLater() throws  {
        return IsPickUpLaterNTV();
    }

    public void StartClosureObject(final boolean $z0, final int $i0, final boolean $z1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.StartClosureObjectNTV($z0, $i0, $z1);
            }
        });
    }

    public void SetVoiceActionsStr(final Object[] $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetVoiceActionStrNTV($r1);
            }
        });
    }

    public Object[] getPhoneBookHash() throws  {
        return AddressBookImpl.getInstance().GetPhonesHash().toArray();
    }

    public static NativeManager Start() throws  {
        startSW.startDelta("NativeManager Start", false);
        if (mInstance != null) {
            return mInstance;
        }
        mNativeStartTime = System.nanoTime();
        mInstance = new NativeManager();
        NativeManager $r1 = mInstance;
        $r1.getClass();
        mNativeThread = new NativeThread("Native Thread");
        mNativeThread.start();
        mInstance.waitCreate();
        startSW.startDelta("NativeManager Start After wait", false);
        Log.d(Logger.TAG, "Before changing native thread priority, current priority is " + Process.getThreadPriority(mNativeThread.getThreadId()));
        Process.setThreadPriority(mNativeThread.getThreadId(), -4);
        mInstance.handlers = new UpdateHandlers();
        ChatNotificationManager.getInstance(true);
        return mInstance;
    }

    private synchronized void notifyCreate() throws  {
        this.mAppLooperReady = true;
        notifyAll();
    }

    private synchronized void waitCreate() throws  {
        while (!this.mAppLooperReady) {
            try {
                wait();
            } catch (Exception $r1) {
                Logger.ee(Logger.TAG, $r1);
            }
        }
    }

    private synchronized void notifyStart() throws  {
        mAppStarted = true;
        notifyAll();
    }

    private synchronized void notifyCanvasConditions() throws  {
        mCanvasConditions = true;
        notify();
    }

    private synchronized void notifyOglData() throws  {
        mOglDataAvailable = true;
        notify();
    }

    private synchronized void waitStart() throws  {
        while (!mAppStarted) {
            try {
                wait();
            } catch (Exception $r1) {
                Logger.ee(Logger.TAG, $r1);
            }
        }
    }

    private synchronized void notifyShutdown() throws  {
        mAppStarted = false;
        notifyAll();
    }

    private synchronized void waitShutdown() throws  {
        while (mAppStarted) {
            try {
                wait();
            } catch (Exception $r1) {
                Logger.ee(Logger.TAG, $r1);
            }
        }
    }

    public static NativeManager getInstance() throws  {
        return mInstance;
    }

    public Timer getTimer() throws  {
        return this.mTimer;
    }

    public boolean IsMenuEnabled() throws  {
        return this.mIsMenuEnabled;
    }

    public static boolean IsAppStarted() throws  {
        return mAppStarted;
    }

    public static boolean IsSyncValid() throws  {
        return IsSyncValid;
    }

    public static void unsetPushToken(boolean $z0) throws  {
        handlePushToken(false, $z0);
    }

    public static boolean setPushToken(boolean $z0) throws  {
        return handlePushToken(true, $z0);
    }

    private static boolean handlePushToken(final boolean $z0, final boolean $z1) throws  {
        if (IsAppStarted()) {
            AnonymousClass68 $r0 = new Runnable() {
                public void run() throws  {
                    NativeManager $r1 = NativeManager.getInstance();
                    String $r2 = null;
                    if ($z0) {
                        $r2 = RegistrationIntentService.getRegistrationId(AppService.getAppContext());
                    }
                    Logger.m41i("NativeManager: Pushing GCM token notification to native code");
                    $r1.SetPushNotificationNTV($r2, $z1);
                }
            };
            if (getInstance().IsNativeThread()) {
                $r0.run();
            } else {
                Post($r0);
            }
            return true;
        }
        Logger.m41i("NativeManager: Pushing GCM token: App not started yet");
        return false;
    }

    public void setGoogleNowToken() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                GoogleNowAuthenticator.refreshAuthCode();
            }
        });
    }

    public void CloseNavigationResult() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().getLayoutMgr().closeNavResults(false);
                }
            }
        });
    }

    public void SendAction(final String $r1, final String $r2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent();
                $r1.setAction("android.intent.action.SEND");
                $r1.putExtra("android.intent.extra.SUBJECT", $r1);
                $r1.putExtra("android.intent.extra.TEXT", $r2);
                $r1.setType("text/plain");
                AppService.getActiveActivity().startActivity($r1);
            }
        });
    }

    public synchronized boolean isAppStartPrepared() throws  {
        return this.mAppStartPrepared;
    }

    public boolean IsNativeThread() throws  {
        return Thread.currentThread().getId() == mNativeThread.getId();
    }

    public void SetIsMenuEnabled(int $i0) throws  {
        boolean $z0;
        if ($i0 != 0) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        this.mIsMenuEnabled = $z0;
    }

    public static boolean isShuttingDown() throws  {
        return mAppShutDownFlag;
    }

    public boolean getInitializedStatus() throws  {
        return this.mAppInitializedFlag;
    }

    public boolean getCanvasBufReady() throws  {
        return this.mAppCanvasBufReady;
    }

    public void setCanvasBufReady(boolean $z0) throws  {
        this.mAppCanvasBufReady = $z0;
    }

    public void DeleteContactsFromDataBase(final long $l0) throws  {
        PostRunnable(new Runnable() {
            public void run() throws  {
                NativeManager.this.DeleteContactsFromDataBaseNTV($l0);
            }
        });
    }

    public boolean UrlHandler(String $r1) throws  {
        return UrlHandler($r1, false);
    }

    public boolean UrlHandler(String $r1, boolean $z0) throws  {
        if ($r1 == null) {
            return false;
        }
        if (IsNativeThread()) {
            return UrlHandlerNTV($r1, $z0);
        }
        UrlResultRequest $r2 = new UrlResultRequest($r1, $z0);
        this.mUrlHandlerWaiter.prepare();
        PostRunnable($r2);
        this.mUrlHandlerWaiter._wait();
        return $r2.handled;
    }

    public void UrlHandler(String $r1, OnUrlHandleResult $r2) throws  {
        UrlHandler($r1, false, $r2);
    }

    public void UrlHandler(final String $r1, final boolean $z0, final OnUrlHandleResult $r2) throws  {
        if ($r1 != null) {
            PostRunnable(new Runnable() {
                public void run() throws  {
                    $r2.result = NativeManager.this.UrlHandlerNTV($r1, $z0);
                    $r2.run();
                }
            });
        }
    }

    public void Config_Set_Closed_Properly(int $i0, String $r1) throws  {
        if (this.mAppInitializedFlag) {
            ClosedProperlyNTV($i0, $r1);
        }
    }

    void ConnectivityChanged(final boolean $z0, final int $i0, final String $r1) throws  {
        if (!IsAppStarted()) {
            return;
        }
        if (IsNativeThread()) {
            ConnectivityChangedNTV($z0, $i0, $r1);
        } else {
            PostRunnable(new Runnable() {
                public void run() throws  {
                    NativeManager.this.ConnectivityChangedNTV($z0, $i0, $r1);
                }
            });
        }
    }

    public boolean isPopupShown() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        return $r1 == null ? false : $r1.getLayoutMgr().isPopupsShown();
    }

    public boolean isForceLoginWithSocial() throws  {
        boolean z0 = this.bToForceLoginWithSocial;
        this.bToForceLoginWithSocial = false;
        return z0;
    }

    public boolean isForceFullSignUp() throws  {
        return mInviteId != null;
    }

    public void OpenExternalBrowser(String $r1) throws  {
        AppService.OpenBrowser($r1);
    }

    public void registerOnUserNameResultListerner(IOnUserNameResult $r1) throws  {
        if (!this.mOnUserNameResultArray.contains($r1)) {
            this.mOnUserNameResultArray.add($r1);
        }
    }

    public void unregisterOnUserNameResultListerner(IOnUserNameResult $r1) throws  {
        if (this.mOnUserNameResultArray.contains($r1)) {
            this.mOnUserNameResultArray.remove($r1);
        }
    }

    public void UserNameSuggestResult(final int $i0, final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Iterator $r4 = NativeManager.this.mOnUserNameResultArray.iterator();
                while ($r4.hasNext()) {
                    ((IOnUserNameResult) $r4.next()).onUserNameResult($i0, $r1);
                }
            }
        });
    }

    public void InviteRequestData(final String $r1, final String $r2, final String $r3) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getActiveActivity() instanceof PhoneNumberSignInActivity) {
                    ((PhoneNumberSignInActivity) AppService.getActiveActivity()).InviteDataCallback($r2, $r1, $r3);
                }
            }
        });
    }

    public void HandlePickUpRequest(final String $r1, final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if (!$z0) {
                    $r2.SendPickUp($r1);
                } else if (AppService.getActiveActivity() instanceof ShareDriveActivity) {
                    ShareUtility.BuildShareStrings(ShareType.ShareType_ShareDrive, $r1, null);
                }
            }
        });
    }

    public void AsrActionResponse(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    $r2.getLayoutMgr().SetPoiAction($r1);
                }
            }
        });
    }

    public void logout_callback() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityLifetimeHandler.ShutDownWaze();
            }
        });
    }

    public void AuthenticateCompleted(final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getActiveActivity() instanceof AuthenticateCallbackActivity) {
                    ((AuthenticateCallbackActivity) AppService.getActiveActivity()).AuthenticateCallback($i0);
                }
            }
        });
    }

    public void AccountPasswordRecovery() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase.closeAllActivities();
                MainActivity.bToOpenPasswordRecovery = true;
                if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
                    Intent $r1 = new Intent(AppService.getMainActivity(), PhoneRegisterActivity.class);
                    $r1.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
                    $r1.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
                    AppService.getMainActivity().startActivityForResult($r1, 5000);
                    return;
                }
                AppService.getMainActivity().openPasswordRecovery();
            }
        });
    }

    public void RefreshFriendsDriving() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getActiveActivity() instanceof IRefreshFriendsDrivingData) {
                    ((IRefreshFriendsDrivingData) AppService.getActiveActivity()).onRefresh();
                }
            }
        });
    }

    public void show_root() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase.closeAllActivities();
            }
        });
    }

    public void OpenInternalBrowser(final String $r1, final String $r2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r2 = AppService.getActiveActivity();
                if ($r2 != null) {
                    Intent $r1 = new Intent($r2, InternalWebBrowser.class);
                    $r1.putExtra("title", $r1);
                    $r1.putExtra("url", $r2);
                    $r2.startActivityForResult($r1, 0);
                }
            }
        });
    }

    public void OpenEmail(final String $r1, final String $r2, final String $r3) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r2 = AppService.getActiveActivity();
                if ($r2 != null) {
                    String $r5 = "mailto:" + $r1;
                    String $r4 = $r5;
                    if (!($r2 == null && $r3 == null)) {
                        $r5 = $r5 + "?";
                        $r4 = $r5;
                        if ($r2 != null) {
                            $r4 = $r5 + "subject=" + Uri.encode($r2);
                        }
                        if ($r3 != null) {
                            if ($r2 != null) {
                                $r4 = $r4 + "&";
                            }
                            $r4 = $r4 + "body=" + Uri.encode($r3);
                        }
                    }
                    Intent $r1 = new Intent("android.intent.action.SENDTO");
                    $r1.setData(Uri.parse($r4));
                    $r2.startActivityForResult($r1, 444);
                }
            }
        });
    }

    public void OpenUserPopup(UserData $r1, int $i0, int $i1) throws  {
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            LayoutManager $r4 = $r3.getLayoutMgr();
            if ($r4 != null) {
                final LayoutManager layoutManager = $r4;
                final UserData userData = $r1;
                final int i = $i0;
                final int i2 = $i1;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.openUserPopup(userData, i, i2);
                    }
                });
            }
        }
    }

    public void OpenSharePopup(FriendUserData $r1, int $i0, String $r2, String $r3) throws  {
        MainActivity $r5 = AppService.getMainActivity();
        if ($r5 != null) {
            LayoutManager $r6 = $r5.getLayoutMgr();
            if ($r6 != null) {
                final LayoutManager layoutManager = $r6;
                final FriendUserData friendUserData = $r1;
                final int i = $i0;
                final String str = $r2;
                final String str2 = $r3;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.openSharePopup(friendUserData, i, str, str2);
                    }
                });
            }
        }
    }

    public String decryptPassword(String $r1) throws  {
        return decryptPasswordStatic($r1, AppService.getAppContext());
    }

    public String GetRegion() throws  {
        return AddressBookImpl.getInstance().GetCountryId();
    }

    public static String decryptPasswordStatic(String $r0, Context $r1) throws  {
        byte[] $r3;
        if ($r0 != null) {
            try {
                $r3 = Base64.decode($r0);
            } catch (Exception $r2) {
                throw new RuntimeException($r2);
            }
        }
        $r3 = new byte[0];
        SecretKey $r7 = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(SEKRIT));
        Cipher $r8 = Cipher.getInstance("PBEWithMD5AndDES");
        $r8.init(2, $r7, new PBEParameterSpec(getUniqueId($r1), 20));
        return new String($r8.doFinal($r3));
    }

    public String encryptPassword(String $r1) throws  {
        byte[] $r3;
        if ($r1 != null) {
            try {
                $r3 = $r1.getBytes("utf-8");
            } catch (Exception $r2) {
                throw new RuntimeException($r2);
            }
        }
        $r3 = new byte[0];
        SecretKey $r7 = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(SEKRIT));
        Cipher $r8 = Cipher.getInstance("PBEWithMD5AndDES");
        $r8.init(1, $r7, new PBEParameterSpec(getUniqueId(AppService.getAppContext()), 20));
        return new String(Base64.encodeBytes($r8.doFinal($r3)));
    }

    private static byte[] getUniqueId(Context $r0) throws  {
        String $r4 = Secure.getString($r0.getContentResolver(), "android_id");
        if ($r4 != null) {
            try {
                return $r4.getBytes("utf-8");
            } catch (Throwable $r1) {
                throw new RuntimeException($r1);
            }
        }
        SharedPreferences $r6 = $r0.getSharedPreferences(PREFS_DB, 0);
        if ($r6.contains(PREFS_KEY_UNIQUE_ID)) {
            return Utils.long2bytes($r6.getLong(PREFS_KEY_UNIQUE_ID, 0));
        }
        byte[] $r5 = new byte[8];
        new SecureRandom(Utils.long2bytes(SystemClock.elapsedRealtime())).nextBytes($r5);
        long $l0 = Utils.bytes2Long($r5).longValue();
        Editor $r9 = $r6.edit();
        $r9.putLong(PREFS_KEY_UNIQUE_ID, $l0);
        $r9.commit();
        return $r5;
    }

    public void activateProximity(final boolean $z0, final boolean $z1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    if ($z0) {
                        VoiceActivator.activate($r1, $z1);
                    } else {
                        VoiceActivator.deactivate($r1);
                    }
                }
            }
        });
    }

    public boolean isFacebookSessionValid() throws  {
        return FacebookManager.getInstance().isLoggedIn();
    }

    public boolean isDisplayReadyForAsr() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 == null) {
            return false;
        }
        LayoutManager $r2 = $r1.getLayoutMgr();
        if ($r2 == null) {
            return false;
        }
        if ($r2.isSplashVisible()) {
            return false;
        }
        ActivityBase $r3 = ActivityBase.getSingleRunningActivity();
        if ($r3 == null) {
            return false;
        }
        if ($r3 instanceof MainActivity) {
            return !this.keyguardManager.inKeyguardRestrictedInputMode();
        } else {
            return false;
        }
    }

    public void setAutoCompleteSearchTerm(final String $r1, final boolean $z0) throws  {
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            final LayoutManager $r4 = $r3.getLayoutMgr();
            if ($r4 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r4.setAutoCompleteSearchTerm($r1, $z0);
                    }
                });
            }
        }
    }

    public void showAsrTip() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.openVoiceControlTip();
                    }
                });
            }
        }
    }

    public boolean asrStartListening() throws  {
        return AsrSpeechRecognizer.getInstance().startListening();
    }

    public void asrPauseListening() throws  {
        AsrSpeechRecognizer.getInstance().pauseListening();
    }

    public void asrResumeListening() throws  {
        AsrSpeechRecognizer.getInstance().resumeListening();
    }

    public void asrStopListening() throws  {
        AsrSpeechRecognizer.getInstance().stopListening();
    }

    public void asrSendBuffer(byte[] $r1) throws  {
        if ($r1 != null) {
            SendAsrAudioBufferNTV($r1, $r1.length);
        }
    }

    public void asrListen(final long $l0, final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                WazeSpeechRecognizer.start($l0, $r1);
            }
        });
    }

    public void asrStopListen() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                WazeSpeechRecognizer.stop();
            }
        });
    }

    public void shutdown() throws  {
        ShutDown();
    }

    public void openPlannedDriveFolder() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), PlannedDriveListActivity.class));
            }
        });
    }

    public boolean IsShutdownActive() throws  {
        return this.bIsShutDown;
    }

    public void SetShutDownActive(boolean $z0) throws  {
        this.bIsShutDown = $z0;
    }

    public void updatePricesDone(String $r1, String $r2) throws  {
        Bundle $r3 = new Bundle();
        $r3.putString("title", $r1);
        $r3.putString("text", $r2);
        this.handlers.sendUpdateMessage(UH_GAS_PRICE_UPDATED, $r3);
    }

    public void CloseUserPopup() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.closeUserPopup();
                    }
                });
            }
        }
    }

    public void OpenAlertPopup(final RTAlertsAlertData $r1, final int $i0, final int $i1, final String $r2, final int $i2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    LayoutManager $r3 = $r2.getLayoutMgr();
                    if ($r3 != null) {
                        $r3.openAlertPopup($r1, $i0, $i1, $r2, $i2);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open alert. Main activity is not available");
            }
        });
    }

    public void OpenSwipePopup(final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        SpotifyPopup.getInstance().dismiss();
                        $r2.SetInterruptTime($i0);
                        $r2.OpenSwipePopups();
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open swipe. Main activity is not available");
            }
        });
    }

    public void SetPopUpInterrupt() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.SetPopUpInterrupt(false);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open swipe. Main activity is not available");
            }
        });
    }

    public void CloseAlertPopup(int $i0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                $r3.preCloseAllPopups($i0);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.doneCloseAllPopups();
                    }
                });
            }
        }
    }

    public void SetAlertPopupTimer(int timeOut) throws  {
    }

    public void openPingPopup(final RTAlertsAlertData $r1, final boolean $z0, final String $r2, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    LayoutManager $r3 = $r2.getLayoutMgr();
                    if ($r3 != null) {
                        $r3.openPingPopup($r1, $z0, $r2, $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open ping Popup. Main activity is not available");
            }
        });
    }

    public void OpenAlertTicker(int $i0, int $i1, String $r1, String $r2) throws  {
        final int i = $i1;
        final String str = $r2;
        final String str2 = $r1;
        final int i2 = $i0;
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    LayoutManager $r3 = $r2.getLayoutMgr();
                    if ($r3 != null) {
                        $r3.openAlertTicker(i, str, str2, i2);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open ping Popup. Main activity is not available");
            }
        });
    }

    public void CloseAllAlertTickersOfType(final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.CloseAllAlertTickersOfType($i0);
                        Log.i(Logger.TAG, "Closing all alert tickers per request, type: " + $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot close alert ticker. Main activity is not available");
            }
        });
    }

    public void CloseAllAlertTickers() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.CloseAllAlertTickers();
                        Log.i(Logger.TAG, "Closing all alert tickers per request");
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot close alert ticker. Main activity is not available");
            }
        });
    }

    public void CloseAlertTicker(final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.CloseAlertTicker($i0);
                        Log.i(Logger.TAG, "Closing all alert tickers per request, index: " + $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot close alert ticker. Main activity is not available");
            }
        });
    }

    public void openCommentPopup(final RTAlertsCommentData $r1, final String $r2, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.openCommentPopup($r1, $r2, $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open ping Popup. Main activity is not available");
            }
        });
    }

    public void openThumbsUpPopup(final RTAlertsThumbsUpData $r1, final String $r2, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.openThumbsUpPopup($r1, $r2, $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open thumbs up Popup. Main activity is not available");
            }
        });
    }

    public void openBeepPopup(final RTAlertsThumbsUpData $r1, final String $r2, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.openBeepPopup($r1, $r2, $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open thumbs up Popup. Main activity is not available");
            }
        });
    }

    public void closeThumbsUpPopup() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.closeThumbsUpPopup();
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot close thumbs up. Main activity is not available");
            }
        });
    }

    public void showTrafficDetectionPopup() throws  {
        final boolean $z0 = MyWazeNativeManager.getInstance().isGuestUserNTV();
        final boolean $z1 = MyWazeNativeManager.getInstance().FoursquareEnabledNTV();
        final boolean $z2 = getInstance().isClosureEnabledNTV();
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.showTrafficDetectionPopup($z0, $z1, $z2);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open Traffic Detetcion Popup. Main activity is not available");
            }
        });
    }

    public void trafficBarSetHidden(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.trafficBarSetHidden($z0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot show Traffic Bar. Main activity is not available");
            }
        });
    }

    public void trafficBarClose() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.trafficBarClose();
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot show Traffic Bar. Main activity is not available");
            }
        });
    }

    public void trafficBarShowPopUp(final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.trafficBarShowPopUp($i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot show Traffic Bar. Main activity is not available");
            }
        });
    }

    public void trafficBarSet(final int $i0, final int $i1, final int[] $r1, final int[] $r2, final int $i2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r3 = AppService.getMainActivity();
                if ($r3 != null) {
                    LayoutManager $r4 = $r3.getLayoutMgr();
                    if ($r4 != null) {
                        $r4.trafficBarSet($i0, $i1, $r1, $r2, $i2);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot show Traffic Bar. Main activity is not available");
            }
        });
    }

    public void showEtaUpdatePopUp(final int $i0, final String $r1, final String $r2, final String $r3, final int $i1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r3 = AppService.getMainActivity();
                if ($r3 != null) {
                    LayoutManager $r4 = $r3.getLayoutMgr();
                    if ($r4 != null) {
                        $r4.showEtaUpdatePopUp($i0, $r1, $r2, $r3, $i1);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open Eta Update Popup. Main activity is not available");
            }
        });
    }

    public void ShowNotificationMessage(final String $r1, final String $r2, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.showNotificationMessage($r1, $r2, $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot show ShowNotificationMessage. Main activity is not available");
            }
        });
    }

    public void showDetailsPopup(int $i0, int $i1, int $i2, String $r1, String $r2, String $r3, boolean $z0, boolean moreActionEnabled, int $i3, int $i4, int $i5, int $i6, String $r4, String $r5, String $r6, String $r7, VenueData $r8, VenueData $r9, int $i7) throws  {
        final int i = $i4;
        final int i2 = $i5;
        final String str = $r1;
        final String str2 = $r2;
        final VenueData venueData = $r8;
        final String str3 = $r6;
        final String str4 = $r7;
        final String str5 = $r4;
        final String str6 = $r5;
        final int i3 = $i0;
        final int i4 = $i1;
        final int i5 = $i2;
        final String str7 = $r3;
        final boolean z = $z0;
        final int i6 = $i3;
        final int i7 = $i6;
        final VenueData venueData2 = $r9;
        final int i8 = $i7;
        AppService.Post(new Runnable() {
            public void run() throws  {
                MapViewWrapper $r2 = AppService.getActiveMapViewWrapper();
                int $i0 = i;
                Integer $r3 = Integer.valueOf($i0);
                $i0 = i2;
                AddressItem addressItem = new AddressItem($r3, Integer.valueOf($i0), str, str2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, venueData, null);
                addressItem.mIsNavigable = true;
                String str = str3;
                String $r5 = str;
                addressItem.mPreviewIcon = str;
                addressItem.setIcon(str4);
                addressItem.setAdvertiserData(str5, str6, 0, null);
                addressItem.setAddress(str2);
                if ($r2 != null) {
                    $r2.showDetailsPopup(i3, i4, i5, str, str2, str7, z, i6, addressItem, i7, venueData2, i8);
                    return;
                }
                Log.e(Logger.TAG, "Cannot show DetailsPopup. MapView is not available");
                MainActivity.registerOnResumeEvent(this);
            }
        });
    }

    public void updateDetailsPopupContent(final int $i0, final String $r1, final String $r2) throws  {
        if (AppService.getMainActivity() != null) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    MapViewWrapper $r3 = AppService.getActiveMapViewWrapper();
                    if ($r3 == null) {
                        MainActivity.registerOnResumeEvent(this);
                    } else if ($r3.hasDetailsPopUpInstance()) {
                        $r3.updateDetailsPopupContent($i0, $r1, $r2);
                        $r3.requestLayout();
                        $r3.invalidate();
                    }
                }
            });
        }
    }

    public void updateDetailsPopupInfo(final int $i0, final String $r1, final boolean $z0) throws  {
        if (AppService.getMainActivity() != null) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    MapViewWrapper $r2 = AppService.getActiveMapViewWrapper();
                    if ($r2 == null) {
                        MainActivity.registerOnResumeEvent(this);
                    } else if ($r2.hasDetailsPopUpInstance()) {
                        $r2.updateDetailsPopupInfo($i0, $r1, $z0);
                        $r2.requestLayout();
                        $r2.invalidate();
                    }
                }
            });
        }
    }

    public void updateDetailsPopup(final int $i0, final int $i1) throws  {
        if (AppService.getMainActivity() != null) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    MapViewWrapper $r1 = AppService.getActiveMapViewWrapper();
                    if ($r1 == null) {
                        MainActivity.registerOnResumeEvent(this);
                    } else if ($r1.hasDetailsPopUpInstance()) {
                        $r1.updateDetailsPopup($i0, $i1);
                        $r1.requestLayout();
                        $r1.invalidate();
                    }
                }
            });
        }
    }

    public void updateUserPopup(final int $i0, final int $i1) throws  {
        if (AppService.getMainActivity() != null) {
            final LayoutManager $r2 = AppService.getMainActivity().getLayoutMgr();
            if ($r2 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r2.UpdateUserPopup($i0, $i1);
                    }
                });
            }
        }
    }

    public void closeDetailsPopup() throws  {
        if (AppService.getMainActivity() != null) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    MapViewWrapper $r1 = AppService.getActiveMapViewWrapper();
                    if ($r1 == null) {
                        Log.e(Logger.TAG, "Cannot close DetailsPopup. Main activity is not available");
                        MainActivity.registerOnResumeEvent(this);
                    } else if ($r1.hasDetailsPopUpInstance()) {
                        $r1.closeDetailsPopup();
                    }
                }
            });
        }
    }

    public void RegisterActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    $r1.startRegisterActivity();
                }
            }
        });
    }

    public boolean isMainActivity() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        return $r1 != null && $r1.isRunning() && $r1.isVisible();
    }

    public String SHA256(String $r1) throws  {
        try {
            MessageDigest $r3 = MessageDigest.getInstance("SHA-256");
            $r3.reset();
            try {
                byte[] $r4 = $r3.digest($r1.getBytes("UTF-8"));
                StringBuffer $r2 = new StringBuffer();
                for (byte $b2 : $r4) {
                    $r2.append(Integer.toString(($b2 & (short) 255) + 256, 16).substring(1));
                }
                return $r2.toString();
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } catch (NoSuchAlgorithmException e2) {
            return null;
        }
    }

    public boolean mainMenuShown() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        return $r1 == null ? false : $r1.getLayoutMgr().isSwipeableLayoutOpened();
    }

    public boolean AccountDetailsShown() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        return $r1 == null ? false : $r1.IsAccountDetailsShown();
    }

    public int getPixelSize(String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            return 0;
        }
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 == null) {
            return 0;
        }
        LayoutManager $r3 = $r2.getLayoutMgr();
        if ($r3 == null) {
            return 0;
        }
        if ("bar_bottom_height".equals($r1)) {
            return $r3.getBottomBarHeight();
        }
        if ("bar_top_height".equals($r1)) {
            return $r3.getNavBarTopHeight();
        }
        return "bar_bottom_height_with_current_street".equals($r1) ? $r3.getCurrentStreetHeight() : 0;
    }

    public boolean reportMenuShown() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        return $r1 == null ? false : $r1.getLayoutMgr().reportMenuShown();
    }

    public boolean IsAlerterShown() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        return $r1 == null ? false : $r1.getLayoutMgr().isAlerterShown();
    }

    public boolean areCarpoolTipsShown() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 == null) {
            return false;
        }
        LayoutManager $r2 = $r1.getLayoutMgr();
        return $r2 != null ? $r2.areCarpoolTipsShown() : false;
    }

    public boolean is24HrClock() throws  {
        return DateFormat.is24HourFormat(AppService.getAppContext());
    }

    public void setSharedAddressItem(AddressItem $r1) throws  {
        this.mSharedAI = $r1;
    }

    public void signup_finished() throws  {
        SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FINISHED, null, null, false);
    }

    public void showAddressOption(String $r1, String $r2, int $i0, int $i1, String $r3, String $r4, String $r5, String $r6, String $r7, String $r8, String $r9, String $r10, String $r11, String $r12) throws  {
        ActivityBase $r13 = AppService.getActiveActivity();
        if ($r13 != null) {
            AddressItem $r16;
            AddressItem $r14 = null;
            AddressItem addressItem = null;
            if (!(this.mCalendarAI == null || $r5 == null)) {
                $r16 = this.mCalendarAI;
                if ($r5.equals($r16.getMeetingId())) {
                    $r14 = this.mCalendarAI;
                }
            }
            if (!(this.mSharedAI == null || $r11 == null)) {
                $r16 = this.mSharedAI;
                if ($r11.equals($r16.getMeetingId())) {
                    addressItem = this.mSharedAI;
                }
            }
            if (this.mSearchResults != null && $r12 != null) {
                ArrayList $r18 = this.mSearchResults;
                Iterator $r19 = $r18.iterator();
                while ($r19.hasNext()) {
                    AddressItem $r162 = (AddressItem) $r19.next();
                    if ($r12.equals($r162.VanueID)) {
                        addressItem = $r162;
                        break;
                    }
                }
            }
            $r13.startNativeOptionActivity($r1, $r2, $i0, $i1, $r3, $r4, $r14, $r6, $r7, $r8, $r9, $r10, addressItem, $r12);
        }
    }

    public void setSearchResults(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/navigate/AddressItem;", ">;)V"}) ArrayList<AddressItem> $r1) throws  {
        this.mSearchResults = $r1;
    }

    public void ShowAlerterPopup(String $r1, String $r2, String $r3, boolean $z0, boolean $z1, int $i0, int $i1, boolean $z2) throws  {
        MainActivity $r5 = AppService.getMainActivity();
        if ($r5 != null) {
            LayoutManager $r6 = $r5.getLayoutMgr();
            if ($r6 != null) {
                final LayoutManager layoutManager = $r6;
                final String str = $r1;
                final String str2 = $r2;
                final String str3 = $r3;
                final boolean z = $z0;
                final boolean z2 = $z1;
                final int i = $i0;
                final int i2 = $i1;
                final boolean z3 = $z2;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.showAlerterPopup(str, str2, str3, z, z2, i, i2, z3);
                    }
                });
            }
        }
    }

    public void ShowFriendsOnlinePopup(final int $i0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.OpenFriendsOnlinePopUp($i0);
                    }
                });
            }
        }
    }

    public void ShowUpdateGasPricePopup(long $l0, long $l1) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                final LayoutManager layoutManager = $r3;
                final long j = $l1;
                final long j2 = $l0;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.OpenUpdatePricesPopUp(j, j2);
                    }
                });
            }
        }
    }

    public void UpdateAlerterPopup(String $r1, String $r2, String $r3) throws  {
        MainActivity $r5 = AppService.getMainActivity();
        if ($r5 != null) {
            LayoutManager $r6 = $r5.getLayoutMgr();
            if ($r6 != null) {
                final LayoutManager layoutManager = $r6;
                final String str = $r1;
                final String str2 = $r2;
                final String str3 = $r3;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.updateAlerterPopup(str, str2, str3);
                    }
                });
            }
        }
    }

    public void SetAlerterPopupCloseTime(final int $i0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.setAlerterPopupCloseTime($i0);
                    }
                });
            }
        }
    }

    public void HideAlerterPopup() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.hideAlerterPopup();
                    }
                });
            }
        }
    }

    public void CloseFriendsOnlinePopup() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.closeFriendsOnlinePopup();
                    }
                });
            }
        }
    }

    public void OpenProgressPopup(final String $r1) throws  {
        if (!isProgressPopupLocked()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.closeProgressPopup();
                    ActivityBase $r3 = AppService.getActiveActivity();
                    if ($r3 != null && $r3.isAlive()) {
                        NativeManager.this.openProgressPopup($r3, $r1, null);
                    }
                }
            });
        }
    }

    public synchronized void lockProgressPopup() throws  {
        this.mProgressBarLocked = true;
    }

    public synchronized void unlockProgressPopup() throws  {
        this.mProgressBarLocked = false;
    }

    public synchronized boolean isProgressPopupLocked() throws  {
        return this.mProgressBarLocked;
    }

    public String getHashToName(int $i0) throws  {
        return AddressBookImpl.getInstance().GetNameFromHash($i0);
    }

    public Locale getLocale() throws  {
        Locale $r2 = Locale.getDefault();
        String $r4 = SettingsNativeManager.getInstance().getLanguagesLocaleNTV();
        if ($r4 == null || $r4.isEmpty()) {
            return $r2;
        }
        try {
            return new Locale($r4);
        } catch (Exception e) {
            return $r2;
        }
    }

    public String intToString(int $i0) throws  {
        return String.format(getLocale(), "%d", new Object[]{Integer.valueOf($i0)});
    }

    public String getLanguageURLSuffix() throws  {
        Locale $r1 = getLocale();
        return "&hl=" + $r1.getLanguage() + "&lang=" + $r1.getISO3Language();
    }

    public String getDisplayDensity() throws  {
        switch (AppService.getAppResources().getDisplayMetrics().densityDpi) {
            case 240:
            case 280:
                return "hd";
            case 320:
            case 360:
                return "xhd";
            case 400:
            case 420:
            case DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO /*480*/:
            case DisplayStrings.DS_RANK /*560*/:
            case DisplayStrings.DS_TIME_SAVEDC /*640*/:
                return "xxhd";
            default:
                return "md";
        }
    }

    public String getLanguageCode() throws  {
        return getLocale().getLanguage();
    }

    public String getLanguageCode2Letters() throws  {
        String $r2 = getLocale().getLanguage();
        if ($r2.equals("iw")) {
            return "he";
        }
        if ($r2.equals("in")) {
            return "id";
        }
        return $r2.equals("ji") ? "yi" : $r2;
    }

    public void OpenProgressIconPopup(final String $r1, final String $r2) throws  {
        if (!isProgressPopupLocked()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.closeProgressPopup();
                    ActivityBase $r4 = AppService.getActiveActivity();
                    if ($r4 != null && $r4.isAlive()) {
                        NativeManager.this.openProgressPopup($r4, $r1, $r2);
                    }
                }
            });
        }
    }

    public void OpenMainActivityProgressIconPopup(final String $r1, final String $r2) throws  {
        if (!isProgressPopupLocked()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.closeProgressPopup();
                    MainActivity $r4 = AppService.getMainActivity();
                    if ($r4 != null && $r4.isAlive()) {
                        NativeManager.this.openProgressPopup($r4, $r1, $r2);
                    }
                }
            });
        }
    }

    public void OpenMainActivityProgressPopup(final String $r1) throws  {
        if (!isProgressPopupLocked()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.closeProgressPopup();
                    MainActivity $r3 = AppService.getMainActivity();
                    if ($r3 != null && $r3.isAlive()) {
                        NativeManager.this.openProgressPopup($r3, $r1, null);
                    }
                }
            });
        }
    }

    public void CloseProgressPopup() throws  {
        DriveToNativeManager.getInstance().loadingRoutesFinished();
        if (!isProgressPopupLocked()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.closeProgressPopup();
                }
            });
        }
    }

    private void openProgressPopup(Context $r1, String $r2, String $r3) throws  {
        this.mProgressBarCommon = new ProgressBarDialog($r1, $r2, $r3);
        this.mProgressBarCommon.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) throws  {
                NativeManager.this.unlockProgressPopup();
            }
        });
        this.mProgressBarCommon.show();
    }

    private void closeProgressPopup() throws  {
        if (this.mProgressBarCommon != null) {
            this.mProgressBarCommon.dismiss();
        }
        this.mProgressBarCommon = null;
    }

    public void setShowRoutesWhenNavigationStarts(boolean $z0) throws  {
        this.mShowRoutesWhenNavigationStarts = $z0;
    }

    public void OpenNavResultPopup(String $r1, String $r2, String $r3, String $r4, String $r5, int $i0, String $r6, int $i1, boolean $z0, boolean $z1, String $r7, String $r8, int $i2, String $r9, String $r10, boolean $z2, int $i3, boolean $z3, int $i4, boolean $z4, String $r11, String $r12, String $r13, int $i5, int $i6, String $r14, String $r15, String $r16, FriendUserData[] $r17, boolean $z5, boolean $z6, boolean $z7, boolean $z8, boolean $z9) throws  {
        MainActivity $r19 = AppService.getMainActivity();
        if ($r19 != null) {
            LayoutManager $r20 = $r19.getLayoutMgr();
            if ($r20 != null) {
                final LayoutManager layoutManager = $r20;
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
                final FriendUserData[] friendUserDataArr = $r17;
                final boolean z6 = $z5;
                final boolean z7 = $z6;
                final boolean z8 = $z7;
                final boolean z9 = $z8;
                final boolean z10 = $z9;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        LayoutManager $r9 = layoutManager;
                        String $r10 = str;
                        String $r1 = str2;
                        String $r2 = str3;
                        String $r3 = str4;
                        String $r4 = str5;
                        int $i0 = i;
                        String $r5 = str6;
                        int $i1 = i2;
                        boolean $z0 = z;
                        boolean $z1 = z2;
                        String $r6 = str7;
                        String $r7 = str8;
                        int $i2 = i3;
                        String $r8 = str9;
                        String $r11 = str10;
                        boolean $z2 = z3;
                        int $i3 = i4;
                        boolean $z3 = z4;
                        int $i4 = i5;
                        boolean $z4 = z5;
                        String $r12 = str11;
                        String $r13 = str12;
                        String $r14 = str13;
                        int $i5 = i6;
                        int $i6 = i7;
                        String $r15 = str14;
                        String $r16 = str15;
                        String $r17 = str16;
                        FriendUserData[] $r18 = friendUserDataArr;
                        boolean $z5 = z6;
                        boolean $z6 = z7;
                        boolean $z7 = z8;
                        boolean $z8 = z9;
                        boolean $z9 = z10;
                        NativeManager $r19 = NativeManager.this;
                        $r9.openNavResults($r10, $r1, $r2, $r3, $r4, $i0, $r5, $i1, $z0, $z1, $r6, $r7, $i2, $r8, $r11, $z2, $i3, $z3, $i4, $z4, $r12, $r13, $r14, $i5, $i6, $r15, $r16, $r17, $r18, $z5, $z6, $z7, $z8, $z9, $r19.mShowRoutesWhenNavigationStarts);
                        $r19 = NativeManager.this;
                        if ($r19.mShowRoutesWhenNavigationStarts && !z7) {
                            NativeManager.this.mShowRoutesWhenNavigationStarts = false;
                        }
                    }
                });
            }
        }
    }

    public void openStopPointNavigate(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startStopPointActivity($z0);
            }
        });
    }

    public void OpenAboutPopup(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                LayoutManager.OpenAboutPopup($r1);
            }
        });
    }

    public void OpenSystemMessageWebPopUp(final String $r1) throws  {
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            final LayoutManager $r4 = $r3.getLayoutMgr();
            if ($r4 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r4.OpenSystemMessageWebPopUp($r1);
                    }
                });
            }
        }
    }

    public void OpenPoi(int $i0, String $r1, int $i1, int $i2, int $i3, int $i4, int $i5, boolean $z0, int $i6, int $i7, String $r2, String $r3) throws  {
        if (AppService.isNetworkAvailable()) {
            MainActivity $r5 = AppService.getMainActivity();
            if ($r5 != null) {
                LayoutManager $r6 = $r5.getLayoutMgr();
                if ($r6 != null) {
                    final LayoutManager layoutManager = $r6;
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
                    AppService.Post(new Runnable() {
                        public void run() throws  {
                            layoutManager.openPoi(i, i2, i3, i4, i5, i6, z, str, i7, i8, str2, str3);
                        }
                    });
                    return;
                }
                return;
            }
            return;
        }
        Log.e(Logger.TAG, "NAtiveManager:: OpenPoi, no network connection");
        AnonymousClass140 anonymousClass140 = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) throws  {
            }
        };
        MsgBox.openMessageBoxWithCallback(getLanguageString(DisplayStrings.DS_NO_NETWORK_CONNECTION), getLanguageString(252), false, anonymousClass140);
    }

    public void setNorthUp(final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetNorthUpNTV($i0);
            }
        });
    }

    public void setAutoZoom(final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetAutoZoomNTV($i0);
            }
        });
    }

    public boolean getNorthUp() throws  {
        return GetNorthUpNTV();
    }

    public boolean getAutoZoom() throws  {
        return GetAutoZoomNTV();
    }

    public boolean getAccessToCalendarAllowed() throws  {
        return calendarAccessEnabled() && AccessToCalendarAllowedNTV();
    }

    public boolean CalendarFeatureEnabled() throws  {
        return CalendarFeatureEnabledNTV();
    }

    public String GetWazeAutocompleteUrl(String $r1) throws  {
        return GetWazeAutoCompleteURLNTV($r1);
    }

    public String GetWazeAutocompleteAdsUrl() throws  {
        return GetWazeAutoCompleteAdsURLNTV();
    }

    public void venueCreate(final VenueData $r1, final String $r2, final String $r3, final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueCreateNTV($r1, $r2, $r3, $z0);
            }
        });
    }

    public void venueUpdate(final VenueData $r1, final VenueData $r2, final String $r3, final String $r4) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueUpdateNTV($r1, $r2, $r3, $r4);
            }
        });
    }

    public void venueSaveNav(final VenueData $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueSaveNavNTV($r1);
            }
        });
    }

    public void venueGet(final String $r1, final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueGetNTV($r1, $i0, false);
            }
        });
    }

    public void venueGet(final String $r1, final int $i0, final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueGetNTV($r1, $i0, $z0);
            }
        });
    }

    public void venueFlag(final String $r1, final int $i0, final String $r2, final String $r3) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueFlagNTV($r1, $i0, $r2, $r3);
            }
        });
    }

    public void venueFlagImage(final String $r1, final String $r2, final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueFlagImageNTV($r1, $r2, $i0);
            }
        });
    }

    public void venueLikeImage(final String $r1, final String $r2) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueLikeImageNTV($r1, $r2);
            }
        });
    }

    public void venueUnlikeImage(final String $r1, final String $r2) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueUnlikeImageNTV($r1, $r2);
            }
        });
    }

    public void venueDeleteImage(final String $r1, final String $r2) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueDeleteImageNTV($r1, $r2);
            }
        });
    }

    public void venueAddImage(final String $r1, final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueAddImageNTV($r1, $i0);
            }
        });
    }

    public void venueSearch(final int $i0, final int $i1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.venueSearchNTV($i0, $i1);
            }
        });
    }

    public int mathDistance(int $i0, int $i1, int $i2, int $i3) throws  {
        return mathDistanceNTV($i0, $i1, $i2, $i3);
    }

    public int mathDistance(AddressItem $r1, AddressItem $r2) throws  {
        return mathDistanceNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), $r2.getLocationX().intValue(), $r2.getLocationY().intValue());
    }

    public DistanceAndUnits mathDistanceAndUnits(int $i0, int $i1, int $i2, int $i3, boolean $z0, int $i4) throws  {
        return mathDistanceAndUnitsNTV($i0, $i1, $i2, $i3, $z0, $i4);
    }

    public DistanceAndUnits mathDistanceAndUnits(AddressItem $r1, AddressItem $r2, boolean $z0, int $i0) throws  {
        return mathDistanceAndUnitsNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), $r2.getLocationX().intValue(), $r2.getLocationY().intValue(), $z0, $i0);
    }

    public DistanceAndUnits mathDistanceAndUnitsToCurrentLocation(AddressItem $r1, boolean $z0, int $i0) throws  {
        Location $r3 = LocationFactory.getInstance().getLastLocation();
        if ($r3 != null) {
            NativeLocation $r4 = LocationFactory.getNativeLocation($r3);
            return mathDistanceAndUnitsNTV($r4.mLongtitude, $r4.mLatitude, $r1.getLocationX().intValue(), $r1.getLocationY().intValue(), $z0, $i0);
        }
        Logger.m38e("mathDistanceAndUnitsToCurrentLocation: Received null location from location factory");
        return null;
    }

    public DistanceAndUnits mathDistanceAndUnitsToCurrentLocation(int $i0, int $i1, boolean $z0, int $i2) throws  {
        Location $r2 = LocationFactory.getInstance().getLastLocation();
        if ($r2 != null) {
            NativeLocation $r3 = LocationFactory.getNativeLocation($r2);
            return mathDistanceAndUnitsNTV($r3.mLongtitude, $r3.mLatitude, $i0, $i1, $z0, $i2);
        }
        Logger.m38e("mathDistanceAndUnitsToCurrentLocation: Received null location from location factory");
        return null;
    }

    public void callCallbackInt(final long $l0, final int $i1) throws  {
        if ($l0 != 0) {
            Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.callCallbackIntNTV($l0, $i1);
                }
            });
        }
    }

    public boolean wasFriendsListBannerDismissed() throws  {
        return wasFriendsListBannerDismissedNTV();
    }

    public void setFriendsListBannerDismissed() throws  {
        setFriendsListBannerDismissedNTV();
    }

    public int getEditPlaceLocationRadius() throws  {
        return getEditPlaceLocationRadiusNTV();
    }

    public String get2LastDigitLicensePlate() throws  {
        return get2LastDigitLicensePlateNTV();
    }

    public int getVenueGetTimeout() throws  {
        return getVenueGetTimeoutNTV();
    }

    public String getNavLink(int $i0, int $i1) throws  {
        return GetNavLinkNTV($i0, $i1);
    }

    public String getTripUnits() throws  {
        return GetTripUnitsNTV();
    }

    public void OpenFriendsList(int Type) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r2 = AppService.getActiveActivity();
                if ($r2 != null) {
                    $r2.startActivity(new Intent($r2, InboxActivity.class));
                }
            }
        });
    }

    public void AddGeofencing(double $d0, double $d1, int $i0, int $i1, QuestionData $r1, String $r2, String $r3) throws  {
        final double d = $d1;
        final double d2 = $d0;
        final String str = $r2;
        final String str2 = $r3;
        final QuestionData questionData = $r1;
        final int i = $i0;
        final int i2 = $i1;
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(AppService.getAppContext()) == 0) {
                    if (GeoFencingService.IsRunning()) {
                        GeoFencingService.stop(false);
                    }
                    PendingIntent $r3 = PendingIntent.getBroadcast(AppService.getAppContext(), 0, new Intent("com.android.GEO_FENCING"), 0);
                    QuestionData.SetLocationData(AppService.getAppContext(), d, d2, str, str2);
                    QuestionData $r6 = questionData;
                    QuestionData.SaveQuestionData($r6);
                    LocationFactory.getInstance().RemoveProximityAlert($r3);
                    LocationFactory.getInstance().SetProximityAlert($r3, d, d2, (float) i, (long) i2);
                }
            }
        });
    }

    public void AddGeofencingForWakeUpPush(double $d0, double $d1, int $i0, int $i1, int $i2, int $i3, String $r1, String $r2, String $r3) throws  {
        final String str = $r3;
        final String str2 = $r1;
        final String str3 = $r2;
        final int i = $i0;
        final int i2 = $i1;
        final double d = $d1;
        final double d2 = $d0;
        final int i3 = $i2;
        final int i4 = $i3;
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(AppService.getAppContext()) == 0) {
                    Intent $r1 = new Intent("com.android.GEO_FENCING");
                    $r1.putExtra("TYPE", str);
                    $r1.putExtra("Destination", str2);
                    $r1.putExtra("VenueID", str3);
                    $r1.putExtra("DEST_LAT", i);
                    $r1.putExtra("DEST_LON", i2);
                    PendingIntent $r4 = PendingIntent.getBroadcast(AppService.getAppContext(), 1, $r1, 0);
                    LocationFactory.getInstance().RemoveProximityAlert($r4);
                    LocationFactory.getInstance().SetProximityAlert($r4, d, d2, (float) i3, (long) i4);
                }
            }
        });
    }

    public void SaveKeyData(final String $r1, final String $r2) throws  {
        AppService.Post(new Runnable() {

            class C12151 implements Runnable {
                C12151() throws  {
                }

                public void run() throws  {
                    Log.w("NativeManager", "SaveKeyData failed to run because context was null. Retrying now.");
                    NativeManager.this.SaveKeyData($r1, $r2);
                }
            }

            public void run() throws  {
                if (AppService.getAppContext() != null) {
                    SharedPreferences $r2 = AppService.getAppContext().getSharedPreferences("com.waze.Keys", 0);
                    $r2.edit().putString($r1, $r2).apply();
                    $r2.edit().commit();
                    return;
                }
                Log.w("NativeManager", "SaveKeyData failed to run because context was null. Retrying soon");
                AppService.Post(new C12151(), 1500);
            }
        });
    }

    public void SetDarkViewOffset(final int $i0, final int $i1) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.SetDarkViewOffset($i0, $i1);
                    }
                });
            }
        }
    }

    public void PreparePoi(final int $i0, final String $r1) throws  {
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            final LayoutManager $r4 = $r3.getLayoutMgr();
            if ($r4 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r4.preparePoi($i0, $r1);
                    }
                });
            }
        }
    }

    public void setAddressCandidatePoiPopup(String $r1, String $r2, String $r3, String $r4, AddressItem $r5) throws  {
        MainActivity $r7 = AppService.getMainActivity();
        if ($r7 != null) {
            LayoutManager $r8 = $r7.getLayoutMgr();
            if ($r8 != null) {
                final LayoutManager layoutManager = $r8;
                final String str = $r1;
                final String str2 = $r2;
                final String str3 = $r3;
                final String str4 = $r4;
                final AddressItem addressItem = $r5;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.enterAddressCandidateToPoi(str, str2, str3, str4, addressItem);
                    }
                });
            }
        }
    }

    public boolean isPoiPreloaded(int $i0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 == null) {
            return false;
        }
        LayoutManager $r3 = $r2.getLayoutMgr();
        if ($r3 == null) {
            return false;
        }
        try {
            return $r3.isPoiPreloaded($i0);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPoiTemplateLoaded() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 == null) {
            return false;
        }
        LayoutManager $r3 = $r2.getLayoutMgr();
        if ($r3 == null) {
            return false;
        }
        try {
            return $r3.isPoiTemplateLoaded();
        } catch (Exception e) {
            return false;
        }
    }

    public void ClosePoi() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.closePoi();
                    }
                });
            }
        }
    }

    public boolean ToCancelSplash() throws  {
        if (!bIsCheck) {
            return true;
        }
        bIsCheck = false;
        return IsUpgradeNTV();
    }

    public void ShowBonusWebPopup(int $i0, String $r1, int $i1, int $i2) throws  {
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            LayoutManager $r4 = $r3.getLayoutMgr();
            if ($r4 != null) {
                final LayoutManager layoutManager = $r4;
                final int i = $i0;
                final String str = $r1;
                final int i2 = $i1;
                final int i3 = $i2;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.ShowBonusWebPopup(i, str, i2, i3);
                    }
                });
            }
        }
    }

    public void OpenPreview(String $r1) throws  {
        final ActivityBase $r3 = AppService.getActiveActivity();
        if ($r3 != null) {
            this.search_handler = new Handler() {
                public void handleMessage(Message $r1) throws  {
                    DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this);
                    NativeManager.this.search_handler = null;
                    if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
                        final AddressItem $r6 = (AddressItem) $r1.getData().getSerializable("address_item");
                        if ($r6.getLocationX().intValue() != -1 && $r6.getLocationY().intValue() != -1) {
                            AppService.Post(new Runnable() {
                                public void run() throws  {
                                    Intent $r1 = new Intent($r3, AddressPreviewActivity.class);
                                    $r1.putExtra(PublicMacros.ADDRESS_ITEM, $r6);
                                    $r3.startActivityForResult($r1, 1);
                                }
                            });
                        }
                    }
                }
            };
            DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.search_handler);
            venueGet($r1, 1);
        }
    }

    public void openPlaceEdit(String $r1) throws  {
        final ActivityBase $r3 = AppService.getActiveActivity();
        if ($r3 != null) {
            DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, new Handler() {
                public void handleMessage(Message $r1) throws  {
                    DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this);
                    if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
                        final AddressItem $r5 = (AddressItem) $r1.getData().getSerializable("address_item");
                        if ($r5.getLocationX().intValue() != -1 && $r5.getLocationY().intValue() != -1) {
                            AppService.Post(new Runnable() {
                                public void run() throws  {
                                    Intent $r1 = new Intent($r3, AddressPreviewActivity.class);
                                    $r1.putExtra(PublicMacros.ADDRESS_ITEM, $r5);
                                    $r1.putExtra(PublicMacros.EDIT, true);
                                    $r3.startActivityForResult($r1, 1);
                                }
                            });
                        }
                    }
                }
            });
            venueGet($r1, 1);
        }
    }

    public void ShowMessageTicker(String $r1, String $r2, String $r3, int $i0) throws  {
        MainActivity $r5 = AppService.getMainActivity();
        if ($r5 != null) {
            LayoutManager $r6 = $r5.getLayoutMgr();
            if ($r6 != null) {
                final LayoutManager layoutManager = $r6;
                final String str = $r1;
                final String str2 = $r2;
                final String str3 = $r3;
                final int i = $i0;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.showMessageTicker(str, str2, str3, i);
                    }
                });
            }
        }
    }

    public void LangInitialized() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.onLanguageInitialized();
                    }
                });
            }
        }
    }

    public void RoadClosureEnableNextButton(final int $i0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.ClosureMapEnableButton($i0);
                    }
                });
            }
        }
    }

    public void createLogCat() throws  {
        try {
            Runtime.getRuntime().exec("logcat -d -v time -f " + AppService.getExternalStoragePath() + "/waze/crash_logs/Logcat.logcat");
            Thread.sleep(2000);
        } catch (IOException $r4) {
            $r4.printStackTrace();
        } catch (InterruptedException $r5) {
            $r5.printStackTrace();
        }
    }

    public void OpenReportMenu() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 != null) {
            LayoutManager $r2 = $r1.getLayoutMgr();
            if ($r2 != null) {
                boolean $z0 = MyWazeNativeManager.getInstance().isGuestUserNTV();
                final LayoutManager layoutManager = $r2;
                final boolean z = $z0;
                final boolean FoursquareEnabledNTV = MyWazeNativeManager.getInstance().FoursquareEnabledNTV();
                final boolean isClosureEnabledNTV = getInstance().isClosureEnabledNTV();
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        layoutManager.openReportMenu(z, FoursquareEnabledNTV, isClosureEnabledNTV);
                    }
                });
            }
        }
    }

    public void execute(Runnable $r1) throws  {
        PostRunnable($r1);
    }

    public static void Post(Runnable $r0) throws  {
        if (mInstance != null && mNativeThread.isAlive()) {
            mInstance.PostRunnable($r0);
        }
    }

    public static void PostInitEvent(Runnable $r0) throws  {
        if (IsAppStarted()) {
            Log.w(Logger.TAG, "The application has been already started - the event will not be called");
        } else {
            mInitialLooperQueue.add($r0);
        }
    }

    public static void Post(Runnable $r0, long $l0) throws  {
        if (mInstance != null) {
            mInstance.PostRunnable($r0, $l0);
        }
    }

    public boolean PostRunnable(Runnable $r1) throws  {
        if (mAppShutDownFlag || !mNativeThread.isAlive()) {
            return false;
        }
        Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt(UIEvent.UI_EVENT_GENERIC_RUNNABLE), $r1).sendToTarget();
        return true;
    }

    public void PostRunnable(Runnable $r1, long $l0) throws  {
        if (mNativeThread.isAlive()) {
            this.mUIMsgDispatcher.sendMessageDelayed(Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt(UIEvent.UI_EVENT_GENERIC_RUNNABLE), $r1), $l0);
        }
    }

    public void PostRunnableAtTime(Runnable $r1, long $l0) throws  {
        if (mNativeThread.isAlive()) {
            this.mUIMsgDispatcher.sendMessageAtTime(Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt(UIEvent.UI_EVENT_GENERIC_RUNNABLE), $r1), $l0);
        }
    }

    public void RemoveRunnable(Runnable $r1) throws  {
        if (mNativeThread.isAlive()) {
            this.mUIMsgDispatcher.removeMessages(UIEvent.ToInt(UIEvent.UI_EVENT_GENERIC_RUNNABLE), $r1);
        }
    }

    public int getBatteryLevel() throws  {
        PowerManager $r1 = AppService.getPowerManager();
        if ($r1 != null) {
            return $r1.getCurrentLevel();
        }
        return -1;
    }

    public int getIsCharging() throws  {
        PowerManager $r1 = AppService.getPowerManager();
        if ($r1 == null) {
            return -1;
        }
        return $r1.getIsCharging();
    }

    public int getTemperature() throws  {
        PowerManager $r1 = AppService.getPowerManager();
        if ($r1 != null) {
            return $r1.getTemperature();
        }
        return -1;
    }

    static NativeCanvasRenderer getNativeCanvas() throws  {
        return mNativeCanvasRenderer;
    }

    SpeechttManagerBase getSpeechttManager() throws  {
        return this.mSpeechttManager;
    }

    TtsManager getTtsManager() throws  {
        return this.mTtsManager;
    }

    public void ShowSoftKeyboard(int $i0, int $i1) throws  {
        boolean $z0 = true;
        final MapView $r3 = getMainView().getMapView();
        AnonymousClass171 $r1 = new Runnable() {
            public void run() throws  {
                $r3.ShowSoftInput();
            }
        };
        $r3.setImeAction($i0);
        if ($i1 != 1) {
            $z0 = false;
        }
        $r3.setImeCloseOnAction($z0);
        $r3.post($r1);
    }

    public void ShowContacts() throws  {
        AppService.ShowContacts();
    }

    public void ShowEditBox(int $i0, int $i1, byte[] $r1, long $l2, int $i3, int $i4) throws  {
        MainActivity $r4 = AppService.getMainActivity();
        if ($r4 != null) {
            LayoutManager $r5 = $r4.getLayoutMgr();
            if ($r5 != null) {
                final int i = $i4;
                final LayoutManager layoutManager = $r5;
                final int i2 = $i0;
                final int i3 = $i1;
                final byte[] bArr = $r1;
                final EditBoxCallback anonymousClass172 = new EditBoxCallback($l2) {
                    public void CallbackDone(int $i0, String $r1, long $l1) throws  {
                        NativeManager.this.EditBoxCallbackNTV($i0, $r1, $l1);
                    }
                };
                final int i4 = $i3;
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        boolean $z0 = true;
                        byte $b0 = (byte) 0;
                        if ((i & 2) > 0) {
                            $b0 = (byte) 1;
                        }
                        EditBox $r1 = layoutManager.CreateEditBox($b0);
                        $r1.setEditBoxAction(i2);
                        if (i3 != 1) {
                            $z0 = false;
                        }
                        $r1.setEditBoxStayOnAction($z0);
                        $r1.setHint(new String(bArr));
                        $r1.setEditBoxCallback(anonymousClass172);
                        $r1.setEditBoxFlags(i);
                        layoutManager.ShowEditBox(i4, $b0);
                    }
                });
            }
        }
    }

    public void HideEditBox() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.HideEditBox();
                    }
                });
            }
        }
    }

    public void EditBoxCheckTypingLock(final EditText $r1) throws  {
        final AnonymousClass175 $r3 = new Runnable() {
            public void run() throws  {
                ((InputMethodManager) AppService.getAppContext().getSystemService("input_method")).hideSoftInputFromWindow($r1.getWindowToken(), 0);
            }
        };
        PostRunnable(new Runnable() {
            public void run() throws  {
                if (NativeManager.this.EditBoxCheckTypingLockNTV()) {
                    MainActivity $r2 = AppService.getMainActivity();
                    if ($r2 != null) {
                        $r2.runOnUiThread($r3);
                    }
                }
            }
        });
    }

    public void checkTypingLock(final TypingLockListener $r1) throws  {
        PostRunnable(new Runnable() {

            class C12181 implements Runnable {
                C12181() throws  {
                }

                public void run() throws  {
                    $r1.shouldLock();
                }
            }

            public void run() throws  {
                if (NativeManager.this.EditBoxCheckTypingLockNTV()) {
                    AppService.Post(new C12181());
                }
            }
        });
    }

    public void EditBoxCheckTypingLockCb(int aRes) throws  {
    }

    public void HideSoftKeyboard() throws  {
        getMainView().post(new Runnable() {
            public void run() throws  {
                NativeManager.this.getMainView().getMapView().HideSoftInput();
            }
        });
    }

    public void Flush() throws  {
    }

    public MainActivity getMainActivity() throws  {
        return AppService.getMainActivity();
    }

    public MapViewWrapper getMainView() throws  {
        return AppService.getMainView();
    }

    public void PostNativeMessage(int $i0, int $i1) throws  {
        Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt(UIEvent.UI_EVENT_NATIVE), $i0, $i1).sendToTarget();
    }

    public void PostPriorityNativeMessage(int $i0, int $i1) throws  {
        Message $r4 = Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt(UIEvent.UI_PRIORITY_EVENT_NATIVE), $i0, $i1);
        $r4.obj = new NativeMsg(true);
        synchronized (this.mPriorityNativeEventQueue) {
            this.mPriorityNativeEventQueue.add($r4);
        }
        $r4.sendToTarget();
    }

    public void PostNativeMessage(int $i0, IMessageParam $r1) throws  {
        Message $r4 = Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt(UIEvent.UI_EVENT_NATIVE), $i0, 0);
        $r4.obj = $r1;
        $r4.sendToTarget();
    }

    public void PostPriorityNativeMessage(int $i0, IMessageParam $r1) throws  {
        Message $r4 = Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt(UIEvent.UI_PRIORITY_EVENT_NATIVE), $i0, 0);
        $r4.obj = $r1;
        synchronized (this.mPriorityNativeEventQueue) {
            this.mPriorityNativeEventQueue.add($r4);
        }
        $r4.sendToTarget();
    }

    public void PostPriorityEvent(Runnable $r1) throws  {
        if ($r1 != null) {
            synchronized (this.mPriorityEventQueue) {
                this.mPriorityEventQueue.add($r1);
            }
        }
        this.mUIMsgDispatcher.sendEmptyMessage(UIEvent.ToInt(UIEvent.UI_EVENT_EMPTY));
    }

    public void PostUIMessage(UIEvent $r1) throws  {
        Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt($r1)).sendToTarget();
    }

    public void PostUIMessage(UIEvent $r1, int $i0) throws  {
        Message $r3 = Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt($r1));
        $r3.arg1 = $i0;
        $r3.sendToTarget();
    }

    public Message GetUIMessage(UIEvent $r1) throws  {
        return Message.obtain(this.mUIMsgDispatcher, UIEvent.ToInt($r1));
    }

    public int GetScreenWidth() throws  {
        return AppService.getDisplay().getWidth();
    }

    public int GetScreenHeight() throws  {
        return AppService.getDisplay().getHeight();
    }

    public int GetScreenRotation() throws  {
        return AppService.getDisplay().getRotation();
    }

    public static boolean onServiceCreated() throws  {
        return startApp();
    }

    public static boolean onMainResume() throws  {
        return startApp();
    }

    public static boolean onSurfaceReady() throws  {
        return startApp();
    }

    private synchronized void waitCanvasConditions() throws  {
        while (!mCanvasConditions) {
            try {
                wait();
            } catch (Exception $r1) {
                Logger.ee(Logger.TAG, $r1);
            }
        }
        return;
    }

    public void focusCanvas(int $i0) throws  {
        focusCanvasNTV($i0);
    }

    public void focusCanvasUser(int $i0) throws  {
        focusCanvasUserNTV($i0);
    }

    private synchronized void waitOglData() throws  {
        while (!mOglDataAvailable) {
            try {
                wait();
            } catch (Exception $r1) {
                Logger.ee(Logger.TAG, $r1);
            }
        }
        return;
    }

    private void printSW(String $r1) throws  {
        startSW.startDelta($r1, false);
    }

    private void prepareAppStart() throws  {
        ResManager.Prepare();
        this.mAppStartPrepared = true;
        AppService.Post(new Runnable() {
            public void run() throws  {
                NativeManager.startApp();
            }
        });
    }

    public void ClearNotifications() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (NativeManager.this.mNotificationManager == null) {
                    Context $r3 = AppService.getAppContext();
                    if ($r3 != null) {
                        NativeManager.this.mNotificationManager = (NotificationManager) $r3.getSystemService("notification");
                    }
                }
                if (NativeManager.this.mNotificationManager != null) {
                    Alerter.instance().clear(NativeManager.this.mNotificationManager);
                }
            }
        });
    }

    private static boolean startApp() throws  {
        if (IsAppStarted()) {
            return false;
        }
        if (isShuttingDown()) {
            return false;
        }
        MainActivity $r0 = AppService.getMainActivity();
        MapView $r2 = AppService.getMainView().getMapView();
        if (mInstance == null) {
            return false;
        }
        if (!AppService.IsInitialized()) {
            return false;
        }
        if ($r0 == null) {
            return false;
        }
        if (!$r0.IsRunning()) {
            return false;
        }
        if ($r2.IsReady()) {
            return mInstance.isAppStartPrepared();
        } else {
            return false;
        }
    }

    public void displayGeoConfigProgress(boolean $z0) throws  {
        if ($z0) {
        }
        if (Locale.getDefault().getCountry().equalsIgnoreCase("CN")) {
            OpenProgressPopup("初始化，请稍候…");
        } else {
            OpenProgressPopup("Initializing, please wait...");
        }
    }

    public void updateIsGasUpdateable(boolean $z0) throws  {
        this.isGasUpdateable = $z0;
    }

    public void ShutDown() throws  {
        if (this.mTrafficStats != null) {
            this.mTrafficStats.endSession();
            this.mTrafficStats = null;
        }
        logAnalyticsFlush();
        ClearNotifications();
        if (IsNativeThread()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NativeManager.mAppShutDownFlag = true;
                    Log.i(Logger.TAG, "Finalizing the application ...");
                    NativeManager.this.mTimerManager.ShutDown();
                    NativeManager.this.appLayerShutDown();
                }
            });
        } else {
            this.mUIMsgDispatcher.post(this.shutDownEvent);
        }
    }

    public void StopWaze() throws  {
        ShutDown();
    }

    public void SetBackLightOn(final int $i0) throws  {
        Logger.d_("BackLight", "Always on value: " + $i0);
        AppService.Post(new Runnable() {
            public void run() throws  {
                boolean $z0 = true;
                if ($i0 != 1) {
                    $z0 = false;
                }
                ActivityBase.setScreenBacklightOn($z0);
            }
        });
    }

    public void SetVolume(int $i0, int $i1, int $i2) throws  {
        AudioManager $r3 = (AudioManager) getMainActivity().getSystemService("audio");
        $r3.setStreamVolume(3, ($i0 - $i1) * (($r3.getStreamMaxVolume(3) + 0) / ($i2 - $i1)), 0);
    }

    public void SetSysVolume(int $i0) throws  {
        ((AudioManager) getMainActivity().getSystemService("audio")).setStreamVolume(3, $i0, 0);
    }

    public void MinimizeApplication(int $i0) throws  {
        AppService.ShowHomeWindow((long) $i0);
    }

    public void MaximizeApplication() throws  {
        AppService.ShowMainActivityWindow(0);
    }

    public void ShowDilerWindow() throws  {
        AppService.ShowDilerWindow(-1);
    }

    public static void Notify(long $l0) throws  {
        if ($l0 > 0) {
            try {
                Thread.sleep($l0);
            } catch (Exception $r0) {
                Logger.m39e("Error waiting for the manager notification. ", $r0);
                $r0.printStackTrace();
            }
        }
        if (mInstance != null) {
            synchronized (mInstance) {
                mInstance.notifyAll();
            }
        }
    }

    public static void Wait() throws  {
        if (mInstance != null) {
            try {
                synchronized (mInstance) {
                    mInstance.wait();
                }
            } catch (Exception $r0) {
                Logger.ee("Error waiting", $r0);
            }
        }
    }

    public boolean getFbAppNotInstalled() throws  {
        return this.mFbAppNotInstallForce;
    }

    public void setFbAppNotInstalled(boolean $z0) throws  {
        this.mFbAppNotInstallForce = $z0;
    }

    public boolean isFbAppInstalled() throws  {
        if (this.mFbAppNotInstallForce) {
            return false;
        }
        try {
            AppService.getAppContext().getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public void MarketPage() throws  {
        Logger.m36d("Calling market page for Waze");
        final Intent $r1 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.waze"));
        $r1.setFlags(268435456);
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getAppContext().startActivity($r1);
            }
        });
    }

    public void ShowWebView(final byte[] $r1, int $i0, int $i1, int $i2, int $i3, final int $i4) throws  {
        Logger.m36d("URL to load: " + $r1);
        final WazeRect $r2 = new WazeRect($i0, $i1, $i2, $i3);
        AppService.Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.getMainActivity().getLayoutMgr().ShowWebView(new String($r1), $r2, $i4);
            }
        });
    }

    public void ResizeWebView(int $i0, int $i1, int $i2, int $i3) throws  {
        final WazeRect $r1 = new WazeRect($i0, $i1, $i2, $i3);
        AppService.Post(new Runnable() {
            public void run() throws  {
                LayoutManager $r3 = NativeManager.this.getMainActivity().getLayoutMgr();
                if ($r3 != null) {
                    $r3.ResizeWebView($r1);
                }
            }
        });
    }

    public void LoadUrl(byte[] $r1) throws  {
        final String $r3 = new String($r1);
        final MainActivity $r4 = getMainActivity();
        Logger.m36d("URL to load: " + $r3);
        $r4.runOnUiThread(new Runnable() {
            public void run() throws  {
                LayoutManager $r2 = $r4.getLayoutMgr();
                if ($r2 != null) {
                    WzWebView $r3 = $r2.getWebView();
                    if ($r3 != null) {
                        $r3.loadUrl($r3);
                    }
                }
            }
        });
    }

    public void HideWebView() throws  {
        final MainActivity $r2 = getMainActivity();
        $r2.runOnUiThread(new Runnable() {
            public void run() throws  {
                $r2.getLayoutMgr().HideWebView();
            }
        });
    }

    public synchronized void RestoreSystemSettings() throws  {
    }

    public synchronized void SaveAppSettings() throws  {
    }

    public synchronized void RestoreAppSettings() throws  {
    }

    public synchronized void SaveSystemSettings() throws  {
    }

    public void InitNativeManager() throws  {
        LoadNativeLib();
        Iterator $r2 = mInitialLooperQueue.iterator();
        while ($r2.hasNext()) {
            ((Runnable) $r2.next()).run();
        }
        mInitialLooperQueue.clear();
        this.mTimer = new Timer("Waze Timer");
        this.mResManager = ResManager.create();
        int $i0 = VERSION.SDK_INT;
        String $r8 = Build.DISPLAY + " | " + Build.ID + " | " + Build.PRODUCT;
        String $r9 = getRelease() + "-SDK" + VERSION.SDK;
        InitNativeManagerNTV($i0, getDevice(), getModel(), getManufacturer(), $r8, $r9, AppService.getAppContext().getFilesDir().getParent() + "/", AppService.getExternalStoragePath() + "/waze/");
        SetUpgradeRunNTV(ResManager.mUpgradeRun);
        this.mTimerManager = new NativeTimerManager(this);
        startLocation();
        if (AppService.getMainActivity() != null) {
            this.mNavBarManager = new NavBarManager(AppService.getMainActivity().getLayoutMgr());
        }
        SoundRecorder.Create();
        RTAlertsNativeManager.create();
        InstallNativeManager.staticInit();
        this.mSpeechttManager = new SpeechttManagerBase();
        SpeechttManagerBase $r21 = this.mSpeechttManager;
        $r21.InitNativeLayer();
        this.mTtsManager = new TtsManager();
        TtsManager $r22 = this.mTtsManager;
        $r22.InitNativeLayer();
        IsSyncValid = true;
        AddressBookImpl.getInstance().performSync(true, null);
        CPUProfiler.getPerformance();
        MsgBox.InitNativeLayer();
        MyWazeNativeManager.getInstance();
        BottomNotification.getInstance().init();
        DriveToNativeManager.getInstance();
        SettingsNativeManager.getInstance();
        ConfigManager.getInstance();
        MoodManager.getInstance();
        NativeSoundManager.create();
        CarpoolNativeManager.create();
        ShareNativeManager.create();
        MessagesNativeManager.create();
        InboxNativeManager.create();
        BeaconManager.create();
        this.keyguardManager = (KeyguardManager) AppService.getAppContext().getSystemService("keyguard");
        this.mAppInitializedFlag = true;
        Position $r26 = QuestionData.ReadParking(AppService.getAppContext(), null);
        if (!($r26 == null || $r26.longitude == -1.0d)) {
            int $i02 = $r26.longitude * 1000000.0d;
            long j = $i02;
            $i0 = (int) $i02;
            $i02 = $r26.latitude * 1000000.0d;
            j = $i02;
            getInstance().Set_Parking($i0, (int) $i02, $r26.parkingTime, null, true);
        }
        if (ConfigValues.getBoolValue(236)) {
            $r26 = QuestionData.ReadParking(AppService.getAppContext(), ParkingPinsFeedbackDialog.AVAILABLE_TAG);
            if (!($r26 == null || $r26.longitude == -1.0d)) {
                $i02 = $r26.longitude * 1000000.0d;
                j = $i02;
                $i0 = (int) $i02;
                $i02 = $r26.latitude * 1000000.0d;
                j = $i02;
                getInstance().Set_Parking($i0, (int) $i02, $r26.parkingTime, ParkingPinsFeedbackDialog.AVAILABLE_TAG, true);
            }
            $r26 = QuestionData.ReadParking(AppService.getAppContext(), ParkingPinsFeedbackDialog.GPS_TAG);
            if (!($r26 == null || $r26.longitude == -1.0d)) {
                $i02 = $r26.longitude * 1000000.0d;
                j = $i02;
                $i0 = (int) $i02;
                $i02 = $r26.latitude * 1000000.0d;
                j = $i02;
                getInstance().Set_Parking($i0, (int) $i02, $r26.parkingTime, ParkingPinsFeedbackDialog.GPS_TAG, true);
            }
        }
        shouldDisplayGasSettings(new GasSettingsListener() {
            public void onComplete(boolean $z0) throws  {
                NativeManager.this.shouldDisplayGasSettings = $z0;
            }
        });
    }

    public void clearParking() throws  {
        QuestionData.ClearParking(AppService.getAppContext());
    }

    private void waitResPrepare() throws  {
        try {
            this.mResPrepareThread.join();
        } catch (InterruptedException $r1) {
            Log.e(Logger.TAG, "Error joining the resources thread");
            $r1.printStackTrace();
        }
    }

    public static void LoadNativeLib() throws  {
        try {
            System.loadLibrary("sqlite");
            Log.i(Logger.TAG, "sqlite Library is loaded");
            System.loadLibrary("waze");
            Log.i(Logger.TAG, "Waze Library is loaded");
        } catch (UnsatisfiedLinkError $r0) {
            Log.e(Logger.TAG, "Error: Could not load library  - exiting! " + $r0.getMessage());
            $r0.printStackTrace();
            System.exit(0);
        }
    }

    public static void registerOnAppStartedEvent(RunnableExecutor $r0) throws  {
        synchronized (mAppStartedEvents) {
            mAppStartedEvents.add($r0);
        }
    }

    public static String getManufacturer() throws  {
        if (VERSION.SDK_INT > 3) {
            return CompatabilityWrapper.getManufacturer();
        }
        return new String("not available");
    }

    public static String getModel() throws  {
        String $r0 = Build.MODEL;
        $r0.replaceAll("|", "");
        return $r0;
    }

    public static String getDevice() throws  {
        String $r0 = Build.DEVICE;
        $r0.replaceAll("|", "");
        return $r0;
    }

    public static String getRelease() throws  {
        return VERSION.RELEASE;
    }

    private void postOnAppStartedEvents() throws  {
        while (mAppStartedEvents.size() > 0) {
            ((Runnable) mAppStartedEvents.remove(0)).run();
        }
    }

    public void startLocation() throws  {
        if (this.mLocationListner == null) {
            this.mLocationListner = LocationFactory.getInstance();
        }
        AnonymousClass191 $r1 = new Runnable() {
            public void run() throws  {
                NativeManager.this.mLocationListner.start();
            }
        };
        if (IsNativeThread()) {
            $r1.run();
        } else {
            Post($r1);
        }
    }

    public void stopLocation() throws  {
        AnonymousClass192 $r1 = new Runnable() {
            public void run() throws  {
                NativeManager.this.mLocationListner.stop();
            }
        };
        if (IsNativeThread()) {
            $r1.run();
        } else {
            Post($r1);
        }
    }

    public void setGpsSourceName(final String $r1) throws  {
        Log.d(Logger.TAG, "GPS Source name: " + $r1);
        AnonymousClass193 $r2 = new Runnable() {
            public void run() throws  {
                NativeManager.this.setGpsSourceNameNTV($r1);
            }
        };
        if (IsNativeThread()) {
            $r2.run();
        } else {
            Post($r2);
        }
    }

    private NativeManager() throws  {
    }

    private void appLayerShutDown() throws  {
        NativeSoundManager.getInstance().shutdown();
        ShutdownManager.start();
        Logger.m43w("NM: appLayerShutDown: Shutting down AddressBook");
        Log.w("WAZE SHUTDOWN", "NM: appLayerShutDown: Shutting down AddressBook");
        AddressBookImpl.stopSyncThread();
        if (this.mLocationListner != null) {
            this.mLocationListner.stop();
        }
        AddressBookImpl.getInstance().cancelSync();
        RestoreSystemSettings();
        ActivityBase.finishAll();
        AppService.ShutDown();
        if (BoundService.mIsRunning && BoundService.getInstance() != null) {
            System.exit(0);
        }
    }

    private void InitMemoryProfiler() throws  {
        final AnonymousClass194 $r2 = new Runnable() {
            public void run() throws  {
                Logger.ww(new String("WAZE MEMORY PROFILER. Global heap used [Kb]: " + (Debug.getGlobalAllocSize() / 1000) + " Native heap. Used [Kb]: " + (Debug.getNativeHeapAllocatedSize() / 1000) + ". Free [Kb]: " + (Debug.getNativeHeapFreeSize() / 1000) + ". Total [Kb]: " + (Debug.getNativeHeapSize() / 1000) + ". External [Kb]: " + (Debug.getThreadExternalAllocSize() / 1000)));
            }
        };
        mInstance.getTimer().scheduleAtFixedRate(new TimerTask() {
            public void run() throws  {
                NativeManager.this.PostRunnable($r2);
            }
        }, 0, MEMORY_PROFILER_PERIOD);
    }

    private void InitTemperatureProfiler() throws  {
        SensorManager $r4 = (SensorManager) getMainActivity().getSystemService("sensor");
        final Sensor $r5 = $r4.getDefaultSensor(7);
        this.mLastTemperatureSampleTime = 0;
        if ($r5 != null) {
            this.mTemperatureEventListener = new SensorEventListener() {
                public final String mSensorName = $r5.getName();

                public void onSensorChanged(SensorEvent $r1) throws  {
                    this = this;
                    if (NativeManager.this.mAppInitializedFlag) {
                        long $l0 = SystemClock.elapsedRealtime();
                        this = this;
                        if ($l0 - NativeManager.this.mLastTemperatureSampleTime > NativeManager.TEMPERATURE_PROFILER_PERIOD) {
                            Logger.m43w("WAZE TEMPERATURE PROFILER. Current temperature: " + $r1.values[0] + ". Sensor: " + this.mSensorName);
                            NativeManager.this.mLastTemperatureSampleTime = $l0;
                        }
                    }
                }

                public void onAccuracyChanged(Sensor arg0, int arg1) throws  {
                }
            };
            $r4.registerListener(this.mTemperatureEventListener, $r5, 3, this.mUIMsgDispatcher);
        }
    }

    private void CloseTemperatureProfiler() throws  {
        if (this.mTemperatureEventListener != null) {
            ((SensorManager) getMainActivity().getSystemService("sensor")).unregisterListener(this.mTemperatureEventListener);
        }
    }

    private boolean isConnectedThroughWifi() throws  {
        return ((ConnectivityManager) AppService.getAppContext().getSystemService("connectivity")).getNetworkInfo(1).isConnected();
    }

    private String getInstallationUUID() throws  {
        return AppUUID.getInstallationUUID(AppService.getAppContext());
    }

    public String getPushInstallationUUID() throws  {
        return Secure.getString(AppService.getAppContext().getContentResolver(), "android_id");
    }

    public boolean IsSDKBound() throws  {
        return BoundService.getTrasnportationAppData(AppService.getAppContext()) != null;
    }

    public NavBarManager getNavBarManager() throws  {
        if (this.mNavBarManager == null && AppService.getMainActivity() != null) {
            this.mNavBarManager = new NavBarManager(AppService.getMainActivity().getLayoutMgr());
        }
        return this.mNavBarManager;
    }

    public String[] GetProblematicGPUNames() throws  {
        return new String[]{"PowerVR SGX 530", "VideoCore IV HW", "Mali-T604", "Mali-T628", "Mali-T624", "Mali-T760"};
    }

    public void crashJava() throws  {
        Logger.m43w("About to crash Waze Java on purpose");
        new ArrayList().get(5);
    }

    public void SpotifyConnect() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Logger.m43w("SpotifyConnect");
                if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled()) {
                    SpotifyManager.getInstance();
                    if (SpotifyManager.appInstalled()) {
                        SpotifyManager.getInstance().authorize();
                    }
                }
            }
        });
    }

    public void SpotifyPlayUri(final String $r1) throws  {
        Logger.m43w("SpotifyPlayTrack uri=" + $r1);
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled()) {
            SpotifyManager.getInstance();
            if (SpotifyManager.appInstalled()) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        SpotifyManager.getInstance().playUri($r1);
                    }
                });
                return;
            }
        }
        StringBuilder $r2 = new StringBuilder().append("SpotifyPlayTrack uri=").append($r1).append("Ignored (").append(SpotifyManager.getInstance().featureEnabled()).append(",").append(SpotifyManager.getInstance().userEnabled()).append(",");
        SpotifyManager.getInstance();
        Logger.m43w($r2.append(SpotifyManager.appInstalled()).append(")").toString());
    }

    public boolean SpotifyInstalled() throws  {
        try {
            AppService.getAppContext().getPackageManager().getApplicationInfo(SpotifyManager.PACKAGE_ID, 0);
            return true;
        } catch (NameNotFoundException e) {
            try {
                AppService.getAppContext().getPackageManager().getApplicationInfo(SpotifyManager.PACKAGE_ID_DEBUD, 0);
                return true;
            } catch (NameNotFoundException e2) {
                return false;
            }
        }
    }

    public void SpotifyPlay() throws  {
        Logger.m36d("SpotifyPlay");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().play();
                }
            });
        } else {
            Logger.m43w("SpotifyPlay Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void SpotifyPause() throws  {
        Logger.m36d("SpotifyPause");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().pause();
                }
            });
        } else {
            Logger.m43w("SpotifyPause Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void SpotifyPlayNext() throws  {
        Logger.m36d("SpotifyPlayNext");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().playNext();
                }
            });
        } else {
            Logger.m43w("SpotifyPlayNext Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void SpotifyPlayPrevious() throws  {
        Logger.m36d("SpotifyPlayPrevious");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().playPrevious();
                }
            });
        } else {
            Logger.m43w("SpotifyPlayPrevious Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void SpotifyOpenApp() throws  {
        Logger.m36d("SpotifyOpenApp");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().openApp();
                }
            });
        } else {
            Logger.m43w("SpotifyOpenApp Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void SpotifySaveToLibrary() throws  {
        Logger.m36d("SpotifySaveToLibrary");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().save();
                }
            });
        } else {
            Logger.m43w("SpotifySaveToLibrary Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void SpotifyNextPlaylist() throws  {
        Logger.m36d("SpotifyNextPlaylist");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().playNextPlayList();
                }
            });
        } else {
            Logger.m43w("SpotifyNextPlaylist Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void SpotifyPreviousPlaylist() throws  {
        Logger.m36d("SpotifyPreviousPlaylist");
        if (SpotifyManager.getInstance().featureEnabled() && SpotifyManager.getInstance().userEnabled() && SpotifyManager.getInstance().isConnected()) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpotifyManager.getInstance().playPreviousPlayList();
                }
            });
        } else {
            Logger.m43w("SpotifyPreviousPlaylist Ignored (" + SpotifyManager.getInstance().featureEnabled() + "," + SpotifyManager.getInstance().userEnabled() + "," + SpotifyManager.getInstance().isConnected() + ")");
        }
    }

    public void addPlaceToRecent(final String $r1, final String $r2, final String $r3, final String $r4, final String $r5, final String $r6) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.addPlaceToRecentNTV($r1, $r2, $r3, $r4, $r5, $r6);
            }
        });
    }

    public void CloseDarkView() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.CloseDarkViewNTV();
            }
        });
    }

    public void OpenBatterySavingSettings() throws  {
        if (AppService.getActiveActivity() != null) {
            AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsPowerSaving.class));
        }
    }

    public void OpenCustomPromptsSettings() throws  {
        if (AppService.getActiveActivity() != null) {
            AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsCustomPrompts.class));
        }
    }

    public void OpenSoundSettings() throws  {
        if (AppService.getActiveActivity() != null) {
            AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsSoundActivity.class));
        }
    }

    public void randomUserMsg() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.RandomUserMsgNTV();
            }
        });
    }

    public void asrCancel(final String $r1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.asrCancelNTV($r1);
            }
        });
    }

    public void StopFollow() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.StopFollowNTV();
            }
        });
    }

    public void BeepClosed(final int $i0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.BeepClosedNTV($i0);
            }
        });
    }

    public void openEditTextDialog(final int $i0, final long $l1, final long $l2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    $r1.startEditTextDialog($i0, $l1, $l2);
                }
            }
        });
    }

    public void asrListenCallback(final long $l0, final Object[] $r1, final float[] $r2) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.asrListenCallbackNTV($l0, $r1, $r2);
            }
        });
    }

    public void editTextDialogCallback(final String $r1, final long $l0, final long $l1) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.editTextDialogCallbackNTV($r1, $l0, $l1);
            }
        });
    }

    public void DeleteAccount() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.DeleteAccountNTV();
            }
        });
    }

    public void LogOutAccount() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.LogOutNTV();
            }
        });
    }

    public void shouldDisplayGasSettings(final GasSettingsListener $r1) throws  {
        Post(new RunnableUICallback() {
            private boolean value;

            public void event() throws  {
                this.value = NativeManager.this.shouldDisplayGasSettingsNTV();
            }

            public void callback() throws  {
                $r1.onComplete(this.value);
            }
        });
    }

    public boolean isGasUpdateable() throws  {
        return this.isGasUpdateable;
    }

    public boolean isAllowAdTracking() throws  {
        return this.mAdsData == null ? false : this.mAdsData.bIsTrackingAllowed;
    }

    public String getAdsId() throws  {
        return this.mAdsData == null ? null : this.mAdsData.token;
    }

    public boolean shouldDisplayGasSettings() throws  {
        return this.shouldDisplayGasSettings;
    }

    public boolean ShouldDisplayGasInSettings() throws  {
        return shouldDisplayGasSettingsNTV();
    }

    public void getDisplayName(final DisplayNameListener $r1) throws  {
        Post(new RunnableUICallback() {
            private String name;

            public void event() throws  {
                this.name = NativeManager.this.getDisplayNameNTV();
            }

            public void callback() throws  {
                $r1.onComplete(this.name);
            }
        });
    }

    public void GetTitle(final String $r1, final GetTitleListener $r2) throws  {
        Post(new RunnableUICallback() {
            private String title;

            public void event() throws  {
                this.title = NativeManager.this.GetTitleNTV($r1);
            }

            public void callback() throws  {
                $r2.onGetTitle(this.title);
            }
        });
    }

    public void onSkinChanged() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr() != null) {
                    AppService.getMainActivity().getLayoutMgr().onSkinChanged();
                }
            }
        });
    }

    public void mapProblemsPave() throws  {
        final boolean $z0 = MyWazeNativeManager.getInstance().isGuestUserNTV();
        final boolean $z1 = MyWazeNativeManager.getInstance().FoursquareEnabledNTV();
        final boolean $z2 = getInstance().isClosureEnabledNTV();
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.OpenPave($z0, $z1, $z2);
                    }
                }
            }
        });
    }

    public void mapProblemsReport() throws  {
        final boolean $z0 = MyWazeNativeManager.getInstance().isGuestUserNTV();
        final boolean $z1 = MyWazeNativeManager.getInstance().FoursquareEnabledNTV();
        final boolean $z2 = getInstance().isClosureEnabledNTV();
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.showMapProblemReport($z0, $z1, $z2);
                    }
                }
            }
        });
    }

    public void reportMenuAllReports() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    $r2.startActivityForResult(new Intent($r2, RTAlertsMenu.class), MainActivity.RTALERTS_REQUEST_CODE);
                }
            }
        });
    }

    public void mainMenuShowMyWaze() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    MyWazeNativeManager.getInstance().launchMyWaze($r1);
                }
            }
        });
    }

    public void openSearchGasScreen() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    Intent $r1 = new Intent($r2, SearchResultsActivity.class);
                    $r1.putExtra(PublicMacros.SEARCH_CATEGORY, "GAS_STATION");
                    $r1.putExtra(PublicMacros.SEARCH_MODE, 2);
                    $r2.startActivityForResult($r1, 1);
                }
            }
        });
    }

    public void OpenInbox() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    AppService.getAppContext().startActivity(new Intent($r2, InboxActivity.class));
                }
            }
        });
    }

    public void OpenNavigateActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                final ActivityBase $r1 = AppService.getMainActivity();
                if ($r1 == null) {
                    return;
                }
                if (AppService.getActiveActivity() != $r1) {
                    NativeManager.this.show_root();
                    $r1.postDelayed(new Runnable() {
                        public void run() throws  {
                            $r1.startNavigateActivity();
                        }
                    }, 1000);
                    return;
                }
                $r1.startNavigateActivity();
            }
        });
    }

    public void OpenAddPlace() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r3 = AppService.getActiveActivity();
                if ($r3 != null) {
                    Intent $r1 = new Intent($r3, AddPlaceFlowActivity.class);
                    if (!(MainActivity.sQuestionID == null || MainActivity.sQuestionID.isEmpty())) {
                        $r1.putExtra("QuestionID", MainActivity.sQuestionID);
                        MainActivity.sQuestionID = null;
                    }
                    Intent $r2 = new Intent($r3, RequestPermissionActivity.class);
                    $r2.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.CAMERA"});
                    $r2.putExtra(RequestPermissionActivity.INT_ON_GRANTED, $r1);
                    $r3.startActivityForResult($r2, 0);
                }
            }
        });
    }

    public void OpenQuickMenuSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getActiveActivity() != null) {
                    AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsMainActivity.class));
                }
            }
        });
    }

    public void OpenMainMenu() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr() != null) {
                    AppService.getMainActivity().getLayoutMgr().openMainMenu();
                }
            }
        });
    }

    public void openNavList() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().startNavigateActivity();
                }
            }
        });
    }

    public void openSendDrive() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if (NativeManager.this.isNavigatingNTV()) {
                    ShareUtility.shareLocationOrDrive($r2, 16);
                } else {
                    $r2.startActivity(new Intent($r2, ShareHelpActivity.class));
                }
            }
        });
    }

    public void openSendLocation() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ShareUtility.shareLocationOrDrive(AppService.getMainActivity(), 32);
            }
        });
    }

    public void openSendCurrentLocation() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ShareUtility.shareLocationOrDrive(AppService.getMainActivity(), 64);
            }
        });
    }

    public void openSendHome() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ShareUtility.shareLocationOrDrive(AppService.getMainActivity(), 128);
            }
        });
    }

    public void openSendWork() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ShareUtility.shareLocationOrDrive(AppService.getMainActivity(), 256);
            }
        });
    }

    public void openAddFriends() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    $r2.startActivity(new Intent($r2, AddFriendsActivity.class));
                }
            }
        });
    }

    public void openAddHome() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    Intent $r1 = new Intent($r2, AddHomeWorkActivity.class);
                    $r1.putExtra(PublicMacros.ADDRESS_TYPE, 2);
                    $r2.startActivity($r1);
                }
            }
        });
    }

    public void openAddWork() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    Intent $r1 = new Intent($r2, AddHomeWorkActivity.class);
                    $r1.putExtra(PublicMacros.ADDRESS_TYPE, 4);
                    $r2.startActivity($r1);
                }
            }
        });
    }

    public void openAddFav() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    Intent $r1 = new Intent($r2, AddFavoriteActivity.class);
                    $r1.putExtra(PublicMacros.ADDRESS_TYPE, 6);
                    $r2.startActivity($r1);
                }
            }
        });
    }

    public void openSoundActions() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().openSoundActions();
                }
            }
        });
    }

    public void onReachedDestination() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().getLayoutMgr().displayEndOfDriveReminder();
                }
            }
        });
    }

    public void OpenAccountAndLogin() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
                    AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), TempUserProfileActivity.class), 0);
                    return;
                }
                AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), MyProfileActivity.class), 0);
            }
        });
    }

    public void OpenCarpoolSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
                        Intent $r1 = new Intent($r2, SettingsCarpoolActivity.class);
                        $r1.putExtra("INT_VIEW_MODE", 2);
                        $r2.startActivityForResult($r1, 0);
                        return;
                    }
                    CarpoolOnboardingManager.getInstance().setIsCalledFromPush(true);
                    $r2.openCarpoolSideMenu();
                }
            }
        });
    }

    public void OpenCarSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    $r2.startActivity(new Intent($r2, CarsActivity.class));
                }
            }
        });
    }

    public void OpenCarpoolOfferRide() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
                        Intent $r1 = new Intent($r2, CarpoolOfferDriveActivity.class);
                        $r1.putExtra("INT_VIEW_MODE", 2);
                        $r2.startActivityForResult($r1, 0);
                        return;
                    }
                    CarpoolOnboardingManager.getInstance().setIsCalledFromPush(true);
                    $r2.openCarpoolSideMenu();
                }
            }
        });
    }

    public void OpenJoinCarpool() throws  {
        CarpoolNativeManager.getInstance().gotoJoin(true);
    }

    public void StartCarpoolOnboarding() throws  {
        CarpoolNativeManager.getInstance().startOnboarding();
    }

    public void OpenCarpoolRight() throws  {
        getInstance().openCarpoolRightSideMenu();
    }

    public void OpenJoinCarpoolSkipMailCheck() throws  {
        CarpoolNativeManager.getInstance().gotoJoinSkipMail(true);
    }

    public void OpenCarProfile() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV() && CarpoolNativeManager.getInstance().getCarpoolProfileNTV().didFinishOnboarding()) {
                    AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), EditCarActivity.class), 0);
                    return;
                }
                NativeManager.this.OpenJoinCarpool();
            }
        });
    }

    public void OpenCarpoolPayments() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
                    AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsCarpoolPaymentsActivity.class), 0);
                    return;
                }
                NativeManager.this.openCarpoolRightSideMenu();
            }
        });
    }

    private void openCarpoolRightSideMenu() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 != null) {
            $r1.openCarpoolRightSideMenu();
        } else {
            Logger.m38e("openCarpoolRightSideMenu: Main activity is null, cannot open");
        }
    }

    public void OpenCarpoolPaymentsDetails() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
                    Intent $r1 = new Intent(AppService.getMainActivity(), SettingsCarpoolPaymentsActivity.class);
                    $r1.putExtra("DETAILS", true);
                    AppService.getMainActivity().startActivityForResult($r1, 0);
                    return;
                }
                NativeManager.this.openCarpoolRightSideMenu();
            }
        });
    }

    public void OpenCarpoolEmailDetails() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
                    AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsCarpoolWorkActivity.class), 0);
                    return;
                }
                NativeManager.this.openCarpoolRightSideMenu();
            }
        });
    }

    public void OpenCarpoolProfile() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
                    AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), CarpoolDriverProfileActivity.class), 0);
                    return;
                }
                CarpoolOnboardingManager.getInstance().setIsCalledFromPush(true);
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().openCarpoolSideMenu();
                }
            }
        });
    }

    public void OpenCommuteModel() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                IntentManager.OpenCommuteModelActivity(AppService.getMainActivity());
            }
        });
    }

    public void OpenAvaliableSeats() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r2 = AppService.getActiveActivity();
                if ($r2 != null) {
                    $r2.startActivity(new Intent($r2, SettingsCarpoolSeatsActivity.class));
                }
            }
        });
    }

    public void OpenMultipaxIntro() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r1 = AppService.getActiveActivity();
                if ($r1 != null) {
                    CarpoolRiderJoinRequest.showMultipxIntroPopup($r1, null);
                }
            }
        });
    }

    public void OpenEnableLocationHistory() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
                    Intent $r2 = new Intent(AppService.getMainActivity(), CarpoolRidesActivity.class);
                    Intent $r1 = new Intent(AppService.getMainActivity(), MissingPermissionsActivity.class);
                    $r1.putExtra("MPType", MPType.MissingLocationHistory);
                    $r1.putExtra("MPNext", $r2);
                    AppService.getMainActivity().startActivityForResult($r1, 0);
                    return;
                }
                NativeManager.this.openCarpoolRightSideMenu();
            }
        });
    }

    public void OpenClosureFromQuestion() throws  {
        final boolean $z0 = MyWazeNativeManager.getInstance().isGuestUserNTV();
        final boolean $z1 = MyWazeNativeManager.getInstance().FoursquareEnabledNTV();
        final boolean $z2 = getInstance().isClosureEnabledNTV();
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr() != null) {
                    AppService.getMainActivity().getLayoutMgr().OpenClosure($z0, $z1, $z2, true);
                }
            }
        });
    }

    public void CopyFileToSdcard(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                try {
                    File $r2 = new File(Environment.getExternalStorageDirectory().getPath() + "/waze/");
                    if (!$r2.exists()) {
                        $r2.mkdirs();
                    }
                    FileInputStream $r3 = new FileInputStream(AppService.getAppContext().getFilesDir().getParent() + "/" + $r1);
                    try {
                        FileOutputStream $r4 = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/waze/" + $r1);
                        byte[] $r1 = new byte[1024];
                        while (true) {
                            try {
                                int $i0 = $r3.read($r1);
                                if ($i0 != -1) {
                                    $r4.write($r1, 0, $i0);
                                } else {
                                    $r3.close();
                                    try {
                                        $r4.flush();
                                        $r4.close();
                                        return;
                                    } catch (FileNotFoundException e) {
                                        return;
                                    } catch (Exception e2) {
                                        return;
                                    }
                                }
                            } catch (FileNotFoundException e3) {
                                return;
                            } catch (Exception e4) {
                                return;
                            }
                        }
                    } catch (FileNotFoundException e5) {
                    } catch (Exception e6) {
                    }
                } catch (FileNotFoundException e7) {
                } catch (Exception e8) {
                }
            }
        });
    }

    public void CopySdcardToInternal(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                try {
                    File $r2 = new File(Environment.getExternalStorageDirectory().getPath() + "/waze/");
                    if (!$r2.exists()) {
                        $r2.mkdirs();
                    }
                    FileInputStream $r3 = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/waze/" + $r1);
                    try {
                        FileOutputStream $r4 = new FileOutputStream(AppService.getAppContext().getFilesDir().getParent() + "/" + $r1);
                        byte[] $r1 = new byte[1024];
                        while (true) {
                            try {
                                int $i0 = $r3.read($r1);
                                if ($i0 != -1) {
                                    $r4.write($r1, 0, $i0);
                                } else {
                                    $r3.close();
                                    try {
                                        $r4.flush();
                                        $r4.close();
                                        return;
                                    } catch (FileNotFoundException e) {
                                        return;
                                    } catch (Exception e2) {
                                        return;
                                    }
                                }
                            } catch (FileNotFoundException e3) {
                                return;
                            } catch (Exception e4) {
                                return;
                            }
                        }
                    } catch (FileNotFoundException e5) {
                    } catch (Exception e6) {
                    }
                } catch (FileNotFoundException e7) {
                } catch (Exception e8) {
                }
            }
        });
    }

    public void OpenInviteSignUp(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                NativeManager.mInviteId = $r1;
                Intent $r1 = new Intent(AppService.getMainActivity(), PhoneRegisterActivity.class);
                $r1.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
                $r1.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
                AppService.getMainActivity().startActivityForResult($r1, 0);
            }
        });
    }

    public void OpenFriendsDriving(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent(AppService.getActiveActivity(), ShareDrivingFriendsActivity.class);
                $r1.putExtra("meeting", $r1);
                AppService.getActiveActivity().startActivityForResult($r1, 0);
            }
        });
    }

    public void OpenAddFriends() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getMainActivity(), AddFriendsActivity.class), 0);
            }
        });
    }

    public void OpenSettingsSoundActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsNavigationGuidanceActivity.class), 0);
            }
        });
    }

    public void OpenSettingsGeneralActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsGeneralActivity.class), 0);
            }
        });
    }

    public void OpenSettingsSpotifyActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsSpotifyActivity.class), 0);
            }
        });
    }

    public void OpenSettingsNotificationActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), SettingsNotificationActivity.class), 0);
            }
        });
    }

    public void OpenSettingsReminderActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), SettingsEndOfDrive.class), 0);
            }
        });
    }

    public void OpenSettingsLangActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsLanguageActivity.class), 0);
            }
        });
    }

    public void OpenHomeWorkSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), AddHomeWorkActivity.class), 0);
            }
        });
    }

    public void OpenAlertSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsAlertsOnRoute.class), 0);
            }
        });
    }

    public void OpenHomeWorkCarpool() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent(AppService.getMainActivity(), AddHomeWorkActivity.class);
                $r1.putExtra("carpool", true);
                AppService.getMainActivity().startActivityForResult($r1, 0);
            }
        });
    }

    public void OpenLicensePlateSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsLicensePlateActivity.class), 0);
            }
        });
    }

    public void showAdvancedSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), SettingsAdvancedActivity.class), 0);
            }
        });
    }

    public void showNavigationSettings() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), SettingsNavigationActivity.class), 0);
            }
        });
    }

    public void updateNavResultPopup(final int $i0, final String $r1, final String $r2, final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Log.d(Logger.TAG, "in updateNavResultPopup eta=" + $i0 + " dist=" + $r1 + " unit=" + $r2);
                MainActivity $r3 = AppService.getMainActivity();
                if ($r3 != null) {
                    LayoutManager $r4 = $r3.getLayoutMgr();
                    if ($r4 != null) {
                        $r4.updateNavResultPopup($i0, $r1, $r2, $z0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot Call updateNavResultPopup. Main activity is not available");
            }
        });
    }

    public void navResSetWeather(int icon, String temprature) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null && $r1.getLayoutMgr() == null) {
                }
            }
        });
    }

    public void navResOpenEtaScreen(final boolean $z0, final boolean $z1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.navResOpenEtaScreen($z0, $z1);
                    }
                }
            }
        });
    }

    public void navResWaypointDone(String title) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null && $r1.getLayoutMgr() == null) {
                }
            }
        });
    }

    public void navResPause() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.navResPause();
                    }
                }
            }
        });
    }

    public void navResResume() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.navResResume();
                    }
                }
            }
        });
    }

    public void navigationStateChanged(boolean $z0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putBoolean("is_navigating", $z0);
        this.handlers.sendUpdateMessage(UH_NAVIGATION_STATE_CHANGED, $r1);
    }

    public void venueSearchResponse(VenueData[] $r1) throws  {
        Bundle $r2 = r4;
        Bundle r4 = new Bundle();
        $r2.putParcelableArray("venue_data", (Parcelable[]) $r1);
        this.handlers.sendUpdateMessage(UH_SEARCH_VENUES, $r2);
    }

    public void showVenueAddressPreview(VenueData $r1) throws  {
        PlaceData $r2 = new PlaceData($r1.id, $r1.name, $r1.street, $r1.city, null);
        $r2.mLocX = $r1.longitude;
        $r2.mLocY = $r1.latitude;
        AddressItem $r5 = new AddressItem($r2);
        $r5.setTitle($r1.getAddressString());
        Intent $r6 = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
        $r6.putExtra(PublicMacros.ADDRESS_ITEM, $r5);
        AppService.getActiveActivity().startActivity($r6);
    }

    public void VenueStatusResponse(int $i0, int $i1, String $r1) throws  {
        Bundle $r2 = new Bundle();
        $r2.putInt("res", $i0);
        $r2.putInt("points", $i1);
        $r2.putString("id", $r1);
        this.handlers.sendUpdateMessage(UH_VENUE_STATUS, $r2);
    }

    public void VenueAddImageResult(boolean $z0, String $r1, String $r2, String $r3, String $r4) throws  {
        Bundle $r5 = new Bundle();
        $r5.putBoolean("res", $z0);
        $r5.putString("path", $r1);
        $r5.putString("id", $r2);
        $r5.putString("image_url", $r3);
        $r5.putString("image_thumbnail_url", $r4);
        this.handlers.sendUpdateMessage(UH_VENUE_ADD_IMAGE_RESULT, $r5);
    }

    public void logAnalytics(final String $r1, final boolean $z0, final boolean $z1) throws  {
        analyticsDebug($r1);
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.logAnalyticsNTV($r1, $z0, $z1);
            }
        });
    }

    public void logAnalyticsFlush() throws  {
        analyticsDebug("FLUSH");
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.logAnalyticsFlushNTV();
            }
        });
    }

    public void logAnalytics(final String $r1, final String $r2, final int $i0) throws  {
        analyticsDebug($r1);
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.logAnalyticsIntNTV($r1, $r2, $i0);
            }
        });
    }

    public void logAnalytics(final String $r1, final String $r2, final String $r3) throws  {
        analyticsDebug($r1);
        if (IsNativeThread()) {
            logAnalyticsStrNTV($r1, $r2, $r3);
        } else {
            Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.logAnalyticsStrNTV($r1, $r2, $r3);
                }
            });
        }
    }

    public void SetNetInfo(NetworkInfo $r1) throws  {
        this.m_NetworkInfo = $r1;
    }

    public void onLoginDone() throws  {
        Logger.m41i("NativeManager:onLoginDone: notifying");
        this.handlers.sendUpdateMessage(UH_LOGIN_DONE, null);
        updateCalendarEvents();
        RealTimeManager.getInstance().sendStoredOfflineStatCommands(AppService.getAppContext());
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LOGIN_COMPLETE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_UP_TIME, AppService.timeSinceCreated()).send();
    }

    public void SendNetInfo() throws  {
        LocationFactory.getInstance().onLogin();
        if (this.m_NetworkInfo != null) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (!NativeManager.this.m_NetworkInfo.isConnected()) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "NA||");
                    } else if (NativeManager.this.m_NetworkInfo.getType() == 0) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "CELL|" + AppService.getAppResources().getConfiguration().mcc + "|" + AppService.getAppResources().getConfiguration().mnc);
                    } else if (NativeManager.this.m_NetworkInfo.getType() == 1) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "WIFI||");
                    } else if (NativeManager.this.m_NetworkInfo.getType() == 6) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "WIMAX||");
                    }
                }
            });
        }
    }

    public void SignUplogAnalytics(final String $r1, final String $r2, final String $r3, final boolean $z0) throws  {
        analyticsDebug($r1);
        if (IsNativeThread()) {
            SignUplogAnalyticsStrNTV($r1, $r2, $r3, $z0);
        } else {
            Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.SignUplogAnalyticsStrNTV($r1, $r2, $r3, $z0);
                }
            });
        }
    }

    public void onAppForeground() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.onAppForegroundNTV();
            }
        });
    }

    public void onAppActive() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.onAppActiveNTV();
            }
        });
    }

    public static int displayDpi() throws  {
        return NativeCanvasRenderer.displayDpi();
    }

    public void AllowSendmails(final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.SetAllowSendMailNTV($z0);
            }
        });
    }

    public void onAppBackground() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.onAppBackgroundNTV();
            }
        });
    }

    private void analyticsDebug(String event) throws  {
    }

    public boolean getIsAllowTripDialog() throws  {
        return this.isAllowTripDialog;
    }

    public void setIsAllowTripDialog(boolean $z0) throws  {
        this.isAllowTripDialog = $z0;
    }

    public void showTooltip(final int $i0, final int $i1, final FriendUserData $r1, final long $l2, final int $i3) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                LayoutManager $r2 = NativeManager.this.getLayoutManager();
                if ($r2 != null) {
                    switch ($i0) {
                        case 0:
                            $r2.getTooltipManager().showToolTip(0, $i1, $r1, $l2, $i3);
                            return;
                        case 1:
                            $r2.getTooltipManager().showToolTip(1, $i1, $r1, $l2, $i3);
                            return;
                        case 2:
                            $r2.getTooltipManager().showToolTip(2, $i1, $r1, $l2, $i3);
                            return;
                        case 3:
                            $r2.getTooltipManager().showToolTip(3, $i1, $r1, $l2, $i3);
                            return;
                        case 4:
                            $r2.getTooltipManager().showToolTip(4, $i1, $r1, $l2, $i3);
                            return;
                        case 5:
                            $r2.getTooltipManager().showToolTip(5, $i1, $r1, $l2, $i3);
                            return;
                        case 6:
                            $r2.getTooltipManager().showToolTip(6, $i1, $r1, $l2, $i3);
                            return;
                        case 7:
                            $r2.getTooltipManager().showToolTip(7, $i1, $r1, $l2, $i3);
                            return;
                        case 8:
                            NativeManager.this.delayTipDisplay(8, $i1, $r1, $l2, $i3);
                            return;
                        case 9:
                            $r2.getTooltipManager().showToolTip(9, $i1, $r1, $l2, $i3);
                            return;
                        case 10:
                            $r2.getTooltipManager().showToolTip(10, $i1, $r1, $l2, $i3);
                            return;
                        case 11:
                            NativeManager.this.delayTipDisplay(11, $i1, $r1, $l2, $i3);
                            return;
                        default:
                            return;
                    }
                }
            }
        });
    }

    private void delayTipDisplay(int $i0, int $i1, FriendUserData $r1, long $l2, int $i3) throws  {
        MapViewWrapper $r2 = AppService.getActiveMapViewWrapper();
        LayoutManager $r3 = getLayoutManager();
        if ($r3 != null) {
            if ($r2 == null || !$r2.hasYouAreHerePopUpInstance()) {
                $r3.getTooltipManager().showToolTip($i0, $i1, $r1, $l2, $i3);
                return;
            }
            final int i = $i0;
            final int i2 = $i1;
            final FriendUserData friendUserData = $r1;
            final long j = $l2;
            final int i3 = $i3;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.delayTipDisplay(i, i2, friendUserData, j, i3);
                }
            }, 2000);
        }
    }

    private LayoutManager getLayoutManager() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 == null) {
            return null;
        }
        LayoutManager $r2 = $r1.getLayoutMgr();
        return $r2 == null ? null : $r2;
    }

    private void runTests() throws  {
        test_venueFlagRequestType(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        test_alertTickerTypes();
        test_tooltipTypes();
        test_timeOfDayType();
        ConfigManager.testCounterConfigEnum();
        OmniSelectionFragment.testCalcRank();
        TicketRoller.testThis();
        CarpoolNativeManager.test_rideStateEnum();
        CarpoolNativeManager.test_endorsementsEnum();
    }

    public boolean RealtimeDebugEnabled() throws  {
        return RealtimeDebugEnabledNTV();
    }

    public ILocationSensorListener getLocationListner() throws  {
        return this.mLocationListner;
    }

    public void updateCalendarEvents() throws  {
        if (this.mCalendarEventsDirty) {
            Post(new Runnable() {
                public void run() throws  {
                    NativeManager.this.updateCalendarEventsNTV();
                }
            });
        }
    }

    public void setCalendarEventsDirty(boolean $z0) throws  {
        Log.d(Logger.TAG, "calendar setting dirty flag to " + $z0);
        this.mCalendarEventsDirty = $z0;
    }

    public void resetEvents() throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.resetEventsNTV();
            }
        });
    }

    public void requestCalendarAccess(long $l0, long $l1) throws  {
        ActivityBase $r4 = AppService.getActiveActivity();
        Intent $r2 = new Intent($r4, CalendarApprovedActivity.class);
        $r2.putExtra(CalendarApprovedActivity.EXTRA_CALLBACK, $l0);
        $r2.putExtra(CalendarApprovedActivity.EXTRA_CONTEXT, $l1);
        $r2.putExtra(CalendarApprovedActivity.EXTRA_GRANTED, true);
        Intent $r1 = new Intent($r4, CalendarApprovedActivity.class);
        $r1.putExtra(CalendarApprovedActivity.EXTRA_CALLBACK, $l0);
        $r1.putExtra(CalendarApprovedActivity.EXTRA_CONTEXT, $l1);
        $r1.putExtra(CalendarApprovedActivity.EXTRA_GRANTED, false);
        Intent $r3 = new Intent($r4, RequestPermissionActivity.class);
        $r3.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.READ_CALENDAR"});
        $r3.putExtra(RequestPermissionActivity.INT_ON_GRANTED, $r2);
        $r3.putExtra(RequestPermissionActivity.INT_ON_REJECTED, $r1);
        $r4.startActivityForResult($r3, CALENDAR_ACCESS_REQUEST_CODE);
    }

    public boolean calendarAccessEnabled() throws  {
        return ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.READ_CALENDAR") == 0 && calendarAuthorizedNTV();
    }

    public void requestCalendarAccessCallback(final long $l0, final long $l1, final boolean $z0) throws  {
        Post(new Runnable() {
            public void run() throws  {
                NativeManager.this.requestCalendarAccessCallbackNTV($l0, $l1, $z0);
            }
        });
    }

    public String getCalendarLearnMoreUrl() throws  {
        return String.format(getCalendarLearnMoreUrlNTV(), new Object[]{getLanguageCode2Letters()});
    }

    public void openCalendarSettings() throws  {
        ActivityBase $r2 = AppService.getActiveActivity();
        if ($r2 != null) {
            $r2.startActivityForResult(new Intent($r2, SettingsCalendarActivity.class), 1);
        }
    }

    public void ShowEncouragement(final QuestionData $r1) throws  {
        Logger.m36d("Encouragement: ShowEncouragement called");
        final MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            $r2.post(new Runnable() {
                public void run() throws  {
                    new EncouragementDialog($r2, $r1).show();
                }
            });
        }
    }

    public void LoginWithFacebook() throws  {
        FacebookManager.getInstance().loginWithFacebook(AppService.getActiveActivity(), FacebookLoginType.SetToken, true);
        if (AppService.getMainActivity() != null) {
            AppService.getMainActivity().promptForSmartLockWhenResumed("FB");
        }
    }

    public void LoginWithPhone() throws  {
        Intent $r1 = new Intent(AppService.getActiveActivity(), PhoneRegisterActivity.class);
        $r1.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
        AppService.getActiveActivity().startActivityForResult($r1, 1111);
    }

    public void ShowLoginOptions() throws  {
        AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), LoginOptionsActivity.class), 1);
    }

    public void ConnectWithSmartLock() throws  {
        SmartLockManager.getInstance().saveCredentials(AppService.getActiveActivity(), "ENCOURAGEMENT");
    }

    public void setCenteredOnMeListener(CenteredOnMeListener $r1) throws  {
        this.mCenteredOnMeListener = $r1;
    }

    public void CenterOnMeShown() throws  {
        this.mIsCenteredOnMe = false;
        if (this.mCenteredOnMeListener != null) {
            this.mCenteredOnMeListener.onCenteredOnMeChanged(false);
        }
    }

    public void CenterOnMeHidden() throws  {
        this.mIsCenteredOnMe = true;
        if (this.mCenteredOnMeListener != null) {
            this.mCenteredOnMeListener.onCenteredOnMeChanged(true);
        }
    }

    public boolean isCenteredOnMe() throws  {
        return this.mIsCenteredOnMe;
    }

    public void showFTEPopup() throws  {
        AppService.getMainActivity();
        MainActivity.registerOnResumeEvent(new Runnable() {

            class C12221 implements OnClickListener {
                C12221() throws  {
                }

                public void onClick(DialogInterface dialog, final int $i0) throws  {
                    AppService.Post(new Runnable() {
                        public void run() throws  {
                            if ($i0 == 0) {
                                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_NOW_LATER_POPUP_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_DRIVE_LATER).send();
                                Intent $r1 = new Intent(AppService.getActiveActivity(), PlannedDriveListActivity.class);
                                $r1.putExtra("search", true);
                                AppService.getActiveActivity().startActivity($r1);
                                return;
                            }
                            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_NOW_LATER_POPUP_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_DRIVE_NOW).send();
                            AppService.getMainActivity().getLayoutMgr().openLeftSideToAutocomplete(false);
                        }
                    });
                }
            }

            class C12232 implements OnCancelListener {
                C12232() throws  {
                }

                public void onCancel(DialogInterface dialog) throws  {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_NOW_LATER_POPUP_CLICK).addParam("ACTION", "BACK").send();
                }
            }

            class C12243 implements OnTouchListener {
                public boolean onTouch(View v, MotionEvent event) throws  {
                    return true;
                }

                C12243() throws  {
                }
            }

            public void run() throws  {
                String $r3 = ConfigValues.getStringValue(413);
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_NOW_LATER_POPUP).addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_SHOWN, $r3.equals(NativeManager.CONFIG_VALUE_NO_FTE) ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE).addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_WITH_TEXT, $r3.equals(NativeManager.CONFIG_VALUE_FTE_WITH_TEXT) ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE).send();
                if (!$r3.equals(NativeManager.CONFIG_VALUE_NO_FTE)) {
                    boolean $z0 = $r3.equals(NativeManager.CONFIG_VALUE_FTE_WITH_TEXT);
                    $r3 = DisplayStrings.displayString(DisplayStrings.DS_FTE_POPUP_TITLE);
                    String $r5 = "";
                    if ($z0) {
                        $r5 = DisplayStrings.displayString(DisplayStrings.DS_FTE_POPUP_BODY);
                    }
                    C12221 c12221 = new C12221();
                    C12232 c12232 = new C12232();
                    MsgBox.getInstance();
                    Dialog $r8 = MsgBox.openConfirmDialogJavaCallback($r3, $r5, true, c12221, DisplayStrings.displayString(DisplayStrings.DS_FTE_POPUP_NOW), DisplayStrings.displayString(DisplayStrings.DS_FTE_POPUP_LATER), 0, "fte_illustration", c12232, false, true, false);
                    View $r9 = $r8.findViewById(C1283R.id.confirmImage);
                    LayoutParams $r10 = $r9.getLayoutParams();
                    $r10.height = -2;
                    $r10.width = -2;
                    $r9.setLayoutParams($r10);
                    if (!$z0) {
                        $r8.findViewById(C1283R.id.confirmText).setVisibility(8);
                    }
                    View $r11 = $r8.findViewById(C1283R.id.confirmMainLayout);
                    $r11.setOnTouchListener(new C12243());
                    final Dialog dialog = $r8;
                    ((View) $r11.getParent()).setOnTouchListener(new OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) throws  {
                            dialog.dismiss();
                            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_NOW_LATER_POPUP_CLICK).addParam("ACTION", "DISMISS").send();
                            return true;
                        }
                    });
                    MainActivity $r15 = AppService.getMainActivity();
                    $r15.setDialog(null);
                    final View view = $r9;
                    $r15.addOrientationTracker(new ITrackOrientation() {
                        public void onOrientationChanged(int $i0) throws  {
                            LayoutParams $r2 = view.getLayoutParams();
                            if ($i0 == 1) {
                                $r2.height = -2;
                                $r2.width = -2;
                            } else {
                                $i0 = PixelMeasure.dp(100);
                                $r2.height = $i0;
                                $r2.width = $i0;
                            }
                            view.setLayoutParams($r2);
                        }
                    });
                }
            }
        });
    }

    public void OpenParkingSearch() throws  {
        VenueData $r1 = getInstance().getDestinationVenueDataNTV();
        ActivityBase $r2 = AppService.getActiveActivity();
        EditTextUtils.closeKeyboard($r2, $r2.getWindow().getDecorView());
        if ($r1 != null) {
            Intent $r5 = r6;
            Intent r6 = new Intent($r2, ParkingSearchResultsActivity.class);
            $r5.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, $r1);
            $r5.putExtra(PublicMacros.SEARCH_CATEGORY_GROUP, "parking");
            $r5.putExtra(PublicMacros.PREVIEW_PARKING_CONTEXT, AnalyticsEvents.ANALYTICS_EVENT_VALUE_CATEGORICAL_SHORT);
            $r2.startActivityForResult($r5, 0);
            return;
        }
        $r5 = r6;
        r6 = new Intent($r2, SearchResultsActivity.class);
        $r5.putExtra(PublicMacros.SEARCH_CATEGORY_GROUP, "parking");
        $r5.putExtra(PublicMacros.SEARCH_TITLE, "parking");
        $r5.putExtra(PublicMacros.SEARCH_MODE, 2);
        $r5.putExtra(PublicMacros.ICON, PARKING_ICON_NAME);
        $r2.startActivityForResult($r5, 0);
    }

    public void OpenCategorySearch(String $r1) throws  {
        ActivityBase $r4 = AppService.getActiveActivity();
        EditTextUtils.closeKeyboard($r4, $r4.getWindow().getDecorView());
        for (VenueCategoryGroup $r2 : getInstance().venueProviderGetCategoryGroups()) {
            if ($r2.id.equals($r1)) {
                Intent $r3 = new Intent($r4, SearchResultsActivity.class);
                $r3.putExtra(PublicMacros.SEARCH_CATEGORY_GROUP, $r1);
                $r3.putExtra(PublicMacros.SEARCH_TITLE, $r2.label);
                $r3.putExtra(PublicMacros.SEARCH_MODE, 2);
                $r3.putExtra(PublicMacros.ICON, $r2.icon + ResManager.mImageExtension);
                $r4.startActivityForResult($r3, 0);
                return;
            }
        }
    }
}
