package bolts;

import java.util.Locale;
import java.util.concurrent.CancellationException;

public class CancellationToken {
    private boolean cancellationRequested;
    private final Object lock = new Object();

    CancellationToken() throws  {
    }

    public boolean isCancellationRequested() throws  {
        boolean z0;
        synchronized (this.lock) {
            z0 = this.cancellationRequested;
        }
        return z0;
    }

    public void throwIfCancellationRequested() throws CancellationException {
        synchronized (this.lock) {
            if (this.cancellationRequested) {
                throw new CancellationException();
            }
        }
    }

    boolean tryCancel() throws  {
        synchronized (this.lock) {
            if (this.cancellationRequested) {
                return false;
            }
            this.cancellationRequested = true;
            return true;
        }
    }

    public String toString() throws  {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[]{getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(this.cancellationRequested)});
    }
}
