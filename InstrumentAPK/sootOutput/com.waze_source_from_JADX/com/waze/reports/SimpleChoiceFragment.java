package com.waze.reports;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.settings.SettingsTitleText;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.io.Serializable;

public class SimpleChoiceFragment extends Fragment {
    private static final String TAG = SimpleChoiceFragment.class.getName();
    private boolean mAllowComment;
    private SimpleChoice mChoice;
    private SimpleChoice[] mChoices;
    private EditText mEditText;
    private int mHintDs = DisplayStrings.DS_NULL;
    private LayoutInflater mInflater;
    private int mInputType;
    private View mSelectedView;
    private boolean mSingleLine;
    private int mSubtitleDs = DisplayStrings.DS_NULL;
    private int mTitleDs = DisplayStrings.DS_NULL;
    private View f96r;

    public interface ISimplyChoose {
        void choiceMade(SimpleChoice simpleChoice, String str);
    }

    class C25331 implements OnClickListener {
        C25331() {
        }

        public void onClick(View v) {
            SimpleChoiceFragment.this.doDone();
        }
    }

    class C25342 implements OnEditorActionListener {
        C25342() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (event != null && event.getAction() == 1) {
                SimpleChoiceFragment.this.doDone();
            }
            return true;
        }
    }

    public static class SimpleChoice implements Serializable {
        private static final long serialVersionUID = 1;
        public String display;
        public int value;

        public SimpleChoice(String display, int value) {
            this.display = display;
            this.value = value;
        }
    }

    public void setTitle(int ds) {
        this.mTitleDs = ds;
    }

    public void setSubtitle(int ds) {
        this.mSubtitleDs = ds;
    }

    public void setHint(int ds) {
        this.mHintDs = ds;
    }

    public void setChoices(SimpleChoice[] choices) {
        this.mChoices = choices;
    }

    public void setInputType(int type) {
        this.mInputType = type;
    }

    public void setAllowComment(boolean on) {
        this.mAllowComment = on;
    }

    public void setSingleLine(boolean on) {
        this.mSingleLine = on;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG + ".mTitleDs", this.mTitleDs);
        outState.putInt(TAG + ".mSubtitleDs", this.mSubtitleDs);
        outState.putInt(TAG + ".mHintDs", this.mHintDs);
        outState.putSerializable(TAG + ".mChoices", this.mChoices);
        outState.putInt(TAG + ".mInputType", this.mInputType);
        outState.putBoolean(TAG + ".mAllowComment", this.mAllowComment);
        outState.putBoolean(TAG + ".mSingleLine", this.mSingleLine);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mInflater = inflater;
        NativeManager nm = NativeManager.getInstance();
        if (savedInstanceState != null) {
            this.mTitleDs = savedInstanceState.getInt(TAG + ".mTitleDs");
            this.mSubtitleDs = savedInstanceState.getInt(TAG + ".mSubtitleDs");
            this.mHintDs = savedInstanceState.getInt(TAG + ".mHintDs");
            this.mChoices = (SimpleChoice[]) savedInstanceState.getSerializable(TAG + ".mChoices");
            this.mInputType = savedInstanceState.getInt(TAG + ".mInputType");
            this.mAllowComment = savedInstanceState.getBoolean(TAG + ".mAllowComment", this.mAllowComment);
            this.mSingleLine = savedInstanceState.getBoolean(TAG + ".mSingleLine", this.mSingleLine);
        }
        this.f96r = inflater.inflate(C1283R.layout.simple_choice_dialog, container, false);
        TitleBar titleBar = (TitleBar) this.f96r.findViewById(C1283R.id.theTitleBar);
        titleBar.init(getActivity(), nm.getLanguageString(this.mTitleDs), nm.getLanguageString(375));
        titleBar.setOnClickCloseListener(new C25331());
        if (DisplayStrings.isValid(this.mSubtitleDs)) {
            SettingsTitleText subView = (SettingsTitleText) this.f96r.findViewById(C1283R.id.subtitle);
            subView.setText(nm.getLanguageString(this.mSubtitleDs));
            subView.setVisibility(0);
        }
        LinearLayout choicesContainer = (LinearLayout) this.f96r.findViewById(C1283R.id.choicesContainer);
        if (this.mChoices == null || this.mChoices.length <= 0) {
            choicesContainer.removeAllViews();
            choicesContainer.setVisibility(8);
        } else {
            choicesContainer.removeAllViews();
            int i = 0;
            while (i < this.mChoices.length) {
                boolean z;
                SimpleChoice simpleChoice = this.mChoices[i];
                if (i == 0) {
                    z = true;
                } else {
                    z = false;
                }
                addChoice(choicesContainer, simpleChoice, z, i == this.mChoices.length);
                i++;
            }
        }
        if (this.mAllowComment) {
            this.mEditText = (EditText) this.f96r.findViewById(C1283R.id.editText);
            this.mEditText.setOnEditorActionListener(new C25342());
            this.mEditText.setInputType(this.mInputType);
        } else {
            this.f96r.findViewById(C1283R.id.editFrame).setVisibility(8);
        }
        if (DisplayStrings.isValid(this.mHintDs)) {
            this.mEditText.setHint(nm.getLanguageString(this.mHintDs));
        }
        this.mEditText.setSingleLine(this.mSingleLine);
        return this.f96r;
    }

    public void onResume() {
        super.onResume();
        this.mEditText.requestFocus();
    }

    @SuppressLint({"InflateParams"})
    private View addChoice(ViewGroup parent, final SimpleChoice choice, boolean isTop, boolean isBottom) {
        final View view = this.mInflater.inflate(C1283R.layout.settings_pick_one, null);
        ((WazeTextView) view.findViewById(C1283R.id.settingsSelectionKey)).setText(choice.display);
        View container = view.findViewById(C1283R.id.settingsSelectionMainLayout);
        if (isBottom) {
            container.setBackgroundResource(C1283R.drawable.item_selector_bottom);
        } else if (isTop) {
            container.setBackgroundResource(C1283R.drawable.item_selector_top);
        } else {
            container.setBackgroundResource(C1283R.drawable.item_selector_middle);
        }
        container.setPadding(0, 0, 0, 0);
        parent.addView(view);
        LayoutParams p = container.getLayoutParams();
        p.height = getResources().getDimensionPixelSize(C1283R.dimen.settingsItemHeight);
        container.setLayoutParams(p);
        view.findViewById(C1283R.id.settingsSelectionChecked).setVisibility(8);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (SimpleChoiceFragment.this.mSelectedView != v) {
                    view.findViewById(C1283R.id.settingsSelectionChecked).setVisibility(0);
                    if (SimpleChoiceFragment.this.mSelectedView != null) {
                        SimpleChoiceFragment.this.mSelectedView.findViewById(C1283R.id.settingsSelectionChecked).setVisibility(8);
                    }
                    SimpleChoiceFragment.this.mSelectedView = view;
                    SimpleChoiceFragment.this.mChoice = choice;
                }
            }
        });
        return view;
    }

    private void doDone() {
        if (this.mChoice != null) {
            ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
            ((ISimplyChoose) getActivity()).choiceMade(this.mChoice, this.mEditText.getText().toString());
        }
    }
}
