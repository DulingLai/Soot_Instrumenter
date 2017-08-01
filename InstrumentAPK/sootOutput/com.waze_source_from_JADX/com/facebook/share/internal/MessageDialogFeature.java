package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

public enum MessageDialogFeature implements DialogFeature {
    MESSAGE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140204),
    PHOTOS(NativeProtocol.PROTOCOL_VERSION_20140324),
    VIDEO(NativeProtocol.PROTOCOL_VERSION_20141218);
    
    private int minVersion;

    public String getAction() throws  {
        return NativeProtocol.ACTION_MESSAGE_DIALOG;
    }

    private MessageDialogFeature(@Signature({"(I)V"}) int $i1) throws  {
        this.minVersion = $i1;
    }

    public int getMinVersion() throws  {
        return this.minVersion;
    }
}
