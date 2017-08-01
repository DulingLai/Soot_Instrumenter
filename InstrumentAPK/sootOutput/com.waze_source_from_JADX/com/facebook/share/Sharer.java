package com.facebook.share;

public interface Sharer {

    public static class Result {
        final String postId;

        public Result(String $r1) throws  {
            this.postId = $r1;
        }

        public String getPostId() throws  {
            return this.postId;
        }
    }

    boolean getShouldFailOnDataError() throws ;

    void setShouldFailOnDataError(boolean z) throws ;
}
