package bolts;

import dalvik.annotation.Signature;

public class Capture<T> {
    private T value;

    public Capture(@Signature({"(TT;)V"}) T $r1) throws  {
        this.value = $r1;
    }

    public T get() throws  {
        return this.value;
    }

    public void set(@Signature({"(TT;)V"}) T $r1) throws  {
        this.value = $r1;
    }
}
