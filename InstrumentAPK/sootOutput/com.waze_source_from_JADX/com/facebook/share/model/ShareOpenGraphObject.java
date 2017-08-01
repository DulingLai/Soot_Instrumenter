package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.internal.NativeProtocol;

public final class ShareOpenGraphObject extends ShareOpenGraphValueContainer<ShareOpenGraphObject, Builder> {
    public static final Creator<ShareOpenGraphObject> CREATOR = new C06161();

    static class C06161 implements Creator<ShareOpenGraphObject> {
        C06161() throws  {
        }

        public ShareOpenGraphObject createFromParcel(Parcel $r1) throws  {
            return new ShareOpenGraphObject($r1);
        }

        public ShareOpenGraphObject[] newArray(int $i0) throws  {
            return new ShareOpenGraphObject[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareOpenGraphValueContainer.Builder<ShareOpenGraphObject, Builder> {
        public Builder() throws  {
            putBoolean(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY, true);
        }

        public ShareOpenGraphObject build() throws  {
            return new ShareOpenGraphObject();
        }

        public Builder readFrom(Parcel $r1) throws  {
            return (Builder) readFrom((ShareOpenGraphValueContainer) (ShareOpenGraphObject) $r1.readParcelable(ShareOpenGraphObject.class.getClassLoader()));
        }
    }

    private ShareOpenGraphObject(Builder $r1) throws  {
        super((com.facebook.share.model.ShareOpenGraphValueContainer.Builder) $r1);
    }

    ShareOpenGraphObject(Parcel $r1) throws  {
        super($r1);
    }
}
