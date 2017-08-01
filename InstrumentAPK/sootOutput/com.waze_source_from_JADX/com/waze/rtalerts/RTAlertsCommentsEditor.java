package com.waze.rtalerts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
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
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.utils.EditTextUtils;
import com.waze.view.title.TitleBar;
import java.util.List;

public class RTAlertsCommentsEditor extends ActivityBase {
    private int mAlertId;
    private EditText mEditBox;

    class C25881 implements OnEditorActionListener {
        C25881() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (event != null && event.getAction() == 1) {
                RTAlertsCommentsEditor.this.done(RTAlertsCommentsEditor.this.mEditBox.getText().toString());
            }
            return true;
        }
    }

    class C25892 implements OnClickListener {
        C25892() {
        }

        public void onClick(View v) {
            RTAlertsCommentsEditor.this.speechRecognitionClicked(v);
        }
    }

    public static class CommentsEditorTitleBar extends TitleBar {
        private RTAlertsCommentsEditor mContext;

        public CommentsEditorTitleBar(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mContext = (RTAlertsCommentsEditor) context;
        }

        public void onCloseClicked() {
            this.mContext.done(this.mContext.mEditBox.getText().toString());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.rtalerts_comments_editor);
        this.mAlertId = getIntent().getIntExtra(RTAlertsConsts.RTALERTS_COMMENTS_ALERT_ID, 0);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, NativeManager.getInstance().getLanguageString(1), (int) C1283R.string.done);
        this.mEditBox = (EditText) findViewById(C1283R.id.searchBox);
        EditTextUtils.checkTypeLockOnCreate(this, this.mEditBox);
        this.mEditBox.setOnTouchListener(EditTextUtils.getKeyboardLockOnTouchListener(this, this.mEditBox, null));
        this.mEditBox.setOnEditorActionListener(new C25881());
        this.mEditBox.requestFocus();
        findViewById(C1283R.id.speechRecognition).setOnClickListener(new C25892());
    }

    public void speechRecognitionClicked(View v) {
        Log.d(Logger.TAG, "SR pressed");
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        startActivityForResult(intent, 1234);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Logger.TAG, "Navigate onActRes requestCode=" + requestCode + " resultCode=" + resultCode + " Intent=" + data);
        if (requestCode == 1234) {
            if (resultCode == -1) {
                List<String> matches = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                if (matches.size() > 0) {
                    this.mEditBox.setText((CharSequence) matches.get(0));
                    ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.mEditBox, 2);
                }
            }
        } else if (resultCode == -1) {
            setResult(-1);
            finish();
        }
    }

    public static void start(Activity contextForResult, int alertId) {
        Intent intent = new Intent(contextForResult, RTAlertsCommentsEditor.class);
        intent.putExtra(RTAlertsConsts.RTALERTS_COMMENTS_ALERT_ID, alertId);
        contextForResult.startActivityForResult(intent, MainActivity.RTALERTS_REQUEST_CODE);
    }

    private void done(String commentText) {
        if (commentText == null || commentText.length() == 0) {
            setResult(0);
            finish();
            return;
        }
        RTAlertsNativeManager.getInstance().postComment(this.mAlertId, commentText);
        setResult(-1);
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mEditBox = null;
    }
}
