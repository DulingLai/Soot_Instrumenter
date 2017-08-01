package com.waze.ifs.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

public class EditTextDialogFragment extends Fragment {
    private static final String TAG = EditTextDialogFragment.class.getName();
    private EditText mEditText;
    private String mExplanation;
    private int mHintDs = DisplayStrings.DS_NULL;
    private String mInitial;
    private int mInputType;
    private int mMaxLength = 0;
    private int mMinLines = 0;
    private boolean mSingleLine;
    private int mSubtitleDs = DisplayStrings.DS_NULL;
    private int mTitleDs = DisplayStrings.DS_NULL;
    private boolean mUseSpeech;
    private View f82r;

    class C17181 implements OnClickListener {
        C17181() throws  {
        }

        public void onClick(View v) throws  {
            EditTextDialogFragment.this.doDone();
        }
    }

    class C17192 implements OnEditorActionListener {
        C17192() throws  {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent $r2) throws  {
            if ($r2 == null) {
                return true;
            }
            if ($r2.getAction() != 1) {
                return true;
            }
            EditTextDialogFragment.this.doDone();
            return true;
        }
    }

    class C17203 implements TextWatcher {
        C17203() throws  {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) throws  {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void afterTextChanged(Editable $r1) throws  {
            if ($r1.length() > EditTextDialogFragment.this.mMaxLength) {
                $r1.delete(300, $r1.length());
            }
        }
    }

    class C17214 implements OnClickListener {
        C17214() throws  {
        }

        public void onClick(View $r1) throws  {
            EditTextDialogFragment.this.speechRecognitionClicked($r1);
        }
    }

    public interface IEditText {
        void textEdited(String str) throws ;
    }

    public void setTitle(int $i0) throws  {
        this.mTitleDs = $i0;
    }

    public void setSubtitle(int $i0) throws  {
        this.mSubtitleDs = $i0;
    }

    public void setHint(int $i0) throws  {
        this.mHintDs = $i0;
    }

    public void setInputType(int $i0) throws  {
        this.mInputType = $i0;
    }

    public void setExplanation(String $r1) throws  {
        this.mExplanation = $r1;
    }

    public void setInitial(String $r1) throws  {
        this.mInitial = $r1;
    }

    public void setSpeech(boolean $z0) throws  {
        this.mUseSpeech = $z0;
    }

    public void setSingleLine(boolean $z0) throws  {
        this.mSingleLine = $z0;
    }

    public void setMinLines(int $i0) throws  {
        this.mMinLines = $i0;
    }

