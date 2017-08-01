package com.waze.navigate.social;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IRefreshFriendsDrivingData;
import com.waze.NativeManager.IResultCode;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.MapView;
import com.waze.messages.MessagesNativeManager;
import com.waze.messages.MessagesNativeManager.EditorContext;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.EndDriveListener;
import com.waze.share.ShareNativeManager;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.user.UserData;
import com.waze.utils.ImageRepository;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.title.TitleBar;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ShareDrivingFriendsActivity extends ActivityBase implements IRefreshFriendsDrivingData {
    private static final int MODE_BEEP = 0;
    private static final int MODE_CUSTOM = 2;
    private static final int MODE_TEXT = 1;
    private static final int RQ_CUSTOM_MESSAGE = 101;
    private boolean IsSetMeeting = false;
    ArrayAdapter<PersonBase> adapter;
    private ImageView increaseMapButton;
    private Button increaseMapOverlay;
    private boolean isMapBig = false;
    private int mCurMode = 1;
    private String mCurText = "";
    private EndDriveData mEndDriveData = null;
    private final RunnableExecutor mNativeCanvasReadyEvent = new C22286();
    private View mRoot;
    protected String mShareOwner;
    private FriendUserData mSharedFriends;
    private TitleBar mTitleBar;
    String mUrl = null;
    private PersonBase mUser;
    private RelativeLayout mapLayout;
    private RelativeLayout mapPlaceholder;
    private MapView mapView;
    private NativeManager nm;
    ArrayList<PersonBase> people;
    String sMeeting = null;
    protected TextView shareDriveSwitch;

    class C22211 implements Runnable {
        C22211() {
        }

        public void run() {
            ShareDrivingFriendsActivity.this.mUser = ShareNativeManager.getInstance().getFriendFromMeeting(ShareDrivingFriendsActivity.this.sMeeting);
        }
    }

    class C22222 implements OnGlobalLayoutListener {
        C22222() {
        }

        public void onGlobalLayout() {
            LayoutParams layoutParams = new LayoutParams(ShareDrivingFriendsActivity.this.mapPlaceholder.getMeasuredWidth(), ShareDrivingFriendsActivity.this.mapPlaceholder.getMeasuredHeight());
            layoutParams.topMargin = ShareDrivingFriendsActivity.this.mapPlaceholder.getTop();
            layoutParams.leftMargin = ShareDrivingFriendsActivity.this.mapPlaceholder.getLeft();
            ShareDrivingFriendsActivity.this.mapLayout.setLayoutParams(layoutParams);
            ShareDrivingFriendsActivity.this.mapPlaceholder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    class C22233 implements OnClickListener {
        C22233() {
        }

        public void onClick(View v) {
            ShareDrivingFriendsActivity.this.driveThere();
        }
    }

    class C22244 implements OnClickListener {
        C22244() {
        }

        public void onClick(View v) {
            ShareDrivingFriendsActivity.this.openTextPicker();
        }
    }

    class C22275 extends RunnableUICallback {
        private int danger;
        private int lat;
        private int lon;

        class C22251 implements DialogInterface.OnClickListener {
            C22251() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    DriveToNativeManager.getInstance().addDangerZoneStat(C22275.this.lon, C22275.this.lat, AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                    ShareDrivingFriendsActivity.this.doDrive();
                    return;
                }
                DriveToNativeManager.getInstance().addDangerZoneStat(C22275.this.lon, C22275.this.lat, AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
            }
        }

        class C22262 implements OnCancelListener {
            C22262() {
            }

            public void onCancel(DialogInterface dialog) {
                DriveToNativeManager.getInstance().addDangerZoneStat(C22275.this.lon, C22275.this.lat, AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, "BACK");
            }
        }

        C22275() {
        }

        public void event() {
            this.danger = DriveToNativeManager.getInstance().isMeetingInDangerZoneNTV(ShareDrivingFriendsActivity.this.sMeeting);
            if (this.danger >= 0) {
                this.lon = DriveToNativeManager.getInstance().getMeetingLongitudeNTV(ShareDrivingFriendsActivity.this.sMeeting);
                this.lat = DriveToNativeManager.getInstance().getMeetingLatitudeNTV(ShareDrivingFriendsActivity.this.sMeeting);
            }
        }

        public void callback() {
            NativeManager nm = NativeManager.getInstance();
            if (this.danger >= 0) {
                MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(nm.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), nm.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_GO), false, new C22251(), nm.getLanguageString(DisplayStrings.DS_KEEP_DRIVE), nm.getLanguageString(344), -1, "dangerous_zone_icon", new C22262(), true, true);
            } else {
                ShareDrivingFriendsActivity.this.doDrive();
            }
        }
    }

    class C22286 extends RunnableExecutor {
        C22286() {
        }

        public void event() {
            if (!ShareDrivingFriendsActivity.this.IsSetMeeting) {
                DriveToNativeManager.getInstance().setMeeting(ShareDrivingFriendsActivity.this.sMeeting);
                ShareDrivingFriendsActivity.this.IsSetMeeting = true;
            }
        }
    }

    class C22297 implements EndDriveListener {
        C22297() {
        }

        public void onComplete(EndDriveData data) {
            ShareDrivingFriendsActivity.this.mEndDriveData = data;
            if (ShareDrivingFriendsActivity.this.mEndDriveData != null) {
                ShareDrivingFriendsActivity.this.mShareOwner = ShareDrivingFriendsActivity.this.mEndDriveData.shareOwner;
                ((TextView) ShareDrivingFriendsActivity.this.findViewById(C1283R.id.shareDrivingFriendsSubtitle)).setText(String.format(ShareDrivingFriendsActivity.this.nm.getLanguageString(DisplayStrings.DS_SHARED_DRIVE_LABEL_PS), new Object[]{ShareUtility.getShortened(ShareDrivingFriendsActivity.this.mShareOwner)}));
                ((TextView) ShareDrivingFriendsActivity.this.findViewById(C1283R.id.shareDrivingFriendsDestination)).setText(ShareDrivingFriendsActivity.this.mEndDriveData.address);
                if (ShareDrivingFriendsActivity.this.mEndDriveData.friends.length > 0 && ShareDrivingFriendsActivity.this.mShareOwner != null && !ShareDrivingFriendsActivity.this.mShareOwner.isEmpty()) {
                    for (FriendUserData fud : ShareDrivingFriendsActivity.this.mEndDriveData.friends) {
                        if (ShareDrivingFriendsActivity.this.mShareOwner.equals(fud.getName())) {
                            ShareDrivingFriendsActivity.this.mSharedFriends = fud;
                            break;
                        }
                    }
                }
                if (ShareDrivingFriendsActivity.this.mSharedFriends == null && ShareDrivingFriendsActivity.this.mUser != null && (ShareDrivingFriendsActivity.this.mUser instanceof FriendUserData)) {
                    ShareDrivingFriendsActivity.this.mSharedFriends = (FriendUserData) ShareDrivingFriendsActivity.this.mUser;
                }
                if (ShareDrivingFriendsActivity.this.mSharedFriends != null) {
                    ShareDrivingFriendsActivity.this.setEta(ShareDrivingFriendsActivity.this.mSharedFriends.mEtaSeconds, ShareDrivingFriendsActivity.this.mSharedFriends.pictureUrl, ShareDrivingFriendsActivity.this.mSharedFriends.getName(), ShareDrivingFriendsActivity.this.mSharedFriends.statusLine);
                } else if (ShareDrivingFriendsActivity.this.mUser != null) {
                    String status = "";
                    if (ShareDrivingFriendsActivity.this.mUser instanceof FriendUserData) {
                        status = ((FriendUserData) ShareDrivingFriendsActivity.this.mUser).statusLine;
                    }
                    ShareDrivingFriendsActivity.this.setEta(ShareDrivingFriendsActivity.this.mEndDriveData.maxEtaSeconds, ShareDrivingFriendsActivity.this.mUser.getImage(), ShareDrivingFriendsActivity.this.mEndDriveData.shareOwner, status);
                } else {
                    ShareDrivingFriendsActivity.this.setEta(ShareDrivingFriendsActivity.this.mEndDriveData.maxEtaSeconds, "", ShareDrivingFriendsActivity.this.mEndDriveData.shareOwner, "");
                }
            }
        }
    }

    class C22308 implements OnGlobalLayoutListener {
        C22308() {
        }

        public void onGlobalLayout() {
            ShareDrivingFriendsActivity.this.layoutMapOnView(ShareDrivingFriendsActivity.this.isMapBig ? ShareDrivingFriendsActivity.this.mRoot : ShareDrivingFriendsActivity.this.mapPlaceholder);
            ShareDrivingFriendsActivity.this.mapPlaceholder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    class C22319 implements IResultCode {
        C22319() {
        }

        public void onResult(int res) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1283R.layout.share_driving_friends);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_SHARED_DRIVE_SHOWN, null, null);
        this.nm = NativeManager.getInstance();
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.shareDrivingFriendsTitle);
        this.mTitleBar.init((Activity) this, this.nm.getLanguageString(DisplayStrings.DS_SHARED_DRIVE));
        this.mUser = (PersonBase) getIntent().getExtras().getSerializable("user");
        this.sMeeting = getIntent().getExtras().getString("meeting");
        if (this.mUser == null) {
            NativeManager.Post(new C22211());
        }
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsUserImageInitials)).setText(" ");
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsSubtitle)).setText(" ");
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsDestination)).setText(" ");
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsArriving)).setText(" ");
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsStatus)).setText(" ");
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsEta)).setText(" ");
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsEtaText)).setText(this.nm.getLanguageString(392));
        this.mapPlaceholder = (RelativeLayout) findViewById(C1283R.id.addressMapLayoutPlaceholder);
        this.mapLayout = (RelativeLayout) findViewById(C1283R.id.addressMapLayout);
        this.mRoot = findViewById(C1283R.id.shareDrivingFriendsContent);
        this.mapPlaceholder.getViewTreeObserver().addOnGlobalLayoutListener(new C22222());
        this.increaseMapOverlay = (Button) findViewById(C1283R.id.shareDrivingFriendsIncreaseMapOverlay);
        this.mapView = (MapView) findViewById(C1283R.id.addressMap);
        this.mapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEvent);
        this.increaseMapOverlay.setVisibility(0);
        findViewById(C1283R.id.shareDrivingFriendsDestination).setOnClickListener(new C22233());
        this.increaseMapButton = (ImageView) findViewById(C1283R.id.increaseMapButton);
        this.increaseMapButton.setImageResource(C1283R.drawable.mag_glass_icon);
        this.mCurText = "\"" + this.nm.getLanguageString(DisplayStrings.DS_SHARE_REPLY_1) + "\"";
        findViewById(C1283R.id.shareDrivingFriendsReplyButton).setOnClickListener(new C22244());
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsReplyButtonText)).setText(this.nm.getLanguageString(DisplayStrings.DS_REPLY));
        onRefresh();
    }

    void driveThere() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_SHARED_DRIVE_GO, null, null);
        NativeManager.Post(new C22275());
    }

    private void doDrive() {
        DriveToNativeManager.getInstance().drive(this.sMeeting, true);
        setResult(-1);
        finish();
    }

    private void layoutMapOnView(View view) {
        LayoutParams layoutParams = new LayoutParams(view.getMeasuredWidth(), view.getMeasuredHeight());
        layoutParams.topMargin = view.getTop();
        layoutParams.leftMargin = view.getLeft();
        this.mapLayout.setLayoutParams(layoutParams);
    }

    public void increaseMapClicked(View v) {
        if (this.isMapBig) {
            this.isMapBig = false;
            this.mapView.setHandleTouch(false);
            this.increaseMapOverlay.setVisibility(0);
            this.increaseMapButton.setImageResource(C1283R.drawable.mag_glass_icon);
            layoutMapOnView(this.mapPlaceholder);
            DriveToNativeManager.getInstance().setMapMode(true);
            return;
        }
        this.isMapBig = true;
        this.mapView.setHandleTouch(true);
        this.increaseMapOverlay.setVisibility(8);
        this.increaseMapButton.setImageResource(C1283R.drawable.mag_glass_icon_out);
        layoutMapOnView(this.mRoot);
        this.mapLayout.bringToFront();
        DriveToNativeManager.getInstance().setMapMode(false);
    }

    protected void onPause() {
        super.onPause();
        this.mapView.onPause();
        this.IsSetMeeting = false;
        DriveToNativeManager.getInstance().unsetMeeting();
    }

    protected void onResume() {
        super.onResume();
        this.mapView.onResume();
        DriveToNativeManager.getInstance().setMeeting(this.sMeeting);
    }

    public void onBackPressed() {
        if (this.isMapBig) {
            increaseMapClicked(null);
        } else {
            super.onBackPressed();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 101) {
            if (resultCode == -1) {
                setResult(-1);
                finish();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onRefresh() {
        DriveToNativeManager.getInstance().getFriendsDriveData(new C22297(), this.sMeeting);
        this.mapPlaceholder.getViewTreeObserver().addOnGlobalLayoutListener(new C22308());
    }

    void setEta(int etaSeconds, String pictureUrl, String name, String status) {
        String arriveIn;
        Log.i("GilTestEta", "eta seconds = " + etaSeconds);
        if (etaSeconds < 30) {
            arriveIn = this.nm.getLanguageString(DisplayStrings.DS_ARRIVING);
        } else if (etaSeconds < 3600) {
            arriveIn = "" + ((etaSeconds + 30) / 60) + " " + this.nm.getLanguageString(DisplayStrings.DS_MIN);
        } else {
            int mins = (etaSeconds % DisplayStrings.DS_CUSTOM_PROMPT_200_METERS) / 60;
            arriveIn = "" + (etaSeconds / DisplayStrings.DS_CUSTOM_PROMPT_200_METERS) + " " + this.nm.getLanguageString(423) + mins + " " + this.nm.getLanguageString(DisplayStrings.DS_MIN);
        }
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsArriving)).setText(arriveIn);
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsStatus)).setText(status);
        ImageView userImage = (ImageView) findViewById(C1283R.id.shareDrivingFriendsUserImage);
        TextView userInitials = (TextView) findViewById(C1283R.id.shareDrivingFriendsUserImageInitials);
        userInitials.setText(ShareUtility.getInitials(name));
        ImageRepository.instance.getImage(pictureUrl, 2, userImage, userInitials, this);
        TimeZone tz = Calendar.getInstance().getTimeZone();
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this);
        timeFormat.setTimeZone(tz);
        ((TextView) findViewById(C1283R.id.shareDrivingFriendsEta)).setText(timeFormat.format(new Date(System.currentTimeMillis() + ((long) (etaSeconds * 1000)))));
    }

    private void onBeepBeep() {
        this.mCurMode = 0;
        this.mCurText = this.nm.getLanguageString(238);
        sendMessage();
    }

    private void onSendMessage(String message) {
        this.mCurMode = 1;
        this.mCurText = message;
        sendMessage();
    }

    private void onSendCustomMessage() {
        this.mCurMode = 2;
        this.mCurText = this.nm.getLanguageString(DisplayStrings.DS_CUSTOM_MESSAGE);
        sendMessage();
    }

    void sendMessage() {
        UserData user;
        if (this.mSharedFriends != null) {
            user = this.mSharedFriends;
        } else if (this.mUser instanceof FriendUserData) {
            this.mSharedFriends = (FriendUserData) this.mUser;
            user = this.mSharedFriends;
        } else if (this.mUser instanceof UserData) {
            user = this.mUser;
        } else {
            return;
        }
        if (this.mCurMode == 0) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_BEEP_BEEP_CLICK_FROM, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SHARED_DRIVE);
            this.nm.SendBeepBeep(user.mLongitude, user.mLatitude, user.mAzimuth, user.mID, new C22319());
        } else if (this.mCurMode == 1) {
            MessagesNativeManager.getInstance().sendMessage(true, user, this.mCurText);
        } else if (this.mCurMode == 2) {
            MessagesNativeManager mnm = MessagesNativeManager.getInstance();
            EditorContext editorContext = new EditorContext(this, 1, user);
            editorContext.mRqCode = 101;
            mnm.startPrivate(editorContext);
        }
    }

    private void openTextPicker() {
        final BottomSheet bs = new BottomSheet(this, DisplayStrings.DS_SELECT_A_MESSAGE, Mode.COLUMN_TEXT_ICON);
        bs.setAdapter(new Adapter() {
            public int getCount() {
                return 4;
            }

            public void onConfigItem(int position, ItemDetails item) {
                switch (position) {
                    case 0:
                        item.setItem((int) DisplayStrings.DS_SHARE_REPLY_1, (int) C1283R.drawable.list_icon_message);
                        return;
                    case 1:
                        item.setItem((int) DisplayStrings.DS_SHARE_REPLY_2, (int) C1283R.drawable.list_icon_message);
                        return;
                    case 2:
                        item.setItem((int) DisplayStrings.DS_CUSTOM_MESSAGE, (int) C1283R.drawable.list_icon_message);
                        return;
                    case 3:
                        item.setItem(238, (int) C1283R.drawable.list_icon_beep);
                        return;
                    default:
                        return;
                }
            }

            public void onClick(int itemId) {
                switch (itemId) {
                    case 0:
                        ShareDrivingFriendsActivity.this.onSendMessage(ShareDrivingFriendsActivity.this.nm.getLanguageString(DisplayStrings.DS_SHARE_REPLY_1));
                        bs.dismiss();
                        return;
                    case 1:
                        ShareDrivingFriendsActivity.this.onSendMessage(ShareDrivingFriendsActivity.this.nm.getLanguageString(DisplayStrings.DS_SHARE_REPLY_2));
                        bs.dismiss();
                        return;
                    case 2:
                        ShareDrivingFriendsActivity.this.onSendCustomMessage();
                        bs.dismiss();
                        return;
                    case 3:
                        ShareDrivingFriendsActivity.this.onBeepBeep();
                        bs.dismiss();
                        return;
                    default:
                        bs.dismiss();
                        return;
                }
            }
        });
        bs.show();
    }
}
