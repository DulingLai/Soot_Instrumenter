package com.waze.utils;

import android.graphics.Bitmap;

public interface VolleyManager$ImageRequestListener {
    void onImageLoadComplete(Bitmap bitmap, Object obj, long j);

    void onImageLoadFailed(Object obj, long j);
}
