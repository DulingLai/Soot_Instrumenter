package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache.Limits;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

class ImageResponseCache {
    static final String TAG = ImageResponseCache.class.getSimpleName();
    private static volatile FileLruCache imageCache;

    private static class BufferedHttpInputStream extends BufferedInputStream {
        HttpURLConnection connection;

        BufferedHttpInputStream(InputStream $r1, HttpURLConnection $r2) throws  {
            super($r1, 8192);
            this.connection = $r2;
        }

        public void close() throws IOException {
            super.close();
            Utility.disconnectQuietly(this.connection);
        }
    }

    ImageResponseCache() throws  {
    }

    static synchronized FileLruCache getCache(Context context) throws IOException {
        Class cls = ImageResponseCache.class;
        synchronized (cls) {
            try {
                if (imageCache == null) {
                    imageCache = new FileLruCache(TAG, new Limits());
                }
                FileLruCache $r3 = imageCache;
                return $r3;
            } finally {
                cls = ImageResponseCache.class;
            }
        }
    }

    static InputStream getCachedImageStream(Uri $r0, Context $r1) throws  {
        if ($r0 == null) {
            return null;
        }
        if (!isCDNURL($r0)) {
            return null;
        }
        try {
            return getCache($r1).get($r0.toString());
        } catch (IOException $r2) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, $r2.toString());
            return null;
        }
    }

    static InputStream interceptAndCacheImageStream(Context $r0, HttpURLConnection $r1) throws IOException {
        if ($r1.getResponseCode() != 200) {
            return null;
        }
        Uri $r4 = Uri.parse($r1.getURL().toString());
        InputStream $r5 = $r1.getInputStream();
        try {
            if (isCDNURL($r4)) {
                return getCache($r0).interceptAndPut($r4.toString(), new BufferedHttpInputStream($r5, $r1));
            }
            return $r5;
        } catch (IOException e) {
            return $r5;
        }
    }

    private static boolean isCDNURL(Uri $r0) throws  {
        if ($r0 != null) {
            String $r1 = $r0.getHost();
            if ($r1.endsWith("fbcdn.net")) {
                return true;
            }
            if ($r1.startsWith("fbcdn") && $r1.endsWith("akamaihd.net")) {
                return true;
            }
        }
        return false;
    }

    static void clearCache(Context $r0) throws  {
        try {
            getCache($r0).clearCache();
        } catch (IOException $r1) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + $r1.getMessage());
        }
    }
}
