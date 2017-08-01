package com.waze.view.timer;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.R;
import java.util.Date;

public class TimerView extends RelativeLayout {
    private static final int TIMER_TICK = 125;
    protected boolean expired = false;
    private Handler handler = new Handler();
    private LayoutInflater inflater;
    protected boolean shouldStop = false;
    private boolean showTime = true;
    private Date startTime;
    private int time = -1;
    private TimerCircle timerCircle;
    private TextView timerText;
    private Runnable updateTimeTask = new C32741();

    class C32741 implements Runnable {
        C32741() {
        }

        public void run() {
            long now = System.currentTimeMillis();
            float ratio = 1.0f - (((float) (now - TimerView.this.startTime.getTime())) / (((float) TimerView.this.time) * 1000.0f));
            if (ratio < 0.0f) {
                ratio = 0.0f;
            }
            int curTime = (int) (((long) TimerView.this.time) - ((now - TimerView.this.startTime.getTime()) / 1000));
            if (curTime <= 0) {
                TimerView.this.setVisibility(8);
            }
            TimerView.this.timerText.setText("" + curTime);
            TimerView.this.timerCircle.setRatio(ratio);
            TimerView.this.timerCircle.invalidate();
            if (TimerView.this.shouldStop) {
                TimerView.this.setVisibility(8);
            } else if (ratio > 0.0f) {
                TimerView.this.handler.postDelayed(this, 125);
            } else {
                TimerView.this.expired = true;
                ((View) TimerView.this.getParent()).performClick();
            }
        }
    }

    public TimerView(Context context, AttributeSet attrs) {
        int i;
        super(context, attrs);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.TimerView);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.timer, this);
        this.timerCircle = (TimerCircle) findViewById(C1283R.id.timerCircle);
        this.timerText = (TextView) findViewById(C1283R.id.timerText);
        this.time = attrArray.getInt(0, -1);
        this.showTime = attrArray.getBoolean(1, this.showTime);
        if (attrArray.hasValue(2)) {
            this.timerCircle.setColor(attrArray.getColor(2, 0));
        }
        if (!isInEditMode() && (this.time <= 0 || this.shouldStop)) {
            setVisibility(8);
        }
        this.timerText.setText("" + this.time);
        TextView textView = this.timerText;
        if (this.showTime) {
            i = 0;
        } else {
            i = 8;
        }
        textView.setVisibility(i);
        this.timerCircle.invalidate();
    }

    public TimerView setTime(int seconds) {
        this.time = seconds;
        if (this.time > 0 && !this.shouldStop) {
            setVisibility(0);
        }
        return this;
    }

    public void setWhiteColor() {
        this.timerText = (TextView) findViewById(C1283R.id.timerText);
        this.timerText.setTextColor(-1);
        ((ImageView) findViewById(C1283R.id.timerBg)).setImageResource(C1283R.drawable.timer_bg_dark);
    }

    public void setGrayColor() {
        this.timerText = (TextView) findViewById(C1283R.id.timerText);
        this.timerText.setTextColor(getContext().getResources().getColor(C1283R.color.GrayTimerColor));
    }

    public TimerView stop() {
        this.shouldStop = true;
        setVisibility(8);
        return this;
    }

    public TimerView start() {
        this.startTime = new Date();
        this.handler.postDelayed(this.updateTimeTask, 0);
        return this;
    }

    public TimerView reset() {
        this.shouldStop = false;
        return this;
    }

    public void setTimeColor(int color) {
        this.timerCircle.setColor(color);
    }

    public boolean hasExpired() {
        return this.expired;
    }
}
