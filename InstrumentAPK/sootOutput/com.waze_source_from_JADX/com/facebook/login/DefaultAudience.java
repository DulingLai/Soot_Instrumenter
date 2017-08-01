package com.facebook.login;

import com.facebook.internal.NativeProtocol;
import dalvik.annotation.Signature;

public enum DefaultAudience {
    NONE(null),
    ONLY_ME(NativeProtocol.AUDIENCE_ME),
    FRIENDS(NativeProtocol.AUDIENCE_FRIENDS),
    EVERYONE(NativeProtocol.AUDIENCE_EVERYONE);
    
    private final String nativeProtocolAudience;

    private DefaultAudience(@Signature({"(", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        this.nativeProtocolAudience = $r2;
    }

    public String getNativeProtocolAudience() throws  {
        return this.nativeProtocolAudience;
    }
}
