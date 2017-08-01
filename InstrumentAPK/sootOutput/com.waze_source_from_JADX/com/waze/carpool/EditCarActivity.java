package com.waze.carpool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolCarProfileFragment.iTakeCarPicture;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.profile.ImageTaker;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class EditCarActivity extends ActivityBase implements iTakeCarPicture, IPressNext {
    static final String CAR_IMAGE_FILE = "CarpoolCarImage";
    private static final String TAG = EditCarActivity.class.getName();
    private String mCarImagePath;
    private ImageTaker mCarImageTaker;
    private String mCarImageUrl;
    private CarpoolCarProfileFragment mCarProfileFragment;
    private boolean mIsSending;
    private NativeManager mNm;
    private TitleBar mTitleBar;

    class C16401 implements OnClickListener {
        C16401() throws  {
        }

        public void onClick(View v) throws  {
            EditCarActivity.this.setResult(0);
            EditCarActivity.this.nextPressed();
        }
    }

    class C16412 implements DialogInterface.OnClickListener {
        C16412() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
            EditCarActivity.this.finish();
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.fragment_activity_w_pager);
        this.mNm = NativeManager.getInstance();
        this.mNm.setUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.framgentActivityTitle);
        this.mTitleBar.init(this, DisplayStrings.DS_CAR_PROFILE, 375);
        this.mTitleBar.setOnClickCloseListener(new C16401());
        if ($r1 == null) {
            this.mCarProfileFragment = new CarpoolCarProfileFragment();
            this.mCarProfileFragment.setProfile(CarpoolNativeManager.getInstance().getCarpoolProfileNTV());
            this.mCarProfileFragment.setHideLicencePlate(false);
            this.mCarProfileFragment.setStandalone(true);
            getFragmentManager().beginTransaction().add(C1283R.id.framgentActivityFrame1, this.mCarProfileFragment, CarpoolCarProfileFragment.class.getName()).commit();
        } else {
            this.mCarImageUrl = $r1.getString(TAG + ".mCarImageUrl");
            this.mCarProfileFragment = (CarpoolCarProfileFragment) getFragmentManager().findFragmentByTag(CarpoolCarProfileFragment.class.getName());
            this.mCarProfileFragment.setHideLicencePlate(false);
            this.mCarProfileFragment.setStandalone(true);
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_YOUR_CAR_SHOWN);
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putString(TAG + ".mCarImageUrl", this.mCarImageUrl);
    }

    public void takeCarPicture() throws  {
        this.mCarImageTaker = new ImageTaker(this, CAR_IMAGE_FILE);
        int $i1 = ConfigValues.getIntValue(41);
        this.mCarImageTaker.setOutputResolution(($i1 * 4) / 3, $i1, 4, 3);
        this.mCarImageTaker.sendIntent();
    }

    private void tryToUpdateProfile() throws  {
        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
        if (this.mCarImagePath != null) {
            this.mIsSending = true;
            return;
        }
        CarpoolNativeManager.getInstance().updateCarProfile(this.mCarProfileFragment.getMake(), this.mCarProfileFragment.getModel(), this.mCarProfileFragment.getColor(), 0, this.mCarProfileFragment.getLicensePlate(), this.mCarImageUrl);
        CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if (this.mCarImageTaker != null && ($i0 == 222 || $i0 == 223)) {
            this.mCarImageTaker.onActivityResult($i0, $i1, $r1);
            if (this.mCarImageTaker.hasImage()) {
                this.mCarProfileFragment.setImage(this.mCarImageTaker.getImage());
                this.mCarImagePath = this.mCarImageTaker.getImagePath();
                this.mNm.venueAddImage(this.mCarImagePath, 3);
            }
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        Bundle $r3;
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            $r3 = $r1.getData();
            NativeManager.getInstance().CloseProgressPopup();
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
            if ($r3.getBoolean("success")) {
                finish();
                return true;
            }
            showError();
            return true;
        } else if ($r1.what != NativeManager.UH_VENUE_ADD_IMAGE_RESULT) {
            return super.myHandleMessage($r1);
        } else {
            $r3 = $r1.getData();
            String $r6 = $r3.getString("path");
            $r3.getString("id");
            String $r7 = $r3.getString("image_url");
            $r3.getString("image_thumbnail_url");
            boolean $z0 = $r3.getBoolean("res");
            if (this.mCarImagePath != null && this.mCarImagePath.equals($r6)) {
                if ($z0) {
                    this.mCarImagePath = null;
                    this.mCarImageUrl = $r7;
                } else {
                    showError();
                }
            }
            if (!this.mIsSending) {
                return true;
            }
            tryToUpdateProfile();
            return true;
        }
    }

    private void showError() throws  {
        MsgBox.openMessageBoxWithCallback(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false, new C16412());
    }

    public void onBackPressed() throws  {
        setResult(0);
        nextPressed();
    }

    public void nextPressed() throws  {
        if (!this.mCarProfileFragment.wasEdited()) {
            finish();
        } else if (this.mCarProfileFragment.checkIfCarIsGood()) {
            tryToUpdateProfile();
        }
    }
}
