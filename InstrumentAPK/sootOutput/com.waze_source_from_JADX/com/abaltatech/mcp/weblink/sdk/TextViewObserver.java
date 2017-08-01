package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.widget.EditText;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint({"ClickableViewAccessibility"})
class TextViewObserver implements OnTouchListener, OnHierarchyChangeListener {
    private static final String TAG = "TextViewObserver";
    private static final TextViewObserver m_textViewObserver = new TextViewObserver();
    private Method m_getListenerInfoMethod;
    private boolean m_isInitialized = false;
    private Class<?> m_listenerInfoClass;
    private Field m_listenerInfoField;
    private List<TextViewNotification> m_notifications = new ArrayList();
    private Map<View, OnTouchListener> m_onTouchListeners = new HashMap();
    private Class<?> m_viewClass;

    private android.view.View.OnTouchListener getTouchListener(android.view.View r11) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x002c in list []
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
        r10 = this;
        r0 = r10.m_getListenerInfoMethod;
        if (r0 == 0) goto L_0x0028;
    L_0x0004:
        r1 = r10.m_listenerInfoField;
        if (r1 == 0) goto L_0x002a;
    L_0x0008:
        r0 = r10.m_getListenerInfoMethod;	 Catch:{ Exception -> 0x001e }
        r3 = 0;	 Catch:{ Exception -> 0x001e }
        r2 = r0.invoke(r11, r3);	 Catch:{ Exception -> 0x001e }
        r1 = r10.m_listenerInfoField;	 Catch:{ Exception -> 0x001e }
        r2 = r1.get(r2);	 Catch:{ Exception -> 0x001e }
        r4 = r2 instanceof android.view.View.OnTouchListener;
        if (r4 == 0) goto L_0x002c;	 Catch:{ Exception -> 0x001e }
    L_0x0019:
        r6 = r2;	 Catch:{ Exception -> 0x001e }
        r6 = (android.view.View.OnTouchListener) r6;	 Catch:{ Exception -> 0x001e }
        r5 = r6;	 Catch:{ Exception -> 0x001e }
        return r5;
    L_0x001e:
        r7 = move-exception;
        r8 = "TextViewObserver";
        r9 = "Exception";
        android.util.Log.e(r8, r9, r7);
        r3 = 0;
        return r3;
    L_0x0028:
        r3 = 0;
        return r3;
    L_0x002a:
        r3 = 0;
        return r3;
    L_0x002c:
        r3 = 0;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.TextViewObserver.getTouchListener(android.view.View):android.view.View$OnTouchListener");
    }

    private TextViewObserver() throws  {
        try {
            this.m_viewClass = Class.forName("android.view.View");
            this.m_getListenerInfoMethod = this.m_viewClass.getDeclaredMethod("getListenerInfo", null);
            this.m_getListenerInfoMethod.setAccessible(true);
            this.m_listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
            this.m_listenerInfoField = this.m_listenerInfoClass.getDeclaredField("mOnTouchListener");
            this.m_listenerInfoField.setAccessible(true);
        } catch (Exception $r1) {
            Log.e(TAG, "Exception", $r1);
        }
    }

    public static TextViewObserver getInstance() throws  {
        return m_textViewObserver;
    }

    public void Init() throws  {
        if (!this.m_isInitialized) {
            this.m_isInitialized = true;
            WLTreeViewObserver.getInstance().registerOnHierarchyChangeListener(this);
        }
    }

    public synchronized void registerViewNotification(TextViewNotification $r1) throws  {
        this.m_notifications.add($r1);
    }

    public synchronized void unregisterViewNotification(TextViewNotification $r1) throws  {
        this.m_notifications.remove($r1);
    }

    public void onChildViewAdded(View parent, View $r2) throws  {
        TextViewObserver $r3 = getTouchListener($r2);
        if ($r3 == this) {
            Log.e(TAG, "Internal error: onTouchListener added twice for the same view!");
            return;
        }
        $r2.setOnTouchListener(this);
        if ($r3 != null) {
            this.m_onTouchListeners.put($r2, $r3);
        }
    }

    public void onChildViewRemoved(View parent, View $r2) throws  {
        $r2.setOnTouchListener((OnTouchListener) this.m_onTouchListeners.remove($r2));
    }

    public boolean onTouch(View $r1, MotionEvent $r2) throws  {
        if ($r2.getPointerCount() == 1 && $r2.getAction() == 1) {
            boolean $z0 = $r1 instanceof EditText;
            if ($r1 != null && ($r1 instanceof WebView)) {
                HitTestResult $r7 = ((WebView) $r1).getHitTestResult();
                if ($r7 != null && $r7.getType() == 9) {
                    $z0 = true;
                }
            }
            for (TextViewNotification onViewClicked : getNotification()) {
                onViewClicked.onViewClicked($r1, $z0);
            }
        }
        OnTouchListener $r5 = (OnTouchListener) this.m_onTouchListeners.get($r1);
        if ($r5 != null && $r5 != this) {
            return $r5.onTouch($r1, $r2);
        }
        if ($r5 != this) {
            return false;
        }
        Log.e(TAG, "Internal error: onTouchListener added twice for the same view!");
        return false;
    }

    private synchronized List<TextViewNotification> getNotification() throws  {
        return new ArrayList(this.m_notifications);
    }
}
