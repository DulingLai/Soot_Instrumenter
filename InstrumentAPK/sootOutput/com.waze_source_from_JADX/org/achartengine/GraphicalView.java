package org.achartengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import com.waze.LayoutManager;
import com.waze.view.text.AutoResizeTextView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.tools.FitZoom;
import org.achartengine.tools.Pan;
import org.achartengine.tools.Zoom;

public class GraphicalView extends View {
    private static final int ZOOM_BUTTONS_COLOR = Color.argb(175, 150, 150, 150);
    private static final int ZOOM_SIZE = 45;
    private FitZoom fitZoom;
    private Bitmap fitZoomImage;
    private AbstractChart mChart;
    private Handler mHandler;
    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();
    private XYMultipleSeriesRenderer mRenderer;
    private float oldX;
    private float oldX2;
    private float oldY;
    private float oldY2;
    private Pan pan;
    private Zoom pinchZoom;
    private Zoom zoomIn;
    private Bitmap zoomInImage;
    private Zoom zoomOut;
    private Bitmap zoomOutImage;
    private RectF zoomR = new RectF();

    class C34451 implements Runnable {
        C34451() {
        }

        public void run() {
            GraphicalView.this.invalidate();
        }
    }

    public GraphicalView(Context context, AbstractChart abstractChart) {
        super(context);
        this.mChart = abstractChart;
        this.mHandler = new Handler();
        if (this.mChart instanceof XYChart) {
            this.zoomInImage = BitmapFactory.decodeStream(getClass().getResourceAsStream("image/zoom_in.png"));
            this.zoomOutImage = BitmapFactory.decodeStream(getClass().getResourceAsStream("image/zoom_out.png"));
            this.fitZoomImage = BitmapFactory.decodeStream(getClass().getResourceAsStream("image/zoom-1.png"));
            this.mRenderer = ((XYChart) this.mChart).getRenderer();
            if (this.mRenderer.getMarginsColor() == 0) {
                this.mRenderer.setMarginsColor(this.mPaint.getColor());
            }
            if (this.mRenderer.isPanXEnabled() || this.mRenderer.isPanYEnabled()) {
                this.pan = new Pan((XYChart) this.mChart);
            }
            if (this.mRenderer.isZoomXEnabled() || this.mRenderer.isZoomYEnabled()) {
                this.zoomIn = new Zoom((XYChart) this.mChart, true, this.mRenderer.getZoomRate());
                this.zoomOut = new Zoom((XYChart) this.mChart, false, this.mRenderer.getZoomRate());
                this.fitZoom = new FitZoom((XYChart) this.mChart);
                this.pinchZoom = new Zoom((XYChart) this.mChart, true, 1.0f);
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.getClipBounds(this.mRect);
        int i = this.mRect.top;
        int i2 = this.mRect.left;
        int width = this.mRect.width();
        int height = this.mRect.height();
        this.mChart.draw(canvas, i2, i, width, height, this.mPaint);
        if (this.mRenderer == null) {
            return;
        }
        if (this.mRenderer.isZoomXEnabled() || this.mRenderer.isZoomYEnabled()) {
            this.mPaint.setColor(ZOOM_BUTTONS_COLOR);
            this.zoomR.set((float) ((i2 + width) - 135), ((float) (i + height)) - 34.875f, (float) (i2 + width), (float) (i + height));
            canvas.drawRoundRect(this.zoomR, AutoResizeTextView.MIN_TEXT_SIZE, AutoResizeTextView.MIN_TEXT_SIZE, this.mPaint);
            float f = ((float) (i + height)) - 28.125f;
            canvas.drawBitmap(this.zoomInImage, ((float) (i2 + width)) - 123.75f, f, null);
            canvas.drawBitmap(this.zoomOutImage, ((float) (i2 + width)) - 78.75f, f, null);
            canvas.drawBitmap(this.fitZoomImage, ((float) (i2 + width)) - 33.75f, f, null);
        }
    }

    public void handleTouch(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.mRenderer == null || action != 2) {
            if (action == 0) {
                this.oldX = motionEvent.getX(0);
                this.oldY = motionEvent.getY(0);
                if (this.mRenderer == null) {
                    return;
                }
                if ((!this.mRenderer.isZoomXEnabled() && !this.mRenderer.isZoomYEnabled()) || !this.zoomR.contains(this.oldX, this.oldY)) {
                    return;
                }
                if (this.oldX < this.zoomR.left + (this.zoomR.width() / 3.0f)) {
                    this.zoomIn.apply();
                } else if (this.oldX < this.zoomR.left + ((this.zoomR.width() * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) / 3.0f)) {
                    this.zoomOut.apply();
                } else {
                    this.fitZoom.apply();
                }
            } else if (action == 1 || action == 6) {
                this.oldX = 0.0f;
                this.oldY = 0.0f;
                this.oldX2 = 0.0f;
                this.oldY2 = 0.0f;
                if (action == 6) {
                    this.oldX = -1.0f;
                    this.oldY = -1.0f;
                }
            }
        } else if (this.oldX >= 0.0f || this.oldY >= 0.0f) {
            float x = motionEvent.getX(0);
            float y = motionEvent.getY(0);
            if (motionEvent.getPointerCount() > 1 && ((this.oldX2 >= 0.0f || this.oldY2 >= 0.0f) && (this.mRenderer.isZoomXEnabled() || this.mRenderer.isZoomYEnabled()))) {
                float x2 = motionEvent.getX(1);
                float y2 = motionEvent.getY(1);
                float abs = Math.abs(x - x2);
                float abs2 = Math.abs(y - y2);
                float abs3 = Math.abs(this.oldX - this.oldX2);
                float abs4 = Math.abs(this.oldY - this.oldY2);
                if (Math.abs(x - this.oldX) >= Math.abs(y - this.oldY)) {
                    abs /= abs3;
                } else {
                    abs = abs2 / abs4;
                }
                if (((double) abs) > 0.909d && ((double) abs) < 1.1d) {
                    this.pinchZoom.setZoomRate(abs);
                    this.pinchZoom.apply();
                }
                this.oldX2 = x2;
                this.oldY2 = y2;
            } else if (this.mRenderer.isPanXEnabled() || this.mRenderer.isPanYEnabled()) {
                this.pan.apply(this.oldX, this.oldY, x, y);
                this.oldX2 = 0.0f;
                this.oldY2 = 0.0f;
            }
            this.oldX = x;
            this.oldY = y;
            repaint();
        }
    }

    public void setZoomRate(float f) {
        if (this.zoomIn != null && this.zoomOut != null) {
            this.zoomIn.setZoomRate(f);
            this.zoomOut.setZoomRate(f);
        }
    }

    public void zoomIn() {
        if (this.zoomIn != null) {
            this.zoomIn.apply();
            repaint();
        }
    }

    public void zoomOut() {
        if (this.zoomOut != null) {
            this.zoomOut.apply();
            repaint();
        }
    }

    public void zoomReset() {
        if (this.fitZoom != null) {
            this.fitZoom.apply();
            repaint();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mRenderer == null || (!this.mRenderer.isPanXEnabled() && !this.mRenderer.isPanYEnabled() && !this.mRenderer.isZoomXEnabled() && !this.mRenderer.isZoomYEnabled())) {
            return super.onTouchEvent(motionEvent);
        }
        handleTouch(motionEvent);
        return true;
    }

    public void repaint() {
        this.mHandler.post(new C34451());
    }

    public void repaint(int i, int i2, int i3, int i4) {
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        this.mHandler.post(new Runnable() {
            public void run() {
                GraphicalView.this.invalidate(i5, i6, i7, i8);
            }
        });
    }
}
