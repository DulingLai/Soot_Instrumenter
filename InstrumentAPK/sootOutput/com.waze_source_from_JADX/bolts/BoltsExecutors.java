package bolts;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

final class BoltsExecutors {
    private static final BoltsExecutors INSTANCE = new BoltsExecutors();
    private final ExecutorService background;
    private final Executor immediate;
    private final ScheduledExecutorService scheduled;

    private static class ImmediateExecutor implements Executor {
        private static final int MAX_DEPTH = 15;
        private ThreadLocal<Integer> executionDepth;

        private ImmediateExecutor() throws  {
            this.executionDepth = new ThreadLocal();
        }

        private int incrementDepth() throws  {
            Integer $r3 = (Integer) this.executionDepth.get();
            if ($r3 == null) {
                $r3 = Integer.valueOf(0);
            }
            int $i0 = $r3.intValue() + 1;
            this.executionDepth.set(Integer.valueOf($i0));
            return $i0;
        }

        private int decrementDepth() throws  {
            Integer $r3 = (Integer) this.executionDepth.get();
            if ($r3 == null) {
                $r3 = Integer.valueOf(0);
            }
            int $i0 = $r3.intValue() - 1;
            if ($i0 == 0) {
                this.executionDepth.remove();
                return $i0;
            }
            this.executionDepth.set(Integer.valueOf($i0));
            return $i0;
        }

        public void execute(Runnable $r1) throws  {
            if (incrementDepth() <= 15) {
                try {
                    $r1.run();
                } catch (Throwable th) {
                    decrementDepth();
                }
            } else {
                BoltsExecutors.background().execute($r1);
            }
            decrementDepth();
        }
    }

    private static boolean isAndroidRuntime() throws  {
        String $r0 = System.getProperty("java.runtime.name");
        return $r0 == null ? false : $r0.toLowerCase(Locale.US).contains("android");
    }

    private BoltsExecutors() throws  {
        this.background = !isAndroidRuntime() ? Executors.newCachedThreadPool() : AndroidExecutors.newCachedThreadPool();
        this.scheduled = Executors.newSingleThreadScheduledExecutor();
        this.immediate = new ImmediateExecutor();
    }

    public static ExecutorService background() throws  {
        return INSTANCE.background;
    }

    static ScheduledExecutorService scheduled() throws  {
        return INSTANCE.scheduled;
    }

    static Executor immediate() throws  {
        return INSTANCE.immediate;
    }
}
