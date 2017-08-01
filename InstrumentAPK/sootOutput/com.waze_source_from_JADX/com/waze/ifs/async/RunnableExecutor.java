package com.waze.ifs.async;

import android.app.Activity;
import android.view.View;
import java.util.concurrent.Executor;

public abstract class RunnableExecutor implements Runnable {
    private Executor mExecutor = null;

    class C16993 implements Runnable {
        C16993() throws  {
        }

        public void run() throws  {
            RunnableExecutor.this.event();
        }
    }

    public abstract void event() throws ;

    public RunnableExecutor(Executor $r1) throws  {
        this.mExecutor = $r1;
    }

    public RunnableExecutor(final Activity $r1) throws  {
        if ($r1 != null) {
            this.mExecutor = new Executor() {
                public void execute(Runnable $r1) throws  {
                    $r1.runOnUiThread($r1);
                }
            };
        }
    }

    public RunnableExecutor(final View $r1) throws  {
        if ($r1 != null) {
            this.mExecutor = new Executor() {
                public void execute(Runnable $r1) throws  {
                    $r1.post($r1);
                }
            };
        }
    }

    public void run() throws  {
        if (this.mExecutor != null) {
            this.mExecutor.execute(new C16993());
            return;
        }
        event();
    }
}
