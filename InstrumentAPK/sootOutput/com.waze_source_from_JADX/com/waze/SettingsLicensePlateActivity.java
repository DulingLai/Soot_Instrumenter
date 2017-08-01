package com.waze;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.view.title.TitleBar;

public class SettingsLicensePlateActivity extends ActivityBase {
    private EditText mEtPlate;
    private NativeManager mNtMgr;
    private TextView mTvExplain;

    class C12981 implements OnClickListener {
        C12981() throws  {
        }

        public void onClick(View v) throws  {
            SettingsLicensePlateActivity.this.setAndExit();
        }
    }

    class C12992 implements OnEditorActionListener {
        C12992() throws  {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) throws  {
            SettingsLicensePlateActivity.this.setAndExit();
            return false;
        }
    }

    class C13003 implements Runnable {
        C13003() throws  {
        }

        public void run() throws  {
            EditTextUtils.openKeyboard(SettingsLicensePlateActivity.this, SettingsLicensePlateActivity.this.mEtPlate);
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNtMgr = AppService.getNativeManager();
        setContentView(C1283R.layout.settings_license_plate);
        TitleBar $r5 = (TitleBar) findViewById(C1283R.id.theTitleBar);
        $r5.init(this, DisplayStrings.DS_NAVIGATION, 375);
        $r5.setOnClickCloseListener(new C12981());
        String $r7 = NativeManager.getInstance().get2LastDigitLicensePlate();
        this.mEtPlate = (EditText) findViewById(C1283R.id.setLicensePlate);
        this.mTvExplain = (TextView) findViewById(C1283R.id.setLicensePlateExplain);
        this.mEtPlate.setHint(this.mNtMgr.getLanguageString((int) DisplayStrings.DS_SETTINGS_LICENCE_PLATE_PLACEHOLDER));
        if (!($r7 == null || $r7.isEmpty())) {
            this.mEtPlate.setText($r7);
        }
        this.mTvExplain.setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_SETTINGS_LICENCE_PLATE_DESCRIPTION));
        this.mEtPlate.setFilters(new InputFilter[]{new LengthFilter(2)});
        this.mEtPlate.setOnEditorActionListener(new C12992());
    }

    private void setAndExit() throws  {
        String $r1 = "";
        if (this.mEtPlate.getText() != null) {
            $r1 = this.mEtPlate.getText().toString();
        }
        NativeManager.getInstance().Set2LastDigitLicensePlate($r1);
        setResult(-1);
        finish();
    }

    protected void onResume() throws  {
        super.onResume();
        postDelayed(new C13003(), 500);
    }

    protected void onPause() throws  {
        EditTextUtils.closeKeyboard(this, this.mEtPlate);
        super.onPause();
    }
}
