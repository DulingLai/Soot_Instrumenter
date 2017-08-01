package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public final class SharePhoto extends ShareMedia {
    public static final Creator<SharePhoto> CREATOR = new C06171();
    private final Bitmap bitmap;
    private final String caption;
    private final Uri imageUrl;
    private final boolean userGenerated;

    static class C06171 implements Creator<SharePhoto> {
        C06171() throws  {
        }

        public SharePhoto createFromParcel(Parcel $r1) throws  {
            return new SharePhoto($r1);
        }

        public SharePhoto[] newArray(int $i0) throws  {
            return new SharePhoto[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareMedia.Builder<SharePhoto, Builder> {
        private Bitmap bitmap;
        private String caption;
        private Uri imageUrl;
        private boolean userGenerated;

        public Builder setBitmap(@Nullable Bitmap $r1) throws  {
            this.bitmap = $r1;
            return this;
        }

        public Builder setImageUrl(@Nullable Uri $r1) throws  {
            this.imageUrl = $r1;
            return this;
        }

        public Builder setUserGenerated(boolean $z0) throws  {
            this.userGenerated = $z0;
            return this;
        }

        public Builder setCaption(@Nullable String $r1) throws  {
            this.caption = $r1;
            return this;
        }

        Uri getImageUrl() throws  {
            return this.imageUrl;
        }

        Bitmap getBitmap() throws  {
            return this.bitmap;
        }

        public SharePhoto build() throws  {
            return new SharePhoto();
        }

        public Builder readFrom(SharePhoto $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareMedia) $r0)).setBitmap($r0.getBitmap()).setImageUrl($r0.getImageUrl()).setUserGenerated($r0.getUserGenerated()).setCaption($r0.getCaption());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((SharePhoto) $r1.readParcelable(SharePhoto.class.getClassLoader()));
        }

        public static void writeListTo(@Signature({"(", "Landroid/os/Parcel;", "Ljava/util/List", "<", "Lcom/facebook/share/model/SharePhoto;", ">;)V"}) Parcel $r0, @Signature({"(", "Landroid/os/Parcel;", "Ljava/util/List", "<", "Lcom/facebook/share/model/SharePhoto;", ">;)V"}) List<SharePhoto> $r1) throws  {
            ArrayList $r2 = new ArrayList();
            for (SharePhoto add : $r1) {
                $r2.add(add);
            }
            $r0.writeTypedList($r2);
        }

        public static List<SharePhoto> readListFrom(@Signature({"(", "Landroid/os/Parcel;", ")", "Ljava/util/List", "<", "Lcom/facebook/share/model/SharePhoto;", ">;"}) Parcel $r0) throws  {
            ArrayList $r1 = new ArrayList();
            $r0.readTypedList($r1, SharePhoto.CREATOR);
            return $r1;
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private SharePhoto(Builder $r1) throws  {
        super((com.facebook.share.model.ShareMedia.Builder) $r1);
        this.bitmap = $r1.bitmap;
        this.imageUrl = $r1.imageUrl;
        this.userGenerated = $r1.userGenerated;
        this.caption = $r1.caption;
    }

    SharePhoto(Parcel $r1) throws  {
        super($r1);
        this.bitmap = (Bitmap) $r1.readParcelable(Bitmap.class.getClassLoader());
        this.imageUrl = (Uri) $r1.readParcelable(Uri.class.getClassLoader());
        this.userGenerated = $r1.readByte() != (byte) 0;
        this.caption = $r1.readString();
    }

    @Nullable
    public Bitmap getBitmap() throws  {
        return this.bitmap;
    }

    @Nullable
    public Uri getImageUrl() throws  {
        return this.imageUrl;
    }

    public boolean getUserGenerated() throws  {
        return this.userGenerated;
    }

    public String getCaption() throws  {
        return this.caption;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        boolean $z0 = false;
        super.writeToParcel($r1, $i0);
        $r1.writeParcelable(this.bitmap, 0);
        $r1.writeParcelable(this.imageUrl, 0);
        if (this.userGenerated) {
            $z0 = true;
        }
        $r1.writeByte((byte) $z0);
        $r1.writeString(this.caption);
    }
}
