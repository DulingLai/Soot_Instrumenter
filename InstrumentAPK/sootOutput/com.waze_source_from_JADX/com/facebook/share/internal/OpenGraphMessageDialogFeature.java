package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

public enum OpenGraphMessageDialogFeature implements DialogFeature {
    OG_MESSAGE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140204);
    
    private int minVersion;

    public String getAction() throws  {
        return NativeProtocol.ACTION_OGMESSAGEPUBLISH_DIALOG;
    }

    private OpenGraphMessageDialogFeature(@Signature({"(I)V"}) int $i1) throws  {
        this.minVersion = $i1;
    }

    public int getMinVersion() throws  {
        return this.minVersion;
    }
}
