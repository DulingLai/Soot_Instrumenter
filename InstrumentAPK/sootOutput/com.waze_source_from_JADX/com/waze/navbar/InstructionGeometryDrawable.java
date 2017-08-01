package com.waze.navbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.map.CanvasFont;
import com.waze.utils.PixelMeasure;
import java.util.ArrayList;

public class InstructionGeometryDrawable extends Drawable {
    private static Bitmap ARROW_BITMAP = null;
    private static Rect ARROW_RECT = null;
    private static final float INNER_STROKE_TO_STROKE_RATIO = 0.65f;
    private static final int MAX_STROKE_WIDTH_DP = 8;
    private static final int MIN_STROKE_WIDTH_DP = 4;
    private static final float STOKE_WIDTH_TO_ARROW_HEIGHT_RATIO = 2.0f;
    private static final float STROKE_WIDTH_TO_ARROW_WIDTH_RATIO = 3.0f;
    private static final float STROKE_WIDTH_TO_PADDING_RATIO = 1.33f;
    private static final float WIDTH_TO_ROUNDABOUT_RADIUS_RATIO = 0.19f;
    private static final float WIDTH_TO_STROKE_WIDTH_RATIO = 0.0815f;
    private Paint mActivePaint = new Paint();
    private float mArrowHeight;
    private RectF mArrowTargetRect;
    private float mArrowWidth;
    private Paint mBitmapPaint;
    private Paint mClosedPaint;
    private InstructionGeometry mInstructionGeometry;
    private float mPadding;
    private float mPassiveInnerStrokeWidth;
    private float mPassiveOutlineStrokeWidth;
    private Paint mPassivePaint;
    private float mRoundaboutRadius;

