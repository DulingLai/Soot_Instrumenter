package bolts;

import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task<TResult> {
    public static final ExecutorService BACKGROUND_EXECUTOR = BoltsExecutors.background();
    private static final Executor IMMEDIATE_EXECUTOR = BoltsExecutors.immediate();
    public static final Executor UI_THREAD_EXECUTOR = AndroidExecutors.uiThread();
    private boolean cancelled;
    private boolean complete;
    private List<Continuation<TResult, Void>> continuations = new ArrayList();
    private Exception error;
    private final Object lock = new Object();
    private TResult result;

    class C02942 implements Continuation<TResult, Task<Void>> {
        C02942() throws  {
        }

        public Task<Void> then(@Signature({"(", "Lbolts/Task", "<TTResult;>;)", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Task<TResult> $r1) throws Exception {
            if ($r1.isCancelled()) {
                return Task.cancelled();
            }
            if ($r1.isFaulted()) {
                return Task.forError($r1.getError());
            }
            return Task.forResult(null);
        }
    }

    public class TaskCompletionSource {
        private TaskCompletionSource() throws  {
        }

        public Task<TResult> getTask() throws  {
            return Task.this;
        }

        public boolean trySetCancelled() throws  {
            synchronized (Task.this.lock) {
                if (Task.this.complete) {
                    return false;
                }
                Task.this.complete = true;
                Task.this.cancelled = true;
                Task.this.lock.notifyAll();
                Task.this.runContinuations();
                return true;
            }
        }

        public boolean trySetResult(@Signature({"(TTResult;)Z"}) TResult $r1) throws  {
            synchronized (Task.this.lock) {
                if (Task.this.complete) {
                    return false;
                }
                Task.this.complete = true;
                Task.this.result = $r1;
                Task.this.lock.notifyAll();
                Task.this.runContinuations();
                return true;
            }
        }

        public boolean trySetError(Exception $r1) throws  {
            synchronized (Task.this.lock) {
                if (Task.this.complete) {
                    return false;
                }
                Task.this.complete = true;
                Task.this.error = $r1;
                Task.this.lock.notifyAll();
                Task.this.runContinuations();
                return true;
            }
        }

        public void setCancelled() throws  {
            if (!trySetCancelled()) {
                throw new IllegalStateException("Cannot cancel a completed task.");
            }
        }

        public void setResult(@Signature({"(TTResult;)V"}) TResult $r1) throws  {
            if (!trySetResult($r1)) {
                throw new IllegalStateException("Cannot set the result of a completed task.");
            }
        }

        public void setError(Exception $r1) throws  {
            if (!trySetError($r1)) {
                throw new IllegalStateException("Cannot set the error on a completed task.");
            }
        }
    }

    private Task() throws  {
    }

    public static <TResult> TaskCompletionSource create() throws  {
        Task $r0 = new Task();
        $r0.getClass();
        return new TaskCompletionSource();
    }

    public boolean isCompleted() throws  {
        boolean z0;
        synchronized (this.lock) {
            z0 = this.complete;
        }
        return z0;
    }

    public boolean isCancelled() throws  {
        boolean z0;
        synchronized (this.lock) {
            z0 = this.cancelled;
        }
        return z0;
    }

    public boolean isFaulted() throws  {
        boolean $z0;
        synchronized (this.lock) {
            $z0 = this.error != null;
        }
        return $z0;
    }

    public TResult getResult() throws  {
        Object r3;
        synchronized (this.lock) {
            r3 = this.result;
        }
        return r3;
    }

    public Exception getError() throws  {
        Exception r3;
        synchronized (this.lock) {
            r3 = this.error;
        }
        return r3;
    }

    public void waitForCompletion() throws InterruptedException {
        synchronized (this.lock) {
            if (!isCompleted()) {
                this.lock.wait();
            }
        }
    }

    public static <TResult> Task<TResult> forResult(@Signature({"<TResult:", "Ljava/lang/Object;", ">(TTResult;)", "Lbolts/Task", "<TTResult;>;"}) TResult $r0) throws  {
        TaskCompletionSource $r1 = create();
        $r1.setResult($r0);
        return $r1.getTask();
    }

    public static <TResult> Task<TResult> forError(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/lang/Exception;", ")", "Lbolts/Task", "<TTResult;>;"}) Exception $r0) throws  {
        TaskCompletionSource $r1 = create();
        $r1.setError($r0);
        return $r1.getTask();
    }

    public static <TResult> Task<TResult> cancelled() throws  {
        TaskCompletionSource $r0 = create();
        $r0.setCancelled();
        return $r0.getTask();
    }

    public static Task<Void> delay(@Signature({"(J)", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) long $l0) throws  {
        return delay($l0, BoltsExecutors.scheduled());
    }

    static Task<Void> delay(@Signature({"(J", "Ljava/util/concurrent/ScheduledExecutorService;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) long $l0, @Signature({"(J", "Ljava/util/concurrent/ScheduledExecutorService;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) ScheduledExecutorService $r0) throws  {
        if ($l0 <= 0) {
            return forResult(null);
        }
        final TaskCompletionSource $r2 = create();
        $r0.schedule(new Runnable() {
            public void run() throws  {
                $r2.setResult(null);
            }
        }, $l0, TimeUnit.MILLISECONDS);
        return $r2.getTask();
    }

    public <TOut> Task<TOut> cast() throws  {
        return this;
    }

    public Task<Void> makeVoid() throws  {
        return continueWithTask(new C02942());
    }

    public static <TResult> Task<TResult> callInBackground(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;)", "Lbolts/Task", "<TTResult;>;"}) Callable<TResult> $r0) throws  {
        return call($r0, BACKGROUND_EXECUTOR, null);
    }

    public static <TResult> Task<TResult> callInBackground(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTResult;>;"}) Callable<TResult> $r0, @Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTResult;>;"}) CancellationToken $r1) throws  {
        return call($r0, BACKGROUND_EXECUTOR, $r1);
    }

    public static <TResult> Task<TResult> call(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTResult;>;"}) Callable<TResult> $r0, @Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTResult;>;"}) Executor $r1) throws  {
        return call($r0, $r1, null);
    }

    public static <TResult> Task<TResult> call(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTResult;>;"}) final Callable<TResult> $r0, @Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTResult;>;"}) Executor $r1, @Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTResult;>;"}) final CancellationToken $r2) throws  {
        final TaskCompletionSource $r3 = create();
        $r1.execute(new Runnable() {
            public void run() throws  {
                if ($r2 == null || !$r2.isCancellationRequested()) {
                    try {
                        $r3.setResult($r0.call());
                        return;
                    } catch (CancellationException e) {
                        $r3.setCancelled();
                        return;
                    } catch (Exception $r6) {
                        $r3.setError($r6);
                        return;
                    }
                }
                $r3.setCancelled();
            }
        });
        return $r3.getTask();
    }

    public static <TResult> Task<TResult> call(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;)", "Lbolts/Task", "<TTResult;>;"}) Callable<TResult> $r0) throws  {
        return call($r0, IMMEDIATE_EXECUTOR, null);
    }

    public static <TResult> Task<TResult> call(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTResult;>;"}) Callable<TResult> $r0, @Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TTResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTResult;>;"}) CancellationToken $r1) throws  {
        return call($r0, IMMEDIATE_EXECUTOR, $r1);
    }

    public static <TResult> Task<Task<TResult>> whenAnyResult(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<+", "Lbolts/Task", "<TTResult;>;>;)", "Lbolts/Task", "<", "Lbolts/Task", "<TTResult;>;>;"}) Collection<? extends Task<TResult>> $r0) throws  {
        if ($r0.size() == 0) {
            return forResult(null);
        }
        final TaskCompletionSource $r3 = create();
        final AtomicBoolean $r1 = new AtomicBoolean(false);
        for (Task continueWith : $r0) {
            continueWith.continueWith(new Continuation<TResult, Void>() {
                public Void then(@Signature({"(", "Lbolts/Task", "<TTResult;>;)", "Ljava/lang/Void;"}) Task<TResult> $r1) throws  {
                    if ($r1.compareAndSet(false, true)) {
                        $r3.setResult($r1);
                    }
                    return null;
                }
            });
        }
        return $r3.getTask();
    }

    public static Task<Task<?>> whenAny(@Signature({"(", "Ljava/util/Collection", "<+", "Lbolts/Task", "<*>;>;)", "Lbolts/Task", "<", "Lbolts/Task", "<*>;>;"}) Collection<? extends Task<?>> $r0) throws  {
        if ($r0.size() == 0) {
            return forResult(null);
        }
        final TaskCompletionSource $r3 = create();
        final AtomicBoolean $r1 = new AtomicBoolean(false);
        for (Task continueWith : $r0) {
            continueWith.continueWith(new Continuation<Object, Void>() {
                public Void then(@Signature({"(", "Lbolts/Task", "<", "Ljava/lang/Object;", ">;)", "Ljava/lang/Void;"}) Task<Object> $r1) throws  {
                    if ($r1.compareAndSet(false, true)) {
                        $r3.setResult($r1);
                    }
                    return null;
                }
            });
        }
        return $r3.getTask();
    }

    public static <TResult> Task<List<TResult>> whenAllResult(@Signature({"<TResult:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<+", "Lbolts/Task", "<TTResult;>;>;)", "Lbolts/Task", "<", "Ljava/util/List", "<TTResult;>;>;"}) final Collection<? extends Task<TResult>> $r0) throws  {
        return whenAll($r0).onSuccess(new Continuation<Void, List<TResult>>() {
            public List<TResult> then(@Signature({"(", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;)", "Ljava/util/List", "<TTResult;>;"}) Task<Void> task) throws Exception {
                if ($r0.size() == 0) {
                    return Collections.emptyList();
                }
                ArrayList $r4 = new ArrayList();
                for (Task result : $r0) {
                    $r4.add(result.getResult());
                }
                return $r4;
            }
        });
    }

    public static Task<Void> whenAll(@Signature({"(", "Ljava/util/Collection", "<+", "Lbolts/Task", "<*>;>;)", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Collection<? extends Task<?>> $r0) throws  {
        if ($r0.size() == 0) {
            return forResult(null);
        }
        TaskCompletionSource $r6 = create();
        ArrayList $r2 = new ArrayList();
        Object $r1 = new Object();
        AtomicInteger $r4 = new AtomicInteger($r0.size());
        AtomicBoolean $r3 = new AtomicBoolean(false);
        for (Task continueWith : $r0) {
            final Object obj = $r1;
            final ArrayList arrayList = $r2;
            final AtomicBoolean atomicBoolean = $r3;
            final AtomicInteger atomicInteger = $r4;
            final TaskCompletionSource taskCompletionSource = $r6;
            continueWith.continueWith(new Continuation<Object, Void>() {
                public Void then(@Signature({"(", "Lbolts/Task", "<", "Ljava/lang/Object;", ">;)", "Ljava/lang/Void;"}) Task<Object> $r1) throws  {
                    if ($r1.isFaulted()) {
                        synchronized (obj) {
                            arrayList.add($r1.getError());
                        }
                    }
                    if ($r1.isCancelled()) {
                        atomicBoolean.set(true);
                    }
                    if (atomicInteger.decrementAndGet() != 0) {
                        return null;
                    }
                    if (arrayList.size() != 0) {
                        if (arrayList.size() == 1) {
                            taskCompletionSource.setError((Exception) arrayList.get(0));
                            return null;
                        }
                        taskCompletionSource.setError(new AggregateException(String.format("There were %d exceptions.", new Object[]{Integer.valueOf(arrayList.size())}), arrayList));
                        return null;
                    } else if (atomicBoolean.get()) {
                        taskCompletionSource.setCancelled();
                        return null;
                    } else {
                        taskCompletionSource.setResult(null);
                        return null;
                    }
                }
            });
        }
        return $r6.getTask();
    }

    public Task<Void> continueWhile(@Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;)", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Callable<Boolean> $r1, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;)", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Continuation<Void, Task<Void>> $r2) throws  {
        return continueWhile($r1, $r2, IMMEDIATE_EXECUTOR, null);
    }

    public Task<Void> continueWhile(@Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Callable<Boolean> $r1, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Continuation<Void, Task<Void>> $r2, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) CancellationToken $r3) throws  {
        return continueWhile($r1, $r2, IMMEDIATE_EXECUTOR, $r3);
    }

    public Task<Void> continueWhile(@Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Callable<Boolean> $r1, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Continuation<Void, Task<Void>> $r2, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Executor $r3) throws  {
        return continueWhile($r1, $r2, $r3, null);
    }

    public Task<Void> continueWhile(@Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Callable<Boolean> $r1, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Continuation<Void, Task<Void>> $r2, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Executor $r3, @Signature({"(", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Boolean;", ">;", "Lbolts/Continuation", "<", "Ljava/lang/Void;", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) CancellationToken $r4) throws  {
        Capture $r5 = new Capture();
        final CancellationToken cancellationToken = $r4;
        final Callable<Boolean> callable = $r1;
        final Continuation<Void, Task<Void>> continuation = $r2;
        final Executor executor = $r3;
        final Capture capture = $r5;
        $r5.set(new Continuation<Void, Task<Void>>() {
            public Task<Void> then(@Signature({"(", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;)", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;"}) Task<Void> task) throws Exception {
                if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if (((Boolean) callable.call()).booleanValue()) {
                    return Task.forResult(null).onSuccessTask(continuation, executor).onSuccessTask((Continuation) capture.get(), executor);
                }
                return Task.forResult(null);
            }
        });
        return makeVoid().continueWithTask((Continuation) $r5.get(), $r3);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, TContinuationResult> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2) throws  {
        return continueWith($r1, $r2, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, TContinuationResult> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) CancellationToken $r3) throws  {
        TaskCompletionSource $r6 = create();
        synchronized (this.lock) {
            boolean $z0 = isCompleted();
            if (!$z0) {
                final TaskCompletionSource taskCompletionSource = $r6;
                final Continuation<TResult, TContinuationResult> continuation = $r1;
                final Executor executor = $r2;
                final CancellationToken cancellationToken = $r3;
                this.continuations.add(new Continuation<TResult, Void>() {
                    public Void then(@Signature({"(", "Lbolts/Task", "<TTResult;>;)", "Ljava/lang/Void;"}) Task<TResult> $r1) throws  {
                        Task.completeImmediately(taskCompletionSource, continuation, $r1, executor, cancellationToken);
                        return null;
                    }
                });
            }
        }
        if ($z0) {
            completeImmediately($r6, $r1, this, $r2, $r3);
        }
        return $r6.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;)", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, TContinuationResult> $r1) throws  {
        return continueWith($r1, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, TContinuationResult> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) CancellationToken $r2) throws  {
        return continueWith($r1, IMMEDIATE_EXECUTOR, $r2);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, Task<TContinuationResult>> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2) throws  {
        return continueWithTask($r1, $r2, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, Task<TContinuationResult>> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) CancellationToken $r3) throws  {
        TaskCompletionSource $r6 = create();
        synchronized (this.lock) {
            boolean $z0 = isCompleted();
            if (!$z0) {
                final TaskCompletionSource taskCompletionSource = $r6;
                final Continuation<TResult, Task<TContinuationResult>> continuation = $r1;
                final Executor executor = $r2;
                final CancellationToken cancellationToken = $r3;
                this.continuations.add(new Continuation<TResult, Void>() {
                    public Void then(@Signature({"(", "Lbolts/Task", "<TTResult;>;)", "Ljava/lang/Void;"}) Task<TResult> $r1) throws  {
                        Task.completeAfterTask(taskCompletionSource, continuation, $r1, executor, cancellationToken);
                        return null;
                    }
                });
            }
        }
        if ($z0) {
            completeAfterTask($r6, $r1, this, $r2, $r3);
        }
        return $r6.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;)", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, Task<TContinuationResult>> $r1) throws  {
        return continueWithTask($r1, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, Task<TContinuationResult>> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) CancellationToken $r2) throws  {
        return continueWithTask($r1, IMMEDIATE_EXECUTOR, $r2);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, TContinuationResult> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2) throws  {
        return onSuccess($r1, $r2, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) final Continuation<TResult, TContinuationResult> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) final CancellationToken $r3) throws  {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            public Task<TContinuationResult> then(@Signature({"(", "Lbolts/Task", "<TTResult;>;)", "Lbolts/Task", "<TTContinuationResult;>;"}) Task<TResult> $r1) throws  {
                if ($r3 != null && $r3.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if ($r1.isFaulted()) {
                    return Task.forError($r1.getError());
                }
                if ($r1.isCancelled()) {
                    return Task.cancelled();
                }
                return $r1.continueWith($r1);
            }
        }, $r2);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;)", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, TContinuationResult> $r1) throws  {
        return onSuccess($r1, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, TContinuationResult> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) CancellationToken $r2) throws  {
        return onSuccess($r1, IMMEDIATE_EXECUTOR, $r2);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, Task<TContinuationResult>> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2) throws  {
        return onSuccessTask($r1, $r2, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) final Continuation<TResult, Task<TContinuationResult>> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Executor $r2, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) final CancellationToken $r3) throws  {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            public Task<TContinuationResult> then(@Signature({"(", "Lbolts/Task", "<TTResult;>;)", "Lbolts/Task", "<TTContinuationResult;>;"}) Task<TResult> $r1) throws  {
                if ($r3 != null && $r3.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if ($r1.isFaulted()) {
                    return Task.forError($r1.getError());
                }
                if ($r1.isCancelled()) {
                    return Task.cancelled();
                }
                return $r1.continueWithTask($r1);
            }
        }, $r2);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;)", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, Task<TContinuationResult>> $r1) throws  {
        return onSuccessTask((Continuation) $r1, IMMEDIATE_EXECUTOR);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) Continuation<TResult, Task<TContinuationResult>> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", ">(", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/CancellationToken;", ")", "Lbolts/Task", "<TTContinuationResult;>;"}) CancellationToken $r2) throws  {
        return onSuccessTask($r1, IMMEDIATE_EXECUTOR, $r2);
    }

    private static <TContinuationResult, TResult> void completeImmediately(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final TaskCompletionSource $r0, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final Continuation<TResult, TContinuationResult> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final Task<TResult> $r2, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) Executor $r3, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;TTContinuationResult;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final CancellationToken $r4) throws  {
        $r3.execute(new Runnable() {
            public void run() throws  {
                if ($r4 == null || !$r4.isCancellationRequested()) {
                    try {
                        $r0.setResult($r1.then($r2));
                        return;
                    } catch (CancellationException e) {
                        $r0.setCancelled();
                        return;
                    } catch (Exception $r7) {
                        $r0.setError($r7);
                        return;
                    }
                }
                $r0.setCancelled();
            }
        });
    }

    private static <TContinuationResult, TResult> void completeAfterTask(@Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final TaskCompletionSource $r0, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final Continuation<TResult, Task<TContinuationResult>> $r1, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final Task<TResult> $r2, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) Executor $r3, @Signature({"<TContinuationResult:", "Ljava/lang/Object;", "TResult:", "Ljava/lang/Object;", ">(", "Lbolts/Task", "<TTContinuationResult;>.TaskCompletionSource;", "Lbolts/Continuation", "<TTResult;", "Lbolts/Task", "<TTContinuationResult;>;>;", "Lbolts/Task", "<TTResult;>;", "Ljava/util/concurrent/Executor;", "Lbolts/CancellationToken;", ")V"}) final CancellationToken $r4) throws  {
        $r3.execute(new Runnable() {

            class C02921 implements Continuation<TContinuationResult, Void> {
                C02921() throws  {
                }

                public Void then(@Signature({"(", "Lbolts/Task", "<TTContinuationResult;>;)", "Ljava/lang/Void;"}) Task<TContinuationResult> $r1) throws  {
                    if ($r4 != null && $r4.isCancellationRequested()) {
                        $r0.setCancelled();
                        return null;
                    } else if ($r1.isCancelled()) {
                        $r0.setCancelled();
                        return null;
                    } else if ($r1.isFaulted()) {
                        $r0.setError($r1.getError());
                        return null;
                    } else {
                        $r0.setResult($r1.getResult());
                        return null;
                    }
                }
            }

            public void run() throws  {
                if ($r4 == null || !$r4.isCancellationRequested()) {
                    try {
                        try {
                            Task $r4 = (Task) $r1.then($r2);
                            if ($r4 == null) {
                                $r0.setResult(null);
                                return;
                            } else {
                                $r4.continueWith(new C02921());
                                return;
                            }
                        } catch (CancellationException e) {
                            $r0.setCancelled();
                            return;
                        }
                    } catch (Exception $r8) {
                        $r0.setError($r8);
                        return;
                    }
                }
                $r0.setCancelled();
            }
        });
    }

    private void runContinuations() throws  {
        synchronized (this.lock) {
            for (Continuation $r5 : this.continuations) {
                try {
                    $r5.then(this);
                } catch (RuntimeException $r6) {
                    throw $r6;
                } catch (Exception $r8) {
                    throw new RuntimeException($r8);
                }
            }
            this.continuations = null;
        }
    }
}
