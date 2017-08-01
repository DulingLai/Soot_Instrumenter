package com.waze;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.menus.SideMenuSearchBar;
import com.waze.menus.SideMenuSearchBar.SearchBarActionListener;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.social.AddFriendsActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.PhoneRequestAccessDialog;
import com.waze.phone.PhoneRequestAccessDialog.PhoneRequestAccessResultListener;
import com.waze.social.FriendsSideMenuRecycler;
import com.waze.social.FriendsSideMenuRecycler.FriendRecyclerListener;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.title.TitleBar;

public class FriendsActivity extends ActivityBase implements SearchBarActionListener, FriendRecyclerListener {
    public static final int RS_CONNECT_WITH_CONTACTS = 401985;
    private ViewGroup mContactsButton;
    private ViewGroup mFacebookButton;
    private FriendsSideMenuRecycler mFriendsRecycler;
    private boolean mIsSearchingFriends;
    private ViewGroup mLoadingPopup;
    private ScrollView mNoFreindsScrollView;
    private ViewGroup mNoFriendsContainer;
    private SideMenuSearchBar mSearchBar;

    class C11271 implements OnClickListener {
        C11271() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FACEBOOK_CONNECT_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_SCREEN);
            FacebookManager.getInstance().loginWithFacebook(AppService.getActiveActivity(), FacebookLoginType.SetToken, true, true);
        }
    }

    class C11282 implements OnClickListener {
        C11282() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONNECT_CONTACTS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_SCREEN);
            FriendsActivity.OpenFriendsActivityOrLogin(FriendsActivity.this);
        }
    }

    class C11293 implements OnClickListener {
        C11293() throws  {
        }

        public void onClick(View v) throws  {
            FriendsActivity.this.onSearchBegin();
        }
    }

    class C11315 implements DialogInterface.OnClickListener {
        C11315() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
            FriendsActivity.this.setResult(-1);
            FriendsActivity.this.finish();
        }
    }

    public int getRequiredPadding() throws  {
        return 0;
    }

    public int getScreenHeight() throws  {
        return 0;
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.friends_menu);
        ((TitleBar) findViewById(C1283R.id.friendsTitle)).init(this, NativeManager.getInstance().getLanguageString(243));
        this.mNoFriendsContainer = (ViewGroup) findViewById(C1283R.id.noFriendsContainer);
        this.mNoFreindsScrollView = (ScrollView) findViewById(C1283R.id.noFriendsScrollView);
        this.mFriendsRecycler = (FriendsSideMenuRecycler) findViewById(C1283R.id.friendsRecycler);
        this.mSearchBar = (SideMenuSearchBar) findViewById(C1283R.id.friendsSearchBar);
        ViewGroup $r6 = (ViewGroup) findViewById(C1283R.id.backToTop);
        this.mSearchBar.setSpeechButtonVisibility(false);
        this.mSearchBar.setSearchBarActionListener(this);
        this.mSearchBar.setAddButtonVisibility(true);
        this.mSearchBar.disableFocus();
        this.mFriendsRecycler.setListener(this);
        this.mFriendsRecycler.setBackToTopContainer($r6);
        this.mFacebookButton = (ViewGroup) findViewById(C1283R.id.btnConnectWithFacebook);
        this.mContactsButton = (ViewGroup) findViewById(C1283R.id.btnConnectWithContacts);
        this.mLoadingPopup = (ViewGroup) findViewById(C1283R.id.loadingPopup);
        this.mFacebookButton.setOnClickListener(new C11271());
        this.mContactsButton.setOnClickListener(new C11282());
        this.mSearchBar.setOnClickListener(new C11293());
        initStrings();
        this.mFriendsRecycler.reloadData();
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_MENU_SHOWN).send();
    }

    public static void OpenFriendsActivityOrLogin(final ActivityBase $r0) throws  {
        if (!MyWazeNativeManager.getInstance().isShareAllowedNTV()) {
            Intent $r4 = new Intent($r0, PhoneRegisterActivity.class);
            $r4.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
            $r0.startActivityForResult($r4, RS_CONNECT_WITH_CONTACTS);
        } else if (NativeManager.getInstance().IsAccessToContactsEnableNTV()) {
            $r0.startActivityForResult(new Intent($r0, AddFriendsActivity.class), 1001);
        } else {
            new PhoneRequestAccessDialog($r0, new PhoneRequestAccessResultListener() {
                public void onResult(boolean $z0) throws  {
                    if ($z0) {
                        FriendsActivity.OpenFriendsActivityOrLogin($r0);
                    }
                }
            }).show();
        }
    }

    private void initStrings() throws  {
        NativeManager $r1 = NativeManager.getInstance();
        ((TextView) findViewById(C1283R.id.noFriendsText)).setText($r1.getLanguageString((int) DisplayStrings.DS_FRIENDS_LIST_EMPTY_TITLE));
        ((TextView) findViewById(C1283R.id.noFriendsConnectText)).setText($r1.getLanguageString((int) DisplayStrings.DS_FRIENDS_LIST_EMPTY_TEXT));
        ((TextView) findViewById(C1283R.id.btnConnectWithFacebookText)).setText($r1.getLanguageString((int) DisplayStrings.DS_FRIENDS_LIST_EMPTY_FACEBOOK_BUTTON));
        ((TextView) findViewById(C1283R.id.btnConnectWithContactsText)).setText($r1.getLanguageString((int) DisplayStrings.DS_FRIENDS_LIST_EMPTY_CONTACTS_BUTTON));
        ((TextView) findViewById(C1283R.id.backToTopText)).setText($r1.getLanguageString((int) DisplayStrings.DS_BACK_TO_TOP_BUTTON));
        this.mSearchBar.setHint($r1.getLanguageString((int) DisplayStrings.DS_FRIENDS_LIST_EMPTY_TITLE));
        this.mFriendsRecycler.initStrings();
    }

    protected void onPause() throws  {
        super.onPause();
        this.mSearchBar.hideKeyboard();
    }

    public void onCancelClick() throws  {
        this.mIsSearchingFriends = false;
        this.mSearchBar.hideCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        this.mSearchBar.disableFocus();
        this.mSearchBar.clearText();
        this.mFriendsRecycler.onSearchFinished();
    }

    public void onSpeechButtonClick() throws  {
    }

    public void onSearchTextChanged(String $r1) throws  {
        this.mFriendsRecycler.onSearchTermChange($r1);
    }

    public void onSearchButtonClick() throws  {
    }

    public void onAddClick() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_VALUE_ADD_FRIEND);
        NativeManager.getInstance().OpenAddFriends();
    }

    public void onNoFriendsLoaded() throws  {
        this.mFriendsRecycler.setVisibility(8);
        this.mNoFreindsScrollView.setTranslationX(0.0f);
        this.mNoFreindsScrollView.setVisibility(0);
    }

    public void onLoadFailed() throws  {
        MsgBox.openMessageBoxWithCallback(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false, new C11315());
    }

    public void onFriendsLoaded() throws  {
        if (this.mFriendsRecycler.getVisibility() == 8) {
            this.mFriendsRecycler.setVisibility(0);
            ViewPropertyAnimatorHelper.initAnimation(this.mNoFreindsScrollView).translationX((float) this.mNoFreindsScrollView.getMeasuredWidth()).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mNoFreindsScrollView));
        }
    }

    public void setFriendsProgressVisiblity(boolean $z0) throws  {
        if ($z0 && this.mLoadingPopup.getVisibility() != 0) {
            this.mLoadingPopup.setVisibility(0);
            this.mLoadingPopup.setScaleX(0.0f);
            this.mLoadingPopup.setScaleY(0.0f);
            this.mLoadingPopup.setAlpha(0.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mLoadingPopup).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setListener(null);
        } else if (!$z0 && this.mLoadingPopup.getVisibility() == 0) {
            ViewPropertyAnimatorHelper.initAnimation(this.mLoadingPopup).scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mLoadingPopup));
        }
    }

    public void onSearchBegin() throws  {
        this.mIsSearchingFriends = true;
        this.mSearchBar.showCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        this.mSearchBar.enableFocus(false);
    }

    public boolean isSearchingFriends() throws  {
        return this.mIsSearchingFriends;
    }

    public void hideKeyboard() throws  {
        this.mSearchBar.hideKeyboard();
    }

    public String getSearchTerm() throws  {
        return this.mSearchBar.getSearchTerm();
    }
}
