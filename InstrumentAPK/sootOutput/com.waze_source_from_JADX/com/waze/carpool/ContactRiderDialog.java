package com.waze.carpool;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;

public class ContactRiderDialog {
    private ActivityBase mActivityBase;
    private AlertDialog mAlertDialog;
    private CarpoolNativeManager mCpnm;
    private NativeManager mNatMgr = AppService.getNativeManager();
    private String mPhoneNumber;
    private final boolean mPresets;
    private CarpoolRide mRide;
    private String mRiderName;

    class C16381 implements OnClickListener {
        C16381() throws  {
        }

        public void onClick(View view) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CONTACT_RIDER_CLICKED, "ACTION", "PHONE");
            ContactRiderDialog.this.mActivityBase.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + ContactRiderDialog.this.mPhoneNumber)));
            ContactRiderDialog.this.mAlertDialog.dismiss();
        }
    }

    class C16392 implements OnClickListener {
        C16392() throws  {
        }

        public void onClick(View view) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CONTACT_RIDER_CLICKED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_TEXT);
            new ChooseTextDialog(ContactRiderDialog.this.mActivityBase, ContactRiderDialog.this.mPhoneNumber, ContactRiderDialog.this.mRiderName, ContactRiderDialog.this.mRide, ContactRiderDialog.this.mPresets).show();
            ContactRiderDialog.this.mAlertDialog.dismiss();
        }
    }

    public ContactRiderDialog(ActivityBase $r1, String $r2, String $r3, CarpoolRide $r4, boolean $z0) throws  {
        this.mActivityBase = $r1;
        this.mPhoneNumber = $r2;
        this.mRiderName = $r3;
        this.mRide = $r4;
        this.mPresets = $z0;
    }

    public void show() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_COMM_STATUS, "STATUS|RIDE_ID|TYPE", "ENABLE|" + this.mRide.getId() + "|PROFILE");
        if (ConfigValues.getBoolValue(5)) {
            Builder builder = new Builder(this.mActivityBase, C1283R.style.CustomPopup);
            builder.setView((int) C1283R.layout.carpool_select_contact_way);
            this.mAlertDialog = builder.create();
            this.mAlertDialog.setCanceledOnTouchOutside(true);
            AlertDialog $r8 = this.mAlertDialog;
            $r8.show();
            ((TextView) this.mAlertDialog.findViewById(C1283R.id.carpoolPhone)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_CONTACT_RIDER_PHONE));
            ((TextView) this.mAlertDialog.findViewById(C1283R.id.carpoolText)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_CONTACT_RIDER_MSG));
            ((TextView) this.mAlertDialog.findViewById(C1283R.id.carpoolPickContactMethodTitle)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_CONTACT_RIDER_TITLE));
            this.mAlertDialog.findViewById(C1283R.id.carpoolSelectItemContainerPhone).setOnClickListener(new C16381());
            this.mAlertDialog.findViewById(C1283R.id.carpoolSelectItemContainerSms).setOnClickListener(new C16392());
            return;
        }
        new ChooseTextDialog(this.mActivityBase, this.mPhoneNumber, this.mRiderName, this.mRide, this.mPresets).show();
    }
}
