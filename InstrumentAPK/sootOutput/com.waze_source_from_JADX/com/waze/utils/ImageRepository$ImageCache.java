package com.waze.utils;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ImageRepository$ImageCache {
    private final int _maxEntries;
    private ArrayList<String> imageAccessHistory = new ArrayList();
    private Map<String, Bitmap> imageMap = new HashMap();

    public ImageRepository$ImageCache(int maxEntries) {
        this._maxEntries = maxEntries;
    }

    private Bitmap get(String urlStr) {
        if (!this.imageMap.containsKey(urlStr)) {
            return null;
        }
        this.imageAccessHistory.remove(urlStr);
        this.imageAccessHistory.add(urlStr);
        return (Bitmap) this.imageMap.get(urlStr);
    }

    private void put(String urlStr, Bitmap d) {
        if (!urlStr.startsWith("file")) {
            if (this.imageMap.containsKey(urlStr)) {
                this.imageAccessHistory.remove(urlStr);
                this.imageAccessHistory.add(urlStr);
                this.imageMap.put(urlStr, d);
                return;
            }
            this.imageAccessHistory.add(urlStr);
            this.imageMap.put(urlStr, d);
            if (this.imageAccessHistory.size() > this._maxEntries) {
                this.imageMap.remove((String) this.imageAccessHistory.remove(0));
            }
        }
    }

    public boolean has(String urlStr) {
        return this.imageMap.get(urlStr) != null;
    }

    public void remove(String removeUrl) {
        this.imageAccessHistory.remove(removeUrl);
        this.imageMap.remove(removeUrl);
    }
}
