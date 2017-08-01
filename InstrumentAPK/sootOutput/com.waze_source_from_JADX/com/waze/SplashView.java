package com.waze;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import com.waze.map.NativeCanvasRenderer;

public final class SplashView extends View {
    private Bitmap mSplashBmp = null;

    public SplashView(Activity $r1) throws  {
        super($r1);
    }

    private void InitSplashBitmap() throws  {
        this.mSplashBmp = NativeCanvasRenderer.GetSplashBmp(this);
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        super.onMeasure($i0, $i1);
        InitSplashBitmap();
    }

    protected void onDraw(Canvas $r1) throws  {
        $r1.drawBitmap(this.mSplashBmp, 0.0f, 0.0f, null);
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        Log.w("WAZE DEBUG", "Splash view detached");
        this.mSplashBmp.recycle();
    }
}