    public void setMaxLength(int $i0) throws  {
        this.mMaxLength = $i0;
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        if (DisplayStrings.isValid(this.mTitleDs)) {
            $r1.putInt(TAG + ".mTitleDs", this.mTitleDs);
        }
        if (DisplayStrings.isValid(this.mSubtitleDs)) {
            $r1.putInt(TAG + ".mSubtitleDs", this.mSubtitleDs);
        }
        if (DisplayStrings.isValid(this.mHintDs)) {
            $r1.putInt(TAG + ".mHintDs", this.mHintDs);
        }
        $r1.putInt(TAG + ".mInputType", this.mInputType);
        $r1.putInt(TAG + ".mMaxLength", this.mMaxLength);
        $r1.putInt(TAG + ".mMinLines", this.mMinLines);
        $r1.putBoolean(TAG + ".mUseSpeech", this.mUseSpeech);
        $r1.putBoolean(TAG + ".mSingleLine", this.mSingleLine);
        $r1.putString(TAG + ".mExplanation", this.mExplanation);
        $r1.putString(TAG + ".mInitial", this.mInitial);
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle $r3) throws  {
        super.onCreateView($r1, $r2, $r3);
        NativeManager $r4 = NativeManager.getInstance();
        if ($r3 != null) {
            if ($r3.containsKey(TAG + ".mTitleDs")) {
                this.mTitleDs = $r3.getInt(TAG + ".mTitleDs");
            }
            if ($r3.containsKey(TAG + ".mSubtitleDs")) {
                this.mSubtitleDs = $r3.getInt(TAG + ".mSubtitleDs");
            }
            if ($r3.containsKey(TAG + ".mHintDs")) {
                this.mHintDs = $r3.getInt(TAG + ".mHintDs");
            }
            this.mInputType = $r3.getInt(TAG + ".mInputType");
            this.mMaxLength = $r3.getInt(TAG + ".mMaxLength");
            this.mMinLines = $r3.getInt(TAG + ".mMinLines");
            this.mUseSpeech = $r3.getBoolean(TAG + ".mUseSpeech", this.mUseSpeech);
            this.mSingleLine = $r3.getBoolean(TAG + ".mSingleLine", this.mSingleLine);
            this.mExplanation = $r3.getString(TAG + ".mExplanation");
            this.mInitial = $r3.getString(TAG + ".mInitial");
        }
        this.f82r = $r1.inflate(C1283R.layout.edit_text_dialog, $r2, false);
        TitleBar $r8 = (TitleBar) this.f82r.findViewById(C1283R.id.theTitleBar);
        $r8.init(getActivity(), $r4.getLanguageString(this.mTitleDs), $r4.getLanguageString(375));
        $r8.setOnClickCloseListener(new C17181());
        if (DisplayStrings.isValid(this.mSubtitleDs)) {
            SettingsTitleText $r12 = (SettingsTitleText) this.f82r.findViewById(C1283R.id.subtitle);
            $r12.setText($r4.getLanguageString(this.mSubtitleDs));
            $r12.setVisibility(0);
        }
        this.mEditText = (EditText) this.f82r.findViewById(C1283R.id.editText);
        this.mEditText.setOnEditorActionListener(new C17192());
        this.mEditText.setInputType(131072 | this.mInputType);
        if (DisplayStrings.isValid(this.mHintDs)) {
            this.mEditText.setHint($r4.getLanguageString(this.mHintDs));
        }
        if (this.mInitial != null) {
            this.mEditText.setText(this.mInitial);
            this.mEditText.setSelectAllOnFocus(true);
        }
        this.mEditText.setSingleLine(this.mSingleLine);
        this.mEditText.setMinLines(this.mMinLines);
        if (this.mMaxLength > 0) {
            this.mEditText.addTextChangedListener(new C17203());
        }
        if (this.mUseSpeech) {
            this.f82r.findViewById(C1283R.id.speechRecognition).setOnClickListener(new C17214());
        } else {
            this.f82r.findViewById(C1283R.id.speechRecognition).setVisibility(8);
        }
        if (this.mTitleDs == DisplayStrings.DS_MAP_CHAT_EDIT_TITLE) {
            this.mExplanation = $r4.getLanguageString(154);
        } else if (this.mTitleDs == DisplayStrings.DS_MESSAGE) {
            this.mExplanation = $r4.getLanguageString((int) DisplayStrings.DS_MESSAGES_ARE_PRIVATE);
        }
        if (this.mExplanation != null) {
            ((TextView) this.f82r.findViewById(C1283R.id.editTextDialogText)).setText(this.mExplanation);
        }
        return this.f82r;
    }

    public void onResume() throws  {
        super.onResume();
        this.mEditText.requestFocus();
        ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(this.mEditText, 2);
    }

    public void speechRecognitionClicked(View v) throws  {
        Intent $r2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        $r2.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        startActivityForResult($r2, 1234);
    }

    public void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        Log.d(Logger.TAG, "Navigate onActRes requestCode=" + $i0 + " resultCode=" + $i1 + " Intent=" + $r1);
        if ($i0 == 1234 && $i1 == -1) {
            ArrayList $r4 = $r1.getStringArrayListExtra("android.speech.extra.RESULTS");
            if ($r4.size() > 0) {
                this.mEditText.setText((CharSequence) $r4.get(0));
            }
        }
    }

    private void doDone() throws  {
        ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
        ((IEditText) getActivity()).textEdited(this.mEditText.getText().toString());
    }
}
