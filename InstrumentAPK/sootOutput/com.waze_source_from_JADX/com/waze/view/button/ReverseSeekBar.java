package com.waze.view.button;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
import com.waze.LayoutManager;

public class ReverseSeekBar extends SeekBar {
    public ReverseSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas c) {
        c.scale(-1.0f, 1.0f, ((float) getWidth()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, ((float) getHeight()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        super.onDraw(c);
    }

    public boolean onTouchEvent(MotionEvent event) {
        event.setLocation(((float) getWidth()) - event.getX(), event.getY());
        return super.onTouchEvent(event);
    }
}
