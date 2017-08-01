package com.abaltatech.mcp.weblink.sdk;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.RemoteException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.weblink.servercore.WebLinkServerCore;
import com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationData;
import com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class WLNotificationManager {
    public static final int DEFAULT_TIMEOUT_MILLISECONDS = 8000;
    public static final int DEFAULT_TIMEOUT_SECONDS = 8;
    private static final int MAX_TIMEOUT = 20000;
    private static final int MIN_TIMEOUT = 3000;
    private static final String TAG = "WLNotificationManager";
    private static WLNotificationManager ms_instance;
    private boolean isTransformingQueues = false;
    private List<WLDisplayNotification> m_displayedNotifications = new ArrayList();
    private boolean m_isInitialized = false;
    private int m_maxNotificationsPerApp;
    private IWLDisplayNotificationManager m_notificationManager = null;
    private List<WLDisplayNotification> m_pendingQueue = new ArrayList();

    public interface INotification {
        void dismissNotification() throws ;

        void registerListener(INotificationEventListener iNotificationEventListener) throws ;

        void setImage(Bitmap bitmap) throws ;

        void setImageUrl(String str) throws ;

        void setNewTimeout(int i) throws ;

        void setShowProgress(boolean z) throws ;

        void setText(String str) throws ;

        void unregisterListener(INotificationEventListener iNotificationEventListener) throws ;
    }

    class C03601 implements ServiceConnection {
        C03601() throws  {
        }

        public void onServiceDisconnected(ComponentName name) throws  {
            synchronized (WLNotificationManager.this) {
                WLNotificationManager.this.m_notificationManager = null;
                WLNotificationManager.this.m_isInitialized = false;
                WLNotificationManager.this.m_displayedNotifications.clear();
                WLNotificationManager.this.m_maxNotificationsPerApp = 0;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onServiceConnected(android.content.ComponentName r11, android.os.IBinder r12) throws  {
            /*
            r10 = this;
            r0 = com.abaltatech.mcp.weblink.sdk.WLNotificationManager.this;
            monitor-enter(r0);
            r1 = com.abaltatech.mcp.weblink.sdk.WLNotificationManager.this;	 Catch:{ Throwable -> 0x002c }
            r2 = com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager.Stub.asInterface(r12);	 Catch:{ Throwable -> 0x002c }
            r1.m_notificationManager = r2;	 Catch:{ Throwable -> 0x002c }
            r1 = com.abaltatech.mcp.weblink.sdk.WLNotificationManager.this;
            r3 = com.abaltatech.mcp.weblink.sdk.WLNotificationManager.this;
            r2 = r3.m_notificationManager;	 Catch:{ RemoteException -> 0x001d }
            r4 = r2.getMaxNotificationsPerApplication();	 Catch:{ RemoteException -> 0x001d }
            r1.m_maxNotificationsPerApp = r4;	 Catch:{ RemoteException -> 0x001d }
        L_0x001b:
            monitor-exit(r0);	 Catch:{ Throwable -> 0x002c }
            return;
        L_0x001d:
            r5 = move-exception;
            r6 = "WLNotificationManager";
            r7 = "Error obtaining max notification number per app,  defaulting to 2";
            com.abaltatech.mcp.mcs.logger.MCSLogger.log(r6, r7, r5);	 Catch:{ Throwable -> 0x002c }
            r1 = com.abaltatech.mcp.weblink.sdk.WLNotificationManager.this;	 Catch:{ Throwable -> 0x002c }
            r8 = 2;
            r1.m_maxNotificationsPerApp = r8;	 Catch:{ Throwable -> 0x002c }
            goto L_0x001b;
        L_0x002c:
            r9 = move-exception;
            monitor-exit(r0);	 Catch:{ Throwable -> 0x002c }
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLNotificationManager.1.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
        }
    }

    public interface INotificationEventListener {
        void onNotificationClicked(INotification iNotification) throws ;

        void onNotificationDismissed(INotification iNotification) throws ;

        void onNotificationShown(INotification iNotification) throws ;
    }

    public enum NotificationPriority {
        Low(0),
        Normal(1),
        High(2);
        
        private final int m_value;

        private NotificationPriority(@Signature({"(I)V"}) int $i1) throws  {
            this.m_value = $i1;
        }

        int toInt() throws  {
            return this.m_value;
        }
    }

    private WLNotificationManager() throws  {
    }

    public static synchronized WLNotificationManager getInstance() throws  {
        Class cls = WLNotificationManager.class;
        synchronized (cls) {
            try {
                if (ms_instance == null) {
                    ms_instance = new WLNotificationManager();
                }
                WLNotificationManager $r0 = ms_instance;
                return $r0;
            } finally {
                cls = WLNotificationManager.class;
            }
        }
    }

    public INotification notify(int $i0, WLNotification $r1) throws  {
        return notify(null, $i0, $r1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.abaltatech.mcp.weblink.sdk.WLNotificationManager.INotification notify(java.lang.String r22, int r23, com.abaltatech.mcp.weblink.sdk.WLNotification r24) throws  {
        /*
        r21 = this;
        r0 = r21;
        r4 = r0.m_isInitialized;
        if (r4 == 0) goto L_0x000c;
    L_0x0006:
        r0 = r21;
        r5 = r0.m_notificationManager;
        if (r5 != 0) goto L_0x001a;
    L_0x000c:
        r6 = new java.lang.Exception;
        r6.<init>();
        r7 = "WLNotificationManager";
        r8 = "Please initialize notification manager before adding a notification!";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r7, r8, r6);
        r9 = 0;
        return r9;
    L_0x001a:
        if (r24 != 0) goto L_0x0024;
    L_0x001c:
        r10 = new java.security.InvalidParameterException;
        r7 = "notification cannot be null!";
        r10.<init>(r7);
        throw r10;
    L_0x0024:
        r0 = r24;
        r11 = r0.text;
        if (r11 != 0) goto L_0x0032;
    L_0x002a:
        r12 = new java.lang.IllegalArgumentException;
        r7 = "Notification text cannot be null";
        r12.<init>(r7);
        throw r12;
    L_0x0032:
        r0 = r24;
        r11 = r0.text;
        r11 = r11.trim();
        r0 = r24;
        r0.text = r11;
        r0 = r24;
        r11 = r0.text;
        r13 = r11.length();
        if (r13 != 0) goto L_0x0050;
    L_0x0048:
        r12 = new java.lang.IllegalArgumentException;
        r7 = "Notification text cannot be empty";
        r12.<init>(r7);
        throw r12;
    L_0x0050:
        r0 = r24;
        r13 = r0.timeoutMilliseconds;
        if (r13 > 0) goto L_0x005e;
    L_0x0056:
        r12 = new java.lang.IllegalArgumentException;
        r7 = "Notification timeout must be positive";
        r12.<init>(r7);
        throw r12;
    L_0x005e:
        r0 = r24;
        r13 = r0.timeoutMilliseconds;
        r14 = 20000; // 0x4e20 float:2.8026E-41 double:9.8813E-320;
        if (r13 <= r14) goto L_0x00c5;
    L_0x0066:
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        r7 = "ERROR: the timeout value is too high: ";
        r15 = r15.append(r7);
        r0 = r24;
        r13 = r0.timeoutMilliseconds;
        r15 = r15.append(r13);
        r7 = "... Decreasing it to ";
        r15 = r15.append(r7);
        r14 = 20000; // 0x4e20 float:2.8026E-41 double:9.8813E-320;
        r15 = r15.append(r14);
        r11 = r15.toString();
        r7 = "WLNotificationManager";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r7, r11);
        r14 = 20000; // 0x4e20 float:2.8026E-41 double:9.8813E-320;
        r0 = r24;
        r0.timeoutMilliseconds = r14;
    L_0x0094:
        r0 = r24;
        r13 = r0.priority;
        r0 = r21;
        r16 = r0.getPriority(r13);
        r0 = r16;
        r1 = r24;
        r1.notificationPriority = r0;
        if (r22 != 0) goto L_0x00a8;
    L_0x00a6:
        r22 = "";
    L_0x00a8:
        monitor-enter(r21);
        r0 = r21;
        r1 = r22;
        r2 = r23;
        r17 = r0.findNotification(r1, r2);	 Catch:{ Throwable -> 0x0117 }
        r18 = r17;
        if (r17 == 0) goto L_0x00fc;
    L_0x00b7:
        r0 = r17;
        r1 = r24;
        r0.updatedData(r1);	 Catch:{ Throwable -> 0x0117 }
    L_0x00be:
        monitor-exit(r21);	 Catch:{ Throwable -> 0x0117 }
        r0 = r21;
        r0.checkNotificationsQueue();
        return r18;
    L_0x00c5:
        r0 = r24;
        r13 = r0.timeoutMilliseconds;
        r14 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        if (r13 >= r14) goto L_0x0094;
    L_0x00cd:
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        r7 = "ERROR: the timeout value is too low: ";
        r15 = r15.append(r7);
        r0 = r24;
        r13 = r0.timeoutMilliseconds;
        r15 = r15.append(r13);
        r7 = "... Increasing it to ";
        r15 = r15.append(r7);
        r14 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        r15 = r15.append(r14);
        r11 = r15.toString();
        r7 = "WLNotificationManager";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r7, r11);
        r14 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        r0 = r24;
        r0.timeoutMilliseconds = r14;
        goto L_0x0094;
    L_0x00fc:
        r17 = new com.abaltatech.mcp.weblink.sdk.WLDisplayNotification;	 Catch:{ Throwable -> 0x0117 }
        r0 = r17;
        r1 = r24;
        r2 = r23;
        r3 = r22;
        r0.<init>(r1, r2, r3);	 Catch:{ Throwable -> 0x0117 }
        r0 = r21;
        r0 = r0.m_pendingQueue;	 Catch:{ Throwable -> 0x011a }
        r19 = r0;
        r1 = r17;
        r0.add(r1);	 Catch:{ Throwable -> 0x011a }
        r18 = r17;
        goto L_0x00be;
    L_0x0117:
        r20 = move-exception;
    L_0x0118:
        monitor-exit(r21);	 Catch:{ Throwable -> 0x0117 }
        throw r20;
    L_0x011a:
        r20 = move-exception;
        goto L_0x0118;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLNotificationManager.notify(java.lang.String, int, com.abaltatech.mcp.weblink.sdk.WLNotification):com.abaltatech.mcp.weblink.sdk.WLNotificationManager$INotification");
    }

    public void cancel(int $i0) throws  {
        cancel(null, $i0);
    }

    public void cancel(String $r3, int $i0) throws  {
        if ($r3 == null) {
            $r3 = "";
        }
        synchronized (this) {
            WLDisplayNotification $r2 = findNotification($r3, $i0);
        }
        if ($r2 != null) {
            $r2.dismissNotification();
        }
    }

    public synchronized void cancelAll() throws  {
        synchronized (this) {
            ArrayList<WLDisplayNotification> $r1 = new ArrayList(this.m_displayedNotifications);
            ArrayList<WLDisplayNotification> $r3 = new ArrayList(this.m_pendingQueue);
            this.m_pendingQueue.clear();
            this.m_displayedNotifications.clear();
            for (WLDisplayNotification $r7 : $r1) {
                if (this.m_notificationManager != null) {
                    try {
                        this.m_notificationManager.dismissNotification($r7.getNotification());
                    } catch (Exception $r2) {
                        MCSLogger.log(TAG, "Error removing notification", $r2);
                    }
                }
            }
        }
        for (WLDisplayNotification notifyDismissed : $r3) {
            notifyDismissed.notifyDismissed();
        }
    }

    private WLDisplayNotification findNotification(String $r1, int $i0) throws  {
        for (WLDisplayNotification $r5 : this.m_displayedNotifications) {
            if ($r5.m_notificationAppID == $i0 && $r5.m_notificationAppTag.compareTo($r1) == 0) {
                return $r5;
            }
        }
        for (WLDisplayNotification $r52 : this.m_pendingQueue) {
            if ($r52.m_notificationAppID == $i0 && $r52.m_notificationAppTag.compareTo($r1) == 0) {
                return $r52;
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkNotificationsQueue() throws  {
        /*
        r31 = this;
        monitor-enter(r31);
        r0 = r31;
        r9 = r0.isTransformingQueues;	 Catch:{ Throwable -> 0x0019 }
        if (r9 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r31);	 Catch:{ Throwable -> 0x0019 }
        return;
    L_0x0009:
        r0 = r31;
        r10 = r0.m_displayedNotifications;	 Catch:{ Throwable -> 0x0019 }
        r11 = r10.size();	 Catch:{ Throwable -> 0x0019 }
        r0 = r31;
        r12 = r0.m_maxNotificationsPerApp;	 Catch:{ Throwable -> 0x0019 }
        if (r11 < r12) goto L_0x001c;
    L_0x0017:
        monitor-exit(r31);	 Catch:{ Throwable -> 0x0019 }
        return;
    L_0x0019:
        r13 = move-exception;
        monitor-exit(r31);	 Catch:{ Throwable -> 0x0019 }
        throw r13;
    L_0x001c:
        r0 = r31;
        r10 = r0.m_pendingQueue;	 Catch:{ Throwable -> 0x0019 }
        r11 = r10.size();	 Catch:{ Throwable -> 0x0019 }
        if (r11 != 0) goto L_0x0028;
    L_0x0026:
        monitor-exit(r31);	 Catch:{ Throwable -> 0x0019 }
        return;
    L_0x0028:
        r0 = r31;
        r10 = r0.m_pendingQueue;	 Catch:{ Throwable -> 0x0019 }
        java.util.Collections.sort(r10);	 Catch:{ Throwable -> 0x0019 }
        r0 = r31;
        r10 = r0.m_pendingQueue;	 Catch:{ Throwable -> 0x0019 }
        r11 = r11 + -1;
        r14 = r10.remove(r11);	 Catch:{ Throwable -> 0x0019 }
        r16 = r14;
        r16 = (com.abaltatech.mcp.weblink.sdk.WLDisplayNotification) r16;	 Catch:{ Throwable -> 0x0019 }
        r15 = r16;
        r17 = r15;
        r18 = 1;
        r0 = r18;
        r1 = r31;
        r1.isTransformingQueues = r0;	 Catch:{ Throwable -> 0x0019 }
        monitor-exit(r31);	 Catch:{ Throwable -> 0x0019 }
        r19 = r15.getOriginalNotification();	 Catch:{ Throwable -> 0x00bc }
        r0 = r19;
        r0 = r0.text;	 Catch:{ Throwable -> 0x00bc }
        r20 = r0;
        r0 = r19;
        r11 = r0.timeoutMilliseconds;	 Catch:{ Throwable -> 0x00bc }
        r0 = r19;
        r0 = r0.largeIcon;	 Catch:{ Throwable -> 0x00bc }
        r21 = r0;
        r0 = r19;
        r0 = r0.largeIconURL;	 Catch:{ Throwable -> 0x00bc }
        r22 = r0;
        r0 = r19;
        r0 = r0.notificationPriority;	 Catch:{ Throwable -> 0x00bc }
        r23 = r0;
        r0 = r19;
        r9 = r0.showProgress;	 Catch:{ Throwable -> 0x00bc }
        r0 = r19;
        r0 = r0.contentIntent;	 Catch:{ Throwable -> 0x00bc }
        r24 = r0;
        r0 = r19;
        r0 = r0.deleteIntent;	 Catch:{ Throwable -> 0x00bc }
        r25 = r0;
        r0 = r31;
        r1 = r20;
        r2 = r11;
        r3 = r21;
        r4 = r22;
        r5 = r23;
        r6 = r9;
        r7 = r24;
        r8 = r25;
        r26 = r0.addNotification(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x00bc }
        if (r26 == 0) goto L_0x00a0;
    L_0x0090:
        r0 = r26;
        r15.setData(r0);	 Catch:{ Throwable -> 0x00bc }
        monitor-enter(r31);	 Catch:{ Throwable -> 0x00bc }
        r0 = r31;
        r10 = r0.m_displayedNotifications;	 Catch:{ Throwable -> 0x00b9 }
        r10.add(r15);	 Catch:{ Throwable -> 0x00b9 }
        r17 = 0;
        monitor-exit(r31);	 Catch:{ Throwable -> 0x00b9 }
    L_0x00a0:
        monitor-enter(r31);
        r18 = 0;
        r0 = r18;
        r1 = r31;
        r1.isTransformingQueues = r0;	 Catch:{ Throwable -> 0x00b6 }
        if (r17 == 0) goto L_0x00b4;
    L_0x00ab:
        r0 = r31;
        r10 = r0.m_pendingQueue;	 Catch:{ Throwable -> 0x00b6 }
        r0 = r17;
        r10.add(r0);	 Catch:{ Throwable -> 0x00b6 }
    L_0x00b4:
        monitor-exit(r31);	 Catch:{ Throwable -> 0x00b6 }
        return;
    L_0x00b6:
        r27 = move-exception;
        monitor-exit(r31);	 Catch:{ Throwable -> 0x00b6 }
        throw r27;
    L_0x00b9:
        r28 = move-exception;
        monitor-exit(r31);	 Catch:{ Throwable -> 0x00b9 }
        throw r28;	 Catch:{ Throwable -> 0x00bc }
    L_0x00bc:
        r29 = move-exception;
        monitor-enter(r31);
        r18 = 0;
        r0 = r18;
        r1 = r31;
        r1.isTransformingQueues = r0;	 Catch:{ Throwable -> 0x00d3 }
        if (r17 == 0) goto L_0x00d1;
    L_0x00c8:
        r0 = r31;
        r10 = r0.m_pendingQueue;	 Catch:{ Throwable -> 0x00d3 }
        r0 = r17;
        r10.add(r0);	 Catch:{ Throwable -> 0x00d3 }
    L_0x00d1:
        monitor-exit(r31);	 Catch:{ Throwable -> 0x00d3 }
        throw r29;
    L_0x00d3:
        r30 = move-exception;
        monitor-exit(r31);	 Catch:{ Throwable -> 0x00d3 }
        throw r30;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLNotificationManager.checkNotificationsQueue():void");
    }

    private NotificationPriority getPriority(int $i0) throws  {
        switch ($i0) {
            case -2:
            case -1:
                return NotificationPriority.Low;
            case 0:
                return NotificationPriority.Normal;
            case 1:
            case 2:
                return NotificationPriority.High;
            default:
                throw new UnsupportedOperationException("Invalid notification priority. Please use one of NotificationPriority.Low, NotificationPriority.Normal, Notification.PRIORITY_MAX");
        }
    }

    private synchronized IWLDisplayNotificationData addNotification(String $r1, int $i0, Bitmap $r2, String $r3, NotificationPriority $r4, boolean $z0, PendingIntent $r5, PendingIntent $r6) throws  {
        IWLDisplayNotificationData $r8;
        $r8 = null;
        if (this.m_notificationManager != null) {
            IWLDisplayNotificationManager $r9 = this.m_notificationManager;
            try {
                $r8 = $r9.addNotification($r1, $i0, $r3, $r2, true, false, $r4.toInt(), $z0, $r5, $r6);
            } catch (Throwable $r7) {
                MCSLogger.log(TAG, "Error adding a notification", $r7);
                $r8 = null;
            }
        }
        return $r8;
    }

    void onNotificationClosed(WLDisplayNotification $r1, boolean $z0) throws  {
        synchronized (this) {
            this.m_displayedNotifications.remove($r1);
            this.m_pendingQueue.remove($r1);
            IWLDisplayNotificationManager $r5 = this.m_notificationManager;
        }
        if ($r5 != null && $z0) {
            try {
                $r5.dismissNotification($r1.getNotification());
            } catch (RemoteException $r2) {
                MCSLogger.log(TAG, "Error dismissing a notification", $r2);
            }
        }
        checkNotificationsQueue();
    }

    synchronized void init() throws  {
        if (this.m_isInitialized) {
            MCSLogger.log(TAG, "Already initialized!");
        } else {
            this.m_maxNotificationsPerApp = 2;
            this.m_isInitialized = WEBLINK.bindService(WEBLINK.instance.getContext(), WebLinkServerCore.WLSERVICE_IWLDISPLAYNOTIFICATIONMANAGER, new C03601());
        }
    }
}
