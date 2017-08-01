package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class AppCompatSeekBar extends SeekBar {
    private AppCompatSeekBarHelper mAppCompatSeekBarHelper;
    private AppCompatDrawableManager mDrawableManager;

    public AppCompatSeekBar(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatSeekBar(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.seekBarStyle);
    }

    public AppCompatSeekBar(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mDrawableManager = AppCompatDrawableManager.get();
        this.mAppCompatSeekBarHelper = new AppCompatSeekBarHelper(this, this.mDrawableManager);
        this.mAppCompatSeekBarHelper.loadFromAttributes($r2, $i0);
    }
}
