package com.waze.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.messages.QuestionData;

public class QuestionDialog extends Dialog {
    private final QuestionData mData;
    private final ResponseHandler mHandler;

    class C31981 implements OnClickListener {
        C31981() {
        }

        public void onClick(View v) {
            QuestionDialog.this.mHandler.onResponse(QuestionDialog.this.mData.IdText1, QuestionDialog.this.mData.SubText1);
        }
    }

    class C31992 implements OnClickListener {
        C31992() {
        }

        public void onClick(View v) {
            QuestionDialog.this.mHandler.onResponse(QuestionDialog.this.mData.IdText2, QuestionDialog.this.mData.SubText2);
        }
    }

    public interface ResponseHandler {
        void onResponse(String str, String str2);
    }

    public QuestionDialog(ActivityBase context, QuestionData question, ResponseHandler handler) {
        super(context, C1283R.style.Dialog);
        this.mData = question;
        this.mHandler = handler;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        setContentView(C1283R.layout.question_dialog);
        ((TextView) findViewById(C1283R.id.questionDialogQuestion)).setText(this.mData.Text);
        TextView tvAnswer1 = (TextView) findViewById(C1283R.id.questionDialogAnswer1);
        tvAnswer1.setText(this.mData.SubText1);
        tvAnswer1.setOnClickListener(new C31981());
        TextView tvAnswer2 = (TextView) findViewById(C1283R.id.questionDialogAnswer2);
        tvAnswer2.setText(this.mData.SubText2);
        tvAnswer2.setOnClickListener(new C31992());
    }
}
