package com.waze.mywaze;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.utils.PixelMeasure;

public class MyStoreModel {
    private String mCategoryIcon;
    private String mDistanceString;
    private String mIcon;
    private String mId;
    private boolean mIsAdvertiser;
    private String mName;

    public MyStoreModel(String $r1, String $r2, String $r3, String $r4, String $r5, boolean $z0) throws  {
        this.mId = $r1;
        this.mName = $r2;
        this.mIcon = $r3;
        this.mIsAdvertiser = $z0;
        this.mDistanceString = $r5;
        this.mCategoryIcon = $r4;
    }

    public String getId() throws  {
        return this.mId;
    }

    public String getName() throws  {
        return this.mName;
    }

    public String getIcon() throws  {
        return this.mIcon;
    }

    public boolean isAdvertiser() throws  {
        return this.mIsAdvertiser;
    }

    public String getDistance() throws  {
        return this.mDistanceString;
    }

    public String getCategoryIcon() throws  {
        return this.mCategoryIcon;
    }

    public String getCorrectIcon() throws  {
        return isAdvertiser() ? getIcon() : getCategoryIcon();
    }

    public Drawable postProcessDrawable(Drawable $r1) throws  {
        if (!this.mIsAdvertiser || !($r1 instanceof BitmapDrawable)) {
            return $r1;
        }
        return new BitmapDrawable(AppService.getActiveActivity().getResources(), postProcessBitmap(((BitmapDrawable) $r1).getBitmap()));
    }

    public Bitmap postProcessBitmap(Bitmap $r7) throws  {
        if (!this.mIsAdvertiser) {
            return $r7;
        }
        Bitmap $r9 = Bitmap.createBitmap($r7.getWidth(), $r7.getHeight(), Config.ARGB_8888);
        Canvas $r3 = new Canvas($r9);
        Paint $r4 = new Paint();
        Rect $r5 = new Rect(0, 0, $r7.getWidth(), $r7.getHeight());
        RectF $r6 = new RectF($r5);
        RectF $r2 = new RectF($r5);
        float $f0 = (float) PixelMeasure.dp(8);
        int $i0 = PixelMeasure.dp(1);
        float $f2 = (float) ($i0 / 2);
        $r2.left += $f2;
        $f2 = (float) ($i0 / 2);
        $r2.right -= $f2;
        $f2 = (float) ($i0 / 2);
        $r2.top += $f2;
        $f2 = (float) ($i0 / 2);
        $r2.bottom -= $f2;
        Paint paint = new Paint();
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth((float) $i0);
        paint.setAntiAlias(true);
        paint.setColor(AppService.getActiveActivity().getResources().getColor(C1283R.color.BlueGrey));
        $r4.setAntiAlias(true);
        $r3.drawARGB(0, 0, 0, 0);
        $r4.setColor(-1);
        $r3.drawRoundRect($r6, $f0, $f0, $r4);
        $r4.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        $r3.drawBitmap($r7, $r5, $r5, $r4);
        $r3.drawRoundRect($r2, $f0, $f0, paint);
        return $r9;
    }
}
