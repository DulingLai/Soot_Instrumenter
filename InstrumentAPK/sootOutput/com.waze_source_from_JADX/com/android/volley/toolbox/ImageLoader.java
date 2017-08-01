package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ImageLoader {
    private int mBatchResponseDelayMs = 100;
    private final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap();
    private final ImageCache mCache;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap();
    private final RequestQueue mRequestQueue;
    private Runnable mRunnable;

    public interface ImageListener extends ErrorListener {
        void onResponse(ImageContainer imageContainer, boolean z) throws ;
    }

    class C04694 implements Runnable {
        C04694() throws  {
        }

        public void run() throws  {
            this = this;
            for (BatchedImageRequest $r6 : ImageLoader.this.mBatchedResponses.values()) {
                Iterator $r8 = $r6.mContainers.iterator();
                while ($r8.hasNext()) {
                    ImageContainer $r9 = (ImageContainer) $r8.next();
                    if ($r9.mListener != null) {
                        if ($r6.getError() == null) {
                            $r9.mBitmap = $r6.mResponseBitmap;
                            $r9.mListener.onResponse($r9, null);
                        } else {
                            $r9.mListener.onErrorResponse($r6.getError());
                        }
                    }
                }
            }
            ImageLoader.this.mBatchedResponses.clear();
            ImageLoader.this.mRunnable = null;
        }
    }

    private class BatchedImageRequest {
        private final LinkedList<ImageContainer> mContainers = new LinkedList();
        private VolleyError mError;
        private final Request<?> mRequest;
        private Bitmap mResponseBitmap;

        public BatchedImageRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/toolbox/ImageLoader$ImageContainer;", ")V"}) Request<?> $r2, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/toolbox/ImageLoader$ImageContainer;", ")V"}) ImageContainer $r3) throws  {
            this.mRequest = $r2;
            this.mContainers.add($r3);
        }

        public void setError(VolleyError $r1) throws  {
            this.mError = $r1;
        }

        public VolleyError getError() throws  {
            return this.mError;
        }

        public void addContainer(ImageContainer $r1) throws  {
            this.mContainers.add($r1);
        }

        public boolean removeContainerAndCancelIfNecessary(ImageContainer $r1) throws  {
            this.mContainers.remove($r1);
            if (this.mContainers.size() != 0) {
                return false;
            }
            this.mRequest.cancel();
            return true;
        }
    }

    public interface ImageCache {
        Bitmap getBitmap(String str) throws ;

        void putBitmap(String str, Bitmap bitmap) throws ;
    }

    public class ImageContainer {
        private Bitmap mBitmap;
        private final String mCacheKey;
        private final ImageListener mListener;
        private final String mRequestUrl;

        public ImageContainer(Bitmap $r2, String $r3, String $r4, ImageListener $r5) throws  {
            this.mBitmap = $r2;
            this.mRequestUrl = $r3;
            this.mCacheKey = $r4;
            this.mListener = $r5;
        }

        public void cancelRequest() throws  {
            if (this.mListener != null) {
                BatchedImageRequest $r6 = (BatchedImageRequest) ImageLoader.this.mInFlightRequests.get(this.mCacheKey);
                if ($r6 == null) {
                    $r6 = (BatchedImageRequest) ImageLoader.this.mBatchedResponses.get(this.mCacheKey);
                    if ($r6 != null) {
                        $r6.removeContainerAndCancelIfNecessary(this);
                        if ($r6.mContainers.size() == 0) {
                            ImageLoader.this.mBatchedResponses.remove(this.mCacheKey);
                        }
                    }
                } else if ($r6.removeContainerAndCancelIfNecessary(this)) {
                    ImageLoader.this.mInFlightRequests.remove(this.mCacheKey);
                }
            }
        }

        public Bitmap getBitmap() throws  {
            return this.mBitmap;
        }

        public String getRequestUrl() throws  {
            return this.mRequestUrl;
        }
    }

    public ImageLoader(RequestQueue $r1, ImageCache $r2) throws  {
        this.mRequestQueue = $r1;
        this.mCache = $r2;
    }

    public static ImageListener getImageListener(final ImageView $r0, final int $i0, final int $i1) throws  {
        return new ImageListener() {
            public void onErrorResponse(VolleyError error) throws  {
                if ($i1 != 0) {
                    $r0.setImageResource($i1);
                }
            }

            public void onResponse(ImageContainer $r1, boolean isImmediate) throws  {
                if ($r1.getBitmap() != null) {
                    $r0.setImageBitmap($r1.getBitmap());
                } else if ($i0 != 0) {
                    $r0.setImageResource($i0);
                }
            }
        };
    }

    public boolean isCached(String $r1, int $i0, int $i1) throws  {
        throwIfNotOnMainThread();
        return this.mCache.getBitmap(getCacheKey($r1, $i0, $i1)) != null;
    }

    public ImageContainer get(String $r1, ImageListener $r2) throws  {
        return get($r1, $r2, 0, 0);
    }

    public ImageContainer get(String $r1, ImageListener $r2, int $i0, int $i1) throws  {
        throwIfNotOnMainThread();
        final String $r7 = getCacheKey($r1, $i0, $i1);
        Bitmap $r9 = this.mCache.getBitmap($r7);
        if ($r9 != null) {
            ImageContainer imageContainer = new ImageContainer($r9, $r1, null, null);
            $r2.onResponse(imageContainer, true);
            return imageContainer;
        }
        imageContainer = new ImageContainer(null, $r1, $r7, $r2);
        $r2.onResponse(imageContainer, true);
        BatchedImageRequest batchedImageRequest = (BatchedImageRequest) this.mInFlightRequests.get($r7);
        if (batchedImageRequest != null) {
            batchedImageRequest.addContainer(imageContainer);
            return imageContainer;
        }
        Request imageRequest = new ImageRequest($r1, new Listener<Bitmap>() {
            public void onResponse(Bitmap $r1) throws  {
                ImageLoader.this.onGetImageSuccess($r7, $r1);
            }
        }, $i0, $i1, Config.RGB_565, new ErrorListener() {
            public void onErrorResponse(VolleyError $r1) throws  {
                ImageLoader.this.onGetImageError($r7, $r1);
            }
        });
        RequestQueue requestQueue = this.mRequestQueue;
        RequestQueue $r14 = requestQueue;
        requestQueue.add(imageRequest);
        this.mInFlightRequests.put($r7, new BatchedImageRequest(imageRequest, imageContainer));
        return imageContainer;
    }

    public void setBatchedResponseDelay(int $i0) throws  {
        this.mBatchResponseDelayMs = $i0;
    }

    private void onGetImageSuccess(String $r1, Bitmap $r2) throws  {
        this.mCache.putBitmap($r1, $r2);
        BatchedImageRequest $r6 = (BatchedImageRequest) this.mInFlightRequests.remove($r1);
        if ($r6 != null) {
            $r6.mResponseBitmap = $r2;
            batchResponse($r1, $r6);
        }
    }

    private void onGetImageError(String $r1, VolleyError $r2) throws  {
        BatchedImageRequest $r5 = (BatchedImageRequest) this.mInFlightRequests.remove($r1);
        $r5.setError($r2);
        if ($r5 != null) {
            batchResponse($r1, $r5);
        }
    }

    private void batchResponse(String $r1, BatchedImageRequest $r2) throws  {
        this.mBatchedResponses.put($r1, $r2);
        if (this.mRunnable == null) {
            this.mRunnable = new C04694();
            this.mHandler.postDelayed(this.mRunnable, (long) this.mBatchResponseDelayMs);
        }
    }

    private void throwIfNotOnMainThread() throws  {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("ImageLoader must be invoked from the main thread.");
        }
    }

    private static String getCacheKey(String $r0, int $i0, int $i1) throws  {
        return new StringBuilder($r0.length() + 12).append("#W").append($i0).append("#H").append($i1).append($r0).toString();
    }
}
