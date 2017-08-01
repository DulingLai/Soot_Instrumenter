package com.waze.view.pages;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.waze.LayoutManager;

public class LinePageIndicator extends View implements PageIndicator, OnPageChangeListener {
    private static final int BG_COLOR = 1174405120;
    private static final int LINE_COLOR = 1728053247;
    private Paint _bgPaint;
    private Interpolator _endInt = new DecelerateInterpolator(0.75f);
    private int _height = 0;
    private float _indicatorEnd;
    private float _indicatorStart;
    private Paint _linePaint;
    private int _nPages;
    private RectF _rect = new RectF();
    private float _segmentSize;
    private Interpolator _startInt = new AccelerateInterpolator(0.75f);
    private ViewPager _viewPager;
    private int _width = 0;

    class C30381 implements OnGlobalLayoutListener {
        C30381() {
        }

        public void onGlobalLayout() {
            LinePageIndicator.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            LinePageIndicator.this.onPageScrolled(0, 0.0f, 0);
        }
    }

    class C30392 implements OnPageChangeListener {
        C30392() {
        }

        public void onPageSelected(int arg0) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public LinePageIndicator(Context context) {
        super(context);
        init();
    }

    public LinePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public LinePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this._bgPaint = new Paint(1);
        this._bgPaint.setStyle(Style.FILL_AND_STROKE);
        this._bgPaint.setColor(BG_COLOR);
        this._linePaint = new Paint(1);
        this._linePaint.setStyle(Style.FILL_AND_STROKE);
        this._linePaint.setColor(LINE_COLOR);
    }

    public void setViewPager(ViewPager viewPager) {
        this._viewPager = viewPager;
        setLines();
    }

    public void setViewPagerAndObserver(ViewPager viewPager) {
        setViewPager(viewPager);
        getViewTreeObserver().addOnGlobalLayoutListener(new C30381());
        viewPager.setOnPageChangeListener(new C30392());
    }

    public void setViewPager(ViewPager viewPager, int initialPosition) {
        this._viewPager = viewPager;
        setLines();
    }

    public void onPageScrollStateChanged(int arg0) {
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this._indicatorStart = ((float) position) * this._segmentSize;
        this._indicatorEnd = ((float) (position + 1)) * this._segmentSize;
        this._indicatorStart += this._startInt.getInterpolation(positionOffset) * this._segmentSize;
        this._indicatorEnd += this._endInt.getInterpolation(positionOffset) * this._segmentSize;
        invalidate();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this._width = w;
        this._height = h;
        setLines();
        if (isInEditMode()) {
            this._indicatorStart = ((float) this._width) / 5.0f;
            this._indicatorEnd = this._indicatorStart * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        }
    }

    private void setLines() {
        if (this._viewPager != null && this._width != 0 && this._height != 0) {
            this._nPages = this._viewPager.getAdapter().getCount();
            this._segmentSize = ((float) this._width) / ((float) this._nPages);
        }
    }

    public void onPageSelected(int selected) {
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this._rect.left = 0.0f;
        this._rect.right = (float) this._width;
        this._rect.top = 0.0f;
        this._rect.bottom = (float) this._height;
        canvas.drawRoundRect(this._rect, (float) (this._height / 2), (float) (this._height / 2), this._bgPaint);
        this._rect.left = this._indicatorStart;
        this._rect.right = this._indicatorEnd;
        canvas.drawRoundRect(this._rect, (float) (this._height / 2), (float) (this._height / 2), this._linePaint);
    }
}
