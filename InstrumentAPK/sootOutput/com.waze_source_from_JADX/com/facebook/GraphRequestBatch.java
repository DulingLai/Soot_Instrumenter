package com.facebook;

import android.os.Handler;
import dalvik.annotation.Signature;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphRequestBatch extends AbstractList<GraphRequest> {
    private static AtomicInteger idGenerator = new AtomicInteger();
    private String batchApplicationId;
    private Handler callbackHandler;
    private List<Callback> callbacks;
    private final String id;
    private List<GraphRequest> requests;
    private int timeoutInMilliseconds;

    public interface Callback {
        void onBatchCompleted(GraphRequestBatch graphRequestBatch) throws ;
    }

    public interface OnProgressCallback extends Callback {
        void onBatchProgress(GraphRequestBatch graphRequestBatch, long j, long j2) throws ;
    }

    public GraphRequestBatch() throws  {
        this.requests = new ArrayList();
        this.timeoutInMilliseconds = 0;
        this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
        this.callbacks = new ArrayList();
        this.requests = new ArrayList();
    }

    public GraphRequestBatch(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)V"}) Collection<GraphRequest> $r1) throws  {
        this.requests = new ArrayList();
        this.timeoutInMilliseconds = 0;
        this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
        this.callbacks = new ArrayList();
        this.requests = new ArrayList($r1);
    }

    public GraphRequestBatch(GraphRequest... $r1) throws  {
        this.requests = new ArrayList();
        this.timeoutInMilliseconds = 0;
        this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
        this.callbacks = new ArrayList();
        this.requests = Arrays.asList($r1);
    }

    public GraphRequestBatch(GraphRequestBatch $r1) throws  {
        this.requests = new ArrayList();
        this.timeoutInMilliseconds = 0;
        this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
        this.callbacks = new ArrayList();
        this.requests = new ArrayList($r1);
        this.callbackHandler = $r1.callbackHandler;
        this.timeoutInMilliseconds = $r1.timeoutInMilliseconds;
        this.callbacks = new ArrayList($r1.callbacks);
    }

    public int getTimeout() throws  {
        return this.timeoutInMilliseconds;
    }

    public void setTimeout(int $i0) throws  {
        if ($i0 < 0) {
            throw new IllegalArgumentException("Argument timeoutInMilliseconds must be >= 0.");
        }
        this.timeoutInMilliseconds = $i0;
    }

    public void addCallback(Callback $r1) throws  {
        if (!this.callbacks.contains($r1)) {
            this.callbacks.add($r1);
        }
    }

    public void removeCallback(Callback $r1) throws  {
        this.callbacks.remove($r1);
    }

    public final boolean add(GraphRequest $r1) throws  {
        return this.requests.add($r1);
    }

    public final void add(int $i0, GraphRequest $r1) throws  {
        this.requests.add($i0, $r1);
    }

    public final void clear() throws  {
        this.requests.clear();
    }

    public final GraphRequest get(int $i0) throws  {
        return (GraphRequest) this.requests.get($i0);
    }

    public final GraphRequest remove(int $i0) throws  {
        return (GraphRequest) this.requests.remove($i0);
    }

    public final GraphRequest set(int $i0, GraphRequest $r1) throws  {
        return (GraphRequest) this.requests.set($i0, $r1);
    }

    public final int size() throws  {
        return this.requests.size();
    }

    final String getId() throws  {
        return this.id;
    }

    final Handler getCallbackHandler() throws  {
        return this.callbackHandler;
    }

    final void setCallbackHandler(Handler $r1) throws  {
        this.callbackHandler = $r1;
    }

    final List<GraphRequest> getRequests() throws  {
        return this.requests;
    }

    final List<Callback> getCallbacks() throws  {
        return this.callbacks;
    }

    public final String getBatchApplicationId() throws  {
        return this.batchApplicationId;
    }

    public final void setBatchApplicationId(String $r1) throws  {
        this.batchApplicationId = $r1;
    }

    public final List<GraphResponse> executeAndWait() throws  {
        return executeAndWaitImpl();
    }

    public final GraphRequestAsyncTask executeAsync() throws  {
        return executeAsyncImpl();
    }

    List<GraphResponse> executeAndWaitImpl() throws  {
        return GraphRequest.executeBatchAndWait(this);
    }

    GraphRequestAsyncTask executeAsyncImpl() throws  {
        return GraphRequest.executeBatchAsync(this);
    }
}
