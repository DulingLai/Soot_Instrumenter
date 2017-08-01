package com.waze.view.navbar;

import android.content.Context;
import android.util.AttributeSet;
import com.waze.navbar.NavBar;
import com.waze.view.text.WazeTextView;

public class NavBarText extends WazeTextView {
    private NavBar navBar;

    public NavBarText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NavBarText(Context context) {
        super(context);
    }

    public NavBarText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.navBar != null) {
            this.navBar.adjustImages();
        }
    }

    public void setNavBar(NavBar navBar) {
        this.navBar = navBar;
    }
}
