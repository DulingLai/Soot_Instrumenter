package android.support.v4.os;

import android.os.AsyncTask;
import dalvik.annotation.Signature;

class AsyncTaskCompatHoneycomb {
    AsyncTaskCompatHoneycomb() throws  {
    }

    static <Params, Progress, Result> void executeParallel(@Signature({"<Params:", "Ljava/lang/Object;", "Progress:", "Ljava/lang/Object;", "Result:", "Ljava/lang/Object;", ">(", "Landroid/os/AsyncTask", "<TParams;TProgress;TResult;>;[TParams;)V"}) AsyncTask<Params, Progress, Result> $r0, @Signature({"<Params:", "Ljava/lang/Object;", "Progress:", "Ljava/lang/Object;", "Result:", "Ljava/lang/Object;", ">(", "Landroid/os/AsyncTask", "<TParams;TProgress;TResult;>;[TParams;)V"}) Params... $r1) throws  {
        $r0.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, $r1);
    }
}
