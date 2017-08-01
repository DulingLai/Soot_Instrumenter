package com.waze.carpool;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Space;
import android.widget.TextView;
import com.facebook.share.internal.ShareConstants;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ChatNotificationManager;
import com.waze.ChatNotificationManager.ChatHandler;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.Utils;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager.DriveUpdates;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolNativeManager.LastChatMsgDetails;
import com.waze.carpool.CarpoolNativeManager.PickDestinationCompleteListener;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.carpool.CarpoolUtils.RiderImageAndMessageCounter;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityBase.IncomingHandler;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.ifs.ui.ObservableScrollView;
import com.waze.ifs.ui.UserTooltipView;
import com.waze.messages.QuestionData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.NavigateNativeManager;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.planned_drive.PlannedDriveOptionsListener;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.settings.SettingsCarpoolActivity;
import com.waze.settings.SettingsNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.button.RidersImages;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.popups.EndorseRiderDialog;
import com.waze.view.popups.EndorseRiderDialog.EndorseRiderResult;
import com.waze.view.popups.QuestionDialog;
import com.waze.view.popups.QuestionDialog.ResponseHandler;
import com.waze.view.popups.RateRiderDialog;
import com.waze.view.popups.RateRiderDialog.RateRiderResult;
import com.waze.view.title.TitleBar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class CarpoolRideDetailsActivity extends ActivityBase implements PickDestinationCompleteListener {
    public static final String INTENT_PARAM_MESSAGING = "Messaging";
    private static final long NETWORK_TIMEOUT = 10000;
    private static final int NUM_OPTIONS = 6;
    private static final int OPTION_BLOCK_RIDER = 2;
    private static final int OPTION_CANCEL_RIDE = 0;
    private static final int OPTION_FAQS = 3;
    private static final int OPTION_FEEDBACK = 4;
    private static final int OPTION_REPORT_NO_SHOW = 1;
    private static final int OPTION_SETTINGS = 5;
    public static final int PICKUP_INTERVAL_SEC = 300;
    public static final int RIDE_CHANGED = 10;
    public static final int RIDE_REJECTED = 11;
    public static final int RIDE_STATE_ACCEPTED = 2;
    public static final int RIDE_STATE_CANCELLED = 3;
    public static final int RIDE_STATE_COMPLETE = 5;
    public static final int RIDE_STATE_PAX_CANCELLED = 6;
    public static final int RIDE_STATE_PAX_PICKED_UP = 8;
    public static final int RIDE_STATE_REQUEST = 1;
    public static final int RIDE_STATE_STARTED = 7;
    public static final int RIDE_STATE_THANKS = 4;
    private static final int RQ_CODE_ONBOARD = 1002;
    private static final int RQ_DECLINE_REASONS = 1003;
    private static final int RQ_FULLSCREEN_MAP = 1005;
    private static final int RQ_RIDER_PROFILE = 1006;
    private static final String TAG = CarpoolRideDetailsActivity.class.getName();
    public static final int USER_PIC_REQUEST_CODE = 28541;
    public static final int WAZE_REG_REQUEST_CODE = 28540;
    private String destinationName;
    private int destinationType = 0;
    private String dropoffName;
    private String mAnalyticsType;
    private boolean mArrivedAtPickup;
    private boolean mBlocked = false;
    private SimpleBottomSheet mBottomSheet;
    private View mBtButtonsLayout;
    private ArrayList<RideChatHandler> mChatHandlers = new ArrayList(2);
    private boolean mChatted = false;
    private CarpoolNativeManager mCpnm;
    private boolean mDidShowRateRider = false;
    CarpoolDrive mDrive = null;
    private SparseArray<Integer> mDriveEtas;
    private boolean mDrivingToDropoff;
    private boolean mDrivingToPickup;
    DriveToNativeManager mDtnm;
    private INextActionCallback mGetAnswerCb = new C14971();
    private boolean mIsOnboarding = false;
    private boolean mIsOnboardingDone = false;
    private boolean mIsPictureAlreadyAdded = false;
    private boolean mMapInit = false;
    boolean mMultiPaxRide = false;
    private Set<View> mMultipaxRidersView = new HashSet();
    private NavigateNativeManager mNavNtvMgr;
    NativeManager mNm;
    private boolean mPicTimeoutOccurred = false;
    private int mRatedRiderNum = 0;
    boolean mRealtimeRide = false;
    CarpoolRide mRide = null;
    int mRideState = 0;
    CarpoolUserData mRider = null;
    HashMap<String, RiderImageAndMessageCounter> mRiderImageAndMessageCounter;
    private int[] mRiderRating;
    HashMap<String, View> mRiderViews = new HashMap(2);
    private ObservableScrollView mScrollView;
    private boolean mThanked = false;
    private DateFormat mTimeFormat;
    private Runnable mTimeoutRunnable;
    private Runnable mTipRunnable = null;
    private TitleBar mTitleBar;
    long mUtcTimeSecSelected;
    private boolean mWaitingForUpdate;
    private boolean mWasTimeSelected = false;
    private String originName;
    private int originType = 0;
    private String pickupName;
    private boolean shortCancel = false;

    class C14971 implements INextActionCallback {
        C14971() throws  {
        }

        public void setNextIntent(Intent $r1) throws  {
            CarpoolRideDetailsActivity.this.startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
        }

        public void setNextFragment(Fragment fragment) throws  {
            Logger.m38e("CarpoolRidesFragment: received unexpected setNextFragment");
        }

        public void setNextResult(int $i0) throws  {
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                CarpoolRideDetailsActivity.this.onBackPressed();
            } else if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                CarpoolRideDetailsActivity.this.setResult(-1);
                CarpoolRideDetailsActivity.this.finish();
            } else {
                Logger.m38e("CarpoolRidesFragment: received unexpected result:" + $i0);
            }
        }

        public Context getContext() throws  {
            return CarpoolRideDetailsActivity.this;
        }
    }

    class AnonymousClass28 implements OnGlobalLayoutListener {
        final /* synthetic */ View val$gridContainer;

        AnonymousClass28(View $r2) throws  {
            this.val$gridContainer = $r2;
        }

        public void onGlobalLayout() throws  {
            this.val$gridContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            View $r1 = CarpoolRideDetailsActivity.this.findViewById(C1283R.id.rideRequestRouteVerticalLine);
            $r1.getLayoutParams().height = this.val$gridContainer.getHeight() - PixelMeasure.dp(64);
            $r1.setVisibility(0);
        }
    }

    class C15002 implements IResultObj<DriveUpdates> {
        C15002() throws  {
        }

        public void onResult(DriveUpdates $r1) throws  {
            if ($r1 != null && $r1.ridesJoined != null && $r1.ridesJoined.length > 0) {
                new CarpoolRiderJoinRequest(CarpoolRideDetailsActivity.this, CarpoolRideDetailsActivity.this.mDrive, $r1.ridesJoined).show();
            }
        }
    }

    class C15053 implements OnClickListener {
        C15053() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_OPTIONS).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
            CarpoolRideDetailsActivity.this.showBottomSheet();
        }
    }

    class C15064 implements Runnable {
        C15064() throws  {
        }

        public void run() throws  {
            if (CarpoolRideDetailsActivity.this.updateTimeBanner()) {
                CarpoolRideDetailsActivity.this.postDelayed(this, 30000);
            }
        }
    }

    class C15105 implements OnClickListener {
        C15105() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", "MAP").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
            CarpoolRideDetailsActivity.this.openFullMap(null);
        }
    }

    class C15126 implements PlannedDriveOptionsListener {

        class C15111 implements Runnable {
            C15111() throws  {
            }

            public void run() throws  {
                CarpoolRideDetailsActivity $r1 = CarpoolRideDetailsActivity.this;
                long $l0 = CarpoolRideDetailsActivity.this.mUtcTimeSecSelected;
                long $l1 = (long) CarpoolRideDetailsActivity.this.mDrive.drive_match_info.origin_to_pickup_duration_seconds;
                int $i2 = CarpoolRideDetailsActivity.this.mDrive.drive_match_info.dropoff_to_destination_duration_seconds;
                int $i3 = CarpoolRideDetailsActivity.this.mDrive.drive_match_info.pickup_to_dropoff_duration_seconds;
                $r1.setRouteTimes($l0, $l1, (long) ($i2 + $i3));
            }
        }

        C15126() throws  {
        }

        public void onPlannedDriveOptionsLoaded(int[] $r1, int[] $r2, boolean too_late) throws  {
            int $i0;
            CarpoolRideDetailsActivity.this.mDriveEtas = new SparseArray($r2.length);
            for ($i0 = 0; $i0 < $r2.length; $i0++) {
                CarpoolRideDetailsActivity.this.mDriveEtas.put($r2[$i0], Integer.valueOf($r1[$i0]));
            }
            if (CarpoolRideDetailsActivity.this.mWasTimeSelected) {
                $i0 = ((Integer) CarpoolRideDetailsActivity.this.mDriveEtas.get((int) CarpoolRideDetailsActivity.this.mUtcTimeSecSelected, Integer.valueOf(0))).intValue();
                if ($i0 > 0) {
                    CarpoolRideDetailsActivity.this.mDrive.drive_match_info.origin_to_pickup_duration_seconds = $i0;
                    CarpoolRideDetailsActivity.this.post(new C15111());
                }
            }
        }

        public void onPlannedDriveCreationSuccess() throws  {
        }

        public void onPlannedDriveCreationFailed() throws  {
        }

        public void onPlannedDriveUpdatedSuccess() throws  {
        }

        public void onPlannedDriveUpdatedFailed() throws  {
        }
    }

    class C15147 implements PlannedDriveOptionsListener {

        class C15131 implements Runnable {
            C15131() throws  {
            }

            public void run() throws  {
                CarpoolRideDetailsActivity $r2 = CarpoolRideDetailsActivity.this;
                long $l0 = CarpoolRideDetailsActivity.this.mDrive.getTime();
                long $l2 = (long) CarpoolRideDetailsActivity.this.mDrive.drive_match_info.origin_to_pickup_duration_seconds;
                int $i1 = CarpoolRideDetailsActivity.this.mDrive.drive_match_info.dropoff_to_destination_duration_seconds;
                int $i3 = CarpoolRideDetailsActivity.this.mDrive.drive_match_info.pickup_to_dropoff_duration_seconds;
                $r2.setRouteTimes($l0, $l2, (long) ($i1 + $i3));
                $r2 = CarpoolRideDetailsActivity.this;
                View $r6 = CarpoolRideDetailsActivity.this.findViewById(C1283R.id.rideRequestScLayout);
                $l0 = CarpoolRideDetailsActivity.this.mDrive.getTime();
                $l2 = (long) CarpoolRideDetailsActivity.this.mDrive.drive_match_info.origin_to_pickup_duration_seconds;
                $i1 = CarpoolRideDetailsActivity.this.mDrive.drive_match_info.dropoff_to_destination_duration_seconds;
                $i3 = CarpoolRideDetailsActivity.this.mDrive.drive_match_info.pickup_to_dropoff_duration_seconds;
                $r2.setRideScheduledDetails($r6, $l0, $l2, (long) ($i1 + $i3));
            }
        }

        C15147() throws  {
        }

        public void onPlannedDriveOptionsLoaded(int[] $r1, int[] times, boolean too_late) throws  {
            if ($r1 != null && $r1.length != 0) {
                CarpoolRideDetailsActivity.this.mDrive.drive_match_info.origin_to_pickup_duration_seconds = $r1[0];
                CarpoolRideDetailsActivity.this.post(new C15131());
            }
        }

        public void onPlannedDriveCreationSuccess() throws  {
        }

        public void onPlannedDriveCreationFailed() throws  {
        }

        public void onPlannedDriveUpdatedSuccess() throws  {
        }

        public void onPlannedDriveUpdatedFailed() throws  {
        }
    }

    class C15178 implements SimpleBottomSheetListener {

        class C15151 implements IResultObj<Integer> {
            C15151() throws  {
            }

            public void onResult(Integer $r1) throws  {
                CarpoolRide $r5 = CarpoolRideDetailsActivity.this.mDrive.getRide($r1.intValue());
                CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE, CarpoolRideDetailsActivity.this.mHandler);
                CarpoolUtils.riderDidntShow(CarpoolRideDetailsActivity.this.mDrive, $r5, CarpoolRideDetailsActivity.this);
            }
        }

        class C15162 implements IResultObj<Integer> {
            C15162() throws  {
            }

            public void onResult(Integer $r1) throws  {
                CarpoolRideDetailsActivity.this.blockRider(CarpoolRideDetailsActivity.this.mDrive.getRide($r1.intValue()).getRider());
            }
        }

        C15178() throws  {
        }

        public void onComplete(SimpleBottomSheetValue $r1) throws  {
            Context $r3;
            CarpoolRideDetailsActivity $r7;
            CarpoolDrive $r4;
            HashMap $r8;
            HashMap $r72;
            switch ($r1.id) {
                case 0:
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OPTION).addParam("ACTION", "CANCEL").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                    if (CarpoolRideDetailsActivity.this.mRideState == 2 || CarpoolRideDetailsActivity.this.mRideState == 7) {
                        CarpoolRideDetailsActivity.this.cancelOrDismissRide();
                        break;
                    }
                    return;
                    break;
                case 1:
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OPTION).addParam("ACTION", "REPORT_NO_SHOW").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                    if (CarpoolRideDetailsActivity.this.mRideState == 2 || CarpoolRideDetailsActivity.this.mRideState == 7) {
                        if (!CarpoolRideDetailsActivity.this.mMultiPaxRide) {
                            CarpoolUtils.riderDidntShow(CarpoolRideDetailsActivity.this.mDrive, CarpoolRideDetailsActivity.this.mRide, CarpoolRideDetailsActivity.this);
                            break;
                        }
                        $r3 = CarpoolRideDetailsActivity.this;
                        $r7 = CarpoolRideDetailsActivity.this;
                        $r4 = $r7.mDrive;
                        $r8 = CarpoolRideDetailsActivity.this;
                        $r72 = $r8;
                        CarpoolUtils.showSelectRiderBottomSheet($r3, $r4, $r8.mRiderImageAndMessageCounter, new C15151(), DisplayStrings.DS_RIDE_DETAILS_OPTION_REPORT_NO_SHOW, DisplayStrings.DS_RIDERS_ACTION_SHEET_NO_SHOW_BUTTON_PS);
                        break;
                    }
                    return;
                case 2:
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OPTION).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_BLOCK).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                    if (!CarpoolRideDetailsActivity.this.mDrive.isMultiPax()) {
                        CarpoolRideDetailsActivity $r32 = CarpoolRideDetailsActivity.this;
                        CarpoolUserData $r12 = CarpoolRideDetailsActivity.this;
                        CarpoolUserData $r73 = $r12;
                        $r12 = $r12.mRider;
                        CarpoolUserData $r122 = $r12;
                        $r32.blockRider($r12);
                        break;
                    }
                    $r3 = CarpoolRideDetailsActivity.this;
                    $r7 = CarpoolRideDetailsActivity.this;
                    $r4 = $r7.mDrive;
                    $r8 = CarpoolRideDetailsActivity.this;
                    $r72 = $r8;
                    CarpoolUtils.showSelectRiderBottomSheet($r3, $r4, $r8.mRiderImageAndMessageCounter, new C15162(), DisplayStrings.DS_RIDERS_ACTION_SHEET_BLOCK_TITLE, -1);
                    break;
                case 3:
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OPTION).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_FAQ).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                    CarpoolRideDetailsActivity.this.startActivity(new Intent(CarpoolRideDetailsActivity.this, CarpoolFAQActivity.class));
                    break;
                case 4:
                    CarpoolUtils.startFeedbackActivity(CarpoolRideDetailsActivity.this.mDrive, null, CarpoolRideDetailsActivity.this.mRiderImageAndMessageCounter, CarpoolRideDetailsActivity.this, CarpoolRideDetailsActivity.this, true);
                    break;
                case 5:
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OPTION).addParam("ACTION", "SETTINGS").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                    CarpoolRideDetailsActivity.this.startActivity(new Intent(CarpoolRideDetailsActivity.this, SettingsCarpoolActivity.class));
                    break;
                default:
                    return;
            }
            CarpoolRideDetailsActivity.this.mBottomSheet.dismiss();
        }
    }

    private class RideChatHandler implements ChatHandler {
        final CarpoolRide ride;
        final View riderView;

        class C15191 implements Runnable {
            C15191() throws  {
            }

            public void run() throws  {
                CarpoolRideDetailsActivity.this.updateChatBadge(CarpoolRideDetailsActivity.this.getRiderButtonsView(RideChatHandler.this.riderView), RideChatHandler.this.ride);
            }
        }

        class C15202 implements Runnable {
            C15202() throws  {
            }

            public void run() throws  {
                CarpoolRideDetailsActivity.this.updateChatBadge(CarpoolRideDetailsActivity.this.getRiderButtonsView(RideChatHandler.this.riderView), RideChatHandler.this.ride);
            }
        }

        RideChatHandler(CarpoolRide $r2, View $r3) throws  {
            this.ride = $r2;
            this.riderView = $r3;
        }

        public boolean onChatMessage(String message) throws  {
            CarpoolRideDetailsActivity.this.post(new C15191());
            return false;
        }

        public void onMessagesLoaded() throws  {
            CarpoolRideDetailsActivity.this.post(new C15202());
        }

        public void onMessageSent(boolean success) throws  {
        }

        public void onMessageRead(String msgId) throws  {
        }
    }

    private void sendShownAnalytics() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:36:0x014a
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
        r25 = this;
        r3 = 0;
        r4 = 1;
        r0 = r25;
        r5 = r0.originType;
        r6 = 1;
        if (r5 != r6) goto L_0x012e;
    L_0x0009:
        r7 = "HOME";
    L_0x000b:
        r0 = r25;
        r5 = r0.destinationType;
        r6 = 1;
        if (r5 != r6) goto L_0x0151;
    L_0x0012:
        r8 = "HOME";
    L_0x0014:
        r0 = r25;
        r9 = r0.mRider;
        if (r9 == 0) goto L_0x0178;
    L_0x001a:
        r0 = r25;
        r9 = r0.mRider;
        r10 = r9.organization;
        if (r10 == 0) goto L_0x0178;
    L_0x0022:
        r0 = r25;
        r9 = r0.mRider;
        r10 = r9.organization;
        r11 = r10.isEmpty();
        if (r11 != 0) goto L_0x0178;
    L_0x002e:
        r0 = r25;
        r12 = r0.mCpnm;
        r0 = r25;
        r13 = r0.mDrive;
        r5 = r12.getUnreadChatMessageCount(r13);
        r15 = "RW_RIDE_SCREEN_SHOWN";
        r14 = com.waze.analytics.AnalyticsBuilder.analytics(r15);
        r0 = r25;
        r10 = r0.mAnalyticsType;
        r15 = "RIDE_STATE";
        r14 = r14.addParam(r15, r10);
        if (r4 == 0) goto L_0x017e;
    L_0x004c:
        r10 = "F";
    L_0x004e:
        r15 = "RIDER_WORKPLACE";
        r14 = r14.addParam(r15, r10);
        r0 = r25;
        r9 = r0.mRider;
        if (r9 == 0) goto L_0x0185;
    L_0x005a:
        r0 = r25;
        r9 = r0.mRider;
        r0 = r9.star_rating_as_pax;
        r16 = r0;
    L_0x0062:
        r15 = "STARS";
        r0 = r16;
        r14 = r14.addParam(r15, r0);
        r0 = r25;
        r9 = r0.mRider;
        if (r9 == 0) goto L_0x018c;
    L_0x0070:
        r0 = r25;
        r9 = r0.mRider;
        r0 = r9.completed_rides_pax;
        r17 = r0;
        r0 = (long) r0;
        r18 = r0;
    L_0x007b:
        r15 = "RIDES";
        r0 = r18;
        r14 = r14.addParam(r15, r0);
        r0 = r25;
        r13 = r0.mDrive;
        r0 = r13.drive_match_info;
        r20 = r0;
        r0 = r0.detour_distance_meters;
        r21 = r0;
        r0 = (float) r0;
        r16 = r0;
        r23 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r0 = r16;
        r1 = r23;
        r0 = r0 / r1;
        r16 = r0;
        r15 = "DETOUR_KM";
        r0 = r16;
        r14 = r14.addParam(r15, r0);
        r0 = r25;
        r13 = r0.mDrive;
        r17 = r13.getDetourMinutes();
        r0 = r17;
        r0 = (long) r0;
        r18 = r0;
        r15 = "DETOUR_MIN";
        r0 = r18;
        r14 = r14.addParam(r15, r0);
        r15 = "START";
        r14 = r14.addParam(r15, r7);
        r15 = "DESTINATION";
        r14 = r14.addParam(r15, r8);
        r0 = r25;
        r13 = r0.mDrive;
        r0 = r25;
        r7 = r13.getRewardString(r0);
        r15 = "RIDE_PRICE";
        r14 = r14.addParam(r15, r7);
        r0 = r25;
        r0 = r0.mRide;
        r24 = r0;
        if (r24 == 0) goto L_0x00e5;
    L_0x00dd:
        r0 = r25;
        r0 = r0.mRide;
        r24 = r0;
        r3 = r0.rider_endorsement;
    L_0x00e5:
        r0 = (long) r3;
        r18 = r0;
        r15 = "ENDORSEMENT";
        r0 = r18;
        r14 = r14.addParam(r15, r0);
        r0 = r25;
        r13 = r0.mDrive;
        r7 = r13.getSomeRideId();
        r15 = "RIDE_ID";
        r14 = r14.addParam(r15, r7);
        r0 = r25;
        r13 = r0.mDrive;
        r7 = r13.getId();
        r15 = "DRIVE_ID";
        r14 = r14.addParam(r15, r7);
        r0 = r25;
        r13 = r0.mDrive;
        r3 = r13.getRidesAmount();
        r0 = (long) r3;
        r18 = r0;
        r15 = "NUM_RIDERS";
        r0 = r18;
        r14 = r14.addParam(r15, r0);
        r0 = (long) r5;
        r18 = r0;
        r15 = "IAM_SHOWN";
        r0 = r18;
        r14 = r14.addParam(r15, r0);
        r14.send();
        return;
    L_0x012e:
        r0 = r25;
        r5 = r0.originType;
        r6 = 3;
        if (r5 != r6) goto L_0x013c;
    L_0x0135:
        goto L_0x0139;
    L_0x0136:
        goto L_0x000b;
    L_0x0139:
        r7 = "WORK";
        goto L_0x0136;
    L_0x013c:
        r0 = r25;
        r5 = r0.originType;
        r6 = 5;
        if (r5 != r6) goto L_0x014e;
    L_0x0143:
        goto L_0x0147;
    L_0x0144:
        goto L_0x000b;
    L_0x0147:
        r7 = "FAV";
        goto L_0x0144;
        goto L_0x014e;
    L_0x014b:
        goto L_0x000b;
    L_0x014e:
        r7 = "ADDRESS";
        goto L_0x014b;
    L_0x0151:
        r0 = r25;
        r5 = r0.destinationType;
        r6 = 3;
        if (r5 != r6) goto L_0x015f;
    L_0x0158:
        goto L_0x015c;
    L_0x0159:
        goto L_0x0014;
    L_0x015c:
        r8 = "WORK";
        goto L_0x0159;
    L_0x015f:
        r0 = r25;
        r5 = r0.destinationType;
        r6 = 5;
        if (r5 != r6) goto L_0x0171;
    L_0x0166:
        goto L_0x016a;
    L_0x0167:
        goto L_0x0014;
    L_0x016a:
        r8 = "FAV";
        goto L_0x0167;
        goto L_0x0171;
    L_0x016e:
        goto L_0x0014;
    L_0x0171:
        r8 = "ADDRESS";
        goto L_0x016e;
        goto L_0x0178;
    L_0x0175:
        goto L_0x002e;
    L_0x0178:
        r4 = 0;
        goto L_0x0175;
        goto L_0x017e;
    L_0x017b:
        goto L_0x004e;
    L_0x017e:
        r10 = "T";
        goto L_0x017b;
        goto L_0x0185;
    L_0x0182:
        goto L_0x0062;
    L_0x0185:
        r16 = 0;
        goto L_0x0182;
        goto L_0x018c;
    L_0x0189:
        goto L_0x007b;
    L_0x018c:
        r18 = 0;
        goto L_0x0189;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRideDetailsActivity.sendShownAnalytics():void");
    }

    private void setupActivity() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:25:0x01de
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
        r63 = this;
        r0 = r63;
        r10 = r0.mDrive;
        if (r10 != 0) goto L_0x0011;
    L_0x0006:
        r11 = "CarpoolRideDetailsActivity: No ride";
        com.waze.Logger.m38e(r11);
        r0 = r63;
        r0.finish();
        return;
    L_0x0011:
        r0 = r63;
        r10 = r0.mDrive;
        r12 = r10.isMultiPax();
        r0 = r63;
        r0.mMultiPaxRide = r12;
        r0 = r63;
        r12 = r0.mMultiPaxRide;
        if (r12 == 0) goto L_0x0033;
    L_0x0023:
        r0 = r63;
        r13 = r0.mRiderImageAndMessageCounter;
        if (r13 != 0) goto L_0x0033;
    L_0x0029:
        r13 = new java.util.HashMap;
        r14 = 3;
        r13.<init>(r14);
        r0 = r63;
        r0.mRiderImageAndMessageCounter = r13;
    L_0x0033:
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r15 = r0.mRide;
        r16 = r10.getState(r15);
        switch(r16) {
            case 1: goto L_0x01cf;
            case 2: goto L_0x01e2;
            case 3: goto L_0x01ec;
            case 4: goto L_0x01f6;
            case 5: goto L_0x01ec;
            case 6: goto L_0x01ec;
            case 7: goto L_0x022a;
            case 8: goto L_0x024c;
            case 9: goto L_0x01f6;
            case 10: goto L_0x0239;
            case 11: goto L_0x0043;
            case 12: goto L_0x0043;
            case 13: goto L_0x01cf;
            case 14: goto L_0x0043;
            case 15: goto L_0x01ec;
            case 16: goto L_0x0239;
            default: goto L_0x0042;
        };
    L_0x0042:
        goto L_0x0043;
    L_0x0043:
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        r11 = "CarpoolRideDetailsActivity: unknown state = ";
        r0 = r17;
        r17 = r0.append(r11);
        r0 = r17;
        r1 = r16;
        r17 = r0.append(r1);
        r0 = r17;
        r18 = r0.toString();
        r0 = r18;
        com.waze.Logger.m38e(r0);
        r14 = 1;
        r0 = r63;
        r0.mRideState = r14;
        r0 = r63;
        r0.loadRequestPickupTimes();
    L_0x006f:
        r14 = 2130903368; // 0x7f030148 float:1.7413552E38 double:1.0528061487E-314;
        r0 = r63;
        r0.setContentView(r14);
        r0 = r63;
        r0 = r0.originName;
        r19 = r0;
        r0 = r63;
        r0 = r0.originType;
        r20 = r0;
        r0 = r63;
        r0 = r0.pickupName;
        r18 = r0;
        r0 = r63;
        r0 = r0.dropoffName;
        r21 = r0;
        r0 = r63;
        r0 = r0.destinationName;
        r22 = r0;
        r0 = r63;
        r0 = r0.destinationType;
        r23 = r0;
        r0 = r63;
        r1 = r19;
        r2 = r20;
        r3 = r18;
        r4 = r21;
        r5 = r22;
        r6 = r23;
        r0.setRouteDetails(r1, r2, r3, r4, r5, r6);
        r14 = 1;
        r0 = r63;
        r0.mMapInit = r14;
        r14 = 2131689745; // 0x7f0f0111 float:1.9008514E38 double:1.0531946706E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r26 = r24;
        r26 = (com.waze.ifs.ui.ObservableScrollView) r26;
        r25 = r26;
        r0 = r25;
        r1 = r63;
        r1.mScrollView = r0;
        r14 = 2131689824; // 0x7f0f0160 float:1.9008674E38 double:1.0531947096E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r0 = r24;
        r1 = r63;
        r1.mBtButtonsLayout = r0;
        r14 = 2131689674; // 0x7f0f00ca float:1.900837E38 double:1.0531946355E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r28 = r24;
        r28 = (com.waze.view.title.TitleBar) r28;
        r27 = r28;
        r0 = r27;
        r1 = r63;
        r1.mTitleBar = r0;
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2815; // 0xaff float:3.945E-42 double:1.391E-320;
        r0 = r27;
        r1 = r63;
        r0.init(r1, r14);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 0;
        r0 = r27;
        r0.setCloseVisibility(r14);
        r14 = 2131691761; // 0x7f0f08f1 float:1.9012603E38 double:1.0531956666E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r29 = new com.waze.carpool.CarpoolRideDetailsActivity$3;
        r0 = r29;
        r1 = r63;
        r0.<init>();
        r0 = r24;
        r1 = r29;
        r0.setOnClickListener(r1);
        r11 = "";
        r0 = r63;
        r0.mAnalyticsType = r11;
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2130839490; // 0x7f0207c2 float:1.7283992E38 double:1.052774589E-314;
        r0 = r27;
        r0.setUpButtonResource(r14);
        r0 = r63;
        r0 = r0.mRideState;
        r20 = r0;
        r14 = 1;
        r0 = r20;
        if (r0 != r14) goto L_0x025a;
    L_0x013d:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r0 = r0.mNm;
        r30 = r0;
        r14 = 1863; // 0x747 float:2.61E-42 double:9.204E-321;
        r0 = r30;
        r18 = r0.getLanguageString(r14);
        r0 = r27;
        r1 = r18;
        r0.setTitle(r1);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2130837892; // 0x7f020184 float:1.728075E38 double:1.0527737993E-314;
        r0 = r27;
        r0.setBackgroundResource(r14);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r31 = r0.getResources();
        r14 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
        r0 = r31;
        r16 = r0.getColor(r14);
        r0 = r27;
        r1 = r16;
        r0.setTextColor(r1);
        r0 = r63;
        r10 = r0.mDrive;
        r12 = r10.isRealTimeRide();
        if (r12 == 0) goto L_0x0253;
    L_0x018c:
        r11 = "RT_REQUEST";
        r0 = r63;
        r0.mAnalyticsType = r11;
    L_0x0192:
        r14 = 2131691754; // 0x7f0f08ea float:1.9012589E38 double:1.053195663E-314;
        r0 = r63;
        r32 = r0.findViewById(r14);
        r24 = r32;
        r0 = r32;
        r33 = r0.getParent();
        r35 = r33;
        r35 = (android.view.ViewGroup) r35;
        r34 = r35;
        r0 = r63;
        r0 = r0.mMultipaxRidersView;
        r36 = r0;
        r37 = r0.iterator();
    L_0x01b3:
        r0 = r37;
        r12 = r0.hasNext();
        if (r12 == 0) goto L_0x045b;
    L_0x01bb:
        r0 = r37;
        r38 = r0.next();
        r40 = r38;
        r40 = (android.view.View) r40;
        r39 = r40;
        r0 = r34;
        r1 = r39;
        r0.removeView(r1);
        goto L_0x01b3;
    L_0x01cf:
        r14 = 1;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x01d8;
    L_0x01d5:
        goto L_0x006f;
    L_0x01d8:
        r0 = r63;
        r0.loadRequestPickupTimes();
        goto L_0x01d5;
        goto L_0x01e2;
    L_0x01df:
        goto L_0x006f;
    L_0x01e2:
        r14 = 6;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x01df;
        goto L_0x01ec;
    L_0x01e9:
        goto L_0x006f;
    L_0x01ec:
        r14 = 3;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x01e9;
        goto L_0x01f6;
    L_0x01f3:
        goto L_0x0192;
    L_0x01f6:
        r0 = r63;
        r0 = r0.mRider;
        r41 = r0;
        if (r41 == 0) goto L_0x021a;
    L_0x01fe:
        r0 = r63;
        r15 = r0.mRide;
        r12 = r15.driver_reviewed;
        if (r12 != 0) goto L_0x021a;
    L_0x0206:
        r0 = r63;
        r10 = r0.mDrive;
        if (r10 == 0) goto L_0x021a;
    L_0x020c:
        r0 = r63;
        r10 = r0.mDrive;
        r12 = r10.hasId();
        if (r12 != 0) goto L_0x0224;
    L_0x0216:
        goto L_0x021a;
    L_0x0217:
        goto L_0x006f;
    L_0x021a:
        r14 = 5;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x0217;
        goto L_0x0224;
    L_0x0221:
        goto L_0x006f;
    L_0x0224:
        r14 = 4;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x0221;
    L_0x022a:
        r14 = 2;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x0233;
    L_0x0230:
        goto L_0x006f;
    L_0x0233:
        r0 = r63;
        r0.loadConfirmedPickupTimes();
        goto L_0x0230;
    L_0x0239:
        r14 = 7;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x0242;
    L_0x023f:
        goto L_0x006f;
    L_0x0242:
        r0 = r63;
        r0.loadConfirmedPickupTimes();
        goto L_0x023f;
        goto L_0x024c;
    L_0x0249:
        goto L_0x006f;
    L_0x024c:
        r14 = 8;
        r0 = r63;
        r0.mRideState = r14;
        goto L_0x0249;
    L_0x0253:
        r11 = "REQUEST";
        r0 = r63;
        r0.mAnalyticsType = r11;
        goto L_0x01f3;
    L_0x025a:
        r0 = r63;
        r0 = r0.mRideState;
        r20 = r0;
        r14 = 2;
        r0 = r20;
        if (r0 == r14) goto L_0x027c;
    L_0x0265:
        r0 = r63;
        r0 = r0.mRideState;
        r20 = r0;
        r14 = 7;
        r0 = r20;
        if (r0 == r14) goto L_0x027c;
    L_0x0270:
        r0 = r63;
        r0 = r0.mRideState;
        r20 = r0;
        r14 = 8;
        r0 = r20;
        if (r0 != r14) goto L_0x02cd;
    L_0x027c:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2130837892; // 0x7f020184 float:1.728075E38 double:1.0527737993E-314;
        r0 = r27;
        r0.setBackgroundResource(r14);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r31 = r0.getResources();
        r14 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
        r0 = r31;
        r16 = r0.getColor(r14);
        r0 = r27;
        r1 = r16;
        r0.setTextColor(r1);
        r0 = r63;
        r12 = r0.updateTimeBanner();
        if (r12 == 0) goto L_0x02c6;
    L_0x02ae:
        r42 = new com.waze.carpool.CarpoolRideDetailsActivity$4;
        r0 = r42;
        r1 = r63;
        r0.<init>();
        r43 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0 = r63;
        r1 = r42;
        r2 = r43;
        r0.postDelayed(r1, r2);
        goto L_0x02c6;
    L_0x02c3:
        goto L_0x0192;
    L_0x02c6:
        r11 = "SCHEDULED";
        r0 = r63;
        r0.mAnalyticsType = r11;
        goto L_0x02c3;
    L_0x02cd:
        r0 = r63;
        r0 = r0.mRideState;
        r20 = r0;
        r14 = 3;
        r0 = r20;
        if (r0 != r14) goto L_0x034a;
    L_0x02d8:
        r14 = 15;
        r0 = r16;
        if (r0 != r14) goto L_0x032e;
    L_0x02de:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r0 = r0.mNm;
        r30 = r0;
        r14 = 1866; // 0x74a float:2.615E-42 double:9.22E-321;
        r0 = r30;
        r18 = r0.getLanguageString(r14);
        r0 = r27;
        r1 = r18;
        r0.setTitle(r1);
    L_0x02f9:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2130837890; // 0x7f020182 float:1.7280747E38 double:1.0527737983E-314;
        r0 = r27;
        r0.setBackgroundResource(r14);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r31 = r0.getResources();
        r14 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
        r0 = r31;
        r16 = r0.getColor(r14);
        r0 = r27;
        r1 = r16;
        r0.setTextColor(r1);
        goto L_0x0327;
    L_0x0324:
        goto L_0x0192;
    L_0x0327:
        r11 = "CANCELED";
        r0 = r63;
        r0.mAnalyticsType = r11;
        goto L_0x0324;
    L_0x032e:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r0 = r0.mNm;
        r30 = r0;
        r14 = 1865; // 0x749 float:2.613E-42 double:9.214E-321;
        r0 = r30;
        r18 = r0.getLanguageString(r14);
        r0 = r27;
        r1 = r18;
        r0.setTitle(r1);
        goto L_0x02f9;
    L_0x034a:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 6;
        r0 = r16;
        if (r0 != r14) goto L_0x03a5;
    L_0x0355:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r0 = r0.mNm;
        r30 = r0;
        r14 = 1865; // 0x749 float:2.613E-42 double:9.214E-321;
        r0 = r30;
        r18 = r0.getLanguageString(r14);
        r0 = r27;
        r1 = r18;
        r0.setTitle(r1);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2130837890; // 0x7f020182 float:1.7280747E38 double:1.0527737983E-314;
        r0 = r27;
        r0.setBackgroundResource(r14);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r31 = r0.getResources();
        r14 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
        r0 = r31;
        r16 = r0.getColor(r14);
        r0 = r27;
        r1 = r16;
        r0.setTextColor(r1);
        goto L_0x039e;
    L_0x039b:
        goto L_0x0192;
    L_0x039e:
        r11 = "CANCELED";
        r0 = r63;
        r0.mAnalyticsType = r11;
        goto L_0x039b;
    L_0x03a5:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 4;
        r0 = r16;
        if (r0 != r14) goto L_0x0400;
    L_0x03b0:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r0 = r0.mNm;
        r30 = r0;
        r14 = 1867; // 0x74b float:2.616E-42 double:9.224E-321;
        r0 = r30;
        r18 = r0.getLanguageString(r14);
        r0 = r27;
        r1 = r18;
        r0.setTitle(r1);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2130837821; // 0x7f02013d float:1.7280607E38 double:1.052773764E-314;
        r0 = r27;
        r0.setBackgroundResource(r14);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r31 = r0.getResources();
        r14 = 2131623979; // 0x7f0e002b float:1.8875125E38 double:1.053162178E-314;
        r0 = r31;
        r16 = r0.getColor(r14);
        r0 = r27;
        r1 = r16;
        r0.setTextColor(r1);
        goto L_0x03f9;
    L_0x03f6:
        goto L_0x0192;
    L_0x03f9:
        r11 = "COMPLETED";
        r0 = r63;
        r0.mAnalyticsType = r11;
        goto L_0x03f6;
    L_0x0400:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 5;
        r0 = r16;
        if (r0 != r14) goto L_0x0192;
    L_0x040b:
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r0 = r0.mNm;
        r30 = r0;
        r14 = 1867; // 0x74b float:2.616E-42 double:9.224E-321;
        r0 = r30;
        r18 = r0.getLanguageString(r14);
        r0 = r27;
        r1 = r18;
        r0.setTitle(r1);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r14 = 2130837821; // 0x7f02013d float:1.7280607E38 double:1.052773764E-314;
        r0 = r27;
        r0.setBackgroundResource(r14);
        r0 = r63;
        r0 = r0.mTitleBar;
        r27 = r0;
        r0 = r63;
        r31 = r0.getResources();
        r14 = 2131623979; // 0x7f0e002b float:1.8875125E38 double:1.053162178E-314;
        r0 = r31;
        r16 = r0.getColor(r14);
        r0 = r27;
        r1 = r16;
        r0.setTextColor(r1);
        goto L_0x0454;
    L_0x0451:
        goto L_0x0192;
    L_0x0454:
        r11 = "COMPLETED";
        r0 = r63;
        r0.mAnalyticsType = r11;
        goto L_0x0451;
    L_0x045b:
        r0 = r63;
        r0 = r0.mMultipaxRidersView;
        r36 = r0;
        r0.clear();
        r0 = r63;
        r12 = r0.mMultiPaxRide;
        if (r12 == 0) goto L_0x0530;
    L_0x046a:
        r0 = r63;
        r0.setMultiPax();
        r0 = r32;
        r33 = r0.getParent();
        r45 = r33;
        r45 = (android.view.ViewGroup) r45;
        r34 = r45;
        r0 = r34;
        r1 = r32;
        r16 = r0.indexOfChild(r1);
        r20 = 0;
    L_0x0485:
        r0 = r63;
        r10 = r0.mDrive;
        r23 = r10.getRidesAmount();
        r0 = r20;
        r1 = r23;
        if (r0 >= r1) goto L_0x0575;
    L_0x0493:
        if (r20 <= 0) goto L_0x04f3;
    L_0x0495:
        r0 = r63;
        r46 = r0.getLayoutInflater();
        r14 = 2130903376; // 0x7f030150 float:1.7413568E38 double:1.0528061527E-314;
        r47 = 0;
        r0 = r46;
        r1 = r34;
        r2 = r47;
        r32 = r0.inflate(r14, r1, r2);
        r0 = r63;
        r46 = r0.getLayoutInflater();
        r14 = 2130903374; // 0x7f03014e float:1.7413564E38 double:1.0528061517E-314;
        r47 = 0;
        r0 = r46;
        r1 = r34;
        r2 = r47;
        r39 = r0.inflate(r14, r1, r2);
        r24 = r39;
        r23 = r16 + 1;
        r0 = r34;
        r1 = r32;
        r2 = r23;
        r0.addView(r1, r2);
        r23 = r16 + 2;
        r0 = r34;
        r1 = r39;
        r2 = r23;
        r0.addView(r1, r2);
        r16 = r16 + 2;
        goto L_0x04dd;
    L_0x04da:
        goto L_0x0485;
    L_0x04dd:
        r0 = r63;
        r0 = r0.mMultipaxRidersView;
        r36 = r0;
        r1 = r32;
        r0.add(r1);
        r0 = r63;
        r0 = r0.mMultipaxRidersView;
        r36 = r0;
        r1 = r39;
        r0.add(r1);
    L_0x04f3:
        r0 = r63;
        r13 = r0.mRiderViews;
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r20;
        r41 = r10.getRider(r0);
        r0 = r41;
        r0 = r0.id;
        r18 = r0;
        r1 = r24;
        r13.put(r0, r1);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r20;
        r41 = r10.getRider(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r20;
        r15 = r10.getRide(r0);
        r23 = r20 + 1;
        r0 = r63;
        r1 = r24;
        r2 = r41;
        r3 = r23;
        r0.setRiderDetails(r1, r2, r15, r3);
        r20 = r20 + 1;
        goto L_0x04da;
    L_0x0530:
        r14 = 2131691735; // 0x7f0f08d7 float:1.901255E38 double:1.053195654E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 8;
        r0 = r24;
        r0.setVisibility(r14);
        r0 = r63;
        r15 = r0.mRide;
        if (r15 == 0) goto L_0x06eb;
    L_0x0546:
        r0 = r63;
        r0 = r0.mRider;
        r41 = r0;
        if (r41 == 0) goto L_0x06eb;
    L_0x054e:
        r0 = r63;
        r13 = r0.mRiderViews;
        r0 = r63;
        r0 = r0.mRider;
        r41 = r0;
        r0 = r0.id;
        r18 = r0;
        r1 = r32;
        r13.put(r0, r1);
        r0 = r63;
        r0 = r0.mRider;
        r41 = r0;
        r0 = r63;
        r15 = r0.mRide;
        r14 = 0;
        r0 = r63;
        r1 = r32;
        r2 = r41;
        r0.setRiderDetails(r1, r2, r15, r14);
    L_0x0575:
        r0 = r63;
        r0.attemptToShowMessageTooltip();
        r0 = r63;
        r10 = r0.mDrive;
        r48 = r10.getTime();
        r14 = 2131691743; // 0x7f0f08df float:1.9012566E38 double:1.0531956577E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 8;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691742; // 0x7f0f08de float:1.9012564E38 double:1.053195657E-314;
        r0 = r63;
        r32 = r0.findViewById(r14);
        r14 = 8;
        r0 = r32;
        r0.setVisibility(r14);
        r14 = 2131691741; // 0x7f0f08dd float:1.9012562E38 double:1.0531956568E-314;
        r0 = r63;
        r39 = r0.findViewById(r14);
        r14 = 8;
        r0 = r39;
        r0.setVisibility(r14);
        r14 = 2131691739; // 0x7f0f08db float:1.9012558E38 double:1.053195656E-314;
        r0 = r63;
        r50 = r0.findViewById(r14);
        r14 = 8;
        r0 = r50;
        r0.setVisibility(r14);
        r14 = 2131690251; // 0x7f0f030b float:1.900954E38 double:1.0531949206E-314;
        r0 = r63;
        r51 = r0.findViewById(r14);
        r14 = 8;
        r0 = r51;
        r0.setVisibility(r14);
        r0 = r63;
        r0.setupButtons();
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 1;
        r0 = r16;
        if (r0 != r14) goto L_0x0709;
    L_0x05e2:
        r14 = 0;
        r0 = r24;
        r0.setVisibility(r14);
        r0 = r63;
        r15 = r0.mRide;
        if (r15 == 0) goto L_0x0703;
    L_0x05ee:
        r0 = r63;
        r15 = r0.mRide;
        r0 = r15.itinerary;
        r52 = r0;
        r0 = r0.window_start_time;
        r53 = r0;
    L_0x05fa:
        r0 = r63;
        r15 = r0.mRide;
        if (r15 == 0) goto L_0x0706;
    L_0x0600:
        r0 = r63;
        r15 = r0.mRide;
        r0 = r15.itinerary;
        r52 = r0;
        r0 = r0.window_duration_seconds;
        r16 = r0;
    L_0x060c:
        r0 = r16;
        r0 = (long) r0;
        r55 = r0;
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.dropoff_to_destination_duration_seconds;
        r16 = r0;
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.pickup_to_dropoff_duration_seconds;
        r20 = r0;
        r0 = r16;
        r1 = r20;
        r0 = r0 + r1;
        r16 = r0;
        r0 = (long) r0;
        r58 = r0;
        r0 = r63;
        r1 = r24;
        r2 = r48;
        r4 = r53;
        r6 = r55;
        r8 = r58;
        r0.setRideRequestDetails(r1, r2, r4, r6, r8);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r18 = r10.getRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r19 = r10.getOriginalRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r16 = r10.getDetourMinutes();
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.detour_distance_string;
        r21 = r0;
        r0 = r63;
        r1 = r18;
        r2 = r19;
        r3 = r16;
        r4 = r21;
        r0.setDetourAndRewardDetails(r1, r2, r3, r4);
    L_0x0677:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 3;
        r0 = r16;
        if (r0 == r14) goto L_0x0691;
    L_0x0682:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        goto L_0x068c;
    L_0x0689:
        goto L_0x060c;
    L_0x068c:
        r14 = 6;
        r0 = r16;
        if (r0 != r14) goto L_0x0873;
    L_0x0691:
        r14 = 2131691757; // 0x7f0f08ed float:1.9012595E38 double:1.0531956647E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 8;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691756; // 0x7f0f08ec float:1.9012593E38 double:1.053195664E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 8;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691759; // 0x7f0f08ef float:1.9012599E38 double:1.0531956656E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 0;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691760; // 0x7f0f08f0 float:1.90126E38 double:1.053195666E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 0;
        r0 = r24;
        r0.setVisibility(r14);
    L_0x06cf:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 4;
        r0 = r16;
        if (r0 != r14) goto L_0x08f3;
    L_0x06da:
        r0 = r63;
        r12 = r0.mDidShowRateRider;
        if (r12 != 0) goto L_0x08f4;
    L_0x06e0:
        r14 = 1;
        r0 = r63;
        r0.mDidShowRateRider = r14;
        r0 = r63;
        r0.showRideRateRider();
        return;
    L_0x06eb:
        r0 = r63;
        r10 = r0.mDrive;
        r15 = r10.getRide();
        goto L_0x06f7;
    L_0x06f4:
        goto L_0x0575;
    L_0x06f7:
        r0 = r63;
        r1 = r32;
        r0.setNoRiderDetails(r1, r15);
        goto L_0x06f4;
        goto L_0x0703;
    L_0x0700:
        goto L_0x05fa;
    L_0x0703:
        r53 = -1;
        goto L_0x0700;
    L_0x0706:
        r16 = -1;
        goto L_0x0689;
    L_0x0709:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 2;
        r0 = r16;
        if (r0 == r14) goto L_0x072b;
    L_0x0714:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 7;
        r0 = r16;
        if (r0 == r14) goto L_0x072b;
    L_0x071f:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 8;
        r0 = r16;
        if (r0 != r14) goto L_0x07a9;
    L_0x072b:
        r14 = 0;
        r0 = r32;
        r0.setVisibility(r14);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.origin_to_pickup_duration_seconds;
        r16 = r0;
        r0 = (long) r0;
        r55 = r0;
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.dropoff_to_destination_duration_seconds;
        r16 = r0;
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.pickup_to_dropoff_duration_seconds;
        r20 = r0;
        r0 = r16;
        r1 = r20;
        r0 = r0 + r1;
        r16 = r0;
        r0 = (long) r0;
        r53 = r0;
        r0 = r63;
        r1 = r32;
        r2 = r48;
        r4 = r55;
        r6 = r53;
        r0.setRideScheduledDetails(r1, r2, r4, r6);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r18 = r10.getRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r19 = r10.getOriginalRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r16 = r10.getDetourMinutes();
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.detour_distance_string;
        r21 = r0;
        goto L_0x079b;
    L_0x0798:
        goto L_0x0677;
    L_0x079b:
        r0 = r63;
        r1 = r18;
        r2 = r19;
        r3 = r16;
        r4 = r21;
        r0.setDetourAndRewardDetails(r1, r2, r3, r4);
        goto L_0x0798;
    L_0x07a9:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 5;
        r0 = r16;
        if (r0 == r14) goto L_0x07bf;
    L_0x07b4:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 4;
        r0 = r16;
        if (r0 != r14) goto L_0x0814;
    L_0x07bf:
        r14 = 0;
        r0 = r39;
        r0.setVisibility(r14);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r18 = r10.getRewardString(r0);
        r0 = r63;
        r1 = r39;
        r2 = r18;
        r3 = r48;
        r0.setRideCompleteDetails(r1, r2, r3);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r18 = r10.getRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r19 = r10.getOriginalRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r16 = r10.getDetourMinutes();
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.detour_distance_string;
        r21 = r0;
        goto L_0x0806;
    L_0x0803:
        goto L_0x0677;
    L_0x0806:
        r0 = r63;
        r1 = r18;
        r2 = r19;
        r3 = r16;
        r4 = r21;
        r0.setDetourAndRewardDetails(r1, r2, r3, r4);
        goto L_0x0803;
    L_0x0814:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 3;
        r0 = r16;
        if (r0 == r14) goto L_0x082a;
    L_0x081f:
        r0 = r63;
        r0 = r0.mRideState;
        r16 = r0;
        r14 = 6;
        r0 = r16;
        if (r0 != r14) goto L_0x0677;
    L_0x082a:
        r14 = 0;
        r0 = r50;
        r0.setVisibility(r14);
        r0 = r63;
        r1 = r50;
        r2 = r48;
        r0.setRideCanceledDetails(r1, r2);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r18 = r10.getRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r63;
        r19 = r10.getOriginalRewardString(r0);
        r0 = r63;
        r10 = r0.mDrive;
        r16 = r10.getDetourMinutes();
        r0 = r63;
        r10 = r0.mDrive;
        r0 = r10.drive_match_info;
        r57 = r0;
        r0 = r0.detour_distance_string;
        r21 = r0;
        goto L_0x0865;
    L_0x0862:
        goto L_0x0677;
    L_0x0865:
        r0 = r63;
        r1 = r18;
        r2 = r19;
        r3 = r16;
        r4 = r21;
        r0.setDetourAndRewardDetails(r1, r2, r3, r4);
        goto L_0x0862;
    L_0x0873:
        r14 = 2131691759; // 0x7f0f08ef float:1.9012599E38 double:1.0531956656E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 8;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691760; // 0x7f0f08f0 float:1.90126E38 double:1.053195666E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 8;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691757; // 0x7f0f08ed float:1.9012595E38 double:1.0531956647E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 0;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691756; // 0x7f0f08ec float:1.9012593E38 double:1.053195664E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r14 = 0;
        r0 = r24;
        r0.setVisibility(r14);
        r14 = 2131691758; // 0x7f0f08ee float:1.9012597E38 double:1.053195665E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r61 = r24;
        r61 = (android.widget.TextView) r61;
        r60 = r61;
        r0 = r63;
        r0 = r0.mNm;
        r30 = r0;
        r14 = 1916; // 0x77c float:2.685E-42 double:9.466E-321;
        r0 = r30;
        r18 = r0.getLanguageString(r14);
        r0 = r60;
        r1 = r18;
        r0.setText(r1);
        r14 = 2131691757; // 0x7f0f08ed float:1.9012595E38 double:1.0531956647E-314;
        r0 = r63;
        r24 = r0.findViewById(r14);
        r62 = new com.waze.carpool.CarpoolRideDetailsActivity$5;
        r0 = r62;
        r1 = r63;
        r0.<init>();
        goto L_0x08eb;
    L_0x08e8:
        goto L_0x06cf;
    L_0x08eb:
        r0 = r24;
        r1 = r62;
        r0.setOnClickListener(r1);
        goto L_0x08e8;
    L_0x08f3:
        return;
    L_0x08f4:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRideDetailsActivity.setupActivity():void");
    }

    void setRideCompleteDetails(android.view.View r93, java.lang.String r94, long r95) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:35:0x0381 in {2, 7, 12, 17, 29, 30, 32, 34, 36, 39, 42, 45, 53, 58, 61, 64, 66, 77, 78, 79, 80, 82, 86, 95, 96, 99, 101, 104} preds:[]
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
        r92 = this;
        r7 = 2131690251; // 0x7f0f030b float:1.900954E38 double:1.0531949206E-314;
        r0 = r92;
        r6 = r0.findViewById(r7);
        r7 = 0;
        r6.setVisibility(r7);
        r7 = 2131691716; // 0x7f0f08c4 float:1.9012512E38 double:1.0531956444E-314;
        r0 = r92;
        r6 = r0.findViewById(r7);
        r9 = r6;
        r9 = (android.widget.TextView) r9;
        r8 = r9;
        r7 = 1930; // 0x78a float:2.705E-42 double:9.535E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r7);
        r8.setText(r10);
        r7 = 2131691717; // 0x7f0f08c5 float:1.9012514E38 double:1.053195645E-314;
        r0 = r92;
        r6 = r0.findViewById(r7);
        r11 = r6;
        r11 = (android.widget.TextView) r11;
        r8 = r11;
        r0 = r94;
        r8.setText(r0);
        r7 = 1909; // 0x775 float:2.675E-42 double:9.43E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r7);
        r14 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r12 = r14 * r95;
        r0 = r92;
        r10 = com.waze.utils.DisplayUtils.getDayTimeString(r0, r12, r10);
        r7 = 2131691740; // 0x7f0f08dc float:1.901256E38 double:1.0531956563E-314;
        r0 = r93;
        r93 = r0.findViewById(r7);
        r16 = r93;
        r16 = (android.widget.TextView) r16;
        r8 = r16;
        r8.setText(r10);
        r7 = 2131691746; // 0x7f0f08e2 float:1.9012573E38 double:1.053195659E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 2131691750; // 0x7f0f08e6 float:1.901258E38 double:1.053195661E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r17 = java.util.Calendar.getInstance();
        r0 = r17;
        r18 = r0.getTimeZone();
        r19 = com.waze.settings.SettingsNativeManager.getInstance();
        r20 = new java.util.Locale;
        r0 = r19;
        r10 = r0.getLanguagesLocaleNTV();
        r0 = r20;
        r0.<init>(r10);
        r7 = 2;
        r0 = r20;
        r21 = java.text.SimpleDateFormat.getDateInstance(r7, r0);
        r0 = r21;
        r1 = r18;
        r0.setTimeZone(r1);
        r22 = new java.util.Date;
        r14 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r12 = r14 * r95;
        r0 = r22;
        r0.<init>(r12);
        r0 = r21;
        r1 = r22;
        r10 = r0.format(r1);
        r22 = new java.util.Date;
        r14 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r12 = r14 * r95;
        r0 = r22;
        r0.<init>(r12);
        r0 = r92;
        r21 = android.text.format.DateFormat.getTimeFormat(r0);
        r0 = r21;
        r1 = r18;
        r0.setTimeZone(r1);
        r0 = r21;
        r1 = r22;
        r23 = r0.format(r1);
        r24 = new java.text.SimpleDateFormat;
        r25 = com.waze.NativeManager.getInstance();
        r0 = r25;
        r26 = r0.getLocale();
        r27 = "EEE";
        r0 = r24;
        r1 = r27;
        r2 = r26;
        r0.<init>(r1, r2);
        r14 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r95 = r14 * r95;
        r0 = r95;
        r28 = java.lang.Long.valueOf(r0);
        r0 = r24;
        r1 = r28;
        r29 = r0.format(r1);
        r7 = 2131691709; // 0x7f0f08bd float:1.9012498E38 double:1.053195641E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r30 = r93;
        r30 = (android.widget.TextView) r30;
        r8 = r30;
        r7 = 3;
        r0 = new java.lang.Object[r7];
        r31 = r0;
        r7 = 0;
        r31[r7] = r29;
        r7 = 1;
        r31[r7] = r10;
        r7 = 2;
        r31[r7] = r23;
        r27 = "%s, %s, %s";
        r0 = r27;
        r1 = r31;
        r10 = java.lang.String.format(r0, r1);
        r8.setText(r10);
        r0 = r92;
        r0 = r0.mDrive;
        r32 = r0;
        r0 = r0.itinerary;
        r33 = r0;
        r0 = r0.free_offer;
        r34 = r0;
        if (r34 == 0) goto L_0x0344;
    L_0x0136:
        r7 = 2131691710; // 0x7f0f08be float:1.90125E38 double:1.0531956414E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r35 = r93;
        r35 = (android.widget.TextView) r35;
        r8 = r35;
        r7 = 1927; // 0x787 float:2.7E-42 double:9.52E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r7);
        r8.setText(r10);
        r7 = 2131691712; // 0x7f0f08c0 float:1.9012504E38 double:1.0531956424E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r36 = r93;
        r36 = (android.widget.TextView) r36;
        r8 = r36;
        r0 = r94;
        r8.setText(r0);
        r7 = 2131691711; // 0x7f0f08bf float:1.9012502E38 double:1.053195642E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 0;
        r0 = r93;
        r0.setVisibility(r7);
    L_0x0171:
        r7 = 70;
        r34 = com.waze.config.ConfigValues.getBoolValue(r7);
        if (r34 == 0) goto L_0x038d;
    L_0x0179:
        r0 = r92;
        r0 = r0.mDrive;
        r32 = r0;
        r0 = r0.itinerary;
        r33 = r0;
        r0 = r0.free_offer;
        r34 = r0;
        if (r34 != 0) goto L_0x038d;
    L_0x0189:
        r7 = 2131691713; // 0x7f0f08c1 float:1.9012506E38 double:1.053195643E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 0;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 71;
        r37 = com.waze.config.ConfigValues.getIntValue(r7);
        r7 = 2131691714; // 0x7f0f08c2 float:1.9012508E38 double:1.0531956434E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r38 = r93;
        r38 = (android.widget.TextView) r38;
        r8 = r38;
        r7 = 1;
        r0 = new java.lang.Object[r7];
        r31 = r0;
        r0 = r37;
        r39 = java.lang.Integer.valueOf(r0);
        r7 = 0;
        r31[r7] = r39;
        r7 = 1928; // 0x788 float:2.702E-42 double:9.526E-321;
        r0 = r31;
        r94 = com.waze.strings.DisplayStrings.displayStringF(r7, r0);
        r40 = new com.waze.utils.ExtHtmlTagHandler;
        r0 = r40;
        r0.<init>();
        r42 = r40;
        r42 = (android.text.Html.TagHandler) r42;
        r41 = r42;
        r44 = 0;
        r0 = r94;
        r1 = r44;
        r2 = r41;
        r43 = android.text.Html.fromHtml(r0, r1, r2);
        r0 = r43;
        r8.setText(r0);
        r7 = 2131691715; // 0x7f0f08c3 float:1.901251E38 double:1.053195644E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r45 = r93;
        r45 = (android.widget.TextView) r45;
        r8 = r45;
        r7 = 1929; // 0x789 float:2.703E-42 double:9.53E-321;
        r94 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r94;
        r8.setText(r0);
    L_0x01fb:
        r0 = r92;
        r0 = r0.mCpnm;
        r46 = r0;
        r0.getCarpoolProfileNTV();
        r7 = 2131691719; // 0x7f0f08c7 float:1.9012518E38 double:1.053195646E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r47 = r93;
        r47 = (android.widget.TextView) r47;
        r8 = r47;
        r7 = 1925; // 0x785 float:2.697E-42 double:9.51E-321;
        r94 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r94;
        r8.setText(r0);
        r7 = 2131691720; // 0x7f0f08c8 float:1.901252E38 double:1.0531956464E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r49 = r93;
        r49 = (android.widget.TextView) r49;
        r48 = r49;
        r7 = 8;
        r0 = r48;
        r0.setVisibility(r7);
        r50 = com.waze.ConfigManager.getInstance();
        r7 = 15;
        r0 = r50;
        r34 = r0.getConfigValueBool(r7);
        if (r34 == 0) goto L_0x03b3;
    L_0x0242:
        r0 = r92;
        r0 = r0.mCpnm;
        r46 = r0;
        r51 = r0.getCachedPayeeNTV();
        if (r51 == 0) goto L_0x03a2;
    L_0x024e:
        r7 = 0;
        r0 = r48;
        r0.setVisibility(r7);
        r0 = r92;
        r0 = r0.mCpnm;
        r46 = r0;
        r0 = r51;
        r0 = r0.unpaid_balance;
        r37 = r0;
        r0 = r92;
        r0 = r0.mDrive;
        r32 = r0;
        r52 = r0.getPickupLocation();
        r0 = r52;
        r0 = r0.country_code;
        r94 = r0;
        r44 = 0;
        r0 = r46;
        r1 = r92;
        r2 = r37;
        r3 = r94;
        r4 = r44;
        r94 = r0.centsToString(r1, r2, r3, r4);
        r0 = r48;
        r1 = r94;
        r0.setText(r1);
        r44 = 0;
        r0 = r48;
        r1 = r44;
        r0.setBackgroundDrawable(r1);
    L_0x0290:
        r53 = new com.waze.carpool.CarpoolRideDetailsActivity$23;
        r0 = r53;
        r1 = r92;
        r0.<init>();
        r0 = r48;
        r1 = r53;
        r0.setOnClickListener(r1);
        r7 = 2131691718; // 0x7f0f08c6 float:1.9012516E38 double:1.0531956454E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r54 = r93;
        r54 = (android.widget.TextView) r54;
        r8 = r54;
        r34 = com.waze.carpool.CarpoolUtils.areBankDetailsAvailable();
        if (r34 != 0) goto L_0x03c4;
    L_0x02b5:
        r7 = 8;
        r34 = com.waze.config.ConfigValues.getBoolValue(r7);
        if (r34 == 0) goto L_0x03c4;
    L_0x02bd:
        r7 = 0;
        r8.setVisibility(r7);
        r7 = 1941; // 0x795 float:2.72E-42 double:9.59E-321;
        r94 = com.waze.strings.DisplayStrings.displayString(r7);
        r55 = new android.text.SpannableString;
        r56 = r55;
        r0 = r55;
        r1 = r94;
        r0.<init>(r1);
        r57 = new android.text.style.UnderlineSpan;
        r0 = r57;
        r0.<init>();
        r0 = r94;
        r37 = r0.length();
        r7 = 0;
        r58 = 0;
        r0 = r56;
        r1 = r57;
        r2 = r37;
        r3 = r58;
        r0.setSpan(r1, r7, r2, r3);
        r0 = r56;
        r8.setText(r0);
        r59 = new com.waze.carpool.CarpoolRideDetailsActivity$24;
        r0 = r59;
        r1 = r92;
        r0.<init>();
        r0 = r59;
        r8.setOnClickListener(r0);
    L_0x0300:
        r60 = 0;
        r61 = 0;
        r37 = 0;
        r0 = r92;
        r0 = r0.mDrive;
        r32 = r0;
        r34 = r0.isMultiPax();
        if (r34 == 0) goto L_0x03cd;
    L_0x0312:
        r0 = r92;
        r0 = r0.mDrive;
        r32 = r0;
        r62 = r0.getRides();
        r0 = r62;
        r0 = r0.length;
        r63 = r0;
        r64 = 0;
    L_0x0323:
        r0 = r64;
        r1 = r63;
        if (r0 >= r1) goto L_0x03e9;
    L_0x0329:
        r65 = r62[r64];
        r0 = r65;
        r0 = r0.rider_endorsement;
        r66 = r0;
        r7 = 1;
        r0 = r66;
        if (r0 < r7) goto L_0x0341;
    L_0x0336:
        r7 = 6;
        r0 = r66;
        if (r0 > r7) goto L_0x0341;
    L_0x033b:
        r37 = r37 + 1;
        if (r60 != 0) goto L_0x03ca;
    L_0x033f:
        r60 = r65;
    L_0x0341:
        r64 = r64 + 1;
        goto L_0x0323;
    L_0x0344:
        r7 = 2131691710; // 0x7f0f08be float:1.90125E38 double:1.0531956414E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r67 = r93;
        r67 = (android.widget.TextView) r67;
        r8 = r67;
        r7 = 1926; // 0x786 float:2.699E-42 double:9.516E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r7);
        r8.setText(r10);
        goto L_0x0360;
    L_0x035d:
        goto L_0x0300;
    L_0x0360:
        r7 = 2131691712; // 0x7f0f08c0 float:1.9012504E38 double:1.0531956424E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r68 = r93;
        r68 = (android.widget.TextView) r68;
        r8 = r68;
        r0 = r94;
        r8.setText(r0);
        r7 = 2131691711; // 0x7f0f08bf float:1.9012502E38 double:1.053195642E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        goto L_0x0385;
    L_0x037e:
        goto L_0x0171;
        goto L_0x0385;
    L_0x0382:
        goto L_0x0341;
    L_0x0385:
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        goto L_0x037e;
    L_0x038d:
        r7 = 2131691713; // 0x7f0f08c1 float:1.9012506E38 double:1.053195643E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        goto L_0x039a;
    L_0x0397:
        goto L_0x01fb;
    L_0x039a:
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        goto L_0x0397;
    L_0x03a2:
        r7 = 8;
        r0 = r48;
        r0.setVisibility(r7);
        goto L_0x03ad;
    L_0x03aa:
        goto L_0x0290;
    L_0x03ad:
        r7 = 8;
        r8.setVisibility(r7);
        goto L_0x03aa;
    L_0x03b3:
        r7 = 8;
        r0 = r48;
        r0.setVisibility(r7);
        goto L_0x03be;
    L_0x03bb:
        goto L_0x0290;
    L_0x03be:
        r7 = 8;
        r8.setVisibility(r7);
        goto L_0x03bb;
    L_0x03c4:
        r7 = 8;
        r8.setVisibility(r7);
        goto L_0x035d;
    L_0x03ca:
        r61 = r65;
        goto L_0x0382;
    L_0x03cd:
        r0 = r92;
        r0 = r0.mRide;
        r65 = r0;
        r0 = r0.rider_endorsement;
        r63 = r0;
        r7 = 1;
        r0 = r63;
        if (r0 < r7) goto L_0x03e9;
    L_0x03dc:
        r7 = 6;
        r0 = r63;
        if (r0 > r7) goto L_0x03e9;
    L_0x03e1:
        r37 = 1;
        r0 = r92;
        r0 = r0.mRide;
        r60 = r0;
    L_0x03e9:
        r7 = 1;
        r0 = r37;
        if (r0 <= r7) goto L_0x0580;
    L_0x03ee:
        r7 = 2131691724; // 0x7f0f08cc float:1.9012528E38 double:1.0531956484E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 2;
        r0 = r37;
        if (r0 != r7) goto L_0x0534;
    L_0x0403:
        r7 = 2131691723; // 0x7f0f08cb float:1.9012526E38 double:1.053195648E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r69 = r93;
        r69 = (android.widget.TextView) r69;
        r8 = r69;
        r7 = 2;
        r0 = new java.lang.Object[r7];
        r31 = r0;
        r0 = r60;
        r70 = r0.getRider();
        r0 = r70;
        r94 = r0.getFirstName();
        r7 = 0;
        r31[r7] = r94;
        r0 = r61;
        r70 = r0.getRider();
        r0 = r70;
        r94 = r0.getFirstName();
        r7 = 1;
        r31[r7] = r94;
        r7 = 1932; // 0x78c float:2.707E-42 double:9.545E-321;
        r0 = r31;
        r94 = com.waze.strings.DisplayStrings.displayStringF(r7, r0);
        r0 = r94;
        r8.setText(r0);
    L_0x0442:
        r7 = 2131691727; // 0x7f0f08cf float:1.9012534E38 double:1.05319565E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r72 = r93;
        r72 = (android.widget.LinearLayout) r72;
        r71 = r72;
        r7 = 2131691728; // 0x7f0f08d0 float:1.9012536E38 double:1.0531956503E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r74 = r93;
        r74 = (android.widget.LinearLayout) r74;
        r73 = r74;
        r0 = r71;
        r0.removeAllViews();
        r7 = 3;
        r0 = r37;
        if (r0 > r7) goto L_0x0563;
    L_0x046a:
        r63 = r37;
        r64 = 0;
    L_0x046e:
        r7 = 0;
        r0 = r71;
        r0.setVisibility(r7);
        r0 = r71;
        r0.removeAllViews();
        r75 = new android.widget.Space;
        r0 = r75;
        r1 = r92;
        r0.<init>(r1);
        r76 = new android.widget.LinearLayout$LayoutParams;
        r7 = 0;
        r58 = 0;
        r77 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r76;
        r1 = r58;
        r2 = r77;
        r0.<init>(r7, r1, r2);
        r0 = r71;
        r1 = r75;
        r2 = r76;
        r0.addView(r1, r2);
        if (r64 <= 0) goto L_0x0568;
    L_0x049e:
        r7 = 0;
        r0 = r73;
        r0.setVisibility(r7);
        r0 = r73;
        r0.removeAllViews();
        r75 = new android.widget.Space;
        r0 = r75;
        r1 = r92;
        r0.<init>(r1);
        r76 = new android.widget.LinearLayout$LayoutParams;
        r7 = 0;
        r58 = 0;
        r77 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r76;
        r1 = r58;
        r2 = r77;
        r0.<init>(r7, r1, r2);
        r0 = r73;
        r1 = r75;
        r2 = r76;
        r0.addView(r1, r2);
    L_0x04cc:
        r0 = r92;
        r78 = r0.getResources();
        r7 = 2130837883; // 0x7f02017b float:1.7280733E38 double:1.052773795E-314;
        r0 = r78;
        r79 = r0.getDrawable(r7);
        r0 = r79;
        r64 = r0.getIntrinsicWidth();
        r66 = 0;
        r0 = r92;
        r80 = r0.getLayoutInflater();
        r0 = r92;
        r0 = r0.mDrive;
        r32 = r0;
        goto L_0x04f3;
    L_0x04f0:
        goto L_0x046e;
    L_0x04f3:
        r62 = r0.getRides();
        r0 = r62;
        r0 = r0.length;
        r81 = r0;
        r82 = 0;
    L_0x04fe:
        r0 = r82;
        r1 = r81;
        if (r0 >= r1) goto L_0x063b;
    L_0x0504:
        r61 = r62[r82];
        r0 = r61;
        r0 = r0.rider_endorsement;
        r83 = r0;
        r7 = 1;
        r0 = r83;
        if (r0 < r7) goto L_0x0531;
    L_0x0511:
        r7 = 6;
        r0 = r83;
        if (r0 > r7) goto L_0x0531;
    L_0x0516:
        r0 = r66;
        r1 = r63;
        if (r0 >= r1) goto L_0x0570;
    L_0x051c:
        goto L_0x0520;
    L_0x051d:
        goto L_0x04cc;
    L_0x0520:
        r0 = r92;
        r1 = r71;
        r2 = r80;
        r3 = r61;
        r4 = r83;
        r5 = r64;
        r0.createMpaxEndorsement(r1, r2, r3, r4, r5);
    L_0x052f:
        r66 = r66 + 1;
    L_0x0531:
        r82 = r82 + 1;
        goto L_0x04fe;
    L_0x0534:
        r7 = 2131691723; // 0x7f0f08cb float:1.9012526E38 double:1.053195648E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r84 = r93;
        r84 = (android.widget.TextView) r84;
        r8 = r84;
        r7 = 1;
        r0 = new java.lang.Object[r7];
        r31 = r0;
        r0 = r37;
        r39 = java.lang.Integer.valueOf(r0);
        r7 = 0;
        r31[r7] = r39;
        r7 = 1933; // 0x78d float:2.709E-42 double:9.55E-321;
        r0 = r31;
        r94 = com.waze.strings.DisplayStrings.displayStringF(r7, r0);
        goto L_0x055d;
    L_0x055a:
        goto L_0x0442;
    L_0x055d:
        r0 = r94;
        r8.setText(r0);
        goto L_0x055a;
    L_0x0563:
        r64 = r37 / 2;
        r63 = r37 - r64;
        goto L_0x04f0;
    L_0x0568:
        r7 = 8;
        r0 = r73;
        r0.setVisibility(r7);
        goto L_0x051d;
    L_0x0570:
        r0 = r92;
        r1 = r73;
        r2 = r80;
        r3 = r61;
        r4 = r83;
        r5 = r64;
        r0.createMpaxEndorsement(r1, r2, r3, r4, r5);
        goto L_0x052f;
    L_0x0580:
        r7 = 1;
        r0 = r37;
        if (r0 != r7) goto L_0x063b;
    L_0x0585:
        r7 = 2131691728; // 0x7f0f08d0 float:1.9012536E38 double:1.0531956503E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 2131691727; // 0x7f0f08cf float:1.9012534E38 double:1.05319565E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r0 = r60;
        r0 = r0.rider_endorsement;
        r63 = r0;
        r7 = 1;
        r0 = r63;
        if (r0 < r7) goto L_0x063b;
    L_0x05b0:
        r7 = 6;
        r0 = r63;
        if (r0 > r7) goto L_0x063b;
    L_0x05b5:
        r7 = 2131691722; // 0x7f0f08ca float:1.9012524E38 double:1.0531956474E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 2131691724; // 0x7f0f08cc float:1.9012528E38 double:1.0531956484E-314;
        r0 = r93;
        r6 = r0.findViewById(r7);
        r7 = 0;
        r6.setVisibility(r7);
        r7 = 2131691723; // 0x7f0f08cb float:1.9012526E38 double:1.053195648E-314;
        r0 = r93;
        r6 = r0.findViewById(r7);
        r85 = r6;
        r85 = (android.widget.TextView) r85;
        r8 = r85;
        r7 = 1;
        r0 = new java.lang.Object[r7];
        r31 = r0;
        r0 = r60;
        r70 = r0.getRider();
        if (r70 == 0) goto L_0x06a3;
    L_0x05e7:
        r0 = r60;
        r70 = r0.getRider();
        r0 = r70;
        r94 = r0.getFirstName();
    L_0x05f3:
        r7 = 0;
        r31[r7] = r94;
        r7 = 1931; // 0x78b float:2.706E-42 double:9.54E-321;
        r0 = r31;
        r94 = com.waze.strings.DisplayStrings.displayStringF(r7, r0);
        r0 = r94;
        r8.setText(r0);
        r7 = 2131691725; // 0x7f0f08cd float:1.901253E38 double:1.053195649E-314;
        r0 = r93;
        r6 = r0.findViewById(r7);
        r87 = r6;
        r87 = (android.widget.ImageView) r87;
        r86 = r87;
        r88 = com.waze.carpool.CarpoolEndorsement.icon;
        r64 = r88[r63];
        r0 = r86;
        r1 = r64;
        r0.setImageResource(r1);
        r7 = 2131691726; // 0x7f0f08ce float:1.9012532E38 double:1.0531956493E-314;
        r0 = r93;
        r93 = r0.findViewById(r7);
        r89 = r93;
        r89 = (android.widget.TextView) r89;
        r8 = r89;
        r88 = com.waze.carpool.CarpoolEndorsement.name;
        r63 = r88[r63];
        r0 = r63;
        r94 = com.waze.strings.DisplayStrings.displayString(r0);
        r0 = r94;
        r8.setText(r0);
    L_0x063b:
        if (r37 <= 0) goto L_0x06aa;
    L_0x063d:
        r7 = 2131691721; // 0x7f0f08c9 float:1.9012522E38 double:1.053195647E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 0;
        r0 = r93;
        r0.setVisibility(r7);
        goto L_0x0650;
    L_0x064d:
        goto L_0x05f3;
    L_0x0650:
        r7 = 2131691729; // 0x7f0f08d1 float:1.9012538E38 double:1.053195651E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r90 = r93;
        r90 = (android.widget.TextView) r90;
        r8 = r90;
        r7 = 1934; // 0x78e float:2.71E-42 double:9.555E-321;
        r94 = com.waze.strings.DisplayStrings.displayString(r7);
        r55 = new android.text.SpannableString;
        r56 = r55;
        r0 = r55;
        r1 = r94;
        r0.<init>(r1);
        r57 = new android.text.style.UnderlineSpan;
        r0 = r57;
        r0.<init>();
        r0 = r94;
        r37 = r0.length();
        r7 = 0;
        r58 = 0;
        r0 = r56;
        r1 = r57;
        r2 = r37;
        r3 = r58;
        r0.setSpan(r1, r7, r2, r3);
        r7 = 0;
        r8.setVisibility(r7);
        r0 = r56;
        r8.setText(r0);
        r91 = new com.waze.carpool.CarpoolRideDetailsActivity$25;
        r0 = r91;
        r1 = r92;
        r0.<init>();
        r0 = r91;
        r8.setOnClickListener(r0);
        return;
    L_0x06a3:
        r7 = 2635; // 0xa4b float:3.692E-42 double:1.302E-320;
        r94 = com.waze.strings.DisplayStrings.displayString(r7);
        goto L_0x064d;
    L_0x06aa:
        r7 = 2131691721; // 0x7f0f08c9 float:1.9012522E38 double:1.053195647E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 2131691722; // 0x7f0f08ca float:1.9012524E38 double:1.0531956474E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 2131691729; // 0x7f0f08d1 float:1.9012538E38 double:1.053195651E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 2131691728; // 0x7f0f08d0 float:1.9012536E38 double:1.0531956503E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        r7 = 2131691727; // 0x7f0f08cf float:1.9012534E38 double:1.05319565E-314;
        r0 = r92;
        r93 = r0.findViewById(r7);
        r7 = 8;
        r0 = r93;
        r0.setVisibility(r7);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRideDetailsActivity.setRideCompleteDetails(android.view.View, java.lang.String, long):void");
    }

    void setRouteDetails(java.lang.String r40, int r41, java.lang.String r42, java.lang.String r43, java.lang.String r44, int r45) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:74:0x0231
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
        r39 = this;
        r3 = 2131691755; // 0x7f0f08eb float:1.901259E38 double:1.0531956637E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r4 = r2.getViewTreeObserver();
        r5 = new com.waze.carpool.CarpoolRideDetailsActivity$28;
        r0 = r39;
        r5.<init>(r2);
        r4.addOnGlobalLayoutListener(r5);
        r3 = 2131691730; // 0x7f0f08d2 float:1.901254E38 double:1.0531956513E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r7 = r2;
        r7 = (android.widget.ImageView) r7;
        r6 = r7;
        r0 = r39;
        r8 = r0.mRide;
        if (r8 == 0) goto L_0x0034;
    L_0x002a:
        r0 = r39;
        r8 = r0.mRide;
        r9 = r8.isCanceled();
        if (r9 == 0) goto L_0x0235;
    L_0x0034:
        r10 = 1;
    L_0x0035:
        r0 = r39;
        r0 = r0.mRideState;
        r41 = r0;
        r3 = 4;
        r0 = r41;
        if (r0 == r3) goto L_0x004b;
    L_0x0040:
        r0 = r39;
        r0 = r0.mRideState;
        r41 = r0;
        r3 = 5;
        r0 = r41;
        if (r0 != r3) goto L_0x023b;
    L_0x004b:
        r9 = 1;
    L_0x004c:
        if (r9 == 0) goto L_0x023d;
    L_0x004e:
        r3 = 8;
        r6.setVisibility(r3);
    L_0x0053:
        r3 = 2131691731; // 0x7f0f08d3 float:1.9012542E38 double:1.053195652E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r12 = r2;
        r12 = (android.widget.TextView) r12;
        r11 = r12;
        if (r9 == 0) goto L_0x0290;
    L_0x0064:
        r13 = 1921; // 0x781 float:2.692E-42 double:9.49E-321;
    L_0x0066:
        r14 = com.waze.strings.DisplayStrings.displayString(r13);
        r11.setText(r14);
        r3 = 2131691732; // 0x7f0f08d4 float:1.9012544E38 double:1.0531956523E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r16 = r2;
        r16 = (android.widget.TextView) r16;
        r15 = r16;
        r0 = r39;
        r0 = r0.destinationType;
        r41 = r0;
        r3 = 1;
        r0 = r41;
        if (r0 != r3) goto L_0x029a;
    L_0x0087:
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        if (r9 == 0) goto L_0x0297;
    L_0x008f:
        r13 = 1922; // 0x782 float:2.693E-42 double:9.496E-321;
    L_0x0091:
        r0 = r17;
        r14 = r0.getLanguageString(r13);
        r15.setText(r14);
        r3 = 0;
        r15.setVisibility(r3);
    L_0x009e:
        r0 = r39;
        r0 = r0.mDrive;
        r18 = r0;
        r0 = r39;
        r8 = r0.mRide;
        r0 = r18;
        r41 = r0.getState(r8);
        r3 = 10;
        r0 = r41;
        if (r0 == r3) goto L_0x00c0;
    L_0x00b4:
        r3 = 16;
        r0 = r41;
        if (r0 == r3) goto L_0x00c0;
    L_0x00ba:
        r3 = 8;
        r0 = r41;
        if (r0 != r3) goto L_0x0102;
    L_0x00c0:
        r3 = 10;
        r0 = r41;
        if (r0 == r3) goto L_0x00cc;
    L_0x00c6:
        r3 = 16;
        r0 = r41;
        if (r0 != r3) goto L_0x02e7;
    L_0x00cc:
        r3 = 3358; // 0xd1e float:4.706E-42 double:1.659E-320;
        r14 = com.waze.strings.DisplayStrings.displayString(r3);
        r11.setText(r14);
        r0 = r39;
        r0 = r0.mDrive;
        r18 = r0;
        r19 = r0.getPickupLocation();
        if (r19 == 0) goto L_0x02df;
    L_0x00e1:
        r0 = r19;
        r14 = r0.placeName;
    L_0x00e5:
        if (r14 == 0) goto L_0x00ed;
    L_0x00e7:
        r9 = r14.isEmpty();
        if (r9 == 0) goto L_0x00f3;
    L_0x00ed:
        if (r19 == 0) goto L_0x02e5;
    L_0x00ef:
        r0 = r19;
        r14 = r0.address;
    L_0x00f3:
        if (r14 == 0) goto L_0x031b;
    L_0x00f5:
        r9 = r14.isEmpty();
        if (r9 != 0) goto L_0x031b;
    L_0x00fb:
        r15.setText(r14);
        r3 = 0;
        r15.setVisibility(r3);
    L_0x0102:
        r3 = 2131691817; // 0x7f0f0929 float:1.9012717E38 double:1.0531956943E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r20 = r2;
        r20 = (android.widget.TextView) r20;
        r11 = r20;
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2026; // 0x7ea float:2.839E-42 double:1.001E-320;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        r11.setText(r14);
        r0 = r39;
        r0 = r0.mDrive;
        r18 = r0;
        r9 = r0.isMultiPax();
        if (r9 == 0) goto L_0x0321;
    L_0x012e:
        r3 = 2131691813; // 0x7f0f0925 float:1.9012708E38 double:1.0531956923E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r21 = r2;
        r21 = (android.widget.TextView) r21;
        r11 = r21;
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2025; // 0x7e9 float:2.838E-42 double:1.0005E-320;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        r11.setText(r14);
    L_0x014e:
        r3 = 2131691803; // 0x7f0f091b float:1.9012688E38 double:1.0531956874E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r22 = r2;
        r22 = (android.widget.TextView) r22;
        r11 = r22;
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2015; // 0x7df float:2.824E-42 double:9.955E-321;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        r11.setText(r14);
        r0 = r39;
        r0 = r0.mDrive;
        r18 = r0;
        r9 = r0.isMultiPax();
        if (r9 == 0) goto L_0x038b;
    L_0x017a:
        r3 = 2131691808; // 0x7f0f0920 float:1.9012698E38 double:1.05319569E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r23 = r2;
        r23 = (android.widget.TextView) r23;
        r11 = r23;
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2021; // 0x7e5 float:2.832E-42 double:9.985E-321;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        r11.setText(r14);
    L_0x019a:
        r3 = 2131691818; // 0x7f0f092a float:1.9012719E38 double:1.053195695E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r24 = r2;
        r24 = (android.widget.TextView) r24;
        r11 = r24;
        if (r44 == 0) goto L_0x03f9;
    L_0x01ab:
        r0 = r44;
        r9 = r0.isEmpty();
        if (r9 != 0) goto L_0x03f9;
    L_0x01b3:
        r0 = r44;
        r11.setText(r0);
        r3 = 0;
        r11.setVisibility(r3);
    L_0x01bc:
        r3 = 2131691814; // 0x7f0f0926 float:1.901271E38 double:1.053195693E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r25 = r2;
        r25 = (android.widget.TextView) r25;
        r11 = r25;
        if (r43 == 0) goto L_0x03ff;
    L_0x01cd:
        r0 = r43;
        r9 = r0.isEmpty();
        if (r9 != 0) goto L_0x03ff;
    L_0x01d5:
        r0 = r43;
        r11.setText(r0);
    L_0x01da:
        r3 = 2131691804; // 0x7f0f091c float:1.901269E38 double:1.053195688E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r26 = r2;
        r26 = (android.widget.TextView) r26;
        r11 = r26;
        if (r40 == 0) goto L_0x0413;
    L_0x01eb:
        r0 = r40;
        r9 = r0.isEmpty();
        if (r9 != 0) goto L_0x0413;
    L_0x01f3:
        r3 = 0;
        r11.setVisibility(r3);
        r0 = r40;
        r11.setText(r0);
    L_0x01fc:
        r3 = 2131691809; // 0x7f0f0921 float:1.90127E38 double:1.0531956903E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r27 = r2;
        r27 = (android.widget.TextView) r27;
        r11 = r27;
        if (r42 == 0) goto L_0x0419;
    L_0x020d:
        r0 = r42;
        r9 = r0.isEmpty();
        if (r9 != 0) goto L_0x0419;
    L_0x0215:
        r0 = r42;
        r11.setText(r0);
    L_0x021a:
        r0 = r39;
        r0 = r0.mRideState;
        r41 = r0;
        r3 = 3;
        r0 = r41;
        if (r0 == r3) goto L_0x0486;
    L_0x0225:
        r0 = r39;
        r0 = r0.mRideState;
        r41 = r0;
        r3 = 6;
        r0 = r41;
        if (r0 != r3) goto L_0x0429;
    L_0x0230:
        return;
        goto L_0x0235;
    L_0x0232:
        goto L_0x0035;
    L_0x0235:
        r10 = 0;
        goto L_0x0232;
        goto L_0x023b;
    L_0x0238:
        goto L_0x004c;
    L_0x023b:
        r9 = 0;
        goto L_0x0238;
    L_0x023d:
        r0 = r39;
        r0 = r0.destinationType;
        r41 = r0;
        r3 = 1;
        r0 = r41;
        if (r0 != r3) goto L_0x025b;
    L_0x0248:
        if (r10 == 0) goto L_0x0257;
    L_0x024a:
        r41 = 2130837841; // 0x7f020151 float:1.7280647E38 double:1.052773774E-314;
        goto L_0x0251;
    L_0x024e:
        goto L_0x0053;
    L_0x0251:
        r0 = r41;
        r6.setImageResource(r0);
        goto L_0x024e;
    L_0x0257:
        r41 = 2130837901; // 0x7f02018d float:1.728077E38 double:1.0527738037E-314;
        goto L_0x0251;
    L_0x025b:
        r0 = r39;
        r0 = r0.destinationType;
        r41 = r0;
        r3 = 3;
        r0 = r41;
        if (r0 != r3) goto L_0x0279;
    L_0x0266:
        if (r10 == 0) goto L_0x0275;
    L_0x0268:
        r41 = 2130837845; // 0x7f020155 float:1.7280656E38 double:1.052773776E-314;
        goto L_0x026f;
    L_0x026c:
        goto L_0x0053;
    L_0x026f:
        r0 = r41;
        r6.setImageResource(r0);
        goto L_0x026c;
    L_0x0275:
        r41 = 2130837904; // 0x7f020190 float:1.7280775E38 double:1.052773805E-314;
        goto L_0x026f;
    L_0x0279:
        if (r10 == 0) goto L_0x0288;
    L_0x027b:
        r41 = 2130837843; // 0x7f020153 float:1.7280652E38 double:1.052773775E-314;
        goto L_0x0282;
    L_0x027f:
        goto L_0x0053;
    L_0x0282:
        r0 = r41;
        r6.setImageResource(r0);
        goto L_0x027f;
    L_0x0288:
        r41 = 2130837902; // 0x7f02018e float:1.7280771E38 double:1.052773804E-314;
        goto L_0x0282;
        goto L_0x0290;
    L_0x028d:
        goto L_0x0066;
    L_0x0290:
        r13 = 1910; // 0x776 float:2.676E-42 double:9.437E-321;
        goto L_0x028d;
        goto L_0x0297;
    L_0x0294:
        goto L_0x0091;
    L_0x0297:
        r13 = 1912; // 0x778 float:2.679E-42 double:9.447E-321;
        goto L_0x0294;
    L_0x029a:
        if (r44 == 0) goto L_0x02d5;
    L_0x029c:
        r0 = r44;
        r10 = r0.isEmpty();
        if (r10 != 0) goto L_0x02d5;
    L_0x02a4:
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        if (r9 == 0) goto L_0x02ce;
    L_0x02ac:
        r13 = 1924; // 0x784 float:2.696E-42 double:9.506E-321;
    L_0x02ae:
        r0 = r17;
        r14 = r0.getLanguageString(r13);
        r3 = 1;
        r0 = new java.lang.Object[r3];
        r28 = r0;
        r3 = 0;
        r28[r3] = r44;
        r0 = r28;
        r14 = java.lang.String.format(r14, r0);
        r15.setText(r14);
        goto L_0x02c9;
    L_0x02c6:
        goto L_0x009e;
    L_0x02c9:
        r3 = 0;
        r15.setVisibility(r3);
        goto L_0x02c6;
    L_0x02ce:
        r13 = 1911; // 0x777 float:2.678E-42 double:9.44E-321;
        goto L_0x02ae;
        goto L_0x02d5;
    L_0x02d2:
        goto L_0x009e;
    L_0x02d5:
        r3 = 8;
        r15.setVisibility(r3);
        goto L_0x02d2;
        goto L_0x02df;
    L_0x02dc:
        goto L_0x00e5;
    L_0x02df:
        r14 = 0;
        goto L_0x02dc;
        goto L_0x02e5;
    L_0x02e2:
        goto L_0x00f3;
    L_0x02e5:
        r14 = 0;
        goto L_0x02e2;
    L_0x02e7:
        r3 = 3359; // 0xd1f float:4.707E-42 double:1.6596E-320;
        r14 = com.waze.strings.DisplayStrings.displayString(r3);
        r11.setText(r14);
        r0 = r39;
        r0 = r0.mDrive;
        r18 = r0;
        r19 = r0.getDropOffLocation();
        if (r19 == 0) goto L_0x0313;
    L_0x02fc:
        r0 = r19;
        r14 = r0.placeName;
    L_0x0300:
        if (r14 == 0) goto L_0x0308;
    L_0x0302:
        r9 = r14.isEmpty();
        if (r9 == 0) goto L_0x00f3;
    L_0x0308:
        if (r19 == 0) goto L_0x0315;
    L_0x030a:
        goto L_0x030e;
    L_0x030b:
        goto L_0x00f3;
    L_0x030e:
        r0 = r19;
        r14 = r0.address;
    L_0x0312:
        goto L_0x030b;
    L_0x0313:
        r14 = 0;
        goto L_0x0300;
    L_0x0315:
        r14 = 0;
        goto L_0x0312;
        goto L_0x031b;
    L_0x0318:
        goto L_0x0102;
    L_0x031b:
        r3 = 8;
        r15.setVisibility(r3);
        goto L_0x0318;
    L_0x0321:
        r0 = r39;
        r0 = r0.mRider;
        r29 = r0;
        if (r29 == 0) goto L_0x0366;
    L_0x0329:
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2024; // 0x7e8 float:2.836E-42 double:1.0E-320;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        r3 = 2131691813; // 0x7f0f0925 float:1.9012708E38 double:1.0531956923E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r30 = r2;
        r30 = (android.widget.TextView) r30;
        r11 = r30;
        r3 = 1;
        r0 = new java.lang.Object[r3];
        r28 = r0;
        r0 = r39;
        r0 = r0.mRider;
        r29 = r0;
        r31 = r0.getFirstName();
        r3 = 0;
        r28[r3] = r31;
        r0 = r28;
        r14 = java.lang.String.format(r14, r0);
        goto L_0x0362;
    L_0x035f:
        goto L_0x014e;
    L_0x0362:
        r11.setText(r14);
        goto L_0x035f;
    L_0x0366:
        r3 = 2131691813; // 0x7f0f0925 float:1.9012708E38 double:1.0531956923E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r32 = r2;
        r32 = (android.widget.TextView) r32;
        r11 = r32;
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2023; // 0x7e7 float:2.835E-42 double:9.995E-321;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        goto L_0x0387;
    L_0x0384:
        goto L_0x014e;
    L_0x0387:
        r11.setText(r14);
        goto L_0x0384;
    L_0x038b:
        r0 = r39;
        r0 = r0.mRider;
        r29 = r0;
        if (r29 == 0) goto L_0x03d0;
    L_0x0393:
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2019; // 0x7e3 float:2.829E-42 double:9.975E-321;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        r3 = 2131691808; // 0x7f0f0920 float:1.9012698E38 double:1.05319569E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r33 = r2;
        r33 = (android.widget.TextView) r33;
        r11 = r33;
        r3 = 1;
        r0 = new java.lang.Object[r3];
        r28 = r0;
        r0 = r39;
        r0 = r0.mRider;
        r29 = r0;
        r31 = r0.getFirstName();
        r3 = 0;
        r28[r3] = r31;
        r0 = r28;
        r14 = java.lang.String.format(r14, r0);
        goto L_0x03cc;
    L_0x03c9:
        goto L_0x019a;
    L_0x03cc:
        r11.setText(r14);
        goto L_0x03c9;
    L_0x03d0:
        r3 = 2131691808; // 0x7f0f0920 float:1.9012698E38 double:1.05319569E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r34 = r2;
        r34 = (android.widget.TextView) r34;
        r11 = r34;
        r0 = r39;
        r0 = r0.mNm;
        r17 = r0;
        r3 = 2018; // 0x7e2 float:2.828E-42 double:9.97E-321;
        r0 = r17;
        r14 = r0.getLanguageString(r3);
        goto L_0x03f1;
    L_0x03ee:
        goto L_0x019a;
    L_0x03f1:
        r11.setText(r14);
        goto L_0x03ee;
        goto L_0x03f9;
    L_0x03f6:
        goto L_0x01bc;
    L_0x03f9:
        r3 = 8;
        r11.setVisibility(r3);
        goto L_0x03f6;
    L_0x03ff:
        r3 = 1917; // 0x77d float:2.686E-42 double:9.47E-321;
        r43 = com.waze.strings.DisplayStrings.displayString(r3);
        goto L_0x0409;
    L_0x0406:
        goto L_0x01da;
    L_0x0409:
        r0 = r43;
        r11.setText(r0);
        goto L_0x0406;
        goto L_0x0413;
    L_0x0410:
        goto L_0x01fc;
    L_0x0413:
        r3 = 8;
        r11.setVisibility(r3);
        goto L_0x0410;
    L_0x0419:
        r3 = 1917; // 0x77d float:2.686E-42 double:9.47E-321;
        r40 = com.waze.strings.DisplayStrings.displayString(r3);
        goto L_0x0423;
    L_0x0420:
        goto L_0x021a;
    L_0x0423:
        r0 = r40;
        r11.setText(r0);
        goto L_0x0420;
    L_0x0429:
        r3 = 2131691802; // 0x7f0f091a float:1.9012686E38 double:1.053195687E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r35 = new com.waze.carpool.CarpoolRideDetailsActivity$29;
        r0 = r35;
        r1 = r39;
        r0.<init>();
        r0 = r35;
        r2.setOnClickListener(r0);
        r3 = 2131691807; // 0x7f0f091f float:1.9012696E38 double:1.0531956894E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r36 = new com.waze.carpool.CarpoolRideDetailsActivity$30;
        r0 = r36;
        r1 = r39;
        r0.<init>();
        r0 = r36;
        r2.setOnClickListener(r0);
        r3 = 2131691812; // 0x7f0f0924 float:1.9012706E38 double:1.053195692E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r37 = new com.waze.carpool.CarpoolRideDetailsActivity$31;
        r0 = r37;
        r1 = r39;
        r0.<init>();
        r0 = r37;
        r2.setOnClickListener(r0);
        r3 = 2131691816; // 0x7f0f0928 float:1.9012715E38 double:1.053195694E-314;
        r0 = r39;
        r2 = r0.findViewById(r3);
        r38 = new com.waze.carpool.CarpoolRideDetailsActivity$32;
        r0 = r38;
        r1 = r39;
        r0.<init>();
        r0 = r38;
        r2.setOnClickListener(r0);
        return;
    L_0x0486:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRideDetailsActivity.setRouteDetails(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, int):void");
    }

    public void onCreate(Bundle $r1) throws  {
        CarpoolDrive $r13;
        boolean $z0 = false;
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        this.mDtnm = DriveToNativeManager.getInstance();
        this.mCpnm = CarpoolNativeManager.getInstance();
        getWindow().setSoftInputMode(3);
        requestWindowFeature(1);
        this.mNavNtvMgr = NavigateNativeManager.instance();
        this.mNavNtvMgr.setUpdateHandler(NavigateNativeManager.UH_LOCATION_PICKER_STATE, this.mHandler);
        boolean $z1 = false;
        boolean $z2 = false;
        Intent $r2 = getIntent();
        if ($r2 != null && $r2.hasExtra(CarpoolDrive.class.getSimpleName())) {
            this.mDrive = (CarpoolDrive) $r2.getParcelableExtra(CarpoolDrive.class.getSimpleName());
            if (this.mDrive != null) {
                $r13 = this.mDrive;
                this.mRealtimeRide = $r13.isRealTimeRide();
            } else {
                Logger.m38e("CarpoolRideDetailsActivity: oncreate: received null for drive!");
            }
            this.mRide = (CarpoolRide) $r2.getParcelableExtra(CarpoolRide.class.getSimpleName());
            if (this.mRide == null && this.mDrive == null) {
                Logger.m38e("CarpoolRideDetailsActivity: oncreate: received null for drive and ride! Cannot display ride details");
                return;
            }
            if (this.mRide == null) {
                Logger.m36d("CarpoolRideDetailsActivity: oncreate: received null for specific ride!");
                $r13 = this.mDrive;
                this.mRide = $r13.getRide();
            }
            if (this.mRide != null) {
                CarpoolUserData $r15 = this.mRide;
                CarpoolUserData $r14 = $r15;
                $r15 = $r15.rider;
                CarpoolUserData $r152 = $r15;
                this.mRider = $r15;
                CarpoolRide $r142 = this.mRide;
                if ($r142.isRealTimeRide()) {
                    this.mRealtimeRide = true;
                }
            } else {
                StringBuilder $r16 = new StringBuilder().append("Drive id ");
                $r13 = this.mDrive;
                Logger.m38e($r16.append($r13.getLiveRideState()).append(" has no rides!").toString());
            }
            this.mIsOnboarding = $r2.getBooleanExtra("onboarding", false);
            $z1 = $r2.getBooleanExtra("OpenRider", false);
            $z2 = $r2.getBooleanExtra(INTENT_PARAM_MESSAGING, false);
        }
        if ($z2) {
            Logger.m36d("CarpoolRideDetailsActivity: oncreate: opened for messaging! opening intent! called from " + Utils.getCallerClassName(CarpoolRideDetailsActivity.class.getName()));
            $r2 = new Intent(this, CarpoolMessagingActivity.class);
            if (this.mRider != null) {
                $r2.putExtra("rider", this.mRider);
            }
            if (this.mRide != null) {
                $r2.putExtra("ride", this.mRide);
            }
            this.mChatted = true;
            startActivityForResult($r2, 0);
        }
        if ($r1 != null) {
            this.mWasTimeSelected = $r1.getBoolean(TAG + ".mWasTimeSelected", this.mWasTimeSelected);
            if (this.mWasTimeSelected) {
                this.mUtcTimeSecSelected = $r1.getLong(TAG + ".mUtcTimeSecSelected");
            }
        }
        if (this.mDrive != null) {
            int $i0 = this.mDrive.getState(this.mRide);
            if ($i0 == 10) {
                $z2 = true;
            } else {
                $z2 = false;
            }
            this.mDrivingToPickup = $z2;
            if ($i0 == 16) {
                $z2 = true;
            } else {
                $z2 = false;
            }
            this.mArrivedAtPickup = $z2;
            if ($i0 == 8) {
                $z0 = true;
            }
            this.mDrivingToDropoff = $z0;
            setupActivity();
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.mHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, this.mHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED, this.mHandler);
            ArrayList $r17 = this.mChatHandlers;
            $r17.clear();
            $i0 = 0;
            while (true) {
                $r13 = this.mDrive;
                if ($i0 >= $r13.getRidesAmount()) {
                    break;
                }
                $r13 = this.mDrive;
                CarpoolRide $r143 = $r13.getRide($i0);
                HashMap $r18 = this.mRiderViews;
                String $r11 = $r143.rider;
                String $r153 = $r11;
                RideChatHandler rideChatHandler = new RideChatHandler($r143, (View) $r18.get($r11.id));
                $r17 = this.mChatHandlers;
                ArrayList $r172 = $r17;
                $r17.add(rideChatHandler);
                ChatNotificationManager.getInstance(true).setChatUpdateHandler($r143.getId(), rideChatHandler);
                $i0++;
            }
            this.mCpnm.getDriveUpdates(this.mDrive, true, new C15002());
        } else {
            Logger.m38e("CarpoolRideDetailsActivity: oncreate: received null for drive!");
        }
        this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CLOSE_RIDE_DETAILS, this.mHandler);
        if ($z1) {
            openRiderProfile(this.mRide);
        }
    }

    private void openRiderProfile(CarpoolRide $r1) throws  {
        if (this.mIsOnboarding && !this.mIsOnboardingDone) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, this.mGetAnswerCb);
        } else if ($r1 != null && $r1.rider != null && isOnboarded()) {
            Intent $r2 = new Intent(this, CarpoolRiderProfileActivity.class);
            $r2.putExtra(CarpoolUserData.class.getSimpleName(), $r1.rider);
            $r2.putExtra(CarpoolRide.class.getSimpleName(), $r1);
            $r2.putExtra("onboarding", this.mIsOnboarding);
            startActivityForResult($r2, 1006);
        } else if ($r1 == null || $r1.rider == null) {
            Logger.m38e("CarpoolRideDetailsActivity:openRiderProfile() - ride/rider is null, not displaying");
        }
    }

    protected void onResume() throws  {
        super.onResume();
        this.originType = this.mDrive.getOriginType();
        this.originName = this.mDrive.getOriginString();
        this.destinationName = this.mDrive.getDestinationString();
        this.pickupName = this.mDrive.getPickupString();
        this.dropoffName = this.mDrive.getDropOffString();
        if (this.mMapInit) {
            long $l2;
            CarpoolDriveMatchInfo $r6;
            long $l3;
            int $i0;
            setRouteDetails(this.originName, this.originType, this.pickupName, this.dropoffName, this.destinationName, this.destinationType);
            if (this.mRideState == 2) {
                $l2 = this.mDrive.getTime();
                $r6 = this.mDrive.drive_match_info;
                $l3 = (long) $r6.origin_to_pickup_duration_seconds;
                $r6 = this.mDrive.drive_match_info;
                $i0 = $r6.dropoff_to_destination_duration_seconds;
                $r6 = this.mDrive.drive_match_info;
                setRouteTimes($l2, $l3, (long) ($i0 + $r6.pickup_to_dropoff_duration_seconds));
            }
            if (this.mRideState == 1 && this.mWasTimeSelected) {
                $l2 = this.mUtcTimeSecSelected;
                $r6 = this.mDrive.drive_match_info;
                $l3 = (long) $r6.origin_to_pickup_duration_seconds;
                $r6 = this.mDrive.drive_match_info;
                $i0 = $r6.dropoff_to_destination_duration_seconds;
                $r6 = this.mDrive.drive_match_info;
                setRouteTimes($l2, $l3, (long) ($i0 + $r6.pickup_to_dropoff_duration_seconds));
            }
        }
        sendShownAnalytics();
    }

    protected void onPause() throws  {
        cancelMessageTooltip();
        super.onPause();
    }

    protected void onDestroy() throws  {
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CLOSE_RIDE_DETAILS, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE, this.mHandler);
        Iterator $r4 = this.mChatHandlers.iterator();
        while ($r4.hasNext()) {
            RideChatHandler $r6 = (RideChatHandler) $r4.next();
            ChatNotificationManager.getInstance(true).unsetChatUpdateHandler($r6.ride.getId(), $r6);
        }
        this.mChatHandlers.clear();
        this.mNavNtvMgr.unsetUpdateHandler(NavigateNativeManager.UH_LOCATION_PICKER_STATE, this.mHandler);
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putBoolean(TAG + ".mWasTimeSelected", this.mWasTimeSelected);
        if (this.mWasTimeSelected) {
            $r1.putLong(TAG + ".mUtcTimeSecSelected", this.mUtcTimeSecSelected);
        }
    }

    public static void loadCanvas(CarpoolRide $r0, int $i0, float $f0, float $f1, boolean $z0, CarpoolLocation $r1) throws  {
        int $i1 = 0;
        NavigateNativeManager $r2 = NavigateNativeManager.instance();
        String $r3 = $r0 != null ? $r0.getId() : "";
        boolean $z1 = $r1 != null;
        int $i2 = $r1 == null ? 0 : $r1.lon;
        if ($r1 != null) {
            $i1 = $r1.lat;
        }
        $r2.LoadRideDetailsCanvas($f0, $f1, $r3, $i0, $z0, $z1, $i2, $i1);
    }

    private void loadRequestPickupTimes() throws  {
        if (this.mRide != null && this.mRide.itinerary != null) {
            DriveToNativeManager $r3 = DriveToNativeManager.getInstance();
            long $l0 = this.mRide.itinerary.window_start_time;
            long $l1 = this.mRide.itinerary.window_start_time;
            long $l3 = this.mRide.itinerary.window_duration_seconds;
            int $i2 = $l3;
            $l3 = (long) $l3;
            $r3.loadCarpoolDriveOptions($l0, $l1 + $l3, 300, this.mRide, new C15126());
        }
    }

    private void loadConfirmedPickupTimes() throws  {
        DriveToNativeManager.getInstance().loadCarpoolDriveOptions(this.mDrive.getTime(), this.mDrive.getTime(), 0, this.mRide, new C15147());
    }

    private void showBottomSheet() throws  {
        boolean $z1;
        boolean $z0 = false;
        SimpleBottomSheetValue[] $r1 = new SimpleBottomSheetValue[6];
        $r1[0] = new SimpleBottomSheetValue(0, this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_OPTION_CANCEL_RIDE), getResources().getDrawable(C1283R.drawable.carpool_options_cancel_ride));
        $r1[0].disabled = !CarpoolUtils.canCancelRide(this.mRideState);
        $r1[1] = new SimpleBottomSheetValue(1, this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_OPTION_REPORT_NO_SHOW), getResources().getDrawable(C1283R.drawable.carpool_options_no_show));
        SimpleBottomSheetValue $r2 = $r1[1];
        if (this.mRideState != 7) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r2.disabled = $z1;
        switch (this.mRideState) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                $r1[1].disabled = true;
                break;
            case 2:
            case 7:
                break;
            default:
                break;
        }
        $r1[2] = new SimpleBottomSheetValue(2, this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_OPTION_BLOCK_RIDER), getResources().getDrawable(C1283R.drawable.carpool_options_block_rider));
        $r2 = $r1[2];
        if (this.mBlocked || this.mIsOnboarding) {
            $z0 = true;
        }
        $r2.disabled = $z0;
        $r1[3] = new SimpleBottomSheetValue(3, this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_OPTION_FAQ), getResources().getDrawable(C1283R.drawable.carpool_options_faq));
        $r1[4] = new SimpleBottomSheetValue(4, this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_OPTION_FEEDBACK), getResources().getDrawable(C1283R.drawable.carpool_options_feedback));
        if (this.mRideState == 1) {
            $r1[4].disabled = true;
        }
        $r1[5] = new SimpleBottomSheetValue(5, this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_OPTION_SETTINGS), getResources().getDrawable(C1283R.drawable.carpool_options_settings));
        $r1[5].disabled = this.mIsOnboarding;
        this.mBottomSheet = new SimpleBottomSheet(this, DisplayStrings.DS_RIDE_DETAILS_OPTION_TITLE, $r1, new C15178());
        this.mBottomSheet.show();
    }

    private boolean updateTimeBanner() throws  {
        if (this.mDrive.isLive()) {
            this.mTitleBar.setTitle(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_TITLE_LIVE));
            this.mTitleBar.setBackgroundResource(C1283R.drawable.carpool_ride_details_live);
            View $r6 = findViewById(C1283R.id.rideRequestBannerLiveRoller);
            $r6.setVisibility(0);
            Animation translateAnimation = new TranslateAnimation(0, 0.0f, 0, (float) getResources().getDrawable(C1283R.drawable.rs_requst_status_livedrive_strips).getIntrinsicWidth(), 0, 0.0f, 0, 0.0f);
            translateAnimation.setDuration(2000);
            translateAnimation.setInterpolator(new LinearInterpolator());
            translateAnimation.setRepeatCount(-1);
            translateAnimation.setRepeatMode(1);
            $r6.startAnimation(translateAnimation);
            return false;
        }
        String $r5 = this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_BANNER_ACCEPTED_PS);
        String $r10 = this.mNm.getRelativeTimeStringNTV(this.mDrive.getTime(), false);
        this.mTitleBar.setTitle(String.format($r5, new Object[]{$r10}));
        return true;
    }

    private void changeThanksToCompleteState(boolean $z0) throws  {
        this.mTitleBar.setTitle(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_TITLE_COMPLETE));
        this.mTitleBar.setBackgroundResource(C1283R.drawable.carpool_ride_details_completed);
        this.mTitleBar.setTextColor(getResources().getColor(C1283R.color.White));
        this.mBtButtonsLayout.setVisibility(8);
        ((FrameLayout) findViewById(C1283R.id.rideDetailsRateContainer)).removeAllViews();
        if ($z0) {
            showPopup(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_THANKED), "sign_up_big_v");
        }
    }

    void setRiderDetails(View $r1, CarpoolUserData $r2, CarpoolRide $r3, int $i0) throws  {
        String $r8;
        ImageView $r6 = (ImageView) $r1.findViewById(C1283R.id.rideRequestRiderImage);
        $r6.setImageResource(C1283R.drawable.rs_profilepic_placeholder);
        final View view = $r1;
        final CarpoolUserData carpoolUserData = $r2;
        ImageRepository.instance.getImage($r2.getImage(), new ImageRepositoryListener() {
            public void onImageRetrieved(Bitmap $r1) throws  {
                CarpoolRideDetailsActivity.this.onImageRetrieved(view, carpoolUserData, $r1);
            }
        });
        final CarpoolRide carpoolRide = $r3;
        final int i = $i0;
        AnonymousClass10 $r4 = new OnClickListener() {
            public void onClick(View v) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_RIDER_PROFILE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, carpoolRide.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_IDX, (long) i).send();
                CarpoolRideDetailsActivity.this.openRiderProfile(carpoolRide);
            }
        };
        $r6.setOnClickListener($r4);
        TextView $r10 = (TextView) $r1.findViewById(C1283R.id.rideRequestRiderName);
        $r10.setText($r2.getName());
        $r10.setOnClickListener($r4);
        $r10 = (TextView) $r1.findViewById(C1283R.id.rideRequestRiderByLine);
        $r10.setOnClickListener($r4);
        View $r5 = $r1.findViewById(C1283R.id.rideRequestRiderRating);
        this.mBlocked = this.mCpnm.isRiderBlocked($r2.id);
        if (this.mBlocked) {
            $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_SUBTITLE_BLOCKED));
            $r10.setTextColor(getResources().getColor(C1283R.color.RedSweet));
            $r10.setVisibility(0);
            $r5.setVisibility(8);
        } else {
            $r8 = $r2.getWorkplace();
            if ($r8 == null || $r8.isEmpty()) {
                $r10.setVisibility(8);
            } else {
                $r10.setVisibility(0);
                $r10.setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_WORKS_AT_PS, new Object[]{$r8}));
            }
            CarpoolUtils.setStarsView($r2.star_rating_as_pax, $r2.completed_rides_pax, $r5, $r2.getFirstName());
        }
        setRiderButtons($r1, $r2, $r3, $i0);
        CarpoolItinerary $r13 = $r3.itinerary;
        $r8 = $r13.ride_note;
        if ($r8 == null || $r8.isEmpty()) {
            $r8 = $r2.motto;
        }
        $r10 = (TextView) $r1.findViewById(C1283R.id.rideRequestRiderNotes);
        if ($r8 == null || $r8.isEmpty()) {
            $r10.setVisibility(8);
            return;
        }
        $r10.setVisibility(0);
        $r10.setText(this.mNm.getFormattedString(DisplayStrings.DS_RIDE_REQ_NOTE_FORMAT, $r8));
    }

    void updateChatBadge(View $r1, CarpoolRide $r2) throws  {
        if (this.mCpnm.isMessagingEnabled()) {
            boolean $z0 = false;
            int $i0 = this.mCpnm.getUnreadChatMessageCount(this.mDrive);
            if (ConfigValues.getBoolValue(31)) {
                TextView $r6 = (TextView) findViewById(C1283R.id.rideRequestMockTextBoxBadge);
                if ($r6 != null) {
                    if ($i0 > 0) {
                        $r6.setBackgroundResource(C1283R.drawable.carpool_text_alert);
                        $r6.setText(NativeManager.getInstance().intToString($i0));
                        $z0 = true;
                    } else {
                        $r6.setBackgroundResource(C1283R.drawable.carpool_text_icon_dark);
                        $r6.setText(" ");
                    }
                }
                $r6 = (TextView) findViewById(C1283R.id.rideRequestMockTextBoxText);
                final View $r5 = findViewById(C1283R.id.rideRequestMockTextBox);
                if (!($r6 == null || $r5 == null)) {
                    this.mCpnm.getCarpoolLastDriveMessage(this.mDrive, new IResultObj<LastChatMsgDetails>() {
                        public void onResult(LastChatMsgDetails $r1) throws  {
                            if ($r1 == null) {
                                $r5.setBackgroundResource(C1283R.drawable.in_app_messaging_textbox);
                                if (!CarpoolRideDetailsActivity.this.mDrive.isLive()) {
                                    $r6.setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_MOCK_TEXT_BOX));
                                    return;
                                } else if (CarpoolRideDetailsActivity.this.mDrive.getRidesAmount() == 1) {
                                    $r6.setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_MOCK_TEXT_BOX_LIVE_PS, new Object[]{CarpoolRideDetailsActivity.this.mDrive.getRide(0).getRider().getFirstName()}));
                                    return;
                                } else if (CarpoolRideDetailsActivity.this.mDrive.getRidesAmount() == 2) {
                                    $r6.setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_MOCK_TEXT_BOX_LIVE_TWO_PS_PS, new Object[]{CarpoolRideDetailsActivity.this.mDrive.getRide(0).getRider().getFirstName(), CarpoolRideDetailsActivity.this.mDrive.getRide(1).getRider().getFirstName()}));
                                    return;
                                } else {
                                    $r6.setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_MOCK_TEXT_BOX_MULTIPAX));
                                    return;
                                }
                            }
                            $r5.setBackgroundResource(C1283R.drawable.in_app_messaging_bubble);
                            if ($r1.msg.from_me) {
                                $r6.setText(String.format(DisplayStrings.displayString(2048), new Object[]{$r1.msg.text}));
                            } else if (!CarpoolRideDetailsActivity.this.mDrive.isMultiPax() || $r1.ride.getRider() == null || $r1.ride.getRider().getFirstName() == null) {
                                $r6.setText($r1.msg.text);
                            } else {
                                $r6.setText(String.format("%s: %s", new Object[]{$r1.ride.getRider().getFirstName(), $r1.msg.text}));
                            }
                        }
                    });
                }
            }
            if ($r2 != null) {
                updateChatBadgeForRide($r1, $r2, $z0);
            } else if (this.mMultiPaxRide) {
                CarpoolRide[] $r10 = this.mDrive.rides;
                CarpoolRide[] $r102 = $r10;
                $i0 = $r10.length;
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    $r2 = $r102[$i1];
                    HashMap $r11 = this.mRiderViews;
                    CarpoolUserData $r12 = $r2.rider;
                    updateChatBadgeForRide((View) $r11.get($r12.id), $r2, $z0);
                }
            } else if (this.mRide != null) {
                updateChatBadgeForRide($r1, this.mRide, $z0);
            }
        }
    }

    void updateChatBadgeForRide(View $r1, CarpoolRide $r2, boolean $z0) throws  {
        int $i0 = this.mCpnm.getUnreadChatMessageCount($r2);
        TextView $r4 = (TextView) $r1.findViewById(C1283R.id.riderTextUnreadBadge);
        if ($r4 != null) {
            if ($i0 <= 0 || $z0) {
                $r4.setBackgroundResource(C1283R.drawable.carpool_text_icon);
                $r4.setText(" ");
            } else {
                $r4.setBackgroundResource(C1283R.drawable.carpool_text_alert);
                $r4.setText(NativeManager.getInstance().intToString($i0));
            }
        }
        if (this.mMultiPaxRide) {
            RiderImageAndMessageCounter $r10 = (RiderImageAndMessageCounter) this.mRiderImageAndMessageCounter.get($r2.rider.id);
            if ($r10 == null) {
                $r10 = new RiderImageAndMessageCounter();
            }
            $r10.counter = $i0;
            this.mRiderImageAndMessageCounter.put($r2.rider.id, $r10);
        }
    }

    void setRiderButtons(View $r1, CarpoolUserData $r2, CarpoolRide $r3, int $i0) throws  {
        View $r4;
        View $r5;
        if (this.mMultiPaxRide) {
            $r4 = $r1.findViewById(C1283R.id.rideRequestRiderSmallButtons);
            $r5 = $r4;
            $r4.setVisibility(0);
            $r1.findViewById(C1283R.id.rideRequestRiderButtons).setVisibility(8);
            $r1 = $r1.findViewById(C1283R.id.rideRequestRemoveRiderButton);
            if (this.mRideState == 1 || this.mRideState == 7 || this.mRideState == 2) {
                $r1.setVisibility(0);
                final CarpoolRide carpoolRide = $r3;
                final int i = $i0;
                final CarpoolUserData carpoolUserData = $r2;
                $r1.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_REMOVE_RIDER).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, carpoolRide.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_IDX, (long) i).send();
                        CarpoolDrive $r6 = CarpoolRideDetailsActivity.this.mDrive;
                        CarpoolRide $r4 = carpoolRide;
                        CarpoolUserData $r7 = carpoolUserData;
                        int $i0 = i;
                        Context $r2 = CarpoolRideDetailsActivity.this;
                        CarpoolRideDetailsActivity $r8 = CarpoolRideDetailsActivity.this;
                        CarpoolUtils.removeRiderFromDrive($r6, $r4, $r7, $i0, $r2, $r8.mHandler);
                    }
                });
            } else {
                $r1.setVisibility(8);
            }
        } else {
            $r4 = $r1.findViewById(C1283R.id.rideRequestRiderButtons);
            $r5 = $r4;
            $r4.setVisibility(0);
            $r1.findViewById(C1283R.id.rideRequestRiderSmallButtons).setVisibility(8);
            ((TextView) $r4.findViewById(C1283R.id.rideRequestMessageButtonText)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_MESSAGE_BUTTON));
            ((TextView) $r4.findViewById(C1283R.id.rideRequestCallButtonText)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_CALL_BUTTON));
        }
        AnalyticsBuilder $r7 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_MESSAGE_RIDER).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_IDX, (long) $i0);
        CarpoolDrive $r8 = this.mDrive;
        $r7 = $r7.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r8.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r3 != null ? $r3.getId() : AnalyticsEvents.ANALYTICS_EVENT_NETWORK_MODE_NA);
        AnalyticsBuilder $r10 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CALL_RIDER).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_IDX, (long) $i0);
        $r8 = this.mDrive;
        $r10 = $r10.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r8.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r3 != null ? $r3.getId() : AnalyticsEvents.ANALYTICS_EVENT_NETWORK_MODE_NA);
        $r1 = $r5.findViewById(C1283R.id.rideRequestMessageButton);
        $r4 = $r5.findViewById(C1283R.id.rideRequestCallButton);
        INextActionCallback $r11 = (!this.mIsOnboarding || this.mIsOnboardingDone) ? null : this.mGetAnswerCb;
        CarpoolRiderProfileActivity.setContactRiderButtons($r3, $r2, $r1, $r4, $r7, $r10, $r11);
        updateChatBadge($r5, $r3);
    }

    @NonNull
    private View getRiderButtonsView(View $r1) throws  {
        if (this.mMultiPaxRide) {
            return $r1.findViewById(C1283R.id.rideRequestRiderSmallButtons);
        }
        return $r1.findViewById(C1283R.id.rideRequestRiderButtons);
    }

    private void attemptToShowMessageTooltip() throws  {
        final View $r1 = findViewById(C1283R.id.rideRequestMessageButton);
        if ($r1.getVisibility() == 0 && $r1.isEnabled() && !ConfigValues.getBoolValue(31) && this.mCpnm.isMessagingEnabled() && !ConfigValues.getBoolValue(307)) {
            this.mTipRunnable = new Runnable() {
                public void run() throws  {
                    Object obj;
                    String $r1 = DisplayStrings.displayString(DisplayStrings.DS_FTE_CARPOOL_RIDE_REQ_BUBBLE_TEXT_PS);
                    Object[] $r2 = new Object[1];
                    $r2[0] = CarpoolRideDetailsActivity.this.mRider != null ? CarpoolRideDetailsActivity.this.mRider.getFirstName() : DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
                    if (UserTooltipView.showUserTooltip(CarpoolRideDetailsActivity.this, $r1, 0, String.format($r1, $r2), 3000, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_TYPE_RIDE_REQ_CONTACT, false) != null) {
                        obj = 1;
                    } else {
                        obj = null;
                    }
                    if (obj != null) {
                        ConfigManager.getInstance().setConfigValueBool(307, true);
                    }
                }
            };
            postDelayed(this.mTipRunnable, (long) ConfigValues.getIntValue(304));
        }
    }

    private void cancelMessageTooltip() throws  {
        if (this.mTipRunnable != null) {
            cancel(this.mTipRunnable);
            this.mTipRunnable = null;
        }
    }

    void setNoRiderDetails(View $r1, CarpoolRide $r2) throws  {
        ((ImageView) $r1.findViewById(C1283R.id.rideRequestRiderImage)).setImageResource(C1283R.drawable.rs_profilepic_placeholder);
        $r1.findViewById(C1283R.id.rideRequestRiderByLine).setVisibility(8);
        ((TextView) $r1.findViewById(C1283R.id.rideRequestRiderName)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER));
        $r1.findViewById(C1283R.id.rideRequestRiderRating).setVisibility(8);
        setRiderButtons($r1, null, $r2, 0);
    }

    void setRideRequestDetails(View $r1, long $l0, long $l1, long $l2, long $l3) throws  {
        TimeZone $r5 = Calendar.getInstance().getTimeZone();
        this.mTimeFormat = android.text.format.DateFormat.getTimeFormat(this);
        this.mTimeFormat.setTimeZone($r5);
        SimpleDateFormat $r3 = new SimpleDateFormat("EEEE, MMM d", new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV()));
        $r3.setTimeZone($r5);
        ((TextView) $r1.findViewById(C1283R.id.rideRequestDate)).setText($r3.format(new Date(1000 * $l0)));
        TextView $r11 = (TextView) $r1.findViewById(C1283R.id.rideRequestPickupTime);
        if (this.mRealtimeRide && !this.mWasTimeSelected) {
            $l0 = Calendar.getInstance().getTimeInMillis() / 1000;
            CarpoolDriveMatchInfo $r13 = this.mDrive;
            CarpoolDriveMatchInfo $r12 = $r13;
            int $i4 = $r13.drive_match_info;
            CarpoolDriveMatchInfo $r132 = $i4;
            long $l5 = $i4.origin_to_pickup_duration_seconds;
            int $i42 = $l5;
            $l5 = $l0 + ((long) $l5);
            $l0 = $l5;
            this.mUtcTimeSecSelected = $l5;
            $l5 = ((this.mUtcTimeSecSelected / 300) * 300) + 300;
            $l0 = $l5;
            this.mUtcTimeSecSelected = $l5;
            if (this.mUtcTimeSecSelected < $l1) {
                this.mUtcTimeSecSelected = $l1;
            } else if (this.mUtcTimeSecSelected > $l1 + $l2) {
                this.mUtcTimeSecSelected = $l1 + $l2;
            }
            this.mWasTimeSelected = true;
        }
        String $r8;
        if (!this.mWasTimeSelected && $l2 < 300) {
            Logger.m36d("CarpoolRideDetaiilsActivity: setRideRequestDetails: window is 0, not insisting on time selection");
            this.mWasTimeSelected = true;
            this.mUtcTimeSecSelected = $l1;
            $r8 = this.mNm.getLanguageString(DisplayStrings.DS_RIDE_REQ_PICKUP_PS);
            Object[] $r16 = new Object[1];
            $r16[0] = this.mTimeFormat.format(new Date(1000 * $l1));
            $r11.setText(String.format($r8, $r16));
        } else if (this.mWasTimeSelected) {
            $r11.setText(this.mTimeFormat.format(new Date(this.mUtcTimeSecSelected * 1000)));
        } else {
            $r8 = this.mNm.getLanguageString(DisplayStrings.DS_RIDE_REQ_PICKUP_PS_PS);
            String $r15 = this.mTimeFormat.format(new Date(1000 * $l1));
            String $r18 = this.mTimeFormat.format(new Date(($l1 + $l2) * 1000));
            $r11.setText(String.format($r8, new Object[]{$r15, $r18}));
        }
        if (this.mWasTimeSelected) {
            setRouteTimes(this.mUtcTimeSecSelected, 0, $l3);
        }
        final long j = $l2;
        final long j2 = $l1;
        final long j3 = $l3;
        $r1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                CarpoolRideDetailsActivity.this.openSelectTimeDialog(j, j2, j3, false);
            }
        });
    }

    void openSelectTimeDialog(long $l0, long $l1, long $l2, boolean $z0) throws  {
        long $l3 = System.currentTimeMillis() / 1000;
        ArrayList $r1 = new ArrayList(8);
        ArrayList arrayList = new ArrayList(8);
        for (long $l5 = 0; $l5 <= $l0; $l5 += 300) {
            $r1.add(this.mTimeFormat.format(new Date(($l1 + $l5) * 1000)));
            CarpoolDriveMatchInfo $r8 = this.mDrive;
            CarpoolDriveMatchInfo $r7 = $r8;
            int $i7 = $r8.drive_match_info;
            CarpoolDriveMatchInfo $r82 = $i7;
            if ($i7.origin_to_pickup_duration_seconds > 0) {
                $r8 = this.mDrive;
                $r7 = $r8;
                $i7 = $r8.drive_match_info;
                $r82 = $i7;
                int $i72 = $i7.origin_to_pickup_duration_seconds;
                int $i8 = $i72;
                if (this.mDriveEtas != null) {
                    $i8 = ((Integer) this.mDriveEtas.get((int) ($l1 + $l5), Integer.valueOf($i72))).intValue();
                }
                long $l4 = ($l1 + $l5) - ((long) $i8);
                if ($l4 > $l3) {
                    String $r6 = this.mTimeFormat.format(new Date(1000 * $l4));
                    if (this.originType == 1) {
                        $r6 = DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_LEAVE_BY_PS_PS, new Object[]{this.originName, $r6});
                    } else if (this.originType == 3) {
                        $r6 = DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_LEAVE_BY_PS_PS, new Object[]{this.originName, $r6});
                    } else if (this.originType == 5) {
                        $r6 = DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_LEAVE_BY_PS_PS, new Object[]{this.originName, $r6});
                    } else {
                        $r6 = DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_LEAVE_BY_PS, new Object[]{$r6});
                    }
                    arrayList.add($r6);
                } else {
                    arrayList.add(DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_LEAVE_NOW));
                }
            }
        }
        BottomSheet bottomSheet = new BottomSheet(this, DisplayStrings.DS_SET_PICKUP_TIME, Mode.COLUMN_TEXT);
        final ArrayList arrayList2 = $r1;
        final ArrayList arrayList3 = arrayList;
        final long j = $l1;
        final long j2 = $l2;
        final BottomSheet bottomSheet2 = bottomSheet;
        final boolean z = $z0;
        bottomSheet.setAdapter(new Adapter() {
            public int getCount() throws  {
                return arrayList2.size();
            }

            public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                if (CarpoolRideDetailsActivity.this.mDrive.drive_match_info.origin_to_pickup_duration_seconds > 0) {
                    $r1.setItem((String) arrayList2.get($i0), (String) arrayList3.get($i0));
                } else {
                    $r1.setItem((String) arrayList2.get($i0));
                }
            }

            public void onClick(int $i0) throws  {
                int $i02 = $i0 * 300;
                long $l2 = j + ((long) $i02);
                if (!(CarpoolRideDetailsActivity.this.mUtcTimeSecSelected == $l2 && CarpoolRideDetailsActivity.this.mWasTimeSelected)) {
                    if (!CarpoolRideDetailsActivity.this.mWasTimeSelected) {
                        CarpoolRideDetailsActivity.this.mWasTimeSelected = true;
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_PICKUP_TIME_CHANGED);
                    }
                    CarpoolRideDetailsActivity.this.mUtcTimeSecSelected = $l2;
                    CarpoolDriveMatchInfo $r3 = CarpoolRideDetailsActivity.this.mDrive;
                    CarpoolDriveMatchInfo $r2 = $r3;
                    $i02 = $r3.drive_match_info;
                    CarpoolDriveMatchInfo $r32 = $i02;
                    $i02 = $i02.origin_to_pickup_duration_seconds;
                    $l2 = (long) $i02;
                    if (CarpoolRideDetailsActivity.this.mDriveEtas != null) {
                        $i02 = (int) $l2;
                        $l2 = (long) ((Integer) CarpoolRideDetailsActivity.this.mDriveEtas.get((int) CarpoolRideDetailsActivity.this.mUtcTimeSecSelected, Integer.valueOf($i02))).intValue();
                        $r3 = CarpoolRideDetailsActivity.this.mDrive;
                        $r2 = $r3;
                        $r32 = $r3.drive_match_info;
                        $i02 = (int) $l2;
                        $i0 = $i02;
                        $r32.origin_to_pickup_duration_seconds = $i02;
                    }
                    CarpoolRideDetailsActivity $r1 = CarpoolRideDetailsActivity.this;
                    long $l5 = CarpoolRideDetailsActivity.this;
                    CarpoolRideDetailsActivity $r7 = $l5;
                    $r1.setRouteTimes($l5.mUtcTimeSecSelected, $l2, j2);
                }
                BottomSheet $r8 = bottomSheet2;
                $r8.dismiss();
                if (z) {
                    AnalyticsBuilder $r9 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_RIDE_SHOWN);
                    CarpoolDrive $r22 = CarpoolRideDetailsActivity.this.mDrive;
                    String $r10 = $r22.getSomeRideId();
                    $r9 = $r9.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r10);
                    $r22 = CarpoolRideDetailsActivity.this.mDrive;
                    $r10 = $r22.getId();
                    $r9.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r10).send();
                    CarpoolRideDetailsActivity.this.acceptTheRide();
                }
            }
        });
        bottomSheet.show();
    }

    void setRideScheduledDetails(View $r1, long $l0, long $l1, long $l2) throws  {
        TimeZone $r3 = Calendar.getInstance().getTimeZone();
        ((TextView) $r1.findViewById(C1283R.id.rideRequestDateAndTime)).setText(DisplayUtils.getDayTimeString(this, 1000 * $l0, DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_DATE_AND_TIME_PS_PS)));
        this.mTimeFormat = android.text.format.DateFormat.getTimeFormat(this);
        DateFormat $r6 = this.mTimeFormat;
        $r6.setTimeZone($r3);
        setRouteTimes($l0, $l1, $l2);
        findViewById(C1283R.id.rideRequestButDismiss).setVisibility(8);
        TextView $r5 = (TextView) findViewById(C1283R.id.rideRequestButAccept);
        $r1 = findViewById(C1283R.id.rideRequestRoutePickupCar);
        $r1.setVisibility(8);
        View $r7 = findViewById(C1283R.id.rideRequestRouteDropOffCar);
        $r7.setVisibility(8);
        if (this.mDrivingToPickup) {
            $r1.setVisibility(0);
            $r5.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_MEETUP_ARRIVED_TO_PICKUP_ACTION));
        } else if (this.mArrivedAtPickup) {
            $r1.setVisibility(0);
            $r5.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_ACTION));
        } else if (this.mDrivingToDropoff) {
            $r7.setVisibility(0);
            $r5.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_DROPOFF_ACTION));
        } else {
            $r5.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_TAKEOVER_UPCOMING_RIDE_ACTION));
        }
        $r1 = findViewById(C1283R.id.rideRequestButMore);
        $r1.setVisibility(0);
        $r1.setOnClickListener(new OnClickListener() {

            class C14961 implements PickDestinationCompleteListener {
                C14961() throws  {
                }

                public void onComplete() throws  {
                }
            }

            public void onClick(View v) throws  {
                CarpoolRideDetailsActivity.this.mCpnm.pickDestinationDialog(CarpoolRideDetailsActivity.this.mDrive, new C14961());
            }
        });
        if (!this.mDrivingToDropoff) {
            $r1 = findViewById(C1283R.id.rideRequestCancelButton);
            $r1.setVisibility(0);
            $r1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    CarpoolRideDetailsActivity.this.cancelOrDismissRide();
                }
            });
            ((TextView) findViewById(C1283R.id.rideRequestCancelText)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CANCEL));
        }
    }

    private void setRouteTimes(long $l0, long $l1, long $l2) throws  {
        String $r3 = this.mTimeFormat.format(new Date(1000 * $l0));
        if (this.mRideState == 1 && this.mWasTimeSelected) {
            ((TextView) findViewById(C1283R.id.rideRequestRqLayout).findViewById(C1283R.id.rideRequestPickupTime)).setText($r3);
        }
        TextView $r5 = (TextView) findViewById(C1283R.id.rideRequestRoutePickupTitle);
        CarpoolDrive $r6 = this.mDrive;
        if ($r6.isMultiPax()) {
            $r5.setText(String.format(DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_ROUTE_PICKUP_RIDERS_AT_PS), new Object[]{$r3}));
        } else {
            String $r11;
            String $r7 = DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_ROUTE_PICKUP_AT_PS_PS);
            Object[] $r8 = new Object[2];
            if (this.mRider != null) {
                CarpoolUserData $r10 = this.mRider;
                $r11 = $r10.getFirstName();
            } else {
                $r11 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
            }
            $r8[0] = $r11;
            $r8[1] = $r3;
            $r5.setText(String.format($r7, $r8));
        }
        if ($l1 > 0) {
            long $l12 = ($l0 - $l1) * 1000;
            $l1 = $l12;
            $r3 = this.mTimeFormat.format(new Date($l12));
            ((TextView) findViewById(C1283R.id.rideRequestRouteOriginTitle)).setText(String.format(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_ROUTE_DEPART_BY_PS), new Object[]{$r3}));
        }
        if ($l2 > 0) {
            $l12 = ($l2 + $l0) * 1000;
            $l0 = $l12;
            $r3 = this.mTimeFormat.format(new Date($l12));
            ((TextView) findViewById(C1283R.id.rideRequestRouteDestTitle)).setText(String.format(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_ROUTE_DEST_AT_PS), new Object[]{$r3}));
        }
    }

    void showRideRateRider() throws  {
        if (this.mRatedRiderNum != this.mDrive.getRidesAmount()) {
            if (this.mRatedRiderNum == 0) {
                this.mRiderRating = new int[this.mDrive.getRidesAmount()];
            }
            new RateRiderDialog(this, this.mDrive.getRide(this.mRatedRiderNum).getId(), this.mDrive.getRider(this.mRatedRiderNum), new RateRiderResult() {
                public void onRating(int $i0) throws  {
                    if (CarpoolRideDetailsActivity.this.mRatedRiderNum >= CarpoolRideDetailsActivity.this.mDrive.getRidesAmount()) {
                        Logger.m38e("CarpoolRideDetailsActivity: mRatedRiderNum out of bounds, " + CarpoolRideDetailsActivity.this.mRatedRiderNum + " >= " + CarpoolRideDetailsActivity.this.mDrive.getRidesAmount());
                        return;
                    }
                    CarpoolRideDetailsActivity.this.mRiderRating[CarpoolRideDetailsActivity.this.mRatedRiderNum] = $i0;
                    if (CarpoolRideDetailsActivity.this.mDrive.isMultiPax()) {
                        CarpoolRideDetailsActivity.this.mRatedRiderNum = CarpoolRideDetailsActivity.this.mRatedRiderNum + 1;
                        if (CarpoolRideDetailsActivity.this.mRatedRiderNum == CarpoolRideDetailsActivity.this.mDrive.getRidesAmount()) {
                            CarpoolRideDetailsActivity.this.sendMultiRating();
                        } else {
                            CarpoolRideDetailsActivity.this.showRideRateRider();
                        }
                    } else if ($i0 >= 4) {
                        CarpoolRideDetailsActivity.this.showRideEndorseRider(CarpoolRideDetailsActivity.this.mRatedRiderNum, $i0);
                    } else {
                        CarpoolRideDetailsActivity.this.sendRating(CarpoolRideDetailsActivity.this.mRatedRiderNum, $i0, 0);
                    }
                }

                public void onSkip() throws  {
                    if (CarpoolRideDetailsActivity.this.mRatedRiderNum >= CarpoolRideDetailsActivity.this.mDrive.getRidesAmount()) {
                        Logger.m38e("CarpoolRideDetailsActivity: mRatedRiderNum out of bounds, " + CarpoolRideDetailsActivity.this.mRatedRiderNum + " >= " + CarpoolRideDetailsActivity.this.mDrive.getRidesAmount());
                        return;
                    }
                    CarpoolRideDetailsActivity.this.mRiderRating[CarpoolRideDetailsActivity.this.mRatedRiderNum] = -1;
                    if (CarpoolRideDetailsActivity.this.mDrive.isMultiPax()) {
                        CarpoolRideDetailsActivity.this.mRatedRiderNum = CarpoolRideDetailsActivity.this.mRatedRiderNum + 1;
                        if (CarpoolRideDetailsActivity.this.mRatedRiderNum == CarpoolRideDetailsActivity.this.mDrive.getRidesAmount()) {
                            CarpoolRideDetailsActivity.this.sendMultiRating();
                        } else {
                            CarpoolRideDetailsActivity.this.showRideRateRider();
                        }
                    }
                }
            }).show();
        }
    }

    void showRideEndorseRider(final int $i0, final int $i1) throws  {
        new EndorseRiderDialog(this, this.mDrive.getRide($i0).getId(), this.mDrive.getRider($i0), new EndorseRiderResult() {
            public void onEndorse(int $i0) throws  {
                CarpoolRideDetailsActivity.this.sendRating($i0, $i1, $i0);
            }
        }).show();
    }

    private void sendRating(int $i0, int $i1, int $i2) throws  {
        this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RATE_RIDER_RES, this.mHandler);
        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
        this.mRatedRiderNum++;
        this.mCpnm.sendRating(this.mDrive, this.mDrive.getRide($i0), this.mDrive.getRider($i0), $i1, $i2, new IResultOk() {

            class C14981 implements DialogInterface.OnClickListener {
                C14981() throws  {
                }

                public void onClick(DialogInterface dialog, int which) throws  {
                    CarpoolRideDetailsActivity.this.finish();
                }
            }

            public void onResult(boolean $z0) throws  {
                if (!$z0) {
                    CarpoolRideDetailsActivity.this.mNm.CloseProgressPopup();
                    CarpoolRideDetailsActivity.this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RATE_RIDER_RES, CarpoolRideDetailsActivity.this.mHandler);
                    MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C14981());
                }
            }
        });
    }

    private void sendMultiRating() throws  {
        boolean $z0 = true;
        for (int $i0 : this.mRiderRating) {
            if ($i0 != -1) {
                $z0 = false;
                break;
            }
        }
        if (!$z0) {
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RATE_RIDER_RES, this.mHandler);
            NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
            this.mCpnm.sendMultiRating(this.mDrive, this.mRiderRating, new int[this.mRiderRating.length], new IResultOk() {

                class C14991 implements DialogInterface.OnClickListener {
                    C14991() throws  {
                    }

                    public void onClick(DialogInterface dialog, int which) throws  {
                        CarpoolRideDetailsActivity.this.finish();
                    }
                }

                public void onResult(boolean $z0) throws  {
                    if (!$z0) {
                        CarpoolRideDetailsActivity.this.mNm.CloseProgressPopup();
                        CarpoolRideDetailsActivity.this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RATE_RIDER_RES, CarpoolRideDetailsActivity.this.mHandler);
                        MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C14991());
                    }
                }
            });
        }
    }

    private void showRatingSubmitted() throws  {
        if (this.mRatedRiderNum < this.mDrive.getRidesAmount()) {
            showRideRateRider();
            return;
        }
        final NativeManager $r2 = NativeManager.getInstance();
        $r2.OpenProgressIconPopup($r2.getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_RATING_RATING_SUBMITTED), "sign_up_big_v");
        postDelayed(new Runnable() {
            public void run() throws  {
                $r2.CloseProgressPopup();
            }
        }, 2000);
    }

    private void createMpaxEndorsement(LinearLayout $r1, LayoutInflater $r2, CarpoolRide $r3, int $i0, int $i1) throws  {
        View $r4 = $r2.inflate(C1283R.layout.ride_details_endorsement, $r1, false);
        final ImageView $r7 = (ImageView) $r4.findViewById(C1283R.id.endRider);
        TextView textView = (TextView) $r4.findViewById(C1283R.id.endName);
        ((ImageView) $r4.findViewById(C1283R.id.endImage)).setImageResource(CarpoolEndorsement.icon[$i0]);
        textView.setText(DisplayStrings.displayString(CarpoolEndorsement.name[$i0]));
        VolleyManager.getInstance().loadImageFromUrl($r3.getRider().getImage(), new ImageRequestListener() {
            public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                $r7.setImageDrawable(new CircleFrameDrawable($r1, 0, 2, 4));
            }

            public void onImageLoadFailed(Object token, long duration) throws  {
            }
        }, null, PixelMeasure.dp(40), PixelMeasure.dp(40), null);
        $r1.addView($r4, new LayoutParams($i1, -2, 0.0f));
        $r1.addView(new Space(this), new LayoutParams(0, 0, 1.0f));
    }

    void setRideCanceledDetails(View $r1, long $l0) throws  {
        ((TextView) $r1.findViewById(C1283R.id.rideRequestDateAndTime)).setText(DisplayUtils.getDayTimeString(this, 1000 * $l0, DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_DATE_AND_TIME_PS_PS)));
    }

    void setDetourAndRewardDetails(String $r1, String $r2, int $i0, String $r3) throws  {
        if (this.mRideState == 4 || this.mRideState == 5) {
            findViewById(C1283R.id.rideRequestDetourLayout).setVisibility(8);
            findViewById(C1283R.id.rideRequestDetourSep).setVisibility(8);
            return;
        }
        ImageView $r6 = (ImageView) findViewById(C1283R.id.rideRequestRewardIcon);
        if (this.mDrive.itinerary.free_offer) {
            $r6.setImageResource(C1283R.drawable.carpool_zero_price_small);
            ((LayoutParams) $r6.getLayoutParams()).setMargins(PixelMeasure.dp(13), 0, PixelMeasure.dp(13), 0);
            ((TextView) findViewById(C1283R.id.rideRequestReward)).setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_FREE_OFFER));
        } else {
            $r6.setImageResource(C1283R.drawable.carpool_get);
            ((LayoutParams) $r6.getLayoutParams()).setMargins(PixelMeasure.dp(16), 0, PixelMeasure.dp(16), 0);
            ((TextView) findViewById(C1283R.id.rideRequestReward)).setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_REQ_REWARD_PS, new Object[]{$r1}));
        }
        TextView $r11 = (TextView) findViewById(C1283R.id.rideRequestDetourTime);
        int $i1 = CarpoolNativeManager.getInstance().getDetourDisplayMode();
        if ($i1 == 0) {
            findViewById(C1283R.id.rideRequestDetourSep).setVisibility(8);
            findViewById(C1283R.id.rideRequestDetourLayout).setVisibility(8);
        } else if ($i1 == 2) {
            $r11.setText(String.format(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_EXTRA_TIME_PD_PS), new Object[]{Integer.valueOf($i0), $r3}));
        } else {
            $r11.setText(String.format(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_EXTRA_TIME_PD), new Object[]{Integer.valueOf($i0)}));
        }
        $r11 = (TextView) findViewById(C1283R.id.rideRequestOrigReward);
        if ($r2 != null) {
            if (!this.mDrive.itinerary.free_offer) {
                $r11.setVisibility(0);
                $r1 = String.format(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_ORIG_REWARD_PS), new Object[]{$r2});
                CharSequence spannableString = new SpannableString($r1);
                spannableString.setSpan(new StrikethroughSpan(), 0, $r1.length(), 0);
                $r11.setText(spannableString);
                findViewById(C1283R.id.rideRequestPaymentLayout).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) throws  {
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRICING_INFO).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                        MsgBox.getInstance().OpenMessageBoxTimeoutCb(DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_REWARD_EXPLANATION_TITLE), DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_REWARD_EXPLANATION_TEXT), -1, 0);
                    }
                });
                return;
            }
        }
        $r11.setVisibility(8);
    }

    private void openFullMap(CarpoolLocation $r1) throws  {
        Intent $r2 = new Intent(this, CarpoolRideDetailsMapActivity.class);
        $r2.putExtra(CarpoolDrive.class.getSimpleName(), this.mDrive);
        $r2.putExtra("title", this.mTitleBar.getTitle());
        $r2.putExtra("titleBackground", this.mTitleBar.getBackgroundResource());
        $r2.putExtra("titleBack", this.mTitleBar.getUpButtonResource());
        $r2.putExtra("titleTextColor", this.mTitleBar.getTextColor());
        $r2.putExtra("ZoomTarget", $r1);
        startActivityForResult($r2, 1005);
    }

    private synchronized void doneWaitingForUpdate(String $r1, String $r2) throws  {
        if (this.mWaitingForUpdate) {
            cancel(this.mTimeoutRunnable);
            this.mWaitingForUpdate = false;
            if ($r1 == null) {
                this.mNm.CloseProgressPopup();
            } else {
                showPopup($r1, $r2, 10);
            }
        }
    }

    private void waitForUpdate() throws  {
        waitForUpdate(-1);
    }

    private void waitForUpdate(final int $i0) throws  {
        if (this.mTimeoutRunnable != null) {
            cancel(this.mTimeoutRunnable);
        }
        this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
        this.mWaitingForUpdate = true;
        this.mTimeoutRunnable = new Runnable() {

            class C15011 implements DialogInterface.OnClickListener {
                C15011() throws  {
                }

                public void onClick(DialogInterface dialog, int which) throws  {
                    CarpoolRideDetailsActivity.this.setResult($i0);
                    CarpoolRideDetailsActivity.this.finish();
                }
            }

            public void run() throws  {
                CarpoolRideDetailsActivity.this.doneWaitingForUpdate(null, null);
                MsgBox.openMessageBoxTimeout(CarpoolRideDetailsActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_UHHOHE), CarpoolRideDetailsActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C15011());
            }
        };
        postDelayed(this.mTimeoutRunnable, NETWORK_TIMEOUT);
    }

    private void initReportProblem(View $r1) throws  {
        if ($r1 instanceof TextView) {
            String $r4 = this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_PROBLEM);
            SpannableString $r2 = new SpannableString($r4);
            $r2.setSpan(new UnderlineSpan(), 0, $r4.length(), 0);
            ((TextView) $r1).setText($r2);
        }
        if (this.mRideState == 1) {
            $r1.setVisibility(8);
            return;
        }
        $r1.setVisibility(0);
        $r1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                CarpoolUtils.startFeedbackActivity(CarpoolRideDetailsActivity.this.mDrive, null, CarpoolRideDetailsActivity.this.mRiderImageAndMessageCounter, CarpoolRideDetailsActivity.this, null, false);
            }
        });
    }

    boolean areBottomButtonsShown() throws  {
        if (this.mRideState == 1) {
            return true;
        }
        if (this.mRideState == 2) {
            return true;
        }
        if (this.mRideState != 7) {
            return this.mRideState == 8;
        } else {
            return true;
        }
    }

    boolean isBottomLayoutShown() throws  {
        if (this.mRideState == 1) {
            return true;
        }
        if (this.mRideState == 2) {
            return true;
        }
        if (this.mRideState == 7) {
            return true;
        }
        if (this.mRideState == 4) {
            return true;
        }
        if (this.mRideState != 5) {
            return this.mRideState == 8;
        } else {
            return true;
        }
    }

    boolean shouldShowTextBox() throws  {
        if (this.mDrive == null) {
            return false;
        }
        if (this.mRider == null) {
            return false;
        }
        if (!this.mCpnm.isMessagingEnabled()) {
            return false;
        }
        int $i0 = this.mDrive.getState(this.mRide);
        if ($i0 == 2) {
            return false;
        }
        if ($i0 == 3) {
            return false;
        }
        if ($i0 == 15) {
            return false;
        }
        if ($i0 != 9) {
            return $i0 != 4;
        } else {
            return false;
        }
    }

    private void setupButtons() throws  {
        if (isBottomLayoutShown()) {
            View $r1 = findViewById(C1283R.id.rideRequestMockTextBox);
            if (ConfigValues.getBoolValue(31) && shouldShowTextBox()) {
                $r1.setVisibility(0);
                if (this.mMultiPaxRide) {
                    $r1.setOnClickListener(new OnClickListener() {

                        class C15021 implements IResultObj<Integer> {
                            C15021() throws  {
                            }

                            public void onResult(Integer $r1) throws  {
                                CarpoolRide $r5 = CarpoolRideDetailsActivity.this.mDrive.getRide($r1.intValue());
                                CarpoolUserData $r6 = $r5.rider;
                                INextActionCallback $r7 = (!CarpoolRideDetailsActivity.this.mIsOnboarding || CarpoolRideDetailsActivity.this.mIsOnboardingDone) ? null : CarpoolRideDetailsActivity.this.mGetAnswerCb;
                                CarpoolRiderProfileActivity.startChat($r5, $r6, $r7, true);
                            }
                        }

                        public void onClick(View v) throws  {
                            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_IAM).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).send();
                            CarpoolUtils.showSelectRiderBottomSheet(CarpoolRideDetailsActivity.this, CarpoolRideDetailsActivity.this.mDrive, CarpoolRideDetailsActivity.this.mRiderImageAndMessageCounter, new C15021(), DisplayStrings.DS_RIDERS_ACTION_SHEET_MESSAGE_TITLE, -1);
                        }
                    });
                } else {
                    AnalyticsBuilder $r3 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_MESSAGE_RIDER).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId());
                    CarpoolRide $r6 = this.mRide;
                    CarpoolUserData $r7 = this.mRider;
                    INextActionCallback $r8 = (!this.mIsOnboarding || this.mIsOnboardingDone) ? null : this.mGetAnswerCb;
                    $r1.setOnClickListener(CarpoolRiderProfileActivity.getChatListener($r6, $r7, $r3, $r8, true));
                }
            } else if (areBottomButtonsShown()) {
                $r1.setVisibility(8);
            } else {
                findViewById(C1283R.id.rideRequestBottomLayout).setVisibility(8);
                return;
            }
            if (areBottomButtonsShown()) {
                TextView textView = (TextView) findViewById(C1283R.id.rideRequestButAccept);
                TextView $r11 = (TextView) findViewById(C1283R.id.rideRequestButDismiss);
                if (this.mRideState == 1) {
                    this.mBtButtonsLayout.setVisibility(0);
                    if (!this.mRealtimeRide) {
                        textView.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_ACCEPT));
                    } else if (isOnboarded()) {
                        textView.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_ACCEPT_RT_REGISTERED));
                    } else {
                        textView.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_ACCEPT_RT_UNREGISTERED));
                    }
                    $r11.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_DISMISS));
                } else if (!(this.mRideState != 2 || this.mDrive.isLive() || this.mDrive.isUpcoming())) {
                    this.mBtButtonsLayout.setVisibility(8);
                }
                textView.setOnClickListener(new OnClickListener() {

                    class C15031 implements IResultOk {
                        C15031() throws  {
                        }

                        public void onResult(boolean $z0) throws  {
                            if ($z0) {
                                CarpoolRideDetailsActivity.this.goToArrivedMode();
                            } else {
                                CarpoolRideDetailsActivity.this.showReallyArrivedDialog();
                            }
                        }
                    }

                    class C15042 implements IResultObj<String> {
                        C15042() throws  {
                        }

                        public void onResult(String $r1) throws  {
                            Logger.m36d("CarpoolRideDetailsActivity: setupButtons: confirming start carpool really");
                            if (CarpoolUtils.confirmDriveToPickUp(CarpoolRideDetailsActivity.this.mDrive, $r1, false)) {
                                CarpoolRideDetailsActivity.this.setResult(-1);
                                CarpoolRideDetailsActivity.this.finish();
                            }
                        }
                    }

                    public void onClick(View v) throws  {
                        CarpoolRideDetailsActivity.this.cancelMessageTooltip();
                        if (CarpoolRideDetailsActivity.this.mWaitingForUpdate) {
                            MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
                        } else if (CarpoolRideDetailsActivity.this.mIsOnboarding && !CarpoolRideDetailsActivity.this.mIsOnboardingDone) {
                            $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_JOIN_ACCEPT);
                            $r7 = CarpoolRideDetailsActivity.this.mDrive;
                            $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                            $r7 = CarpoolRideDetailsActivity.this.mDrive;
                            $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, CarpoolRideDetailsActivity.this.mGetAnswerCb);
                        } else if (CarpoolRideDetailsActivity.this.mRealtimeRide && !CarpoolRideDetailsActivity.this.isOnboarded()) {
                            $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_JOIN_ACCEPT);
                            $r7 = CarpoolRideDetailsActivity.this.mDrive;
                            $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                            $r7 = CarpoolRideDetailsActivity.this.mDrive;
                            $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT_RTR, CarpoolRideDetailsActivity.this.mGetAnswerCb);
                        } else if (CarpoolRideDetailsActivity.this.mDrive == null) {
                        } else {
                            if (CarpoolRideDetailsActivity.this.mRideState == 1) {
                                if (CarpoolRideDetailsActivity.this.mRealtimeRide) {
                                    $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ACCEPT_GO);
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                                } else {
                                    String $r4 = "" + CarpoolRideDetailsActivity.this.mUtcTimeSecSelected;
                                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_PICKUP_TIME_CHANGED_DONE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $r4);
                                    $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", "ACCEPT");
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                                }
                                if (CarpoolRideDetailsActivity.this.mWasTimeSelected) {
                                    $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_RIDE_SHOWN);
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                                    CarpoolRideDetailsActivity.this.acceptTheRide();
                                    return;
                                }
                                CarpoolRideDetailsActivity $r2 = CarpoolRideDetailsActivity.this;
                                CarpoolRide $r11 = CarpoolRideDetailsActivity.this;
                                CarpoolRide $r10 = $r11;
                                CarpoolItinerary $r12 = $r11.mRide;
                                CarpoolItinerary $r112 = $r12;
                                int $i0 = $r12.itinerary;
                                CarpoolItinerary $r122 = $i0;
                                long $l1 = $i0.window_duration_seconds;
                                int $i02 = $l1;
                                long $l12 = (long) $l1;
                                $r11 = CarpoolRideDetailsActivity.this;
                                $r10 = $r11;
                                $r12 = $r11.mRide;
                                $r112 = $r12;
                                $l1 = $r12.itinerary;
                                $r122 = $l1;
                                long $l2 = $l1.window_start_time;
                                $r7 = CarpoolRideDetailsActivity.this;
                                CarpoolDrive $r102 = $r7;
                                CarpoolDriveMatchInfo $r13 = $r7.mDrive;
                                CarpoolDriveMatchInfo $r7 = $r13;
                                $i0 = $r13.drive_match_info;
                                CarpoolDriveMatchInfo $r132 = $i0;
                                $i02 = $i0.dropoff_to_destination_duration_seconds;
                                $r7 = CarpoolRideDetailsActivity.this;
                                $r102 = $r7;
                                $r13 = $r7.mDrive;
                                $r7 = $r13;
                                $i0 = $r13.drive_match_info;
                                $r132 = $i0;
                                $l1 = $i02 + $i0.pickup_to_dropoff_duration_seconds;
                                Object obj = $l1;
                                $r2.openSelectTimeDialog($l12, $l2, (long) $l1, true);
                            } else if (CarpoolRideDetailsActivity.this.mRideState != 2 && CarpoolRideDetailsActivity.this.mRideState != 7 && CarpoolRideDetailsActivity.this.mRideState != 8) {
                            } else {
                                if (CarpoolRideDetailsActivity.this.mDrivingToDropoff) {
                                    $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_DROPOFF_COMPLETE);
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                                    Logger.m36d("CarpoolRideDetailsActivity: setupButtons: confirming pax dropoff");
                                    if (CarpoolUtils.confirmPaxDropOff(CarpoolRideDetailsActivity.this.mDrive, CarpoolRideDetailsActivity.this)) {
                                        CarpoolUtils.closeSwipableLayout();
                                        CarpoolRideDetailsActivity.this.setResult(-1);
                                        CarpoolRideDetailsActivity.this.finish();
                                    }
                                } else if (CarpoolRideDetailsActivity.this.mDrivingToPickup) {
                                    $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ARRIVED);
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                                    Logger.m36d("CarpoolRideDetailsActivity: setupButtons: confirming driver arrived");
                                    CarpoolNativeManager $r15 = CarpoolRideDetailsActivity.this.mCpnm;
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r15.checkDriverArrived($r7.getId(), new C15031());
                                } else if (CarpoolRideDetailsActivity.this.mArrivedAtPickup) {
                                    $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_RIDER_ONBOARD);
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                                    Logger.m36d("CarpoolRideDetailsActivity: setupButtons: confirming pax pickup");
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    if (CarpoolUtils.confirmPaxPickedUp($r7)) {
                                        CarpoolUtils.closeSwipableLayout();
                                        CarpoolRideDetailsActivity.this.setResult(-1);
                                        CarpoolRideDetailsActivity.this.finish();
                                    }
                                } else {
                                    $r6 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_GO);
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6 = $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r7.getSomeRideId());
                                    $r7 = CarpoolRideDetailsActivity.this.mDrive;
                                    $r6.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7.getId()).send();
                                    Logger.m36d("CarpoolRideDetailsActivity: setupButtons: confirming start carpool");
                                    CarpoolRideDetailsActivity.this.mCpnm.getMeetingIdByDrive(CarpoolRideDetailsActivity.this.mDrive, true, new C15042());
                                }
                            }
                        }
                    }
                });
                $r11.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        CarpoolRideDetailsActivity.this.cancelOrDismissRide();
                    }
                });
                return;
            }
            this.mBtButtonsLayout.setVisibility(8);
            return;
        }
        findViewById(C1283R.id.rideRequestBottomLayout).setVisibility(8);
    }

    private void showReallyArrivedDialog() throws  {
        String $r3;
        if (this.mDrive.isMultiPax()) {
            $r3 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_MANY_BODY);
        } else {
            $r3 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_BODY);
        }
        MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_TITLE), $r3, false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int $i0) throws  {
                if ($i0 == 1) {
                    CarpoolRideDetailsActivity.this.goToArrivedMode();
                }
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_YES), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_NO), 0);
    }

    private void goToArrivedMode() throws  {
        this.mCpnm.driverArrived(this.mDrive);
        CarpoolUtils.closeSwipableLayout();
        setResult(-1);
        finish();
    }

    void acceptTheRide() throws  {
        if (MyWazeNativeManager.getInstance().getContactLoggedInNTV()) {
            Logger.m36d("CarpoolRideDetailsActivity: acceptTheRide: user is waze reg, accepting");
            validateCarpoolUser();
            return;
        }
        Logger.m43w("CarpoolRideDetailsActivity: acceptTheRide: user is not waze reg, passing to register");
        Intent $r1 = new Intent(this, PhoneRegisterActivity.class);
        $r1.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
        startActivityForResult($r1, WAZE_REG_REQUEST_CODE);
    }

    void validateCarpoolUser() throws  {
        CarpoolUserData $r5 = this.mCpnm.getCarpoolProfileNTV();
        String $r7 = MyWazeNativeManager.getInstance().getPhoneNumberNTV();
        if ($r5 == null) {
            Logger.m38e("CarpoolRideDetailsActivity: validateCarpoolUser: Do not have a carpool user at confirmation stage! Internal error");
            CarpoolUtils.showError("");
        } else if ($r5.hasPhoneNumber() && $r5.getRealPhoneNumber().equalsIgnoreCase($r7)) {
            Logger.m36d("CarpoolRideDetailsActivity: validateCarpoolUser: Carpool user has correct phone number");
            if (this.mIsPictureAlreadyAdded) {
                openConfirmDialog();
                return;
            }
            $r7 = $r5.getImage();
            if ($r7 == null || $r7.isEmpty()) {
                Logger.m36d("CarpoolAddPhotoActivity: image url is null");
                updateUserPicture(false);
                return;
            }
            this.mPicTimeoutOccurred = false;
            AnonymousClass39 anonymousClass39 = new Runnable() {
                public void run() throws  {
                    CarpoolRideDetailsActivity.this.mPicTimeoutOccurred = true;
                    CarpoolRideDetailsActivity.this.updateUserPicture(true);
                }
            };
            this.mPicTimeoutOccurred = false;
            this.mHandler.postDelayed(anonymousClass39, NETWORK_TIMEOUT);
            final AnonymousClass39 anonymousClass392 = anonymousClass39;
            VolleyManager.getInstance().loadImageFromUrl($r7, new ImageRequestListener() {
                boolean receivedCacheResponse = false;

                public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) throws  {
                    CarpoolRideDetailsActivity.this.mHandler.removeCallbacks(anonymousClass392);
                    if (!CarpoolRideDetailsActivity.this.mPicTimeoutOccurred) {
                        CarpoolRideDetailsActivity.this.openConfirmDialog();
                        Logger.m36d("CarpoolRideDetailsActivity: onImageLoadComplete: moving to confirm");
                    }
                }

                public void onImageLoadFailed(Object token, long duration) throws  {
                    if (!CarpoolRideDetailsActivity.this.mPicTimeoutOccurred) {
                        if (this.receivedCacheResponse) {
                            Logger.m36d("CarpoolAddPhotoActivity: onImageLoadFailed: Waze data image URL failed, not passing image");
                            CarpoolRideDetailsActivity.this.mHandler.removeCallbacks(anonymousClass392);
                            CarpoolRideDetailsActivity.this.updateUserPicture(true);
                            return;
                        }
                        Logger.m36d("CarpoolAddPhotoActivity: onImageLoadFailed: received from cache");
                        this.receivedCacheResponse = true;
                    }
                }
            });
        } else {
            this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
            NativeManager $r8 = this.mNm;
            $r8.lockProgressPopup();
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CREATE_PROFILE_REQUEST);
            Logger.m43w("CarpoolRideDetailsActivity: validateCarpoolUser: Carpool user does not have correct phone number, updating");
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
            this.mCpnm.createUser(5, $r5.email, $r5.getGivenName(), $r5.family_name, $r5.photo_url, $r7, null, null, null, null, 0, null, null);
        }
    }

    private void updateUserPicture(boolean $z0) throws  {
        Intent $r1 = new Intent(this, CarpoolAddPhotoActivity.class);
        $r1.putExtra(CarpoolAddPhotoActivity.INTENT_USER_PICTURE_MANDATORY, true);
        $r1.putExtra(CarpoolAddPhotoActivity.INTENT_USER_PICTURE_ARRIVE_FROM, 2);
        $r1.putExtra(CarpoolAddPhotoActivity.INTENT_USER_PICTURE_UPDATE, $z0);
        startActivityForResult($r1, USER_PIC_REQUEST_CODE);
    }

    void openConfirmDialog() throws  {
        AnonymousClass41 $r3 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface $r1, int $i0) throws  {
                $r1.dismiss();
                if ($i0 == 1) {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_RIDE_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                    CarpoolRideDetailsActivity.this.mCpnm.confirmRideRequest(CarpoolRideDetailsActivity.this.mRide, CarpoolRideDetailsActivity.this.mUtcTimeSecSelected, CarpoolRideDetailsActivity.this.mIsOnboardingDone, CarpoolRideDetailsActivity.this.mRealtimeRide);
                    CarpoolRideDetailsActivity.this.waitForUpdate();
                    return;
                }
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_RIDE_CLICKED).addParam("ACTION", "BACK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
            }
        };
        new RideConfirmDialog(this, this.mDrive, this.mUtcTimeSecSelected, $r3).show();
    }

    private void onRejectRideAnswer(String messageID, String $r2) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_CANCEL_QUESTION).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $r2).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).send();
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CANCELATION_POPUP_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "CANCEL_RIDE").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).send();
        if (this.mDrive != null) {
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_REJECT_RIDE_RES, this.mHandler);
            Logger.m36d("CarpoolRideDetailsActivity: Rejecting ride");
            this.mCpnm.rejectRideRequest(this.mDrive.getSomeRideId(), $r2);
            waitForUpdate();
        }
    }

    void cancelOrDismissRide() throws  {
        cancelMessageTooltip();
        if (this.mWaitingForUpdate) {
            MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
        } else if (this.mRideState == 2 || this.mRideState == 7) {
            CarpoolUtils.cancelDriveAfterAccepted(this.mDrive, this, this);
        } else if (this.mRideState == 1 && this.mDrive != null) {
            boolean $z0 = this.mCpnm.configShouldAskDeclineReasonNTV() && !this.mIsOnboarding;
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", "DECLINE").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_SHOULD_ASK, Boolean.toString($z0)).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).send();
            QuestionData $r11 = this.mDrive.drive_match_info;
            QuestionData $r10 = $r11;
            if ($r11.question != null) {
                $r11 = this.mDrive.drive_match_info;
                $r10 = $r11;
                $r11 = $r11.question;
                if ($r11.Text != null) {
                    $r11 = this.mDrive.drive_match_info;
                    $r10 = $r11;
                    $r11 = $r11.question;
                    if (!$r11.Text.isEmpty()) {
                        $r11 = this.mDrive.drive_match_info;
                        $r10 = $r11;
                        new QuestionDialog(this, $r11.question, new ResponseHandler() {
                            public void onResponse(String $r1, String $r2) throws  {
                                CarpoolRideDetailsActivity.this.onRejectRideAnswer(CarpoolRideDetailsActivity.this.mDrive.drive_match_info.question.QuestionID, $r1);
                                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_TUNEUP_POPUP_CLICKED).addParam("ACTION", $r2).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolRideDetailsActivity.this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolRideDetailsActivity.this.mDrive.getId()).send();
                            }
                        }).show();
                        CarpoolNativeManager.getInstance().setTuneupQuestionFlag();
                        AnalyticsBuilder $r9 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_TUNEUP_POPUP_SHOWN);
                        $r11 = this.mDrive.drive_match_info;
                        $r10 = $r11;
                        $r11 = $r11.question;
                        $r9.addParam("TYPE", $r11.Text).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).send();
                        return;
                    }
                }
            }
            if (!this.mRealtimeRide && $z0) {
                Intent intent = new Intent(this, ReasonSelectionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("reasons", this.mCpnm.configGetDeclineReasonsNTV());
                bundle.putString("title", this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_DECLINE_REASON_CHOOSE_REASON));
                bundle.putParcelable("CarpoolRide", this.mDrive);
                bundle.putInt("updateServer", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1003);
            } else if (this.mRealtimeRide) {
                this.mCpnm.rejectRideRequest(this.mDrive.getSomeRideId(), null);
                setResult(11);
                finish();
            } else {
                CarpoolNativeManager $r8 = this.mCpnm;
                int $i0 = CarpoolNativeManager.UH_CARPOOL_REJECT_RIDE_RES;
                IncomingHandler $r14 = this.mHandler;
                $r8.setUpdateHandler($i0, $r14);
                Logger.m36d("CarpoolRideDetailsActivity: Rejecting ride");
                this.mCpnm.rejectRideRequest(this.mDrive.getSomeRideId(), null);
                waitForUpdate(11);
            }
        }
    }

    private void blockRider(final CarpoolUserData $r1) throws  {
        if ($r1 != null) {
            if (this.mDrive != null) {
                this.mDrive.getId();
            }
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_BLOCK).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).send();
            final ReportRiderDialog $r2 = new ReportRiderDialog(this, $r1);
            $r2.setType(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_BLOCK));
            $r2.setBlock(true);
            $r2.setMaxLength(ConfigValues.getIntValue(45));
            $r2.setOnOk(new OnClickListener() {
                public void onClick(View v) throws  {
                    String $r3 = $r2.getText();
                    CarpoolRideDetailsActivity.this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_BLOCK_USER_RES, CarpoolRideDetailsActivity.this.mHandler);
                    CarpoolNativeManager.getInstance().reportUser($r1.id, CarpoolRideDetailsActivity.this.mDrive.getId(), 0, $r3, true);
                    CarpoolNativeManager.getInstance().blockUser($r1.id);
                    NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
                }
            });
            $r2.show();
        }
    }

    public void onImageRetrieved(final View $r1, final CarpoolUserData $r2, final Bitmap $r3) throws  {
        if ($r3 != null) {
            post(new Runnable() {
                public void run() throws  {
                    ImageView $r3 = (ImageView) $r1.findViewById(C1283R.id.rideRequestRiderImage);
                    CircleShaderDrawable $r1 = new CircleShaderDrawable($r3, 0);
                    $r3.setImageDrawable($r1);
                    if (CarpoolRideDetailsActivity.this.mMultiPaxRide) {
                        RiderImageAndMessageCounter $r10 = (RiderImageAndMessageCounter) CarpoolRideDetailsActivity.this.mRiderImageAndMessageCounter.get($r2.id);
                        if ($r10 == null) {
                            $r10 = new RiderImageAndMessageCounter();
                        }
                        $r10.image = $r1;
                        CarpoolRideDetailsActivity.this.mRiderImageAndMessageCounter.put($r2.id, $r10);
                    }
                }
            });
        }
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == NavigateNativeManager.UH_CARPOOL_MAP_DRAW) {
            return true;
        }
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_CLOSE_RIDE_DETAILS) {
            Logger.m36d("CarpoolRideDetailsActivity: closing activity upon request");
            setResult(-1);
            finish();
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED) {
            Logger.m36d("CarpoolRideDetailsActivity: myHandleMessage: received carpool drive updated");
            if (this.mThanked) {
                return true;
            }
            r21 = (CarpoolDrive) $r1.getData().getParcelable(CarpoolDrive.class.getSimpleName());
            if (isThisDrive(r21)) {
                refreshRideDetails(r21);
                NativeManager.getInstance().CloseProgressPopup();
            }
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE) {
            $r7 = this.mNm;
            $r7.CloseProgressPopup();
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE, this.mHandler);
            if ($r1.getData().getInt("res") == 0) {
                $r7 = NativeManager.getInstance();
                $r7.OpenProgressIconPopup($r7.getLanguageString((int) DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRMATION_NON_LAST_RIDER), "sign_up_big_v");
                r2 = $r7;
                postDelayed(new Runnable() {
                    public void run() throws  {
                        r2.CloseProgressPopup();
                    }
                }, 2000);
            } else {
                MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
            }
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED) {
            if (this.mDrive != null) {
                CarpoolDrive $r2 = this.mDrive;
                if ($r2.getId() != null) {
                    $r2 = this.mDrive;
                    if ($r2.getId().contentEquals($r1.getData().getString("id", ""))) {
                        doneWaitingForUpdate(null, null);
                        setResult(11);
                        finish();
                        return true;
                    }
                }
            }
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED) {
            CarpoolDrive[] $r13 = (CarpoolDrive[]) $r1.getData().getParcelableArray("drives");
            if ($r13 == null || this.mThanked) {
                return true;
            }
            for (CarpoolDrive carpoolDrive : $r13) {
                if (isThisDrive(carpoolDrive)) {
                    refreshRideDetails(carpoolDrive);
                    NativeManager.getInstance().CloseProgressPopup();
                }
            }
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            String $r5;
            $r3 = $r1.getData();
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
            $r7 = this.mNm;
            $r7.unlockProgressPopup();
            $r7 = this.mNm;
            $r7.CloseProgressPopup();
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
            $z0 = $r3.getBoolean("success");
            CarpoolUserData $r14 = (CarpoolUserData) $r3.getParcelable("user");
            if ($z0) {
                $r5 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE;
            } else {
                $r5 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE;
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CREATE_PROFILE_DONE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, $r5);
            if ($z0) {
                NativeManager.setPushToken(true);
                Logger.m36d("CarpoolRideDetailsActivity: Carpool user updated with correct phone number");
                openConfirmDialog();
            } else {
                Logger.m38e("CarpoolRideDetailsActivity: Failed to create user in server, success=" + $z0);
                CarpoolUtils.showError(null);
            }
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_ERROR) {
            $r7 = this.mNm;
            $r7.unlockProgressPopup();
            $r7 = this.mNm;
            $r7.CloseProgressPopup();
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CREATE_PROFILE_DONE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE);
            $r3 = $r1.getData();
            CarpoolUtils.parseCarpoolUserCreateError($r3.getInt("type"), $r3.getString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE), null, null);
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_BLOCK_USER_RES) {
            $r7 = this.mNm;
            $r7.CloseProgressPopup();
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_BLOCK_USER_RES, this.mHandler);
            if ($r1.getData().getInt("res") == 0) {
                $r7 = NativeManager.getInstance();
                $r7.OpenProgressIconPopup($r7.getLanguageString((int) DisplayStrings.DS_CARPOOL_BLOCK_OK), "sign_up_big_v");
                r2 = $r7;
                postDelayed(new Runnable() {
                    public void run() throws  {
                        r2.CloseProgressPopup();
                        CarpoolRideDetailsActivity.this.finish();
                    }
                }, 2000);
            } else {
                MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) throws  {
                        CarpoolRideDetailsActivity.this.finish();
                    }
                });
            }
            return true;
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_RATE_RIDER_RES) {
            $r7 = this.mNm;
            $r7.CloseProgressPopup();
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RATE_RIDER_RES, this.mHandler);
            NativeManager.getInstance().CloseProgressPopup();
            if ($r1.getData().getInt("res") == 0) {
                this.mThanked = true;
                if (this.mRide != null) {
                    this.mRide.driver_reviewed = true;
                }
                $z0 = false;
                int[] $r19 = this.mRiderRating;
                int[] $r192 = $r19;
                $i1 = $r19.length;
                for (int $i2 = 0; $i2 < $i1; $i2++) {
                    if ($r192[$i2] == 1) {
                        $z0 = Boolean.TRUE.booleanValue();
                        break;
                    }
                }
                if ($z0 && ConfigValues.getBoolValue(33)) {
                    MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_LOW_RATING_TITLE), DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_LOW_RATING_TEXT), false, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int $i0) throws  {
                            if ($i0 == 0) {
                                CarpoolRideDetailsActivity.this.showRatingSubmitted();
                                return;
                            }
                            CarpoolUtils.startFeedbackActivity(CarpoolRideDetailsActivity.this.mDrive, CarpoolRideDetailsActivity.this.mDrive.getRide(CarpoolRideDetailsActivity.this.mRatedRiderNum), CarpoolRideDetailsActivity.this.mRiderImageAndMessageCounter, CarpoolRideDetailsActivity.this, CarpoolRideDetailsActivity.this, false);
                        }
                    }, DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_LOW_RATING_SEND_FEEDBACK), DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_LOW_RATING_SKIP), -1, null, null, false, true, false);
                } else {
                    showRatingSubmitted();
                }
            } else {
                MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) throws  {
                        CarpoolRideDetailsActivity.this.finish();
                    }
                });
            }
            return true;
        } else if ($r1.what != CarpoolNativeManager.UH_CARPOOL_REJECT_RIDE_RES) {
            return super.myHandleMessage($r1);
        } else {
            Logger.m36d("CarpoolRideDetailsActivity: Received UH_CARPOOL_REJECT_RIDE_RES msg");
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_REJECT_RIDE_RES, this.mHandler);
            if (this.mTimeoutRunnable != null) {
                cancel(this.mTimeoutRunnable);
            }
            setResult(11);
            finish();
            return true;
        }
    }

    private boolean isThisDrive(CarpoolDrive $r1) throws  {
        return $r1 != null && ((this.mDrive.uuid != null && this.mDrive.uuid.equals($r1.uuid)) || !($r1.getRide() == null || this.mRide == null || this.mRide.uuid == null || !this.mRide.uuid.equals($r1.getRide().uuid)));
    }

    private void refreshRideDetails(CarpoolDrive $r1) throws  {
        int $i0 = this.mDrive.getState(this.mRide);
        int $i1 = $r1.getState(null);
        CarpoolDrive $r12;
        if (($i0 == 1 || $i0 == 13) && $i1 != 1 && $i1 != 13 && $i1 != 3 && $i1 != 7 && $i1 != 16 && $i1 != 10) {
            Logger.m38e("Unavailable: CarpoolRideDetailsActivity: ride state entry: " + $i0 + "; new ride state entry: " + $i1);
            doneWaitingForUpdate(null, null);
            Intent $r2 = new Intent(this, RideUnavailableActivity.class);
            $r12 = this.mDrive;
            $r2.putExtra(RideUnavailableActivity.RIDE_ID, $r12.uuid);
            startActivityForResult($r2, 1);
        } else if (($i0 == 1 || $i0 == 13) && $i1 == 3) {
            doneWaitingForUpdate(null, null);
            setResult(11);
            finish();
        } else if ((this.mRideState == 2 || this.mRideState == 7) && $i1 == 3) {
            if (this.shortCancel) {
                doneWaitingForUpdate(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CANCEL_BAD_MSG), "popup_x_icon");
            } else {
                doneWaitingForUpdate(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CANCEL_MSG), "sign_up_big_v");
            }
        } else if (($i0 == 1 || $i0 == 13) && $i1 == 7) {
            doneWaitingForUpdate(null, null);
            AppService.Post(new Runnable() {
                public void run() throws  {
                    final NativeManager $r1 = NativeManager.getInstance();
                    $r1.OpenProgressIconPopup($r1.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_RIDE_SET), "sign_up_big_v");
                    CarpoolRideDetailsActivity.this.postDelayed(new Runnable() {
                        public void run() throws  {
                            $r1.CloseProgressPopup();
                            CarpoolRideDetailsActivity.this.finish();
                        }
                    }, 2000);
                }
            }, 1000);
            setResult(10, new Intent());
            finish();
        } else if (!this.mRealtimeRide) {
            if (!($r1.getRide() == null || this.mRide == null || !this.mRide.driver_reviewed)) {
                $r1.getRide().driver_reviewed = true;
            }
            this.mDrive = $r1;
            $r12 = this.mDrive;
            this.mRide = $r12.getRide();
            if (this.mRide != null) {
                CarpoolRide $r4 = this.mRide;
                boolean $z0 = this.mRealtimeRide;
                $r4.setIsRealTime($z0);
                CarpoolUserData carpoolUserData = this.mRide.rider;
                CarpoolUserData $r9 = carpoolUserData;
                this.mRider = carpoolUserData;
            }
            setupActivity();
        }
    }

    protected void onActivityResult(int requestCode, int $i1, Intent data) throws  {
        updateChatBadge(getRiderButtonsView(findViewById(C1283R.id.rideRequestRiderLayout)), null);
        if (requestCode == 1001) {
            if ($i1 == -1) {
                if (this.mRideState == 4) {
                    this.mRatedRiderNum++;
                    if (this.mRatedRiderNum < this.mDrive.getRidesAmount()) {
                        showRideRateRider();
                        return;
                    }
                }
                if (this.mRide != null) {
                    this.mRide.driver_reviewed = true;
                }
                changeThanksToCompleteState(false);
                return;
            }
        } else if (requestCode == 1002) {
            if ($i1 == 0) {
                setResult(-1);
                finish();
                return;
            } else if (this.mRealtimeRide && isOnboarded()) {
                this.mCpnm.getDriveByRideId(this.mRide, new IResultObj<CarpoolDrive>() {
                    public void onResult(CarpoolDrive $r1) throws  {
                        if ($r1 != null) {
                            CarpoolRideDetailsActivity.this.mDrive = $r1;
                            CarpoolRideDetailsActivity.this.mRide.setIsRealTime(CarpoolRideDetailsActivity.this.mRealtimeRide);
                            CarpoolRideDetailsActivity.this.setupActivity();
                            return;
                        }
                        Logger.m38e("CarpoolRideDetailsActivity: failed to get drive for ride id " + CarpoolRideDetailsActivity.this.mRide.getId());
                    }
                });
                ((TextView) findViewById(C1283R.id.rideRequestButAccept)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_ACCEPT_RT_REGISTERED));
                return;
            }
        } else if (requestCode == 1003 && $i1 == -1) {
            waitForUpdate(11);
            return;
        } else if (requestCode == 1004 && $i1 == -1) {
            if (this.mDrive != null) {
                Logger.m36d("CarpoolRideDetailsActivity: waiting for ride to be deleted");
                waitForUpdate();
                return;
            }
            return;
        }
        if ($i1 == 461) {
            setupActivity();
        } else if (requestCode == 28541) {
            if ($i1 == -1) {
                Logger.m36d("CarpoolRideDetailsActivity: user pic added, passing to confirm");
                this.mIsPictureAlreadyAdded = true;
                validateCarpoolUser();
                return;
            }
            this.mIsPictureAlreadyAdded = false;
            Logger.m36d("CarpoolRideDetailsActivity: user did NOT add pic, doing nothing");
        } else if (requestCode == 28540) {
            if ($i1 != -1) {
                Logger.m36d("CarpoolRideDetailsActivity: user did NOT waze registered, error occurred/ doing nothing");
            } else if (MyWazeNativeManager.getInstance().getContactLoggedInNTV()) {
                Logger.m36d("CarpoolRideDetailsActivity: user waze registered, passing to confirm");
                validateCarpoolUser();
            } else {
                Logger.m36d("CarpoolRideDetailsActivity: user did NOT waze registered, doing nothing");
            }
        } else if (requestCode == CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY && $i1 == -1 && (this.mIsOnboarding || this.mRealtimeRide)) {
            this.mIsOnboardingDone = true;
            checkIfWeShouldDisableButtons();
            if (this.mWasTimeSelected) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_RIDE_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).send();
                acceptTheRide();
                return;
            }
            int $i0 = this.mRide.itinerary;
            CarpoolItinerary $r12 = $i0;
            long $l3 = $i0.window_duration_seconds;
            requestCode = $l3;
            long $l32 = (long) $l3;
            $l3 = this.mRide.itinerary;
            $r12 = $l3;
            long $l4 = $l3.window_start_time;
            $i0 = this.mDrive.drive_match_info;
            CarpoolDriveMatchInfo $r13 = $i0;
            requestCode = $i0.dropoff_to_destination_duration_seconds;
            $i0 = this.mDrive.drive_match_info;
            $r13 = $i0;
            $l3 = requestCode + $i0.pickup_to_dropoff_duration_seconds;
            Object obj = $l3;
            openSelectTimeDialog($l32, $l4, (long) $l3, true);
        } else {
            if (requestCode == 1006 && this.mIsOnboarding && !this.mIsOnboardingDone && this.mCpnm.isDriverOnboarded()) {
                this.mIsOnboardingDone = true;
                checkIfWeShouldDisableButtons();
            }
            if ($i1 == -1) {
                setResult($i1);
                finish();
            }
        }
    }

    private void checkIfWeShouldDisableButtons() throws  {
        final AnonymousClass52 $r1 = new OnClickListener() {
            public void onClick(View view) throws  {
                MsgBox.openMessageBoxTimeout(CarpoolRideDetailsActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_UHHOHE), CarpoolRideDetailsActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
            }
        };
        if (this.mDrive == null || (this.mDrive.getId() == null && (this.mRide == null || this.mRide.getId() == null))) {
            findViewById(C1283R.id.rideRequestMessageButton).setOnClickListener($r1);
            findViewById(C1283R.id.rideRequestCallButton).setOnClickListener($r1);
            return;
        }
        CarpoolUtils.isIdInDrivesList(this.mDrive.getId() != null ? this.mDrive.getId() : this.mRide.getId(), new IResultOk() {
            public void onResult(boolean $z0) throws  {
                if (!$z0) {
                    CarpoolRideDetailsActivity.this.findViewById(C1283R.id.rideRequestMessageButton).setOnClickListener($r1);
                    CarpoolRideDetailsActivity.this.findViewById(C1283R.id.rideRequestCallButton).setOnClickListener($r1);
                }
            }
        });
    }

    private void driveToMeeting(final boolean $z0, final boolean $z1) throws  {
        this.mCpnm.getMeetingIdByDrive(this.mDrive, $z0, new IResultObj<String>() {
            public void onResult(String $r1) throws  {
                CarpoolRideDetailsActivity.this.driveToMeeting($z0, $z1, $r1);
            }
        });
    }

    private void driveToMeeting(boolean $z0, boolean $z1, String $r1) throws  {
        if ($r1 == null || $r1.isEmpty()) {
            if ($z0) {
                this.mDtnm.navigate(this.mCpnm.driveGetAddressItem(this.mDrive, 2), null);
                setResult(-1);
                finish();
                return;
            }
            this.mDtnm.navigate(this.mCpnm.driveGetAddressItem(this.mDrive, 3), null);
            setResult(-1);
            finish();
        } else if ($z1) {
            this.mDtnm.drive($r1, false);
            setResult(-1);
            finish();
        } else {
            this.mCpnm.safeDriveToMeeting($r1, $z0, new IResultOk() {

                class C15091 implements Runnable {
                    int waitForDialog = 5;

                    class C15081 implements OnDismissListener {
                        C15081() throws  {
                        }

                        public void onDismiss(DialogInterface dialog) throws  {
                            CarpoolRideDetailsActivity.this.setResult(-1);
                        }
                    }

                    C15091() throws  {
                    }

                    public void run() throws  {
                        if (CarpoolRideDetailsActivity.this.dialog == null) {
                            this.waitForDialog--;
                            if (this.waitForDialog >= 0) {
                                CarpoolRideDetailsActivity.this.postDelayed(this, 100);
                                return;
                            }
                            CarpoolRideDetailsActivity.this.setResult(-1);
                            CarpoolRideDetailsActivity.this.finish();
                            return;
                        }
                        CarpoolRideDetailsActivity.this.dialog.setOnDismissListener(new C15081());
                    }
                }

                public void onResult(boolean $z0) throws  {
                    if ($z0) {
                        CarpoolRideDetailsActivity.this.postDelayed(new C15091(), 1);
                        return;
                    }
                    CarpoolRideDetailsActivity.this.setResult(-1);
                    CarpoolRideDetailsActivity.this.finish();
                }
            });
        }
    }

    public void onComplete() throws  {
        setResult(-1);
        finish();
    }

    public void onBackPressed() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_SCREEN_CLICKED).addParam("ACTION", "BACK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).send();
        if (this.mThanked || this.mChatted) {
            setResult(10);
        }
        super.onBackPressed();
    }

    private void scrollToTop() throws  {
        this.mScrollView.post(new Runnable() {
            public void run() throws  {
                CarpoolRideDetailsActivity.this.mScrollView.smoothScrollTo(0, 0);
            }
        });
    }

    private void setAllChildrenEnabled(View $r1, boolean $z0) throws  {
        if ($r1 instanceof ViewGroup) {
            ViewGroup $r2 = (ViewGroup) $r1;
            int $i0 = $r2.getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                setAllChildrenEnabled($r2.getChildAt($i1), $z0);
            }
            return;
        }
        $r1.setEnabled($z0);
    }

    private boolean isOnboarded() throws  {
        CarpoolUserData $r2 = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
        return $r2 != null && $r2.didFinishOnboarding();
    }

    private void setMultiPax() throws  {
        findViewById(C1283R.id.rideRequestRidersOnRideLayout).setVisibility(0);
        ((TextView) findViewById(C1283R.id.rideRequestRidersOnRideTitle)).setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_DETAILS_RIDERS_ON_RIDE_PD, new Object[]{Integer.valueOf(this.mDrive.getRidesAmount())}));
        RidersImages $r7 = (RidersImages) findViewById(C1283R.id.rideRequestRidersOnRideImages);
        $r7.setStrokeDp(0);
        $r7.clearImages();
        for (int $i0 = 0; $i0 < this.mDrive.getRidesAmount(); $i0++) {
            CarpoolUserData $r8 = this.mDrive.getRider($i0);
            if ($r8 != null) {
                $r7.addImage($r8.getImage());
            }
        }
        $r7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                AnimationUtils.focusOnView(CarpoolRideDetailsActivity.this.mScrollView, CarpoolRideDetailsActivity.this.findViewById(C1283R.id.rideRequestRiderLayout), -20);
            }
        });
    }
}
