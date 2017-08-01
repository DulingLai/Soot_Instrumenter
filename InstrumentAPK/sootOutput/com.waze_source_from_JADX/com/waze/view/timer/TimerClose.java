package com.waze.view.timer;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.waze.C1283R;
import com.waze.R;
import java.util.Date;

public class TimerClose extends RelativeLayout {
    private static final int TIMER_TICK = 125;
    protected boolean expired = false;
    private Handler handler = new Handler();
    private LayoutInflater inflater;
    protected boolean shouldStop = false;
    private Date startTime;
    private int time = -1;
    private TimerCircle timerCircle;
    private Runnable updateTimeTask = new C32731();

    class C32731 implements Runnable {
        C32731() {
        }

        public void run() {
            float ratio = 1.0f - (((float) (System.currentTimeMillis() - TimerClose.this.startTime.getTime())) / (((float) TimerClose.this.time) * 1000.0f));
            if (ratio < 0.0f) {
                ratio = 0.0f;
            }
            TimerClose.this.timerCircle.setRatio(ratio);
            TimerClose.this.timerCircle.invalidate();
            if (!TimerClose.this.shouldStop) {
                if (ratio > 0.0f) {
                    TimerClose.this.handler.postDelayed(this, 125);
                    return;
                }
                TimerClose.this.expired = true;
                TimerClose.this.performClick();
            }
        }
    }

    public TimerClose(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.TimerView);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.timer_close, this);
        this.timerCircle = (TimerCircle) findViewById(C1283R.id.timerCloseCircle);
        this.time = attrArray.getInt(0, -1);
        this.timerCircle.invalidate();
    }

    public TimerClose setTime(int seconds) {
        this.time = seconds;
        if (this.time > 0 && !this.shouldStop) {
            setVisibility(0);
        }
        return this;
    }

    public TimerClose stop() {
        this.shouldStop = true;
        return this;
    }

    public TimerClose start() {
        this.startTime = new Date();
        this.handler.postDelayed(this.updateTimeTask, 0);
        return this;
    }

    public TimerClose reset() {
        this.shouldStop = false;
        return this;
    }

    public boolean hasExpired() {
        return this.expired;
    }
}
