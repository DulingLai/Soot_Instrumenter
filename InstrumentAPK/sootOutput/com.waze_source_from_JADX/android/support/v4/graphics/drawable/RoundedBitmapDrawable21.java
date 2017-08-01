package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.Gravity;

class RoundedBitmapDrawable21 extends RoundedBitmapDrawable {
    protected RoundedBitmapDrawable21(Resources $r1, Bitmap $r2) throws  {
        super($r1, $r2);
    }

    public void getOutline(Outline $r1) throws  {
        updateDstRect();
        $r1.setRoundRect(this.mDstRect, getCornerRadius());
    }

    public void setMipMap(boolean $z0) throws  {
        if (this.mBitmap != null) {
            this.mBitmap.setHasMipMap($z0);
            invalidateSelf();
        }
    }

    public boolean hasMipMap() throws  {
        return this.mBitmap != null && this.mBitmap.hasMipMap();
    }

    void gravityCompatApply(int $i0, int $i1, int $i2, Rect $r1, Rect $r2) throws  {
        Gravity.apply($i0, $i1, $i2, $r1, $r2, 0);
    }
}
