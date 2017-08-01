package com.abaltatech.wlappservices;

import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import java.util.ArrayList;
import java.util.Date;

public class TestAppService_Timer implements IServiceHandler {
    public static final String NotificationID = "Notification1";
    protected static final String TAG = "TestAppService_Timer";
    private SparseBooleanArray m_cancelRequestMap = new SparseBooleanArray();
    private Handler m_handler = new Handler();
    private ArrayList<IServiceNotificationHandler> m_notificationHandlers = new ArrayList();
    private int m_requestID = 0;

    class C04511 implements Runnable {
        C04511() throws  {
        }

        public void run() throws  {
            while (true) {
                try {
                    Thread.sleep(5000);
                    TestAppService_Timer.this.onTimer();
                } catch (InterruptedException $r1) {
                    Log.e(TestAppService_Timer.TAG, "Thread interrupted", $r1);
                    return;
                }
            }
        }
    }

    class C04555 implements Runnable {
        final /* synthetic */ IServiceResponseNotification val$notification;
        final /* synthetic */ ServiceRequest val$request;
        final /* synthetic */ int val$requestID;

        C04555(int $i0, ServiceRequest $r2, IServiceResponseNotification $r3) throws  {
            this.val$requestID = $i0;
            this.val$request = $r2;
            this.val$notification = $r3;
        }

        public void run() throws  {
            TestAppService_Timer.this.notifyTimeout(this.val$requestID, this.val$request, this.val$notification);
        }
    }

