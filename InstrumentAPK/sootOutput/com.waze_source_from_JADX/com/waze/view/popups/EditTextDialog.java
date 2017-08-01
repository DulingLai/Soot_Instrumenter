package com.waze.view.popups;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.utils.EditTextUtils;

public class EditTextDialog extends Dialog {
    private EditText mEdit;
    private String mHint;
    private int mMaxLength = 0;
    private final NativeManager mNm;
    private boolean mOkeyed;
    private OnClickListener mOnCancel;
    private OnClickListener mOnOk;
    private final ActivityBase mParent;
    private String mText;
    private String mTitle;

    class C31251 implements OnDismissListener {
        C31251() {
        }

        public void onDismiss(DialogInterface dialog) {
            if (!(EditTextDialog.this.mOkeyed || EditTextDialog.this.mOnCancel == null)) {
                EditTextDialog.this.mOnCancel.onClick(null);
            }
            EditTextUtils.closeKeyboard(EditTextDialog.this.mParent, EditTextDialog.this.mEdit);
        }
    }

    class C31262 implements OnClickListener {
        C31262() {
        }

        public void onClick(View v) {
            EditTextDialog.this.mOkeyed = true;
            if (EditTextDialog.this.mOnOk != null) {
                EditTextDialog.this.mOnOk.onClick(v);
            }
        }
    }

    class C31273 implements OnClickListener {
        C31273() {
        }

        public void onClick(View v) {
            if (EditTextDialog.this.mOnCancel != null) {
                EditTextDialog.this.mOnCancel.onClick(v);
            }
        }
    }

    class C31284 implements OnShowListener {
        C31284() {
        }

        public void onShow(DialogInterface dialog) {
            EditTextUtils.openKeyboard(EditTextDialog.this.mParent, EditTextDialog.this.mEdit);
        }
    }

    public EditTextDialog(ActivityBase parent) {
        super(parent, C1283R.style.Dialog);
        this.mParent = parent;
        this.mNm = NativeManager.getInstance();
        this.mOkeyed = false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(C1283R.layout.editbox_popup);
        this.mEdit = (EditText) findViewById(C1283R.id.editTextBox);
        ((TextView) findViewById(C1283R.id.confirmSendText)).setText(this.mNm.getLanguageString(375));
        ((TextView) findViewById(C1283R.id.confirmCloseText)).setText(this.mNm.getLanguageString(344));
        findViewById(C1283R.id.confirmSendTimer).setVisibility(8);
        findViewById(C1283R.id.confirmCloseTimer).setVisibility(8);
        super.setOnDismissListener(new C31251());
        findViewById(C1283R.id.confirmSend).setOnClickListener(new C31262());
        findViewById(C1283R.id.confirmClose).setOnClickListener(new C31273());
        if (this.mMaxLength > 0) {
            this.mEdit.setFilters(new InputFilter[]{new LengthFilter(this.mMaxLength)});
        }
        this.mEdit.requestFocus();
        setOnShowListener(new C31284());
        if (this.mHint != null) {
            this.mEdit.setHint(this.mHint);
        }
        if (this.mText != null) {
            this.mEdit.setText(this.mText);
        }
        if (this.mTitle != null) {
            ((TextView) findViewById(C1283R.id.confirmTitle)).setText(this.mTitle);
        }
    }

    public void setMaxLength(int maxLength) {
        this.mMaxLength = maxLength;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setHint(int ds) {
        this.mHint = this.mNm.getLanguageString(ds);
    }

    public void setTitle(int ds) {
        this.mTitle = this.mNm.getLanguageString(ds);
    }

    public String getText() {
        return this.mEdit.getText().toString();
    }

    public void setOnOk(OnClickListener onClickListener) {
        this.mOnOk = onClickListener;
    }

    public void setOnCancel(OnClickListener onClickListener) {
        this.mOnCancel = onClickListener;
    }
}
