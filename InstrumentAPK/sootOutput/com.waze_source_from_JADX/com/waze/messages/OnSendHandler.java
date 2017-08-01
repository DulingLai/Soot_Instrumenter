package com.waze.messages;

import com.waze.user.UserData;
import java.io.Serializable;

public abstract class OnSendHandler implements Serializable {
    protected static final long serialVersionUID = 1;

    public abstract void onSend(boolean z, UserData userData, String str) throws ;
}
