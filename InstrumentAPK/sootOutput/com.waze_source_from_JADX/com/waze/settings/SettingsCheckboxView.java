package com.waze.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;

public class SettingsCheckboxView extends RelativeLayout {
    private CheckBox checkBox = ((CheckBox) findViewById(C1283R.id.settingsCheckboxCheckbox));
    private LayoutInflater inflater;
    private TextView textView = ((TextView) findViewById(C1283R.id.settingsCheckboxText));

    class C26651 implements OnClickListener {
        C26651() {
        }

        public void onClick(View v) {
            ((CheckBox) SettingsCheckboxView.this.findViewById(C1283R.id.settingsCheckboxCheckbox)).performClick();
        }
    }

    public SettingsCheckboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflater = LayoutInflater.from(context);
        this.inflater.inflate(C1283R.layout.settings_checkbox, this);
        findViewById(C1283R.id.settingsCheckboxMainLayout).setOnClickListener(new C26651());
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setValue(boolean value) {
        this.checkBox.setChecked(value);
    }

    public boolean isChecked() {
        return this.checkBox.isChecked();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.checkBox.setOnCheckedChangeListener(listener);
    }
}
