package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;
import com.google.android.gms.auth.firstparty.recovery.RecoveryParamConstants;
import com.spotify.sdk.android.authentication.LoginActivity;
import dalvik.annotation.Signature;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager {
    private static final String MANAGE_PERMISSION_PREFIX = "manage";
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
    private static final String PUBLISH_PERMISSION_PREFIX = "publish";
    private static volatile LoginManager instance;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
    private LoginLogger loginLogger;
    private HashMap<String, String> pendingLoggingExtras;
    private Request pendingLoginRequest;

    static class C05552 extends HashSet<String> {
        C05552() throws  {
            add("ads_management");
            add("create_event");
            add("rsvp_event");
        }
    }

    class C05563 implements Callback {
        C05563() throws  {
        }

        public boolean onActivityResult(int $i0, Intent $r1) throws  {
            return LoginManager.this.onActivityResult($i0, $r1);
        }
    }

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity $r1) throws  {
            Validate.notNull($r1, RecoveryParamConstants.VALUE_ACTIVITY);
            this.activity = $r1;
        }

        public void startActivityForResult(Intent $r1, int $i0) throws  {
            this.activity.startActivityForResult($r1, $i0);
        }

        public Activity getActivityContext() throws  {
            return this.activity;
        }
    }

    private static class FragmentStartActivityDelegate implements StartActivityDelegate {
        private final Fragment fragment;

        FragmentStartActivityDelegate(Fragment $r1) throws  {
            Validate.notNull($r1, "fragment");
            this.fragment = $r1;
        }

        public void startActivityForResult(Intent $r1, int $i0) throws  {
            this.fragment.startActivityForResult($r1, $i0);
        }

        public Activity getActivityContext() throws  {
            return this.fragment.getActivity();
        }
    }

    LoginManager() throws  {
        Validate.sdkInitialized();
    }

    public static LoginManager getInstance() throws  {
        if (instance == null) {
            synchronized (LoginManager.class) {
                try {
                    if (instance == null) {
                        instance = new LoginManager();
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = LoginManager.class;
                    }
                }
            }
        }
        return instance;
    }

    public void resolveError(Activity $r1, GraphResponse $r2) throws  {
        startLogin(new ActivityStartActivityDelegate($r1), createLoginRequestFromResponse($r2));
    }

    public void resolveError(Fragment $r1, GraphResponse $r2) throws  {
        startLogin(new FragmentStartActivityDelegate($r1), createLoginRequestFromResponse($r2));
    }

    private Request createLoginRequestFromResponse(GraphResponse $r1) throws  {
        Validate.notNull($r1, LoginActivity.RESPONSE_KEY);
        AccessToken $r3 = $r1.getRequest().getAccessToken();
        return createLoginRequest($r3 != null ? $r3.getPermissions() : null);
    }

    public void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) CallbackManager $r2, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) final FacebookCallback<LoginResult> $r1) throws  {
        if ($r2 instanceof CallbackManagerImpl) {
            ((CallbackManagerImpl) $r2).registerCallback(RequestCodeOffset.Login.toRequestCode(), new Callback() {
                public boolean onActivityResult(int $i0, Intent $r1) throws  {
                    return LoginManager.this.onActivityResult($i0, $r1, $r1);
                }
            });
            return;
        }
        throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
    }

    boolean onActivityResult(int $i0, Intent $r1) throws  {
        return onActivityResult($i0, $r1, null);
    }

    boolean onActivityResult(@Signature({"(I", "Landroid/content/Intent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)Z"}) int $i0, @Signature({"(I", "Landroid/content/Intent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)Z"}) Intent $r1, @Signature({"(I", "Landroid/content/Intent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)Z"}) FacebookCallback<LoginResult> $r2) throws  {
        if (this.pendingLoginRequest == null) {
            return false;
        }
        FacebookException $r6 = null;
        AccessToken $r7 = null;
        Code $r8 = Code.ERROR;
        Map $r9 = null;
        boolean $z0 = false;
        if ($r1 != null) {
            Result $r10 = (Result) $r1.getParcelableExtra("com.facebook.LoginFragment:Result");
            if ($r10 != null) {
                $r8 = $r10.code;
                if ($i0 == -1) {
                    if ($r10.code == Code.SUCCESS) {
                        $r7 = $r10.token;
                    } else {
                        $r6 = r0;
                        FacebookException facebookAuthorizationException = new FacebookAuthorizationException($r10.errorMessage);
                    }
                } else if ($i0 == 0) {
                    $z0 = true;
                }
                $r9 = $r10.loggingExtras;
            }
        } else if ($i0 == 0) {
            $z0 = true;
            $r8 = Code.CANCEL;
        }
        if ($r6 == null && $r7 == null && !$z0) {
            $r6 = r13;
            FacebookException r13 = new FacebookException("Unexpected call to LoginManager.onActivityResult");
        }
        logCompleteLogin($r8, $r9, $r6);
        finishLogin($r7, $r6, $z0, $r2);
        return true;
    }

    public LoginBehavior getLoginBehavior() throws  {
        return this.loginBehavior;
    }

    public LoginManager setLoginBehavior(LoginBehavior $r1) throws  {
        this.loginBehavior = $r1;
        return this;
    }

    public DefaultAudience getDefaultAudience() throws  {
        return this.defaultAudience;
    }

    public LoginManager setDefaultAudience(DefaultAudience $r1) throws  {
        this.defaultAudience = $r1;
        return this;
    }

    public void logOut() throws  {
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);
    }

    public void logInWithReadPermissions(@Signature({"(", "Landroid/support/v4/app/Fragment;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Fragment $r1, @Signature({"(", "Landroid/support/v4/app/Fragment;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r2) throws  {
        validateReadPermissions($r2);
        startLogin(new FragmentStartActivityDelegate($r1), createLoginRequest($r2));
    }

    public void logInWithReadPermissions(@Signature({"(", "Landroid/app/Activity;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Activity $r1, @Signature({"(", "Landroid/app/Activity;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r2) throws  {
        validateReadPermissions($r2);
        startLogin(new ActivityStartActivityDelegate($r1), createLoginRequest($r2));
    }

    public void logInWithPublishPermissions(@Signature({"(", "Landroid/support/v4/app/Fragment;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Fragment $r1, @Signature({"(", "Landroid/support/v4/app/Fragment;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r2) throws  {
        validatePublishPermissions($r2);
        startLogin(new FragmentStartActivityDelegate($r1), createLoginRequest($r2));
    }

    public void logInWithPublishPermissions(@Signature({"(", "Landroid/app/Activity;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Activity $r1, @Signature({"(", "Landroid/app/Activity;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r2) throws  {
        validatePublishPermissions($r2);
        startLogin(new ActivityStartActivityDelegate($r1), createLoginRequest($r2));
    }

    Request getPendingLoginRequest() throws  {
        return this.pendingLoginRequest;
    }

    private void validateReadPermissions(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r1) throws  {
        if ($r1 != null) {
            for (String $r4 : $r1) {
                if (isPublishPermission($r4)) {
                    throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[]{(String) $r2.next()}));
                }
            }
        }
    }

    private void validatePublishPermissions(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r1) throws  {
        if ($r1 != null) {
            for (String $r4 : $r1) {
                if (!isPublishPermission($r4)) {
                    throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", new Object[]{(String) $r2.next()}));
                }
            }
        }
    }

    static boolean isPublishPermission(String $r0) throws  {
        return $r0 != null && ($r0.startsWith(PUBLISH_PERMISSION_PREFIX) || $r0.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains($r0));
    }

    private static Set<String> getOtherPublishPermissions() throws  {
        return Collections.unmodifiableSet(new C05552());
    }

    private Request createLoginRequest(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/facebook/login/LoginClient$Request;"}) Collection<String> $r1) throws  {
        Request request = new Request(this.loginBehavior, Collections.unmodifiableSet($r1 != null ? new HashSet($r1) : new HashSet()), this.defaultAudience, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        request.setRerequest(AccessToken.getCurrentAccessToken() != null);
        return request;
    }

    private void startLogin(StartActivityDelegate $r1, Request $r2) throws FacebookException {
        this.pendingLoginRequest = $r2;
        this.pendingLoggingExtras = new HashMap();
        this.loginLogger = getLoggerForContext($r1.getActivityContext());
        logStartLogin();
        CallbackManagerImpl.registerStaticCallback(RequestCodeOffset.Login.toRequestCode(), new C05563());
        boolean $z0 = tryFacebookActivity($r1, $r2);
        this.pendingLoggingExtras.put("try_login_activity", $z0 ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        if (!$z0) {
            FacebookException $r3 = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
            logCompleteLogin(Code.ERROR, null, $r3);
            this.pendingLoginRequest = null;
            throw $r3;
        }
    }

    private LoginLogger getLoggerForContext(Context $r1) throws  {
        if ($r1 == null || this.pendingLoginRequest == null) {
            return null;
        }
        LoginLogger $r3 = this.loginLogger;
        return ($r3 == null || !$r3.getApplicationId().equals(this.pendingLoginRequest.getApplicationId())) ? new LoginLogger($r1, this.pendingLoginRequest.getApplicationId()) : $r3;
    }

    private void logStartLogin() throws  {
        if (this.loginLogger != null && this.pendingLoginRequest != null) {
            this.loginLogger.logStartLogin(this.pendingLoginRequest);
        }
    }

    private void logCompleteLogin(@Signature({"(", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) Code $r1, @Signature({"(", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) Map<String, String> $r2, @Signature({"(", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) Exception $r3) throws  {
        if (this.loginLogger != null) {
            if (this.pendingLoginRequest == null) {
                this.loginLogger.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
                return;
            }
            this.loginLogger.logCompleteLogin(this.pendingLoginRequest.getAuthId(), this.pendingLoggingExtras, $r1, $r2, $r3);
        }
    }

    private boolean tryFacebookActivity(StartActivityDelegate $r1, Request $r2) throws  {
        Intent $r4 = getFacebookActivityIntent($r2);
        if (!resolveIntent($r4)) {
            return false;
        }
        try {
            $r1.startActivityForResult($r4, LoginClient.getLoginRequestCode());
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private boolean resolveIntent(Intent $r1) throws  {
        return FacebookSdk.getApplicationContext().getPackageManager().resolveActivity($r1, 0) != null;
    }

    private Intent getFacebookActivityIntent(Request $r1) throws  {
        Intent $r2 = new Intent();
        $r2.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        $r2.setAction($r1.getLoginBehavior().toString());
        $r2.putExtras(LoginFragment.populateIntentExtras($r1));
        return $r2;
    }

    static LoginResult computeLoginResult(Request $r0, AccessToken $r1) throws  {
        Set $r4 = $r0.getPermissions();
        HashSet $r3 = new HashSet($r1.getPermissions());
        if ($r0.isRerequest()) {
            $r3.retainAll($r4);
        }
        HashSet $r2 = new HashSet($r4);
        $r2.removeAll($r3);
        return new LoginResult($r1, $r3, $r2);
    }

    private void finishLogin(@Signature({"(", "Lcom/facebook/AccessToken;", "Lcom/facebook/FacebookException;", "Z", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) AccessToken $r1, @Signature({"(", "Lcom/facebook/AccessToken;", "Lcom/facebook/FacebookException;", "Z", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) FacebookException $r2, @Signature({"(", "Lcom/facebook/AccessToken;", "Lcom/facebook/FacebookException;", "Z", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) boolean $z0, @Signature({"(", "Lcom/facebook/AccessToken;", "Lcom/facebook/FacebookException;", "Z", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) FacebookCallback<LoginResult> $r3) throws  {
        if ($r1 != null) {
            AccessToken.setCurrentAccessToken($r1);
            Profile.fetchProfileForCurrentAccessToken();
        }
        if ($r3 != null) {
            LoginResult $r5 = $r1 != null ? computeLoginResult(this.pendingLoginRequest, $r1) : null;
            if ($z0 || ($r5 != null && $r5.getRecentlyGrantedPermissions().size() == 0)) {
                $r3.onCancel();
            } else if ($r2 != null) {
                $r3.onError($r2);
            } else if ($r1 != null) {
                $r3.onSuccess($r5);
            }
        }
        this.pendingLoginRequest = null;
        this.loginLogger = null;
    }
}
