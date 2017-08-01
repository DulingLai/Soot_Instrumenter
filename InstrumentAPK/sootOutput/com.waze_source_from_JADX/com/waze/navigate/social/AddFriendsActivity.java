package com.waze.navigate.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.RequestPermissionActivity;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.Person;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.FacebookCallback;
import com.waze.mywaze.MyWazeNativeManager.FacebookSettings;
import com.waze.mywaze.MyWazeNativeManager.IFriendsChanged;
import com.waze.mywaze.social.FacebookActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.AddFriendsListener;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.PhoneRequestAccessDialog;
import com.waze.phone.PhoneRequestAccessDialog.PhoneRequestAccessResultListener;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.share.NameYourselfView;
import com.waze.share.NameYourselfView.NameYourselfViewListener;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.view.title.TitleBar;

public class AddFriendsActivity extends ActivityBase implements IFriendsChanged {
    private static final int ACTIVITY_RESULT_ADD_FROM_CONTACTS = 1001;
    private static final int ACTIVITY_RESULT_ADD_FROM_FB = 1003;
    private static final int ACTIVITY_RESULT_LOGIN_CONTACTS = 1002;
    private static final int ACTIVITY_RESULT_LOGIN_FB = 1004;
    private static final boolean SHOW_FRIENDS_ADDED_YOU = false;
    protected static final long TIMEOUT_BETWEEN_EVENTS = 100;
    private LinearLayout AddedFriendsLayout;
    private LayoutInflater inflater;
    protected AddFriendsData mAddFriendsData;
    private LinearLayout mFriendsSuggestionLayout;
    private NameYourselfView mNameYourselfView;
    private NativeManager nativeManager;
    private View viewToConfirm = null;
    private View viewToRemove = null;

