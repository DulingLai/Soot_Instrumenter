package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

public enum AppInviteDialogFeature implements DialogFeature {
    APP_INVITES_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140701);
    
    private int minVersion;

    public String getAction() throws  {
        return NativeProtocol.ACTION_APPINVITE_DIALOG;
    }

    private AppInviteDialogFeature(@Signature({"(I)V"}) int $i1) throws  {
        this.minVersion = $i1;
    }

    public int getMinVersion() throws  {
        return this.minVersion;
    }
}
