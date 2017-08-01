package com.android.volley.toolbox;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import dalvik.annotation.Signature;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestFuture<T> implements Future<T>, Listener<T>, ErrorListener {
    private VolleyError mException;
    private Request<?> mRequest;
    private T mResult;
    private boolean mResultReceived = false;

    public static <E> RequestFuture<E> newFuture() throws  {
        return new RequestFuture();
    }

    private RequestFuture() throws  {
    }

    public void setRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;)V"}) Request<?> $r1) throws  {
        this.mRequest = $r1;
    }

    public synchronized boolean cancel(boolean mayInterruptIfRunning) throws  {
        boolean $z1 = false;
        synchronized (this) {
            if (this.mRequest != null) {
                if (!isDone()) {
                    this.mRequest.cancel();
                    $z1 = true;
                }
            }
        }
        return $z1;
    }

    public T get() throws InterruptedException, ExecutionException {
        try {
            return doGet(null);
        } catch (TimeoutException $r1) {
            throw new AssertionError($r1);
        }
    }

    public T get(@Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TT;"}) long $l0, @Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TT;"}) TimeUnit $r1) throws InterruptedException, ExecutionException, TimeoutException {
        return doGet(Long.valueOf(TimeUnit.MILLISECONDS.convert($l0, $r1)));
    }

    private synchronized T doGet(@Signature({"(", "Ljava/lang/Long;", ")TT;"}) Long $r1) throws InterruptedException, ExecutionException, TimeoutException {
        Object $r5;
        if (this.mException != null) {
            throw new ExecutionException(this.mException);
        } else if (this.mResultReceived) {
            $r5 = this.mResult;
        } else {
            if ($r1 == null) {
                wait(0);
            } else if ($r1.longValue() > 0) {
                wait($r1.longValue());
            }
            if (this.mException != null) {
                throw new ExecutionException(this.mException);
            } else if (this.mResultReceived) {
                $r5 = this.mResult;
            } else {
                throw new TimeoutException();
            }
        }
        return $r5;
    }

    public boolean isCancelled() throws  {
        return this.mRequest == null ? false : this.mRequest.isCanceled();
    }

    public synchronized boolean isDone() throws  {
        boolean $z0;
        $z0 = this.mResultReceived || this.mException != null || isCancelled();
        return $z0;
    }

    public synchronized void onResponse(@Signature({"(TT;)V"}) T $r1) throws  {
        this.mResultReceived = true;
        this.mResult = $r1;
        notifyAll();
    }

    public synchronized void onErrorResponse(VolleyError $r1) throws  {
        this.mException = $r1;
        notifyAll();
    }
}
