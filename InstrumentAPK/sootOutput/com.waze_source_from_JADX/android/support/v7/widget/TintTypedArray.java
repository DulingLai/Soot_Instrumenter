package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

public class TintTypedArray {
    private final Context mContext;
    private final TypedArray mWrapped;

    public static TintTypedArray obtainStyledAttributes(Context $r0, AttributeSet $r1, int[] $r2) throws  {
        return new TintTypedArray($r0, $r0.obtainStyledAttributes($r1, $r2));
    }

    public static TintTypedArray obtainStyledAttributes(Context $r0, AttributeSet $r1, int[] $r2, int $i0, int $i1) throws  {
        return new TintTypedArray($r0, $r0.obtainStyledAttributes($r1, $r2, $i0, $i1));
    }

    private TintTypedArray(Context $r1, TypedArray $r2) throws  {
        this.mContext = $r1;
        this.mWrapped = $r2;
    }

    public Drawable getDrawable(int $i0) throws  {
        if (this.mWrapped.hasValue($i0)) {
            int $i1 = this.mWrapped.getResourceId($i0, 0);
            if ($i1 != 0) {
                return AppCompatDrawableManager.get().getDrawable(this.mContext, $i1);
            }
        }
        return this.mWrapped.getDrawable($i0);
    }

    public Drawable getDrawableIfKnown(int $i0) throws  {
        if (this.mWrapped.hasValue($i0)) {
            $i0 = this.mWrapped.getResourceId($i0, 0);
            if ($i0 != 0) {
                return AppCompatDrawableManager.get().getDrawable(this.mContext, $i0, true);
            }
        }
        return null;
    }

    public int length() throws  {
        return this.mWrapped.length();
    }

    public int getIndexCount() throws  {
        return this.mWrapped.getIndexCount();
    }

    public int getIndex(int $i0) throws  {
        return this.mWrapped.getIndex($i0);
    }

    public Resources getResources() throws  {
        return this.mWrapped.getResources();
    }

    public CharSequence getText(int $i0) throws  {
        return this.mWrapped.getText($i0);
    }

    public String getString(int $i0) throws  {
        return this.mWrapped.getString($i0);
    }

    public String getNonResourceString(int $i0) throws  {
        return this.mWrapped.getNonResourceString($i0);
    }

    public boolean getBoolean(int $i0, boolean $z0) throws  {
        return this.mWrapped.getBoolean($i0, $z0);
    }

    public int getInt(int $i0, int $i1) throws  {
        return this.mWrapped.getInt($i0, $i1);
    }

    public float getFloat(int $i0, float $f0) throws  {
        return this.mWrapped.getFloat($i0, $f0);
    }

    public int getColor(int $i0, int $i1) throws  {
        return this.mWrapped.getColor($i0, $i1);
    }

    public ColorStateList getColorStateList(int $i0) throws  {
        return this.mWrapped.getColorStateList($i0);
    }

    public int getInteger(int $i0, int $i1) throws  {
        return this.mWrapped.getInteger($i0, $i1);
    }

    public float getDimension(int $i0, float $f0) throws  {
        return this.mWrapped.getDimension($i0, $f0);
    }

    public int getDimensionPixelOffset(int $i0, int $i1) throws  {
        return this.mWrapped.getDimensionPixelOffset($i0, $i1);
    }

    public int getDimensionPixelSize(int $i0, int $i1) throws  {
        return this.mWrapped.getDimensionPixelSize($i0, $i1);
    }

    public int getLayoutDimension(int $i0, String $r1) throws  {
        return this.mWrapped.getLayoutDimension($i0, $r1);
    }

    public int getLayoutDimension(int $i0, int $i1) throws  {
        return this.mWrapped.getLayoutDimension($i0, $i1);
    }

    public float getFraction(int $i0, int $i1, int $i2, float $f0) throws  {
        return this.mWrapped.getFraction($i0, $i1, $i2, $f0);
    }

    public int getResourceId(int $i0, int $i1) throws  {
        return this.mWrapped.getResourceId($i0, $i1);
    }

    public CharSequence[] getTextArray(int $i0) throws  {
        return this.mWrapped.getTextArray($i0);
    }

    public boolean getValue(int $i0, TypedValue $r1) throws  {
        return this.mWrapped.getValue($i0, $r1);
    }

    public int getType(int $i0) throws  {
        return this.mWrapped.getType($i0);
    }

    public boolean hasValue(int $i0) throws  {
        return this.mWrapped.hasValue($i0);
    }

    public TypedValue peekValue(int $i0) throws  {
        return this.mWrapped.peekValue($i0);
    }

    public String getPositionDescription() throws  {
        return this.mWrapped.getPositionDescription();
    }

    public void recycle() throws  {
        this.mWrapped.recycle();
    }

    public int getChangingConfigurations() throws  {
        return this.mWrapped.getChangingConfigurations();
    }
}
