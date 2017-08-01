package com.spotify.sdk.android.authentication;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import dalvik.annotation.Signature;

public class AuthenticationResponse implements Parcelable {
    public static final Creator<AuthenticationResponse> CREATOR = new C10831();
    private final String mAccessToken;
    private final String mCode;
    private final String mError;
    private final int mExpiresIn;
    private final String mState;
    private final Type mType;

    static class C10831 implements Creator<AuthenticationResponse> {
        C10831() throws  {
        }

        public AuthenticationResponse createFromParcel(Parcel $r1) throws  {
            return new AuthenticationResponse($r1);
        }

        public AuthenticationResponse[] newArray(int $i0) throws  {
            return new AuthenticationResponse[$i0];
        }
    }

    public static class Builder {
        private String mAccessToken;
        private String mCode;
        private String mError;
        private int mExpiresIn;
        private String mState;
        private Type mType;

        public Builder setType(Type $r1) throws  {
            this.mType = $r1;
            return this;
        }

        public Builder setCode(String $r1) throws  {
            this.mCode = $r1;
            return this;
        }

        public Builder setAccessToken(String $r1) throws  {
            this.mAccessToken = $r1;
            return this;
        }

        public Builder setState(String $r1) throws  {
            this.mState = $r1;
            return this;
        }

        public Builder setError(String $r1) throws  {
            this.mError = $r1;
            return this;
        }

        public Builder setExpiresIn(int $i0) throws  {
            this.mExpiresIn = $i0;
            return this;
        }

        public AuthenticationResponse build() throws  {
            return new AuthenticationResponse(this.mType, this.mCode, this.mAccessToken, this.mState, this.mError, this.mExpiresIn);
        }
    }

    static final class QueryParams {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String CODE = "code";
        public static final String ERROR = "error";
        public static final String EXPIRES_IN = "expires_in";
        public static final String STATE = "state";

        QueryParams() throws  {
        }
    }

    public enum Type {
        CODE(QueryParams.CODE),
        TOKEN("token"),
        ERROR("error"),
        EMPTY("empty"),
        UNKNOWN("unknown");
        
        private final String mType;

        private Type(@Signature({"(", "Ljava/lang/String;", ")V"}) String $r2) throws  {
            this.mType = $r2;
        }

        public String toString() throws  {
            return this.mType;
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private AuthenticationResponse(Type $r5, String $r1, String $r2, String $r3, String $r4, int $i0) throws  {
        if ($r5 == null) {
            $r5 = Type.UNKNOWN;
        }
        this.mType = $r5;
        this.mCode = $r1;
        this.mAccessToken = $r2;
        this.mState = $r3;
        this.mError = $r4;
        this.mExpiresIn = $i0;
    }

    public AuthenticationResponse(Parcel $r1) throws  {
        this.mExpiresIn = $r1.readInt();
        this.mError = $r1.readString();
        this.mState = $r1.readString();
        this.mAccessToken = $r1.readString();
        this.mCode = $r1.readString();
        this.mType = Type.values()[$r1.readInt()];
    }

    public static AuthenticationResponse fromUri(Uri $r0) throws  {
        Builder $r1 = new Builder();
        if ($r0 == null) {
            $r1.setType(Type.EMPTY);
            return $r1.build();
        }
        String $r5 = $r0.getQueryParameter("error");
        if ($r5 != null) {
            String $r6 = $r0.getQueryParameter("state");
            $r1.setError($r5);
            $r1.setState($r6);
            $r1.setType(Type.ERROR);
            return $r1.build();
        }
        $r5 = $r0.getQueryParameter(QueryParams.CODE);
        if ($r5 != null) {
            $r6 = $r0.getQueryParameter("state");
            $r1.setCode($r5);
            $r1.setState($r6);
            $r1.setType(Type.CODE);
            return $r1.build();
        }
        $r5 = $r0.getEncodedFragment();
        if ($r5 == null || $r5.length() <= 0) {
            $r1.setType(Type.UNKNOWN);
            return $r1.build();
        }
        String[] $r7 = $r5.split("&");
        $r5 = null;
        $r6 = null;
        String $r8 = null;
        for (String $r2 : $r7) {
            String[] $r9 = $r2.split("=");
            if ($r9.length == 2) {
                if ($r9[0].startsWith("access_token")) {
                    $r5 = Uri.decode($r9[1]);
                }
                if ($r9[0].startsWith("state")) {
                    $r6 = Uri.decode($r9[1]);
                }
                if ($r9[0].startsWith("expires_in")) {
                    $r8 = Uri.decode($r9[1]);
                }
            }
        }
        $r1.setAccessToken($r5);
        $r1.setState($r6);
        if ($r8 != null) {
            try {
                $r1.setExpiresIn(Integer.parseInt($r8));
            } catch (NumberFormatException e) {
            }
        }
        $r1.setType(Type.TOKEN);
        return $r1.build();
    }

    public Type getType() throws  {
        return this.mType;
    }

    public String getCode() throws  {
        return this.mCode;
    }

    public String getAccessToken() throws  {
        return this.mAccessToken;
    }

    public String getState() throws  {
        return this.mState;
    }

    public String getError() throws  {
        return this.mError;
    }

    public int getExpiresIn() throws  {
        return this.mExpiresIn;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeInt(this.mExpiresIn);
        $r1.writeString(this.mError);
        $r1.writeString(this.mState);
        $r1.writeString(this.mAccessToken);
        $r1.writeString(this.mCode);
        $r1.writeInt(this.mType.ordinal());
    }
}
