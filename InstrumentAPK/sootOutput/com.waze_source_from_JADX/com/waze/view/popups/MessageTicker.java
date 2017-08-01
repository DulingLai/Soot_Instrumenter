package com.waze.view.popups;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.ResManager;

public class MessageTicker extends GenericNotification {

    class C31691 implements Runnable {
        C31691() {
        }

        public void run() {
            AppService.getNativeManager().TickerClosedNTV();
        }
    }

    class C31702 implements OnClickListener {
        C31702() {
        }

        public void onClick(View v) {
            MessageTicker.this.hide();
        }
    }

    class C31713 implements Runnable {
        C31713() {
        }

        public void run() {
            MessageTicker.this.hide();
        }
    }

    class C31724 implements OnTouchListener {
        C31724() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            MessageTicker.this.hide();
            return true;
        }
    }

    public MessageTicker(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
    }

    public void hide() {
        NativeManager.Post(new C31691());
        super.hide();
    }

    public void show(String title, String text, String iconName, int timeout) {
        if (iconName == null || iconName.length() <= 0) {
            setIcon(null);
        } else {
            setIcon(ResManager.GetSkinDrawable(iconName + ResManager.mImageExtension));
        }
        setText(text);
        setButton1(C1283R.drawable.accessory_icon_blue, "", true, new C31702());
        setButton2Gone();
        postDelayed(new C31713(), (long) (timeout * 1000));
        setOnTouchListener(new C31724());
        super.show();
    }
}
