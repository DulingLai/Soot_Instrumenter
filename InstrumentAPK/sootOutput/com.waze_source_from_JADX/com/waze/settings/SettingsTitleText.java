package com.waze.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.R;

public class SettingsTitleText extends RelativeLayout {
    private LayoutInflater inflater;

    public SettingsTitleText(Context context, AttributeSet attrs) {
        boolean isTop = true;
        super(context, attrs);
        int position = context.obtainStyledAttributes(attrs, R.styleable.SettingsTitleText).getInteger(0, 0);
        this.inflater = LayoutInflater.from(context);
        this.inflater.inflate(C1283R.layout.settings_title_text, this);
        if (position != 1) {
            isTop = false;
        }
        setTop(isTop);
    }

    void setTop(boolean isTop) {
        findViewById(C1283R.id.settingsTitleMarginShadow).setVisibility(isTop ? 8 : 0);
        if (isTop) {
            findViewById(C1283R.id.settingsTitleMargin).getLayoutParams().height = getResources().getDimensionPixelSize(C1283R.dimen.settingsItemGapHalf);
        }
    }

    public SettingsTitleText(Context context, boolean isTop) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.inflater.inflate(C1283R.layout.settings_title_text, this);
        findViewById(C1283R.id.settingsTitleMarginShadow).setVisibility(isTop ? 8 : 0);
    }

    public void setText(CharSequence text) {
        ((TextView) findViewById(C1283R.id.settingsTitleText)).setText(text);
    }
}
