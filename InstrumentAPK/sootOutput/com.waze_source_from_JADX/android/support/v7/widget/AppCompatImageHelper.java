package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AppCompatImageHelper {
    private final AppCompatDrawableManager mDrawableManager;
    private final ImageView mView;

    public AppCompatImageHelper(ImageView $r1, AppCompatDrawableManager $r2) throws  {
        this.mView = $r1;
        this.mDrawableManager = $r2;
    }

    public void loadFromAttributes(AttributeSet $r1, int $i0) throws  {
        TintTypedArray $r5 = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), $r1, C0192R.styleable.AppCompatImageView, $i0, 0);
        try {
            Drawable $r6 = $r5.getDrawableIfKnown(C0192R.styleable.AppCompatImageView_android_src);
            if ($r6 != null) {
                this.mView.setImageDrawable($r6);
            }
            $i0 = $r5.getResourceId(C0192R.styleable.AppCompatImageView_srcCompat, -1);
            if ($i0 != -1) {
                $r6 = this.mDrawableManager.getDrawable(this.mView.getContext(), $i0);
                if ($r6 != null) {
                    this.mView.setImageDrawable($r6);
                }
            }
            $r6 = this.mView.getDrawable();
            if ($r6 != null) {
                DrawableUtils.fixDrawable($r6);
            }
            $r5.recycle();
        } catch (Throwable th) {
            $r5.recycle();
        }
    }

    public void setImageResource(int $i0) throws  {
        if ($i0 != 0) {
            Drawable $r4 = this.mDrawableManager != null ? this.mDrawableManager.getDrawable(this.mView.getContext(), $i0) : ContextCompat.getDrawable(this.mView.getContext(), $i0);
            if ($r4 != null) {
                DrawableUtils.fixDrawable($r4);
            }
            this.mView.setImageDrawable($r4);
            return;
        }
        this.mView.setImageDrawable(null);
    }
}
