package com.waze.carpool;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ChatNotificationManager;
import com.waze.ChatNotificationManager.ChatHandler;
import com.waze.ConfigManager;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.RightSideMenu;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager.DriveUpdates;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.carpool.MissingPermissionsActivity.MPType;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.FullScreenTooltipShape;
import com.waze.ifs.ui.FullScreenTooltipShape.IToolTipClicked;
import com.waze.location.LocationHistory;
import com.waze.location.LocationHistory.LocationHistoryOptInStatus;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.navigate.PublicMacros;
import com.waze.settings.SettingsCarpoolPaymentsActivity;
import com.waze.settings.SettingsCarpoolWorkActivity;
import com.waze.settings.SettingsTitleText;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.utils.ImageRepository;
import com.waze.utils.PixelMeasure;
import com.waze.view.text.WazeTextView;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TimeZone;

public class CarpoolRidesFragment extends Fragment implements IResultObj<CarpoolDrive[]> {
    public static final String INT_VIEW_MODE = "INT_VIEW_MODE";
    private static final int RC_OFFER_RIDE = 1227;
    private static final int RC_UPDATE_GOOGLE_PLAY_SERVICES = 1225;
    private static final String TAG = CarpoolRidesFragment.class.getName();
    private static final long UPDATE_TIMER = 60000;
    public static final int VIEWING_HISTORY = 2;
    public static final int VIEWING_NEARBY = 3;
    public static final int VIEWING_RIDES = 1;
    public static final int VIEWING_UNDEFINED = 0;
    private static Banner mLastShownBanner = null;
    private static boolean mbNewUser = false;
    private static boolean sUserClosedBannerThisSession = false;
    private WazeTextView legal;
    private RideListAdapter mAdapter;
    private ChatHandler mChatHandler = new C15451();
    private boolean mCleaned = true;
    CarpoolNativeManager mCpnm;
    private CarpoolDrive[] mDrives;
    private HashMap<String, CarpoolDrive> mDrivesById = new HashMap(8);
    private INextActionCallback mGetAnswerCb = new C15462();
    private boolean mHadPreviousRides;
    Handler mHandler = new MyHandler(this);
    ISetTitle mISetTitle;
    private LayoutInflater mInflater;
    private int mIntentMode = 0;
    private boolean mIsInMainActivity;
    private boolean mIsOnboarding = false;
    private ListView mList;
    private boolean mListAvailable = false;
    private Parcelable mListState;
    private int mNowViewing = 1;
    private int mNumDrives = 0;
    private int mNumFilteredDrives;
    private FullScreenTooltipShape mOfferDriveTip;
    boolean mOfferRideTooltipShown = false;
    private CarpoolUserData mProfile;
    private int mRideRequestsAmount = -1;
    private boolean mTestedLocHist = false;
    private Runnable mUpdateRunnable = new C15473();
    private boolean mbViewModeSetOnIntent = false;

    public interface ISetTitle {
        void setTitleBar(int i) throws ;
    }

    class C15451 implements ChatHandler {

        class C15441 implements Runnable {
            C15441() throws  {
            }

            public void run() throws  {
                CarpoolRidesFragment.this.mAdapter.notifyDataSetChanged();
            }
        }

        C15451() throws  {
        }

        public boolean onChatMessage(String message) throws  {
            ((ActivityBase) CarpoolRidesFragment.this.getActivity()).post(new C15441());
            return false;
        }

        public void onMessagesLoaded() throws  {
        }

        public void onMessageSent(boolean success) throws  {
            ActivityBase $r3 = (ActivityBase) CarpoolRidesFragment.this.getActivity();
            if ($r3.isRunning()) {
                $r3.showPopup(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CARPOOL_MESSAGING_QUICKLINKS_SENT), "sign_up_big_v");
            }
        }

