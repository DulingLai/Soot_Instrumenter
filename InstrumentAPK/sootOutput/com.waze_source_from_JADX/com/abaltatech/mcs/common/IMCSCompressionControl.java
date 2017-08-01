package com.abaltatech.mcs.common;

public interface IMCSCompressionControl {
    public static final int eAlwaysOn = 1;
    public static final int eCompressionOff = 0;
    public static final int eSmartCompression = 2;

    int getCompressionMode() throws ;

    int getMinCompressionSize() throws ;

    void setCompressionMode(int i) throws ;

    void setMinCompressionSize(int i) throws ;
}
