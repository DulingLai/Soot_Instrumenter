package android.support.v4.content;

import android.os.AsyncTask;
import java.util.concurrent.Executor;

class ExecutorCompatHoneycomb {
    ExecutorCompatHoneycomb() throws  {
    }

    public static Executor getParallelExecutor() throws  {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }
}
