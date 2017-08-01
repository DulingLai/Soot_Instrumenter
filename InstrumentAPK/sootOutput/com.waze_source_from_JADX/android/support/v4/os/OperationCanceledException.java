package android.support.v4.os;

public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() throws  {
        this(null);
    }

    public OperationCanceledException(String $r1) throws  {
        if ($r1 == null) {
            $r1 = "The operation has been canceled.";
        }
        super($r1);
    }
}
