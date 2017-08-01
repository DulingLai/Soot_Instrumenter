package com.waze.carpool;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public final class RideUnavailableActivity extends ActivityBase {
    public static final String RIDE_ID = "rideId";
    private NativeManager mNatMgr;

    class C16911 implements OnClickListener {
        C16911() throws  {
        }

        public void onClick(View v) throws  {
            RideUnavailableActivity.this.setResult(-1);
            RideUnavailableActivity.this.finish();
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setRequestedOrientation(1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.ride_unavailable);
        CarpoolNativeManager.getInstance().RefreshListOfRides();
        this.mNatMgr = NativeManager.getInstance();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_UNAVAILABLE_TITLE));
        ((WazeTextView) findViewById(C1283R.id.rideUnavailableSubtitle)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_ACCEPTED_BY_ANOTHER_WAZER));
        ((WazeTextView) findViewById(C1283R.id.rideUnavailableSubtitleDetails)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_LOOKS_LIKE_THIS_ROUTE_IS_POPULAR));
        WazeTextView $r7 = (WazeTextView) findViewById(C1283R.id.rideUnavailableButton);
        $r7.setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_UNAVAILABLE_BUTTON));
        $r7.setOnClickListener(new C16911());
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RIDE_TAKEN_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, getIntent().getStringExtra(RIDE_ID));
    }
}
