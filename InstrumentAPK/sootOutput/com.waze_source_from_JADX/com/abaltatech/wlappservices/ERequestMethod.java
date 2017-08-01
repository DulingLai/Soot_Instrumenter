package com.abaltatech.wlappservices;

public enum ERequestMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public byte toByte() throws  {
        switch (this) {
            case GET:
                return (byte) 0;
            case POST:
                return (byte) 1;
            case PUT:
                return (byte) 2;
            case DELETE:
                return (byte) 3;
            default:
                throw new UnsupportedOperationException("Unknown request method: " + this);
        }
    }

    public static ERequestMethod valueOf(byte $b0) throws  {
        switch ($b0) {
            case (byte) 0:
                return GET;
            case (byte) 1:
                return POST;
            case (byte) 2:
                return PUT;
            case (byte) 3:
                return DELETE;
            default:
                throw new UnsupportedOperationException("Unknown request method: " + $b0);
        }
    }
}
