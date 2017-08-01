package android.support.v7.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

class ResourcesWrapper extends Resources {
    private final Resources mResources;

    public ResourcesWrapper(Resources $r1) throws  {
        super($r1.getAssets(), $r1.getDisplayMetrics(), $r1.getConfiguration());
        this.mResources = $r1;
    }

    public CharSequence getText(int $i0) throws NotFoundException {
        return this.mResources.getText($i0);
    }

    public CharSequence getQuantityText(int $i0, int $i1) throws NotFoundException {
        return this.mResources.getQuantityText($i0, $i1);
    }

    public String getString(int $i0) throws NotFoundException {
        return this.mResources.getString($i0);
    }

    public String getString(int $i0, Object... $r1) throws NotFoundException {
        return this.mResources.getString($i0, $r1);
    }

    public String getQuantityString(int $i0, int $i1, Object... $r1) throws NotFoundException {
        return this.mResources.getQuantityString($i0, $i1, $r1);
    }

    public String getQuantityString(int $i0, int $i1) throws NotFoundException {
        return this.mResources.getQuantityString($i0, $i1);
    }

    public CharSequence getText(int $i0, CharSequence $r1) throws  {
        return this.mResources.getText($i0, $r1);
    }

    public CharSequence[] getTextArray(int $i0) throws NotFoundException {
        return this.mResources.getTextArray($i0);
    }

    public String[] getStringArray(int $i0) throws NotFoundException {
        return this.mResources.getStringArray($i0);
    }

    public int[] getIntArray(int $i0) throws NotFoundException {
        return this.mResources.getIntArray($i0);
    }

    public TypedArray obtainTypedArray(int $i0) throws NotFoundException {
        return this.mResources.obtainTypedArray($i0);
    }

    public float getDimension(int $i0) throws NotFoundException {
        return this.mResources.getDimension($i0);
    }

    public int getDimensionPixelOffset(int $i0) throws NotFoundException {
        return this.mResources.getDimensionPixelOffset($i0);
    }

    public int getDimensionPixelSize(int $i0) throws NotFoundException {
        return this.mResources.getDimensionPixelSize($i0);
    }

    public float getFraction(int $i0, int $i1, int $i2) throws  {
        return this.mResources.getFraction($i0, $i1, $i2);
    }

    public Drawable getDrawable(int $i0) throws NotFoundException {
        return this.mResources.getDrawable($i0);
    }

    public Drawable getDrawable(int $i0, Theme $r1) throws NotFoundException {
        return this.mResources.getDrawable($i0, $r1);
    }

    public Drawable getDrawableForDensity(int $i0, int $i1) throws NotFoundException {
        return this.mResources.getDrawableForDensity($i0, $i1);
    }

    public Drawable getDrawableForDensity(int $i0, int $i1, Theme $r1) throws  {
        return this.mResources.getDrawableForDensity($i0, $i1, $r1);
    }

    public Movie getMovie(int $i0) throws NotFoundException {
        return this.mResources.getMovie($i0);
    }

    public int getColor(int $i0) throws NotFoundException {
        return this.mResources.getColor($i0);
    }

    public ColorStateList getColorStateList(int $i0) throws NotFoundException {
        return this.mResources.getColorStateList($i0);
    }

    public boolean getBoolean(int $i0) throws NotFoundException {
        return this.mResources.getBoolean($i0);
    }

    public int getInteger(int $i0) throws NotFoundException {
        return this.mResources.getInteger($i0);
    }

    public XmlResourceParser getLayout(int $i0) throws NotFoundException {
        return this.mResources.getLayout($i0);
    }

    public XmlResourceParser getAnimation(int $i0) throws NotFoundException {
        return this.mResources.getAnimation($i0);
    }

    public XmlResourceParser getXml(int $i0) throws NotFoundException {
        return this.mResources.getXml($i0);
    }

    public InputStream openRawResource(int $i0) throws NotFoundException {
        return this.mResources.openRawResource($i0);
    }

    public InputStream openRawResource(int $i0, TypedValue $r1) throws NotFoundException {
        return this.mResources.openRawResource($i0, $r1);
    }

    public AssetFileDescriptor openRawResourceFd(int $i0) throws NotFoundException {
        return this.mResources.openRawResourceFd($i0);
    }

    public void getValue(int $i0, TypedValue $r1, boolean $z0) throws NotFoundException {
        this.mResources.getValue($i0, $r1, $z0);
    }

    public void getValueForDensity(int $i0, int $i1, TypedValue $r1, boolean $z0) throws NotFoundException {
        this.mResources.getValueForDensity($i0, $i1, $r1, $z0);
    }

    public void getValue(String $r1, TypedValue $r2, boolean $z0) throws NotFoundException {
        this.mResources.getValue($r1, $r2, $z0);
    }

    public TypedArray obtainAttributes(AttributeSet $r1, int[] $r2) throws  {
        return this.mResources.obtainAttributes($r1, $r2);
    }

    public void updateConfiguration(Configuration $r1, DisplayMetrics $r2) throws  {
        super.updateConfiguration($r1, $r2);
        if (this.mResources != null) {
            this.mResources.updateConfiguration($r1, $r2);
        }
    }

    public DisplayMetrics getDisplayMetrics() throws  {
        return this.mResources.getDisplayMetrics();
    }

    public Configuration getConfiguration() throws  {
        return this.mResources.getConfiguration();
    }

    public int getIdentifier(String $r1, String $r2, String $r3) throws  {
        return this.mResources.getIdentifier($r1, $r2, $r3);
    }

    public String getResourceName(int $i0) throws NotFoundException {
        return this.mResources.getResourceName($i0);
    }

    public String getResourcePackageName(int $i0) throws NotFoundException {
        return this.mResources.getResourcePackageName($i0);
    }

    public String getResourceTypeName(int $i0) throws NotFoundException {
        return this.mResources.getResourceTypeName($i0);
    }

    public String getResourceEntryName(int $i0) throws NotFoundException {
        return this.mResources.getResourceEntryName($i0);
    }

    public void parseBundleExtras(XmlResourceParser $r1, Bundle $r2) throws XmlPullParserException, IOException {
        this.mResources.parseBundleExtras($r1, $r2);
    }

    public void parseBundleExtra(String $r1, AttributeSet $r2, Bundle $r3) throws XmlPullParserException {
        this.mResources.parseBundleExtra($r1, $r2, $r3);
    }
}
