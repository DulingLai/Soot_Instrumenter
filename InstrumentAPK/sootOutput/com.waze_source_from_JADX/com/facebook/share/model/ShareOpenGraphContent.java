package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareOpenGraphContent extends ShareContent<ShareOpenGraphContent, Builder> {
    public static final Creator<ShareOpenGraphContent> CREATOR = new C06151();
    private final ShareOpenGraphAction action;
    private final String previewPropertyName;

    static class C06151 implements Creator<ShareOpenGraphContent> {
        C06151() throws  {
        }

        public ShareOpenGraphContent createFromParcel(Parcel $r1) throws  {
            return new ShareOpenGraphContent($r1);
        }

        public ShareOpenGraphContent[] newArray(int $i0) throws  {
            return new ShareOpenGraphContent[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareContent.Builder<ShareOpenGraphContent, Builder> {
        private ShareOpenGraphAction action;
        private String previewPropertyName;

        public Builder setAction(@Nullable ShareOpenGraphAction $r1) throws  {
            this.action = $r1 == null ? null : new com.facebook.share.model.ShareOpenGraphAction.Builder().readFrom($r1).build();
            return this;
        }

        public Builder setPreviewPropertyName(@Nullable String $r1) throws  {
            this.previewPropertyName = $r1;
            return this;
        }

        public ShareOpenGraphContent build() throws  {
            return new ShareOpenGraphContent();
        }

        public Builder readFrom(ShareOpenGraphContent $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareContent) $r0)).setAction($r0.getAction()).setPreviewPropertyName($r0.getPreviewPropertyName());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((ShareOpenGraphContent) $r1.readParcelable(ShareOpenGraphContent.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private ShareOpenGraphContent(Builder $r1) throws  {
        super((com.facebook.share.model.ShareContent.Builder) $r1);
        this.action = $r1.action;
        this.previewPropertyName = $r1.previewPropertyName;
    }

    ShareOpenGraphContent(Parcel $r1) throws  {
        super($r1);
        this.action = new com.facebook.share.model.ShareOpenGraphAction.Builder().readFrom($r1).build();
        this.previewPropertyName = $r1.readString();
    }

    @Nullable
    public ShareOpenGraphAction getAction() throws  {
        return this.action;
    }

    @Nullable
    public String getPreviewPropertyName() throws  {
        return this.previewPropertyName;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeParcelable(this.action, 0);
        $r1.writeString(this.previewPropertyName);
    }
}
