package com.facebook.internal;

public interface DialogFeature {
    String getAction() throws ;

    int getMinVersion() throws ;

    String name() throws ;
}
