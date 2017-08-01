package com.waze.view.popups;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.waze.C1283R;

public class BaseBottomDialog extends Dialog {
    public BaseBottomDialog(Context context) {
        super(context, C1283R.style.SlideUpDialog);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        setCanceledOnTouchOutside(true);
    }

    private void initLayout() {
        LayoutParams lp = new LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.gravity = 80;
        lp.height = -2;
        lp.width = -1;
        window.setAttributes(lp);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            Rect dialogBounds = new Rect();
            getWindow().getDecorView().getHitRect(dialogBounds);
            if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
                onTouchOutside();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    void onTouchOutside() {
        cancel();
    }
}
