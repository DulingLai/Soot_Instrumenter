package com.waze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL10;

public class ScreenShotManager extends Thread {
    private static final long SCRN_SHOT_EFFECT_TIMEOUT = 1200;
    private static volatile int mMaxIndex = GetMaxFileIndex();
    public static final String mScrnShotsDir = "screenshots/";
    public static final String mScrnShotsNamePrefix = "screenshot_";
    public static final String mScrnShotsNameSuffix = ".jpg";
    private Bitmap mBitMap;

    private final class ImageWriter extends Thread {
        private ImageWriter() throws  {
        }

        public void run() throws  {
            if (ScreenShotManager.this.mBitMap != null) {
                ScreenShotManager.access$208();
                String $r7 = ScreenShotManager.mScrnShotsNamePrefix + String.valueOf(ScreenShotManager.mMaxIndex) + ScreenShotManager.mScrnShotsNameSuffix;
                File $r2 = new File(ResManager.mSDCardResDir + ScreenShotManager.mScrnShotsDir + $r7);
                $r2.getParentFile().mkdirs();
                try {
                    FileOutputStream $r3 = new FileOutputStream($r2);
                    ScreenShotManager.this.mBitMap.compress(CompressFormat.JPEG, 50, $r3);
                    $r3.flush();
                    $r3.close();
                    ScreenShotManager.this.mBitMap.recycle();
                    ScreenShotManager.this.mBitMap = null;
                } catch (Exception $r1) {
                    Logger.m44w("ScreenShot: File writing error for " + $r7, $r1);
                    $r1.printStackTrace();
                }
            }
        }
    }

    private final class ScaleEffectView extends View {
        public ScaleEffectView(Context $r2, Bitmap $r3) throws  {
            super($r2);
            setFocusable(true);
            setBackgroundDrawable(new BitmapDrawable($r3));
        }
    }

    static /* synthetic */ int access$208() throws  {
        int $i0 = mMaxIndex;
        mMaxIndex = $i0 + 1;
        return $i0;
    }

    public void Capture(View $r1, GL10 $r2) throws  {
        int $i3 = $r1.getMeasuredWidth();
        int $i4 = $r1.getMeasuredHeight();
        int[] $r3 = new int[($i3 * $i4)];
        int[] $r4 = new int[($i3 * $i4)];
        Buffer $r5 = IntBuffer.wrap($r3);
        $r5.position(0);
        $r2.glReadPixels(0, 0, $i3, $i4, 6408, 5121, $r5);
        for (int $i5 = 0; $i5 < $i4; $i5++) {
            for (int $i6 = 0; $i6 < $i3; $i6++) {
                int $i1 = $r3[($i5 * $i3) + $i6];
                int $i0 = ((-16711936 & $i1) | (($i1 << 16) & 16711680)) | (($i1 >> 16) & 255);
                int $i12 = (($i4 - $i5) - 1) * $i3;
                $i1 = $i12;
                $r4[$i12 + $i6] = $i0;
            }
        }
        this.mBitMap = Bitmap.createBitmap($r4, $i3, $i4, Config.ARGB_8888);
        DrawScaledImage($r2, this.mBitMap);
        new ImageWriter().run();
    }

    public Bitmap CaptureMapScreenShot(View $r1, GL10 $r2) throws  {
        int $i3 = $r1.getMeasuredWidth();
        int $i4 = $r1.getMeasuredHeight();
        int[] $r3 = new int[($i3 * $i4)];
        int[] $r4 = new int[($i3 * $i4)];
        Buffer $r5 = IntBuffer.wrap($r3);
        $r5.position(0);
        $r2.glReadPixels(0, 0, $i3, $i4, 6408, 5121, $r5);
        for (int $i5 = 0; $i5 < $i4; $i5++) {
            for (int $i6 = 0; $i6 < $i3; $i6++) {
                int $i1 = $r3[($i5 * $i3) + $i6];
                int $i0 = ((-16711936 & $i1) | (($i1 << 16) & 16711680)) | (($i1 >> 16) & 255);
                int $i12 = (($i4 - $i5) - 1) * $i3;
                $i1 = $i12;
                $r4[$i12 + $i6] = $i0;
            }
        }
        return Bitmap.createBitmap($r4, $i3, $i4, Config.ARGB_8888);
    }

    private void DrawScaledImage(GL10 aGL, Bitmap $r2) throws  {
        final MainActivity $r8 = AppService.getMainActivity();
        int $i0 = $r2.getWidth();
        int $i1 = $r2.getHeight();
        final Bitmap $r10 = Bitmap.createBitmap($i0, $i1, Config.ARGB_8888);
        Canvas $r3 = new Canvas($r10);
        $r3.drawColor(-12303292);
        Matrix $r5 = new Matrix();
        $r5.setScale(0.75f, 0.75f);
        float $f1 = (((float) $i1) * 0.25f) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        float f = $f1;
        $r5.postTranslate((((float) $i0) * 0.25f) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, $f1);
        $r3.drawBitmap($r2, $r5, null);
        $f1 = new ScaleEffectView($r8, $r10);
        final View view = $f1;
        $r8.runOnUiThread(new Runnable() {
            public void run() throws  {
                $r8.addContentView(view, new LayoutParams(-2, -2));
                view.invalidate();
            }
        });
        view = $f1;
        $f1.postDelayed(new Runnable() {
            public void run() throws  {
                view.setVisibility(8);
                $r10.recycle();
            }
        }, SCRN_SHOT_EFFECT_TIMEOUT);
    }

    private static int GetMaxFileIndex() throws  {
        int $i1 = -1;
        String[] $r3 = new File(ResManager.mSDCardResDir + mScrnShotsDir).list();
        if ($r3 == null) {
            return -1;
        }
        for (int $i2 = 0; $i2 < $r3.length; $i2++) {
            if ($r3[$i2].startsWith(mScrnShotsNamePrefix)) {
                String $r2 = $r3[$i2].substring(mScrnShotsNamePrefix.length(), $r3[$i2].length() - mScrnShotsNameSuffix.length());
                if (Integer.decode($r2).intValue() > $i1) {
                    $i1 = Integer.decode($r2).intValue();
                }
            }
        }
        return $i1;
    }
}
