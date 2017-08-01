package com.abaltatech.mcp.mcs.fileupload;

public interface IFileUploadNotification {
    void onFileUploadComplete(String str) throws ;

    void onFileUploadFailed(String str, EFileUploadError eFileUploadError) throws ;

    void onFileUploadStarted(String str) throws ;
}
