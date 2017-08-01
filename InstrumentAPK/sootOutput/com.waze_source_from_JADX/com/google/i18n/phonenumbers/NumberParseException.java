package com.google.i18n.phonenumbers;

public class NumberParseException extends Exception {
    private ErrorType errorType;
    private String message;

    public enum ErrorType {
        INVALID_COUNTRY_CODE,
        NOT_A_NUMBER,
        TOO_SHORT_AFTER_IDD,
        TOO_SHORT_NSN,
        TOO_LONG
    }

    public NumberParseException(ErrorType $r1, String $r2) throws  {
        super($r2);
        this.message = $r2;
        this.errorType = $r1;
    }

    public ErrorType getErrorType() throws  {
        return this.errorType;
    }

    public String toString() throws  {
        return "Error type: " + this.errorType + ". " + this.message;
    }
}
