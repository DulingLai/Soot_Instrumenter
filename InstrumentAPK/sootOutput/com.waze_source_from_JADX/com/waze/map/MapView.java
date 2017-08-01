package com.waze.map;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView;
import com.facebook.internal.NativeProtocol;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.WazeApplication;
import com.waze.ifs.async.RunnableExecutor;
import java.util.Arrays;

public final class MapView extends WLGLSurfaceView implements CanvasView {
    public static int GPU_NOT_SUPPORTED = 0;
    public static int GPU_SUPPORTED = 1;
    public static int GPU_UNDEFINED = -1;
    private static final int MODIFIER_STATE_CLICK = 1;
    private static final int MODIFIER_STATE_DOUBLE_CLICK = 2;
    private static final int MODIFIER_STATE_NONE = 0;
    private static final float MOTION_RESOLUTION_VAL = 0.5f;
    private static final int[] mUnhandledKeys = new int[]{24, 25, 62, 90, 87, 85, 88, 89, 86, 127, 126};
    private static final int[] mUnhandledKeysMetaMask = new int[]{0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean handleKeys;
    private boolean handleTouch;
    private int mImeAction = 0;
    private boolean mImeCloseOnAction = false;
    private volatile boolean mIsRunning = false;
    private volatile boolean mIsSurfaceReady = true;
    private KeyEvent mLastEvent = null;
    private MotionEvent mLastHandledEvent;
    private int mModifierKeyCode = -1;
    private int mModifierState = 0;
    private NativeCanvasRenderer mNativeRenderer = null;
    private RunnableExecutor mOnMapReadyCallback = null;
    public Bitmap mSplashBitmap = null;
    private String nativeTag;

    private class AppViewInputConnection extends BaseInputConnection {
        AppViewInputConnection(View $r2) throws  {
            super($r2, false);
        }

        public boolean performEditorAction(int $i0) throws  {
            if ($i0 != MapView.this.mImeAction) {
                return false;
            }
            MapView.this.onKeyDown(66, new KeyEvent(0, 66));
            if (MapView.this.mImeCloseOnAction) {
                MapView.this.HideSoftInput();
            }
            return true;
        }
    }

    public MapView(Context $r1) throws  {
        super($r1);
        Init();
    }

    public MapView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        TypedArray $r4 = $r1.obtainStyledAttributes($r2, C1283R.styleable.MapView);
        this.handleKeys = $r4.getBoolean(0, true);
        this.handleTouch = $r4.getBoolean(1, true);
        this.nativeTag = $r4.getString(2);
        if (!isInEditMode()) {
            Init();
        }
    }

    public void setRenderer() throws  {
        if (this.mNativeRenderer != null) {
            return;
        }
        if (this.nativeTag != null) {
            this.mNativeRenderer = new NativeCanvasRenderer(this.nativeTag, this);
            setRenderer(this.mNativeRenderer);
            setRenderMode(0);
            return;
        }
        Log.w(Logger.TAG, "Unable to create renderer - the TAG is null");
    }

    public void surfaceCreated(SurfaceTexture $r1) throws  {
        this.mNativeRenderer.onViewSurfaceCreated();
        super.surfaceCreated($r1);
    }

    public void surfaceDestroyed(SurfaceTexture $r1) throws  {
        this.mNativeRenderer.onViewSurfaceDestroyed();
        super.surfaceDestroyed($r1);
    }

    public void surfaceChanged(SurfaceTexture $r1, int $i0, int $i1, int $i2) throws  {
        this.mNativeRenderer.onViewSurfaceChanged();
        super.surfaceChanged($r1, $i0, $i1, $i2);
    }

    public void surfaceChangedCompleted(int w, int h) throws  {
    }

    public void registerOnMapReadyCallback(RunnableExecutor $r1) throws  {
        this.mOnMapReadyCallback = $r1;
    }

    public void onNativeCanvasReady() throws  {
        if (this.mOnMapReadyCallback != null) {
            this.mOnMapReadyCallback.run();
        }
    }

    public void onGLReady() throws  {
        NativeManager.getInstance().onGLReady();
    }

    public void onPause() throws  {
        Log.w(Logger.TAG, "MapView onPause");
        if (this.mNativeRenderer != null) {
            this.mNativeRenderer.onViewSurfaceDestroyed();
        }
        this.mIsRunning = false;
        super.onPause();
    }

    public void onResume() throws  {
        WazeApplication.startSW.startDelta("MapView onResume", false);
        Log.w(Logger.TAG, "MapView onResume");
        this.mNativeRenderer.onViewSurfaceCreated();
        super.onResume();
        this.mIsRunning = true;
        NativeManager.onSurfaceReady();
    }

    public boolean IsReady() throws  {
        return this.mIsRunning && this.mIsSurfaceReady;
    }

    private void Init() throws  {
        if (!isInEditMode()) {
            setRenderer();
        }
        setFocusableInTouchMode(true);
        Arrays.sort(mUnhandledKeys);
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        if (!this.handleTouch) {
            return false;
        }
        this.mNativeRenderer.onTouchEvent(MotionEvent.obtain($r1));
        return true;
    }

