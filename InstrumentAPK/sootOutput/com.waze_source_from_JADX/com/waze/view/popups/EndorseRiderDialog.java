package com.waze.view.popups;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolEndorsement;
import com.waze.carpool.CarpoolUserData;
import com.waze.strings.DisplayStrings;

public class EndorseRiderDialog extends BaseBottomDialog {
    public static final int DURATION = 150;
    public static final float SCALE = 1.15f;
    private final EndorseRiderResult mEndorseRiderResult;
    private final String mRideId;
    private final CarpoolUserData mRider;
    private int selectedEndorsement = 0;
    private View selectedView = null;

    class C31361 implements OnClickListener {
        C31361() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_ENDORSE_RIDER_SHEET_DONE_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, EndorseRiderDialog.this.mRideId).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ENDORSEMENT, "" + EndorseRiderDialog.this.selectedEndorsement).send();
            EndorseRiderDialog.this.mEndorseRiderResult.onEndorse(EndorseRiderDialog.this.selectedEndorsement);
            EndorseRiderDialog.this.dismiss();
        }
    }

    class C31372 implements OnCancelListener {
        C31372() {
        }

        public void onCancel(DialogInterface dialog) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_ENDORSE_RIDER_SHEET_CANCELED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, EndorseRiderDialog.this.mRideId).send();
            EndorseRiderDialog.this.mEndorseRiderResult.onEndorse(0);
        }
    }

    public interface EndorseRiderResult {
        void onEndorse(int i);
    }

    public EndorseRiderDialog(Context context, String rideId, CarpoolUserData rider, EndorseRiderResult onRes) {
        super(context);
        this.mRideId = rideId;
        this.mRider = rider;
        this.mEndorseRiderResult = onRes;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_ENDORSE_RIDER_SHEET_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.mRideId).send();
        setContentView(C1283R.layout.ride_details_endorse);
        TextView tvTitle = (TextView) findViewById(C1283R.id.riderEndorseTitle);
        String name = this.mRider != null ? this.mRider.getFirstName() : DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        tvTitle.setText(DisplayStrings.displayStringF(DisplayStrings.DS_ENDORSE_RIDER_TITLE_PS, name));
        ((TextView) findViewById(C1283R.id.riderEndorseSubTitle)).setText(DisplayStrings.displayStringF(DisplayStrings.DS_ENDORSE_RIDER_SUBTITLE_PS, name));
        TextView tvRate = (TextView) findViewById(C1283R.id.riderEndorseButton);
        tvRate.setText(DisplayStrings.displayString(DisplayStrings.DS_ENDORSE_RIDER_SUBMIT));
        tvRate.setOnClickListener(new C31361());
        LinearLayout endorsementsLayout = (LinearLayout) findViewById(C1283R.id.rideRequestEndorsements);
        initEndorsement(endorsementsLayout.getChildAt(0), 1);
        initEndorsement(endorsementsLayout.getChildAt(1), 2);
        initEndorsement(endorsementsLayout.getChildAt(2), 3);
        setOnCancelListener(new C31372());
    }

    private void initEndorsement(View v, final int endorsement) {
        ((ImageView) v.findViewById(C1283R.id.endorsementImage)).setImageResource(CarpoolEndorsement.icon[endorsement]);
        ((TextView) v.findViewById(C1283R.id.endorsementText)).setText(DisplayStrings.displayString(CarpoolEndorsement.name[endorsement]));
        v.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EndorseRiderDialog.this.selectedView != null) {
                    EndorseRiderDialog.this.selectedView.findViewById(C1283R.id.endorsementBg).animate().alpha(0.0f).setDuration(150).start();
                    EndorseRiderDialog.this.selectedView.findViewById(C1283R.id.endorsementImage).animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start();
                }
                if (EndorseRiderDialog.this.selectedView == v) {
                    EndorseRiderDialog.this.selectedView = null;
                    EndorseRiderDialog.this.selectedEndorsement = 0;
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_ENDORSE_RIDER_SHEET_ENDORSEMENT_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, EndorseRiderDialog.this.mRideId).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ENDORSEMENT, "NONE").send();
                    return;
                }
                v.findViewById(C1283R.id.endorsementBg).animate().alpha(1.0f).setDuration(150).start();
                v.findViewById(C1283R.id.endorsementImage).animate().scaleX(EndorseRiderDialog.SCALE).scaleY(EndorseRiderDialog.SCALE).setDuration(150).start();
                EndorseRiderDialog.this.selectedView = v;
                EndorseRiderDialog.this.selectedEndorsement = endorsement;
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_ENDORSE_RIDER_SHEET_ENDORSEMENT_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, EndorseRiderDialog.this.mRideId).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ENDORSEMENT, "" + EndorseRiderDialog.this.selectedEndorsement).send();
            }
        });
    }
}
