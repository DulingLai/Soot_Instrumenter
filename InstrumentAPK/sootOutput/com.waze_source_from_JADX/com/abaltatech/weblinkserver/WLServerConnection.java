package com.abaltatech.weblinkserver;

import android.annotation.SuppressLint;
import android.hardware.display.VirtualDisplay;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.weblink.core.WebLinkConnection;
import com.abaltatech.weblink.core.commandhandling.BrowserCommand;
import com.abaltatech.weblink.core.commandhandling.Command;
import com.abaltatech.weblink.core.commandhandling.KeyboardCommand;
import com.abaltatech.weblink.core.commandhandling.MouseCommand;
import com.abaltatech.weblink.core.commandhandling.SetCurrentAppCommand;
import com.abaltatech.weblink.core.commandhandling.SetFpsCommand;
import com.abaltatech.weblink.core.commandhandling.SyncSessionTimeCommand;
import com.abaltatech.weblink.core.commandhandling.TouchCommand;
import com.abaltatech.weblink.servercore.WebLinkServerConnection;
import com.abaltatech.weblinkserver.WLEventUtils.MotionContext;
import dalvik.annotation.Signature;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"NewApi"})
class WLServerConnection extends WebLinkServerConnection {
    static final /* synthetic */ boolean $assertionsDisabled = (!WLServerConnection.class.desiredAssertionStatus());
    private static final int DEFAULT_CAPTURE_INTERVAL = 30;
    private static final String TAG = "WLConn";
    private int m_captureInterval = 30;
    private WLServerDelegate m_delegate = null;
    private EncodingThread m_encodingThread = null;
    private WLKeyboardEventHandler m_focusedLayer = null;
    private ByteBuffer m_frameBuffer = null;
    private boolean m_isCapturing = false;
    private int m_lastMouseAction = -1;
    private long m_lastMouseEventTimestamp = 0;
    private int m_lastMouseX = -1;
    private int m_lastMouseY = -1;
    List<WLLayer> m_layers = null;
    private MotionContext m_motionContext = new MotionContext();
    private WLLayer m_touchTarget = null;
    private boolean m_videoPaused = false;
    private VirtualDisplay m_virtualDisplay = null;
    private int m_virtualDisplayID = -1;

    class C04201 implements Runnable {
        C04201() throws  {
        }

        public void run() throws  {
            WLServerConnection.this.disconnect();
        }
    }

    class C04212 extends WebLinkConnection {
        C04212() throws  {
        }

        protected long getSystemTime() throws  {
            return SystemClock.uptimeMillis();
        }
    }

