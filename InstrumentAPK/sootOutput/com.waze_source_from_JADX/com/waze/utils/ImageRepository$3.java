package com.waze.utils;

import android.graphics.Bitmap;

class ImageRepository$3 implements VolleyManager$ImageRequestListener {
    final /* synthetic */ ImageRepository this$0;
    final /* synthetic */ ImageRepository$ImageRepositoryListener val$listener;

    ImageRepository$3(ImageRepository this$0, ImageRepository$ImageRepositoryListener imageRepository$ImageRepositoryListener) {
        this.this$0 = this$0;
        this.val$listener = imageRepository$ImageRepositoryListener;
    }

    public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
        if (this.val$listener != null) {
            this.val$listener.onImageRetrieved(bitmap);
        }
    }

    public void onImageLoadFailed(Object token, long duration) {
        if (this.val$listener != null) {
            this.val$listener.onImageRetrieved(null);
        }
    }
}
