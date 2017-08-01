package com.waze.map;

public interface CanvasView {
    void onGLReady() throws ;

    void onNativeCanvasReady() throws ;

    boolean postDelayed(Runnable runnable, long j) throws ;

    void queueEvent(Runnable runnable) throws ;

    void requestRender() throws ;
}
