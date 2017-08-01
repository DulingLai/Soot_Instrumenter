package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Transformation;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.weblink.core.commandhandling.BrowserCommand;
import com.abaltatech.mcp.weblink.core.commandhandling.Command;
import com.abaltatech.mcp.weblink.sdk.WEBLINK.ICommandHandler;
import com.abaltatech.mcp.weblink.sdk.widgets.IWLCustomDraw;
import com.abaltatech.mcp.weblink.sdk.widgets.IWLNotDrawable;
import com.abaltatech.mcp.weblink.sdk.widgets.IWLPostChildrenDraw;
import com.abaltatech.mcp.weblink.sdk.widgets.WLNotDrawableLinearLayout;
import com.abaltatech.mcp.weblink.utils.WLEventUtils;
import com.abaltatech.mcp.weblink.utils.WLImageUtils;
import com.abaltatech.mcp.weblink.utils.WLSurface;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class WLMirrorMode implements OnHierarchyChangeListener {
    private static final int DIRTY_OPAQUE_FLAG = 6291456;
    private static final int FRAME_AVERAGE_COUNT = 64;
    private static final String TAG = "WLMirrorMode";
    public static final int TRANSLATE_PRESENTATION_X = 4000;
    public static final int TRANSLATE_PRESENTATION_Y = 4000;
    public static final WLMirrorMode instance = new WLMirrorMode();
    private static final String[] m_hierarchyIndents = new String[100];
    private static boolean m_isFullScreenMode = false;
    private static final int m_maxIndent = 100;
    private static final Field ms_fieldChildrenCount;
    private static final Field ms_fieldPrivateFlags;
    private int m_clientDpiX = 160;
    private int m_clientDpiY = 160;
    private final ICommandHandler m_commandHandler = new C03522();
    private boolean m_debugOverlayEnabled = false;
    private Paint m_debugTextPaint = new Paint(0);
    private Display m_display;
    private int m_displayID = -1;
    private boolean m_extendedLoggingEnabled = false;
    private int m_extendedLoggingPeriod = 0;
    private long[] m_frameTimeArray = new long[64];
    private int m_frameTimeCounter = 0;
    private long m_frameTimeMovingAverage = 0;
    private int m_height;
    private int m_hierarchyLevel;
    private boolean m_isActive;
    private long m_lastLoggingTime = 0;
    private final HashSet<View> m_layoutParamViews = new HashSet();
    private boolean m_mirrorModeDpiOverride = false;
    private final HashSet<View> m_nondrawableViews = new HashSet();
    private boolean m_offscreenModeDpiOverride = true;
    private final HashSet<View> m_offscreenViews = new HashSet();
    private float m_offsetScreenScaleX = 1.0f;
    private float m_offsetScreenScaleY = 1.0f;
    private Configuration m_originalConfiguration;
    private DisplayMetrics m_originalMetrics;
    private final int[] m_position = new int[2];
    private float m_scaleX;
    private float m_scaleY;
    private final HashSet<View> m_skipDrawableViews = new HashSet();
    private ConcurrentHashMap<View, IViewHandler> m_specialViews = new ConcurrentHashMap();
    private Surface m_surface;
    private Transformation m_transformation = new Transformation();
    private int m_translateX;
    private int m_translateY;
    private Handler m_uiHandler;
    private int m_width;

    class C03522 implements ICommandHandler {

        class C03511 implements Runnable {
            C03511() throws  {
            }

            public void run() throws  {
                ArrayList $r5 = WLMirrorMode.this.getTheRootViews();
                if (!$r5.isEmpty()) {
                    KeyEvent $r1 = new KeyEvent(0, 4);
                    KeyEvent $r2 = new KeyEvent(1, 4);
                    View $r7 = (View) $r5.get($r5.size() - 1);
                    if ($r7.getContext() instanceof Activity) {
                        Activity $r9 = (Activity) $r7.getContext();
                        $r9.dispatchKeyEvent($r1);
                        $r9.dispatchKeyEvent($r2);
                        return;
                    }
                    $r7.dispatchKeyEvent($r1);
                    $r7.dispatchKeyEvent($r2);
                }
            }
        }

        C03522() throws  {
        }

        public boolean onCommand(Command $r1) throws  {
            if (!WLMirrorMode.this.m_isActive) {
                return false;
            }
            switch ($r1.getCommandID()) {
                case (short) 18:
                    return handleBrowserCommand(new BrowserCommand($r1.getRawCommandData()));
                default:
                    return false;
            }
        }

        private boolean handleBrowserCommand(BrowserCommand $r1) throws  {
            if ($r1.getAction() == (byte) 0) {
                WEBLINK.instance.getHandler().post(new C03511());
                return true;
            } else if ($r1.getAction() != (byte) 1) {
                return false;
            } else {
                WEBLINK.instance.activateHomeApp();
                return false;
            }
        }
    }

    private static class GLSurfaceViewHandler implements Renderer, IViewHandler {
        private static final int MAX_COUNTER = 100;
        private static final Field mFieldRenderer;
        private static int m_index = 0;
        private static boolean m_logEnabled = false;
        private final String TAG = getTag();
        private Bitmap m_backBuffer;
        private int m_drawCounter = 0;
        private int m_onDrawFrameCounter = 0;
        private Renderer m_renderer;
        private final int[] m_storage = new int[4];
        private GLSurfaceView m_view;

        public synchronized void draw(android.graphics.Canvas r27, float r28, float r29, int r30, int r31, float r32, float r33) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:15:0x01c0 in {4, 7, 12, 14, 16, 20, 23, 26} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
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
            r26 = this;
            monitor-enter(r26);
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r5 = r0.m_view;	 Catch:{ Throwable -> 0x01ce }
            r6 = r5.getWidth();	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r5 = r0.m_view;	 Catch:{ Throwable -> 0x01ce }
            r7 = r5.getHeight();	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r8 = r0.m_drawCounter;	 Catch:{ Throwable -> 0x01ce }
            r8 = r8 + 1;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.m_drawCounter = r8;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r8 = r0.m_drawCounter;	 Catch:{ Throwable -> 0x01ce }
            r9 = 100;	 Catch:{ Throwable -> 0x01ce }
            if (r8 <= r9) goto L_0x0028;	 Catch:{ Throwable -> 0x01ce }
        L_0x0023:
            r9 = 1;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.m_drawCounter = r9;	 Catch:{ Throwable -> 0x01ce }
        L_0x0028:
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r8 = r0.m_drawCounter;	 Catch:{ Throwable -> 0x01ce }
            r9 = 1;	 Catch:{ Throwable -> 0x01ce }
            if (r9 != r8) goto L_0x01c4;	 Catch:{ Throwable -> 0x01ce }
        L_0x002f:
            r10 = 1;	 Catch:{ Throwable -> 0x01ce }
        L_0x0030:
            r11 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01ce }
            r11.<init>();	 Catch:{ Throwable -> 0x01ce }
            r12 = "draw(), m_onDrawFrameCounter: ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r8 = r0.m_onDrawFrameCounter;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r8);	 Catch:{ Throwable -> 0x01ce }
            r13 = r11.toString();	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.log(r13, r10);	 Catch:{ Throwable -> 0x01ce }
            r14 = (float) r6;	 Catch:{ Throwable -> 0x01ce }
            r0 = r28;	 Catch:{ Throwable -> 0x01ce }
            r14 = r14 / r0;	 Catch:{ Throwable -> 0x01ce }
            r15 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;	 Catch:{ Throwable -> 0x01ce }
            r14 = r14 + r15;	 Catch:{ Throwable -> 0x01ce }
            r6 = (int) r14;	 Catch:{ Throwable -> 0x01ce }
            r14 = (float) r7;	 Catch:{ Throwable -> 0x01ce }
            r0 = r29;	 Catch:{ Throwable -> 0x01ce }
            r14 = r14 / r0;	 Catch:{ Throwable -> 0x01ce }
            r15 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;	 Catch:{ Throwable -> 0x01ce }
            r14 = r14 + r15;	 Catch:{ Throwable -> 0x01ce }
            r7 = (int) r14;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r16 = r0.getViewRenderer();	 Catch:{ Throwable -> 0x01ce }
            r0 = r16;	 Catch:{ Throwable -> 0x01ce }
            r1 = r26;	 Catch:{ Throwable -> 0x01ce }
            if (r0 != r1) goto L_0x01d1;	 Catch:{ Throwable -> 0x01ce }
        L_0x006a:
            r11 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01ce }
            r11.<init>();	 Catch:{ Throwable -> 0x01ce }
            r12 = "draw(), scaleX = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r28;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", scaleY = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r29;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", offsetX = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r30;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", offsetY = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r31;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", viewHierarchyScaleX = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r32;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", viewHierarchyScaleY = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r33;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r13 = r11.toString();	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.log(r13, r10);	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0.m_backBuffer;	 Catch:{ Throwable -> 0x01ce }
            r17 = r0;	 Catch:{ Throwable -> 0x01ce }
            if (r17 == 0) goto L_0x01c6;	 Catch:{ Throwable -> 0x01ce }
        L_0x00c8:
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r5 = r0.m_view;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0.m_storage;	 Catch:{ Throwable -> 0x01ce }
            r18 = r0;	 Catch:{ Throwable -> 0x01ce }
            r5.getLocationOnScreen(r0);	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0.m_storage;	 Catch:{ Throwable -> 0x01ce }
            r18 = r0;	 Catch:{ Throwable -> 0x01ce }
            r9 = 0;	 Catch:{ Throwable -> 0x01ce }
            r30 = r18[r9];	 Catch:{ Throwable -> 0x01ce }
            r0 = r30;	 Catch:{ Throwable -> 0x01ce }
            r0 = (float) r0;	 Catch:{ Throwable -> 0x01ce }
            r32 = r0;	 Catch:{ Throwable -> 0x01ce }
            r28 = r32 / r28;	 Catch:{ Throwable -> 0x01ce }
            r15 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;	 Catch:{ Throwable -> 0x01ce }
            r0 = r28;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0 + r15;	 Catch:{ Throwable -> 0x01ce }
            r28 = r0;	 Catch:{ Throwable -> 0x01ce }
            r0 = (int) r0;	 Catch:{ Throwable -> 0x01ce }
            r30 = r0;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0.m_storage;	 Catch:{ Throwable -> 0x01ce }
            r18 = r0;	 Catch:{ Throwable -> 0x01ce }
            r9 = 1;	 Catch:{ Throwable -> 0x01ce }
            r31 = r18[r9];	 Catch:{ Throwable -> 0x01ce }
            r0 = r31;	 Catch:{ Throwable -> 0x01ce }
            r0 = (float) r0;	 Catch:{ Throwable -> 0x01ce }
            r28 = r0;	 Catch:{ Throwable -> 0x01ce }
            r1 = r29;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0 / r1;	 Catch:{ Throwable -> 0x01ce }
            r28 = r0;	 Catch:{ Throwable -> 0x01ce }
            r15 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;	 Catch:{ Throwable -> 0x01ce }
            r0 = r28;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0 + r15;	 Catch:{ Throwable -> 0x01ce }
            r28 = r0;	 Catch:{ Throwable -> 0x01ce }
            r0 = (int) r0;	 Catch:{ Throwable -> 0x01ce }
            r31 = r0;	 Catch:{ Throwable -> 0x01ce }
            r0 = r27;	 Catch:{ Throwable -> 0x01ce }
            r0.save();	 Catch:{ Throwable -> 0x01ce }
            r19 = 0;	 Catch:{ Throwable -> 0x01ce }
            r0 = r27;	 Catch:{ Throwable -> 0x01ce }
            r1 = r19;	 Catch:{ Throwable -> 0x01ce }
            r0.setMatrix(r1);	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0.m_backBuffer;	 Catch:{ Throwable -> 0x01ce }
            r17 = r0;	 Catch:{ Throwable -> 0x01ce }
            r20 = new android.graphics.Rect;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0.m_backBuffer;	 Catch:{ Throwable -> 0x01ce }
            r21 = r0;	 Catch:{ Throwable -> 0x01ce }
            r8 = r0.getWidth();	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0.m_backBuffer;	 Catch:{ Throwable -> 0x01ce }
            r21 = r0;	 Catch:{ Throwable -> 0x01ce }
            r22 = r0.getHeight();	 Catch:{ Throwable -> 0x01ce }
            r9 = 0;	 Catch:{ Throwable -> 0x01ce }
            r23 = 0;	 Catch:{ Throwable -> 0x01ce }
            r0 = r20;	 Catch:{ Throwable -> 0x01ce }
            r1 = r23;	 Catch:{ Throwable -> 0x01ce }
            r2 = r22;	 Catch:{ Throwable -> 0x01ce }
            r0.<init>(r9, r1, r8, r2);	 Catch:{ Throwable -> 0x01ce }
            r24 = new android.graphics.RectF;	 Catch:{ Throwable -> 0x01ce }
            r0 = r30;	 Catch:{ Throwable -> 0x01ce }
            r0 = (float) r0;	 Catch:{ Throwable -> 0x01ce }
            r28 = r0;	 Catch:{ Throwable -> 0x01ce }
            r0 = r31;	 Catch:{ Throwable -> 0x01ce }
            r0 = (float) r0;	 Catch:{ Throwable -> 0x01ce }
            r29 = r0;	 Catch:{ Throwable -> 0x01ce }
            r8 = r30 + r6;	 Catch:{ Throwable -> 0x01ce }
            r0 = (float) r8;	 Catch:{ Throwable -> 0x01ce }
            r32 = r0;	 Catch:{ Throwable -> 0x01ce }
            r8 = r31 + r7;	 Catch:{ Throwable -> 0x01ce }
            r0 = (float) r8;	 Catch:{ Throwable -> 0x01ce }
            r33 = r0;	 Catch:{ Throwable -> 0x01ce }
            r0 = r24;	 Catch:{ Throwable -> 0x01ce }
            r1 = r28;	 Catch:{ Throwable -> 0x01ce }
            r2 = r29;	 Catch:{ Throwable -> 0x01ce }
            r3 = r32;	 Catch:{ Throwable -> 0x01ce }
            r4 = r33;	 Catch:{ Throwable -> 0x01ce }
            r0.<init>(r1, r2, r3, r4);	 Catch:{ Throwable -> 0x01ce }
            r19 = 0;	 Catch:{ Throwable -> 0x01ce }
            r0 = r27;	 Catch:{ Throwable -> 0x01ce }
            r1 = r17;	 Catch:{ Throwable -> 0x01ce }
            r2 = r20;	 Catch:{ Throwable -> 0x01ce }
            r3 = r24;	 Catch:{ Throwable -> 0x01ce }
            r4 = r19;	 Catch:{ Throwable -> 0x01ce }
            r0.drawBitmap(r1, r2, r3, r4);	 Catch:{ Throwable -> 0x01ce }
            r11 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01ce }
            r11.<init>();	 Catch:{ Throwable -> 0x01ce }
            r12 = "draw(), offsetX1 = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r30;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", offsetY1 = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r31;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", offsetX1 + width = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r0 = r30;	 Catch:{ Throwable -> 0x01ce }
            r0 = r0 + r6;	 Catch:{ Throwable -> 0x01ce }
            r30 = r0;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r12 = ", offsetY1 + height = ";	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r12);	 Catch:{ Throwable -> 0x01ce }
            r30 = r31 + r7;	 Catch:{ Throwable -> 0x01ce }
            r0 = r30;	 Catch:{ Throwable -> 0x01ce }
            r11 = r11.append(r0);	 Catch:{ Throwable -> 0x01ce }
            r13 = r11.toString();	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.log(r13, r10);	 Catch:{ Throwable -> 0x01ce }
            r0 = r27;	 Catch:{ Throwable -> 0x01ce }
            r0.restore();	 Catch:{ Throwable -> 0x01ce }
        L_0x01be:
            monitor-exit(r26);
            return;
            goto L_0x01c4;
        L_0x01c1:
            goto L_0x0030;
        L_0x01c4:
            r10 = 0;
            goto L_0x01c1;
        L_0x01c6:
            r12 = "draw() - ERROR: the back buffer is not initialized yet!";	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.log(r12, r10);	 Catch:{ Throwable -> 0x01ce }
            goto L_0x01be;
        L_0x01ce:
            r25 = move-exception;
            monitor-exit(r26);
            throw r25;
        L_0x01d1:
            r12 = "draw(), view not intercapted yet!";	 Catch:{ Throwable -> 0x01ce }
            r9 = 1;	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.log(r12, r9);	 Catch:{ Throwable -> 0x01ce }
            r0 = r26;	 Catch:{ Throwable -> 0x01ce }
            r0.interceptView();	 Catch:{ Throwable -> 0x01ce }
            goto L_0x01be;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLMirrorMode.GLSurfaceViewHandler.draw(android.graphics.Canvas, float, float, int, int, float, float):void");
        }

        static {
            Field $r2 = null;
            if (VERSION.SDK_INT >= 16) {
                try {
                    Field $r1 = GLSurfaceView.class.getDeclaredField("mRenderer");
                    $r2 = $r1;
                    $r1.setAccessible(true);
                } catch (Exception $r0) {
                    Log.e("GLSurfaceViewHandler - static initializer", "init()", $r0);
                }
            }
            mFieldRenderer = $r2;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static java.lang.String getTag() throws  {
            /*
            r0 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.GLSurfaceViewHandler.class;
            monitor-enter(r0);
            r1 = m_index;	 Catch:{ Throwable -> 0x0022 }
            r1 = r1 + 1;
            m_index = r1;	 Catch:{ Throwable -> 0x0022 }
            r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0022 }
            r2.<init>();	 Catch:{ Throwable -> 0x0022 }
            r3 = "GLSurfaceViewHandler ";
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x0022 }
            r1 = m_index;	 Catch:{ Throwable -> 0x0022 }
            r2 = r2.append(r1);	 Catch:{ Throwable -> 0x0022 }
            r4 = r2.toString();	 Catch:{ Throwable -> 0x0022 }
            r0 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.GLSurfaceViewHandler.class;
            monitor-exit(r0);	 Catch:{ Throwable -> 0x0022 }
            return r4;
        L_0x0022:
            r5 = move-exception;
            r0 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.GLSurfaceViewHandler.class;
            monitor-exit(r0);	 Catch:{ Throwable -> 0x0022 }
            throw r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLMirrorMode.GLSurfaceViewHandler.getTag():java.lang.String");
        }

        public GLSurfaceViewHandler(GLSurfaceView $r1) throws  {
            log("GLSurfaceViewHandler()", true);
            this.m_view = $r1;
            interceptView();
        }

        public void attach() throws  {
        }

        public void detach() throws  {
            destroy();
        }

        public void destroy() throws  {
            log("destroy", true);
            if (this.m_view != null) {
                log("destroy - m_view != null", true);
                if (getViewRenderer() == this) {
                    setViewRenderer(this.m_renderer);
                }
                this.m_renderer = null;
                this.m_view = null;
            }
        }

        public void onSurfaceCreated(GL10 $r1, EGLConfig $r2) throws  {
            if (this.m_renderer != null) {
                this.m_renderer.onSurfaceCreated($r1, $r2);
                log("onSurfaceCreated", true);
                return;
            }
            log("onSurfaceCreated - NO renderer!", true);
        }

        public void onSurfaceChanged(GL10 $r1, int $i0, int $i1) throws  {
            log("onSurfaceChanged", true);
            if (this.m_renderer != null) {
                this.m_renderer.onSurfaceChanged($r1, $i0, $i1);
                log("onSurfaceChanged", true);
                return;
            }
            log("onSurfaceChanged - NO renderer!", true);
        }

        public synchronized void onDrawFrame(GL10 $r1) throws  {
            boolean $z0 = true;
            synchronized (this) {
                this.m_onDrawFrameCounter++;
                if (this.m_onDrawFrameCounter > 100) {
                    this.m_onDrawFrameCounter = 1;
                }
                if (1 != this.m_onDrawFrameCounter) {
                    $z0 = false;
                }
                log("onDrawFrame", $z0);
                if (this.m_renderer != null) {
                    log("onDrawFrame - calling m_renderer", $z0);
                    this.m_renderer.onDrawFrame($r1);
                    log("onDrawFrame - m_renderer called", $z0);
                    $r1.glGetIntegerv(DisplayStrings.DS_JOIN_CARPOOL_GOOGLE_CONNECT_LATER, this.m_storage, 0);
                    if (!(this.m_backBuffer != null && this.m_backBuffer.getWidth() == this.m_storage[2] && this.m_backBuffer.getHeight() == this.m_storage[3])) {
                        log("onDrawFrame - will create back buffer", true);
                        if (this.m_backBuffer != null) {
                            this.m_backBuffer.recycle();
                            this.m_backBuffer = null;
                        }
                        this.m_backBuffer = Bitmap.createBitmap(this.m_storage[2], this.m_storage[3], Config.ARGB_8888);
                        log("onDrawFrame - back buffer created", true);
                    }
                    if (this.m_backBuffer != null) {
                        log("onDrawFrame - glReadPixels", $z0);
                        WLImageUtils.glReadPixels(this.m_backBuffer, true);
                        log("onDrawFrame- after glReadPixels", $z0);
                    } else {
                        log("onDrawFrame - failed to create backbuffer", true);
                    }
                } else {
                    log("onDrawFrame - NO Renderer!", true);
                }
            }
        }

        private Renderer getViewRenderer() throws  {
            if (mFieldRenderer == null) {
                return null;
            }
            if (this.m_view == null) {
                return null;
            }
            try {
                return (Renderer) mFieldRenderer.get(this.m_view);
            } catch (Exception $r1) {
                Log.e(this.TAG, "getViewRenderer()", $r1);
                return null;
            }
        }

        private void setViewRenderer(Renderer $r1) throws  {
            if (mFieldRenderer != null && this.m_view != null) {
                try {
                    mFieldRenderer.set(this.m_view, $r1);
                    log("setViewRenderer", true);
                } catch (Exception $r2) {
                    Log.e(this.TAG, "setViewRenderer()", $r2);
                }
            }
        }

        private void interceptView() throws  {
            log("interceptView", true);
            if (mFieldRenderer != null) {
                GLSurfaceViewHandler $r1 = getViewRenderer();
                if ($r1 != this) {
                    setViewRenderer(this);
                    this.m_renderer = $r1;
                    log("renderer changed", true);
                    return;
                }
                log("renderer unchanged", true);
                return;
            }
            log("no mFieldRenderer yet...", true);
        }

        private void log(String $r1, boolean $z0) throws  {
            if (m_logEnabled && $z0) {
                Log.d(this.TAG, $r1);
            }
        }
    }

    private static class NotDrawableViewHandler implements IViewHandler {
        public NotDrawableViewHandler(IWLNotDrawable view) throws  {
        }

        public void attach() throws  {
        }

        public void detach() throws  {
            destroy();
        }

        public synchronized void destroy() throws  {
        }

        public void draw(Canvas canvas, float scaleX, float scaleY, int offsetX, int offsetY, float viewHierarchyScaleX, float viewHierarchyScaleY) throws  {
        }
    }

    private class SurfaceViewHandler implements IViewHandler {
        private static final String TAG = "WLMirrorMode.SurfaceViewHandler";
        private boolean m_attached;
        private final Callback m_customCallback = new C03575();
        private final SurfaceHolder m_customHolder = new C03597();
        private final OnFrameAvailableListener m_frameListener = new C03586();
        private ArrayList<Callback> m_origCallbacks;
        private int m_origFormat;
        private int m_origHeight;
        private SurfaceHolder m_origHolder;
        private int m_origWidth;
        private final int[] m_position = new int[2];
        private WLSurface m_surface;
        private SurfaceView m_view;
        private final Field ms_fieldCallbacks;
        private final Field ms_fieldFormat;
        private final Field ms_fieldHeight;
        private final Field ms_fieldSurfaceHolder;
        private final Field ms_fieldWidth;

        class C03531 implements Runnable {
            C03531() throws  {
            }

            public void run() throws  {
                SurfaceViewHandler.this.attach();
            }
        }

        class C03542 implements Runnable {
            C03542() throws  {
            }

            public void run() throws  {
                if (SurfaceViewHandler.this.m_origWidth > 0 && SurfaceViewHandler.this.m_origHeight > 0) {
                    SurfaceViewHandler.this.m_surface = WLSurface.create(SurfaceViewHandler.this.m_origWidth, SurfaceViewHandler.this.m_origHeight);
                    SurfaceViewHandler.this.m_surface.setOnFrameAvailableListener(SurfaceViewHandler.this.m_frameListener);
                    Iterator $r6 = SurfaceViewHandler.this.m_origCallbacks.iterator();
                    while ($r6.hasNext()) {
                        Callback $r8 = (Callback) $r6.next();
                        $r8.surfaceDestroyed(SurfaceViewHandler.this.m_origHolder);
                        $r8.surfaceCreated(SurfaceViewHandler.this.m_customHolder);
                        $r8.surfaceChanged(SurfaceViewHandler.this.m_customHolder, 1, SurfaceViewHandler.this.m_origWidth, SurfaceViewHandler.this.m_origHeight);
                    }
                }
            }
        }

        class C03553 implements Runnable {
            final /* synthetic */ Object val$waitLock;

            C03553(Object $r2) throws  {
                this.val$waitLock = $r2;
            }

            public void run() throws  {
                synchronized (this.val$waitLock) {
                    Iterator $r4 = SurfaceViewHandler.this.m_origCallbacks.iterator();
                    while ($r4.hasNext()) {
                        ((Callback) $r4.next()).surfaceDestroyed(SurfaceViewHandler.this.m_customHolder);
                    }
                    this.val$waitLock.notify();
                }
            }
        }

        class C03564 implements Runnable {
            final /* synthetic */ Object val$waitLock;

            C03564(Object $r2) throws  {
                this.val$waitLock = $r2;
            }

            public void run() throws  {
                synchronized (this.val$waitLock) {
                    Iterator $r4 = SurfaceViewHandler.this.m_origCallbacks.iterator();
                    while ($r4.hasNext()) {
                        Callback $r6 = (Callback) $r4.next();
                        $r6.surfaceCreated(SurfaceViewHandler.this.m_origHolder);
                        $r6.surfaceChanged(SurfaceViewHandler.this.m_origHolder, SurfaceViewHandler.this.m_origFormat, SurfaceViewHandler.this.m_origWidth, SurfaceViewHandler.this.m_origHeight);
                    }
                    this.val$waitLock.notify();
                }
            }
        }

        class C03575 implements Callback {
            C03575() throws  {
            }

            public void surfaceChanged(SurfaceHolder holder, int $i0, int $i1, int $i2) throws  {
                Log.d(SurfaceViewHandler.TAG, "surfaceChanged()");
                synchronized (SurfaceViewHandler.this) {
                    SurfaceViewHandler.this.m_origFormat = $i0;
                    if (!($i1 == SurfaceViewHandler.this.m_origWidth && $i2 == SurfaceViewHandler.this.m_origHeight)) {
                        SurfaceViewHandler.this.m_origWidth = $i1;
                        SurfaceViewHandler.this.m_origHeight = $i2;
                        SurfaceViewHandler.this.m_surface.destroySurface();
                        SurfaceViewHandler.this.m_surface = WLSurface.create(SurfaceViewHandler.this.m_origWidth, SurfaceViewHandler.this.m_origHeight);
                        SurfaceViewHandler.this.m_surface.setOnFrameAvailableListener(SurfaceViewHandler.this.m_frameListener);
                    }
                }
                Iterator $r8 = SurfaceViewHandler.this.m_origCallbacks.iterator();
                while ($r8.hasNext()) {
                    ((Callback) $r8.next()).surfaceChanged(SurfaceViewHandler.this.m_customHolder, 1, $i1, $i2);
                }
            }

            public void surfaceCreated(SurfaceHolder holder) throws  {
                Log.d(SurfaceViewHandler.TAG, "surfaceCreated()");
                synchronized (SurfaceViewHandler.this) {
                    SurfaceViewHandler.this.m_surface = WLSurface.create(SurfaceViewHandler.this.m_origWidth, SurfaceViewHandler.this.m_origHeight);
                    SurfaceViewHandler.this.m_surface.setOnFrameAvailableListener(SurfaceViewHandler.this.m_frameListener);
                    Iterator $r8 = SurfaceViewHandler.this.m_origCallbacks.iterator();
                    while ($r8.hasNext()) {
                        ((Callback) $r8.next()).surfaceCreated(SurfaceViewHandler.this.m_customHolder);
                    }
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) throws  {
                Log.d(SurfaceViewHandler.TAG, "surfaceDestroyed()");
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_surface != null) {
                        SurfaceViewHandler.this.m_surface.destroySurface();
                        SurfaceViewHandler.this.m_surface = null;
                    }
                    Iterator $r6 = SurfaceViewHandler.this.m_origCallbacks.iterator();
                    while ($r6.hasNext()) {
                        ((Callback) $r6.next()).surfaceDestroyed(SurfaceViewHandler.this.m_customHolder);
                    }
                }
            }
        }

        class C03586 implements OnFrameAvailableListener {
            C03586() throws  {
            }

            public void onFrameAvailable(SurfaceTexture surfaceTexture) throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (!(SurfaceViewHandler.this.m_surface == null || SurfaceViewHandler.this.m_origHolder == null)) {
                        Canvas $r6 = SurfaceViewHandler.this.m_origHolder.lockCanvas(null);
                        if ($r6 != null) {
                            $r6.drawBitmap(SurfaceViewHandler.this.m_surface.getBitmap(), 0.0f, 0.0f, null);
                            SurfaceViewHandler.this.m_origHolder.unlockCanvasAndPost($r6);
                        }
                    }
                }
            }
        }

        class C03597 implements SurfaceHolder {
            C03597() throws  {
            }

            public void addCallback(Callback $r1) throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_origCallbacks != null) {
                        SurfaceViewHandler.this.m_origCallbacks.add($r1);
                    }
                }
            }

            public void removeCallback(Callback $r1) throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_origCallbacks != null) {
                        SurfaceViewHandler.this.m_origCallbacks.remove($r1);
                    }
                }
            }

            public boolean isCreating() throws  {
                boolean $z0;
                synchronized (SurfaceViewHandler.this) {
                    $z0 = SurfaceViewHandler.this.m_origHolder != null ? SurfaceViewHandler.this.m_origHolder.isCreating() : false;
                }
                return $z0;
            }

            @Deprecated
            public void setType(@Deprecated int $i0) throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_origHolder != null) {
                        SurfaceViewHandler.this.m_origHolder.setType($i0);
                    }
                }
            }

            public void setFixedSize(int $i0, int $i1) throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_origHolder != null) {
                        SurfaceViewHandler.this.m_origHolder.setFixedSize($i0, $i1);
                    }
                }
            }

            public void setSizeFromLayout() throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_origHolder != null) {
                        SurfaceViewHandler.this.m_origHolder.setSizeFromLayout();
                    }
                }
            }

            public void setFormat(int $i0) throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_origHolder != null) {
                        SurfaceViewHandler.this.m_origHolder.setFormat($i0);
                    }
                }
            }

            public void setKeepScreenOn(boolean $z0) throws  {
                synchronized (SurfaceViewHandler.this) {
                    if (SurfaceViewHandler.this.m_origHolder != null) {
                        SurfaceViewHandler.this.m_origHolder.setKeepScreenOn($z0);
                    }
                }
            }

            public Canvas lockCanvas() throws  {
                return lockCanvas(null);
            }

            public Canvas lockCanvas(Rect $r1) throws  {
                Surface $r5 = SurfaceViewHandler.this.m_surface != null ? SurfaceViewHandler.this.m_surface.getSurface() : null;
                if ($r5 == null) {
                    return null;
                }
                try {
                    return $r5.lockCanvas($r1);
                } catch (Exception $r2) {
                    Log.e(SurfaceViewHandler.TAG, "lockCanvas: ", $r2);
                    return null;
                }
            }

            public void unlockCanvasAndPost(Canvas $r1) throws  {
                Surface $r4 = SurfaceViewHandler.this.m_surface != null ? SurfaceViewHandler.this.m_surface.getSurface() : null;
                if ($r4 != null && $r1 != null) {
                    $r4.unlockCanvasAndPost($r1);
                }
            }

            public Rect getSurfaceFrame() throws  {
                Rect $r4;
                synchronized (SurfaceViewHandler.this) {
                    $r4 = SurfaceViewHandler.this.m_origHolder != null ? SurfaceViewHandler.this.m_origHolder.getSurfaceFrame() : null;
                }
                return $r4;
            }

            public Surface getSurface() throws  {
                if (SurfaceViewHandler.this.m_surface != null) {
                    return SurfaceViewHandler.this.m_surface.getSurface();
                }
                return null;
            }
        }

        public synchronized void destroy() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:76:0x0223 in {12, 19, 22, 30, 32, 40, 45, 46, 49, 54, 59, 61, 68, 75, 77, 81} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
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
            r37 = this;
            monitor-enter(r37);
            r0 = r37;	 Catch:{ Throwable -> 0x0188 }
            r3 = r0.m_attached;	 Catch:{ Throwable -> 0x0188 }
            if (r3 == 0) goto L_0x0098;
        L_0x0007:
            r4 = new java.lang.StringBuilder;
            r4.<init>();	 Catch:{  }
            r5 = "destroy() on ";	 Catch:{  }
            r4 = r4.append(r5);	 Catch:{  }
            r6 = java.lang.Thread.currentThread();	 Catch:{  }
            r4 = r4.append(r6);	 Catch:{  }
            r7 = r4.toString();	 Catch:{  }
            r5 = "WLMirrorMode.SurfaceViewHandler";	 Catch:{  }
            android.util.Log.d(r5, r7);	 Catch:{  }
            r8 = android.os.Looper.myLooper();	 Catch:{  }
            r9 = android.os.Looper.getMainLooper();	 Catch:{  }
            if (r8 != r9) goto L_0x00aa;	 Catch:{  }
        L_0x002d:
            r0 = r37;	 Catch:{  }
            r10 = r0.m_origCallbacks;	 Catch:{  }
            r11 = r10.iterator();	 Catch:{  }
        L_0x0035:
            r3 = r11.hasNext();	 Catch:{  }
            if (r3 == 0) goto L_0x00ce;	 Catch:{  }
        L_0x003b:
            r12 = r11.next();	 Catch:{  }
            r14 = r12;	 Catch:{  }
            r14 = (android.view.SurfaceHolder.Callback) r14;	 Catch:{  }
            r13 = r14;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r15 = r0.m_customHolder;	 Catch:{  }
            r13.surfaceDestroyed(r15);	 Catch:{  }
            goto L_0x0035;
        L_0x004b:
            r16 = move-exception;
            r5 = "WLMirrorMode.SurfaceViewHandler";	 Catch:{ Throwable -> 0x0145 }
            r17 = "destroy()";	 Catch:{ Throwable -> 0x0145 }
            r0 = r17;	 Catch:{ Throwable -> 0x0145 }
            r1 = r16;	 Catch:{ Throwable -> 0x0145 }
            android.util.Log.e(r5, r0, r1);	 Catch:{ Throwable -> 0x0145 }
            r18 = -1;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origFormat = r0;	 Catch:{ Throwable -> 0x0188 }
            r18 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origWidth = r0;	 Catch:{ Throwable -> 0x0188 }
            r18 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origHeight = r0;	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origCallbacks = r0;	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origHolder = r0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r37;	 Catch:{ Throwable -> 0x0188 }
            r0 = r0.m_surface;	 Catch:{ Throwable -> 0x0188 }
            r20 = r0;	 Catch:{ Throwable -> 0x0188 }
            if (r20 == 0) goto L_0x0098;	 Catch:{ Throwable -> 0x0188 }
        L_0x0087:
            r0 = r37;	 Catch:{ Throwable -> 0x0188 }
            r0 = r0.m_surface;	 Catch:{ Throwable -> 0x0188 }
            r20 = r0;	 Catch:{ Throwable -> 0x0188 }
            r0.destroySurface();	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_surface = r0;	 Catch:{ Throwable -> 0x0188 }
        L_0x0098:
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_view = r0;	 Catch:{ Throwable -> 0x0188 }
            r18 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_attached = r0;	 Catch:{ Throwable -> 0x0188 }
            monitor-exit(r37);
            return;
        L_0x00aa:
            r12 = new java.lang.Object;	 Catch:{ Throwable -> 0x0145 }
            r12.<init>();	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.this;	 Catch:{  }
            r21 = r0;	 Catch:{  }
            r22 = r0.m_uiHandler;	 Catch:{  }
            r23 = new com.abaltatech.mcp.weblink.sdk.WLMirrorMode$SurfaceViewHandler$3;	 Catch:{  }
            r0 = r23;	 Catch:{  }
            r1 = r37;	 Catch:{  }
            r0.<init>(r12);	 Catch:{  }
            r0 = r22;	 Catch:{  }
            r1 = r23;	 Catch:{  }
            r0.post(r1);	 Catch:{  }
            monitor-enter(r12);	 Catch:{ Exception -> 0x004b }
            r12.wait();	 Catch:{ InterruptedException -> 0x0227 }
        L_0x00cd:
            monitor-exit(r12);	 Catch:{ Throwable -> 0x018b }
        L_0x00ce:
            r0 = r37;	 Catch:{  }
            r0 = r0.ms_fieldCallbacks;	 Catch:{  }
            r24 = r0;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = r0.m_view;	 Catch:{  }
            r25 = r0;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r10 = r0.m_origCallbacks;	 Catch:{  }
            r0 = r24;	 Catch:{  }
            r1 = r25;	 Catch:{  }
            r0.set(r1, r10);	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = r0.ms_fieldSurfaceHolder;	 Catch:{  }
            r24 = r0;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = r0.m_view;	 Catch:{  }
            r25 = r0;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r15 = r0.m_origHolder;	 Catch:{  }
            r0 = r24;	 Catch:{  }
            r1 = r25;	 Catch:{  }
            r0.set(r1, r15);	 Catch:{  }
            r8 = android.os.Looper.myLooper();	 Catch:{  }
            r9 = android.os.Looper.getMainLooper();	 Catch:{  }
            if (r8 != r9) goto L_0x018e;	 Catch:{  }
        L_0x0106:
            r0 = r37;	 Catch:{  }
            r10 = r0.m_origCallbacks;	 Catch:{  }
            r11 = r10.iterator();	 Catch:{  }
        L_0x010e:
            r3 = r11.hasNext();	 Catch:{  }
            if (r3 == 0) goto L_0x01b2;	 Catch:{  }
        L_0x0114:
            r12 = r11.next();	 Catch:{  }
            r26 = r12;	 Catch:{  }
            r26 = (android.view.SurfaceHolder.Callback) r26;	 Catch:{  }
            r13 = r26;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r15 = r0.m_origHolder;	 Catch:{  }
            r13.surfaceCreated(r15);	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r15 = r0.m_origHolder;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = r0.m_origFormat;	 Catch:{  }
            r27 = r0;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = r0.m_origWidth;	 Catch:{  }
            r28 = r0;	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = r0.m_origHeight;	 Catch:{  }
            r29 = r0;	 Catch:{  }
            r0 = r27;	 Catch:{  }
            r1 = r28;	 Catch:{  }
            r2 = r29;	 Catch:{  }
            r13.surfaceChanged(r15, r0, r1, r2);	 Catch:{  }
            goto L_0x010e;
        L_0x0145:
            r30 = move-exception;
            r18 = -1;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origFormat = r0;	 Catch:{ Throwable -> 0x0188 }
            r18 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origWidth = r0;	 Catch:{ Throwable -> 0x0188 }
            r18 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origHeight = r0;	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origCallbacks = r0;	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origHolder = r0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r37;	 Catch:{ Throwable -> 0x0188 }
            r0 = r0.m_surface;	 Catch:{ Throwable -> 0x0188 }
            r20 = r0;	 Catch:{ Throwable -> 0x0188 }
            if (r20 == 0) goto L_0x0187;	 Catch:{ Throwable -> 0x0188 }
        L_0x0176:
            r0 = r37;	 Catch:{ Throwable -> 0x0188 }
            r0 = r0.m_surface;	 Catch:{ Throwable -> 0x0188 }
            r20 = r0;	 Catch:{ Throwable -> 0x0188 }
            r0.destroySurface();	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_surface = r0;	 Catch:{ Throwable -> 0x0188 }
        L_0x0187:
            throw r30;	 Catch:{ Throwable -> 0x0188 }
        L_0x0188:
            r31 = move-exception;
            monitor-exit(r37);
            throw r31;
        L_0x018b:
            r32 = move-exception;
            monitor-exit(r12);	 Catch:{ Throwable -> 0x018b }
            throw r32;	 Catch:{  }
        L_0x018e:
            r12 = new java.lang.Object;	 Catch:{  }
            r12.<init>();	 Catch:{  }
            r0 = r37;	 Catch:{  }
            r0 = com.abaltatech.mcp.weblink.sdk.WLMirrorMode.this;	 Catch:{  }
            r21 = r0;	 Catch:{  }
            r22 = r0.m_uiHandler;	 Catch:{  }
            r33 = new com.abaltatech.mcp.weblink.sdk.WLMirrorMode$SurfaceViewHandler$4;	 Catch:{  }
            r0 = r33;	 Catch:{  }
            r1 = r37;	 Catch:{  }
            r0.<init>(r12);	 Catch:{  }
            r0 = r22;	 Catch:{  }
            r1 = r33;	 Catch:{  }
            r0.post(r1);	 Catch:{  }
            monitor-enter(r12);	 Catch:{ Exception -> 0x004b }
            r12.wait();	 Catch:{ InterruptedException -> 0x0229 }
        L_0x01b1:
            monitor-exit(r12);	 Catch:{ Throwable -> 0x0220 }
        L_0x01b2:
            r18 = -1;	 Catch:{  }
            r0 = r18;	 Catch:{  }
            r1 = r37;	 Catch:{  }
            r1.m_origFormat = r0;	 Catch:{  }
            r18 = 0;	 Catch:{  }
            r0 = r18;	 Catch:{  }
            r1 = r37;	 Catch:{  }
            r1.m_origWidth = r0;	 Catch:{  }
            r18 = 0;	 Catch:{  }
            r0 = r18;	 Catch:{  }
            r1 = r37;	 Catch:{  }
            r1.m_origHeight = r0;	 Catch:{  }
            r19 = 0;	 Catch:{  }
            r0 = r19;	 Catch:{  }
            r1 = r37;	 Catch:{  }
            r1.m_origCallbacks = r0;	 Catch:{  }
            r19 = 0;	 Catch:{  }
            r0 = r19;	 Catch:{  }
            r1 = r37;	 Catch:{  }
            r1.m_origHolder = r0;	 Catch:{  }
            r18 = -1;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origFormat = r0;	 Catch:{ Throwable -> 0x0188 }
            r18 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origWidth = r0;	 Catch:{ Throwable -> 0x0188 }
            r18 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r18;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origHeight = r0;	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origCallbacks = r0;	 Catch:{ Throwable -> 0x0188 }
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_origHolder = r0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r37;	 Catch:{ Throwable -> 0x0188 }
            r0 = r0.m_surface;	 Catch:{ Throwable -> 0x0188 }
            r20 = r0;	 Catch:{ Throwable -> 0x0188 }
            if (r20 == 0) goto L_0x0098;	 Catch:{ Throwable -> 0x0188 }
        L_0x020a:
            r0 = r37;	 Catch:{ Throwable -> 0x0188 }
            r0 = r0.m_surface;	 Catch:{ Throwable -> 0x0188 }
            r20 = r0;	 Catch:{ Throwable -> 0x0188 }
            r0.destroySurface();	 Catch:{ Throwable -> 0x0188 }
            goto L_0x0217;	 Catch:{ Throwable -> 0x0188 }
        L_0x0214:
            goto L_0x0098;	 Catch:{ Throwable -> 0x0188 }
        L_0x0217:
            r19 = 0;	 Catch:{ Throwable -> 0x0188 }
            r0 = r19;	 Catch:{ Throwable -> 0x0188 }
            r1 = r37;	 Catch:{ Throwable -> 0x0188 }
            r1.m_surface = r0;	 Catch:{ Throwable -> 0x0188 }
            goto L_0x0214;
        L_0x0220:
            r34 = move-exception;
            monitor-exit(r12);	 Catch:{ Throwable -> 0x0220 }
            throw r34;	 Catch:{  }
            goto L_0x0227;	 Catch:{  }
        L_0x0224:
            goto L_0x00cd;	 Catch:{  }
        L_0x0227:
            r35 = move-exception;
            goto L_0x0224;
        L_0x0229:
            r36 = move-exception;
            goto L_0x01b1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLMirrorMode.SurfaceViewHandler.destroy():void");
        }

        public SurfaceViewHandler(SurfaceView $r2) throws  {
            Field $r10;
            Field $r12;
            Field $r14;
            Field $r16;
            Field $r18;
            SurfaceViewHandler surfaceViewHandler = this;
            try {
                Class $r8 = Class.forName("android.view.SurfaceView");
                Field $r9 = $r8.getDeclaredField("mCallbacks");
                $r10 = $r9;
                Field $r11 = $r8.getDeclaredField("mSurfaceHolder");
                $r12 = $r11;
                Field $r13 = $r8.getDeclaredField("mFormat");
                $r14 = $r13;
                Field $r15 = $r8.getDeclaredField("mWidth");
                $r16 = $r15;
                Field $r17 = $r8.getDeclaredField("mHeight");
                $r18 = $r17;
                $r9.setAccessible(true);
                $r11.setAccessible(true);
                $r13.setAccessible(true);
                $r15.setAccessible(true);
                $r17.setAccessible(true);
            } catch (Throwable $r3) {
                Log.e(TAG, "SurfaceViewHandler()", $r3);
                $r10 = null;
                $r12 = null;
                $r14 = null;
                $r16 = null;
                $r18 = null;
            }
            this.ms_fieldCallbacks = $r10;
            this.ms_fieldSurfaceHolder = $r12;
            this.ms_fieldFormat = $r14;
            this.ms_fieldWidth = $r16;
            this.ms_fieldHeight = $r18;
            this.m_view = $r2;
            Log.i(TAG, "Created SurfaceViewHandler from view " + $r2.toString());
        }

        public synchronized void attach() throws  {
            if (!(this.ms_fieldCallbacks == null || this.ms_fieldSurfaceHolder == null || this.ms_fieldFormat == null)) {
                try {
                    this.m_origCallbacks = (ArrayList) this.ms_fieldCallbacks.get(this.m_view);
                    this.m_origHolder = (SurfaceHolder) this.ms_fieldSurfaceHolder.get(this.m_view);
                    this.m_origFormat = this.ms_fieldFormat.getInt(this.m_view);
                    this.m_origWidth = this.ms_fieldWidth.getInt(this.m_view);
                    this.m_origHeight = this.ms_fieldHeight.getInt(this.m_view);
                    if (this.m_origWidth <= 0) {
                        Log.d(TAG, "attach() original width <= 0, delaying");
                        WLMirrorMode.this.m_uiHandler.postDelayed(new C03531(), 50);
                    } else {
                        Log.d(TAG, "attach()");
                        ArrayList $r1 = new ArrayList();
                        Callback $r10 = this.m_customCallback;
                        $r1.add($r10);
                        this.ms_fieldCallbacks.set(this.m_view, $r1);
                        this.ms_fieldSurfaceHolder.set(this.m_view, this.m_customHolder);
                        if (this.m_origCallbacks.size() == 0) {
                            Log.e(TAG, "There are no callbacks attached to the surface holder. Nobody will be notified for changes!!! View: " + this.m_view.toString());
                        }
                        WLMirrorMode.this.m_uiHandler.post(new C03542());
                        this.m_attached = true;
                    }
                } catch (Throwable $r2) {
                    Log.e(TAG, "attach: ", $r2);
                    this.m_origCallbacks = null;
                    this.m_origHolder = null;
                    this.m_origFormat = -1;
                }
            }
        }

        public synchronized void detach() throws  {
            destroy();
        }

        public synchronized void draw(Canvas $r1, float scaleX, float scaleY, int offsetX, int offsetY, float viewHierarchyScaleX, float viewHierarchyScaleY) throws  {
            try {
                this.m_view.getLocationOnScreen(this.m_position);
                offsetX = this.m_position[0];
                offsetY = this.m_position[1];
                if (this.m_surface != null) {
                    $r1.drawBitmap(this.m_surface.getBitmap(), (float) offsetX, (float) offsetY, null);
                }
            } catch (Exception $r2) {
                MCSLogger.log(TAG, "Surface View drawing error", $r2);
            }
        }
    }

    private static class TextureViewHandler implements SurfaceTextureListener, IViewHandler {
        public static boolean ENABLE_DOUBLE_BUFFER_BITMAP = true;
        public static boolean ENABLE_STATIC_BITMAP = false;
        private static Bitmap s_largestViewBitmap = null;
        private static final Object s_largestViewBitmapLock = new Object();
        private static boolean s_largetViewBitmapInUse = false;
        private Bitmap m_bufferBitmap;
        private Bitmap m_emptyBitmap;
        private boolean m_frameAvailable;
        private final int[] m_position = new int[2];
        private SurfaceTextureListener m_prevListener;
        private TextureView m_view;
        private Bitmap m_viewBitmap;

        @android.annotation.SuppressLint({"NewApi"})
        public synchronized void draw(android.graphics.Canvas r28, float r29, float r30, int r31, int r32, float r33, float r34) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x02b7 in list []
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
            r27 = this;
            monitor-enter(r27);
            r0 = r27;
            r3 = r0.m_view;
            r4 = r3.getWidth();
            r0 = r27;
            r3 = r0.m_view;
            r5 = r3.getHeight();
            r6 = (float) r4;
            r0 = r29;
            r6 = r6 / r0;
            r33 = r6 * r33;
            r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
            r0 = r33;
            r0 = r0 + r7;
            r33 = r0;
            r4 = (int) r0;
            r0 = (float) r5;
            r33 = r0;
            r1 = r30;
            r0 = r0 / r1;
            r33 = r0;
            r1 = r34;
            r0 = r0 * r1;
            r33 = r0;
            r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
            r0 = r33;
            r0 = r0 + r7;
            r33 = r0;
            r5 = (int) r0;
            r0 = r27;
            r8 = r0.m_viewBitmap;
            if (r8 == 0) goto L_0x0087;
        L_0x003c:
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r9 = r8.getWidth();
            if (r9 != r4) goto L_0x0050;
        L_0x0046:
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r9 = r8.getHeight();
            if (r9 == r5) goto L_0x0087;
        L_0x0050:
            r10 = ENABLE_STATIC_BITMAP;
            if (r10 == 0) goto L_0x015c;
        L_0x0054:
            r11 = s_largestViewBitmapLock;
            monitor-enter(r11);
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r12 = s_largestViewBitmap;
            if (r8 != r12) goto L_0x014f;
        L_0x005f:
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r9 = r8.getWidth();
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r13 = r8.getHeight();
            r9 = r9 * r13;
            r13 = r4 * r5;
            if (r9 >= r13) goto L_0x0145;
        L_0x0074:
            r14 = 0;
            s_largetViewBitmapInUse = r14;
            r15 = 0;
            s_largestViewBitmap = r15;
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r8.recycle();
            r15 = 0;
            r0 = r27;
            r0.m_viewBitmap = r15;
        L_0x0086:
            monitor-exit(r11);
        L_0x0087:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r10 = r0.m_frameAvailable;	 Catch:{ Exception -> 0x01c2 }
            if (r10 != 0) goto L_0x0093;	 Catch:{ Exception -> 0x01c2 }
        L_0x008d:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 != 0) goto L_0x00bf;	 Catch:{ Exception -> 0x01c2 }
        L_0x0093:
            r10 = ENABLE_STATIC_BITMAP;
            if (r10 == 0) goto L_0x0267;
        L_0x0097:
            r11 = s_largestViewBitmapLock;	 Catch:{  }
            monitor-enter(r11);	 Catch:{  }
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 != 0) goto L_0x01a2;	 Catch:{ Exception -> 0x01c2 }
        L_0x009e:
            r14 = 1;	 Catch:{ Exception -> 0x01c2 }
            s_largetViewBitmapInUse = r14;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 == 0) goto L_0x0199;	 Catch:{ Exception -> 0x01c2 }
        L_0x00a7:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r8);	 Catch:{ Exception -> 0x01c2 }
        L_0x00b3:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_viewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            s_largestViewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
        L_0x00b9:
            monitor-exit(r11);	 Catch:{ Exception -> 0x01c2 }
            r14 = 0;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_frameAvailable = r14;	 Catch:{ Exception -> 0x01c2 }
        L_0x00bf:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 == 0) goto L_0x0143;	 Catch:{ Exception -> 0x01c2 }
        L_0x00c5:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0 = r0.m_position;	 Catch:{ Exception -> 0x01c2 }
            r16 = r0;	 Catch:{ Exception -> 0x01c2 }
            r3.getLocationOnScreen(r0);	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0 = r0.m_position;	 Catch:{ Exception -> 0x01c2 }
            r16 = r0;	 Catch:{ Exception -> 0x01c2 }
            r14 = 0;	 Catch:{ Exception -> 0x01c2 }
            r4 = r16[r14];	 Catch:{ Exception -> 0x01c2 }
            r31 = r4 + r31;
            r0 = r31;
            r0 = (float) r0;
            r33 = r0;
            goto L_0x00e6;
        L_0x00e3:
            goto L_0x0086;
        L_0x00e6:
            r29 = r33 / r29;
            r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
            r0 = r29;
            r0 = r0 + r7;
            r29 = r0;
            goto L_0x00f4;
        L_0x00f1:
            goto L_0x0086;
        L_0x00f4:
            r0 = (int) r0;	 Catch:{ Exception -> 0x01c2 }
            r31 = r0;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0 = r0.m_position;	 Catch:{ Exception -> 0x01c2 }
            r16 = r0;	 Catch:{ Exception -> 0x01c2 }
            r14 = 1;	 Catch:{ Exception -> 0x01c2 }
            r4 = r16[r14];	 Catch:{ Exception -> 0x01c2 }
            r32 = r4 + r32;
            r0 = r32;
            r0 = (float) r0;
            r29 = r0;
            r1 = r30;
            r0 = r0 / r1;
            r29 = r0;
            r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
            r0 = r29;
            r0 = r0 + r7;
            r29 = r0;
            r0 = (int) r0;	 Catch:{ Exception -> 0x01c2 }
            r32 = r0;	 Catch:{ Exception -> 0x01c2 }
            r0 = r28;	 Catch:{ Exception -> 0x01c2 }
            r0.save();	 Catch:{ Exception -> 0x01c2 }
            r15 = 0;	 Catch:{ Exception -> 0x01c2 }
            r0 = r28;	 Catch:{ Exception -> 0x01c2 }
            r0.setMatrix(r15);	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x012a;	 Catch:{ Exception -> 0x01c2 }
        L_0x0127:
            goto L_0x00b3;	 Catch:{ Exception -> 0x01c2 }
        L_0x012a:
            r0 = r31;
            r0 = (float) r0;
            r29 = r0;
            r0 = r32;	 Catch:{ Exception -> 0x01c2 }
            r0 = (float) r0;	 Catch:{ Exception -> 0x01c2 }
            r30 = r0;	 Catch:{ Exception -> 0x01c2 }
            r15 = 0;	 Catch:{ Exception -> 0x01c2 }
            r0 = r28;	 Catch:{ Exception -> 0x01c2 }
            r1 = r29;	 Catch:{ Exception -> 0x01c2 }
            r2 = r30;	 Catch:{ Exception -> 0x01c2 }
            r0.drawBitmap(r8, r1, r2, r15);	 Catch:{ Exception -> 0x01c2 }
            r0 = r28;	 Catch:{ Exception -> 0x01c2 }
            r0.restore();	 Catch:{ Exception -> 0x01c2 }
        L_0x0143:
            monitor-exit(r27);
            return;
        L_0x0145:
            r14 = 0;
            s_largetViewBitmapInUse = r14;
            goto L_0x00e3;
        L_0x0149:
            r17 = move-exception;
            monitor-exit(r11);
            throw r17;
        L_0x014c:
            r18 = move-exception;
            monitor-exit(r27);
            throw r18;
        L_0x014f:
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r8.recycle();
            r15 = 0;
            r0 = r27;
            r0.m_viewBitmap = r15;
            goto L_0x00f1;
        L_0x015c:
            r0 = r27;
            r8 = r0.m_viewBitmap;
            r8.recycle();
            r15 = 0;
            r0 = r27;
            r0.m_viewBitmap = r15;
            r10 = ENABLE_DOUBLE_BUFFER_BITMAP;
            if (r10 == 0) goto L_0x0087;
        L_0x016c:
            r0 = r27;
            r8 = r0.m_bufferBitmap;
            if (r8 == 0) goto L_0x017e;
        L_0x0172:
            r0 = r27;
            r8 = r0.m_bufferBitmap;
            r8.recycle();
            r15 = 0;
            r0 = r27;
            r0.m_bufferBitmap = r15;
        L_0x017e:
            r0 = r27;
            r8 = r0.m_emptyBitmap;
            if (r8 == 0) goto L_0x0087;
        L_0x0184:
            goto L_0x0188;
        L_0x0185:
            goto L_0x0143;
        L_0x0188:
            r0 = r27;
            r8 = r0.m_emptyBitmap;
            r8.recycle();
            goto L_0x0193;
        L_0x0190:
            goto L_0x0087;
        L_0x0193:
            r15 = 0;
            r0 = r27;
            r0.m_emptyBitmap = r15;
            goto L_0x0190;
        L_0x0199:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r4, r5);	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0127;	 Catch:{ Exception -> 0x01c2 }
        L_0x01a2:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r12 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 != r12) goto L_0x01d1;	 Catch:{ Exception -> 0x01c2 }
        L_0x01aa:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r8);	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_viewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x01bc;	 Catch:{ Exception -> 0x01c2 }
        L_0x01b9:
            goto L_0x00b9;	 Catch:{ Exception -> 0x01c2 }
        L_0x01bc:
            s_largestViewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x01b9;	 Catch:{ Exception -> 0x01c2 }
        L_0x01bf:
            r19 = move-exception;	 Catch:{ Exception -> 0x01c2 }
            monitor-exit(r11);	 Catch:{ Exception -> 0x01c2 }
            throw r19;	 Catch:{ Exception -> 0x01c2 }
        L_0x01c2:
            r20 = move-exception;
            r21 = "WLMirrorMode";
            r22 = "Surface View drawing error";
            r0 = r21;
            r1 = r22;
            r2 = r20;
            android.util.Log.e(r0, r1, r2);
            goto L_0x0185;
        L_0x01d1:
            r10 = s_largetViewBitmapInUse;	 Catch:{ Exception -> 0x01c2 }
            if (r10 != 0) goto L_0x01fd;	 Catch:{ Exception -> 0x01c2 }
        L_0x01d5:
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r9 = r8.getWidth();	 Catch:{ Exception -> 0x01c2 }
            if (r9 != r4) goto L_0x01fd;	 Catch:{ Exception -> 0x01c2 }
        L_0x01dd:
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r9 = r8.getHeight();	 Catch:{ Exception -> 0x01c2 }
            if (r9 != r5) goto L_0x01fd;	 Catch:{ Exception -> 0x01c2 }
        L_0x01e5:
            r14 = 1;	 Catch:{ Exception -> 0x01c2 }
            s_largetViewBitmapInUse = r14;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r8);	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_viewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x01fa;	 Catch:{ Exception -> 0x01c2 }
        L_0x01f7:
            goto L_0x00b9;	 Catch:{ Exception -> 0x01c2 }
        L_0x01fa:
            s_largestViewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x01f7;	 Catch:{ Exception -> 0x01c2 }
        L_0x01fd:
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r9 = r8.getWidth();	 Catch:{ Exception -> 0x01c2 }
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r13 = r8.getHeight();	 Catch:{ Exception -> 0x01c2 }
            r9 = r9 * r13;	 Catch:{ Exception -> 0x01c2 }
            r13 = r4 * r5;	 Catch:{ Exception -> 0x01c2 }
            if (r9 >= r13) goto L_0x0243;	 Catch:{ Exception -> 0x01c2 }
        L_0x020e:
            r10 = s_largetViewBitmapInUse;	 Catch:{ Exception -> 0x01c2 }
            if (r10 != 0) goto L_0x021a;	 Catch:{ Exception -> 0x01c2 }
        L_0x0212:
            r8 = s_largestViewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8.recycle();	 Catch:{ Exception -> 0x01c2 }
            r15 = 0;	 Catch:{ Exception -> 0x01c2 }
            s_largestViewBitmap = r15;	 Catch:{ Exception -> 0x01c2 }
        L_0x021a:
            r14 = 1;	 Catch:{ Exception -> 0x01c2 }
            s_largetViewBitmapInUse = r14;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 == 0) goto L_0x023a;	 Catch:{ Exception -> 0x01c2 }
        L_0x0223:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r8);	 Catch:{ Exception -> 0x01c2 }
        L_0x022f:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_viewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0237;	 Catch:{ Exception -> 0x01c2 }
        L_0x0234:
            goto L_0x00b9;	 Catch:{ Exception -> 0x01c2 }
        L_0x0237:
            s_largestViewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0234;	 Catch:{ Exception -> 0x01c2 }
        L_0x023a:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r4, r5);	 Catch:{ Exception -> 0x01c2 }
            goto L_0x022f;	 Catch:{ Exception -> 0x01c2 }
        L_0x0243:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 == 0) goto L_0x025e;	 Catch:{ Exception -> 0x01c2 }
        L_0x0249:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r8);	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0259;	 Catch:{ Exception -> 0x01c2 }
        L_0x0256:
            goto L_0x00b9;	 Catch:{ Exception -> 0x01c2 }
        L_0x0259:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_viewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0256;	 Catch:{ Exception -> 0x01c2 }
        L_0x025e:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r4, r5);	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0259;
        L_0x0267:
            r10 = ENABLE_DOUBLE_BUFFER_BITMAP;
            if (r10 == 0) goto L_0x02f6;
        L_0x026b:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_bufferBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 == 0) goto L_0x02d1;	 Catch:{ Exception -> 0x01c2 }
        L_0x0271:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_bufferBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r8);	 Catch:{ Exception -> 0x01c2 }
        L_0x027d:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r12 = r0.m_emptyBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r12 != 0) goto L_0x02b7;
        L_0x0283:
            if (r8 == 0) goto L_0x02b7;
        L_0x0285:
            r9 = android.os.Build.VERSION.SDK_INT;
            r14 = 17;	 Catch:{ Exception -> 0x01c2 }
            if (r9 < r14) goto L_0x02da;	 Catch:{ Exception -> 0x01c2 }
        L_0x028b:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r23 = r3.getContext();	 Catch:{ Exception -> 0x01c2 }
            r0 = r23;	 Catch:{ Exception -> 0x01c2 }
            r24 = r0.getResources();	 Catch:{ Exception -> 0x01c2 }
            r0 = r24;	 Catch:{ Exception -> 0x01c2 }
            r25 = r0.getDisplayMetrics();	 Catch:{ Exception -> 0x01c2 }
            r26 = r8.getConfig();	 Catch:{ Exception -> 0x01c2 }
            r0 = r25;	 Catch:{ Exception -> 0x01c2 }
            r1 = r26;	 Catch:{ Exception -> 0x01c2 }
            r12 = android.graphics.Bitmap.createBitmap(r0, r4, r5, r1);	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_emptyBitmap = r12;	 Catch:{ Exception -> 0x01c2 }
        L_0x02af:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r12 = r0.m_emptyBitmap;	 Catch:{ Exception -> 0x01c2 }
            r14 = -1;	 Catch:{ Exception -> 0x01c2 }
            r12.eraseColor(r14);	 Catch:{ Exception -> 0x01c2 }
        L_0x02b7:
            if (r8 == 0) goto L_0x02c3;	 Catch:{ Exception -> 0x01c2 }
        L_0x02b9:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r12 = r0.m_emptyBitmap;	 Catch:{ Exception -> 0x01c2 }
            r10 = r8.sameAs(r12);	 Catch:{ Exception -> 0x01c2 }
            if (r10 == 0) goto L_0x02e9;	 Catch:{ Exception -> 0x01c2 }
        L_0x02c3:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_bufferBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x02cb;	 Catch:{ Exception -> 0x01c2 }
        L_0x02c8:
            goto L_0x00bf;	 Catch:{ Exception -> 0x01c2 }
        L_0x02cb:
            r14 = 0;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_frameAvailable = r14;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x02c8;	 Catch:{ Exception -> 0x01c2 }
        L_0x02d1:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r4, r5);	 Catch:{ Exception -> 0x01c2 }
            goto L_0x027d;	 Catch:{ Exception -> 0x01c2 }
        L_0x02da:
            r26 = r8.getConfig();	 Catch:{ Exception -> 0x01c2 }
            r0 = r26;	 Catch:{ Exception -> 0x01c2 }
            r12 = android.graphics.Bitmap.createBitmap(r4, r5, r0);	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_emptyBitmap = r12;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x02af;	 Catch:{ Exception -> 0x01c2 }
        L_0x02e9:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r12 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_bufferBitmap = r12;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_viewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x02cb;	 Catch:{ Exception -> 0x01c2 }
        L_0x02f6:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            if (r8 == 0) goto L_0x0316;	 Catch:{ Exception -> 0x01c2 }
        L_0x02fc:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r8 = r0.m_viewBitmap;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r8);	 Catch:{ Exception -> 0x01c2 }
        L_0x0308:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_viewBitmap = r8;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0310;	 Catch:{ Exception -> 0x01c2 }
        L_0x030d:
            goto L_0x00bf;	 Catch:{ Exception -> 0x01c2 }
        L_0x0310:
            r14 = 0;	 Catch:{ Exception -> 0x01c2 }
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r0.m_frameAvailable = r14;	 Catch:{ Exception -> 0x01c2 }
            goto L_0x030d;	 Catch:{ Exception -> 0x01c2 }
        L_0x0316:
            r0 = r27;	 Catch:{ Exception -> 0x01c2 }
            r3 = r0.m_view;	 Catch:{ Exception -> 0x01c2 }
            r8 = r3.getBitmap(r4, r5);	 Catch:{ Exception -> 0x01c2 }
            goto L_0x0308;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLMirrorMode.TextureViewHandler.draw(android.graphics.Canvas, float, float, int, int, float, float):void");
        }

        public TextureViewHandler(TextureView $r1) throws  {
            this.m_view = $r1;
            this.m_prevListener = this.m_view.getSurfaceTextureListener();
            this.m_view.setSurfaceTextureListener(this);
        }

        public void attach() throws  {
        }

        public void detach() throws  {
            destroy();
        }

        public synchronized void destroy() throws  {
            if (this.m_view != null) {
                if (this.m_prevListener != null) {
                    this.m_view.setSurfaceTextureListener(this.m_prevListener);
                    this.m_prevListener = null;
                }
                if (ENABLE_STATIC_BITMAP) {
                    synchronized (s_largestViewBitmapLock) {
                        if (this.m_viewBitmap != null) {
                            if (this.m_viewBitmap == s_largestViewBitmap) {
                                s_largetViewBitmapInUse = false;
                            } else {
                                this.m_viewBitmap.recycle();
                            }
                            this.m_viewBitmap = null;
                        }
                    }
                } else if (this.m_viewBitmap != null) {
                    this.m_viewBitmap.recycle();
                    this.m_viewBitmap = null;
                }
                this.m_view = null;
            }
        }

        public void onSurfaceTextureAvailable(SurfaceTexture $r1, int $i0, int $i1) throws  {
            this.m_frameAvailable = true;
            if (this.m_prevListener != null) {
                this.m_prevListener.onSurfaceTextureAvailable($r1, $i0, $i1);
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture $r1, int $i0, int $i1) throws  {
            this.m_frameAvailable = true;
            if (this.m_prevListener != null) {
                this.m_prevListener.onSurfaceTextureSizeChanged($r1, $i0, $i1);
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture $r1) throws  {
            Log.d(WLMirrorMode.TAG, "onSurfaceTextureDestroyed: " + this.m_view);
            if (this.m_prevListener != null) {
                return this.m_prevListener.onSurfaceTextureDestroyed($r1);
            }
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture $r1) throws  {
            this.m_frameAvailable = true;
            if (this.m_prevListener != null) {
                this.m_prevListener.onSurfaceTextureUpdated($r1);
            }
        }
    }

    private void drawView(android.graphics.Canvas r43, android.view.View r44, boolean r45) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0053 in list []
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
        r42 = this;
        if (r44 == 0) goto L_0x0267;
    L_0x0002:
        r0 = r44;
        r5 = r0.getWindowVisibility();
        r0 = r44;
        r6 = r0.isShown();
        if (r6 == 0) goto L_0x0232;
    L_0x0010:
        r7 = 8;
        if (r5 == r7) goto L_0x0232;
    L_0x0014:
        if (r45 == 0) goto L_0x001f;
    L_0x0016:
        r0 = r42;
        r1 = r44;
        r2 = r43;
        r0.checkDimViews(r1, r2);
    L_0x001f:
        r5 = 0;
        r8 = ms_fieldPrivateFlags;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        if (r8 == 0) goto L_0x0053;
    L_0x0024:
        r8 = ms_fieldPrivateFlags;
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r9 = r8.get(r0);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r11 = r9;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r11 = (java.lang.Integer) r11;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r10 = r11;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r12 = r10.intValue();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r5 = r12;
        r7 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r13 = r7 & r12;
        if (r13 == 0) goto L_0x0053;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x003c:
        r14 = "Clear DIRTY_OPAQUE_FLAG";	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.logExtendedHierarchy(r14);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r8 = ms_fieldPrivateFlags;
        r7 = -6291457; // 0xffffffffff9fffff float:NaN double:NaN;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r12 = r7 & r12;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r10 = java.lang.Integer.valueOf(r12);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r8.set(r0, r10);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x0053:
        r0 = r43;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.save();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r15 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r15.<init>();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r14 = "Drawing view ";	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r15 = r15.append(r14);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r16 = r0.toString();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r16;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r15 = r15.append(r0);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r16 = r15.toString();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r16;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.logExtendedHierarchy(r1);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_position;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r17 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r17;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.getLocationOnScreen(r1);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_position;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r17 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r7 = 0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r12 = r17[r7];	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_position;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r17 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r7 = 1;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r13 = r17[r7];	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        if (r45 == 0) goto L_0x00bd;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x009b:
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_layoutParamViews;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r18 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r6 = r0.contains(r1);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        if (r6 == 0) goto L_0x00bd;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x00a9:
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r19 = r0.getLayoutParams();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r21 = r19;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r21 = (android.view.WindowManager.LayoutParams) r21;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r20 = r21;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r20;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r12 = r0.x;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r20;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r13 = r0.y;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x00bd:
        r0 = (float) r12;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r22 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = (float) r13;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r23 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r43;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r22;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r2 = r23;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.translate(r1, r2);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        if (r45 == 0) goto L_0x010d;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x00ce:
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r22 = r0.getScaleX();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r25 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r24 = (r22 > r25 ? 1 : (r22 == r25 ? 0 : -1));
        if (r24 != 0) goto L_0x00e8;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x00db:
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r22 = r0.getScaleY();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r25 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r24 = (r22 > r25 ? 1 : (r22 == r25 ? 0 : -1));
        if (r24 == 0) goto L_0x010d;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x00e8:
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r22 = r0.getScaleX();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r23 = r0.getScaleY();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r26 = r0.getPivotX();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r27 = r0.getPivotY();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r43;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r22;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r2 = r23;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r3 = r26;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r4 = r27;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.scale(r1, r2, r3, r4);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x010d:
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r28 = r0.getAnimation();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        if (r28 == 0) goto L_0x0150;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x0115:
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_transformation;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r29 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.clear();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r30 = r0.getDrawingTime();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_transformation;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r29 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r28;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r30;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r3 = r29;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.getTransformation(r1, r3);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_transformation;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r29 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r12 = r0.getTransformationType();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        if (r12 == 0) goto L_0x0150;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x013f:
        r0 = r42;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0.m_transformation;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r29 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r32 = r0.getMatrix();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r43;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r32;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.concat(r1);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x0150:
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r0 instanceof android.widget.ScrollView;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r45 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        if (r45 == 0) goto L_0x017b;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x0158:
        r34 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r34 = (android.widget.ScrollView) r34;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r33 = r34;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r33;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r13 = r0.getScrollX();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r33;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r12 = r0.getScrollY();	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r13 = -r13;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = (float) r13;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r22 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r12 = -r12;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = (float) r12;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r23 = r0;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r43;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r22;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r2 = r23;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.translate(r1, r2);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
    L_0x017b:
        r0 = r44;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r43;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0.draw(r1);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r43;
        r0.restore();
        r14 = "All child views drawn.";
        r0 = r42;
        r0.logExtendedHierarchy(r14);
        r8 = ms_fieldPrivateFlags;
        if (r8 == 0) goto L_0x0268;
    L_0x0192:
        r7 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r12 = r7 & r5;
        if (r12 == 0) goto L_0x0269;
    L_0x0199:
        r14 = "Restore DIRTY_OPAQUE_FLAG";	 Catch:{ Exception -> 0x0218 }
        r0 = r42;	 Catch:{ Exception -> 0x0218 }
        r0.logExtendedHierarchy(r14);	 Catch:{ Exception -> 0x0218 }
        r8 = ms_fieldPrivateFlags;	 Catch:{ Exception -> 0x0218 }
        r0 = r44;	 Catch:{ Exception -> 0x0218 }
        r9 = r8.get(r0);	 Catch:{ Exception -> 0x0218 }
        r35 = r9;	 Catch:{ Exception -> 0x0218 }
        r35 = (java.lang.Integer) r35;	 Catch:{ Exception -> 0x0218 }
        r10 = r35;	 Catch:{ Exception -> 0x0218 }
        r12 = r10.intValue();	 Catch:{ Exception -> 0x0218 }
        r8 = ms_fieldPrivateFlags;
        r7 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r5 = r7 & r5;
        r5 = r5 | r12;	 Catch:{ Exception -> 0x0218 }
        r10 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x0218 }
        r0 = r44;	 Catch:{ Exception -> 0x0218 }
        r8.set(r0, r10);	 Catch:{ Exception -> 0x0218 }
        return;
    L_0x01c4:
        r36 = move-exception;
        goto L_0x01c9;
    L_0x01c6:
        goto L_0x0053;
    L_0x01c9:
        r14 = "WLMirrorMode";	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r37 = "drawView save flags failed";	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r0 = r37;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        r1 = r36;	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r0, r1);	 Catch:{ Exception -> 0x01c4, Throwable -> 0x01d5 }
        goto L_0x01c6;
    L_0x01d5:
        r38 = move-exception;
        r0 = r43;
        r0.restore();
        r14 = "All child views drawn.";
        r0 = r42;
        r0.logExtendedHierarchy(r14);
        r8 = ms_fieldPrivateFlags;
        if (r8 == 0) goto L_0x0217;
    L_0x01e6:
        r7 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r12 = r7 & r5;
        if (r12 == 0) goto L_0x0217;
    L_0x01ed:
        r14 = "Restore DIRTY_OPAQUE_FLAG";	 Catch:{ Exception -> 0x0225 }
        r0 = r42;	 Catch:{ Exception -> 0x0225 }
        r0.logExtendedHierarchy(r14);	 Catch:{ Exception -> 0x0225 }
        r8 = ms_fieldPrivateFlags;	 Catch:{ Exception -> 0x0225 }
        r0 = r44;	 Catch:{ Exception -> 0x0225 }
        r9 = r8.get(r0);	 Catch:{ Exception -> 0x0225 }
        r39 = r9;	 Catch:{ Exception -> 0x0225 }
        r39 = (java.lang.Integer) r39;	 Catch:{ Exception -> 0x0225 }
        r10 = r39;	 Catch:{ Exception -> 0x0225 }
        r12 = r10.intValue();	 Catch:{ Exception -> 0x0225 }
        r8 = ms_fieldPrivateFlags;
        r7 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r5 = r7 & r5;
        r5 = r5 | r12;	 Catch:{ Exception -> 0x0225 }
        r10 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x0225 }
        r0 = r44;	 Catch:{ Exception -> 0x0225 }
        r8.set(r0, r10);	 Catch:{ Exception -> 0x0225 }
    L_0x0217:
        throw r38;
    L_0x0218:
        r40 = move-exception;
        r14 = "WLMirrorMode";
        r37 = "drawView restore flags failed";
        r0 = r37;
        r1 = r40;
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r0, r1);
        return;
    L_0x0225:
        r41 = move-exception;
        r14 = "WLMirrorMode";
        r37 = "drawView restore flags failed";
        r0 = r37;
        r1 = r41;
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r14, r0, r1);
        goto L_0x0217;
    L_0x0232:
        r0 = r42;
        r0 = r0.m_extendedLoggingEnabled;
        r45 = r0;
        if (r45 == 0) goto L_0x026a;
    L_0x023a:
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        r14 = "Skipping view due to WindowVisibility=";
        r15 = r15.append(r14);
        r15 = r15.append(r5);
        r14 = ": ";
        r15 = r15.append(r14);
        r0 = r44;
        r16 = r0.toString();
        r0 = r16;
        r15 = r15.append(r0);
        r16 = r15.toString();
        r0 = r42;
        r1 = r16;
        r0.logExtendedHierarchy(r1);
        return;
    L_0x0267:
        return;
    L_0x0268:
        return;
    L_0x0269:
        return;
    L_0x026a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLMirrorMode.drawView(android.graphics.Canvas, android.view.View, boolean):void");
    }

    private void drawViewHierarchy(android.graphics.Canvas r36) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:28:0x017a in {5, 8, 14, 19, 25, 27, 29, 32, 37, 38, 39} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
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
        r35 = this;
        r4 = android.os.SystemClock.currentThreadTimeMillis();
        r0 = r35;
        r6 = r0.getTheRootViews();
        r7 = -1;
        r0 = r36;
        r0.drawColor(r7);
        r8 = r6.isEmpty();
        if (r8 != 0) goto L_0x01c3;
    L_0x0016:
        r0 = r35;
        r9 = r0.m_display;
        r10 = r9.getWidth();
        r0 = r35;
        r9 = r0.m_display;
        r11 = r9.getHeight();
        r12 = com.abaltatech.mcp.weblink.sdk.WEBLINK.instance;
        r13 = r12.getUiMode();
        r7 = 2;
        if (r13 != r7) goto L_0x0035;
    L_0x002f:
        r14 = android.os.Build.VERSION.SDK_INT;
        r7 = 19;
        if (r14 < r7) goto L_0x0038;
    L_0x0035:
        r7 = 3;
        if (r13 != r7) goto L_0x017e;
    L_0x0038:
        r8 = 1;
    L_0x0039:
        if (r8 == 0) goto L_0x0197;
    L_0x003b:
        r0 = r35;
        r8 = r0.hasOffScreenViews();
        if (r8 == 0) goto L_0x0197;
    L_0x0043:
        r7 = -4000; // 0xfffffffffffff060 float:NaN double:NaN;
        r0 = r35;
        r0.m_translateX = r7;
        r7 = -4000; // 0xfffffffffffff060 float:NaN double:NaN;
        r0 = r35;
        r0.m_translateY = r7;
        r7 = 3;
        if (r13 != r7) goto L_0x0180;
    L_0x0052:
        r0 = r35;
        r15 = r0.m_offsetScreenScaleX;
        r0 = r35;
        r0.m_scaleX = r15;
        r0 = r35;
        r15 = r0.m_offsetScreenScaleY;
        r0 = r35;
        r0.m_scaleY = r15;
    L_0x0062:
        r12 = com.abaltatech.mcp.weblink.sdk.WEBLINK.instance;
        r16 = r12.getContext();
        r0 = r16;
        r17 = r0.getResources();
        r18 = "status_bar_height";
        r19 = "dimen";
        r20 = "android";
        r0 = r17;
        r1 = r18;
        r2 = r19;
        r3 = r20;
        r11 = r0.getIdentifier(r1, r2, r3);
        if (r11 <= 0) goto L_0x00c1;
    L_0x0083:
        r8 = m_isFullScreenMode;
        if (r8 != 0) goto L_0x00c1;
    L_0x0087:
        r0 = r17;
        r10 = r0.getDimensionPixelSize(r11);
        r0 = r35;
        r11 = r0.m_translateY;
        r15 = (float) r10;
        r0 = r35;
        r0 = r0.m_originalMetrics;
        r21 = r0;
        r10 = r0.densityDpi;
        r0 = (float) r10;
        r22 = r0;
        r0 = r17;
        r21 = r0.getDisplayMetrics();
        r0 = r21;
        r10 = r0.densityDpi;
        r0 = (float) r10;
        r23 = r0;
        r0 = r22;
        r1 = r23;
        r0 = r0 / r1;
        r22 = r0;
        r15 = r15 * r0;
        r24 = 1036831949; // 0x3dcccccd float:0.1 double:5.122630465E-315;
        r0 = r24;
        r15 = r15 + r0;
        r10 = java.lang.Math.round(r15);
        r11 = r11 - r10;
        r0 = r35;
        r0.m_translateY = r11;
    L_0x00c1:
        r0 = r36;	 Catch:{ Throwable -> 0x01bc }
        r0.save();	 Catch:{ Throwable -> 0x01bc }
        r0 = r35;	 Catch:{ Throwable -> 0x01bc }
        r15 = r0.m_scaleX;	 Catch:{ Throwable -> 0x01bc }
        r24 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;	 Catch:{ Throwable -> 0x01bc }
        r15 = r24 / r15;	 Catch:{ Throwable -> 0x01bc }
        r0 = r35;	 Catch:{ Throwable -> 0x01bc }
        r0 = r0.m_scaleY;	 Catch:{ Throwable -> 0x01bc }
        r22 = r0;	 Catch:{ Throwable -> 0x01bc }
        r24 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;	 Catch:{ Throwable -> 0x01bc }
        r22 = r24 / r22;	 Catch:{ Throwable -> 0x01bc }
        r0 = r36;	 Catch:{ Throwable -> 0x01bc }
        r1 = r22;	 Catch:{ Throwable -> 0x01bc }
        r0.scale(r15, r1);	 Catch:{ Throwable -> 0x01bc }
        r0 = r35;	 Catch:{ Throwable -> 0x01bc }
        r11 = r0.m_translateX;	 Catch:{ Throwable -> 0x01bc }
        r15 = (float) r11;	 Catch:{ Throwable -> 0x01bc }
        r0 = r35;	 Catch:{ Throwable -> 0x01bc }
        r11 = r0.m_translateY;	 Catch:{ Throwable -> 0x01bc }
        r0 = (float) r11;	 Catch:{ Throwable -> 0x01bc }
        r22 = r0;	 Catch:{ Throwable -> 0x01bc }
        r0 = r36;	 Catch:{ Throwable -> 0x01bc }
        r1 = r22;	 Catch:{ Throwable -> 0x01bc }
        r0.translate(r15, r1);	 Catch:{ Throwable -> 0x01bc }
        r0 = r35;	 Catch:{ Throwable -> 0x01bc }
        r1 = r36;	 Catch:{ Throwable -> 0x01bc }
        r0.drawViewHierarchy(r1, r6);	 Catch:{ Throwable -> 0x01bc }
        r0 = r36;
        r0.restore();
        r0 = r35;
        r8 = r0.m_debugOverlayEnabled;
        if (r8 == 0) goto L_0x01c4;
    L_0x0106:
        r25 = android.os.SystemClock.currentThreadTimeMillis();
        r4 = r25 - r4;
        r0 = r35;
        r4 = r0.calculateAverageFrameTime(r4);
        r27 = java.util.Locale.ENGLISH;
        r7 = 1;
        r0 = new java.lang.Object[r7];
        r28 = r0;
        r29 = java.lang.Long.valueOf(r4);
        r7 = 0;
        r28[r7] = r29;
        r18 = "%03d ms";
        r0 = r27;
        r1 = r18;
        r2 = r28;
        r30 = java.lang.String.format(r0, r1, r2);
        r31 = new android.graphics.Rect;
        r0 = r31;
        r0.<init>();
        r0 = r35;
        r0 = r0.m_debugTextPaint;
        r32 = r0;
        r0 = r30;
        r11 = r0.length();
        goto L_0x0143;
    L_0x0140:
        goto L_0x00c1;
    L_0x0143:
        r7 = 0;
        r0 = r32;
        r1 = r30;
        r2 = r31;
        r0.getTextBounds(r1, r7, r11, r2);
        r0 = r36;
        r11 = r0.getWidth();
        r0 = r31;
        r10 = r0.width();
        r10 = r10 + 10;
        r11 = r11 - r10;
        r15 = (float) r11;
        r0 = r31;
        r11 = r0.height();
        r11 = r11 + 10;
        r0 = (float) r11;
        r22 = r0;
        r0 = r35;
        r0 = r0.m_debugTextPaint;
        r32 = r0;
        r0 = r36;
        r1 = r30;
        r2 = r22;
        r3 = r32;
        r0.drawText(r1, r15, r2, r3);
        return;
        goto L_0x017e;
    L_0x017b:
        goto L_0x0039;
    L_0x017e:
        r8 = 0;
        goto L_0x017b;
    L_0x0180:
        r33 = com.abaltatech.mcp.weblink.sdk.WLDisplayManager.getInstance();
        r0 = r33;
        r15 = r0.getLastVirtualDisplayScale();
        r0 = r35;
        r0.m_scaleY = r15;
        goto L_0x0192;
    L_0x018f:
        goto L_0x0062;
    L_0x0192:
        r0 = r35;
        r0.m_scaleX = r15;
        goto L_0x018f;
    L_0x0197:
        r15 = (float) r10;
        r0 = r35;
        r10 = r0.m_width;
        r0 = (float) r10;
        r22 = r0;
        r15 = r15 / r0;
        r0 = r35;
        r0.m_scaleX = r15;
        r15 = (float) r11;
        r0 = r35;
        r11 = r0.m_height;
        r0 = (float) r11;
        r22 = r0;
        r15 = r15 / r0;
        r0 = r35;
        r0.m_scaleY = r15;
        r7 = 0;
        r0 = r35;
        r0.m_translateX = r7;
        r7 = 0;
        r0 = r35;
        r0.m_translateY = r7;
        goto L_0x0140;
    L_0x01bc:
        r34 = move-exception;
        r0 = r36;
        r0.restore();
        throw r34;
    L_0x01c3:
        return;
    L_0x01c4:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLMirrorMode.drawViewHierarchy(android.graphics.Canvas):void");
    }

    @android.annotation.SuppressLint({"NewApi"})
    public void renderFrame() throws  {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
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
        r8 = this;
        r0 = r8.m_surface;
        if (r0 == 0) goto L_0x0031;
    L_0x0004:
        r1 = 0;
        r0 = r8.m_surface;	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        r3 = 0;	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        r2 = r0.lockCanvas(r3);	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        r1 = r2;	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        r8.drawViewHierarchy(r2);	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        if (r2 == 0) goto L_0x0032;
    L_0x0012:
        r0 = r8.m_surface;
        r0.unlockCanvasAndPost(r2);
        return;
    L_0x0018:
        r4 = move-exception;
        r5 = "WLMirrorMode";	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        r6 = "renderFrame exception!";	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        android.util.Log.e(r5, r6, r4);	 Catch:{ Exception -> 0x0018, Throwable -> 0x0028 }
        if (r1 == 0) goto L_0x0033;
    L_0x0022:
        r0 = r8.m_surface;
        r0.unlockCanvasAndPost(r1);
        return;
    L_0x0028:
        r7 = move-exception;
        if (r1 == 0) goto L_0x0030;
    L_0x002b:
        r0 = r8.m_surface;
        r0.unlockCanvasAndPost(r1);
    L_0x0030:
        throw r7;
    L_0x0031:
        return;
    L_0x0032:
        return;
    L_0x0033:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLMirrorMode.renderFrame():void");
    }

    static {
        Field $r4;
        Field $r5;
        try {
            Class $r2 = Class.forName("android.view.ViewGroup");
            Field $r3 = $r2.getDeclaredField("mChildren");
            $r4 = $r2.getDeclaredField("mChildrenCount");
            $r5 = $r4;
            $r3.setAccessible(true);
            $r4.setAccessible(true);
        } catch (Exception e) {
            $r5 = null;
        }
        ms_fieldChildrenCount = $r5;
        m_hierarchyIndents[0] = "";
        for (int $i1 = 1; $i1 < 100; $i1++) {
            m_hierarchyIndents[$i1] = m_hierarchyIndents[$i1 - 1] + "  ";
        }
        try {
            $r3 = Class.forName("android.view.View").getDeclaredField("mPrivateFlags");
            $r4 = $r3;
            $r3.setAccessible(true);
        } catch (Exception e2) {
            $r4 = null;
        }
        ms_fieldPrivateFlags = $r4;
    }

    private WLMirrorMode() throws  {
        WEBLINK.instance.registerForCommand(18);
        this.m_uiHandler = WEBLINK.instance.getHandler();
    }

    public void init(Context $r1, Surface $r2, int $i0, int $i1, int $i2) throws  {
        if (!this.m_isActive) {
            this.m_display = ((WindowManager) $r1.getSystemService("window")).getDefaultDisplay();
            this.m_displayID = $i2;
            this.m_surface = $r2;
            this.m_width = $i0;
            this.m_height = $i1;
            this.m_scaleX = 1.0f;
            this.m_scaleY = 1.0f;
            this.m_isActive = true;
            this.m_debugTextPaint.setARGB(255, 0, 255, 0);
            this.m_debugTextPaint.setTextSize(20.0f);
            saveOriginalResourceMetrics($r1);
            this.m_specialViews.clear();
            this.m_offscreenViews.clear();
            WLTreeViewObserver.getInstance().registerOnHierarchyChangeListener(this);
            WEBLINK.instance.registerCommandHandler(this.m_commandHandler);
        }
    }

    public void terminate() throws  {
        if (this.m_isActive) {
            this.m_isActive = false;
            restoreOriginalResourceMetrics(WEBLINK.instance.getContext());
            WLTreeViewObserver.getInstance().unregisterOnHierarchyChangeListener(this);
            for (IViewHandler $r8 : this.m_specialViews.values()) {
                if ($r8 != null) {
                    $r8.detach();
                }
            }
            this.m_specialViews.clear();
            this.m_surface = null;
            this.m_width = 0;
            this.m_height = 0;
            this.m_displayID = -1;
            WEBLINK.instance.unregisterCommandHandler(this.m_commandHandler);
        }
    }

    public boolean isActive() throws  {
        return this.m_isActive;
    }

    public void handleUiEvent(InputEvent $r1) throws  {
        if ($r1 instanceof MotionEvent) {
            processMotionEvent((MotionEvent) $r1);
        } else if ($r1 instanceof KeyEvent) {
            processKeyEvent((KeyEvent) $r1);
        }
    }

    public void onChildViewAdded(View parent, View $r2) throws  {
        IViewHandler $r4 = WEBLINK.instance.getViewHandler($r2);
        IViewHandler $r5 = $r4;
        boolean $z0 = $r2 instanceof IWLCustomDraw;
        if ($r4 == null) {
            $r5 = (IViewHandler) this.m_specialViews.get($r2);
        }
        if ($r5 == null && !$z0) {
            if ($r2 instanceof TextureView) {
                $r5 = r15;
                TextureViewHandler r15 = new TextureViewHandler((TextureView) $r2);
            } else if ($r2 instanceof GLSurfaceView) {
                $r5 = r0;
                GLSurfaceViewHandler gLSurfaceViewHandler = new GLSurfaceViewHandler((GLSurfaceView) $r2);
            } else if ($r2 instanceof SurfaceView) {
                $r5 = r0;
                SurfaceViewHandler surfaceViewHandler = new SurfaceViewHandler((SurfaceView) $r2);
            } else if ($r2 instanceof IWLNotDrawable) {
                $r5 = r0;
                NotDrawableViewHandler notDrawableViewHandler = new NotDrawableViewHandler((IWLNotDrawable) $r2);
            }
        }
        if ($r5 != null) {
            Log.d(TAG, "Child added: " + $r2.getClass().getName() + " handler: " + ($r5 != null ? $r5.getClass().getName() : "null"));
        }
        if ($r5 != null) {
            $r5.attach();
            this.m_specialViews.put($r2, $r5);
        }
    }

    public void onChildViewRemoved(View parent, View child) throws  {
    }

    public Configuration getOriginalConfiguration() throws  {
        return this.m_originalConfiguration;
    }

    void setClientDpi(int $i0, int $i1) throws  {
        this.m_clientDpiX = $i0;
        this.m_clientDpiY = $i1;
    }

    void setOffscreenModeDpiOverride(boolean $z0) throws  {
        this.m_offscreenModeDpiOverride = $z0;
    }

    void setMirrorModeDpiOverride(boolean $z0) throws  {
        this.m_mirrorModeDpiOverride = $z0;
    }

    public boolean getOffscreenModeDpiOverride() throws  {
        return this.m_offscreenModeDpiOverride;
    }

    public boolean getMirrorModeDpiOverride() throws  {
        return this.m_mirrorModeDpiOverride;
    }

    void saveOriginalResourceMetrics(Context $r1) throws  {
        if ($r1 != null) {
            Resources $r4 = $r1.getResources();
            Configuration $r2 = new Configuration($r4.getConfiguration());
            DisplayMetrics $r3 = new DisplayMetrics();
            $r3.setTo($r4.getDisplayMetrics());
            this.m_originalConfiguration = $r2;
            this.m_originalMetrics = $r3;
            return;
        }
        this.m_originalConfiguration = null;
        this.m_originalMetrics = null;
    }

    void restoreOriginalResourceMetrics(Context $r1) throws  {
        if ($r1 != null && this.m_originalConfiguration != null && this.m_originalMetrics != null) {
            $r1.getResources().updateConfiguration(this.m_originalConfiguration, this.m_originalMetrics);
        }
    }

    float calculateDiagonalDpi(int $i0, int $i1, float $f0, float $f1) throws  {
        float $f2 = ((float) $i0) / $f0;
        $f1 = ((float) $i1) / $f1;
        return ((float) Math.sqrt((double) (($i0 * $i0) + ($i1 * $i1)))) / ((float) Math.sqrt((double) (($f2 * $f2) + ($f1 * $f1))));
    }

    private boolean testDpiRange(float $f0, int $i0, int $i1, int $i2) throws  {
        return $f0 >= ((float) $i0) - (((float) ($i0 - $i1)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) && $f0 < ((float) $i0) + (((float) ($i2 - $i0)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
    }

    void initMirrorModeResourceMetrics(Context $r1) throws  {
        if (this.m_mirrorModeDpiOverride) {
            Point $r2 = new Point();
            this.m_display.getSize($r2);
            overrideOriginalResourceMetrics($r1, (float) ((int) (((float) this.m_clientDpiX) * (((float) $r2.x) / ((float) this.m_width)))), (float) ((int) (((float) this.m_clientDpiY) * (((float) $r2.y) / ((float) this.m_height)))));
        }
    }

    @SuppressLint({"NewApi"})
    public Context revertResourceMetrics(Context $r1) throws  {
        if ($r1 == null || this.m_originalConfiguration == null || VERSION.SDK_INT < 17) {
            return $r1;
        }
        ContextThemeWrapper $r4 = new ContextThemeWrapper($r1, $r1.getApplicationInfo().theme);
        $r4.applyOverrideConfiguration(this.m_originalConfiguration);
        return $r4;
    }

    @SuppressLint({"NewApi"})
    public float getOverrideScaleFactor(Context $r1) throws  {
        if ($r1 == null || VERSION.SDK_INT < 17) {
            return 1.0f;
        }
        return ((float) WEBLINK.instance.getContext().getResources().getConfiguration().densityDpi) / ((float) $r1.getResources().getConfiguration().densityDpi);
    }

    public int calculateMirrorModeDensityOverride(int $i0, int $i1) throws  {
        Point $r1 = new Point();
        this.m_display.getSize($r1);
        return roundToNearestDpiConstant((int) calculateDiagonalDpi(this.m_width, this.m_height, (float) ((int) (((float) $i0) * (((float) $r1.x) / ((float) this.m_width)))), (float) ((int) (((float) $i1) * (((float) $r1.y) / ((float) this.m_height))))));
    }

    void initOffscreenModeResourceMetrics(Context $r1) throws  {
        if (this.m_offscreenModeDpiOverride) {
            overrideOriginalResourceMetrics($r1, (float) this.m_clientDpiX, (float) this.m_clientDpiY);
        }
    }

    @SuppressLint({"InlinedApi"})
    int roundToNearestDpiConstant(int $i0) throws  {
        int[] $r1 = VERSION.SDK_INT >= 19 ? new int[7] : VERSION.SDK_INT >= 18 ? new int[6] : new int[5];
        int $i3 = 0 + 1;
        $r1[0] = 120;
        int $i2 = $i3 + 1;
        $r1[$i3] = 160;
        $i3 = $i2 + 1;
        $r1[$i2] = 240;
        $i2 = $i3 + 1;
        $r1[$i3] = 320;
        if (VERSION.SDK_INT >= 19) {
            $r1[$i2] = 400;
            $i2++;
        }
        $i3 = $i2 + 1;
        $r1[$i2] = DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO;
        if (VERSION.SDK_INT >= 18) {
            $r1[$i3] = DisplayStrings.DS_TIME_SAVEDC;
        }
        if ($i0 < $r1[0]) {
            return $r1[0];
        }
        if ($i0 >= $r1[$r1.length - 1]) {
            return $r1[$r1.length - 1];
        }
        $i2 = 0;
        while ($i2 < $r1.length) {
            if (testDpiRange((float) $i0, $r1[$i2], $i2 + -1 >= 0 ? $r1[$i2 - 1] : $r1[$i2], $i2 + 1 < $r1.length ? $r1[$i2 + 1] : $r1[$i2])) {
                return $r1[$i2];
            }
            $i2++;
        }
        return 160;
    }

    @SuppressLint({"NewApi"})
    private void overrideOriginalResourceMetrics(Context $r1, float $f0, float $f1) throws  {
        if ($r1 != null) {
            Resources $r4 = $r1.getResources();
            int $i0 = roundToNearestDpiConstant((int) calculateDiagonalDpi(this.m_width, this.m_height, $f0, $f1));
            DisplayMetrics $r3 = new DisplayMetrics();
            $r3.setToDefaults();
            $r3.densityDpi = $i0;
            $r3.density = ((float) $i0) / 160.0f;
            $r3.xdpi = $f0;
            $r3.ydpi = $f1;
            $r3.scaledDensity = $r3.density;
            Configuration $r2 = new Configuration($r4.getConfiguration());
            if (VERSION.SDK_INT >= 17) {
                $r2.densityDpi = $i0;
            }
            $r4.updateConfiguration($r2, $r3);
        }
    }

    static void setFullScreenMode(boolean $z0) throws  {
        m_isFullScreenMode = $z0;
    }

    private void processMotionEvent(MotionEvent $r1) throws  {
        ArrayList $r3 = getTheRootViews();
        if (!$r3.isEmpty()) {
            int $i2;
            int[] $r2 = new int[2];
            View $r5 = WLEventUtils.hittest($r3, this.m_layoutParamViews, ($r1.getX() * this.m_scaleX) - ((float) this.m_translateX), ($r1.getY() * this.m_scaleY) - ((float) this.m_translateY), true);
            if (this.m_layoutParamViews.contains($r5)) {
                LayoutParams $r7 = (LayoutParams) $r5.getLayoutParams();
                $r2[0] = $r7.x;
                $r2[1] = $r7.y;
                if (!($r5.getPivotX() == 0.0f || $r5.getScaleX() == 1.0f)) {
                    $i2 = (int) (CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR + ($r5.getPivotX() * (1.0f - $r5.getScaleX())));
                    $r2[0] = $r2[0] + $i2;
                }
                if (!($r5.getPivotY() == 0.0f || $r5.getScaleY() == 1.0f)) {
                    $i2 = (int) (CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR + ($r5.getPivotY() * (1.0f - $r5.getScaleY())));
                    $r2[1] = $r2[1] + $i2;
                }
            } else {
                $r5.getLocationOnScreen($r2);
            }
            float $f1 = $r5.getScaleX();
            float $f0 = $r5.getScaleY();
            int $i0 = $r2[0];
            $i2 = this.m_translateX;
            $i0 = (int) (((float) (-($i0 + $i2))) / $f1);
            $i2 = $r2[1] + this.m_translateY;
            int i = $i2;
            $i2 = -$i2;
            i = (int) (((float) $i2) / $f0);
            $r1 = WLEventUtils.createMotionEvent($r1, $i0, i, this.m_scaleX / $f1, this.m_scaleY / $f0);
            $r5.dispatchTouchEvent($r1);
            $r1.recycle();
        }
    }

    private void processKeyEvent(KeyEvent $r1) throws  {
        final int $i0 = $r1.getKeyCode();
        if ($i0 != 23 && $i0 != 21 && $i0 != 19 && $i0 != 22 && $i0 != 20) {
            ArrayList $r4 = getTheRootViews();
            if (!$r4.isEmpty()) {
                View $r5 = null;
                $i0 = $r4.size() - 1;
                while ($r5 == null && $i0 >= 0) {
                    View $r7 = (View) $r4.get($i0);
                    if ($r7 instanceof ViewGroup) {
                        $r5 = ((ViewGroup) $r7).getFocusedChild();
                    }
                    $i0--;
                }
                if ($r5 != null) {
                    $r5.dispatchKeyEvent($r1);
                } else {
                    ((View) $r4.get($r4.size() - 1)).dispatchKeyEvent($r1);
                }
            }
        } else if ($r1.getAction() == 0) {
            new Thread(new Runnable() {
                public void run() throws  {
                    new Instrumentation().sendKeyDownUpSync($i0);
                }
            }).start();
        }
    }

    private void logExtendedHierarchy(String $r1) throws  {
        if (this.m_extendedLoggingEnabled) {
            Log.d(TAG, m_hierarchyIndents[this.m_hierarchyLevel < 100 ? this.m_hierarchyLevel : 99] + $r1);
        }
    }

    private void filterNonDrawableViews(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1) throws  {
        synchronized (this.m_nondrawableViews) {
            boolean $z0 = true;
            while ($z0) {
                $z0 = false;
                Iterator $r3 = $r1.iterator();
                while ($r3.hasNext()) {
                    View $r5 = (View) $r3.next();
                    if (this.m_nondrawableViews.contains($r5)) {
                        $r1.remove($r5);
                        $z0 = true;
                        break;
                    }
                }
            }
        }
    }

    private void filterSkipDrawableViews(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1) throws  {
        synchronized (this.m_skipDrawableViews) {
            boolean $z0 = true;
            while ($z0) {
                $z0 = false;
                Iterator $r3 = $r1.iterator();
                while ($r3.hasNext()) {
                    View $r5 = (View) $r3.next();
                    if (this.m_skipDrawableViews.contains($r5)) {
                        $r1.remove($r5);
                        $z0 = true;
                        break;
                    }
                }
            }
        }
    }

    private void drawViewHierarchy(@Signature({"(", "Landroid/graphics/Canvas;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Canvas $r1, @Signature({"(", "Landroid/graphics/Canvas;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r2) throws  {
        long $l2 = SystemClock.currentThreadTimeMillis();
        HashSet $r3 = prepareSpecialDraw($r2);
        if (SystemClock.uptimeMillis() - this.m_lastLoggingTime < ((long) this.m_extendedLoggingPeriod)) {
            this.m_extendedLoggingEnabled = false;
        }
        this.m_hierarchyLevel = 0;
        if (this.m_extendedLoggingEnabled) {
            $l2 = SystemClock.currentThreadTimeMillis();
            Log.i(TAG, "================================");
            Log.i(TAG, "Starting view hierarchy drawing.");
        }
        WLNotDrawableLinearLayout.setDrawing(true);
        if ($r3 != null) {
            drawViewsSpecial($r1, $r2, $r3);
        } else {
            drawViews($r1, $r2);
        }
        WLNotDrawableLinearLayout.setDrawing(false);
        if (this.m_extendedLoggingEnabled) {
            long $l1 = SystemClock.currentThreadTimeMillis();
            Log.i(TAG, "Draw time: " + ($l1 - $l2) + " ms");
            this.m_lastLoggingTime = SystemClock.uptimeMillis();
        }
    }

    private void drawViewsSpecial(@Signature({"(", "Landroid/graphics/Canvas;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;)V"}) Canvas $r1, @Signature({"(", "Landroid/graphics/Canvas;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r2, @Signature({"(", "Landroid/graphics/Canvas;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;)V"}) HashSet<View> $r3) throws  {
        Iterator $r4 = $r2.iterator();
        while ($r4.hasNext()) {
            drawViewSpecial($r1, (View) $r4.next(), $r3, 1.0f, 1.0f, true);
        }
    }

    private void checkDimViews(View $r1, Canvas $r2) throws  {
        ViewGroup.LayoutParams $r3 = $r1.getLayoutParams();
        if ($r3 instanceof LayoutParams) {
            LayoutParams $r4 = (LayoutParams) $r3;
            float $f0 = $r4.dimAmount;
            if (($r4.flags & 2) == 2 && $f0 > 0.0f) {
                dimCanvas($r2, $f0);
            }
        }
    }

    private void dimCanvas(Canvas $r1, float $f0) throws  {
        int $i0 = (int) (((double) (255.0f * $f0)) + 0.5d);
        if ($i0 > 255) {
            $i0 = 255;
        } else if ($i0 < 0) {
            $i0 = 0;
        }
        $r1.drawColor(Color.argb($i0, 0, 0, 0));
        logExtendedHierarchy("Dimmed canvas with alpha: " + $i0);
    }

    private void drawViews(@Signature({"(", "Landroid/graphics/Canvas;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Canvas $r1, @Signature({"(", "Landroid/graphics/Canvas;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r2) throws  {
        Iterator $r3 = $r2.iterator();
        while ($r3.hasNext()) {
            drawView($r1, (View) $r3.next(), true);
        }
    }

    private void drawViewSpecial(@Signature({"(", "Landroid/graphics/Canvas;", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) Canvas $r1, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) View $r3, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) HashSet<View> $r2, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) float $f0, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) float $f1, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) boolean $z0) throws  {
        if ($r2.contains($r3)) {
            float $f2 = $r3.getScaleX();
            float $f3 = $r3.getScaleY();
            drawSingleView($r1, $r3, $f0, $f1, $z0);
            if (($r3 instanceof ViewGroup) && !($r3 instanceof IWLNotDrawable)) {
                int $i1;
                ViewGroup $r4 = (ViewGroup) $r3;
                int $i0 = $r4.getChildCount();
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    drawViewSpecial($r1, $r4.getChildAt($i1), $r2, $f0 * $f2, $f1 * $f3, false);
                }
                if ($r3 instanceof IWLPostChildrenDraw) {
                    $r3.getLocationOnScreen(this.m_position);
                    $i1 = this.m_position[0];
                    $i0 = this.m_position[1];
                    if ($z0) {
                        HashSet hashSet = this.m_layoutParamViews;
                        HashSet $r22 = hashSet;
                        if (hashSet.contains($r3)) {
                            LayoutParams $r8 = (LayoutParams) $r3.getLayoutParams();
                            $i1 = $r8.x;
                            $i0 = $r8.y;
                        }
                    }
                    $r1.translate((float) $i1, (float) $i0);
                    ((IWLPostChildrenDraw) $r3).onChildrenDrawn($r1);
                    $r1.translate((float) (-$i1), (float) (-$i0));
                    return;
                }
                return;
            }
            return;
        }
        drawView($r1, $r3, $z0);
    }

    private void drawSingleView(Canvas $r1, View $r2, float $f0, float $f1, boolean $z0) throws  {
        if ($r2.isShown() && !($r2 instanceof IWLNotDrawable)) {
            if (($r2 instanceof TextureView) || ($r2 instanceof SurfaceView)) {
                IViewHandler $r5 = (IViewHandler) this.m_specialViews.get($r2);
                if ($r5 != null) {
                    logExtendedHierarchy("Draw special view type: " + $r2.toString());
                    try {
                        $r5.draw($r1, this.m_scaleX, this.m_scaleY, this.m_translateX, this.m_translateY, $f0, $f1);
                        return;
                    } catch (Throwable $r8) {
                        Log.e(TAG, "Drawing failed", $r8);
                        return;
                    }
                }
                Log.d(TAG, "Trying to draw missing view. Please check!");
            } else if (!$r2.willNotDraw() || $r2.getBackground() != null) {
                if ($r2 instanceof ViewGroup) {
                    ViewGroup $r10 = (ViewGroup) $r2;
                    int $i0 = $r10.getChildCount();
                    if ($i0 <= 0 || ms_fieldChildrenCount == null) {
                        Log.d(TAG, "Normaly we shall not get here. Please check");
                        drawView($r1, $r2, $z0);
                        return;
                    }
                    try {
                        ms_fieldChildrenCount.setInt($r10, 0);
                        logExtendedHierarchy("Drawing only view background: " + $r2.toString());
                        drawView($r1, $r2, $z0);
                        ms_fieldChildrenCount.setInt($r10, $i0);
                        return;
                    } catch (Throwable $r12) {
                        Log.e(TAG, "Drawing error", $r12);
                        return;
                    }
                }
                Log.d(TAG, "Normaly we shall not get here. Please check");
                drawView($r1, $r2, $z0);
            }
        }
    }

    private ArrayList<View> getTheRootViews() throws  {
        ArrayList $r2 = WLTreeViewObserver.getInstance().getRootViews(this.m_displayID);
        int $i0 = WEBLINK.instance.getUiMode();
        if ($i0 == 1) {
            filterNonDrawableViews($r2);
        }
        if ($i0 != 1 && $i0 != 3) {
            return $r2;
        }
        filterSkipDrawableViews($r2);
        return $r2;
    }

    private HashSet<View> prepareSpecialDraw(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;"}) ArrayList<View> $r1) throws  {
        Throwable $r10;
        synchronized ($r0) {
            Iterator $r4;
            WLMirrorMode $r2 = $r0;
            $r0 = $r2;
            HashSet $r5 = null;
            for (View $r7 : $r2.m_specialViews.keySet()) {
                try {
                    if ($r7.isShown()) {
                        if ($r5 == null) {
                            $r5 = new HashSet();
                        }
                        try {
                            ViewParent $r8 = $r7.getParent();
                            while ($r8 != null && ($r8 instanceof View)) {
                                $r5.add((View) $r8);
                                $r8 = $r8.getParent();
                            }
                            $r5.add($r7);
                        } catch (Throwable th) {
                            $r10 = th;
                        }
                    }
                } catch (Throwable th2) {
                    $r10 = th2;
                }
            }
            if ($r5 != null) {
                boolean $z0 = false;
                $r4 = $r1.iterator();
                while ($r4.hasNext()) {
                    if ($r5.contains((View) $r4.next())) {
                        $z0 = true;
                        break;
                    }
                }
                if (!$z0) {
                    return null;
                }
            }
            return $r5;
        }
        throw $r10;
    }

    public int getWidth() throws  {
        return this.m_width;
    }

    public int getHeight() throws  {
        return this.m_height;
    }

    void setOffscreenScale(float $f0, float $f1) throws  {
        this.m_offsetScreenScaleX = $f0;
        this.m_offsetScreenScaleY = $f1;
    }

    void onAttachedToWindow(View view) throws  {
    }

    void onDetachedFromWindow(View $r1) throws  {
        for (View $r6 : this.m_specialViews.keySet()) {
            ViewParent $r7 = $r6.getParent();
            while ($r7 != null) {
                if ($r7.equals($r1)) {
                    IViewHandler $r8 = null;
                    if (($r6 instanceof SurfaceView) || ($r6 instanceof TextureView)) {
                        $r8 = (IViewHandler) this.m_specialViews.remove($r6);
                    }
                    if ($r8 != null) {
                        Log.d(TAG, "Child removed: " + $r6.getClass().getName());
                        $r8.detach();
                    }
                } else {
                    $r7 = $r7.getParent();
                }
            }
        }
    }

    void removeOffScreenView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_offscreenViews) {
                this.m_offscreenViews.remove($r1);
            }
        }
    }

    void addOffScreenView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_offscreenViews) {
                this.m_offscreenViews.add($r1);
            }
        }
    }

    void registerLayoutParamView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_layoutParamViews) {
                this.m_layoutParamViews.add($r1);
            }
        }
    }

    void unregisterLayoutParamView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_layoutParamViews) {
                this.m_layoutParamViews.remove($r1);
            }
        }
    }

    public boolean hasOffScreenViews() throws  {
        return this.m_offscreenViews.size() > 0;
    }

    public void unregisterNonDrawbleView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_nondrawableViews) {
                this.m_nondrawableViews.remove($r1);
            }
        }
    }

    public void registerNonDrawbleView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_nondrawableViews) {
                this.m_nondrawableViews.add($r1);
            }
        }
    }

    public void unregisterSkipDrawbleView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_skipDrawableViews) {
                this.m_skipDrawableViews.remove($r1);
            }
        }
    }

    public void registerSkipDrawbleView(View $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_skipDrawableViews) {
                this.m_skipDrawableViews.add($r1);
            }
        }
    }

    boolean dipatchTouchEventOnScreenViews(MotionEvent $r1) throws  {
        ArrayList $r3 = getTheRootViews();
        if ($r3.isEmpty()) {
            return false;
        }
        int[] $r2 = new int[2];
        View $r5 = WLEventUtils.hittest($r3, this.m_layoutParamViews, $r1.getX() - ((float) this.m_translateX), $r1.getY() - ((float) this.m_translateY), false);
        if ($r5 == null) {
            return false;
        }
        $r5.getLocationOnScreen($r2);
        $r1 = WLEventUtils.createMotionEvent($r1, -($r2[0] + this.m_translateX), -($r2[1] + this.m_translateY), 1.0f, 1.0f);
        boolean $z0 = $r5.dispatchTouchEvent($r1);
        $r1.recycle();
        return $z0;
    }

    public static void setTextureViewHandlerUseStaticBitmap(boolean $z0) throws  {
        TextureViewHandler.ENABLE_STATIC_BITMAP = $z0;
    }

    private long calculateAverageFrameTime(long $l0) throws  {
        this.m_frameTimeMovingAverage -= this.m_frameTimeArray[this.m_frameTimeCounter];
        this.m_frameTimeMovingAverage += $l0;
        this.m_frameTimeArray[this.m_frameTimeCounter] = $l0;
        int $i1 = this.m_frameTimeCounter + 1;
        this.m_frameTimeCounter = $i1;
        if ($i1 == 64) {
            this.m_frameTimeCounter = 0;
        }
        return this.m_frameTimeMovingAverage / 64;
    }

    void showDebugOverlay(boolean $z0) throws  {
        this.m_debugOverlayEnabled = $z0;
    }
}
