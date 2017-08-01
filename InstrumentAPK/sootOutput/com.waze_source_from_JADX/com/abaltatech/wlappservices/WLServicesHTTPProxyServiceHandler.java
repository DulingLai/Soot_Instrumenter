package com.abaltatech.wlappservices;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.SparseArray;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint({"DefaultLocale"})
public class WLServicesHTTPProxyServiceHandler implements IServiceHandler {
    private static final byte[] s_emptyResponse = new byte[]{(byte) 0};
    protected String m_baseURL;
    private final Object m_lock = new Object();
    protected int m_nextRequestID = 1;
    protected Map<NotificationPair, NotificationHandler> m_notificationMap = new HashMap();
    protected SparseArray<RequestWrapper> m_pendingRequests = new SparseArray();
    protected int m_port;
    protected String m_remoteHost;
    protected List<RequestWrapper> m_requestsQueue = new ArrayList();
    protected Map<String, String> m_resourceMapping;
    private WLServicesHTTPProxyServiceHandlerThread m_thread;

    enum EResponseStatus {
        ERS_UNKNOWN,
        ERS_OK,
        ERS_BAD
    }

    public static class NotificationHandler implements IServiceNotificationHandler {
        protected IServiceNotificationHandler m_handler;
        protected boolean m_isStopped = false;
        protected String m_resourcePath;

        public NotificationHandler(String $r1, IServiceNotificationHandler $r2) throws  {
            this.m_resourcePath = $r1;
            this.m_handler = $r2;
        }

        public synchronized void stop() throws  {
            this.m_isStopped = true;
        }

        public synchronized boolean isStopped() throws  {
            return this.m_isStopped;
        }

        public String getResourcePath() throws  {
            return this.m_resourcePath;
        }

        public IServiceNotificationHandler getNotificationHandler() throws  {
            return this.m_handler;
        }

        public void onNotification(String $r1, byte[] $r2) throws  {
            this.m_handler.onNotification($r1, $r2);
        }
    }

    private static class NotificationPair {
        public String first;
        public IServiceNotificationHandler second;

