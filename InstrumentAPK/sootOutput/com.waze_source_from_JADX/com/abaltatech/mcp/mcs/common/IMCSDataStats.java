package com.abaltatech.mcp.mcs.common;

public interface IMCSDataStats {
    void enable(boolean z) throws ;

    int getAverageReceivedThroughput() throws ;

    int getAverageSentThroughput() throws ;

    long getBytesReceived() throws ;

    long getBytesSent() throws ;

    int getMaxReceivedThroughput() throws ;

    int getMaxSentThroughput() throws ;

    boolean isEnabled() throws ;

    void onDataReceived(int i) throws ;

    void onDataSent(int i) throws ;

    void resetStats() throws ;
}
