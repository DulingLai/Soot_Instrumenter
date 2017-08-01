package com.waze.ticker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.waze.C1283R;

public class Ticker extends RelativeLayout {
    private LayoutInflater inflater;

    public Ticker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.message_ticker, this);
    }
}
