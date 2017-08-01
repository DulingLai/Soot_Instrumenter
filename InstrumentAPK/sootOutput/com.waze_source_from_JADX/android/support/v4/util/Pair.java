package android.support.v4.util;

import dalvik.annotation.Signature;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(@Signature({"(TF;TS;)V"}) F $r1, @Signature({"(TF;TS;)V"}) S $r2) throws  {
        this.first = $r1;
        this.second = $r2;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof Pair)) {
            return false;
        }
        Pair $r2 = (Pair) $r1;
        if (objectsEqual($r2.first, this.first)) {
            return objectsEqual($r2.second, this.second);
        } else {
            return false;
        }
    }

    private static boolean objectsEqual(Object $r0, Object $r1) throws  {
        return $r0 == $r1 || ($r0 != null && $r0.equals($r1));
    }

    public int hashCode() throws  {
        int $i0 = 0;
        int $i1 = this.first == null ? 0 : this.first.hashCode();
        if (this.second != null) {
            $i0 = this.second.hashCode();
        }
        return $i1 ^ $i0;
    }

    public static <A, B> Pair<A, B> create(@Signature({"<A:", "Ljava/lang/Object;", "B:", "Ljava/lang/Object;", ">(TA;TB;)", "Landroid/support/v4/util/Pair", "<TA;TB;>;"}) A $r0, @Signature({"<A:", "Ljava/lang/Object;", "B:", "Ljava/lang/Object;", ">(TA;TB;)", "Landroid/support/v4/util/Pair", "<TA;TB;>;"}) B $r1) throws  {
        return new Pair($r0, $r1);
    }
}
