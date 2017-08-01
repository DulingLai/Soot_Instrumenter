package com.waze.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Build.VERSION;
import com.waze.AppService;
import com.waze.ResManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class CanvasFont {
    public static final float DP2PIX_FACTOR = (AppService.getAppContext().getResources().getDisplayMetrics().xdpi / 160.0f);
    public static final int FONT_TYPE_BOLD = 1;
    public static final int FONT_TYPE_NORMAL = 0;
    public static final int FONT_TYPE_OUTLINE = 2;
    public static final float OUTLINE_GLYPH_HEIGHT_FACTOR = 0.5f;
    public static final float OUTLINE_GLYPH_WIDTH_FACTOR = 0.8f;
    public static final float OUTLINE_SPACING = 0.06f;
    public static final float OUTLINE_STROKE_MITER = 1.0f;
    public static final float OUTLINE_STROKE_WIDTH = 2.5f;
    public static final float REGULAR_SPACING = 0.04f;
    private static int SAVE_GLYPH = 0;
    private static CanvasFont mInstance = new CanvasFont();

    public static class TextMetrics {
        public int mAscent;
        public float mBaseLine;
        public int mDescent;
        public int mHeight;
        public int mHeightOrig;
        public float mOutlineOffset;
        public int mWidth;
        public int mWidthOrig;
    }

    public static CanvasFont instance() throws  {
        return mInstance;
    }

    public Bitmap getFontImage(String $r1, int $i0, boolean $z0, int $i1, Config $r2, TextMetrics $r4) throws  {
        Paint $r5 = createPaint($i0, $z0, $i1);
        if ($r4 == null) {
            TextMetrics textMetrics = new TextMetrics();
        }
        assignTextMetrics($r5, $r1, $z0, $i1, $r4);
        if ($r4.mWidth <= 0 || $r4.mHeight <= 0) {
            $r4.mWidth = 0;
            $r4.mHeight = 0;
            return null;
        }
        Bitmap $r6 = Bitmap.createBitmap($r4.mWidth, $r4.mHeight, $r2);
        String str = $r1;
        new Canvas($r6).drawText(str, (float) ($r4.mWidth / 2), $r4.mBaseLine + ((float) (($r4.mHeight - $r4.mHeightOrig) / 2)), $r5);
        if (SAVE_GLYPH != -1) {
            return $r6;
        }
        saveImage($r1, $i0, $r6);
        SAVE_GLYPH++;
        return $r6;
    }

    public TextMetrics getTextMetrics(String $r1, int $i0, boolean $z0, int $i1) throws  {
        Paint $r3 = createPaint($i0, $z0, $i1);
        TextMetrics $r2 = new TextMetrics();
        assignTextMetrics($r3, $r1, $z0, $i1, $r2);
        if ($r2.mWidth > 0 && $r2.mHeight > 0) {
            return $r2;
        }
        $r2.mWidth = 0;
        $r2.mHeight = 0;
        return $r2;
    }

    private void assignTextMetrics(Paint $r1, String $r2, boolean $z0, int fontType, TextMetrics $r3) throws  {
        $r1.getTextBounds($r2, 0, $r2.length(), new Rect());
        $r3.mBaseLine = -$r1.ascent();
        $r3.mAscent = (int) ($r1.ascent() + OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r3.mDescent = (int) ($r1.descent() + OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r3.mHeight = (int) (($r3.mBaseLine + $r1.descent()) + OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r3.mWidth = (int) ($r1.measureText($r2) + OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r3.mWidthOrig = $r3.mWidth;
        $r3.mHeightOrig = $r3.mHeight;
        if ($z0 && VERSION.SDK_INT < 21) {
            $r3.mWidth = (int) (((float) $r3.mWidth) + ((OUTLINE_GLYPH_WIDTH_FACTOR * DP2PIX_FACTOR) * OUTLINE_STROKE_WIDTH));
            $r3.mHeight = (int) (((float) $r3.mHeight) + ((DP2PIX_FACTOR * OUTLINE_GLYPH_HEIGHT_FACTOR) * OUTLINE_STROKE_WIDTH));
        }
    }

    private Paint createPaint(int $i0, boolean $z0, int $i1) throws  {
        Paint $r1 = new Paint();
        $r1.setAntiAlias(true);
        $r1.setTextSize((float) $i0);
        $r1.setTextAlign(Align.CENTER);
        if (VERSION.SDK_INT >= 21) {
            $r1.setLetterSpacing(REGULAR_SPACING);
        }
        if (($i1 & 1) > 0) {
            $r1.setTypeface(ResManager.getRobotoBold(AppService.getAppContext()));
        } else {
            $r1.setTypeface(ResManager.getRobotoReg(AppService.getAppContext()));
        }
        $r1.setColor(-1);
        if ($z0) {
            $r1.setStyle(Style.STROKE);
            $r1.setStrokeWidth((float) Math.round(OUTLINE_STROKE_WIDTH * DP2PIX_FACTOR));
            $r1.setStrokeMiter((float) Math.round(1.0f * DP2PIX_FACTOR));
            $r1.setStrokeCap(Cap.ROUND);
            $r1.setStrokeJoin(Join.ROUND);
            if (VERSION.SDK_INT < 21) {
                return $r1;
            }
            $r1.setLetterSpacing(OUTLINE_SPACING);
            return $r1;
        }
        $r1.setStyle(Style.FILL);
        return $r1;
    }

    private void saveImage(String $r1, int $i0, Bitmap $r2) throws  {
        try {
            byte[] $r6 = new byte[$r2.getByteCount()];
            $r2.copyPixelsToBuffer(ByteBuffer.wrap($r6));
            File $r4 = new File(AppService.getExternalStoragePath() + "/waze/TestText_" + $r1 + "_" + $r2.getWidth() + "_" + $r2.getHeight() + "_size_" + $i0 + ".raw");
            if ($r4.exists()) {
                $r4.delete();
            }
            FileOutputStream $r5 = new FileOutputStream($r4);
            $r5.write($r6);
            $r5.flush();
            $r5.close();
        } catch (IOException $r3) {
            $r3.printStackTrace();
        }
    }

    private CanvasFont() throws  {
    }
}
