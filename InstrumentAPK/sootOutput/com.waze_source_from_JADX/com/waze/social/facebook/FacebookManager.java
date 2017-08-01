package com.waze.social.facebook;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdk.InitializeCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.internal.ShareConstants;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityResultCallback;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.share.ShareFbLocation;
import com.waze.share.ShareFbQueries.IPostCallback;
import com.waze.share.ShareNativeManager;
import java.util.Arrays;
import java.util.List;

public class FacebookManager {
    public static final String DATA_FACEBOOK_FLAG = "is_facebook_for_waze";
    private static final String FB_PREFS_ACCESS_PERMISSIONS = "facebookAccessPermissions";
    private static final String FB_PREFS_DELIM = ",";
    private static FacebookManager _instance;
    private AccessTokenTracker mAccessTokenTracker;
    private ActivityResultCallback mActivityResultCallback = new C29171();
    private CallbackManager mCallbackManager = Factory.create();
    private List<String> mPublishPermissions;
    private List<String> mReadPermissions;
    private List<String> mStreamPermissions;

    public interface FacebookLoginListener {
        void onFacebookLoginResult(boolean z);
    }

    class C29171 implements ActivityResultCallback {
        C29171() {
        }

        public void onActivitResult(ActivityBase context, int requestCode, int resultCode, Intent data) {
            FacebookManager.this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                data.putExtra(FacebookManager.DATA_FACEBOOK_FLAG, true);
            }
        }
    }

    class C29193 extends AccessTokenTracker {
        C29193() {
        }

        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
        }
    }

    private class FacebookConnector {
        private ActivityBase mActivity;
        private boolean mForceFacebookConnect;
        private FacebookLoginListener mListener;
        private RunnableUICallback mLoadPermissionsCallback;
        private FacebookLoginType mType;

        public FacebookConnector(ActivityBase activity, FacebookLoginType type, boolean forceFacebookConnect, FacebookLoginListener listener) {
            this.mActivity = activity;
            this.mType = type;
            this.mListener = listener;
            this.mForceFacebookConnect = forceFacebookConnect;
            this.mActivity.addActivityResultCallback(FacebookManager.this.mActivityResultCallback);
            LoginManager.getInstance().registerCallback(FacebookManager.this.mCallbackManager, new FacebookCallback<LoginResult>(FacebookManager.this) {
                public void onSuccess(LoginResult loginResult) {
                    FacebookConnector.this.removeActivityCallback();
                    FacebookConnector.this.processLoginResult(loginResult);
                    if (FacebookConnector.this.mListener != null) {
                        FacebookConnector.this.mListener.onFacebookLoginResult(true);
                    }
                }

                public void onCancel() {
                    FacebookConnector.this.removeActivityCallback();
                    FacebookConnector.this.processLoginFailure();
                    if (FacebookConnector.this.mListener != null) {
                        FacebookConnector.this.mListener.onFacebookLoginResult(false);
                    }
                }

                public void onError(FacebookException error) {
                    FacebookConnector.this.removeActivityCallback();
                    FacebookConnector.this.processLoginFailure();
                    if (FacebookConnector.this.mListener != null) {
                        FacebookConnector.this.mListener.onFacebookLoginResult(false);
                    }
                }
            });
            this.mLoadPermissionsCallback = new RunnableUICallback(FacebookManager.this) {
                public void event() {
                    String[] permissions;
                    switch (FacebookConnector.this.mType) {
                        case SignIn:
                        case SetToken:
                            permissions = NativeManager.getInstance().getFbPermissionsNTV();
                            if (permissions != null) {
                                FacebookManager.this.mReadPermissions = Arrays.asList(permissions);
                                return;
                            }
                            return;
                        case Publish:
                            permissions = NativeManager.getInstance().getPublishFbPermissionsNTV();
                            if (permissions != null) {
                                FacebookManager.this.mPublishPermissions = Arrays.asList(permissions);
                                return;
                            }
                            return;
                        case PublishStream:
                            FacebookManager.this.mStreamPermissions = Arrays.asList(new String[]{"publish_stream"});
                            return;
                        default:
                            return;
                    }
                }

                public void callback() {
                    FacebookConnector.this.performLogin();
                }
            };
        }

        public void performLogin() {
            List<String> permissions = null;
            switch (this.mType) {
                case SignIn:
                case SetToken:
                    permissions = FacebookManager.this.mReadPermissions;
                    break;
                case Publish:
                    permissions = FacebookManager.this.mPublishPermissions;
                    break;
                case PublishStream:
                    permissions = FacebookManager.this.mStreamPermissions;
                    break;
            }
            if (permissions != null) {
                if (FacebookManager.this.isLoggedIn() && AccessToken.getCurrentAccessToken().getPermissions().containsAll(permissions)) {
                    if (this.mListener != null) {
                        this.mListener.onFacebookLoginResult(true);
                    }
                } else if (this.mType == FacebookLoginType.SignIn || this.mType == FacebookLoginType.SetToken) {
                    LoginManager.getInstance().logInWithReadPermissions(this.mActivity, permissions);
                } else {
                    LoginManager.getInstance().logInWithPublishPermissions(this.mActivity, permissions);
                }
            } else if (NativeManager.IsAppStarted()) {
                NativeManager.Post(this.mLoadPermissionsCallback);
            } else {
                NativeManager.registerOnAppStartedEvent(new RunnableExecutor(NativeManager.getInstance()) {
                    public void event() {
                        FacebookConnector.this.mLoadPermissionsCallback.run();
                    }
                });
            }
        }

        private void processLoginResult(LoginResult loginResult) {
            String token = loginResult.getAccessToken().getToken();
            long expires = loginResult.getAccessToken().getExpires().getTime();
            if (this.mType == FacebookLoginType.SetToken) {
                MyWazeNativeManager.getInstance().setFacebookToken(token, expires, this.mForceFacebookConnect);
            } else if (this.mType == FacebookLoginType.SignIn) {
                MyWazeNativeManager.getInstance().setSignIn(token);
            } else {
                MyWazeNativeManager.getInstance().authorizePublishCallback(true);
            }
        }

        private void processLoginFailure() {
            if (this.mType == FacebookLoginType.Publish || this.mType == FacebookLoginType.PublishStream) {
                MyWazeNativeManager.getInstance().authorizePublishCallback(false);
            }
            MyWazeNativeManager.getInstance().facebookDisconnectNow();
        }

        private void removeActivityCallback() {
            if (this.mActivity != null) {
                this.mActivity.removeActivityResultCallback(FacebookManager.this.mActivityResultCallback);
            }
        }
    }

    public enum FacebookLoginType {
        SetToken,
        SignIn,
        Publish,
        PublishStream
    }

    public static synchronized FacebookManager getInstance() {
        FacebookManager facebookManager;
        synchronized (FacebookManager.class) {
            if (_instance == null) {
                _instance = new FacebookManager();
            }
            facebookManager = _instance;
        }
        return facebookManager;
    }

    private FacebookManager() {
    }

    public void initialize(final ActivityBase context) {
        FacebookSdk.setApplicationId(context.getString(C1283R.string.fb_app_id));
        FacebookSdk.setLegacyTokenUpgradeSupported(true);
        FacebookSdk.sdkInitialize(context, new InitializeCallback() {
            public void onInitialized() {
                if (FacebookManager.this.isLoggedIn()) {
                    FacebookManager.this.loginWithFacebook(context, FacebookLoginType.SetToken, false);
                }
            }
        });
        AppEventsLogger.activateApp(context);
        this.mAccessTokenTracker = new C29193();
    }

    public void loginWithFacebook(ActivityBase activity, FacebookLoginType type, boolean logoutFirst) {
        loginWithFacebook(activity, type, logoutFirst, false, null);
    }

    public void loginWithFacebook(ActivityBase activity, FacebookLoginType type, boolean logoutFirst, boolean forceFacebookConnect) {
        loginWithFacebook(activity, type, logoutFirst, forceFacebookConnect, null);
    }

    public void loginWithFacebook(ActivityBase activity, FacebookLoginType type, boolean logoutFirst, FacebookLoginListener listener) {
        loginWithFacebook(activity, type, logoutFirst, false, listener);
    }

    public void loginWithFacebook(ActivityBase activity, FacebookLoginType type, boolean logoutFirst, boolean forceFacebookConnect, FacebookLoginListener listener) {
        FacebookConnector facebookConnector = new FacebookConnector(activity, type, forceFacebookConnect, listener);
        if (logoutFirst && isLoggedIn() && (type == FacebookLoginType.SignIn || type == FacebookLoginType.SetToken)) {
            logoutFromFacebook();
            MyWazeNativeManager.getInstance().facebookDisconnectNow();
        }
        facebookConnector.performLogin();
    }

    public void logoutFromFacebook() {
        LoginManager.getInstance().logOut();
    }

    public void postFeed(ShareFbLocation destination, String link, String[] tags, String message, final IPostCallback cb, String eventId, String desc, String picture) {
        Bundle params = new Bundle();
        if (destination != null) {
            params.putString("place", destination.id);
        }
        if (link != null) {
            params.putString("link", link);
        }
        if (desc != null) {
            params.putString("description", desc);
        }
        if (picture != null) {
            params.putString("picture", picture);
        }
        String tagsList = toTagList(tags);
        if (tagsList != null) {
            params.putString("tags", tagsList);
        }
        if (message != null) {
            params.putString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, message);
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + eventId + "/feed", params, HttpMethod.POST, new Callback() {
            public void onCompleted(GraphResponse response) {
                if (cb == null) {
                    return;
                }
                if (response.getError() != null) {
                    cb.onPostResult(response.getError().getErrorCode(), response.getError().getErrorMessage());
                } else {
                    cb.onPostResult(0, null);
                }
            }
        }).executeAsync();
    }

    public void postAction(ShareFbLocation destination, String end_time, String[] tags, String message, IPostCallback cb) {
        String serverUrl = ShareNativeManager.getInstance().getShareUserPrerfixUrl();
        Bundle params = new Bundle();
        StringBuilder url = new StringBuilder();
        String lon = String.valueOf(destination.longitude);
        String lat = String.valueOf(destination.latitude);
        url.append(serverUrl + destination.id);
        url.append("?placeName=" + destination.name + "&lon=" + lon + "&lat=" + lat);
        params.putString("destination", url.toString());
        params.putString("end_time", end_time);
        if (message != null) {
            params.putString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, message);
        }
        String tagsList = toTagList(tags);
        if (tagsList != null) {
            params.putString("tags", tagsList);
        }
        final IPostCallback iPostCallback = cb;
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/wazeapplication:drive_to", params, HttpMethod.POST, new Callback() {
            public void onCompleted(GraphResponse response) {
                if (iPostCallback == null) {
                    return;
                }
                if (response.getError() != null) {
                    iPostCallback.onPostResult(response.getError().getErrorCode(), response.getError().getErrorMessage());
                } else {
                    iPostCallback.onPostResult(0, null);
                }
            }
        }).executeAsync();
    }

    private static String toTagList(String[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.length; i++) {
            if (i > 0) {
                sb.append(FB_PREFS_DELIM);
            }
            sb.append(tags[i]);
        }
        return sb.toString();
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return (accessToken == null || accessToken.isExpired()) ? false : true;
    }

    public String getAccessToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            return accessToken.getToken();
        }
        return null;
    }

    public long getExpiration() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            return accessToken.getExpires().getTime();
        }
        return 0;
    }
}
