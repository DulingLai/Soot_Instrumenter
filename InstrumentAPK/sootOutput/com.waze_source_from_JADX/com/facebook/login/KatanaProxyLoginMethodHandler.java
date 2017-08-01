package com.facebook.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;

class KatanaProxyLoginMethodHandler extends LoginMethodHandler {
    public static final Creator<KatanaProxyLoginMethodHandler> CREATOR = new C05481();

    static class C05481 implements Creator {
        C05481() throws  {
        }

        public KatanaProxyLoginMethodHandler createFromParcel(Parcel $r1) throws  {
            return new KatanaProxyLoginMethodHandler($r1);
        }

        public KatanaProxyLoginMethodHandler[] newArray(int $i0) throws  {
            return new KatanaProxyLoginMethodHandler[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    String getNameForLogging() throws  {
        return "katana_proxy_auth";
    }

    KatanaProxyLoginMethodHandler(LoginClient $r1) throws  {
        super($r1);
    }

    boolean tryAuthorize(Request $r1) throws  {
        String $r2 = LoginClient.getE2E();
        Intent $r8 = NativeProtocol.createProxyAuthIntent(this.loginClient.getActivity(), $r1.getApplicationId(), $r1.getPermissions(), $r2, $r1.isRerequest(), $r1.hasPublishPermission(), $r1.getDefaultAudience());
        addLoggingExtra("e2e", $r2);
        return tryIntent($r8, LoginClient.getLoginRequestCode());
    }

    boolean onActivityResult(int requestCode, int $i1, Intent $r1) throws  {
        Result $r4;
        Request $r3 = this.loginClient.getPendingRequest();
        if ($r1 == null) {
            $r4 = Result.createCancelResult($r3, "Operation canceled");
        } else if ($i1 == 0) {
            $r4 = handleResultCancel($r3, $r1);
        } else if ($i1 != -1) {
            $r4 = Result.createErrorResult($r3, "Unexpected resultCode from authorization.", null);
        } else {
            $r4 = handleResultOk($r3, $r1);
        }
        if ($r4 != null) {
            this.loginClient.completeAndValidate($r4);
        } else {
            this.loginClient.tryNextHandler();
        }
        return true;
    }

    private Result handleResultOk(Request $r1, Intent $r2) throws  {
        Bundle $r5 = $r2.getExtras();
        String $r6 = getError($r5);
        String $r7 = $r5.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
        String $r8 = getErrorMessage($r5);
        String $r9 = $r5.getString("e2e");
        if (!Utility.isNullOrEmpty($r9)) {
            logWebLoginCompleted($r9);
        }
        if ($r6 == null && $r7 == null && $r8 == null) {
            try {
                return Result.createTokenResult($r1, LoginMethodHandler.createAccessTokenFromWebBundle($r1.getPermissions(), $r5, AccessTokenSource.FACEBOOK_APPLICATION_WEB, $r1.getApplicationId()));
            } catch (FacebookException $r3) {
                return Result.createErrorResult($r1, null, $r3.getMessage());
            }
        } else if (ServerProtocol.errorsProxyAuthDisabled.contains($r6)) {
            return null;
        } else {
            if (ServerProtocol.errorsUserCanceled.contains($r6)) {
                return Result.createCancelResult($r1, null);
            }
            return Result.createErrorResult($r1, $r6, $r8, $r7);
        }
    }

    private Result handleResultCancel(Request $r1, Intent $r2) throws  {
        Bundle $r3 = $r2.getExtras();
        String $r4 = getError($r3);
        String $r5 = $r3.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
        if (ServerProtocol.errorConnectionFailure.equals($r5)) {
            return Result.createErrorResult($r1, $r4, getErrorMessage($r3), $r5);
        }
        return Result.createCancelResult($r1, $r4);
    }

    private String getError(Bundle $r1) throws  {
        String $r2 = $r1.getString("error");
        if ($r2 == null) {
            return $r1.getString(NativeProtocol.BRIDGE_ARG_ERROR_TYPE);
        }
        return $r2;
    }

    private String getErrorMessage(Bundle $r1) throws  {
        String $r2 = $r1.getString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE);
        if ($r2 == null) {
            return $r1.getString(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
        }
        return $r2;
    }

    protected boolean tryIntent(Intent $r1, int $i0) throws  {
        if ($r1 == null) {
            return false;
        }
        try {
            this.loginClient.getFragment().startActivityForResult($r1, $i0);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    KatanaProxyLoginMethodHandler(Parcel $r1) throws  {
        super($r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
    }
}
