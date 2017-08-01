package com.waze.carpool;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.profile.ImageTaker;
import com.waze.reports.PointsViewTextWatcher.HasContentValidator;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.title.TitleBar;

public class CarpoolAddPhotoActivity extends ActivityBase implements INextActionCallback {
    private static final String CARPOOL_MANDATORY_INDICATOR = " <font color=#FF7878>*</font>";
    public static final int INTENT_ARRIVE_FROM_ONBOARDING = 1;
    public static final int INTENT_ARRIVE_FROM_OTHER = 0;
    public static final int INTENT_ARRIVE_FROM_RR = 2;
    public static final String INTENT_USER_PICTURE_ARRIVE_FROM = "arriveFrom";
    public static final String INTENT_USER_PICTURE_MANDATORY = "userPictureMandatory";
    public static final String INTENT_USER_PICTURE_UPDATE = "userPictureUpdate";
    private static final String TAG = CarpoolAddPhotoActivity.class.getName();
    private static final String USER_IMAGE_FILE = "CarpoolUserImage";
    private int mArriveFrom = 0;
    private TextView mBottomButton;
    private Bitmap mImageBitmap = null;
    private boolean mIsSending;
    private NativeManager mNm;
    private boolean mPicTimeoutOccurred = false;
    private boolean mUpdatePic = false;
    private String mUserImagePath = null;
    private ImageTaker mUserImageTaker;
    private String mUserImageUrl = null;
    private boolean mUserPictureMandatory;

