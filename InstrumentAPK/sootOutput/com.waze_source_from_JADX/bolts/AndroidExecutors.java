package bolts;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class AndroidExecutors {
    static final int CORE_POOL_SIZE = (CPU_COUNT + 1);
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final AndroidExecutors INSTANCE = new AndroidExecutors();
    static final long KEEP_ALIVE_TIME = 1;
    static final int MAX_POOL_SIZE = ((CPU_COUNT * 2) + 1);
    private final Executor uiThread = new UIThreadExecutor();

    private static class UIThreadExecutor implements Executor {
        private UIThreadExecutor() throws  {
        }

        public void execute(Runnable $r1) throws  {
            new Handler(Looper.getMainLooper()).post($r1);
        }
    }

    private AndroidExecutors() throws  {
    }

    public static ExecutorService newCachedThreadPool() throws  {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue());
        allowCoreThreadTimeout(threadPoolExecutor, true);
        return threadPoolExecutor;
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory $r0) throws  {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(), $r0);
        allowCoreThreadTimeout(threadPoolExecutor, true);
        return threadPoolExecutor;
    }

    @SuppressLint({"NewApi"})
    public static void allowCoreThreadTimeout(ThreadPoolExecutor $r0, boolean $z0) throws  {
        if (VERSION.SDK_INT >= 9) {
            $r0.allowCoreThreadTimeOut($z0);
        }
    }

    public static Executor uiThread() throws  {
        return INSTANCE.uiThread;
    }
}