    public boolean dispatchKeyEventPreIme(KeyEvent $r1) throws  {
        if (this.handleKeys) {
            return onKeyDown($r1.getKeyCode(), $r1);
        }
        return super.dispatchKeyEventPreIme($r1);
    }

    public InputConnection onCreateInputConnection(EditorInfo $r1) throws  {
        $r1.imeOptions = this.mImeAction | 268435456;
        $r1.inputType = NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY;
        return new AppViewInputConnection(this);
    }

    public void ShowSoftInput() throws  {
        getInputMethodManager().restartInput(this);
        getInputMethodManager().showSoftInput(this, 2);
    }

    public void HideSoftInput() throws  {
        getInputMethodManager().hideSoftInputFromWindow(getWindowToken(), 0);
    }

    public void setImeAction(int $i0) throws  {
        this.mImeAction = $i0;
    }

    public void setImeCloseOnAction(boolean $z0) throws  {
        this.mImeCloseOnAction = $z0;
    }

    public InputMethodManager getInputMethodManager() throws  {
        return (InputMethodManager) getContext().getSystemService("input_method");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(int r27, android.view.KeyEvent r28) throws  {
        /*
        r26 = this;
        r0 = r28;
        r9 = r0.getMetaState();
        r0 = r26;
        r10 = r0.getContext();
        r11 = r10.getResources();
        r12 = r11.getConfiguration();
        r0 = r26;
        r13 = r0.mLastEvent;
        r0 = r28;
        if (r13 != r0) goto L_0x0027;
    L_0x001c:
        r0 = r26;
        r1 = r27;
        r2 = r28;
        r14 = super.onKeyDown(r1, r2);
        return r14;
    L_0x0027:
        r0 = r28;
        r1 = r26;
        r1.mLastEvent = r0;
        r15 = mUnhandledKeys;
        r0 = r27;
        r16 = java.util.Arrays.binarySearch(r15, r0);
        if (r16 < 0) goto L_0x005b;
    L_0x0037:
        r15 = mUnhandledKeysMetaMask;
        r17 = r15[r16];
        if (r17 == 0) goto L_0x0050;
    L_0x003d:
        r15 = mUnhandledKeysMetaMask;
        r16 = r15[r16];
        r0 = r28;
        r17 = r0.getMetaState();
        r0 = r16;
        r1 = r17;
        r0 = r0 & r1;
        r16 = r0;
        if (r16 <= 0) goto L_0x005b;
    L_0x0050:
        r0 = r26;
        r1 = r27;
        r2 = r28;
        r14 = super.onKeyDown(r1, r2);
        return r14;
    L_0x005b:
        r18 = 82;
        r0 = r27;
        r1 = r18;
        if (r0 != r1) goto L_0x0077;
    L_0x0063:
        r19 = com.waze.AppService.getNativeManager();
        if (r19 == 0) goto L_0x0071;
    L_0x0069:
        r0 = r19;
        r14 = r0.IsMenuEnabled();
        if (r14 != 0) goto L_0x0074;
    L_0x0071:
        r18 = 1;
        return r18;
    L_0x0074:
        r18 = 0;
        return r18;
    L_0x0077:
        r0 = r28;
        r16 = r0.getAction();
        if (r16 == 0) goto L_0x0090;
    L_0x007f:
        r0 = r28;
        r16 = r0.getAction();
        r18 = 2;
        r0 = r16;
        r1 = r18;
        if (r0 == r1) goto L_0x0090;
    L_0x008d:
        r18 = 1;
        return r18;
    L_0x0090:
        r18 = 6;
        r0 = r27;
        r1 = r18;
        if (r0 != r1) goto L_0x009b;
    L_0x0098:
        r18 = 1;
        return r18;
    L_0x009b:
        r0 = r12.hardKeyboardHidden;
        r16 = r0;
        r18 = 1;
        r0 = r16;
        r1 = r18;
        if (r0 != r1) goto L_0x0125;
    L_0x00a7:
        r0 = r27;
        r14 = android.view.KeyEvent.isModifierKey(r0);
        if (r14 == 0) goto L_0x00dd;
    L_0x00af:
        r0 = r26;
        r9 = r0.mModifierKeyCode;
        r0 = r27;
        if (r9 != r0) goto L_0x00ce;
    L_0x00b7:
        r0 = r26;
        r9 = r0.mModifierState;
        r9 = r9 + 1;
        r9 = r9 % 3;
        r0 = r26;
        r0.mModifierState = r9;
    L_0x00c3:
        r0 = r26;
        r1 = r27;
        r2 = r28;
        r14 = super.onKeyDown(r1, r2);
        return r14;
    L_0x00ce:
        r0 = r27;
        r1 = r26;
        r1.mModifierKeyCode = r0;
        r18 = 1;
        r0 = r18;
        r1 = r26;
        r1.mModifierState = r0;
        goto L_0x00c3;
    L_0x00dd:
        r0 = r26;
        r0 = r0.mModifierState;
        r16 = r0;
        r18 = 2;
        r0 = r16;
        r1 = r18;
        if (r0 != r1) goto L_0x014a;
    L_0x00eb:
        r0 = r26;
        r0 = r0.mModifierKeyCode;
        r16 = r0;
        r18 = 57;
        r0 = r16;
        r1 = r18;
        if (r0 == r1) goto L_0x0107;
    L_0x00f9:
        r0 = r26;
        r0 = r0.mModifierKeyCode;
        r16 = r0;
        r18 = 58;
        r0 = r16;
        r1 = r18;
        if (r0 != r1) goto L_0x0108;
    L_0x0107:
        r9 = 2;
    L_0x0108:
        r0 = r26;
        r0 = r0.mModifierKeyCode;
        r16 = r0;
        r18 = 59;
        r0 = r16;
        r1 = r18;
        if (r0 == r1) goto L_0x0124;
    L_0x0116:
        r0 = r26;
        r0 = r0.mModifierKeyCode;
        r16 = r0;
        r18 = 60;
        r0 = r16;
        r1 = r18;
        if (r0 != r1) goto L_0x0125;
    L_0x0124:
        r9 = 1;
    L_0x0125:
        r19 = com.waze.AppService.getNativeManager();
        r0 = r26;
        r14 = r0.handleKeys;
        if (r14 != 0) goto L_0x016b;
    L_0x012f:
        r18 = 84;
        r0 = r27;
        r1 = r18;
        if (r0 == r1) goto L_0x013f;
    L_0x0137:
        r18 = 4;
        r0 = r27;
        r1 = r18;
        if (r0 != r1) goto L_0x016b;
    L_0x013f:
        r0 = r26;
        r1 = r27;
        r2 = r28;
        r14 = super.onKeyDown(r1, r2);
        return r14;
    L_0x014a:
        r0 = r28;
        r14 = r0.isShiftPressed();
        if (r14 != 0) goto L_0x0125;
    L_0x0152:
        r0 = r28;
        r14 = r0.isAltPressed();
        if (r14 != 0) goto L_0x0125;
    L_0x015a:
        r18 = -1;
        r0 = r18;
        r1 = r26;
        r1.mModifierKeyCode = r0;
        r18 = 0;
        r0 = r18;
        r1 = r26;
        r1.mModifierState = r0;
        goto L_0x0125;
    L_0x016b:
        r18 = 27;
        r0 = r27;
        r1 = r18;
        if (r0 != r1) goto L_0x0176;
    L_0x0173:
        r18 = 1;
        return r18;
    L_0x0176:
        if (r19 == 0) goto L_0x01ed;
    L_0x0178:
        r13 = r28;
        r0 = r28;
        r27 = r0.getAction();
        if (r27 != 0) goto L_0x01b1;
    L_0x0182:
        r13 = new android.view.KeyEvent;
        r0 = r28;
        r20 = r0.getEventTime();
        r0 = r28;
        r22 = r0.getEventTime();
        r0 = r28;
        r27 = r0.getAction();
        r0 = r28;
        r16 = r0.getKeyCode();
        r0 = r28;
        r17 = r0.getRepeatCount();
        r0 = r13;
        r1 = r20;
        r3 = r22;
        r5 = r27;
        r6 = r16;
        r7 = r17;
        r8 = r9;
        r0.<init>(r1, r3, r5, r6, r7, r8);
    L_0x01b1:
        r0 = r28;
        r27 = r0.getAction();
        r18 = 2;
        r0 = r27;
        r1 = r18;
        if (r0 != r1) goto L_0x01e4;
    L_0x01bf:
        r13 = new android.view.KeyEvent;
        r0 = r28;
        r20 = r0.getEventTime();
        r0 = r28;
        r24 = r0.getCharacters();
        r0 = r28;
        r27 = r0.getDeviceId();
        r0 = r28;
        r9 = r0.getFlags();
        r0 = r13;
        r1 = r20;
        r3 = r24;
        r4 = r27;
        r5 = r9;
        r0.<init>(r1, r3, r4, r5);
    L_0x01e4:
        r0 = r26;
        r0 = r0.mNativeRenderer;
        r25 = r0;
        r0.onKeyDownEvent(r13);
    L_0x01ed:
        r18 = 1;
        return r18;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.map.MapView.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    public boolean onKeyMultiple(int $i0, int aRepeatCount, KeyEvent $r1) throws  {
        return onKeyDown($i0, $r1);
    }

    public void setHandleTouch(boolean $z0) throws  {
        this.handleTouch = $z0;
        setFocusableInTouchMode($z0);
    }

    public void setHandleKeys(boolean $z0) throws  {
        this.handleKeys = $z0;
    }

    public void setNativeTag(String $r1) throws  {
        this.nativeTag = $r1;
    }
}
