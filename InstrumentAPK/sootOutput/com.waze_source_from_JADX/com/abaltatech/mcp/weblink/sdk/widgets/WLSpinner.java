package com.abaltatech.mcp.weblink.sdk.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.Spinner;
import com.abaltatech.mcp.weblink.sdk.WEBLINK;
import com.abaltatech.mcp.weblink.sdk.WLDisplayManager;
import com.abaltatech.mcp.weblink.sdk.WLMirrorMode;

public class WLSpinner extends Spinner {
    public WLSpinner(Context $r1) throws  {
        super($r1);
        init();
    }

    public WLSpinner(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        init();
    }

    public WLSpinner(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    public WLSpinner(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super($r1, $r2, $i0, $i1);
        init();
    }

    public WLSpinner(Context $r1, int $i0) throws  {
        super($r1, $i0);
        init();
    }

    private void init() throws  {
    }

    public void getWindowVisibleDisplayFrame(Rect $r1) throws  {
        int $i0;
        int $i1;
        super.getWindowVisibleDisplayFrame($r1);
        StackTraceElement[] $r4 = Thread.currentThread().getStackTrace();
        boolean $z0 = false;
        boolean $z1 = false;
        for (StackTraceElement $r2 : $r4) {
            if ($z0) {
                if ($r2.getMethodName().compareTo("getMaxAvailableHeight") == 0) {
                    $z1 = true;
                }
                $i1 = WEBLINK.instance.getUiMode();
                $z0 = (VERSION.SDK_INT > 18 && $i1 == 2) || $i1 == 3;
                if ($z0) {
                    $i0 = $i1 != 3 ? WLMirrorMode.instance.getHeight() : WLDisplayManager.getInstance().getLastDisplayHeight();
                    $i1 = $i1 != 3 ? WLMirrorMode.instance.getWidth() : WLDisplayManager.getInstance().getLastDisplayWidth();
                    if ($z1) {
                        $r1.left = 0;
                        $r1.top = 10;
                    } else {
                        $r1.left = 4000;
                        $r1.top = 4010;
                    }
                    $r1.right = $r1.left + $i1;
                    $r1.bottom = ($r1.top + $i0) - 10;
                }
            }
            if ($r2.getMethodName().compareTo("getWindowVisibleDisplayFrame") == 0) {
                $z0 = true;
            }
        }
        $i1 = WEBLINK.instance.getUiMode();
        if (VERSION.SDK_INT > 18) {
        }
        if ($z0) {
            if ($i1 != 3) {
            }
            if ($i1 != 3) {
            }
            if ($z1) {
                $r1.left = 0;
                $r1.top = 10;
            } else {
                $r1.left = 4000;
                $r1.top = 4010;
            }
            $r1.right = $r1.left + $i1;
            $r1.bottom = ($r1.top + $i0) - 10;
        }
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
    }
}
