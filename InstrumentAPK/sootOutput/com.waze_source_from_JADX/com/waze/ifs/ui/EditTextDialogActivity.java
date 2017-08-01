package com.waze.ifs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.settings.SettingsTitleText;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;

public class EditTextDialogActivity extends ActivityBase {
    public static final String ARG_CALLBACK = (TAG + ".arg.callback");
    public static final String ARG_CONTEXT = (TAG + ".arg.context");
    public static final String ARG_EXPLAIN = (TAG + ".arg.explain");
    public static final String ARG_HINT_DS = (TAG + ".arg.hint_ds");
    public static final String ARG_INITIAL = (TAG + ".arg.initial");
    public static final String ARG_MIN_LINES = (TAG + ".arg.nin_lines");
    public static final String ARG_NATIVE = (TAG + ".arg.native");
    public static final String ARG_SINGLE_LINE = (TAG + ".arg.single_line");
    public static final String ARG_SPEECH = (TAG + ".arg.speech");
    public static final String ARG_SUBTITLE_DS = (TAG + ".arg.subtitle_ds");
    public static final String ARG_TITLE_DS = (TAG + ".arg.title_ds");
    public static final String ARG_TYPE = (TAG + ".arg.type");
    public static final String RET_VAL = (TAG + ".ret.val");
    private static final String TAG = EditTextDialogActivity.class.getName();
    private EditText mEditText;
    private boolean mIsNative = false;
    private long mNativeCallback;
    private long mNativeContext;

    class C17151 implements OnClickListener {
        C17151() throws  {
        }

        public void onClick(View v) throws  {
            EditTextDialogActivity.this.doDone();
            EditTextDialogActivity.this.finish();
        }
    }

    class C17162 implements OnEditorActionListener {
        C17162() throws  {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent $r2) throws  {
            if ($r2 == null) {
                return true;
            }
            if ($r2.getAction() != 1) {
                return true;
            }
            EditTextDialogActivity.this.doDone();
            EditTextDialogActivity.this.finish();
            return true;
        }
    }

    class C17173 implements OnClickListener {
        C17173() throws  {
        }

        public void onClick(View $r1) throws  {
            EditTextDialogActivity.this.speechRecognitionClicked($r1);
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        NativeManager $r2 = NativeManager.getInstance();
        requestWindowFeature(1);
        setContentView(C1283R.layout.edit_text_dialog);
        Intent $r3 = getIntent();
        int $i0 = $r3.getIntExtra(ARG_TITLE_DS, 0);
        this.mIsNative = $r3.getBooleanExtra(ARG_NATIVE, false);
        if (this.mIsNative) {
            this.mNativeCallback = $r3.getLongExtra(ARG_CALLBACK, 0);
            this.mNativeContext = $r3.getLongExtra(ARG_CONTEXT, 0);
        }
        String $r4 = $r3.getStringExtra(ARG_EXPLAIN);
        int $i2 = $r3.getIntExtra(ARG_TYPE, 1);
        boolean $z0 = $r3.getBooleanExtra(ARG_SPEECH, false);
        TitleBar $r7 = (TitleBar) findViewById(C1283R.id.theTitleBar);
        $r7.init(this, $r2.getLanguageString($i0), $r2.getLanguageString(375));
        $r7.setOnClickCloseListener(new C17151());
        if ($r3.hasExtra(ARG_SUBTITLE_DS)) {
            SettingsTitleText $r10 = (SettingsTitleText) findViewById(C1283R.id.subtitle);
            $r10.setText($r2.getLanguageString($r3.getIntExtra(ARG_SUBTITLE_DS, 0)));
            $r10.setVisibility(0);
        }
        this.mEditText = (EditText) findViewById(C1283R.id.editText);
        this.mEditText.setOnEditorActionListener(new C17162());
        this.mEditText.setInputType(131072 | $i2);
        if ($r3.hasExtra(ARG_HINT_DS)) {
            $i2 = $r3.getIntExtra(ARG_HINT_DS, 0);
            this.mEditText.setHint($r2.getLanguageString($i2));
        }
        if ($r3.hasExtra(ARG_INITIAL)) {
            String $r5 = $r3.getStringExtra(ARG_INITIAL);
            EditText $r11 = this.mEditText;
            $r11.setText($r5);
            this.mEditText.setSelectAllOnFocus(true);
        }
        if ($r3.hasExtra(ARG_SINGLE_LINE)) {
            boolean $z1 = $r3.getBooleanExtra(ARG_SINGLE_LINE, false);
            $r11 = this.mEditText;
            EditText $r112 = $r11;
            $r11.setSingleLine($z1);
        }
        if ($r3.hasExtra(ARG_MIN_LINES)) {
            $i2 = $r3.getIntExtra(ARG_MIN_LINES, 0);
            $r11 = this.mEditText;
            $r11.setMinLines($i2);
        }
        if ($z0) {
            findViewById(C1283R.id.speechRecognition).setOnClickListener(new C17173());
        } else {
            findViewById(C1283R.id.speechRecognition).setVisibility(8);
        }
        if ($i0 == DisplayStrings.DS_MAP_CHAT_EDIT_TITLE) {
            $r4 = $r2.getLanguageString(154);
        } else if ($i0 == DisplayStrings.DS_MESSAGE) {
            $r4 = $r2.getLanguageString((int) DisplayStrings.DS_MESSAGES_ARE_PRIVATE);
        }
        if ($r4 != null) {
            ((TextView) findViewById(C1283R.id.editTextDialogText)).setText($r4);
        }
    }

    protected void onResume() throws  {
        super.onResume();
        this.mEditText.requestFocus();
    }

    public void speechRecognitionClicked(View v) throws  {
        Intent $r2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        $r2.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        startActivityForResult($r2, 1234);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        Log.d(Logger.TAG, "Navigate onActRes requestCode=" + $i0 + " resultCode=" + $i1 + " Intent=" + $r1);
        if ($i0 == 1234) {
            if ($i1 == -1) {
                ArrayList $r4 = $r1.getStringArrayListExtra("android.speech.extra.RESULTS");
                if ($r4.size() > 0) {
                    this.mEditText.setText((CharSequence) $r4.get(0));
                    ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.mEditText, 2);
                }
            }
        } else if ($i1 == -1) {
            setResult(-1);
            finish();
        }
    }

    private void doDone() throws  {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
        if (this.mIsNative) {
            NativeManager.getInstance().editTextDialogCallback(this.mEditText.getText().toString(), this.mNativeCallback, this.mNativeContext);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(RET_VAL, this.mEditText.getText().toString());
        setResult(-1, intent);
    }
}
