package com.waze.view.title;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.view.text.WazeTextView;

public class TitleBar extends RelativeLayout {
    public static final int TITLE_BAR_BUTTON_TYPE_DEFAULT = 0;
    public static final int TITLE_BAR_BUTTON_TYPE_TEXT = 1;
    public static final int TITLE_BAR_SKIN_BLUE = 0;
    public static final int TITLE_BAR_SKIN_WHITE = 1;
    private ColorStateList activeColor;
    private Activity activity;
    private ImageButton closeButton;
    private ImageButton closeButton2;
    private WazeTextView closeButtonText;
    private WazeTextView closeButtonText2;
    private int closeButtonType = 0;
    private View closeDivider;
    private View closeDivider2;
    private boolean closeEnabled = true;
    private OnClickListener closeListener = null;
    private int closedResultCode = -1;
    private ActivityBase mActivity;
    private int mBackgroundResource = 0;
    private int mTextColor = 0;
    private int mUpButtonResource = 0;
    private String textPlaceholder;
    private TextView textView;
    private ImageButton upButton;

    class C32751 implements OnClickListener {
        C32751() {
        }

        public void onClick(View v) {
            if (TitleBar.this.closeListener != null) {
                TitleBar.this.closeListener.onClick(v);
            } else {
                TitleBar.this.onCloseClicked();
            }
        }
    }

    class C32762 implements OnClickListener {
        C32762() {
        }

        public void onClick(View v) {
            if (TitleBar.this.closeListener != null) {
                TitleBar.this.closeListener.onClick(v);
            } else {
                TitleBar.this.onCloseClicked();
            }
        }
    }

    class C32773 implements OnClickListener {
        C32773() {
        }

        public void onClick(View v) {
            if (TitleBar.this.mActivity != null) {
                TitleBar.this.mActivity.onBackPressed();
            }
        }
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        inflater.inflate(C1283R.layout.title_bar, this);
        if (context instanceof ActivityBase) {
            this.mActivity = (ActivityBase) context;
        }
        setBackgroundColor(getResources().getColor(C1283R.color.title_bar));
        this.textView = (TextView) findViewById(C1283R.id.titleBarTitleText);
        this.textPlaceholder = attrArray.getString(0);
        this.closeButtonType = attrArray.getInt(2, 0);
        int textPosition = attrArray.getInteger(1, 0);
        if (this.textPlaceholder != null) {
            this.textView.setText(this.textPlaceholder);
        }
        this.textView.setGravity(textPosition == 0 ? GravityCompat.START : 17);
        this.closeButton = (ImageButton) findViewById(C1283R.id.titleBarCloseButton);
        this.closeButton2 = (ImageButton) findViewById(C1283R.id.titleBarCloseButton2);
        this.closeButtonText = (WazeTextView) findViewById(C1283R.id.titleBarCloseButtonText);
        this.closeButtonText2 = (WazeTextView) findViewById(C1283R.id.titleBarCloseButtonText2);
        this.upButton = (ImageButton) findViewById(C1283R.id.titleBarCloseButtonFake);
        this.closeDivider = findViewById(C1283R.id.titleBarCloseDivider);
        this.closeDivider2 = findViewById(C1283R.id.titleBarCloseDivider2);
        this.upButton.setImageResource(C1283R.drawable.up_btn);
        this.closeButton.setOnClickListener(new C32751());
        this.closeButtonText.setOnClickListener(new C32762());
        this.upButton.setOnClickListener(new C32773());
        applyCloseButtonType();
        if (attrArray.getInt(3, 0) == 1) {
            setBackgroundResource(C1283R.drawable.title_bar_white_bg);
            this.textView.setTextColor(getResources().getColor(C1283R.color.Dark));
            this.textView.setVisibility(8);
            this.upButton.setImageResource(C1283R.drawable.white_screen_back_button);
            this.closeButton.setImageResource(C1283R.drawable.white_screen_x_button);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getResources().getDimensionPixelSize(C1283R.dimen.titleBarHeight);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, 1073741824));
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    public void init(Activity activity, String title, int closeTextResId) {
        init(activity, title, closeTextResId != 0 ? getResources().getString(closeTextResId) : null);
    }

    public void init(Activity activity, String title, String closeButtonText) {
        NativeManager nativeManager = NativeManager.getInstance();
        this.activity = activity;
        if (this.textView != null) {
            this.textView.setText(nativeManager.getLanguageString(title));
        }
        if (this.closeButton.getVisibility() == 0 && closeButtonText != null && closeButtonText.length() > 0) {
            setCloseText(nativeManager.getLanguageString(closeButtonText));
        }
    }

    public void init(Activity activity, int titleDS, boolean showClose) {
        init(activity, NativeManager.getInstance().getLanguageString(titleDS), showClose);
    }

    public void init(Activity activity, int titleDS) {
        init(activity, NativeManager.getInstance().getLanguageString(titleDS));
    }

    public void init(Activity activity, int titleDS, int closeButtonTextDS) {
        NativeManager nativeManager = NativeManager.getInstance();
        init(activity, nativeManager.getLanguageString(titleDS), nativeManager.getLanguageString(closeButtonTextDS));
    }

    public void init(Activity activity, String title) {
        init(activity, title, null);
    }

