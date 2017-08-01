package com.spotify.android.appremote.api;

import com.spotify.protocol.client.RequiredFeatures;
import com.spotify.protocol.mappers.JsonMapper;
import com.spotify.protocol.mappers.gson.GsonMapper;
import dalvik.annotation.Signature;
import java.util.List;

public class ConnectionParams {
    private final AuthMethod mAuthMethod;
    private final String mClientId;
    private final int mImageSize;
    private final JsonMapper mJsonMapper;
    private final String mRedirectUri;
    private final List<String> mRequiredFeatures;
    private final boolean mShowAuthView;
    private final int mThumbnailImageSize;

    public enum AuthMethod {
        APP_ID,
        NONE
    }

    public static class Builder {
        private AuthMethod mAuthMethod;
        private final String mClientId;
        private int mImageSize;
        private JsonMapper mJsonMapper;
        private String mRedirectUri;
        private List<String> mRequiredFeatures;
        private boolean mShowAuthView;
        private int mThumbnailImageSize;

        public Builder(String $r1) throws  {
            this.mClientId = $r1;
        }

        public Builder setRedirectUri(String $r1) throws  {
            this.mRedirectUri = $r1;
            return this;
        }

        public Builder setAuthMethod(AuthMethod $r1) throws  {
            this.mAuthMethod = $r1;
            return this;
        }

        public Builder showAuthView(boolean $z0) throws  {
            this.mShowAuthView = $z0;
            return this;
        }

        public Builder setPreferredImageSize(int $i0) throws  {
            this.mImageSize = $i0;
            return this;
        }

        public Builder setPreferredThumbnailImageSize(int $i0) throws  {
            this.mThumbnailImageSize = $i0;
            return this;
        }

        public Builder setRequiredFeatures(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/spotify/android/appremote/api/ConnectionParams$Builder;"}) List<String> $r1) throws  {
            this.mRequiredFeatures = $r1;
            return this;
        }

        public Builder setJsonMapper(JsonMapper $r1) throws  {
            this.mJsonMapper = $r1;
            return this;
        }

        public ConnectionParams build() throws  {
            return new ConnectionParams(this.mClientId, this.mAuthMethod, this.mRedirectUri, this.mShowAuthView, this.mImageSize, this.mThumbnailImageSize, this.mRequiredFeatures, this.mJsonMapper);
        }
    }

    private ConnectionParams(@Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) AuthMethod $r4, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) boolean $z0, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) List<String> $r5, @Signature({"(", "Ljava/lang/String;", "Lcom/spotify/android/appremote/api/ConnectionParams$AuthMethod;", "Ljava/lang/String;", "ZII", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/spotify/protocol/mappers/JsonMapper;", ")V"}) JsonMapper $r6) throws  {
        List $r52;
        this.mClientId = $r1;
        if ($r4 == null) {
            $r4 = AuthMethod.APP_ID;
        }
        this.mAuthMethod = $r4;
        this.mShowAuthView = $z0;
        this.mImageSize = $i0;
        this.mThumbnailImageSize = $i1;
        this.mRedirectUri = $r2;
        if ($r5 == null) {
            $r52 = RequiredFeatures.FEATURES;
        }
        this.mRequiredFeatures = $r52;
        if ($r6 == null) {
            $r6 = GsonMapper.create();
        }
        this.mJsonMapper = $r6;
    }

    public String getClientId() throws  {
        return this.mClientId;
    }

    public String getRedirectUri() throws  {
        return this.mRedirectUri;
    }

    public AuthMethod getAuthMethod() throws  {
        return this.mAuthMethod;
    }

    public boolean getShowAuthView() throws  {
        return this.mShowAuthView;
    }

    public int getImageSize() throws  {
        return this.mImageSize;
    }

    public int getThumbnailImageSize() throws  {
        return this.mThumbnailImageSize;
    }

    public List<String> getRequiredFeatures() throws  {
        return this.mRequiredFeatures;
    }

    public JsonMapper getJsonMapper() throws  {
        return this.mJsonMapper;
    }
}
