package com.facebook.login;

import dalvik.annotation.Signature;

public enum LoginBehavior {
    NATIVE_WITH_FALLBACK(true, true),
    NATIVE_ONLY(true, false),
    WEB_ONLY(false, true);
    
    private final boolean allowsKatanaAuth;
    private final boolean allowsWebViewAuth;

    private LoginBehavior(@Signature({"(ZZ)V"}) boolean $z0, @Signature({"(ZZ)V"}) boolean $z1) throws  {
        this.allowsKatanaAuth = $z0;
        this.allowsWebViewAuth = $z1;
    }

    boolean allowsKatanaAuth() throws  {
        return this.allowsKatanaAuth;
    }

    boolean allowsWebViewAuth() throws  {
        return this.allowsWebViewAuth;
    }
}
