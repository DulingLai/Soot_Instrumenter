package android.support.v4.view;

import android.graphics.Rect;
import android.view.WindowInsets;

class WindowInsetsCompatApi21 extends WindowInsetsCompat {
    private final WindowInsets mSource;

    WindowInsetsCompatApi21(WindowInsets $r1) throws  {
        this.mSource = $r1;
    }

    public int getSystemWindowInsetLeft() throws  {
        return this.mSource.getSystemWindowInsetLeft();
    }

    public int getSystemWindowInsetTop() throws  {
        return this.mSource.getSystemWindowInsetTop();
    }

    public int getSystemWindowInsetRight() throws  {
        return this.mSource.getSystemWindowInsetRight();
    }

    public int getSystemWindowInsetBottom() throws  {
        return this.mSource.getSystemWindowInsetBottom();
    }

    public boolean hasSystemWindowInsets() throws  {
        return this.mSource.hasSystemWindowInsets();
    }

    public boolean hasInsets() throws  {
        return this.mSource.hasInsets();
    }

    public boolean isConsumed() throws  {
        return this.mSource.isConsumed();
    }

    public boolean isRound() throws  {
        return this.mSource.isRound();
    }

    public WindowInsetsCompat consumeSystemWindowInsets() throws  {
        return new WindowInsetsCompatApi21(this.mSource.consumeSystemWindowInsets());
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int $i0, int $i1, int $i2, int $i3) throws  {
        return new WindowInsetsCompatApi21(this.mSource.replaceSystemWindowInsets($i0, $i1, $i2, $i3));
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect $r1) throws  {
        return new WindowInsetsCompatApi21(this.mSource.replaceSystemWindowInsets($r1));
    }

    public int getStableInsetTop() throws  {
        return this.mSource.getStableInsetTop();
    }

    public int getStableInsetLeft() throws  {
        return this.mSource.getStableInsetLeft();
    }

    public int getStableInsetRight() throws  {
        return this.mSource.getStableInsetRight();
    }

    public int getStableInsetBottom() throws  {
        return this.mSource.getStableInsetBottom();
    }

    public boolean hasStableInsets() throws  {
        return this.mSource.hasStableInsets();
    }

    public WindowInsetsCompat consumeStableInsets() throws  {
        return new WindowInsetsCompatApi21(this.mSource.consumeStableInsets());
    }

    WindowInsets unwrap() throws  {
        return this.mSource;
    }
}
