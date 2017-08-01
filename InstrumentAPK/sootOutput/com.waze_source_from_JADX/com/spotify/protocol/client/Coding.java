package com.spotify.protocol.client;

import dalvik.annotation.Signature;

public class Coding {
    private Coding() throws  {
    }

    public static <T> T checkNotNull(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)TT;"}) T $r0) throws  {
        if ($r0 != null) {
            return $r0;
        }
        throw new NullPointerException("Null is not allowed here.");
    }
}
