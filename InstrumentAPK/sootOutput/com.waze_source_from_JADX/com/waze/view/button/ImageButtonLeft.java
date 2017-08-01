package com.waze.view.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.R;

public class ImageButtonLeft extends RelativeLayout {
    private RelativeLayout mBackground;
    private ImageView mImageView;
    private RelativeLayout mRoot;
    private TextView mTextView;

    public ImageButtonLeft(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ImageButtonLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ImageButtonLeft(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(C1283R.layout.image_button_left, this);
        this.mTextView = (TextView) findViewById(C1283R.id.image_button_text);
        this.mImageView = (ImageView) findViewById(C1283R.id.image_button_image);
        this.mRoot = (RelativeLayout) findViewById(C1283R.id.image_button_left_root);
        this.mBackground = (RelativeLayout) findViewById(C1283R.id.image_button_left_bg);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageButtonLeft, defStyle, 0);
        int btnImage = ta.getResourceId(2, -1);
        int btnBackground = ta.getResourceId(3, -1);
        String text = ta.getString(5);
        int textColor = ta.getColor(0, -16777216);
        float textSize = (float) ta.getDimensionPixelSize(1, 14);
        int btnImagePadding = ta.getDimensionPixelSize(4, 9);
        Log.d("WAZE DEBUG", "Font size: " + textSize + ". Image Padding: " + btnImagePadding + ". Text: " + text);
        this.mImageView.setImageResource(btnImage);
        this.mImageView.setPadding(btnImagePadding, btnImagePadding, btnImagePadding, btnImagePadding);
        this.mTextView.setText(text);
        this.mTextView.setTextColor(textColor);
        this.mTextView.setTextSize(0, textSize);
        this.mBackground.setBackgroundResource(btnBackground);
        ta.recycle();
    }

    public void setBackgroundResource(int res) {
        this.mBackground.setBackgroundResource(res);
    }

    public void setImageResource(int res) {
        this.mImageView.setImageResource(res);
    }

    public void setText(CharSequence text) {
        this.mTextView.setText(text);
    }
}
