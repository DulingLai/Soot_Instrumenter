package com.waze.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.waze.C1283R;
import com.waze.ResManager;
import com.waze.view.text.WazeTextView;

public class PhoneLoginFillInField extends LinearLayout {
    private Context mContext;
    private LinearLayout mFieldLinearLayout;
    private EditText mInputText;
    private WazeTextView mLabaleText;
    private OnClickListener mListener;

    public void setLabaleText(String text) {
        this.mLabaleText.setText(text);
    }

    public void setInputHintText(String text) {
        this.mInputText.setHint(text);
    }

    public String getText() {
        return this.mInputText.getText().toString();
    }

    public void setEditTextDpMarginTop(float size) {
        int top = (int) convertDpToPixels(size, this.mContext);
        int right = this.mFieldLinearLayout.getPaddingRight();
        this.mFieldLinearLayout.setPadding(this.mFieldLinearLayout.getPaddingLeft(), top, right, this.mFieldLinearLayout.getPaddingBottom());
    }

    public void setEditTextDpMarginBottom(float size) {
        int bottom = (int) convertDpToPixels(size, this.mContext);
        int right = this.mFieldLinearLayout.getPaddingRight();
        this.mFieldLinearLayout.setPadding(this.mFieldLinearLayout.getPaddingLeft(), this.mFieldLinearLayout.getPaddingTop(), right, bottom);
    }

    public PhoneLoginFillInField(Context context) {
        super(context);
        Init(context);
    }

    public PhoneLoginFillInField(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }

    private void Init(Context context) {
        View.inflate(context, C1283R.layout.phone_login_fill_in_field, this);
        this.mContext = context;
        initMembers();
        initLayout();
        setFont();
    }

    private void setFont() {
        if (!isInEditMode()) {
            this.mInputText.setTypeface(ResManager.getRobotoLight(this.mContext));
        }
    }

    public void setImeOptions(int imeOptions) {
        this.mInputText.setImeOptions(imeOptions);
    }

    private void initMembers() {
        this.mLabaleText = (WazeTextView) findViewById(C1283R.id.lableText);
        this.mInputText = (EditText) findViewById(C1283R.id.inputText);
        this.mFieldLinearLayout = (LinearLayout) findViewById(C1283R.id.fieldLinearLayout);
    }

    private void initLayout() {
        this.mInputText.setImeOptions(6);
    }

    private static float convertDpToPixels(float dp, Context context) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    public void setPasswordTextType() {
        this.mInputText.setInputType(129);
        this.mInputText.setTypeface(ResManager.getRobotoLight(this.mContext));
    }

    public void setInputTextOnTouchListener(OnTouchListener onTouchListener) {
        this.mInputText.setOnTouchListener(onTouchListener);
    }

    public void setTextGravity(int gravity) {
        this.mInputText.setGravity(gravity);
    }
}
