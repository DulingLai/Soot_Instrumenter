package com.abaltatech.mcp.mcs.filedownload;

public interface IFileDownloadNotification {
    void OnFileDownloadCompleted(String str, String str2) throws ;

    void OnFileDownloadFailed(String str, String str2) throws ;

    void OnFileDownloadPaused(String str, String str2) throws ;

    void OnFileDownloadResumed(String str, String str2) throws ;

    void OnFileDownloadStarted(String str, String str2) throws ;
}
