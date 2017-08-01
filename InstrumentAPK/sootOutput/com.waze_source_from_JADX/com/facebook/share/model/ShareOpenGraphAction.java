package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareOpenGraphAction extends ShareOpenGraphValueContainer<ShareOpenGraphAction, Builder> {
    public static final Creator<ShareOpenGraphAction> CREATOR = new C06141();

    static class C06141 implements Creator<ShareOpenGraphAction> {
        C06141() throws  {
        }

        public ShareOpenGraphAction createFromParcel(Parcel $r1) throws  {
            return new ShareOpenGraphAction($r1);
        }

        public ShareOpenGraphAction[] newArray(int $i0) throws  {
            return new ShareOpenGraphAction[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareOpenGraphValueContainer.Builder<ShareOpenGraphAction, Builder> {
        private static final String ACTION_TYPE_KEY = "og:type";

        public Builder setActionType(String $r1) throws  {
            putString(ACTION_TYPE_KEY, $r1);
            return this;
        }

        public ShareOpenGraphAction build() throws  {
            return new ShareOpenGraphAction();
        }

        public Builder readFrom(ShareOpenGraphAction $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareOpenGraphValueContainer) $r0)).setActionType($r0.getActionType());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((ShareOpenGraphAction) $r1.readParcelable(ShareOpenGraphAction.class.getClassLoader()));
        }
    }

    private ShareOpenGraphAction(Builder $r1) throws  {
        super((com.facebook.share.model.ShareOpenGraphValueContainer.Builder) $r1);
    }

    ShareOpenGraphAction(Parcel $r1) throws  {
        super($r1);
    }

    @Nullable
    public String getActionType() throws  {
        return getString("og:type");
    }
}
