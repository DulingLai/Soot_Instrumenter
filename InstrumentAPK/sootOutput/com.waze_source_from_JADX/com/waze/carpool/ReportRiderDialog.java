package com.waze.carpool;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.WazeCheckBoxView;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import java.util.Locale;

public class ReportRiderDialog extends Dialog {
    private boolean mButtonClicked;
    private WazeCheckBoxView mCheckBox;
    private TextView mCounter;
    private EditText mEdit;
    private boolean mFromBlock;
    private int mMaxLength = 0;
    private final NativeManager mNm;
    private OnClickListener mOnCancel;
    private OnClickListener mOnOk;
    private final ActivityBase mParent;
    private CarpoolUserData mRider;
    private View mSend;
    private String mType;

    class C16831 implements OnDismissListener {
        C16831() throws  {
        }

        public void onDismiss(DialogInterface dialog) throws  {
            if (!ReportRiderDialog.this.mButtonClicked) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_DIALOG_CLICKED, "ACTION", "CANCEL");
                if (ReportRiderDialog.this.mOnCancel != null) {
                    ReportRiderDialog.this.mOnCancel.onClick(null);
                }
            }
            EditTextUtils.closeKeyboard(ReportRiderDialog.this.mParent, ReportRiderDialog.this.mEdit);
        }
    }

    class C16842 implements OnClickListener {
        C16842() throws  {
        }

        public void onClick(View $r1) throws  {
            if (!ReportRiderDialog.this.mFromBlock || ReportRiderDialog.this.mEdit.getText().length() != 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_DIALOG_CLICKED, "ACTION", "SEND");
                ReportRiderDialog.this.mButtonClicked = true;
                if (ReportRiderDialog.this.mOnOk != null) {
                    ReportRiderDialog.this.mOnOk.onClick($r1);
                }
                ReportRiderDialog.this.dismiss();
            }
        }
    }

    class C16853 implements OnClickListener {
        C16853() throws  {
        }

        public void onClick(View $r1) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_DIALOG_CLICKED, "ACTION", "CANCEL");
            ReportRiderDialog.this.mButtonClicked = true;
            if (ReportRiderDialog.this.mOnCancel != null) {
                ReportRiderDialog.this.mOnCancel.onClick($r1);
            }
            ReportRiderDialog.this.dismiss();
        }
    }

    class C16864 implements OnShowListener {
        C16864() throws  {
        }

        public void onShow(DialogInterface dialog) throws  {
            boolean $z0 = true;
            if (ReportRiderDialog.this.getContext().getResources().getConfiguration().orientation != 1) {
                $z0 = false;
            }
            if ($z0) {
                EditTextUtils.openKeyboard(ReportRiderDialog.this.mParent, ReportRiderDialog.this.mEdit);
            }
        }
    }

    class C16875 implements TextWatcher {
        C16875() throws  {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) throws  {
        }

        public void afterTextChanged(Editable $r1) throws  {
            int $i0 = $r1.length();
            if (ReportRiderDialog.this.mFromBlock) {
                boolean $z0;
                ReportRiderDialog.this.mSend.setAlpha($i0 > 0 ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                View $r3 = ReportRiderDialog.this.mSend;
                if ($i0 > 0) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                $r3.setEnabled($z0);
            }
            ReportRiderDialog.this.mCounter.setText(String.format(ReportRiderDialog.this.mNm.getLocale(), "%d/%d", new Object[]{Integer.valueOf($i0), Integer.valueOf(ReportRiderDialog.this.mMaxLength)}));
        }
    }

    class C16886 implements OnClickListener {
        C16886() throws  {
        }

        public void onClick(View v) throws  {
            ReportRiderDialog.this.mCheckBox.toggle();
        }
    }

    public ReportRiderDialog(ActivityBase $r1, CarpoolUserData $r2) throws  {
        super($r1, C1283R.style.Dialog);
        this.mParent = $r1;
        this.mNm = NativeManager.getInstance();
        this.mButtonClicked = false;
        this.mRider = $r2;
    }

    protected void onCreate(Bundle savedInstanceState) throws  {
        setContentView(C1283R.layout.report_rider_dialog);
        Window $r3 = getWindow();
        $r3.setLayout(-1, -1);
        $r3.setGravity(17);
        $r3.setSoftInputMode(32);
        this.mEdit = (EditText) findViewById(C1283R.id.repRiderTextBox);
        this.mCounter = (TextView) findViewById(C1283R.id.repRiderCharCount);
        this.mCheckBox = (WazeCheckBoxView) findViewById(C1283R.id.repRiderCheckbox);
        ((TextView) findViewById(C1283R.id.confirmSendText)).setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_REPORT_USER_DIALOG_SEND));
        ((TextView) findViewById(C1283R.id.confirmCloseText)).setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_REPORT_USER_DIALOG_CANCEL));
        findViewById(C1283R.id.confirmSendTimer).setVisibility(8);
        findViewById(C1283R.id.confirmCloseTimer).setVisibility(8);
        super.setOnDismissListener(new C16831());
        this.mSend = findViewById(C1283R.id.confirmSend);
        this.mSend.setOnClickListener(new C16842());
        findViewById(C1283R.id.confirmClose).setOnClickListener(new C16853());
        if (this.mMaxLength > 0) {
            this.mEdit.setFilters(new InputFilter[]{new LengthFilter(this.mMaxLength)});
        }
        this.mEdit.requestFocus();
        setOnShowListener(new C16864());
        TextView $r6 = (TextView) findViewById(C1283R.id.repRiderTitle);
        Object[] $r15 = new Object[1];
        CarpoolUserData $r16 = this.mRider;
        $r15[0] = $r16.getFirstName();
        $r6.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_REPORT_USER_DIALOG_TITLE_PS, $r15));
        if (this.mType != null) {
            ((TextView) findViewById(C1283R.id.repRiderSubTitle)).setText(this.mType);
        } else {
            findViewById(C1283R.id.repRiderSubTitle).setVisibility(8);
        }
        this.mEdit.setHint(DisplayStrings.displayString(this.mFromBlock ? (short) 3373 : (short) 3372));
        $r6 = this.mCounter;
        NativeManager $r17 = this.mNm;
        Locale $r18 = $r17.getLocale();
        $r15 = new Object[2];
        $r15[0] = Integer.valueOf(0);
        int $i0 = this.mMaxLength;
        $r15[1] = Integer.valueOf($i0);
        $r6.setText(String.format($r18, "%d/%d", $r15));
        this.mEdit.addTextChangedListener(new C16875());
        $r17 = this.mNm;
        if ($r17.getLanguageRtl()) {
            ((TextView) findViewById(C1283R.id.repRiderCheckboxTextRtl)).setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_REPORT_USER_DIALOG_CHECKBOX_TEXT));
            findViewById(C1283R.id.repRiderCheckboxTextRtl).setVisibility(0);
            findViewById(C1283R.id.repRiderCheckboxText).setVisibility(8);
            ((LayoutParams) this.mCounter.getLayoutParams()).gravity = 83;
        } else {
            ((TextView) findViewById(C1283R.id.repRiderCheckboxText)).setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_REPORT_USER_DIALOG_CHECKBOX_TEXT));
            findViewById(C1283R.id.repRiderCheckboxText).setVisibility(0);
            findViewById(C1283R.id.repRiderCheckboxTextRtl).setVisibility(8);
            ((LayoutParams) this.mCounter.getLayoutParams()).gravity = 85;
        }
        if (this.mFromBlock) {
            this.mCheckBox.setValue(true);
            this.mSend.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            this.mSend.setEnabled(false);
            return;
        }
        this.mCheckBox.setValue(false);
        C16886 c16886 = new C16886();
        this.mCheckBox.setOnClickListener(c16886);
        findViewById(C1283R.id.repRiderCheckboxText).setOnClickListener(c16886);
        findViewById(C1283R.id.repRiderCheckboxTextRtl).setOnClickListener(c16886);
    }

    public void setMaxLength(int $i0) throws  {
        this.mMaxLength = $i0;
    }

    public void setType(String $r1) throws  {
        this.mType = $r1;
    }

    public void setBlock(boolean $z0) throws  {
        this.mFromBlock = $z0;
    }

    public String getText() throws  {
        return this.mEdit.getText().toString();
    }

    public boolean getIsChecked() throws  {
        return this.mCheckBox.isChecked();
    }

    public void setOnOk(OnClickListener $r1) throws  {
        this.mOnOk = $r1;
    }

    public void setOnCancel(OnClickListener $r1) throws  {
        this.mOnCancel = $r1;
    }
}
