package com.spotify.android.appremote.api;

import android.graphics.Bitmap;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.ImageUri;
import dalvik.annotation.Signature;

public interface ImagesApi {
    CallResult<Bitmap> getImage(@Signature({"(", "Lcom/spotify/protocol/types/ImageUri;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Landroid/graphics/Bitmap;", ">;"}) ImageUri imageUri) throws ;
}
