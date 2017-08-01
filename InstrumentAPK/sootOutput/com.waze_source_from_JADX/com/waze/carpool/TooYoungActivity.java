package com.waze.carpool;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public final class TooYoungActivity extends ActivityBase {
    private NativeManager mNatMgr;

    class C16921 implements OnClickListener {
        C16921() throws  {
        }

        public void onClick(View v) throws  {
            TooYoungActivity.this.setResult(-1);
            TooYoungActivity.this.finish();
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setRequestedOrientation(1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.too_young);
        this.mNatMgr = NativeManager.getInstance();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, this.mNatMgr.getLanguageString((int) DisplayStrings.DS_TOO_YOUNG_TITLE));
        ((WazeTextView) findViewById(C1283R.id.tooYoungSubtitle)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_TOO_YOUNG_SUBTITLE));
        ((WazeTextView) findViewById(C1283R.id.tooYoungText)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_TOO_YOUNG_TEXT));
        WazeTextView $r6 = (WazeTextView) findViewById(C1283R.id.tooYoungButton);
        $r6.setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_TOO_YOUNG_BUTTON));
        $r6.setOnClickListener(new C16921());
    }
}
