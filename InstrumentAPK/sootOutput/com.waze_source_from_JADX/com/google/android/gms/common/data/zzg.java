package com.google.android.gms.common.data;

import dalvik.annotation.Signature;
import java.util.NoSuchElementException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg<T> extends zzb<T> {
    private T Hj;

    public zzg(@Signature({"(", "Lcom/google/android/gms/common/data/DataBuffer", "<TT;>;)V"}) DataBuffer<T> $r1) throws  {
        super($r1);
    }

    public T next() throws  {
        if (hasNext()) {
            this.GM++;
            if (this.GM == 0) {
                this.Hj = this.GL.get(0);
                if (!(this.Hj instanceof zzc)) {
                    String $r3 = String.valueOf(this.Hj.getClass());
                    throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 44).append("DataBuffer reference of type ").append($r3).append(" is not movable").toString());
                }
            }
            ((zzc) this.Hj).zzia(this.GM);
            return this.Hj;
        }
        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.GM);
    }
}
