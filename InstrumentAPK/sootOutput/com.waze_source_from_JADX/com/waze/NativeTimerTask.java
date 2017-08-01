package com.waze;

import java.util.TimerTask;

public final class NativeTimerTask extends TimerTask implements IMessageParam {
    private int mInterval;
    private boolean mIsActive = false;
    private NativeManager mNativeManager;
    private int mTaskId;
    private int mTaskNativeMsg;

    NativeTimerTask(int $i0, int $i1, int $i2, NativeManager $r1) throws  {
        this.mNativeManager = $r1;
        this.mTaskId = $i0;
        this.mInterval = $i2;
        this.mTaskNativeMsg = $i1;
        this.mIsActive = true;
    }

    public boolean IsActive() throws  {
        return this.mIsActive;
    }

    public synchronized void run() throws  {
        if (this.mInterval < 100) {
            this.mNativeManager.PostPriorityNativeMessage(this.mTaskNativeMsg, (IMessageParam) this);
        } else {
            this.mNativeManager.PostNativeMessage(this.mTaskNativeMsg, (IMessageParam) this);
        }
    }

    public void setTaskId(int $i0) throws  {
        this.mTaskId = $i0;
    }

    public int getTaskId() throws  {
        return this.mTaskId;
    }

    public boolean cancel() throws  {
        this.mIsActive = false;
        return super.cancel();
    }

    public synchronized void CancelSync() throws  {
        cancel();
    }
}