        public NotificationPair(String $r1, IServiceNotificationHandler $r2) throws  {
            this.first = $r1;
            this.second = $r2;
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = ((this.first == null ? 0 : this.first.hashCode()) + 31) * 31;
            if (this.second != null) {
                $i0 = this.second.hashCode();
            }
            return $i1 + $i0;
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null) {
                return false;
            }
            if (getClass() != $r1.getClass()) {
                return false;
            }
            NotificationPair $r4 = (NotificationPair) $r1;
            if (this.first == null) {
                if ($r4.first != null) {
                    return false;
                }
            } else if (!this.first.equals($r4.first)) {
                return false;
            }
            if (this.second != null) {
                return this.second.equals($r4.second);
            } else {
                if ($r4.second != null) {
                    return false;
                }
                return true;
            }
        }
    }

    public static class RequestWrapper {
        public IServiceResponseNotification m_notification;
        public ServiceRequest m_request;
        public int m_requestID;
        public String m_resourcePath;

        public RequestWrapper(int $i0, ServiceRequest $r1, String $r2, IServiceResponseNotification $r3) throws  {
            this.m_requestID = $i0;
            this.m_request = $r1;
            this.m_resourcePath = $r2;
            this.m_notification = $r3;
        }

        public String getRequestMethodText() throws  {
            switch (this.m_request.getRequestMethod()) {
                case GET:
                    return "GET";
                case POST:
                    return "POST";
                case PUT:
                    return "PUT";
                case DELETE:
                    return "DELETE";
                default:
                    return "UNKNOWN REQUEST METHOD!!!";
            }
        }
    }

    private class SocketRequestHandler extends Thread {
        private final String TAG = SocketRequestHandler.class.getSimpleName();
        private String m_host = null;
        private NotificationHandler m_notification;
        private int m_port = 0;
        private String m_url = null;
        private RequestWrapper m_wrapper;

        final byte[] getContent(byte[] r12) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0064 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r11 = this;
            r0 = "Content-Length:";
            r1 = new java.lang.String;
            r1.<init>(r12);
            r3 = "Content-Length:";
            r2 = r1.indexOf(r3);
            if (r2 < 0) goto L_0x005e;
        L_0x000f:
            r3 = "Content-Length:";
            r2 = r1.indexOf(r3);
            r4 = r0.length();
            r2 = r2 + r4;
            r0 = r1.substring(r2);
            r5 = 10;
            r2 = r0.indexOf(r5);
            if (r2 >= 0) goto L_0x002e;
        L_0x0026:
            r5 = 13;
            r2 = r0.indexOf(r5);
            if (r2 < 0) goto L_0x0060;
        L_0x002e:
            r3 = "[\r\n]";	 Catch:{ NumberFormatException -> 0x0058 }
            r6 = r0.split(r3);	 Catch:{ NumberFormatException -> 0x0058 }
            r5 = 0;	 Catch:{ NumberFormatException -> 0x0058 }
            r1 = r6[r5];	 Catch:{ NumberFormatException -> 0x0058 }
            r7 = java.lang.Integer.valueOf(r1);	 Catch:{ NumberFormatException -> 0x0058 }
            r2 = r7.intValue();	 Catch:{ NumberFormatException -> 0x0058 }
            if (r2 > 0) goto L_0x004a;	 Catch:{ NumberFormatException -> 0x0058 }
        L_0x0041:
            r5 = 0;
            r8 = r12[r5];
            r5 = 48;
            if (r5 != r8) goto L_0x0062;
        L_0x0048:
            if (r2 != 0) goto L_0x0064;	 Catch:{ NumberFormatException -> 0x0058 }
        L_0x004a:
            r12 = r0.getBytes();	 Catch:{ NumberFormatException -> 0x0058 }
            r12 = r11.getContentStart(r12);	 Catch:{ NumberFormatException -> 0x0058 }
            if (r12 == 0) goto L_0x0066;
        L_0x0054:
            r4 = r12.length;
            if (r4 < r2) goto L_0x0068;
        L_0x0057:
            return r12;
        L_0x0058:
            r9 = move-exception;
            r9.printStackTrace();
            r10 = 0;
            return r10;
        L_0x005e:
            r10 = 0;
            return r10;
        L_0x0060:
            r10 = 0;
            return r10;
        L_0x0062:
            r10 = 0;
            return r10;
        L_0x0064:
            r10 = 0;
            return r10;
        L_0x0066:
            r10 = 0;
            return r10;
        L_0x0068:
            r10 = 0;
            return r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.WLServicesHTTPProxyServiceHandler.SocketRequestHandler.getContent(byte[]):byte[]");
        }

        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:153:0x0536 in {2, 6, 8, 10, 15, 21, 22, 23, 26, 31, 32, 35, 50, 53, 56, 59, 62, 65, 71, 75, 77, 81, 91, 109, 110, 125, 127, 130, 133, 135, 140, 143, 147, 151, 154} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r45 = this;
            r0 = r45;
            r5 = r0.TAG;
            r0 = r45;
            r0.setName(r5);
            r0 = r45;
            r5 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "START ";
            r6 = r6.append(r7);
            r0 = r45;
            r8 = r0.TAG;
            r6 = r6.append(r8);
            r8 = r6.toString();
            android.util.Log.d(r5, r8);
            r0 = r45;
            r5 = r0.m_host;
            r9 = java.net.InetAddress.getByName(r5);	 Catch:{ UnknownHostException -> 0x0087 }
        L_0x002f:
            if (r9 != 0) goto L_0x00ae;
        L_0x0031:
            r0 = r45;
            r5 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "Resolved host is null! :";
            r6 = r6.append(r7);
            r0 = r45;
            r8 = r0.m_host;
            r6 = r6.append(r8);
            r8 = r6.toString();
            android.util.Log.w(r5, r8);
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            if (r11 == 0) goto L_0x0068;
        L_0x0057:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            r0 = r45;
            r10 = r0.m_wrapper;
            r12 = r10.m_request;
            r13 = com.abaltatech.wlappservices.EServiceErrorCode.NotAvailable;
            r11.onRequestFailed(r12, r13);
        L_0x0068:
            r0 = r45;
            r5 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "STOP ";
            r6 = r6.append(r7);
            r0 = r45;
            r8 = r0.TAG;
            r6 = r6.append(r8);
            r8 = r6.toString();
            android.util.Log.d(r5, r8);
            return;
        L_0x0087:
            r14 = move-exception;
            r0 = r45;
            r5 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "Cannot resolve host ";
            r6 = r6.append(r7);
            r0 = r45;
            r8 = r0.m_host;
            r6 = r6.append(r8);
            r7 = " :";
            r6 = r6.append(r7);
            r8 = r6.toString();
            android.util.Log.e(r5, r8, r14);
            r9 = 0;
            goto L_0x002f;
        L_0x00ae:
            r15 = new java.net.Socket;
            r0 = r45;
            r0 = r0.m_port;
            r16 = r0;
            r15.<init>(r9, r0);	 Catch:{ IOException -> 0x00e8 }
            r17 = 1;	 Catch:{ IOException -> 0x053a }
            r0 = r17;	 Catch:{ IOException -> 0x053a }
            r15.setKeepAlive(r0);	 Catch:{ IOException -> 0x053a }
            r18 = r15.getOutputStream();	 Catch:{ IOException -> 0x053a }
            r19 = r15.getInputStream();	 Catch:{ IOException -> 0x053a }
        L_0x00c8:
            if (r15 == 0) goto L_0x00ce;
        L_0x00ca:
            if (r18 == 0) goto L_0x00ce;
        L_0x00cc:
            if (r19 != 0) goto L_0x0116;
        L_0x00ce:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            if (r11 == 0) goto L_0x0068;
        L_0x00d6:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            r0 = r45;
            r10 = r0.m_wrapper;
            r12 = r10.m_request;
            r13 = com.abaltatech.wlappservices.EServiceErrorCode.NotAvailable;
            r11.onRequestFailed(r12, r13);
            goto L_0x0068;
        L_0x00e8:
            r20 = move-exception;
        L_0x00e9:
            r0 = r45;
            r5 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "ERROR opening communication socket on ";
            r6 = r6.append(r7);
            r6 = r6.append(r9);
            r7 = " :";
            r6 = r6.append(r7);
            r8 = r6.toString();
            r0 = r20;
            android.util.Log.e(r5, r8, r0);
            r0 = r20;
            r0.printStackTrace();
            r15 = 0;
            r18 = 0;
            r19 = 0;
            goto L_0x00c8;
        L_0x0116:
            r5 = new java.lang.String;
            r0 = r45;
            r8 = r0.m_host;
            r5.<init>(r8);
            r0 = r45;
            r0 = r0.m_port;
            r16 = r0;
            r17 = 80;
            r0 = r16;
            r1 = r17;
            if (r0 == r1) goto L_0x014f;
        L_0x012d:
            r17 = 2;
            r0 = r17;
            r0 = new java.lang.Object[r0];
            r21 = r0;
            r17 = 0;
            r21[r17] = r5;
            r0 = r45;
            r0 = r0.m_port;
            r16 = r0;
            r22 = java.lang.Integer.valueOf(r0);
            r17 = 1;
            r21[r17] = r22;
            r7 = "%s:%d";
            r0 = r21;
            r5 = java.lang.String.format(r7, r0);
        L_0x014f:
            r0 = r45;
            r10 = r0.m_wrapper;
            r8 = r10.getRequestMethodText();
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r6 = r6.append(r8);
            r7 = " ";
            r6 = r6.append(r7);
            r8 = r6.toString();
            r23 = r8;
            r0 = r45;
            r0 = r0.m_url;
            r24 = r0;
            r16 = r0.length();
            if (r16 <= 0) goto L_0x01ba;
        L_0x0178:
            r0 = r45;
            r0 = r0.m_url;
            r24 = r0;
            r17 = 0;
            r0 = r24;
            r1 = r17;
            r25 = r0.charAt(r1);
            r17 = 47;
            r0 = r25;
            r1 = r17;
            if (r0 == r1) goto L_0x01a3;
        L_0x0190:
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r6 = r6.append(r8);
            r7 = "/";
            r6 = r6.append(r7);
            r23 = r6.toString();
        L_0x01a3:
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r0 = r23;
            r6 = r6.append(r0);
            r0 = r45;
            r8 = r0.m_url;
            r6 = r6.append(r8);
            r23 = r6.toString();
        L_0x01ba:
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r0 = r23;
            r6 = r6.append(r0);
            r7 = " HTTP/1.1\r\nHost: ";
            r6 = r6.append(r7);
            r8 = r6.toString();
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r6 = r6.append(r8);
            r6 = r6.append(r5);
            r5 = r6.toString();
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r6 = r6.append(r5);
            r7 = "\r\n\r\n";
            r6 = r6.append(r7);
            r5 = r6.toString();
            r0 = r45;
            r8 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "Posting request string: ";
            r6 = r6.append(r7);
            r6 = r6.append(r5);
            r23 = r6.toString();
            r0 = r23;
            android.util.Log.v(r8, r0);
            r26 = r5.getBytes();	 Catch:{ IOException -> 0x02ad }
            r0 = r18;	 Catch:{ IOException -> 0x02ad }
            r1 = r26;	 Catch:{ IOException -> 0x02ad }
            r0.write(r1);	 Catch:{ IOException -> 0x02ad }
        L_0x021a:
            r27 = r15.isConnected();
            if (r27 == 0) goto L_0x0511;
        L_0x0220:
            r17 = 4097; // 0x1001 float:5.741E-42 double:2.024E-320;
            r0 = r17;
            r0 = new byte[r0];
            r26 = r0;
            r28 = 0;
            r16 = -1;
            r29 = 0;
            r30 = com.abaltatech.wlappservices.WLServicesHTTPProxyServiceHandler.EResponseStatus.ERS_UNKNOWN;
        L_0x0230:
            r17 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
            r31 = r17 - r28;
            r0 = r19;	 Catch:{ IOException -> 0x0308 }
            r1 = r26;	 Catch:{ IOException -> 0x0308 }
            r2 = r28;	 Catch:{ IOException -> 0x0308 }
            r3 = r31;	 Catch:{ IOException -> 0x0308 }
            r31 = r0.read(r1, r2, r3);	 Catch:{ IOException -> 0x0308 }
            r16 = r31;
            if (r31 <= 0) goto L_0x026a;
        L_0x0244:
            r0 = r28;
            r1 = r31;
            r0 = r0 + r1;
            r28 = r0;
            r17 = 0;	 Catch:{ IOException -> 0x0308 }
            r26[r28] = r17;	 Catch:{ IOException -> 0x0308 }
            r0 = r45;	 Catch:{ IOException -> 0x0308 }
            r1 = r26;	 Catch:{ IOException -> 0x0308 }
            r32 = r0.getResponseStatus(r1);	 Catch:{ IOException -> 0x0308 }
            r30 = r32;
            r33 = com.abaltatech.wlappservices.WLServicesHTTPProxyServiceHandler.EResponseStatus.ERS_BAD;
            r0 = r33;
            r1 = r32;
            if (r0 != r1) goto L_0x02df;
        L_0x0261:
            r0 = r45;	 Catch:{ IOException -> 0x0308 }
            r5 = r0.TAG;	 Catch:{ IOException -> 0x0308 }
            r7 = "ResponseStatus - found BAD!";	 Catch:{ IOException -> 0x0308 }
            android.util.Log.w(r5, r7);	 Catch:{ IOException -> 0x0308 }
        L_0x026a:
            if (r16 >= 0) goto L_0x0327;
        L_0x026c:
            r0 = r45;
            r5 = r0.TAG;
            goto L_0x0274;
        L_0x0271:
            goto L_0x021a;
        L_0x0274:
            r7 = "BytesRead from socket < 0! Stopping.";
            android.util.Log.w(r5, r7);
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            if (r34 == 0) goto L_0x0315;
        L_0x0281:
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            r0.stop();
        L_0x028a:
            if (r19 == 0) goto L_0x0291;
        L_0x028c:
            r0 = r19;	 Catch:{ IOException -> 0x0520 }
            r0.close();	 Catch:{ IOException -> 0x0520 }
        L_0x0291:
            if (r18 == 0) goto L_0x0298;
        L_0x0293:
            r0 = r18;	 Catch:{ IOException -> 0x052b }
            r0.close();	 Catch:{ IOException -> 0x052b }
        L_0x0298:
            if (r15 == 0) goto L_0x0068;
        L_0x029a:
            goto L_0x029e;
        L_0x029b:
            goto L_0x0068;
        L_0x029e:
            r15.close();	 Catch:{ IOException -> 0x02a2 }
            goto L_0x029b;
        L_0x02a2:
            r35 = move-exception;
            goto L_0x02a7;
        L_0x02a4:
            goto L_0x0068;
        L_0x02a7:
            r0 = r35;
            r0.printStackTrace();
            goto L_0x02a4;
        L_0x02ad:
            r36 = move-exception;
            r0 = r45;
            r5 = r0.TAG;
            goto L_0x02b6;
        L_0x02b3:
            goto L_0x026a;
        L_0x02b6:
            r7 = "ERROR writing to socket! ";
            r0 = r36;
            android.util.Log.e(r5, r7, r0);
            goto L_0x02c1;
        L_0x02be:
            goto L_0x026a;
        L_0x02c1:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            if (r11 == 0) goto L_0x021a;
        L_0x02c9:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            r0 = r45;
            r10 = r0.m_wrapper;
            r12 = r10.m_request;
            r13 = com.abaltatech.wlappservices.EServiceErrorCode.NotAvailable;
            goto L_0x02db;
        L_0x02d8:
            goto L_0x028a;
        L_0x02db:
            r11.onRequestFailed(r12, r13);
            goto L_0x0271;
        L_0x02df:
            r33 = com.abaltatech.wlappservices.WLServicesHTTPProxyServiceHandler.EResponseStatus.ERS_OK;
            r0 = r33;
            r1 = r32;
            if (r0 != r1) goto L_0x0230;
        L_0x02e7:
            r0 = r45;	 Catch:{ IOException -> 0x0308 }
            r1 = r26;	 Catch:{ IOException -> 0x0308 }
            r37 = r0.getContent(r1);	 Catch:{ IOException -> 0x0308 }
            r29 = r37;
            if (r37 != 0) goto L_0x026a;
        L_0x02f3:
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            if (r34 == 0) goto L_0x0230;	 Catch:{ IOException -> 0x0308 }
        L_0x02fb:
            r0 = r45;	 Catch:{ IOException -> 0x0308 }
            r1 = r26;	 Catch:{ IOException -> 0x0308 }
            r37 = r0.getContentStart(r1);	 Catch:{ IOException -> 0x0308 }
            r29 = r37;
            if (r37 == 0) goto L_0x0230;
        L_0x0307:
            goto L_0x02b3;
        L_0x0308:
            r38 = move-exception;
            r0 = r45;
            r5 = r0.TAG;
            r7 = "ERROR reading from socket! ";
            r0 = r38;
            android.util.Log.e(r5, r7, r0);
            goto L_0x02be;
        L_0x0315:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            r0 = r45;
            r10 = r0.m_wrapper;
            r12 = r10.m_request;
            r13 = com.abaltatech.wlappservices.EServiceErrorCode.NotAvailable;
            r11.onRequestFailed(r12, r13);
            goto L_0x02d8;
        L_0x0327:
            r32 = com.abaltatech.wlappservices.WLServicesHTTPProxyServiceHandler.EResponseStatus.ERS_OK;
            r0 = r32;
            r1 = r30;
            if (r0 != r1) goto L_0x04f3;
        L_0x032f:
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            if (r34 == 0) goto L_0x04b1;
        L_0x0337:
            r5 = "data: ";
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            r39 = r0.getNotificationHandler();
            r0 = r29;
            r0 = r0.length;
            r31 = r0;
            r31 = r28 - r31;
            r0 = r45;
            r8 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "moving by offset=";
            r6 = r6.append(r7);
            r0 = r31;
            r6 = r6.append(r0);
            r7 = " from totalBytes=";
            r6 = r6.append(r7);
            r0 = r28;
            r6 = r6.append(r0);
            r23 = r6.toString();
            r0 = r23;
            android.util.Log.v(r8, r0);
            r0 = r28;
            r1 = r31;
            r0 = r0 - r1;
            r28 = r0;
            r17 = 0;
            r40 = 0;
            r0 = r29;
            r1 = r17;
            r2 = r26;
            r3 = r40;
            r4 = r28;
            java.lang.System.arraycopy(r0, r1, r2, r3, r4);
            r17 = 0;
            r26[r28] = r17;
            r0 = r45;
            r1 = r26;
            r29 = r0.getContentStart(r1);
        L_0x0398:
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            r27 = r0.isStopped();
            if (r27 != 0) goto L_0x028a;
        L_0x03a4:
            if (r29 == 0) goto L_0x0454;
        L_0x03a6:
            r8 = new java.lang.String;
            r0 = r29;
            r8.<init>(r0);
            r8 = r8.trim();
            r7 = "data: ";
            r27 = r8.startsWith(r7);
            if (r27 == 0) goto L_0x03fe;
        L_0x03b9:
            r31 = r5.length();
            r0 = r31;
            r8 = r8.substring(r0);
            r0 = r45;
            r0 = r0.TAG;
            r23 = r0;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "found notification data! ";
            r6 = r6.append(r7);
            r6 = r6.append(r8);
            r24 = r6.toString();
            r0 = r23;
            r1 = r24;
            android.util.Log.v(r0, r1);
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            r23 = r0.getResourcePath();
            r37 = r8.getBytes();
            r0 = r39;
            r1 = r23;
            r2 = r37;
            r0.onNotification(r1, r2);
            goto L_0x03fe;
        L_0x03fb:
            goto L_0x03a4;
        L_0x03fe:
            r0 = r29;
            r0 = r0.length;
            r31 = r0;
            r31 = r28 - r31;
            r0 = r45;
            r8 = r0.TAG;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "moving by offset=";
            r6 = r6.append(r7);
            r0 = r31;
            r6 = r6.append(r0);
            r7 = " from totalBytes=";
            r6 = r6.append(r7);
            r0 = r28;
            r6 = r6.append(r0);
            r23 = r6.toString();
            r0 = r23;
            android.util.Log.v(r8, r0);
            r0 = r28;
            r1 = r31;
            r0 = r0 - r1;
            r28 = r0;
            r17 = 0;
            r40 = 0;
            r0 = r29;
            r1 = r17;
            r2 = r26;
            r3 = r40;
            r4 = r28;
            java.lang.System.arraycopy(r0, r1, r2, r3, r4);
            r17 = 0;
            r26[r28] = r17;
            r0 = r45;
            r1 = r26;
            r29 = r0.getContentStart(r1);
            goto L_0x03fb;
        L_0x0454:
            r29 = 0;
        L_0x0456:
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            r27 = r0.isStopped();	 Catch:{ IOException -> 0x048c }
            if (r27 != 0) goto L_0x0498;
        L_0x0462:
            if (r29 != 0) goto L_0x0498;
        L_0x0464:
            r17 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;	 Catch:{ IOException -> 0x048c }
            r31 = r17 - r28;	 Catch:{ IOException -> 0x048c }
            r0 = r19;	 Catch:{ IOException -> 0x048c }
            r1 = r26;	 Catch:{ IOException -> 0x048c }
            r2 = r28;	 Catch:{ IOException -> 0x048c }
            r3 = r31;	 Catch:{ IOException -> 0x048c }
            r31 = r0.read(r1, r2, r3);	 Catch:{ IOException -> 0x048c }
            r16 = r31;
            if (r31 <= 0) goto L_0x0498;
        L_0x0478:
            r0 = r28;
            r1 = r31;
            r0 = r0 + r1;
            r28 = r0;
            r17 = 0;	 Catch:{ IOException -> 0x048c }
            r26[r28] = r17;	 Catch:{ IOException -> 0x048c }
            r0 = r45;	 Catch:{ IOException -> 0x048c }
            r1 = r26;	 Catch:{ IOException -> 0x048c }
            r29 = r0.getContentStart(r1);	 Catch:{ IOException -> 0x048c }
            goto L_0x0456;
        L_0x048c:
            r41 = move-exception;
            r0 = r45;
            r8 = r0.TAG;
            r7 = "ERROR reading from socket! ";
            r0 = r41;
            android.util.Log.e(r8, r7, r0);
        L_0x0498:
            if (r16 > 0) goto L_0x0398;
        L_0x049a:
            r0 = r45;
            r5 = r0.TAG;
            r7 = "BytesRead from socket < 0! Stopping(2).";
            android.util.Log.w(r5, r7);
            r0 = r45;
            r0 = r0.m_notification;
            r34 = r0;
            goto L_0x04ad;
        L_0x04aa:
            goto L_0x028a;
        L_0x04ad:
            r0.stop();
            goto L_0x04aa;
        L_0x04b1:
            if (r29 != 0) goto L_0x04c0;
        L_0x04b3:
            r0 = r45;
            r5 = r0.TAG;
            r7 = "NULL response, switching to empty response!";
            android.util.Log.v(r5, r7);
            r29 = com.abaltatech.wlappservices.WLServicesHTTPProxyServiceHandler.s_emptyResponse;
        L_0x04c0:
            r42 = new com.abaltatech.wlappservices.ServiceResponse;
            r0 = r42;
            r0.<init>();
            r0 = r45;
            r10 = r0.m_wrapper;
            r0 = r10.m_requestID;
            r16 = r0;
            r0 = r42;
            r1 = r16;
            r0.setRequestID(r1);
            r0 = r42;
            r1 = r29;
            r0.setResponseBody(r1);
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            r0 = r45;
            r10 = r0.m_wrapper;
            r12 = r10.m_request;
            goto L_0x04ed;
        L_0x04ea:
            goto L_0x028a;
        L_0x04ed:
            r0 = r42;
            r11.onResponseReceived(r12, r0);
            goto L_0x04ea;
        L_0x04f3:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            if (r11 == 0) goto L_0x028a;
        L_0x04fb:
            r0 = r45;
            r10 = r0.m_wrapper;
            r11 = r10.m_notification;
            r0 = r45;
            r10 = r0.m_wrapper;
            r12 = r10.m_request;
            r13 = com.abaltatech.wlappservices.EServiceErrorCode.UnsupportedRequest;
            goto L_0x050d;
        L_0x050a:
            goto L_0x028a;
        L_0x050d:
            r11.onRequestFailed(r12, r13);
            goto L_0x050a;
        L_0x0511:
            r0 = r45;
            r5 = r0.TAG;
            goto L_0x0519;
        L_0x0516:
            goto L_0x028a;
        L_0x0519:
            r7 = "socket failed isConnected check!";
            android.util.Log.w(r5, r7);
            goto L_0x0516;
        L_0x0520:
            r43 = move-exception;
            goto L_0x0525;
        L_0x0522:
            goto L_0x0291;
        L_0x0525:
            r0 = r43;
            r0.printStackTrace();
            goto L_0x0522;
        L_0x052b:
            r44 = move-exception;
            goto L_0x0530;
        L_0x052d:
            goto L_0x0298;
        L_0x0530:
            r0 = r44;
            r0.printStackTrace();
            goto L_0x052d;
            goto L_0x053a;
        L_0x0537:
            goto L_0x00e9;
        L_0x053a:
            r20 = move-exception;
            goto L_0x0537;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.WLServicesHTTPProxyServiceHandler.SocketRequestHandler.run():void");
        }

        public SocketRequestHandler(RequestWrapper $r2, String $r3, int $i0, String $r4, NotificationHandler $r5) throws  {
            this.m_wrapper = $r2;
            this.m_host = $r3;
            this.m_port = $i0;
            this.m_url = $r4;
            this.m_notification = $r5;
        }

        EResponseStatus getResponseStatus(byte[] $r1) throws  {
            EResponseStatus r4 = EResponseStatus.ERS_UNKNOWN;
            String $r2 = new String($r1);
            if (!$r2.contains("HTTP/1")) {
                return r4;
            }
            if ($r2.substring($r2.indexOf("HTTP/1") + "HTTP/1".length()).contains("200 OK")) {
                return EResponseStatus.ERS_OK;
            }
            return EResponseStatus.ERS_BAD;
        }

        final byte[] getContentStart(byte[] $r1) throws  {
            String $r2 = new String($r1);
            int $i0 = $r2.indexOf("\r\n\r\n");
            if ($i0 >= 0) {
                return $r2.substring("\r\n\r\n".length() + $i0).getBytes();
            }
            $i0 = $r2.indexOf("\n\n");
            return $i0 >= 0 ? $r2.substring("\n\n".length() + $i0).getBytes() : null;
        }
    }

    private class WLServicesHTTPProxyServiceHandlerThread extends Thread {
        private final String TAG;
        private boolean m_exitFlag;

        private WLServicesHTTPProxyServiceHandlerThread() throws  {
            this.TAG = WLServicesHTTPProxyServiceHandlerThread.class.getSimpleName();
            this.m_exitFlag = false;
        }

        public void cancel() throws  {
            this.m_exitFlag = true;
        }

        public void run() throws  {
            setName(this.TAG);
            Log.d(this.TAG, "START " + this.TAG);
            while (!this.m_exitFlag && !isInterrupted()) {
                RequestWrapper $r6 = null;
                synchronized (WLServicesHTTPProxyServiceHandler.this.m_lock) {
                    if (!WLServicesHTTPProxyServiceHandler.this.m_requestsQueue.isEmpty()) {
                        $r6 = (RequestWrapper) WLServicesHTTPProxyServiceHandler.this.m_requestsQueue.get(0);
                        WLServicesHTTPProxyServiceHandler.this.m_requestsQueue.remove(0);
                    }
                }
                if ($r6 != null) {
                    String $r3 = WLServicesHTTPProxyServiceHandler.this.findUrl($r6.m_resourcePath);
                    if ($r3 == null || $r3.isEmpty()) {
                        $r6.m_notification.onRequestFailed($r6.m_request, EServiceErrorCode.UnsupportedRequest);
                    } else {
                        Log.v(this.TAG, "Found request " + $r3);
                        WLServicesHTTPProxyServiceHandler $r7 = WLServicesHTTPProxyServiceHandler.this;
                        WLServicesHTTPProxyServiceHandler $r12 = WLServicesHTTPProxyServiceHandler.this;
                        String $r5 = $r12.m_remoteHost;
                        int $i0 = WLServicesHTTPProxyServiceHandler.this;
                        WLServicesHTTPProxyServiceHandler $r122 = $i0;
                        new SocketRequestHandler($r6, $r5, $i0.m_port, $r3, null).start();
                    }
                } else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException $r2) {
                        $r2.printStackTrace();
                    }
                }
            }
            Log.d(this.TAG, "STOP " + this.TAG);
        }
    }

    public WLServicesHTTPProxyServiceHandler(@Signature({"(", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r3) throws  {
        this.m_remoteHost = $r1;
        this.m_port = $i0;
        this.m_baseURL = $r2;
        this.m_resourceMapping = new HashMap($r3);
        this.m_thread = new WLServicesHTTPProxyServiceHandlerThread();
        this.m_thread.start();
    }

    public void terminate() throws  {
        this.m_thread.cancel();
    }

    public int onProcessRequest(String $r1, ServiceRequest $r2, IServiceResponseNotification $r3) throws  {
        int $i0;
        synchronized (this.m_lock) {
            $i0 = this.m_nextRequestID;
            this.m_nextRequestID++;
            RequestWrapper $r4 = new RequestWrapper($i0, $r2, $r1, $r3);
            this.m_pendingRequests.put($i0, $r4);
            this.m_requestsQueue.add($r4);
        }
        return $i0;
    }

    public boolean onCancelRequest(int $i0) throws  {
        boolean $z0;
        synchronized (this.m_lock) {
            $z0 = false;
            if (this.m_pendingRequests.get($i0) != null) {
                this.m_pendingRequests.remove($i0);
                $z0 = true;
            }
        }
        return $z0;
    }

    public void registerForNotification(String $r1, IServiceNotificationHandler $r2) throws  {
        String $r8 = findUrl($r1);
        if ($r8 != null && !$r8.isEmpty()) {
            NotificationPair $r6 = new NotificationPair($r1, $r2);
            NotificationHandler $r5 = new NotificationHandler($r1, $r2);
            ServiceRequest $r7 = new ServiceRequest();
            RequestWrapper $r4 = new RequestWrapper(-1, $r7, $r1, null);
            $r7.setRequestMethod(ERequestMethod.GET);
            new SocketRequestHandler($r4, this.m_remoteHost, this.m_port, $r8, $r5).start();
            synchronized (this.m_lock) {
                Map $r11 = this.m_notificationMap;
                $r11.put($r6, $r5);
            }
        }
    }

    public void unregisterFromNotification(String $r1, IServiceNotificationHandler $r2) throws  {
        NotificationPair $r3 = new NotificationPair($r1, $r2);
        synchronized (this.m_lock) {
            if (this.m_notificationMap.containsKey($r3)) {
                ((NotificationHandler) this.m_notificationMap.get($r3)).stop();
                this.m_notificationMap.remove($r3);
            }
        }
    }

    public void removeAllNotifications() throws  {
        synchronized (this.m_lock) {
            for (NotificationPair $r6 : this.m_notificationMap.keySet()) {
                ((NotificationHandler) this.m_notificationMap.get($r6)).stop();
            }
            this.m_notificationMap.clear();
        }
    }

    public String findUrl(String $r1) throws  {
        String $r4;
        if (this.m_resourceMapping.containsKey($r1)) {
            $r4 = (String) this.m_resourceMapping.get($r1);
            if ($r4.charAt(0) == '/') {
                $r4 = this.m_baseURL + $r4;
            }
        } else {
            $r4 = this.m_baseURL;
            if (!$r1.isEmpty()) {
                if ($r1.charAt(0) != '/') {
                    $r4 = $r4 + '/';
                }
                $r4 = $r4 + $r1;
            }
        }
        return $r4.trim();
    }
}
