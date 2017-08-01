package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue {
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;
    private final Cache mCache;
    private CacheDispatcher mCacheDispatcher;
    private final PriorityBlockingQueue<Request> mCacheQueue;
    private final Set<Request> mCurrentRequests;
    private final ResponseDelivery mDelivery;
    private NetworkDispatcher[] mDispatchers;
    private final Network mNetwork;
    private final PriorityBlockingQueue<Request> mNetworkQueue;
    private AtomicInteger mSequenceGenerator;
    private final Map<String, Queue<Request>> mWaitingRequests;

    public interface RequestFilter {
        boolean apply(@Signature({"(", "Lcom/android/volley/Request", "<*>;)Z"}) Request<?> request) throws ;
    }

    public RequestQueue(Cache $r1, Network $r2, int $i0, ResponseDelivery $r3) throws  {
        this.mSequenceGenerator = new AtomicInteger();
        this.mWaitingRequests = new HashMap();
        this.mCurrentRequests = new HashSet();
        this.mCacheQueue = new PriorityBlockingQueue();
        this.mNetworkQueue = new PriorityBlockingQueue();
        this.mCache = $r1;
        this.mNetwork = $r2;
        this.mDispatchers = new NetworkDispatcher[$i0];
        this.mDelivery = $r3;
    }

    public RequestQueue(Cache $r1, Network $r2, int $i0) throws  {
        this($r1, $r2, $i0, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    public RequestQueue(Cache $r1, Network $r2) throws  {
        this($r1, $r2, 4);
    }

    public void start() throws  {
        stop();
        this.mCacheDispatcher = new CacheDispatcher(this.mCacheQueue, this.mNetworkQueue, this.mCache, this.mDelivery);
        this.mCacheDispatcher.start();
        for (int $i0 = 0; $i0 < this.mDispatchers.length; $i0++) {
            NetworkDispatcher $r1 = new NetworkDispatcher(this.mNetworkQueue, this.mNetwork, this.mCache, this.mDelivery);
            this.mDispatchers[$i0] = $r1;
            $r1.start();
        }
    }

    public void stop() throws  {
        if (this.mCacheDispatcher != null) {
            this.mCacheDispatcher.quit();
        }
        for (int $i0 = 0; $i0 < this.mDispatchers.length; $i0++) {
            if (this.mDispatchers[$i0] != null) {
                this.mDispatchers[$i0].quit();
            }
        }
    }

    public int getSequenceNumber() throws  {
        return this.mSequenceGenerator.incrementAndGet();
    }

    public Cache getCache() throws  {
        return this.mCache;
    }

    public void cancelAll(RequestFilter $r1) throws  {
        synchronized (this.mCurrentRequests) {
            for (Request $r6 : this.mCurrentRequests) {
                if ($r1.apply($r6)) {
                    $r6.cancel();
                }
            }
        }
    }

    public void cancelAll(final Object $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("Cannot cancelAll with a null tag");
        }
        cancelAll(new RequestFilter() {
            public boolean apply(@Signature({"(", "Lcom/android/volley/Request", "<*>;)Z"}) Request<?> $r1) throws  {
                return $r1.getTag() == $r1;
            }
        });
    }

    public Request add(Request $r1) throws  {
        $r1.setRequestQueue(this);
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.add($r1);
        }
        $r1.setSequence(getSequenceNumber());
        $r1.addMarker("add-to-queue");
        if ($r1.shouldCache()) {
            synchronized (this.mWaitingRequests) {
                String $r7 = $r1.getCacheKey();
                if (this.mWaitingRequests.containsKey($r7)) {
                    Queue $r10 = (Queue) this.mWaitingRequests.get($r7);
                    if ($r10 == null) {
                        $r10 = r13;
                        LinkedList r13 = new LinkedList();
                    }
                    $r10.add($r1);
                    this.mWaitingRequests.put($r7, $r10);
                    if (VolleyLog.DEBUG) {
                        VolleyLog.m18v("Request for cacheKey=%s is in flight, putting on hold.", $r7);
                    }
                } else {
                    this.mWaitingRequests.put($r7, null);
                    this.mCacheQueue.add($r1);
                }
            }
            return $r1;
        }
        this.mNetworkQueue.add($r1);
        return $r1;
    }

    void finish(Request $r1) throws  {
        Set set = this.mCurrentRequests;
        this = this;
        synchronized (set) {
            this.mCurrentRequests.remove($r1);
        }
        if ($r1.shouldCache()) {
            synchronized (this.mWaitingRequests) {
                Queue $r8 = (Queue) this.mWaitingRequests.remove($r1.getCacheKey());
                if ($r8 != null) {
                    if (VolleyLog.DEBUG) {
                        VolleyLog.m18v("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf($r8.size()), $r5);
                    }
                    this.mCacheQueue.addAll($r8);
                }
            }
        }
    }
}
