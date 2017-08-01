package com.waze.view.button;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.utils.PixelMeasure;

public class BadgeButton extends FrameLayout {
    private FrameLayout mBackgroundContainer;
    private TextView mBadgeLabel;
    private String mBadgeText;

    class C29821 extends ViewOutlineProvider {
        C29821() {
        }

        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, BadgeButton.this.getMeasuredWidth(), BadgeButton.this.getMeasuredHeight());
        }
    }

    public BadgeButton(Context context) {
        this(context, null);
    }

    public BadgeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.badge_indicator_view, null);
        this.mBackgroundContainer = (FrameLayout) content.findViewById(C1283R.id.badgeContainer);
        this.mBadgeLabel = (TextView) content.findViewById(C1283R.id.lblIndicatorValue);
        if (VERSION.SDK_INT >= 21) {
            setElevation((float) PixelMeasure.dp(4));
            setOutlineProvider(new C29821());
        }
        addView(content);
    }

    public String getBadgeText() {
        return this.mBadgeText;
    }

    public void setBadgeText(String badgeText) {
        this.mBadgeText = badgeText;
        this.mBadgeLabel.setText(badgeText);
    }

    public void setBadgeBackgroundColor(int backgroundColor) {
        this.mBackgroundContainer.getBackground().setColorFilter(backgroundColor, Mode.MULTIPLY);
    }

    public void setBadgeTextColor(int textColor) {
        this.mBadgeLabel.setTextColor(textColor);
    }
}
