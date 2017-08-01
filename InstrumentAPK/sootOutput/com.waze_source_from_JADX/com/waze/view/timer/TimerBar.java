package com.waze.view.timer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import com.waze.R;
import com.waze.map.CanvasFont;
import com.waze.weblink.WeblinkManager;

public class TimerBar extends View {
    public static final int LOCATION_BOTTOM = 3;
    public static final int LOCATION_LEFT = 1;
    public static final int LOCATION_NONE = 0;
    public static final int LOCATION_RIGHT = 4;
    public static final int LOCATION_TOP = 2;
    private static final int TIMER_TICK = 25;
    private Paint _alphaPaint;
    private int _bottom;
    private Paint _clearPaint;
    private int _cornerRadii = 0;
    protected boolean _expired = false;
    private int _left;
    private int _location = 0;
    private RectF _mask;
    private int _maskEdge;
    private float _ratio = 1.0f;
    private float _realHeight;
    private float _realWidth;
    private int _right;
    protected boolean _shouldStop = false;
    private long _startTime;
    private int _thick = -1;
    private int _time = -1;
    private int _timeLeftColor = 0;
    private Paint _timePaint;
    private int _top;
    private int _trackColor = 0;
    private Paint _trackPaint;
    private Handler handler = new Handler();
    private Runnable mOnEndRunnable;
    private Runnable updateTimeTask = new C32721();

    class C32721 implements Runnable {
        C32721() {
        }

        public void run() {
            long now = System.currentTimeMillis();
            TimerBar.this._ratio = 1.0f - (((float) (now - TimerBar.this._startTime)) / (((float) TimerBar.this._time) * 1000.0f));
            if (TimerBar.this._ratio < 0.0f) {
                TimerBar.this._ratio = 0.0f;
            }
            if (((int) (((long) TimerBar.this._time) - ((now - TimerBar.this._startTime) / 1000))) <= 0) {
                TimerBar.this.makeGone(true);
            }
            if (!TimerBar.this._shouldStop) {
                if (TimerBar.this._ratio > 0.0f) {
                    TimerBar.this.handler.postDelayed(this, 25);
                } else {
                    TimerBar.this._expired = true;
                    ((View) TimerBar.this.getParent()).performClick();
                    if (TimerBar.this.mOnEndRunnable != null) {
                        TimerBar.this.mOnEndRunnable.run();
                    }
                }
            }
            TimerBar.this.invalidate();
        }
    }

