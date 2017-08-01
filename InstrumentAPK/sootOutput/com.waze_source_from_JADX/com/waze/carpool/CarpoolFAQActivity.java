package com.waze.carpool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.internal.NativeProtocol;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingsCarpoolPaymentsActivity;
import com.waze.settings.SettingsCarpoolWorkActivity;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;

public class CarpoolFAQActivity extends ActivityBase {
    private NativeManager nm;

    private enum Question {
        Q1(DisplayStrings.DS_CARPOOL_FAQ_OFFER_RIDE_QUESTION, DisplayStrings.DS_CARPOOL_FAQ_OFFER_RIDE_ANSWER, DisplayStrings.DS_CARPOOL_FAQ_OFFER_A_RIDE),
        Q2(DisplayStrings.DS_CARPOOL_FAQ_RIDE_NEEDED_QUESTION, DisplayStrings.DS_CARPOOL_FAQ_RIDE_NEEDED_ANSWER, DisplayStrings.DS_CARPOOL_FAQ_CHECK_RIDE_REQUESTS),
        Q3(DisplayStrings.DS_CARPOOL_FAQ_KEEP_WAZE_OPEN_QUESTION, DisplayStrings.DS_CARPOOL_FAQ_KEEP_WAZE_OPEN_ANSWER, DisplayStrings.DS_NULL),
        Q4(DisplayStrings.DS_CARPOOL_FAQ_GET_PAID_QUESTION, DisplayStrings.DS_CARPOOL_FAQ_GET_PAID_ANSWER, DisplayStrings.DS_CARPOOL_FAQ_ADD_BANK_DETAILS),
        Q5(DisplayStrings.DS_CARPOOL_FAQ_GET_MORE_RIDES_QUESTION, DisplayStrings.DS_CARPOOL_FAQ_GET_MORE_RIDES_ANSWER, DisplayStrings.DS_CARPOOL_FAQ_ADD_COMPANY_NAME),
        Q6(DisplayStrings.DS_CARPOOL_FAQ_BECOME_RIDER_QUESTION, DisplayStrings.DS_CARPOOL_FAQ_BECOME_RIDER_ANSWER, DisplayStrings.DS_ALL_RIDES_GET_APP);
        
        public final String mAction;
        public final String mAnswer;
        public final String mQuestion;

        private Question(@Signature({"(III)V"}) int $i1, @Signature({"(III)V"}) int $i2, @Signature({"(III)V"}) int $i3) throws  {
            this.mQuestion = NativeManager.getInstance().getLanguageString($i1);
            this.mAnswer = NativeManager.getInstance().getLanguageString($i2);
            this.mAction = $i3 == DisplayStrings.DS_NULL ? null : NativeManager.getInstance().getLanguageString($i3);
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.nm = NativeManager.getInstance();
        setContentView(C1283R.layout.carpool_faq);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, DisplayStrings.DS_CARPOOL_FAQ_TITLE);
        setupItem(C1283R.id.faq_question1, Question.Q1, 1);
        setupItem(C1283R.id.faq_question2, Question.Q2, 2);
        setupItem(C1283R.id.faq_question3, Question.Q3, 3);
        setupItem(C1283R.id.faq_question4, Question.Q4, 4);
        setupItem(C1283R.id.faq_question5, Question.Q5, 5);
        setupItem(C1283R.id.faq_question6, Question.Q6, 6);
    }

    private void setupItem(int $i0, final Question $r1, final int $i1) throws  {
        WazeSettingsView $r3 = (WazeSettingsView) findViewById($i0);
        if ($r3 != null) {
            $r3.setText($r1.mQuestion);
            $r3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    Intent $r2 = new Intent(CarpoolFAQActivity.this, CarpoolFAQAnswerActivity.class);
                    $r2.putExtra("question", $r1.mQuestion);
                    $r2.putExtra("answer", $r1.mAnswer);
                    $r2.putExtra(NativeProtocol.WEB_DIALOG_ACTION, $r1.mAction);
                    CarpoolFAQActivity.this.startActivityForResult($r2, $i1);
                }
            });
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        if ($i1 == CarpoolFAQAnswerActivity.ACTION) {
            Intent $r2 = null;
            switch ($i0) {
                case 1:
                    setResult(3);
                    finish();
                    MainActivity $r3 = AppService.getMainActivity();
                    if ($r3 != null) {
                        $r3.openCarpoolRightSideMenu();
                        break;
                    }
                    break;
                case 2:
                    $r2 = new Intent(this, CarpoolRidesActivity.class);
                    break;
                case 3:
                    break;
                case 4:
                    $r2 = new Intent(this, SettingsCarpoolPaymentsActivity.class);
                    break;
                case 5:
                    $r2 = new Intent(this, SettingsCarpoolWorkActivity.class);
                    break;
                case 6:
                    $r2 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.ridewith"));
                    break;
                default:
                    break;
            }
            if ($r2 != null) {
                startActivityForResult($r2, 0);
            }
        }
        super.onActivityResult($i0, $i1, $r1);
    }
}
