package com.abaltatech.weblinkserver;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
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
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.Window;
import android.view.WindowManager;
import com.abaltatech.mcs.logger.MCSLogger;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class WLMirrorLayer implements OnHierarchyChangeListener, WLBrowserEventHandler, WLKeyboardEventHandler, WLLayer, WLMouseEventHandler, WLTouchEventHandler {
    private static final int DIRTY_OPAQUE_FLAG = 6291456;
    private static final String TAG = "WLMirrorLayer";
    private static final String[] m_hierarchyIndents = new String[100];
    private static final int m_maxIndent = 100;
    private static final Field ms_fieldChildrenCount;
    private static final Field ms_fieldPrivateFlags;
    private int m_backgroundColor;
    private Bitmap m_bitmap;
    private Canvas m_canvas;
    private Display m_display;
    private volatile long m_drawFinishTime;
    private volatile boolean m_drawing;
    private boolean m_extendedLoggingEnabled;
    private ByteBuffer m_frameBuffer;
    private int m_hierarchyLevel;
    private boolean m_isActive;
    private long m_lastLoggingTime;
    private final HashSet<Class<? extends View>> m_nonCacheViewClasses;
    private final HashMap<View, NonCacheViewInfo> m_nonCacheViewInfoMap;
    private final int[] m_position;
    private WLRect m_rect;
    private float m_scaleX;
    private float m_scaleY;
    private ConcurrentHashMap<View, IViewHandler> m_specialViews;
    private Handler m_uiHandler;

    class C04011 implements Runnable {
        C04011() throws  {
        }

        public void run() throws  {
            if (!WLMirrorLayer.this.m_isActive) {
                WLMirrorLayer.this.m_isActive = true;
                WLMirrorLayer.this.m_drawing = false;
                WLMirrorLayer.this.m_drawFinishTime = 0;
                WLMirrorLayer.this.m_specialViews.clear();
                WLMirrorLayer.this.m_nonCacheViewInfoMap.clear();
                WLTreeViewObserver.getInstance().registerOnHierarchyChangeListener(WLMirrorLayer.this);
            }
        }
    }

    class C04022 implements Runnable {
        C04022() throws  {
        }

        public void run() throws  {
            if (WLMirrorLayer.this.m_isActive) {
                WLMirrorLayer.this.m_isActive = false;
                WLTreeViewObserver.getInstance().unregisterOnHierarchyChangeListener(WLMirrorLayer.this);
                for (IViewHandler $r7 : WLMirrorLayer.this.m_specialViews.values()) {
                    if ($r7 != null) {
                        $r7.detach();
                    }
                }
                WLMirrorLayer.this.m_specialViews.clear();
                synchronized (WLMirrorLayer.this.m_nonCacheViewInfoMap) {
                    for (Entry entry : WLMirrorLayer.this.m_nonCacheViewInfoMap.entrySet()) {
                        View $r12 = (View) entry.getKey();
                        NonCacheViewInfo $r13 = (NonCacheViewInfo) entry.getValue();
                        try {
                            $r12.setDrawingCacheEnabled($r13.m_drawingCacheEnabled);
                            $r12.setLayerType($r13.m_layerType, null);
                        } catch (Exception e) {
                        }
                    }
                    WLMirrorLayer.this.m_nonCacheViewInfoMap.clear();
                }
            }
        }
    }

    class C04033 implements Runnable {
        C04033() throws  {
        }

        public void run() throws  {
            ArrayList $r4 = WLTreeViewObserver.getInstance().getRootViews();
            WLMirrorLayer.this.m_canvas.drawColor(WLMirrorLayer.this.m_backgroundColor);
            Context $r7 = WLServerApp.getAppContext();
            PowerManager $r9 = (PowerManager) $r7.getSystemService("power");
            boolean $z0 = $r9 != null ? $r9.isScreenOn() : true;
            boolean $z1 = ((KeyguardManager) $r7.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
            if (!($r4.isEmpty() || !$z0 || $z1)) {
                int $i1 = WLMirrorLayer.this.m_display.getWidth();
                int $i0 = WLMirrorLayer.this.m_display.getHeight();
                Rect rect = new Rect();
                $z0 = false;
                for (int $i2 = 0; $i2 < $r4.size(); $i2++) {
                    Window $r13 = WLMirrorLayer.getWindow((View) $r4.get($i2));
                    if ($r13 != null) {
                        rect = new Rect();
                        $r13.getDecorView().getWindowVisibleDisplayFrame(rect);
                        rect.union(rect);
                        $z0 = true;
                    }
                }
                if (!$z0) {
                    rect.union(WLMirrorLayer.this.m_display.getWidth(), WLMirrorLayer.this.m_display.getHeight());
                }
                WLMirrorLayer $r5 = WLMirrorLayer.this;
                float $f0 = (float) $i1;
                WLMirrorLayer $r14 = WLMirrorLayer.this;
                float $f02 = $f0 / ((float) $r14.m_rect.width());
                $f0 = $f02;
                $r5.m_scaleX = $f02;
                $r5 = WLMirrorLayer.this;
                $f0 = (float) $i0;
                $r14 = WLMirrorLayer.this;
                $f02 = $f0 / ((float) $r14.m_rect.height());
                $f0 = $f02;
                $r5.m_scaleY = $f02;
                WLMirrorLayer.this.m_canvas.save();
                WLMirrorLayer.this.m_canvas.scale(1.0f / WLMirrorLayer.this.m_scaleX, 1.0f / WLMirrorLayer.this.m_scaleY);
                WLMirrorLayer.this.m_canvas.clipRect(rect);
                WLMirrorLayer.this.drawViewHierarchy($r4);
                WLMirrorLayer.this.m_canvas.restore();
            }
            synchronized (WLMirrorLayer.this) {
                $r14 = WLMirrorLayer.this;
                $r14.m_frameBuffer.position(0);
                $r14 = WLMirrorLayer.this;
                Bitmap $r17 = $r14.m_bitmap;
                $r14 = WLMirrorLayer.this;
                $r17.copyPixelsToBuffer($r14.m_frameBuffer);
                WLMirrorLayer.this.m_drawing = false;
            }
            WLMirrorLayer.this.m_drawFinishTime = SystemClock.uptimeMillis();
        }
    }

    private static class GLSurfaceViewHandler implements Renderer, IViewHandler {
        private static final String TAG = "GLSurfaceViewHandler";
        private static final Field mFieldRenderer;
        private Bitmap m_backBuffer;
        private Renderer m_renderer;
        private final int[] m_storage = new int[4];
        private GLSurfaceView m_view;

        static {
            Field $r2 = null;
            if (VERSION.SDK_INT >= 16) {
                try {
                    Field $r1 = GLSurfaceView.class.getDeclaredField("mRenderer");
                    $r2 = $r1;
                    $r1.setAccessible(true);
                } catch (Exception $r0) {
                    MCSLogger.log(TAG, "init()", $r0);
                }
            }
            mFieldRenderer = $r2;
        }

        public GLSurfaceViewHandler(GLSurfaceView $r1) throws  {
            this.m_view = $r1;
            interceptView();
            Log.i(TAG, "Created GLSurfaceViewHandler from view " + $r1.toString());
        }

        public synchronized void draw(Canvas $r1, float $f0, float $f1, float viewHierarchyScaleX, float viewHierarchyScaleY) throws  {
            float $f2 = (float) this.m_view.getWidth();
            $f2 = ($f2 / $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            viewHierarchyScaleX = $f2;
            int $i2 = (int) $f2;
            $f2 = (float) this.m_view.getHeight();
            $f2 = ($f2 / $f1) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            viewHierarchyScaleX = $f2;
            int $i3 = (int) $f2;
            if (getViewRenderer() != this) {
                interceptView();
            } else if (this.m_backBuffer != null) {
                this.m_view.getLocationOnScreen(this.m_storage);
                $f2 = (((float) this.m_storage[0]) / $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                $f0 = $f2;
                int $i0 = (int) $f2;
                $f2 = (float) this.m_storage[1];
                $f2 = ($f2 / $f1) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                $f0 = $f2;
                int $i1 = (int) $f2;
                $r1.save();
                $r1.setMatrix(null);
                Bitmap $r4 = this.m_backBuffer;
                Bitmap $r7 = this.m_backBuffer;
                int $i4 = $r7.getWidth();
                $r7 = this.m_backBuffer;
                $r1.drawBitmap($r4, new Rect(0, 0, $i4, $r7.getHeight()), new RectF((float) $i0, (float) $i1, (float) ($i0 + $i2), (float) ($i1 + $i3)), null);
                $r1.restore();
            }
        }

        public void attach() throws  {
        }

        public void detach() throws  {
            destroy();
        }

        public void destroy() throws  {
            if (this.m_view != null) {
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
            }
        }

        public void onSurfaceChanged(GL10 $r1, int $i0, int $i1) throws  {
            if (this.m_renderer != null) {
                this.m_renderer.onSurfaceChanged($r1, $i0, $i1);
            }
        }

        public synchronized void onDrawFrame(GL10 $r1) throws  {
            if (this.m_renderer != null) {
                this.m_renderer.onDrawFrame($r1);
                $r1.glGetIntegerv(DisplayStrings.DS_JOIN_CARPOOL_GOOGLE_CONNECT_LATER, this.m_storage, 0);
                if (!(this.m_backBuffer != null && this.m_backBuffer.getWidth() == this.m_storage[2] && this.m_backBuffer.getHeight() == this.m_storage[3])) {
                    if (this.m_backBuffer != null) {
                        this.m_backBuffer.recycle();
                        this.m_backBuffer = null;
                    }
                    this.m_backBuffer = Bitmap.createBitmap(this.m_storage[2], this.m_storage[3], Config.ARGB_8888);
                }
                if (this.m_backBuffer != null) {
                    WLImageUtils.glReadPixels(this.m_backBuffer, true);
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
                MCSLogger.log(TAG, "getViewRenderer()", $r1);
                return null;
            }
        }

        private void setViewRenderer(Renderer $r1) throws  {
            if (mFieldRenderer != null && this.m_view != null) {
                try {
                    mFieldRenderer.set(this.m_view, $r1);
                } catch (Exception $r2) {
                    MCSLogger.log(TAG, "setViewRenderer()", $r2);
                }
            }
        }

        private void interceptView() throws  {
            if (mFieldRenderer != null) {
                GLSurfaceViewHandler $r2 = getViewRenderer();
                if ($r2 != this) {
                    setViewRenderer(this);
                    this.m_renderer = $r2;
                }
            }
        }
    }

    private static class NonCacheViewInfo {
        boolean m_drawingCacheEnabled;
        int m_layerType;

        NonCacheViewInfo(boolean $z0, int $i0) throws  {
            this.m_drawingCacheEnabled = $z0;
            this.m_layerType = $i0;
        }
    }

    private static class NotDrawableViewHandler implements IViewHandler {
        public NotDrawableViewHandler(IWLNotDrawable $r1) throws  {
            Log.i(WLMirrorLayer.TAG, "Created NotDrawableViewHandler from view " + $r1.toString());
        }

        public void attach() throws  {
        }

        public void detach() throws  {
            destroy();
        }

        public synchronized void destroy() throws  {
        }

        public void draw(Canvas canvas, float scaleX, float scaleY, float viewHierarchyScaleX, float viewHierarchyScaleY) throws  {
        }
    }

    private static class SingletonHolder {
        public static final WLMirrorLayer INSTANCE = new WLMirrorLayer();

        private SingletonHolder() throws  {
        }
    }

    private class SurfaceViewHandler implements IViewHandler {
        private boolean m_attached;
        private final Callback m_customCallback = new C04113();
        private final SurfaceHolder m_customHolder = new C04135();
        private final OnFrameAvailableListener m_frameListener = new C04124();
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

        class C04091 implements Runnable {
            C04091() throws  {
            }

            public void run() throws  {
                SurfaceViewHandler.this.attach();
            }
        }

        class C04102 implements Runnable {
            C04102() throws  {
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

        class C04113 implements Callback {
            C04113() throws  {
            }

            public void surfaceChanged(SurfaceHolder holder, int $i0, int $i1, int $i2) throws  {
                Log.d(WLMirrorLayer.TAG, "surfaceChanged()");
                synchronized (SurfaceViewHandler.this) {
                    SurfaceViewHandler.this.m_origFormat = $i0;
                    if (!($i1 == SurfaceViewHandler.this.m_origWidth && $i2 == SurfaceViewHandler.this.m_origHeight) && $i1 > 0 && $i2 > 0) {
                        SurfaceViewHandler.this.m_origWidth = $i1;
                        SurfaceViewHandler.this.m_origHeight = $i2;
                        if (SurfaceViewHandler.this.m_surface != null) {
                            SurfaceViewHandler.this.m_surface.destroySurface();
                        }
                        SurfaceViewHandler.this.m_surface = WLSurface.create(SurfaceViewHandler.this.m_origWidth, SurfaceViewHandler.this.m_origHeight);
                        if (SurfaceViewHandler.this.m_surface != null) {
                            SurfaceViewHandler.this.m_surface.setOnFrameAvailableListener(SurfaceViewHandler.this.m_frameListener);
                            Iterator $r8 = SurfaceViewHandler.this.m_origCallbacks.iterator();
                            while ($r8.hasNext()) {
                                ((Callback) $r8.next()).surfaceChanged(SurfaceViewHandler.this.m_customHolder, 1, $i1, $i2);
                            }
                        }
                    }
                }
            }

            public void surfaceCreated(SurfaceHolder holder) throws  {
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

        class C04124 implements OnFrameAvailableListener {
            C04124() throws  {
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onFrameAvailable(android.graphics.SurfaceTexture r14) throws  {
                /*
                r13 = this;
                r0 = com.abaltatech.weblinkserver.WLMirrorLayer.SurfaceViewHandler.this;
                monitor-enter(r0);
                r1 = com.abaltatech.weblinkserver.WLMirrorLayer.SurfaceViewHandler.this;	 Catch:{ Throwable -> 0x0044 }
                r2 = r1.m_surface;	 Catch:{ Throwable -> 0x0044 }
                if (r2 == 0) goto L_0x0039;
            L_0x000b:
                r1 = com.abaltatech.weblinkserver.WLMirrorLayer.SurfaceViewHandler.this;	 Catch:{ Throwable -> 0x0044 }
                r3 = r1.m_origHolder;	 Catch:{ Throwable -> 0x0044 }
                if (r3 == 0) goto L_0x0039;
            L_0x0013:
                r1 = com.abaltatech.weblinkserver.WLMirrorLayer.SurfaceViewHandler.this;	 Catch:{ Exception -> 0x003b }
                r3 = r1.m_origHolder;	 Catch:{ Exception -> 0x003b }
                r5 = 0;
                r4 = r3.lockCanvas(r5);	 Catch:{ Exception -> 0x003b }
                if (r4 == 0) goto L_0x0039;
            L_0x0020:
                r1 = com.abaltatech.weblinkserver.WLMirrorLayer.SurfaceViewHandler.this;	 Catch:{ Exception -> 0x003b }
                r2 = r1.m_surface;	 Catch:{ Exception -> 0x003b }
                r6 = r2.getBitmap();	 Catch:{ Exception -> 0x003b }
                r7 = 0;
                r8 = 0;
                r5 = 0;
                r4.drawBitmap(r6, r7, r8, r5);	 Catch:{ Exception -> 0x003b }
                r1 = com.abaltatech.weblinkserver.WLMirrorLayer.SurfaceViewHandler.this;	 Catch:{ Exception -> 0x003b }
                r3 = r1.m_origHolder;	 Catch:{ Exception -> 0x003b }
                r3.unlockCanvasAndPost(r4);	 Catch:{ Exception -> 0x003b }
            L_0x0039:
                monitor-exit(r0);	 Catch:{ Throwable -> 0x0044 }
                return;
            L_0x003b:
                r9 = move-exception;
                r10 = "SurfaceHolder, SurfaceTexture.OnFrameAvailableListener";
                r11 = "Exception while copying the bitmap";
                android.util.Log.e(r10, r11, r9);	 Catch:{ Throwable -> 0x0044 }
                goto L_0x0039;
            L_0x0044:
                r12 = move-exception;
                monitor-exit(r0);	 Catch:{ Throwable -> 0x0044 }
                throw r12;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLMirrorLayer.SurfaceViewHandler.4.onFrameAvailable(android.graphics.SurfaceTexture):void");
            }
        }

        class C04135 implements SurfaceHolder {
            C04135() throws  {
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
                    Canvas $r6 = $r5.lockCanvas($r1);
                    Log.d("SurfaceViewHandler.SurfaceHolder", "lockCanvas");
                    return $r6;
                } catch (Exception e) {
                    return null;
                }
            }

            public void unlockCanvasAndPost(Canvas $r1) throws  {
                Surface $r4 = SurfaceViewHandler.this.m_surface != null ? SurfaceViewHandler.this.m_surface.getSurface() : null;
                if ($r4 != null && $r1 != null) {
                    $r4.unlockCanvasAndPost($r1);
                    Log.d("SurfaceViewHandler.SurfaceHolder", "unlockCanvasAndPost");
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
            } catch (Exception e) {
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
            Log.i(WLMirrorLayer.TAG, "Created SurfaceViewHandler from view " + $r2.toString());
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
                        WLMirrorLayer.this.m_uiHandler.postDelayed(new C04091(), 50);
                    } else {
                        ArrayList $r1 = new ArrayList();
                        $r1.add(this.m_customCallback);
                        this.ms_fieldCallbacks.set(this.m_view, $r1);
                        this.ms_fieldSurfaceHolder.set(this.m_view, this.m_customHolder);
                        if (this.m_origCallbacks.size() == 0) {
                            Log.e(WLMirrorLayer.TAG, "There are no callbacks attached to the surface holder. Nobody will be notified for changes!!! View: " + this.m_view.toString());
                        }
                        WLMirrorLayer.this.m_uiHandler.post(new C04102());
                        this.m_attached = true;
                    }
                } catch (Exception e) {
                    this.m_origCallbacks = null;
                    this.m_origHolder = null;
                    this.m_origFormat = -1;
                }
            }
        }

        public synchronized void detach() throws  {
            destroy();
        }

        public synchronized void destroy() throws  {
            if (this.m_attached) {
                try {
                    Iterator $r2 = this.m_origCallbacks.iterator();
                    while ($r2.hasNext()) {
                        ((Callback) $r2.next()).surfaceDestroyed(this.m_customHolder);
                    }
                    this.ms_fieldCallbacks.set(this.m_view, this.m_origCallbacks);
                    this.ms_fieldSurfaceHolder.set(this.m_view, this.m_origHolder);
                    $r2 = this.m_origCallbacks.iterator();
                    while ($r2.hasNext()) {
                        Callback $r4 = (Callback) $r2.next();
                        $r4.surfaceCreated(this.m_origHolder);
                        $r4.surfaceChanged(this.m_origHolder, this.m_origFormat, this.m_origWidth, this.m_origHeight);
                    }
                    this.m_origFormat = -1;
                    this.m_origWidth = 0;
                    this.m_origHeight = 0;
                    this.m_origCallbacks = null;
                    this.m_origHolder = null;
                    this.m_origFormat = -1;
                    this.m_origWidth = 0;
                    this.m_origHeight = 0;
                    this.m_origCallbacks = null;
                    this.m_origHolder = null;
                    if (this.m_surface != null) {
                        this.m_surface.destroySurface();
                        this.m_surface = null;
                    }
                } catch (Exception e) {
                    this.m_origFormat = -1;
                    this.m_origWidth = 0;
                    this.m_origHeight = 0;
                    this.m_origCallbacks = null;
                    this.m_origHolder = null;
                    if (this.m_surface != null) {
                        this.m_surface.destroySurface();
                        this.m_surface = null;
                    }
                } catch (Throwable th) {
                    this.m_origFormat = -1;
                    this.m_origWidth = 0;
                    this.m_origHeight = 0;
                    this.m_origCallbacks = null;
                    this.m_origHolder = null;
                    if (this.m_surface != null) {
                        this.m_surface.destroySurface();
                        this.m_surface = null;
                    }
                }
            }
            this.m_view = null;
        }

        public synchronized void draw(Canvas $r1, float scaleX, float scaleY, float viewHierarchyScaleX, float viewHierarchyScaleY) throws  {
            try {
                this.m_view.getLocationOnScreen(this.m_position);
                int $i0 = this.m_position[0];
                int $i1 = this.m_position[1];
                if (this.m_surface != null) {
                    $r1.drawBitmap(this.m_surface.getBitmap(), (float) $i0, (float) $i1, null);
                }
            } catch (Exception $r2) {
                MCSLogger.log(WLMirrorLayer.TAG, "Surface View drawing error", $r2);
            }
        }
    }

    private static class TextureViewHandler implements SurfaceTextureListener, IViewHandler {
        private final RectF m_clipRect = new RectF();
        private boolean m_frameAvailable;
        private final int[] m_position = new int[2];
        private SurfaceTextureListener m_prevListener;
        private TextureView m_view;
        private Bitmap m_viewBitmap;

        public TextureViewHandler(TextureView $r1) throws  {
            this.m_view = $r1;
            this.m_prevListener = this.m_view.getSurfaceTextureListener();
            this.m_view.setSurfaceTextureListener(this);
            Log.i(WLMirrorLayer.TAG, "Created TextureViewHandler from view " + $r1.toString());
        }

        public synchronized void draw(Canvas $r1, float $f0, float $f1, float $f2, float $f3) throws  {
            float $f22 = ((((float) this.m_view.getWidth()) / $f0) * $f2) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            $f2 = $f22;
            int $i1 = (int) $f22;
            $f22 = (float) this.m_view.getHeight();
            $f22 /= $f1;
            $f2 = $f22;
            $f22 = ($f22 * $f3) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            $f2 = $f22;
            int $i0 = (int) $f22;
            if (!(this.m_viewBitmap == null || (this.m_viewBitmap.getWidth() == $i1 && this.m_viewBitmap.getHeight() == $i0))) {
                this.m_viewBitmap.recycle();
                this.m_viewBitmap = null;
            }
            try {
                if (this.m_frameAvailable || this.m_viewBitmap == null) {
                    Bitmap $r4;
                    if (this.m_viewBitmap != null) {
                        $r4 = this.m_view.getBitmap(this.m_viewBitmap);
                    } else {
                        $r4 = this.m_view.getBitmap($i1, $i0);
                    }
                    this.m_viewBitmap = $r4;
                    this.m_frameAvailable = false;
                }
                if (this.m_viewBitmap != null) {
                    this.m_view.getLocationOnScreen(this.m_position);
                    $f22 = (float) this.m_position[0];
                    $f22 = ($f22 / $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                    $f2 = $f22;
                    $i1 = (int) $f22;
                    $f22 = (float) this.m_position[1];
                    $f22 = ($f22 / $f1) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                    $f2 = $f22;
                    $i0 = (int) $f22;
                    $r1.save();
                    $r1.setMatrix(null);
                    WLMirrorLayer.calcClipRect(this.m_view, this.m_clipRect);
                    RectF $r6 = this.m_clipRect;
                    $f22 = $r6.left;
                    $f22 /= $f0;
                    $f2 = $f22;
                    $r6.left = $f22;
                    $r6 = this.m_clipRect;
                    $f22 = $r6.top;
                    $f22 /= $f1;
                    $f2 = $f22;
                    $r6.top = $f22;
                    $r6 = this.m_clipRect;
                    $r6.right /= $f0;
                    $r6 = this.m_clipRect;
                    $f22 = $r6.bottom;
                    $f22 /= $f1;
                    $f0 = $f22;
                    $r6.bottom = $f22;
                    $r1.clipRect(this.m_clipRect);
                    $r1.drawBitmap(this.m_viewBitmap, (float) $i1, (float) $i0, null);
                    $r1.restore();
                }
            } catch (Exception $r2) {
                MCSLogger.log(WLMirrorLayer.TAG, "Surface View drawing error", $r2);
            }
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
                if (this.m_viewBitmap != null) {
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
            if (this.m_prevListener != null) {
                return this.m_prevListener.onSurfaceTextureDestroyed($r1);
            }
            return false;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture $r1) throws  {
            this.m_frameAvailable = true;
            if (this.m_prevListener != null) {
                this.m_prevListener.onSurfaceTextureUpdated($r1);
            }
        }
    }

    @android.annotation.SuppressLint({"NewApi"})
    private void drawView(android.view.View r21, float r22, float r23, boolean r24) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0051 in list []
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
        r20 = this;
        if (r21 == 0) goto L_0x0165;
    L_0x0002:
        r0 = r21;
        r2 = r0.getWindowVisibility();
        r0 = r21;
        r3 = r0.isShown();
        if (r3 == 0) goto L_0x0134;
    L_0x0010:
        r4 = 8;
        if (r2 == r4) goto L_0x0134;
    L_0x0014:
        if (r24 == 0) goto L_0x001d;
    L_0x0016:
        r0 = r20;
        r1 = r21;
        r0.checkDimViews(r1);
    L_0x001d:
        r2 = 0;
        r5 = ms_fieldPrivateFlags;
        if (r5 == 0) goto L_0x0051;
    L_0x0022:
        r5 = ms_fieldPrivateFlags;
        r0 = r21;	 Catch:{ Exception -> 0x011a }
        r6 = r5.get(r0);	 Catch:{ Exception -> 0x011a }
        r8 = r6;	 Catch:{ Exception -> 0x011a }
        r8 = (java.lang.Integer) r8;	 Catch:{ Exception -> 0x011a }
        r7 = r8;	 Catch:{ Exception -> 0x011a }
        r9 = r7.intValue();	 Catch:{ Exception -> 0x011a }
        r2 = r9;
        r4 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r10 = r9 & r4;
        if (r10 == 0) goto L_0x0051;	 Catch:{ Exception -> 0x011a }
    L_0x003a:
        r11 = "Clear DIRTY_OPAQUE_FLAG";	 Catch:{ Exception -> 0x011a }
        r0 = r20;	 Catch:{ Exception -> 0x011a }
        r0.logExtendedHierarchy(r11);	 Catch:{ Exception -> 0x011a }
        r5 = ms_fieldPrivateFlags;
        r4 = -6291457; // 0xffffffffff9fffff float:NaN double:NaN;	 Catch:{ Exception -> 0x011a }
        r9 = r4 & r9;	 Catch:{ Exception -> 0x011a }
        r7 = java.lang.Integer.valueOf(r9);	 Catch:{ Exception -> 0x011a }
        r0 = r21;	 Catch:{ Exception -> 0x011a }
        r5.set(r0, r7);	 Catch:{ Exception -> 0x011a }
    L_0x0051:
        r0 = r20;
        r12 = r0.m_position;
        r0 = r21;
        r0.getLocationOnScreen(r12);
        r0 = r20;
        r13 = r0.m_canvas;
        r13.save();
        r0 = r20;
        r13 = r0.m_canvas;
        r0 = r22;
        r1 = r23;
        r13.scale(r0, r1);
        r0 = r20;
        r13 = r0.m_canvas;
        r0 = r20;
        r12 = r0.m_position;
        r4 = 0;
        r9 = r12[r4];
        r0 = (float) r9;
        r22 = r0;
        r0 = r20;
        r12 = r0.m_position;
        r4 = 1;
        r9 = r12[r4];
        r0 = (float) r9;
        r23 = r0;
        r0 = r22;
        r1 = r23;
        r13.translate(r0, r1);
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r11 = "Drawing view ";
        r14 = r14.append(r11);
        r0 = r21;
        r15 = r0.toString();
        r14 = r14.append(r15);
        r15 = r14.toString();
        r0 = r20;
        r0.logExtendedHierarchy(r15);
        r0 = r20;
        r13 = r0.m_canvas;
        r0 = r21;
        r0.draw(r13);
        r11 = "All child views drawn.";
        r0 = r20;
        r0.logExtendedHierarchy(r11);
        r0 = r20;
        r13 = r0.m_canvas;
        goto L_0x00c1;
    L_0x00be:
        goto L_0x0051;
    L_0x00c1:
        r0 = r20;
        r12 = r0.m_position;
        r4 = 0;
        r9 = r12[r4];
        r9 = -r9;
        r0 = (float) r9;
        r22 = r0;
        r0 = r20;
        r12 = r0.m_position;
        r4 = 1;
        r9 = r12[r4];
        r9 = -r9;
        r0 = (float) r9;
        r23 = r0;
        r0 = r22;
        r1 = r23;
        r13.translate(r0, r1);
        r0 = r20;
        r13 = r0.m_canvas;
        r13.restore();
        r5 = ms_fieldPrivateFlags;
        if (r5 == 0) goto L_0x0166;
    L_0x00e9:
        r4 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r9 = r2 & r4;
        if (r9 == 0) goto L_0x0167;
    L_0x00f0:
        r11 = "Restore DIRTY_OPAQUE_FLAG";	 Catch:{ Exception -> 0x0127 }
        r0 = r20;	 Catch:{ Exception -> 0x0127 }
        r0.logExtendedHierarchy(r11);	 Catch:{ Exception -> 0x0127 }
        r5 = ms_fieldPrivateFlags;	 Catch:{ Exception -> 0x0127 }
        r0 = r21;	 Catch:{ Exception -> 0x0127 }
        r6 = r5.get(r0);	 Catch:{ Exception -> 0x0127 }
        r16 = r6;	 Catch:{ Exception -> 0x0127 }
        r16 = (java.lang.Integer) r16;	 Catch:{ Exception -> 0x0127 }
        r7 = r16;	 Catch:{ Exception -> 0x0127 }
        r9 = r7.intValue();	 Catch:{ Exception -> 0x0127 }
        r5 = ms_fieldPrivateFlags;
        r4 = 6291456; // 0x600000 float:8.816208E-39 double:3.1083923E-317;
        r2 = r2 & r4;
        r2 = r2 | r9;	 Catch:{ Exception -> 0x0127 }
        r7 = java.lang.Integer.valueOf(r2);	 Catch:{ Exception -> 0x0127 }
        r0 = r21;	 Catch:{ Exception -> 0x0127 }
        r5.set(r0, r7);	 Catch:{ Exception -> 0x0127 }
        return;
    L_0x011a:
        r17 = move-exception;
        r11 = "WLMirrorLayer";
        r18 = "drawView save flags failed";
        r0 = r18;
        r1 = r17;
        com.abaltatech.mcs.logger.MCSLogger.log(r11, r0, r1);
        goto L_0x00be;
    L_0x0127:
        r19 = move-exception;
        r11 = "WLMirrorLayer";
        r18 = "drawView restore flags failed";
        r0 = r18;
        r1 = r19;
        com.abaltatech.mcs.logger.MCSLogger.log(r11, r0, r1);
        return;
    L_0x0134:
        r0 = r20;
        r0 = r0.m_extendedLoggingEnabled;
        r24 = r0;
        if (r24 == 0) goto L_0x0168;
    L_0x013c:
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r11 = "Skipping view due to WindowVisibility=";
        r14 = r14.append(r11);
        r14 = r14.append(r2);
        r11 = ": ";
        r14 = r14.append(r11);
        r0 = r21;
        r15 = r0.toString();
        r14 = r14.append(r15);
        r15 = r14.toString();
        r0 = r20;
        r0.logExtendedHierarchy(r15);
        return;
    L_0x0165:
        return;
    L_0x0166:
        return;
    L_0x0167:
        return;
    L_0x0168:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLMirrorLayer.drawView(android.view.View, float, float, boolean):void");
    }

    public boolean onGoForward() throws  {
        return false;
    }

    public boolean onGoHome() throws  {
        return false;
    }

    public boolean onGoUrl(String url) throws  {
        return false;
    }

    static {
        Field $r3;
        Field $r4;
        try {
            Class $r1 = Class.forName("android.view.ViewGroup");
            Field $r2 = $r1.getDeclaredField("mChildren");
            $r3 = $r1.getDeclaredField("mChildrenCount");
            $r4 = $r3;
            $r2.setAccessible(true);
            $r3.setAccessible(true);
        } catch (Exception e) {
            $r4 = null;
        }
        ms_fieldChildrenCount = $r4;
        m_hierarchyIndents[0] = "";
        for (int $i1 = 1; $i1 < 100; $i1++) {
            m_hierarchyIndents[$i1] = m_hierarchyIndents[$i1 - 1] + "  ";
        }
        try {
            $r2 = Class.forName("android.view.View").getDeclaredField("mPrivateFlags");
            $r3 = $r2;
            $r2.setAccessible(true);
        } catch (Exception e2) {
            $r3 = null;
        }
        ms_fieldPrivateFlags = $r3;
    }

    public static WLMirrorLayer getInstance() throws  {
        return SingletonHolder.INSTANCE;
    }

    private WLMirrorLayer() throws  {
        this.m_position = new int[2];
        this.m_backgroundColor = -16777216;
        this.m_lastLoggingTime = 0;
        this.m_uiHandler = new Handler(WLServerApp.getAppContext().getMainLooper());
        this.m_specialViews = new ConcurrentHashMap();
        this.m_nonCacheViewClasses = new HashSet();
        this.m_nonCacheViewInfoMap = new HashMap();
        this.m_display = ((WindowManager) WLServerApp.getAppContext().getSystemService("window")).getDefaultDisplay();
    }

    public synchronized void init() throws  {
        this.m_uiHandler.post(new C04011());
    }

    public synchronized void terminate() throws  {
        this.m_uiHandler.post(new C04022());
    }

    public void registerNonCacheView(@Signature({"(", "Ljava/lang/Class", "<+", "Landroid/view/View;", ">;)V"}) Class<? extends View> $r1) throws  {
        this.m_nonCacheViewClasses.add($r1);
    }

    public boolean isActive() throws  {
        return this.m_isActive;
    }

    public synchronized void setScreenRect(WLRect $r1) throws  {
        int $i0 = 0;
        synchronized (this) {
            int $i1 = this.m_rect == null ? 0 : this.m_rect.width();
            if (this.m_rect != null) {
                $i0 = this.m_rect.height();
            }
            this.m_rect = $r1;
            if (!($r1.width() == $i1 && $r1.height() == $i0)) {
                this.m_bitmap = Bitmap.createBitmap($r1.width(), $r1.height(), Config.ARGB_8888);
                this.m_canvas = new Canvas(this.m_bitmap);
                this.m_frameBuffer = ByteBuffer.allocateDirect(this.m_bitmap.getRowBytes() * this.m_rect.height());
            }
        }
    }

    public WLRect getScreenRect() throws  {
        return this.m_rect;
    }

    public void render(ByteBuffer $r1, int $i0, int $i1, int $i2) throws  {
        if (this.m_bitmap != null) {
            synchronized (this) {
                if (!(this.m_canvas == null || this.m_rect == null || this.m_frameBuffer == null)) {
                    this.m_frameBuffer.position(0);
                    $r1.position(0);
                    if (this.m_rect.left() == 0 && this.m_rect.top() == 0 && this.m_rect.height() == $i1 && this.m_bitmap.getRowBytes() == $i2) {
                        $r1.put(this.m_frameBuffer);
                    } else {
                        WLRect $r5 = new WLRect(0, 0, $i0, $i1);
                        WLRect wLRect = this.m_rect;
                        WLRect $r8 = wLRect;
                        $r5 = $r5.intersected(wLRect);
                        if (!$r5.isEmpty()) {
                            ByteBuffer $r2 = this.m_frameBuffer;
                            wLRect = this.m_rect;
                            int $i4 = wLRect.left();
                            wLRect = this.m_rect;
                            int $i6 = wLRect.top();
                            wLRect = this.m_rect;
                            int $i7 = wLRect.width();
                            wLRect = this.m_rect;
                            WLImageUtils.copyImage($r1, $i0, $i1, $i2, $r2, $i4, $i6, $i7, wLRect.height(), $r5.left(), $r5.top(), $r5.width(), $r5.height());
                        }
                    }
                }
            }
            if (!this.m_drawing) {
                long $l3 = Math.max(10, 10 - (SystemClock.uptimeMillis() - this.m_drawFinishTime));
                this.m_drawing = true;
                this.m_uiHandler.postDelayed(new C04033(), $l3);
            }
        }
    }

    private static Window getWindow(View $r0) throws  {
        Context $r1 = $r0.getContext();
        Context $r2 = $r1;
        if ($r1 instanceof Activity) {
            return ((Activity) $r1).getWindow();
        }
        if ($r1 instanceof ContextWrapper) {
            $r2 = ((ContextWrapper) $r1).getBaseContext();
        }
        return $r2 instanceof Activity ? ((Activity) $r2).getWindow() : null;
    }

    public boolean canRender() throws  {
        return (this.m_canvas == null || this.m_drawing) ? false : true;
    }

    public boolean onMouseEvent(MotionEvent $r1) throws  {
        processMotionEvent($r1);
        return true;
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        processMotionEvent($r1);
        return true;
    }

    private void processMotionEvent(MotionEvent $r1) throws  {
        ArrayList $r4 = WLTreeViewObserver.getInstance().getRootViews();
        if (!$r4.isEmpty()) {
            final View $r6 = (View) $r4.get($r4.size() - 1);
            int[] $r2 = new int[2];
            $r6.getLocationOnScreen($r2);
            $r1.setLocation(($r1.getX() * this.m_scaleX) - ((float) $r2[0]), ($r1.getY() * this.m_scaleY) - ((float) $r2[1]));
            $r1 = MotionEvent.obtain($r1);
            this.m_uiHandler.post(new Runnable() {
                public void run() throws  {
                    $r6.dispatchTouchEvent($r1);
                    $r1.recycle();
                }
            });
        }
    }

    public void onKeyEvent(final KeyEvent $r1) throws  {
        this.m_uiHandler.post(new Runnable() {
            public void run() throws  {
                ArrayList $r2 = WLTreeViewObserver.getInstance().getRootViews();
                if (!$r2.isEmpty()) {
                    ((View) $r2.get($r2.size() - 1)).dispatchKeyEvent($r1);
                }
            }
        });
    }

    public void onKeyboardShown() throws  {
    }

    public void onKeyboardHidden() throws  {
    }

    public void onChildViewAdded(View parent, View $r2) throws  {
        if (WLServer.getInstance().getExtendedLoggingEnabled()) {
            Log.i(TAG, "Added view " + $r2);
        }
        if (this.m_nonCacheViewClasses.contains($r2.getClass())) {
            final View view = $r2;
            this.m_uiHandler.post(new Runnable() {
                public void run() throws  {
                    try {
                        synchronized (WLMirrorLayer.this.m_nonCacheViewInfoMap) {
                            WLMirrorLayer.this.m_nonCacheViewInfoMap.put(view, new NonCacheViewInfo(view.isDrawingCacheEnabled(), view.getLayerType()));
                        }
                        view.setDrawingCacheEnabled(false);
                        view.setLayerType(1, null);
                    } catch (Exception e) {
                    }
                }
            });
        }
        boolean $z0 = $r2 instanceof IWLCustomDraw;
        IViewHandler $r10 = WLServer.getInstance().getViewHandler($r2);
        IViewHandler $r11 = $r10;
        if ($r10 == null && !$z0) {
            if ($r2 instanceof TextureView) {
                $r11 = r19;
                TextureViewHandler r19 = new TextureViewHandler((TextureView) $r2);
            } else if ($r2 instanceof GLSurfaceView) {
                $r11 = r0;
                GLSurfaceViewHandler gLSurfaceViewHandler = new GLSurfaceViewHandler((GLSurfaceView) $r2);
            } else if ($r2 instanceof SurfaceView) {
                $r11 = r0;
                SurfaceViewHandler surfaceViewHandler = new SurfaceViewHandler((SurfaceView) $r2);
            } else if ($r2 instanceof IWLNotDrawable) {
                $r11 = r0;
                NotDrawableViewHandler notDrawableViewHandler = new NotDrawableViewHandler((IWLNotDrawable) $r2);
            }
        }
        if ($r11 != null) {
            $r11.attach();
            ConcurrentHashMap concurrentHashMap = this.m_specialViews;
            ConcurrentHashMap $r13 = concurrentHashMap;
            concurrentHashMap.put($r2, $r11);
        }
    }

    public synchronized void onChildViewRemoved(View parent, View $r2) throws  {
        if (WLServer.getInstance().getExtendedLoggingEnabled()) {
            Log.i(TAG, "Removed view " + $r2);
        }
        synchronized (this.m_nonCacheViewInfoMap) {
            final NonCacheViewInfo $r9 = (NonCacheViewInfo) this.m_nonCacheViewInfoMap.get($r2);
            if ($r9 != null) {
                this.m_nonCacheViewInfoMap.remove($r2);
            }
        }
        if ($r9 != null) {
            final View view = $r2;
            this.m_uiHandler.post(new Runnable() {
                public void run() throws  {
                    try {
                        view.setDrawingCacheEnabled($r9.m_drawingCacheEnabled);
                        view.setLayerType($r9.m_layerType, null);
                    } catch (Exception e) {
                    }
                }
            });
        }
        if (($r2 instanceof SurfaceView) || ($r2 instanceof TextureView)) {
            IViewHandler $r13 = (IViewHandler) this.m_specialViews.remove($r2);
            if ($r13 != null) {
                $r13.detach();
            }
        }
    }

    private void logExtendedHierarchy(String $r1) throws  {
        if (this.m_extendedLoggingEnabled) {
            Log.d(TAG, m_hierarchyIndents[this.m_hierarchyLevel < 100 ? this.m_hierarchyLevel : 99] + $r1);
        }
    }

    private void drawViewHierarchy(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1) throws  {
        long $l1 = 0;
        HashSet $r2 = prepareSpecialDraw($r1);
        this.m_extendedLoggingEnabled = WLServer.getInstance().getExtendedLoggingEnabled();
        if (SystemClock.uptimeMillis() - this.m_lastLoggingTime < ((long) WLServer.getInstance().getExtendedLoggingPeriod())) {
            this.m_extendedLoggingEnabled = false;
        }
        this.m_hierarchyLevel = 0;
        if (this.m_extendedLoggingEnabled) {
            $l1 = SystemClock.currentThreadTimeMillis();
            Log.i(TAG, "================================");
            Log.i(TAG, "Starting view hierarchy drawing.");
        }
        if ($r2 != null) {
            drawViewsSpecial($r1, $r2);
        } else {
            drawViews($r1);
        }
        if (this.m_extendedLoggingEnabled) {
            long $l0 = SystemClock.currentThreadTimeMillis();
            Log.i(TAG, "Draw time: " + ($l0 - $l1) + " ms");
            this.m_lastLoggingTime = SystemClock.uptimeMillis();
        }
    }

    private void drawViewsSpecial(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;)V"}) HashSet<View> $r2) throws  {
        Iterator $r3 = $r1.iterator();
        while ($r3.hasNext()) {
            drawViewSpecial((View) $r3.next(), $r2, 1.0f, 1.0f, true);
        }
    }

    private void checkDimViews(View $r1) throws  {
        LayoutParams $r2 = $r1.getLayoutParams();
        if ($r2 instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams $r3 = (WindowManager.LayoutParams) $r2;
            float $f0 = $r3.dimAmount;
            if (($r3.flags & 2) == 2 && $f0 > 0.0f) {
                dimCanvas($f0);
            }
        }
    }

    private void dimCanvas(float $f0) throws  {
        int $i0 = (int) (((double) (255.0f * $f0)) + 0.5d);
        if ($i0 > 255) {
            $i0 = 255;
        } else if ($i0 < 0) {
            $i0 = 0;
        }
        this.m_canvas.drawColor(Color.argb($i0, 0, 0, 0));
        logExtendedHierarchy("Dimmed canvas with alpha: " + $i0);
    }

    private void drawViews(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1) throws  {
        Iterator $r2 = $r1.iterator();
        while ($r2.hasNext()) {
            drawView((View) $r2.next(), 1.0f, 1.0f, true);
        }
    }

    private void drawViewSpecial(@Signature({"(", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) View $r1, @Signature({"(", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) HashSet<View> $r2, @Signature({"(", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) float $f0, @Signature({"(", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) float $f1, @Signature({"(", "Landroid/view/View;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)V"}) boolean $z0) throws  {
        if ($r2.contains($r1)) {
            float $f4 = $r1.getScaleX();
            float $f5 = $r1.getScaleY();
            drawSingleView($r1, $f0, $f1, $z0);
            if (($r1 instanceof ViewGroup) && !($r1 instanceof IWLNotDrawable)) {
                this.m_hierarchyLevel++;
                ViewGroup $r3 = (ViewGroup) $r1;
                int $i0 = $r3.getChildCount();
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    drawViewSpecial($r3.getChildAt($i1), $r2, $f0 * $f4, $f1 * $f5, false);
                }
                this.m_hierarchyLevel--;
                return;
            }
            return;
        }
        drawView($r1, $f0, $f1, $z0);
    }

    private void drawSingleView(View $r1, float $f0, float $f1, boolean $z0) throws  {
        if (!$r1.isShown() || ($r1 instanceof IWLNotDrawable)) {
            logExtendedHierarchy("Skipping view: view.isShown()==false  OR  view instanceof IWLNotDrawable: " + $r1.toString());
        } else if (($r1 instanceof TextureView) || ($r1 instanceof SurfaceView)) {
            IViewHandler $r4 = (IViewHandler) this.m_specialViews.get($r1);
            if ($r4 != null) {
                logExtendedHierarchy("Draw special view type: " + $r1.toString());
                try {
                    $r4.draw(this.m_canvas, this.m_scaleX, this.m_scaleY, $f0, $f1);
                    return;
                } catch (Throwable $r8) {
                    logExtendedHierarchy("====> Drawing FAILED: " + $r8.toString());
                    logExtendedHierarchy("====> Drawing FAILED: " + Log.getStackTraceString($r8));
                    MCSLogger.log(TAG, "Drawing failed", $r8);
                    return;
                }
            }
            logExtendedHierarchy("Trying to draw missing view type: " + $r1.toString());
            MCSLogger.log(TAG, "Trying to draw missing view. Please check!");
        } else if ($r1.willNotDraw() && $r1.getBackground() == null) {
            logExtendedHierarchy("Skipping view: view.willNotDraw()==true  AND  view.getBackground()==null : " + $r1.toString());
        } else if ($r1 instanceof ViewGroup) {
            ViewGroup $r10 = (ViewGroup) $r1;
            int $i0 = $r10.getChildCount();
            if ($i0 <= 0 || ms_fieldChildrenCount == null) {
                MCSLogger.log(TAG, "Normaly we shall not get here. Please check");
                logExtendedHierarchy("Normaly we shall not get here. Please check count==" + $i0 + " : " + $r1.toString());
                drawView($r1, $f0, $f1, $z0);
                return;
            }
            try {
                ms_fieldChildrenCount.setInt($r10, 0);
                logExtendedHierarchy("Drawing only view background: " + $r1.toString());
                drawView($r1, $f0, $f1, $z0);
                ms_fieldChildrenCount.setInt($r10, $i0);
            } catch (Throwable $r12) {
                MCSLogger.log(TAG, "Drawing error", $r12);
            }
        } else {
            MCSLogger.log(TAG, "Normaly we shall not get here. Please check");
            logExtendedHierarchy("Normaly we shall not get here. Please check: " + $r1.toString());
            drawView($r1, $f0, $f1, $z0);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.HashSet<android.view.View> prepareSpecialDraw(@dalvik.annotation.Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;"}) java.util.ArrayList<android.view.View> r15) throws  {
        /*
        r14 = this;
        monitor-enter(r14);
        r0 = r14.m_specialViews;	 Catch:{ Throwable -> 0x0062 }
        r1 = r0.keySet();	 Catch:{ Throwable -> 0x0062 }
        r2 = r1.iterator();	 Catch:{ Throwable -> 0x0062 }
        r3 = 0;
    L_0x000c:
        r4 = r2.hasNext();	 Catch:{ Throwable -> 0x0065 }
        if (r4 == 0) goto L_0x0041;
    L_0x0012:
        r5 = r2.next();	 Catch:{ Throwable -> 0x0065 }
        r7 = r5;
        r7 = (android.view.View) r7;	 Catch:{ Throwable -> 0x0065 }
        r6 = r7;
        r4 = r6.isShown();	 Catch:{ Throwable -> 0x0065 }
        if (r4 == 0) goto L_0x0069;
    L_0x0020:
        if (r3 != 0) goto L_0x0068;
    L_0x0022:
        r3 = new java.util.HashSet;	 Catch:{ Throwable -> 0x0065 }
        r3.<init>();	 Catch:{ Throwable -> 0x0065 }
    L_0x0027:
        r8 = r6.getParent();	 Catch:{ Throwable -> 0x0062 }
    L_0x002b:
        if (r8 == 0) goto L_0x003d;
    L_0x002d:
        r4 = r8 instanceof android.view.View;	 Catch:{ Throwable -> 0x0062 }
        if (r4 == 0) goto L_0x003d;
    L_0x0031:
        r10 = r8;
        r10 = (android.view.View) r10;	 Catch:{ Throwable -> 0x0062 }
        r9 = r10;
        r3.add(r9);	 Catch:{ Throwable -> 0x0062 }
        r8 = r8.getParent();	 Catch:{ Throwable -> 0x0062 }
        goto L_0x002b;
    L_0x003d:
        r3.add(r6);	 Catch:{ Throwable -> 0x0062 }
    L_0x0040:
        goto L_0x000c;
    L_0x0041:
        if (r3 == 0) goto L_0x0067;
    L_0x0043:
        r4 = 0;
        r2 = r15.iterator();	 Catch:{ Throwable -> 0x0065 }
    L_0x0048:
        r11 = r2.hasNext();	 Catch:{ Throwable -> 0x0065 }
        if (r11 == 0) goto L_0x005d;
    L_0x004e:
        r5 = r2.next();	 Catch:{ Throwable -> 0x0065 }
        r12 = r5;
        r12 = (android.view.View) r12;	 Catch:{ Throwable -> 0x0065 }
        r6 = r12;
        r11 = r3.contains(r6);	 Catch:{ Throwable -> 0x0065 }
        if (r11 == 0) goto L_0x0048;
    L_0x005c:
        r4 = 1;
    L_0x005d:
        if (r4 != 0) goto L_0x0067;
    L_0x005f:
        r3 = 0;
    L_0x0060:
        monitor-exit(r14);
        return r3;
    L_0x0062:
        r13 = move-exception;
    L_0x0063:
        monitor-exit(r14);
        throw r13;
    L_0x0065:
        r13 = move-exception;
        goto L_0x0063;
    L_0x0067:
        goto L_0x0060;
    L_0x0068:
        goto L_0x0027;
    L_0x0069:
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLMirrorLayer.prepareSpecialDraw(java.util.ArrayList):java.util.HashSet<android.view.View>");
    }

    private static void calcClipRect(View $r1, RectF $r0) throws  {
        int $i0 = $r1.getLeft();
        int $i1 = $r1.getTop();
        $r0.set((float) $i0, (float) $i1, (float) ($i0 + $r1.getWidth()), (float) ($i1 + $r1.getHeight()));
        $r1 = $r1.getParent() instanceof View ? (View) $r1.getParent() : null;
        while ($r1 != null) {
            float $f1 = $r1.getScaleX();
            float $f0 = $r1.getScaleX();
            $i0 = $r1.getLeft();
            $i1 = $r1.getTop();
            int $i2 = $r1.getWidth();
            int $i3 = $r1.getHeight();
            $r0.left *= $f1;
            $r0.top *= $f0;
            $r0.right *= $f1;
            $r0.bottom *= $f0;
            $r0.offset((float) $i0, (float) $i1);
            $r0.intersect((float) $i0, (float) $i1, (float) ($i0 + $i2), (float) ($i1 + $i3));
            $r1 = $r1.getParent() instanceof View ? (View) $r1.getParent() : null;
        }
    }

    public boolean onGoBack() throws  {
        ArrayList $r4 = WLTreeViewObserver.getInstance().getRootViews();
        if ($r4.isEmpty()) {
            return false;
        }
        final KeyEvent $r1 = new KeyEvent(0, 4);
        final KeyEvent $r2 = new KeyEvent(1, 4);
        final View $r6 = (View) $r4.get($r4.size() - 1);
        this.m_uiHandler.post(new Runnable() {
            public void run() throws  {
                if ($r6.getContext() instanceof Activity) {
                    Activity $r3 = (Activity) $r6.getContext();
                    $r3.dispatchKeyEvent($r1);
                    $r3.dispatchKeyEvent($r2);
                    return;
                }
                $r6.dispatchKeyEvent($r1);
                $r6.dispatchKeyEvent($r2);
            }
        });
        return false;
    }

    public int getBackgroundColor() throws  {
        return this.m_backgroundColor;
    }

    public void setBackgroundColor(int $i0) throws  {
        this.m_backgroundColor = $i0;
    }
}
