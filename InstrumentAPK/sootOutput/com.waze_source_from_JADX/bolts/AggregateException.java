package bolts;

import dalvik.annotation.Signature;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AggregateException extends Exception {
    private static final String DEFAULT_MESSAGE = "There were multiple errors.";
    private static final long serialVersionUID = 1;
    private List<Throwable> innerThrowables;

    public AggregateException(String $r1, Throwable[] $r2) throws  {
        this($r1, Arrays.asList($r2));
    }

    public AggregateException(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<+", "Ljava/lang/Throwable;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<+", "Ljava/lang/Throwable;", ">;)V"}) List<? extends Throwable> $r2) throws  {
        Throwable $r4 = ($r2 == null || $r2.size() <= 0) ? null : (Throwable) $r2.get(0);
        super($r1, $r4);
        this.innerThrowables = Collections.unmodifiableList($r2);
    }

    public AggregateException(@Signature({"(", "Ljava/util/List", "<+", "Ljava/lang/Throwable;", ">;)V"}) List<? extends Throwable> $r1) throws  {
        this(DEFAULT_MESSAGE, (List) $r1);
    }

    public List<Throwable> getInnerThrowables() throws  {
        return this.innerThrowables;
    }

    public void printStackTrace(PrintStream $r1) throws  {
        super.printStackTrace($r1);
        int $i0 = -1;
        for (Throwable $r5 : this.innerThrowables) {
            $r1.append("\n");
            $r1.append("  Inner throwable #");
            $i0++;
            $r1.append(Integer.toString($i0));
            $r1.append(": ");
            $r5.printStackTrace($r1);
            $r1.append("\n");
        }
    }

    public void printStackTrace(PrintWriter $r1) throws  {
        super.printStackTrace($r1);
        int $i0 = -1;
        for (Throwable $r5 : this.innerThrowables) {
            $r1.append("\n");
            $r1.append("  Inner throwable #");
            $i0++;
            $r1.append(Integer.toString($i0));
            $r1.append(": ");
            $r5.printStackTrace($r1);
            $r1.append("\n");
        }
    }

    @Deprecated
    public List<Exception> getErrors() throws  {
        ArrayList $r1 = new ArrayList();
        if (this.innerThrowables == null) {
            return $r1;
        }
        for (Throwable $r5 : this.innerThrowables) {
            if ($r5 instanceof Exception) {
                $r1.add((Exception) $r5);
            } else {
                $r1.add(new Exception($r5));
            }
        }
        return $r1;
    }

    @Deprecated
    public Throwable[] getCauses() throws  {
        return (Throwable[]) this.innerThrowables.toArray(new Throwable[this.innerThrowables.size()]);
    }
}
