package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

public enum OpenGraphActionDialogFeature implements DialogFeature {
    OG_ACTION_DIALOG(NativeProtocol.PROTOCOL_VERSION_20130618);
    
    private int minVersion;

    public String getAction() throws  {
        return NativeProtocol.ACTION_OGACTIONPUBLISH_DIALOG;
    }

    private OpenGraphActionDialogFeature(@Signature({"(I)V"}) int $i1) throws  {
        this.minVersion = $i1;
    }

    public int getMinVersion() throws  {
        return this.minVersion;
    }
}
