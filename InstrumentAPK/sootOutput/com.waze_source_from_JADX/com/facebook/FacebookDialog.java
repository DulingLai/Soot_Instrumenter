package com.facebook;

import dalvik.annotation.Signature;

public interface FacebookDialog<CONTENT, RESULT> {
    boolean canShow(@Signature({"(TCONTENT;)Z"}) CONTENT content) throws ;

    void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;)V"}) CallbackManager callbackManager, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;)V"}) FacebookCallback<RESULT> facebookCallback) throws ;

    void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;I)V"}) CallbackManager callbackManager, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;I)V"}) FacebookCallback<RESULT> facebookCallback, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;I)V"}) int i) throws ;

    void show(@Signature({"(TCONTENT;)V"}) CONTENT content) throws ;
}
