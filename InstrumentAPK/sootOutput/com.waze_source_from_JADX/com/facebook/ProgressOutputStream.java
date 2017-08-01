package com.facebook;

import android.os.Handler;
import com.facebook.GraphRequestBatch.Callback;
import com.facebook.GraphRequestBatch.OnProgressCallback;
import dalvik.annotation.Signature;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

class ProgressOutputStream extends FilterOutputStream implements RequestOutputStream {
    private long batchProgress;
    private RequestProgress currentRequestProgress;
    private long lastReportedProgress;
    private long maxProgress;
    private final Map<GraphRequest, RequestProgress> progressMap;
    private final GraphRequestBatch requests;
    private final long threshold = FacebookSdk.getOnProgressThreshold();

    ProgressOutputStream(@Signature({"(", "Ljava/io/OutputStream;", "Lcom/facebook/GraphRequestBatch;", "Ljava/util/Map", "<", "Lcom/facebook/GraphRequest;", "Lcom/facebook/RequestProgress;", ">;J)V"}) OutputStream $r1, @Signature({"(", "Ljava/io/OutputStream;", "Lcom/facebook/GraphRequestBatch;", "Ljava/util/Map", "<", "Lcom/facebook/GraphRequest;", "Lcom/facebook/RequestProgress;", ">;J)V"}) GraphRequestBatch $r2, @Signature({"(", "Ljava/io/OutputStream;", "Lcom/facebook/GraphRequestBatch;", "Ljava/util/Map", "<", "Lcom/facebook/GraphRequest;", "Lcom/facebook/RequestProgress;", ">;J)V"}) Map<GraphRequest, RequestProgress> $r3, @Signature({"(", "Ljava/io/OutputStream;", "Lcom/facebook/GraphRequestBatch;", "Ljava/util/Map", "<", "Lcom/facebook/GraphRequest;", "Lcom/facebook/RequestProgress;", ">;J)V"}) long $l0) throws  {
        super($r1);
        this.requests = $r2;
        this.progressMap = $r3;
        this.maxProgress = $l0;
    }

    private void addProgress(long $l0) throws  {
        if (this.currentRequestProgress != null) {
            this.currentRequestProgress.addProgress($l0);
        }
        this.batchProgress += $l0;
        if (this.batchProgress >= this.lastReportedProgress + this.threshold || this.batchProgress >= this.maxProgress) {
            reportBatchProgress();
        }
    }

    private void reportBatchProgress() throws  {
        if (this.batchProgress > this.lastReportedProgress) {
            for (Callback callback : this.requests.getCallbacks()) {
                if (callback instanceof OnProgressCallback) {
                    Handler $r6 = this.requests.getCallbackHandler();
                    OnProgressCallback onProgressCallback = (OnProgressCallback) callback;
                    if ($r6 == null) {
                        onProgressCallback.onBatchProgress(this.requests, this.batchProgress, this.maxProgress);
                    } else {
                        final OnProgressCallback onProgressCallback2 = onProgressCallback;
                        $r6.post(new Runnable() {
                            public void run() throws  {
                                onProgressCallback2.onBatchProgress(ProgressOutputStream.this.requests, ProgressOutputStream.this.batchProgress, ProgressOutputStream.this.maxProgress);
                            }
                        });
                    }
                }
            }
            this.lastReportedProgress = this.batchProgress;
        }
    }

    public void setCurrentRequest(GraphRequest $r1) throws  {
        this.currentRequestProgress = $r1 != null ? (RequestProgress) this.progressMap.get($r1) : null;
    }

    long getBatchProgress() throws  {
        return this.batchProgress;
    }

    long getMaxProgress() throws  {
        return this.maxProgress;
    }

    public void write(byte[] $r1) throws IOException {
        this.out.write($r1);
        addProgress((long) $r1.length);
    }

    public void write(byte[] $r1, int $i0, int $i1) throws IOException {
        this.out.write($r1, $i0, $i1);
        addProgress((long) $i1);
    }

    public void write(int $i0) throws IOException {
        this.out.write($i0);
        addProgress(1);
    }

    public void close() throws IOException {
        super.close();
        for (RequestProgress reportProgress : this.progressMap.values()) {
            reportProgress.reportProgress();
        }
        reportBatchProgress();
    }
}
