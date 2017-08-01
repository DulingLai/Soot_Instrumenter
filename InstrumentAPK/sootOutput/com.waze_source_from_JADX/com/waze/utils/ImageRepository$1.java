package com.waze.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

class ImageRepository$1 extends ImageRepository$ImageRepositoryListener {
    final /* synthetic */ ImageRepository this$0;
    final /* synthetic */ ImageView val$toSet;
    final /* synthetic */ String val$urlStr;

    ImageRepository$1(ImageRepository this$0, ImageView imageView, String str) {
        this.this$0 = this$0;
        this.val$toSet = imageView;
        this.val$urlStr = str;
    }

    public void onImageRetrieved(final Bitmap bitmap) {
        if (bitmap != null && this.val$toSet != null) {
            this.val$toSet.post(new Runnable() {
                public void run() {
                    if (ImageRepository$1.this.val$toSet != null && ImageRepository$1.this.val$urlStr.equals(ImageRepository$1.this.val$toSet.getTag())) {
                        ImageRepository$1.this.val$toSet.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }
}
