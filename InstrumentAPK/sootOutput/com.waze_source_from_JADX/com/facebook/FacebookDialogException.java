package com.facebook;

public class FacebookDialogException extends FacebookException {
    static final long serialVersionUID = 1;
    private int errorCode;
    private String failingUrl;

    public FacebookDialogException(String $r1, int $i0, String $r2) throws  {
        super($r1);
        this.errorCode = $i0;
        this.failingUrl = $r2;
    }

    public int getErrorCode() throws  {
        return this.errorCode;
    }

    public String getFailingUrl() throws  {
        return this.failingUrl;
    }

    public final String toString() throws  {
        return "{FacebookDialogException: " + "errorCode: " + getErrorCode() + ", message: " + getMessage() + ", url: " + getFailingUrl() + "}";
    }
}
