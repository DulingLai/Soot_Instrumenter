package android.support.v7.view.menu;

import dalvik.annotation.Signature;

class BaseWrapper<T> {
    final T mWrappedObject;

    BaseWrapper(@Signature({"(TT;)V"}) T $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.mWrappedObject = $r1;
    }

    public T getWrappedObject() throws  {
        return this.mWrappedObject;
    }
}
