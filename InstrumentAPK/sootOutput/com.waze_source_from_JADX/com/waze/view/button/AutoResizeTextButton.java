package com.waze.view.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.waze.C1283R;
import com.waze.R;
import com.waze.view.text.AutoResizeTextView;

public class AutoResizeTextButton extends RelativeLayout {
    private AutoResizeTextView textView = ((AutoResizeTextView) findViewById(C1283R.id.autoResizeButtonText));

    class C29811 implements OnClickListener {
        C29811() {
        }

        public void onClick(View v) {
            AutoResizeTextButton.this.performClick();
        }
    }

    public AutoResizeTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.AutoResizeTextButton);
        inflater.inflate(C1283R.layout.auto_resize_text_button, this);
        float minSize = attrArray.getDimension(0, 12.0f);
        String textStyle = attrArray.getString(2);
        int textColor = attrArray.getColor(3, -1);
        float textSize = attrArray.getDimension(4, 14.0f);
        float paddingLeft = attrArray.getDimension(5, 0.0f);
        int buttonImage = attrArray.getResourceId(1, C1283R.drawable.close_selector);
        if (textStyle != null && textStyle.equals("bold")) {
            this.textView.setTypeface(null, 1);
        }
        this.textView.setTextColor(textColor);
        this.textView.setTextSize(0, textSize);
        this.textView.setPadding((int) (paddingLeft * context.getResources().getDisplayMetrics().density), 0, 0, 0);
        this.textView.setMinTextSize(minSize);
        setButtonImageResource(buttonImage);
        findViewById(C1283R.id.autoResizeButton).setOnClickListener(new C29811());
    }

    public void setButtonImageResource(int buttonImage) {
        if (buttonImage == 0) {
            ((ImageButton) findViewById(C1283R.id.autoResizeButton)).setImageDrawable(null);
        } else {
            ((ImageButton) findViewById(C1283R.id.autoResizeButton)).setImageResource(buttonImage);
        }
    }

    public void setText(String text) {
        this.textView.setText(text);
        setButtonImageResource(0);
    }

    public void setTextColor(int color) {
        this.textView.setTextColor(color);
    }

    public void setTextColor(int red, int green, int blue) {
        this.textView.setTextColor(Color.rgb(red, green, blue));
    }
}
