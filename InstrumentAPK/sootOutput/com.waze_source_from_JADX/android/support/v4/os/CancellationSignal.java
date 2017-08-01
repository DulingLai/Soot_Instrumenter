package android.support.v4.os;

import android.os.Build.VERSION;

public final class CancellationSignal {
    private boolean mCancelInProgress;
    private Object mCancellationSignalObj;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    public interface OnCancelListener {
        void onCancel() throws ;
    }

    public boolean isCanceled() throws  {
        boolean z0;
        synchronized (this) {
            z0 = this.mIsCanceled;
        }
        return z0;
    }

    public void throwIfCanceled() throws  {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() throws  {
        /*
        r8 = this;
        monitor-enter(r8);
        r0 = r8.mIsCanceled;	 Catch:{ Throwable -> 0x0028 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r8);	 Catch:{ Throwable -> 0x0028 }
        return;
    L_0x0007:
        r1 = 1;
        r8.mIsCanceled = r1;	 Catch:{ Throwable -> 0x0028 }
        r1 = 1;
        r8.mCancelInProgress = r1;	 Catch:{ Throwable -> 0x0028 }
        r2 = r8.mOnCancelListener;	 Catch:{ Throwable -> 0x0028 }
        r3 = r8.mCancellationSignalObj;	 Catch:{ Throwable -> 0x0028 }
        monitor-exit(r8);	 Catch:{ Throwable -> 0x0028 }
        if (r2 == 0) goto L_0x0017;
    L_0x0014:
        r2.onCancel();	 Catch:{ Throwable -> 0x002b }
    L_0x0017:
        if (r3 == 0) goto L_0x001c;
    L_0x0019:
        android.support.v4.os.CancellationSignalCompatJellybean.cancel(r3);	 Catch:{ Throwable -> 0x002b }
    L_0x001c:
        monitor-enter(r8);
        r1 = 0;
        r8.mCancelInProgress = r1;	 Catch:{ Throwable -> 0x0025 }
        r8.notifyAll();	 Catch:{ Throwable -> 0x0025 }
        monitor-exit(r8);	 Catch:{ Throwable -> 0x0025 }
        return;
    L_0x0025:
        r4 = move-exception;
        monitor-exit(r8);	 Catch:{ Throwable -> 0x0025 }
        throw r4;
    L_0x0028:
        r5 = move-exception;
        monitor-exit(r8);	 Catch:{ Throwable -> 0x0028 }
        throw r5;
    L_0x002b:
        r6 = move-exception;
        monitor-enter(r8);
        r1 = 0;
        r8.mCancelInProgress = r1;	 Catch:{ Throwable -> 0x0035 }
        r8.notifyAll();	 Catch:{ Throwable -> 0x0035 }
        monitor-exit(r8);	 Catch:{ Throwable -> 0x0035 }
        throw r6;
    L_0x0035:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ Throwable -> 0x0035 }
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.os.CancellationSignal.cancel():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setOnCancelListener(android.support.v4.os.CancellationSignal.OnCancelListener r4) throws  {
        /*
        r3 = this;
        monitor-enter(r3);
        r3.waitForCancelFinishedLocked();	 Catch:{ Throwable -> 0x0014 }
        r0 = r3.mOnCancelListener;	 Catch:{ Throwable -> 0x0014 }
        if (r0 != r4) goto L_0x000a;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ Throwable -> 0x0014 }
        return;
    L_0x000a:
        r3.mOnCancelListener = r4;	 Catch:{ Throwable -> 0x0014 }
        r1 = r3.mIsCanceled;	 Catch:{ Throwable -> 0x0014 }
        if (r1 == 0) goto L_0x0012;
    L_0x0010:
        if (r4 != 0) goto L_0x0017;
    L_0x0012:
        monitor-exit(r3);	 Catch:{ Throwable -> 0x0014 }
        return;
    L_0x0014:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ Throwable -> 0x0014 }
        throw r2;
    L_0x0017:
        monitor-exit(r3);	 Catch:{ Throwable -> 0x0014 }
        r4.onCancel();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.os.CancellationSignal.setOnCancelListener(android.support.v4.os.CancellationSignal$OnCancelListener):void");
    }

    public Object getCancellationSignalObject() throws  {
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        Object $r1;
        synchronized (this) {
            if (this.mCancellationSignalObj == null) {
                this.mCancellationSignalObj = CancellationSignalCompatJellybean.create();
                if (this.mIsCanceled) {
                    CancellationSignalCompatJellybean.cancel(this.mCancellationSignalObj);
                }
            }
            $r1 = this.mCancellationSignalObj;
        }
        return $r1;
    }

    private void waitForCancelFinishedLocked() throws  {
        while (this.mCancelInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
}
