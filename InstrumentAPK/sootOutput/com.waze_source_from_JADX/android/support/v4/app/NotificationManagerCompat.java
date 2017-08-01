package android.support.v4.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.support.v4.app.INotificationSideChannel.Stub;
import android.util.Log;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    private static final Impl IMPL;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final int SIDE_CHANNEL_BIND_FLAGS = IMPL.getSideChannelBindFlags();
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    private static Set<String> sEnabledNotificationListenerPackages = new HashSet();
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock = new Object();
    private static final Object sLock = new Object();
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager = ((NotificationManager) this.mContext.getSystemService("notification"));

    private interface Task {
        void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException;
    }

    private static class CancelTask implements Task {
        final boolean all;
        final int id;
        final String packageName;
        final String tag;

        public CancelTask(String $r1) throws  {
            this.packageName = $r1;
            this.id = 0;
            this.tag = null;
            this.all = true;
        }

        public CancelTask(String $r1, int $i0, String $r2) throws  {
            this.packageName = $r1;
            this.id = $i0;
            this.tag = $r2;
            this.all = false;
        }

        public void send(INotificationSideChannel $r1) throws RemoteException {
            if (this.all) {
                $r1.cancelAll(this.packageName);
            } else {
                $r1.cancel(this.packageName, this.id, this.tag);
            }
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder("CancelTask[");
            $r1.append("packageName:").append(this.packageName);
            $r1.append(", id:").append(this.id);
            $r1.append(", tag:").append(this.tag);
            $r1.append(", all:").append(this.all);
            $r1.append("]");
            return $r1.toString();
        }
    }

    interface Impl {
        void cancelNotification(NotificationManager notificationManager, String str, int i) throws ;

        int getSideChannelBindFlags() throws ;

        void postNotification(NotificationManager notificationManager, String str, int i, Notification notification) throws ;
    }

    static class ImplBase implements Impl {
        public int getSideChannelBindFlags() throws  {
            return 1;
        }

        ImplBase() throws  {
        }

        public void cancelNotification(NotificationManager $r1, String tag, int $i0) throws  {
            $r1.cancel($i0);
        }

        public void postNotification(NotificationManager $r1, String tag, int $i0, Notification $r3) throws  {
            $r1.notify($i0, $r3);
        }
    }

    static class ImplEclair extends ImplBase {
        ImplEclair() throws  {
        }

        public void cancelNotification(NotificationManager $r1, String $r2, int $i0) throws  {
            NotificationManagerCompatEclair.cancelNotification($r1, $r2, $i0);
        }

        public void postNotification(NotificationManager $r1, String $r2, int $i0, Notification $r3) throws  {
            NotificationManagerCompatEclair.postNotification($r1, $r2, $i0, $r3);
        }
    }

    static class ImplIceCreamSandwich extends ImplEclair {
        public int getSideChannelBindFlags() throws  {
            return 33;
        }

        ImplIceCreamSandwich() throws  {
        }
    }

    private static class NotifyTask implements Task {
        final int id;
        final Notification notif;
        final String packageName;
        final String tag;

        public NotifyTask(String $r1, int $i0, String $r2, Notification $r3) throws  {
            this.packageName = $r1;
            this.id = $i0;
            this.tag = $r2;
            this.notif = $r3;
        }

        public void send(INotificationSideChannel $r1) throws RemoteException {
            $r1.notify(this.packageName, this.id, this.tag, this.notif);
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder("NotifyTask[");
            $r1.append("packageName:").append(this.packageName);
            $r1.append(", id:").append(this.id);
            $r1.append(", tag:").append(this.tag);
            $r1.append("]");
            return $r1.toString();
        }
    }

    private static class ServiceConnectedEvent {
        final ComponentName componentName;
        final IBinder iBinder;

        public ServiceConnectedEvent(ComponentName $r1, IBinder $r2) throws  {
            this.componentName = $r1;
            this.iBinder = $r2;
        }
    }

    private static class SideChannelManager implements ServiceConnection, Callback {
        private static final String KEY_BINDER = "binder";
        private static final int MSG_QUEUE_TASK = 0;
        private static final int MSG_RETRY_LISTENER_QUEUE = 3;
        private static final int MSG_SERVICE_CONNECTED = 1;
        private static final int MSG_SERVICE_DISCONNECTED = 2;
        private Set<String> mCachedEnabledPackages = new HashSet();
        private final Context mContext;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap();

        private static class ListenerRecord {
            public boolean bound = false;
            public final ComponentName componentName;
            public int retryCount = 0;
            public INotificationSideChannel service;
            public LinkedList<Task> taskQueue = new LinkedList();

            public ListenerRecord(ComponentName $r1) throws  {
                this.componentName = $r1;
            }
        }

        public SideChannelManager(Context $r1) throws  {
            this.mContext = $r1;
            this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper(), this);
        }

        public void queueTask(Task $r1) throws  {
            this.mHandler.obtainMessage(0, $r1).sendToTarget();
        }

        public boolean handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 0:
                    handleQueueTask((Task) $r1.obj);
                    return true;
                case 1:
                    ServiceConnectedEvent $r5 = (ServiceConnectedEvent) $r1.obj;
                    handleServiceConnected($r5.componentName, $r5.iBinder);
                    return true;
                case 2:
                    handleServiceDisconnected((ComponentName) $r1.obj);
                    return true;
                case 3:
                    handleRetryListenerQueue((ComponentName) $r1.obj);
                    return true;
                default:
                    return false;
            }
        }

        private void handleQueueTask(Task $r1) throws  {
            updateListenerMap();
            for (ListenerRecord $r6 : this.mRecordMap.values()) {
                $r6.taskQueue.add($r1);
                processListenerQueue($r6);
            }
        }

        private void handleServiceConnected(ComponentName $r1, IBinder $r2) throws  {
            ListenerRecord $r5 = (ListenerRecord) this.mRecordMap.get($r1);
            if ($r5 != null) {
                $r5.service = Stub.asInterface($r2);
                $r5.retryCount = 0;
                processListenerQueue($r5);
            }
        }

        private void handleServiceDisconnected(ComponentName $r1) throws  {
            ListenerRecord $r4 = (ListenerRecord) this.mRecordMap.get($r1);
            if ($r4 != null) {
                ensureServiceUnbound($r4);
            }
        }

        private void handleRetryListenerQueue(ComponentName $r1) throws  {
            ListenerRecord $r4 = (ListenerRecord) this.mRecordMap.get($r1);
            if ($r4 != null) {
                processListenerQueue($r4);
            }
        }

        public void onServiceConnected(ComponentName $r1, IBinder $r2) throws  {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                Log.d(NotificationManagerCompat.TAG, "Connected to service " + $r1);
            }
            this.mHandler.obtainMessage(1, new ServiceConnectedEvent($r1, $r2)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName $r1) throws  {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                Log.d(NotificationManagerCompat.TAG, "Disconnected from service " + $r1);
            }
            this.mHandler.obtainMessage(2, $r1).sendToTarget();
        }

        private void updateListenerMap() throws  {
            Set $r3 = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
            if (!$r3.equals(this.mCachedEnabledPackages)) {
                Map map;
                this.mCachedEnabledPackages = $r3;
                List<ResolveInfo> $r7 = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 4);
                HashSet<ComponentName> $r1 = new HashSet();
                for (ResolveInfo resolveInfo : $r7) {
                    String $r12 = resolveInfo.serviceInfo;
                    String $r11 = $r12;
                    $r12 = $r12.packageName;
                    if ($r3.contains($r12)) {
                        $r12 = resolveInfo.serviceInfo;
                        $r11 = $r12;
                        String $r122 = $r12.packageName;
                        $r12 = resolveInfo.serviceInfo;
                        $r11 = $r12;
                        ComponentName componentName = new ComponentName($r122, $r12.name);
                        $r12 = resolveInfo.serviceInfo;
                        $r11 = $r12;
                        if ($r12.permission != null) {
                            Log.w(NotificationManagerCompat.TAG, "Permission present on component " + componentName + ", not adding listener record.");
                        } else {
                            $r1.add(componentName);
                        }
                    }
                }
                for (ComponentName $r13 : $r1) {
                    map = this.mRecordMap;
                    Map $r16 = map;
                    if (!map.containsKey($r13)) {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            Log.d(NotificationManagerCompat.TAG, "Adding listener record for " + $r13);
                        }
                        this.mRecordMap.put($r13, new ListenerRecord($r13));
                    }
                }
                map = this.mRecordMap;
                Iterator $r8 = map.entrySet().iterator();
                while ($r8.hasNext()) {
                    Entry $r18 = (Entry) $r8.next();
                    if (!$r1.contains($r18.getKey())) {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            Log.d(NotificationManagerCompat.TAG, "Removing listener record for " + $r18.getKey());
                        }
                        ensureServiceUnbound((ListenerRecord) $r18.getValue());
                        $r8.remove();
                    }
                }
            }
        }

        private boolean ensureServiceBound(ListenerRecord $r1) throws  {
            if ($r1.bound) {
                return true;
            }
            $r1.bound = this.mContext.bindService(new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent($r1.componentName), this, NotificationManagerCompat.SIDE_CHANNEL_BIND_FLAGS);
            if ($r1.bound) {
                $r1.retryCount = 0;
            } else {
                Log.w(NotificationManagerCompat.TAG, "Unable to bind to listener " + $r1.componentName);
                this.mContext.unbindService(this);
            }
            return $r1.bound;
        }

        private void ensureServiceUnbound(ListenerRecord $r1) throws  {
            if ($r1.bound) {
                this.mContext.unbindService(this);
                $r1.bound = false;
            }
            $r1.service = null;
        }

        private void scheduleListenerRetry(ListenerRecord $r1) throws  {
            if (!this.mHandler.hasMessages(3, $r1.componentName)) {
                $r1.retryCount++;
                if ($r1.retryCount > 6) {
                    Log.w(NotificationManagerCompat.TAG, "Giving up on delivering " + $r1.taskQueue.size() + " tasks to " + $r1.componentName + " after " + $r1.retryCount + " retries");
                    $r1.taskQueue.clear();
                    return;
                }
                int $i0 = (1 << ($r1.retryCount - 1)) * 1000;
                if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                    Log.d(NotificationManagerCompat.TAG, "Scheduling retry for " + $i0 + " ms");
                }
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, $r1.componentName), (long) $i0);
            }
        }

        private void processListenerQueue(ListenerRecord $r1) throws  {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                Log.d(NotificationManagerCompat.TAG, "Processing component " + $r1.componentName + ", " + $r1.taskQueue.size() + " queued tasks");
            }
            if (!$r1.taskQueue.isEmpty()) {
                if (!ensureServiceBound($r1) || $r1.service == null) {
                    scheduleListenerRetry($r1);
                    return;
                }
                while (true) {
                    Task $r8 = (Task) $r1.taskQueue.peek();
                    if ($r8 == null) {
                        break;
                    }
                    try {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            Log.d(NotificationManagerCompat.TAG, "Sending task " + $r8);
                        }
                        $r8.send($r1.service);
                        $r1.taskQueue.remove();
                    } catch (DeadObjectException e) {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            Log.d(NotificationManagerCompat.TAG, "Remote service has died: " + $r1.componentName);
                        }
                    } catch (RemoteException $r10) {
                        Log.w(NotificationManagerCompat.TAG, "RemoteException communicating with " + $r1.componentName, $r10);
                    }
                }
                if (!$r1.taskQueue.isEmpty()) {
                    scheduleListenerRetry($r1);
                }
            }
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new ImplIceCreamSandwich();
        } else if (VERSION.SDK_INT >= 5) {
            IMPL = new ImplEclair();
        } else {
            IMPL = new ImplBase();
        }
    }

    public static NotificationManagerCompat from(Context $r0) throws  {
        return new NotificationManagerCompat($r0);
    }

    private NotificationManagerCompat(Context $r1) throws  {
        this.mContext = $r1;
    }

    public void cancel(int $i0) throws  {
        cancel(null, $i0);
    }

    public void cancel(String $r1, int $i0) throws  {
        IMPL.cancelNotification(this.mNotificationManager, $r1, $i0);
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), $i0, $r1));
        }
    }

    public void cancelAll() throws  {
        this.mNotificationManager.cancelAll();
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
        }
    }

    public void notify(int $i0, Notification $r1) throws  {
        notify(null, $i0, $r1);
    }

    public void notify(String $r1, int $i0, Notification $r2) throws  {
        if (useSideChannelForNotification($r2)) {
            pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), $i0, $r1, $r2));
            IMPL.cancelNotification(this.mNotificationManager, $r1, $i0);
            return;
        }
        IMPL.postNotification(this.mNotificationManager, $r1, $i0, $r2);
    }

    public static Set<String> getEnabledListenerPackages(@Signature({"(", "Landroid/content/Context;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) Context $r0) throws  {
        String $r4 = Secure.getString($r0.getContentResolver(), SETTING_ENABLED_NOTIFICATION_LISTENERS);
        if (!($r4 == null || $r4.equals(sEnabledNotificationListeners))) {
            String[] $r5 = $r4.split(":");
            HashSet $r2 = new HashSet($r5.length);
            for (String $r1 : $r5) {
                ComponentName $r6 = ComponentName.unflattenFromString($r1);
                if ($r6 != null) {
                    $r2.add($r6.getPackageName());
                }
            }
            synchronized (sEnabledNotificationListenersLock) {
                sEnabledNotificationListenerPackages = $r2;
                sEnabledNotificationListeners = $r4;
            }
        }
        return sEnabledNotificationListenerPackages;
    }

    private static boolean useSideChannelForNotification(Notification $r0) throws  {
        Bundle $r1 = NotificationCompat.getExtras($r0);
        return $r1 != null && $r1.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    private void pushSideChannelQueue(Task $r1) throws  {
        synchronized (sLock) {
            if (sSideChannelManager == null) {
                sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
            }
        }
        sSideChannelManager.queueTask($r1);
    }
}
