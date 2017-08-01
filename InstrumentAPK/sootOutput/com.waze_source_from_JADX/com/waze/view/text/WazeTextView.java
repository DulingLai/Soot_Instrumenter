package com.waze.view.text;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.waze.R;
import com.waze.ResManager;

public class WazeTextView extends TextView {
    private String mText;
    int nType = 0;

    public WazeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.nType = context.obtainStyledAttributes(attrs, R.styleable.WazeTextView).getInteger(0, 1);
        setFont(context);
    }

    public WazeTextView(Context context) {
        super(context);
        setFont(context);
    }

    public WazeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.nType = context.obtainStyledAttributes(attrs, R.styleable.WazeTextView).getInteger(0, 1);
        setFont(context);
    }

    public void setFontType(int type) {
        this.nType = type;
        setFont(getContext());
    }

    private void setFont(Context context) {
        int style = 0;
        boolean isLight = false;
        if (getTypeface() != null) {
            if (getTypeface().isBold()) {
                style = 1;
            }
            if (getTypeface().isItalic()) {
                isLight = true;
            }
        }
        Typeface font = getTypeface();
        if (!isInEditMode()) {
            font = loadTypeface(context, this.nType, isLight, font);
        }
        setTypeface(font, style);
    }

    public void setFont(int nType, int style) {
        setTypeface(loadTypeface(getContext(), nType, false, getTypeface()), style);
    }

    public static Typeface loadTypeface(Context context, int style, boolean isLight, Typeface font) {
        switch (style) {
            case 1:
                return ResManager.getRobotoReg(context);
            case 2:
                return ResManager.getRobotoBlack(context);
            case 3:
                return ResManager.getRobotoBold(context);
            case 4:
                return ResManager.getRobotoLight(context);
            case 5:
                return ResManager.getRobotoMedium(context);
            case 6:
                return ResManager.getRobotoCondensedReg(context);
            case 7:
                return ResManager.getRobotoCondensedBold(context);
            case 8:
                return ResManager.getRobotoCondensedLight(context);
            case 9:
                return ResManager.getRobotoSlabReg(context);
            case 10:
                return ResManager.getRobotoSlabBold(context);
            case 11:
                return ResManager.getProximaBold(context);
            case 12:
                return ResManager.getProximaLight(context);
            case 13:
                return ResManager.getProximaSemibold(context);
            case 14:
                return ResManager.getProximaReg(context);
            case 15:
                return ResManager.getRobotoRegItalic(context);
            case 16:
                return ResManager.getRobotoBoldItalic(context);
            case 17:
                return ResManager.getRobotoLightItalic(context);
            case 18:
                return ResManager.getRobotoMediumItalic(context);
            case 19:
                return ResManager.getProximaExtraBoldItalic(context);
            case 20:
                return ResManager.getProximaLightItalic(context);
            case 21:
                return ResManager.getProximaSemiboldItalic(context);
            case 22:
                return ResManager.getProximaRegItalic(context);
            case 23:
                return ResManager.getProximaExBold(context);
            default:
                if (isLight) {
                    return ResManager.getRobotoLight(context);
                }
                return ResManager.getRobotoReg(context);
        }
    }

    public void setDrawableRight(Drawable drawable) {
        Drawable[] d = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(d[0], d[1], drawable, d[3]);
    }

    public void setDrawableLeft(Drawable drawable) {
        Drawable[] d = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(drawable, d[1], d[2], d[3]);
    }

    public void setText(CharSequence text, BufferType type) {
        if (this.mText == null || text == null || !this.mText.equals(text)) {
            if (text != null) {
                this.mText = text.toString();
            } else {
                this.mText = "";
            }
            super.setText(text, type);
        }
    }

    public void setRightBreak(int margin) {
        Layout layout = getLayout();
        int lastLine = layout.getLineCount() - 1;
        if (lastLine > 0) {
            float lineWidth = layout.getLineWidth(lastLine);
            int myWidth = getWidth();
            if (lineWidth > ((float) (myWidth - margin))) {
                CharSequence text = getText();
                int start = layout.getLineStart(lastLine);
                int lineBreak = text.subSequence(start, start + ((int) (((float) ((myWidth - margin) * (layout.getLineEnd(lastLine) - start))) / lineWidth))).toString().lastIndexOf(32);
                if (lineBreak > 0) {
                    StringBuilder bob = new StringBuilder(text);
                    bob.setCharAt(start + lineBreak, '\n');
                    setText(bob.toString());
                    requestLayout();
                    invalidate();
                }
            }
        }
    }
}