    public TimerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.TimerBar);
        this._time = attrArray.getInt(5, -1);
        this._cornerRadii = attrArray.getDimensionPixelSize(4, 0);
        this._thick = attrArray.getDimensionPixelSize(0, 0);
        this._timeLeftColor = attrArray.getColor(2, -10790053);
        this._trackColor = attrArray.getColor(1, -14013652);
        this._location = attrArray.getInt(3, 3);
        if (this._time <= 0 || this._shouldStop) {
            makeGone(false);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this._left = getPaddingLeft();
        int padRight = getPaddingRight();
        float xpad = (float) (this._left + padRight);
        int padBottom = getPaddingBottom();
        this._top = getPaddingTop();
        float ypad = (float) (this._top + padBottom);
        this._realWidth = ((float) w) - xpad;
        this._realHeight = ((float) h) - ypad;
        this._bottom = h - padBottom;
        this._right = w - padRight;
        if (this._location == 3) {
            this._maskEdge = (this._bottom - this._cornerRadii) - Math.max(this._cornerRadii, this._thick);
            this._mask = new RectF((float) this._left, (float) this._maskEdge, (float) this._right, (float) this._bottom);
        } else if (this._location == 2) {
            this._maskEdge = (this._top + this._cornerRadii) + Math.max(this._cornerRadii, this._thick);
            this._mask = new RectF((float) this._left, (float) this._top, (float) this._right, (float) this._maskEdge);
        } else if (this._location == 1) {
            this._maskEdge = (this._left + this._cornerRadii) + Math.max(this._cornerRadii, this._thick);
            this._mask = new RectF((float) this._left, (float) this._top, (float) this._maskEdge, (float) this._bottom);
        } else if (this._location == 4) {
            this._maskEdge = (this._right - this._cornerRadii) - Math.max(this._cornerRadii, this._thick);
            this._mask = new RectF((float) this._maskEdge, (float) this._top, (float) this._right, (float) this._bottom);
        }
        this._trackPaint = new Paint();
        this._trackPaint.setColor(this._trackColor);
        this._trackPaint.setAntiAlias(true);
        this._trackPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        this._timePaint = new Paint();
        this._timePaint.setColor(this._timeLeftColor);
        this._timePaint.setAntiAlias(true);
        this._timePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        this._alphaPaint = new Paint();
        this._alphaPaint.setAntiAlias(true);
        this._alphaPaint.setColor(-1);
        this._clearPaint = new Paint();
        this._clearPaint.setColor(-1);
        this._clearPaint.setAntiAlias(true);
        this._clearPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        setLayerType(1, null);
        if (isInEditMode()) {
            this._ratio = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }
    }

    public void setOnEndRunnable(Runnable onEndRunnable) {
        this.mOnEndRunnable = onEndRunnable;
    }

    public void setTime(int seconds) {
        this._time = seconds;
        if (this._time > 0 && !this._shouldStop) {
            setVisibility(0);
            clearAnimation();
        }
    }

    public void stop() {
        this.handler.removeCallbacks(this.updateTimeTask);
        if (this._shouldStop || this._startTime <= 0) {
            makeGone(false);
            return;
        }
        this._shouldStop = true;
        makeGone(true);
    }

    public void start() {
        this._startTime = System.currentTimeMillis();
        this.handler.postDelayed(this.updateTimeTask, 0);
    }

    public void reset() {
        this.handler.removeCallbacks(this.updateTimeTask);
        this._shouldStop = false;
        if (this._time > 0) {
            setVisibility(0);
            clearAnimation();
        }
    }

    private void makeGone(boolean animate) {
        if (!animate || WeblinkManager.getInstance().isConnectedToClient()) {
            setVisibility(8);
            return;
        }
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setDuration(100);
        aa.setFillAfter(true);
        startAnimation(aa);
    }

    public boolean hasExpired() {
        return this._expired;
    }

    protected void onDraw(Canvas canvas) {
        if (this._mask != null) {
            if (!(this._mask == null || this._alphaPaint == null)) {
                canvas.drawRoundRect(this._mask, (float) this._cornerRadii, (float) this._cornerRadii, this._alphaPaint);
            }
            Canvas canvas2;
            if (this._location == 3) {
                canvas2 = canvas;
                canvas2.drawRect((this._realWidth * this._ratio) + ((float) this._left), (float) (this._bottom - this._thick), (float) (this._right + 1), (float) this._bottom, this._trackPaint);
                canvas2 = canvas;
                canvas2.drawRect((float) this._left, (float) (this._bottom - this._thick), (this._realWidth * this._ratio) + ((float) this._left), (float) this._bottom, this._timePaint);
                canvas.drawRect((float) this._left, (float) this._maskEdge, (float) this._right, (float) (this._bottom - this._thick), this._clearPaint);
            } else if (this._location == 2) {
                canvas2 = canvas;
                canvas2.drawRect((this._realWidth * this._ratio) + ((float) this._left), (float) this._top, (float) this._right, (float) (this._top + this._thick), this._trackPaint);
                canvas2 = canvas;
                canvas2.drawRect((float) this._left, (float) this._top, (this._realWidth * this._ratio) + ((float) this._left), (float) (this._top + this._thick), this._timePaint);
                canvas.drawRect((float) this._left, (float) (this._top + this._thick), (float) this._right, (float) this._maskEdge, this._clearPaint);
            } else if (this._location == 1) {
                canvas2 = canvas;
                canvas2.drawRect((float) this._left, (float) this._top, (float) (this._left + this._thick), (this._realHeight * this._ratio) + ((float) this._top), this._trackPaint);
                canvas2 = canvas;
                canvas2.drawRect((float) this._left, (this._realHeight * this._ratio) + ((float) this._top), (float) (this._left + this._thick), (float) (this._bottom + 1), this._timePaint);
                canvas.drawRect((float) (this._left + this._thick), (float) this._top, (float) this._maskEdge, (float) this._bottom, this._clearPaint);
            } else if (this._location == 4) {
                canvas2 = canvas;
                canvas2.drawRect((float) (this._right - this._thick), (float) this._top, (float) this._right, (this._realHeight * this._ratio) + ((float) this._top), this._trackPaint);
                canvas2 = canvas;
                canvas2.drawRect((float) (this._right - this._thick), (this._realHeight * this._ratio) + ((float) this._top), (float) this._right, (float) (this._bottom + 1), this._timePaint);
                canvas.drawRect((float) this._maskEdge, (float) this._top, (float) (this._right - this._thick), (float) this._bottom, this._clearPaint);
            }
        }
    }

    public void setTrackColor(int color) {
        this._trackColor = color;
        if (this._trackPaint != null) {
            this._trackPaint.setColor(this._trackColor);
        }
    }

    public void setTimeLeftColor(int color) {
        this._timeLeftColor = color;
        if (this._timePaint != null) {
            this._timePaint.setColor(this._timeLeftColor);
        }
    }
}
