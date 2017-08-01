package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import dalvik.annotation.Signature;

public abstract class ShareMedia implements ShareModel {
    private final Bundle params;

    public static abstract class Builder<M extends ShareMedia, B extends Builder> implements ShareModelBuilder<M, B> {
        private Bundle params = new Bundle();

        @Deprecated
        public B setParameter(@Deprecated @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")TB;"}) String $r1, @Deprecated @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")TB;"}) String $r2) throws  {
            this.params.putString($r1, $r2);
            return this;
        }

        @Deprecated
        public B setParameters(@Deprecated @Signature({"(", "Landroid/os/Bundle;", ")TB;"}) Bundle $r1) throws  {
            this.params.putAll($r1);
            return this;
        }

        public B readFrom(@Signature({"(TM;)TB;"}) M $r0) throws  {
            return $r0 == null ? this : setParameters($r0.getParameters());
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected ShareMedia(Builder $r1) throws  {
        this.params = new Bundle($r1.params);
    }

    ShareMedia(Parcel $r1) throws  {
        this.params = $r1.readBundle();
    }

    @Deprecated
    public Bundle getParameters() throws  {
        return new Bundle(this.params);
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeBundle(this.params);
    }
}
