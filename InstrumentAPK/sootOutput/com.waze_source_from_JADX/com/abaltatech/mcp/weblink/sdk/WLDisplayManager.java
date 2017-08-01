package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import com.facebook.internal.ServerProtocol;
import java.util.ArrayList;
import java.util.Iterator;

public final class WLDisplayManager {
    private static final int EVENT_DISPLAY_ADDED = 101;
    private static final int EVENT_DISPLAY_CHANGED = 102;
    private static final int EVENT_DISPLAY_REMOVED = 103;
    private static final int FIRST_DISPLAY_ID = -10;
    public static final int INVALID_DISPLAY_ID = -9;
    private static final String TAG = "WLDisplayManager";
    public static final String WLDISPLAY_NAME = "WEBLINK";
    private static WLDisplayManager ms_instance = new WLDisplayManager();
    private final ArrayList<WLDisplayListenerDelegate> mDisplayListeners = new ArrayList();
    private ArrayList<WLDisplay> m_currentDisplays = new ArrayList();
    private DisplayListener m_displayListener = null;
    private int m_lastDisplayHeight;
    private int m_lastDisplayID = FIRST_DISPLAY_ID;
    private int m_lastDisplayWidth;
    private float m_lastVirtualDisplayScale = 1.0f;
    private final Object m_lock = new Object();

    class C03391 implements DisplayListener {
        C03391() throws  {
        }

        public void onDisplayAdded(int $i0) throws  {
            WLDisplayManager.this.onDisplayAddedInternal($i0);
        }

        public void onDisplayChanged(int $i0) throws  {
            synchronized (WLDisplayManager.this.m_lock) {
                int $i1 = WLDisplayManager.this.mDisplayListeners.size();
                for (int $i2 = 0; $i2 < $i1; $i2++) {
                    ((WLDisplayListenerDelegate) WLDisplayManager.this.mDisplayListeners.get($i2)).sendDisplayEvent($i0, 102);
                }
            }
        }

        public void onDisplayRemoved(int $i0) throws  {
            WLDisplayManager.this.onDisplayRemovedInternal($i0);
        }
    }

    public interface IWLDisplayListener {
        void onDisplayAdded(int i) throws ;

        void onDisplayChanged(int i) throws ;

        void onDisplayRemoved(int i) throws ;
    }

    private static final class WLDisplayListenerDelegate extends Handler {
        public final IWLDisplayListener mListener;

        public WLDisplayListenerDelegate(IWLDisplayListener $r1, Handler $r2) throws  {
            super($r2 != null ? $r2.getLooper() : Looper.myLooper(), null);
            this.mListener = $r1;
        }

        public void sendDisplayEvent(int $i0, int $i1) throws  {
            sendMessage(obtainMessage($i1, $i0, 0));
        }

