package com.waze.voice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.waze.utils.PixelMeasure;
import java.util.ArrayList;
import java.util.List;

public class AsrVoiceWaveView extends View {
    private static final float PASSIVE_WAVE_STRENGTH = 0.015f;
    private int mAnimationSpeed;
    private Rect mClipRect;
    private boolean mIsAnimating;
    private long mLastAddSegmentTime;
    private Paint mPaint;
    private boolean mPassiveWaveIsUp;
    private boolean mPathInitialized;
    private int mPreviousWidth;
    private int mSegmentWidth;
    private Path mWavePath;
    private List<Float> mWaveQueue;
    private WaveValueProvider mWaveValueProvider;

    public interface WaveValueProvider {
        float getCurrentValue();

        void resetSegmentWidth();
    }

    public AsrVoiceWaveView(Context context) {
        this(context, null);
    }

    public AsrVoiceWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsrVoiceWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        this.mWaveQueue = new ArrayList();
        this.mWavePath = new Path();
        this.mPaint = new Paint();
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mClipRect = new Rect();
    }

    public void start() {
        this.mIsAnimating = true;
        this.mLastAddSegmentTime = System.currentTimeMillis();
        postInvalidate();
    }

    public void stop() {
        this.mIsAnimating = false;
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void setSegmentWidth(int width) {
        this.mSegmentWidth = width;
        if (this.mPreviousWidth > 0) {
            initPath();
            generatePath();
            this.mPathInitialized = true;
            postInvalidate();
        }
    }

    public void setAnimationSpeed(int timeToAnimateSegment) {
        this.mAnimationSpeed = timeToAnimateSegment;
    }

    public void setWaveValueProvider(WaveValueProvider waveValueProvider) {
        this.mWaveValueProvider = waveValueProvider;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mPreviousWidth != getMeasuredWidth()) {
            this.mPreviousWidth = getMeasuredWidth();
            if (this.mSegmentWidth > 0) {
                this.mPassiveWaveIsUp = false;
                if (this.mPathInitialized) {
                    this.mWaveValueProvider.resetSegmentWidth();
                }
                initPath();
                generatePath();
                this.mPathInitialized = true;
                postInvalidate();
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPathInitialized && this.mIsAnimating) {
            boolean addPlotPoint;
            long currentTime = System.currentTimeMillis();
            long timeDelta = currentTime - this.mLastAddSegmentTime;
            if (timeDelta >= ((long) this.mAnimationSpeed)) {
                addPlotPoint = true;
            } else {
                addPlotPoint = false;
            }
            timeDelta %= (long) this.mAnimationSpeed;
            if (addPlotPoint) {
                addPathPoint();
                generatePath();
                this.mLastAddSegmentTime = currentTime;
            }
            float ratio = ((float) timeDelta) / ((float) this.mAnimationSpeed);
            canvas.save();
            this.mClipRect.set(getMeasuredWidth() / 2, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.clipRect(this.mClipRect);
            canvas.translate((float) (getMeasuredWidth() / 2), 0.0f);
            canvas.translate(((float) this.mSegmentWidth) * ratio, 0.0f);
            canvas.drawPath(this.mWavePath, this.mPaint);
            canvas.restore();
            canvas.save();
            this.mClipRect.set(0, 0, getMeasuredWidth() / 2, getMeasuredHeight());
            canvas.clipRect(this.mClipRect);
            canvas.scale(-1.0f, 1.0f);
            canvas.translate((float) ((-getMeasuredWidth()) / 2), 0.0f);
            canvas.translate(((float) this.mSegmentWidth) * ratio, 0.0f);
            canvas.drawPath(this.mWavePath, this.mPaint);
            canvas.restore();
            if (this.mIsAnimating) {
                postInvalidate();
            }
        }
    }

    private void initPath() {
        int measuredWidth = getMeasuredWidth();
        int width = 0;
        while (width <= this.mSegmentWidth + measuredWidth) {
            this.mWaveQueue.add(Float.valueOf(0.0f));
            width += this.mSegmentWidth;
        }
    }

    private void addPathPoint() {
        this.mWaveQueue.remove(this.mWaveQueue.size() - 1);
        this.mWaveQueue.add(0, Float.valueOf(this.mWaveValueProvider.getCurrentValue()));
    }

    private void generatePath() {
        int minimumHeight = PixelMeasure.dp(8);
        int measuredHeight = getMeasuredHeight() - minimumHeight;
        int totalWidth = getMeasuredWidth();
        float currentHeight = (float) (getMeasuredHeight() - minimumHeight);
        int currentX = -this.mSegmentWidth;
        this.mWavePath.reset();
        for (int i = 0; i < this.mWaveQueue.size(); i++) {
            float currentRatio = ((Float) this.mWaveQueue.get(i)).floatValue();
            if (currentRatio == 0.0f) {
                currentRatio = this.mPassiveWaveIsUp ? PASSIVE_WAVE_STRENGTH : -0.015f;
            }
            this.mPassiveWaveIsUp = !this.mPassiveWaveIsUp;
            float previousHeight = currentHeight;
            currentHeight = ((float) getMeasuredHeight()) - (((float) minimumHeight) + (((float) measuredHeight) * currentRatio));
            if (i == 0) {
                this.mWavePath.moveTo((float) currentX, currentHeight);
            } else if (currentX < totalWidth / 2) {
                currentX += this.mSegmentWidth;
                this.mWavePath.cubicTo(((float) currentX) - (((float) this.mSegmentWidth) * 0.66f), previousHeight, ((float) currentX) - (((float) this.mSegmentWidth) * 0.33f), currentHeight, (float) currentX, currentHeight);
            }
        }
        this.mWavePath.lineTo((float) currentX, (float) getMeasuredHeight());
        this.mWavePath.lineTo((float) (-this.mSegmentWidth), (float) getMeasuredHeight());
        this.mWavePath.close();
    }
}
