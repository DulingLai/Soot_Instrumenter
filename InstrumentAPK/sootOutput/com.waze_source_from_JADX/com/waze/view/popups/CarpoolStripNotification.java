package com.waze.view.popups;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.utils.PixelMeasure;

public class CarpoolStripNotification extends GenericNotification {

    class C30921 implements Runnable {
        C30921() {
        }

        public void run() {
            AppService.getNativeManager().TickerClosedNTV();
        }
    }

    class C30932 implements OnClickListener {
        C30932() {
        }

        public void onClick(View v) {
            CarpoolStripNotification.this.onYes();
        }
    }

    class C30943 implements OnClickListener {
        C30943() {
        }

        public void onClick(View v) {
            CarpoolStripNotification.this.onNo();
        }
    }

    class C30954 implements Runnable {
        C30954() {
        }

        public void run() {
            CarpoolStripNotification.this.hide();
        }
    }

    class C30976 implements Runnable {
        C30976() {
        }

        public void run() {
        }
    }

    class C30987 implements Runnable {
        C30987() {
        }

        public void run() {
        }
    }

    class C30998 implements Runnable {
        C30998() {
        }

        public void run() {
        }
    }

    public CarpoolStripNotification(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
    }

    public void hide() {
        NativeManager.Post(new C30921());
        super.hide();
    }

    public void show(String title, String text, String iconName, int timeout) {
        show(title, text, iconName, timeout, null, null, false, false);
    }

    public void show(String title, String text, String iconName, int timeout, final OnClickListener onYes, OnClickListener onNo, boolean timeoutNo, final boolean touchYes) {
        if (iconName == null || iconName.length() <= 0) {
            setIcon(null);
        } else {
            setIcon(ResManager.GetSkinDrawable(iconName + ResManager.mImageExtension));
        }
        TextView textTv = (TextView) findViewById(C1283R.id.genNotificationText);
        textTv.setTextSize(2, 18.0f);
        ((TextView) findViewById(C1283R.id.genNotificationTitle)).setTextSize(2, 18.0f);
        setText(text);
        setTitle(title);
        ((LayoutParams) textTv.getLayoutParams()).bottomMargin = PixelMeasure.dp(16);
        setButton1(C1283R.drawable.accessory_icon_white, "", true, onYes != null ? onYes : new C30932());
        String str = "";
        if (onNo == null) {
            onNo = new C30943();
        }
        setButton2(C1283R.drawable.close_icon_grey, str, true, onNo);
        if (timeoutNo) {
            setCloseTimerButton2(timeout * 1000);
        } else {
            postDelayed(new C30954(), (long) (timeout * 1000));
        }
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    if (!touchYes) {
                        CarpoolStripNotification.this.hide();
                    } else if (onYes != null) {
                        onYes.onClick(v);
                    } else {
                        CarpoolStripNotification.this.onYes();
                    }
                }
                return true;
            }
        });
        super.show();
    }

    public boolean onBackPressed() {
        onClose();
        return super.onBackPressed();
    }

    private void onYes() {
        NativeManager.Post(new C30976());
        hide();
    }

    private void onNo() {
        NativeManager.Post(new C30987());
        hide();
    }

    private void onClose() {
        NativeManager.Post(new C30998());
        hide();
    }
}
