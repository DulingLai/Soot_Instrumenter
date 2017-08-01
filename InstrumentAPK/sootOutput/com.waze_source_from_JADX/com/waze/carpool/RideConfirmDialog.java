package com.waze.carpool;

import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.utils.ImageRepository;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RideConfirmDialog extends Dialog {
    private ActivityBase mActivity;
    private final CarpoolDrive mDrive;
    private final OnClickListener mListener;
    private NativeManager mNatMgr = AppService.getNativeManager();
    private final long mTimeMs;

    class C16891 implements View.OnClickListener {
        C16891() throws  {
        }

        public void onClick(View v) throws  {
            RideConfirmDialog.this.mListener.onClick(RideConfirmDialog.this, 1);
        }
    }

    class C16902 implements View.OnClickListener {
        C16902() throws  {
        }

        public void onClick(View v) throws  {
            RideConfirmDialog.this.mListener.onClick(RideConfirmDialog.this, 0);
        }
    }

    public RideConfirmDialog(ActivityBase $r1, CarpoolDrive $r2, long $l0, OnClickListener $r3) throws  {
        super($r1, C1283R.style.Dialog);
        this.mActivity = $r1;
        this.mDrive = $r2;
        this.mTimeMs = 1000 * $l0;
        this.mListener = $r3;
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        initLayout();
    }

    private void initLayout() throws  {
        setContentView(C1283R.layout.ride_confirm_dialog);
        getWindow().setLayout(-1, -1);
        ((TextView) findViewById(C1283R.id.rideConfirmTitle)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_TITLE));
        ImageView $r6 = (ImageView) findViewById(C1283R.id.rideConfirmRiderProfileImage);
        CarpoolDrive $r7 = this.mDrive;
        if ($r7.hasRider()) {
            TextView $r3 = (TextView) findViewById(C1283R.id.rideConfirmSubTitle);
            NativeManager $r4 = this.mNatMgr;
            Object[] $r8 = new Object[1];
            $r7 = this.mDrive;
            $r8[0] = $r7.getRider().getFirstName();
            $r3.setText($r4.getFormattedString(DisplayStrings.DS_RIDE_REQ_CONFIRM_SUBTITLE_PS, $r8));
            ImageRepository $r10 = ImageRepository.instance;
            $r7 = this.mDrive;
            $r10.getImage($r7.getRider().getImage(), 2, $r6, null, this.mActivity);
        } else {
            ((TextView) findViewById(C1283R.id.rideConfirmSubTitle)).setText(this.mNatMgr.getFormattedString(DisplayStrings.DS_RIDE_REQ_CONFIRM_SUBTITLE_PS, ""));
            Logger.m38e("RideConfirmDialog: rider is null, cannot display details");
        }
        ((TextView) findViewById(C1283R.id.rideConfirmWhen1)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_PICKUP_DAY));
        ((TextView) findViewById(C1283R.id.rideConfirmWhen2)).setText(DisplayUtils.getDayString(this.mActivity, this.mTimeMs, false, false));
        ((TextView) findViewById(C1283R.id.rideConfirmPickup1)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_PICKUP_TIME));
        TimeZone $r13 = Calendar.getInstance().getTimeZone();
        ActivityBase $r11 = this.mActivity;
        DateFormat $r14 = android.text.format.DateFormat.getTimeFormat($r11);
        $r14.setTimeZone($r13);
        ((TextView) findViewById(C1283R.id.rideConfirmPickup2)).setText($r14.format(new Date(this.mTimeMs)));
        ((TextView) findViewById(C1283R.id.rideConfirmLeave1)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_LEAVE_BY));
        CarpoolDriveMatchInfo $r16 = this.mDrive;
        CarpoolDriveMatchInfo $r72 = $r16;
        int $i1 = $r16.drive_match_info;
        CarpoolDriveMatchInfo $r162 = $i1;
        $i1 = $i1.origin_to_pickup_duration_seconds;
        long $l0 = $i1 * 1000;
        int $i12 = $l0;
        ((TextView) findViewById(C1283R.id.rideConfirmLeave2)).setText($r14.format(new Date(this.mTimeMs - ((long) $l0))));
        ((TextView) findViewById(C1283R.id.rideConfirmGet1)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_REWARD));
        ((TextView) findViewById(C1283R.id.rideConfirmGet2)).setText(this.mDrive.getRewardString(this.mActivity));
        ((TextView) findViewById(C1283R.id.confirmSendText)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_YES));
        findViewById(C1283R.id.confirmSend).setOnClickListener(new C16891());
        findViewById(C1283R.id.confirmSendTimer).setVisibility(8);
        ((TextView) findViewById(C1283R.id.confirmCloseText)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_CONFIRM_NO));
        findViewById(C1283R.id.confirmClose).setOnClickListener(new C16902());
        findViewById(C1283R.id.confirmCloseTimer).setVisibility(8);
    }
}
