package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

public class AppCompatPopupWindow extends PopupWindow {
    private static final boolean COMPAT_OVERLAP_ANCHOR = (VERSION.SDK_INT < 21);
    private static final String TAG = "AppCompatPopupWindow";
    private boolean mOverlapAnchor;

    public AppCompatPopupWindow(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        TintTypedArray $r4 = TintTypedArray.obtainStyledAttributes($r1, $r2, C0192R.styleable.PopupWindow, $i0, 0);
        if ($r4.hasValue(C0192R.styleable.PopupWindow_overlapAnchor)) {
            setSupportOverlapAnchor($r4.getBoolean(C0192R.styleable.PopupWindow_overlapAnchor, false));
        }
        setBackgroundDrawable($r4.getDrawable(C0192R.styleable.PopupWindow_android_popupBackground));
        $r4.recycle();
        if (VERSION.SDK_INT < 14) {
            wrapOnScrollChangedListener(this);
        }
    }

    public void showAsDropDown(View $r1, int $i0, int $i2) throws  {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            $i2 -= $r1.getHeight();
        }
        super.showAsDropDown($r1, $i0, $i2);
    }

    @TargetApi(19)
    public void showAsDropDown(View $r1, int $i0, int $i3, int $i1) throws  {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            $i3 -= $r1.getHeight();
        }
        super.showAsDropDown($r1, $i0, $i3, $i1);
    }

    public void update(View $r1, int $i0, int $i4, int $i1, int $i2) throws  {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            $i4 -= $r1.getHeight();
        }
        super.update($r1, $i0, $i4, $i1, $i2);
    }

    private static void wrapOnScrollChangedListener(final PopupWindow $r0) throws  {
        try {
            final Field $r3 = PopupWindow.class.getDeclaredField("mAnchor");
            $r3.setAccessible(true);
            Field $r4 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            $r4.setAccessible(true);
            final OnScrollChangedListener $r6 = (OnScrollChangedListener) $r4.get($r0);
            $r4.set($r0, new OnScrollChangedListener() {
                public void onScrollChanged() throws  {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x001d in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r7 = this;
                    r0 = r1;
                    r1 = r11;
                    r2 = r0.get(r1);	 Catch:{ IllegalAccessException -> 0x001b }
                    r4 = r2;
                    r4 = (java.lang.ref.WeakReference) r4;
                    r3 = r4;
                    if (r3 == 0) goto L_0x001d;	 Catch:{ IllegalAccessException -> 0x001b }
                L_0x000e:
                    r2 = r3.get();	 Catch:{ IllegalAccessException -> 0x001b }
                    if (r2 != 0) goto L_0x0015;
                L_0x0014:
                    return;
                L_0x0015:
                    r5 = r6;	 Catch:{ IllegalAccessException -> 0x001b }
                    r5.onScrollChanged();	 Catch:{ IllegalAccessException -> 0x001b }
                    return;
                L_0x001b:
                    r6 = move-exception;
                    return;
                L_0x001d:
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatPopupWindow.1.onScrollChanged():void");
                }
            });
        } catch (Exception $r1) {
            Log.d(TAG, "Exception while installing workaround OnScrollChangedListener", $r1);
        }
    }

    public void setSupportOverlapAnchor(boolean $z0) throws  {
        if (COMPAT_OVERLAP_ANCHOR) {
            this.mOverlapAnchor = $z0;
        } else {
            PopupWindowCompat.setOverlapAnchor(this, $z0);
        }
    }

    public boolean getSupportOverlapAnchor() throws  {
        if (COMPAT_OVERLAP_ANCHOR) {
            return this.mOverlapAnchor;
        }
        return PopupWindowCompat.getOverlapAnchor(this);
    }
}
