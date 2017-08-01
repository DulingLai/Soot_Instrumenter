package com.waze.ifs.async;

import android.app.Activity;
import android.view.View;
import java.util.concurrent.Executor;

public abstract class RunnableCallback implements Runnable {
    private final Executor mExecutor;

    class C16963 implements Runnable {
        C16963() throws  {
        }

        public void run() throws  {
            RunnableCallback.this.callback();
        }
    }

    public abstract void callback() throws ;

    public abstract void event() throws ;

    public RunnableCallback(Executor $r1) throws  {
        this.mExecutor = $r1;
    }

    public RunnableCallback(final Activity $r1) throws  {
        if ($r1 == null) {
            this.mExecutor = null;
        } else {
            this.mExecutor = new Executor() {
                public void execute(Runnable $r1) throws  {
                    $r1.runOnUiThread($r1);
                }
            };
        }
    }

    public RunnableCallback(final View $r1) throws  {
        if ($r1 == null) {
            this.mExecutor = null;
        } else {
            this.mExecutor = new Executor() {
                public void execute(Runnable $r1) throws  {
                    $r1.post($r1);
                }
            };
        }
    }

    public void run() throws  {
        event();
        if (this.mExecutor != null) {
            this.mExecutor.execute(new C16963());
        }
    }
}
