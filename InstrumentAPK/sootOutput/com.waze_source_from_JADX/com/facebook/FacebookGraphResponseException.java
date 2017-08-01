package com.facebook;

public class FacebookGraphResponseException extends FacebookException {
    private final GraphResponse graphResponse;

    public FacebookGraphResponseException(GraphResponse $r1, String $r2) throws  {
        super($r2);
        this.graphResponse = $r1;
    }

    public final GraphResponse getGraphResponse() throws  {
        return this.graphResponse;
    }

    public final String toString() throws  {
        FacebookRequestError $r2 = this.graphResponse != null ? this.graphResponse.getError() : null;
        StringBuilder $r3 = new StringBuilder().append("{FacebookGraphResponseException: ");
        String $r4 = getMessage();
        if ($r4 != null) {
            $r3.append($r4);
            $r3.append(" ");
        }
        if ($r2 != null) {
            $r3.append("httpResponseCode: ").append($r2.getRequestStatusCode()).append(", facebookErrorCode: ").append($r2.getErrorCode()).append(", facebookErrorType: ").append($r2.getErrorType()).append(", message: ").append($r2.getErrorMessage()).append("}");
        }
        return $r3.toString();
    }
}
