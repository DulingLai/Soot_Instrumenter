package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import java.io.InputStream;

public final class RoundedBitmapDrawableFactory {
    private static final String TAG = "RoundedBitmapDrawableFactory";

    private static class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
        DefaultRoundedBitmapDrawable(Resources $r1, Bitmap $r2) throws  {
            super($r1, $r2);
        }

        public void setMipMap(boolean $z0) throws  {
            if (this.mBitmap != null) {
                BitmapCompat.setHasMipMap(this.mBitmap, $z0);
                invalidateSelf();
            }
        }

        public boolean hasMipMap() throws  {
            return this.mBitmap != null && BitmapCompat.hasMipMap(this.mBitmap);
        }

        void gravityCompatApply(int $i0, int $i1, int $i2, Rect $r1, Rect $r2) throws  {
            GravityCompat.apply($i0, $i1, $i2, $r1, $r2, 0);
        }
    }

    public static RoundedBitmapDrawable create(Resources $r0, Bitmap $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            return new RoundedBitmapDrawable21($r0, $r1);
        }
        return new DefaultRoundedBitmapDrawable($r0, $r1);
    }

    public static RoundedBitmapDrawable create(Resources $r0, String $r1) throws  {
        RoundedBitmapDrawable $r3 = create($r0, BitmapFactory.decodeFile($r1));
        if ($r3.getBitmap() != null) {
            return $r3;
        }
        Log.w(TAG, "RoundedBitmapDrawable cannot decode " + $r1);
        return $r3;
    }

    public static RoundedBitmapDrawable create(Resources $r0, InputStream $r1) throws  {
        RoundedBitmapDrawable $r3 = create($r0, BitmapFactory.decodeStream($r1));
        if ($r3.getBitmap() != null) {
            return $r3;
        }
        Log.w(TAG, "RoundedBitmapDrawable cannot decode " + $r1);
        return $r3;
    }

    private RoundedBitmapDrawableFactory() throws  {
    }
}
