package com.facebook;

import android.os.Handler;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphRequest.OnProgressCallback;

class RequestProgress {
    private final Handler callbackHandler;
    private long lastReportedProgress;
    private long maxProgress;
    private long progress;
    private final GraphRequest request;
    private final long threshold = FacebookSdk.getOnProgressThreshold();

    RequestProgress(Handler $r1, GraphRequest $r2) throws  {
        this.request = $r2;
        this.callbackHandler = $r1;
    }

    long getProgress() throws  {
        return this.progress;
    }

    long getMaxProgress() throws  {
        return this.maxProgress;
    }

    void addProgress(long $l0) throws  {
        this.progress += $l0;
        if (this.progress >= this.lastReportedProgress + this.threshold || this.progress >= this.maxProgress) {
            reportProgress();
        }
    }

    void addToMax(long $l0) throws  {
        this.maxProgress += $l0;
    }

    void reportProgress() throws  {
        if (this.progress > this.lastReportedProgress) {
            Callback $r1 = this.request.getCallback();
            if (this.maxProgress > 0 && ($r1 instanceof OnProgressCallback)) {
                long $l0 = this.progress;
                long $l1 = this.maxProgress;
                OnProgressCallback onProgressCallback = (OnProgressCallback) $r1;
                if (this.callbackHandler == null) {
                    onProgressCallback.onProgress($l0, $l1);
                } else {
                    final OnProgressCallback onProgressCallback2 = onProgressCallback;
                    final long j = $l0;
                    final long j2 = $l1;
                    this.callbackHandler.post(new Runnable() {
                        public void run() throws  {
                            onProgressCallback2.onProgress(j, j2);
                        }
                    });
                }
                this.lastReportedProgress = this.progress;
            }
        }
    }
}
