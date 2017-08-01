package com.waze;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.NativeManager.IResultOk;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.Person;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.PickupOfferDialog;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.ifs.ui.TinyTooltip;
import com.waze.main.navigate.EventOnRoute;
import com.waze.main.navigate.LocationData;
import com.waze.map.CanvasFont;
import com.waze.map.NativeCanvasRenderer;
import com.waze.menus.NavResultsTooltipView;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.EventsOnRouteListener;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.DriveToNativeManager.LocationDataListener;
import com.waze.navigate.social.AddFromActivity;
import com.waze.navigate.social.FriendsListData;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.PhoneRequestAccessDialog;
import com.waze.phone.PhoneRequestAccessDialog.PhoneRequestAccessResultListener;
import com.waze.routes.ETATrafficBar;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.drawables.MultiContactsBitmap;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.timer.TimerBar;
import dalvik.annotation.Signature;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class NavResultsFragment extends Fragment {
    private static final long TOOLTIP_DELAY = 2000;
    private View mAddStopTooltip;
    private ArrayList<PersonBase> mAlreadyShared;
    private boolean mCalculating;
    private TinyTooltip mCarpoolTooltip;
    private boolean mClosing = false;
    private boolean mDidGetEventsOnRoute = false;
    private String mDistance;
    private String mDistanceUnit;
    private boolean mFinalIsParking;
    private String mFinalTitle;
    private String mFreeText;
    private LinearLayout mFreqFriendsLayout;
    private FriendUserData[] mFriendsData;
    private boolean mGoOnReturnFromAddMore = false;
    private Handler mHandler = new Handler();
    private boolean mIsCarpoolDrive;
    private boolean mIsTripServerResult;
    private boolean mIsViaFerry;
    private boolean mIsViaToll;
    private boolean mIsWaypoint;
    private String mMeetingId;
    private Bitmap mMultiPaxBitmap;
    private String mNote;
    private int mNoteID;
    private boolean mOfflineRoute;
    private PickupOfferDialog mPickupOfferDialog;
    private ProgressAnimation mProg;
    private int mRouteLength;
    private ArrayList<PersonBase> mSelected;
    private boolean mSetUp = false;
    private ImageView mShareImage;
    private ImageView mShareUserFrame;
    private ImageView mShareUserIcon;
    private ImageView mShareUserImage;
    private TextView mShareUserInitials;
    private boolean mShowDisclaimer;
    private boolean mShowRoutes;
    private boolean mShownAgain;
    private ArrayList<PersonBase> mSuggestions;
    private int mTimeOut;
    private String mTitle;
    private boolean mTooltipWasHandled = false;
    private String mVia;
    private boolean mViaDangerZone;
    private String mWaypointDistance;
    private String mWaypointDistanceUnit;
    private int mWaypointLength;
    private String mWaypointTitle;

    class AnonymousClass10 implements IResultObj<CarpoolDrive> {
        final /* synthetic */ boolean val$isReopen;
        final /* synthetic */ View val$shareButton;
        final /* synthetic */ View val$shareSep;

        AnonymousClass10(boolean $z0, View $r2, View $r3) throws  {
            this.val$isReopen = $z0;
            this.val$shareButton = $r2;
            this.val$shareSep = $r3;
        }

        public void onResult(CarpoolDrive $r1) throws  {
            NavResultsFragment.this.setCarpoolRiderInShare($r1, this.val$isReopen, this.val$shareButton, this.val$shareSep);
        }
    }

    class AnonymousClass12 implements FriendsListListener {
        final /* synthetic */ boolean val$isReopen;
        final /* synthetic */ View val$r;

        AnonymousClass12(View $r2, boolean $z0) throws  {
            this.val$r = $r2;
            this.val$isReopen = $z0;
        }

        public void onComplete(FriendsListData $r1) throws  {
            if ($r1 != null) {
                FriendUserData[] $r3 = $r1;
                $r1 = $r3;
                if ($r3.friends != null) {
                    $r3 = $r1;
                    $r1 = $r3;
                    NavResultsFragment.this.mAlreadyShared = new ArrayList($r3.friends.length);
                    $r3 = $r1;
                    $r1 = $r3;
                    for (FriendUserData $r2 : $r3.friends) {
                        if ($r2.mContactID != -1) {
                            Person $r7 = AddressBookImpl.getInstance().GetPersonFromID($r2.mContactID);
                            if (!($r7 == null || $r7.getImage() == null)) {
                                $r2.setImage($r7.getImage());
                            }
                        }
                        NavResultsFragment.this.mAlreadyShared.add($r2);
                    }
                    if (NavResultsFragment.this.mAlreadyShared.size() > 0) {
                        NavResultsFragment.this.populateShareButton(this.val$r, this.val$isReopen, (PersonBase) NavResultsFragment.this.mAlreadyShared.get(0), true);
                    }
                }
            }
        }
    }

    class AnonymousClass15 implements OnClickListener {
        final /* synthetic */ View val$r;

        class C12561 implements Runnable {
            C12561() throws  {
            }

            public void run() throws  {
                AppService.getNativeManager().navigateMainPlayStartNTV();
                AppService.getNativeManager().navigateMainGetCouponNTV();
            }
        }

        class C12572 implements Runnable {
            C12572() throws  {
            }

            public void run() throws  {
                AppService.getNativeManager().startTripServerNavigationNTV();
            }
        }

        AnonymousClass15(View $r2) throws  {
            this.val$r = $r2;
        }

        public void onClick(View v) throws  {
            if (!NavResultsFragment.this.mCalculating) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ETA_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_GO);
                boolean $z0 = ((TimerBar) this.val$r.findViewById(C1283R.id.fragNavResGoTimer)).hasExpired();
                NavResultsFragment.this.sendMeeting();
                if (NavResultsFragment.this.mShownAgain) {
                    NavResultsFragment.this.dismissMe(true);
                    return;
                }
                int $i0 = 0;
                Location $r5 = LocationFactory.getInstance().getLastLocation();
                if ($r5 != null) {
                    $i0 = (int) ($r5.getSpeed() * 3.6f);
                }
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_GO_FROM_ETA, "TIMEOUT|CURRENT_SPEED", ($z0 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F) + "|" + $i0);
                if (!NavResultsFragment.this.mIsTripServerResult) {
                    NativeManager.Post(new C12561());
                }
                NativeManager.Post(new C12572());
                NavResultsFragment.this.dismissMe(true);
                if (NativeManager.getInstance().isNavigatingNTV()) {
                    NativeManager.getInstance().getNavBarManager().showNavBar();
                }
            }
        }
    }

    class C12655 implements OnClickListener {
        C12655() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ETA_CLICK, "ACTION", "ADD_STOP");
            DriveToNativeManager.getInstance().setSkipConfirmWaypoint(true);
            NavResultsFragment.this.dismissMe(true);
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().getLayoutMgr().openLeftSideToAutocomplete(true);
            }
        }
    }

    class C12677 implements Runnable {
        C12677() throws  {
        }

        public void run() throws  {
            AppService.getNativeManager().navigateMainGetCouponNTV();
        }
    }

    class C12688 implements OnClickListener {
        C12688() throws  {
        }

        public void onClick(View v) throws  {
            NavResultsFragment.this.showRoutes();
        }
    }

    class C12699 implements IResultObj<CarpoolDrive> {
        final /* synthetic */ boolean val$isReopen;
        final /* synthetic */ View val$shareButton;
        final /* synthetic */ View val$shareSep;

        C12699(boolean $z0, View $r2, View $r3) throws  {
            this.val$isReopen = $z0;
            this.val$shareButton = $r2;
            this.val$shareSep = $r3;
        }

        public void onResult(CarpoolDrive $r1) throws  {
            NavResultsFragment.this.setCarpoolRiderInShare($r1, this.val$isReopen, this.val$shareButton, this.val$shareSep);
        }
    }

    private void setButtonsArea(android.view.View r42, com.waze.NativeManager r43, boolean r44, boolean r45) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:19:0x018b in {2, 9, 11, 15, 16, 18, 20, 33, 40, 43, 46, 49} preds:[]
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
        r41 = this;
        r0 = r42;
        r4 = r0.getResources();
        if (r45 == 0) goto L_0x018f;
    L_0x0008:
        r5 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
    L_0x000b:
        r5 = r4.getColor(r5);
        r7 = 2131623939; // 0x7f0e0003 float:1.8875044E38 double:1.053162158E-314;
        r6 = r4.getColor(r7);
        r7 = 2131623996; // 0x7f0e003c float:1.887516E38 double:1.0531621863E-314;
        r8 = r4.getColor(r7);
        r7 = 585; // 0x249 float:8.2E-43 double:2.89E-321;
        r0 = r43;
        r9 = r0.getLanguageString(r7);
        r7 = 2131690609; // 0x7f0f0471 float:1.9010266E38 double:1.0531950975E-314;
        r0 = r42;
        r10 = r0.findViewById(r7);
        r12 = r10;
        r12 = (android.widget.TextView) r12;
        r11 = r12;
        r11.setText(r9);
        r7 = 2131690608; // 0x7f0f0470 float:1.9010264E38 double:1.053195097E-314;
        r0 = r42;
        r10 = r0.findViewById(r7);
        r13 = new com.waze.NavResultsFragment$8;
        r0 = r41;
        r13.<init>();
        r10.setOnClickListener(r13);
        com.waze.view.anim.AnimationUtils.viewBgInit(r10, r5, r8, r6);
        r7 = 2131690611; // 0x7f0f0473 float:1.901027E38 double:1.0531950985E-314;
        r0 = r42;
        r10 = r0.findViewById(r7);
        r7 = 2131690610; // 0x7f0f0472 float:1.9010268E38 double:1.053195098E-314;
        r0 = r42;
        r14 = r0.findViewById(r7);
        r7 = 2131690612; // 0x7f0f0474 float:1.9010273E38 double:1.053195099E-314;
        r0 = r42;
        r15 = r0.findViewById(r7);
        r17 = r15;
        r17 = (android.widget.ImageView) r17;
        r16 = r17;
        r0 = r16;
        r1 = r41;
        r1.mShareUserImage = r0;
        r7 = 2131690614; // 0x7f0f0476 float:1.9010277E38 double:1.0531951E-314;
        r0 = r42;
        r15 = r0.findViewById(r7);
        r18 = r15;
        r18 = (android.widget.TextView) r18;
        r11 = r18;
        r0 = r41;
        r0.mShareUserInitials = r11;
        r7 = 2131690613; // 0x7f0f0475 float:1.9010275E38 double:1.0531950994E-314;
        r0 = r42;
        r15 = r0.findViewById(r7);
        r19 = r15;
        r19 = (android.widget.ImageView) r19;
        r16 = r19;
        r0 = r16;
        r1 = r41;
        r1.mShareUserFrame = r0;
        r7 = 2131690615; // 0x7f0f0477 float:1.9010279E38 double:1.0531951004E-314;
        r0 = r42;
        r15 = r0.findViewById(r7);
        r20 = r15;
        r20 = (android.widget.ImageView) r20;
        r16 = r20;
        r0 = r16;
        r1 = r41;
        r1.mShareUserIcon = r0;
        r7 = 2131690616; // 0x7f0f0478 float:1.901028E38 double:1.053195101E-314;
        r0 = r42;
        r15 = r0.findViewById(r7);
        r21 = r15;
        r21 = (android.widget.ImageView) r21;
        r16 = r21;
        r0 = r16;
        r1 = r41;
        r1.mShareImage = r0;
        r22 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r0 = r22;
        r45 = r0.isCarpoolShareOnly();
        r0 = r43;
        r23 = r0.ShareRideFeatureEnabledNTV();
        r0 = r43;
        r24 = r0.isFollowActiveNTV();
        if (r45 == 0) goto L_0x01ba;
    L_0x00dc:
        r7 = 2131690617; // 0x7f0f0479 float:1.9010283E38 double:1.0531951014E-314;
        r0 = r42;
        r15 = r0.findViewById(r7);
        r25 = r15;
        r25 = (android.widget.TextView) r25;
        r11 = r25;
        r7 = 2676; // 0xa74 float:3.75E-42 double:1.322E-320;
        r0 = r43;
        r9 = r0.getLanguageString(r7);
        r11.setText(r9);
        r0 = r22;
        r9 = r0.getCurMeetingIdNTV();
        if (r9 == 0) goto L_0x0193;
    L_0x00fe:
        r45 = r9.isEmpty();
        if (r45 != 0) goto L_0x0193;
    L_0x0104:
        r26 = new com.waze.NavResultsFragment$9;
        r0 = r26;
        r1 = r41;
        r2 = r44;
        r0.<init>(r2, r10, r14);
        r0 = r22;
        r1 = r26;
        r0.getDriveInfoByMeetingId(r9, r1);
    L_0x0116:
        r7 = 2131690620; // 0x7f0f047c float:1.9010289E38 double:1.053195103E-314;
        r0 = r42;
        r10 = r0.findViewById(r7);
        r27 = r10;
        r27 = (android.widget.TextView) r27;
        r11 = r27;
        r7 = 620; // 0x26c float:8.69E-43 double:3.063E-321;
        r0 = r43;
        r9 = r0.getLanguageString(r7);
        r11.setText(r9);
        r7 = 2131690619; // 0x7f0f047b float:1.9010287E38 double:1.0531951024E-314;
        r0 = r42;
        r10 = r0.findViewById(r7);
        r28 = new com.waze.NavResultsFragment$13;
        r0 = r28;
        r1 = r41;
        r0.<init>();
        r0 = r28;
        r10.setOnClickListener(r0);
        com.waze.view.anim.AnimationUtils.viewBgInit(r10, r5, r8, r6);
        r0 = r41;
        r1 = r42;
        r2 = r44;
        r0.setGoButton(r1, r2);
        r7 = 2131689848; // 0x7f0f0178 float:1.9008723E38 double:1.0531947215E-314;
        r0 = r42;
        r10 = r0.findViewById(r7);
        goto L_0x0160;
    L_0x015d:
        goto L_0x0116;
    L_0x0160:
        r0 = r41;
        r0 = r0.mIsCarpoolDrive;
        r44 = r0;
        if (r44 == 0) goto L_0x02cc;
    L_0x0168:
        goto L_0x016c;
    L_0x0169:
        goto L_0x0116;
    L_0x016c:
        r7 = 8;
        r10.setVisibility(r7);
    L_0x0171:
        r7 = 2131690622; // 0x7f0f047e float:1.9010293E38 double:1.053195104E-314;
        r0 = r42;
        r10 = r0.findViewById(r7);
        r29 = new com.waze.NavResultsFragment$15;
        r0 = r29;
        r1 = r41;
        r2 = r42;
        r0.<init>(r2);
        r0 = r29;
        r10.setOnClickListener(r0);
        return;
        goto L_0x018f;
    L_0x018c:
        goto L_0x000b;
    L_0x018f:
        r5 = 2131623950; // 0x7f0e000e float:1.8875066E38 double:1.0531621635E-314;
        goto L_0x018c;
    L_0x0193:
        r0 = r41;
        r0 = r0.mIsCarpoolDrive;
        r45 = r0;
        if (r45 == 0) goto L_0x01ae;
    L_0x019b:
        r30 = new com.waze.NavResultsFragment$10;
        r0 = r30;
        r1 = r41;
        r2 = r44;
        r0.<init>(r2, r10, r14);
        r0 = r22;
        r1 = r30;
        r0.getLiveDrive(r1);
        goto L_0x015d;
    L_0x01ae:
        r31 = 0;
        r0 = r41;
        r1 = r31;
        r2 = r44;
        r0.setCarpoolRiderInShare(r1, r2, r10, r14);
        goto L_0x0169;
    L_0x01ba:
        if (r23 != 0) goto L_0x01be;
    L_0x01bc:
        if (r24 == 0) goto L_0x02bd;
    L_0x01be:
        r7 = 0;
        r10.setVisibility(r7);
        r7 = 0;
        r14.setVisibility(r7);
        r7 = 2131690617; // 0x7f0f0479 float:1.9010283E38 double:1.0531951014E-314;
        r0 = r42;
        r14 = r0.findViewById(r7);
        r32 = r14;
        r32 = (android.widget.TextView) r32;
        r11 = r32;
        r7 = 44;
        r0 = r43;
        r9 = r0.getLanguageString(r7);
        r11.setText(r9);
        r33 = new com.waze.NavResultsFragment$11;
        r0 = r33;
        r1 = r41;
        r0.<init>();
        r0 = r33;
        r10.setOnClickListener(r0);
        com.waze.view.anim.AnimationUtils.viewBgInit(r10, r5, r8, r6);
        if (r24 == 0) goto L_0x0250;
    L_0x01f3:
        r0 = r41;
        r0 = r0.mShareUserImage;
        r16 = r0;
        r7 = 8;
        r0 = r16;
        r0.setVisibility(r7);
        r0 = r41;
        r0 = r0.mShareUserFrame;
        r16 = r0;
        r7 = 8;
        r0 = r16;
        r0.setVisibility(r7);
        r0 = r41;
        r0 = r0.mShareUserIcon;
        r16 = r0;
        r7 = 8;
        r0 = r16;
        r0.setVisibility(r7);
        r0 = r41;
        r11 = r0.mShareUserInitials;
        r7 = 8;
        r11.setVisibility(r7);
        r0 = r43;
        r9 = r0.getCurMeetingNTV();
        r0 = r41;
        r0.mMeetingId = r9;
        r0 = r41;
        r9 = r0.mMeetingId;
        if (r9 == 0) goto L_0x0116;
    L_0x0233:
        r34 = com.waze.navigate.DriveToNativeManager.getInstance();
        r35 = new com.waze.NavResultsFragment$12;
        r0 = r35;
        r1 = r41;
        r2 = r42;
        r3 = r44;
        r0.<init>(r2, r3);
        goto L_0x0248;
    L_0x0245:
        goto L_0x0116;
    L_0x0248:
        r0 = r34;
        r1 = r35;
        r0.getMySharedDriveUsers(r1);
        goto L_0x0245;
    L_0x0250:
        r0 = r41;
        r0 = r0.mSuggestions;
        r36 = r0;
        if (r36 == 0) goto L_0x0264;
    L_0x0258:
        r0 = r41;
        r0 = r0.mSuggestions;
        r36 = r0;
        r45 = r0.isEmpty();
        if (r45 == 0) goto L_0x0299;
    L_0x0264:
        r0 = r41;
        r0 = r0.mShareUserImage;
        r16 = r0;
        r7 = 8;
        r0 = r16;
        r0.setVisibility(r7);
        r0 = r41;
        r0 = r0.mShareUserFrame;
        r16 = r0;
        r7 = 8;
        r0 = r16;
        r0.setVisibility(r7);
        r0 = r41;
        r0 = r0.mShareUserIcon;
        r16 = r0;
        r7 = 8;
        r0 = r16;
        r0.setVisibility(r7);
        r0 = r41;
        r11 = r0.mShareUserInitials;
        goto L_0x0293;
    L_0x0290:
        goto L_0x0116;
    L_0x0293:
        r7 = 8;
        r11.setVisibility(r7);
        goto L_0x0290;
    L_0x0299:
        r0 = r41;
        r0 = r0.mSuggestions;
        r36 = r0;
        r7 = 0;
        r0 = r36;
        r37 = r0.get(r7);
        r39 = r37;
        r39 = (com.waze.user.PersonBase) r39;
        r38 = r39;
        goto L_0x02b0;
    L_0x02ad:
        goto L_0x0116;
    L_0x02b0:
        r7 = 0;
        r0 = r41;
        r1 = r42;
        r2 = r44;
        r3 = r38;
        r0.populateShareButton(r1, r2, r3, r7);
        goto L_0x02ad;
    L_0x02bd:
        r7 = 8;
        r10.setVisibility(r7);
        goto L_0x02c6;
    L_0x02c3:
        goto L_0x0116;
    L_0x02c6:
        r7 = 8;
        r14.setVisibility(r7);
        goto L_0x02c3;
    L_0x02cc:
        r7 = 0;
        r10.setVisibility(r7);
        r40 = new com.waze.NavResultsFragment$14;
        r0 = r40;
        r1 = r41;
        r0.<init>();
        r0 = r40;
        r10.setOnClickListener(r0);
        goto L_0x02e2;
    L_0x02df:
        goto L_0x0171;
    L_0x02e2:
        com.waze.planned_drive.PlannedDriveActivity.setupDriveLaterButton(r10);
        goto L_0x02df;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.NavResultsFragment.setButtonsArea(android.view.View, com.waze.NativeManager, boolean, boolean):void");
    }

    private void setCarpoolAddressStrings(boolean r19, com.waze.carpool.CarpoolDrive r20, android.view.View r21) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:40:0x0143 in {4, 7, 12, 13, 15, 16, 20, 22, 23, 27, 31, 35, 38, 41} preds:[]
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
        r18 = this;
        r0 = r20;
        r1 = r0.getRidesAmount();
        if (r19 == 0) goto L_0x00c5;
    L_0x0008:
        r0 = r20;
        r2 = r0.getPickupLocation();
        if (r2 == 0) goto L_0x006d;
    L_0x0010:
        r3 = r2.address;
    L_0x0012:
        r4 = 1;
        if (r1 != r4) goto L_0x0070;
    L_0x0015:
        r4 = 2;
        r5 = new java.lang.Object[r4];
        r0 = r20;
        r6 = r0.getRider();
        r7 = r6.getFirstName();
        r4 = 0;
        r5[r4] = r7;
        r4 = 1;
        r5[r4] = r3;
        r4 = 2667; // 0xa6b float:3.737E-42 double:1.3177E-320;
        r3 = com.waze.strings.DisplayStrings.displayStringF(r4, r5);
    L_0x002e:
        r4 = 2131690593; // 0x7f0f0461 float:1.9010234E38 double:1.0531950896E-314;
        r0 = r21;
        r8 = r0.findViewById(r4);
        r10 = r8;
        r10 = (android.widget.TextView) r10;
        r9 = r10;
        r9.setText(r3);
        r0 = r18;
        r11 = r0.mShownAgain;
        if (r11 != 0) goto L_0x0061;
    L_0x0044:
        r4 = 2131690623; // 0x7f0f047f float:1.9010295E38 double:1.0531951044E-314;
        r0 = r21;
        r21 = r0.findViewById(r4);
        r12 = r21;
        r12 = (android.widget.TextView) r12;
        r9 = r12;
        if (r19 == 0) goto L_0x0147;
    L_0x0054:
        r13 = 2661; // 0xa65 float:3.729E-42 double:1.3147E-320;
    L_0x0056:
        r3 = com.waze.strings.DisplayStrings.displayString(r13);
        r9.setText(r3);
        r4 = 0;
        r9.setVisibility(r4);
    L_0x0061:
        r14 = com.waze.AppService.getMainActivity();
        r15 = r14.getLayoutMgr();
        r15.navResultsLayoutChanged();
        return;
    L_0x006d:
        r3 = "";
        goto L_0x0012;
    L_0x0070:
        r4 = 2;
        if (r1 != r4) goto L_0x00b1;
    L_0x0073:
        r4 = 3;
        r5 = new java.lang.Object[r4];
        goto L_0x007a;
    L_0x0077:
        goto L_0x002e;
    L_0x007a:
        r4 = 0;
        r0 = r20;
        r16 = r0.getRide(r4);
        r0 = r16;
        r6 = r0.getRider();
        r7 = r6.getFirstName();
        r4 = 0;
        r5[r4] = r7;
        goto L_0x0092;
    L_0x008f:
        goto L_0x002e;
    L_0x0092:
        r4 = 1;
        r0 = r20;
        r16 = r0.getRide(r4);
        r0 = r16;
        r6 = r0.getRider();
        r7 = r6.getFirstName();
        r4 = 1;
        r5[r4] = r7;
        r4 = 2;
        r5[r4] = r3;
        r4 = 2668; // 0xa6c float:3.739E-42 double:1.318E-320;
        r3 = com.waze.strings.DisplayStrings.displayStringF(r4, r5);
        goto L_0x002e;
    L_0x00b1:
        r4 = 2;
        r5 = new java.lang.Object[r4];
        r17 = java.lang.Integer.valueOf(r1);
        r4 = 0;
        r5[r4] = r17;
        r4 = 1;
        r5[r4] = r3;
        r4 = 2669; // 0xa6d float:3.74E-42 double:1.3187E-320;
        r3 = com.waze.strings.DisplayStrings.displayStringF(r4, r5);
        goto L_0x0077;
    L_0x00c5:
        r0 = r20;
        r2 = r0.getDropOffLocation();
        if (r2 == 0) goto L_0x00ec;
    L_0x00cd:
        r3 = r2.address;
    L_0x00cf:
        r4 = 1;
        if (r1 != r4) goto L_0x00ef;
    L_0x00d2:
        r4 = 2;
        r5 = new java.lang.Object[r4];
        r0 = r20;
        r6 = r0.getRider();
        r7 = r6.getFirstName();
        r4 = 0;
        r5[r4] = r7;
        r4 = 1;
        r5[r4] = r3;
        r4 = 2664; // 0xa68 float:3.733E-42 double:1.316E-320;
        r3 = com.waze.strings.DisplayStrings.displayStringF(r4, r5);
        goto L_0x008f;
    L_0x00ec:
        r3 = "";
        goto L_0x00cf;
    L_0x00ef:
        r4 = 2;
        if (r1 != r4) goto L_0x012b;
    L_0x00f2:
        r4 = 3;
        r5 = new java.lang.Object[r4];
        r4 = 0;
        r0 = r20;
        r16 = r0.getRide(r4);
        r0 = r16;
        r6 = r0.getRider();
        r7 = r6.getFirstName();
        r4 = 0;
        r5[r4] = r7;
        r4 = 1;
        r0 = r20;
        r16 = r0.getRide(r4);
        r0 = r16;
        r6 = r0.getRider();
        r7 = r6.getFirstName();
        r4 = 1;
        r5[r4] = r7;
        r4 = 2;
        r5[r4] = r3;
        goto L_0x0124;
    L_0x0121:
        goto L_0x002e;
    L_0x0124:
        r4 = 2665; // 0xa69 float:3.734E-42 double:1.3167E-320;
        r3 = com.waze.strings.DisplayStrings.displayStringF(r4, r5);
        goto L_0x0121;
    L_0x012b:
        r4 = 2;
        r5 = new java.lang.Object[r4];
        r17 = java.lang.Integer.valueOf(r1);
        r4 = 0;
        r5[r4] = r17;
        r4 = 1;
        r5[r4] = r3;
        goto L_0x013c;
    L_0x0139:
        goto L_0x002e;
    L_0x013c:
        r4 = 2666; // 0xa6a float:3.736E-42 double:1.317E-320;
        r3 = com.waze.strings.DisplayStrings.displayStringF(r4, r5);
        goto L_0x0139;
        goto L_0x0147;
    L_0x0144:
        goto L_0x0056;
    L_0x0147:
        r13 = 2662; // 0xa66 float:3.73E-42 double:1.315E-320;
        goto L_0x0144;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.NavResultsFragment.setCarpoolAddressStrings(boolean, com.waze.carpool.CarpoolDrive, android.view.View):void");
    }

    private void setFragmentData(android.view.View r36) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:55:0x0293
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
        r35 = this;
        r7 = com.waze.AppService.getMainActivity();
        if (r7 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = r35;
        r8 = r0.mProg;
        if (r8 != 0) goto L_0x001d;
    L_0x000d:
        r7 = com.waze.AppService.getMainActivity();
        r9 = r7.getLayoutMgr();
        r8 = r9.getNavProgress();
        r0 = r35;
        r0.mProg = r8;
    L_0x001d:
        r0 = r35;
        r10 = r0.mShownAgain;
        if (r10 == 0) goto L_0x0247;
    L_0x0023:
        r0 = r35;
        r8 = r0.mProg;
        r11 = r8.getParent();
        r13 = r11;
        r13 = (android.view.View) r13;
        r12 = r13;
        r14 = 8;
        r12.setVisibility(r14);
    L_0x0034:
        if (r36 == 0) goto L_0x0301;
    L_0x0036:
        r15 = com.waze.NativeManager.getInstance();
        com.waze.carpool.CarpoolNativeManager.getInstance();
        r16 = com.waze.navigate.DriveToNativeManager.getInstance();
        r0 = r16;
        r10 = r0.isDayMode();
        r0 = r35;
        r17 = r0.getResources();
        if (r10 == 0) goto L_0x0297;
    L_0x004f:
        r18 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
    L_0x0052:
        r0 = r17;
        r1 = r18;
        r18 = r0.getColor(r1);
        r0 = r36;
        r1 = r18;
        r0.setBackgroundColor(r1);
        if (r10 == 0) goto L_0x029f;
    L_0x0063:
        r18 = 2131623958; // 0x7f0e0016 float:1.8875082E38 double:1.0531621675E-314;
    L_0x0066:
        r0 = r17;
        r1 = r18;
        r18 = r0.getColor(r1);
        if (r10 == 0) goto L_0x02ab;
    L_0x0070:
        r19 = 2131623948; // 0x7f0e000c float:1.8875062E38 double:1.0531621626E-314;
    L_0x0073:
        r0 = r17;
        r1 = r19;
        r19 = r0.getColor(r1);
        r14 = 2131690593; // 0x7f0f0461 float:1.9010234E38 double:1.0531950896E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r21 = r12;
        r21 = (android.widget.TextView) r21;
        r20 = r21;
        r0 = r20;
        r1 = r18;
        r0.setTextColor(r1);
        r14 = 2131690596; // 0x7f0f0464 float:1.901024E38 double:1.053195091E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r22 = r12;
        r22 = (android.widget.TextView) r22;
        r20 = r22;
        r0 = r20;
        r1 = r19;
        r0.setTextColor(r1);
        r14 = 2131690600; // 0x7f0f0468 float:1.9010248E38 double:1.053195093E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r23 = r12;
        r23 = (android.widget.TextView) r23;
        r20 = r23;
        r0 = r20;
        r1 = r18;
        r0.setTextColor(r1);
        r14 = 2131690602; // 0x7f0f046a float:1.9010252E38 double:1.053195094E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r24 = r12;
        r24 = (android.widget.TextView) r24;
        r20 = r24;
        r0 = r20;
        r1 = r18;
        r0.setTextColor(r1);
        r14 = 2131690604; // 0x7f0f046c float:1.9010256E38 double:1.053195095E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r25 = r12;
        r25 = (android.widget.TextView) r25;
        r20 = r25;
        r0 = r20;
        r1 = r19;
        r0.setTextColor(r1);
        r14 = 2131690606; // 0x7f0f046e float:1.901026E38 double:1.053195096E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r26 = r12;
        r26 = (android.widget.TextView) r26;
        r20 = r26;
        r0 = r20;
        r1 = r19;
        r0.setTextColor(r1);
        r14 = 2131690609; // 0x7f0f0471 float:1.9010266E38 double:1.0531950975E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r27 = r12;
        r27 = (android.widget.TextView) r27;
        r20 = r27;
        r0 = r20;
        r1 = r18;
        r0.setTextColor(r1);
        r14 = 2131690617; // 0x7f0f0479 float:1.9010283E38 double:1.0531951014E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r28 = r12;
        r28 = (android.widget.TextView) r28;
        r20 = r28;
        r0 = r20;
        r1 = r18;
        r0.setTextColor(r1);
        if (r10 == 0) goto L_0x02b3;
    L_0x012d:
        r18 = -3026479; // 0xffffffffffd1d1d1 float:NaN double:NaN;
    L_0x0130:
        r14 = 2131690598; // 0x7f0f0466 float:1.9010244E38 double:1.053195092E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r0 = r18;
        r12.setBackgroundColor(r0);
        r14 = 2131690607; // 0x7f0f046f float:1.9010262E38 double:1.0531950965E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r0 = r18;
        r12.setBackgroundColor(r0);
        r14 = 2131690610; // 0x7f0f0472 float:1.9010268E38 double:1.053195098E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r0 = r18;
        r12.setBackgroundColor(r0);
        r14 = 2131690618; // 0x7f0f047a float:1.9010285E38 double:1.053195102E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r0 = r18;
        r12.setBackgroundColor(r0);
        r14 = 2131690621; // 0x7f0f047d float:1.901029E38 double:1.0531951034E-314;
        r0 = r36;
        r12 = r0.findViewById(r14);
        r0 = r18;
        r12.setBackgroundColor(r0);
        r0 = r35;
        r0 = r0.mIsWaypoint;
        r29 = r0;
        if (r29 == 0) goto L_0x02be;
    L_0x017e:
        r0 = r35;
        r0 = r0.mNoteID;
        r18 = r0;
        r14 = 1;
        r0 = r18;
        if (r0 == r14) goto L_0x02bb;
    L_0x0189:
        r0 = r35;
        r0 = r0.mWaypointLength;
        r18 = r0;
        r18 = r18 / 60;
    L_0x0191:
        r0 = r35;
        r0 = r0.mWaypointDistance;
        r30 = r0;
        r0 = r35;
        r0 = r0.mWaypointDistanceUnit;
        r31 = r0;
    L_0x019d:
        r0 = r35;
        r1 = r36;
        r2 = r30;
        r3 = r31;
        r4 = r18;
        r0.setEtaAndDistance(r1, r2, r3, r4);
        r0 = r35;
        r0 = r0.mIsWaypoint;
        r29 = r0;
        if (r29 == 0) goto L_0x02e9;
    L_0x01b2:
        r0 = r35;
        r0 = r0.mWaypointTitle;
        r30 = r0;
    L_0x01b8:
        r0 = r35;
        r0 = r0.mVia;
        r31 = r0;
        r0 = r35;
        r0 = r0.mIsViaToll;
        r29 = r0;
        r0 = r35;
        r0 = r0.mIsViaFerry;
        r32 = r0;
        r0 = r35;
        r1 = r36;
        r2 = r30;
        r3 = r31;
        r4 = r29;
        r5 = r32;
        r0.setDestinationAndRoute(r1, r2, r3, r4, r5);
        r0 = r35;
        r1 = r36;
        r0.setEventsOnRoute(r1);
        r0 = r35;
        r0 = r0.mIsWaypoint;
        r29 = r0;
        r0 = r35;
        r0 = r0.mFinalTitle;
        r30 = r0;
        r0 = r35;
        r0 = r0.mNoteID;
        r18 = r0;
        r14 = 1;
        r0 = r18;
        if (r0 == r14) goto L_0x02f0;
    L_0x01f7:
        r0 = r35;
        r0 = r0.mRouteLength;
        r18 = r0;
        r18 = r18 / 60;
    L_0x01ff:
        r0 = r35;
        r1 = r36;
        r2 = r29;
        r3 = r30;
        r4 = r18;
        r5 = r15;
        r6 = r10;
        r0.setFinalDestination(r1, r2, r3, r4, r5, r6);
        r0 = r35;
        r0 = r0.mShownAgain;
        r29 = r0;
        r0 = r35;
        r1 = r36;
        r2 = r29;
        r0.setButtonsArea(r1, r15, r2, r10);
        r0 = r35;
        r0 = r0.mTimeOut;
        r18 = r0;
        r14 = -1;
        r0 = r18;
        if (r0 == r14) goto L_0x023a;
    L_0x0228:
        r0 = r35;
        r10 = r0.mShownAgain;
        if (r10 != 0) goto L_0x023a;
    L_0x022e:
        r0 = r35;
        r10 = r0.mCalculating;
        if (r10 != 0) goto L_0x023a;
    L_0x0234:
        r0 = r35;
        r10 = r0.mViaDangerZone;
        if (r10 == 0) goto L_0x02f3;
    L_0x023a:
        r0 = r35;
        r1 = r36;
        r0.stopTimer(r1);
    L_0x0241:
        r14 = 1;
        r0 = r35;
        r0.mSetUp = r14;
        return;
    L_0x0247:
        r0 = r35;
        r10 = r0.mCalculating;
        if (r10 == 0) goto L_0x026b;
    L_0x024d:
        r0 = r35;
        r8 = r0.mProg;
        r11 = r8.getParent();
        r33 = r11;
        r33 = (android.view.View) r33;
        r12 = r33;
        r14 = 0;
        r12.setVisibility(r14);
        r0 = r35;
        r8 = r0.mProg;
        goto L_0x0267;
    L_0x0264:
        goto L_0x0034;
    L_0x0267:
        r8.start();
        goto L_0x0264;
    L_0x026b:
        r0 = r35;
        r8 = r0.mProg;
        r8.stop();
        r14 = 0;
        r0 = r35;
        r0.mDidGetEventsOnRoute = r14;
        r0 = r35;
        r8 = r0.mProg;
        goto L_0x027f;
    L_0x027c:
        goto L_0x01ff;
    L_0x027f:
        r11 = r8.getParent();
        r34 = r11;
        r34 = (android.view.View) r34;
        r12 = r34;
        goto L_0x028d;
    L_0x028a:
        goto L_0x0034;
    L_0x028d:
        r14 = 8;
        r12.setVisibility(r14);
        goto L_0x028a;
        goto L_0x0297;
    L_0x0294:
        goto L_0x0052;
    L_0x0297:
        r18 = 2131623950; // 0x7f0e000e float:1.8875066E38 double:1.0531621635E-314;
        goto L_0x0294;
        goto L_0x029f;
    L_0x029c:
        goto L_0x0066;
    L_0x029f:
        r18 = 2131623960; // 0x7f0e0018 float:1.8875086E38 double:1.0531621685E-314;
        goto L_0x02a6;
    L_0x02a3:
        goto L_0x0241;
    L_0x02a6:
        goto L_0x029c;
        goto L_0x02ab;
    L_0x02a8:
        goto L_0x0073;
    L_0x02ab:
        r19 = 2131623951; // 0x7f0e000f float:1.8875068E38 double:1.053162164E-314;
        goto L_0x02a8;
        goto L_0x02b3;
    L_0x02b0:
        goto L_0x0130;
    L_0x02b3:
        r18 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        goto L_0x02b0;
        goto L_0x02bb;
    L_0x02b8:
        goto L_0x0191;
    L_0x02bb:
        r18 = 0;
        goto L_0x02b8;
    L_0x02be:
        r0 = r35;
        r0 = r0.mNoteID;
        r18 = r0;
        r14 = 1;
        r0 = r18;
        if (r0 == r14) goto L_0x02e2;
    L_0x02c9:
        r0 = r35;
        r0 = r0.mRouteLength;
        r18 = r0;
        r18 = r18 / 60;
    L_0x02d1:
        r0 = r35;
        r0 = r0.mDistance;
        r30 = r0;
        goto L_0x02db;
    L_0x02d8:
        goto L_0x019d;
    L_0x02db:
        r0 = r35;
        r0 = r0.mDistanceUnit;
        r31 = r0;
        goto L_0x02d8;
    L_0x02e2:
        r18 = 0;
        goto L_0x02d1;
        goto L_0x02e9;
    L_0x02e6:
        goto L_0x01b8;
    L_0x02e9:
        r0 = r35;
        r0 = r0.mFinalTitle;
        r30 = r0;
        goto L_0x02e6;
    L_0x02f0:
        r18 = 0;
        goto L_0x027c;
    L_0x02f3:
        r0 = r35;
        r0 = r0.mTimeOut;
        r18 = r0;
        r0 = r35;
        r1 = r18;
        r0.startTimer(r1);
        goto L_0x02a3;
    L_0x0301:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.NavResultsFragment.setFragmentData(android.view.View):void");
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle savedInstanceState) throws  {
        this.mProg = AppService.getMainActivity().getLayoutMgr().getNavProgress();
        View $r7 = $r1.inflate(C1283R.layout.fragment_nav_results, $r2, false);
        if (this.mSetUp) {
            return $r7;
        }
        setFragmentData($r7);
        return $r7;
    }

    public void onResume() throws  {
        super.onResume();
        NativeCanvasRenderer.OnMainCanvasOverlayShown();
        View $r1 = getView();
        if ($r1 != null) {
            setFragmentData($r1);
        }
    }

    public void onPause() throws  {
        super.onPause();
        NativeCanvasRenderer.OnMainCanvasOverlayHidden();
    }

    public void setData(String $r1, String goodMorning, String $r3, String $r4, String $r5, int $i0, String $r6, int $i1, boolean $z0, boolean $z1, String $r7, String $r8, int $i2, String $r9, String $r10, boolean $z2, int waypointDelay, boolean $z3, int $i4, boolean $z4, String UserPictureUrl, String Friend1PictureUrl, String Friend2PictureUrlfinal, int OnlineFriends, int FriendsDrivingCount, String $r14, String jConfigImage, String $r16, FriendUserData[] $r17, boolean $z5, boolean $z6, boolean $z7, boolean $z8, boolean $z9, boolean $z10) throws  {
        this.mClosing = false;
        this.mCalculating = $z6;
        this.mFriendsData = $r17;
        this.mNoteID = $i4;
        this.mNote = $r1;
        this.mTitle = $r3;
        this.mOfflineRoute = $z5;
        this.mDistance = $r4;
        this.mDistanceUnit = $r5;
        this.mVia = $r6;
        this.mRouteLength = $i0;
        this.mShowDisclaimer = $z0;
        this.mIsWaypoint = $z1;
        this.mWaypointDistance = $r7;
        this.mWaypointDistanceUnit = $r8;
        this.mWaypointLength = $i2;
        this.mWaypointTitle = $r9;
        this.mShowRoutes = $z10;
        if (!($r10 == null && $r3 == null && this.mSetUp)) {
            if ($r10 != null) {
                this.mFinalTitle = $r10;
            } else {
                this.mFinalTitle = $r3;
            }
        }
        this.mFinalIsParking = $z2;
        this.mIsTripServerResult = $z3;
        if ($z7) {
            $i0 = -1;
        } else {
            $i0 = $i1 / 1000;
        }
        this.mTimeOut = $i0;
        if (!this.mSetUp) {
            this.mShownAgain = $z4;
            this.mIsCarpoolDrive = $z9;
        }
        this.mIsViaFerry = $z8;
        $z0 = ($r14 == null || $r14.isEmpty()) ? false : true;
        this.mIsViaToll = $z0;
        this.mFreeText = $r16;
        this.mViaDangerZone = $z7;
        this.mSelected = new ArrayList();
        this.mAlreadyShared = null;
        if (this.mFriendsData != null) {
            FriendUserData[] $r172 = this.mFriendsData;
            this.mSuggestions = new ArrayList(Arrays.asList($r172));
        } else {
            this.mSuggestions = new ArrayList();
        }
        setFragmentData(getView());
        if (this.mShownAgain) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_BOTTOM_ETA_BOX_CLICKED);
        }
        if (this.mIsTripServerResult) {
            Analytics.log("NAVIGATE", AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, "TRIP_SRV");
        }
        if (this.mShowRoutes) {
            showRoutes();
        }
    }

    private void setEtaAndDistance(View $r1, String distance, String distanceUnits, int $i0) throws  {
        Calendar $r4 = Calendar.getInstance();
        TimeZone $r5 = $r4.getTimeZone();
        DateFormat $r7 = android.text.format.DateFormat.getTimeFormat($r1.getContext());
        $r7.setTimeZone($r5);
        $r4.add(12, $i0);
        $r7.format($r4.getTime());
    }

    private void setDestinationAndRoute(View $r1, String $r2, String $r3, boolean $z0, boolean $z1) throws  {
        byte $b1;
        boolean $z2 = true;
        byte $b0 = (byte) 0;
        final CarpoolNativeManager $r4 = CarpoolNativeManager.getInstance();
        String $r5 = $r4.getCurMeetingIdNTV();
        final View view;
        if (!this.mIsWaypoint && $r5 != null && !$r5.isEmpty()) {
            this.mIsCarpoolDrive = true;
            view = $r1;
            $r4.getDriveInfoByMeetingId($r5, new IResultObj<CarpoolDrive>() {
                public void onResult(final CarpoolDrive $r1) throws  {
                    $r4.isCurMeetingPickup(new IResultOk() {
                        public void onResult(boolean $z0) throws  {
                            if ($r1 == null || $r1.getRide() == null) {
                                Logger.m38e("NavResultFragment: setDestinationAndRoute: received null result from cpnm.isCurMeetingPickup");
                            } else {
                                NavResultsFragment.this.setCarpoolAddressStrings($z0, $r1, view);
                            }
                        }
                    });
                }
            });
        } else if (this.mIsWaypoint || !this.mIsCarpoolDrive) {
            ((TextView) $r1.findViewById(C1283R.id.fragNavResDestination)).setText(NativeManager.getInstance().getLanguageString($r2));
        } else {
            view = $r1;
            final String str = $r2;
            $r4.getLiveDrive(new IResultObj<CarpoolDrive>() {
                public void onResult(CarpoolDrive $r1) throws  {
                    if ($r1 == null || $r1.getRide() == null) {
                        ((TextView) view.findViewById(C1283R.id.fragNavResDestination)).setText(str);
                        AppService.getMainActivity().getLayoutMgr().navResultsLayoutChanged();
                        return;
                    }
                    NavResultsFragment.this.setCarpoolAddressStrings($r1.getRide().state != 8, $r1, view);
                }
            });
        }
        ((TextView) $r1.findViewById(C1283R.id.fragNavResViaText)).setText($r3);
        View $r7 = $r1.findViewById(C1283R.id.fragNavResViaToll);
        if ($z0) {
            $b1 = (byte) 0;
        } else {
            $b1 = (byte) 8;
        }
        $r7.setVisibility($b1);
        if (!($z1 && ConfigManager.getInstance().getConfigValueBool(130))) {
            $z2 = false;
        }
        $r1 = $r1.findViewById(C1283R.id.fragNavResViaFerry);
        if (!$z2) {
            $b0 = (byte) 8;
        }
        $r1.setVisibility($b0);
    }

    private void setEventsOnRoute(final View $r1) throws  {
        if ($r1.getMeasuredHeight() > 0) {
            getEventsOnRoute($r1);
            return;
        }
        final View $r3 = $r1.findViewById(C1283R.id.fragNavResEtaTrafficBar);
        $r3.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                $r3.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                NavResultsFragment.this.getEventsOnRoute($r1);
            }
        });
    }

    private void getEventsOnRoute(View $r1) throws  {
        final ETATrafficBar $r2 = (ETATrafficBar) $r1.findViewById(C1283R.id.fragNavResEtaTrafficBar);
        if (this.mCalculating) {
            $r2.clearEventsOnRoute();
        } else if (!this.mDidGetEventsOnRoute) {
            DriveToNativeManager.getInstance().getAlertsOnRoute(new EventsOnRouteListener() {
                public void onComplete(EventOnRoute[] $r1) throws  {
                    boolean $z0 = true;
                    if (!NavResultsFragment.this.mDidGetEventsOnRoute && NavResultsFragment.this.getView() != null) {
                        NavResultsFragment.this.mDidGetEventsOnRoute = true;
                        if (NavResultsFragment.this.mIsCarpoolDrive) {
                            final EventOnRoute[] eventOnRouteArr = $r1;
                            CarpoolNativeManager.getInstance().getLiveDrive(new IResultObj<CarpoolDrive>() {

                                class C12611 implements ImageRequestListener {
                                    C12611() throws  {
                                    }

                                    public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                                        boolean $z0 = false;
                                        $r2.addExtEvent(new CircleShaderDrawable($r1, 0), 100);
                                        ETATrafficBar $r5 = $r2;
                                        EventOnRoute[] $r7 = eventOnRouteArr;
                                        int $i1 = NavResultsFragment.this.mRouteLength;
                                        Handler $r9 = NavResultsFragment.this.mHandler;
                                        if (!NavResultsFragment.this.mShownAgain) {
                                            $z0 = true;
                                        }
                                        $r5.populateEventsOnRoute(null, $r7, $i1, $r9, $z0);
                                    }

                                    public void onImageLoadFailed(Object token, long duration) throws  {
                                        $r2.populateEventsOnRoute(null, eventOnRouteArr, NavResultsFragment.this.mRouteLength, NavResultsFragment.this.mHandler, !NavResultsFragment.this.mShownAgain);
                                    }
                                }

                                class C12622 extends ImageRepositoryListener {
                                    C12622() throws  {
                                    }

                                    public void onImageRetrieved(Bitmap $r1) throws  {
                                        boolean $z0 = false;
                                        NavResultsFragment.this.mMultiPaxBitmap = $r1;
                                        $r2.addExtEvent(new CircleShaderDrawable(NavResultsFragment.this.mMultiPaxBitmap, 0), 100);
                                        ETATrafficBar $r6 = $r2;
                                        EventOnRoute[] $r7 = eventOnRouteArr;
                                        int $i0 = NavResultsFragment.this.mRouteLength;
                                        Handler $r8 = NavResultsFragment.this.mHandler;
                                        if (!NavResultsFragment.this.mShownAgain) {
                                            $z0 = true;
                                        }
                                        $r6.populateEventsOnRoute(null, $r7, $i0, $r8, $z0);
                                    }
                                }

                                public void onResult(CarpoolDrive $r1) throws  {
                                    boolean $z0 = true;
                                    ETATrafficBar $r5;
                                    EventOnRoute[] $r6;
                                    int $i0;
                                    Handler $r8;
                                    if ($r1 == null || $r1.getRidesAmount() == 0 || !($r1.getRide().state == 10 || $r1.getRide().state == 16)) {
                                        $r5 = $r2;
                                        $r6 = eventOnRouteArr;
                                        $i0 = NavResultsFragment.this.mRouteLength;
                                        $r8 = NavResultsFragment.this.mHandler;
                                        if (NavResultsFragment.this.mShownAgain) {
                                            $z0 = false;
                                        }
                                        $r5.populateEventsOnRoute(null, $r6, $i0, $r8, $z0);
                                    } else if (!$r1.isMultiPax()) {
                                        VolleyManager.getInstance().loadImageFromUrl($r1.getRider().getImage(), new C12611(), null, PixelMeasure.dp(29), PixelMeasure.dp(29), null);
                                    } else if (NavResultsFragment.this.mMultiPaxBitmap == null) {
                                        new MultiContactsBitmap(new C12622(), NavResultsFragment.this.getResources(), C1283R.drawable.user_image_placeholder).buildBitmap($r1);
                                    } else {
                                        $r2.addExtEvent(new CircleShaderDrawable(NavResultsFragment.this.mMultiPaxBitmap, 0), 100);
                                        $r5 = $r2;
                                        $r6 = eventOnRouteArr;
                                        $i0 = NavResultsFragment.this.mRouteLength;
                                        $r8 = NavResultsFragment.this.mHandler;
                                        if (NavResultsFragment.this.mShownAgain) {
                                            $z0 = false;
                                        }
                                        $r5.populateEventsOnRoute(null, $r6, $i0, $r8, $z0);
                                    }
                                }
                            });
                            return;
                        }
                        ETATrafficBar $r6 = $r2;
                        int $i0 = NavResultsFragment.this.mRouteLength;
                        Handler $r7 = NavResultsFragment.this.mHandler;
                        if (NavResultsFragment.this.mShownAgain) {
                            $z0 = false;
                        }
                        $r6.populateEventsOnRoute(null, $r1, $i0, $r7, $z0);
                    }
                }
            });
        }
    }

    private void setFinalDestination(View $r1, boolean $z0, String $r2, int $i0, NativeManager $r3, boolean $z1) throws  {
        final View $r4 = $r1.findViewById(C1283R.id.fragNavResAddStopPointLayout);
        if ($z0) {
            $r4.setVisibility(8);
            $r1.findViewById(C1283R.id.fragNavResFinalDestLayout).setVisibility(0);
            ((TextView) $r1.findViewById(C1283R.id.fragNavResAndThen)).setText($r3.getLanguageString((int) DisplayStrings.DS_ETA_SCREEN_AND_THEN));
            ((TextView) $r1.findViewById(C1283R.id.fragNavResFinalDestination)).setText($r2);
            $r1.findViewById(C1283R.id.fragNavResAndThenParking).setVisibility(this.mFinalIsParking ? (byte) 0 : (byte) 8);
            Calendar $r12 = Calendar.getInstance();
            TimeZone $r13 = $r12.getTimeZone();
            DateFormat $r15 = android.text.format.DateFormat.getTimeFormat(getActivity());
            $r15.setTimeZone($r13);
            $r12.add(12, $i0);
            $r2 = $r15.format($r12.getTime());
            ((TextView) $r1.findViewById(C1283R.id.fragNavResEtaText)).setText($r3.getLanguageString(392));
            ((TextView) $r1.findViewById(C1283R.id.fragNavResEta)).setText($r2);
            return;
        }
        $r1.findViewById(C1283R.id.fragNavResFinalDestLayout).setVisibility(8);
        $r4.setVisibility(0);
        ((TextView) $r1.findViewById(C1283R.id.fragNavResAddStopPointText)).setText($r3.getLanguageString((int) DisplayStrings.DS_ETA_STOP_SUGGESTION));
        $r4.setOnClickListener(new C12655());
        Resources $r8 = $r1.getResources();
        if ($z1) {
            int $i1 = $r8.getColor(C1283R.color.BlueWhaleLight);
            $i0 = Color.argb(38, Color.red($i1), Color.green($i1), Color.blue($i1));
        } else {
            $i0 = $r8.getColor(C1283R.color.DarkShade);
        }
        AnimationUtils.viewBgInit($r4, $i0, $r8.getColor(C1283R.color.blue_bg), $r8.getColor(C1283R.color.BlueGrey));
        $r4.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                $r4.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                NavResultsFragment.this.attemptToShowStopTooltip($r4);
            }
        });
    }

    private void showRoutes() throws  {
        if (!AppService.isNetworkAvailable()) {
            MsgBox.openMessageBox(DisplayStrings.displayString(386), DisplayStrings.displayString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
        } else if (!this.mCalculating) {
            this.mShowRoutes = false;
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ETA_CLICK, "ACTION", "ROUTES");
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ROUTES_FROM_ETA);
            DriveToNativeManager.getInstance().requestRoute(this.mIsTripServerResult);
            NativeManager.Post(new C12677());
            dismissMe(false);
            NativeManager.getInstance().getNavBarManager().showNavBar();
        }
    }

    private void setCarpoolRiderInShare(CarpoolDrive $r1, boolean $z0, View $r2, View $r3) throws  {
        if ($r1 != null) {
            String $r4;
            if ($r1.isMultiPax()) {
                setShareButtonContentMPAX(true, $r1);
                $r4 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ETA_AUTOMATICALLY_SENT_TO_RIDERS);
            } else {
                String $r11 = $r1.getRider().getImage();
                setShareButtonContent(true, $r1.getRider().getFirstName(), $r11);
                $r4 = String.format(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CARPOOL_ETA_AUTOMATICALLY_SENT_TO_PS), new Object[]{$r4});
            }
            this.mShareUserFrame.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    NavResultsFragment.this.showCarpoolTooltip(NavResultsFragment.this.mShareUserFrame, $r4);
                }
            });
            this.mShareImage.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    NavResultsFragment.this.showCarpoolTooltip(NavResultsFragment.this.mShareImage, $r4);
                }
            });
            if (!$z0) {
                this.mShareUserFrame.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() throws  {
                        NavResultsFragment.this.mShareUserFrame.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        NavResultsFragment.this.showCarpoolTooltip(NavResultsFragment.this.mShareUserFrame, $r4);
                    }
                });
                return;
            }
            return;
        }
        $r2.setVisibility(8);
        $r3.setVisibility(8);
    }

    private void showCarpoolTooltip(View $r1, String $r2) throws  {
        if (this.mCarpoolTooltip != null && this.mCarpoolTooltip.isShowing()) {
            this.mCarpoolTooltip.dismiss(false);
        }
        Activity $r5 = getActivity();
        if ($r5 != null) {
            this.mCarpoolTooltip = new TinyTooltip($r5, $r2);
            this.mCarpoolTooltip.setAnimations(C1283R.anim.contact_tooltip_show, C1283R.anim.contact_tooltip_hide);
            this.mCarpoolTooltip.show($r1);
            final TinyTooltip $r4 = this.mCarpoolTooltip;
            $r1.postDelayed(new Runnable() {
                public void run() throws  {
                    if (NavResultsFragment.this.mCarpoolTooltip != null && $r4 == NavResultsFragment.this.mCarpoolTooltip && NavResultsFragment.this.mCarpoolTooltip.isShowing()) {
                        NavResultsFragment.this.mCarpoolTooltip.dismiss(true);
                        NavResultsFragment.this.mCarpoolTooltip = null;
                    }
                }
            }, 3000);
        }
    }

    private void checkAndOpenAddFriends() throws  {
        if (!MyWazeNativeManager.getInstance().getContactLoggedInNTV()) {
            Intent $r2 = new Intent(getActivity(), PhoneRegisterActivity.class);
            $r2.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
            $r2.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
            AppService.getMainActivity().startActivityForResult($r2, MainActivity.POI_POPUP_INFO_REQUEST_CODE);
        } else if (NativeManager.getInstance().IsAccessToContactsEnableNTV()) {
            this.mGoOnReturnFromAddMore = false;
            openAddFromActivity();
        } else {
            new PhoneRequestAccessDialog(getActivity(), new PhoneRequestAccessResultListener() {
                public void onResult(boolean $z0) throws  {
                    if ($z0) {
                        NavResultsFragment.this.mGoOnReturnFromAddMore = false;
                        NavResultsFragment.this.openAddFromActivity();
                    }
                }
            }).show();
        }
    }

    private void populateShareButton(final View $r1, final boolean $z0, final PersonBase $r2, boolean $z1) throws  {
        if (isAdded() && isVisible()) {
            setShareButtonContent($z1, $r2.getName(), $r2.getImage());
            if (!$z1) {
                this.mShareUserFrame.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        if (NavResultsFragment.this.mSelected.isEmpty()) {
                            NavResultsFragment.this.mSelected.add($r2);
                        } else {
                            NavResultsFragment.this.mSelected.clear();
                        }
                        NavResultsFragment.this.stopTimer($r1);
                        NavResultsFragment.this.setShareButtonState(false);
                        NavResultsFragment.this.setGoButton($r1, $z0);
                    }
                });
            }
        }
    }

    private void setShareButtonContent(boolean $z0, String $r1, String $r2) throws  {
        this.mShareUserImage.setVisibility(0);
        this.mShareUserInitials.setVisibility(0);
        this.mShareUserFrame.setVisibility(0);
        this.mShareUserIcon.setVisibility(0);
        this.mShareUserInitials.setText(ShareUtility.getInitials($r1));
        this.mShareUserImage.setImageResource(C1283R.drawable.user_image_placeholder);
        ImageRepository.instance.getImage($r2, 3, this.mShareUserImage, this.mShareUserInitials, (ActivityBase) getActivity());
        setShareButtonState($z0);
    }

    private void setShareButtonContentMPAX(boolean $z0, CarpoolDrive $r1) throws  {
        this.mShareUserImage.setVisibility(0);
        this.mShareUserInitials.setVisibility(0);
        this.mShareUserFrame.setVisibility(0);
        this.mShareUserIcon.setVisibility(0);
        this.mShareUserInitials.setVisibility(8);
        this.mShareUserImage.setImageResource(C1283R.drawable.user_image_placeholder);
        if (this.mMultiPaxBitmap == null) {
            new MultiContactsBitmap(new ImageRepositoryListener() {
                public void onImageRetrieved(Bitmap $r1) throws  {
                    if ($r1 != null) {
                        NavResultsFragment.this.mMultiPaxBitmap = $r1;
                        NavResultsFragment.this.mShareUserImage.setImageDrawable(new CircleShaderDrawable(NavResultsFragment.this.mMultiPaxBitmap, 0));
                    }
                }
            }, getResources(), C1283R.drawable.user_image_placeholder).buildBitmap($r1);
        } else {
            this.mShareUserImage.setImageDrawable(new CircleShaderDrawable(this.mMultiPaxBitmap, 0));
        }
        setShareButtonState($z0);
    }

    public void openAddFromActivity() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        Intent $r1 = new Intent(getActivity(), AddFromActivity.class);
        $r1.putExtra(AddFromActivity.INTENT_FROM_WHERE, AddFromActivity.INTENT_FROM_SHARE);
        $r1.putExtra(AddFromActivity.INTENT_SELECTED, this.mSelected);
        if (this.mAlreadyShared == null) {
            $r1.putExtra(AddFromActivity.INTENT_SUGGESTED, this.mSuggestions);
        }
        $r1.putExtra("type", 1);
        $r2.startActivityForResult($r1, MainActivity.REQUEST_ADD_DRIVE_SHARE);
    }

    public void updateShare(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ArrayList<PersonBase> $r1) throws  {
        this.mSelected = $r1;
        if (this.mSelected == null) {
            this.mSelected = new ArrayList();
        }
        if (!this.mSelected.isEmpty()) {
            populateShareButton(getView(), this.mShownAgain, (PersonBase) this.mSelected.get(0), false);
            setGoButton(getView(), this.mShownAgain);
            if (this.mGoOnReturnFromAddMore) {
                getView().findViewById(C1283R.id.fragNavResGo).performClick();
            }
        }
    }

    private void setGoButton(View $r1, boolean $z0) throws  {
        NativeManager $r2 = NativeManager.getInstance();
        View $r3 = $r1.findViewById(C1283R.id.fragNavResGo);
        TimerBar $r5 = (TimerBar) $r1.findViewById(C1283R.id.fragNavResGoTimer);
        TextView $r6 = (TextView) $r1.findViewById(C1283R.id.fragNavResGoText);
        ImageView $r7 = (ImageView) $r1.findViewById(C1283R.id.fragNavResGoImage);
        if (this.mIsWaypoint || !this.mIsCarpoolDrive || $z0) {
            $r3.setBackgroundResource(C1283R.drawable.button_blue_bg);
            $r5.setTrackColor(getResources().getColor(C1283R.color.BlueDeepLight));
            $r5.setTimeLeftColor(getResources().getColor(C1283R.color.BlueDeep));
            $r7.setImageResource(C1283R.drawable.eta_go);
            if (this.mSelected != null && !this.mSelected.isEmpty()) {
                $r6.setText($r2.getLanguageString((int) DisplayStrings.DS_ETA_SCREEN_SHARE_N_GO));
                $r7.setVisibility(0);
                $r7.setImageResource(C1283R.drawable.eta_send_and_go);
                return;
            } else if ($z0) {
                $r6.setText($r2.getLanguageString(354));
                $r7.setVisibility(8);
                return;
            } else {
                $r6.setText($r2.getLanguageString((int) DisplayStrings.DS_ETA_SCREEN_GO_NOW));
                $r7.setVisibility(0);
                $r7.setImageResource(C1283R.drawable.eta_go);
                return;
            }
        }
        $r3.setBackgroundResource(C1283R.drawable.button_green_bg);
        $r5.setTrackColor(-14968486);
        $r5.setTimeLeftColor(-15498934);
        $r7.setImageResource(C1283R.drawable.go_green_icon);
    }

    private void setShareButtonState(boolean $z0) throws  {
        if (this.mSelected == null || !this.mSelected.isEmpty() || $z0) {
            this.mShareUserImage.clearAnimation();
            this.mShareUserInitials.clearAnimation();
            this.mShareUserFrame.clearAnimation();
            this.mShareUserIcon.setImageResource(C1283R.drawable.eta_send_confirmed);
            this.mShareImage.setImageResource(C1283R.drawable.eta_send_plus);
            return;
        }
        AlphaAnimation $r2 = new AlphaAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r2.setFillAfter(true);
        this.mShareUserImage.startAnimation($r2);
        $r2 = new AlphaAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r2.setFillAfter(true);
        this.mShareUserInitials.startAnimation($r2);
        $r2 = new AlphaAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r2.setFillAfter(true);
        this.mShareUserFrame.startAnimation($r2);
        this.mShareUserIcon.setImageResource(C1283R.drawable.eta_send_add);
        this.mShareImage.setImageResource(C1283R.drawable.eta_send);
    }

    public void updateData() throws  {
        setFragmentData(getView());
    }

    public void onBackPressed() throws  {
        if (this.mShownAgain) {
            dismissMe(false);
        } else {
            cancelNavigation();
        }
    }

    public void cancelNavigation() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CANCEL_FROM_ETA);
        Analytics.logAdsContextNav(AnalyticsEvents.ANALYTICS_EVENT_ADS_NAVIGATE_CANCEL_ETA);
        if (this.mIsTripServerResult) {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    AppService.getNativeManager().StopFollow();
                    AppService.getNativeManager().stopTripServerNavigationNTV();
                }
            });
        } else {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    AppService.getNativeManager().StopFollow();
                    AppService.getNativeManager().stopNavigationNTV();
                }
            });
        }
        dismissMe(false);
    }

    void stopTimer(View $r1) throws  {
        if ($r1 != null) {
            ((TimerBar) $r1.findViewById(C1283R.id.fragNavResGoTimer)).stop();
        }
    }

    private void startTimer(int $i0) throws  {
        if (getView() != null) {
            TimerBar $r2 = (TimerBar) getView().findViewById(C1283R.id.fragNavResGoTimer);
            $r2.reset();
            $r2.setTime($i0);
            $r2.start();
        }
    }

    public void dismissMe(boolean $z0) throws  {
        if (isAdded()) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ETA_CLICK, "ACTION", "CLOSE");
            clear();
            ((MainActivity) getActivity()).dismissNavResultsDialog($z0);
            if (!(this.mShownAgain || this.mFreeText == null || this.mFreeText.isEmpty() || LayoutManager.isDueToPopupShown)) {
                AppService.getMainActivity().getLayoutMgr().showDueToPopup(this.mFreeText, this.mIsTripServerResult);
            }
            if (this.mCarpoolTooltip != null && this.mCarpoolTooltip.isShowing()) {
                this.mCarpoolTooltip.dismiss(false);
                this.mCarpoolTooltip = null;
            }
            if (this.mAddStopTooltip != null) {
                this.mAddStopTooltip.setVisibility(8);
                this.mAddStopTooltip = null;
            }
        }
    }

    public void clear() throws  {
        this.mClosing = true;
        this.mDidGetEventsOnRoute = false;
        this.mSetUp = false;
        stopTimer(getView());
        ((View) this.mProg.getParent()).setVisibility(8);
    }

    public boolean isShownFirstTime() throws  {
        return (!isVisible() || this.mShownAgain || this.mClosing) ? false : true;
    }

    public void updateData(int $i0, String $r1, String $r2, boolean $z0) throws  {
        View $r3;
        String $r22;
        if ($z0) {
            if ($r1 != null) {
                this.mWaypointDistance = $r1;
                this.mWaypointDistanceUnit = $r2;
            }
            if ($i0 > 0) {
                this.mWaypointLength = $i0 * 60;
            } else {
                $i0 = this.mWaypointLength / 60;
            }
            if (this.mIsWaypoint) {
                $r3 = getView();
                $r1 = this.mWaypointDistance;
                $r22 = this.mWaypointDistanceUnit;
                setEtaAndDistance($r3, $r1, $r22, $i0);
            }
        } else {
            if ($r1 != null) {
                this.mDistance = $r1;
                this.mDistanceUnit = $r2;
            }
            if ($i0 > 0) {
                this.mRouteLength = $i0 * 60;
            } else {
                $i0 = this.mRouteLength / 60;
            }
            if (this.mIsWaypoint) {
                $r3 = getView();
                $z0 = this.mIsWaypoint;
                $r1 = this.mFinalTitle;
                if (this.mNoteID == 1) {
                    $i0 = 0;
                }
                setFinalDestination($r3, $z0, $r1, $i0, NativeManager.getInstance(), DriveToNativeManager.getInstance().isDayMode());
            } else {
                $r3 = getView();
                $r1 = this.mDistance;
                $r22 = this.mDistanceUnit;
                setEtaAndDistance($r3, $r1, $r22, $i0);
            }
        }
        setEventsOnRoute(getView());
    }

    public void onOrientationChanged(int orientation) throws  {
        this.mDidGetEventsOnRoute = false;
        setFragmentData(getView());
        if (this.mAddStopTooltip != null) {
            this.mAddStopTooltip.setVisibility(8);
            this.mAddStopTooltip = null;
        }
    }

    public void pauseCounter() throws  {
        stopTimer(getView());
    }

    public void resumeCounter() throws  {
        startTimer(this.mTimeOut);
        this.mPickupOfferDialog = null;
    }

    public void showCandidateRideForRoute(CarpoolDrive $r1) throws  {
        stopTimer(getView());
        this.mPickupOfferDialog = new PickupOfferDialog((ActivityBase) getActivity(), $r1, new Runnable() {
            public void run() throws  {
                NavResultsFragment.this.resumeCounter();
            }
        });
        this.mPickupOfferDialog.show();
    }

    void sendMeeting() throws  {
        stopTimer(getView());
        if (this.mSelected != null && !this.mSelected.isEmpty()) {
            ArrayList<PersonBase> $r6 = this.mSelected;
            int[] $r1 = new int[$r6.size()];
            String[] $r2 = new String[$r6.size()];
            int[] $r3 = new int[$r6.size()];
            String[] $r4 = new String[$r6.size()];
            int $i0 = 0;
            int $i1 = 0;
            for (PersonBase personBase : $r6) {
                if (!personBase.getIsOnWaze() || personBase.getID() < 0) {
                    if (!(personBase instanceof FriendUserData) || personBase.getID() >= 0) {
                        $r2[$i1] = personBase.getPhone();
                        $r3[$i1] = personBase.getID();
                    } else {
                        FriendUserData $r11 = (FriendUserData) personBase;
                        PersonBase $r13 = AddressBookImpl.getInstance().GetPersonFromID($r11.mContactID);
                        $r2[$i1] = $r13.getPhone();
                        $r3[$i1] = $r13.getID();
                    }
                    $i1++;
                } else {
                    $r4[$i0] = personBase.getPhone();
                    $r1[$i0] = personBase.getID();
                    $i0++;
                }
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_SENT, null, null);
            String $r10 = MyWazeNativeManager.getInstance().GetLastShareURL();
            if (!NativeManager.getInstance().isFollowActiveNTV() || $r10 == null || $r10.equals("")) {
                final int[] iArr = $r1;
                final String[] strArr = $r2;
                final int[] iArr2 = $r3;
                final int i = $i0;
                final int i2 = $i1;
                final String[] strArr2 = $r4;
                DriveToNativeManager.getInstance().getLocationData(1, Integer.valueOf(0), Integer.valueOf(0), new LocationDataListener() {
                    public void onComplete(LocationData $r1) throws  {
                        NativeManager.getInstance().CreateMeetingBulk($r1.locationName, "ShareDrive", $r1.locationX, $r1.locationY, iArr, strArr, iArr2, i, i2, true, strArr2, $r1.mStreet, $r1.mCity, null, true, $r1.mVenueId);
                    }
                }, null);
                return;
            }
            if ($i0 > 0) {
                NativeManager.getInstance().AddToMeeting($r1, $i0, $r4, false);
            }
            if ($i1 > 0) {
                NativeManager.getInstance().InviteToMeeting($r2, $r3, $i1, 4);
            }
        }
    }

    private void attemptToShowStopTooltip(final View $r1) throws  {
        if (!this.mTooltipWasHandled) {
            this.mTooltipWasHandled = true;
            int $i0 = ConfigManager.getInstance().getConfigValueInt(407);
            if ($i0 > 0) {
                ConfigManager.getInstance().setConfigValueIntNTV(407, $i0 - 1);
            } else if ($i0 == 0) {
                AppService.getActiveActivity().postDelayed(new Runnable() {
                    public void run() throws  {
                        if (NavResultsFragment.this.isVisible() && !AppService.getMainActivity().getLayoutMgr().isAnyMenuOpen()) {
                            NavResultsFragment.this.mTooltipWasHandled = true;
                            NavResultsFragment.this.mAddStopTooltip = NavResultsTooltipView.showNavResultsAddStopTooltip($r1);
                            ConfigManager.getInstance().setConfigValueIntNTV(407, -1);
                        }
                    }
                }, TOOLTIP_DELAY);
            }
        }
    }

    public boolean withStop() throws  {
        return this.mIsWaypoint;
    }
}
