package com.facebook;

import android.os.Handler;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

class ProgressNoopOutputStream extends OutputStream implements RequestOutputStream {
    private int batchMax;
    private final Handler callbackHandler;
    private GraphRequest currentRequest;
    private RequestProgress currentRequestProgress;
    private final Map<GraphRequest, RequestProgress> progressMap = new HashMap();

    ProgressNoopOutputStream(Handler $r1) throws  {
        this.callbackHandler = $r1;
    }

    public void setCurrentRequest(GraphRequest $r1) throws  {
        this.currentRequest = $r1;
        this.currentRequestProgress = $r1 != null ? (RequestProgress) this.progressMap.get($r1) : null;
    }

    int getMaxProgress() throws  {
        return this.batchMax;
    }

    Map<GraphRequest, RequestProgress> getProgressMap() throws  {
        return this.progressMap;
    }

    void addProgress(long $l0) throws  {
        if (this.currentRequestProgress == null) {
            this.currentRequestProgress = new RequestProgress(this.callbackHandler, this.currentRequest);
            this.progressMap.put(this.currentRequest, this.currentRequestProgress);
        }
        this.currentRequestProgress.addToMax($l0);
        this.batchMax = (int) (((long) this.batchMax) + $l0);
    }

    public void write(byte[] $r1) throws  {
        addProgress((long) $r1.length);
    }

    public void write(byte[] buffer, int offset, int $i1) throws  {
        addProgress((long) $i1);
    }

    public void write(int oneByte) throws  {
        addProgress(1);
    }
}
