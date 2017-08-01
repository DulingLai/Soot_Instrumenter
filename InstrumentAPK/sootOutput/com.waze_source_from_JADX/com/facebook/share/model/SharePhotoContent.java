package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SharePhotoContent extends ShareContent<SharePhotoContent, Builder> {
    public static final Creator<SharePhotoContent> CREATOR = new C06181();
    private final List<SharePhoto> photos;

    static class C06181 implements Creator<SharePhotoContent> {
        C06181() throws  {
        }

        public SharePhotoContent createFromParcel(Parcel $r1) throws  {
            return new SharePhotoContent($r1);
        }

        public SharePhotoContent[] newArray(int $i0) throws  {
            return new SharePhotoContent[$i0];
        }
    }

    public static class Builder extends com.facebook.share.model.ShareContent.Builder<SharePhotoContent, Builder> {
        private final List<SharePhoto> photos = new ArrayList();

        public Builder addPhoto(@Nullable SharePhoto $r1) throws  {
            if ($r1 == null) {
                return this;
            }
            this.photos.add(new com.facebook.share.model.SharePhoto.Builder().readFrom($r1).build());
            return this;
        }

        public Builder addPhotos(@Nullable @Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/share/model/SharePhoto;", ">;)", "Lcom/facebook/share/model/SharePhotoContent$Builder;"}) List<SharePhoto> $r1) throws  {
            if ($r1 == null) {
                return this;
            }
            for (SharePhoto addPhoto : $r1) {
                addPhoto(addPhoto);
            }
            return this;
        }

        public SharePhotoContent build() throws  {
            return new SharePhotoContent();
        }

        public Builder readFrom(SharePhotoContent $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareContent) $r0)).addPhotos($r0.getPhotos());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((SharePhotoContent) $r1.readParcelable(SharePhotoContent.class.getClassLoader()));
        }

        public Builder setPhotos(@Nullable @Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/share/model/SharePhoto;", ">;)", "Lcom/facebook/share/model/SharePhotoContent$Builder;"}) List<SharePhoto> $r1) throws  {
            this.photos.clear();
            addPhotos($r1);
            return this;
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private SharePhotoContent(Builder $r1) throws  {
        super((com.facebook.share.model.ShareContent.Builder) $r1);
        this.photos = Collections.unmodifiableList($r1.photos);
    }

    SharePhotoContent(Parcel $r1) throws  {
        super($r1);
        this.photos = Collections.unmodifiableList(com.facebook.share.model.SharePhoto.Builder.readListFrom($r1));
    }

    @Nullable
    public List<SharePhoto> getPhotos() throws  {
        return this.photos;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        com.facebook.share.model.SharePhoto.Builder.writeListTo($r1, this.photos);
    }
}
