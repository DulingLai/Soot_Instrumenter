package com.facebook;

import dalvik.annotation.Signature;

public enum AccessTokenSource {
    NONE(false),
    FACEBOOK_APPLICATION_WEB(true),
    FACEBOOK_APPLICATION_NATIVE(true),
    FACEBOOK_APPLICATION_SERVICE(true),
    WEB_VIEW(false),
    TEST_USER(true),
    CLIENT_TOKEN(true);
    
    private final boolean canExtendToken;

    private AccessTokenSource(@Signature({"(Z)V"}) boolean $z0) throws  {
        this.canExtendToken = $z0;
    }

    boolean canExtendToken() throws  {
        return this.canExtendToken;
    }
}
