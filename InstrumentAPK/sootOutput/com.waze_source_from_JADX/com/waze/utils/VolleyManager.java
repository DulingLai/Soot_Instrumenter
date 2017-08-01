package com.waze.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.Volley;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import dalvik.annotation.Signature;

public class VolleyManager {
    private static final int CACHE_SIZE = 20;
    private static final String CONTENT_SCHEME = "content://";
    private static final int MAX_NUM_OF_PIXELS = 16384;
    private static final int MIN_SIDE_LENGTH = 100;
    private static VolleyManager _instance;
    private MyImageCache mImageCache;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    public static synchronized VolleyManager getInstance() throws  {
        Class cls = VolleyManager.class;
        synchronized (cls) {
            try {
                if (_instance == null) {
                    _instance = new VolleyManager();
                }
                VolleyManager $r0 = _instance;
                return $r0;
            } finally {
                cls = VolleyManager.class;
            }
        }
    }

    private VolleyManager() throws  {
    }

    public void initializeContext(Context $r1) throws  {
        if (this.mRequestQueue == null && $r1 != null) {
            this.mRequestQueue = Volley.newRequestQueue($r1);
            this.mImageCache = new MyImageCache($r1);
            this.mImageLoader = new ImageLoader(this.mRequestQueue, this.mImageCache);
        }
    }

    public void removeCache(String $r1, int $i0, int $i1) throws  {
        this.mImageCache.remove(new StringBuilder($r1.length() + 12).append("#W").append($i0).append("#H").append($i1).append($r1).toString());
        this.mRequestQueue.getCache().remove($r1);
    }

    public void forceCache(String $r1, Bitmap $r2, int $i0, int $i1) throws  {
        this.mImageCache.putBitmap(new StringBuilder($r1.length() + 12).append("#W").append($i0).append("#H").append($i1).append($r1).toString(), $r2);
    }

    public boolean hasCache(String $r1, int $i0, int $i1) throws  {
        if (this.mImageCache.has(new StringBuilder($r1.length() + 12).append("#W").append($i0).append("#H").append($i1).append($r1).toString())) {
            return true;
        }
        return this.mRequestQueue.getCache().get($r1) != null;
    }

    public void addRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;)V"}) Request<?> $r1) throws  {
        this.mRequestQueue.add($r1);
    }

    public ImageContainer loadImageFromUrl(String $r1, ImageRequestListener $r2) throws  {
        return loadImageFromUrl($r1, $r2, null, 0, 0);
    }

    public ImageContainer loadImageFromUrl(String $r1, ImageRequestListener $r2, Object $r3) throws  {
        return loadImageFromUrl($r1, $r2, $r3, 0, 0);
    }

    public ImageContainer loadImageFromUrl(String $r1, ImageRequestListener $r2, Object $r3, int $i0, int $i1) throws  {
        return loadImageFromUrl($r1, $r2, $r3, $i0, $i1, null);
    }

    public ImageContainer loadImageFromUrl(String $r5, ImageRequestListener $r1, Object $r2, int $i0, int $i1, String $r3) throws  {
        long $l2 = System.currentTimeMillis();
        if ($r5 == null || Uri.parse($r5) == null || Uri.parse($r5).getHost() == null) {
            Object[] $r8 = new Object[1];
            if ($r5 == null) {
                $r5 = "null";
            }
            $r8[0] = $r5;
            Logger.m38e(String.format("VolleyManager: loadImageFromUrl: invalid image_url %s", $r8));
            sendFailedAnalyticsEvent($r3);
            $r1.onImageLoadFailed($r2, System.currentTimeMillis() - $l2);
            return null;
        }
        return this.mImageLoader.get($r5, new 1(this, $r1, $r3, $r2, $l2, $r5), $i0, $i1);
    }

    private void sendSuccessAnalyticsEvent(String $r2, Bitmap $r1) throws  {
        if ($r2 == null) {
            $r2 = AnalyticsEvents.ANALYTICS_EVENT_LOAD_IMAGE;
        }
        Analytics.log($r2, "STATUS|WIDTH|HEIGHT", "CACHED|" + $r1.getWidth() + "|" + $r1.getHeight());
    }

    private void sendFailedAnalyticsEvent(String $r1) throws  {
        if ($r1 == null) {
            $r1 = AnalyticsEvents.ANALYTICS_EVENT_LOAD_IMAGE;
        }
        Analytics.log($r1, AnalyticsEvents.ANALYTICS_EVENT_INFO_STATUS, AnalyticsEvents.ANALYTICS_EVENT_FAILURE);
    }
}
