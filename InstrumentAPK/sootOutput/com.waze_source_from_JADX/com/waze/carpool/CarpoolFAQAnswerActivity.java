package com.waze.carpool;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.facebook.internal.NativeProtocol;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class CarpoolFAQAnswerActivity extends ActivityBase {
    public static final int ACTION = 6673;

    class C14141 implements OnClickListener {
        C14141() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolFAQAnswerActivity.this.setResult(CarpoolFAQAnswerActivity.ACTION);
            CarpoolFAQAnswerActivity.this.finish();
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        String $r3 = getIntent().getStringExtra("question");
        String $r4 = getIntent().getStringExtra("answer");
        String $r5 = getIntent().getStringExtra(NativeProtocol.WEB_DIALOG_ACTION);
        setContentView(C1283R.layout.carpool_faq_answer);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, DisplayStrings.DS_CARPOOL_FAQ_TITLE);
        ((TextView) findViewById(C1283R.id.carpoolFAQQuestion)).setText(Html.fromHtml($r3));
        ((TextView) findViewById(C1283R.id.carpoolFAQAnswer)).setText(Html.fromHtml($r4));
        WazeSettingsView $r10 = (WazeSettingsView) findViewById(C1283R.id.carpoolFAQAction);
        if ($r5 != null) {
            $r10.setText($r5);
            $r10.setOnClickListener(new C14141());
            return;
        }
        $r10.setVisibility(8);
        findViewById(C1283R.id.carpoolFAQActionShadow).setVisibility(8);
    }
}
