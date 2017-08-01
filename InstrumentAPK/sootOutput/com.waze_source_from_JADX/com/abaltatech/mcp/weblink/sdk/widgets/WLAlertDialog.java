package com.abaltatech.mcp.weblink.sdk.widgets;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import android.widget.Toast;
import com.abaltatech.mcp.weblink.sdk.WEBLINK;
import com.abaltatech.mcp.weblink.sdk.WLMirrorMode;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint({"RtlHardcoded"})
public class WLAlertDialog {
    private static final int ALERT_DIALOG_MOVE_DELAY = 300;
    private static final int HIDDEN_X = 8000;
    private static final int HIDDEN_Y = 8000;
    private static final String TAG = "WLAlertDialog";
    private static final Handler m_handler = new Handler();

    interface IOnViewLayout {
        void onViewLayout(View view) throws ;
    }

    public static int getAlertDialogMoveDelay() throws  {
        return 300;
    }

    public static int getHiddenX() throws  {
        return WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS;
    }

    public static int getHiddenY() throws  {
        return WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS;
    }

    public static void showDialog(Dialog $r0) throws  {
        int $i0 = WEBLINK.instance.getUiMode();
        if (($i0 != 2 || VERSION.SDK_INT >= 19) && $i0 != 3) {
            $r0.show();
        } else {
            showDialog($r0, null);
        }
    }

    public static Context initDialogContext(Context $r0) throws  {
        int $i0 = WEBLINK.instance.getUiMode();
        if (($i0 != 2 || VERSION.SDK_INT >= 19) && $i0 != 3) {
            return $i0 == 1 ? WLMirrorMode.instance.revertResourceMetrics($r0) : $r0;
        } else {
            return WLMirrorMode.instance.revertResourceMetrics($r0);
        }
    }

    public static void showToast(Context $r3, CharSequence $r0, int $i0) throws  {
        int $i2 = WEBLINK.instance.getUiMode();
        if ($i2 == 2 || $i2 == 3) {
            short $s4;
            if (VERSION.SDK_INT == 19 && $i2 == 2) {
                $r3 = new ContextThemeWrapper($r3, WLContextUtils.DefaultContextMenuThemeInPresentationMode);
            }
            Builder $r1 = new Builder(initDialogContext($r3));
            $r1.setMessage($r0);
            final AlertDialog $r6 = $r1.create();
            if ($i0 == 1) {
                $s4 = (short) 6000;
            } else {
                $s4 = (short) 3000;
            }
            if (VERSION.SDK_INT >= 19 || $i2 != 2) {
                showDialog($r6);
                long $l1 = (long) $s4;
                m_handler.postDelayed(new Runnable() {
                    public void run() throws  {
                        $r6.dismiss();
                    }
                }, $l1);
                return;
            }
            final CharSequence charSequence = $r0;
            showDialog($r6, new IOnViewLayout() {

                class C03751 implements Runnable {
                    C03751() throws  {
                    }

                    public void run() throws  {
                        $r6.dismiss();
                    }
                }

                public void onViewLayout(View $r1) throws  {
                    $r1 = $r1.findViewById(16908290);
                    ArrayList $r2 = new ArrayList();
                    $r1.findViewsWithText($r2, charSequence, 1);
                    Iterator $r4 = $r2.iterator();
                    while ($r4.hasNext()) {
                        $r1 = (View) $r4.next();
                        if ($r1 instanceof TextView) {
                            ((TextView) $r1).setGravity(17);
                        }
                    }
                    WLAlertDialog.m_handler.postDelayed(new C03751(), (long) $s4);
                }
            });
            return;
        }
        Toast.makeText(initDialogContext($r3), $r0, $i0).show();
    }

