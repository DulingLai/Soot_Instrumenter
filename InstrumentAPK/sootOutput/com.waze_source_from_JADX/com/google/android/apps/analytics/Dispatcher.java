package com.google.android.apps.analytics;

/* compiled from: dalvik_source_com.waze.apk */
interface Dispatcher {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Callbacks {
        void dispatchFinished() throws ;

        void eventDispatched(long j) throws ;
    }

    void dispatchEvents(Event[] eventArr) throws ;

    void init(Callbacks callbacks, String str) throws ;

    boolean isDryRun() throws ;

    void setDryRun(boolean z) throws ;

    void stop() throws ;
}
