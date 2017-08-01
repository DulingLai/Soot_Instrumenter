package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.WorkQueue.WorkItem;
import com.waze.strings.DisplayStrings;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
    private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static WorkQueue cacheReadQueue = new WorkQueue(2);
    private static WorkQueue downloadQueue = new WorkQueue(8);
    private static Handler handler;
    private static final Map<RequestKey, DownloaderContext> pendingRequests = new HashMap();

    private static class CacheReadWorkItem implements Runnable {
        private boolean allowCachedRedirects;
        private Context context;
        private RequestKey key;

        CacheReadWorkItem(Context $r1, RequestKey $r2, boolean $z0) throws  {
            this.context = $r1;
            this.key = $r2;
            this.allowCachedRedirects = $z0;
        }

        public void run() throws  {
            ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
        }
    }

    private static class DownloadImageWorkItem implements Runnable {
        private Context context;
        private RequestKey key;

        DownloadImageWorkItem(Context $r1, RequestKey $r2) throws  {
            this.context = $r1;
            this.key = $r2;
        }

        public void run() throws  {
            ImageDownloader.download(this.key, this.context);
        }
    }

    private static class DownloaderContext {
        boolean isCancelled;
        ImageRequest request;
        WorkItem workItem;

        private DownloaderContext() throws  {
        }
    }

    private static class RequestKey {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        Object tag;
        Uri uri;

        RequestKey(Uri $r1, Object $r2) throws  {
            this.uri = $r1;
            this.tag = $r2;
        }

        public int hashCode() throws  {
            return ((this.uri.hashCode() + DisplayStrings.DS_FACEBOOK_PRIVACY_BODY3) * 37) + this.tag.hashCode();
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == null) {
                return false;
            }
            if (!($r1 instanceof RequestKey)) {
                return false;
            }
            RequestKey $r2 = (RequestKey) $r1;
            return $r2.uri == this.uri && $r2.tag == this.tag;
        }
    }

    private static void download(com.facebook.internal.ImageDownloader.RequestKey r33, android.content.Context r34) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00e9 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 1;
        r7 = new java.net.URL;	 Catch:{ Throwable -> 0x00f7 }
        r8 = r7;	 Catch:{ Throwable -> 0x00f7 }
        r0 = r33;	 Catch:{ Throwable -> 0x00f7 }
        r9 = r0.uri;	 Catch:{ Throwable -> 0x00f7 }
        r10 = r9.toString();	 Catch:{ IOException -> 0x0061 }
        r7.<init>(r10);	 Catch:{ IOException -> 0x0061 }
        r11 = r8.openConnection();	 Catch:{ IOException -> 0x0061 }
        r13 = r11;	 Catch:{ Throwable -> 0x00f7 }
        r13 = (java.net.HttpURLConnection) r13;	 Catch:{ Throwable -> 0x00f7 }
        r12 = r13;	 Catch:{ Throwable -> 0x00f7 }
        r2 = r12;	 Catch:{ IOException -> 0x0061 }
        r14 = 0;	 Catch:{ IOException -> 0x0061 }
        r12.setInstanceFollowRedirects(r14);	 Catch:{ IOException -> 0x0061 }
        r15 = r12.getResponseCode();	 Catch:{ IOException -> 0x0061 }
        switch(r15) {
            case 200: goto L_0x00c5;
            case 301: goto L_0x0073;
            case 302: goto L_0x0073;
            default: goto L_0x0027;
        };	 Catch:{ IOException -> 0x0061 }
    L_0x0027:
        goto L_0x0028;	 Catch:{ IOException -> 0x0061 }
    L_0x0028:
        r16 = r12.getErrorStream();	 Catch:{ IOException -> 0x0061 }
        r3 = r16;	 Catch:{ Throwable -> 0x00f7 }
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00f7 }
        r18 = r17;	 Catch:{ IOException -> 0x0061 }
        r0 = r17;	 Catch:{ IOException -> 0x0061 }
        r0.<init>();	 Catch:{ IOException -> 0x0061 }
        if (r16 == 0) goto L_0x00e9;	 Catch:{ Throwable -> 0x00f7 }
    L_0x0039:
        r19 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x00f7 }
        r20 = r19;	 Catch:{ IOException -> 0x0061 }
        r0 = r19;	 Catch:{ IOException -> 0x0061 }
        r1 = r16;	 Catch:{ IOException -> 0x0061 }
        r0.<init>(r1);	 Catch:{ IOException -> 0x0061 }
        r14 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r0 = new char[r14];
        r21 = r0;
    L_0x004a:
        r0 = r21;	 Catch:{ IOException -> 0x0061 }
        r15 = r0.length;	 Catch:{ IOException -> 0x0061 }
        r14 = 0;	 Catch:{ IOException -> 0x0061 }
        r0 = r20;	 Catch:{ IOException -> 0x0061 }
        r1 = r21;	 Catch:{ IOException -> 0x0061 }
        r15 = r0.read(r1, r14, r15);	 Catch:{ IOException -> 0x0061 }
        if (r15 <= 0) goto L_0x00d4;	 Catch:{ IOException -> 0x0061 }
    L_0x0058:
        r14 = 0;	 Catch:{ IOException -> 0x0061 }
        r0 = r18;	 Catch:{ IOException -> 0x0061 }
        r1 = r21;	 Catch:{ IOException -> 0x0061 }
        r0.append(r1, r14, r15);	 Catch:{ IOException -> 0x0061 }
        goto L_0x004a;
    L_0x0061:
        r22 = move-exception;
        r4 = r22;
        com.facebook.internal.Utility.closeQuietly(r3);
        com.facebook.internal.Utility.disconnectQuietly(r2);
    L_0x006a:
        if (r6 == 0) goto L_0x00ff;
    L_0x006c:
        r14 = 0;
        r0 = r33;
        issueResponse(r0, r4, r5, r14);
        return;
    L_0x0073:
        r6 = 0;
        r23 = "location";	 Catch:{ IOException -> 0x0061 }
        r0 = r23;	 Catch:{ IOException -> 0x0061 }
        r10 = r12.getHeaderField(r0);	 Catch:{ IOException -> 0x0061 }
        r24 = com.facebook.internal.Utility.isNullOrEmpty(r10);	 Catch:{ IOException -> 0x0061 }
        if (r24 != 0) goto L_0x00be;	 Catch:{ IOException -> 0x0061 }
    L_0x0082:
        r9 = android.net.Uri.parse(r10);	 Catch:{ IOException -> 0x0061 }
        r0 = r33;	 Catch:{ IOException -> 0x0061 }
        r0 = r0.uri;	 Catch:{ IOException -> 0x0061 }
        r25 = r0;	 Catch:{ IOException -> 0x0061 }
        com.facebook.internal.UrlRedirectCache.cacheUriRedirect(r0, r9);	 Catch:{ IOException -> 0x0061 }
        r0 = r33;	 Catch:{ IOException -> 0x0061 }
        r26 = removePendingRequest(r0);	 Catch:{ IOException -> 0x0061 }
        if (r26 == 0) goto L_0x00be;
    L_0x0097:
        r0 = r26;
        r0 = r0.isCancelled;
        r24 = r0;
        if (r24 != 0) goto L_0x00be;
    L_0x009f:
        r0 = r26;
        r0 = r0.request;
        r27 = r0;
        r28 = new com.facebook.internal.ImageDownloader$RequestKey;
        r29 = r28;
        r0 = r33;	 Catch:{ IOException -> 0x0061 }
        r0 = r0.tag;	 Catch:{ IOException -> 0x0061 }
        r30 = r0;	 Catch:{ IOException -> 0x0061 }
        r0 = r28;	 Catch:{ IOException -> 0x0061 }
        r1 = r30;	 Catch:{ IOException -> 0x0061 }
        r0.<init>(r9, r1);	 Catch:{ IOException -> 0x0061 }
        r14 = 0;	 Catch:{ IOException -> 0x0061 }
        r0 = r27;	 Catch:{ IOException -> 0x0061 }
        r1 = r29;	 Catch:{ IOException -> 0x0061 }
        enqueueCacheRead(r0, r1, r14);	 Catch:{ IOException -> 0x0061 }
    L_0x00be:
        com.facebook.internal.Utility.closeQuietly(r3);
        com.facebook.internal.Utility.disconnectQuietly(r12);
        goto L_0x006a;
    L_0x00c5:
        r0 = r34;	 Catch:{ IOException -> 0x0061 }
        r16 = com.facebook.internal.ImageResponseCache.interceptAndCacheImageStream(r0, r12);	 Catch:{ IOException -> 0x0061 }
        r3 = r16;	 Catch:{ IOException -> 0x0061 }
        r0 = r16;	 Catch:{ IOException -> 0x0061 }
        r5 = android.graphics.BitmapFactory.decodeStream(r0);	 Catch:{ IOException -> 0x0061 }
        goto L_0x00be;
    L_0x00d4:
        r0 = r20;	 Catch:{ IOException -> 0x0061 }
        com.facebook.internal.Utility.closeQuietly(r0);	 Catch:{ IOException -> 0x0061 }
    L_0x00d9:
        r31 = new com.facebook.FacebookException;
        r4 = r31;	 Catch:{ IOException -> 0x0061 }
        r0 = r18;	 Catch:{ IOException -> 0x0061 }
        r10 = r0.toString();	 Catch:{ IOException -> 0x0061 }
        r0 = r31;	 Catch:{ IOException -> 0x0061 }
        r0.<init>(r10);	 Catch:{ IOException -> 0x0061 }
        goto L_0x00be;
    L_0x00e9:
        r15 = com.facebook.C0496R.string.com_facebook_image_download_unknown_error;	 Catch:{ IOException -> 0x0061 }
        r0 = r34;	 Catch:{ IOException -> 0x0061 }
        r10 = r0.getString(r15);	 Catch:{ IOException -> 0x0061 }
        r0 = r18;	 Catch:{ IOException -> 0x0061 }
        r0.append(r10);	 Catch:{ IOException -> 0x0061 }
        goto L_0x00d9;
    L_0x00f7:
        r32 = move-exception;
        com.facebook.internal.Utility.closeQuietly(r3);
        com.facebook.internal.Utility.disconnectQuietly(r2);
        throw r32;
    L_0x00ff:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.download(com.facebook.internal.ImageDownloader$RequestKey, android.content.Context):void");
    }

    public static void downloadAsync(ImageRequest $r0) throws  {
        if ($r0 != null) {
            RequestKey $r1 = new RequestKey($r0.getImageUri(), $r0.getCallerTag());
            synchronized (pendingRequests) {
                DownloaderContext $r6 = (DownloaderContext) pendingRequests.get($r1);
                if ($r6 != null) {
                    $r6.request = $r0;
                    $r6.isCancelled = false;
                    $r6.workItem.moveToFront();
                } else {
                    enqueueCacheRead($r0, $r1, $r0.isCachedRedirectAllowed());
                }
            }
        }
    }

    public static boolean cancelRequest(ImageRequest $r0) throws  {
        boolean $z0 = false;
        RequestKey $r1 = new RequestKey($r0.getImageUri(), $r0.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext $r6 = (DownloaderContext) pendingRequests.get($r1);
            if ($r6 != null) {
                $z0 = true;
                if ($r6.workItem.cancel()) {
                    pendingRequests.remove($r1);
                } else {
                    $r6.isCancelled = true;
                }
            }
        }
        return $z0;
    }

    public static void prioritizeRequest(ImageRequest $r0) throws  {
        RequestKey $r1 = new RequestKey($r0.getImageUri(), $r0.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext $r6 = (DownloaderContext) pendingRequests.get($r1);
            if ($r6 != null) {
                $r6.workItem.moveToFront();
            }
        }
    }

    public static void clearCache(Context $r0) throws  {
        ImageResponseCache.clearCache($r0);
        UrlRedirectCache.clearCache();
    }

    private static void enqueueCacheRead(ImageRequest $r0, RequestKey $r1, boolean $z0) throws  {
        enqueueRequest($r0, $r1, cacheReadQueue, new CacheReadWorkItem($r0.getContext(), $r1, $z0));
    }

    private static void enqueueDownload(ImageRequest $r0, RequestKey $r1) throws  {
        enqueueRequest($r0, $r1, downloadQueue, new DownloadImageWorkItem($r0.getContext(), $r1));
    }

    private static void enqueueRequest(ImageRequest $r0, RequestKey $r1, WorkQueue $r2, Runnable $r3) throws  {
        synchronized (pendingRequests) {
            DownloaderContext $r4 = new DownloaderContext();
            $r4.request = $r0;
            pendingRequests.put($r1, $r4);
            $r4.workItem = $r2.addActiveWorkItem($r3);
        }
    }

    private static void issueResponse(RequestKey $r0, Exception $r1, Bitmap $r2, boolean $z0) throws  {
        DownloaderContext $r4 = removePendingRequest($r0);
        if ($r4 != null && !$r4.isCancelled) {
            ImageRequest $r3 = $r4.request;
            Callback $r5 = $r3.getCallback();
            if ($r5 != null) {
                final ImageRequest imageRequest = $r3;
                final Exception exception = $r1;
                final boolean z = $z0;
                final Bitmap bitmap = $r2;
                final Callback callback = $r5;
                getHandler().post(new Runnable() {
                    public void run() throws  {
                        callback.onCompleted(new ImageResponse(imageRequest, exception, z, bitmap));
                    }
                });
            }
        }
    }

    private static void readFromCache(RequestKey $r0, Context $r1, boolean $z0) throws  {
        InputStream $r2 = null;
        boolean $z1 = false;
        if ($z0) {
            Uri $r3 = UrlRedirectCache.getRedirectedUri($r0.uri);
            if ($r3 != null) {
                InputStream $r4 = ImageResponseCache.getCachedImageStream($r3, $r1);
                $r2 = $r4;
                $z1 = $r4 != null;
            }
        }
        if (!$z1) {
            $r2 = ImageResponseCache.getCachedImageStream($r0.uri, $r1);
        }
        if ($r2 != null) {
            Bitmap $r5 = BitmapFactory.decodeStream($r2);
            Utility.closeQuietly($r2);
            issueResponse($r0, null, $r5, $z1);
            return;
        }
        DownloaderContext $r6 = removePendingRequest($r0);
        if ($r6 != null && !$r6.isCancelled) {
            enqueueDownload($r6.request, $r0);
        }
    }

    private static synchronized Handler getHandler() throws  {
        Class cls = ImageDownloader.class;
        synchronized (cls) {
            try {
                if (handler == null) {
                    handler = new Handler(Looper.getMainLooper());
                }
                Handler $r1 = handler;
                return $r1;
            } finally {
                cls = ImageDownloader.class;
            }
        }
    }

    private static DownloaderContext removePendingRequest(RequestKey $r0) throws  {
        DownloaderContext $r4;
        synchronized (pendingRequests) {
            $r4 = (DownloaderContext) pendingRequests.remove($r0);
        }
        return $r4;
    }
}
