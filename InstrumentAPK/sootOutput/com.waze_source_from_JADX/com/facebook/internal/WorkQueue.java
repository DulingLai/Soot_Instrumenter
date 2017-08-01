package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.concurrent.Executor;

public class WorkQueue {
    static final /* synthetic */ boolean $assertionsDisabled = (!WorkQueue.class.desiredAssertionStatus());
    public static final int DEFAULT_MAX_CONCURRENT = 8;
    private final Executor executor;
    private final int maxConcurrent;
    private WorkNode pendingJobs;
    private int runningCount;
    private WorkNode runningJobs;
    private final Object workLock;

    public interface WorkItem {
        boolean cancel() throws ;

        boolean isRunning() throws ;

        void moveToFront() throws ;
    }

    private class WorkNode implements WorkItem {
        static final /* synthetic */ boolean $assertionsDisabled = (!WorkQueue.class.desiredAssertionStatus());
        private final Runnable callback;
        private boolean isRunning;
        private WorkNode next;
        private WorkNode prev;

        WorkNode(Runnable $r2) throws  {
            this.callback = $r2;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean cancel() throws  {
            /*
            r7 = this;
            r0 = com.facebook.internal.WorkQueue.this;
            r1 = r0.workLock;
            monitor-enter(r1);
            r2 = r7.isRunning();	 Catch:{ Throwable -> 0x0022 }
            if (r2 != 0) goto L_0x001f;
        L_0x000d:
            r0 = com.facebook.internal.WorkQueue.this;	 Catch:{ Throwable -> 0x0022 }
            r3 = com.facebook.internal.WorkQueue.this;	 Catch:{ Throwable -> 0x0022 }
            r4 = r3.pendingJobs;	 Catch:{ Throwable -> 0x0022 }
            r7 = r7.removeFromList(r4);	 Catch:{ Throwable -> 0x0022 }
            r0.pendingJobs = r7;	 Catch:{ Throwable -> 0x0022 }
            monitor-exit(r1);	 Catch:{ Throwable -> 0x0022 }
            r5 = 1;
            return r5;
        L_0x001f:
            monitor-exit(r1);	 Catch:{ Throwable -> 0x0022 }
            r5 = 0;
            return r5;
        L_0x0022:
            r6 = move-exception;
            monitor-exit(r1);	 Catch:{ Throwable -> 0x0022 }
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.WorkQueue.WorkNode.cancel():boolean");
        }

        public void moveToFront() throws  {
            synchronized (WorkQueue.this.workLock) {
                if (!isRunning()) {
                    WorkQueue.this.pendingJobs = removeFromList(WorkQueue.this.pendingJobs);
                    WorkQueue.this.pendingJobs = addToList(WorkQueue.this.pendingJobs, true);
                }
            }
        }

        public boolean isRunning() throws  {
            return this.isRunning;
        }

        Runnable getCallback() throws  {
            return this.callback;
        }

        WorkNode getNext() throws  {
            return this.next;
        }

        void setIsRunning(boolean $z0) throws  {
            this.isRunning = $z0;
        }

        WorkNode addToList(WorkNode $r2, boolean $z0) throws  {
            if (!$assertionsDisabled && this.next != null) {
                throw new AssertionError();
            } else if ($assertionsDisabled || this.prev == null) {
                if ($r2 == null) {
                    this.prev = this;
                    this.next = this;
                    $r2 = this;
                } else {
                    this.next = $r2;
                    this.prev = $r2.prev;
                    WorkNode $r4 = this.next;
                    this.prev.next = this;
                    $r4.prev = this;
                }
                if ($z0) {
                    return this;
                }
                return $r2;
            } else {
                throw new AssertionError();
            }
        }

        WorkNode removeFromList(WorkNode $r1) throws  {
            if (!$assertionsDisabled && this.next == null) {
                throw new AssertionError();
            } else if ($assertionsDisabled || this.prev != null) {
                if ($r1 == this) {
                    if (this.next == this) {
                        $r1 = null;
                    } else {
                        $r1 = this.next;
                    }
                }
                this.next.prev = this.prev;
                this.prev.next = this.next;
                this.prev = null;
                this.next = null;
                return $r1;
            } else {
                throw new AssertionError();
            }
        }

        void verify(boolean $z0) throws  {
            if (!$assertionsDisabled && this.prev.next != this) {
                throw new AssertionError();
            } else if (!$assertionsDisabled && this.next.prev != this) {
                throw new AssertionError();
            } else if (!$assertionsDisabled && isRunning() != $z0) {
                throw new AssertionError();
            }
        }
    }

    public WorkQueue() throws  {
        this(8);
    }

    public WorkQueue(int $i0) throws  {
        this($i0, FacebookSdk.getExecutor());
    }

    public WorkQueue(int $i0, Executor $r1) throws  {
        this.workLock = new Object();
        this.runningJobs = null;
        this.runningCount = 0;
        this.maxConcurrent = $i0;
        this.executor = $r1;
    }

    public WorkItem addActiveWorkItem(Runnable $r1) throws  {
        return addActiveWorkItem($r1, true);
    }

    public WorkItem addActiveWorkItem(Runnable $r1, boolean $z0) throws  {
        WorkNode $r2 = new WorkNode($r1);
        synchronized (this.workLock) {
            this.pendingJobs = $r2.addToList(this.pendingJobs, $z0);
        }
        startItem();
        return $r2;
    }

    public void validate() throws  {
        synchronized (this.workLock) {
            int $i0 = 0;
            if (this.runningJobs != null) {
                WorkNode $r3 = this.runningJobs;
                WorkNode $r2;
                do {
                    $r3.verify(true);
                    $i0++;
                    $r2 = $r3.getNext();
                    $r3 = $r2;
                } while ($r2 != this.runningJobs);
            }
            if ($assertionsDisabled || this.runningCount == $i0) {
            } else {
                throw new AssertionError();
            }
        }
    }

    private void startItem() throws  {
        finishItemAndStartNew(null);
    }

    private void finishItemAndStartNew(WorkNode $r1) throws  {
        WorkNode $r3 = null;
        synchronized (this.workLock) {
            if ($r1 != null) {
                this.runningJobs = $r1.removeFromList(this.runningJobs);
                this.runningCount--;
            }
            if (this.runningCount < this.maxConcurrent) {
                $r3 = this.pendingJobs;
                if ($r3 != null) {
                    this.pendingJobs = $r3.removeFromList(this.pendingJobs);
                    this.runningJobs = $r3.addToList(this.runningJobs, false);
                    this.runningCount++;
                    $r3.setIsRunning(true);
                }
            }
        }
        if ($r3 != null) {
            execute($r3);
        }
    }

    private void execute(final WorkNode $r1) throws  {
        this.executor.execute(new Runnable() {
            public void run() throws  {
                try {
                    $r1.getCallback().run();
                } finally {
                    WorkQueue.this.finishItemAndStartNew($r1);
                }
            }
        });
    }
}
