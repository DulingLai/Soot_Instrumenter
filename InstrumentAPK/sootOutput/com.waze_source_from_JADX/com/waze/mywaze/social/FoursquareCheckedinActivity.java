package com.waze.mywaze.social;

import android.os.Bundle;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class FoursquareCheckedinActivity extends ActivityBase {
    private NativeManager nativeManager = AppService.getNativeManager();

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.foursquare_checkedin);
        ((TitleBar) findViewById(C1283R.id.title)).init(this, 162);
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            ((TextView) findViewById(C1283R.id.label)).setText($r1.getString("com.waze.mywaze.foursquare_label"));
            ((TextView) findViewById(C1283R.id.address)).setText($r1.getString("com.waze.mywaze.foursquare_address"));
            ((TextView) findViewById(C1283R.id.points)).setText("" + this.nativeManager.getLanguageString((int) DisplayStrings.DS_POINTSC) + " " + $r1.getString("com.waze.mywaze.foursquare_points"));
            ((TextView) findViewById(C1283R.id.details)).setText(this.nativeManager.getLanguageString(408));
        }
    }
}