    class C21764 implements OnClickListener {
        C21764() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ADD_FRIENDS_OPTIONS_CONTACTS_CLICK, null, null);
            if (MyWazeNativeManager.getInstance().getContactLoggedInNTV()) {
                AddFriendsActivity.this.connectWithContacts();
                return;
            }
            Intent $r2 = new Intent(AddFriendsActivity.this, PhoneRegisterActivity.class);
            $r2.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
            $r2.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
            AddFriendsActivity.this.startActivityForResult($r2, 1002);
        }
    }

    class C21775 implements OnClickListener {
        C21775() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ADD_FRIENDS_OPTIONS_FACEBOOK_CLICK, null, null);
            AddFriendsActivity.this.facebookPrivacyClicked();
        }
    }

    class C21786 implements OnClickListener {
        C21786() throws  {
        }

        public void onClick(View v) throws  {
        }
    }

    class C21797 implements AddFriendsListener {
        C21797() throws  {
        }

        public void onComplete(AddFriendsData $r1) throws  {
            AddFriendsActivity.this.mAddFriendsData = $r1;
            boolean $z0 = false;
            if (AddFriendsActivity.this.mAddFriendsData == null || AddFriendsActivity.this.mAddFriendsData.SuggestionFriends.length == 0) {
                AddFriendsActivity.this.findViewById(C1283R.id.FriendsSuggestionTitle).setVisibility(8);
                AddFriendsActivity.this.findViewById(C1283R.id.FriendsSuggestionLayout).setVisibility(8);
            } else {
                AddFriendsActivity.this.findViewById(C1283R.id.FriendsSuggestionTitle).setVisibility(0);
                AddFriendsActivity.this.findViewById(C1283R.id.FriendsSuggestionLayout).setVisibility(0);
                ((SettingsTitleText) AddFriendsActivity.this.findViewById(C1283R.id.FriendsSuggestionTitle)).setText(AddFriendsActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_FRIENDS_SUGGESTIONS));
                $z0 = true;
            }
            AddFriendsActivity.this.findViewById(C1283R.id.FriendsAddedYouTitle).setVisibility(8);
            AddFriendsActivity.this.findViewById(C1283R.id.FriendsAddedLayout).setVisibility(8);
            AddFriendsActivity.this.findViewById(C1283R.id.friendsAddedText).setVisibility(8);
            if ($z0) {
                AddFriendsActivity.this.endDrivePopulateFriends();
            }
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i0 == 1002 && $i1 == -1) {
            connectWithContacts();
        } else if ($i0 == 1004) {
            readFriendSuggestions();
        } else {
            if ($i1 == -1) {
                setResult(-1);
                finish();
            }
            if ($i1 == 201) {
                setResult(201);
                finish();
            }
            super.onActivityResult($i0, $i1, $r1);
        }
    }

    private void connectWithContacts() throws  {
        final Intent $r2 = new Intent(this, RequestPermissionActivity.class);
        $r2.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.READ_CONTACTS"});
        if (NativeManager.getInstance().IsAccessToContactsEnableNTV()) {
            final NameYourselfView[] $r3 = new NameYourselfView[1];
            NameYourselfView.showNameYourselfViewIfNeeded(this, $r3, new NameYourselfViewListener() {
                public void onNameYourselfClosed(boolean $z0) throws  {
                    if ($z0) {
                        Intent $r1 = new Intent(AddFriendsActivity.this, AddFromActivity.class);
                        $r1.putExtra(AddFromActivity.INTENT_FROM_WHERE, AddFromActivity.INTENT_FROM_DEFAULT);
                        $r2.putExtra(RequestPermissionActivity.INT_ON_GRANTED, $r1);
                        AddFriendsActivity.this.startActivityForResult($r2, 1001);
                    }
                    AddFriendsActivity.this.mNameYourselfView = null;
                }
            }, new Runnable() {
                public void run() throws  {
                    AddFriendsActivity.this.mNameYourselfView = $r3[0];
                }
            });
            return;
        }
        new PhoneRequestAccessDialog(this, new PhoneRequestAccessResultListener() {

            class C21731 implements NameYourselfViewListener {
                C21731() throws  {
                }

                public void onNameYourselfClosed(boolean $z0) throws  {
                    if ($z0) {
                        NativeManager.bToUploadContacts = true;
                        AddressBookImpl.getInstance().performSync(true, null);
                        Intent $r1 = new Intent(AddFriendsActivity.this, AddFromActivity.class);
                        $r1.putExtra(AddFromActivity.INTENT_FROM_WHERE, AddFromActivity.INTENT_FROM_DEFAULT);
                        $r2.putExtra(RequestPermissionActivity.INT_ON_GRANTED, $r1);
                        AddFriendsActivity.this.startActivityForResult($r2, 1001);
                    }
                    AddFriendsActivity.this.mNameYourselfView = null;
                }
            }

            public void onResult(boolean $z0) throws  {
                if ($z0) {
                    final NameYourselfView[] $r1 = new NameYourselfView[1];
                    NameYourselfView.showNameYourselfViewIfNeeded(AddFriendsActivity.this, $r1, new C21731(), new Runnable() {
                        public void run() throws  {
                            AddFriendsActivity.this.mNameYourselfView = $r1[0];
                        }
                    });
                }
            }
        }).show();
    }

    public void onBackPressed() throws  {
        if (this.mNameYourselfView != null) {
            this.mNameYourselfView.close();
        } else {
            super.onBackPressed();
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.inflater = (LayoutInflater) getSystemService("layout_inflater");
        this.nativeManager = NativeManager.getInstance();
        setContentView(C1283R.layout.add_friends);
        ((TitleBar) findViewById(C1283R.id.shareTitle)).init(this, DisplayStrings.DS_ADD_FRIENDS);
        this.AddedFriendsLayout = (LinearLayout) findViewById(C1283R.id.FriendsAddedLayout);
        this.mFriendsSuggestionLayout = (LinearLayout) findViewById(C1283R.id.FriendsSuggestionLayout);
        ((TextView) findViewById(C1283R.id.addFriendsBodyText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_ADD_FRIENDS_DESCRIPTION));
        ((WazeSettingsView) findViewById(C1283R.id.AddContactsButton)).setText(NativeManager.getInstance().getLanguageString(1226));
        findViewById(C1283R.id.AddContactsButton).setOnClickListener(new C21764());
        readFriendSuggestions();
        ((SettingsTitleText) findViewById(C1283R.id.MoreOptions)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MORE_OPTIONS));
        ((WazeSettingsView) findViewById(C1283R.id.AddFromFacebook)).setText(NativeManager.getInstance().getLanguageString(1227));
        findViewById(C1283R.id.AddFromFacebook).setOnClickListener(new C21775());
        findViewById(C1283R.id.SearchWazeUserName).setVisibility(8);
        ((WazeSettingsView) findViewById(C1283R.id.SearchWazeUserName)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_SEARCH));
        findViewById(C1283R.id.SearchWazeUserName).setOnClickListener(new C21786());
        MyWazeNativeManager.getInstance().addFriendsChangedListener(this);
    }

    private void readFriendSuggestions() throws  {
        DriveToNativeManager.getInstance().getAddFriendsData(new C21797());
    }

    protected void onDestroy() throws  {
        MyWazeNativeManager.getInstance().removeFriendsChangedListener(this);
        super.onDestroy();
    }

    private void endDrivePopulateFriends() throws  {
        if (this.mAddFriendsData != null) {
            this.mFriendsSuggestionLayout.removeAllViews();
            if (this.mAddFriendsData.SuggestionFriends.length > 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ADD_FRIENDS_OPTIONS_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, this.mAddFriendsData.SuggestionFriends.length);
            }
            for (FriendUserData $r4 : this.mAddFriendsData.SuggestionFriends) {
                RecommendedAddFriend($r4);
            }
            for (FriendUserData $r42 : this.mAddFriendsData.WaitingForApprovalFriends) {
                PendingAddFriend($r42);
            }
        }
    }

    private void RecommendedAddFriend(final FriendUserData $r1) throws  {
        final View $r3 = this.inflater.inflate(C1283R.layout.add_friends_in_list, null);
        if ($r1.mContactID != -1) {
            Person $r5 = AddressBookImpl.getInstance().GetPersonFromID($r1.mContactID);
            if (!($r5 == null || $r5.getImage() == null)) {
                $r1.setImage($r5.getImage());
            }
        }
        AddFriendsUtils.setNameAndImage(this, $r3, $r1.name, $r1.pictureUrl);
        $r3.findViewById(C1283R.id.RemoveFriendButton).setVisibility(8);
        $r3.findViewById(C1283R.id.AddFriendButton).setOnClickListener(new OnClickListener() {

            class C21801 implements NameYourselfViewListener {
                C21801() throws  {
                }

                public void onNameYourselfClosed(boolean $z0) throws  {
                    if ($z0) {
                        AddFriendsActivity.this.viewToConfirm = $r3;
                        MyWazeNativeManager.getInstance().sendSocialAddFriends(new int[]{$r1.getID()}, 1, String.format(AddFriendsActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_PS_ADDED), new Object[]{$r1.getName()}));
                    }
                    AddFriendsActivity.this.mNameYourselfView = null;
                }
            }

            public void onClick(View v) throws  {
                final NameYourselfView[] $r2 = new NameYourselfView[1];
                NameYourselfView.showNameYourselfViewIfNeeded(AddFriendsActivity.this, $r2, new C21801(), new Runnable() {
                    public void run() throws  {
                        AddFriendsActivity.this.mNameYourselfView = $r2[0];
                    }
                });
            }
        });
        this.mFriendsSuggestionLayout.addView($r3);
    }

    private void PendingAddFriend(final FriendUserData $r1) throws  {
        final View $r3 = this.inflater.inflate(C1283R.layout.add_friends_in_list, null);
        if ($r1.mContactID != -1) {
            Person $r5 = AddressBookImpl.getInstance().GetPersonFromID($r1.mContactID);
            if (!($r5 == null || $r5.getImage() == null)) {
                $r1.setImage($r5.getImage());
            }
        }
        AddFriendsUtils.setNameAndImage(this, $r3, $r1.name, $r1.pictureUrl);
        $r3.findViewById(C1283R.id.RemoveFriendButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                AddFriendsActivity.this.viewToRemove = $r3;
                MyWazeNativeManager.getInstance().sendSocialRemoveFriends(new int[]{$r1.getID()}, 1, String.format(AddFriendsActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_PS_REMOVED), new Object[]{$r1.getName()}));
            }
        });
        $r3.findViewById(C1283R.id.AddFriendButton).setOnClickListener(new OnClickListener() {

            class C21691 implements NameYourselfViewListener {
                C21691() throws  {
                }

                public void onNameYourselfClosed(boolean $z0) throws  {
                    if ($z0) {
                        AddFriendsActivity.this.viewToConfirm = $r3;
                        MyWazeNativeManager.getInstance().sendSocialAddFriends(new int[]{$r1.getID()}, 1, String.format(AddFriendsActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_PS_ADDED), new Object[]{$r1.getName()}));
                    }
                    AddFriendsActivity.this.mNameYourselfView = null;
                }
            }

            public void onClick(View v) throws  {
                final NameYourselfView[] $r2 = new NameYourselfView[1];
                NameYourselfView.showNameYourselfViewIfNeeded(AddFriendsActivity.this, $r2, new C21691(), new Runnable() {
                    public void run() throws  {
                        AddFriendsActivity.this.mNameYourselfView = $r2[0];
                    }
                });
            }
        });
        this.AddedFriendsLayout.addView($r3);
    }

    protected void facebookPrivacyClicked() throws  {
        MyWazeNativeManager.getInstance().getFacebookSettings(new FacebookCallback() {
            public void onFacebookSettings(FacebookSettings $r1) throws  {
                Intent $r2 = new Intent(AddFriendsActivity.this, FacebookActivity.class);
                $r2.putExtra("com.waze.mywaze.facebooksettings", $r1);
                AddFriendsActivity.this.startActivityForResult($r2, 1004);
            }
        });
    }

    public void onFriendsChanged() throws  {
        if (this.viewToRemove != null) {
            this.AddedFriendsLayout.removeView(this.viewToRemove);
            this.viewToRemove = null;
            if (this.AddedFriendsLayout.getChildCount() == 0) {
                findViewById(C1283R.id.FriendsAddedYouTitle).setVisibility(8);
                findViewById(C1283R.id.FriendsAddedLayout).setVisibility(8);
                findViewById(C1283R.id.friendsAddedText).setVisibility(8);
            }
        }
        if (this.viewToConfirm != null) {
            ImageView $r3 = (ImageView) this.viewToConfirm.findViewById(C1283R.id.AddFriendButton);
            $r3.setOnClickListener(null);
            $r3.setImageResource(C1283R.drawable.friends_pending_button);
            this.viewToConfirm.findViewById(C1283R.id.RemoveFriendButton).setVisibility(8);
            this.viewToConfirm = null;
        }
    }
}
