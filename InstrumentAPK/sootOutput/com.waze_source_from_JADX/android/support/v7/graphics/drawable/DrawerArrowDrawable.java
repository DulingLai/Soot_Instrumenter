package android.support.v7.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v7.appcompat.C0192R;
import com.waze.LayoutManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawerArrowDrawable extends Drawable {
    public static final int ARROW_DIRECTION_END = 3;
    public static final int ARROW_DIRECTION_LEFT = 0;
    public static final int ARROW_DIRECTION_RIGHT = 1;
    public static final int ARROW_DIRECTION_START = 2;
    private static final float ARROW_HEAD_ANGLE = ((float) Math.toRadians(45.0d));
    private float mArrowHeadLength;
    private float mArrowShaftLength;
    private float mBarGap;
    private float mBarLength;
    private int mDirection = 2;
    private float mMaxCutForBarSize;
    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();
    private float mProgress;
    private final int mSize;
    private boolean mSpin;
    private boolean mVerticalMirror = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowDirection {
    }

    public void draw(android.graphics.Canvas r32) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:19:0x01cb
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r31 = this;
        r0 = r31;
        r4 = r0.getBounds();
        r0 = r31;
        r5 = r0.mDirection;
        switch(r5) {
            case 0: goto L_0x01cf;
            case 1: goto L_0x01d5;
            case 2: goto L_0x000e;
            case 3: goto L_0x01d7;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x000e;
    L_0x000e:
        r0 = r31;
        r5 = android.support.v4.graphics.drawable.DrawableCompat.getLayoutDirection(r0);
        r6 = 1;
        if (r5 != r6) goto L_0x01eb;
    L_0x0017:
        r7 = 1;
    L_0x0018:
        r0 = r31;
        r8 = r0.mArrowHeadLength;
        r0 = r31;
        r9 = r0.mArrowHeadLength;
        r8 = r8 * r9;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r8 = r8 * r10;
        r11 = (double) r8;
        r11 = java.lang.Math.sqrt(r11);
        r8 = (float) r11;
        r0 = r31;
        r9 = r0.mBarLength;
        r0 = r31;
        r13 = r0.mProgress;
        r14 = lerp(r9, r8, r13);
        r0 = r31;
        r8 = r0.mBarLength;
        r0 = r31;
        r9 = r0.mArrowShaftLength;
        r0 = r31;
        r13 = r0.mProgress;
        r9 = lerp(r8, r9, r13);
        r0 = r31;
        r8 = r0.mMaxCutForBarSize;
        r0 = r31;
        r13 = r0.mProgress;
        r10 = 0;
        r8 = lerp(r10, r8, r13);
        r5 = java.lang.Math.round(r8);
        r13 = (float) r5;
        r8 = ARROW_HEAD_ANGLE;
        r0 = r31;
        r15 = r0.mProgress;
        r10 = 0;
        r16 = lerp(r10, r8, r15);
        if (r7 == 0) goto L_0x01f1;
    L_0x0066:
        r8 = 0;
    L_0x0067:
        if (r7 == 0) goto L_0x01f9;
    L_0x0069:
        r15 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
    L_0x006c:
        r0 = r31;
        r0 = r0.mProgress;
        r17 = r0;
        r8 = lerp(r8, r15, r0);
        r11 = (double) r14;
        r0 = r16;
        r0 = (double) r0;
        r18 = r0;
        r18 = java.lang.Math.cos(r0);
        r0 = r18;
        r11 = r11 * r0;
        r20 = java.lang.Math.round(r11);
        r0 = r20;
        r15 = (float) r0;
        r11 = (double) r14;
        r0 = r16;
        r0 = (double) r0;
        r18 = r0;
        r18 = java.lang.Math.sin(r0);
        r0 = r18;
        r11 = r11 * r0;
        r20 = java.lang.Math.round(r11);
        r0 = r20;
        r14 = (float) r0;
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r0.rewind();
        r0 = r31;
        r0 = r0.mBarGap;
        r16 = r0;
        r0 = r31;
        r0 = r0.mPaint;
        r23 = r0;
        r17 = r0.getStrokeWidth();
        r0 = r16;
        r1 = r17;
        r0 = r0 + r1;
        r16 = r0;
        r0 = r31;
        r0 = r0.mMaxCutForBarSize;
        r17 = r0;
        r0 = -r0;
        r17 = r0;
        r0 = r31;
        r0 = r0.mProgress;
        r24 = r0;
        r0 = r16;
        r1 = r17;
        r2 = r24;
        r17 = lerp(r0, r1, r2);
        r0 = -r9;
        r16 = r0;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r16;
        r0 = r0 / r10;
        r16 = r0;
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r24 = r16 + r13;
        r10 = 0;
        r0 = r22;
        r1 = r24;
        r0.moveTo(r1, r10);
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r13 = r10 * r13;
        r9 = r9 - r13;
        r10 = 0;
        r0 = r22;
        r0.rLineTo(r9, r10);
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r1 = r16;
        r2 = r17;
        r0.moveTo(r1, r2);
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r0.rLineTo(r15, r14);
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r0 = r17;
        r9 = -r0;
        r0 = r22;
        r1 = r16;
        r0.moveTo(r1, r9);
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r9 = -r14;
        r0 = r22;
        r0.rLineTo(r15, r9);
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r0.close();
        r0 = r32;
        r0.save();
        r0 = r31;
        r0 = r0.mPaint;
        r23 = r0;
        r9 = r0.getStrokeWidth();
        r5 = r4.height();
        r13 = (float) r5;
        r10 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r15 = r10 * r9;
        r13 = r13 - r15;
        r0 = r31;
        r15 = r0.mBarGap;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r15 = r15 * r10;
        r13 = r13 - r15;
        r5 = (int) r13;
        r5 = r5 / 4;
        r5 = r5 * 2;
        r13 = (float) r5;
        r11 = (double) r13;
        r0 = (double) r9;
        r18 = r0;
        r25 = 4609434218613702656; // 0x3ff8000000000000 float:0.0 double:1.5;
        r0 = r18;
        r2 = r25;
        r0 = r0 * r2;
        r18 = r0;
        r0 = r31;
        r9 = r0.mBarGap;
        r0 = (double) r9;
        r27 = r0;
        r0 = r18;
        r2 = r27;
        r0 = r0 + r2;
        r18 = r0;
        r11 = r11 + r0;
        r9 = (float) r11;
        r5 = r4.centerX();
        r13 = (float) r5;
        r0 = r32;
        r0.translate(r13, r9);
        r0 = r31;
        r0 = r0.mSpin;
        r29 = r0;
        if (r29 == 0) goto L_0x01fe;
    L_0x019a:
        r0 = r31;
        r0 = r0.mVerticalMirror;
        r29 = r0;
        r7 = r29 ^ r7;
        if (r7 == 0) goto L_0x01fb;
    L_0x01a4:
        r30 = -1;
    L_0x01a6:
        r0 = r30;
        r9 = (float) r0;
        r8 = r9 * r8;
        r0 = r32;
        r0.rotate(r8);
    L_0x01b0:
        r0 = r31;
        r0 = r0.mPath;
        r22 = r0;
        r0 = r31;
        r0 = r0.mPaint;
        r23 = r0;
        r0 = r32;
        r1 = r22;
        r2 = r23;
        r0.drawPath(r1, r2);
        r0 = r32;
        r0.restore();
        return;
        goto L_0x01cf;
    L_0x01cc:
        goto L_0x0018;
    L_0x01cf:
        r7 = 0;
        goto L_0x01cc;
        goto L_0x01d5;
    L_0x01d2:
        goto L_0x0018;
    L_0x01d5:
        r7 = 1;
        goto L_0x01d2;
    L_0x01d7:
        r0 = r31;
        r5 = android.support.v4.graphics.drawable.DrawableCompat.getLayoutDirection(r0);
        if (r5 != 0) goto L_0x01e5;
    L_0x01df:
        goto L_0x01e3;
    L_0x01e0:
        goto L_0x0018;
    L_0x01e3:
        r7 = 1;
    L_0x01e4:
        goto L_0x01e0;
    L_0x01e5:
        r7 = 0;
        goto L_0x01e4;
        goto L_0x01eb;
    L_0x01e8:
        goto L_0x0018;
    L_0x01eb:
        r7 = 0;
        goto L_0x01e8;
        goto L_0x01f1;
    L_0x01ee:
        goto L_0x0067;
    L_0x01f1:
        r8 = -1020002304; // 0xffffffffc3340000 float:-180.0 double:NaN;
        goto L_0x01ee;
        goto L_0x01f9;
    L_0x01f6:
        goto L_0x006c;
    L_0x01f9:
        r15 = 0;
        goto L_0x01f6;
    L_0x01fb:
        r30 = 1;
        goto L_0x01a6;
    L_0x01fe:
        if (r7 == 0) goto L_0x01b0;
    L_0x0200:
        r10 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r0 = r32;
        r0.rotate(r10);
        goto L_0x01b0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.graphics.drawable.DrawerArrowDrawable.draw(android.graphics.Canvas):void");
    }

    public int getOpacity() throws  {
        return -3;
    }

    public DrawerArrowDrawable(Context $r1) throws  {
        Drawable drawable = this;
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeJoin(Join.MITER);
        this.mPaint.setStrokeCap(Cap.BUTT);
        this.mPaint.setAntiAlias(true);
        TypedArray $r9 = $r1.getTheme().obtainStyledAttributes(null, C0192R.styleable.DrawerArrowToggle, C0192R.attr.drawerArrowStyle, C0192R.style.Base_Widget_AppCompat_DrawerArrowToggle);
        setColor($r9.getColor(C0192R.styleable.DrawerArrowToggle_color, 0));
        setBarThickness($r9.getDimension(C0192R.styleable.DrawerArrowToggle_thickness, 0.0f));
        setSpinEnabled($r9.getBoolean(C0192R.styleable.DrawerArrowToggle_spinBars, true));
        setGapSize((float) Math.round($r9.getDimension(C0192R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0f)));
        this.mSize = $r9.getDimensionPixelSize(C0192R.styleable.DrawerArrowToggle_drawableSize, 0);
        this.mBarLength = (float) Math.round($r9.getDimension(C0192R.styleable.DrawerArrowToggle_barLength, 0.0f));
        this.mArrowHeadLength = (float) Math.round($r9.getDimension(C0192R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0f));
        this.mArrowShaftLength = $r9.getDimension(C0192R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0f);
        $r9.recycle();
    }

    public void setArrowHeadLength(float $f0) throws  {
        if (this.mArrowHeadLength != $f0) {
            this.mArrowHeadLength = $f0;
            invalidateSelf();
        }
    }

    public float getArrowHeadLength() throws  {
        return this.mArrowHeadLength;
    }

    public void setArrowShaftLength(float $f0) throws  {
        if (this.mArrowShaftLength != $f0) {
            this.mArrowShaftLength = $f0;
            invalidateSelf();
        }
    }

    public float getArrowShaftLength() throws  {
        return this.mArrowShaftLength;
    }

    public float getBarLength() throws  {
        return this.mBarLength;
    }

    public void setBarLength(float $f0) throws  {
        if (this.mBarLength != $f0) {
            this.mBarLength = $f0;
            invalidateSelf();
        }
    }

    public void setColor(@ColorInt int $i0) throws  {
        if ($i0 != this.mPaint.getColor()) {
            this.mPaint.setColor($i0);
            invalidateSelf();
        }
    }

    @ColorInt
    public int getColor() throws  {
        return this.mPaint.getColor();
    }

    public void setBarThickness(float $f0) throws  {
        if (this.mPaint.getStrokeWidth() != $f0) {
            this.mPaint.setStrokeWidth($f0);
            this.mMaxCutForBarSize = (float) (((double) ($f0 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN)) * Math.cos((double) ARROW_HEAD_ANGLE));
            invalidateSelf();
        }
    }

    public float getBarThickness() throws  {
        return this.mPaint.getStrokeWidth();
    }

    public float getGapSize() throws  {
        return this.mBarGap;
    }

    public void setGapSize(float $f0) throws  {
        if ($f0 != this.mBarGap) {
            this.mBarGap = $f0;
            invalidateSelf();
        }
    }

    public void setDirection(int $i0) throws  {
        if ($i0 != this.mDirection) {
            this.mDirection = $i0;
            invalidateSelf();
        }
    }

    public boolean isSpinEnabled() throws  {
        return this.mSpin;
    }

    public void setSpinEnabled(boolean $z0) throws  {
        if (this.mSpin != $z0) {
            this.mSpin = $z0;
            invalidateSelf();
        }
    }

    public int getDirection() throws  {
        return this.mDirection;
    }

    public void setVerticalMirror(boolean $z0) throws  {
        if (this.mVerticalMirror != $z0) {
            this.mVerticalMirror = $z0;
            invalidateSelf();
        }
    }

    public void setAlpha(int $i0) throws  {
        if ($i0 != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha($i0);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.mPaint.setColorFilter($r1);
        invalidateSelf();
    }

    public int getIntrinsicHeight() throws  {
        return this.mSize;
    }

    public int getIntrinsicWidth() throws  {
        return this.mSize;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() throws  {
        return this.mProgress;
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float $f0) throws  {
        if (this.mProgress != $f0) {
            this.mProgress = $f0;
            invalidateSelf();
        }
    }

    public final Paint getPaint() throws  {
        return this.mPaint;
    }

    private static float lerp(float $f0, float $f1, float $f2) throws  {
        return (($f1 - $f0) * $f2) + $f0;
    }
}
