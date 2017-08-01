package com.google.android.apps.analytics;

/* compiled from: dalvik_source_com.waze.apk */
interface EventStore {
    void deleteEvent(long j) throws ;

    int getNumStoredEvents() throws ;

    String getReferrer() throws ;

    int getStoreId() throws ;

    String getVisitorCustomVar(int i) throws ;

    Event[] peekEvents() throws ;

    Event[] peekEvents(int i) throws ;

    void putEvent(Event event) throws ;

    void setReferrer(String str) throws ;

    void startNewVisit() throws ;
}
