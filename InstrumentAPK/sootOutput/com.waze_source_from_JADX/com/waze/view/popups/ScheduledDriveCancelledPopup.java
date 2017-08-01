package com.waze.view.popups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.MoodManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolRide;
import com.waze.carpool.CarpoolUserData;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository$ImageRepositoryListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ScheduledDriveCancelledPopup extends PopUp {
    private static Context mContext = null;
    private static LayoutManager mLayoutManager;
    private boolean mIsShown = false;
    private NativeManager mNm;
    private CarpoolRide mRide = null;
    private CarpoolUserData mUser;

    class C32041 implements OnClickListener {
        C32041() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_POPUP_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "GOT_IT");
            ScheduledDriveCancelledPopup.this.onButtonClicked();
        }
    }

    public boolean onBackPressed() {
        mLayoutManager.callCloseAllPopups(1);
        return true;
    }

    public void dismiss() {
        this.mIsShown = false;
        mLayoutManager.dismiss(this);
    }

    private void close() {
        dismiss();
    }

    private void onButtonClicked() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_POPUP_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "GOT_IT");
        mLayoutManager.callCloseAllPopups(1);
    }

    private void setButtons() {
        ((TextView) findViewById(C1283R.id.schDriCclPupGotItButtonText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_RIDE_CANCELED_TO_BUTTON));
        findViewById(C1283R.id.schDriCclPupGotItButton).setOnClickListener(new C32041());
    }

    public ScheduledDriveCancelledPopup(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        mContext = context;
        mLayoutManager = layoutManager;
        this.mNm = AppService.getNativeManager();
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.scheduled_drive_canceled_popup, this);
    }

    private void fillFrom() {
        ((TextView) findViewById(C1283R.id.schDriCclPupTitleText)).setText(String.format(this.mNm.getLanguageString(2500), new Object[]{this.mUser.getFirstNameAndInitials()}));
        TextView tvTime = (TextView) findViewById(C1283R.id.schDriCclPupTimeText);
        TimeZone tz = Calendar.getInstance().getTimeZone();
        DateFormat tf = android.text.format.DateFormat.getTimeFormat(mContext);
        tf.setTimeZone(tz);
        String localTime = tf.format(new Date(this.mRide.getTime() * 1000));
        tvTime.setText(String.format(this.mNm.getLanguageString(DisplayStrings.DS_RIDE_CANCELED_TO_PS_PICKUP), new Object[]{localTime}));
        findViewById(C1283R.id.schDriCclPupTime).setVisibility(8);
        setButtons();
    }

    private void setupImage() {
        if (this.mUser == null || this.mUser.getImage() == null) {
            findViewById(C1283R.id.schDriCclPupImage).setVisibility(8);
            findViewById(C1283R.id.schDriCclPupTypeIndication).setVisibility(8);
            ImageView icon = (ImageView) findViewById(C1283R.id.schDriCclPupMoodImage);
            if (this.mUser == null) {
                findViewById(C1283R.id.schDriCclPupImage).setVisibility(0);
                findViewById(C1283R.id.schDriCclPupTypeIndication).setVisibility(0);
            }
            icon.setVisibility(0);
            return;
        }
        final ImageView Userimg = (ImageView) findViewById(C1283R.id.schDriCclPupImage);
        Userimg.setVisibility(0);
        findViewById(C1283R.id.schDriCclPupTypeIndication).setVisibility(0);
        Userimg.setVisibility(0);
        ImageRepository.instance.getImage(this.mUser.getImage(), new ImageRepository$ImageRepositoryListener() {
            public void onImageRetrieved(final Bitmap bitmap) {
                ScheduledDriveCancelledPopup.this.post(new Runnable() {
                    public void run() {
                        Userimg.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public Drawable getResourceDrawable(Context context, String image) {
        return MoodManager.getMoodDrawable(context, image);
    }

    public void show(CarpoolRide ride, CarpoolUserData user) {
        if (ride != null) {
            this.mIsShown = true;
            this.mRide = ride;
            this.mUser = user;
            init();
            mLayoutManager.addView(this);
        }
    }

    public View GetView(CarpoolRide ride, CarpoolUserData user) {
        this.mIsShown = true;
        this.mRide = ride;
        this.mUser = user;
        init();
        return this;
    }

    private void init() {
        setupImage();
        fillFrom();
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        for (int id : new int[]{C1283R.id.schDriCclPupGotItButton}) {
            View but = findViewById(id);
            if (dm.widthPixels > DisplayStrings.DS_WAZE_UPDATE_H_ISLL_BE_LATE) {
                but.setBackgroundResource(0);
            } else {
                but.setBackgroundResource(C1283R.drawable.popup_button);
            }
        }
        View layout = findViewById(C1283R.id.schDriCclPupLayout);
        if (dm.widthPixels > DisplayStrings.DS_WAZE_UPDATE_H_ISLL_BE_LATE) {
            layout.setBackgroundResource(C1283R.drawable.base_roundcorners);
            layout.setPadding((int) (((double) dm.density) * 8.2d), 0, (int) (((double) dm.density) * 8.2d), 0);
            return;
        }
        layout.setBackgroundResource(C1283R.drawable.base_reg);
        layout.setPadding(0, 0, 0, 0);
    }

    public void hide() {
        if (this.mIsShown) {
            close();
        }
    }
}
