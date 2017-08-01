package com.google.android.gms.common.data;

/* compiled from: dalvik_source_com.waze.apk */
public interface DataBufferObserver {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Observable {
        void addObserver(DataBufferObserver dataBufferObserver) throws ;

        void removeObserver(DataBufferObserver dataBufferObserver) throws ;
    }

    void onDataChanged() throws ;

    void onDataRangeChanged(int i, int i2) throws ;

    void onDataRangeInserted(int i, int i2) throws ;

    void onDataRangeMoved(int i, int i2, int i3) throws ;

    void onDataRangeRemoved(int i, int i2) throws ;
}
