package com.google.android.gms.people;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.people.People.ReleasableResult;
import com.google.android.gms.people.model.AvatarReference;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface Images {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface SetAvatarResult extends Result {
        String getUrl() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadImageResult extends ReleasableResult {
        int getHeight() throws ;

        ParcelFileDescriptor getParcelFileDescriptor() throws ;

        int getWidth() throws ;

        boolean isRewindable() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadImageOptions {
        public static final LoadImageOptions DEFAULT = new Builder().build();
        public final int avatarOptions;
        public final int imageSize;
        public final boolean useLargePictureForCp2Images;

        /* compiled from: dalvik_source_com.waze.apk */
        public static class Builder {
            private int aLA = 1;
            private int aLZ = 0;
            public boolean mUseLargePictureForCp2Images;

            public final LoadImageOptions build() throws  {
                return new LoadImageOptions();
            }

            public Builder setAvatarOptions(int $i0) throws  {
                this.aLZ = $i0;
                return this;
            }

            public Builder setImageSize(int $i0) throws  {
                this.aLA = $i0;
                return this;
            }

            public Builder setUseLargePictureForCp2Images(boolean $z0) throws  {
                this.mUseLargePictureForCp2Images = $z0;
                return this;
            }
        }

        private LoadImageOptions(Builder $r1) throws  {
            this.imageSize = $r1.aLA;
            this.avatarOptions = $r1.aLZ;
            this.useLargePictureForCp2Images = $r1.mUseLargePictureForCp2Images;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface OnAvatarSetCallback {
        void onAvatarSet(SetAvatarResult setAvatarResult) throws ;
    }

    PendingResult<LoadImageResult> loadByReference(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) AvatarReference avatarReference, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) LoadImageOptions loadImageOptions) throws ;

    PendingResult<LoadImageResult> loadByUrl(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int i, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int i2) throws ;

    PendingResult<LoadImageResult> loadContactThumbnailByContactId(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) long j) throws ;

    PendingResult<LoadImageResult> loadOwnerAvatar(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int i, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int i2) throws ;

    PendingResult<LoadImageResult> loadOwnerCoverPhoto(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str2) throws ;

    @Deprecated
    PendingResult<LoadImageResult> loadOwnerCoverPhoto(@Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient googleApiClient, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str2, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int i) throws ;

    PendingResult<LoadImageResult> loadRemoteImage(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String str) throws ;

    PendingResult<SetAvatarResult> setAvatar(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) Uri uri, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) boolean z) throws ;
}