    private class EncodingThread extends Thread {
        private volatile boolean m_paused = true;
        private volatile boolean m_stopped = false;

        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005e in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r23 = this;
            r0 = r23;
            r0.notifyClientConnected();
        L_0x0005:
            r0 = r23;
            r3 = r0.m_stopped;
            if (r3 != 0) goto L_0x0082;
        L_0x000b:
            r4 = android.os.SystemClock.uptimeMillis();	 Catch:{ InterruptedException -> 0x0081 }
            r0 = r23;
            r3 = r0.m_paused;
            if (r3 != 0) goto L_0x005e;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x0015:
            r0 = r23;	 Catch:{ InterruptedException -> 0x0081 }
            r6 = com.abaltatech.weblinkserver.WLServerConnection.this;	 Catch:{ InterruptedException -> 0x0081 }
            r7 = r6.m_connection;	 Catch:{ InterruptedException -> 0x0081 }
            if (r7 == 0) goto L_0x005e;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x001f:
            r0 = r23;	 Catch:{ InterruptedException -> 0x0081 }
            r6 = com.abaltatech.weblinkserver.WLServerConnection.this;	 Catch:{ InterruptedException -> 0x0081 }
            r7 = r6.m_connection;	 Catch:{ InterruptedException -> 0x0081 }
            r3 = r7.canSendCommand();	 Catch:{ InterruptedException -> 0x0081 }
            if (r3 == 0) goto L_0x005e;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x002d:
            r0 = r23;	 Catch:{ InterruptedException -> 0x0081 }
            r6 = com.abaltatech.weblinkserver.WLServerConnection.this;	 Catch:{ InterruptedException -> 0x0081 }
            r7 = r6.m_connection;	 Catch:{ InterruptedException -> 0x0081 }
            r8 = 1;	 Catch:{ InterruptedException -> 0x0081 }
            r3 = r7.hasCommand(r8);	 Catch:{ InterruptedException -> 0x0081 }
            if (r3 != 0) goto L_0x005e;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x003c:
            r0 = r23;	 Catch:{ InterruptedException -> 0x0081 }
            r6 = com.abaltatech.weblinkserver.WLServerConnection.this;	 Catch:{ InterruptedException -> 0x0081 }
            r9 = r6.m_frameBuffer;	 Catch:{ InterruptedException -> 0x0081 }
            r0 = r23;	 Catch:{ InterruptedException -> 0x0081 }
            r6 = com.abaltatech.weblinkserver.WLServerConnection.this;	 Catch:{ InterruptedException -> 0x0081 }
            r10 = r6.m_frameEncoder;	 Catch:{ InterruptedException -> 0x0081 }
            if (r9 == 0) goto L_0x005e;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x004e:
            if (r10 == 0) goto L_0x005e;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x0050:
            r11 = com.abaltatech.weblinkserver.WLServerApp.getMode();	 Catch:{ InterruptedException -> 0x0081 }
            r8 = 2;	 Catch:{ InterruptedException -> 0x0081 }
            if (r11 != r8) goto L_0x0093;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x0057:
            r13 = r10;	 Catch:{ InterruptedException -> 0x0081 }
            r13 = (com.abaltatech.weblinkserver.IFrameEncoderSurface) r13;	 Catch:{ InterruptedException -> 0x0081 }
            r12 = r13;	 Catch:{ InterruptedException -> 0x0081 }
            r12.encodeSurface();	 Catch:{ InterruptedException -> 0x0081 }
        L_0x005e:
            r14 = android.os.SystemClock.uptimeMillis();	 Catch:{ InterruptedException -> 0x0081 }
            r4 = r14 - r4;
            r0 = r23;	 Catch:{ InterruptedException -> 0x0081 }
            r6 = com.abaltatech.weblinkserver.WLServerConnection.this;	 Catch:{ InterruptedException -> 0x0081 }
            r11 = r6.m_captureInterval;	 Catch:{ InterruptedException -> 0x0081 }
            r14 = (long) r11;
            r4 = r14 - r4;	 Catch:{ InterruptedException -> 0x0081 }
            r16 = 0;	 Catch:{ InterruptedException -> 0x0081 }
            r0 = r16;	 Catch:{ InterruptedException -> 0x0081 }
            r4 = java.lang.Math.max(r0, r4);	 Catch:{ InterruptedException -> 0x0081 }
            r16 = 0;
            r18 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1));
            if (r18 <= 0) goto L_0x0005;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x007d:
            sleep(r4);	 Catch:{ InterruptedException -> 0x0081 }
            goto L_0x0005;
        L_0x0081:
            r19 = move-exception;
        L_0x0082:
            r0 = r23;
            r0.notifyClientDisconnected();
            r20 = "WLConn";
            r21 = "ScreenCaptureThread exited";
            r0 = r20;
            r1 = r21;
            com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);
            return;
        L_0x0093:
            r0 = r23;	 Catch:{ InterruptedException -> 0x0081 }
            r3 = r0.renderLayers();	 Catch:{ InterruptedException -> 0x0081 }
            if (r3 == 0) goto L_0x005e;	 Catch:{ InterruptedException -> 0x0081 }
        L_0x009b:
            r8 = 4;	 Catch:{ InterruptedException -> 0x0081 }
            r22 = 0;	 Catch:{ InterruptedException -> 0x0081 }
            r0 = r22;	 Catch:{ InterruptedException -> 0x0081 }
            r10.encodeImage(r8, r9, r0);	 Catch:{ InterruptedException -> 0x0081 }
            goto L_0x005e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLServerConnection.EncodingThread.run():void");
        }

        public void startCapture() throws  {
            this.m_paused = false;
        }

        public void stopCapture() throws  {
            this.m_paused = true;
        }

        public void interrupt() throws  {
            this.m_stopped = true;
            super.interrupt();
        }

        boolean renderLayers() throws  {
            int $i1 = WLServerConnection.this.m_layers != null ? WLServerConnection.this.m_layers.size() : 0;
            boolean $z0 = $i1 > 0;
            ByteBuffer $r3 = WLServerConnection.this.m_frameBuffer;
            int $i2 = WLServerConnection.this.m_srcWidth;
            int $i3 = WLServerConnection.this.m_srcHeight;
            int $i0 = 0;
            while ($z0 && $i0 < $i1) {
                $z0 = ((WLLayer) WLServerConnection.this.m_layers.get($i0)).canRender();
                $i0++;
            }
            if (!$z0) {
                return $z0;
            }
            $i0 = WLServerConnection.this.m_srcWidth * 4;
            int $i4 = 0;
            while ($z0 && $i4 < $i1) {
                $z0 = ((WLLayer) WLServerConnection.this.m_layers.get($i4)).canRender();
                $r3.position(0);
                ((WLLayer) WLServerConnection.this.m_layers.get($i4)).render($r3, $i2, $i3, $i0);
                $i4++;
            }
            return $z0;
        }

        void notifyClientConnected() throws  {
            int $i0 = WLServerConnection.this.m_layers != null ? WLServerConnection.this.m_layers.size() : 0;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                WLLayer $r4 = (WLLayer) WLServerConnection.this.m_layers.get($i1);
                if ($r4 instanceof WLClientConnectionHandler) {
                    ((WLClientConnectionHandler) $r4).onClientConnected();
                }
            }
        }

        void notifyClientDisconnected() throws  {
            int $i0 = WLServerConnection.this.m_layers != null ? WLServerConnection.this.m_layers.size() : 0;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                WLLayer $r4 = (WLLayer) WLServerConnection.this.m_layers.get($i1);
                if ($r4 instanceof WLClientConnectionHandler) {
                    ((WLClientConnectionHandler) $r4).onClientDisconnected();
                }
            }
        }
    }

    protected void configureVideoEncoder(com.abaltatech.weblink.core.commandhandling.VideoConfigCommand r1) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.abaltatech.weblinkserver.WLServerConnection.configureVideoEncoder(com.abaltatech.weblink.core.commandhandling.VideoConfigCommand):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLServerConnection.configureVideoEncoder(com.abaltatech.weblink.core.commandhandling.VideoConfigCommand):void");
    }

    WLServerConnection(@Signature({"(", "Lcom/abaltatech/weblinkserver/WLServerDelegate;", "Ljava/util/List", "<", "Lcom/abaltatech/weblinkserver/WLLayer;", ">;)V"}) WLServerDelegate $r1, @Signature({"(", "Lcom/abaltatech/weblinkserver/WLServerDelegate;", "Ljava/util/List", "<", "Lcom/abaltatech/weblinkserver/WLLayer;", ">;)V"}) List<WLLayer> $r2) throws  {
        if ($assertionsDisabled || $r1 != null) {
            this.m_delegate = $r1;
            this.m_layers = $r2;
            return;
        }
        throw new AssertionError();
    }

    public void setSize(int $i0, int $i1) throws  {
        this.m_srcWidth = $i0;
        this.m_srcHeight = $i1;
    }

    public void disconnect() throws  {
        if (this.m_delegate != null) {
            this.m_delegate.onClientDisconnected();
        }
        stopCapture();
        if (this.m_encodingThread != null) {
            this.m_encodingThread.interrupt();
            try {
                this.m_encodingThread.join();
            } catch (InterruptedException e) {
            }
            this.m_encodingThread = null;
        }
        releaseDisplay();
        this.m_frameBuffer = null;
        if (WLServerApp.getMode() == 1) {
            WLMirrorLayer.getInstance().terminate();
            WLServer.getInstance().clearLayers();
        }
        super.disconnect();
    }

    private void releaseDisplay() throws  {
        if (this.m_virtualDisplay != null) {
            WLServer.getInstance().notifyWLDisplayRemoved(this.m_virtualDisplay.getDisplay());
            this.m_virtualDisplay.release();
            this.m_virtualDisplay = null;
        }
    }

    public boolean pauseVideo() throws  {
        if (this.m_videoPaused) {
            return false;
        }
        if (this.m_frameEncoder == null) {
            return false;
        }
        stopCapture();
        boolean $z0 = this.m_frameEncoder.suspend();
        this.m_videoPaused = $z0;
        return $z0;
    }

    public boolean resumeVideo() throws  {
        if (!this.m_videoPaused) {
            return false;
        }
        if (this.m_frameEncoder == null) {
            return false;
        }
        boolean $z0 = this.m_frameEncoder.resume();
        if (!$z0) {
            return $z0;
        }
        startCapture();
        this.m_videoPaused = false;
        return $z0;
    }

    public boolean isVideoPaused() throws  {
        return this.m_videoPaused;
    }

    protected boolean prepareConnection(WebLinkConnection $r1) throws  {
        boolean $z0 = super.prepareConnection($r1);
        if (!$z0) {
            return $z0;
        }
        $r1.registerHandler((short) 18, this);
        $r1.registerHandler((short) 16, this);
        $r1.registerHandler((short) 72, this);
        $r1.registerHandler((short) 17, this);
        $r1.registerHandler((short) 19, this);
        $r1.registerHandler((short) 20, this);
        $r1.registerHandler((short) 66, this);
        $r1.registerHandler((short) 71, this);
        $r1.registerHandler((short) 73, this);
        return $z0;
    }

    public boolean handleCommand(Command $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if (!$r1.isValid()) {
            return false;
        }
        switch ($r1.getCommandID()) {
            case (short) 16:
                handleMouseCommand(new MouseCommand($r1.getRawCommandData()));
                return true;
            case (short) 17:
                handleKeyboardCommand(new KeyboardCommand($r1.getRawCommandData()));
                return true;
            case (short) 18:
                handleBrowserCommand(new BrowserCommand($r1.getRawCommandData()));
                return true;
            case (short) 19:
                handleKeyboardShown();
                return true;
            case (short) 20:
                handleKeyboardHidden();
                return true;
            case (short) 66:
                handleSetCurrentApp(new SetCurrentAppCommand($r1.getRawCommandData()));
                return true;
            case (short) 71:
                handleSetFps(new SetFpsCommand($r1.getRawCommandData()));
                return true;
            case (short) 72:
                handleTouchCommand(new TouchCommand($r1.getRawCommandData()));
                return true;
            case (short) 73:
                handleSyncSessionTime(new SyncSessionTimeCommand($r1.getRawCommandData()));
                return true;
            default:
                return super.handleCommand($r1);
        }
    }

    protected boolean configureVirtualView(int $i0, int $i1) throws  {
        if (this.m_delegate != null) {
            this.m_delegate.onClientConnectedWithScreenSize(this.m_srcWidth, this.m_srcHeight);
        }
        if (WLServerApp.getMode() != 1) {
            return true;
        }
        WLMirrorLayer $r2 = WLMirrorLayer.getInstance();
        $r2.terminate();
        $r2.init();
        $r2.setScreenRect(new WLRect(0, 0, $i0, $i1));
        WLServer.getInstance().clearLayers();
        WLServer.getInstance().addLayer($r2);
        return true;
    }

    protected void onVideoConfigurationCompleted() throws  {
        this.m_motionContext.reset();
        this.m_frameBuffer = ByteBuffer.allocateDirect((this.m_srcWidth * this.m_srcHeight) * 4);
        this.m_isCapturing = false;
        this.m_lastMouseAction = -1;
        this.m_lastMouseX = -1;
        this.m_lastMouseY = -1;
        this.m_lastMouseEventTimestamp = 0;
        stopCapture();
        if (this.m_encodingThread != null) {
            this.m_encodingThread.interrupt();
            this.m_encodingThread = null;
        }
        this.m_encodingThread = new EncodingThread();
        this.m_encodingThread.start();
        startCapture();
    }

    protected WebLinkConnection createConnection() throws  {
        return new C04212();
    }

    private boolean canHandle(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        boolean $z0 = false;
        int $i0 = this.m_layers != null ? this.m_layers.size() : 0;
        int $i1 = 0;
        while (!$z0 && $i1 < $i0) {
            $z0 = $r1.isInstance((WLLayer) this.m_layers.get($i1));
            $i1++;
        }
        return $z0;
    }

    private boolean processMouseEvent(WLLayer $r1, MouseCommand $r2) throws  {
        if (!($r1 instanceof WLMouseEventHandler)) {
            return false;
        }
        WLMouseEventHandler $r3 = (WLMouseEventHandler) $r1;
        WLRect $r4 = $r1.getScreenRect();
        float $f2 = (float) this.m_encHeight;
        MotionEvent $r6 = WLEventUtils.createMotionEvent(this.m_motionContext, $r2, $r4.left(), $r4.top(), ((float) this.m_srcWidth) / ((float) this.m_encWidth), ((float) this.m_srcHeight) / $f2);
        if ($r6 == null) {
            return true;
        }
        boolean $z0 = $r3.onMouseEvent($r6);
        $r6.recycle();
        return $z0;
    }

    private void handleMouseCommand(MouseCommand $r1) throws  {
        if (this.m_encWidth <= 0 || this.m_encHeight <= 0) {
            MCSLogger.log(TAG, "handleMouseCommand called before initializing.");
            return;
        }
        int $i5;
        WLLayer $r5;
        boolean $z0 = false;
        int $i2 = $r1.getActionType();
        int $i0 = ($r1.getX() * this.m_srcWidth) / this.m_encWidth;
        int $i1 = ($r1.getY() * this.m_srcHeight) / this.m_encHeight;
        int $i3 = this.m_layers != null ? this.m_layers.size() : 0;
        long $l4 = SystemClock.uptimeMillis();
        if ($i2 == 2) {
            for ($i5 = 0; $i5 < $i3; $i5++) {
                $r5 = (WLLayer) this.m_layers.get(($i3 - $i5) - 1);
                if (($r5 instanceof WLKeyboardEventHandler) && $r5.getScreenRect().contains($i0, $i1)) {
                    this.m_focusedLayer = (WLKeyboardEventHandler) $r5;
                }
            }
        }
        int $i52 = this.m_lastMouseAction;
        if ($i52 != $i2 || $l4 - this.m_lastMouseEventTimestamp > 500 || Math.abs($i0 - this.m_lastMouseX) > 5 || Math.abs($i1 - this.m_lastMouseY) > 5) {
            this.m_lastMouseAction = $i2;
            this.m_lastMouseX = $i0;
            this.m_lastMouseY = $i1;
            this.m_lastMouseEventTimestamp = $l4;
            if (this.m_touchTarget != null) {
                processMouseEvent(this.m_touchTarget, $r1);
            } else {
                $i5 = 0;
                while (!$z0 && $i5 < $i3) {
                    $r5 = (WLLayer) this.m_layers.get(($i3 - $i5) - 1);
                    if ($r5.getScreenRect().contains($i0, $i1)) {
                        this.m_touchTarget = $r5;
                        $z0 = processMouseEvent(this.m_touchTarget, $r1);
                    }
                    $i5++;
                }
            }
            if (WLServerApp.getMode() == 2 && this.m_virtualDisplayID != -1) {
                ArrayList $r9 = WLTreeViewObserver.getInstance().getRootViews(this.m_virtualDisplayID);
                if (!$r9.isEmpty()) {
                    View $r10 = (View) $r9.get($r9.size() - 1);
                    int[] $r2 = new int[2];
                    MotionEvent $r12 = WLEventUtils.createMotionEvent(this.m_motionContext, $r1, 0, 0, ((float) this.m_srcWidth) / ((float) this.m_encWidth), ((float) this.m_srcHeight) / ((float) this.m_encHeight));
                    $r10.getLocationOnScreen($r2);
                    $r12.setLocation($r12.getX() - ((float) $r2[0]), $r12.getY() - ((float) $r2[1]));
                    final View view = $r10;
                    final MotionEvent motionEvent = $r12;
                    $r10.getHandler().post(new Runnable() {
                        public void run() throws  {
                            view.dispatchTouchEvent(motionEvent);
                            motionEvent.recycle();
                        }
                    });
                }
            }
        }
        if ($i2 == 3) {
            this.m_touchTarget = null;
        }
    }

    @SuppressLint({"NewApi"})
    private void handleTouchCommand(TouchCommand $r1) throws  {
        if (this.m_encWidth <= 0 || this.m_encHeight <= 0) {
            MCSLogger.log(TAG, "handleTouchCommand called before initializing.");
            return;
        }
        int $i0 = this.m_layers != null ? this.m_layers.size() : 0;
        MotionEvent $r5 = WLEventUtils.createMotionEvent(this.m_motionContext, $r1, 0, 0, ((float) this.m_srcWidth) / ((float) this.m_encWidth), ((float) this.m_srcHeight) / ((float) this.m_encHeight));
        if ($r5 != null) {
            int $i1;
            WLLayer $r7;
            if ($r5.getActionMasked() == 0) {
                $i1 = (int) $r5.getX();
                int $i2 = (int) $r5.getY();
                for (int $i3 = 0; $i3 < $i0; $i3++) {
                    $r7 = (WLLayer) this.m_layers.get(($i0 - $i3) - 1);
                    if (($r7 instanceof WLKeyboardEventHandler) && $r7.getScreenRect().contains($i1, $i2)) {
                        this.m_focusedLayer = (WLKeyboardEventHandler) $r7;
                    }
                }
            }
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r7 = (WLLayer) this.m_layers.get(($i0 - $i1) - 1);
                if ($r7 instanceof WLTouchEventHandler) {
                    ((WLTouchEventHandler) $r7).onTouchEvent($r5);
                }
            }
            if (WLServerApp.getMode() == 2 && this.m_virtualDisplayID != -1) {
                ArrayList $r12 = WLTreeViewObserver.getInstance().getRootViews(this.m_virtualDisplayID);
                if (!$r12.isEmpty()) {
                    View $r13 = (View) $r12.get($r12.size() - 1);
                    int[] $r2 = new int[2];
                    $r13.getLocationOnScreen($r2);
                    $r5.setLocation($r5.getX() - ((float) $r2[0]), $r5.getY() - ((float) $r2[1]));
                    final View view = $r13;
                    final MotionEvent obtain = MotionEvent.obtain($r5);
                    $r13.getHandler().post(new Runnable() {
                        public void run() throws  {
                            view.dispatchTouchEvent(obtain);
                            obtain.recycle();
                        }
                    });
                }
            }
            $r5.recycle();
        }
    }

    private void handleSetFps(SetFpsCommand $r1) throws  {
        byte $b0 = $r1.getFps();
        byte $b1 = $b0;
        if ($b0 < (byte) 1) {
            $b1 = (byte) 1;
        } else if ($b0 > (byte) 30) {
            $b1 = (byte) 30;
        }
        this.m_captureInterval = 1000 / $b1;
    }

    private void handleSyncSessionTime(SyncSessionTimeCommand $r1) throws  {
        if (this.m_connection != null) {
            this.m_connection.sendCommand(new SyncSessionTimeCommand($r1.getCurrentClientTime(), SystemClock.uptimeMillis()));
        }
    }

    private void handleBrowserCommand(BrowserCommand $r1) throws  {
        boolean $z0 = false;
        if (WLServerApp.getMode() != 2) {
            if (canHandle(WLBrowserEventHandler.class)) {
                List $r9;
                int $i0;
                if (this.m_layers != null) {
                    $r9 = this.m_layers;
                    $i0 = $r9.size();
                } else {
                    $i0 = 0;
                }
                int $i2;
                List $r92;
                switch ($r1.getAction()) {
                    case (byte) 0:
                        $i2 = 0;
                        while (!$z0 && $i2 < $i0) {
                            $r9 = this.m_layers;
                            $r92 = $r9;
                            if ($r9.get($i2) instanceof WLBrowserEventHandler) {
                                $r9 = this.m_layers;
                                $r92 = $r9;
                                $z0 = ((WLBrowserEventHandler) $r9.get($i2)).onGoBack();
                            }
                            $i2++;
                        }
                        break;
                    case (byte) 1:
                        $i2 = 0;
                        while (!$z0 && $i2 < $i0) {
                            $r9 = this.m_layers;
                            $r92 = $r9;
                            if ($r9.get($i2) instanceof WLBrowserEventHandler) {
                                $r9 = this.m_layers;
                                $r92 = $r9;
                                $z0 = ((WLBrowserEventHandler) $r9.get($i2)).onGoHome();
                            }
                            $i2++;
                        }
                        break;
                    case (byte) 2:
                        $i2 = 0;
                        while (!$z0 && $i2 < $i0) {
                            $r9 = this.m_layers;
                            $r92 = $r9;
                            if ($r9.get($i2) instanceof WLBrowserEventHandler) {
                                $r9 = this.m_layers;
                                $r92 = $r9;
                                $z0 = ((WLBrowserEventHandler) $r9.get($i2)).onGoForward();
                            }
                            $i2++;
                        }
                        break;
                    case (byte) 3:
                        $i2 = 0;
                        while (!$z0 && $i2 < $i0) {
                            $r9 = this.m_layers;
                            $r92 = $r9;
                            if ($r9.get($i2) instanceof WLBrowserEventHandler) {
                                $r9 = this.m_layers;
                                $r92 = $r9;
                                $z0 = ((WLBrowserEventHandler) $r9.get($i2)).onGoUrl($r1.getParams());
                            }
                            $i2++;
                        }
                        break;
                    default:
                        MCSLogger.log(TAG, "handleBrowserCommand: Action " + $r1.getAction() + " is not handled!");
                        break;
                }
            }
        } else if ($r1.getAction() == (byte) 0) {
            ArrayList $r3 = WLTreeViewObserver.getInstance().getRootViews(this.m_virtualDisplayID);
            if (!$r3.isEmpty()) {
                final View $r5 = (View) $r3.get($r3.size() - 1);
                $r5.getHandler().post(new Runnable() {
                    public void run() throws  {
                        KeyEvent $r1 = new KeyEvent(0, 4);
                        KeyEvent $r2 = new KeyEvent(1, 4);
                        $r5.dispatchKeyEvent($r1);
                        $r5.dispatchKeyEvent($r2);
                    }
                });
            }
        }
        if (!$z0 && $r1.getAction() == (byte) 1) {
            WLServer.getInstance().switchToHomeApp();
        }
    }

    private void handleKeyboardCommand(KeyboardCommand $r1) throws  {
        final KeyEvent $r2 = WLEventUtils.createKeyEvent($r1.getActionType(), $r1.getVirtualKey());
        if ($r2 == null) {
            return;
        }
        if (WLServerApp.getMode() == 2 && this.m_virtualDisplayID != -1) {
            ArrayList $r4 = WLTreeViewObserver.getInstance().getRootViews(this.m_virtualDisplayID);
            if (!$r4.isEmpty()) {
                final View $r6 = (View) $r4.get($r4.size() - 1);
                $r6.getHandler().post(new Runnable() {
                    public void run() throws  {
                        $r6.dispatchKeyEvent($r2);
                    }
                });
            }
        } else if (this.m_focusedLayer != null) {
            this.m_focusedLayer.onKeyEvent($r2);
        }
    }

    private void handleKeyboardShown() throws  {
        if (this.m_focusedLayer != null) {
            this.m_focusedLayer.onKeyboardShown();
        }
    }

    private void handleKeyboardHidden() throws  {
        if (this.m_focusedLayer != null) {
            this.m_focusedLayer.onKeyboardHidden();
        }
    }

    private void handleSetCurrentApp(SetCurrentAppCommand $r1) throws  {
        String $r2 = $r1.getAppID();
        String $r3 = $r1.getAppParams();
        WLServer $r4 = WLServer.getInstance();
        boolean $z0 = false;
        if ($r2.startsWith($r4.getAppID()) && canHandle(WLCurrentAppHandler.class)) {
            int $i0 = this.m_layers != null ? this.m_layers.size() : 0;
            int $i1 = 0;
            while (!$z0 && $i1 < $i0) {
                if (this.m_layers.get($i1) instanceof WLCurrentAppHandler) {
                    $z0 = ((WLCurrentAppHandler) this.m_layers.get($i1)).onSetCurrentApp($r2, $r3);
                }
                $i1++;
            }
        }
        if (!$z0) {
            $r4.switchToApp($r2, $r3);
        }
    }

    private synchronized void startCapture() throws  {
        if (!this.m_isCapturing) {
            this.m_isCapturing = true;
            this.m_encodingThread.startCapture();
        }
    }

    private synchronized void stopCapture() throws  {
        if (this.m_isCapturing) {
            this.m_isCapturing = false;
            this.m_encodingThread.stopCapture();
        }
    }
}
