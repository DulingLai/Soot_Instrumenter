package com.waze.share;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MoodManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultCode;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.Person;
import com.waze.ifs.ui.ActivityBase;
import com.waze.messages.UserMessage;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.DriveToNativeManager.EndDriveListener;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.social.EndDriveData;
import com.waze.navigate.social.ShareDrivingFriendsActivity;
import com.waze.navigate.social.ShareHelpActivity;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.PhoneRequestAccessDialog;
import com.waze.phone.PhoneRequestAccessDialog.PhoneRequestAccessResultListener;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.share.NameYourselfView.NameYourselfViewListener;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.user.UserData;
import com.waze.utils.ImageRepository;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UserDetailsActivity extends ActivityBase implements DriveToNavigateCallback {
    private static final int RES_CODE_DIAL = 1004;
    private static final int RES_CODE_SHARE_HELP = 1002;
    private static final int RES_CODE_SHARING_INFO = 1003;
    private static final int RES_CODE_SIGN_IN = 1005;
    private static final int RES_CODE_STOP_SHARING = 1001;
    private String mImageUrl;
    private NameYourselfView mNameYourselfView;
    private NativeManager mNm;
    private LinearLayout mReceivedLocation;
    private SettingsTitleText mReceivedLocationsTitleText;
    private PersonBase mUser;
    String sPhoneNumer = null;

    class C28732 implements EndDriveListener {
        C28732() {
        }

        public void onComplete(EndDriveData data) {
            if (data != null) {
                ((TextView) UserDetailsActivity.this.findViewById(C1283R.id.friendDetailsSharedDriveDestination)).setText(data.address);
            }
        }
    }

    class C28754 implements OnClickListener {
        C28754() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", "CALL");
            Intent intent = new Intent("android.intent.action.DIAL");
            intent.setData(Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + UserDetailsActivity.this.sPhoneNumer));
            UserDetailsActivity.this.startActivityForResult(intent, 1004);
        }
    }

    class C28795 implements OnClickListener {

        class C28761 implements PhoneRequestAccessResultListener {
            C28761() {
            }

            public void onResult(boolean okToAccess) {
                if (okToAccess) {
                    C28795.this.doShare();
                }
            }
        }

        class C28772 implements NameYourselfViewListener {
            C28772() {
            }

            public void onNameYourselfClosed(boolean nameWasChanged) {
                if (nameWasChanged) {
                    new ShareSelectorDialog(UserDetailsActivity.this, UserDetailsActivity.this.mUser).show();
                }
                UserDetailsActivity.this.mNameYourselfView = null;
            }
        }

        C28795() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", "SHARE");
            if (!MyWazeNativeManager.getInstance().getContactLoggedInNTV()) {
                Intent intent = new Intent(UserDetailsActivity.this, PhoneRegisterActivity.class);
                intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
                UserDetailsActivity.this.startActivityForResult(intent, 1005);
            } else if (NativeManager.getInstance().IsAccessToContactsEnableNTV()) {
                doShare();
            } else {
                new PhoneRequestAccessDialog(UserDetailsActivity.this, new C28761()).show();
            }
        }

        private void doShare() {
            final NameYourselfView[] viewRef = new NameYourselfView[1];
            NameYourselfView.showNameYourselfViewIfNeeded(UserDetailsActivity.this, viewRef, new C28772(), new Runnable() {
                public void run() {
                    UserDetailsActivity.this.mNameYourselfView = viewRef[0];
                }
            });
        }
    }

    class C28836 implements OnClickListener {

        class C28811 implements NameYourselfViewListener {

            class C28801 implements IResultCode {
                C28801() {
                }

                public void onResult(int res) {
                    if (res >= 0) {
                        UserDetailsActivity.this.setResult(-1);
                        UserDetailsActivity.this.finish();
                    }
                }
            }

            C28811() {
            }

            public void onNameYourselfClosed(boolean nameWasChanged) {
                if (nameWasChanged) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_BEEP_BEEP_CLICK_FROM, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "FRIENDS_LIST");
                    UserDetailsActivity.this.mNm.SendBeepBeep(((UserData) UserDetailsActivity.this.mUser).mLongitude, ((UserData) UserDetailsActivity.this.mUser).mLatitude, ((UserData) UserDetailsActivity.this.mUser).mAzimuth, ((UserData) UserDetailsActivity.this.mUser).mID, new C28801());
                }
                UserDetailsActivity.this.mNameYourselfView = null;
            }
        }

        C28836() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_BEEP);
            if (AppService.isNetworkAvailable()) {
                final NameYourselfView[] viewRef = new NameYourselfView[1];
                NameYourselfView.showNameYourselfViewIfNeeded(UserDetailsActivity.this, viewRef, new C28811(), new Runnable() {
                    public void run() {
                        UserDetailsActivity.this.mNameYourselfView = viewRef[0];
                    }
                });
                return;
            }
            UserDetailsActivity.this.showErrorPopup(252);
        }
    }

    class C28847 implements OnClickListener {
        C28847() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_MESSAGE);
            UserMessage.startPrivate(UserDetailsActivity.this, (UserData) UserDetailsActivity.this.mUser);
        }
    }

    class C28858 implements DriveToGetAddressItemArrayCallback {
        C28858() {
        }

        public void getAddressItemArrayCallback(AddressItem[] ai) {
            if (ai != null && ai.length > 0) {
                UserDetailsActivity.this.populateReceivedLocations(ai);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean bBeep;
        boolean bMessage;
        boolean bPending;
        boolean bSharingMyDrive;
        boolean bSharedDriveWithMe;
        super.onCreate(savedInstanceState);
        this.mNm = NativeManager.getInstance();
        requestWindowFeature(1);
        setContentView(C1283R.layout.friend_details);
        this.mUser = (PersonBase) getIntent().getExtras().getSerializable(PublicMacros.FriendUserData);
        String status = null;
        this.sPhoneNumer = null;
        if (this.mUser instanceof FriendUserData) {
            FriendUserData fud;
            fud = (FriendUserData) this.mUser;
            status = fud.statusLine;
            bBeep = fud.mAllowBeepBeep;
            bMessage = fud.mAllowPrivatePing;
            bPending = fud.mIsPendingFriend;
            if (fud.mContactID != -1) {
                Person pr = AddressBookImpl.getInstance().GetPersonFromID(fud.mContactID);
                if (pr != null) {
                    this.sPhoneNumer = pr.getPhone();
                }
            }
            bSharingMyDrive = fud.mMeetingIdSharedByMe.length() > 0;
            if (fud.mMeetingIdSharedWithMe.length() > 0) {
                bSharedDriveWithMe = true;
            } else {
                bSharedDriveWithMe = false;
            }
        } else {
            if (this.mUser instanceof UserData) {
                status = ((UserData) this.mUser).mLastReportText;
            }
            bBeep = false;
            bMessage = false;
            bPending = false;
            bSharingMyDrive = false;
            bSharedDriveWithMe = false;
            this.sPhoneNumer = this.mUser.getPhone();
        }
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.friendDetailsTitle);
        titleBar.init((Activity) this, DisplayStrings.DS_FRIEND);
        final TitleBar titleBar2 = titleBar;
        titleBar.setOnClickCloseListener(new OnClickListener() {
            public void onClick(View v) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", "X");
                titleBar2.onCloseClicked();
            }
        });
        this.mImageUrl = this.mUser.getImage();
        if (!(this.mImageUrl == null || this.mImageUrl.isEmpty())) {
            String scheme = Uri.parse(this.mImageUrl).getScheme();
            if (scheme == null || !scheme.equals("content")) {
                this.mImageUrl = DriveToNativeManager.getInstance().getFriendImageNTV(this.mUser.getID(), (int) (80.0f * getResources().getDisplayMetrics().density));
            }
        }
        ImageView userMood = (ImageView) findViewById(C1283R.id.friendDetailsUserMood);
        if (this.mUser instanceof UserData) {
            userMood.setImageDrawable(MoodManager.getMoodDrawable(this, ((UserData) this.mUser).mMood));
        } else {
            userMood.setVisibility(8);
        }
        ((WazeTextView) findViewById(C1283R.id.friendDetailsName)).setText(this.mUser.getName());
        ((WazeTextView) findViewById(C1283R.id.friendDetailsStatus)).setText(status);
        if (bSharedDriveWithMe) {
            View sharedLayout = findViewById(C1283R.id.friendDetailsSharedDriveLayout);
            sharedLayout.setVisibility(0);
            ((TextView) findViewById(C1283R.id.friendDetailsSharedDriveTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_FRIEND_SHARED_TITLE));
            if (!(this.mUser instanceof FriendUserData) || ((FriendUserData) this.mUser).mEtaSeconds < 0) {
                findViewById(C1283R.id.friendDetailsSharedDriveETA).setVisibility(8);
            } else {
                TimeZone tz = Calendar.getInstance().getTimeZone();
                DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this);
                timeFormat.setTimeZone(tz);
                String etaString = timeFormat.format(new Date(System.currentTimeMillis() + ((long) (((FriendUserData) this.mUser).mEtaSeconds * 1000))));
                ((TextView) findViewById(C1283R.id.friendDetailsSharedDriveETA)).setText(String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FRIEND_SHARED_ETA_PS), new Object[]{etaString}));
            }
            String meetingId = ((FriendUserData) this.mUser).mMeetingIdSharedWithMe;
            DriveToNativeManager.getInstance().getFriendsDriveData(new C28732(), meetingId);
            final String str = meetingId;
            sharedLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_PROFILE_SHARING_DRIVE_WITH_ME_CLICK, null, null);
                    Intent intent = new Intent(UserDetailsActivity.this, ShareDrivingFriendsActivity.class);
                    intent.putExtra("meeting", str);
                    intent.putExtra("user", UserDetailsActivity.this.mUser);
                    UserDetailsActivity.this.startActivityForResult(intent, 1003);
                }
            });
        }
        View callButton = findViewById(C1283R.id.friendDetailsCall);
        if (this.sPhoneNumer != null) {
            callButton.setVisibility(0);
            ((TextView) findViewById(C1283R.id.friendDetailsCallLabel)).setText(this.mNm.getLanguageString(DisplayStrings.DS_CALL));
            callButton.setOnClickListener(new C28754());
        } else {
            callButton.setVisibility(8);
        }
        View shareButton = findViewById(C1283R.id.friendDetailsShare);
        ((TextView) findViewById(C1283R.id.friendDetailsShareLabel)).setText(this.mNm.getLanguageString(DisplayStrings.DS_FRIEND_SHARE_ETA));
        if (bSharingMyDrive) {
            TextView sharedStatusLabel = (TextView) findViewById(C1283R.id.friendDetailsSharingDriveLabel);
            sharedStatusLabel.setText(this.mNm.getLanguageString(DisplayStrings.DS_FRIEND_SHARED_VIEWING_YOU));
            sharedStatusLabel.setVisibility(0);
            findViewById(C1283R.id.friendDetailsStatus).setVisibility(8);
        }
        shareButton.setOnClickListener(new C28795());
        TextView bottomButton = (TextView) findViewById(C1283R.id.friendDetailsBottomButton);
        this.mReceivedLocationsTitleText = (SettingsTitleText) findViewById(C1283R.id.friendDetailsReceivedLocationTitle);
        this.mReceivedLocation = (LinearLayout) findViewById(C1283R.id.friendDetailsReceivedLocationLayout);
        if ((this.mUser instanceof FriendUserData) && ((FriendUserData) this.mUser).mIsFriend) {
            ((TextView) findViewById(C1283R.id.friendDetailsMessageLabel)).setText(this.mNm.getLanguageString(DisplayStrings.DS_MESSAGE));
            ((TextView) findViewById(C1283R.id.friendDetailsBeepLabel)).setText(this.mNm.getLanguageString(238));
            findViewById(C1283R.id.friendDetailsBeepBeep).setOnClickListener(new C28836());
            findViewById(C1283R.id.friendDetailsMessage).setOnClickListener(new C28847());
            if (!bBeep) {
                findViewById(C1283R.id.friendDetailsBeepBeep).setVisibility(8);
            }
            if (!bMessage) {
                findViewById(C1283R.id.friendDetailsMessage).setVisibility(8);
            }
            this.mReceivedLocationsTitleText.setText(this.mNm.getLanguageString(DisplayStrings.DS_FRIEND_SHARED_LOCATIONS_TITLE));
            ((TextView) findViewById(C1283R.id.friendDetailsReceivedLocationPlaceholderText)).setText(String.format(this.mNm.getLanguageString(DisplayStrings.DS_FRIEND_SHARED_LOCATIONS_PLACEHOLDER_PS), new Object[]{this.mUser.getName()}));
            DriveToNativeManager.getInstance().getFriendsSharedPlaces(this.mUser.getID(), new C28858());
            findViewById(C1283R.id.friendDetailsMoreOptions).setOnClickListener(new OnClickListener() {

                class C28861 implements Adapter {
                    C28861() {
                    }

                    public int getCount() {
                        return 1;
                    }

                    public void onConfigItem(int position, ItemDetails item) {
                        item.setItem(bPending ? DisplayStrings.DS_CANCEL_FRIEND_REQUEST : 1234, (int) C1283R.drawable.list_icon_remove_red);
                    }

                    public void onClick(int itemId) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_REMOVE);
                        if (AppService.isNetworkAvailable()) {
                            UserDetailsActivity.this.removeFriend();
                        } else {
                            UserDetailsActivity.this.showErrorPopup(252);
                        }
                    }
                }

                public void onClick(View v) {
                    BottomSheet s = new BottomSheet(UserDetailsActivity.this, DisplayStrings.DS_FRIEND_ACTIONS_TITLE, Mode.COLUMN_TEXT_ICON);
                    s.setAdapter(new C28861());
                    s.show();
                }
            });
            bottomButton.setVisibility(8);
            return;
        }
        findViewById(C1283R.id.friendDetailsBeepBeep).setVisibility(8);
        findViewById(C1283R.id.friendDetailsMessage).setVisibility(8);
        this.mReceivedLocationsTitleText.setVisibility(8);
        this.mReceivedLocation.setVisibility(8);
        findViewById(C1283R.id.friendDetailsMoreOptions).setVisibility(4);
        bottomButton.setVisibility(0);
        if (this.mUser instanceof FriendUserData) {
            fud = (FriendUserData) this.mUser;
            if (fud.mIsPendingFriend) {
                bottomButton.setBackgroundResource(C1283R.drawable.button_red_bg);
                bottomButton.setTextColor(getResources().getColor(C1283R.color.White));
                bottomButton.setText(this.mNm.getLanguageString(DisplayStrings.DS_CANCEL_FRIEND_REQUEST));
                bottomButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_REMOVE);
                        if (AppService.isNetworkAvailable()) {
                            UserDetailsActivity.this.removeFriend();
                            UserDetailsActivity.this.finish();
                            return;
                        }
                        UserDetailsActivity.this.showErrorPopup(252);
                    }
                });
                return;
            } else if (fud.mIsPendingMy) {
                bottomButton.setBackgroundResource(C1283R.drawable.button_white_bg);
                bottomButton.setTextColor(getResources().getColor(C1283R.color.Light));
                bottomButton.setText(this.mNm.getLanguageString(DisplayStrings.DS_ADD_AS_A_FRIEND));
                bottomButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_INVITE);
                        if (AppService.isNetworkAvailable()) {
                            UserDetailsActivity.this.inviteFriend();
                            UserDetailsActivity.this.finish();
                            return;
                        }
                        UserDetailsActivity.this.showErrorPopup(252);
                    }
                });
                return;
            } else {
                return;
            }
        }
        bottomButton.setBackgroundResource(C1283R.drawable.button_white_bg);
        bottomButton.setTextColor(getResources().getColor(C1283R.color.Light));
        bottomButton.setText(this.mNm.getLanguageString(this.mUser.getIsOnWaze() ? DisplayStrings.DS_ADD_AS_A_FRIEND : DisplayStrings.DS_INVITE_TO_WAZE));
        bottomButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_INVITE);
                if (AppService.isNetworkAvailable()) {
                    UserDetailsActivity.this.inviteFriend();
                    UserDetailsActivity.this.finish();
                    return;
                }
                UserDetailsActivity.this.showErrorPopup(252);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        ImageRepository.instance.getImage(this.mImageUrl, 2, (ImageView) findViewById(C1283R.id.friendDetailsUserPic), null, this);
    }

    protected void populateReceivedLocations(AddressItem[] ai) {
        this.mReceivedLocation.removeAllViews();
        for (int i = 0; i < ai.length; i++) {
            View v = getSharedLocationsView(i, ai.length, ai[i]);
            this.mReceivedLocation.addView(v);
            LayoutParams p = v.getLayoutParams();
            p.height = (int) getResources().getDimension(C1283R.dimen.settingsItemHeight);
            v.setLayoutParams(p);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 65554 && resultCode == -1) {
            NativeManager.bToUploadContacts = true;
            AddressBookImpl.getInstance().performSync(true, null);
            if (NativeManager.getInstance().isNavigatingNTV()) {
                ShareUtility.OpenShareActivity(this, ShareType.ShareType_ShareDrive, null, null, new PersonBase[]{this.mUser});
            } else {
                startActivityForResult(new Intent(this, ShareHelpActivity.class), 1002);
            }
        } else if (resultCode == -1) {
            setResult(-1);
            finish();
        } else if (resultCode == 3) {
            if (requestCode == 1001) {
                setResult(3);
            } else {
                setResult(0);
            }
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showErrorPopup(int bodyTextDS) {
        MsgBox.openMessageBoxWithCallback(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(bodyTextDS), false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UserDetailsActivity.this.setResult(0);
                UserDetailsActivity.this.finish();
            }
        });
    }

    private void inviteFriend() {
        int[] ids = new int[]{this.mUser.getID()};
        String[] phone = new String[]{this.mUser.getPhone()};
        if (this.mUser.getIsOnWaze()) {
            MyWazeNativeManager.getInstance().sendSocialAddFriends(ids, 1, String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PS_ADDED), new Object[]{this.mUser.getName()}));
            return;
        }
        MyWazeNativeManager.getInstance().sendSocialInviteFriends(ids, phone, 1, String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PS_INVITED), new Object[]{this.mUser.getName()}));
    }

    private void removeFriend() {
        NativeManager nativeManager = NativeManager.getInstance();
        DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    UserDetailsActivity.this.reallyRemoveFriend(UserDetailsActivity.this.mUser);
                }
            }
        };
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(nativeManager.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_YOU_WANT_TO_REMOVE_PS_FROM_FRIENDS_LISTQ), new Object[]{this.mUser.getName()}), true, onClick, nativeManager.getLanguageString(DisplayStrings.DS_YES), nativeManager.getLanguageString(DisplayStrings.DS_NO), -1);
    }

    private void reallyRemoveFriend(PersonBase p) {
        MyWazeNativeManager.getInstance().sendSocialRemoveFriends(new int[]{p.getID()}, 1, String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PS_REMOVED), new Object[]{p.getName()}));
        setResult(201);
        finish();
    }

    private View getSharedLocationsView(int position, int total, final AddressItem ai) {
        WazeSettingsView view = new WazeSettingsView(this);
        view.setType(0);
        if (ai.getType() > 0) {
            view.setIcon((int) C1283R.drawable.list_icon_home);
        } else {
            view.setIcon((int) C1283R.drawable.list_icon_location);
        }
        view.setKeyText(ai.getTitle());
        String address = ai.getAddress();
        if (address == null || address.length() <= 0 || address.equals(ai.getTitle())) {
            view.setValueText(null);
        } else {
            view.setValueText(ai.getAddress());
        }
        view.setPosition(position, total);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DriveToNativeManager.getInstance().navigate(ai, UserDetailsActivity.this);
            }
        });
        return view;
    }

    public void navigateCallback(int rc) {
        Log.d(Logger.TAG, "navigateCallback:rc=" + rc);
        if (rc == 0) {
            setResult(-1);
            finish();
        }
    }

    public void onBackPressed() {
        if (this.mNameYourselfView != null) {
            this.mNameYourselfView.close();
            return;
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIEND_PROFILE_CLICK, "ACTION", "BACK");
        super.onBackPressed();
    }
}
