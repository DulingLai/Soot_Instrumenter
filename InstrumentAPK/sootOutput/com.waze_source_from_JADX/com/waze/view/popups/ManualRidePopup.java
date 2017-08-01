package com.waze.view.popups;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ChatNotificationManager;
import com.waze.ChatNotificationManager.ChatHandler;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolLocation;
import com.waze.carpool.CarpoolMessagingActivity;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.CarpoolRidePickupMeetingDetails;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolNativeManager.getMeetingDetailsForPickupReceiveCompleteListener;
import com.waze.carpool.CarpoolRide;
import com.waze.carpool.CarpoolRideDetailsActivity;
import com.waze.carpool.CarpoolTripDialog;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.CarpoolUtils;
import com.waze.carpool.CarpoolUtils.RiderImageAndMessageCounter;
import com.waze.carpool.ChooseTextDialog;
import com.waze.navbar.NavBar;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.EasingInterpolators;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.text.WazeTextView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class ManualRidePopup extends PopUp implements getMeetingDetailsForPickupReceiveCompleteListener {
    private static final int CALL_RIDER = 0;
    private static final int CANCEL_RIDE = 4;
    private static final int FEEDBACK = 5;
    private static final int NONE = -1;
    private static final int NO_SHOW = 3;
    private static final int SEND_MSG = 1;
    private static final int VIEW_RIDE = 2;
    private ChatHandler mChatHandler;
    private final Configuration mConfig;
    private View mContent = null;
    private final Context mContext;
    private CarpoolNativeManager mCpnm;
    private CarpoolDrive mDrive;
    View mFillerView;
    private ArrayList<String> mImageUrls;
    private boolean mIsShown;
    private final LayoutManager mLayoutManager;
    private CarpoolRidePickupMeetingDetails mMeetingDetails;
    private final NativeManager mNm;
    private int mOrientation;
    private int mRideState;
    HashMap<String, RiderImageAndMessageCounter> mRiderImageAndMessageCounter = new HashMap();
    private DateFormat mTimeFormat;
    private boolean mbPickup;

    class C31572 implements Runnable {
        C31572() {
        }

        public void run() {
            ManualRidePopup.this.mFillerView.setVisibility(8);
        }
    }

    class C31583 implements OnClickListener {
        C31583() {
        }

        public void onClick(View view) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, ManualRidePopup.this.mDrive != null ? ManualRidePopup.this.mDrive.getId() : ManualRidePopup.this.mMeetingDetails.meetingId).addParam("ACTION", "CLOSE").addParam("TYPE", ManualRidePopup.this.mbPickup ? "PICKUP" : "DROPOFF").send();
            ManualRidePopup.this.hide(true);
        }
    }

    class C31594 implements OnClickListener {
        C31594() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, ManualRidePopup.this.mDrive != null ? ManualRidePopup.this.mDrive.getId() : ManualRidePopup.this.mMeetingDetails.meetingId).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM).addParam("TYPE", ManualRidePopup.this.mbPickup ? "PICKUP" : "DROPOFF").send();
            CarpoolUtils.confirmPaxDropOff(ManualRidePopup.this.mDrive, AppService.getActiveActivity());
            ManualRidePopup.this.hide(true);
        }
    }

    class C31636 implements OnClickListener {

        class C31621 implements IResultOk {
            C31621() {
            }

            public void onResult(boolean isOk) {
                if (isOk) {
                    ManualRidePopup.this.mCpnm.driverArrived(ManualRidePopup.this.mDrive);
                    ManualRidePopup.this.openArrivedFullScreen();
                    return;
                }
                ManualRidePopup.this.showReallyArrivedDialog();
            }
        }

        C31636() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, ManualRidePopup.this.mDrive != null ? ManualRidePopup.this.mDrive.getId() : ManualRidePopup.this.mMeetingDetails.meetingId).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ARRIVED).addParam("TYPE", ManualRidePopup.this.mbPickup ? "PICKUP" : "DROPOFF").send();
            if (ManualRidePopup.this.mDrive != null) {
                ManualRidePopup.this.mCpnm.checkDriverArrived(ManualRidePopup.this.mDrive.getId(), new C31621());
            } else {
                ManualRidePopup.this.openArrivedFullScreen();
            }
        }
    }

    class C31647 implements DialogInterface.OnClickListener {
        C31647() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == 1) {
                ManualRidePopup.this.mCpnm.driverArrived(ManualRidePopup.this.mDrive);
                ManualRidePopup.this.openArrivedFullScreen();
            }
        }
    }

    class C31678 implements ChatHandler {

        class C31651 implements Runnable {
            C31651() {
            }

            public void run() {
                ManualRidePopup.this.updateChatBadge();
            }
        }

        class C31662 implements Runnable {
            C31662() {
            }

            public void run() {
                ManualRidePopup.this.updateChatBadge();
            }
        }

        C31678() {
        }

        public boolean onChatMessage(String message) {
            AppService.Post(new C31651());
            return false;
        }

        public void onMessagesLoaded() {
            AppService.Post(new C31662());
        }

        public void onMessageSent(boolean success) {
        }

        public void onMessageRead(String msgId) {
        }
    }

    class C31689 implements OnClickListener {
        C31689() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, ManualRidePopup.this.mDrive != null ? ManualRidePopup.this.mDrive.getId() : ManualRidePopup.this.mMeetingDetails.meetingId).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_START_CARPOOL).addParam("TYPE", ManualRidePopup.this.mbPickup ? "PICKUP" : "DROPOFF").send();
            CarpoolUtils.confirmDriveToPickUp(ManualRidePopup.this.mDrive, ManualRidePopup.this.mMeetingDetails != null ? ManualRidePopup.this.mMeetingDetails.meetingId : null, false);
            ManualRidePopup.this.hide(true);
        }
    }

    public void onComplete(CarpoolRidePickupMeetingDetails details) {
        this.mMeetingDetails = details;
    }

    public ManualRidePopup(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mContext = context;
        this.mLayoutManager = layoutManager;
        this.mNm = AppService.getNativeManager();
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.mConfig = getResources().getConfiguration();
        init();
    }

    public void init() {
        this.mIsShown = false;
        if (this.mContent != null) {
            removeView(this.mContent);
            this.mContent = null;
        }
    }

    public void reshow() {
        show(this.mDrive, this.mMeetingDetails, this.mRideState);
    }

    public boolean onBackPressed() {
        hide(true);
        return true;
    }

    public void hide() {
        hide(false);
    }

    public void hide(final boolean collapse) {
        if (this.mIsShown) {
            this.mIsShown = false;
            setTranslationY(0.0f);
            this.mFillerView.setVisibility(0);
            ViewPropertyAnimatorHelper.initAnimation(this, 300, EasingInterpolators.BOUNCE_IN).translationY((float) (-PixelMeasure.dp(150))).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new Runnable() {
                public void run() {
                    ManualRidePopup.this.mFillerView.setVisibility(8);
                    ManualRidePopup.this.mCpnm.setManualRideTakeoverExpanded(false);
                    Logger.d("Manual rides: hiding takeover");
                    NavBar navBar = ManualRidePopup.this.mLayoutManager.getNavBar();
                    if (navBar != null) {
                        navBar.setAlertMode(false);
                    }
                    if (collapse) {
                        ManualRidePopup.this.collapseToTicker();
                    }
                    ManualRidePopup.this.dismiss();
                }
            }));
        }
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    public void dismiss() {
        this.mLayoutManager.dismiss(this);
    }

    public void show(CarpoolDrive drive, CarpoolRidePickupMeetingDetails meetingDeatils, int rideState) {
        if (!(this.mContent == null || this.mOrientation == this.mConfig.orientation)) {
            init();
        }
        if (this.mContent == null) {
            this.mContent = LayoutInflater.from(getContext()).inflate(C1283R.layout.manual_ride_popup, this, false);
            addView(this.mContent);
            this.mFillerView = findViewById(C1283R.id.layoutFiller);
            setClipChildren(false);
            setClipToPadding(false);
            this.mOrientation = this.mConfig.orientation;
        }
        this.mDrive = drive;
        this.mMeetingDetails = meetingDeatils;
        this.mRideState = rideState;
        boolean valid = initRide();
        if (!this.mIsShown && valid) {
            addViewToLayoutManager();
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive != null ? this.mDrive.getId() : this.mMeetingDetails.meetingId).addParam("TYPE", this.mbPickup ? "PICKUP" : "DROPOFF").send();
        }
        this.mIsShown = valid;
        this.mCpnm.setManualRideTakeoverExpanded(valid);
        NavBar navBar = this.mLayoutManager.getNavBar();
        if (navBar != null) {
            navBar.setAlertMode(true, true);
        }
        setTranslationY((float) (-PixelMeasure.dp(150)));
        this.mFillerView.setVisibility(0);
        ViewPropertyAnimatorHelper.initAnimation(this, 300, EasingInterpolators.BOUNCE_OUT).translationY(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C31572()));
        setupChatHandler();
    }

    public void addViewToLayoutManager() {
        LayoutParams p = new LayoutParams(-1, -2);
        p.addRule(3, C1283R.id.NavBarLayout);
        this.mLayoutManager.addView(this, p);
    }

    private boolean initRide() {
        if (this.mDrive == null && (this.mMeetingDetails == null || this.mMeetingDetails.meetingId == null || this.mMeetingDetails.meetingId.isEmpty())) {
            Logger.e("ManualRidePopup: Empty ride ID and meeting ID; cannot show takeover");
            return false;
        }
        String riderName;
        Logger.e("ManualRidePopup: initRide: meeting ID " + (this.mMeetingDetails != null ? this.mMeetingDetails.meetingId : "(null)") + "; state: " + this.mRideState);
        if (this.mDrive == null || this.mDrive.getRider() == null) {
            riderName = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        } else {
            riderName = this.mDrive.getRider().getFirstName();
        }
        if (this.mRideState == 7 || this.mRideState == 10 || this.mRideState == 16) {
            this.mbPickup = true;
            fillFormDriverStarted(riderName);
        } else if (this.mRideState == 8) {
            this.mbPickup = false;
            fillFormPxPickedUp(riderName);
        }
        findViewById(C1283R.id.closeManualRideTakeoverButton).setOnClickListener(new C31583());
        setOverflowButton();
        return true;
    }

    private void fillFormPxPickedUp(String name) {
        String nameTxt;
        TextView tvRiderName = (TextView) findViewById(C1283R.id.schDriPupDescText);
        if (this.mDrive == null || !this.mDrive.isMultiPax()) {
            nameTxt = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_TAKEOVER_DRIVING_TO_DROPOFF_TITLE_PS, name);
        } else {
            nameTxt = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_DRIVING_TO_DROPOFF_TITLE_MPAX);
        }
        tvRiderName.setText(nameTxt);
        tvRiderName.setVisibility(0);
        TextView tvAddrTime = (TextView) findViewById(C1283R.id.schDriPup2ndText);
        String addressTxt = "";
        if (this.mDrive != null) {
            CarpoolLocation loc = this.mDrive.getDropOffLocation();
            if (loc != null) {
                addressTxt = (loc.placeName == null || loc.placeName.isEmpty()) ? loc.address : loc.placeName;
            }
        }
        tvAddrTime.setText(addressTxt);
        tvAddrTime.setVisibility(0);
        findViewById(C1283R.id.schDriPupCallButton).setVisibility(8);
        WazeTextView startBtn = (WazeTextView) findViewById(C1283R.id.schDriPupNavigateButtonText);
        startBtn.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_DRIVING_TO_DROPOFF_ACTION));
        startBtn.setOnClickListener(new C31594());
    }

    private void fillFormDriverStarted(String name) {
        findViewById(C1283R.id.schDriPupDescText).setVisibility(4);
        findViewById(C1283R.id.schDriPup2ndText).setVisibility(4);
        final boolean canCall = CarpoolUtils.canCallRider(this.mDrive);
        View callButton = findViewById(C1283R.id.schDriPupCallButton);
        callButton.setVisibility(0);
        callButton.setOnClickListener(new OnClickListener() {

            class C31601 implements IResultObj<Integer> {
                C31601() {
                }

                public void onResult(Integer res) {
                    ManualRidePopup.this.mContext.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + ManualRidePopup.this.mDrive.getRide(res.intValue()).getProxyNumber())));
                }
            }

            public void onClick(View v) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, ManualRidePopup.this.mDrive != null ? ManualRidePopup.this.mDrive.getId() : ManualRidePopup.this.mMeetingDetails.meetingId).addParam("ACTION", "CALL").addParam("TYPE", ManualRidePopup.this.mbPickup ? "PICKUP" : "DROPOFF").send();
                Logger.d("Manual rides: Calling rider");
                if (!canCall) {
                    CarpoolUtils.DisplayErrorMsgBox();
                } else if (ManualRidePopup.this.mDrive.isMultiPax()) {
                    CarpoolUtils.showSelectRiderBottomSheet(AppService.getActiveActivity(), ManualRidePopup.this.mDrive, ManualRidePopup.this.mRiderImageAndMessageCounter, new C31601(), DisplayStrings.DS_RIDERS_ACTION_SHEET_CALL_TITLE, -1);
                } else {
                    ManualRidePopup.this.mContext.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + ManualRidePopup.this.mDrive.getRide().getProxyNumber())));
                }
            }
        });
        TextView buttonText = (TextView) findViewById(C1283R.id.schDriPupCallButtonText);
        if (this.mDrive == null || !this.mDrive.isMultiPax()) {
            buttonText.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_TAKEOVER_CALL_PS, name));
        } else {
            buttonText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_CALL_MPAX));
        }
        WazeTextView startBtn = (WazeTextView) findViewById(C1283R.id.schDriPupNavigateButtonText);
        startBtn.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_ARRIVED_TO_PICKUP_ACTION));
        startBtn.setOnClickListener(new C31636());
        retrieveRidersImages();
    }

    private void retrieveRidersImages() {
        if (this.mDrive != null && this.mDrive.hasRider()) {
            Bitmap placeholder = BitmapFactory.decodeResource(this.mContent.getResources(), C1283R.drawable.ridecard_profilepic_placeholder);
            int count = this.mDrive.getRidesAmount();
            if (count != 0) {
                for (int i = 0; i < count; i++) {
                    CarpoolUserData rider = this.mDrive.getRider(i);
                    if (rider == null) {
                        Logger.e("CarpoolTripDialog: rider is null in drive=" + this.mDrive.getId() + "position=" + i);
                    } else if (this.mRiderImageAndMessageCounter.containsKey(rider.id)) {
                        Logger.d("CarpoolTripDialog: rider " + rider.id + " already has an image");
                    } else {
                        CarpoolUtils.initRiderImagesAndMsgCounts(rider, this.mRiderImageAndMessageCounter, new ImageView(this.mContext), placeholder, CarpoolNativeManager.getInstance().getUnreadChatMessageCount(this.mDrive.getRide(i)), 4);
                    }
                }
            }
        }
    }

    private void showReallyArrivedDialog() {
        MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_TITLE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_BODY), false, new C31647(), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_YES), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_NO), 0);
    }

    private void openArrivedFullScreen() {
        new CarpoolTripDialog(AppService.getMainActivity(), this.mDrive, this.mMeetingDetails, this.mRideState, true).show();
        hide(true);
    }

    private void setupChatHandler() {
        updateChatBadge();
        if (this.mChatHandler == null && this.mDrive != null) {
            this.mChatHandler = new C31678();
            for (CarpoolRide ride : this.mDrive.getRides()) {
                ChatNotificationManager.getInstance(true).setChatUpdateHandler(ride.getId(), this.mChatHandler);
            }
        }
    }

    private void updateChatBadge() {
        int i = 0;
        while (i < this.mDrive.getRidesAmount()) {
            if (this.mDrive.getRide(i) == null || this.mDrive.getRide(i).getRider() == null) {
                Logger.e("CarpoolTripDialog: ride is null in drive=" + this.mDrive.getId() + "position=" + i);
            } else {
                String riderId = this.mDrive.getRide(i).getRider().getId();
                int count = CarpoolNativeManager.getInstance().getUnreadChatMessageCount(this.mDrive.getRide(i));
                if (this.mRiderImageAndMessageCounter.containsKey(riderId)) {
                    ((RiderImageAndMessageCounter) this.mRiderImageAndMessageCounter.get(riderId)).counter = count;
                } else {
                    Logger.e("CarpoolTripDialog: Internal error! missing rider image for ride in drive=" + this.mDrive.getId() + "position=" + i);
                    this.mRiderImageAndMessageCounter.put(riderId, new RiderImageAndMessageCounter(null, count));
                }
            }
            i++;
        }
    }

    private void fillFromStartDrive(String name) {
        String nameTxt;
        String pickupHour;
        TextView tvRiderName = (TextView) findViewById(C1283R.id.schDriPupDescText);
        if (this.mMeetingDetails != null && this.mMeetingDetails.meetingTitle != null) {
            nameTxt = this.mMeetingDetails.meetingTitle;
        } else if (this.mDrive == null || !this.mDrive.isMultiPax()) {
            nameTxt = String.format(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_UPCOMING_RIDE_TITLE_PS), new Object[]{name});
        } else {
            nameTxt = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_UPCOMING_RIDE_TITLE_MPAX);
        }
        tvRiderName.setText(nameTxt);
        TextView tvAddrTime = (TextView) findViewById(C1283R.id.schDriPup2ndText);
        findViewById(C1283R.id.schDriPupCallButton).setVisibility(8);
        TimeZone tz = Calendar.getInstance().getTimeZone();
        this.mTimeFormat = android.text.format.DateFormat.getTimeFormat(AppService.getAppContext());
        this.mTimeFormat.setTimeZone(tz);
        if (this.mDrive.getRide() != null && this.mDrive.getRide().itinerary != null) {
            pickupHour = this.mTimeFormat.format(new Date(1000 * this.mDrive.getTime()));
        } else if (this.mMeetingDetails != null) {
            pickupHour = this.mTimeFormat.format(new Date(this.mMeetingDetails.meetingStartTime * 1000));
        } else {
            pickupHour = "??:??";
        }
        tvAddrTime.setText(String.format(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_UPCOMING_RIDE_TEXT_PS), new Object[]{pickupHour}));
        WazeTextView startBtn = (WazeTextView) findViewById(C1283R.id.schDriPupNavigateButtonText);
        startBtn.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_UPCOMING_RIDE_ACTION).toUpperCase());
        startBtn.setOnClickListener(new C31689());
        retrieveRidersImages();
    }

    private void collapseToTicker() {
        if (this.mLayoutManager.shouldShowCarpoolBar()) {
            AppService.Post(new Runnable() {
                public void run() {
                    ManualRidePopup.this.mLayoutManager.openUpcomingCarpoolBar(ManualRidePopup.this.mDrive);
                }
            }, 600);
            this.mCpnm.setManualRideTickerOpen(true);
        }
    }

    private void setOverflowButton() {
        findViewById(C1283R.id.schDriPupMoreButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ManualRidePopup.this.openOverflowMenu();
            }
        });
    }

    private void openOverflowMenu() {
        boolean z;
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive != null ? this.mDrive.getId() : this.mMeetingDetails.meetingId).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_OVERFLOW).addParam("TYPE", this.mbPickup ? "PICKUP" : "DROPOFF").send();
        boolean canCall = CarpoolUtils.canCallRider(this.mDrive);
        boolean canMsg = CarpoolUtils.canChatRider(this.mDrive);
        String callRider = this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_CONTACT_RIDER_PHONE);
        String sendMessage = this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_TAKEOVER_OPTIONS_TEXT);
        String viewRide = this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_TAKEOVER_OPTIONS_RIDE_DETAILS);
        boolean allowCancel = false;
        if (this.mRideState == 7 || this.mRideState == 10 || this.mRideState == 16) {
            allowCancel = true;
        }
        String noShow = this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRM_BUTTON);
        String cancelRide = this.mNm.getLanguageString(DisplayStrings.DS_RIDE_DETAILS_CANCEL_BUTTON);
        String feedback = this.mNm.getLanguageString(DisplayStrings.DS_COMMUTE_MODEL_WEEK_FEEDBACK_TITLE);
        String[] options = new String[]{callRider, sendMessage, viewRide, noShow, cancelRide, feedback};
        int[] optionIcons = new int[]{C1283R.drawable.actionsheet_call, C1283R.drawable.actionsheet_message, C1283R.drawable.actionsheet_location_info, C1283R.drawable.carpool_options_no_show, C1283R.drawable.carpool_options_cancel_ride, C1283R.drawable.carpool_options_feedback};
        int[] optionValues = new int[]{0, 1, 2, 3, 4, 5};
        boolean disableNoShow = (this.mRideState == 10 || this.mRideState == 16) ? false : true;
        boolean[] optionsDisabled = new boolean[6];
        optionsDisabled[0] = !canCall;
        if (canMsg) {
            z = false;
        } else {
            z = true;
        }
        optionsDisabled[1] = z;
        optionsDisabled[2] = false;
        optionsDisabled[3] = disableNoShow;
        if (allowCancel) {
            z = false;
        } else {
            z = true;
        }
        optionsDisabled[4] = z;
        optionsDisabled[5] = false;
        showGridSubmenu(DisplayStrings.DS_CARPOOL_SETTINGS_MORE, options, optionIcons, optionValues, optionsDisabled);
    }

    protected void showGridSubmenu(int dialogTitleDS, String[] options, int[] optionIds, int[] optionValues, boolean[] optionsDisabled) {
        SimpleBottomSheetValue[] values = new SimpleBottomSheetValue[options.length];
        for (int i = 0; i < options.length; i++) {
            values[i] = new SimpleBottomSheetValue(optionValues[i], options[i], this.mContext.getResources().getDrawable(optionIds[i]), optionsDisabled[i]);
        }
        new SimpleBottomSheet(this.mContext, dialogTitleDS, values, new SimpleBottomSheetListener() {
            public void onComplete(SimpleBottomSheetValue value) {
                ManualRidePopup.this.handleSelectedMoreOption(value.id);
            }
        }) {
            public void onClick(int itemId) {
                super.onClick(itemId);
                dismiss();
            }
        }.show();
    }

    private void handleSelectedMoreOption(int option) {
        final Context activity = AppService.getActiveActivity();
        switch (option) {
            case 0:
                Logger.d("Manual rides: Calling rider");
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CONTACT_RIDER_CLICKED, "ACTION", "PHONE");
                if (this.mDrive.isMultiPax()) {
                    CarpoolUtils.showSelectRiderBottomSheet(activity, this.mDrive, this.mRiderImageAndMessageCounter, new IResultObj<Integer>() {
                        public void onResult(Integer res) {
                            ManualRidePopup.this.mContext.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + ManualRidePopup.this.mDrive.getRide(res.intValue()).getProxyNumber())));
                        }
                    }, DisplayStrings.DS_RIDERS_ACTION_SHEET_CALL_TITLE, -1);
                } else {
                    this.mContext.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + this.mDrive.getRide().getProxyNumber())));
                }
                if (CarpoolUtils.isRideInvalid(this.mDrive.getRide()) || this.mDrive.getRider() == null) {
                    getRideData();
                    CarpoolUtils.DisplayErrorMsgBox();
                    return;
                }
                return;
            case 1:
                if (this.mCpnm.isMessagingEnabled()) {
                    Logger.d("Manual rides: In app msg");
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CONTACT_RIDER_CLICKED, "ACTION", "IN-APP");
                    if (this.mDrive.isMultiPax()) {
                        CarpoolUtils.showSelectRiderBottomSheet(this.mContext, this.mDrive, this.mRiderImageAndMessageCounter, new IResultObj<Integer>() {
                            public void onResult(Integer res) {
                                Intent intent = new Intent(ManualRidePopup.this.mContext, CarpoolMessagingActivity.class);
                                intent.putExtra("rider", ManualRidePopup.this.mDrive.getRider(res.intValue()));
                                intent.putExtra("ride", ManualRidePopup.this.mDrive.getRide(res.intValue()));
                                ManualRidePopup.this.mContext.startActivity(intent);
                            }
                        }, DisplayStrings.DS_RIDERS_ACTION_SHEET_MESSAGE_TITLE, -1);
                        return;
                    } else if (CarpoolUtils.isRideInvalid(this.mDrive.getRide()) || this.mDrive.getRider() == null) {
                        getRideData();
                        CarpoolUtils.DisplayErrorMsgBox();
                        return;
                    } else {
                        Intent intent = new Intent(this.mContext, CarpoolMessagingActivity.class);
                        intent.putExtra("rider", this.mDrive.getRider());
                        intent.putExtra("ride", this.mDrive.getRide());
                        this.mContext.startActivity(intent);
                        return;
                    }
                }
                Logger.d("Manual rides: Txt msg");
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CONTACT_RIDER_CLICKED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_TEXT);
                new ChooseTextDialog(activity, this.mDrive.getRide().getProxyNumber(), this.mDrive.getRider().getGivenName(), this.mDrive.getRide(), true).show();
                return;
            case 2:
                Logger.d("Manual rides: View ride");
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_POPUP_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "DETAILS");
                if (CarpoolUtils.isRideInvalid(this.mDrive.getRide())) {
                    getRideData();
                    CarpoolUtils.DisplayErrorMsgBox();
                    return;
                }
                Intent openRide = new Intent(this.mContext, CarpoolRideDetailsActivity.class);
                openRide.putExtra(CarpoolDrive.class.getSimpleName(), this.mDrive);
                this.mContext.startActivity(openRide);
                return;
            case 3:
                Logger.d("Manual rides: No show");
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OPTION, "ACTION|RIDE_ID", "REPORT_NO_SHOW|" + this.mDrive.getRide().getId());
                if (CarpoolUtils.isRideInvalid(this.mDrive.getRide())) {
                    getRideData();
                    CarpoolUtils.DisplayErrorMsgBox();
                    return;
                }
                if (this.mDrive.isMultiPax()) {
                    CarpoolUtils.showSelectRiderBottomSheet(activity, this.mDrive, this.mRiderImageAndMessageCounter, new IResultObj<Integer>() {
                        public void onResult(Integer res) {
                            CarpoolUtils.riderDidntShow(ManualRidePopup.this.mDrive, ManualRidePopup.this.mDrive.getRide(res.intValue()), activity);
                        }
                    }, DisplayStrings.DS_RIDE_DETAILS_OPTION_REPORT_NO_SHOW, DisplayStrings.DS_RIDERS_ACTION_SHEET_NO_SHOW_BUTTON_PS);
                } else {
                    CarpoolUtils.riderDidntShow(this.mDrive, this.mDrive.getRide(), activity);
                }
                hide();
                return;
            case 4:
                Logger.d("Manual rides: Cancel ride");
                if (CarpoolUtils.isRideInvalid(this.mDrive.getRide())) {
                    getRideData();
                    CarpoolUtils.DisplayErrorMsgBox();
                    return;
                }
                CarpoolUtils.cancelDriveAfterAccepted(this.mDrive, null, this.mContext);
                hide();
                return;
            case 5:
                Logger.d("Manual rides: feedback");
                CarpoolUtils.startFeedbackActivity(this.mDrive, null, this.mRiderImageAndMessageCounter, null, this.mContext, true);
                return;
            default:
                Logger.e("Manual rides: Unsupported overflow option: " + option);
                CarpoolUtils.DisplayErrorMsgBox();
                return;
        }
    }

    private void getRideData() {
        CarpoolNativeManager.getInstance().getDriveInfoByMeetingId(this.mMeetingDetails.meetingId, new IResultObj<CarpoolDrive>() {
            public void onResult(CarpoolDrive res) {
                ManualRidePopup.this.mDrive = res;
            }
        });
    }

    private void close() {
        dismiss();
    }
}