        public void clearEvents() throws  {
            removeCallbacksAndMessages(null);
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 101:
                    this.mListener.onDisplayAdded($r1.arg1);
                    return;
                case 102:
                    this.mListener.onDisplayChanged($r1.arg1);
                    return;
                case 103:
                    this.mListener.onDisplayRemoved($r1.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    @SuppressLint({"NewApi"})
    private WLDisplayManager() throws  {
        if (VERSION.SDK_INT >= 17) {
            DisplayManager $r5 = (DisplayManager) WEBLINK.instance.getContext().getSystemService(ServerProtocol.DIALOG_PARAM_DISPLAY);
            this.m_displayListener = new C03391();
            $r5.registerDisplayListener(this.m_displayListener, null);
        }
    }

    public static WLDisplayManager getInstance() throws  {
        return ms_instance;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"NewApi"})
    com.abaltatech.mcp.weblink.sdk.WLDisplay createVirtualDisplay(int r32, int r33, int r34, android.view.Surface r35) throws  {
        /*
        r31 = this;
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r31;
        r0.m_lastVirtualDisplayScale = r7;
        r0 = r32;
        r1 = r31;
        r1.m_lastDisplayWidth = r0;
        r0 = r33;
        r1 = r31;
        r1.m_lastDisplayHeight = r0;
        r8 = android.os.Build.VERSION.SDK_INT;
        r9 = 19;
        if (r8 < r9) goto L_0x0058;
    L_0x0019:
        if (r35 == 0) goto L_0x0058;
    L_0x001b:
        r10 = com.abaltatech.mcp.weblink.sdk.WEBLINK.instance;
        r11 = r10.getContext();
        r13 = "display";
        r12 = r11.getSystemService(r13);
        r15 = r12;
        r15 = (android.hardware.display.DisplayManager) r15;
        r14 = r15;
        r13 = "WEBLINK";
        r9 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
        r17 = 0;
        r0 = r14;
        r1 = r13;
        r2 = r32;
        r3 = r33;
        r4 = r9;
        r5 = r35;
        r6 = r17;
        r16 = r0.createVirtualDisplay(r1, r2, r3, r4, r5, r6);
        if (r16 == 0) goto L_0x012e;
    L_0x0042:
        r0 = r16;
        r18 = r0.getDisplay();
        r0 = r31;
        r1 = r18;
        r19 = r0.createDisplay(r1);
        r0 = r19;
        r1 = r16;
        r0.setVirtualDisplay(r1);
        return r19;
    L_0x0058:
        r8 = -10;
        r0 = r31;
        r12 = r0.m_lock;
        monitor-enter(r12);
        r0 = r31;
        r0 = r0.m_currentDisplays;	 Catch:{ Throwable -> 0x0126 }
        r20 = r0;
        r21 = r0.size();	 Catch:{ Throwable -> 0x0126 }
        if (r21 <= 0) goto L_0x011f;
    L_0x006b:
        r0 = r31;
        r8 = r0.m_lastDisplayID;	 Catch:{ Throwable -> 0x0126 }
        r8 = r8 + -1;
        r0 = r31;
        r0.m_lastDisplayID = r8;	 Catch:{ Throwable -> 0x0126 }
        r0 = r31;
        r8 = r0.m_lastDisplayID;	 Catch:{ Throwable -> 0x0126 }
    L_0x0079:
        r9 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
        r0 = r34;
        if (r0 == r9) goto L_0x00de;
    L_0x007f:
        r0 = r34;
        r0 = (float) r0;	 Catch:{ Throwable -> 0x0126 }
        r22 = r0;
        r7 = 1126170624; // 0x43200000 float:160.0 double:5.564022167E-315;
        r0 = r22;
        r0 = r0 / r7;
        r22 = r0;
        r0 = r32;
        r0 = (float) r0;	 Catch:{ Throwable -> 0x0126 }
        r23 = r0;
        r1 = r22;
        r0 = r0 * r1;
        r23 = r0;
        r0 = (double) r0;	 Catch:{ Throwable -> 0x0126 }
        r24 = r0;
        r26 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r0 = r24;
        r2 = r26;
        r0 = r0 + r2;
        r24 = r0;
        r0 = (int) r0;	 Catch:{ Throwable -> 0x0126 }
        r34 = r0;
        r32 = r34;
        r0 = r33;
        r0 = (float) r0;	 Catch:{ Throwable -> 0x0126 }
        r23 = r0;
        r1 = r22;
        r0 = r0 * r1;
        r23 = r0;
        r0 = (double) r0;	 Catch:{ Throwable -> 0x0126 }
        r24 = r0;
        r26 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r0 = r24;
        r2 = r26;
        r0 = r0 + r2;
        r24 = r0;
        r0 = (int) r0;	 Catch:{ Throwable -> 0x0126 }
        r21 = r0;
        r33 = r21;
        r0 = r22;
        r1 = r31;
        r1.m_lastVirtualDisplayScale = r0;	 Catch:{ Throwable -> 0x0126 }
        goto L_0x00d2;
    L_0x00cf:
        goto L_0x0079;
    L_0x00d2:
        r0 = r34;
        r1 = r31;
        r1.m_lastDisplayWidth = r0;	 Catch:{ Throwable -> 0x0126 }
        r0 = r21;
        r1 = r31;
        r1.m_lastDisplayHeight = r0;	 Catch:{ Throwable -> 0x0126 }
    L_0x00de:
        r19 = new com.abaltatech.mcp.weblink.sdk.WLDisplay;	 Catch:{ Throwable -> 0x0126 }
        r10 = com.abaltatech.mcp.weblink.sdk.WEBLINK.instance;	 Catch:{ Throwable -> 0x0126 }
        r34 = r10.getUiMode();	 Catch:{ Throwable -> 0x0126 }
        r13 = "WEBLINK";
        r0 = r19;
        r1 = r32;
        r2 = r33;
        r3 = r35;
        r4 = r8;
        r5 = r13;
        r6 = r34;
        r0.<init>(r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x0126 }
        r0 = r31;
        r1 = r19;
        r0.setClientFeatures(r1);	 Catch:{ Throwable -> 0x011c }
        r0 = r31;
        r0 = r0.m_currentDisplays;	 Catch:{ Throwable -> 0x011c }
        r20 = r0;
        r1 = r19;
        r0.add(r1);	 Catch:{ Throwable -> 0x011c }
        r0 = r31;
        r0 = r0.m_displayListener;	 Catch:{ Throwable -> 0x011c }
        r28 = r0;
        if (r28 == 0) goto L_0x0128;
    L_0x0111:
        r0 = r31;
        r0 = r0.m_displayListener;	 Catch:{ Throwable -> 0x011c }
        r28 = r0;
        r0.onDisplayAdded(r8);	 Catch:{ Throwable -> 0x011c }
    L_0x011a:
        monitor-exit(r12);	 Catch:{ Throwable -> 0x011c }
        return r19;
    L_0x011c:
        r29 = move-exception;
    L_0x011d:
        monitor-exit(r12);	 Catch:{ Throwable -> 0x011c }
        throw r29;
    L_0x011f:
        r9 = -10;
        r0 = r31;
        r0.m_lastDisplayID = r9;	 Catch:{ Throwable -> 0x0126 }
        goto L_0x00cf;
    L_0x0126:
        r29 = move-exception;
        goto L_0x011d;
    L_0x0128:
        r0 = r31;
        r0.onDisplayAddedInternal(r8);	 Catch:{ Throwable -> 0x011c }
        goto L_0x011a;
    L_0x012e:
        r30 = 0;
        return r30;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLDisplayManager.createVirtualDisplay(int, int, int, android.view.Surface):com.abaltatech.mcp.weblink.sdk.WLDisplay");
    }

    @SuppressLint({"DefaultLocale"})
    private void setClientFeatures(WLDisplay $r1) throws  {
        String $r2 = WEBLINK.instance.getClientFeaturesString();
        if ($r2 != null) {
            for (String $r22 : $r22.split("\\|")) {
                String[] $r5 = $r22.split("=");
                if ($r5.length == 2) {
                    if ($r5[0].toLowerCase().compareTo("xdpi") == 0) {
                        $r1.setClientXdpi(tryParse($r5[1], 160));
                    } else if ($r5[0].toLowerCase().compareTo("ydpi") == 0) {
                        $r1.setClientYdpi(tryParse($r5[1], 160));
                    }
                }
            }
        }
    }

    private static int tryParse(String $r0, int $i0) throws  {
        try {
            $i0 = Integer.parseInt($r0);
            return $i0;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error parsing integer: " + $r0);
            return $i0;
        }
    }

    private void onDisplayAddedInternal(int $i0) throws  {
        synchronized (this.m_lock) {
            int $i1 = this.mDisplayListeners.size();
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                ((WLDisplayListenerDelegate) this.mDisplayListeners.get($i2)).sendDisplayEvent($i0, 101);
            }
        }
    }

    @SuppressLint({"NewApi"})
    public WLDisplay getDisplay(int $i0) throws  {
        WLDisplay $r1 = null;
        synchronized (this.m_lock) {
            Iterator $r4 = this.m_currentDisplays.iterator();
            while ($r4.hasNext()) {
                WLDisplay $r6 = (WLDisplay) $r4.next();
                if ($r6.getDisplayId() == $i0) {
                    $r1 = $r6;
                    break;
                }
            }
        }
        if ($r1 != null || VERSION.SDK_INT < 19) {
            return $r1;
        }
        return createDisplay(((DisplayManager) WEBLINK.instance.getContext().getSystemService(ServerProtocol.DIALOG_PARAM_DISPLAY)).getDisplay($i0));
    }

    @SuppressLint({"NewApi"})
    private WLDisplay createDisplay(Display $r1) throws  {
        String $r2 = (VERSION.SDK_INT < 17 || $r1 == null) ? "" : $r1.getName();
        WLDisplay $r3 = new WLDisplay($r1, $r1 != null ? $r1.getDisplayId() : -9, $r2, 2);
        setClientFeatures($r3);
        return $r3;
    }

    public void registerDisplayListener(IWLDisplayListener $r1, Handler $r2) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.m_lock) {
            if (findDisplayListener($r1) < 0) {
                this.mDisplayListeners.add(new WLDisplayListenerDelegate($r1, $r2));
            }
        }
    }

    public void unregisterDisplayListener(IWLDisplayListener $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.m_lock) {
            int $i0 = findDisplayListener($r1);
            if ($i0 >= 0) {
                ((WLDisplayListenerDelegate) this.mDisplayListeners.get($i0)).clearEvents();
                this.mDisplayListeners.remove($i0);
            }
        }
    }

    private int findDisplayListener(IWLDisplayListener $r1) throws  {
        int $i0 = this.mDisplayListeners.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (((WLDisplayListenerDelegate) this.mDisplayListeners.get($i1)).mListener == $r1) {
                return $i1;
            }
        }
        return -1;
    }

    @SuppressLint({"NewApi"})
    void releaseDisplay(WLDisplay $r1) throws  {
        synchronized (this.m_lock) {
            this.m_currentDisplays.remove($r1);
        }
        if (this.m_displayListener != null) {
            this.m_displayListener.onDisplayRemoved($r1.getDisplayId());
        } else {
            onDisplayRemovedInternal($r1.getDisplayId());
        }
    }

    private void onDisplayRemovedInternal(int $i0) throws  {
        synchronized (this.m_lock) {
            int $i1 = this.mDisplayListeners.size();
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                ((WLDisplayListenerDelegate) this.mDisplayListeners.get($i2)).sendDisplayEvent($i0, 103);
            }
        }
    }

    float getLastVirtualDisplayScale() throws  {
        return this.m_lastVirtualDisplayScale;
    }

    public int getLastDisplayWidth() throws  {
        return this.m_lastDisplayWidth;
    }

    public int getLastDisplayHeight() throws  {
        return this.m_lastDisplayHeight;
    }

    public synchronized WLDisplay getLastDisplay() throws  {
        WLDisplay $r4;
        if (this.m_currentDisplays.size() > 0) {
            $r4 = (WLDisplay) this.m_currentDisplays.get(this.m_currentDisplays.size() - 1);
        } else {
            $r4 = null;
        }
        return $r4;
    }
}
