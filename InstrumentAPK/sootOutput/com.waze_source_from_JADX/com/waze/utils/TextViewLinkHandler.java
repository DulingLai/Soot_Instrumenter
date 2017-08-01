package com.waze.utils;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public abstract class TextViewLinkHandler extends LinkMovementMethod {
    public abstract void onLinkClick(String str);

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (event.getAction() != 1) {
            return super.onTouchEvent(widget, buffer, event);
        }
        int x = (((int) event.getX()) - widget.getTotalPaddingLeft()) + widget.getScrollX();
        int y = (((int) event.getY()) - widget.getTotalPaddingTop()) + widget.getScrollY();
        Layout layout = widget.getLayout();
        int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
        URLSpan[] link = (URLSpan[]) buffer.getSpans(off, off, URLSpan.class);
        if (link.length == 0) {
            return true;
        }
        onLinkClick(link[0].getURL());
        return true;
    }
}
