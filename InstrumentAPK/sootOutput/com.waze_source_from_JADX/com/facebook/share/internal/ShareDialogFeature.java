package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

public enum ShareDialogFeature implements DialogFeature {
    SHARE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20130618),
    PHOTOS(NativeProtocol.PROTOCOL_VERSION_20140204),
    VIDEO(NativeProtocol.PROTOCOL_VERSION_20141028);
    
    private int minVersion;

    public String getAction() throws  {
        return NativeProtocol.ACTION_FEED_DIALOG;
    }

    private ShareDialogFeature(@Signature({"(I)V"}) int $i1) throws  {
        this.minVersion = $i1;
    }

    public int getMinVersion() throws  {
        return this.minVersion;
    }
}
