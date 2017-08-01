package com.google.gson.stream;

import java.io.IOException;

public final class MalformedJsonException extends IOException {
    private static final long serialVersionUID = 1;

    public MalformedJsonException(String $r1) throws  {
        super($r1);
    }

    public MalformedJsonException(String $r1, Throwable $r2) throws  {
        super($r1);
        initCause($r2);
    }

    public MalformedJsonException(Throwable $r1) throws  {
        initCause($r1);
    }
}
