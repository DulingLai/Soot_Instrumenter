package com.waze.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Process;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.map.CanvasFont.TextMetrics;
import com.waze.map.FlushRequestQueue.FlushRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public final class NativeCanvasRenderer implements Renderer {
    public static final int CORDING_POINTS_MAX_COUNT = 8;
    public static final int INITIAL_CANVAS_BG_COLOR = -657158;
    private static final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    private final long FRAME_TIME = 17;
    private final String LOG_TAG = "WAZE NTV RENDERER";
    private final int MAX_TOUCH_COUNT = 3;
    private final long MIN_IDLE_TIME = 2;
    private int draw_count = 0;
    private volatile boolean mCanvasCreated = false;
    private String mCanvasTag;
    private volatile boolean mCreateRequest = false;
    private volatile boolean mDestroyRequest = false;
    private final Runnable mDrawEvent = new C18676();
    private volatile UISurfaceEvent mEvent = null;
    private final FlushRequestQueue mFlushRequests = new FlushRequestQueue();
    private CanvasFont mFontHelper = CanvasFont.instance();
    private CanvasView mGLView;
    private int mHeight = -1;
    private int mWidth = -1;
    private int render_count = 0;
    int request_render_count = 0;

    class C18621 extends RunnableExecutor {
        C18621() throws  {
        }

        public void event() throws  {
            NativeCanvasRenderer.this.CreateNTV(NativeCanvasRenderer.this.mCanvasTag);
        }
    }

    class C18654 extends RunnableExecutor {
        C18654() throws  {
        }

        public void event() throws  {
            Log.d("WAZE NTV RENDERER", "Going to run destroy event. Created: " + NativeCanvasRenderer.this.mCanvasCreated);
            if (NativeCanvasRenderer.this.mCanvasCreated) {
                NativeCanvasRenderer.this.DestroySurfaceNTV();
                NativeCanvasRenderer.this.mCanvasCreated = false;
            }
            NativeCanvasRenderer.this.notifyDestroy();
            Log.d("WAZE NTV RENDERER", "Finished to run destroy event");
        }
    }

    class C18665 implements Runnable {
        C18665() throws  {
        }

        public void run() throws  {
            NativeCanvasRenderer.this.onFlush();
        }
    }

    class C18676 implements Runnable {
        private long nextFrameTime = -1;

        C18676() throws  {
        }

        public void run() throws  {
            if (NativeCanvasRenderer.this.mCanvasCreated && !NativeManager.isShuttingDown()) {
                if (this.nextFrameTime == -1) {
                    this.nextFrameTime = SystemClock.uptimeMillis();
                }
                NativeCanvasRenderer.this.DrawNTV();
                this.nextFrameTime += 17;
                long $l0 = SystemClock.uptimeMillis() + 2;
                if (this.nextFrameTime < $l0) {
                    this.nextFrameTime = $l0;
                }
                NativeManager.getInstance().PostRunnableAtTime(this, this.nextFrameTime);
            }
        }
    }

    static class C18698 implements Runnable {
        C18698() throws  {
        }

        public void run() throws  {
            NativeCanvasRenderer.OnViewOverlayShownNTV(AppService.getAppContext().getString(C1283R.string.nativeTagMainCanvas));
        }
    }

    static class C18709 implements Runnable {
        C18709() throws  {
        }

        public void run() throws  {
            NativeCanvasRenderer.OnViewOverlayHiddenNTV(AppService.getAppContext().getString(C1283R.string.nativeTagMainCanvas));
        }
    }

    static class NativeFontData {
        public byte[] mImage;
        TextMetrics mMetrics;

        NativeFontData() throws  {
        }
    }

    private static class UISurfaceEvent {
        private static int mFrameId = 0;
        private Type mType = Type.DESTROYED;

        public enum Type {
            CREATED,
            CHANGED,
            DESTROYED
        }

        public UISurfaceEvent(Type $r1) throws  {
            this.mType = $r1;
            mFrameId++;
        }

        public String toString() throws  {
            return " UISurfaceEvent. Type: " + this.mType + ". Frame id: " + frameId() + " ";
        }

        public synchronized Type type() throws  {
            return this.mType;
        }

        public synchronized void set_type(Type $r1) throws  {
            this.mType = $r1;
        }

        public synchronized boolean isDestroyed() throws  {
            return this.mType == Type.DESTROYED;
        }

        public synchronized boolean isCreated() throws  {
            return this.mType == Type.CREATED;
        }

        public int frameId() throws  {
            return mFrameId;
        }
    }

    private native void CreateNTV(String str) throws ;

    private native void CreateSurfaceNTV(String str, int i, int i2) throws ;

    private native void DestroyNTV() throws ;

    private native void DestroySurfaceNTV() throws ;

    private native void DrawNTV() throws ;

    private native void FlushNTV(int i, int i2) throws ;

    private native void KeyDownHandlerNTV(int i, boolean z, byte[] bArr) throws ;

    private native void OnTouchCancelNTV(int[] iArr) throws ;

    private native void OnTouchMovedNTV(int[] iArr) throws ;

    private native void OnTouchPressedNTV(int[] iArr) throws ;

    private native void OnTouchReleasedNTV(int[] iArr) throws ;

    private native void OnTouchTap3NTV(int[] iArr) throws ;

    private static native void OnViewOverlayHiddenNTV(String str) throws ;

    private static native void OnViewOverlayShownNTV(String str) throws ;

    private native void RenderNTV(int i, int i2) throws ;

    private native void ResizeSurfaceNTV(int i, int i2) throws ;

    static {
        ((WindowManager) AppService.getAppContext().getSystemService("window")).getDefaultDisplay().getMetrics(mDisplayMetrics);
    }

    public NativeCanvasRenderer(String $r1, CanvasView $r2) throws  {
        this.mCanvasTag = $r1;
        this.mGLView = $r2;
        queueNative(new C18621());
    }

    public void onViewSurfaceCreated() throws  {
        if (this.mEvent == null || !this.mEvent.isCreated()) {
            Log.d("WAZE NTV RENDERER", "onViewSurfaceCreated [ " + this.mCanvasTag + " ].");
            this.mEvent = new UISurfaceEvent(Type.CREATED);
            return;
        }
        Log.d("WAZE NTV RENDERER", "onViewSurfaceCreated - already created. " + this.mEvent);
    }

    public void onViewSurfaceChanged() throws  {
        Log.d("WAZE NTV RENDERER", "onViewSurfaceChanged [ " + this.mCanvasTag + " ].");
        this.mEvent.set_type(Type.CHANGED);
    }

    public void onSurfaceCreated(GL10 $r1, EGLConfig var2) throws  {
        Log.d("WAZE NTV RENDERER", "onSurfaceCreated [ " + this.mCanvasTag + " ]. " + this.mEvent);
        this.mHeight = -1;
        this.mWidth = -1;
        Process.setThreadPriority(-4);
        Clear($r1);
    }

    public void onSurfaceChanged(GL10 gl, final int $i0, final int $i1) throws  {
        if (this.mWidth != $i0 || this.mHeight != $i1) {
            Log.d("WAZE NTV RENDERER", "onSurfaceChanged [ " + this.mCanvasTag + " ] :" + $i0 + ", " + $i1 + this.mEvent);
            C18632 $r2 = new RunnableExecutor() {
                final UISurfaceEvent uiEvent = NativeCanvasRenderer.this.mEvent;

                public void event() throws  {
                    if (this.uiEvent != NativeCanvasRenderer.this.mEvent || NativeCanvasRenderer.this.mEvent.isDestroyed()) {
                        Log.d("WAZE NTV RENDERER", "surfaceCreatedEvent dropped - out of events frame or already destroyed. Posted: " + this.uiEvent + " Current: " + NativeCanvasRenderer.this.mEvent);
                        return;
                    }
                    NativeCanvasRenderer.this.CreateSurfaceNTV(NativeCanvasRenderer.this.mCanvasTag, $i0, $i1);
                    NativeCanvasRenderer.this.mCanvasCreated = true;
                    NativeCanvasRenderer.this.startDrawTimer();
                    NativeCanvasRenderer.this.mGLView.onNativeCanvasReady();
                    Log.d("WAZE NTV RENDERER", "surfaceCreatedEvent execution finished");
                }
            };
            C18643 $r3 = new RunnableExecutor() {
                final UISurfaceEvent uiEvent = NativeCanvasRenderer.this.mEvent;

                public void event() throws  {
                    if (this.uiEvent != NativeCanvasRenderer.this.mEvent || NativeCanvasRenderer.this.mEvent.isDestroyed()) {
                        Log.d("WAZE NTV RENDERER", "surfaceResizedEvent dropped - out of events frame or already destroyed. Posted: " + this.uiEvent + " Current: " + NativeCanvasRenderer.this.mEvent);
                        return;
                    }
                    NativeCanvasRenderer.this.ResizeSurfaceNTV($i0, $i1);
                    Log.d("WAZE NTV RENDERER", "surfaceResizedEvent execution finished");
                }
            };
            if (this.mWidth == -1 || this.mHeight == -1) {
                this.mFlushRequests.clear();
                queueNative($r2);
            } else {
                queueNative($r3);
            }
            this.mWidth = $i0;
            this.mHeight = $i1;
        }
    }

    public void onDrawFrame(GL10 gl) throws  {
        Pair $r2 = popFlushRequests();
        if ($r2.second != null) {
            handleNextFlush((FlushRequest) $r2.second);
        }
        if ($r2.first != null) {
            RenderNTV(((FlushRequest) $r2.first).queue_id, ((FlushRequest) $r2.first).frame_id);
        }
    }

    public void onViewSurfaceDestroyed() throws  {
        Log.d("WAZE NTV RENDERER", "onViewSurfaceDestroyed: [ " + this.mCanvasTag + " ]" + ". Event: " + this.mEvent + ". Shutting down: " + NativeManager.isShuttingDown() + this.mEvent);
        if (!NativeManager.isShuttingDown() && this.mEvent != null && !this.mEvent.isDestroyed()) {
            this.mEvent.set_type(Type.DESTROYED);
            stopDrawTimer();
            this.mDestroyRequest = true;
            queueNative(new C18654());
            waitDestroy();
            this.mFlushRequests.clear();
            Log.d("WAZE NTV RENDERER", "Finished onViewSurfaceDestroyed");
        }
    }

    private void Clear(GL10 $r1) throws  {
        $r1.glClearColor((float) Color.red(INITIAL_CANVAS_BG_COLOR), (float) Color.green(INITIAL_CANVAS_BG_COLOR), (float) Color.blue(INITIAL_CANVAS_BG_COLOR), (float) Color.alpha(INITIAL_CANVAS_BG_COLOR));
        $r1.glClear(16384);
    }

    private void handleNextFlush(FlushRequest $r1) throws  {
        if ($r1.is_render) {
            this.mGLView.requestRender();
        } else {
            queueFlushEvent();
        }
        $r1.request_posted = true;
    }

    private void onFlush() throws  {
        Pair $r1 = popFlushRequests();
        if ($r1.second != null) {
            handleNextFlush((FlushRequest) $r1.second);
        }
        if ($r1.first != null) {
            FlushNTV(((FlushRequest) $r1.first).queue_id, ((FlushRequest) $r1.first).frame_id);
        }
    }

    Pair<FlushRequest, FlushRequest> popFlushRequests() throws  {
        FlushRequest $r4;
        FlushRequest $r2 = null;
        synchronized (this.mFlushRequests) {
            $r4 = this.mFlushRequests.pop();
            if (!(this.mFlushRequests.head() == null || this.mFlushRequests.head().request_posted)) {
                $r2 = this.mFlushRequests.head();
            }
        }
        return Pair.create($r4, $r2);
    }

    private boolean requestRender(int $i0, int $i1) throws  {
        requestFlushHandler($i0, $i1, true);
        return true;
    }

    private boolean requestFlush(int $i0, int $i1) throws  {
        requestFlushHandler($i0, $i1, false);
        return true;
    }

    private FlushRequest requestFlushHandler(int $i0, int $i1, boolean $z0) throws  {
        FlushRequest $r1 = new FlushRequest($i0, $i1, true, $z0);
        synchronized (this.mFlushRequests) {
            if (this.mFlushRequests.isEmpty()) {
                this.mFlushRequests.push($r1);
                if ($z0) {
                    this.mGLView.requestRender();
                } else {
                    queueFlushEvent();
                }
            } else {
                $r1.request_posted = false;
                this.mFlushRequests.push($r1);
            }
        }
        return $r1;
    }

    private synchronized void notifyDestroy() throws  {
        if (this.mDestroyRequest) {
            this.mDestroyRequest = false;
            notifyAll();
        }
    }

    private synchronized void waitDestroy() throws  {
        while (this.mDestroyRequest) {
            try {
                wait();
            } catch (Exception $r1) {
                Logger.ee(Logger.TAG, $r1);
            }
        }
    }

    private synchronized void notifyCreate() throws  {
        if (this.mCreateRequest) {
            this.mCreateRequest = false;
            notifyAll();
        }
    }

    private synchronized void waitCreate() throws  {
        while (this.mCreateRequest) {
            try {
                wait();
            } catch (Exception $r1) {
                Logger.ee(Logger.TAG, $r1);
            }
        }
    }

    public static int displayDpi() throws  {
        return mDisplayMetrics.densityDpi;
    }

    private void queueFlushEvent() throws  {
        this.mGLView.queueEvent(new C18665());
    }

    private void queueNative(RunnableExecutor $r1) throws  {
        if (NativeManager.IsAppStarted()) {
            NativeManager.Post($r1);
        } else {
            NativeManager.registerOnAppStartedEvent($r1);
        }
    }

    private void startDrawTimer() throws  {
        NativeManager.getInstance().PostPriorityEvent(this.mDrawEvent);
    }

    private void stopDrawTimer() throws  {
        NativeManager.getInstance().RemoveRunnable(this.mDrawEvent);
    }

    public void onTouchEvent(final MotionEvent $r1) throws  {
        NativeManager $r3 = NativeManager.getInstance();
        if ($r3 != null) {
            $r3.PostPriorityEvent(new Runnable() {
                public void run() throws  {
                    NativeCanvasRenderer.this.onTouchEventHandler($r1);
                }
            });
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean onTouchEventHandler(android.view.MotionEvent r9) throws  {
        /*
        r8 = this;
        r0 = r9.getAction();
        r0 = r0 & 255;
        r1 = r9.getPointerCount();
        r2 = 3;
        if (r1 <= r2) goto L_0x001f;
    L_0x000d:
        r1 = 3;
    L_0x000e:
        r3 = r1 * 2;
        r4 = new int[r3];
        r3 = 0;
    L_0x0013:
        if (r3 >= r1) goto L_0x0019;
    L_0x0015:
        r2 = 8;
        if (r3 != r2) goto L_0x0024;
    L_0x0019:
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x004b;
            case 2: goto L_0x004f;
            case 3: goto L_0x0053;
            case 4: goto L_0x001d;
            case 5: goto L_0x003b;
            case 6: goto L_0x004b;
            default: goto L_0x001c;
        };
    L_0x001c:
        goto L_0x001d;
    L_0x001d:
        r2 = 0;
        return r2;
    L_0x001f:
        r1 = r9.getPointerCount();
        goto L_0x000e;
    L_0x0024:
        r5 = r3 * 2;
        r6 = r9.getX(r3);
        r7 = (int) r6;
        r4[r5] = r7;
        r5 = r3 * 2;
        r5 = r5 + 1;
        r6 = r9.getY(r3);
        r7 = (int) r6;
        r4[r5] = r7;
        r3 = r3 + 1;
        goto L_0x0013;
    L_0x003b:
        r2 = 3;
        if (r1 != r2) goto L_0x0043;
    L_0x003e:
        r8.OnTouchTap3NTV(r4);
    L_0x0041:
        r2 = 1;
        return r2;
    L_0x0043:
        r8.OnTouchPressedNTV(r4);
        goto L_0x0041;
    L_0x0047:
        r8.OnTouchPressedNTV(r4);
        goto L_0x0041;
    L_0x004b:
        r8.OnTouchReleasedNTV(r4);
        goto L_0x0041;
    L_0x004f:
        r8.OnTouchMovedNTV(r4);
        goto L_0x0041;
    L_0x0053:
        r8.OnTouchCancelNTV(r4);
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.map.NativeCanvasRenderer.onTouchEventHandler(android.view.MotionEvent):boolean");
    }

    public static void OnMainCanvasOverlayShown() throws  {
        NativeManager.Post(new C18698());
    }

    public static void OnMainCanvasOverlayHidden() throws  {
        NativeManager.Post(new C18709());
    }

    public void onKeyDownEvent(final KeyEvent $r1) throws  {
        NativeManager $r3 = NativeManager.getInstance();
        if ($r3 != null) {
            $r3.PostPriorityEvent(new Runnable() {
                public void run() throws  {
                    NativeCanvasRenderer.this.onKeyDownHandler($r1);
                }
            });
        }
    }

    private boolean onKeyDownHandler(KeyEvent $r1) throws  {
        boolean $z0;
        int $i0 = $r1.getKeyCode();
        int $i1 = $r1.getUnicodeChar();
        String $r5 = $r1.getCharacters();
        byte[] $r4 = new byte[64];
        if ($i1 == 0 && $r5 == null) {
            $z0 = true;
        } else {
            int[] $r2 = new int[1];
            $z0 = false;
            if ($i1 != 0) {
                $r2[0] = $i1;
                $r5 = new String($r2, 0, 1);
            }
            try {
                byte[] $r6 = $r5.getBytes("UTF-8");
                $i1 = 0;
                while ($i1 < $r6.length && $i1 < 64) {
                    $r4[$i1] = $r6[$i1];
                    $i1++;
                }
            } catch (UnsupportedEncodingException $r3) {
                Logger.m39e("The conversion to the unicode cannot be performed for " + $r5, $r3);
            }
        }
        MainActivity $r8;
        if ($i0 == 84) {
            $r8 = AppService.getMainActivity();
            if ($r8 != null) {
                final MainActivity mainActivity = $r8;
                $r8.post(new Runnable() {
                    public void run() throws  {
                        mainActivity.startNavigateActivity();
                    }
                });
            }
            return true;
        }
        if ($i0 == 4) {
            $r8 = AppService.getMainActivity();
            if ($r8 != null) {
                LayoutManager $r10 = $r8.getLayoutMgr();
                if ($r10 != null && (($r10.isPopupsShown() && !$r10.isCarpoolTickerVisible()) || $r10.canGoBack() || $r10.isSwipeableLayoutOpened() || $r10.isShowingCarGasSettings() || $r10.isShowingSearchOnMapResults())) {
                    final LayoutManager layoutManager = $r10;
                    AppService.Post(new Runnable() {
                        public void run() throws  {
                            layoutManager.onBackPressed();
                        }
                    });
                    return true;
                }
            }
            MapViewWrapper $r12 = AppService.getActiveMapViewWrapper();
            if ($r12 != null && $r12.hidePopupIfShown()) {
                return true;
            }
        }
        KeyDownHandlerNTV($i0, $z0, $r4);
        return true;
    }

    private NativeFontData getFontImage(String $r1, int $i0, boolean $z0, int $i1) throws  {
        TextMetrics $r3 = new TextMetrics();
        Bitmap $r5 = this.mFontHelper.getFontImage($r1, $i0, $z0, $i1, Config.ALPHA_8, $r3);
        if ($r5 == null) {
            return null;
        }
        NativeFontData $r6 = new NativeFontData();
        $r6.mMetrics = $r3;
        $r6.mImage = new byte[$r5.getByteCount()];
        $r5.copyPixelsToBuffer(ByteBuffer.wrap($r6.mImage));
        $r5.recycle();
        return $r6;
    }

    private TextMetrics getTextMetrics(String $r1, int $i0, boolean $z0, int $i1) throws  {
        return this.mFontHelper.getTextMetrics($r1, $i0, $z0, $i1);
    }

    public static Bitmap GetSplashBmp(View aView) throws  {
        InputStream $r3 = null;
        try {
            $r3 = ResManager.LoadSkinStream(ResManager.GetSplashName(false));
        } catch (Exception $r1) {
            Log.w(Logger.TAG, "Error loading splash screen! " + $r1.getMessage());
            $r1.printStackTrace();
        }
        if ($r3 == null) {
            return null;
        }
        Options $r2 = new Options();
        $r2.inPreferredConfig = Config.ARGB_8888;
        return BitmapFactory.decodeStream($r3, null, $r2);
    }
}
