package com.facebook;

public class FacebookException extends RuntimeException {
    static final long serialVersionUID = 1;

    public FacebookException(String $r1) throws  {
        super($r1);
    }

    public FacebookException(String $r1, Object... $r2) throws  {
        this(String.format($r1, $r2));
    }

    public FacebookException(String $r1, Throwable $r2) throws  {
        super($r1, $r2);
    }

    public FacebookException(Throwable $r1) throws  {
        super($r1);
    }

    public String toString() throws  {
        return getMessage();
    }
}
