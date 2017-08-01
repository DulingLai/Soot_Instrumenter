package com.waze.view.popups;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolUserData;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.StarRatingView;
import com.waze.view.anim.StarRatingView.StarRatingListener;

public class TimeRangeSelectDialog extends BaseBottomDialog {
    private final RateRiderResult mRateRiderResult;
    private final String mRideId;
    private final CarpoolUserData mRider;

    class C32283 implements OnClickListener {
        C32283() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RATE_RIDER_SHEET_SKIP_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, TimeRangeSelectDialog.this.mRideId).send();
            TimeRangeSelectDialog.this.dismiss();
        }
    }

    class C32294 implements OnCancelListener {
        C32294() {
        }

        public void onCancel(DialogInterface dialog) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RATE_RIDER_SHEET_CANCELED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, TimeRangeSelectDialog.this.mRideId).send();
        }
    }

    public interface RateRiderResult {
        void onRating(int i);
    }

    public TimeRangeSelectDialog(Context context, String rideId, CarpoolUserData rider, RateRiderResult onRes) {
        super(context);
        this.mRideId = rideId;
        this.mRider = rider;
        this.mRateRiderResult = onRes;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RATE_RIDER_SHEET_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mRideId).send();
        setContentView(C1283R.layout.ride_details_rate);
        TextView tvTitle = (TextView) findViewById(C1283R.id.rideRequestRateTitle);
        Object[] objArr = new Object[1];
        objArr[0] = this.mRider != null ? this.mRider.getFirstName() : DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        tvTitle.setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_DETAILS_RATING_TITLE_PS, objArr));
        ((TextView) findViewById(C1283R.id.rideRequestRateSubTitle)).setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_SUBTITLE));
        final TextView tvSkip = (TextView) findViewById(C1283R.id.rideRequestSkipButton);
        tvSkip.setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_SKIP));
        final TextView tvRate = (TextView) findViewById(C1283R.id.rideRequestRateButton);
        tvRate.setText(DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_SUBMIT));
        final StarRatingView srv = (StarRatingView) findViewById(C1283R.id.rideRequestStars);
        srv.setListener(new StarRatingListener() {
            public void onRatingChanged(int rating) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RATE_RIDER_SHEET_STAR_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, TimeRangeSelectDialog.this.mRideId).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_STARS, "" + rating).send();
                if (rating > 0) {
                    tvRate.setVisibility(0);
                    tvSkip.setEnabled(false);
                    return;
                }
                tvRate.setVisibility(8);
                tvSkip.setEnabled(true);
            }
        });
        tvRate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int rating = srv.getRating();
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RATE_RIDER_SHEET_DONE_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, TimeRangeSelectDialog.this.mRideId).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_STARS, "" + rating).send();
                TimeRangeSelectDialog.this.mRateRiderResult.onRating(rating);
                TimeRangeSelectDialog.this.dismiss();
            }
        });
        tvSkip.setOnClickListener(new C32283());
        setOnCancelListener(new C32294());
    }
}
