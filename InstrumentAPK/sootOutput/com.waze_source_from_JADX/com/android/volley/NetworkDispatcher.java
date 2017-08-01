package com.android.volley;

import dalvik.annotation.Signature;
import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher extends Thread {
    private final Cache mCache;
    private final ResponseDelivery mDelivery;
    private final Network mNetwork;
    private final BlockingQueue<Request> mQueue;
    private volatile boolean mQuit = false;

    public void run() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0084 in list []
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
        r22 = this;
        r3 = 10;
        android.os.Process.setThreadPriority(r3);
    L_0x0005:
        r0 = r22;
        r4 = r0.mQueue;
        r5 = r4.take();	 Catch:{ InterruptedException -> 0x0029 }
        r7 = r5;
        r7 = (com.android.volley.Request) r7;
        r6 = r7;
        r8 = "network-queue-take";	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r6.addMarker(r8);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r9 = r6.isCanceled();	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        if (r9 == 0) goto L_0x0031;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
    L_0x001c:
        r8 = "network-discard-cancelled";	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r6.finish(r8);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        goto L_0x0005;
    L_0x0022:
        r10 = move-exception;
        r0 = r22;
        r0.parseAndDeliverNetworkError(r6, r10);
        goto L_0x0005;
    L_0x0029:
        r11 = move-exception;
        r0 = r22;
        r9 = r0.mQuit;
        if (r9 == 0) goto L_0x0005;
    L_0x0030:
        return;
    L_0x0031:
        r12 = android.os.Build.VERSION.SDK_INT;
        r3 = 14;
        if (r12 < r3) goto L_0x003e;
    L_0x0037:
        r12 = r6.getTrafficStatsTag();	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        android.net.TrafficStats.setThreadStatsTag(r12);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
    L_0x003e:
        r0 = r22;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r13 = r0.mNetwork;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r14 = r13.performRequest(r6);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r8 = "network-http-complete";	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r6.addMarker(r8);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r9 = r14.notModified;
        if (r9 == 0) goto L_0x0084;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
    L_0x004f:
        r9 = r6.hasHadResponseDelivered();	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        if (r9 == 0) goto L_0x0084;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
    L_0x0055:
        r8 = "not-modified";	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r6.finish(r8);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        goto L_0x0005;
    L_0x005b:
        r15 = move-exception;
        goto L_0x0060;
    L_0x005d:
        goto L_0x0005;
    L_0x0060:
        r3 = 1;
        r0 = new java.lang.Object[r3];
        r16 = r0;
        r17 = r15.toString();
        r3 = 0;
        r16[r3] = r17;
        r8 = "Unhandled exception %s";
        r0 = r16;
        com.android.volley.VolleyLog.m17e(r15, r8, r0);
        r0 = r22;
        r0 = r0.mDelivery;
        r18 = r0;
        r10 = new com.android.volley.VolleyError;
        r10.<init>(r15);
        r0 = r18;
        r0.postError(r6, r10);
        goto L_0x0005;
    L_0x0084:
        r19 = r6.parseNetworkResponse(r14);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r8 = "network-parse-complete";	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r6.addMarker(r8);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r9 = r6.shouldCache();	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        if (r9 == 0) goto L_0x00b9;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
    L_0x0093:
        r0 = r19;
        r0 = r0.cacheEntry;
        r20 = r0;
        if (r20 == 0) goto L_0x00b9;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
    L_0x009b:
        r0 = r22;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0 = r0.mCache;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r21 = r0;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r17 = r6.getCacheKey();	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0 = r19;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0 = r0.cacheEntry;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r20 = r0;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0 = r21;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r1 = r17;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r2 = r20;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0.put(r1, r2);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r8 = "network-cache-written";	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r6.addMarker(r8);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
    L_0x00b9:
        r6.markDelivered();	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0 = r22;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0 = r0.mDelivery;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r18 = r0;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r1 = r19;	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        r0.postResponse(r6, r1);	 Catch:{ VolleyError -> 0x0022, Exception -> 0x005b }
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.NetworkDispatcher.run():void");
    }

    public NetworkDispatcher(@Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Network;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) BlockingQueue<Request> $r1, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Network;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) Network $r2, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Network;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) Cache $r3, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/android/volley/Request;", ">;", "Lcom/android/volley/Network;", "Lcom/android/volley/Cache;", "Lcom/android/volley/ResponseDelivery;", ")V"}) ResponseDelivery $r4) throws  {
        this.mQueue = $r1;
        this.mNetwork = $r2;
        this.mCache = $r3;
        this.mDelivery = $r4;
    }

    public void quit() throws  {
        this.mQuit = true;
        interrupt();
    }

    private void parseAndDeliverNetworkError(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) Request<?> $r1, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) VolleyError $r3) throws  {
        this.mDelivery.postError($r1, $r1.parseNetworkError($r3));
    }
}
