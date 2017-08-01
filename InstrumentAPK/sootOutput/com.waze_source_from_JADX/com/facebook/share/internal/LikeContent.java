package com.facebook.share.internal;

import android.os.Parcel;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public class LikeContent implements ShareModel {
    private final String objectId;
    private final String objectType;

    public static class Builder implements ShareModelBuilder<LikeContent, Builder> {
        private String objectId;
        private String objectType;

        public Builder setObjectId(String $r1) throws  {
            this.objectId = $r1;
            return this;
        }

        public Builder setObjectType(String $r1) throws  {
            this.objectType = $r1;
            return this;
        }

        public LikeContent build() throws  {
            return new LikeContent();
        }

        public Builder readFrom(LikeContent $r0) throws  {
            return $r0 == null ? this : setObjectId($r0.getObjectId()).setObjectType($r0.getObjectType());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((LikeContent) $r1.readParcelable(LikeContent.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private LikeContent(Builder $r1) throws  {
        this.objectId = $r1.objectId;
        this.objectType = $r1.objectType;
    }

    LikeContent(Parcel $r1) throws  {
        this.objectId = $r1.readString();
        this.objectType = $r1.readString();
    }

    public String getObjectId() throws  {
        return this.objectId;
    }

    public String getObjectType() throws  {
        return this.objectType;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.objectId);
        $r1.writeString(this.objectType);
    }
}
