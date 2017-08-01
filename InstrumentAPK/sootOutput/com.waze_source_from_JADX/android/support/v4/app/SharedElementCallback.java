package android.support.v4.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import dalvik.annotation.Signature;
import java.util.List;
import java.util.Map;

public abstract class SharedElementCallback {
    private static final String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
    private static final String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
    private static final String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
    private static int MAX_IMAGE_SIZE = 1048576;
    private Matrix mTempMatrix;

    public void onSharedElementStart(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<String> list, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list3) throws  {
    }

    public void onSharedElementEnd(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<String> list, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list3) throws  {
    }

    public void onRejectSharedElements(@Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list) throws  {
    }

    public void onMapSharedElements(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) List<String> list, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Map<String, View> map) throws  {
    }

    public Parcelable onCaptureSharedElementSnapshot(View $r1, Matrix $r2, RectF $r3) throws  {
        Bitmap $r9;
        if ($r1 instanceof ImageView) {
            ImageView $r6 = (ImageView) $r1;
            Drawable $r7 = $r6.getDrawable();
            Drawable $r8 = $r6.getBackground();
            if ($r7 != null && $r8 == null) {
                $r9 = createDrawableBitmap($r7);
                if ($r9 != null) {
                    Bundle $r10 = new Bundle();
                    $r10.putParcelable(BUNDLE_SNAPSHOT_BITMAP, $r9);
                    $r10.putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, $r6.getScaleType().toString());
                    if ($r6.getScaleType() != ScaleType.MATRIX) {
                        return $r10;
                    }
                    float[] $r5 = new float[9];
                    $r6.getImageMatrix().getValues($r5);
                    $r10.putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, $r5);
                    return $r10;
                }
            }
        }
        int $i0 = Math.round($r3.width());
        int $i1 = Math.round($r3.height());
        $r9 = null;
        if ($i0 > 0 && $i1 > 0) {
            float $f0 = Math.min(1.0f, ((float) MAX_IMAGE_SIZE) / ((float) ($i0 * $i1)));
            float $f1 = (float) $i0;
            int $i02 = $f1 * $f0;
            int i = $i02;
            $i0 = (int) $i02;
            $f1 = (float) $i1;
            $i02 = $f1 * $f0;
            i = $i02;
            $i1 = (int) $i02;
            if (this.mTempMatrix == null) {
                this.mTempMatrix = new Matrix();
            }
            Matrix matrix = this.mTempMatrix;
            Matrix $r14 = matrix;
            matrix.set($r2);
            $r2 = this.mTempMatrix;
            $f1 = $r3.left;
            float $f12 = -$f1;
            $f1 = $r3.top;
            $r2.postTranslate($f12, -$f1);
            matrix = this.mTempMatrix;
            $r2 = matrix;
            matrix.postScale($f0, $f0);
            Bitmap $r16 = Bitmap.createBitmap($i0, $i1, Config.ARGB_8888);
            $r9 = $r16;
            Canvas canvas = new Canvas($r16);
            canvas.concat(this.mTempMatrix);
            $r1.draw(canvas);
        }
        return $r9;
    }

    private static Bitmap createDrawableBitmap(Drawable $r1) throws  {
        int $i1 = $r1.getIntrinsicWidth();
        int $i0 = $r1.getIntrinsicHeight();
        if ($i1 <= 0 || $i0 <= 0) {
            return null;
        }
        float $f0 = Math.min(1.0f, ((float) MAX_IMAGE_SIZE) / ((float) ($i1 * $i0)));
        if (($r1 instanceof BitmapDrawable) && $f0 == 1.0f) {
            return ((BitmapDrawable) $r1).getBitmap();
        }
        $i1 = (int) (((float) $i1) * $f0);
        $i0 = (int) (((float) $i0) * $f0);
        Bitmap $r3 = Bitmap.createBitmap($i1, $i0, Config.ARGB_8888);
        Canvas canvas = new Canvas($r3);
        Rect $r5 = $r1.getBounds();
        int $i3 = $r5.left;
        int $i5 = $r5.top;
        int $i4 = $r5.right;
        int $i2 = $r5.bottom;
        $r1.setBounds(0, 0, $i1, $i0);
        $r1.draw(canvas);
        $r1.setBounds($i3, $i5, $i4, $i2);
        return $r3;
    }

    public View onCreateSnapshotView(Context $r1, Parcelable $r2) throws  {
        ImageView $r6 = null;
        Bitmap $r8;
        if ($r2 instanceof Bundle) {
            Bundle $r7 = (Bundle) $r2;
            $r8 = (Bitmap) $r7.getParcelable(BUNDLE_SNAPSHOT_BITMAP);
            if ($r8 == null) {
                return null;
            }
            ImageView $r3 = new ImageView($r1);
            $r6 = $r3;
            $r3.setImageBitmap($r8);
            $r3.setScaleType(ScaleType.valueOf($r7.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)));
            if ($r3.getScaleType() == ScaleType.MATRIX) {
                float[] $r11 = $r7.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX);
                Matrix $r4 = new Matrix();
                $r4.setValues($r11);
                $r3.setImageMatrix($r4);
            }
        } else if ($r2 instanceof Bitmap) {
            $r8 = (Bitmap) $r2;
            $r6 = new ImageView($r1);
            $r6.setImageBitmap($r8);
        }
        return $r6;
    }
}