    class C13801 implements OnClickListener {
        C13801() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolAddPhotoActivity.this.onLater();
        }
    }

    class C13812 implements OnClickListener {
        C13812() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolAddPhotoActivity.this.onLater();
        }
    }

    class C13823 implements Runnable {
        C13823() throws  {
        }

        public void run() throws  {
            CarpoolAddPhotoActivity.this.mPicTimeoutOccurred = true;
            CarpoolAddPhotoActivity.this.mUserImageUrl = null;
        }
    }

    class C13845 implements OnClickListener {
        C13845() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolAddPhotoActivity.this.takeUserPicture();
        }
    }

    class C13856 implements OnClickListener {
        C13856() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolAddPhotoActivity.this.takeUserPicture();
        }
    }

    class C13867 implements OnClickListener {
        C13867() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolAddPhotoActivity.this.nextPressed();
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mArriveFrom = getIntent().getIntExtra(INTENT_USER_PICTURE_ARRIVE_FROM, 0);
        this.mUpdatePic = getIntent().getBooleanExtra(INTENT_USER_PICTURE_UPDATE, false);
        getWindow().setSoftInputMode(3);
        this.mNm = NativeManager.getInstance();
        this.mNm.setUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        this.mUserPictureMandatory = getIntent().getBooleanExtra(INTENT_USER_PICTURE_MANDATORY, false);
        setContentView(C1283R.layout.onboarding_add_photo);
        setUpActivity();
        checkForPicture();
        TitleBar $r7 = (TitleBar) findViewById(C1283R.id.addPhotoTitle);
        if (this.mUserPictureMandatory) {
            $r7.init(this, DisplayStrings.DS_JOIN_CARPOOL_PROFILE_PHOTO_TITLE, true);
            $r7.setOnClickListener(new C13801());
            $r7.setCloseVisibility(false);
        } else {
            $r7.init(this, DisplayStrings.DS_JOIN_CARPOOL_PROFILE_PHOTO_TITLE, DisplayStrings.DS_JOIN_CARPOOL_PROFILE_PHOTO_SKIP);
            $r7.setOnClickCloseListener(new C13812());
        }
        if ($r1 != null) {
            Bitmap $r12 = (Bitmap) $r1.getParcelable(TAG + ".mImageBitmap");
            if ($r12 != null) {
                setImage($r12);
            }
            this.mUserImagePath = $r1.getString(TAG + ".mUserImagePath");
            this.mUserImageUrl = $r1.getString(TAG + ".mUserImageUrl");
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PICTURE_SHOWN);
    }

    private void checkForPicture() throws  {
        CarpoolUserData $r3 = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
        if ($r3 == null) {
            Logger.m43w("CarpoolAddPhotoActivity: CarpoolUserData is null");
            this.mUserImageUrl = null;
            return;
        }
        final String $r4 = $r3.getImage();
        this.mUserImageUrl = null;
        if ($r4 == null || $r4.isEmpty()) {
            Logger.m36d("CarpoolAddPhotoActivity: image url is null");
            this.mUserImageUrl = null;
            return;
        }
        this.mPicTimeoutOccurred = false;
        final C13823 $r1 = new C13823();
        this.mPicTimeoutOccurred = false;
        this.mHandler.postDelayed($r1, 10000);
        VolleyManager.getInstance().loadImageFromUrl($r4, new ImageRequestListener() {
            public boolean receivedCacheResponse = false;

            public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                CarpoolAddPhotoActivity.this.mHandler.removeCallbacks($r1);
                if (!CarpoolAddPhotoActivity.this.mPicTimeoutOccurred) {
                    CarpoolAddPhotoActivity.this.setImage($r1);
                    Logger.m36d("CarpoolAddPhotoActivity: onImageLoadComplete: Setting image");
                    CarpoolAddPhotoActivity.this.mUserImageUrl = $r4;
                }
            }

            public void onImageLoadFailed(Object token, long duration) throws  {
                if (this.receivedCacheResponse) {
                    Logger.m36d("CarpoolAddPhotoActivity: onImageLoadFailed: Waze data image URL failed, not passing image");
                    CarpoolAddPhotoActivity.this.mUserImageUrl = null;
                    return;
                }
                Logger.m36d("CarpoolAddPhotoActivity: onImageLoadFailed: received from cache");
                this.receivedCacheResponse = true;
            }
        });
    }

    private void onLater() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PICTURE_CLICKED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_LATER);
        if (this.mArriveFrom == 1) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_LATER, this);
        }
        setResult(0);
        finish();
    }

    protected void onDestroy() throws  {
        this.mNm.unsetUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putParcelable(TAG + ".mImageBitmap", this.mImageBitmap);
        $r1.putString(TAG + ".mUserImagePath", this.mUserImagePath);
        $r1.putString(TAG + ".mUserImageUrl", this.mUserImageUrl);
    }

    private void setUpActivity() throws  {
        HasContentValidator $r1 = new HasContentValidator();
        this.mBottomButton = (TextView) findViewById(C1283R.id.addPhotoButton);
        TextView $r4 = (TextView) findViewById(C1283R.id.addPhotoText);
        if (this.mArriveFrom != 2) {
            $r4.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_PHOTO_EXPLAIN));
            this.mBottomButton.setText(this.mNm.getLanguageString(this.mImageBitmap == null ? (short) 2981 : (short) 2982));
        } else if (this.mUpdatePic) {
            $r4.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_RR_PHOTO_BROKEN_EXPLAIN));
            this.mBottomButton.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_PROFILE_PHOTO_UPDATE));
        } else {
            $r4.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_RR_PHOTO_MISSING_EXPLAIN));
            this.mBottomButton.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_PROFILE_PHOTO_ADD));
        }
        findViewById(C1283R.id.addPhotoPicLayout).setOnClickListener(new C13845());
        this.mBottomButton.setOnClickListener(new C13856());
    }

    private void nextPressed() throws  {
        if (this.mUserImagePath != null) {
            this.mIsSending = true;
            return;
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PICTURE_CLICKED, "ACTION", "DONE");
        if (this.mArriveFrom == 1) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, this);
            return;
        }
        setResult(-1);
        finish();
    }

    void takeUserPicture() throws  {
        this.mUserImageTaker = new ImageTaker(this, USER_IMAGE_FILE);
        int $i0 = ConfigValues.getIntValue(40);
        this.mUserImageTaker.setOutputResolution($i0, $i0, 1, 1);
        this.mUserImagePath = null;
        this.mUserImageTaker.sendIntent();
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i0 == 222 || $i0 == 223) {
            if (this.mUserImageTaker != null) {
                this.mUserImageTaker.onActivityResult($i0, $i1, $r1);
                if (this.mUserImageTaker.hasImage()) {
                    if (this.mImageBitmap != null) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PICTURE_ADD_CLICKED, "ACTION", "UPDATE");
                    } else {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PICTURE_ADD_CLICKED, "ACTION", "ADD");
                    }
                    setImage(this.mUserImageTaker.getImage());
                    this.mUserImagePath = this.mUserImageTaker.getImagePath();
                    this.mNm.venueAddImage(this.mUserImagePath, 2);
                    findViewById(C1283R.id.addPhotoPicProgress).setVisibility(0);
                }
            }
        } else if ($i1 == -1) {
            setResult(-1);
            finish();
            return;
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    void setImage(Bitmap $r1) throws  {
        this.mImageBitmap = $r1;
        ((ImageView) findViewById(C1283R.id.addPhotoPic)).setImageBitmap($r1);
        ((ImageView) findViewById(C1283R.id.addPhotoPicMustAdd)).setImageResource(C1283R.drawable.carpool_edit_photo);
        this.mBottomButton.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_PROFILE_NEXT_BUTTON));
        this.mBottomButton.setOnClickListener(new C13867());
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what != NativeManager.UH_VENUE_ADD_IMAGE_RESULT) {
            return false;
        }
        Bundle $r2 = $r1.getData();
        String $r3 = $r2.getString("path");
        $r2.getString("id");
        String $r4 = $r2.getString("image_url");
        $r2.getString("image_thumbnail_url");
        boolean $z0 = $r2.getBoolean("res");
        if (this.mUserImagePath != null && this.mUserImagePath.equals($r3)) {
            if ($z0) {
                this.mUserImagePath = null;
                this.mUserImageUrl = $r4;
                ImageRepository.instance.unCache(this.mUserImageUrl);
                ImageRepository.instance.forceCache(this.mUserImageUrl, this.mImageBitmap, false);
                findViewById(C1283R.id.addPhotoPicProgress).setVisibility(8);
                if (this.mIsSending) {
                    this.mIsSending = false;
                    nextPressed();
                }
            } else {
                showError(null);
            }
        }
        return true;
    }

    private void showError(String $r1) throws  {
        if ($r1 == null) {
            $r1 = this.mNm.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_);
        }
        MsgBox.openMessageBox(this.mNm.getLanguageString((int) DisplayStrings.DS_CONNECT_POPUP_TITLE), $r1, false);
    }

    public void onBackPressed() throws  {
        if (this.mArriveFrom == 1) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this);
        }
        super.onBackPressed();
    }

    public void setNextIntent(Intent $r1) throws  {
        startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
    }

    public void setNextFragment(Fragment fragment) throws  {
        Logger.m38e("CarpoolAddPhotoActivity: unexpected action: setNextFragment");
    }

    public void setNextResult(int $i0) throws  {
        if ($i0 == 0) {
            setResult(0);
            finish();
        } else if ($i0 == -1) {
            setResult(-1);
            finish();
        } else {
            Logger.m38e("CarpoolAddPhotoActivity: received unexpected result:" + $i0);
        }
    }

    public Context getContext() throws  {
        return this;
    }
}