    private void drawArrowHead(android.graphics.Canvas r32) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:8:0x014c in {5, 7, 9} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r31 = this;
        r0 = r32;
        r0.save();
        r7 = 0;
        r8 = new android.graphics.PointF;
        r8.<init>();
        r9 = new android.graphics.PointF;
        r9.<init>();
        r10 = 0;
    L_0x0011:
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r12 = r11.getTotalPoints();
        if (r10 >= r12) goto L_0x00c1;
    L_0x001b:
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r12 = r11.getType(r10);
        r13 = 1;
        if (r12 != r13) goto L_0x0150;
    L_0x0026:
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r14 = r11.getStartPoint(r10);
        r0 = r31;
        r14 = r0.normalizePoint(r14);
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r15 = r11.getEndPoint(r10);
        r0 = r31;
        r15 = r0.normalizePoint(r15);
        r0 = r31;
        r7 = r0.mPadding;
        r16 = 0;
        r17 = 0;
        r0 = r31;
        r1 = r14;
        r2 = r15;
        r3 = r16;
        r4 = r7;
        r5 = r17;
        r6 = r8;
        r0.plotPointsWithPadding(r1, r2, r3, r4, r5, r6);
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r14 = r11.getEndPoint(r10);
        r7 = r14.x;
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r14 = r11.getStartPoint(r10);
        r0 = r14.x;
        r18 = r0;
        r7 = r7 - r0;
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r14 = r11.getEndPoint(r10);
        r0 = r14.y;
        r18 = r0;
        r0 = r31;
        r11 = r0.mInstructionGeometry;
        r14 = r11.getStartPoint(r10);
        r0 = r14.y;
        r19 = r0;
        r0 = r18;
        r1 = r19;
        r0 = r0 - r1;
        r18 = r0;
        r9.set(r7, r0);
        r7 = r9.y;
        r0 = (double) r7;
        r20 = r0;
        r7 = r9.x;
        r0 = (double) r7;
        r22 = r0;
        r0 = r20;
        r2 = r22;
        r20 = java.lang.Math.atan2(r0, r2);
        r24 = 4614256656552045848; // 0x400921fb54442d18 float:3.37028055E12 double:3.141592653589793;
        r0 = r20;
        r2 = r24;
        r0 = r0 / r2;
        r20 = r0;
        r24 = 4640537203540230144; // 0x4066800000000000 float:0.0 double:180.0;
        r0 = r20;
        r2 = r24;
        r0 = r0 * r2;
        r20 = r0;
        r7 = (float) r0;
        r16 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r0 = r16;
        r7 = r7 + r0;
    L_0x00c1:
        r0 = r8.x;
        r18 = r0;
        r0 = r31;
        r0 = r0.mArrowWidth;
        r19 = r0;
        r16 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r19;
        r1 = r16;
        r0 = r0 / r1;
        r19 = r0;
        r0 = r18;
        r1 = r19;
        r0 = r0 - r1;
        r18 = r0;
        r0 = r8.y;
        r19 = r0;
        r0 = r31;
        r0 = r0.mArrowHeight;
        r26 = r0;
        r16 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r26;
        r1 = r16;
        r0 = r0 / r1;
        r26 = r0;
        r0 = r19;
        r1 = r26;
        r0 = r0 - r1;
        r19 = r0;
        r0 = r32;
        r1 = r18;
        r2 = r19;
        r0.translate(r1, r2);
        r0 = r31;
        r0 = r0.mArrowWidth;
        r18 = r0;
        r16 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r18;
        r1 = r16;
        r0 = r0 / r1;
        r18 = r0;
        r0 = r31;
        r0 = r0.mArrowHeight;
        r19 = r0;
        r16 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r19;
        r1 = r16;
        r0 = r0 / r1;
        r19 = r0;
        r0 = r32;
        r1 = r18;
        r2 = r19;
        r0.rotate(r7, r1, r2);
        r27 = ARROW_BITMAP;
        r28 = ARROW_RECT;
        r0 = r31;
        r0 = r0.mArrowTargetRect;
        r29 = r0;
        r0 = r31;
        r0 = r0.mBitmapPaint;
        r30 = r0;
        r0 = r32;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r4 = r30;
        r0.drawBitmap(r1, r2, r3, r4);
        r0 = r32;
        r0.restore();
        return;
        goto L_0x0150;
    L_0x014d:
        goto L_0x0011;
    L_0x0150:
        r10 = r10 + 1;
        goto L_0x014d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navbar.InstructionGeometryDrawable.drawArrowHead(android.graphics.Canvas):void");
    }

    public int getOpacity() throws  {
        return -3;
    }

    public InstructionGeometryDrawable(InstructionGeometry $r1) throws  {
        this.mInstructionGeometry = $r1;
        this.mActivePaint.setColor(-1);
        this.mActivePaint.setStyle(Style.STROKE);
        this.mActivePaint.setStrokeCap(Cap.ROUND);
        this.mActivePaint.setAntiAlias(true);
        this.mClosedPaint = new Paint();
        this.mClosedPaint.setColor(-1594867648);
        this.mClosedPaint.setStyle(Style.STROKE);
        this.mClosedPaint.setStrokeCap(Cap.ROUND);
        this.mClosedPaint.setAntiAlias(true);
        this.mPassivePaint = new Paint();
        this.mPassivePaint.setStrokeCap(Cap.ROUND);
        this.mPassivePaint.setStyle(Style.STROKE);
        this.mPassivePaint.setAntiAlias(true);
        this.mBitmapPaint = new Paint(3);
        if (ARROW_BITMAP == null) {
            ARROW_BITMAP = BitmapFactory.decodeResource(AppService.getAppResources(), C1283R.drawable.direction_arrow_head);
            ARROW_RECT = new Rect(0, 0, ARROW_BITMAP.getWidth(), ARROW_BITMAP.getHeight());
        }
    }

    public void draw(Canvas $r1) throws  {
        calculateSizes();
        if (this.mInstructionGeometry.isRoundabout()) {
            $r1.drawPath(generateClosedRoundaboutRoads(), this.mClosedPaint);
        }
        Path $r3 = this.mInstructionGeometry.isRoundabout() ? generatePassiveRoundaboutPath() : generatePassivePath();
        this.mPassivePaint.setStrokeWidth(this.mPassiveOutlineStrokeWidth);
        this.mPassivePaint.setColor(-1);
        $r1.drawPath($r3, this.mPassivePaint);
        this.mPassivePaint.setStrokeWidth(this.mPassiveInnerStrokeWidth);
        this.mPassivePaint.setColor(-16777216);
        $r1.drawPath($r3, this.mPassivePaint);
        $r1.drawPath(this.mInstructionGeometry.isRoundabout() ? generateActiveRoundaboutPath() : generateActivePath(), this.mActivePaint);
        drawArrowHead($r1);
    }

    private void calculateSizes() throws  {
        Rect $r1 = getBounds();
        float $f0 = ((float) Math.min($r1.width(), $r1.height())) * WIDTH_TO_STROKE_WIDTH_RATIO;
        if ($f0 < ((float) PixelMeasure.dp(4))) {
            $f0 = (float) PixelMeasure.dp(4);
        }
        if ($f0 > ((float) PixelMeasure.dp(8))) {
            $f0 = (float) PixelMeasure.dp(8);
        }
        this.mPassiveOutlineStrokeWidth = $f0;
        this.mPassiveInnerStrokeWidth = INNER_STROKE_TO_STROKE_RATIO * $f0;
        this.mActivePaint.setStrokeWidth($f0);
        this.mClosedPaint.setStrokeWidth($f0);
        this.mPassivePaint.setStrokeWidth($f0);
        this.mArrowWidth = STROKE_WIDTH_TO_ARROW_WIDTH_RATIO * $f0;
        this.mArrowHeight = 2.0f * $f0;
        this.mPadding = STROKE_WIDTH_TO_PADDING_RATIO * $f0;
        this.mRoundaboutRadius = ((float) Math.min($r1.width(), $r1.height())) * WIDTH_TO_ROUNDABOUT_RADIUS_RATIO;
        this.mArrowTargetRect = new RectF(0.0f, 0.0f, this.mArrowWidth, this.mArrowHeight);
    }

    private PointF normalizePoint(PointF $r1) throws  {
        Rect $r2 = getBounds();
        float $f0 = (float) Math.min($r2.width(), $r2.height());
        float $f2 = $r1.x * $f0;
        float $f1 = $r1.y * $f0;
        if ($f0 < ((float) $r2.width())) {
            $f2 += ((float) ($r2.width() / 2)) - ($f0 / 2.0f);
        }
        return new PointF($f2, $f1);
    }

    public void setAlpha(int $i0) throws  {
        this.mActivePaint.setAlpha($i0);
        this.mPassivePaint.setAlpha($i0);
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.mActivePaint.setColorFilter($r1);
        this.mPassivePaint.setColorFilter($r1);
    }

    private Path generatePassiveRoundaboutPath() throws  {
        Path $r1 = new Path();
        Rect $r3 = getBounds();
        $r1.addCircle((float) ($r3.width() / 2), (float) ($r3.height() / 2), this.mRoundaboutRadius, Direction.CW);
        for (int $i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
            if (this.mInstructionGeometry.getType($i0) != 3) {
                plotRoundaboutRoad($r1, $i0);
            }
        }
        return $r1;
    }

    private Path generateClosedRoundaboutRoads() throws  {
        Path $r1 = new Path();
        for (int $i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
            if (this.mInstructionGeometry.getType($i0) == 3) {
                plotRoundaboutRoad($r1, $i0);
            }
        }
        return $r1;
    }

    private boolean hasOpenRoadWithPathInIndex(int $i0) throws  {
        PointF $r2 = this.mInstructionGeometry.getStartPoint($i0);
        PointF $r3 = this.mInstructionGeometry.getEndPoint($i0);
        int $i1 = 0;
        while ($i1 < this.mInstructionGeometry.getTotalPoints()) {
            if ($i1 != $i0 && ((this.mInstructionGeometry.getStartPoint($i1).equals($r2) || this.mInstructionGeometry.getEndPoint($i1).equals($r3)) && ((this.mInstructionGeometry.getEndPoint($i1).equals($r2) || this.mInstructionGeometry.getStartPoint($i1).equals($r3)) && this.mInstructionGeometry.getType($i1) != 3))) {
                return true;
            }
            $i1++;
        }
        return false;
    }

    private Path generateActiveRoundaboutPath() throws  {
        int $i0;
        Path $r1 = new Path();
        RectF $r3 = getRoundaboutRect();
        float $f0 = 90.0f;
        PointF $r2 = new PointF();
        boolean $z0 = NativeManager.getInstance().getNavBarManager().getDriveOnLeft();
        for ($i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
            if (this.mInstructionGeometry.getType($i0) == 1) {
                $f0 = this.mInstructionGeometry.getEndPoint($i0).x;
                float $f1 = this.mInstructionGeometry.getStartPoint($i0).x;
                $f0 -= $f1;
                $f1 = this.mInstructionGeometry.getEndPoint($i0).y - this.mInstructionGeometry.getStartPoint($i0).y;
                float f = $f1;
                $r2.set($f0, $f1);
                double $d0 = (Math.atan2((double) $r2.y, (double) $r2.x) / 3.141592653589793d) * 180.0d;
                double d = $d0;
                $f0 = ((float) $d0) - 1119092736;
                if ($z0) {
                    if ($f0 < 0.0f) {
                        $f0 += 1135869952;
                    }
                    if ($f0 == 0.0f) {
                        $f0 = 359.0f;
                    }
                } else {
                    if ($f0 > 0.0f) {
                        $f0 -= 1135869952;
                    }
                    if ($f0 == 0.0f) {
                        $f0 = -359.0f;
                    }
                }
                for ($i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
                    if (this.mInstructionGeometry.getType($i0) == 0) {
                        plotRoundaboutRoad($r1, $i0);
                    }
                }
                if (!($r2.x == 0.0f && $r2.y == 0.0f)) {
                    $r1.addArc($r3, 90.0f, $f0);
                }
                for ($i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
                    if (this.mInstructionGeometry.getType($i0) == 1) {
                        plotRoundaboutRoad($r1, $i0);
                    }
                }
                return $r1;
            }
        }
        for ($i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
            if (this.mInstructionGeometry.getType($i0) == 0) {
                plotRoundaboutRoad($r1, $i0);
            }
        }
        $r1.addArc($r3, 90.0f, $f0);
        for ($i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
            if (this.mInstructionGeometry.getType($i0) == 1) {
                plotRoundaboutRoad($r1, $i0);
            }
        }
        return $r1;
    }

    private void plotRoundaboutRoad(Path $r1, int $i0) throws  {
        float $f1;
        PointF $r3 = this.mInstructionGeometry.getStartPoint($i0);
        PointF $r4 = this.mInstructionGeometry.getEndPoint($i0);
        float $f0 = ($r3.x == CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR && $r3.y == CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) ? this.mRoundaboutRadius : 0.0f;
        if ($r4.x == CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR && $r4.y == CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
            $f1 = this.mRoundaboutRadius;
        } else {
            $f1 = 0.0f;
        }
        if ($f1 == 0.0f && this.mInstructionGeometry.getType($i0) == 1) {
            $f1 = this.mPadding;
        }
        $r3 = normalizePoint($r3);
        $r4 = normalizePoint($r4);
        plotPointsWithPadding($r3, $r4, $f0, $f1, $r3, $r4);
        $r1.moveTo($r3.x, $r3.y);
        $r1.lineTo($r4.x, $r4.y);
    }

    private Path generatePassivePath() throws  {
        Path $r1 = new Path();
        for (int $i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
            PointF $r3 = normalizePoint(this.mInstructionGeometry.getStartPoint($i0));
            PointF $r4 = normalizePoint(this.mInstructionGeometry.getEndPoint($i0));
            if (this.mInstructionGeometry.getType($i0) == 1) {
                plotPointsWithPadding($r3, $r4, 0.0f, this.mPadding, null, $r4);
            }
            float $f0 = $r3.x;
            float $f1 = $r3.y;
            $r1.moveTo($f0, $f1);
            $f0 = $r4.x;
            $f1 = $r4.y;
            $r1.lineTo($f0, $f1);
        }
        return $r1;
    }

    private Path generateActivePath() throws  {
        int $i0;
        Path $r5 = new Path();
        ArrayList $r3 = new ArrayList();
        for ($i0 = 0; $i0 < this.mInstructionGeometry.getTotalPoints(); $i0++) {
            if (this.mInstructionGeometry.getType($i0) == 1) {
                $r3.add(this.mInstructionGeometry.getStartPoint($i0));
                $r3.add(this.mInstructionGeometry.getEndPoint($i0));
                break;
            }
        }
        if ($r3.size() > 0) {
            boolean $z0 = true;
            while ($z0) {
                $z0 = false;
                $i0 = 0;
                while ($i0 < this.mInstructionGeometry.getTotalPoints()) {
                    if (this.mInstructionGeometry.getType($i0) == 0 && this.mInstructionGeometry.getEndPoint($i0).equals($r3.get(0))) {
                        $r3.add(0, this.mInstructionGeometry.getStartPoint($i0));
                        $z0 = true;
                        break;
                    }
                    $i0++;
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        PointF $r1 = new PointF();
        PointF pointF = new PointF();
        $i0 = 0;
        while ($i0 < $r3.size() - 1) {
            plotPointsWithPadding(normalizePoint((PointF) $r3.get($i0)), normalizePoint((PointF) $r3.get($i0 + 1)), $i0 == 0 ? 0.0f : this.mPadding, this.mPadding, $r1, pointF);
            arrayList.add(new PointF($r1.x, $r1.y));
            arrayList.add(new PointF(pointF.x, pointF.y));
            if ($i0 < $r3.size() - 2) {
                arrayList.add(normalizePoint((PointF) $r3.get($i0 + 1)));
            }
            $i0++;
        }
        for ($i0 = 0; $i0 < arrayList.size(); $i0 += 3) {
            $r1 = (PointF) arrayList.get($i0);
            PointF $r2 = (PointF) arrayList.get($i0 + 1);
            $r5.moveTo($r1.x, $r1.y);
            $r5.lineTo($r2.x, $r2.y);
            if ($i0 < arrayList.size() - 2) {
                $r1 = (PointF) arrayList.get($i0 + 2);
                $r2 = (PointF) arrayList.get($i0 + 3);
                $r5.quadTo($r1.x, $r1.y, $r2.x, $r2.y);
            }
        }
        return $r5;
    }

    private RectF getRoundaboutRect() throws  {
        RectF $r1 = new RectF();
        Rect $r2 = getBounds();
        $r1.set(((float) ($r2.width() / 2)) - this.mRoundaboutRadius, ((float) ($r2.height() / 2)) - this.mRoundaboutRadius, ((float) ($r2.width() / 2)) + this.mRoundaboutRadius, ((float) ($r2.height() / 2)) + this.mRoundaboutRadius);
        return $r1;
    }

    private void plotPointsWithPadding(PointF $r1, PointF $r2, float $f0, float $f1, PointF $r3, PointF $r4) throws  {
        PointF $r5 = new PointF($r2.x - $r1.x, $r2.y - $r1.y);
        float $f2 = (float) Math.sqrt(Math.pow((double) ($r2.x - $r1.x), 2.0d) + Math.pow((double) ($r2.y - $r1.y), 2.0d));
        float $f3 = $f0 / $f2;
        $f0 = $f1 / $f2;
        if ($r3 != null) {
            $r3.set($r1.x + ($r5.x * $f3), $r1.y + ($r5.y * $f3));
        }
        if ($r4 != null) {
            $r4.set($r2.x - ($r5.x * $f0), $r2.y - ($r5.y * $f0));
        }
    }
}
