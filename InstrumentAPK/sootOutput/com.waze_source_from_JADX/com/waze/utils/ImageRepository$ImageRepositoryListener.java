package com.waze.utils;

import android.graphics.Bitmap;

public abstract class ImageRepository$ImageRepositoryListener {
    public abstract void onImageRetrieved(Bitmap bitmap);

    public boolean isImageStillNeeded() {
        return true;
    }

    public void setLoadTime(long time_ms) {
    }
}
