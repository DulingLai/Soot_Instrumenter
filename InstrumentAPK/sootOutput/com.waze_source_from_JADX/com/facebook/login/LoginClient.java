package com.facebook.login;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.C0496R;
import com.facebook.FacebookException;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class LoginClient implements Parcelable {
    public static final Creator<LoginClient> CREATOR = new C05491();
    BackgroundProcessingListener backgroundProcessingListener;
    boolean checkedInternetPermission;
    int currentHandler = -1;
    Fragment fragment;
    LoginMethodHandler[] handlersToTry;
    Map<String, String> loggingExtras;
    private LoginLogger loginLogger;
    OnCompletedListener onCompletedListener;
    Request pendingRequest;

    static class C05491 implements Creator {
        C05491() throws  {
        }

        public LoginClient createFromParcel(Parcel $r1) throws  {
            return new LoginClient($r1);
        }

        public LoginClient[] newArray(int $i0) throws  {
            return new LoginClient[$i0];
        }
    }

    interface BackgroundProcessingListener {
        void onBackgroundProcessingStarted() throws ;

        void onBackgroundProcessingStopped() throws ;
    }

    public interface OnCompletedListener {
        void onCompleted(Result result) throws ;
    }

    private static class PermissionsPair {
        List<String> declinedPermissions;
        List<String> grantedPermissions;

        public PermissionsPair(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r2) throws  {
            this.grantedPermissions = $r1;
            this.declinedPermissions = $r2;
        }

        public List<String> getGrantedPermissions() throws  {
            return this.grantedPermissions;
        }

        public List<String> getDeclinedPermissions() throws  {
            return this.declinedPermissions;
        }
    }

    public static class Request implements Parcelable {
        public static final Creator<Request> CREATOR = new C05501();
        private final String applicationId;
        private final String authId;
        private final DefaultAudience defaultAudience;
        private boolean isRerequest;
        private final LoginBehavior loginBehavior;
        private Set<String> permissions;

        static class C05501 implements Creator {
            C05501() throws  {
            }

            public Request createFromParcel(Parcel $r1) throws  {
                return new Request($r1);
            }

            public Request[] newArray(int $i0) throws  {
                return new Request[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        Request(@Signature({"(", "Lcom/facebook/login/LoginBehavior;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/login/DefaultAudience;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) LoginBehavior $r1, @Signature({"(", "Lcom/facebook/login/LoginBehavior;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/login/DefaultAudience;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Set<String> $r5, @Signature({"(", "Lcom/facebook/login/LoginBehavior;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/login/DefaultAudience;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) DefaultAudience $r2, @Signature({"(", "Lcom/facebook/login/LoginBehavior;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/login/DefaultAudience;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Lcom/facebook/login/LoginBehavior;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/login/DefaultAudience;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
            this.isRerequest = false;
            this.loginBehavior = $r1;
            if ($r5 == null) {
                Object $r52 = r6;
                HashSet r6 = new HashSet();
            }
            this.permissions = $r5;
            this.defaultAudience = $r2;
            this.applicationId = $r3;
            this.authId = $r4;
        }

        Set<String> getPermissions() throws  {
            return this.permissions;
        }

        void setPermissions(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String> $r1) throws  {
            Validate.notNull($r1, NativeProtocol.RESULT_ARGS_PERMISSIONS);
            this.permissions = $r1;
        }

        LoginBehavior getLoginBehavior() throws  {
            return this.loginBehavior;
        }

        DefaultAudience getDefaultAudience() throws  {
            return this.defaultAudience;
        }

        String getApplicationId() throws  {
            return this.applicationId;
        }

        String getAuthId() throws  {
            return this.authId;
        }

        boolean isRerequest() throws  {
            return this.isRerequest;
        }

        void setRerequest(boolean $z0) throws  {
            this.isRerequest = $z0;
        }

        boolean hasPublishPermission() throws  {
            for (String isPublishPermission : this.permissions) {
                if (LoginManager.isPublishPermission(isPublishPermission)) {
                    return true;
                }
            }
            return false;
        }

        private Request(Parcel $r1) throws  {
            boolean $z0;
            DefaultAudience $r3 = null;
            this.isRerequest = false;
            String $r4 = $r1.readString();
            this.loginBehavior = $r4 != null ? LoginBehavior.valueOf($r4) : null;
            ArrayList $r2 = new ArrayList();
            $r1.readStringList($r2);
            this.permissions = new HashSet($r2);
            $r4 = $r1.readString();
            if ($r4 != null) {
                $r3 = DefaultAudience.valueOf($r4);
            }
            this.defaultAudience = $r3;
            this.applicationId = $r1.readString();
            this.authId = $r1.readString();
            if ($r1.readByte() != (byte) 0) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            this.isRerequest = $z0;
        }

        public void writeToParcel(Parcel $r1, int flags) throws  {
            String $r3 = null;
            $r1.writeString(this.loginBehavior != null ? this.loginBehavior.name() : null);
            $r1.writeStringList(new ArrayList(this.permissions));
            if (this.defaultAudience != null) {
                $r3 = this.defaultAudience.name();
            }
            $r1.writeString($r3);
            $r1.writeString(this.applicationId);
            $r1.writeString(this.authId);
            $r1.writeByte((byte) (this.isRerequest));
        }
    }

    public static class Result implements Parcelable {
        public static final Creator<Result> CREATOR = new C05511();
        final Code code;
        final String errorCode;
        final String errorMessage;
        public Map<String, String> loggingExtras;
        final Request request;
        final AccessToken token;

        static class C05511 implements Creator {
            C05511() throws  {
            }

            public Result createFromParcel(Parcel $r1) throws  {
                return new Result($r1);
            }

            public Result[] newArray(int $i0) throws  {
                return new Result[$i0];
            }
        }

        enum Code {
            SUCCESS("success"),
            CANCEL("cancel"),
            ERROR("error");
            
            private final String loggingValue;

            private Code(@Signature({"(", "Ljava/lang/String;", ")V"}) String $r2) throws  {
                this.loggingValue = $r2;
            }

            String getLoggingValue() throws  {
                return this.loggingValue;
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        Result(Request $r1, Code $r2, AccessToken $r3, String $r4, String $r5) throws  {
            Validate.notNull($r2, QueryParams.CODE);
            this.request = $r1;
            this.token = $r3;
            this.errorMessage = $r4;
            this.code = $r2;
            this.errorCode = $r5;
        }

        static Result createTokenResult(Request $r0, AccessToken $r1) throws  {
            return new Result($r0, Code.SUCCESS, $r1, null, null);
        }

        static Result createCancelResult(Request $r0, String $r1) throws  {
            return new Result($r0, Code.CANCEL, null, $r1, null);
        }

        static Result createErrorResult(Request $r0, String $r1, String $r2) throws  {
            return createErrorResult($r0, $r1, $r2, null);
        }

        static Result createErrorResult(Request $r0, String $r1, String $r2, String $r3) throws  {
            CharSequence charSequence = ": ";
            return new Result($r0, Code.ERROR, null, TextUtils.join(charSequence, Utility.asListNoNulls($r1, $r2)), $r3);
        }

        private Result(Parcel $r1) throws  {
            this.code = Code.valueOf($r1.readString());
            this.token = (AccessToken) $r1.readParcelable(AccessToken.class.getClassLoader());
            this.errorMessage = $r1.readString();
            this.errorCode = $r1.readString();
            this.request = (Request) $r1.readParcelable(Request.class.getClassLoader());
            this.loggingExtras = Utility.readStringMapFromParcel($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            $r1.writeString(this.code.name());
            $r1.writeParcelable(this.token, $i0);
            $r1.writeString(this.errorMessage);
            $r1.writeString(this.errorCode);
            $r1.writeParcelable(this.request, $i0);
            Utility.writeStringMapToParcel($r1, this.loggingExtras);
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public LoginClient(Fragment $r1) throws  {
        this.fragment = $r1;
    }

    public Fragment getFragment() throws  {
        return this.fragment;
    }

    void setFragment(Fragment $r1) throws  {
        if (this.fragment != null) {
            throw new FacebookException("Can't set fragment once it is already set.");
        }
        this.fragment = $r1;
    }

    FragmentActivity getActivity() throws  {
        return this.fragment.getActivity();
    }

    public Request getPendingRequest() throws  {
        return this.pendingRequest;
    }

    public static int getLoginRequestCode() throws  {
        return RequestCodeOffset.Login.toRequestCode();
    }

    void startOrContinueAuth(Request $r1) throws  {
        if (!getInProgress()) {
            authorize($r1);
        }
    }

    void authorize(Request $r1) throws  {
        if ($r1 != null) {
            if (this.pendingRequest != null) {
                throw new FacebookException("Attempted to authorize while a request is pending.");
            } else if (AccessToken.getCurrentAccessToken() == null || checkInternetPermission()) {
                this.pendingRequest = $r1;
                this.handlersToTry = getHandlersToTry($r1);
                tryNextHandler();
            }
        }
    }

    boolean getInProgress() throws  {
        return this.pendingRequest != null && this.currentHandler >= 0;
    }

    void cancelCurrentHandler() throws  {
        if (this.currentHandler >= 0) {
            getCurrentHandler().cancel();
        }
    }

    private LoginMethodHandler getCurrentHandler() throws  {
        if (this.currentHandler >= 0) {
            return this.handlersToTry[this.currentHandler];
        }
        return null;
    }

    public boolean onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if (this.pendingRequest != null) {
            return getCurrentHandler().onActivityResult($i0, $i1, $r1);
        }
        return false;
    }

    private LoginMethodHandler[] getHandlersToTry(Request $r1) throws  {
        ArrayList $r2 = new ArrayList();
        LoginBehavior $r4 = $r1.getLoginBehavior();
        if ($r4.allowsKatanaAuth()) {
            $r2.add(new GetTokenLoginMethodHandler(this));
            $r2.add(new KatanaProxyLoginMethodHandler(this));
        }
        if ($r4.allowsWebViewAuth()) {
            $r2.add(new WebViewLoginMethodHandler(this));
        }
        LoginMethodHandler[] $r3 = new LoginMethodHandler[$r2.size()];
        $r2.toArray($r3);
        return $r3;
    }

    boolean checkInternetPermission() throws  {
        if (this.checkedInternetPermission) {
            return true;
        }
        if (checkPermission("android.permission.INTERNET") != 0) {
            FragmentActivity $r1 = getActivity();
            complete(Result.createErrorResult(this.pendingRequest, $r1.getString(C0496R.string.com_facebook_internet_permission_error_title), $r1.getString(C0496R.string.com_facebook_internet_permission_error_message)));
            return false;
        }
        this.checkedInternetPermission = true;
        return true;
    }

    void tryNextHandler() throws  {
        if (this.currentHandler >= 0) {
            logAuthorizationMethodComplete(getCurrentHandler().getNameForLogging(), "skipped", null, null, getCurrentHandler().methodLoggingExtras);
        }
        while (this.handlersToTry != null && this.currentHandler < this.handlersToTry.length - 1) {
            this.currentHandler++;
            if (tryCurrentHandler()) {
                return;
            }
        }
        if (this.pendingRequest != null) {
            completeWithFailure();
        }
    }

    private void completeWithFailure() throws  {
        complete(Result.createErrorResult(this.pendingRequest, "Login attempt failed.", null));
    }

    private void addLoggingExtra(String $r1, String $r2, boolean $z0) throws  {
        if (this.loggingExtras == null) {
            this.loggingExtras = new HashMap();
        }
        if (this.loggingExtras.containsKey($r1) && $z0) {
            $r2 = ((String) this.loggingExtras.get($r1)) + "," + $r2;
        }
        this.loggingExtras.put($r1, $r2);
    }

    boolean tryCurrentHandler() throws  {
        LoginMethodHandler $r1 = getCurrentHandler();
        if (!$r1.needsInternetPermission() || checkInternetPermission()) {
            boolean $z0 = $r1.tryAuthorize(this.pendingRequest);
            if ($z0) {
                getLogger().logAuthorizationMethodStart(this.pendingRequest.getAuthId(), $r1.getNameForLogging());
                return $z0;
            }
            addLoggingExtra("not_tried", $r1.getNameForLogging(), true);
            return $z0;
        }
        addLoggingExtra("no_internet_permission", AppEventsConstants.EVENT_PARAM_VALUE_YES, false);
        return false;
    }

    void completeAndValidate(Result $r1) throws  {
        if ($r1.token == null || AccessToken.getCurrentAccessToken() == null) {
            complete($r1);
        } else {
            validateSameFbidAndFinish($r1);
        }
    }

    void complete(Result $r1) throws  {
        LoginMethodHandler $r3 = getCurrentHandler();
        if ($r3 != null) {
            logAuthorizationMethodComplete($r3.getNameForLogging(), $r1, $r3.methodLoggingExtras);
        }
        if (this.loggingExtras != null) {
            $r1.loggingExtras = this.loggingExtras;
        }
        this.handlersToTry = null;
        this.currentHandler = -1;
        this.pendingRequest = null;
        this.loggingExtras = null;
        notifyOnCompleteListener($r1);
    }

    OnCompletedListener getOnCompletedListener() throws  {
        return this.onCompletedListener;
    }

    void setOnCompletedListener(OnCompletedListener $r1) throws  {
        this.onCompletedListener = $r1;
    }

    BackgroundProcessingListener getBackgroundProcessingListener() throws  {
        return this.backgroundProcessingListener;
    }

    void setBackgroundProcessingListener(BackgroundProcessingListener $r1) throws  {
        this.backgroundProcessingListener = $r1;
    }

    int checkPermission(String $r1) throws  {
        return getActivity().checkCallingOrSelfPermission($r1);
    }

    void validateSameFbidAndFinish(Result $r1) throws  {
        if ($r1.token == null) {
            throw new FacebookException("Can't validate without a token");
        }
        AccessToken $r5 = AccessToken.getCurrentAccessToken();
        AccessToken $r3 = $r1.token;
        if (!($r5 == null || $r3 == null)) {
            try {
                if ($r5.getUserId().equals($r3.getUserId())) {
                    $r1 = Result.createTokenResult(this.pendingRequest, $r1.token);
                    complete($r1);
                }
            } catch (Exception $r2) {
                complete(Result.createErrorResult(this.pendingRequest, "Caught exception", $r2.getMessage()));
                return;
            }
        }
        $r1 = Result.createErrorResult(this.pendingRequest, "User logged in as different Facebook user.", null);
        complete($r1);
    }

    private static AccessToken createFromTokenWithRefreshedPermissions(@Signature({"(", "Lcom/facebook/AccessToken;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/facebook/AccessToken;"}) AccessToken $r0, @Signature({"(", "Lcom/facebook/AccessToken;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/facebook/AccessToken;"}) Collection<String> $r1, @Signature({"(", "Lcom/facebook/AccessToken;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/facebook/AccessToken;"}) Collection<String> $r2) throws  {
        return new AccessToken($r0.getToken(), $r0.getApplicationId(), $r0.getUserId(), $r1, $r2, $r0.getSource(), $r0.getExpires(), $r0.getLastRefresh());
    }

    private LoginLogger getLogger() throws  {
        if (this.loginLogger == null || !this.loginLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
            this.loginLogger = new LoginLogger(getActivity(), this.pendingRequest.getApplicationId());
        }
        return this.loginLogger;
    }

    private void notifyOnCompleteListener(Result $r1) throws  {
        if (this.onCompletedListener != null) {
            this.onCompletedListener.onCompleted($r1);
        }
    }

    void notifyBackgroundProcessingStart() throws  {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStarted();
        }
    }

    void notifyBackgroundProcessingStop() throws  {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStopped();
        }
    }

    private void logAuthorizationMethodComplete(@Signature({"(", "Ljava/lang/String;", "Lcom/facebook/login/LoginClient$Result;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/facebook/login/LoginClient$Result;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Result $r2, @Signature({"(", "Ljava/lang/String;", "Lcom/facebook/login/LoginClient$Result;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r3) throws  {
        logAuthorizationMethodComplete($r1, $r2.code.getLoggingValue(), $r2.errorMessage, $r2.errorCode, $r3);
    }

    private void logAuthorizationMethodComplete(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r5) throws  {
        if (this.pendingRequest == null) {
            getLogger().logUnexpectedError("fb_mobile_login_method_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.", $r1);
            return;
        }
        getLogger().logAuthorizationMethodComplete(this.pendingRequest.getAuthId(), $r1, $r2, $r3, $r4, $r5);
    }

    static String getE2E() throws  {
        JSONObject $r0 = new JSONObject();
        try {
            $r0.put("init", System.currentTimeMillis());
        } catch (JSONException e) {
        }
        return $r0.toString();
    }

    private static PermissionsPair handlePermissionResponse(GraphResponse $r0) throws  {
        if ($r0.getError() != null) {
            return null;
        }
        JSONObject $r5 = $r0.getJSONObject();
        if ($r5 == null) {
            return null;
        }
        JSONArray $r6 = $r5.optJSONArray("data");
        if ($r6 == null) {
            return null;
        }
        if ($r6.length() == 0) {
            return null;
        }
        ArrayList $r2 = new ArrayList($r6.length());
        ArrayList $r1 = new ArrayList($r6.length());
        for (int $i0 = 0; $i0 < $r6.length(); $i0++) {
            $r5 = $r6.optJSONObject($i0);
            String $r7 = $r5.optString("permission");
            if (!($r7 == null || $r7.equals("installed"))) {
                String $r8 = $r5.optString("status");
                if ($r8 != null) {
                    if ($r8.equals("granted")) {
                        $r2.add($r7);
                    } else if ($r8.equals("declined")) {
                        $r1.add($r7);
                    }
                }
            }
        }
        return new PermissionsPair($r2, $r1);
    }

    public LoginClient(Parcel $r1) throws  {
        Parcelable[] $r5 = $r1.readParcelableArray(LoginMethodHandler.class.getClassLoader());
        this.handlersToTry = new LoginMethodHandler[$r5.length];
        for (int $i0 = 0; $i0 < $r5.length; $i0++) {
            this.handlersToTry[$i0] = (LoginMethodHandler) $r5[$i0];
            this.handlersToTry[$i0].setLoginClient(this);
        }
        this.currentHandler = $r1.readInt();
        this.pendingRequest = (Request) $r1.readParcelable(Request.class.getClassLoader());
        this.loggingExtras = Utility.readStringMapFromParcel($r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeParcelableArray(this.handlersToTry, $i0);
        $r1.writeInt(this.currentHandler);
        $r1.writeParcelable(this.pendingRequest, $i0);
        Utility.writeStringMapToParcel($r1, this.loggingExtras);
    }
}
