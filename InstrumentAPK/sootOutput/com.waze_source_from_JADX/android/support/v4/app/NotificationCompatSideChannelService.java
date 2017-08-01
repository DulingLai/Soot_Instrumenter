package android.support.v4.app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel.Stub;

public abstract class NotificationCompatSideChannelService extends Service {

    private class NotificationSideChannelStub extends Stub {
        private NotificationSideChannelStub() throws  {
        }

        public void notify(String $r1, int $i0, String $r2, Notification $r3) throws RemoteException {
            NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), $r1);
            long $l2 = clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.notify($r1, $i0, $r2, $r3);
            } finally {
                restoreCallingIdentity($l2);
            }
        }

        public void cancel(String $r1, int $i0, String $r2) throws RemoteException {
            NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), $r1);
            long $l2 = clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.cancel($r1, $i0, $r2);
            } finally {
                restoreCallingIdentity($l2);
            }
        }

        public void cancelAll(String $r1) throws  {
            NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), $r1);
            long $l1 = clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.cancelAll($r1);
            } finally {
                restoreCallingIdentity($l1);
            }
        }
    }

    public abstract void cancel(String str, int i, String str2) throws ;

    public abstract void cancelAll(String str) throws ;

    public abstract void notify(String str, int i, String str2, Notification notification) throws ;

    public IBinder onBind(Intent $r1) throws  {
        if (!$r1.getAction().equals(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL)) {
            return null;
        }
        if (VERSION.SDK_INT > 19) {
            return null;
        }
        return new NotificationSideChannelStub();
    }

    private void checkPermission(int $i0, String $r1) throws  {
        String[] $r4 = getPackageManager().getPackagesForUid($i0);
        int $i1 = $r4.length;
        int $i2 = 0;
        while ($i2 < $i1) {
            if (!$r4[$i2].equals($r1)) {
                $i2++;
            } else {
                return;
            }
        }
        throw new SecurityException("NotificationSideChannelService: Uid " + $i0 + " is not authorized for package " + $r1);
    }
}
