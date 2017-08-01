package com.facebook;

public class FacebookServiceException extends FacebookException {
    private static final long serialVersionUID = 1;
    private final FacebookRequestError error;

    public FacebookServiceException(FacebookRequestError $r1, String $r2) throws  {
        super($r2);
        this.error = $r1;
    }

    public final FacebookRequestError getRequestError() throws  {
        return this.error;
    }

    public final String toString() throws  {
        return "{FacebookServiceException: " + "httpResponseCode: " + this.error.getRequestStatusCode() + ", facebookErrorCode: " + this.error.getErrorCode() + ", facebookErrorType: " + this.error.getErrorType() + ", message: " + this.error.getErrorMessage() + "}";
    }
}
