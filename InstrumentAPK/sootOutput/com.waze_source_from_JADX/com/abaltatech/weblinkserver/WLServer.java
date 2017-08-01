package com.abaltatech.weblinkserver;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;
import com.abaltatech.mcs.logger.IMCSLogHandler;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.tcpip.TCPIPAddress;
import com.abaltatech.weblink.core.commandhandling.Command;
import com.abaltatech.weblink.core.commandhandling.ReconnectCommand;
import com.abaltatech.weblink.core.commandhandling.SetCurrentAppCommand;
import com.abaltatech.weblink.core.frameencoding.FrameEncoderFactory;
import com.abaltatech.weblink.servercore.WebLinkServerConnection;
import com.abaltatech.weblink.servercore.WebLinkServerCore;
import com.abaltatech.weblinkserver.IWLAppManager.Stub;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WLServer extends WebLinkServerCore {
    public static final String HOME_APP_ID = "wlhome_1.0://";
    public static int MIN_ANDROID_OS_SUPPORTED_VERSION = 16;
    private static final String TAG = "WLServer";
    private static WLServer ms_instance = null;
    private ConcurrentLinkedQueue<Activity> m_activityList = new ConcurrentLinkedQueue();
    private String m_appID = null;
    private IWLAppManager m_appManager = null;
    private ServiceConnection m_appManagerConn = new C04195();
    private BluetoothAdapter m_bluetoothAdapter = null;
    private UUID m_bluetoothServiceID = null;
    private BluetoothAcceptThread m_btAcceptThread = null;
    private String m_currentAppID = null;
    private String m_currentAppParams = null;
    private WLServerDelegate m_delegate = null;
    private ConcurrentLinkedQueue<IDisplayListener> m_displayListeners = new ConcurrentLinkedQueue();
    private boolean m_extendedLoggingEnabled = false;
    private int m_extendedLoggingPeriod = 10000;
    private Handler m_handler = null;
    private boolean m_isBroadcastingEnabled = true;
    private boolean m_isReady = false;
    private boolean m_isStarted = false;
    private List<WLLayer> m_layers = null;
    private int m_listenPort = -1;
    private IMCSLogHandler m_logger;
    private Runnable m_onCloseHandler = null;
    private IServerReadyListener m_readyListener = null;
    private ConcurrentHashMap<View, IViewHandler> m_registeredViews = new ConcurrentHashMap();
    private String m_serverName = null;
    private Display m_wlDisplay;

    public interface IDisplayListener {
        void onDisplayAdded(Display display) throws ;

        void onDisplayRemoved(Display display) throws ;
    }

    interface IViewHandler {
        void attach() throws ;

        void destroy() throws ;

        void detach() throws ;

        void draw(Canvas canvas, float f, float f2, float f3, float f4) throws ;
    }

    class C04162 implements IMCSLogHandler {
        C04162() throws  {
        }

        public void log(String $r1) throws  {
            Log.d(WLServer.TAG, $r1);
        }

        public void log(String $r1, String $r2) throws  {
            Log.d($r1, $r2);
        }

        public void log(String $r1, String $r2, Throwable $r3) throws  {
            Log.e($r1, $r2, $r3);
        }
    }

    class C04195 implements ServiceConnection {
        C04195() throws  {
        }

        public void onServiceConnected(ComponentName className, IBinder $r2) throws  {
            WLServer.this.m_appManager = Stub.asInterface($r2);
            WLServer.this.m_isReady = true;
            if (WLServer.this.m_readyListener != null) {
                WLServer.this.m_readyListener.onServerReady();
            }
        }

        public void onServiceDisconnected(ComponentName className) throws  {
            WLServer.this.m_appManager = null;
            WLServer.this.m_isReady = false;
        }
    }

    private class BluetoothAcceptThread extends Thread {
        private final BluetoothServerSocket m_serverSocket;

        public BluetoothAcceptThread(String $r2, BluetoothAdapter $r3, UUID $r4) throws  {
            BluetoothServerSocket bluetoothServerSocket = null;
            try {
                bluetoothServerSocket = $r3.listenUsingRfcommWithServiceRecord($r2, $r4);
            } catch (IOException e) {
            }
            this.m_serverSocket = bluetoothServerSocket;
        }

        public void run() throws  {
            if (this.m_serverSocket != null) {
                BluetoothSocket $r4;
                do {
                    try {
                        $r4 = this.m_serverSocket.accept();
                    } catch (IOException e) {
                        return;
                    }
                } while ($r4 == null);
                BluetoothLayer $r1 = new BluetoothLayer();
                if ($r1.attachBluetooth($r4)) {
                    WLServer.this.OnConnectionEstablished(null, null, $r1);
                    cancel();
                    return;
                }
                try {
                    $r4.close();
                } catch (Exception e2) {
                }
            }
        }

        public void cancel() throws  {
            if (this.m_serverSocket != null) {
                try {
                    this.m_serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public interface IServerReadyListener {
        void onServerReady() throws ;
    }

    public boolean start(java.lang.String r25, java.lang.String r26, int r27, android.bluetooth.BluetoothAdapter r28, java.util.UUID r29) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0063 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r24 = this;
        r0 = r24;
        r5 = r0.m_isStarted;
        r6 = android.os.Build.VERSION.SDK_INT;
        r7 = MIN_ANDROID_OS_SUPPORTED_VERSION;
        if (r6 >= r7) goto L_0x0026;
    L_0x000a:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Unsupported Android version: ";
        r8 = r8.append(r9);
        r8 = r8.append(r6);
        r25 = r8.toString();
        r9 = "WLServer";
        r0 = r25;
        com.abaltatech.mcs.logger.MCSLogger.log(r9, r0);
        r10 = 0;
        return r10;
    L_0x0026:
        r0 = r24;
        r11 = r0.m_isStarted;
        if (r11 != 0) goto L_0x0147;
    L_0x002c:
        r0 = r24;
        r11 = r0.m_isReady;
        if (r11 == 0) goto L_0x00ee;
    L_0x0032:
        r12 = new com.abaltatech.mcs.tcpip.TCPIPAddress;
        r0 = r27;
        r12.<init>(r0);
        r13 = new com.abaltatech.weblink.servercore.SocketConnListener;
        r0 = r24;
        r5 = r0.m_isBroadcastingEnabled;
        if (r5 == 0) goto L_0x0102;
    L_0x0041:
        r0 = r24;
        r14 = r0.getBroadcastAddress();
    L_0x0047:
        r0 = r24;
        r5 = r0.m_isBroadcastingEnabled;
        r10 = 51729; // 0xca11 float:7.2488E-41 double:2.55575E-319;
        r0 = r25;
        r13.<init>(r0, r14, r10, r5);
        r0 = r24;	 Catch:{ Exception -> 0x0104 }
        r11 = super.startServer(r13, r12);	 Catch:{ Exception -> 0x0104 }
        r5 = r11;
        if (r11 == 0) goto L_0x0063;	 Catch:{ Exception -> 0x0104 }
    L_0x005c:
        r15 = com.abaltatech.weblinkserver.WLTreeViewObserver.getInstance();	 Catch:{ Exception -> 0x0104 }
        r15.startMonitoring();	 Catch:{ Exception -> 0x0104 }
    L_0x0063:
        if (r5 == 0) goto L_0x011f;
    L_0x0065:
        r0 = r24;
        r0 = r0.m_appManager;
        r16 = r0;
        if (r16 == 0) goto L_0x0078;
    L_0x006d:
        r0 = r24;	 Catch:{ Exception -> 0x0112 }
        r0 = r0.m_appManager;	 Catch:{ Exception -> 0x0112 }
        r16 = r0;	 Catch:{ Exception -> 0x0112 }
        r1 = r26;	 Catch:{ Exception -> 0x0112 }
        r0.onAppReadyForClient(r1);	 Catch:{ Exception -> 0x0112 }
    L_0x0078:
        r0 = r24;
        r0 = r0.m_currentAppID;
        r17 = r0;
        if (r17 == 0) goto L_0x008c;
    L_0x0080:
        r0 = r24;
        r0 = r0.m_currentAppID;
        r17 = r0;
        r11 = r0.isEmpty();
        if (r11 == 0) goto L_0x009a;
    L_0x008c:
        r0 = r26;
        r1 = r24;
        r1.m_currentAppID = r0;
        r18 = 0;
        r0 = r18;
        r1 = r24;
        r1.m_currentAppParams = r0;
    L_0x009a:
        r0 = r27;
        r1 = r24;
        r1.m_listenPort = r0;
        goto L_0x00a4;
    L_0x00a1:
        goto L_0x0047;
    L_0x00a4:
        r0 = r26;
        r1 = r24;
        r1.m_appID = r0;
        r0 = r25;
        r1 = r24;
        r1.m_serverName = r0;
        r0 = r28;
        r1 = r24;
        r1.m_bluetoothAdapter = r0;
        goto L_0x00ba;
    L_0x00b7:
        goto L_0x0063;
    L_0x00ba:
        r0 = r29;
        r1 = r24;
        r1.m_bluetoothServiceID = r0;
        if (r28 == 0) goto L_0x00ee;
    L_0x00c2:
        r0 = r28;
        r11 = r0.isEnabled();
        if (r11 == 0) goto L_0x00ee;
    L_0x00ca:
        if (r29 == 0) goto L_0x00ee;
    L_0x00cc:
        goto L_0x00d0;
    L_0x00cd:
        goto L_0x0078;
    L_0x00d0:
        r19 = new com.abaltatech.weblinkserver.WLServer$BluetoothAcceptThread;
        r0 = r19;
        r1 = r24;
        r2 = r25;
        r3 = r28;
        r4 = r29;
        r0.<init>(r2, r3, r4);
        r0 = r19;
        r1 = r24;
        r1.m_btAcceptThread = r0;
        r0 = r24;
        r0 = r0.m_btAcceptThread;
        r19 = r0;
        r0.start();
    L_0x00ee:
        r0 = r24;
        r0.m_isStarted = r5;
        r0 = r24;
        r11 = r0.m_isStarted;
        if (r11 == 0) goto L_0x0144;
    L_0x00f8:
        r25 = "Server started successfully!";
    L_0x00fa:
        r9 = "WLServer";
        r0 = r25;
        com.abaltatech.mcs.logger.MCSLogger.log(r9, r0);
        return r5;
    L_0x0102:
        r14 = 0;
        goto L_0x00a1;
    L_0x0104:
        r20 = move-exception;
        r9 = "WLServer";
        r21 = "Error detected during server start";
        r0 = r21;
        r1 = r20;
        com.abaltatech.mcs.logger.MCSLogger.log(r9, r0, r1);
        r5 = 0;
        goto L_0x00b7;
    L_0x0112:
        r22 = move-exception;
        r9 = "WLServer";
        r21 = "Error detected:";
        r0 = r21;
        r1 = r22;
        com.abaltatech.mcs.logger.MCSLogger.log(r9, r0, r1);
        goto L_0x00cd;
    L_0x011f:
        r0 = r24;
        r0 = r0.m_appManager;
        r16 = r0;
        if (r16 == 0) goto L_0x00ee;
    L_0x0127:
        r0 = r24;	 Catch:{ Exception -> 0x0137 }
        r0 = r0.m_appManager;	 Catch:{ Exception -> 0x0137 }
        r16 = r0;	 Catch:{ Exception -> 0x0137 }
        r18 = 0;	 Catch:{ Exception -> 0x0137 }
        r0 = r16;	 Catch:{ Exception -> 0x0137 }
        r1 = r18;	 Catch:{ Exception -> 0x0137 }
        r0.onAppReadyForClient(r1);	 Catch:{ Exception -> 0x0137 }
        goto L_0x00ee;
    L_0x0137:
        r23 = move-exception;
        r9 = "WLServer";
        r21 = "Error detected:";
        r0 = r21;
        r1 = r23;
        com.abaltatech.mcs.logger.MCSLogger.log(r9, r0, r1);
        goto L_0x00ee;
    L_0x0144:
        r25 = "Server filed to start!";
        goto L_0x00fa;
    L_0x0147:
        r9 = "WLServer";
        r21 = "Server already started !";
        r0 = r21;
        com.abaltatech.mcs.logger.MCSLogger.log(r9, r0);
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLServer.start(java.lang.String, java.lang.String, int, android.bluetooth.BluetoothAdapter, java.util.UUID):boolean");
    }

    public static boolean isOSSupported() throws  {
        return VERSION.SDK_INT >= MIN_ANDROID_OS_SUPPORTED_VERSION;
    }

    public static synchronized WLServer getInstance() throws  {
        Class cls = WLServer.class;
        synchronized (cls) {
            try {
                if (ms_instance == null) {
                    ms_instance = new WLServer();
                }
                WLServer $r0 = ms_instance;
                return $r0;
            } finally {
                cls = WLServer.class;
            }
        }
    }

    public static String getVersion() throws  {
        return WLVersionInfo.VERSION;
    }

    public static void init(Context $r0) throws  {
        WLServerApp.setContext($r0);
    }

    public void stop() throws  {
        WLTreeViewObserver.getInstance().stopMonitoring();
        super.stopServer();
        if (this.m_btAcceptThread != null) {
            this.m_btAcceptThread.cancel();
            this.m_btAcceptThread = null;
        }
        this.m_serverName = null;
        this.m_bluetoothAdapter = null;
        this.m_bluetoothServiceID = null;
        this.m_appID = null;
        this.m_listenPort = -1;
        this.m_isStarted = false;
        MCSLogger.log(TAG, "Server stopped !");
    }

    public boolean isReady() throws  {
        return this.m_isReady;
    }

    public int getListenPort() throws  {
        int $i0 = this.m_listenPort;
        if (this.m_address == null || !(this.m_address instanceof TCPIPAddress)) {
            return $i0;
        }
        return ((TCPIPAddress) this.m_address).getPort();
    }

    public void registerNonCacheView(@Signature({"(", "Ljava/lang/Class", "<+", "Landroid/view/View;", ">;)V"}) Class<? extends View> $r1) throws  {
        WLMirrorLayer.getInstance().registerNonCacheView($r1);
    }

    public boolean isStarted() throws  {
        return this.m_isStarted;
    }

    public boolean isConnected() throws  {
        return getConnection() != null;
    }

    public boolean pauseVideo() throws  {
        WLServerConnection $r1 = getConnection();
        if ($r1 != null) {
            return $r1.pauseVideo();
        }
        return false;
    }

    public boolean resumeVideo() throws  {
        WLServerConnection $r1 = getConnection();
        if ($r1 != null) {
            return $r1.resumeVideo();
        }
        return false;
    }

    public boolean isVideoPaused() throws  {
        WLServerConnection $r1 = getConnection();
        return $r1 != null ? $r1.isVideoPaused() : false;
    }

    public WLServerDelegate getDelegate() throws  {
        return this.m_delegate;
    }

    public void setDelegate(WLServerDelegate $r1) throws  {
        this.m_delegate = $r1;
    }

    public void setServerReadyListener(IServerReadyListener $r1) throws  {
        this.m_readyListener = $r1;
        if ($r1 != null && this.m_isReady) {
            $r1.onServerReady();
        }
    }

    public void setSize(int $i0, int $i1) throws  {
        WLServerConnection $r1 = getConnection();
        if ($r1 != null) {
            $r1.setSize($i0, $i1);
        }
    }

    public List<WLLayer> getLayers() throws  {
        return this.m_layers;
    }

    public void addLayer(WLLayer $r1) throws  {
        this.m_layers.add($r1);
    }

    public void removeLayer(WLLayer $r1) throws  {
        do {
        } while (this.m_layers.remove($r1));
    }

    public void clearLayers() throws  {
        this.m_layers.clear();
    }

    public void raiseEvent(Command $r1) throws  {
        if (this.m_connection != null) {
            this.m_connection.sendCommand($r1);
        }
    }

    public void setCurrentApp(String $r1, String $r2) throws  {
        if ($r1 == null) {
            return;
        }
        if ($r1.startsWith(this.m_appID)) {
            this.m_currentAppID = $r1;
            this.m_currentAppParams = $r2;
            if (this.m_connection != null) {
                this.m_connection.sendCommand(new SetCurrentAppCommand($r1, $r2));
                return;
            }
            return;
        }
        switchToApp($r1, $r2);
    }

    public void switchToApp(String $r1, String $r2) throws  {
        if (!($r2 == null || $r2.isEmpty())) {
            $r1 = $r1 + $r2;
        }
        if (this.m_appManager != null) {
            try {
                if (!this.m_appManager.canActivateApp($r1)) {
                    Toast.makeText(WLServerApp.getAppContext(), "Application not installed.", 1).show();
                } else if (this.m_connection != null) {
                    this.m_onCloseHandler = new Runnable() {
                        public void run() throws  {
                            if (WLServer.this.m_appManager != null && WLServer.this.m_isStarted) {
                                boolean $z0 = false;
                                String $r4 = WLServer.this.m_serverName;
                                BluetoothAdapter $r5 = WLServer.this.m_bluetoothAdapter;
                                UUID $r6 = WLServer.this.m_bluetoothServiceID;
                                String $r7 = WLServer.this.m_appID;
                                int $i0 = WLServer.this.m_listenPort;
                                WLServer.this.stop();
                                try {
                                    $z0 = WLServer.this.m_appManager.activateApp($r1);
                                } catch (Exception $r1) {
                                    MCSLogger.log(WLServer.TAG, "Error detected:", $r1);
                                }
                                if (!$z0) {
                                    WLServer.this.start($r4, $r7, $i0, $r5, $r6);
                                }
                            }
                        }
                    };
                    this.m_connection.sendCommand(new ReconnectCommand());
                }
            } catch (Exception $r3) {
                MCSLogger.log(TAG, "Error detected:", $r3);
            }
        }
    }

    public void switchToHomeApp() throws  {
        if (!this.m_appID.contentEquals(HOME_APP_ID)) {
            switchToApp(HOME_APP_ID, null);
        }
    }

    public String getAppID() throws  {
        return this.m_appID;
    }

    public String getActiveAppID() throws  {
        if (this.m_appManager == null) {
            return null;
        }
        try {
            return this.m_appManager.getCurrentAppID();
        } catch (Exception $r1) {
            MCSLogger.log(TAG, "Error detected:", $r1);
            return null;
        }
    }

    public boolean registerView(View $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if (WLServerApp.getMode() != 1) {
            return false;
        }
        if (this.m_registeredViews.contains($r1)) {
            return false;
        }
        WLVideoViewHandler $r3 = null;
        if ($r1 instanceof VideoView) {
            try {
                $r3 = new WLVideoViewHandler((VideoView) $r1);
            } catch (Exception $r2) {
                MCSLogger.log(TAG, "registerView()", $r2);
                return false;
            }
        }
        if ($r3 == null) {
            return false;
        }
        this.m_registeredViews.put($r1, $r3);
        return true;
    }

    public boolean unregisterView(View $r1) throws  {
        IViewHandler $r5 = (IViewHandler) this.m_registeredViews.remove($r1);
        if ($r5 == null) {
            return false;
        }
        try {
            $r5.destroy();
            return false;
        } catch (Exception $r2) {
            MCSLogger.log(TAG, "unregisterView()", $r2);
            return false;
        }
    }

    public IViewHandler getViewHandler(View $r1) throws  {
        return (IViewHandler) this.m_registeredViews.get($r1);
    }

    public void registerDisplayListener(IDisplayListener $r1) throws  {
        if ($r1 != null) {
            this.m_displayListeners.remove($r1);
            this.m_displayListeners.add($r1);
        }
    }

    public void unregisterDisplayListener(IDisplayListener $r1) throws  {
        this.m_displayListeners.remove($r1);
    }

    public synchronized void registerActivity(Activity $r1) throws  {
        if ($r1 != null) {
            this.m_activityList.remove($r1);
            this.m_activityList.add($r1);
        }
    }

    public synchronized void unregisterActivity(Activity $r1) throws  {
        this.m_activityList.remove($r1);
    }

    public Display getWLDisplay() throws  {
        return this.m_wlDisplay;
    }

    public boolean isLoggingEnabled() throws  {
        return this.m_logger != null;
    }

    public synchronized void setLoggingEnabled(boolean $z0) throws  {
        if ((this.m_logger != null) != $z0) {
            if (this.m_logger != null) {
                MCSLogger.unregisterLogger(this.m_logger);
                this.m_logger = null;
            } else {
                this.m_logger = new C04162();
                MCSLogger.registerLogger(this.m_logger);
                MCSLogger.log(TAG, "Version: " + WLVersionInfo.VERSION + ", built on " + WLVersionInfo.BUILD_DATE);
            }
        }
    }

    void notifyWLDisplayAdded(final Display $r1) throws  {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLServer.this.m_wlDisplay = $r1;
                Iterator $r5 = WLServer.this.m_displayListeners.iterator();
                while ($r5.hasNext()) {
                    try {
                        ((IDisplayListener) $r5.next()).onDisplayAdded($r1);
                    } catch (Exception $r1) {
                        MCSLogger.log(WLServer.TAG, "notifyWLDisplayAdded()", $r1);
                    }
                }
            }
        });
    }

    void notifyWLDisplayRemoved(final Display $r1) throws  {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                Iterator $r4 = WLServer.this.m_displayListeners.iterator();
                while ($r4.hasNext()) {
                    try {
                        ((IDisplayListener) $r4.next()).onDisplayRemoved($r1);
                    } catch (Exception $r1) {
                        MCSLogger.log(WLServer.TAG, "notifyWLDisplayRemoved()", $r1);
                    }
                }
                WLServer.this.m_wlDisplay = null;
            }
        });
    }

    private WLServer() throws  {
        boolean $z0 = true;
        if (isOSSupported()) {
            FrameEncoderFactory $r5 = FrameEncoderFactory.getInstance();
            $r5.registerEncoder(1, FrameEncoderI420.class);
            $r5.registerEncoder(4, FrameEncoderXOR.class);
            if (FrameEncoderH264.isSupported()) {
                $r5.registerEncoder(2, FrameEncoderH264.class);
            }
            this.m_handler = new Handler();
            this.m_layers = new ArrayList();
            Context $r8 = WLServerApp.getAppContext();
            if ($r8.bindService(new Intent($r8, WLAppManagerService.class), this.m_appManagerConn, 1)) {
                $z0 = false;
            }
            this.m_isReady = $z0;
            setExtendedLoggingEnabled(false);
            return;
        }
        this.m_isReady = false;
    }

    protected WebLinkServerConnection createConnection() throws  {
        return new WLServerConnection(this.m_delegate, this.m_layers);
    }

    protected void onConnectionEstablished(WebLinkServerConnection $r1) throws  {
        super.onConnectionEstablished($r1);
        this.m_onCloseHandler = null;
        $r1.sendCommand(new SetCurrentAppCommand(this.m_currentAppID, this.m_currentAppParams));
    }

    public void onConnectionClosed(WebLinkServerConnection $r1) throws  {
        super.onConnectionClosed($r1);
        if (!(this.m_btAcceptThread == null || this.m_btAcceptThread.isAlive())) {
            this.m_btAcceptThread = new BluetoothAcceptThread(this.m_serverName, this.m_bluetoothAdapter, this.m_bluetoothServiceID);
            this.m_btAcceptThread.start();
        }
        if (this.m_onCloseHandler != null) {
            Runnable $r2 = this.m_onCloseHandler;
            this.m_onCloseHandler = null;
            this.m_handler.post($r2);
        }
    }

    private WLServerConnection getConnection() throws  {
        try {
            return (WLServerConnection) this.m_connection;
        } catch (ClassCastException e) {
            return null;
        }
    }

    private InetAddress getBroadcastAddress() throws  {
        try {
            NetworkInterface $r2 = NetworkInterface.getByName("wlan0");
            if ($r2 == null) {
                return null;
            }
            for (InterfaceAddress broadcast : $r2.getInterfaceAddresses()) {
                InetAddress $r7 = broadcast.getBroadcast();
                if ($r7 instanceof Inet4Address) {
                    return $r7;
                }
            }
            return null;
        } catch (SocketException $r1) {
            MCSLogger.log(TAG, "Can't identify broadcast address", $r1);
            return null;
        }
    }

    public boolean getIsBroadcastingEnabled() throws  {
        return this.m_isBroadcastingEnabled;
    }

    public void setIsBroadcastingEnabled(boolean $z0) throws  {
        this.m_isBroadcastingEnabled = $z0;
    }

    public void setExtendedLoggingEnabled(boolean $z0) throws  {
        this.m_extendedLoggingEnabled = $z0;
    }

    public boolean getExtendedLoggingEnabled() throws  {
        return this.m_extendedLoggingEnabled;
    }

    public int getExtendedLoggingPeriod() throws  {
        return this.m_extendedLoggingPeriod;
    }

    public void setExtendedLoggingPeriod(int $i0) throws  {
        this.m_extendedLoggingPeriod = $i0;
    }
}
