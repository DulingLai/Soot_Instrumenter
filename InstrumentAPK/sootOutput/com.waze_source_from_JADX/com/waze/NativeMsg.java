package com.waze;

public class NativeMsg implements IMessageParam {
    private boolean mIsActive = true;

    public boolean IsActive() throws  {
        return this.mIsActive;
    }

    public void setActive(boolean $z0) throws  {
        this.mIsActive = $z0;
    }

    public NativeMsg(boolean $z0) throws  {
        this.mIsActive = $z0;
    }
}
