package com.facebook;

import dalvik.annotation.Signature;

public interface FacebookCallback<RESULT> {
    void onCancel() throws ;

    void onError(FacebookException facebookException) throws ;

    void onSuccess(@Signature({"(TRESU", "LT;", ")V"}) RESULT result) throws ;
}
