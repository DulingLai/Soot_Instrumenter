package com.abaltatech.mcp.weblink.sdk.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class WLNotDrawableLinearLayout extends LinearLayout implements IWLNotDrawable {
    private static boolean ms_internalDraw;

    public WLNotDrawableLinearLayout(Context $r1) throws  {
        super($r1);
    }

    public WLNotDrawableLinearLayout(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public WLNotDrawableLinearLayout(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    public static void setDrawing(boolean $z0) throws  {
        ms_internalDraw = $z0;
    }

    protected void dispatchDraw(Canvas $r1) throws  {
        if (!ms_internalDraw) {
            super.dispatchDraw($r1);
        }
    }
}
