package com.waze.notificationbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.waze.C1283R;

public class NotifBar extends RelativeLayout {
    private LayoutInflater inflater;

    public NotifBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.notification_bar, this);
    }
}
