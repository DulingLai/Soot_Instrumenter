package com.waze.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.waze.AppService;
import com.waze.ifs.ui.CircleShaderDrawable;

class ImageRepository$2 extends ImageRepository$ImageRepositoryListener {
    long loadTime = 0;
    final /* synthetic */ ImageRepository this$0;
    final /* synthetic */ boolean val$bRound;
    final /* synthetic */ int val$flags;
    final /* synthetic */ View val$toRemove;
    final /* synthetic */ ImageView val$toSet;
    final /* synthetic */ String val$urlStr;

    ImageRepository$2(ImageRepository this$0, ImageView imageView, View view, int i, boolean z, String str) {
        this.this$0 = this$0;
        this.val$toSet = imageView;
        this.val$toRemove = view;
        this.val$flags = i;
        this.val$bRound = z;
        this.val$urlStr = str;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    public void onImageRetrieved(final Bitmap bitmap) {
        AppService.getActiveActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (bitmap != null && ImageRepository$2.this.val$toSet != null && ImageRepository$2.this.isImageStillNeeded()) {
                    if (ImageRepository$2.this.loadTime > 300) {
                        ImageRepository.imageViewAnimatedChange(ImageRepository$2.this.val$toSet, ImageRepository$2.this.val$toRemove, bitmap, ImageRepository$2.this.val$flags);
                    } else if (ImageRepository$2.this.val$toSet != null) {
                        if (ImageRepository$2.this.val$bRound) {
                            ImageRepository$2.this.val$toSet.setImageDrawable(new CircleShaderDrawable(bitmap, 0));
                        } else {
                            ImageRepository$2.this.val$toSet.setImageBitmap(bitmap);
                        }
                        if (ImageRepository$2.this.val$toRemove != null) {
                            ImageRepository$2.this.val$toRemove.setVisibility(8);
                        }
                    }
                }
            }
        });
    }

    public boolean isImageStillNeeded() {
        return this.val$toSet == null || this.val$urlStr.equals(this.val$toSet.getTag());
    }
}
