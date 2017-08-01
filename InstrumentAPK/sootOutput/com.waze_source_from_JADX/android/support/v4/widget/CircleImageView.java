package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import com.waze.LayoutManager;

class CircleImageView extends ImageView {
    private static final int FILL_SHADOW_COLOR = 1023410176;
    private static final int KEY_SHADOW_COLOR = 503316480;
    private static final int SHADOW_ELEVATION = 4;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 1.75f;
    private AnimationListener mListener;
    private int mShadowRadius;

    private class OvalShadow extends OvalShape {
        private int mCircleDiameter;
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint = new Paint();

        public OvalShadow(int $i0, int $i1) throws  {
            OvalShape ovalShape = this;
            CircleImageView.this.mShadowRadius = $i0;
            this.mCircleDiameter = $i1;
            this.mRadialGradient = new RadialGradient((float) (this.mCircleDiameter / 2), (float) (this.mCircleDiameter / 2), (float) CircleImageView.this.mShadowRadius, new int[]{CircleImageView.FILL_SHADOW_COLOR, 0}, null, TileMode.CLAMP);
            this.mShadowPaint.setShader(this.mRadialGradient);
        }

        public void draw(Canvas $r1, Paint $r2) throws  {
            int $i0 = CircleImageView.this.getWidth();
            int $i1 = CircleImageView.this.getHeight();
            $r1.drawCircle((float) ($i0 / 2), (float) ($i1 / 2), (float) ((this.mCircleDiameter / 2) + CircleImageView.this.mShadowRadius), this.mShadowPaint);
            $r1.drawCircle((float) ($i0 / 2), (float) ($i1 / 2), (float) (this.mCircleDiameter / 2), $r2);
        }
    }

    public CircleImageView(Context $r1, int $i0, float $f0) throws  {
        ShapeDrawable $r5;
        super($r1);
        float $f1 = getContext().getResources().getDisplayMetrics().density;
        float $f02 = ($f0 * $f1) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        $f0 = $f02;
        int $i1 = (int) $f02;
        int $i3 = (int) (Y_OFFSET * $f1);
        int $i2 = (int) (0.0f * $f1);
        this.mShadowRadius = (int) (SHADOW_RADIUS * $f1);
        if (elevationSupported()) {
            $r5 = new ShapeDrawable(new OvalShape());
            ViewCompat.setElevation(this, 4.0f * $f1);
        } else {
            $r5 = new ShapeDrawable(new OvalShadow(this.mShadowRadius, $i1));
            ViewCompat.setLayerType(this, 1, $r5.getPaint());
            $r5.getPaint().setShadowLayer((float) this.mShadowRadius, (float) $i2, (float) $i3, KEY_SHADOW_COLOR);
            $i2 = this.mShadowRadius;
            setPadding($i2, $i2, $i2, $i2);
        }
        $r5.getPaint().setColor($i0);
        setBackgroundDrawable($r5);
    }

    private boolean elevationSupported() throws  {
        return VERSION.SDK_INT >= 21;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        super.onMeasure($i0, $i1);
        if (!elevationSupported()) {
            setMeasuredDimension(getMeasuredWidth() + (this.mShadowRadius * 2), getMeasuredHeight() + (this.mShadowRadius * 2));
        }
    }

    public void setAnimationListener(AnimationListener $r1) throws  {
        this.mListener = $r1;
    }

    public void onAnimationStart() throws  {
        super.onAnimationStart();
        if (this.mListener != null) {
            this.mListener.onAnimationStart(getAnimation());
        }
    }

    public void onAnimationEnd() throws  {
        super.onAnimationEnd();
        if (this.mListener != null) {
            this.mListener.onAnimationEnd(getAnimation());
        }
    }

    public void setBackgroundColorRes(int $i0) throws  {
        setBackgroundColor(getContext().getResources().getColor($i0));
    }

    public void setBackgroundColor(int $i0) throws  {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor($i0);
        }
    }
}