        public void onMessageRead(String msgId) throws  {
        }
    }

    class C15462 implements INextActionCallback {
        C15462() throws  {
        }

        public void setNextIntent(Intent $r1) throws  {
            CarpoolRidesFragment.this.startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
        }

        public void setNextFragment(Fragment $r1) throws  {
            AppService.getMainActivity().getLayoutMgr().getRightSideMenu().replaceCarpoolFragment($r1);
        }

        public void setNextResult(int $i0) throws  {
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                CarpoolRidesFragment.this.cleanup();
                CarpoolRidesFragment.this.getActivity().onBackPressed();
            } else if ($i0 == -1) {
                Logger.m41i("CarpoolRidesFragment: received RESULT OK");
            } else {
                Logger.m38e("CarpoolRidesFragment: received unexpected result:" + $i0);
            }
        }

        public Context getContext() throws  {
            return CarpoolRidesFragment.this.getActivity();
        }
    }

    class C15473 implements Runnable {
        C15473() throws  {
        }

        public void run() throws  {
            if (CarpoolRidesFragment.this.mList != null) {
                CarpoolRidesFragment.this.mListState = CarpoolRidesFragment.this.mList.onSaveInstanceState();
            }
            CarpoolRidesFragment.this.mCpnm.getDrives(CarpoolRidesFragment.this.mNowViewing == 2, CarpoolRidesFragment.this);
            if (CarpoolRidesFragment.this.mNowViewing != 2 && CarpoolRidesFragment.this.getActivity() != null) {
                ((ActivityBase) CarpoolRidesFragment.this.getActivity()).postDelayed(CarpoolRidesFragment.this.mUpdateRunnable, CarpoolRidesFragment.UPDATE_TIMER);
            }
        }
    }

    class C15484 implements ITrackOrientation {
        C15484() throws  {
        }

        public void onOrientationChanged(int orientation) throws  {
            if (CarpoolRidesFragment.this.mOfferDriveTip != null && CarpoolRidesFragment.this.mOfferDriveTip.isShowing()) {
                CarpoolRidesFragment.this.mOfferDriveTip.dismissTooltip();
                CarpoolRidesFragment.this.mOfferDriveTip = null;
            }
        }
    }

    class C15495 implements Comparator<CarpoolDrive> {
        C15495() throws  {
        }

        public int compare(CarpoolDrive $r1, CarpoolDrive $r2) throws  {
            boolean $z1;
            boolean $z0 = $r1 == null || $r1.isEmpty();
            if ($r2 == null || $r2.isEmpty()) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            if ($z0 && $z1) {
                return 0;
            }
            if ($z0) {
                return 1;
            }
            if ($z1) {
                return -1;
            }
            if (CarpoolRidesFragment.this.mNowViewing == 1 && $r1.getState(null) != $r2.getState(null)) {
                if ($r1.isLive() || $r1.isUpcoming()) {
                    return -1;
                }
                if ($r2.isLive() || $r2.isUpcoming()) {
                    return 1;
                }
            }
            CarpoolDrive $r4 = null;
            if ($r2.hasParentDrive()) {
                $r4 = (CarpoolDrive) CarpoolRidesFragment.this.mDrivesById.get($r2.getParentDriveId());
            }
            CarpoolDrive $r8 = null;
            if ($r1.hasParentDrive()) {
                $r8 = (CarpoolDrive) CarpoolRidesFragment.this.mDrivesById.get($r1.getParentDriveId());
            }
            if (CarpoolRidesFragment.this.mNowViewing == 2) {
                return Long.valueOf($r2.getSortTime($r4)).compareTo(Long.valueOf($r1.getSortTime($r8)));
            }
            return Long.valueOf($r1.getSortTime($r8)).compareTo(Long.valueOf($r2.getSortTime($r4)));
        }
    }

    static abstract class RideListAbsItem {
        RideListAbsItem() throws  {
        }
    }

    static abstract class RideListCustom extends RideListAbsItem {
        public abstract View inflate(LayoutInflater layoutInflater, ViewGroup viewGroup) throws ;
    }

    class C15537 implements OnItemClickListener {
        C15537() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View v, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
            Object $r5 = CarpoolRidesFragment.this.mAdapter.getItem($i0);
            if ($r5 instanceof RideListItem) {
                CarpoolRidesFragment.this.rideClicked($i0, (RideListItem) $r5);
            } else if ($r5 instanceof DriveListItem) {
                CarpoolRidesFragment.this.driveClicked((DriveListItem) $r5);
            } else if (!($r5 instanceof RideListHeader) && ($r5 instanceof RideListDrill)) {
                ((RideListDrill) $r5).toDo.run();
            }
        }
    }

    class C15548 implements OnClickListener {
        C15548() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BUTTON_CLICKED).addParam("ACTION", "OFFER_RIDE").send();
            if (CarpoolRidesFragment.this.mIsOnboarding) {
                CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT_RTR, CarpoolRidesFragment.this.mGetAnswerCb);
                return;
            }
            CarpoolRidesFragment.this.startActivityForResult(new Intent(CarpoolRidesFragment.this.getActivity(), CarpoolOfferDriveActivity.class), 1227);
        }
    }

    private interface BannerInterface {
        int getCounterConfig() throws ;

        ShowResult shouldShow(CarpoolRidesFragment carpoolRidesFragment, CarpoolUserData carpoolUserData) throws ;

        void show(ActivityBase activityBase, View view) throws ;
    }

    private enum Banner implements BannerInterface {
        MULTIPAX {
            public int getCounterConfig() throws  {
                return 14;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                $r2.setVisibility(0);
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_green);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_MULTIPAX_INTRO);
                final View view = $r2;
                $r2.findViewById(C1283R.id.allRidesBannerClose).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "CLOSE");
                        ConfigManager.getInstance().setConfigSwitchValue(7, true);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.carpool_multipax_banner_illustration);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_MULTIPAX_INTRO_LINE1));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_MULTIPAX_INTRO_LINE2));
                final ActivityBase activityBase = $r1;
                final View view2 = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        CarpoolRiderJoinRequest.showMultipxIntroPopup(activityBase, null);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view2.setVisibility(8);
                        CarpoolRidesFragment.sUserClosedBannerThisSession = true;
                        ConfigManager.getInstance().setConfigSwitchValue(7, true);
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData profile) throws  {
                if (ConfigManager.getInstance().getConfigSwitchValue(7)) {
                    return ShowResult.DONT_SHOW;
                }
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                return ShowResult.SHOW;
            }
        },
        TUNEUP {
            public int getCounterConfig() throws  {
                return 12;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                $r2.setVisibility(0);
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_red);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_AVAILABILITY);
                final View view = $r2;
                $r2.findViewById(C1283R.id.allRidesBannerClose).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "CLOSE");
                        ConfigManager.getInstance().setConfigSwitchValue(6, true);
                        view.setVisibility(8);
                    }
                });
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.availabilty_icon_banner);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_TUNEUP_LINE1));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_TUNEUP_LINE2));
                final ActivityBase activityBase = $r1;
                final View view2 = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        Intent $r2 = new Intent(activityBase, CommuteModelWeekActivity.class);
                        $r2.putExtra("carpool", true);
                        activityBase.startActivityForResult($r2, 0);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view2.setVisibility(8);
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData profile) throws  {
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                if (CarpoolNativeManager.getInstance().getAndResetTuneupQuestionFlag()) {
                    return ShowResult.SHOW;
                }
                return ShowResult.DONT_SHOW;
            }
        },
        HW_CONFLICT {
            boolean mHomeConflict;
            boolean mWorkConflict;

            public int getCounterConfig() throws  {
                return -1;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                int $s0;
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_red);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", "CONFIRM_HW");
                $r2.findViewById(C1283R.id.allRidesBannerClose).setVisibility(8);
                if (this.mWorkConflict && this.mHomeConflict) {
                    $s0 = (short) 2232;
                } else if (this.mWorkConflict) {
                    $s0 = (short) 2234;
                } else {
                    $s0 = (short) 2233;
                }
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.home_icon_banner);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString($s0));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_CONFIRM_HOME_WORK_LINE2));
                final ActivityBase activityBase = $r1;
                final View view = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        Intent $r2 = new Intent(activityBase, AddHomeWorkActivity.class);
                        $r2.putExtra("carpool", true);
                        activityBase.startActivityForResult($r2, 0);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData $r2) throws  {
                if ($r2 == null) {
                    Logger.m38e("CarpoolRidesFragment: shouldShow: received null profile");
                    return ShowResult.DONT_SHOW;
                } else if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                } else {
                    this.mWorkConflict = $r2.inferredHomeConflict();
                    this.mHomeConflict = $r2.inferredWorkConflict();
                    return (this.mWorkConflict || this.mHomeConflict) ? ShowResult.SHOW : ShowResult.DONT_SHOW;
                }
            }
        },
        LOCATION_HISTORY {
            public boolean mInferred;
            boolean mNeededLocHist;
            boolean mTestedLocHist;

            public int getCounterConfig() throws  {
                return 1;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                int $s0;
                int $s1;
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_red);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", "LOCATION");
                final View view = $r2;
                $r2.findViewById(C1283R.id.allRidesBannerClose).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "CLOSE");
                        ConfigManager.getInstance().setConfigSwitchValue(1, true);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
                if (this.mInferred) {
                    $s0 = (short) 2228;
                    $s1 = (short) 2229;
                } else {
                    $s0 = (short) 2230;
                    $s1 = (short) 2231;
                }
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.megaphone_location_history_banner);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString($s0));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString($s1));
                final ActivityBase activityBase = $r1;
                final View view2 = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        Intent $r2 = new Intent(activityBase, MissingPermissionsActivity.class);
                        $r2.putExtra("MPType", MPType.MissingLocationHistory);
                        activityBase.startActivityForResult($r2, 0);
                        ConfigManager.getInstance().setConfigSwitchValue(1, true);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view2.setVisibility(8);
                    }
                });
            }

            public void resetCheck() throws  {
                this.mTestedLocHist = false;
            }

            public ShowResult shouldShow(final CarpoolRidesFragment $r1, CarpoolUserData $r2) throws  {
                if ($r2 == null) {
                    return ShowResult.DONT_SHOW;
                }
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                if (ConfigManager.getInstance().getConfigSwitchValue(1)) {
                    return ShowResult.DONT_SHOW;
                }
                this.mInferred = $r2.has_inferred_commute;
                if (this.mTestedLocHist) {
                    return ShowResult.fromBoolean(this.mNeededLocHist);
                }
                int $i0 = GooglePlayServicesUtil.isGooglePlayServicesAvailable($r1.getActivity());
                if ($i0 == 0) {
                    LocationHistory.checkLocationHistoryOptInStatus($r1.getActivity(), new LocationHistoryOptInStatus() {
                        public void onLocationHistoryOptInStatus(boolean $z0, boolean optedOut, boolean $z2) throws  {
                            C15694.this.mTestedLocHist = true;
                            if (!$z0 || $z2) {
                                C15694.this.mNeededLocHist = false;
                            } else {
                                C15694.this.mNeededLocHist = true;
                            }
                            $r1.setupBanner(true);
                        }
                    });
                } else if (GooglePlayServicesUtil.isUserRecoverableError($i0)) {
                    GooglePlayServicesUtil.showErrorDialogFragment($i0, $r1.getActivity(), 1225, new OnCancelListener() {
                        public void onCancel(DialogInterface dialog) throws  {
                            $r1.getActivity().finish();
                        }
                    });
                } else {
                    NativeManager $r9 = NativeManager.getInstance();
                    MsgBox.openMessageBoxWithCallback($r9.getLanguageString((int) DisplayStrings.DS_UHHOHE), $r9.getLanguageString((int) DisplayStrings.DS_GOOGLE_PLAY_SERVICES_ERROR), false, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) throws  {
                            $r1.getActivity().finish();
                        }
                    });
                }
                return ShowResult.CHECKING;
            }
        },
        HW_MISSING {
            boolean mHomeMissing;
            boolean mWorkMissing;

            public int getCounterConfig() throws  {
                return -1;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                int $s0;
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_orange);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", "ADD_HW");
                $r2.findViewById(C1283R.id.allRidesBannerClose).setVisibility(8);
                if (this.mWorkMissing && this.mHomeMissing) {
                    $s0 = (short) 2224;
                } else if (this.mWorkMissing) {
                    $s0 = (short) 2225;
                } else {
                    $s0 = (short) 2226;
                }
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.home_icon_banner);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString($s0));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_ADD_HOME_WORK_LINE2));
                final ActivityBase activityBase = $r1;
                final View view = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        Intent $r2 = new Intent(activityBase, AddHomeWorkActivity.class);
                        $r2.putExtra("carpool", true);
                        activityBase.startActivityForResult($r2, 0);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData $r2) throws  {
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                this.mWorkMissing = $r2.inferredHomeMissing();
                this.mHomeMissing = $r2.inferredWorkMissing();
                return (this.mWorkMissing || this.mHomeMissing) ? ShowResult.SHOW : ShowResult.DONT_SHOW;
            }
        },
        DRIVER_PROFILE {
            private int profileCompleteness;

            public int getCounterConfig() throws  {
                return 8;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_green);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_PROFILE);
                final View view = $r2;
                $r2.findViewById(C1283R.id.allRidesBannerClose).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "CLOSE");
                        ConfigManager.getInstance().setConfigSwitchValue(5, true);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
                ImageView $r5 = (ImageView) $r2.findViewById(C1283R.id.allRidesBannerProfileImage);
                CarpoolUserData $r7 = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
                if ($r7 != null) {
                    ImageRepository.instance.getImage($r7.getImage(), 2, $r5, null, $r1);
                }
                $r2.findViewById(C1283R.id.allRidesBannerProfileLayout).setVisibility(0);
                $r2.findViewById(C1283R.id.allRidesBannerImageFrame).setVisibility(8);
                TextView $r10 = (TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1);
                String $r9 = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_PROFILE_LINE1_PS);
                Object[] $r12 = new Object[1];
                int $i0 = this.profileCompleteness;
                $r12[0] = Integer.valueOf($i0);
                $r10.setText(String.format($r9, $r12));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_PROFILE_LINE2));
                final ActivityBase activityBase = $r1;
                final View view2 = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        activityBase.startActivityForResult(new Intent(activityBase, CarpoolDriverProfileActivity.class), 0);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view2.setVisibility(8);
                        CarpoolRidesFragment.sUserClosedBannerThisSession = true;
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData profile) throws  {
                if (ConfigManager.getInstance().getConfigSwitchValue(5)) {
                    return ShowResult.DONT_SHOW;
                }
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                this.profileCompleteness = CarpoolDriverProfileActivity.getPercentCompleteProfile();
                return ShowResult.fromBoolean(this.profileCompleteness < 100);
            }
        },
        CAR {
            public int getCounterConfig() throws  {
                return 9;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                $r2.setVisibility(0);
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_cyan);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", "CAR");
                final View view = $r2;
                $r2.findViewById(C1283R.id.allRidesBannerClose).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "CLOSE");
                        ConfigManager.getInstance().setConfigSwitchValue(2, true);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.rw_banner_car_details_illu);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_CAR_LINE1));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_CAR_LINE2));
                final ActivityBase activityBase = $r1;
                final View view2 = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        Intent $r2 = new Intent(activityBase, EditCarActivity.class);
                        $r2.putExtra("MPType", MPType.MissingLocationHistory);
                        activityBase.startActivityForResult($r2, 0);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view2.setVisibility(8);
                        CarpoolRidesFragment.sUserClosedBannerThisSession = true;
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData $r2) throws  {
                if (ConfigManager.getInstance().getConfigSwitchValue(2)) {
                    return ShowResult.DONT_SHOW;
                }
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                boolean $z0 = TextUtils.isEmpty($r2.car_info.photo_url) && TextUtils.isEmpty($r2.car_info.color) && TextUtils.isEmpty($r2.car_info.model) && TextUtils.isEmpty($r2.car_info.make) && TextUtils.isEmpty($r2.car_info.license_plate);
                return ShowResult.fromBoolean($z0);
            }
        },
        WORK {
            public int getCounterConfig() throws  {
                return 10;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                $r2.setVisibility(0);
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_orange);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", "WORK");
                final View view = $r2;
                $r2.findViewById(C1283R.id.allRidesBannerClose).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "CLOSE");
                        ConfigManager.getInstance().setConfigSwitchValue(3, true);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.rw_banner_workplace_illu);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_WORK_LINE1));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_WORK_LINE2));
                final ActivityBase activityBase = $r1;
                final View view2 = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        Intent $r2 = new Intent(activityBase, SettingsCarpoolWorkActivity.class);
                        $r2.putExtra("MPType", MPType.MissingLocationHistory);
                        activityBase.startActivityForResult($r2, 0);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view2.setVisibility(8);
                        CarpoolRidesFragment.sUserClosedBannerThisSession = true;
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData $r2) throws  {
                if (ConfigManager.getInstance().getConfigSwitchValue(3)) {
                    return ShowResult.DONT_SHOW;
                }
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                boolean $z0 = TextUtils.isEmpty($r2.work_email) || !$r2.work_email_verified;
                return ShowResult.fromBoolean($z0);
            }
        },
        BANK {
            public int getCounterConfig() throws  {
                return 11;
            }

            public void show(ActivityBase $r1, View $r2) throws  {
                $r2.setVisibility(0);
                $r2.findViewById(C1283R.id.allRidesBannerBackground).setBackgroundResource(C1283R.drawable.carpool_banner_red);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER, "TYPE", "BANK");
                final View view = $r2;
                $r2.findViewById(C1283R.id.allRidesBannerClose).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "CLOSE");
                        ConfigManager.getInstance().setConfigSwitchValue(4, true);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view.setVisibility(8);
                    }
                });
                ((ImageView) $r2.findViewById(C1283R.id.allRidesBannerImage)).setImageResource(C1283R.drawable.rw_banner_payments_illu);
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine1)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_BANK_LINE1));
                ((TextView) $r2.findViewById(C1283R.id.allRidesBannerLine2)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ALL_RIDES_BANNER_BANK_LINE2));
                final ActivityBase activityBase = $r1;
                final View view2 = $r2;
                $r2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_BANNER_CLICKED, "TYPE", "TAP");
                        activityBase.startActivityForResult(new Intent(activityBase, SettingsCarpoolPaymentsActivity.class), 0);
                        CarpoolRidesFragment.mLastShownBanner = null;
                        view2.setVisibility(8);
                        CarpoolRidesFragment.sUserClosedBannerThisSession = true;
                    }
                });
            }

            public ShowResult shouldShow(CarpoolRidesFragment parent, CarpoolUserData $r2) throws  {
                if (!ConfigValues.getBoolValue(8)) {
                    return ShowResult.DONT_SHOW;
                }
                if (ConfigManager.getInstance().getConfigSwitchValue(4)) {
                    return ShowResult.DONT_SHOW;
                }
                if (!ConfigManager.getInstance().getConfigValueBool(15)) {
                    return ShowResult.DONT_SHOW;
                }
                if (CarpoolOnboardingManager.didBoardThisSession()) {
                    return ShowResult.DONT_SHOW;
                }
                CarpoolPayee $r5 = CarpoolNativeManager.getInstance().getCachedPayeeNTV();
                if ($r2.prompt_payment_action == 0 || ($r5 != null && $r5.payout_account_name != null && !$r5.payout_account_name.isEmpty())) {
                    return ShowResult.DONT_SHOW;
                }
                return ShowResult.SHOW;
            }
        };

        public void resetCheck() throws  {
        }
    }

    static class BannerRideListItem extends RideListAbsItem {
        BannerRideListItem() throws  {
        }
    }

    static class DriveListItem extends RideListAbsItem {
        final String destination;
        CarpoolDrive drive;
        final String origin;
        final long utcTime;

        public DriveListItem(long $l0, String $r1, String $r2) throws  {
            this.utcTime = $l0;
            this.origin = $r1;
            this.destination = $r2;
        }

        public static DriveListItem fromDrive(CarpoolDrive $r0) throws  {
            String $r2 = "";
            CarpoolLocation $r4 = $r0.itinerary.origin;
            if ($r4 != null) {
                $r2 = $r4.placeName;
                if ($r2 == null || $r2.isEmpty()) {
                    $r2 = $r4.address;
                }
            }
            $r4 = $r0.itinerary.destination;
            String $r5 = "";
            if ($r4 != null) {
                $r5 = $r4.placeName;
                if ($r5 == null || $r5.isEmpty()) {
                    $r5 = $r4.address;
                }
            }
            DriveListItem $r1 = new DriveListItem($r0.getTime(), $r2, $r5);
            $r1.drive = $r0;
            return $r1;
        }
    }

    private static class MyHandler extends Handler {
        final WeakReference<CarpoolRidesFragment> ref;

        MyHandler(CarpoolRidesFragment $r1) throws  {
            this.ref = new WeakReference($r1);
        }

        public void handleMessage(Message $r1) throws  {
            CarpoolRidesFragment $r4 = (CarpoolRidesFragment) this.ref.get();
            if ($r4 != null) {
                $r4.handleMessage($r1);
            }
            super.handleMessage($r1);
        }
    }

    class RideListAdapter extends BaseAdapter {
        private static final int ITEM_TYPE_BANNER = 5;
        private static final int ITEM_TYPE_COUNT = 9;
        private static final int ITEM_TYPE_DRILL = 2;
        private static final int ITEM_TYPE_DRIVE = 8;
        private static final int ITEM_TYPE_HEADER = 1;
        private static final int ITEM_TYPE_LIVE_RIDE = 6;
        private static final int ITEM_TYPE_RIDE = 0;
        private static final int ITEM_TYPE_SPACE = 3;
        private static final int ITEM_TYPE_UPCOMING_RIDE = 7;
        private static final int ITEM_TYPE_VIEW = 4;
        private Context _ctx;
        private LayoutInflater _inflater;
        private ArrayList<RideListAbsItem> _itemsList;
        private NativeManager _nm = AppService.getNativeManager();
        private SimpleDateFormat _sdf;
        private DateFormat _tf;

        class C15841 implements OnClickListener {
            final /* synthetic */ int val$position;
            final /* synthetic */ RideListItem val$rli;
            final /* synthetic */ TextView val$tvState;
            final /* synthetic */ View val$vRateButton;

            C15841(RideListItem $r2, int $i0, TextView $r3, View $r4) throws  {
                this.val$rli = $r2;
                this.val$position = $i0;
                this.val$tvState = $r3;
                this.val$vRateButton = $r4;
            }

            public void onClick(View v) throws  {
                RideListAdapter.this.openRating(this.val$rli, this.val$position, this.val$tvState, this.val$vRateButton);
            }
        }

        class C15852 implements OnClickListener {
            final /* synthetic */ int val$position;
            final /* synthetic */ RideListItem val$rli;

            C15852(int $i0, RideListItem $r2) throws  {
                this.val$position = $i0;
                this.val$rli = $r2;
            }

            public void onClick(View v) throws  {
                CarpoolRidesFragment.this.rideClicked(this.val$position, this.val$rli);
            }
        }

        class C15874 implements OnClickListener {
            final /* synthetic */ DriveListItem val$dli;

            C15874(DriveListItem $r2) throws  {
                this.val$dli = $r2;
            }

            public void onClick(View v) throws  {
                CarpoolRidesFragment.this.driveClicked(this.val$dli);
            }
        }

        private android.view.View getDriveView(int r39, android.view.View r40, android.view.ViewGroup r41) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:30:0x01fc in {2, 5, 8, 10, 14, 15, 21, 28, 31} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r38 = this;
            r4 = r40;
            if (r40 != 0) goto L_0x0012;
        L_0x0004:
            r0 = r38;
            r5 = r0._inflater;
            r6 = 2130903115; // 0x7f03004b float:1.7413039E38 double:1.0528060237E-314;
            r7 = 0;
            r0 = r41;
            r4 = r5.inflate(r6, r0, r7);
        L_0x0012:
            r0 = r38;
            r1 = r39;
            r8 = r0.getItem(r1);
            r10 = r8;
            r10 = (com.waze.carpool.CarpoolRidesFragment.DriveListItem) r10;
            r9 = r10;
            r6 = 2131690113; // 0x7f0f0281 float:1.900926E38 double:1.0531948524E-314;
            r40 = r4.findViewById(r6);
            r12 = r40;
            r12 = (android.widget.TextView) r12;
            r11 = r12;
            r6 = 3567; // 0xdef float:4.998E-42 double:1.7623E-320;
            r13 = com.waze.strings.DisplayStrings.displayString(r6);
            r11.setText(r13);
            r14 = java.util.Calendar.getInstance();
            r15 = r14.getTimeZone();
            r16 = com.waze.carpool.CarpoolOfferDriveActivity.getShortDateFormat();
            r0 = r16;
            r0.setTimeZone(r15);
            r0 = r38;
            r0 = com.waze.carpool.CarpoolRidesFragment.this;
            r17 = r0;
            r18 = r0.getActivity();
            r0 = r18;
            r19 = android.text.format.DateFormat.getTimeFormat(r0);
            r0 = r19;
            r0.setTimeZone(r15);
            r0 = r9.utcTime;
            r20 = r0;
            r22 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r20;
            r2 = r22;
            r0 = r0 * r2;
            r20 = r0;
            r0 = r38;
            r0 = com.waze.carpool.CarpoolRidesFragment.this;
            r17 = r0;
            r18 = r0.getActivity();
            r6 = 0;
            r7 = 1;
            r0 = r18;
            r1 = r20;
            r13 = com.waze.utils.DisplayUtils.getDayString(r0, r1, r6, r7);
            r24 = new java.util.Date;
            r0 = r24;
            r1 = r20;
            r0.<init>(r1);
            r0 = r16;
            r1 = r24;
            r25 = r0.format(r1);
            r24 = new java.util.Date;
            r0 = r24;
            r1 = r20;
            r0.<init>(r1);
            r0 = r19;
            r1 = r24;
            r26 = r0.format(r1);
            r6 = 3;
            r0 = new java.lang.Object[r6];
            r27 = r0;
            r6 = 0;
            r27[r6] = r13;
            r6 = 1;
            r27[r6] = r25;
            r6 = 2;
            r27[r6] = r26;
            r6 = 3571; // 0xdf3 float:5.004E-42 double:1.7643E-320;
            r0 = r27;
            r13 = com.waze.strings.DisplayStrings.displayStringF(r6, r0);
            r6 = 2131690114; // 0x7f0f0282 float:1.9009262E38 double:1.053194853E-314;
            r40 = r4.findViewById(r6);
            r28 = r40;
            r28 = (android.widget.TextView) r28;
            r11 = r28;
            r11.setText(r13);
            r6 = 2131690115; // 0x7f0f0283 float:1.9009264E38 double:1.0531948534E-314;
            r40 = r4.findViewById(r6);
            r29 = r40;
            r29 = (android.widget.TextView) r29;
            r11 = r29;
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            r0 = r0.destination;
            r32 = r0;
            r33 = r0.isHome();
            if (r33 == 0) goto L_0x012d;
        L_0x00e1:
            r0 = r38;
            r0 = r0._nm;
            r34 = r0;
            r6 = 3568; // 0xdf0 float:5.0E-42 double:1.763E-320;
            r0 = r34;
            r13 = r0.getLanguageString(r6);
        L_0x00ef:
            r35 = android.text.Html.fromHtml(r13);
            r0 = r35;
            r11.setText(r0);
            r6 = 2131690116; // 0x7f0f0284 float:1.9009267E38 double:1.053194854E-314;
            r40 = r4.findViewById(r6);
            r36 = r40;
            r36 = (android.widget.TextView) r36;
            r11 = r36;
            r0 = r38;
            r0 = r0._nm;
            r34 = r0;
            r33 = r0.RealtimeDebugEnabled();
            if (r33 == 0) goto L_0x0203;
        L_0x0111:
            r6 = 0;
            r11.setVisibility(r6);
            r0 = r9.drive;
            r30 = r0;
            r13 = r0.uuid;
            r11.setText(r13);
        L_0x011e:
            r37 = new com.waze.carpool.CarpoolRidesFragment$RideListAdapter$4;
            r0 = r37;
            r1 = r38;
            r0.<init>(r9);
            r0 = r37;
            r4.setOnClickListener(r0);
            return r4;
        L_0x012d:
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            r0 = r0.destination;
            r32 = r0;
            r33 = r0.isWork();
            if (r33 == 0) goto L_0x0152;
        L_0x013f:
            r0 = r38;
            r0 = r0._nm;
            r34 = r0;
            goto L_0x0149;
        L_0x0146:
            goto L_0x00ef;
        L_0x0149:
            r6 = 3569; // 0xdf1 float:5.001E-42 double:1.7633E-320;
            r0 = r34;
            r13 = r0.getLanguageString(r6);
            goto L_0x00ef;
        L_0x0152:
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            r0 = r0.destination;
            r32 = r0;
            r13 = r0.placeName;
            if (r13 == 0) goto L_0x01a7;
        L_0x0162:
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            r0 = r0.destination;
            r32 = r0;
            r13 = r0.placeName;
            r33 = r13.isEmpty();
            if (r33 != 0) goto L_0x01a7;
        L_0x0176:
            r0 = r38;
            r0 = r0._nm;
            r34 = r0;
            r6 = 3570; // 0xdf2 float:5.003E-42 double:1.764E-320;
            r0 = r34;
            r13 = r0.getLanguageString(r6);
            r6 = 1;
            r0 = new java.lang.Object[r6];
            r27 = r0;
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            goto L_0x0195;
        L_0x0192:
            goto L_0x011e;
        L_0x0195:
            r0 = r0.destination;
            r32 = r0;
            r0 = r0.placeName;
            r25 = r0;
            r6 = 0;
            r27[r6] = r25;
            r0 = r27;
            r13 = java.lang.String.format(r13, r0);
            goto L_0x0146;
        L_0x01a7:
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            r0 = r0.destination;
            r32 = r0;
            r13 = r0.address;
            if (r13 == 0) goto L_0x0200;
        L_0x01b7:
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            r0 = r0.destination;
            r32 = r0;
            r13 = r0.address;
            r33 = r13.isEmpty();
            if (r33 != 0) goto L_0x0200;
        L_0x01cb:
            r0 = r38;
            r0 = r0._nm;
            r34 = r0;
            r6 = 3570; // 0xdf2 float:5.003E-42 double:1.764E-320;
            r0 = r34;
            r13 = r0.getLanguageString(r6);
            r6 = 1;
            r0 = new java.lang.Object[r6];
            r27 = r0;
            r0 = r9.drive;
            r30 = r0;
            r0 = r0.itinerary;
            r31 = r0;
            r0 = r0.destination;
            r32 = r0;
            r0 = r0.address;
            r25 = r0;
            r6 = 0;
            r27[r6] = r25;
            goto L_0x01f5;
        L_0x01f2:
            goto L_0x00ef;
        L_0x01f5:
            r0 = r27;
            r13 = java.lang.String.format(r13, r0);
            goto L_0x01f2;
            goto L_0x0200;
        L_0x01fd:
            goto L_0x00ef;
        L_0x0200:
            r13 = "";
            goto L_0x01fd;
        L_0x0203:
            r6 = 8;
            r11.setVisibility(r6);
            goto L_0x0192;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRidesFragment.RideListAdapter.getDriveView(int, android.view.View, android.view.ViewGroup):android.view.View");
        }

        private android.view.View getRideView(int r76, android.view.View r77, android.view.ViewGroup r78) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:43:0x0280
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r75 = this;
            r6 = r77;
            if (r77 != 0) goto L_0x0012;
        L_0x0004:
            r0 = r75;
            r7 = r0._inflater;
            r8 = 2130903130; // 0x7f03005a float:1.741307E38 double:1.052806031E-314;
            r9 = 0;
            r0 = r78;
            r6 = r7.inflate(r8, r0, r9);
        L_0x0012:
            r0 = r75;
            r1 = r76;
            r10 = r0.getItem(r1);
            r12 = r10;
            r12 = (com.waze.carpool.CarpoolRidesFragment.RideListItem) r12;
            r11 = r12;
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r14 = r13.mNowViewing;
            r8 = 2;
            if (r14 != r8) goto L_0x0273;
        L_0x0029:
            r15 = com.waze.carpool.CarpoolNativeManager.getInstance();
            r16 = r15.configGetRideListTimeFormatHistoryNTV();
        L_0x0031:
            r17 = new java.text.SimpleDateFormat;
            r0 = r17;
            r1 = r16;
            r0.<init>(r1);
            r0 = r11.drive;
            r18 = r0;
            r19 = r0.hasTimeZone();
            if (r19 == 0) goto L_0x0059;
        L_0x0044:
            r0 = r11.drive;
            r18 = r0;
            r16 = r0.timeZone();
            r0 = r16;
            r20 = java.util.TimeZone.getTimeZone(r0);
            r0 = r17;
            r1 = r20;
            r0.setTimeZone(r1);
        L_0x0059:
            r21 = new java.util.Date;
            r0 = r11.utcTime;
            r22 = r0;
            r24 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r22;
            r2 = r24;
            r0 = r0 * r2;
            r22 = r0;
            r0 = r21;
            r1 = r22;
            r0.<init>(r1);
            r0 = r17;
            r1 = r21;
            r16 = r0.format(r1);
            r8 = 2131690179; // 0x7f0f02c3 float:1.9009394E38 double:1.053194885E-314;
            r77 = r6.findViewById(r8);
            r27 = r77;
            r27 = (android.widget.TextView) r27;
            r26 = r27;
            r8 = 2131690180; // 0x7f0f02c4 float:1.9009396E38 double:1.0531948855E-314;
            r77 = r6.findViewById(r8);
            r29 = r77;
            r29 = (android.widget.TextView) r29;
            r28 = r29;
            r8 = 2131690181; // 0x7f0f02c5 float:1.9009398E38 double:1.053194886E-314;
            r77 = r6.findViewById(r8);
            r31 = r77;
            r31 = (android.widget.TextView) r31;
            r30 = r31;
            r8 = 0;
            r0 = r26;
            r0.setVisibility(r8);
            r8 = 0;
            r0 = r28;
            r0.setVisibility(r8);
            r0 = r11.drive;
            r18 = r0;
            r19 = com.waze.carpool.CarpoolRidesFragment.isToday(r0);
            if (r19 != 0) goto L_0x00be;
        L_0x00b4:
            r0 = r11.drive;
            r18 = r0;
            r19 = com.waze.carpool.CarpoolRidesFragment.isYesterday(r0);
            if (r19 == 0) goto L_0x0284;
        L_0x00be:
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r14 = r13.mNowViewing;
            r8 = 2;
            if (r14 == r8) goto L_0x0284;
        L_0x00c9:
            r8 = 8;
            r0 = r28;
            r0.setVisibility(r8);
        L_0x00d0:
            r0 = r11.drive;
            r18 = r0;
            r32 = r0.getRide();
            if (r32 == 0) goto L_0x02f3;
        L_0x00da:
            r0 = r32;
            r14 = r0.state;
            r8 = 1;
            if (r14 == r8) goto L_0x00e9;
        L_0x00e1:
            r0 = r32;
            r14 = r0.state;
            r8 = 13;
            if (r14 != r8) goto L_0x02f3;
        L_0x00e9:
            r33 = new java.util.Date;
            r0 = r32;
            r0 = r0.itinerary;
            r34 = r0;
            r0 = r0.window_start_time;
            r22 = r0;
            r24 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r22;
            r2 = r24;
            r0 = r0 * r2;
            r22 = r0;
            r0 = r11.drive;
            r18 = r0;
            r0 = r0.drive_match_info;
            r35 = r0;
            r14 = r0.origin_to_pickup_duration_seconds;
            r0 = (long) r14;
            r36 = r0;
            r0 = r22;
            r2 = r36;
            r0 = r0 + r2;
            r22 = r0;
            r0 = r33;
            r1 = r22;
            r0.<init>(r1);
            r0 = r75;
            r0 = r0._tf;
            r38 = r0;
            r1 = r33;
            r16 = r0.format(r1);
            r0 = r32;
            r0 = r0.itinerary;
            r34 = r0;
            r14 = r0.window_duration_seconds;
            r8 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
            if (r14 >= r8) goto L_0x028c;
        L_0x0131:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2211; // 0x8a3 float:3.098E-42 double:1.0924E-320;
            r0 = r39;
            r40 = r0.getLanguageString(r8);
            r8 = 1;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r8 = 0;
            r41[r8] = r16;
            r0 = r40;
            r1 = r41;
            r16 = java.lang.String.format(r0, r1);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
        L_0x0156:
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r14 = r13.mNowViewing;
            r8 = 2;
            if (r14 != r8) goto L_0x030b;
        L_0x0161:
            r8 = 8;
            r0 = r26;
            r0.setVisibility(r8);
        L_0x0168:
            r8 = 2131690186; // 0x7f0f02ca float:1.9009409E38 double:1.0531948885E-314;
            r77 = r6.findViewById(r8);
            r42 = r77;
            r42 = (android.widget.TextView) r42;
            r26 = r42;
            r8 = 0;
            r0 = r26;
            r0.setVisibility(r8);
            r8 = 2131690187; // 0x7f0f02cb float:1.900941E38 double:1.053194889E-314;
            r77 = r6.findViewById(r8);
            r43 = r77;
            r43 = (android.widget.TextView) r43;
            r28 = r43;
            r8 = 0;
            r0 = r28;
            r0.setVisibility(r8);
            r44 = new java.lang.StringBuilder;
            r0 = r44;
            r0.<init>();
            r45 = "<b>";
            r0 = r44;
            r1 = r45;
            r44 = r0.append(r1);
            r0 = r11.name;
            r16 = r0;
            r0 = r44;
            r1 = r16;
            r44 = r0.append(r1);
            r45 = "</b>";
            r0 = r44;
            r1 = r45;
            r44 = r0.append(r1);
            r0 = r44;
            r16 = r0.toString();
            r0 = r11.drive;
            r18 = r0;
            r0 = r11.ride;
            r46 = r0;
            r0 = r18;
            r1 = r46;
            r14 = r0.getState(r1);
            r8 = 2;
            if (r14 != r8) goto L_0x032f;
        L_0x01ce:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2208; // 0x8a0 float:3.094E-42 double:1.091E-320;
            r0 = r39;
            r40 = r0.getLanguageString(r8);
            r8 = 1;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r8 = 0;
            r41[r8] = r16;
            r0 = r40;
            r1 = r41;
            r16 = java.lang.String.format(r0, r1);
            r0 = r16;
            r47 = android.text.Html.fromHtml(r0);
            r0 = r26;
            r1 = r47;
            r0.setText(r1);
            r45 = "";
            r0 = r28;
            r1 = r45;
            r0.setText(r1);
        L_0x0202:
            r0 = r11.drive;
            r18 = r0;
            r48 = r0.getDestination();
            r0 = r48;
            r19 = r0.isHome();
            if (r19 == 0) goto L_0x04d8;
        L_0x0212:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2212; // 0x8a4 float:3.1E-42 double:1.093E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
        L_0x0220:
            r0 = r16;
            r47 = android.text.Html.fromHtml(r0);
            r0 = r28;
            r1 = r47;
            r0.setText(r1);
            r8 = 2131690184; // 0x7f0f02c8 float:1.9009404E38 double:1.0531948875E-314;
            r77 = r6.findViewById(r8);
            r50 = r77;
            r50 = (com.waze.view.button.RidersImages) r50;
            r49 = r50;
            r0 = r49;
            r0.clearImages();
            r0 = r11.drive;
            r18 = r0;
            r51 = r0.getRidesAmount();
            if (r51 <= 0) goto L_0x0537;
        L_0x0249:
            r51 = 0;
        L_0x024b:
            r0 = r11.drive;
            r18 = r0;
            r52 = r0.getRidesAmount();
            r0 = r51;
            r1 = r52;
            if (r0 >= r1) goto L_0x0552;
        L_0x0259:
            r0 = r11.drive;
            r18 = r0;
            r1 = r51;
            r53 = r0.getRider(r1);
            r0 = r53;
            r16 = r0.getImage();
            r0 = r49;
            r1 = r16;
            r0.addImage(r1);
            r51 = r51 + 1;
            goto L_0x024b;
        L_0x0273:
            r15 = com.waze.carpool.CarpoolNativeManager.getInstance();
            goto L_0x027b;
        L_0x0278:
            goto L_0x0031;
        L_0x027b:
            r16 = r15.configGetRideListTimeFormatNTV();
            goto L_0x0278;
            goto L_0x0284;
        L_0x0281:
            goto L_0x00d0;
        L_0x0284:
            r0 = r28;
            r1 = r16;
            r0.setText(r1);
            goto L_0x0281;
        L_0x028c:
            r33 = new java.util.Date;
            r0 = r32;
            r0 = r0.itinerary;
            r34 = r0;
            r0 = r0.window_start_time;
            r22 = r0;
            r0 = r32;
            r0 = r0.itinerary;
            r34 = r0;
            r14 = r0.window_duration_seconds;
            r0 = (long) r14;
            r36 = r0;
            r0 = r22;
            r2 = r36;
            r0 = r0 + r2;
            r22 = r0;
            r24 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r22;
            r2 = r24;
            r0 = r0 * r2;
            r22 = r0;
            r0 = r33;
            r1 = r22;
            r0.<init>(r1);
            r0 = r75;
            r0 = r0._tf;
            r38 = r0;
            r1 = r33;
            r40 = r0.format(r1);
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2210; // 0x8a2 float:3.097E-42 double:1.092E-320;
            r0 = r39;
            r54 = r0.getLanguageString(r8);
            r8 = 2;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r8 = 0;
            r41[r8] = r16;
            r8 = 1;
            r41[r8] = r40;
            r0 = r54;
            r1 = r41;
            r16 = java.lang.String.format(r0, r1);
            goto L_0x02eb;
        L_0x02e8:
            goto L_0x0156;
        L_0x02eb:
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            goto L_0x02e8;
        L_0x02f3:
            r0 = r75;
            r0 = r0._tf;
            r38 = r0;
            r1 = r21;
            r16 = r0.format(r1);
            goto L_0x0303;
        L_0x0300:
            goto L_0x0156;
        L_0x0303:
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            goto L_0x0300;
        L_0x030b:
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r55 = r13.getActivity();
            r0 = r21;
            r22 = r0.getTime();
            r8 = 0;
            r9 = 1;
            r0 = r55;
            r1 = r22;
            r16 = com.waze.utils.DisplayUtils.getDayString(r0, r1, r8, r9);
            goto L_0x0327;
        L_0x0324:
            goto L_0x0168;
        L_0x0327:
            r0 = r26;
            r1 = r16;
            r0.setText(r1);
            goto L_0x0324;
        L_0x032f:
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r51 = r13.mNowViewing;
            r8 = 2;
            r0 = r51;
            if (r0 == r8) goto L_0x0343;
        L_0x033c:
            r8 = 4;
            if (r14 == r8) goto L_0x0343;
        L_0x033f:
            r8 = 9;
            if (r14 != r8) goto L_0x045e;
        L_0x0343:
            r19 = 1;
        L_0x0345:
            r0 = r11.drive;
            r18 = r0;
            r56 = r0.isMultiPax();
            if (r56 == 0) goto L_0x04a3;
        L_0x034f:
            r0 = r11.drive;
            r18 = r0;
            r51 = r0.getRidesAmount();
            r8 = 2;
            r0 = r51;
            if (r0 != r8) goto L_0x0476;
        L_0x035c:
            r0 = r11.drive;
            r18 = r0;
            r8 = 0;
            r0 = r18;
            r53 = r0.getRider(r8);
            if (r53 == 0) goto L_0x0461;
        L_0x0369:
            r0 = r53;
            r16 = r0.getFirstName();
            if (r16 == 0) goto L_0x0461;
        L_0x0371:
            r0 = r53;
            r16 = r0.getFirstName();
        L_0x0377:
            r0 = r11.drive;
            r18 = r0;
            r8 = 1;
            r0 = r18;
            r53 = r0.getRider(r8);
            if (r53 == 0) goto L_0x0468;
        L_0x0384:
            r0 = r53;
            r40 = r0.getFirstName();
            if (r40 == 0) goto L_0x0468;
        L_0x038c:
            r0 = r53;
            r40 = r0.getFirstName();
        L_0x0392:
            if (r19 == 0) goto L_0x0473;
        L_0x0394:
            r57 = 2206; // 0x89e float:3.091E-42 double:1.09E-320;
        L_0x0396:
            r8 = 2;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r44 = new java.lang.StringBuilder;
            r0 = r44;
            r0.<init>();
            r45 = "<b>";
            r0 = r44;
            r1 = r45;
            r44 = r0.append(r1);
            r0 = r44;
            r1 = r16;
            r44 = r0.append(r1);
            r45 = "</b>";
            r0 = r44;
            r1 = r45;
            r44 = r0.append(r1);
            r0 = r44;
            r16 = r0.toString();
            r8 = 0;
            r41[r8] = r16;
            r44 = new java.lang.StringBuilder;
            r0 = r44;
            r0.<init>();
            r45 = "<b>";
            r0 = r44;
            r1 = r45;
            r44 = r0.append(r1);
            r0 = r44;
            r1 = r40;
            r44 = r0.append(r1);
            goto L_0x03e4;
        L_0x03e1:
            goto L_0x0377;
        L_0x03e4:
            r45 = "</b>";
            r0 = r44;
            r1 = r45;
            r44 = r0.append(r1);
            r0 = r44;
            r16 = r0.toString();
            r8 = 1;
            r41[r8] = r16;
            goto L_0x03ff;
        L_0x03f8:
            goto L_0x0392;
            goto L_0x03ff;
        L_0x03fc:
            goto L_0x0396;
        L_0x03ff:
            r0 = r57;
            r1 = r41;
            r16 = com.waze.strings.DisplayStrings.displayStringF(r0, r1);
            r0 = r16;
            r47 = android.text.Html.fromHtml(r0);
            r0 = r26;
            r1 = r47;
            r0.setText(r1);
        L_0x0414:
            if (r19 == 0) goto L_0x04c8;
        L_0x0416:
            r0 = r11.drive;
            r18 = r0;
            r0 = r75;
            r0 = r0._ctx;
            r58 = r0;
            goto L_0x0424;
        L_0x0421:
            goto L_0x03e1;
        L_0x0424:
            r0 = r18;
            r1 = r58;
            r16 = r0.getRewardString(r1);
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2209; // 0x8a1 float:3.095E-42 double:1.0914E-320;
            r0 = r39;
            r40 = r0.getLanguageString(r8);
            r8 = 1;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r8 = 0;
            r41[r8] = r16;
            r0 = r40;
            r1 = r41;
            r16 = java.lang.String.format(r0, r1);
            goto L_0x044e;
        L_0x044b:
            goto L_0x0202;
        L_0x044e:
            r0 = r28;
            r1 = r16;
            r0.setText(r1);
            goto L_0x044b;
            goto L_0x045e;
        L_0x0457:
            goto L_0x0345;
            goto L_0x045e;
        L_0x045b:
            goto L_0x0414;
        L_0x045e:
            r19 = 0;
            goto L_0x0457;
        L_0x0461:
            r8 = 902; // 0x386 float:1.264E-42 double:4.456E-321;
            r16 = com.waze.strings.DisplayStrings.displayString(r8);
            goto L_0x0421;
        L_0x0468:
            r8 = 902; // 0x386 float:1.264E-42 double:4.456E-321;
            r40 = com.waze.strings.DisplayStrings.displayString(r8);
            goto L_0x0472;
        L_0x046f:
            goto L_0x0414;
        L_0x0472:
            goto L_0x03f8;
        L_0x0473:
            r57 = 3511; // 0xdb7 float:4.92E-42 double:1.7347E-320;
            goto L_0x03fc;
        L_0x0476:
            if (r19 == 0) goto L_0x04a0;
        L_0x0478:
            r57 = 2207; // 0x89f float:3.093E-42 double:1.0904E-320;
        L_0x047a:
            r8 = 1;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r0 = r11.drive;
            r18 = r0;
            r51 = r0.getRidesAmount();
            r0 = r51;
            r59 = java.lang.Integer.valueOf(r0);
            r8 = 0;
            r41[r8] = r59;
            r0 = r57;
            r1 = r41;
            r16 = com.waze.strings.DisplayStrings.displayStringF(r0, r1);
            r0 = r26;
            r1 = r16;
            r0.setText(r1);
            goto L_0x045b;
        L_0x04a0:
            r57 = 3512; // 0xdb8 float:4.921E-42 double:1.735E-320;
            goto L_0x047a;
        L_0x04a3:
            if (r19 == 0) goto L_0x04c5;
        L_0x04a5:
            r57 = 2205; // 0x89d float:3.09E-42 double:1.0894E-320;
        L_0x04a7:
            r8 = 1;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r8 = 0;
            r41[r8] = r16;
            r0 = r57;
            r1 = r41;
            r16 = com.waze.strings.DisplayStrings.displayStringF(r0, r1);
            r0 = r16;
            r47 = android.text.Html.fromHtml(r0);
            r0 = r26;
            r1 = r47;
            r0.setText(r1);
            goto L_0x046f;
        L_0x04c5:
            r57 = 3510; // 0xdb6 float:4.919E-42 double:1.734E-320;
            goto L_0x04a7;
        L_0x04c8:
            r0 = r11.pickup;
            r16 = r0;
            goto L_0x04d0;
        L_0x04cd:
            goto L_0x0202;
        L_0x04d0:
            r0 = r28;
            r1 = r16;
            r0.setText(r1);
            goto L_0x04cd;
        L_0x04d8:
            r0 = r48;
            r19 = r0.isWork();
            if (r19 == 0) goto L_0x04f3;
        L_0x04e0:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            goto L_0x04ea;
        L_0x04e7:
            goto L_0x0220;
        L_0x04ea:
            r8 = 2213; // 0x8a5 float:3.101E-42 double:1.0934E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            goto L_0x04e7;
        L_0x04f3:
            r0 = r48;
            r0 = r0.address;
            r16 = r0;
            if (r16 == 0) goto L_0x050b;
        L_0x04fb:
            r0 = r48;
            r0 = r0.address;
            r16 = r0;
            r19 = r0.isEmpty();
            if (r19 == 0) goto L_0x050e;
        L_0x0507:
            goto L_0x050b;
        L_0x0508:
            goto L_0x0220;
        L_0x050b:
            r16 = "";
            goto L_0x0508;
        L_0x050e:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2214; // 0x8a6 float:3.102E-42 double:1.094E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            r8 = 1;
            r0 = new java.lang.Object[r8];
            r41 = r0;
            r0 = r48;
            r0 = r0.address;
            r40 = r0;
            r8 = 0;
            r41[r8] = r40;
            goto L_0x052e;
        L_0x052b:
            goto L_0x0220;
        L_0x052e:
            r0 = r16;
            r1 = r41;
            r16 = java.lang.String.format(r0, r1);
            goto L_0x052b;
        L_0x0537:
            r0 = r11.ride;
            r46 = r0;
            if (r46 == 0) goto L_0x0552;
        L_0x053d:
            r0 = r11.ride;
            r46 = r0;
            r53 = r0.getRider();
            r0 = r53;
            r16 = r0.getImage();
            r0 = r49;
            r1 = r16;
            r0.addImage(r1);
        L_0x0552:
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r15 = r13.mCpnm;
            r0 = r11.drive;
            r18 = r0;
            r51 = r15.getUnreadChatMessageCount(r0);
            r8 = 2131690190; // 0x7f0f02ce float:1.9009417E38 double:1.0531948905E-314;
            r77 = r6.findViewById(r8);
            r60 = r77;
            r60 = (android.widget.TextView) r60;
            r30 = r60;
            if (r51 <= 0) goto L_0x0693;
        L_0x056f:
            r8 = 0;
            r0 = r30;
            r0.setVisibility(r8);
            r0 = r51;
            r16 = java.lang.Integer.toString(r0);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
        L_0x0582:
            r0 = r11.drive;
            r18 = r0;
            r0 = r75;
            r1 = r30;
            r2 = r51;
            r3 = r18;
            r0.updateChatCounterWithDriveUpdates(r1, r2, r3);
            r8 = 2131690177; // 0x7f0f02c1 float:1.900939E38 double:1.053194884E-314;
            r77 = r6.findViewById(r8);
            r61 = r77;
            r61 = (android.widget.TextView) r61;
            r30 = r61;
            r8 = 2131690189; // 0x7f0f02cd float:1.9009415E38 double:1.05319489E-314;
            r77 = r6.findViewById(r8);
            r63 = r77;
            r63 = (android.widget.TextView) r63;
            r62 = r63;
            r8 = 2131690188; // 0x7f0f02cc float:1.9009413E38 double:1.0531948895E-314;
            r77 = r6.findViewById(r8);
            r8 = 8;
            r0 = r77;
            r0.setVisibility(r8);
            r8 = 2131690182; // 0x7f0f02c6 float:1.90094E38 double:1.0531948865E-314;
            r64 = r6.findViewById(r8);
            r66 = r64;
            r66 = (android.widget.TextView) r66;
            r65 = r66;
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r19 = r0.RealtimeDebugEnabled();
            if (r19 == 0) goto L_0x069b;
        L_0x05d2:
            r8 = 0;
            r0 = r65;
            r0.setVisibility(r8);
            r0 = r11.drive;
            r18 = r0;
            r0 = r0.uuid;
            r16 = r0;
            r0 = r65;
            r1 = r16;
            r0.setText(r1);
        L_0x05e7:
            r8 = 2131690173; // 0x7f0f02bd float:1.9009382E38 double:1.053194882E-314;
            r64 = r6.findViewById(r8);
            r8 = 2130838939; // 0x7f02059b float:1.7282874E38 double:1.0527743166E-314;
            r0 = r64;
            r0.setBackgroundResource(r8);
            r8 = 10;
            r51 = com.waze.utils.PixelMeasure.dp(r8);
            goto L_0x0600;
        L_0x05fd:
            goto L_0x0582;
        L_0x0600:
            r8 = 0;
            r9 = 0;
            r67 = 0;
            r0 = r64;
            r1 = r51;
            r2 = r67;
            r0.setPadding(r1, r8, r9, r2);
            r8 = 2131690176; // 0x7f0f02c0 float:1.9009388E38 double:1.0531948835E-314;
            r68 = r6.findViewById(r8);
            r8 = 8;
            r0 = r68;
            r0.setVisibility(r8);
            switch(r14) {
                case 1: goto L_0x07d1;
                case 2: goto L_0x06a3;
                case 3: goto L_0x06a3;
                case 4: goto L_0x0711;
                case 5: goto L_0x06a3;
                case 6: goto L_0x06a3;
                case 7: goto L_0x06d5;
                case 8: goto L_0x06d5;
                case 9: goto L_0x0711;
                case 10: goto L_0x06d5;
                case 11: goto L_0x061f;
                case 12: goto L_0x061f;
                case 13: goto L_0x07d1;
                case 14: goto L_0x061f;
                case 15: goto L_0x079f;
                case 16: goto L_0x06d5;
                default: goto L_0x061e;
            };
        L_0x061e:
            goto L_0x061f;
        L_0x061f:
            r44 = new java.lang.StringBuilder;
            r0 = r44;
            r0.<init>();
            r45 = "CarpoolRidesFragment: Unknown state = ";
            r0 = r44;
            r1 = r45;
            r44 = r0.append(r1);
            r0 = r44;
            r44 = r0.append(r14);
            r0 = r44;
            r16 = r0.toString();
            r0 = r16;
            com.waze.Logger.m38e(r0);
            goto L_0x0645;
        L_0x0642:
            goto L_0x05e7;
        L_0x0645:
            r8 = 8;
            r6.setVisibility(r8);
            goto L_0x064e;
        L_0x064b:
            goto L_0x05fd;
        L_0x064e:
            r8 = 15;
            if (r14 == r8) goto L_0x0674;
        L_0x0652:
            r8 = 2;
            if (r14 != r8) goto L_0x0843;
        L_0x0655:
            r0 = r11.drive;
            r18 = r0;
            r22 = r0.getTime();
            r69 = java.util.Calendar.getInstance();
            r0 = r69;
            r36 = r0.getTimeInMillis();
            r24 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r36;
            r2 = r24;
            r0 = r0 / r2;
            r36 = r0;
            r70 = (r22 > r36 ? 1 : (r22 == r36 ? 0 : -1));
            if (r70 >= 0) goto L_0x0843;
        L_0x0674:
            r71 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
            r0 = r75;
            r1 = r71;
            r0.setTextFieldsAlpha(r6, r1);
        L_0x067e:
            r72 = new com.waze.carpool.CarpoolRidesFragment$RideListAdapter$2;
            r0 = r72;
            r1 = r75;
            r2 = r76;
            r0.<init>(r2, r11);
            r0 = r72;
            r6.setOnClickListener(r0);
            return r6;
            goto L_0x0693;
        L_0x0690:
            goto L_0x064e;
        L_0x0693:
            r8 = 8;
            r0 = r30;
            r0.setVisibility(r8);
            goto L_0x064b;
        L_0x069b:
            r8 = 8;
            r0 = r65;
            r0.setVisibility(r8);
            goto L_0x0642;
        L_0x06a3:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            goto L_0x06ad;
        L_0x06aa:
            goto L_0x064e;
        L_0x06ad:
            r8 = 2201; // 0x899 float:3.084E-42 double:1.0874E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r73 = r13.getResources();
            r8 = 2131623969; // 0x7f0e0021 float:1.8875104E38 double:1.053162173E-314;
            r0 = r73;
            r51 = r0.getColor(r8);
            r0 = r30;
            r1 = r51;
            r0.setTextColor(r1);
            goto L_0x0690;
        L_0x06d5:
            r8 = 2130838940; // 0x7f02059c float:1.7282876E38 double:1.052774317E-314;
            r0 = r64;
            r0.setBackgroundResource(r8);
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2203; // 0x89b float:3.087E-42 double:1.0884E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r73 = r13.getResources();
            r8 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
            r0 = r73;
            r51 = r0.getColor(r8);
            r0 = r30;
            r1 = r51;
            r0.setTextColor(r1);
            r8 = 0;
            r0 = r68;
            r0.setVisibility(r8);
            goto L_0x06aa;
        L_0x0711:
            r0 = r11.drive;
            r18 = r0;
            r53 = r0.getRider();
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2202; // 0x89a float:3.086E-42 double:1.088E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r73 = r13.getResources();
            r8 = 2131623979; // 0x7f0e002b float:1.8875125E38 double:1.053162178E-314;
            r0 = r73;
            r51 = r0.getColor(r8);
            r0 = r30;
            r1 = r51;
            r0.setTextColor(r1);
            if (r32 == 0) goto L_0x064e;
        L_0x0748:
            r0 = r32;
            r0 = r0.driver_reviewed;
            r19 = r0;
            if (r19 != 0) goto L_0x064e;
        L_0x0750:
            if (r53 == 0) goto L_0x064e;
        L_0x0752:
            r0 = r11.drive;
            r18 = r0;
            r19 = r0.hasId();
            if (r19 == 0) goto L_0x064e;
        L_0x075c:
            r8 = 0;
            r0 = r77;
            r0.setVisibility(r8);
            r8 = 8;
            r0 = r26;
            r0.setVisibility(r8);
            r8 = 8;
            r0 = r28;
            r0.setVisibility(r8);
            r8 = 2216; // 0x8a8 float:3.105E-42 double:1.095E-320;
            r16 = com.waze.strings.DisplayStrings.displayString(r8);
            r0 = r62;
            r1 = r16;
            r0.setText(r1);
            r74 = new com.waze.carpool.CarpoolRidesFragment$RideListAdapter$1;
            r0 = r74;
            r1 = r75;
            r2 = r11;
            r3 = r76;
            r4 = r30;
            r5 = r77;
            r0.<init>(r2, r3, r4, r5);
            r0 = r77;
            r1 = r74;
            r0.setOnClickListener(r1);
            goto L_0x0798;
        L_0x0795:
            goto L_0x064e;
        L_0x0798:
            r8 = 0;
            r0 = r77;
            r0.setFocusable(r8);
            goto L_0x0795;
        L_0x079f:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2004; // 0x7d4 float:2.808E-42 double:9.9E-321;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r73 = r13.getResources();
            r8 = 2131623969; // 0x7f0e0021 float:1.8875104E38 double:1.053162173E-314;
            r0 = r73;
            r51 = r0.getColor(r8);
            goto L_0x07c9;
        L_0x07c6:
            goto L_0x064e;
        L_0x07c9:
            r0 = r30;
            r1 = r51;
            r0.setTextColor(r1);
            goto L_0x07c6;
        L_0x07d1:
            r0 = r11.drive;
            r18 = r0;
            r19 = r0.hasParentDrive();
            if (r19 == 0) goto L_0x080d;
        L_0x07db:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2200; // 0x898 float:3.083E-42 double:1.087E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r73 = r13.getResources();
            r8 = 2131623940; // 0x7f0e0004 float:1.8875046E38 double:1.0531621586E-314;
            r0 = r73;
            r51 = r0.getColor(r8);
            goto L_0x0805;
        L_0x0802:
            goto L_0x064e;
        L_0x0805:
            r0 = r30;
            r1 = r51;
            r0.setTextColor(r1);
            goto L_0x0802;
        L_0x080d:
            r0 = r75;
            r0 = r0._nm;
            r39 = r0;
            r8 = 2199; // 0x897 float:3.081E-42 double:1.0865E-320;
            r0 = r39;
            r16 = r0.getLanguageString(r8);
            r0 = r30;
            r1 = r16;
            r0.setText(r1);
            r0 = r75;
            r13 = com.waze.carpool.CarpoolRidesFragment.this;
            r73 = r13.getResources();
            r8 = 2131623944; // 0x7f0e0008 float:1.8875054E38 double:1.0531621606E-314;
            r0 = r73;
            r51 = r0.getColor(r8);
            goto L_0x0837;
        L_0x0834:
            goto L_0x064e;
        L_0x0837:
            r0 = r30;
            r1 = r51;
            r0.setTextColor(r1);
            goto L_0x0834;
            goto L_0x0843;
        L_0x0840:
            goto L_0x067e;
        L_0x0843:
            r71 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r0 = r75;
            r1 = r71;
            r0.setTextFieldsAlpha(r6, r1);
            goto L_0x0840;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRidesFragment.RideListAdapter.getRideView(int, android.view.View, android.view.ViewGroup):android.view.View");
        }

        public int getViewTypeCount() throws  {
            return 9;
        }

        public RideListAdapter(@Signature({"(", "Landroid/content/Context;", "Ljava/util/ArrayList", "<", "Lcom/waze/carpool/CarpoolRidesFragment$RideListAbsItem;", ">;)V"}) Context $r2, @Signature({"(", "Landroid/content/Context;", "Ljava/util/ArrayList", "<", "Lcom/waze/carpool/CarpoolRidesFragment$RideListAbsItem;", ">;)V"}) ArrayList<RideListAbsItem> $r3) throws  {
            this._ctx = $r2;
            this._inflater = (LayoutInflater) this._ctx.getSystemService("layout_inflater");
            this._itemsList = $r3;
            TimeZone $r8 = Calendar.getInstance().getTimeZone();
            this._sdf = new SimpleDateFormat(CarpoolNativeManager.getInstance().configGetRideListTimeFormatNTV());
            this._sdf.setTimeZone($r8);
            this._tf = android.text.format.DateFormat.getTimeFormat(this._ctx);
            this._tf.setTimeZone($r8);
        }

        public void setRideList(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/carpool/CarpoolRidesFragment$RideListAbsItem;", ">;)V"}) ArrayList<RideListAbsItem> $r1) throws  {
            this._itemsList = $r1;
            notifyDataSetChanged();
        }

        public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
            int $i1 = getItemViewType($i0);
            if ($i1 == 5) {
                return getBannerView($r2);
            }
            if ($i1 == 0) {
                return getRideView($i0, $r1, $r2);
            }
            if ($i1 == 7) {
                return getUpcomingRideView($i0, $r1, $r2);
            }
            if ($i1 == 6) {
                return getLiveRideView($i0, $r1, $r2);
            }
            if ($i1 == 8) {
                return getDriveView($i0, $r1, $r2);
            }
            if ($i1 == 1) {
                return getHeaderView($i0, $r1, $r2);
            }
            if ($i1 == 2) {
                return getDrillView($i0, $r1, $r2);
            }
            if ($i1 == 3) {
                return getSpaceView($i0, $r1, $r2);
            }
            return $i1 == 4 ? ((RideListCustom) getItem($i0)).inflate(this._inflater, $r2) : null;
        }

        private void updateChatCounterWithDriveUpdates(final TextView $r1, final int $i0, CarpoolDrive $r2) throws  {
            CarpoolRidesFragment.this.mCpnm.getDriveUpdates($r2, false, new IResultObj<DriveUpdates>() {
                public void onResult(DriveUpdates $r1) throws  {
                    if ($r1 != null && $r1.ridesJoined != null && $r1.ridesJoined.length > 0) {
                        $r1.setVisibility(0);
                        $r1.setText(Integer.toString($i0 + 1));
                    }
                }
            });
        }

        private View getUpcomingRideView(int $i0, View $r1, ViewGroup $r2) throws  {
            $r1 = getRideView($i0, $r1, $r2);
            ((TextView) $r1.findViewById(C1283R.id.rideItemType)).setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_REQ_BANNER_UPCOMING).toUpperCase());
            return $r1;
        }

        private void setTextFieldsAlpha(View $r1, float $f0) throws  {
            for (int $i0 : new int[]{C1283R.id.rideItemType, C1283R.id.rideItemDayTitle, C1283R.id.rideItemDate, C1283R.id.rideItemTime, C1283R.id.rideItemName, C1283R.id.rideItemWhere}) {
                $r1.findViewById($i0).setAlpha($f0);
            }
        }

        private void openRating(RideListItem $r1, int $i0, TextView tvState, View vRateFrame) throws  {
            if ($r1.drive != null) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_RATE_RIDERS_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CARD_NUM, (long) $i0).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r1.drive.getId()).send();
                Intent $r4 = new Intent(CarpoolRidesFragment.this.getActivity(), CarpoolRideDetailsActivity.class);
                $r4.putExtra(CarpoolDrive.class.getSimpleName(), $r1.drive);
                CarpoolRidesFragment.this.startActivityForResult($r4, PublicMacros.CARPOOL_RIDE_DETAILS_REQUEST_CODE);
            }
        }

        private View getLiveRideView(int $i0, View $r1, ViewGroup $r2) throws  {
            $r1 = getRideView($i0, $r1, $r2);
            View $r4 = $r1.findViewById(C1283R.id.rideItemTopHalf);
            TextView $r6 = (TextView) $r1.findViewById(C1283R.id.rideItemType);
            $r4.setBackgroundResource(C1283R.drawable.ridecard_confirmed_base_top);
            $r4.setPadding(PixelMeasure.dp(10), 0, 0, 0);
            $r6.setText(this._nm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_BANNER_LIVE).toUpperCase());
            CarpoolRidesFragment $r9 = CarpoolRidesFragment.this;
            $r6.setTextColor($r9.getResources().getColor(C1283R.color.White));
            $r1.findViewById(C1283R.id.rideItemTypeV).setVisibility(8);
            $r4 = $r1.findViewById(C1283R.id.rideRequestBannerLiveRoller);
            $r4.setVisibility(0);
            $r9 = CarpoolRidesFragment.this;
            Animation translateAnimation = new TranslateAnimation(0, 0.0f, 0, (float) $r9.getResources().getDrawable(C1283R.drawable.sticky_notification_live_carpool_ride_background).getIntrinsicWidth(), 0, 0.0f, 0, 0.0f);
            translateAnimation.setDuration(2000);
            translateAnimation.setInterpolator(new LinearInterpolator());
            translateAnimation.setRepeatMode(1);
            translateAnimation.setRepeatCount(-1);
            $r4.startAnimation(translateAnimation);
            return $r1;
        }

        private void setDriveButton(View $r1, RideListItem $r2, boolean $z0, boolean $z1) throws  {
            TextView $r6 = (TextView) $r1.findViewById(C1283R.id.rideItemLiveButDrive);
            ImageView $r7 = (ImageView) $r1.findViewById(C1283R.id.rideItemLiveButDriveMore);
            final RideListItem rideListItem;
            if ($z0) {
                $r6.setBackgroundResource(C1283R.drawable.button_blue_bg);
                $r7.setBackgroundResource(C1283R.drawable.button_blue_bg);
                $r6.setText(this._nm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_RIDER_ONBOARD));
                final RideListItem rideListItem2 = $r2;
                $r6.setOnClickListener(new OnClickListener() {

                    class C15881 implements IResultObj<String> {
                        C15881() throws  {
                        }

                        public void onResult(String $r1) throws  {
                            DriveToNativeManager.getInstance().drive($r1, false);
                            ((MainActivity) CarpoolRidesFragment.this.getActivity()).getLayoutMgr().closeSwipeableLayout();
                        }
                    }

                    public void onClick(View v) throws  {
                        CarpoolRidesFragment.this.mCpnm.getMeetingIdByDrive(rideListItem2.drive, false, new C15881());
                    }
                });
                $r1.findViewById(C1283R.id.rideItemLiveSeparator).setVisibility(8);
                $r1.findViewById(C1283R.id.rideItemLiveSuggestionArea).setVisibility(0);
                ((TextView) $r1.findViewById(C1283R.id.rideItemLiveSuggestionsTitle)).setText(String.format(this._nm.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_TITLE_V4), new Object[]{$r2.name}));
                $r6 = (TextView) $r1.findViewById(C1283R.id.rideItemLiveSuggestion1);
                TextView $r12 = (TextView) $r1.findViewById(C1283R.id.rideItemLiveSuggestion2);
                TextView $r13 = (TextView) $r1.findViewById(C1283R.id.rideItemLiveSuggestion3);
                $r6.setText(this._nm.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_BE_THERE_SOON));
                $r12.setText(this._nm.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_WHERE_ARE_YOU));
                $r13.setText(this._nm.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_AT_PICKUP_POINT));
                rideListItem = $r2;
                C15906 c15906 = new OnClickListener() {
                    public void onClick(View $r2) throws  {
                        String $r5 = ((TextView) $r2).getText().toString();
                        if (CarpoolRidesFragment.this.mCpnm.isMessagingEnabled()) {
                            CarpoolRidesFragment.this.mCpnm.sendChatMessage(rideListItem.drive.getId(), $r5);
                            return;
                        }
                        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + rideListItem.drive.rides[0].proxy_number));
                        intent.putExtra("sms_body", $r5);
                        CarpoolRidesFragment.this.getActivity().startActivity(intent);
                    }
                };
                $r6.setOnClickListener(c15906);
                $r12.setOnClickListener(c15906);
                $r13.setOnClickListener(c15906);
            } else if ($z1) {
                CarpoolRidesFragment $r14 = CarpoolRidesFragment.this;
                $r6.setBackgroundDrawable($r14.getResources().getDrawable(C1283R.drawable.button_blue_bg));
                $r14 = CarpoolRidesFragment.this;
                $r7.setBackgroundDrawable($r14.getResources().getDrawable(C1283R.drawable.button_blue_bg));
                $r6.setText(this._nm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_RIDER_DROP_OFF_COMPLETE));
                rideListItem = $r2;
                $r6.setOnClickListener(new OnClickListener() {

                    class C15911 implements DriveToNavigateCallback {
                        C15911() throws  {
                        }

                        public void navigateCallback(int $i0) throws  {
                            if ($i0 == 0) {
                                ((MainActivity) CarpoolRidesFragment.this.getActivity()).getLayoutMgr().closeSwipeableLayout();
                            }
                        }
                    }

                    public void onClick(View v) throws  {
                        DriveToNativeManager.getInstance().navigate(CarpoolRidesFragment.this.mCpnm.driveGetAddressItem(rideListItem.drive, 4), new C15911(), true);
                    }
                });
            } else {
                $r6.setText(this._nm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_START_DRIVING));
                rideListItem = $r2;
                $r6.setOnClickListener(new OnClickListener() {

                    class C15931 implements IResultObj<String> {
                        C15931() throws  {
                        }

                        public void onResult(String $r1) throws  {
                            CarpoolRidesFragment.this.mCpnm.safeDriveToMeeting($r1, true, null);
                            ((MainActivity) CarpoolRidesFragment.this.getActivity()).getLayoutMgr().closeSwipeableLayout();
                        }
                    }

                    public void onClick(View v) throws  {
                        CarpoolRidesFragment.this.mCpnm.getMeetingIdByDrive(rideListItem.drive, true, new C15931());
                    }
                });
            }
        }

        private View getHeaderView(int $i0, View $r1, ViewGroup parent) throws  {
            SettingsTitleText $r3 = (SettingsTitleText) $r1;
            if ($r3 == null) {
                $r3 = r10;
                SettingsTitleText r10 = new SettingsTitleText(this._ctx, null);
            }
            $r3.setPadding(0, (int) (this._ctx.getResources().getDisplayMetrics().density * 16.0f), 0, (int) (this._ctx.getResources().getDisplayMetrics().density * 8.0f));
            String $r9 = ((RideListHeader) getItem($i0)).title;
            $r3.setText($r9);
            return $r3;
        }

        private View getDrillView(int $i0, View $r2, ViewGroup $r1) throws  {
            if ($r2 == null) {
                $r2 = this._inflater.inflate(C1283R.layout.carpool_item_drill, $r1, false);
            }
            final RideListDrill $r5 = (RideListDrill) getItem($i0);
            ((TextView) $r2.findViewById(C1283R.id.carpoolItemDrillText)).setText($r5.title);
            $r2.findViewById(C1283R.id.carpoolItemDrill).setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    $r5.toDo.run();
                }
            });
            return $r2;
        }

        private View getSpaceView(int $i0, View $r1, ViewGroup parent) throws  {
            Space $r5 = (Space) $r1;
            if ($r5 == null) {
                $r5 = new Space(this._ctx, null);
            }
            $r5.setLayoutParams(new LayoutParams(-1, ((RideListSpace) getItem($i0)).height));
            return $r5;
        }

        private View getBannerView(ViewGroup $r1) throws  {
            if (CarpoolRidesFragment.mLastShownBanner == null) {
                return new View(CarpoolRidesFragment.this.getActivity());
            }
            View $r4 = this._inflater.inflate(C1283R.layout.carpool_banner, $r1, false);
            CarpoolRidesFragment.mLastShownBanner.show((ActivityBase) CarpoolRidesFragment.this.getActivity(), $r4);
            return $r4;
        }

        public int getCount() throws  {
            return this._itemsList.size();
        }

        public Object getItem(int $i0) throws  {
            if ($i0 < 0 || $i0 >= this._itemsList.size()) {
                return null;
            }
            return this._itemsList.get($i0);
        }

        public int getItemViewType(int $i0) throws  {
            Object $r1 = getItem($i0);
            if ($r1 instanceof RideListItem) {
                RideListItem $r2 = (RideListItem) $r1;
                if ($r2.drive.isLive()) {
                    return 6;
                }
                return $r2.drive.isUpcoming() ? 7 : 0;
            } else if ($r1 instanceof RideListHeader) {
                return 1;
            } else {
                if ($r1 instanceof RideListDrill) {
                    return 2;
                }
                if ($r1 instanceof RideListSpace) {
                    return 3;
                }
                if ($r1 instanceof RideListCustom) {
                    return 4;
                }
                if ($r1 instanceof BannerRideListItem) {
                    return 5;
                }
                return $r1 instanceof DriveListItem ? 8 : -1;
            }
        }

        public boolean isEnabled(int $i0) throws  {
            $i0 = getItemViewType($i0);
            return $i0 == 0 || $i0 == 6 || $i0 == 2 || $i0 == 7;
        }

        public long getItemId(int $i0) throws  {
            return (long) $i0;
        }
    }

    static class RideListDrill extends RideListAbsItem {
        final int pos;
        final String title;
        final Runnable toDo;

        public RideListDrill(String $r1, int $i0, Runnable $r2) throws  {
            this.title = $r1;
            this.pos = $i0;
            this.toDo = $r2;
        }
    }

    static class RideListHeader extends RideListAbsItem {
        final String title;

        public RideListHeader(String $r1) throws  {
            this.title = $r1;
        }
    }

    static class RideListItem extends RideListAbsItem {
        CarpoolDrive drive;
        final String dropOff;
        final String name;
        final String pickup;
        final String reward;
        CarpoolRide ride;
        final long utcTime;

        public RideListItem(String $r1, String iurl, long $l0, String $r3, String $r4, String $r5) throws  {
            this.name = $r1;
            this.utcTime = $l0;
            this.pickup = $r3;
            this.dropOff = $r4;
            this.reward = $r5;
        }

        public static RideListItem fromDrive(CarpoolDrive $r0, CarpoolRide $r1, Context $r2) throws  {
            String $r4 = "";
            CarpoolLocation $r5 = $r0.getPickupLocation();
            if ($r5 != null) {
                $r4 = $r5.placeName;
                if ($r4 == null || $r4.isEmpty()) {
                    $r4 = $r5.address;
                }
            }
            $r5 = $r0.getPickupLocation();
            String $r6 = "";
            if ($r5 != null) {
                $r6 = $r5.placeName;
                if ($r6 == null || $r6.isEmpty()) {
                    $r6 = $r5.address;
                }
            }
            String $r7 = "";
            String $r8 = "";
            if ($r1 != null && $r1.getRider() != null) {
                $r7 = $r1.getRider().getName();
                $r8 = $r1.getRider().getImage();
            } else if ($r1 == null && $r0.hasRider()) {
                $r7 = $r0.getRider().getName();
                $r8 = $r0.getRider().getImage();
            }
            RideListItem rideListItem = new RideListItem($r7, $r8, $r0.getTime(), $r4, $r6, $r0.getRewardString($r2));
            rideListItem.drive = $r0;
            rideListItem.ride = $r1;
            return rideListItem;
        }
    }

    static class RideListSpace extends RideListAbsItem {
        final int height;

        public RideListSpace(int $i0) throws  {
            this.height = $i0;
        }
    }

    private enum ShowResult {
        SHOW,
        DONT_SHOW,
        CHECKING;

        static ShowResult fromBoolean(boolean $z0) throws  {
            return $z0 ? SHOW : DONT_SHOW;
        }
    }

    private void init(android.view.View r30) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:33:0x0146
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
        r29 = this;
        if (r30 != 0) goto L_0x0003;
    L_0x0002:
        return;
    L_0x0003:
        r0 = r29;
        r4 = r0.mISetTitle;
        if (r4 == 0) goto L_0x0014;
    L_0x0009:
        r0 = r29;
        r4 = r0.mISetTitle;
        r0 = r29;
        r5 = r0.mNowViewing;
        r4.setTitleBar(r5);
    L_0x0014:
        r0 = r29;
        r6 = r0.mProfile;
        if (r6 == 0) goto L_0x014a;
    L_0x001a:
        r0 = r29;
        r6 = r0.mProfile;
        r7 = r6.didFinishOnboarding();
        if (r7 == 0) goto L_0x014a;
    L_0x0024:
        r8 = "ONBOARDED";
    L_0x0026:
        r0 = r29;
        r5 = r0.mNumDrives;
        r0 = r29;
        r9 = r0.mNumFilteredDrives;
        r5 = r5 - r9;
        if (r5 <= 0) goto L_0x0151;
    L_0x0031:
        r7 = 1;
    L_0x0032:
        r11 = "RW_RIDES_LIST_SHOWN";
        r10 = com.waze.analytics.AnalyticsBuilder.analytics(r11);
        r0 = r29;
        r12 = r0.getAnalyticsCurPageName();
        r11 = "PAGE";
        r10 = r10.addParam(r11, r12);
        if (r7 == 0) goto L_0x0153;
    L_0x0046:
        r12 = "RIDES";
    L_0x0048:
        r11 = "STATE";
        r10 = r10.addParam(r11, r12);
        r11 = "STATUS";
        r10 = r10.addParam(r11, r8);
        r0 = r29;
        r5 = r0.mNumDrives;
        r0 = r29;
        r9 = r0.mNumFilteredDrives;
        r5 = r5 - r9;
        r13 = (long) r5;
        r11 = "NUM_REQUESTS";
        r10 = r10.addParam(r11, r13);
        r0 = r29;
        r5 = r0.mNumDrives;
        r13 = (long) r5;
        r11 = "NUM_RIDE_OFFER_REQUESTS";
        r10 = r10.addParam(r11, r13);
        r10.send();
        r0 = r29;
        r15 = r0.mList;
        if (r15 == 0) goto L_0x0082;
    L_0x0078:
        r0 = r29;
        r15 = r0.mList;
        r16 = r15.getAdapter();
        if (r16 != 0) goto L_0x0163;
    L_0x0082:
        r17 = new com.waze.carpool.CarpoolRidesFragment$RideListAdapter;
        r0 = r29;
        r18 = r0.getActivity();
        r0 = r29;
        r19 = r0.getRidesList();
        r0 = r17;
        r1 = r29;
        r2 = r18;
        r3 = r19;
        r0.<init>(r2, r3);
        r0 = r17;
        r1 = r29;
        r1.mAdapter = r0;
        r21 = 2131690098; // 0x7f0f0272 float:1.900923E38 double:1.053194845E-314;
        r0 = r30;
        r1 = r21;
        r20 = r0.findViewById(r1);
        r22 = r20;
        r22 = (android.widget.ListView) r22;
        r15 = r22;
        r0 = r29;
        r0.mList = r15;
        r0 = r29;
        r15 = r0.mList;
        r0 = r29;
        r0 = r0.mAdapter;
        r17 = r0;
        r15.setAdapter(r0);
        r0 = r29;
        r15 = r0.mList;
        r23 = new com.waze.carpool.CarpoolRidesFragment$7;
        goto L_0x00cd;
    L_0x00ca:
        goto L_0x0048;
    L_0x00cd:
        r0 = r23;
        r1 = r29;
        r0.<init>();
        r0 = r23;
        r15.setOnItemClickListener(r0);
    L_0x00d9:
        r0 = r29;
        r0.setLegalText();
        r0 = r29;
        r5 = r0.mNowViewing;
        r21 = 1;
        r0 = r21;
        if (r5 != r0) goto L_0x0194;
    L_0x00e8:
        r21 = 58;
        r0 = r21;
        r7 = com.waze.config.ConfigValues.getBoolValue(r0);
        if (r7 == 0) goto L_0x0194;
    L_0x00f2:
        r11 = "RW_RIDES_LIST_OFFER_RIDE_BUTTON_SHOWN";
        r10 = com.waze.analytics.AnalyticsBuilder.analytics(r11);
        r10.send();
        r21 = 2131690099; // 0x7f0f0273 float:1.9009232E38 double:1.0531948455E-314;
        r0 = r30;
        r1 = r21;
        r20 = r0.findViewById(r1);
        r21 = 0;
        r0 = r20;
        r1 = r21;
        r0.setVisibility(r1);
        goto L_0x0113;
    L_0x0110:
        goto L_0x00ca;
    L_0x0113:
        r21 = 2131690100; // 0x7f0f0274 float:1.9009234E38 double:1.053194846E-314;
        r0 = r30;
        r1 = r21;
        r30 = r0.findViewById(r1);
        r25 = r30;
        r25 = (android.widget.TextView) r25;
        r24 = r25;
        r21 = 3540; // 0xdd4 float:4.96E-42 double:1.749E-320;
        r0 = r21;
        r8 = com.waze.strings.DisplayStrings.displayString(r0);
        r0 = r24;
        r0.setText(r8);
        r26 = new com.waze.carpool.CarpoolRidesFragment$8;
        r0 = r26;
        r1 = r29;
        r0.<init>();
        goto L_0x013e;
    L_0x013b:
        goto L_0x00d9;
    L_0x013e:
        r0 = r24;
        r1 = r26;
        r0.setOnClickListener(r1);
        return;
        goto L_0x014a;
    L_0x0147:
        goto L_0x0026;
    L_0x014a:
        r8 = "NOT_ONBOARDED";
        goto L_0x0147;
        goto L_0x0151;
    L_0x014e:
        goto L_0x0032;
    L_0x0151:
        r7 = 0;
        goto L_0x014e;
    L_0x0153:
        r0 = r29;
        r7 = r0.mHadPreviousRides;
        if (r7 == 0) goto L_0x0160;
    L_0x0159:
        r12 = "ZERO1";
        goto L_0x0110;
        goto L_0x0160;
    L_0x015d:
        goto L_0x0048;
    L_0x0160:
        r12 = "ZERO2 ";
        goto L_0x015d;
    L_0x0163:
        r0 = r29;
        r0 = r0.mAdapter;
        r17 = r0;
        r0 = r29;
        r19 = r0.getRidesList();
        r0 = r17;
        r1 = r19;
        r0.setRideList(r1);
        r0 = r29;
        r0 = r0.mListState;
        r27 = r0;
        if (r27 == 0) goto L_0x00d9;
    L_0x017e:
        r0 = r29;
        r15 = r0.mList;
        r0 = r29;
        r0 = r0.mListState;
        r27 = r0;
        r15.onRestoreInstanceState(r0);
        r28 = 0;
        r0 = r28;
        r1 = r29;
        r1.mListState = r0;
        goto L_0x013b;
    L_0x0194:
        r21 = 2131690099; // 0x7f0f0273 float:1.9009232E38 double:1.0531948455E-314;
        r0 = r30;
        r1 = r21;
        r30 = r0.findViewById(r1);
        r21 = 8;
        r0 = r30;
        r1 = r21;
        r0.setVisibility(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRidesFragment.init(android.view.View):void");
    }

    public static void setJustRegistered() throws  {
        mbNewUser = true;
    }

    public void setISetTitle(ISetTitle $r1) throws  {
        this.mISetTitle = $r1;
    }

    public void setIntentMode(int $i0) throws  {
        this.mIntentMode = $i0;
    }

    @Nullable
    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle savedInstanceState) throws  {
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.mInflater = $r1;
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            this.mIsOnboarding = savedInstanceState.getBoolean("onboarding", false);
        }
        return $r1.inflate(C1283R.layout.carpool_all_rides, $r2, false);
    }

    private void setLegalText() throws  {
        if (!ConfigManager.getInstance().getConfigValueBool(15) || this.mList == null) {
            return;
        }
        if (this.mNowViewing == 2) {
            CarpoolPayee $r4 = CarpoolNativeManager.getInstance().getCachedPayeeNTV();
            if (this.legal == null) {
                Spanned $r10;
                this.legal = (WazeTextView) this.mInflater.inflate(C1283R.layout.payoneer_legal_text, this.mList, false);
                this.legal.setMovementMethod(LinkMovementMethod.getInstance());
                if ($r4 != null) {
                    $r10 = CarpoolUtils.getSpannedLegal($r4.currency_code);
                } else {
                    $r10 = CarpoolUtils.getSpannedLegal("");
                }
                this.legal.setText($r10);
                WazeTextView $r5 = this.legal;
                WazeTextView $r11 = this.legal;
                $r5.setLinkTextColor($r11.getTextColors());
                this.mList.addFooterView(this.legal);
            }
            this.legal.setVisibility(0);
        } else if (this.legal != null) {
            this.legal.setVisibility(8);
        }
    }

    public void onActivityCreated(Bundle $r1) throws  {
        boolean $z0 = false;
        super.onActivityCreated($r1);
        Activity $r2 = AppService.getMainActivity();
        boolean $z1 = $r2 != null && $r2 == getActivity();
        this.mIsInMainActivity = $z1;
        setHandlers();
        if (this.mIsOnboarding) {
            this.mListAvailable = true;
        } else {
            CarpoolNativeManager $r4 = this.mCpnm;
            if (this.mIntentMode == 2) {
                $z0 = true;
            }
            this.mListAvailable = $r4.drivesListAvailable($z0);
        }
        this.mProfile = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
        if (this.mProfile != null) {
            this.mHadPreviousRides = this.mProfile.hadPrevRides();
        }
        if ($r1 != null) {
            this.mNowViewing = $r1.getInt(TAG + ".mNowViewing", this.mNowViewing);
            this.mbViewModeSetOnIntent = $r1.getBoolean(TAG + ".mbViewModeSetOnIntent", this.mbViewModeSetOnIntent);
            this.mTestedLocHist = $r1.getBoolean(TAG + ".mTestedLocHist", this.mTestedLocHist);
        } else if (this.mIntentMode != 0) {
            this.mNowViewing = this.mIntentMode;
            this.mbViewModeSetOnIntent = true;
            if (this.mNowViewing == 2) {
                this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_HISTORY_LOADED, this.mHandler);
                this.mCpnm.getRidesHistory();
            }
        }
        setupActivity();
    }

    public void onResume() throws  {
        super.onResume();
        this.mProfile = this.mCpnm.getCarpoolProfileNTV();
        boolean $z0 = this.mProfile == null || !this.mProfile.didFinishOnboarding();
        if ($z0) {
            Activity $r3 = getActivity();
            if ($r3 instanceof MainActivity) {
                ((MainActivity) $r3).getLayoutMgr().refreshCarpoolPanel();
                return;
            }
            return;
        }
        if (this.mIsOnboarding && !$z0) {
            this.mIsOnboarding = false;
        }
        setupBanner(false);
    }

    public void onPause() throws  {
        super.onPause();
    }

    public void onDetach() throws  {
        super.onDetach();
        cleanup();
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
        this.mUpdateRunnable = null;
    }

    public void onAttach(Context $r1) throws  {
        super.onAttach($r1);
        setHandlers();
    }

    private void setHandlers() throws  {
        if (this.mCleaned) {
            this.mCleaned = false;
            this.mCpnm = CarpoolNativeManager.getInstance();
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, this.mHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.mHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED, this.mHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED, this.mHandler);
            ChatNotificationManager.getInstance(true).setChatUpdateHandler("", this.mChatHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE, this.mHandler);
        }
    }

    void setupBanner(boolean $z0) throws  {
        if (sUserClosedBannerThisSession) {
            mLastShownBanner = null;
            if (this.mAdapter != null) {
                this.mAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (!$z0) {
            Banner.LOCATION_HISTORY.resetCheck();
        }
        this.mProfile = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
        if (this.mProfile == null) {
            mLastShownBanner = null;
            return;
        }
        int $i0;
        Banner $r3 = null;
        for (Banner $r1 : Banner.values()) {
            ShowResult $r7 = $r1.shouldShow(this, this.mProfile);
            if ($r7 == ShowResult.SHOW) {
                int $i2 = $r1.getCounterConfig();
                int $i3 = 0;
                if ($i2 != -1) {
                    $i3 = ConfigManager.getInstance().checkConfigDisplayCounter($i2, false);
                }
                if ($i2 == -1 || $i3 > 0 || $r1 == mLastShownBanner) {
                    $r3 = $r1;
                    break;
                }
            } else if ($r7 != ShowResult.DONT_SHOW) {
                return;
            }
        }
        if ($r3 == null) {
            return;
        }
        if ((mLastShownBanner == null || $r3 == mLastShownBanner) && mLastShownBanner == null) {
            mLastShownBanner = $r3;
            if (this.mAdapter != null) {
                this.mAdapter.notifyDataSetChanged();
            }
            $i0 = $r3.getCounterConfig();
            if ($i0 != -1 && ConfigManager.getInstance().checkConfigDisplayCounter($i0, true) <= 0) {
                mLastShownBanner = null;
            }
        }
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putInt(TAG + ".mNowViewing", this.mNowViewing);
        $r1.putBoolean(TAG + ".mbViewModeSetOnIntent", this.mbViewModeSetOnIntent);
        $r1.putBoolean(TAG + ".mTestedLocHist", this.mTestedLocHist);
    }

    public void onDestroy() throws  {
        cleanup();
        super.onDestroy();
    }

    private void cleanup() throws  {
        if (!this.mCleaned) {
            this.mCleaned = true;
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_HISTORY_LOADED, this.mHandler);
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.mHandler);
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, this.mHandler);
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED, this.mHandler);
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED, this.mHandler);
            ChatNotificationManager.getInstance(true).unsetChatUpdateHandler("", this.mChatHandler);
            this.mHandler.removeCallbacks(this.mUpdateRunnable);
            this.mUpdateRunnable = null;
        }
    }

    public boolean onBackPressed() throws  {
        if (this.mIsOnboarding) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.mGetAnswerCb);
            return true;
        } else if (this.mbViewModeSetOnIntent) {
            return false;
        } else {
            if (this.mNowViewing == 1) {
                return false;
            }
            this.mNowViewing = 1;
            setupActivity();
            return true;
        }
    }

    public void setupActivity() throws  {
        boolean $z0 = true;
        this.mNumDrives = 0;
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 != null) {
            $r1.addOrientationTracker(new C15484());
        }
        if (this.mListAvailable) {
            CarpoolNativeManager $r3 = this.mCpnm;
            if (this.mNowViewing != 2) {
                $z0 = false;
            }
            $r3.getDrives($z0, this);
            if (this.mNowViewing != 2) {
                ((ActivityBase) getActivity()).postDelayed(this.mUpdateRunnable, UPDATE_TIMER);
            }
        } else {
            StringBuilder $r8 = new StringBuilder().append("CarpoolRidesFragment: list not available yet, history = ");
            if (this.mNowViewing != 2) {
                $z0 = false;
            }
            Logger.m36d($r8.append($z0).toString());
            openProgress();
        }
        if (this.mISetTitle != null) {
            this.mISetTitle.setTitleBar(this.mNowViewing);
        }
    }

    public void onResult(CarpoolDrive[] $r1) throws  {
        int $i0;
        this.mDrivesById.clear();
        if (this.mDrives == null || this.mDrives.length <= 0) {
        }
        this.mDrives = $r1;
        if (this.mDrives != null) {
            $i0 = this.mDrives.length;
        } else {
            $i0 = 0;
        }
        this.mNumDrives = $i0;
        $i0 = 0;
        if (this.mDrives != null) {
            for (int $i1 = 0; $i1 < this.mDrives.length; $i1++) {
                if (!this.mDrives[$i1].isEmpty()) {
                    this.mDrivesById.put(this.mDrives[$i1].uuid, this.mDrives[$i1]);
                    if (this.mDrives[$i1].isRequest()) {
                        $i0++;
                    }
                }
            }
        }
        if ($i0 != this.mRideRequestsAmount) {
            MainActivity $r6 = AppService.getMainActivity();
            if (this.mIsInMainActivity && $r6 != null) {
                $r6.getLayoutMgr().setRidewithDot();
            }
        }
        if (this.mDrives != null) {
            Arrays.sort(this.mDrives, new C15495());
            this.mNumFilteredDrives = 0;
            $i0 = 0;
            while ($i0 < this.mDrives.length && this.mDrives[$i0] != null && !this.mDrives[$i0].isEmpty()) {
                if (isFilteredOut(this.mDrives[$i0])) {
                    this.mNumFilteredDrives++;
                }
                $i0++;
            }
            this.mNumDrives = $i0;
        }
        init(getView());
    }

    void openProgress() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if (!this.mIsInMainActivity || $r1 == null) {
            NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
            return;
        }
        LayoutManager $r2 = $r1.getLayoutMgr();
        if ($r2 != null) {
            RightSideMenu $r3 = $r2.getRightSideMenu();
            if ($r3 != null) {
                $r3.setRightSideProgressVisiblity(true);
            }
        }
    }

    void closeProgress() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if (!this.mIsInMainActivity || $r1 == null) {
            NativeManager.getInstance().CloseProgressPopup();
            return;
        }
        LayoutManager $r2 = $r1.getLayoutMgr();
        if ($r2 != null) {
            RightSideMenu $r3 = $r2.getRightSideMenu();
            if ($r3 != null) {
                $r3.setRightSideProgressVisiblity(false);
            }
        }
    }

    private String getAnalyticsCurPageName() throws  {
        if (this.mNowViewing == 1) {
            return AnalyticsEvents.ANALYTICS_EVENT_VALUE_YOUR_RIDES;
        }
        if (this.mNowViewing == 2) {
            return AnalyticsEvents.ANALYTICS_EVENT_VALUE_RIDES_HISTORY;
        }
        return this.mNowViewing == 3 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_LOW_PRIORITY : "UNKNOWN";
    }

    static boolean isToday(CarpoolDrive $r0) throws  {
        if (!$r0.hasTimeZone()) {
            return false;
        }
        Calendar $r1 = Calendar.getInstance();
        $r1.setTimeZone(TimeZone.getTimeZone($r0.timeZone()));
        $r1.setTimeInMillis($r0.getTime() * 1000);
        return Calendar.getInstance().get(5) == $r1.get(5);
    }

    static boolean isYesterday(CarpoolDrive $r0) throws  {
        boolean $z0 = true;
        if (!$r0.hasTimeZone()) {
            return false;
        }
        Calendar $r1 = Calendar.getInstance();
        $r1.setTimeZone(TimeZone.getTimeZone($r0.timeZone()));
        $r1.setTimeInMillis($r0.getTime() * 1000);
        if (Calendar.getInstance().get(5) - $r1.get(5) != 1) {
            $z0 = false;
        }
        return $z0;
    }

    private boolean isFilteredOut(CarpoolDrive $r1) throws  {
        if ($r1.getRide() != null) {
            int $i2;
            if ($r1.getRides() != null) {
                for (CarpoolRide $r2 : $r1.getRides()) {
                    $i2 = $r2.getState();
                    if ($i2 != 3 && $i2 < 17 && $i2 > 0 && $i2 != 11 && $i2 != 12 && $i2 != 14) {
                        return false;
                    }
                }
            }
            if ($r1.former_rides != null) {
                for (CarpoolRide $r22 : $r1.former_rides) {
                    $i2 = $r22.getState();
                    if ($i2 != 3 && $i2 < 17 && $i2 > 0 && $i2 != 11 && $i2 != 12 && $i2 != 14) {
                        return false;
                    }
                }
            }
            return true;
        } else if ($r1.isCancelled() || $r1.state == 0) {
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<RideListAbsItem> getRidesList() throws  {
        final NativeManager $r2 = NativeManager.getInstance();
        ArrayList $r1 = new ArrayList();
        if (!this.mIsOnboarding) {
            $r1.add(new BannerRideListItem());
        }
        if (this.mNumDrives - this.mNumFilteredDrives > 0) {
            for (int $i0 = 0; $i0 < this.mNumDrives; $i0++) {
                if (!isFilteredOut(this.mDrives[$i0])) {
                    if (this.mDrives[$i0].isParentDrive()) {
                        CarpoolRide $r8;
                        if (!this.mDrives[$i0].isCancelled()) {
                            $r1.add(DriveListItem.fromDrive(this.mDrives[$i0]));
                        }
                        if (this.mDrives[$i0].getRidesAmount() > 0) {
                            for (CarpoolRide $r82 : this.mDrives[$i0].getRides()) {
                                if ($r82.state != 1) {
                                    if ($r82.state != 13) {
                                    }
                                }
                                $r1.add(RideListItem.fromDrive(this.mDrives[$i0], $r82, getActivity()));
                            }
                        }
                        if (this.mDrives[$i0].former_rides != null) {
                            $r82 = null;
                            for (CarpoolRide $r11 : this.mDrives[$i0].former_rides) {
                                if ($r11.state == 2) {
                                    $r82 = $r11;
                                    break;
                                }
                            }
                            if ($r82 != null) {
                                $r1.add(RideListItem.fromDrive(this.mDrives[$i0], $r82, getActivity()));
                            }
                        }
                    } else {
                        $r1.add(RideListItem.fromDrive(this.mDrives[$i0], this.mDrives[$i0].getRide(), getActivity()));
                    }
                }
            }
        } else if (this.mListAvailable && !this.mbViewModeSetOnIntent) {
            $r1.add(new RideListCustom() {

                class C15501 implements OnClickListener {
                    C15501() throws  {
                    }

                    public void onClick(View v) throws  {
                        try {
                            CarpoolRidesFragment.this.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.ridewith")));
                        } catch (ActivityNotFoundException e) {
                            CarpoolRidesFragment.this.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.ridewith")));
                        }
                    }
                }

                class C15512 implements OnClickListener {
                    C15512() throws  {
                    }

                    public void onClick(View v) throws  {
                        CarpoolRidesFragment.this.startActivityForResult(new Intent(CarpoolRidesFragment.this.getActivity(), CarpoolFAQActivity.class), 0);
                    }
                }

                public View inflate(LayoutInflater $r1, ViewGroup $r2) throws  {
                    View $r3 = $r1.inflate(C1283R.layout.carpool_no_rides, $r2, false);
                    TextView $r5 = (TextView) $r3.findViewById(C1283R.id.noRidesTitle);
                    TextView $r6 = (TextView) $r3.findViewById(C1283R.id.noRidesText);
                    TextView $r7 = (TextView) $r3.findViewById(C1283R.id.noRidesMore);
                    $r7.setVisibility(8);
                    ImageView $r8 = (ImageView) $r3.findViewById(C1283R.id.noRidesImage);
                    View $r4 = $r3.findViewById(C1283R.id.getRiderApp);
                    if (CarpoolRidesFragment.this.mNowViewing == 1) {
                        TextView $r12 = (TextView) $r3.findViewById(C1283R.id.getClientText);
                        ((TextView) $r3.findViewById(C1283R.id.getClientLabel)).setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_GET_APP_LABEL));
                        $r12.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_GET_APP));
                        $r12.setPaintFlags(8);
                        $r4.setOnClickListener(new C15501());
                        if (CarpoolRidesFragment.mbNewUser) {
                            $r5.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_NO_REQUESTS_TITLE_ON_REGITER));
                            $r6.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_NO_REQUESTS_TEXT_ON_REGITER));
                            $r8.setImageResource(C1283R.drawable.all_rides_empty_illu_screenmock);
                            $r7.setVisibility(0);
                            $r7.setText(EditTextUtils.underlineSpan(DisplayStrings.DS_ALL_RIDES_LEARN_MORE));
                            $r7.setOnClickListener(new C15512());
                            return $r3;
                        } else if (CarpoolRidesFragment.this.mHadPreviousRides) {
                            $r5.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_NO_REQUESTS_TITLE));
                            $r6.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_NO_REQUESTS_TEXT));
                            $r8.setImageResource(C1283R.drawable.all_rides_empty_illu_screenmock);
                            $r3.findViewById(C1283R.id.getRiderApp).setVisibility(8);
                            return $r3;
                        } else {
                            $r5.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_NO_REQUESTS_TITLE_FIRST));
                            $r6.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_NO_REQUESTS_TEXT_FIRST));
                            $r8.setImageResource(C1283R.drawable.all_rides_empty_illu_screenmock);
                            return $r3;
                        }
                    } else if (CarpoolRidesFragment.this.mNowViewing == 2) {
                        $r6.setVisibility(8);
                        $r4.setVisibility(8);
                        $r5.setText($r2.getLanguageString((int) DisplayStrings.DS_ALL_RIDES_NO_HISTORY_TITLE));
                        $r8.setImageResource(C1283R.drawable.ridewith_rideshistory_empty_illu);
                        return $r3;
                    } else {
                        $r5.setVisibility(8);
                        return $r3;
                    }
                }
            });
        }
        if (this.mNowViewing != 1) {
            return $r1;
        }
        if (this.mNumDrives - this.mNumFilteredDrives > 0) {
            $r1.add(new RideListSpace(PixelMeasure.dp(10)));
        }
        $r1.add(new RideListSpace(PixelMeasure.dp(20)));
        return $r1;
    }

    public void handleMessage(Message $r1) throws  {
        boolean $z0 = true;
        if (!isAdded()) {
            return;
        }
        if ($r1.what == CarpoolNativeManager.UH_HISTORY_LOADED) {
            Logger.m36d("CarpoolRidesFragment: receveid UH_HISTORY_LOADED msg");
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_HISTORY_LOADED, this.mHandler);
            closeProgress();
            this.mNowViewing = 2;
            this.mListAvailable = true;
            setupActivity();
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED) {
            Logger.m36d("CarpoolRidesFragment: myHandleMessage: received carpool drive updated");
            if (this.mNowViewing != 2) {
                setupActivity();
            }
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED) {
            Logger.m36d("CarpoolRidesFragment: received UH_CARPOOL_DRIVES_UPDATED msg");
            if (this.mNowViewing != 2) {
                if (!this.mListAvailable) {
                    CarpoolNativeManager $r2 = CarpoolNativeManager.getInstance();
                    if (this.mNowViewing != 2) {
                        $z0 = false;
                    }
                    this.mListAvailable = $r2.drivesListAvailable($z0);
                    if (this.mListAvailable) {
                        closeProgress();
                        setupActivity();
                        return;
                    }
                }
                onResult((CarpoolDrive[]) $r1.getData().getParcelableArray("drives"));
            }
        } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED || $r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED) {
            Logger.m36d("CarpoolRidesFragment: received UH_CARPOOL_DRIVE_CREATED|UH_CARPOOL_DRIVE_CREATED msg");
            if (this.mNowViewing != 2) {
                this.mCpnm.getDrives(false, this);
            }
        }
    }

    void postDelayed(Runnable $r1, long $l0) throws  {
        this.mHandler.postDelayed($r1, $l0);
    }

    private void rideClicked(int $i0, RideListItem $r1) throws  {
        if ($r1.ride != null) {
            String $r3;
            if (this.mNowViewing != 1) {
                $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_RECENT;
            } else if ($r1.ride.state == 1 || $r1.ride.state == 13) {
                $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_PENDING;
            } else if ($r1.ride.state != 4) {
                $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_UPCOMING;
            } else {
                $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_RECENT;
            }
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_RIDE_CLICKED).addParam("TYPE", $r3).addParam("TIME", $r1.ride.getTime() * 1000).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CARD_NUM, (long) $i0).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r1.drive != null ? $r1.drive.getId() : "").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r1.ride.getId()).send();
            openRide($r1.drive, $r1.ride);
        }
    }

    private void showOfferTooltip(TextView $r1, View $r2) throws  {
        Activity $r3 = getActivity();
        if ($r3 != null && !this.mOfferRideTooltipShown) {
            this.mOfferRideTooltipShown = true;
            ConfigManager.getInstance().setConfigValueBool(311, true);
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_INTRO_POPUP_SHOWN).send();
            this.mOfferDriveTip = new FullScreenTooltipShape($r3, DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_INTRO_TITLE), DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_INTRO_TEXT));
            this.mOfferDriveTip.setShapeType(FullScreenTooltipShape.RECTANGLE);
            final TextView textView = $r1;
            this.mOfferDriveTip.setOnClickListeners(new IToolTipClicked() {
                public void onBackgroundClicked() throws  {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_INTRO_POPUP_CLICKED).addParam("ACTION", "BG").send();
                    CarpoolRidesFragment.this.mOfferDriveTip.dismissTooltip();
                    CarpoolRidesFragment.this.mOfferDriveTip = null;
                }

                public void onBack() throws  {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_INTRO_POPUP_CLICKED).addParam("ACTION", "BACK").send();
                    CarpoolRidesFragment.this.mOfferDriveTip.dismissTooltip();
                    CarpoolRidesFragment.this.mOfferDriveTip = null;
                }

                public void onHighLightClicked() throws  {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_INTRO_POPUP_CLICKED).addParam("ACTION", "OFFER_RIDE").send();
                    CarpoolRidesFragment.this.mOfferDriveTip.dismissTooltip();
                    CarpoolRidesFragment.this.mOfferDriveTip = null;
                    textView.performClick();
                }
            });
            float $f0 = getResources().getDisplayMetrics().density;
            this.mOfferDriveTip.setOffsets(5.0f * $f0, 2.75f * $f0, 0.75f * $f0);
            this.mOfferDriveTip.show($r1, $r2);
        }
    }

    private void driveClicked(DriveListItem $r1) throws  {
        CarpoolDrive $r2 = $r1.drive;
        if ($r2 != null) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDES_LIST_RIDE_CLICKED).addParam("TYPE", "DRIVE").addParam("TIME", $r2.getTime() * 1000).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CARD_NUM, 2130771973).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r2.getId()).send();
            Intent $r3 = new Intent(getActivity(), CarpoolOfferDriveActivity.class);
            $r3.putExtra(CarpoolDrive.class.getSimpleName(), $r2);
            startActivityForResult($r3, PublicMacros.CARPOOL_DRIVE_DETAILS_REQUEST_CODE);
        }
    }

    private void openRide(CarpoolDrive $r1, CarpoolRide $r2) throws  {
        if ($r1 == null) {
            Logger.m38e("NavigateActivity: drive is null! cannot view ride details");
            MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
            return;
        }
        Intent $r3 = new Intent(getActivity(), CarpoolRideDetailsActivity.class);
        $r3.putExtra(CarpoolDrive.class.getSimpleName(), $r1);
        $r3.putExtra(CarpoolRide.class.getSimpleName(), $r2);
        $r3.putExtra("onboarding", this.mIsOnboarding);
        startActivityForResult($r3, PublicMacros.CARPOOL_RIDE_DETAILS_REQUEST_CODE);
    }

    public void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
        if ($i0 == 16777217 && ($i1 == 10 || $i1 == 11)) {
            this.mCpnm.getDrives(false, this);
            setupActivity();
            if ($i1 == 11) {
                AppService.getActiveActivity().showPopup(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_RIDE_REQ_DECLINE_MSG), "popup_x_icon");
                if (this.mIsOnboarding) {
                    CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_DECLINE, this.mGetAnswerCb);
                }
            } else if (this.mIsOnboarding) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        CarpoolRidesFragment.this.mCpnm.RefreshListOfRides();
                    }
                }, 1000);
            }
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    public void removeBanner() throws  {
        mLastShownBanner = null;
        this.mAdapter.notifyDataSetChanged();
    }
}