    protected void processTimeoutRequest(int r13, com.abaltatech.wlappservices.ServiceRequest r14, com.abaltatech.wlappservices.IServiceResponseNotification r15) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0029 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r12 = this;
        r0 = 0;
        r1 = r14.getRequestBody();	 Catch:{ Throwable -> 0x002a }
        r2 = new java.lang.String;	 Catch:{ Throwable -> 0x002a }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x002a }
        r3 = java.lang.Integer.parseInt(r2);	 Catch:{ Throwable -> 0x0017 }
        r0 = r3;
        if (r3 >= 0) goto L_0x0029;	 Catch:{ Throwable -> 0x0017 }
    L_0x0011:
        r4 = new java.lang.IllegalArgumentException;	 Catch:{ Throwable -> 0x0017 }
        r4.<init>();	 Catch:{ Throwable -> 0x0017 }
        throw r4;	 Catch:{ Throwable -> 0x0017 }
    L_0x0017:
        r5 = move-exception;
    L_0x0018:
        r6 = com.abaltatech.wlappservices.EServiceErrorCode.InvalidArgument;
        r15.onRequestFailed(r14, r6);
    L_0x001d:
        r7 = r12.m_handler;
        r8 = new com.abaltatech.wlappservices.TestAppService_Timer$5;
        r8.<init>(r13, r14, r15);
        r9 = (long) r0;
        r7.postDelayed(r8, r9);
        return;
    L_0x0029:
        goto L_0x001d;
    L_0x002a:
        r11 = move-exception;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.TestAppService_Timer.processTimeoutRequest(int, com.abaltatech.wlappservices.ServiceRequest, com.abaltatech.wlappservices.IServiceResponseNotification):void");
    }

    public TestAppService_Timer() throws  {
        Thread $r1 = new Thread(new C04511());
        $r1.setName("TestAppService_Timer thread");
        $r1.start();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onTimer() throws  {
        /*
        r13 = this;
        r0 = r13.m_notificationHandlers;
        monitor-enter(r0);
        r1 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x0030 }
        r2 = r13.m_notificationHandlers;	 Catch:{ Throwable -> 0x0030 }
        r1.<init>(r2);	 Catch:{ Throwable -> 0x0030 }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0033 }
        r3 = new java.util.Date;
        r3.<init>();
        r4 = r3.toString();
        r5 = r4.getBytes();
        r6 = r1.iterator();
    L_0x001c:
        r7 = r6.hasNext();
        if (r7 == 0) goto L_0x0035;
    L_0x0022:
        r8 = r6.next();
        r10 = r8;
        r10 = (com.abaltatech.wlappservices.IServiceNotificationHandler) r10;
        r9 = r10;
        r11 = "Notification1";
        r9.onNotification(r11, r5);
        goto L_0x001c;
    L_0x0030:
        r12 = move-exception;
    L_0x0031:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0030 }
        throw r12;
    L_0x0033:
        r12 = move-exception;
        goto L_0x0031;
    L_0x0035:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.TestAppService_Timer.onTimer():void");
    }

    public int onProcessRequest(String $r1, ServiceRequest $r2, IServiceResponseNotification $r3) throws  {
        final int $i0 = getNextRequestID();
        Log.i("====>", "getNextRequestID: " + this.m_requestID + ",  path: " + $r1);
        if (!($r2 == null || $r1 == null)) {
            final ServiceRequest serviceRequest;
            final IServiceResponseNotification iServiceResponseNotification;
            if ($r1.compareTo("currentTime") == 0 && $r2.getRequestMethod() == ERequestMethod.GET) {
                serviceRequest = $r2;
                iServiceResponseNotification = $r3;
                this.m_handler.post(new Runnable() {
                    public void run() throws  {
                        TestAppService_Timer.this.processCurrentTimeRequest($i0, serviceRequest, iServiceResponseNotification);
                    }
                });
                return $i0;
            }
            if ($r1.compareTo("timeout") == 0 && $r2.getRequestMethod() == ERequestMethod.POST) {
                serviceRequest = $r2;
                iServiceResponseNotification = $r3;
                this.m_handler.post(new Runnable() {
                    public void run() throws  {
                        TestAppService_Timer.this.processTimeoutRequest($i0, serviceRequest, iServiceResponseNotification);
                    }
                });
                return $i0;
            }
        }
        final IServiceResponseNotification iServiceResponseNotification2 = $r3;
        final ServiceRequest serviceRequest2 = $r2;
        this.m_handler.postDelayed(new Runnable() {
            public void run() throws  {
                synchronized (this) {
                    TestAppService_Timer.this.m_cancelRequestMap.delete($i0);
                }
                iServiceResponseNotification2.onRequestFailed(serviceRequest2, EServiceErrorCode.UnsupportedRequest);
            }
        }, 100);
        return $i0;
    }

    protected void notifyTimeout(int $i0, ServiceRequest $r1, IServiceResponseNotification $r2) throws  {
        synchronized (this) {
            boolean $z0 = this.m_cancelRequestMap.get($i0);
            this.m_cancelRequestMap.delete($i0);
        }
        if (!$z0) {
            ServiceResponse $r3 = new ServiceResponse();
            $r3.setRequestID($i0);
            $r3.setResponseBody(null);
            $r2.onResponseReceived($r1, $r3);
        }
    }

    protected void processCurrentTimeRequest(int $i0, ServiceRequest $r1, IServiceResponseNotification $r2) throws  {
        synchronized (this) {
            boolean $z0 = this.m_cancelRequestMap.get($i0);
            this.m_cancelRequestMap.delete($i0);
        }
        if (!$z0) {
            byte[] $r7 = new Date().toString().getBytes();
            ServiceResponse $r4 = new ServiceResponse();
            $r4.setRequestID($i0);
            $r4.setResponseBody($r7);
            $r2.onResponseReceived($r1, $r4);
        }
    }

    private synchronized int getNextRequestID() throws  {
        this.m_requestID++;
        this.m_cancelRequestMap.put(this.m_requestID, false);
        return this.m_requestID;
    }

    public void registerForNotification(String $r1, IServiceNotificationHandler $r2) throws  {
        if ($r1 != null && $r2 != null && $r1.compareTo(NotificationID) == 0) {
            synchronized (this.m_notificationHandlers) {
                this.m_notificationHandlers.add($r2);
            }
        }
    }

    public void unregisterFromNotification(String $r1, IServiceNotificationHandler $r2) throws  {
        if ($r1 != null && $r2 != null && $r1.compareTo(NotificationID) == 0) {
            synchronized (this.m_notificationHandlers) {
                this.m_notificationHandlers.remove($r2);
            }
        }
    }

    public synchronized boolean onCancelRequest(int $i0) throws  {
        boolean $z0;
        $z0 = false;
        if ($i0 > 0) {
            if ($i0 <= this.m_requestID && this.m_cancelRequestMap.indexOfKey($i0) >= 0) {
                this.m_cancelRequestMap.put($i0, true);
                $z0 = true;
            }
        }
        return $z0;
    }

    public void removeAllNotifications() throws  {
        synchronized (this.m_notificationHandlers) {
            this.m_notificationHandlers.clear();
        }
    }
}
