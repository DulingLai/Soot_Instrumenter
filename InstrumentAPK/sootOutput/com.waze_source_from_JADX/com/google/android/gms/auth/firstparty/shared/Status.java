package com.google.android.gms.auth.firstparty.shared;

import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public enum Status {
    CLIENT_LOGIN_DISABLED("ClientLoginDisabled"),
    DEVICE_MANAGEMENT_REQUIRED("DeviceManagementRequiredOrSyncDisabled"),
    SOCKET_TIMEOUT("SocketTimeout"),
    SUCCESS("Ok"),
    UNKNOWN_ERROR("UNKNOWN_ERR"),
    NETWORK_ERROR("NetworkError"),
    SERVICE_UNAVAILABLE("ServiceUnavailable"),
    INTNERNAL_ERROR("InternalError"),
    BAD_AUTHENTICATION("BadAuthentication"),
    EMPTY_CONSUMER_PKG_OR_SIG("EmptyConsumerPackageOrSig"),
    NEEDS_2F("InvalidSecondFactor"),
    NEEDS_POST_SIGN_IN_FLOW("PostSignInFlowRequired"),
    NEEDS_BROWSER("NeedsBrowser"),
    UNKNOWN("Unknown"),
    NOT_VERIFIED("NotVerified"),
    TERMS_NOT_AGREED("TermsNotAgreed"),
    ACCOUNT_DISABLED("AccountDisabled"),
    CAPTCHA("CaptchaRequired"),
    ACCOUNT_DELETED("AccountDeleted"),
    SERVICE_DISABLED(NativeProtocol.ERROR_SERVICE_DISABLED),
    NEED_PERMISSION("NeedPermission"),
    INVALID_SCOPE("INVALID_SCOPE"),
    USER_CANCEL("UserCancel"),
    PERMISSION_DENIED(NativeProtocol.ERROR_PERMISSION_DENIED),
    INVALID_AUDIENCE("INVALID_AUDIENCE"),
    UNREGISTERED_ON_API_CONSOLE("UNREGISTERED_ON_API_CONSOLE"),
    THIRD_PARTY_DEVICE_MANAGEMENT_REQUIRED("ThirdPartyDeviceManagementRequired"),
    DM_INTERNAL_ERROR("DeviceManagementInternalError"),
    DM_SYNC_DISABLED("DeviceManagementSyncDisabled"),
    DM_ADMIN_BLOCKED("DeviceManagementAdminBlocked"),
    DM_ADMIN_PENDING_APPROVAL("DeviceManagementAdminPendingApproval"),
    DM_STALE_SYNC_REQUIRED("DeviceManagementStaleSyncRequired"),
    DM_DEACTIVATED("DeviceManagementDeactivated"),
    DM_REQUIRED("DeviceManagementRequired"),
    ALREADY_HAS_GMAIL("ALREADY_HAS_GMAIL"),
    BAD_PASSWORD("WeakPassword"),
    BAD_REQUEST("BadRequest"),
    BAD_USERNAME("BadUsername"),
    DELETED_GMAIL("DeletedGmail"),
    EXISTING_USERNAME("ExistingUsername"),
    LOGIN_FAIL("LoginFail"),
    NOT_LOGGED_IN("NotLoggedIn"),
    NO_GMAIL("NoGmail"),
    REQUEST_DENIED("RequestDenied"),
    SERVER_ERROR("ServerError"),
    USERNAME_UNAVAILABLE("UsernameUnavailable"),
    GPLUS_OTHER("GPlusOther"),
    GPLUS_NICKNAME("GPlusNickname"),
    GPLUS_INVALID_CHAR("GPlusInvalidChar"),
    GPLUS_INTERSTITIAL("GPlusInterstitial"),
    GPLUS_PROFILE_ERROR("ProfileUpgradeError");
    
    public static final String EXTRA_KEY_STATUS = "Error";
    public static final String JSON_KEY_STATUS = "status";
    private final String iN;

    private Status(@Signature({"(", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        this.iN = $r2;
    }

    public static final Status fromWireCode(String $r0) throws  {
        Status $r1 = null;
        Status[] $r2 = values();
        int $i0 = $r2.length;
        int $i1 = 0;
        while ($i1 < $i0) {
            Status $r3 = $r2[$i1];
            if (!$r3.iN.equals($r0)) {
                $r3 = $r1;
            }
            $i1++;
            $r1 = $r3;
        }
        return $r1;
    }

    public static boolean isRetryableError(Status $r0) throws  {
        return NETWORK_ERROR.equals($r0) || SERVICE_UNAVAILABLE.equals($r0);
    }

    public static boolean isUserRecoverableError(Status $r0) throws  {
        return BAD_AUTHENTICATION.equals($r0) || CAPTCHA.equals($r0) || NEED_PERMISSION.equals($r0) || NEEDS_BROWSER.equals($r0) || USER_CANCEL.equals($r0) || DEVICE_MANAGEMENT_REQUIRED.equals($r0) || DM_INTERNAL_ERROR.equals($r0) || DM_SYNC_DISABLED.equals($r0) || DM_ADMIN_BLOCKED.equals($r0) || DM_ADMIN_PENDING_APPROVAL.equals($r0) || DM_STALE_SYNC_REQUIRED.equals($r0) || DM_DEACTIVATED.equals($r0) || DM_REQUIRED.equals($r0) || THIRD_PARTY_DEVICE_MANAGEMENT_REQUIRED.equals($r0);
    }

    public boolean equals(String $r1) throws  {
        return this.iN.equals($r1);
    }

    public String getWire() throws  {
        return this.iN;
    }
}
