package com.abaltatech.mcp.mcs.logger;

public interface IMCSLogHandler {
    void log(String str) throws ;

    void log(String str, String str2) throws ;

    void log(String str, String str2, Throwable th) throws ;
}