    public void init(Activity activity, String title, boolean showClose) {
        setCloseVisibility(showClose);
        init(activity, title);
    }

    public void setCloseVisibility(boolean visible) {
        if (visible) {
            if (this.closeButtonType == 1) {
                this.closeButtonText.setVisibility(0);
            } else {
                this.closeButton.setVisibility(0);
            }
            this.closeDivider.setVisibility(0);
            return;
        }
        this.closeButton.setVisibility(4);
        this.closeButtonText.setVisibility(4);
        this.closeDivider.setVisibility(4);
    }

    public void setTitle(String title) {
        if (this.textView != null) {
            this.textView.setVisibility(0);
            this.textView.setText(title);
        }
    }

    public String getTitle() {
        if (this.textView != null) {
            return this.textView.getText().toString();
        }
        return "";
    }

    public void setCloseImageResource(int resource) {
        this.closeButton.setVisibility(0);
        this.closeDivider.setVisibility(0);
        this.closeButtonText.setVisibility(8);
        if (resource == 0) {
            this.closeButton.setImageResource(C1283R.drawable.close_selector);
        } else {
            this.closeButton.setImageResource(resource);
        }
    }

    public void setCloseButtonMargin(int margin) {
        LayoutParams params = (LayoutParams) this.closeButton.getLayoutParams();
        params.topMargin = margin;
        params.leftMargin = margin;
        params.bottomMargin = margin;
        params.rightMargin = margin;
        this.closeButton.setLayoutParams(params);
    }

    public void setCloseText(String text) {
        if (text != null) {
            this.closeButtonType = 1;
            applyCloseButtonType();
            this.closeButtonText.setText(text);
            return;
        }
        this.closeButtonType = 0;
        applyCloseButtonType();
    }

    public void setCloseTextColor(int color) {
        this.closeButtonText.setTextColor(color);
    }

    public void setCloseTextColor(int red, int green, int blue) {
        this.closeButtonText.setTextColor(Color.rgb(red, green, blue));
    }

    public void setCloseResultCode(int resultCode) {
        this.closedResultCode = resultCode;
    }

    public void setOnClickCloseListener(OnClickListener listener) {
        this.closeListener = listener;
    }

    public void setCloseEnabled(boolean enabled) {
        this.closeEnabled = enabled;
    }

    public void onCloseClicked() {
        if (this.closeEnabled && this.activity != null) {
            this.activity.setResult(this.closedResultCode);
            this.activity.finish();
        }
    }

    public void setUpButtonDisabled() {
        this.upButton.setVisibility(8);
        setPadding(0, 0, 0, 0);
    }

    public void setUpButtonResource(int resource) {
        this.upButton.setImageResource(resource);
        this.mUpButtonResource = resource;
    }

    private void applyCloseButtonType() {
        switch (this.closeButtonType) {
            case 0:
                this.closeButton.setVisibility(0);
                this.closeButtonText.setVisibility(8);
                if (this.closeButton.getVisibility() == 0) {
                    this.closeButtonText.setText(null);
                }
                this.closeButton.setImageResource(C1283R.drawable.map_selector);
                break;
            case 1:
                this.closeButton.setVisibility(8);
                this.closeButtonText.setVisibility(0);
                if (this.closeButtonText.getVisibility() == 0) {
                    this.closeButton.setImageResource(0);
                    break;
                }
                break;
            default:
                Log.e(Logger.TAG, "Undefined TitleBar type");
                break;
        }
        setPadding(0, 0, 0, 0);
    }

    public void setCloseButtonDisabled(boolean isDisabled) {
        this.closeButtonText.setEnabled(!isDisabled);
        if (isDisabled) {
            this.activeColor = this.closeButtonText.getTextColors();
            this.closeButtonText.setTextColor(Color.argb(128, 255, 255, 255));
        } else if (this.activeColor != null) {
            this.closeButtonText.setTextColor(this.activeColor);
        }
    }

    public void setButtonTwo(int resource, String text, OnClickListener onClick) {
        if (resource == 0 && (text == null || text.isEmpty())) {
            this.closeButton2.setVisibility(8);
            this.closeButtonText2.setVisibility(8);
            this.closeDivider2.setVisibility(8);
            return;
        }
        this.closeDivider2.setVisibility(0);
        findViewById(C1283R.id.titleBarCloseTitle2).setVisibility(0);
        if (resource == 0) {
            this.closeButtonText2.setVisibility(0);
            this.closeButtonText2.setText(text);
            this.closeButtonText2.setOnClickListener(onClick);
            this.closeDivider2.setVisibility(8);
            return;
        }
        this.closeButton2.setVisibility(0);
        this.closeButton2.setImageResource(resource);
        this.closeButton2.setOnClickListener(onClick);
        this.closeButtonText2.setVisibility(8);
    }

    public void setTextColor(int color) {
        this.mTextColor = color;
        this.textView.setTextColor(ColorStateList.valueOf(color));
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setBackgroundResource(@DrawableRes int resid) {
        this.mBackgroundResource = resid;
        super.setBackgroundResource(resid);
    }

    public int getBackgroundResource() {
        return this.mBackgroundResource;
    }

    public int getUpButtonResource() {
        return this.mUpButtonResource;
    }
}
