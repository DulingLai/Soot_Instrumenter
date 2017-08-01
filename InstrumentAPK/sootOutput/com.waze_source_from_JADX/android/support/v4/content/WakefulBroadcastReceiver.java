package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.util.SparseArray;

public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
    private static final SparseArray<WakeLock> mActiveWakeLocks = new SparseArray();
    private static int mNextId = 1;

    public static ComponentName startWakefulService(Context $r0, Intent $r1) throws  {
        synchronized (mActiveWakeLocks) {
            int $i0 = mNextId;
            mNextId++;
            if (mNextId <= 0) {
                mNextId = 1;
            }
            $r1.putExtra(EXTRA_WAKE_LOCK_ID, $i0);
            ComponentName $r3 = $r0.startService($r1);
            if ($r3 == null) {
                return null;
            }
            WakeLock $r8 = ((PowerManager) $r0.getSystemService("power")).newWakeLock(1, "wake:" + $r3.flattenToShortString());
            $r8.setReferenceCounted(false);
            $r8.acquire(60000);
            mActiveWakeLocks.put($i0, $r8);
            return $r3;
        }
    }

    public static boolean completeWakefulIntent(Intent $r0) throws  {
        int $i0 = $r0.getIntExtra(EXTRA_WAKE_LOCK_ID, 0);
        if ($i0 == 0) {
            return false;
        }
        synchronized (mActiveWakeLocks) {
            WakeLock $r4 = (WakeLock) mActiveWakeLocks.get($i0);
            if ($r4 != null) {
                $r4.release();
                mActiveWakeLocks.remove($i0);
                return true;
            }
            Log.w("WakefulBroadcastReceiver", "No active wake lock id #" + $i0);
            return true;
        }
    }
}