    private static void showDialog(final Dialog $r0, final IOnViewLayout $r1) throws  {
        boolean $z0;
        final Window $r3 = $r0.getWindow();
        final Callback $r4 = $r3.getCallback();
        int $i0 = WEBLINK.instance.getUiMode();
        if ($r0 instanceof AlertDialog) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        if (($i0 == 2 && VERSION.SDK_INT < 19) || $i0 == 3) {
            LayoutParams $r6 = $r3.getAttributes();
            LayoutParams $r2 = new LayoutParams();
            $r2.copyFrom($r6);
            $r2.gravity = 51;
            $r2.flags |= 512;
            $r2.x = WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS;
            $r2.y = WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS;
            $r3.setAttributes($r2);
            $r3.setCallback(new Callback() {
                final Callback m_clb = $r4;
                private OnGlobalLayoutListener m_onGlobalLayoutListener;

                public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.dispatchKeyEvent($r1);
                    }
                    return false;
                }

                public boolean dispatchKeyShortcutEvent(KeyEvent $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.dispatchKeyShortcutEvent($r1);
                    }
                    return false;
                }

                public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.dispatchTouchEvent($r1);
                    }
                    return false;
                }

                public boolean dispatchTrackballEvent(MotionEvent $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.dispatchTrackballEvent($r1);
                    }
                    return false;
                }

                public boolean dispatchGenericMotionEvent(MotionEvent $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.dispatchGenericMotionEvent($r1);
                    }
                    return false;
                }

                public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.dispatchPopulateAccessibilityEvent($r1);
                    }
                    return false;
                }

                public View onCreatePanelView(int $i0) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.onCreatePanelView($i0);
                    }
                    return null;
                }

                public boolean onCreatePanelMenu(int $i0, Menu $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.onCreatePanelMenu($i0, $r1);
                    }
                    return true;
                }

                public boolean onPreparePanel(int $i0, View $r1, Menu $r2) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.onPreparePanel($i0, $r1, $r2);
                    }
                    return true;
                }

                public boolean onMenuOpened(int $i0, Menu $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.onMenuOpened($i0, $r1);
                    }
                    return true;
                }

                public boolean onMenuItemSelected(int $i0, MenuItem $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.onMenuItemSelected($i0, $r1);
                    }
                    return false;
                }

                public void onWindowAttributesChanged(LayoutParams $r1) throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onWindowAttributesChanged($r1);
                    }
                }

                public void onContentChanged() throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onContentChanged();
                    }
                    this.m_onGlobalLayoutListener = WLAlertDialog.setLayoutListener($r3, $r1, $r0.getContext());
                }

                public void onWindowFocusChanged(boolean $z0) throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onWindowFocusChanged($z0);
                    }
                }

                public void onAttachedToWindow() throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onAttachedToWindow();
                    }
                }

                @SuppressLint({"MissingSuperCall"})
                public void onDetachedFromWindow() throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onDetachedFromWindow();
                    }
                    if (this.m_onGlobalLayoutListener == null) {
                    }
                }

                public void onPanelClosed(int $i0, Menu $r1) throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onPanelClosed($i0, $r1);
                    }
                }

                public boolean onSearchRequested() throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.onSearchRequested();
                    }
                    return false;
                }

                public ActionMode onWindowStartingActionMode(ActionMode.Callback $r1) throws  {
                    if (this.m_clb != null) {
                        return this.m_clb.onWindowStartingActionMode($r1);
                    }
                    return null;
                }

                public void onActionModeStarted(ActionMode $r1) throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onActionModeStarted($r1);
                    }
                }

                public void onActionModeFinished(ActionMode $r1) throws  {
                    if (this.m_clb != null) {
                        this.m_clb.onActionModeFinished($r1);
                    }
                }
            });
        }
        if ($z0) {
            setLayoutListener($r3, $r1, $r0.getContext());
        }
        $r0.show();
        Log.d("", "");
    }

    protected static OnGlobalLayoutListener setLayoutListener(final Window $r0, final IOnViewLayout $r1, final Context $r2) throws  {
        ViewTreeObserver $r5 = $r0.getDecorView().getViewTreeObserver();
        C03804 $r3 = new OnGlobalLayoutListener() {

            class C03791 implements Runnable {
                C03791() throws  {
                }

                public void run() throws  {
                    View $r5 = $r0.getDecorView();
                    View $r6 = $r0.getDecorView().findViewById(16908290);
                    if ($r1 != null) {
                        $r1.onViewLayout($r6);
                    }
                    float $f0 = WLMirrorMode.instance.getOverrideScaleFactor($r5.getContext());
                    $r5.setScaleX($f0);
                    $r5.setScaleY($f0);
                    $r5.setPivotX(0.0f);
                    $r5.setPivotY(0.0f);
                    int $i2 = WLMirrorMode.instance.getWidth();
                    int $i3 = WLMirrorMode.instance.getHeight();
                    float $f1 = (float) $r6.getHeight();
                    $f1 *= $f0;
                    int $i0 = (int) $f1;
                    int $i1 = (int) (((float) $r6.getWidth()) * $f0);
                    LayoutParams $r10 = $r0.getAttributes();
                    ViewGroup.LayoutParams layoutParams = new LayoutParams();
                    layoutParams.copyFrom($r10);
                    layoutParams.gravity = 51;
                    int $i4 = layoutParams.flags;
                    $i4 |= 512;
                    int $i42 = $i4;
                    layoutParams.flags = $i4;
                    if (layoutParams.width == -1) {
                        layoutParams.x = 4000;
                        layoutParams.width = $i2;
                    } else {
                        layoutParams.x = (($i2 - $i1) / 2) + 4000;
                    }
                    if (layoutParams.height == -1) {
                        layoutParams.y = 4000;
                        layoutParams.height = $i3;
                    } else {
                        layoutParams.y = (($i3 - $i0) / 2) + 4000;
                    }
                    try {
                        ((WindowManager) $r2.getSystemService("window")).updateViewLayout($r0.getDecorView(), layoutParams);
                    } catch (Throwable $r1) {
                        Log.w(WLAlertDialog.TAG, "Error repositioning window in Off-screen mode", $r1);
                    }
                }
            }

            public void onGlobalLayout() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0039 in list []
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
                r14 = this;
                r0 = r3;	 Catch:{ Throwable -> 0x002f }
                r1 = r0.getDecorView();	 Catch:{ Throwable -> 0x002f }
                r3 = 16908290; // 0x1020002 float:2.3877235E-38 double:8.353805E-317;	 Catch:{ Throwable -> 0x002f }
                r2 = r1.findViewById(r3);	 Catch:{ Throwable -> 0x002f }
                r4 = r2.getHeight();	 Catch:{ Throwable -> 0x002f }
                r5 = r2.getWidth();	 Catch:{ Throwable -> 0x002f }
                if (r5 <= 0) goto L_0x0038;
            L_0x0017:
                if (r4 <= 0) goto L_0x0039;	 Catch:{ Throwable -> 0x002f }
            L_0x0019:
                r6 = r1.getViewTreeObserver();	 Catch:{ Throwable -> 0x002f }
                r6.removeOnGlobalLayoutListener(r14);	 Catch:{ Throwable -> 0x002f }
                r7 = com.abaltatech.mcp.weblink.sdk.widgets.WLAlertDialog.m_handler;	 Catch:{ Throwable -> 0x002f }
                r8 = new com.abaltatech.mcp.weblink.sdk.widgets.WLAlertDialog$4$1;	 Catch:{ Throwable -> 0x002f }
                r8.<init>();	 Catch:{ Throwable -> 0x002f }
                r9 = 300; // 0x12c float:4.2E-43 double:1.48E-321;	 Catch:{ Throwable -> 0x002f }
                r7.postDelayed(r8, r9);	 Catch:{ Throwable -> 0x002f }
                return;
            L_0x002f:
                r11 = move-exception;
                r12 = "";
                r13 = ".";
                android.util.Log.e(r12, r13, r11);
                return;
            L_0x0038:
                return;
            L_0x0039:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.widgets.WLAlertDialog.4.onGlobalLayout():void");
            }
        };
        $r5.addOnGlobalLayoutListener($r3);
        return $r3;
    }
}
