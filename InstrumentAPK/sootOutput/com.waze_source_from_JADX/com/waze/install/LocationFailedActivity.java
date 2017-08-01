package com.waze.install;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;

public final class LocationFailedActivity extends ActivityBase {

    class C18181 implements OnClickListener {
        C18181() throws  {
        }

        public void onClick(View v) throws  {
            LocationFailedActivity.this.startActivityForResult(new Intent(LocationFailedActivity.this, SelectCountryActivity.class), 1);
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.location_failed);
        NativeManager $r2 = NativeManager.getInstance();
        ((TextView) findViewById(C1283R.id.locationFailedText)).setText($r2.getLanguageString((int) DisplayStrings.DS_LOCATION_FAILED));
        ((WazeTextView) findViewById(C1283R.id.locationFailedSelectCountryText)).setText($r2.getLanguageString((int) DisplayStrings.DS_SELECT_COUNTRY));
        findViewById(C1283R.id.locationFailedSelectCountry).setOnClickListener(new C18181());
    }

    public void onBackPressed() throws  {
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }
}
