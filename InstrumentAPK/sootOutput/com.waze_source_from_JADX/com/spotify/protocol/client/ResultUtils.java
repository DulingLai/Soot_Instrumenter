package com.spotify.protocol.client;

import dalvik.annotation.Signature;

public class ResultUtils {

    static class ErrorResult<T> implements Result<T> {
        private final Throwable mError;

        public T getData() throws  {
            return null;
        }

        public boolean isSuccessful() throws  {
            return false;
        }

        ErrorResult(Throwable $r1) throws  {
            this.mError = $r1;
        }

        public String getErrorMessage() throws  {
            return this.mError.getMessage();
        }

        public Throwable getError() throws  {
            return this.mError;
        }
    }

    static class SuccessfulResult<T> implements Result<T> {
        private final T mPayload;

        public Throwable getError() throws  {
            return null;
        }

        public String getErrorMessage() throws  {
            return null;
        }

        public boolean isSuccessful() throws  {
            return true;
        }

        SuccessfulResult(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mPayload = $r1;
        }

        public T getData() throws  {
            return this.mPayload;
        }
    }

    public static <T> Result<T> createErrorResult(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Throwable;", ")", "Lcom/spotify/protocol/client/Result", "<TT;>;"}) Throwable $r0) throws  {
        return new ErrorResult($r0);
    }

    public static <T> Result<T> createSuccessfulResult(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)", "Lcom/spotify/protocol/client/Result", "<TT;>;"}) T $r0) throws  {
        return new SuccessfulResult($r0);
    }

    private ResultUtils() throws  {
    }
}
