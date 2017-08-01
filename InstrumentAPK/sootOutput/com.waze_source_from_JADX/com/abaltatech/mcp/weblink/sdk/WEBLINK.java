package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowId;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.VideoView;
import com.abaltatech.mcp.weblink.core.DataBuffer;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.abaltatech.mcp.weblink.core.commandhandling.Command;
import com.abaltatech.mcp.weblink.core.commandhandling.HideKeyboardCommand;
import com.abaltatech.mcp.weblink.core.commandhandling.ShowKeyboardCommand;
import com.abaltatech.mcp.weblink.core.commandhandling.WaitIndicatorCommand;
import com.abaltatech.mcp.weblink.sdk.internal.IWLInternalHomeApp;
import com.abaltatech.mcp.weblink.sdk.widgets.WLAlertDialog;
import com.abaltatech.mcp.weblink.servercore.WebLinkServerCore;
import com.abaltatech.mcp.weblink.utils.AppUtils;
import com.abaltatech.weblink.service.interfaces.IWLApp;
import com.abaltatech.weblink.service.interfaces.IWLAppNotification;
import com.abaltatech.weblink.service.interfaces.IWLInputConnection;
import com.abaltatech.weblink.service.interfaces.IWLKeyboardManager;
import com.abaltatech.weblink.service.interfaces.IWLService;
import com.abaltatech.weblink.service.interfaces.IWLService.Stub;
import com.abaltatech.weblink.service.interfaces.IWLServicePrivate;
import com.abaltatech.weblink.service.interfaces.WLInputEventParcelable;
import com.abaltatech.wlappservices.SecureServiceManager;
import com.abaltatech.wlappservices.ServiceDispatcher;
import com.abaltatech.wlappservices.ServiceDispatcher.ServiceDispatcherType;
import com.abaltatech.wlappservices.ServiceManager;
import com.google.android.gms.auth.firstparty.recovery.RecoveryParamConstants;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WEBLINK {
    private static final int AIDL_IWLAPPNOTIFICATION_REVISION = 1;
    private static final String HOME_APP_ID = "wlhome://";
    public static final int KBD_MODE_CONDENSED = 1;
    public static final int KBD_MODE_DEFAULT = 0;
    public static final int KBD_MODE_FULL_SCREEN = 0;
    public static final int KBD_MODE_SYSTEM = 2;
    private static final String TAG = "WEBLINK";
    public static final int UI_MODE_CUSTOM = 0;
    private static final int UI_MODE_MAX = 3;
    private static final int UI_MODE_MIN = 0;
    public static final int UI_MODE_MIRROR = 1;
    public static final int UI_MODE_OFFSCREEN = 3;
    public static final int UI_MODE_PRESENTATION = 2;
    public static final WEBLINK instance = new WEBLINK();
    private IWLApp m_app;
    private CommThread m_commThread;
    private final ConcurrentLinkedQueue<ICommandHandler> m_commandHandlers = new ConcurrentLinkedQueue();
    private final HashSet<Integer> m_commands = new HashSet();
    private final ServiceConnection m_connection = new C03202();
    private final ConcurrentLinkedQueue<IConnectionListener> m_connectionListeners = new ConcurrentLinkedQueue();
    private Context m_context;
    private ICustomUiHandler m_customHandler;
    private final ExecutorService m_executor = Executors.newSingleThreadExecutor();
    private final Handler m_handler = new Handler();
    private IWLInternalHomeApp m_homeApp = null;
    private boolean m_isActive;
    private boolean m_isBound;
    private IWLKeyboardManager m_kbdManager;
    private int m_keyboardMode = 0;
    private View m_keyboardView;
    private MemoryBoss m_memoryBoss = null;
    private final IWLAppNotification m_notification = new C03264();
    private boolean m_pendingActivation;
    private final ServiceConnection m_privateConnection = new C03213();
    private ConcurrentHashMap<View, IViewHandler> m_registeredViews = new ConcurrentHashMap();
    private final Runnable m_renderView = new C03275();
    private IWLService m_service;
    private IWLServicePrivate m_servicePrivate;
    private Surface m_surface;
    private final TextViewNotification m_textViewNotification = new C03286();
    private int m_uiMode = 1;
    private ConcurrentHashMap<EWLUIParam, Object> m_uiParams = new ConcurrentHashMap();
    private WLDisplay m_virtualDisplay;

    class C03202 implements ServiceConnection {
        C03202() throws  {
        }

        public void onServiceConnected(ComponentName className, IBinder $r2) throws  {
            this = this;
            WEBLINK.this.m_service = Stub.asInterface($r2);
            try {
                String $r8;
                C03202 $r3;
                Iterator $r13;
                int $i0 = Process.myPid();
                String $r6 = WEBLINK.getAppNameByPID(WEBLINK.this.m_context, $i0);
                this = this;
                if (WEBLINK.this.m_homeApp != null) {
                    this = this;
                    if (!WEBLINK.this.m_homeApp.isService()) {
                        $r8 = WEBLINK.HOME_APP_ID;
                        $r3 = this;
                        this = $r3;
                        WEBLINK.this.m_app = WEBLINK.this.m_service.registerApplication(WEBLINK.this.m_notification, $r6, $i0, $r8);
                        $r3 = this;
                        this = $r3;
                        $r13 = WEBLINK.this.m_commands.iterator();
                        while ($r13.hasNext()) {
                            $r3 = this;
                            this = $r3;
                            WEBLINK.this.m_app.registerForCommand(((Integer) $r13.next()).intValue());
                        }
                        $r3 = this;
                        $r3 = $r3;
                        this = $r3;
                        WEBLINK.this.m_app.setKeyboardMode(WEBLINK.this.m_keyboardMode);
                        $r3 = this;
                        this = $r3;
                        if (WEBLINK.this.m_pendingActivation) {
                            $r3 = this;
                            this = $r3;
                            WEBLINK.this.requestActivation();
                        }
                    }
                }
                $r8 = "";
                $r3 = this;
                this = $r3;
                WEBLINK.this.m_app = WEBLINK.this.m_service.registerApplication(WEBLINK.this.m_notification, $r6, $i0, $r8);
                $r3 = this;
                this = $r3;
                $r13 = WEBLINK.this.m_commands.iterator();
                while ($r13.hasNext()) {
                    $r3 = this;
                    this = $r3;
                    WEBLINK.this.m_app.registerForCommand(((Integer) $r13.next()).intValue());
                }
                $r3 = this;
                $r3 = $r3;
                this = $r3;
                WEBLINK.this.m_app.setKeyboardMode(WEBLINK.this.m_keyboardMode);
                $r3 = this;
                this = $r3;
                if (WEBLINK.this.m_pendingActivation) {
                    $r3 = this;
                    this = $r3;
                    WEBLINK.this.requestActivation();
                }
            } catch (RemoteException e) {
            }
        }

        public void onServiceDisconnected(ComponentName className) throws  {
            WEBLINK.this.m_service = null;
            WEBLINK.this.m_app = null;
            if (WEBLINK.this.m_surface != null) {
                try {
                    WEBLINK.this.m_notification.onRenderStop();
                } catch (RemoteException e) {
                }
            }
            if (WEBLINK.this.m_isActive) {
                try {
                    WEBLINK.this.m_notification.onDeactivated();
                } catch (RemoteException e2) {
                }
            }
        }
    }

    class C03213 implements ServiceConnection {
        C03213() throws  {
        }

        public void onServiceConnected(ComponentName className, IBinder $r2) throws  {
            WEBLINK.this.m_servicePrivate = IWLServicePrivate.Stub.asInterface($r2);
            try {
                WEBLINK.this.m_kbdManager = WEBLINK.this.m_servicePrivate.getKeyboardManager();
            } catch (RemoteException $r3) {
                WEBLINK.this.m_kbdManager = null;
                Log.e("WEBLINK", "onServiceConnected", $r3);
            }
        }

        public void onServiceDisconnected(ComponentName className) throws  {
            WEBLINK.this.m_servicePrivate = null;
            WEBLINK.this.m_kbdManager = null;
        }
    }

    class C03264 extends IWLAppNotification.Stub {

        class C03232 implements Runnable {
            public void run() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:15:0x0077 in {5, 8, 13, 14, 16, 21, 22, 41, 43, 46} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r26 = this;
                r0 = r26;
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;
                r5 = r4.m_handler;
                r0 = r26;
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;
                r6 = r4.m_renderView;
                r5.removeCallbacks(r6);
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r7 = r4.m_uiMode;	 Catch:{ Exception -> 0x00c0 }
                switch(r7) {
                    case 0: goto L_0x007b;
                    case 1: goto L_0x00cb;
                    case 2: goto L_0x0101;
                    case 3: goto L_0x00cb;
                    default: goto L_0x0024;
                };	 Catch:{ Exception -> 0x00c0 }
            L_0x0024:
                goto L_0x0025;	 Catch:{ Exception -> 0x00c0 }
            L_0x0025:
                r8 = "WEBLINK";	 Catch:{ Exception -> 0x00c0 }
                r9 = "Invalid UI mode is set";	 Catch:{ Exception -> 0x00c0 }
                android.util.Log.e(r8, r9);	 Catch:{ Exception -> 0x00c0 }
            L_0x002c:
                r0 = r26;
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;
                r10 = r4.m_surface;
                if (r10 == 0) goto L_0x004f;
            L_0x0038:
                r0 = r26;
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;
                r10 = r4.m_surface;
                r10.release();
                r0 = r26;
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;
                r11 = 0;
                r4.m_surface = r11;
            L_0x004f:
                r0 = r26;
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;
                r12 = r4.m_connectionListeners;
                r13 = r12.iterator();
            L_0x005d:
                r14 = r13.hasNext();
                if (r14 == 0) goto L_0x014a;
            L_0x0063:
                r15 = r13.next();
                r17 = r15;
                r17 = (com.abaltatech.mcp.weblink.sdk.WEBLINK.IConnectionListener) r17;
                r16 = r17;
                goto L_0x0071;
            L_0x006e:
                goto L_0x002c;
            L_0x0071:
                r0 = r16;
                r0.onClientDisconnected();
                goto L_0x005d;
                goto L_0x007b;
            L_0x0078:
                goto L_0x002c;
            L_0x007b:
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r18 = r4.m_virtualDisplay;	 Catch:{ Exception -> 0x00c0 }
                if (r18 == 0) goto L_0x00a4;	 Catch:{ Exception -> 0x00c0 }
            L_0x0087:
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r18 = r4.m_virtualDisplay;	 Catch:{ Exception -> 0x00c0 }
                goto L_0x0095;	 Catch:{ Exception -> 0x00c0 }
            L_0x0092:
                goto L_0x002c;	 Catch:{ Exception -> 0x00c0 }
            L_0x0095:
                r0 = r18;	 Catch:{ Exception -> 0x00c0 }
                r0.release();	 Catch:{ Exception -> 0x00c0 }
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r11 = 0;	 Catch:{ Exception -> 0x00c0 }
                r4.m_virtualDisplay = r11;	 Catch:{ Exception -> 0x00c0 }
            L_0x00a4:
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r19 = r4.m_customHandler;	 Catch:{ Exception -> 0x00c0 }
                if (r19 == 0) goto L_0x002c;	 Catch:{ Exception -> 0x00c0 }
            L_0x00b0:
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r19 = r4.m_customHandler;	 Catch:{ Exception -> 0x00c0 }
                r0 = r19;	 Catch:{ Exception -> 0x00c0 }
                r0.onUiDestroy();	 Catch:{ Exception -> 0x00c0 }
                goto L_0x006e;
            L_0x00c0:
                r20 = move-exception;
                r8 = "WEBLINK";
                r9 = "Error stopping rendering";
                r0 = r20;
                android.util.Log.e(r8, r9, r0);
                goto L_0x0078;
            L_0x00cb:
                r21 = com.abaltatech.mcp.weblink.sdk.WLTreeViewObserver.getInstance();	 Catch:{ Exception -> 0x00c0 }
                r0 = r21;	 Catch:{ Exception -> 0x00c0 }
                r0.stopMonitoring();	 Catch:{ Exception -> 0x00c0 }
                r22 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x00c0 }
                r0 = r22;	 Catch:{ Exception -> 0x00c0 }
                r0.terminate();	 Catch:{ Exception -> 0x00c0 }
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r18 = r4.m_virtualDisplay;	 Catch:{ Exception -> 0x00c0 }
                if (r18 == 0) goto L_0x002c;	 Catch:{ Exception -> 0x00c0 }
            L_0x00e7:
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r18 = r4.m_virtualDisplay;	 Catch:{ Exception -> 0x00c0 }
                r0 = r18;	 Catch:{ Exception -> 0x00c0 }
                r0.release();	 Catch:{ Exception -> 0x00c0 }
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r11 = 0;	 Catch:{ Exception -> 0x00c0 }
                r4.m_virtualDisplay = r11;	 Catch:{ Exception -> 0x00c0 }
                goto L_0x0092;
            L_0x0101:
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r18 = r4.m_virtualDisplay;	 Catch:{ Exception -> 0x00c0 }
                if (r18 == 0) goto L_0x002c;
            L_0x010d:
                r7 = android.os.Build.VERSION.SDK_INT;
                r23 = 19;	 Catch:{ Exception -> 0x00c0 }
                r0 = r23;	 Catch:{ Exception -> 0x00c0 }
                if (r7 != r0) goto L_0x011c;	 Catch:{ Exception -> 0x00c0 }
            L_0x0115:
                r24 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ Exception -> 0x00c0 }
                r0 = r24;	 Catch:{ Exception -> 0x00c0 }
                java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x00c0 }
            L_0x011c:
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r18 = r4.m_virtualDisplay;	 Catch:{ Exception -> 0x00c0 }
                r0 = r18;	 Catch:{ Exception -> 0x00c0 }
                r0.release();	 Catch:{ Exception -> 0x00c0 }
                r0 = r26;	 Catch:{ Exception -> 0x00c0 }
                r3 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x00c0 }
                r4 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x00c0 }
                r11 = 0;	 Catch:{ Exception -> 0x00c0 }
                r4.m_virtualDisplay = r11;	 Catch:{ Exception -> 0x00c0 }
                r21 = com.abaltatech.mcp.weblink.sdk.WLTreeViewObserver.getInstance();	 Catch:{ Exception -> 0x00c0 }
                r0 = r21;	 Catch:{ Exception -> 0x00c0 }
                r0.stopMonitoring();	 Catch:{ Exception -> 0x00c0 }
                r22 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x00c0 }
                goto L_0x0144;	 Catch:{ Exception -> 0x00c0 }
            L_0x0141:
                goto L_0x002c;	 Catch:{ Exception -> 0x00c0 }
            L_0x0144:
                r0 = r22;	 Catch:{ Exception -> 0x00c0 }
                r0.terminate();	 Catch:{ Exception -> 0x00c0 }
                goto L_0x0141;
            L_0x014a:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WEBLINK.4.2.run():void");
            }

            C03232() throws  {
            }
        }

        public int getInterfaceRevision() throws RemoteException {
            return 1;
        }

        C03264() throws  {
        }

        public void onActivated(int $i0) throws RemoteException {
            Log.d("WEBLINK", "onActivated");
            WEBLINK.this.m_isActive = true;
            WEBLINK.this.m_commThread = new CommThread($i0);
            WEBLINK.this.m_commThread.start();
        }

        public void onDeactivated() throws RemoteException {
            CommThread $r1 = WEBLINK.this.m_commThread;
            Log.d("WEBLINK", "onDeactivated");
            if ($r1 != null) {
                WEBLINK.this.m_commThread = null;
                $r1.terminate();
            }
            WEBLINK.this.m_isActive = false;
        }

        public void onRenderStart(Surface $r1, final int $i0, final int $i1) throws RemoteException {
            WEBLINK.this.m_keyboardView = null;
            WEBLINK.this.m_surface = $r1;
            WEBLINK.this.m_handler.post(new Runnable() {
                public void run() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:87:0x0206
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r70 = this;
                    r0 = r70;
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;
                    r8 = r7.m_connectionListeners;
                    r9 = r8.iterator();
                L_0x000e:
                    r10 = r9.hasNext();
                    if (r10 == 0) goto L_0x0020;
                L_0x0014:
                    r11 = r9.next();
                    r13 = r11;
                    r13 = (com.abaltatech.mcp.weblink.sdk.WEBLINK.IConnectionListener) r13;
                    r12 = r13;
                    r12.onClientConnected();
                    goto L_0x000e;
                L_0x0020:
                    r14 = com.abaltatech.mcp.weblink.sdk.WLDisplayManager.getInstance();	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r15 = r7.getContext();	 Catch:{ Exception -> 0x02ba }
                    r16 = r15.getResources();	 Catch:{ Exception -> 0x02ba }
                    r0 = r16;	 Catch:{ Exception -> 0x02ba }
                    r17 = r0.getDisplayMetrics();	 Catch:{ Exception -> 0x02ba }
                    r18 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
                    r19 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r20 = r7.getClientFeaturesDict();	 Catch:{ Exception -> 0x02ba }
                    r21 = "xdpi";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r10 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    if (r10 == 0) goto L_0x006a;
                L_0x0053:
                    r21 = "xdpi";	 Catch:{ NumberFormatException -> 0x05c2 }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x05c2 }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x05c2 }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x05c2 }
                    r23 = r11;	 Catch:{ NumberFormatException -> 0x05c2 }
                    r23 = (java.lang.String) r23;	 Catch:{ NumberFormatException -> 0x05c2 }
                    r22 = r23;	 Catch:{ NumberFormatException -> 0x05c2 }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x05c2 }
                    r18 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x05c2 }
                L_0x006a:
                    r21 = "ydpi";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r10 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    if (r10 == 0) goto L_0x008e;
                L_0x0077:
                    r21 = "ydpi";	 Catch:{ NumberFormatException -> 0x05bc }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x05bc }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x05bc }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x05bc }
                    r24 = r11;	 Catch:{ NumberFormatException -> 0x05bc }
                    r24 = (java.lang.String) r24;	 Catch:{ NumberFormatException -> 0x05bc }
                    r22 = r24;	 Catch:{ NumberFormatException -> 0x05bc }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x05bc }
                    r19 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x05bc }
                L_0x008e:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r25 = com.abaltatech.mcp.weblink.sdk.WEBLINK.EWLUIParam.UIP_KEEP_PHONE_DPI;	 Catch:{ Exception -> 0x02ba }
                    r0 = r25;	 Catch:{ Exception -> 0x02ba }
                    r26 = r7.getUIParamInt(r0);	 Catch:{ Exception -> 0x02ba }
                    if (r26 != 0) goto L_0x0232;
                L_0x009e:
                    r10 = 1;	 Catch:{ Exception -> 0x02ba }
                L_0x009f:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r25 = com.abaltatech.mcp.weblink.sdk.WEBLINK.EWLUIParam.UIP_SCALE_APP_CANVAS_TO_DPI_MODE;	 Catch:{ Exception -> 0x02ba }
                    r0 = r25;	 Catch:{ Exception -> 0x02ba }
                    r26 = r7.getUIParamInt(r0);	 Catch:{ Exception -> 0x02ba }
                    r27 = 1;
                    r0 = r26;
                    r1 = r27;
                    if (r0 != r1) goto L_0x0238;
                L_0x00b5:
                    r28 = 1;	 Catch:{ Exception -> 0x02ba }
                L_0x00b7:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r25 = com.abaltatech.mcp.weblink.sdk.WEBLINK.EWLUIParam.UIP_MAX_APP_CANVAS_SCALE_FACTOR_PERCENT;	 Catch:{ Exception -> 0x02ba }
                    r0 = r25;	 Catch:{ Exception -> 0x02ba }
                    r26 = r7.getUIParamInt(r0);	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r25 = com.abaltatech.mcp.weblink.sdk.WEBLINK.EWLUIParam.UIP_ENABLE_STATIC_TEXTUREBITMAP;	 Catch:{ Exception -> 0x02ba }
                    r0 = r25;	 Catch:{ Exception -> 0x02ba }
                    r29 = r7.getUIParamInt(r0);	 Catch:{ Exception -> 0x02ba }
                    r27 = 1;
                    r0 = r29;
                    r1 = r27;
                    if (r0 != r1) goto L_0x023f;
                L_0x00db:
                    r30 = 1;
                L_0x00dd:
                    r31 = 0;
                    r32 = 0;	 Catch:{ Exception -> 0x02ba }
                    r21 = "uip_keep_phone_dpi";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r33 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    if (r33 == 0) goto L_0x0108;
                L_0x00ee:
                    r21 = "uip_keep_phone_dpi";	 Catch:{ NumberFormatException -> 0x05b6 }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x05b6 }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x05b6 }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x05b6 }
                    r34 = r11;	 Catch:{ NumberFormatException -> 0x05b6 }
                    r34 = (java.lang.String) r34;	 Catch:{ NumberFormatException -> 0x05b6 }
                    r22 = r34;	 Catch:{ NumberFormatException -> 0x05b6 }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x05b6 }
                    r29 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x05b6 }
                    if (r29 != 0) goto L_0x0246;
                L_0x0107:
                    r10 = 1;
                L_0x0108:
                    r21 = "uip_scale_app_canvas_to_dpi_mode";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r33 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    if (r33 == 0) goto L_0x0136;
                L_0x0115:
                    r21 = "uip_scale_app_canvas_to_dpi_mode";	 Catch:{ NumberFormatException -> 0x05b0 }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x05b0 }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x05b0 }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x05b0 }
                    r35 = r11;	 Catch:{ NumberFormatException -> 0x05b0 }
                    r35 = (java.lang.String) r35;	 Catch:{ NumberFormatException -> 0x05b0 }
                    r22 = r35;	 Catch:{ NumberFormatException -> 0x05b0 }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x05b0 }
                    r29 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x05b0 }
                    r27 = 1;
                    r0 = r29;
                    r1 = r27;
                    if (r0 != r1) goto L_0x0248;
                L_0x0134:
                    r28 = 1;
                L_0x0136:
                    r21 = "uip_max_app_canvas_scale_factor_percent";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r33 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    if (r33 == 0) goto L_0x015a;
                L_0x0143:
                    r21 = "uip_max_app_canvas_scale_factor_percent";	 Catch:{ NumberFormatException -> 0x05aa }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x05aa }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x05aa }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x05aa }
                    r36 = r11;	 Catch:{ NumberFormatException -> 0x05aa }
                    r36 = (java.lang.String) r36;	 Catch:{ NumberFormatException -> 0x05aa }
                    r22 = r36;	 Catch:{ NumberFormatException -> 0x05aa }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x05aa }
                    r26 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x05aa }
                L_0x015a:
                    r21 = "show_debug_frame_time_overlay";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r33 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    if (r33 == 0) goto L_0x0188;
                L_0x0167:
                    r21 = "show_debug_frame_time_overlay";	 Catch:{ NumberFormatException -> 0x05a4 }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x05a4 }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x05a4 }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x05a4 }
                    r37 = r11;	 Catch:{ NumberFormatException -> 0x05a4 }
                    r37 = (java.lang.String) r37;	 Catch:{ NumberFormatException -> 0x05a4 }
                    r22 = r37;	 Catch:{ NumberFormatException -> 0x05a4 }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x05a4 }
                    r29 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x05a4 }
                    r27 = 1;
                    r0 = r29;
                    r1 = r27;
                    if (r0 != r1) goto L_0x024b;
                L_0x0186:
                    r31 = 1;
                L_0x0188:
                    r21 = "force_enable_mirror_mode_override_dpi";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r33 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    if (r33 == 0) goto L_0x01b4;
                L_0x0194:
                    r21 = "force_enable_mirror_mode_override_dpi";	 Catch:{ NumberFormatException -> 0x059e }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x059e }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x059e }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x059e }
                    r38 = r11;	 Catch:{ NumberFormatException -> 0x059e }
                    r38 = (java.lang.String) r38;	 Catch:{ NumberFormatException -> 0x059e }
                    r22 = r38;	 Catch:{ NumberFormatException -> 0x059e }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x059e }
                    r29 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x059e }
                    r27 = 1;
                    r0 = r29;
                    r1 = r27;
                    if (r0 != r1) goto L_0x024e;
                L_0x01b2:
                    r32 = 1;
                L_0x01b4:
                    r21 = "enable_static_texturebitmap";	 Catch:{ Exception -> 0x02ba }
                    r0 = r20;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r33 = r0.containsKey(r1);	 Catch:{ Exception -> 0x02ba }
                    goto L_0x01c2;	 Catch:{ Exception -> 0x02ba }
                L_0x01bf:
                    goto L_0x0136;	 Catch:{ Exception -> 0x02ba }
                L_0x01c2:
                    if (r33 == 0) goto L_0x01e4;
                L_0x01c4:
                    r21 = "enable_static_texturebitmap";	 Catch:{ NumberFormatException -> 0x0598 }
                    r0 = r20;	 Catch:{ NumberFormatException -> 0x0598 }
                    r1 = r21;	 Catch:{ NumberFormatException -> 0x0598 }
                    r11 = r0.get(r1);	 Catch:{ NumberFormatException -> 0x0598 }
                    r39 = r11;	 Catch:{ NumberFormatException -> 0x0598 }
                    r39 = (java.lang.String) r39;	 Catch:{ NumberFormatException -> 0x0598 }
                    r22 = r39;	 Catch:{ NumberFormatException -> 0x0598 }
                    r0 = r22;	 Catch:{ NumberFormatException -> 0x0598 }
                    r29 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x0598 }
                    r27 = 1;
                    r0 = r29;
                    r1 = r27;
                    if (r0 != r1) goto L_0x0251;
                L_0x01e2:
                    r30 = 1;
                L_0x01e4:
                    r0 = r30;	 Catch:{ Exception -> 0x02ba }
                    com.abaltatech.mcp.weblink.sdk.WLMirrorMode.setTextureViewHandlerUseStaticBitmap(r0);	 Catch:{ Exception -> 0x02ba }
                    goto L_0x01ed;	 Catch:{ Exception -> 0x02ba }
                L_0x01ea:
                    goto L_0x0188;	 Catch:{ Exception -> 0x02ba }
                L_0x01ed:
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r0.setOffscreenModeDpiOverride(r10);	 Catch:{ Exception -> 0x02ba }
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;
                    if (r32 == 0) goto L_0x0254;
                L_0x01f8:
                    if (r10 == 0) goto L_0x0254;
                L_0x01fa:
                    r30 = 1;	 Catch:{ Exception -> 0x02ba }
                L_0x01fc:
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r30;	 Catch:{ Exception -> 0x02ba }
                    r0.setMirrorModeDpiOverride(r1);	 Catch:{ Exception -> 0x02ba }
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    goto L_0x020d;	 Catch:{ Exception -> 0x02ba }
                    goto L_0x020a;	 Catch:{ Exception -> 0x02ba }
                L_0x0207:
                    goto L_0x01b4;	 Catch:{ Exception -> 0x02ba }
                L_0x020a:
                    goto L_0x01bf;	 Catch:{ Exception -> 0x02ba }
                L_0x020d:
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r31;	 Catch:{ Exception -> 0x02ba }
                    r0.showDebugOverlay(r1);	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r29 = r7.m_uiMode;	 Catch:{ Exception -> 0x02ba }
                    switch(r29) {
                        case 0: goto L_0x0257;
                        case 1: goto L_0x02c9;
                        case 2: goto L_0x04c2;
                        case 3: goto L_0x0351;
                        default: goto L_0x0221;
                    };	 Catch:{ Exception -> 0x02ba }
                L_0x0221:
                    goto L_0x0222;	 Catch:{ Exception -> 0x02ba }
                L_0x0222:
                    r21 = "WEBLINK";	 Catch:{ Exception -> 0x02ba }
                    r41 = "Invalid UI mode is set";	 Catch:{ Exception -> 0x02ba }
                    r0 = r21;	 Catch:{ Exception -> 0x02ba }
                    r1 = r41;	 Catch:{ Exception -> 0x02ba }
                    android.util.Log.e(r0, r1);	 Catch:{ Exception -> 0x02ba }
                    return;
                    goto L_0x0232;
                L_0x022f:
                    goto L_0x009f;
                L_0x0232:
                    r10 = 0;
                    goto L_0x022f;	 Catch:{ Exception -> 0x02ba }
                    goto L_0x0238;	 Catch:{ Exception -> 0x02ba }
                L_0x0235:
                    goto L_0x00b7;	 Catch:{ Exception -> 0x02ba }
                L_0x0238:
                    r28 = 0;
                    goto L_0x0235;	 Catch:{ Exception -> 0x02ba }
                    goto L_0x023f;	 Catch:{ Exception -> 0x02ba }
                L_0x023c:
                    goto L_0x00dd;	 Catch:{ Exception -> 0x02ba }
                L_0x023f:
                    r30 = 0;
                    goto L_0x023c;
                    goto L_0x0246;
                L_0x0243:
                    goto L_0x0108;
                L_0x0246:
                    r10 = 0;
                    goto L_0x0243;	 Catch:{ Exception -> 0x02ba }
                L_0x0248:
                    r28 = 0;
                    goto L_0x020a;	 Catch:{ Exception -> 0x02ba }
                L_0x024b:
                    r31 = 0;
                    goto L_0x01ea;	 Catch:{ Exception -> 0x02ba }
                L_0x024e:
                    r32 = 0;
                    goto L_0x0207;	 Catch:{ Exception -> 0x02ba }
                L_0x0251:
                    r30 = 0;
                    goto L_0x01e4;	 Catch:{ Exception -> 0x02ba }
                L_0x0254:
                    r30 = 0;
                    goto L_0x01fc;	 Catch:{ Exception -> 0x02ba }
                L_0x0257:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r42 = r7.m_customHandler;	 Catch:{ Exception -> 0x02ba }
                    if (r42 == 0) goto L_0x028e;	 Catch:{ Exception -> 0x02ba }
                L_0x0263:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r42 = r7.m_customHandler;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r43 = r7.m_surface;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r18 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r19 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r42;	 Catch:{ Exception -> 0x02ba }
                    r1 = r43;	 Catch:{ Exception -> 0x02ba }
                    r2 = r18;	 Catch:{ Exception -> 0x02ba }
                    r3 = r19;	 Catch:{ Exception -> 0x02ba }
                    r0.onUiCreate(r1, r2, r3);	 Catch:{ Exception -> 0x02ba }
                L_0x028e:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r18 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r19 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r17;	 Catch:{ Exception -> 0x02ba }
                    r0 = r0.densityDpi;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0;	 Catch:{ Exception -> 0x02ba }
                    r45 = 0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r18;	 Catch:{ Exception -> 0x02ba }
                    r1 = r19;	 Catch:{ Exception -> 0x02ba }
                    r2 = r26;	 Catch:{ Exception -> 0x02ba }
                    r3 = r45;	 Catch:{ Exception -> 0x02ba }
                    r44 = r14.createVirtualDisplay(r0, r1, r2, r3);	 Catch:{ Exception -> 0x02ba }
                    r0 = r44;	 Catch:{ Exception -> 0x02ba }
                    r7.m_virtualDisplay = r0;	 Catch:{ Exception -> 0x02ba }
                    return;
                L_0x02ba:
                    r46 = move-exception;
                    r21 = "WEBLINK";
                    r41 = "Error starting rendering";
                    r0 = r21;
                    r1 = r41;
                    r2 = r46;
                    android.util.Log.e(r0, r1, r2);
                    return;
                L_0x02c9:
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r15 = r7.m_context;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r43 = r7.m_surface;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r29 = r0;	 Catch:{ Exception -> 0x02ba }
                    r27 = -1;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r15;	 Catch:{ Exception -> 0x02ba }
                    r2 = r43;	 Catch:{ Exception -> 0x02ba }
                    r3 = r26;	 Catch:{ Exception -> 0x02ba }
                    r4 = r29;	 Catch:{ Exception -> 0x02ba }
                    r5 = r27;	 Catch:{ Exception -> 0x02ba }
                    r0.init(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x02ba }
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r18;	 Catch:{ Exception -> 0x02ba }
                    r2 = r19;	 Catch:{ Exception -> 0x02ba }
                    r0.setClientDpi(r1, r2);	 Catch:{ Exception -> 0x02ba }
                    r47 = com.abaltatech.mcp.weblink.sdk.WLTreeViewObserver.getInstance();	 Catch:{ Exception -> 0x02ba }
                    r0 = r47;	 Catch:{ Exception -> 0x02ba }
                    r0.startMonitoring();	 Catch:{ Exception -> 0x02ba }
                    r0 = r17;	 Catch:{ Exception -> 0x02ba }
                    r0 = r0.densityDpi;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0;	 Catch:{ Exception -> 0x02ba }
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r10 = r0.getMirrorModeDpiOverride();	 Catch:{ Exception -> 0x02ba }
                    if (r10 == 0) goto L_0x032b;	 Catch:{ Exception -> 0x02ba }
                L_0x031f:
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r18;	 Catch:{ Exception -> 0x02ba }
                    r2 = r19;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0.calculateMirrorModeDensityOverride(r1, r2);	 Catch:{ Exception -> 0x02ba }
                L_0x032b:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r18 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r19 = r0;	 Catch:{ Exception -> 0x02ba }
                    r45 = 0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r18;	 Catch:{ Exception -> 0x02ba }
                    r1 = r19;	 Catch:{ Exception -> 0x02ba }
                    r2 = r26;	 Catch:{ Exception -> 0x02ba }
                    r3 = r45;	 Catch:{ Exception -> 0x02ba }
                    r44 = r14.createVirtualDisplay(r0, r1, r2, r3);	 Catch:{ Exception -> 0x02ba }
                    r0 = r44;	 Catch:{ Exception -> 0x02ba }
                    r7.m_virtualDisplay = r0;	 Catch:{ Exception -> 0x02ba }
                    return;
                L_0x0351:
                    r0 = r17;	 Catch:{ Exception -> 0x02ba }
                    r0 = r0.densityDpi;	 Catch:{ Exception -> 0x02ba }
                    r29 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = (double) r0;
                    r48 = r0;
                    r50 = 4573855781664849920; // 0x3f799999a0000000 float:-1.0842022E-19 double:0.0062500000931322575;
                    r0 = r48;
                    r2 = r50;
                    r0 = r0 * r2;
                    r48 = r0;
                    r50 = 4616189618054758400; // 0x4010000000000000 float:0.0 double:4.0;	 Catch:{ Exception -> 0x02ba }
                    r52 = r50 * r48;	 Catch:{ Exception -> 0x02ba }
                    r0 = r52;	 Catch:{ Exception -> 0x02ba }
                    r54 = java.lang.Math.round(r0);	 Catch:{ Exception -> 0x02ba }
                    r0 = r54;
                    r0 = (float) r0;
                    r56 = r0;
                    r57 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
                    r0 = r56;
                    r1 = r57;
                    r0 = r0 / r1;
                    r56 = r0;
                    r58 = android.os.Build.VERSION.SDK_INT;
                    r27 = 17;
                    r0 = r58;
                    r1 = r27;
                    if (r0 >= r1) goto L_0x0397;
                L_0x038c:
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r27 = 0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r27;	 Catch:{ Exception -> 0x02ba }
                    r0.setOffscreenModeDpiOverride(r1);	 Catch:{ Exception -> 0x02ba }
                L_0x0397:
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r31 = r0.getOffscreenModeDpiOverride();	 Catch:{ Exception -> 0x02ba }
                    if (r31 == 0) goto L_0x03a3;
                L_0x03a1:
                    r29 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
                L_0x03a3:
                    if (r10 != 0) goto L_0x04be;
                L_0x03a5:
                    if (r28 == 0) goto L_0x04be;
                L_0x03a7:
                    if (r26 <= 0) goto L_0x03be;
                L_0x03a9:
                    r0 = r26;
                    r0 = (float) r0;
                    r59 = r0;
                    r57 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;	 Catch:{ Exception -> 0x02ba }
                    r0 = r59;	 Catch:{ Exception -> 0x02ba }
                    r1 = r57;	 Catch:{ Exception -> 0x02ba }
                    r0 = r0 / r1;	 Catch:{ Exception -> 0x02ba }
                    r59 = r0;	 Catch:{ Exception -> 0x02ba }
                    r1 = r56;	 Catch:{ Exception -> 0x02ba }
                    r56 = java.lang.Math.min(r0, r1);	 Catch:{ Exception -> 0x02ba }
                L_0x03be:
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r15 = r7.m_context;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r43 = r7.m_surface;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = (float) r0;
                    r59 = r0;
                    r1 = r56;
                    r0 = r0 * r1;
                    r59 = r0;
                    r57 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
                    r0 = r59;
                    r1 = r57;
                    r0 = r0 + r1;
                    r59 = r0;
                    r0 = (int) r0;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r58 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = (float) r0;
                    r59 = r0;
                    r1 = r56;
                    r0 = r0 * r1;
                    r59 = r0;
                    r57 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
                    r0 = r59;
                    r1 = r57;
                    r0 = r0 + r1;
                    r59 = r0;
                    r0 = (int) r0;	 Catch:{ Exception -> 0x02ba }
                    r58 = r0;	 Catch:{ Exception -> 0x02ba }
                    r27 = -1;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r15;	 Catch:{ Exception -> 0x02ba }
                    r2 = r43;	 Catch:{ Exception -> 0x02ba }
                    r3 = r26;	 Catch:{ Exception -> 0x02ba }
                    r4 = r58;	 Catch:{ Exception -> 0x02ba }
                    r5 = r27;	 Catch:{ Exception -> 0x02ba }
                    r0.init(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x02ba }
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r18;	 Catch:{ Exception -> 0x02ba }
                    r2 = r19;	 Catch:{ Exception -> 0x02ba }
                    r0.setClientDpi(r1, r2);	 Catch:{ Exception -> 0x02ba }
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r18;	 Catch:{ Exception -> 0x02ba }
                    r2 = r19;	 Catch:{ Exception -> 0x02ba }
                    r0.setClientDpi(r1, r2);	 Catch:{ Exception -> 0x02ba }
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    goto L_0x0436;	 Catch:{ Exception -> 0x02ba }
                L_0x0433:
                    goto L_0x03be;	 Catch:{ Exception -> 0x02ba }
                L_0x0436:
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r56;	 Catch:{ Exception -> 0x02ba }
                    r2 = r56;	 Catch:{ Exception -> 0x02ba }
                    r0.setOffscreenScale(r1, r2);	 Catch:{ Exception -> 0x02ba }
                    r47 = com.abaltatech.mcp.weblink.sdk.WLTreeViewObserver.getInstance();	 Catch:{ Exception -> 0x02ba }
                    r0 = r47;	 Catch:{ Exception -> 0x02ba }
                    r0.startMonitoring();	 Catch:{ Exception -> 0x02ba }
                    r60 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r0.<init>();	 Catch:{ Exception -> 0x02ba }
                    r21 = "DPI: ";	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r60 = r0.append(r1);	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r1 = r29;	 Catch:{ Exception -> 0x02ba }
                    r60 = r0.append(r1);	 Catch:{ Exception -> 0x02ba }
                    r21 = ", scale: ";	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r60 = r0.append(r1);	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r1 = r48;	 Catch:{ Exception -> 0x02ba }
                    r60 = r0.append(r1);	 Catch:{ Exception -> 0x02ba }
                    goto L_0x0477;	 Catch:{ Exception -> 0x02ba }
                L_0x0474:
                    goto L_0x0433;	 Catch:{ Exception -> 0x02ba }
                L_0x0477:
                    r21 = ", Offscreen scale: ";	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r1 = r21;	 Catch:{ Exception -> 0x02ba }
                    r60 = r0.append(r1);	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r1 = r56;	 Catch:{ Exception -> 0x02ba }
                    r60 = r0.append(r1);	 Catch:{ Exception -> 0x02ba }
                    r0 = r60;	 Catch:{ Exception -> 0x02ba }
                    r22 = r0.toString();	 Catch:{ Exception -> 0x02ba }
                    r21 = "====>";	 Catch:{ Exception -> 0x02ba }
                    r0 = r21;	 Catch:{ Exception -> 0x02ba }
                    r1 = r22;	 Catch:{ Exception -> 0x02ba }
                    android.util.Log.i(r0, r1);	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r18 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r19 = r0;	 Catch:{ Exception -> 0x02ba }
                    r45 = 0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r18;	 Catch:{ Exception -> 0x02ba }
                    r1 = r19;	 Catch:{ Exception -> 0x02ba }
                    r2 = r29;	 Catch:{ Exception -> 0x02ba }
                    r3 = r45;	 Catch:{ Exception -> 0x02ba }
                    r44 = r14.createVirtualDisplay(r0, r1, r2, r3);	 Catch:{ Exception -> 0x02ba }
                    r0 = r44;	 Catch:{ Exception -> 0x02ba }
                    r7.m_virtualDisplay = r0;	 Catch:{ Exception -> 0x02ba }
                    return;
                L_0x04be:
                    r56 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
                    goto L_0x0474;
                L_0x04c2:
                    r18 = android.os.Build.VERSION.SDK_INT;
                    r27 = 19;	 Catch:{ Exception -> 0x02ba }
                    r0 = r18;	 Catch:{ Exception -> 0x02ba }
                    r1 = r27;	 Catch:{ Exception -> 0x02ba }
                    if (r0 < r1) goto L_0x0565;	 Catch:{ Exception -> 0x02ba }
                L_0x04cc:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r18 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r19 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r17;	 Catch:{ Exception -> 0x02ba }
                    r0 = r0.densityDpi;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r0 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r61 = r0;	 Catch:{ Exception -> 0x02ba }
                    r43 = r0.m_surface;	 Catch:{ Exception -> 0x02ba }
                    r0 = r18;	 Catch:{ Exception -> 0x02ba }
                    r1 = r19;	 Catch:{ Exception -> 0x02ba }
                    r2 = r26;	 Catch:{ Exception -> 0x02ba }
                    r3 = r43;	 Catch:{ Exception -> 0x02ba }
                    r44 = r14.createVirtualDisplay(r0, r1, r2, r3);	 Catch:{ Exception -> 0x02ba }
                    r0 = r44;	 Catch:{ Exception -> 0x02ba }
                    r7.m_virtualDisplay = r0;	 Catch:{ Exception -> 0x02ba }
                L_0x0501:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r44 = r7.m_virtualDisplay;	 Catch:{ Exception -> 0x02ba }
                    if (r44 == 0) goto L_0x05c4;
                L_0x050d:
                    r40 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r15 = r7.m_context;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r43 = r7.m_surface;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r18 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r19 = r0;	 Catch:{ Exception -> 0x02ba }
                    r26 = android.os.Build.VERSION.SDK_INT;
                    r27 = 19;	 Catch:{ Exception -> 0x02ba }
                    r0 = r26;	 Catch:{ Exception -> 0x02ba }
                    r1 = r27;	 Catch:{ Exception -> 0x02ba }
                    if (r0 < r1) goto L_0x0591;	 Catch:{ Exception -> 0x02ba }
                L_0x0539:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r44 = r7.m_virtualDisplay;	 Catch:{ Exception -> 0x02ba }
                    goto L_0x0547;	 Catch:{ Exception -> 0x02ba }
                L_0x0544:
                    goto L_0x0501;	 Catch:{ Exception -> 0x02ba }
                L_0x0547:
                    r0 = r44;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0.getDisplayId();	 Catch:{ Exception -> 0x02ba }
                L_0x054d:
                    r0 = r40;	 Catch:{ Exception -> 0x02ba }
                    r1 = r15;	 Catch:{ Exception -> 0x02ba }
                    r2 = r43;	 Catch:{ Exception -> 0x02ba }
                    r3 = r18;	 Catch:{ Exception -> 0x02ba }
                    r4 = r19;	 Catch:{ Exception -> 0x02ba }
                    r5 = r26;	 Catch:{ Exception -> 0x02ba }
                    r0.init(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x02ba }
                    r47 = com.abaltatech.mcp.weblink.sdk.WLTreeViewObserver.getInstance();	 Catch:{ Exception -> 0x02ba }
                    r0 = r47;	 Catch:{ Exception -> 0x02ba }
                    r0.startMonitoring();	 Catch:{ Exception -> 0x02ba }
                    return;
                L_0x0565:
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r6 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x02ba }
                    r7 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r6;	 Catch:{ Exception -> 0x02ba }
                    r18 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r70;	 Catch:{ Exception -> 0x02ba }
                    r0 = r7;	 Catch:{ Exception -> 0x02ba }
                    r19 = r0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r17;	 Catch:{ Exception -> 0x02ba }
                    r0 = r0.densityDpi;	 Catch:{ Exception -> 0x02ba }
                    r26 = r0;	 Catch:{ Exception -> 0x02ba }
                    r45 = 0;	 Catch:{ Exception -> 0x02ba }
                    r0 = r18;	 Catch:{ Exception -> 0x02ba }
                    r1 = r19;	 Catch:{ Exception -> 0x02ba }
                    r2 = r26;	 Catch:{ Exception -> 0x02ba }
                    r3 = r45;	 Catch:{ Exception -> 0x02ba }
                    r44 = r14.createVirtualDisplay(r0, r1, r2, r3);	 Catch:{ Exception -> 0x02ba }
                    r0 = r44;	 Catch:{ Exception -> 0x02ba }
                    r7.m_virtualDisplay = r0;	 Catch:{ Exception -> 0x02ba }
                    goto L_0x0544;
                L_0x0591:
                    r26 = -1;
                    goto L_0x054d;
                    goto L_0x0598;
                L_0x0595:
                    goto L_0x01e4;
                L_0x0598:
                    r62 = move-exception;
                    goto L_0x0595;
                    goto L_0x059e;
                L_0x059b:
                    goto L_0x01b4;
                L_0x059e:
                    r63 = move-exception;
                    goto L_0x059b;
                    goto L_0x05a4;
                L_0x05a1:
                    goto L_0x0188;
                L_0x05a4:
                    r64 = move-exception;
                    goto L_0x05a1;
                    goto L_0x05aa;
                L_0x05a7:
                    goto L_0x015a;
                L_0x05aa:
                    r65 = move-exception;
                    goto L_0x05a7;
                    goto L_0x05b0;
                L_0x05ad:
                    goto L_0x0136;
                L_0x05b0:
                    r66 = move-exception;
                    goto L_0x05ad;
                    goto L_0x05b6;
                L_0x05b3:
                    goto L_0x0108;
                L_0x05b6:
                    r67 = move-exception;
                    goto L_0x05b3;
                    goto L_0x05bc;
                L_0x05b9:
                    goto L_0x008e;
                L_0x05bc:
                    r68 = move-exception;
                    goto L_0x05b9;
                    goto L_0x05c2;
                L_0x05bf:
                    goto L_0x006a;
                L_0x05c2:
                    r69 = move-exception;
                    goto L_0x05bf;
                L_0x05c4:
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WEBLINK.4.1.run():void");
                }
            });
        }

        public void onRenderStop() throws RemoteException {
            WEBLINK.this.m_keyboardView = null;
            WEBLINK.this.m_handler.post(new C03232());
        }

        public void onRenderFrame() throws RemoteException {
            WEBLINK.this.m_handler.removeCallbacks(WEBLINK.this.m_renderView);
            WEBLINK.this.m_handler.post(WEBLINK.this.m_renderView);
        }

        @SuppressLint({"NewApi"})
        public void onEvent(InputEvent $r2) throws RemoteException {
            final boolean $z0 = $r2 instanceof MotionEvent;
            if ($z0) {
                $r2 = MotionEvent.obtain((MotionEvent) $r2);
            }
            WEBLINK.this.m_handler.post(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() throws  {
                    /*
                    r13 = this;
                    r0 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x0039 }
                    r1 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x0039 }
                    r2 = r1.m_uiMode;	 Catch:{ Exception -> 0x0039 }
                    switch(r2) {
                        case 0: goto L_0x0021;
                        case 1: goto L_0x0042;
                        case 2: goto L_0x004a;
                        case 3: goto L_0x0042;
                        default: goto L_0x000b;
                    };	 Catch:{ Exception -> 0x0039 }
                L_0x000b:
                    goto L_0x000c;
                L_0x000c:
                    r3 = "WEBLINK";
                    r4 = "Invalid UI mode set";
                    android.util.Log.e(r3, r4);	 Catch:{ Exception -> 0x0039 }
                L_0x0013:
                    r5 = r0;
                    if (r5 == 0) goto L_0x005c;
                L_0x0017:
                    r6 = r7;
                    r8 = r6;
                    r8 = (android.view.MotionEvent) r8;
                    r7 = r8;
                    r7.recycle();
                    return;
                L_0x0021:
                    r0 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x0039 }
                    r1 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x0039 }
                    r9 = r1.m_customHandler;	 Catch:{ Exception -> 0x0039 }
                    if (r9 == 0) goto L_0x0013;
                L_0x002b:
                    r0 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x0039 }
                    r1 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x0039 }
                    r9 = r1.m_customHandler;	 Catch:{ Exception -> 0x0039 }
                    r6 = r7;	 Catch:{ Exception -> 0x0039 }
                    r9.onUiEvent(r6);	 Catch:{ Exception -> 0x0039 }
                    goto L_0x0013;
                L_0x0039:
                    r10 = move-exception;
                    r3 = "WEBLINK";
                    r4 = "Error handling UI event";
                    android.util.Log.e(r3, r4, r10);
                    goto L_0x0013;
                L_0x0042:
                    r11 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;
                    r6 = r7;	 Catch:{ Exception -> 0x0039 }
                    r11.handleUiEvent(r6);	 Catch:{ Exception -> 0x0039 }
                    goto L_0x0013;
                L_0x004a:
                    r0 = com.abaltatech.mcp.weblink.sdk.WEBLINK.C03264.this;	 Catch:{ Exception -> 0x0039 }
                    r1 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ Exception -> 0x0039 }
                    r12 = r1.m_virtualDisplay;	 Catch:{ Exception -> 0x0039 }
                    if (r12 == 0) goto L_0x0013;
                L_0x0054:
                    r11 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.instance;	 Catch:{ Exception -> 0x0039 }
                    r6 = r7;	 Catch:{ Exception -> 0x0039 }
                    r11.handleUiEvent(r6);	 Catch:{ Exception -> 0x0039 }
                    goto L_0x0013;
                L_0x005c:
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WEBLINK.4.3.run():void");
                }
            });
        }

        public void onKeybordStateChanged(final boolean $z0) throws RemoteException {
            WEBLINK.this.m_handler.post(new Runnable() {
                public void run() throws  {
                    boolean $z0 = $z0;
                    this = this;
                    if (!($z0 || WEBLINK.this.m_keyboardView == null)) {
                        ((InputMethodManager) WEBLINK.this.m_context.getSystemService("input_method")).hideSoftInputFromWindow(WEBLINK.this.m_keyboardView.getWindowToken(), 0);
                        WEBLINK.this.m_keyboardView = null;
                    }
                    try {
                        switch (WEBLINK.this.m_uiMode) {
                            case 0:
                                if (WEBLINK.this.m_customHandler != null) {
                                    WEBLINK.this.m_customHandler.onKeybordStateChanged($z0);
                                    return;
                                }
                                return;
                            case 1:
                            case 2:
                            case 3:
                                break;
                            default:
                                Log.e("WEBLINK", "Invalid UI mode set");
                                break;
                        }
                    } catch (Exception $r1) {
                        Log.e("WEBLINK", "Error handling UI event", $r1);
                    }
                }
            });
        }

        public void onWLInputEvent(WLInputEventParcelable $r1) throws RemoteException {
            onEvent($r1.getEvent());
        }
    }

    class C03275 implements Runnable {
        C03275() throws  {
        }

        public void run() throws  {
            if (WEBLINK.this.m_surface != null) {
                try {
                    switch (WEBLINK.this.m_uiMode) {
                        case 0:
                            if (WEBLINK.this.m_customHandler != null) {
                                WEBLINK.this.m_customHandler.onRenderUi();
                                return;
                            }
                            return;
                        case 1:
                        case 3:
                            WLMirrorMode.instance.renderFrame();
                            return;
                        case 2:
                            if (VERSION.SDK_INT < 19) {
                                WLMirrorMode.instance.renderFrame();
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                } catch (Exception $r1) {
                    Log.e("WEBLINK", "Error rendering frame", $r1);
                }
                Log.e("WEBLINK", "Error rendering frame", $r1);
            }
        }
    }

    class C03286 implements TextViewNotification {
        C03286() throws  {
        }

        public void onViewClicked(View $r1, boolean $z0) throws  {
            if (WEBLINK.this.isServerSideKeyboard()) {
                if ($z0) {
                    WEBLINK.this.showKeyboard($r1);
                }
            } else if ($z0) {
                WEBLINK.this.sendCommand(new ShowKeyboardCommand((short) 0));
            } else {
                WEBLINK.this.sendCommand(new HideKeyboardCommand());
            }
        }
    }

    private class CommThread extends Thread {
        private final int m_port;
        private Socket m_socket;

        public CommThread(int $i0) throws  {
            this.m_port = $i0;
        }

        public synchronized void terminate() throws  {
            if (!(this.m_socket == null || this.m_socket.isClosed())) {
                try {
                    this.m_socket.close();
                } catch (Exception e) {
                }
            }
            interrupt();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() throws  {
            /*
            r32 = this;
            r3 = 10;
            sleep(r3);	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r5 = new java.net.Socket;	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r6 = java.net.InetAddress.getLocalHost();	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r0 = r32;
            r7 = r0.m_port;	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r5.<init>(r6, r7);	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r0 = r32;
            r0.m_socket = r5;
            r0 = r32;
            r5 = r0.m_socket;	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r8 = r5.getInputStream();	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r9 = new com.abaltatech.mcp.weblink.core.DataBuffer;	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
            r10 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
            r9.<init>(r10);	 Catch:{ IOException -> 0x00f6, InterruptedException -> 0x00e4 }
        L_0x0025:
            r11 = interrupted();	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            if (r11 != 0) goto L_0x00de;
        L_0x002b:
            r12 = r9.getData();	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r10 = 0;
            r13 = 8;
            r7 = r8.read(r12, r10, r13);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r10 = 8;
            if (r7 != r10) goto L_0x00dc;
        L_0x003a:
            r10 = 0;
            r14 = r9.getByte(r10);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r10 = 1;
            r15 = r9.getByte(r10);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r10 = 4;
            r7 = r9.getInt(r10);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r10 = 87;
            if (r14 != r10) goto L_0x0025;
        L_0x004d:
            r10 = 76;
            if (r15 != r10) goto L_0x0025;
        L_0x0051:
            if (r7 < 0) goto L_0x0025;
        L_0x0053:
            r16 = r7 + 8;
            r0 = r16;
            r9.resize(r0);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            if (r7 <= 0) goto L_0x0065;
        L_0x005c:
            r12 = r9.getData();	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r10 = 8;
            r8.read(r12, r10, r7);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
        L_0x0065:
            r0 = r32;
            r0 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r17 = r0;
            r18 = r0.m_commandHandlers;	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r0 = r18;
            r11 = r0.isEmpty();	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            if (r11 != 0) goto L_0x0025;
        L_0x0077:
            r19 = new com.abaltatech.mcp.weblink.core.commandhandling.Command;	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r0 = r19;
            r0.<init>(r9);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r11 = 0;
            r0 = r32;
            r0 = com.abaltatech.mcp.weblink.sdk.WEBLINK.this;	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r17 = r0;
            r18 = r0.m_commandHandlers;	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            r0 = r18;
            r20 = r0.iterator();	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
        L_0x008f:
            if (r11 != 0) goto L_0x0025;
        L_0x0091:
            r0 = r20;
            r21 = r0.hasNext();	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            if (r21 == 0) goto L_0x0025;
        L_0x0099:
            r0 = r20;
            r22 = r0.next();	 Catch:{ Exception -> 0x00ae, Throwable -> 0x00f2 }
            r24 = r22;
            r24 = (com.abaltatech.mcp.weblink.sdk.WEBLINK.ICommandHandler) r24;	 Catch:{ Exception -> 0x00ae, Throwable -> 0x00f2 }
            r23 = r24;
            r0 = r23;
            r1 = r19;
            r11 = r0.onCommand(r1);	 Catch:{ Exception -> 0x00ae, Throwable -> 0x00f2 }
            goto L_0x008f;
        L_0x00ae:
            r25 = move-exception;
            r26 = "WEBLINK";
            r27 = "Error handling command";
            r0 = r26;
            r1 = r27;
            r2 = r25;
            android.util.Log.e(r0, r1, r2);	 Catch:{ IOException -> 0x00bd, InterruptedException -> 0x00f4, Throwable -> 0x00f2 }
            goto L_0x008f;
        L_0x00bd:
            r28 = move-exception;
        L_0x00be:
            r26 = "WEBLINK";
            r27 = "Error in CommThread";
            r0 = r26;
            r1 = r27;
            r2 = r28;
            android.util.Log.e(r0, r1, r2);	 Catch:{ Throwable -> 0x00eb }
            r0 = r32;
            r0.terminate();
        L_0x00d0:
            r26 = "WEBLINK";
            r27 = "Connection thread exited";
            r0 = r26;
            r1 = r27;
            android.util.Log.d(r0, r1);
            return;
        L_0x00dc:
            if (r7 >= 0) goto L_0x0025;
        L_0x00de:
            r0 = r32;
            r0.terminate();
            goto L_0x00d0;
        L_0x00e4:
            r29 = move-exception;
        L_0x00e5:
            r0 = r32;
            r0.terminate();
            goto L_0x00d0;
        L_0x00eb:
            r30 = move-exception;
        L_0x00ec:
            r0 = r32;
            r0.terminate();
            throw r30;
        L_0x00f2:
            r30 = move-exception;
            goto L_0x00ec;
        L_0x00f4:
            r31 = move-exception;
            goto L_0x00e5;
        L_0x00f6:
            r28 = move-exception;
            goto L_0x00be;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WEBLINK.CommThread.run():void");
        }

        public boolean sendData(byte[] $r1, int $i0, int $i1) throws  {
            if (this.m_socket == null) {
                return false;
            }
            if (this.m_socket.isClosed()) {
                return false;
            }
            try {
                this.m_socket.getOutputStream().write($r1, $i0, $i1);
                return true;
            } catch (IOException $r2) {
                Log.e("WEBLINK", "Error in sendData()", $r2);
                return false;
            }
        }
    }

    public enum EWLUIParam {
        UIP_KEEP_PHONE_DPI(Integer.valueOf(0)),
        UIP_SCALE_APP_CANVAS_TO_DPI_MODE(Integer.valueOf(0)),
        UIP_MAX_APP_CANVAS_SCALE_FACTOR_PERCENT(Integer.valueOf(0)),
        UIP_ENABLE_STATIC_TEXTUREBITMAP(Integer.valueOf(0));
        
        private Object m_defaultValue;

        private EWLUIParam(@Signature({"(", "Ljava/lang/Object;", ")V"}) Object $r2) throws  {
            this.m_defaultValue = $r2;
        }

        Object getDefaultValue() throws  {
            return this.m_defaultValue;
        }
    }

    public interface ICommandHandler {
        boolean onCommand(Command command) throws ;
    }

    public interface IConnectionListener {
        void onClientConnected() throws ;

        void onClientDisconnected() throws ;
    }

    public interface ICustomUiHandler {
        void onKeybordStateChanged(boolean z) throws ;

        void onRenderUi() throws ;

        void onUiCreate(Surface surface, int i, int i2) throws ;

        void onUiDestroy() throws ;

        void onUiEvent(InputEvent inputEvent) throws ;
    }

    interface IViewHandler {
        void attach() throws ;

        void destroy() throws ;

        void detach() throws ;

        void draw(Canvas canvas, float f, float f2, int i, int i2, float f3, float f4) throws ;
    }

    private class MemoryBoss implements ComponentCallbacks2 {
        private MemoryBoss() throws  {
        }

        public void onLowMemory() throws  {
        }

        public void onTrimMemory(int $i0) throws  {
            if ($i0 == 20) {
                WEBLINK.this.onGoingToBackground();
            }
        }

        public void onConfigurationChanged(Configuration newConfig) throws  {
        }
    }

    public synchronized boolean init(Context $r2) throws  {
        boolean $z0;
        $z0 = false;
        if (this.m_context == null && $r2 != null) {
            WEBLINK weblink;
            ServiceDispatcher serviceDispatcher;
            if ($r2 instanceof IWLInternalHomeApp) {
                this.m_homeApp = (IWLInternalHomeApp) $r2;
            }
            $r2 = $r2.getApplicationContext();
            this.m_context = $r2;
            TextViewObserver.getInstance().Init();
            TextViewObserver.getInstance().registerViewNotification(this.m_textViewNotification);
            this.m_isActive = false;
            this.m_surface = null;
            if (bindService(this.m_context, WebLinkServerCore.WLSERVICE_IWLSERVICE, this.m_connection)) {
                if (bindService(this.m_context, WebLinkServerCore.WLSERVICE_IWLSERVICEPRIVATE, this.m_privateConnection)) {
                    $z0 = true;
                    this.m_isBound = $z0;
                    this.m_pendingActivation = false;
                    this.m_commands.clear();
                    this.m_commandHandlers.clear();
                    WLNotificationManager.getInstance().init();
                    $z0 = this.m_isBound;
                    if (!$z0) {
                        terminate();
                        this.m_context = $r2;
                    } else if (VERSION.SDK_INT >= 14) {
                        weblink = this;
                        this.m_memoryBoss = new MemoryBoss();
                        $r2.registerComponentCallbacks(this.m_memoryBoss);
                    }
                    if (this.m_homeApp == null || !this.m_homeApp.isService()) {
                        ServiceManager.getInstance().init($r2);
                        serviceDispatcher = new ServiceDispatcher(ServiceDispatcherType.WLApp, this.m_context);
                        SecureServiceManager.getInstance().initServiceManager(serviceDispatcher, this.m_context);
                    }
                }
            }
            $z0 = false;
            this.m_isBound = $z0;
            this.m_pendingActivation = false;
            this.m_commands.clear();
            this.m_commandHandlers.clear();
            WLNotificationManager.getInstance().init();
            $z0 = this.m_isBound;
            if (!$z0) {
                terminate();
                this.m_context = $r2;
            } else if (VERSION.SDK_INT >= 14) {
                weblink = this;
                this.m_memoryBoss = new MemoryBoss();
                $r2.registerComponentCallbacks(this.m_memoryBoss);
            }
            ServiceManager.getInstance().init($r2);
            serviceDispatcher = new ServiceDispatcher(ServiceDispatcherType.WLApp, this.m_context);
            SecureServiceManager.getInstance().initServiceManager(serviceDispatcher, this.m_context);
        }
        return $z0;
    }

    public synchronized void terminate() throws  {
        if (this.m_context != null) {
            if (this.m_isBound) {
                this.m_context.unbindService(this.m_connection);
                this.m_isBound = false;
            }
            this.m_context = null;
        }
    }

    public boolean isInitialized() throws  {
        return this.m_isBound;
    }

    public Context getContext() throws  {
        return this.m_context;
    }

    public Handler getHandler() throws  {
        return this.m_handler;
    }

    public boolean isConnectedToClient() throws  {
        return this.m_surface != null;
    }

    public boolean setUiMode(int $i0) throws  {
        if (!isConnectedToClient() && $i0 >= 0 && $i0 <= 3) {
            this.m_uiMode = $i0;
            return true;
        } else if ($i0 >= 0) {
            return false;
        } else {
            if ($i0 <= 3) {
                return false;
            }
            Log.e("WEBLINK", "Unsupported UI Mode set to WEBLINK: " + $i0);
            return false;
        }
    }

    public int getUiMode() throws  {
        return this.m_uiMode;
    }

    public void setUpdatesEnabled(boolean $z0) throws  {
        if (this.m_app != null) {
            try {
                this.m_app.setUpdatesEnabled($z0);
            } catch (RemoteException e) {
            }
        }
    }

    public boolean setCustomUIModeHandler(ICustomUiHandler $r1) throws  {
        if (isConnectedToClient()) {
            return false;
        }
        this.m_customHandler = $r1;
        if (this.m_customHandler == null) {
            return false;
        }
        this.m_uiMode = 0;
        return false;
    }

    public void registerConnectionListener(IConnectionListener $r1) throws  {
        if ($r1 != null) {
            this.m_connectionListeners.add($r1);
        }
    }

    public void unregisterConnectionListener(IConnectionListener $r1) throws  {
        if ($r1 != null) {
            this.m_connectionListeners.remove($r1);
        }
    }

    public void registerCommandHandler(ICommandHandler $r1) throws  {
        if ($r1 != null && !this.m_commandHandlers.contains($r1)) {
            this.m_commandHandlers.add($r1);
        }
    }

    public void unregisterCommandHandler(ICommandHandler $r1) throws  {
        if (this.m_commandHandlers.contains($r1)) {
            this.m_commandHandlers.remove($r1);
        }
    }

    public boolean registerForCommand(int $i0) throws  {
        if (this.m_commands.contains(Integer.valueOf($i0))) {
            return false;
        }
        this.m_commands.add(Integer.valueOf($i0));
        if (this.m_app == null) {
            return true;
        }
        try {
            this.m_app.registerForCommand($i0);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean requestActivation() throws  {
        if (this.m_app == null) {
            this.m_pendingActivation = true;
            return false;
        } else if (this.m_isActive) {
            return true;
        } else {
            this.m_pendingActivation = false;
            try {
                this.m_app.requestActivation();
                return true;
            } catch (RemoteException e) {
                return false;
            }
        }
    }

    public boolean sendCommand(Command $r1) throws  {
        final CommThread $r2 = this.m_commThread;
        if ($r1 == null) {
            return false;
        }
        if ($r2 == null) {
            return false;
        }
        final DataBuffer $r5 = $r1.getRawCommandData();
        this.m_executor.submit(new Runnable() {
            public void run() throws  {
                $r2.sendData($r5.getData(), $r5.getPos(), $r5.getSize());
            }
        });
        return true;
    }

    public boolean showWaitIndicator(boolean $z0) throws  {
        return sendCommand(new WaitIndicatorCommand($z0));
    }

    public boolean registerView(View $r1) throws  {
        int $i0 = getUiMode();
        if ($r1 == null) {
            return false;
        }
        if ($i0 != 1 && $i0 != 3) {
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
                Log.e("WEBLINK", "registerView()", $r2);
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
            Log.e("WEBLINK", "unregisterView()", $r2);
            return false;
        }
    }

    public IViewHandler getViewHandler(View $r1) throws  {
        return (IViewHandler) this.m_registeredViews.get($r1);
    }

    public void setKeyboardMode(int $i0) throws  {
        this.m_keyboardMode = $i0;
        if (this.m_app != null) {
            try {
                this.m_app.setKeyboardMode(this.m_keyboardMode);
            } catch (RemoteException e) {
            }
        }
    }

    public int getKeyboardMode() throws  {
        return this.m_keyboardMode;
    }

    public boolean isServerSideKeyboard() throws  {
        boolean $z0 = (getClientFeatures() & 1) != 0;
        String $r2 = "";
        if (this.m_kbdManager != null) {
            try {
                $r2 = this.m_kbdManager.getCurrentKeyboard();
            } catch (RemoteException $r1) {
                Log.e("WEBLINK", "isServerSideKeyboard", $r1);
            }
        } else {
            $r2 = "";
        }
        boolean $z1;
        if ($r2.compareTo(WLTypes.KB_CLIENT_SIDE_ID) == 0) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        if ($z0 && $z1) {
            return false;
        }
        return true;
    }

    public String getClientFeaturesString() throws  {
        if (this.m_service == null) {
            return "";
        }
        try {
            return this.m_service.getClientFeaturesString();
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressLint({"DefaultLocale"})
    public Map<String, String> getClientFeaturesDict() throws  {
        HashMap $r2 = new HashMap();
        if (this.m_service == null) {
            return $r2;
        }
        String $r1 = "";
        try {
            $r1 = this.m_service.getClientFeaturesString();
        } catch (Exception e) {
        }
        if ($r1 == null) {
            return $r2;
        }
        for (String $r12 : $r12.split("\\|")) {
            String[] $r5 = $r12.split("=");
            if ($r5.length == 2) {
                $r2.put($r5[0].toLowerCase(), $r5[1]);
            }
        }
        return $r2;
    }

    public int getClientFeatures() throws  {
        if (this.m_service == null) {
            return 0;
        }
        try {
            return this.m_service.getClientFeatures();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean showKeyboard(View $r1) throws  {
        if (!isServerSideKeyboard()) {
            sendCommand(new ShowKeyboardCommand((short) 0));
            return true;
        } else if ($r1 == null) {
            return false;
        } else {
            if (!($r1 instanceof TextView)) {
                return false;
            }
            EditorInfo $r2 = new EditorInfo();
            IWLInputConnection $r5 = onCreateInputConnection((TextView) $r1, $r2);
            if ($r2.actionId == 0) {
                $r2.actionId = 6;
            }
            if ($r5 == null) {
                return false;
            }
            try {
                this.m_app.showKeyboard($r5, $r2);
                this.m_keyboardView = $r1;
                return true;
            } catch (RemoteException e) {
                return false;
            }
        }
    }

    public void hideKeyboard() throws  {
        if (this.m_app != null) {
            try {
                this.m_app.hideKeyboard();
            } catch (RemoteException e) {
            }
        }
        sendCommand(new HideKeyboardCommand());
    }

    public boolean activateHomeApp() throws  {
        return AppUtils.activateApp(HOME_APP_ID, this.m_context, true);
    }

    public void setUIParamInt(EWLUIParam $r1, int $i0) throws  {
        switch ($r1) {
            case UIP_KEEP_PHONE_DPI:
            case UIP_SCALE_APP_CANVAS_TO_DPI_MODE:
            case UIP_MAX_APP_CANVAS_SCALE_FACTOR_PERCENT:
                this.m_uiParams.put($r1, Integer.valueOf($i0));
                return;
            default:
                return;
        }
    }

    public int getUIParamInt(EWLUIParam $r1) throws  {
        switch ($r1) {
            case UIP_KEEP_PHONE_DPI:
            case UIP_SCALE_APP_CANVAS_TO_DPI_MODE:
            case UIP_MAX_APP_CANVAS_SCALE_FACTOR_PERCENT:
            case UIP_ENABLE_STATIC_TEXTUREBITMAP:
                if (this.m_uiParams.containsKey($r1)) {
                    return ((Integer) this.m_uiParams.get($r1)).intValue();
                }
                return ((Integer) $r1.getDefaultValue()).intValue();
            default:
                return -1;
        }
    }

    private WEBLINK() throws  {
    }

    private static IWLInputConnection onCreateInputConnection(TextView $r0, EditorInfo $r1) throws  {
        if ($r0 == null) {
            return null;
        }
        if ($r1 == null) {
            return null;
        }
        $r1.packageName = $r0.getContext().getPackageName();
        $r1.fieldId = $r0.getId();
        $r1.inputType = $r0.getInputType();
        $r1.imeOptions = $r0.getImeOptions();
        $r1.privateImeOptions = $r0.getPrivateImeOptions();
        $r1.actionLabel = $r0.getImeActionLabel();
        $r1.actionId = $r0.getImeActionId();
        $r1.extras = $r0.getInputExtras(false);
        $r1.hintText = $r0.getHint();
        CharSequence $r5 = $r0.getText();
        if (!($r5 instanceof Editable)) {
            return null;
        }
        Editable $r7 = (Editable) $r5;
        int $i0 = Math.min(Selection.getSelectionStart($r7), Selection.getSelectionEnd($r7));
        $r1.initialSelStart = $r0.getSelectionStart();
        $r1.initialSelEnd = $r0.getSelectionEnd();
        $r1.initialCapsMode = TextUtils.getCapsMode($r7, $i0, $r0.getInputType());
        return new WLEditableConnection($r0);
    }

    static String getAppNameByPID(Context $r0, int $i0) throws  {
        for (RunningAppProcessInfo $r5 : ((ActivityManager) $r0.getSystemService(RecoveryParamConstants.VALUE_ACTIVITY)).getRunningAppProcesses()) {
            if ($r5.pid == $i0) {
                return $r5.processName;
            }
        }
        return "";
    }

    public static final boolean bindService(Context $r0, String $r1, ServiceConnection $r2) throws  {
        return AppUtils.bindService($r0, $r1, $r2);
    }

    public final void waitForContextMenu(ContextMenu menu) throws  {
        int $i0 = getUiMode();
        if (($i0 == 2 && VERSION.SDK_INT < 19) || $i0 == 3) {
            final ArrayList $r3 = WLTreeViewObserver.getInstance().getRootViews();
            this.m_handler.postDelayed(new Runnable() {
                public void run() throws  {
                    ArrayList $r2;
                    while (true) {
                        $r2 = WLTreeViewObserver.getInstance().getRootViews();
                        if ($r2.size() > $r3.size()) {
                            break;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    Iterator $r5 = $r2.iterator();
                    while ($r5.hasNext()) {
                        View $r7 = (View) $r5.next();
                        if (!WEBLINK.this.isViewInViews($r7, $r3)) {
                            float $f0 = WLMirrorMode.instance.getOverrideScaleFactor($r7.getContext());
                            $r7.setPivotX(0.0f);
                            $r7.setPivotY(0.0f);
                            $r7.setScaleX($f0);
                            $r7.setScaleY($f0);
                            WEBLINK.this.moveWindowToOffscreenPosition($r7);
                            return;
                        }
                    }
                }
            }, 100);
        }
    }

    protected void moveWindowToOffscreenPosition(View $r1) throws  {
        int $i1 = (int) (((float) $r1.getHeight()) * $r1.getScaleX());
        int $i0 = (int) (((float) $r1.getWidth()) * $r1.getScaleY());
        if ($i1 <= 0) {
            final View view = $r1;
            this.m_handler.postDelayed(new Runnable() {
                public void run() throws  {
                    WEBLINK.this.moveWindowToOffscreenPosition(view);
                }
            }, 20);
            return;
        }
        int $i2 = getUiMode();
        int $i3 = $i2 == 2 ? WLDisplayManager.getInstance().getLastDisplayWidth() : WLMirrorMode.instance.getWidth();
        $i2 = $i2 == 2 ? WLDisplayManager.getInstance().getLastDisplayHeight() : WLMirrorMode.instance.getHeight();
        moveViewToPosition($r1, WLAlertDialog.getHiddenX(), WLAlertDialog.getHiddenY());
        final View view2 = $r1;
        final int i = $i3;
        final int i2 = $i0;
        final int i3 = $i2;
        final int i4 = $i1;
        this.m_handler.postDelayed(new Runnable() {
            public void run() throws  {
                WEBLINK.this.moveViewToPosition(view2, ((i - i2) / 2) + 4000, ((i3 - i4) / 2) + 4000);
            }
        }, (long) WLAlertDialog.getAlertDialogMoveDelay());
    }

    @SuppressLint({"RtlHardcoded"})
    protected void moveViewToPosition(View $r1, int $i0, int $i1) throws  {
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        LayoutParams $r2 = new LayoutParams();
        $r2.copyFrom($r4);
        $r2.gravity = 51;
        $r2.flags |= 512;
        $r2.x = $i0;
        $r2.y = $i1;
        ((WindowManager) getContext().getSystemService("window")).updateViewLayout($r1, $r2);
    }

    @TargetApi(18)
    protected boolean isViewInViews(@Signature({"(", "Landroid/view/View;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)Z"}) View $r1, @Signature({"(", "Landroid/view/View;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)Z"}) ArrayList<View> $r2) throws  {
        WindowId $r3 = $r1.getWindowId();
        Iterator $r4 = $r2.iterator();
        while ($r4.hasNext()) {
            if (((View) $r4.next()).getWindowId().equals($r3)) {
                return true;
            }
        }
        return false;
    }

    public Context initOffscreenContext(Context $r1) throws  {
        int $i0 = getUiMode();
        if (($i0 != 2 || VERSION.SDK_INT >= 19) && $i0 != 3) {
            return $r1;
        }
        return WLMirrorMode.instance.revertResourceMetrics($r1);
    }

    private void onGoingToBackground() throws  {
        int $i0 = getUiMode();
        if (($i0 == 3 || $i0 == 1) && this.m_homeApp == null && isConnectedToClient()) {
            IWLApp $r1 = this.m_app;
            if ($r1 != null) {
                try {
                    if ($r1.activateApplicationByAppID(HOME_APP_ID)) {
                        return;
                    }
                } catch (RemoteException $r2) {
                    $r2.printStackTrace();
                    return;
                }
            }
            activateHomeApp();
        }
    }

    public static String getVersion() throws  {
        return WLVersionInfo.VERSION;
    }
}
