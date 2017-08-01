package com.waze.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;

class VolleyManager$MyImageCache implements ImageCache {
    private final Context context;
    private final LruCache<String, Bitmap> mMemCache = new LruCache(20);

    public VolleyManager$MyImageCache(Context context) {
        this.context = context;
    }

    public Bitmap getBitmap(String url) {
        if (url == null || !url.contains("content://") || this.mMemCache.get(url) != null) {
            return (Bitmap) this.mMemCache.get(url);
        }
        Bitmap result = ImageUtils.makeBitmap(100, 16384, Uri.parse(url.substring(url.indexOf("content://"), url.length())), this.context.getContentResolver(), true);
        if (result == null) {
            return result;
        }
        putBitmap(url, result);
        return result;
    }

    public void putBitmap(String url, Bitmap bitmap) {
        this.mMemCache.put(url, bitmap);
    }

    public void remove(String cacheKey) {
        this.mMemCache.remove(cacheKey);
    }

    public boolean has(String cacheKey) {
        return this.mMemCache.get(cacheKey) != null;
    }
}
