package android.support.v4.view;

import android.graphics.Rect;

public class WindowInsetsCompat {
    public int getStableInsetBottom() throws  {
        return 0;
    }

    public int getStableInsetLeft() throws  {
        return 0;
    }

    public int getStableInsetRight() throws  {
        return 0;
    }

    public int getStableInsetTop() throws  {
        return 0;
    }

    public int getSystemWindowInsetBottom() throws  {
        return 0;
    }

    public int getSystemWindowInsetLeft() throws  {
        return 0;
    }

    public int getSystemWindowInsetRight() throws  {
        return 0;
    }

    public int getSystemWindowInsetTop() throws  {
        return 0;
    }

    public boolean hasInsets() throws  {
        return false;
    }

    public boolean hasStableInsets() throws  {
        return false;
    }

    public boolean hasSystemWindowInsets() throws  {
        return false;
    }

    public boolean isConsumed() throws  {
        return false;
    }

    public boolean isRound() throws  {
        return false;
    }

    WindowInsetsCompat() throws  {
    }

    public WindowInsetsCompat consumeSystemWindowInsets() throws  {
        return this;
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int left, int top, int right, int bottom) throws  {
        return this;
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect systemWindowInsets) throws  {
        return this;
    }

    public WindowInsetsCompat consumeStableInsets() throws  {
        return this;
    }
}
