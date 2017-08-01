package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.os.OperationCanceledException;
import android.support.v4.util.TimeUtils;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class AsyncTaskLoader<D> extends Loader<D> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncTaskLoader";
    volatile LoadTask mCancellingTask;
    private final Executor mExecutor;
    Handler mHandler;
    long mLastLoadCompleteTime;
    volatile LoadTask mTask;
    long mUpdateThrottle;

    final class LoadTask extends ModernAsyncTask<Void, Void, D> implements Runnable {
        private final CountDownLatch mDone = new CountDownLatch(1);
        boolean waiting;

        LoadTask() throws  {
        }

        protected D doInBackground(@Signature({"([", "Ljava/lang/Void;", ")TD;"}) Void... params) throws  {
            try {
                return AsyncTaskLoader.this.onLoadInBackground();
            } catch (OperationCanceledException $r2) {
                if (isCancelled()) {
                    return null;
                }
                throw $r2;
            }
        }

        protected void onPostExecute(@Signature({"(TD;)V"}) D $r1) throws  {
            try {
                AsyncTaskLoader.this.dispatchOnLoadComplete(this, $r1);
            } finally {
                this.mDone.countDown();
            }
        }

        protected void onCancelled(@Signature({"(TD;)V"}) D $r1) throws  {
            try {
                AsyncTaskLoader.this.dispatchOnCancelled(this, $r1);
            } finally {
                this.mDone.countDown();
            }
        }

        public void run() throws  {
            this.waiting = false;
            AsyncTaskLoader.this.executePendingTask();
        }

        public void waitForLoader() throws  {
            try {
                this.mDone.await();
            } catch (InterruptedException e) {
            }
        }
    }

    public abstract D loadInBackground() throws ;

    public AsyncTaskLoader(Context $r1) throws  {
        this($r1, ModernAsyncTask.THREAD_POOL_EXECUTOR);
    }

    private AsyncTaskLoader(Context $r1, Executor $r2) throws  {
        super($r1);
        this.mLastLoadCompleteTime = -10000;
        this.mExecutor = $r2;
    }

    public void setUpdateThrottle(long $l0) throws  {
        this.mUpdateThrottle = $l0;
        if ($l0 != 0) {
            this.mHandler = new Handler();
        }
    }

    protected void onForceLoad() throws  {
        super.onForceLoad();
        cancelLoad();
        this.mTask = new LoadTask();
        executePendingTask();
    }

    protected boolean onCancelLoad() throws  {
        if (this.mTask == null) {
            return false;
        }
        if (this.mCancellingTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            this.mTask = null;
            return false;
        } else if (this.mTask.waiting) {
            this.mTask.waiting = false;
            this.mHandler.removeCallbacks(this.mTask);
            this.mTask = null;
            return false;
        } else {
            boolean $z0 = this.mTask.cancel(false);
            if ($z0) {
                this.mCancellingTask = this.mTask;
                cancelLoadInBackground();
            }
            this.mTask = null;
            return $z0;
        }
    }

    public void onCanceled(@Signature({"(TD;)V"}) D d) throws  {
    }

    void executePendingTask() throws  {
        if (this.mCancellingTask == null && this.mTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            if (this.mUpdateThrottle <= 0 || SystemClock.uptimeMillis() >= this.mLastLoadCompleteTime + this.mUpdateThrottle) {
                this.mTask.executeOnExecutor(this.mExecutor, null);
                return;
            }
            this.mTask.waiting = true;
            this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
        }
    }

    void dispatchOnCancelled(@Signature({"(", "Landroid/support/v4/content/AsyncTaskLoader", "<TD;>.", "LoadTask;", "TD;)V"}) LoadTask $r1, @Signature({"(", "Landroid/support/v4/content/AsyncTaskLoader", "<TD;>.", "LoadTask;", "TD;)V"}) D $r2) throws  {
        onCanceled($r2);
        if (this.mCancellingTask == $r1) {
            rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            deliverCancellation();
            executePendingTask();
        }
    }

    void dispatchOnLoadComplete(@Signature({"(", "Landroid/support/v4/content/AsyncTaskLoader", "<TD;>.", "LoadTask;", "TD;)V"}) LoadTask $r1, @Signature({"(", "Landroid/support/v4/content/AsyncTaskLoader", "<TD;>.", "LoadTask;", "TD;)V"}) D $r2) throws  {
        if (this.mTask != $r1) {
            dispatchOnCancelled($r1, $r2);
        } else if (isAbandoned()) {
            onCanceled($r2);
        } else {
            commitContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mTask = null;
            deliverResult($r2);
        }
    }

    protected D onLoadInBackground() throws  {
        return loadInBackground();
    }

    public void cancelLoadInBackground() throws  {
    }

    public boolean isLoadInBackgroundCanceled() throws  {
        return this.mCancellingTask != null;
    }

    public void waitForLoader() throws  {
        LoadTask $r1 = this.mTask;
        if ($r1 != null) {
            $r1.waitForLoader();
        }
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        super.dump($r1, $r2, $r3, $r4);
        if (this.mTask != null) {
            $r3.print($r1);
            $r3.print("mTask=");
            $r3.print(this.mTask);
            $r3.print(" waiting=");
            $r3.println(this.mTask.waiting);
        }
        if (this.mCancellingTask != null) {
            $r3.print($r1);
            $r3.print("mCancellingTask=");
            $r3.print(this.mCancellingTask);
            $r3.print(" waiting=");
            $r3.println(this.mCancellingTask.waiting);
        }
        if (this.mUpdateThrottle != 0) {
            $r3.print($r1);
            $r3.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.mUpdateThrottle, $r3);
            $r3.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), $r3);
            $r3.println();
        }
    }
}
