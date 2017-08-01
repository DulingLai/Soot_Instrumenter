package com.waze.navigate;

import android.graphics.drawable.Drawable;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;

public class SearchButton {
    private ImageButton button;
    private Drawable drawableIdle;
    private Drawable drawableSelected;

    SearchButton(ImageButton $r1, Drawable $r2, Drawable $r3) throws  {
        this.button = $r1;
        this.drawableSelected = $r2;
        this.drawableIdle = $r3;
        idleButton();
    }

    public void selectButton() throws  {
        this.button.setImageDrawable(this.drawableSelected);
        if (this.button != null && this.button.getParent() != null && this.button.getParent().getParent() != null) {
            if (this.button.getParent().getParent() instanceof HorizontalScrollView) {
                HorizontalScrollView $r4 = (HorizontalScrollView) this.button.getParent().getParent();
                $r4.smoothScrollTo(getButtonXPosition() - ($r4.getWidth() / 2), 0);
                return;
            }
            ScrollView $r5 = (ScrollView) this.button.getParent().getParent();
            $r5.smoothScrollTo(0, getButtonYPosition() - ($r5.getHeight() / 2));
        }
    }

    public void idleButton() throws  {
        this.button.setImageDrawable(this.drawableIdle);
    }

    public int getButtonXPosition() throws  {
        return (this.button.getLeft() + this.button.getRight()) / 2;
    }

    public int getButtonYPosition() throws  {
        return (this.button.getTop() + this.button.getBottom()) / 2;
    }
}
