package com.facebook.share.model;

import android.os.Parcel;
import com.facebook.share.ShareBuilder;
import dalvik.annotation.Signature;

public interface ShareModelBuilder<P extends ShareModel, E extends ShareModelBuilder> extends ShareBuilder<P, E> {
    E readFrom(@Signature({"(", "Landroid/os/Parcel;", ")TE;"}) Parcel parcel) throws ;

    E readFrom(@Signature({"(TP;)TE;"}) P p) throws ;
}
