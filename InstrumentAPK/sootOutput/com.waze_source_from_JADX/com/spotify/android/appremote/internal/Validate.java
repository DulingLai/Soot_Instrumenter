package com.spotify.android.appremote.internal;

import dalvik.annotation.Signature;

public final class Validate {
    private Validate() throws  {
    }

    public static <T> T checkNotNull(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)TT;"}) T $r0) throws  {
        if ($r0 != null) {
            return $r0;
        }
        throw new NullPointerException("The object is expected to be not null");
    }
}
