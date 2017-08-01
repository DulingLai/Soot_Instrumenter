package com.waze.mywaze;

import android.app.Activity;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.waze.AppService;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.WazeBackupAgent;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.async.UpdateHandlers;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.VideoActivity;
import com.waze.inbox.InboxNativeManager;
import com.waze.install.GuidedTourActivity;
import com.waze.install.InstallNativeManager;
import com.waze.mywaze.moods.MoodsActivity;
import com.waze.mywaze.social.FacebookActivity;
import com.waze.mywaze.social.FoursquareActivity;
import com.waze.mywaze.social.FoursquareCheckedinActivity;
import com.waze.mywaze.social.FoursquareListActivity;
import com.waze.mywaze.social.LinkedInActivity;
import com.waze.mywaze.social.SocialActivity;
import com.waze.mywaze.social.TwitterActivity;
import com.waze.phone.LoginOptionsActivity;
import com.waze.phone.PhoneAlreadyAWazerActivity;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.PhoneValidationCodeActivity;
import com.waze.phone.PhoneVerifyYourAccountFailureActivity;
import com.waze.phone.PhoneVerifyYourNumbersActivity;
import com.waze.planned_drive.PlannedDriveListActivity;
import com.waze.profile.MapCarItem;
import com.waze.profile.MinimalSignInActivity;
import com.waze.profile.ProfileLauncher;
import com.waze.profile.RegisterActivity;
import com.waze.profile.SignInActivity;
import com.waze.profile.WelcomeActivity;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class MyWazeNativeManager {
    public static final int FB_CARPOOL_USER_ALREADY_IN_USE = 6;
    public static final String FB_CONNECTED = "FBconnected";
    public static final String FB_CONNECT_ERROR_REASON = "FBErrorReason";
    private static final String PREF_LAST_PHONE = "socialContactsLastPhoneUsed";
    private static final String PREF_PHONE_PREFIX = "socialContactsPhoneRecovery_";
    public static int UH_FACEBOOK_CONNECT = TicketRoller.get(Type.Handler);
    public static int UH_GOOGLE_CONNECT = TicketRoller.get(Type.Handler);
    private static MyWazeNativeManager instance;
    private UpdateHandlers handlers = new UpdateHandlers();
    private MyWazeData mConflictingDataUser = new MyWazeData();
    ArrayList<IFriendsChanged> mFriendsChangedListeners = new ArrayList(4);
    private boolean mIsInvisible = false;
    private boolean mWasFbTokenSet = false;
    private boolean mWasLoginSuccess = false;
    private MapCarsCallback mapCarsCallback = null;
    private LoginCallback pendingLogin;
    private RegisterActivity pendingRegister;

    public interface FacebookCallback {
        void onFacebookSettings(FacebookSettings facebookSettings) throws ;
    }

    public interface MyWazeDataHandler {
        void onMyWazeDataReceived(MyWazeData myWazeData) throws ;
    }

    public static abstract class LoginCallback {
        public abstract void onLogin(boolean z) throws ;
    }

    public interface ProfileCallback {
        void onProfile(ProfileSettings profileSettings) throws ;
    }

    public interface UrlCallback {
        void onUrl(String str) throws ;
    }

    public interface ISetUserUpdateResult {
        void SetUserUpdateResult(boolean z) throws ;
    }

    public interface ActiveGroupListener {
        void onComplete(Group group) throws ;
    }

    public static class FacebookSettings implements Serializable {
        private static final long serialVersionUID = 1;
        public boolean loggedIn;
    }

    public interface FoursquareCallback {
        void onFoursquareSettings(FoursquareSettings foursquareSettings) throws ;
    }

    public static class FoursquareSettings implements Serializable {
        private static final long serialVersionUID = 1;
        public boolean checkInAfterLogin;
        public boolean enableTweetBadge;
        public boolean enableTweetLogin;
        public boolean loggedIn;
    }

    public interface GetGroupsListener {
        void onComplete(Group[] groupArr) throws ;
    }

    public interface IFriendsChanged {
        void onFriendsChanged() throws ;
    }

    public interface LinkedinCallback {
        void onLinkedinSettings(LinkedinSettings linkedinSettings) throws ;
    }

    public static class LinkedinSettings implements Serializable {
        private static final long serialVersionUID = 1;
        public boolean loggedIn;
    }

    public interface MapCarsCallback {
        void onMapCars(MapCarItem[] mapCarItemArr, String str) throws ;
    }

    public interface PrivacyCallback {
        void onPrivacySettings(PrivacySettings privacySettings) throws ;
    }

    public static class PrivacySettings {
        public int twitterShowProf;
        public int visibility;
    }

    public static class ProfileSettings {
        public boolean allowPings;
        public boolean guestUser;
        public String nickName;
        public String password;
        public String userName;
    }

    public interface TwitterCallback {
        void onTwitterSettings(TwitterSettings twitterSettings) throws ;
    }

    public static class TwitterSettings implements Serializable {
        private static final long serialVersionUID = 1;
        public boolean loggedIn;
        public int postDriving;
        public boolean postMunching;
        public boolean postReports;
        public boolean showMunching;
    }

    private enum UrlSelector {
        URL_GROUPS,
        URL_SCOREBOARD,
        URL_COUPONS,
        URL_FACEBOOKCONNECT,
        URL_FOURSQUARECONNECT,
        URL_FORGOTPASSWORD,
        URL_FACEBOOKSHARE,
        URL_FACEBOOKLIKE,
        URL_TWITTERFOLLOW,
        URL_TWITTERCONNECT,
        URL_TWITTERCONNECTPLUSINITIAL,
        URL_NOTIFICATIONSETTINGS,
        URL_LINKEDINCONNECT
    }

    public interface UserCarCallback {
        void onUserCar(String str) throws ;
    }

    private native void GoogleDisconnectNowNTV() throws ;

    private native void ImportFacebookNTV() throws ;

    private native void SetInvisibleNTV(boolean z) throws ;

    private native void ShowUserScreenIfNeededNTV() throws ;

    private native void addBrandNTV(String str) throws ;

    private native void afterConnectToFacebookNTV() throws ;

    private native void afterConnectToFoursquareNTV() throws ;

    private native void afterConnectToLinkedinNTV() throws ;

    private native void afterConnectToTwitterNTV() throws ;

    private native void authorizePublishCallbackNTV(boolean z) throws ;

    private native void completeSignupNTV() throws ;

    private native void doLoginNTV(String str, String str2, String str3) throws ;

    private native void doLoginOkNTV(String str, String str2, String str3, boolean z) throws ;

    private native void facebookDisconnectNTV() throws ;

    private native void facebookDisconnectNowNTV() throws ;

    private native void facebookResolveConflictNTV() throws ;

    private native void foursquareCheckinNTV() throws ;

    private native void foursquareDisconnectNTV() throws ;

    private native void foursquareSetTweetBadgeNTV(boolean z) throws ;

    private native void foursquareSetTweetLoginNTV(boolean z) throws ;

    private native Group getActiveGroupNTV() throws ;

    private native boolean getAllowPMNTV() throws ;

    private native boolean getAllowPingsNTV() throws ;

    private native String getDisplayNameNTV() throws ;

    private native String getEmailNTV() throws ;

    private native String getFaceBookNickNTV() throws ;

    private native String getFirstNameNTV() throws ;

    private native boolean getFoursquareEnableTweetBadgeNTV() throws ;

    private native boolean getFoursquareEnableTweetLoginNTV() throws ;

    private native boolean getFoursquareLoggedInNTV() throws ;

    private native Group[] getGroupsNTV() throws ;

    private native String getJoinedStrNTV() throws ;

    private native String getLastNameNTV() throws ;

    private native String getLastShareDriveURLNTV() throws ;

    private native MapCarItem[] getMapCarsNTV() throws ;

    private native String getNickNameNTV() throws ;

    private native int getNumberOfFriendsNTV() throws ;

    private native int getNumberOfFriendsOnlineNTV() throws ;

    private native int getNumberOfFriendsPendingNTV() throws ;

    private native String getPasswordNTV() throws ;

    private native String getSocialJoinedStrNTV() throws ;

    private native String getSocialMoodNTV() throws ;

    private native String getSocialUserNameNTV() throws ;

    private native boolean getTwitterLoggedInNTV() throws ;

    private native String getTwitterPasswordNTV() throws ;

    private native int getTwitterPostDrivingModeNTV() throws ;

    private native boolean getTwitterPostMunchingNTV() throws ;

    private native boolean getTwitterPostReportsNTV() throws ;

    private native boolean getTwitterShowMunchingNTV() throws ;

    private native int getTwitterShowProfNTV() throws ;

    private native String getTwitterUsernameNTV() throws ;

    private native String getUrlNTV(int i, int i2, int i3) throws ;

    private native String getUserCarNTV() throws ;

    private native String getUserNameNTV() throws ;

    private native int getUserVisibilityNTV() throws ;

    private native String getWelcomeNameNTV() throws ;

    private native void initNTV() throws ;

    private native boolean isOptedInNTV(String str) throws ;

    private native void linkedinDisconnectNTV() throws ;

    private native boolean myCouponsEnabledNTV() throws ;

    private native void registerUserNTV(String str, String str2, String str3, String str4, boolean z) throws ;

    private native void reloadAllUserStoresNTV() throws ;

    private native void removeBrandNTV(String str) throws ;

    private native void selectFoursquareVenueNTV(int i) throws ;

    private native void sendAdBrandStatsNTV(String str, String str2, String str3, int i) throws ;

    private native void sendSocialAddFriendsNTV(int[] iArr, int i, String str) throws ;

    private native void sendSocialInviteFriendsNTV(int[] iArr, String[] strArr, int i, String str) throws ;

    private native void sendSocialRemoveFriendsNTV(int[] iArr, int i, String str) throws ;

    private native void setAllowPMNTV(boolean z) throws ;

    private native void setAllowPingsNTV(boolean z) throws ;

    private native void setContactSignInNTV(boolean z, boolean z2, String str, String str2) throws ;

    private native void setFacebookSignInNTV(String str) throws ;

    private native void setFacebookTokenNTV(String str, long j, boolean z) throws ;

    private native void setGoogleSignInNTV(boolean z, boolean z2, String str, String str2, String str3) throws ;

    private native void setNamesNTV(String str, String str2, String str3, String str4, String str5) throws ;

    private native void setSelectedGroupNTV(String str, String str2) throws ;

    private native void setTwitterShowProfNTV(int i) throws ;

    private native void setUserCarNTV(String str) throws ;

    private native void setUserVisibilityNTV(int i) throws ;

    private native void setVisibilityNTV() throws ;

    private native void skipSignupNTV() throws ;

    private native void twitterConnectNTV(String str, String str2) throws ;

    private native void twitterDisconnectNTV() throws ;

    private native void twitterSetDrivingModeNTV(int i) throws ;

    private native void twitterSetMunchingModeNTV(boolean z) throws ;

    private native void twitterSetPasswordNTV(String str) throws ;

    private native void twitterSetReportModeNTV(boolean z) throws ;

    private native void twitterSetUsernameNTV(String str) throws ;

    private native void updateFacebookTokenNTV(String str, long j) throws ;

    private native void updateTwitterNTV(String str, String str2, boolean z) throws ;

    private native boolean validateNicknameNTV(String str) throws ;

    private native boolean validatePasswordNTV(String str) throws ;

    private native boolean validateUserNTV(String str, String str2, String str3, String str4, String str5, boolean z) throws ;

    public native boolean FacebookEnabledNTV() throws ;

    public native boolean FoursquareEnabledNTV() throws ;

    public native boolean GroupsEnabledNTV() throws ;

    public native boolean HasSocialInfoNTV() throws ;

    public native boolean LinkedinEnabledNTV() throws ;

    public native boolean MarketEnabledNTV() throws ;

    public native boolean TwitterEnabledNTV() throws ;

    public native MyStoreModel[] getAllUserStoresFromCacheNTV() throws ;

    public native boolean getContactLoggedInNTV() throws ;

    public native boolean getFacebookLoggedInNTV() throws ;

    public native String getFacebookProfileUrlNTV() throws ;

    public native boolean getInvisibleNTV() throws ;

    public native String getLearnMorePrivacyUrlNTV() throws ;

    public native boolean getLinkedinLoggedInNTV() throws ;

    public native String getNameNTV() throws ;

    public native MyStoreModel[] getNearbyStoresNTV() throws ;

    public native String getPhoneNumberNTV() throws ;

    public native int getPointsNTV() throws ;

    public native int getRankNTV() throws ;

    public native String getRealUserNameNTV() throws ;

    public native int getSocialPointsNTV() throws ;

    public native int getSocialRankNTV() throws ;

    public native String getUserImageUrlNTV() throws ;

    public native int getUserTypeNTV() throws ;

    public native boolean isGuestUserNTV() throws ;

    public native boolean isJustJoinedNTV() throws ;

    public native boolean isRandomUserNTV() throws ;

    public native void setFirstLastNamesNTV(String str, String str2) throws ;

    public native void setGuestUserNTV(boolean z) throws ;

    public void setUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.setUpdateHandler($i0, $r1);
    }

    public void unsetUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.unsetUpdateHandler($i0, $r1);
    }

    private MyWazeNativeManager() throws  {
        initNTV();
    }

    public static MyWazeNativeManager getInstance() throws  {
        if (instance == null) {
            instance = new MyWazeNativeManager();
        }
        return instance;
    }

    public void getGroupsUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_GROUPS, $r1, $i0, $i1);
    }

    public void getNotificationSettingsUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_NOTIFICATIONSETTINGS, $r1, $i0, $i1);
    }

    public void getScoreboardUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_SCOREBOARD, $r1, $i0, $i1);
    }

    public void getCouponsUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_COUPONS, $r1, $i0, $i1);
    }

    public void getFacebookConnectUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_FACEBOOKCONNECT, $r1, $i0, $i1);
    }

    public void getFoursquareConnectUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_FOURSQUARECONNECT, $r1, $i0, $i1);
    }

    public void getLinkedinConnectUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_LINKEDINCONNECT, $r1, $i0, $i1);
    }

    public void getTwitterConnectUrl(UrlCallback $r1, int $i0, int $i1, boolean $z0) throws  {
        if ($z0) {
            getUrl(UrlSelector.URL_TWITTERCONNECTPLUSINITIAL, $r1, $i0, $i1);
        } else {
            getUrl(UrlSelector.URL_TWITTERCONNECT, $r1, $i0, $i1);
        }
    }

    public void getForgotPasswordUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_FORGOTPASSWORD, $r1, $i0, $i1);
    }

    public void getFacebookShareUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_FACEBOOKSHARE, $r1, $i0, $i1);
    }

    public void getFacebookLikeUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_FACEBOOKLIKE, $r1, $i0, $i1);
    }

    public void getTwitterFollowUrl(UrlCallback $r1, int $i0, int $i1) throws  {
        getUrl(UrlSelector.URL_TWITTERFOLLOW, $r1, $i0, $i1);
    }

    public String GetLastShareURL() throws  {
        return getLastShareDriveURLNTV();
    }

    public void getProfileSettings(final ProfileCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            ProfileSettings profile;

            public void event() throws  {
                Log.d(Logger.TAG, "ProfileSettings - event");
                this.profile = new ProfileSettings();
                this.profile.userName = MyWazeNativeManager.this.getUserNameNTV();
                this.profile.password = MyWazeNativeManager.this.getPasswordNTV();
                this.profile.nickName = MyWazeNativeManager.this.getNickNameNTV();
                this.profile.allowPings = MyWazeNativeManager.this.getAllowPingsNTV();
                this.profile.guestUser = MyWazeNativeManager.this.isGuestUserNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "ProfileSettings - callback");
                $r1.onProfile(this.profile);
            }
        });
    }

    public void GetAllowPing(final SocialActivity $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            boolean AllowPing;

            public void event() throws  {
                Log.d(Logger.TAG, "ProfileSettings - event");
                this.AllowPing = MyWazeNativeManager.this.getAllowPingsNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "ProfileSettings - callback");
                $r1.SetAllowPing(this.AllowPing);
            }
        });
    }

    public void GetAllowPM(final SocialActivity $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            boolean AllowPing;

            public void event() throws  {
                Log.d(Logger.TAG, "ProfileSettings - event");
                this.AllowPing = MyWazeNativeManager.this.getAllowPMNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "ProfileSettings - callback");
                $r1.SetAllowPM(this.AllowPing);
            }
        });
    }

    public void getFacebookSettings(final FacebookCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            FacebookSettings settings;

            public void event() throws  {
                Log.d(Logger.TAG, "getFacebookSettings - event");
                this.settings = MyWazeNativeManager.this.getFacebookSettings();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "getFacebookSettings - callback");
                $r1.onFacebookSettings(this.settings);
            }
        });
    }

    private FacebookSettings getFacebookSettings() throws  {
        FacebookSettings $r1 = new FacebookSettings();
        $r1.loggedIn = getFacebookLoggedInNTV();
        return $r1;
    }

    public String GetDisplayName() throws  {
        return getDisplayNameNTV();
    }

    public String getWelcomeName() throws  {
        return getWelcomeNameNTV();
    }

    public void getMyWazeData(final ActivityBase $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            MyWazeData Data;

            public void event() throws  {
                Log.d(Logger.TAG, "getFacebookSettings - event");
                this.Data = new MyWazeData();
                this.Data.mFaceBookLoggedIn = MyWazeNativeManager.this.getFacebookLoggedInNTV();
                this.Data.mContactsLoggedIn = MyWazeNativeManager.this.getContactLoggedInNTV();
                this.Data.mImageUrl = MyWazeNativeManager.this.getUserImageUrlNTV();
                this.Data.mRank = MyWazeNativeManager.this.getRankNTV();
                this.Data.mPts = MyWazeNativeManager.this.getPointsNTV();
                this.Data.mUserName = MyWazeNativeManager.this.getUserNameNTV();
                this.Data.mNickName = MyWazeNativeManager.this.getNickNameNTV();
                this.Data.mFaceBookNickName = MyWazeNativeManager.this.getFaceBookNickNTV();
                this.Data.mJoinedStr = MyWazeNativeManager.this.getJoinedStrNTV();
                this.Data.nFriendsOnline = MyWazeNativeManager.this.getNumberOfFriendsOnlineNTV();
                this.Data.mFirstName = MyWazeNativeManager.this.getFirstNameNTV();
                this.Data.mLastName = MyWazeNativeManager.this.getLastNameNTV();
                this.Data.mMood = MyWazeNativeManager.this.getSocialMoodNTV();
                this.Data.mPhoneNumber = MyWazeNativeManager.this.getPhoneNumberNTV();
                this.Data.mPassword = MyWazeNativeManager.this.getPasswordNTV();
                this.Data.mEmail = MyWazeNativeManager.this.getEmailNTV();
                this.Data.mUnreadEmails = InboxNativeManager.getInstance().getInboxUnreadNTV();
                this.Data.mNewEmails = InboxNativeManager.getInstance().getInboxBadgeNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "getFacebookSettings - callback");
                $r1.SetMyWazeData(this.Data);
            }
        });
    }

    public void getMyWazeData(final MyWazeDataHandler $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            MyWazeData Data;

            public void event() throws  {
                Log.d(Logger.TAG, "getFacebookSettings - event");
                this.Data = new MyWazeData();
                this.Data.mFaceBookLoggedIn = MyWazeNativeManager.this.getFacebookLoggedInNTV();
                this.Data.mContactsLoggedIn = MyWazeNativeManager.this.getContactLoggedInNTV();
                this.Data.mImageUrl = MyWazeNativeManager.this.getUserImageUrlNTV();
                this.Data.mRank = MyWazeNativeManager.this.getRankNTV();
                this.Data.mPts = MyWazeNativeManager.this.getPointsNTV();
                this.Data.mUserName = MyWazeNativeManager.this.getUserNameNTV();
                this.Data.mNickName = MyWazeNativeManager.this.getNickNameNTV();
                this.Data.mFaceBookNickName = MyWazeNativeManager.this.getFaceBookNickNTV();
                this.Data.mJoinedStr = MyWazeNativeManager.this.getJoinedStrNTV();
                this.Data.nFriendsOnline = MyWazeNativeManager.this.getNumberOfFriendsOnlineNTV();
                this.Data.mFirstName = MyWazeNativeManager.this.getFirstNameNTV();
                this.Data.mLastName = MyWazeNativeManager.this.getLastNameNTV();
                this.Data.mMood = MyWazeNativeManager.this.getSocialMoodNTV();
                this.Data.mPhoneNumber = MyWazeNativeManager.this.getPhoneNumberNTV();
                this.Data.mPassword = MyWazeNativeManager.this.getPasswordNTV();
                this.Data.mEmail = MyWazeNativeManager.this.getEmailNTV();
                this.Data.mUnreadEmails = InboxNativeManager.getInstance().getInboxUnreadNTV();
                this.Data.mNewEmails = InboxNativeManager.getInstance().getInboxBadgeNTV();
            }

            public void callback() throws  {
                $r1.onMyWazeDataReceived(this.Data);
            }
        });
    }

    public String getRegisteredPhoneNumber() throws  {
        return getPhoneNumberNTV();
    }

    public void getMyWazeDataForVerification(final ActivityBase $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            MyWazeData Data;

            public void event() throws  {
                Log.d(Logger.TAG, "getFacebookSettings - event");
                this.Data = new MyWazeData();
                this.Data.mRank = MyWazeNativeManager.this.getSocialRankNTV();
                this.Data.mPts = MyWazeNativeManager.this.getSocialPointsNTV();
                this.Data.mUserName = MyWazeNativeManager.this.getSocialUserNameNTV();
                this.Data.mJoinedStr = MyWazeNativeManager.this.getSocialJoinedStrNTV();
                this.Data.mMood = MyWazeNativeManager.this.getSocialMoodNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "getFacebookSettings - callback");
                $r1.SetMyWazeData(this.Data);
            }
        });
    }

    public int getNumberOfFriendsOnline() throws  {
        return getNumberOfFriendsOnlineNTV();
    }

    public int getNumberOfFriendsPending() throws  {
        return getNumberOfFriendsPendingNTV();
    }

    public int getNumberOfFriends() throws  {
        return getNumberOfFriendsNTV();
    }

    public MyWazeData getMyConflictingUserData() throws  {
        return this.mConflictingDataUser;
    }

    public void getTwitterSettings(final TwitterCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            TwitterSettings settings;

            public void event() throws  {
                Log.d(Logger.TAG, "getTwitterSettings - event");
                this.settings = new TwitterSettings();
                this.settings.loggedIn = MyWazeNativeManager.this.getTwitterLoggedInNTV();
                this.settings.postReports = MyWazeNativeManager.this.getTwitterPostReportsNTV();
                this.settings.postDriving = MyWazeNativeManager.this.getTwitterPostDrivingModeNTV();
                this.settings.postMunching = MyWazeNativeManager.this.getTwitterPostMunchingNTV();
                this.settings.showMunching = MyWazeNativeManager.this.getTwitterShowMunchingNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "getTwitterSettings - callback");
                $r1.onTwitterSettings(this.settings);
            }
        });
    }

    public void getFoursquareSettings(final FoursquareCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            FoursquareSettings settings;

            public void event() throws  {
                Log.d(Logger.TAG, "getFoursquareSettings - event");
                this.settings = new FoursquareSettings();
                this.settings.loggedIn = MyWazeNativeManager.this.getFoursquareLoggedInNTV();
                this.settings.enableTweetLogin = MyWazeNativeManager.this.getFoursquareEnableTweetLoginNTV();
                this.settings.enableTweetBadge = MyWazeNativeManager.this.getFoursquareEnableTweetBadgeNTV();
                this.settings.checkInAfterLogin = false;
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "getFoursquareSettings - callback");
                $r1.onFoursquareSettings(this.settings);
            }
        });
    }

    public void getLinkedinSettings(final LinkedinCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            LinkedinSettings settings;

            public void event() throws  {
                Log.d(Logger.TAG, "getLinkedinSettings - event");
                this.settings = new LinkedinSettings();
                this.settings.loggedIn = MyWazeNativeManager.this.getLinkedinLoggedInNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "getLinkedinSettings - callback");
                $r1.onLinkedinSettings(this.settings);
            }
        });
    }

    public void openFoursquareDialog() throws  {
        final FoursquareSettings $r1 = new FoursquareSettings();
        $r1.loggedIn = getFoursquareLoggedInNTV();
        $r1.enableTweetLogin = getFoursquareEnableTweetLoginNTV();
        $r1.enableTweetBadge = getFoursquareEnableTweetBadgeNTV();
        $r1.checkInAfterLogin = true;
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r3 = AppService.getActiveActivity();
                if ($r3 != null) {
                    Intent $r1 = new Intent($r3, FoursquareActivity.class);
                    $r1.putExtra("com.waze.mywaze.foursquaresettings", $r1);
                    $r3.startActivityForResult($r1, 0);
                }
            }
        });
    }

    public void openLinkedinDialog() throws  {
        final LinkedinSettings $r1 = new LinkedinSettings();
        $r1.loggedIn = getLinkedinLoggedInNTV();
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r3 = AppService.getActiveActivity();
                if ($r3 != null) {
                    Intent $r1 = new Intent($r3, LinkedInActivity.class);
                    $r1.putExtra("com.waze.mywaze.linkedinsettings", $r1);
                    $r3.startActivityForResult($r1, 0);
                }
            }
        });
    }

    public void getActiveGroup(final ActiveGroupListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            Group group;

            public void event() throws  {
                this.group = MyWazeNativeManager.this.getActiveGroupNTV();
            }

            public void callback() throws  {
                $r1.onComplete(this.group);
            }
        });
    }

    public void getGroups(final GetGroupsListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            Group[] groups;

            public void event() throws  {
                this.groups = MyWazeNativeManager.this.getGroupsNTV();
            }

            public void callback() throws  {
                $r1.onComplete(this.groups);
            }
        });
    }

    public void getUserCar(final UserCarCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            String selection;

            public void event() throws  {
                this.selection = MyWazeNativeManager.this.getUserCarNTV();
            }

            public void callback() throws  {
                $r1.onUserCar(this.selection);
            }
        });
    }

    public void registerMapCarsDataCallback(MapCarsCallback $r1) throws  {
        this.mapCarsCallback = $r1;
    }

    public void unregisterMapCarsDataCallback() throws  {
        this.mapCarsCallback = null;
    }

    public void getMapCars(final MapCarsCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            String carIdCurrent;
            MapCarItem[] carItems;

            public void event() throws  {
                this.carIdCurrent = MyWazeNativeManager.this.getUserCarNTV();
                this.carItems = MyWazeNativeManager.this.getMapCarsNTV();
            }

            public void callback() throws  {
                $r1.onMapCars(this.carItems, this.carIdCurrent);
            }
        });
    }

    private void updateMapCarsData() throws  {
        if (this.mapCarsCallback != null) {
            final String $r2 = getUserCarNTV();
            final MapCarItem[] $r3 = getMapCarsNTV();
            AppService.Post(new Runnable() {
                public void run() throws  {
                    MyWazeNativeManager.this.mapCarsCallback.onMapCars($r3, $r2);
                }
            });
        }
    }

    public void setUserCar(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setUserCarNTV($r1);
            }
        });
    }

    public void setNames(final String $r1, final String $r2, final String $r3, final String $r4, final String $r5) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setNamesNTV($r1, $r2, $r3, $r4, $r5);
            }
        });
    }

    public void setInvisible(final boolean $z0) throws  {
        this.mIsInvisible = $z0;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.SetInvisibleNTV($z0);
            }
        });
    }

    public boolean GetInvisible() throws  {
        return this.mIsInvisible;
    }

    public void setSelectedGroup(final Group $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setSelectedGroupNTV($r1.name, $r1.icon);
            }
        });
    }

    public boolean validateNickname(String $r1) throws  {
        return validateNicknameNTV($r1);
    }

    public boolean validatePassword(String $r1) throws  {
        return validatePasswordNTV($r1);
    }

    public void doLoginOk(final String $r1, final String $r2, final String $r3, final boolean $z0, LoginCallback $r4) throws  {
        this.pendingLogin = $r4;
        AppService.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.doLoginOkNTV($r1, $r2, $r3, $z0);
            }
        });
    }

    public void onLoginFailed() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (MyWazeNativeManager.this.pendingLogin != null) {
                    MyWazeNativeManager.this.pendingLogin.onLogin(false);
                    MyWazeNativeManager.this.pendingLogin = null;
                }
            }
        });
    }

    public void onLoginSuccess() throws  {
        this.mWasLoginSuccess = true;
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (MyWazeNativeManager.this.pendingLogin != null) {
                    MyWazeNativeManager.this.pendingLogin.onLogin(true);
                    MyWazeNativeManager.this.pendingLogin = null;
                }
            }
        });
    }

    public boolean getWasLoginSuccess() throws  {
        return this.mWasLoginSuccess;
    }

    public void doLogin(final String $r1, final String $r2, final String $r3, LoginCallback $r4) throws  {
        this.pendingLogin = $r4;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.doLoginNTV($r1, $r2, $r3);
            }
        });
    }

    private void getUrl(UrlSelector $r1, UrlCallback $r2, int $i0, int $i1) throws  {
        final UrlSelector urlSelector = $r1;
        final int i = $i0;
        final int i2 = $i1;
        final UrlCallback urlCallback = $r2;
        NativeManager.Post(new RunnableUICallback() {
            String url;

            public void event() throws  {
                Log.d(Logger.TAG, "getGroupsEvent - event");
                this.url = MyWazeNativeManager.this.getUrlNTV(urlSelector.ordinal(), i, i2);
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "getGroupsEvent - callback");
                urlCallback.onUrl(this.url);
            }
        });
    }

    public void getPrivacySettings(final PrivacyCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            PrivacySettings settings;

            public void event() throws  {
                Log.d(Logger.TAG, "PrivacySettings - event");
                this.settings = new PrivacySettings();
                this.settings.visibility = MyWazeNativeManager.this.getUserVisibilityNTV();
                this.settings.twitterShowProf = MyWazeNativeManager.this.getTwitterShowProfNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "ProfileSettings - callback");
                $r1.onPrivacySettings(this.settings);
            }
        });
    }

    public void launchMyWaze(final Context $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            boolean couponsEnabled;

            public void event() throws  {
                Log.d(Logger.TAG, "launchMyWaze - event");
                this.couponsEnabled = MyWazeNativeManager.this.myCouponsEnabledNTV();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "launchMyWaze - callback");
                Intent $r1 = new Intent($r1, MyWazeActivity.class);
                $r1.putExtra("com.waze.mywaze.coupons", this.couponsEnabled);
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().startActivityForResult($r1, MainActivity.MYWAZE_CODE);
                }
            }
        });
    }

    public void setVisibility(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setUserVisibilityNTV($i0);
            }
        });
    }

    public void ShowUserScreenIfNeeded() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.ShowUserScreenIfNeededNTV();
            }
        });
    }

    public void setTwitterShowProfile(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setTwitterShowProfNTV($i0);
            }
        });
    }

    public boolean registerUser(String $r1, String $r2, String $r3, String $r4, String $r5, boolean $z0, RegisterActivity $r6) throws  {
        if (!validateUserNTV($r1, $r2, $r3, $r4, $r5, $z0)) {
            return false;
        }
        this.pendingRegister = $r6;
        final String str = $r1;
        final String str2 = $r2;
        final String str3 = $r4;
        final String str4 = $r5;
        final boolean z = $z0;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.registerUserNTV(str, str2, str3, str4, z);
            }
        });
        return true;
    }

    public void registerFailed(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (MyWazeNativeManager.this.pendingRegister == null || !MyWazeNativeManager.this.pendingRegister.isRunning()) {
                    if (AppService.getActiveActivity() instanceof ISetUserUpdateResult) {
                        ((ISetUserUpdateResult) AppService.getActiveActivity()).SetUserUpdateResult($z0);
                    }
                    if (AppService.getMainActivity() != null) {
                        AppService.getMainActivity().SetUserUpdateResult($z0);
                        return;
                    }
                    return;
                }
                MyWazeNativeManager.this.pendingRegister.OnResponse($z0);
            }
        });
    }

    private void closePendingRegister() throws  {
        if (this.pendingRegister != null && this.pendingRegister.isAlive()) {
            this.pendingRegister.finish();
            this.pendingRegister = null;
        }
    }

    public void openWelcome() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (MyWazeNativeManager.this.pendingRegister != null) {
                    MyWazeNativeManager.this.pendingRegister.openWelcome();
                    MyWazeNativeManager.this.pendingRegister = null;
                }
            }
        });
    }

    public void updateTwitter(final String $r1, final String $r2, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.updateTwitterNTV($r1, $r2, $z0);
            }
        });
    }

    public void allowPings(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setAllowPingsNTV($z0);
            }
        });
    }

    public void SetallowPM(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setAllowPMNTV($z0);
            }
        });
    }

    public void OnSettingChange_SetVisibilty() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setVisibilityNTV();
            }
        });
    }

    public void facebookPostConnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.afterConnectToFacebookNTV();
            }
        });
    }

    public void sendSocialAddFriends(final int[] $r1, final int $i0, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.sendSocialAddFriendsNTV($r1, $i0, $r2);
            }
        });
    }

    public void sendSocialRemoveFriends(final int[] $r1, final int $i0, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.sendSocialRemoveFriendsNTV($r1, $i0, $r2);
            }
        });
    }

    public void sendSocialInviteFriends(final int[] $r1, final String[] $r2, final int $i0, final String $r3) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.sendSocialInviteFriendsNTV($r1, $r2, $i0, $r3);
            }
        });
    }

    public void addFriendsChangedListener(IFriendsChanged $r1) throws  {
        this.mFriendsChangedListeners.add($r1);
    }

    public void removeFriendsChangedListener(IFriendsChanged $r1) throws  {
        this.mFriendsChangedListeners.remove($r1);
    }

    public void socialFriendsAddedOrRemoved() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Iterator $r3 = MyWazeNativeManager.this.mFriendsChangedListeners.iterator();
                while ($r3.hasNext()) {
                    ((IFriendsChanged) $r3.next()).onFriendsChanged();
                }
            }
        });
    }

    public void foursquarePostConnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.afterConnectToFoursquareNTV();
            }
        });
    }

    public void linkedinPostConnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.afterConnectToLinkedinNTV();
            }
        });
    }

    public void twitterPostConnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.afterConnectToTwitterNTV();
            }
        });
    }

    public void showSignIn() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (!SignInActivity.isActive()) {
                    AppService.showActivity(new Intent(AppService.getAppContext(), SignInActivity.class));
                }
            }
        });
    }

    private void facebook_settings_dialog_open() throws  {
        final FacebookSettings $r2 = getFacebookSettings();
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent(AppService.getAppContext(), FacebookActivity.class);
                $r1.putExtra("com.waze.mywaze.facebooksettings", $r2);
                AppService.showActivity($r1);
            }
        });
    }

    public void mood_dialog_show() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                AppService.showActivity(new Intent(AppService.getAppContext(), MoodsActivity.class));
            }
        });
    }

    public void showProfile() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ProfileLauncher.launch(AppService.getAppContext(), true);
            }
        });
    }

    public void showRegister() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent(AppService.getActiveActivity(), PhoneRegisterActivity.class);
                $r1.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
                $r1.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
                AppService.getActiveActivity().startActivity($r1);
            }
        });
    }

    public void showVideo(final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent(AppService.getActiveActivity(), VideoActivity.class);
                $r1.putExtra("landscape", true);
                $r1.putExtra("url", $r1);
                AppService.getActiveActivity().startActivity($r1);
            }
        });
    }

    public void skipSignup() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.skipSignupNTV();
            }
        });
    }

    public void openWelcomeActivityAndCloseCurrentActivity() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r2 = AppService.getActiveActivity();
                if ($r2 != null) {
                    writeSkipSignInLogs($r2);
                    if (shouldOpenWelcomeActivity($r2)) {
                        $r2.startActivityForResult(new Intent($r2, WelcomeActivity.class), 0);
                        $r2.setResult(-1);
                        $r2.finish();
                    }
                }
            }

            private boolean shouldOpenWelcomeActivity(Activity $r1) throws  {
                if (!($r1 instanceof GuidedTourActivity)) {
                    return $r1 instanceof SignInActivity;
                } else {
                    if (InstallNativeManager.IsMinimalInstallation()) {
                        return true;
                    }
                    return false;
                }
            }

            private void writeSkipSignInLogs(Activity $r1) throws  {
                NativeManager $r2 = NativeManager.getInstance();
                if ($r1 instanceof GuidedTourActivity) {
                    if (InstallNativeManager.IsMinimalInstallation()) {
                        $r2.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_START_DRIVING, null, null, true);
                    }
                } else if ($r1 instanceof SignInActivity) {
                    $r2.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_NEW_EXISTING_SKIP, null, null, true);
                }
            }
        });
    }

    public void completeSignup() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.completeSignupNTV();
            }
        });
    }

    public void refreshFacebookConnection(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r2 = AppService.getActiveActivity();
                if ($r2 instanceof FacebookActivity) {
                    ((FacebookActivity) $r2).refreshStatus($z0);
                }
                Logger.m36d("MyWazeNativeManager: refreshFacebookConnection: received, connected=" + $z0);
                if ($z0) {
                    Bundle $r1 = new Bundle();
                    $r1.putBoolean(MyWazeNativeManager.FB_CONNECTED, $z0);
                    Logger.m36d("MyWazeNativeManager: refreshFacebookConnection: sent UH_FACEBOOK_CONNECT");
                    MyWazeNativeManager.this.handlers.sendUpdateMessage(MyWazeNativeManager.UH_FACEBOOK_CONNECT, $r1);
                }
            }
        });
    }

    public void facebookDisconnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.facebookDisconnectNTV();
                NativeManager.getInstance().logAnalytics(AnalyticsEvents.ANALYTICS_EVENT_FACEBOOK_DISCONNECT, false, false);
            }
        });
    }

    public void facebookDisconnectNow() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.facebookDisconnectNowNTV();
                NativeManager.getInstance().logAnalytics(AnalyticsEvents.ANALYTICS_EVENT_FACEBOOK_DISCONNECT, false, false);
            }
        });
    }

    public void twitterDisconnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.twitterDisconnectNTV();
            }
        });
    }

    public void twitterConnect(final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.twitterConnectNTV($r1, $r2);
            }
        });
    }

    public void twitterSetUsername(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.twitterSetUsernameNTV($r1);
            }
        });
    }

    public void twitterSetPassword(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.twitterSetPasswordNTV($r1);
            }
        });
    }

    public void twitterSetReportMode(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.twitterSetReportModeNTV($z0);
            }
        });
    }

    public void twitterSetDrivingMode(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.twitterSetDrivingModeNTV($i0);
            }
        });
    }

    public void twitterSetMunchingMode(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.twitterSetMunchingModeNTV($z0);
            }
        });
    }

    public void facebookUserAlreadyInUse() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Logger.m36d("MyWazeNativeManager: received facebookUserAlreadyInUse, sent UH_FACEBOOK_CONNECT");
                Bundle $r1 = new Bundle();
                $r1.putBoolean(MyWazeNativeManager.FB_CONNECTED, false);
                $r1.putInt(MyWazeNativeManager.FB_CONNECT_ERROR_REASON, 6);
                MyWazeNativeManager.this.handlers.sendUpdateMessage(MyWazeNativeManager.UH_FACEBOOK_CONNECT, $r1);
            }
        });
    }

    public void facebook_show_conflicting_users(String $r1, String $r2, int $i0, int $i1) throws  {
        final int i = $i0;
        final int i2 = $i1;
        final String str = $r1;
        final String str2 = $r2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.mConflictingDataUser.mPts = i;
                MyWazeNativeManager.this.mConflictingDataUser.mRank = i2;
                MyWazeNativeManager.this.mConflictingDataUser.mUserName = str;
                MyWazeNativeManager.this.mConflictingDataUser.mLastSeen = str2;
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().openConflictingActivity();
                }
            }
        });
    }

    public void refreshFoursquareConnection(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r1 = AppService.getActiveActivity();
                if ($r1 instanceof FoursquareActivity) {
                    ((FoursquareActivity) $r1).refreshStatus($z0);
                }
            }
        });
    }

    public void refreshLinkedinConnection(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                LinkedInActivity.refreshStatus($z0);
            }
        });
    }

    public void refreshTwitterConnection(final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r1 = AppService.getActiveActivity();
                if ($r1 instanceof TwitterActivity) {
                    ((TwitterActivity) $r1).refreshStatus($z0);
                }
            }
        });
    }

    public void foursquareDisconnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.foursquareDisconnectNTV();
            }
        });
    }

    public void linkedinDisconnect() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.linkedinDisconnectNTV();
            }
        });
    }

    public void foursquareSetTweetLogin(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.foursquareSetTweetLoginNTV($z0);
            }
        });
    }

    public void foursquareSetTweetBadge(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.foursquareSetTweetBadgeNTV($z0);
            }
        });
    }

    public void facebookResolveConflict() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.facebookResolveConflictNTV();
            }
        });
    }

    public void foursquareCheckin() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.foursquareCheckinNTV();
            }
        });
    }

    public void showFoursquareVenues(final String[] $r1, final String[] $r2, final String[] $r3) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent(AppService.getAppContext(), FoursquareListActivity.class);
                $r1.putExtra("com.waze.mywaze.foursquare.descriptions", $r1);
                $r1.putExtra("com.waze.mywaze.foursquare.addresses", $r2);
                $r1.putExtra("com.waze.mywaze.foursquare.distances", $r3);
                AppService.showActivity($r1);
            }
        });
    }

    public void selectFoursquareVenue(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.selectFoursquareVenueNTV($i0);
            }
        });
    }

    public void updateFacebookToken(final String $r1, final long $l0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.updateFacebookTokenNTV($r1, $l0);
            }
        });
    }

    private void FacebookConnect() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                FacebookManager.getInstance().loginWithFacebook(AppService.getActiveActivity(), FacebookLoginType.SetToken, false);
            }
        });
    }

    private void FacebookRegisterConnect() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                FacebookManager.getInstance().loginWithFacebook(AppService.getActiveActivity(), FacebookLoginType.SignIn, true);
            }
        });
    }

    private void updatePermissions(String[] readPermissions) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
            }
        });
    }

    public void authorizePublishCallback(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.authorizePublishCallbackNTV($z0);
            }
        });
    }

    private void FacebookConnectPublish() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                FacebookManager.getInstance().loginWithFacebook(AppService.getActiveActivity(), FacebookLoginType.Publish, false);
            }
        });
    }

    public void setFacebookToken(final String $r1, final long $l0, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setFacebookTokenNTV($r1, $l0, $z0);
            }
        });
    }

    public void setSignIn(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setFacebookSignInNTV($r1);
            }
        });
    }

    public void ImportFacebook() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.ImportFacebookNTV();
            }
        });
    }

    public void setContactsSignIn(final boolean $z0, final boolean $z1, final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setContactSignInNTV($z0, $z1, $r1, $r2);
            }
        });
    }

    public void SetGoogleSignIn(final boolean $z0, final boolean $z1, final String $r1, final String $r2, final String $r3) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.setGoogleSignInNTV($z0, $z1, $r1, $r2, $r3);
            }
        });
    }

    public void ClearToken() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
            }
        });
    }

    public void showFoursquareResult(final String $r1, final String $r2, final String $r3) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r2 = AppService.getActiveActivity();
                if ($r2 != null) {
                    Intent $r1 = new Intent($r2, FoursquareCheckedinActivity.class);
                    $r1.putExtra("com.waze.mywaze.foursquare_label", $r1);
                    $r1.putExtra("com.waze.mywaze.foursquare_address", $r2);
                    $r1.putExtra("com.waze.mywaze.foursquare_points", $r3);
                    AppService.getActiveActivity().startActivityForResult($r1, 0);
                }
            }
        });
    }

    public boolean facebookUpdateToken() throws  {
        if (!FacebookManager.getInstance().isLoggedIn()) {
            return false;
        }
        updateFacebookTokenNTV(FacebookManager.getInstance().getAccessToken(), FacebookManager.getInstance().getExpiration());
        return true;
    }

    public void facebookTokenSet() throws  {
        this.mWasFbTokenSet = true;
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getActiveActivity() instanceof GuidedTourActivity) {
                    ((GuidedTourActivity) AppService.getActiveActivity()).onFacebookTokenSet();
                } else if (AppService.getActiveActivity() instanceof PhoneNumberSignInActivity) {
                    ((PhoneNumberSignInActivity) AppService.getActiveActivity()).onPinTokenSet();
                } else if (AppService.getActiveActivity() instanceof PhoneAlreadyAWazerActivity) {
                    ((PhoneAlreadyAWazerActivity) AppService.getActiveActivity()).onPinTokenSet();
                } else if (AppService.getActiveActivity() instanceof PhoneVerifyYourAccountFailureActivity) {
                    ((PhoneVerifyYourAccountFailureActivity) AppService.getActiveActivity()).onPinTokenSet();
                } else if (AppService.getActiveActivity() instanceof PhoneNumberSignInActivity) {
                    ((PhoneNumberSignInActivity) AppService.getActiveActivity()).onPinTokenSet();
                } else if (AppService.getActiveActivity() instanceof MainActivity) {
                    ((MainActivity) AppService.getActiveActivity()).onFacebookTokenSet();
                } else if (AppService.getActiveActivity() instanceof LoginOptionsActivity) {
                    ((LoginOptionsActivity) AppService.getActiveActivity()).onFacebookTokenSet();
                } else if (AppService.getActiveActivity() instanceof PhoneValidationCodeActivity) {
                    ((PhoneValidationCodeActivity) AppService.getActiveActivity()).onFacebookTokenSet();
                } else if (AppService.getActiveActivity() instanceof PhoneVerifyYourNumbersActivity) {
                    ((PhoneVerifyYourNumbersActivity) AppService.getActiveActivity()).onPinTokenSet();
                } else if (AppService.getActiveActivity() instanceof MinimalSignInActivity) {
                    ((MinimalSignInActivity) AppService.getActiveActivity()).onFacebookTokenSet();
                } else if (AppService.getActiveActivity() instanceof PlannedDriveListActivity) {
                    ((PlannedDriveListActivity) AppService.getActiveActivity()).onFacebookTokenSet();
                } else {
                    Logger.m43w("facebookTokenSet: received");
                    MyWazeNativeManager $r12 = MyWazeNativeManager.this;
                    $r12.handlers.sendUpdateMessage(MyWazeNativeManager.UH_GOOGLE_CONNECT, null);
                }
            }
        });
    }

    public boolean getWasFbTokenSet() throws  {
        return this.mWasFbTokenSet;
    }

    public void socialContactsSavePhoneRecovery(final String $r1, final String $r2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Context $r2 = AppService.getMainActivity();
                Context $r3 = $r2;
                if ($r2 == null) {
                    $r3 = AppService.getAppContext();
                }
                WazeBackupAgent.putString($r3, MyWazeNativeManager.PREF_PHONE_PREFIX + $r1, $r2);
                WazeBackupAgent.putString($r3, MyWazeNativeManager.PREF_LAST_PHONE, $r1);
                new BackupManager($r3).dataChanged();
            }
        });
    }

    public String socialContactsGetRecoveryToken(String $r1) throws  {
        Context $r2 = AppService.getMainActivity();
        Context $r3 = $r2;
        if ($r2 == null) {
            $r3 = AppService.getAppContext();
        }
        return WazeBackupAgent.getString($r3, PREF_PHONE_PREFIX + $r1, null);
    }

    public String socialContactsGetLastPhoneUsed() throws  {
        Context $r1 = AppService.getMainActivity();
        Context $r2 = $r1;
        if ($r1 == null) {
            $r2 = AppService.getAppContext();
        }
        return WazeBackupAgent.getString($r2, PREF_LAST_PHONE, null);
    }

    public void reloadAllUserStores() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.reloadAllUserStoresNTV();
            }
        });
    }

    public void removeStoreByBrandId(final String $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    MyWazeNativeManager.this.removeBrandNTV($r1);
                }
            });
        }
    }

    public void addStoreByBrandId(final String $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    MyWazeNativeManager.this.addBrandNTV($r1);
                }
            });
        }
    }

    public void sendAdBrandStats(final String $r1, final String $r2, final String $r3, final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MyWazeNativeManager.this.sendAdBrandStatsNTV($r1, $r2, $r3, $i0);
            }
        });
    }

    public boolean isBrandOptedIn(String $r1) throws  {
        return isOptedInNTV($r1);
    }

    public void openMyStoresScreen() throws  {
        if (ConfigManager.getInstance().getConfigValueBool(409)) {
            Intent $r1 = new Intent(AppService.getActiveActivity(), MyStoresActivity.class);
            $r1.putExtra("source", 0);
            AppService.getActiveActivity().startActivity($r1);
        }
    }

    public void onUserStoresReloaded() throws  {
        if (AppService.getActiveActivity() instanceof MyStoresActivity) {
            ((MyStoresActivity) AppService.getActiveActivity()).onMyStoreModelsRefreshed();
        }
    }

    public boolean isShareAllowedNTV() throws  {
        return getContactLoggedInNTV() && !isGuestUserNTV();
    }
}
