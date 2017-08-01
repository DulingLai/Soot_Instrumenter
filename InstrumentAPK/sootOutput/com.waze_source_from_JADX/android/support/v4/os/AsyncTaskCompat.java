package android.support.v4.os;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import dalvik.annotation.Signature;

public final class AsyncTaskCompat {
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(@Signature({"<Params:", "Ljava/lang/Object;", "Progress:", "Ljava/lang/Object;", "Result:", "Ljava/lang/Object;", ">(", "Landroid/os/AsyncTask", "<TParams;TProgress;TResult;>;[TParams;)", "Landroid/os/AsyncTask", "<TParams;TProgress;TResult;>;"}) AsyncTask<Params, Progress, Result> $r0, @Signature({"<Params:", "Ljava/lang/Object;", "Progress:", "Ljava/lang/Object;", "Result:", "Ljava/lang/Object;", ">(", "Landroid/os/AsyncTask", "<TParams;TProgress;TResult;>;[TParams;)", "Landroid/os/AsyncTask", "<TParams;TProgress;TResult;>;"}) Params... $r1) throws  {
        if ($r0 == null) {
            throw new IllegalArgumentException("task can not be null");
        } else if (VERSION.SDK_INT >= 11) {
            AsyncTaskCompatHoneycomb.executeParallel($r0, $r1);
            return $r0;
        } else {
            $r0.execute($r1);
            return $r0;
        }
    }

    private AsyncTaskCompat() throws  {
    }
}
