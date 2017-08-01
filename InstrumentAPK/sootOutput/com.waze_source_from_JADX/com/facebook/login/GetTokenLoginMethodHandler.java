package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient.CompletedListener;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class GetTokenLoginMethodHandler extends LoginMethodHandler {
    public static final Creator<GetTokenLoginMethodHandler> CREATOR = new C05473();
    private GetTokenClient getTokenClient;

    static class C05473 implements Creator {
        C05473() throws  {
        }

        public GetTokenLoginMethodHandler createFromParcel(Parcel $r1) throws  {
            return new GetTokenLoginMethodHandler($r1);
        }

        public GetTokenLoginMethodHandler[] newArray(int $i0) throws  {
            return new GetTokenLoginMethodHandler[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    String getNameForLogging() throws  {
        return "get_token";
    }

    GetTokenLoginMethodHandler(LoginClient $r1) throws  {
        super($r1);
    }

    void cancel() throws  {
        if (this.getTokenClient != null) {
            this.getTokenClient.cancel();
            this.getTokenClient.setCompletedListener(null);
            this.getTokenClient = null;
        }
    }

    boolean tryAuthorize(final Request $r1) throws  {
        this.getTokenClient = new GetTokenClient(this.loginClient.getActivity(), $r1.getApplicationId());
        if (!this.getTokenClient.start()) {
            return false;
        }
        this.loginClient.notifyBackgroundProcessingStart();
        this.getTokenClient.setCompletedListener(new CompletedListener() {
            public void completed(Bundle $r1) throws  {
                GetTokenLoginMethodHandler.this.getTokenCompleted($r1, $r1);
            }
        });
        return true;
    }

    void getTokenCompleted(Request $r1, Bundle $r2) throws  {
        if (this.getTokenClient != null) {
            this.getTokenClient.setCompletedListener(null);
        }
        this.getTokenClient = null;
        this.loginClient.notifyBackgroundProcessingStop();
        if ($r2 != null) {
            ArrayList $r6 = $r2.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
            Set<String> $r7 = $r1.getPermissions();
            if ($r6 == null || !($r7 == null || $r6.containsAll($r7))) {
                HashSet $r3 = new HashSet();
                for (String $r10 : $r7) {
                    if (!$r6.contains($r10)) {
                        $r3.add($r10);
                    }
                }
                if (!$r3.isEmpty()) {
                    addLoggingExtra("new_permissions", TextUtils.join(",", $r3));
                }
                $r1.setPermissions($r3);
            } else {
                complete($r1, $r2);
                return;
            }
        }
        this.loginClient.tryNextHandler();
    }

    void onComplete(Request $r1, Bundle $r2) throws  {
        this.loginClient.completeAndValidate(Result.createTokenResult(this.loginClient.getPendingRequest(), LoginMethodHandler.createAccessTokenFromNativeLogin($r2, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE, $r1.getApplicationId())));
    }

    void complete(final Request $r1, final Bundle $r2) throws  {
        String $r3 = $r2.getString(NativeProtocol.EXTRA_USER_ID);
        if ($r3 == null || $r3.isEmpty()) {
            this.loginClient.notifyBackgroundProcessingStart();
            Utility.getGraphMeRequestWithCacheAsync($r2.getString(NativeProtocol.EXTRA_ACCESS_TOKEN), new GraphMeRequestWithCacheCallback() {
                public void onSuccess(JSONObject $r1) throws  {
                    try {
                        $r2.putString(NativeProtocol.EXTRA_USER_ID, $r1.getString("id"));
                        GetTokenLoginMethodHandler.this.onComplete($r1, $r2);
                    } catch (JSONException $r2) {
                        GetTokenLoginMethodHandler.this.loginClient.complete(Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", $r2.getMessage()));
                    }
                }

                public void onFailure(FacebookException $r1) throws  {
                    GetTokenLoginMethodHandler.this.loginClient.complete(Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", $r1.getMessage()));
                }
            });
            return;
        }
        onComplete($r1, $r2);
    }

    GetTokenLoginMethodHandler(Parcel $r1) throws  {
        super($r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
    }
}
