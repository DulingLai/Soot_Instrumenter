package com.spotify.sdk.android.authentication;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.spotify.sdk.android.authentication.AuthenticationResponse.Type;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AuthenticationRequest implements Parcelable {
    static final String ACCOUNTS_AUTHORITY = "accounts.spotify.com";
    static final String ACCOUNTS_PATH = "authorize";
    static final String ACCOUNTS_SCHEME = "https";
    static final String ANDROID_SDK = "android-sdk";
    public static final Creator<AuthenticationRequest> CREATOR = new C10821();
    static final String SCOPES_SEPARATOR = " ";
    static final String SPOTIFY_SDK = "spotify-sdk";
    private final String mCampaign;
    private final String mClientId;
    private final Map<String, String> mCustomParams;
    private final String mRedirectUri;
    private final String mResponseType;
    private final String[] mScopes;
    private final boolean mShowDialog;
    private final String mState;

    static class C10821 implements Creator<AuthenticationRequest> {
        C10821() throws  {
        }

        public AuthenticationRequest createFromParcel(Parcel $r1) throws  {
            return new AuthenticationRequest($r1);
        }

        public AuthenticationRequest[] newArray(int $i0) throws  {
            return new AuthenticationRequest[$i0];
        }
    }

    public static class Builder {
        private String mCampaign;
        private final String mClientId;
        private final Map<String, String> mCustomParams = new HashMap();
        private final String mRedirectUri;
        private final Type mResponseType;
        private String[] mScopes;
        private boolean mShowDialog;
        private String mState;

        public Builder(String $r1, Type $r2, String $r3) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("Client ID can't be null");
            } else if ($r2 == null) {
                throw new IllegalArgumentException("Response type can't be null");
            } else if ($r3 == null || $r3.length() == 0) {
                throw new IllegalArgumentException("Redirect URI can't be null or empty");
            } else {
                this.mClientId = $r1;
                this.mResponseType = $r2;
                this.mRedirectUri = $r3;
            }
        }

        public Builder setState(String $r1) throws  {
            this.mState = $r1;
            return this;
        }

        public Builder setScopes(String[] $r1) throws  {
            this.mScopes = $r1;
            return this;
        }

        public Builder setShowDialog(boolean $z0) throws  {
            this.mShowDialog = $z0;
            return this;
        }

        public Builder setCustomParam(String $r1, String $r2) throws  {
            if ($r1 == null || $r1.isEmpty()) {
                throw new IllegalArgumentException("Custom parameter key can't be null or empty");
            } else if ($r2 == null || $r2.isEmpty()) {
                throw new IllegalArgumentException("Custom parameter value can't be null or empty");
            } else {
                this.mCustomParams.put($r1, $r2);
                return this;
            }
        }

        public Builder setCampaign(String $r1) throws  {
            this.mCampaign = $r1;
            return this;
        }

        public AuthenticationRequest build() throws  {
            return new AuthenticationRequest(this.mClientId, this.mResponseType, this.mRedirectUri, this.mState, this.mScopes, this.mShowDialog, this.mCustomParams, this.mCampaign);
        }
    }

    static final class QueryParams {
        public static final String CLIENT_ID = "client_id";
        public static final String REDIRECT_URI = "redirect_uri";
        public static final String RESPONSE_TYPE = "response_type";
        public static final String SCOPE = "scope";
        public static final String SHOW_DIALOG = "show_dialog";
        public static final String STATE = "state";
        public static final String UTM_CAMPAIGN = "utm_campaign";
        public static final String UTM_MEDIUM = "utm_medium";
        public static final String UTM_SOURCE = "utm_source";

        QueryParams() throws  {
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public AuthenticationRequest(Parcel $r1) throws  {
        boolean $z0 = true;
        AuthenticationRequest authenticationRequest = this;
        this.mClientId = $r1.readString();
        this.mResponseType = $r1.readString();
        this.mRedirectUri = $r1.readString();
        this.mState = $r1.readString();
        this.mScopes = $r1.createStringArray();
        if ($r1.readByte() != (byte) 1) {
            $z0 = false;
        }
        this.mShowDialog = $z0;
        this.mCustomParams = new HashMap();
        this.mCampaign = $r1.readString();
        Bundle $r7 = $r1.readBundle(getClass().getClassLoader());
        for (String $r2 : $r7.keySet()) {
            this.mCustomParams.put($r2, $r7.getString($r2));
        }
    }

    public String getClientId() throws  {
        return this.mClientId;
    }

    public String getResponseType() throws  {
        return this.mResponseType;
    }

    public String getRedirectUri() throws  {
        return this.mRedirectUri;
    }

    public String getState() throws  {
        return this.mState;
    }

    public String[] getScopes() throws  {
        return this.mScopes;
    }

    public String getCustomParam(String $r1) throws  {
        return (String) this.mCustomParams.get($r1);
    }

    public String getCampaign() throws  {
        return TextUtils.isEmpty(this.mCampaign) ? ANDROID_SDK : this.mCampaign;
    }

    private AuthenticationRequest(@Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) Type $r2, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String[] $r5, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) Map<String, String> $r6, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/sdk/android/authentication/AuthenticationResponse$Type;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String $r7) throws  {
        this.mClientId = $r1;
        this.mResponseType = $r2.toString();
        this.mRedirectUri = $r3;
        this.mState = $r4;
        this.mScopes = $r5;
        this.mShowDialog = $z0;
        this.mCustomParams = $r6;
        this.mCampaign = $r7;
    }

    public Uri toUri() throws  {
        android.net.Uri.Builder $r1 = new android.net.Uri.Builder();
        String str = "response_type";
        str = "redirect_uri";
        str = "utm_campaign";
        $r1.scheme(ACCOUNTS_SCHEME).authority(ACCOUNTS_AUTHORITY).appendPath(ACCOUNTS_PATH).appendQueryParameter("client_id", this.mClientId).appendQueryParameter(str, this.mResponseType).appendQueryParameter(str, this.mRedirectUri).appendQueryParameter(QueryParams.SHOW_DIALOG, String.valueOf(this.mShowDialog)).appendQueryParameter("utm_source", SPOTIFY_SDK).appendQueryParameter("utm_medium", ANDROID_SDK).appendQueryParameter(str, getCampaign());
        if (this.mScopes != null && this.mScopes.length > 0) {
            $r1.appendQueryParameter("scope", scopesToString());
        }
        if (this.mState != null) {
            $r1.appendQueryParameter("state", this.mState);
        }
        if (this.mCustomParams.size() > 0) {
            for (Entry $r9 : this.mCustomParams.entrySet()) {
                $r1.appendQueryParameter((String) $r9.getKey(), (String) $r9.getValue());
            }
        }
        return $r1.build();
    }

    private String scopesToString() throws  {
        StringBuilder $r1 = new StringBuilder();
        for (String $r2 : this.mScopes) {
            $r1.append($r2);
            $r1.append(SCOPES_SEPARATOR);
        }
        return $r1.toString().trim();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.mClientId);
        $r1.writeString(this.mResponseType);
        $r1.writeString(this.mRedirectUri);
        $r1.writeString(this.mState);
        $r1.writeStringArray(this.mScopes);
        $r1.writeByte((byte) (this.mShowDialog));
        $r1.writeString(this.mCampaign);
        Bundle $r2 = new Bundle();
        for (Entry $r9 : this.mCustomParams.entrySet()) {
            $r2.putString((String) $r9.getKey(), (String) $r9.getValue());
        }
        $r1.writeBundle($r2);
    }
}
