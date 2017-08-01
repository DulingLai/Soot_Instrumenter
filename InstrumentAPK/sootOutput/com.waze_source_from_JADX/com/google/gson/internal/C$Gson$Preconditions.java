package com.google.gson.internal;

import dalvik.annotation.Signature;

public final class C$Gson$Preconditions {
    private C$Gson$Preconditions() throws  {
        throw new UnsupportedOperationException();
    }

    public static <T> T checkNotNull(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)TT;"}) T $r0) throws  {
        if ($r0 != null) {
            return $r0;
        }
        throw new NullPointerException();
    }

    public static void checkArgument(boolean $z0) throws  {
        if (!$z0) {
            throw new IllegalArgumentException();
        }
    }
}
