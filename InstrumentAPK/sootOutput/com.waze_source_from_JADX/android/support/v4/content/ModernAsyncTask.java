package android.support.v4.content;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import dalvik.annotation.Signature;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

abstract class ModernAsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE = 5;
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    private static volatile Executor sDefaultExecutor = THREAD_POOL_EXECUTOR;
    private static InternalHandler sHandler;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue(10);
    private static final ThreadFactory sThreadFactory = new C00421();
    private final FutureTask<Result> mFuture = new FutureTask<Result>(this.mWorker) {
        protected void done() throws  {
            try {
                ModernAsyncTask.this.postResultIfNotInvoked(get());
            } catch (InterruptedException $r4) {
                Log.w(ModernAsyncTask.LOG_TAG, $r4);
            } catch (ExecutionException $r5) {
                throw new RuntimeException("An error occurred while executing doInBackground()", $r5.getCause());
            } catch (CancellationException e) {
                ModernAsyncTask.this.postResultIfNotInvoked(null);
            } catch (Throwable $r1) {
                RuntimeException $r6 = new RuntimeException("An error occurred while executing doInBackground()", $r1);
            }
        }
    };
    private volatile Status mStatus = Status.PENDING;
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();
    private final WorkerRunnable<Params, Result> mWorker = new C00432();

    static class C00421 implements ThreadFactory {
        private final AtomicInteger mCount = new AtomicInteger(1);

        C00421() throws  {
        }

        public Thread newThread(Runnable $r1) throws  {
            return new Thread($r1, "ModernAsyncTask #" + this.mCount.getAndIncrement());
        }
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        private WorkerRunnable() throws  {
        }
    }

    class C00432 extends WorkerRunnable<Params, Result> {
        C00432() throws  {
            super();
        }

        public Result call() throws Exception {
            ModernAsyncTask.this.mTaskInvoked.set(true);
            Process.setThreadPriority(10);
            return ModernAsyncTask.this.postResult(ModernAsyncTask.this.doInBackground(this.mParams));
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final ModernAsyncTask mTask;

        AsyncTaskResult(@Signature({"(", "Landroid/support/v4/content/ModernAsyncTask;", "[TData;)V"}) ModernAsyncTask $r1, @Signature({"(", "Landroid/support/v4/content/ModernAsyncTask;", "[TData;)V"}) Data... $r2) throws  {
            this.mTask = $r1;
            this.mData = $r2;
        }
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() throws  {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message $r1) throws  {
            AsyncTaskResult $r3 = (AsyncTaskResult) $r1.obj;
            switch ($r1.what) {
                case 1:
                    $r3.mTask.finish($r3.mData[0]);
                    return;
                case 2:
                    $r3.mTask.onProgressUpdate($r3.mData);
                    return;
                default:
                    return;
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(@Signature({"([TParams;)TResult;"}) Params... paramsArr) throws ;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.os.Handler getHandler() throws  {
        /*
        r0 = android.support.v4.content.ModernAsyncTask.class;
        monitor-enter(r0);
        r1 = sHandler;	 Catch:{ Throwable -> 0x0014 }
        if (r1 != 0) goto L_0x000e;
    L_0x0007:
        r1 = new android.support.v4.content.ModernAsyncTask$InternalHandler;	 Catch:{ Throwable -> 0x0014 }
        r1.<init>();	 Catch:{ Throwable -> 0x0014 }
        sHandler = r1;	 Catch:{ Throwable -> 0x0014 }
    L_0x000e:
        r1 = sHandler;	 Catch:{ Throwable -> 0x0014 }
        r0 = android.support.v4.content.ModernAsyncTask.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0014 }
        return r1;
    L_0x0014:
        r2 = move-exception;
        r0 = android.support.v4.content.ModernAsyncTask.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0014 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.ModernAsyncTask.getHandler():android.os.Handler");
    }

    public static void setDefaultExecutor(Executor $r0) throws  {
        sDefaultExecutor = $r0;
    }

    private void postResultIfNotInvoked(@Signature({"(TResult;)V"}) Result $r1) throws  {
        if (!this.mTaskInvoked.get()) {
            postResult($r1);
        }
    }

    private Result postResult(@Signature({"(TResult;)TResult;"}) Result $r1) throws  {
        getHandler().obtainMessage(1, new AsyncTaskResult(this, $r1)).sendToTarget();
        return $r1;
    }

    public final Status getStatus() throws  {
        return this.mStatus;
    }

    protected void onPreExecute() throws  {
    }

    protected void onPostExecute(@Signature({"(TResult;)V"}) Result result) throws  {
    }

    protected void onProgressUpdate(@Signature({"([TProgress;)V"}) Progress... progressArr) throws  {
    }

    protected void onCancelled(@Signature({"(TResult;)V"}) Result result) throws  {
        onCancelled();
    }

    protected void onCancelled() throws  {
    }

    public final boolean isCancelled() throws  {
        return this.mFuture.isCancelled();
    }

    public final boolean cancel(boolean $z0) throws  {
        return this.mFuture.cancel($z0);
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(@Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TResult;"}) long $l0, @Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TResult;"}) TimeUnit $r1) throws InterruptedException, ExecutionException, TimeoutException {
        return this.mFuture.get($l0, $r1);
    }

    public final ModernAsyncTask<Params, Progress, Result> execute(@Signature({"([TParams;)", "Landroid/support/v4/content/ModernAsyncTask", "<TParams;TProgress;TResult;>;"}) Params... $r1) throws  {
        return executeOnExecutor(sDefaultExecutor, $r1);
    }

    public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(@Signature({"(", "Ljava/util/concurrent/Executor;", "[TParams;)", "Landroid/support/v4/content/ModernAsyncTask", "<TParams;TProgress;TResult;>;"}) Executor $r1, @Signature({"(", "Ljava/util/concurrent/Executor;", "[TParams;)", "Landroid/support/v4/content/ModernAsyncTask", "<TParams;TProgress;TResult;>;"}) Params... $r2) throws  {
        if (this.mStatus != Status.PENDING) {
            switch (this.mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                default:
                    break;
            }
        }
        this.mStatus = Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = $r2;
        $r1.execute(this.mFuture);
        return this;
    }

    public static void execute(Runnable $r0) throws  {
        sDefaultExecutor.execute($r0);
    }

    protected final void publishProgress(@Signature({"([TProgress;)V"}) Progress... $r1) throws  {
        if (!isCancelled()) {
            getHandler().obtainMessage(2, new AsyncTaskResult(this, $r1)).sendToTarget();
        }
    }

    private void finish(@Signature({"(TResult;)V"}) Result $r1) throws  {
        if (isCancelled()) {
            onCancelled($r1);
        } else {
            onPostExecute($r1);
        }
        this.mStatus = Status.FINISHED;
    }
}
