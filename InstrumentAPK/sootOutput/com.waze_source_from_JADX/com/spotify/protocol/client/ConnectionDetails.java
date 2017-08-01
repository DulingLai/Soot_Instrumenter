package com.spotify.protocol.client;

import dalvik.annotation.Signature;
import java.util.List;
import java.util.Map;

public class ConnectionDetails {
    private final String mAuthId;
    private final String[] mAuthMethods;
    private final Map<String, String> mExtras;
    private final String mId;
    private final int mImageHeight;
    private final int mImageWidth;
    private final String mModel;
    private final String mName;
    private final List<String> mRequiredFeatures;
    private final int mThumbnailImageHeight;
    private final int mThumbnailImageWidth;

    public static class Builder {
        private String mAuthId;
        private String[] mAuthMethods;
        private Map<String, String> mExtras;
        private final String mId;
        private int mImageSize;
        private String mModel;
        private String mName;
        private List<String> mRequiredFeatures;
        private int mThumbnailSize;

        public Builder(String $r1) throws  {
            this.mId = $r1;
        }

        public Builder setImageSize(int $i0) throws  {
            this.mImageSize = $i0;
            return this;
        }

        public Builder setThumbnailSize(int $i0) throws  {
            this.mThumbnailSize = $i0;
            return this;
        }

        public Builder setAuthMethods(String[] $r1) throws  {
            this.mAuthMethods = $r1;
            return this;
        }

        public Builder setAuthId(String $r1) throws  {
            this.mAuthId = $r1;
            return this;
        }

        public Builder setExtras(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lcom/spotify/protocol/client/ConnectionDetails$Builder;"}) Map<String, String> $r1) throws  {
            this.mExtras = $r1;
            return this;
        }

        public Builder setName(String $r1) throws  {
            this.mName = $r1;
            return this;
        }

        public String getName() throws  {
            return this.mName;
        }

        public String getModel() throws  {
            return this.mModel;
        }

        public Builder setModel(String $r1) throws  {
            this.mModel = $r1;
            return this;
        }

        public Builder setRequiredFeatures(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/spotify/protocol/client/ConnectionDetails$Builder;"}) List<String> $r1) throws  {
            this.mRequiredFeatures = $r1;
            return this;
        }

        public ConnectionDetails build() throws  {
            return new ConnectionDetails(this.mId, this.mName, this.mModel, this.mImageSize, this.mImageSize, this.mThumbnailSize, this.mThumbnailSize, this.mAuthMethods, this.mAuthId, this.mExtras, this.mRequiredFeatures);
        }
    }

    private ConnectionDetails(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String[] $r4, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r5, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r6, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r7) throws  {
        this.mId = $r1;
        this.mName = $r2;
        this.mModel = $r3;
        this.mImageWidth = $i0;
        this.mImageHeight = $i1;
        this.mThumbnailImageWidth = $i2;
        this.mThumbnailImageHeight = $i3;
        this.mAuthMethods = $r4;
        this.mAuthId = $r5;
        this.mExtras = $r6;
        this.mRequiredFeatures = $r7;
    }

    public String getId() throws  {
        return this.mId;
    }

    public String getName() throws  {
        return this.mName;
    }

    public String getModel() throws  {
        return this.mModel;
    }

    public int getImageHeight() throws  {
        return this.mImageHeight;
    }

    public int getImageWidth() throws  {
        return this.mImageWidth;
    }

    public int getThumbnailImageHeight() throws  {
        return this.mThumbnailImageHeight;
    }

    public int getThumbnailImageWidth() throws  {
        return this.mThumbnailImageWidth;
    }

    public String getAuthId() throws  {
        return this.mAuthId;
    }

    public String[] getAuthMethods() throws  {
        return this.mAuthMethods;
    }

    public Map<String, String> getExtras() throws  {
        return this.mExtras;
    }

    public List<String> getRequiredFeatures() throws  {
        return this.mRequiredFeatures;
    }
}
