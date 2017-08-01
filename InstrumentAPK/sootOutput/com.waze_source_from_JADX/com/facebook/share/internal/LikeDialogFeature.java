package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

public enum LikeDialogFeature implements DialogFeature {
    LIKE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140701);
    
    private int minVersion;

    public String getAction() throws  {
        return NativeProtocol.ACTION_LIKE_DIALOG;
    }

    private LikeDialogFeature(@Signature({"(I)V"}) int $i1) throws  {
        this.minVersion = $i1;
    }

    public int getMinVersion() throws  {
        return this.minVersion;
    }
}
