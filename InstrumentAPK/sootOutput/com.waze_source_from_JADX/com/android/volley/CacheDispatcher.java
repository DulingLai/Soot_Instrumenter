package com.android.volley;

import dalvik.annotation.Signature;
import java.util.concurrent.BlockingQueue;

public class CacheDispatcher extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    private final Cache mCache;
    private final BlockingQueue<Request> mCacheQueue;
    private final ResponseDelivery mDelivery;
    private final BlockingQueue<Request> mNetworkQueue;
    private volatile boolean mQuit = false;

    class C04601 implements Runnable {
        final /* synthetic */ Request val$request;

        C04601(Request $r2) throws  {
            this.val$request = $r2;
        }

        public void run() throws  {
            try {
                CacheDispatcher.this.mNetworkQueue.put(this.val$request);
            } catch (InterruptedException e) {
            }
        }
    }

    public void run() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:20:0x0059 in {2, 9, 13, 19, 21, 26, 28} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r21 = this;
        r3 = DEBUG;
        if (r3 == 0) goto L_0x000d;
    L_0x0004:
        r5 = 0;
        r4 = new java.lang.Object[r5];
        r6 = "start new dispatcher";
        com.android.volley.VolleyLog.m18v(r6, r4);
    L_0x000d:
        r5 = 10;
        android.os.Process.setThreadPriority(r5);
        r0 = r21;
        r7 = r0.mCache;
        r7.initialize();
    L_0x0019:
        r0 = r21;
        r8 = r0.mCacheQueue;
        r9 = r8.take();	 Catch:{ InterruptedException -> 0x0036 }
        r11 = r9;	 Catch:{ InterruptedException -> 0x0036 }
        r11 = (com.android.volley.Request) r11;	 Catch:{ InterruptedException -> 0x0036 }
        r10 = r11;	 Catch:{ InterruptedException -> 0x0036 }
        r6 = "cache-queue-take";	 Catch:{ InterruptedException -> 0x0036 }
        r10.addMarker(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r3 = r10.isCanceled();	 Catch:{ InterruptedException -> 0x0036 }
        if (r3 == 0) goto L_0x003e;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0030:
        r6 = "cache-discard-canceled";	 Catch:{ InterruptedException -> 0x0036 }
        r10.finish(r6);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0019;
    L_0x0036:
        r12 = move-exception;
        r0 = r21;
        r3 = r0.mQuit;
        if (r3 == 0) goto L_0x0019;
    L_0x003d:
        return;
    L_0x003e:
        r0 = r21;
        r7 = r0.mCache;
        r13 = r10.getCacheKey();	 Catch:{ InterruptedException -> 0x0036 }
        r14 = r7.get(r13);	 Catch:{ InterruptedException -> 0x0036 }
        if (r14 != 0) goto L_0x005d;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x004c:
        r6 = "cache-miss";	 Catch:{ InterruptedException -> 0x0036 }
        r10.addMarker(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r8 = r0.mNetworkQueue;	 Catch:{ InterruptedException -> 0x0036 }
        r8.put(r10);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0019;
        goto L_0x005d;
    L_0x005a:
        goto L_0x0019;
    L_0x005d:
        r3 = r14.isExpired();	 Catch:{ InterruptedException -> 0x0036 }
        if (r3 == 0) goto L_0x0077;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0063:
        r6 = "cache-hit-expired";	 Catch:{ InterruptedException -> 0x0036 }
        r10.addMarker(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r10.setCacheEntry(r14);	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r8 = r0.mNetworkQueue;	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0073;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0070:
        goto L_0x0019;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0073:
        r8.put(r10);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0019;
    L_0x0077:
        r6 = "cache-hit";	 Catch:{ InterruptedException -> 0x0036 }
        r10.addMarker(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r15 = new com.android.volley.NetworkResponse;
        r0 = r14.data;
        r16 = r0;
        r0 = r14.responseHeaders;	 Catch:{ InterruptedException -> 0x0036 }
        r17 = r0;	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r16;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r17;	 Catch:{ InterruptedException -> 0x0036 }
        r15.<init>(r0, r1);	 Catch:{ InterruptedException -> 0x0036 }
        r18 = r10.parseNetworkResponse(r15);	 Catch:{ InterruptedException -> 0x0036 }
        r6 = "cache-hit-parsed";	 Catch:{ InterruptedException -> 0x0036 }
        r10.addMarker(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r3 = r14.refreshNeeded();	 Catch:{ InterruptedException -> 0x0036 }
        if (r3 != 0) goto L_0x00a8;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x009c:
        r0 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r0.mDelivery;	 Catch:{ InterruptedException -> 0x0036 }
        r19 = r0;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r18;	 Catch:{ InterruptedException -> 0x0036 }
        r0.postResponse(r10, r1);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x005a;
    L_0x00a8:
        r6 = "cache-hit-refresh-needed";	 Catch:{ InterruptedException -> 0x0036 }
        r10.addMarker(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r10.setCacheEntry(r14);	 Catch:{ InterruptedException -> 0x0036 }
        r5 = 1;
        r0 = r18;
        r0.intermediate = r5;
        r0 = r21;
        r0 = r0.mDelivery;
        r19 = r0;
        r20 = new com.android.volley.CacheDispatcher$1;	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r20;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r0.<init>(r10);	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r19;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r18;	 Catch:{ InterruptedException -> 0x0036 }
        r2 = r20;	 Catch:{ InterruptedException -> 0x0036 }
        r0.postResponse(r10, r1, r2);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0070;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.CacheDispatcher.run():void");
    }

    public CacheDispatcher(@Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) BlockingQueue<Request> $r1, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) BlockingQueue<Request> $r2, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) Cache $r3, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) ResponseDelivery $r4) throws  {
        this.mCacheQueue = $r1;
        this.mNetworkQueue = $r2;
        this.mCache = $r3;
        this.mDelivery = $r4;
    }

    public void quit() throws  {
        this.mQuit = true;
        interrupt();
    }
}
