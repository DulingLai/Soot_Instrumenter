package com.waze.planned_drive;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.LocationFactory;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolRideDetailsActivity;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.map.CanvasFont;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.planned_drive.PlannedDriveDayPicker.DayPickerListener;
import com.waze.planned_drive.PlannedDriveRecycler.PlannedDriveRecyclerListener;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.map.ProgressAnimation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlannedDriveActivity extends ActivityBase implements PlannedDriveOptionsListener, PlannedDriveRecyclerListener, DayPickerListener {
    public static final String EDIT_EXTRA = "editAddressItem";
    private static final long MILIS_IN_DAY = 86400000;
    public static final String SELECT_DESTINATION_FLAG = "select_destination";
    private static AddressItem fromAddressItem;
    private static AddressItem navigationAddressItem;
    public static long nextGraphRevealTime;
    private boolean changedDayAutomatically = false;
    private boolean changedDayManually = false;
    private int delayedSaveAmount = 0;
    private ImageView mBackButton;
    private RelativeLayout mCancelButton;
    private TextView mCancelLabel;
    private LinearLayout mChangeFromButton;
    private TextView mChangeFromLabel;
    private LinearLayout mChangeWhenButton;
    private TextView mChangeWhenLabel;
    private AddressItem mCurrentAddressItem;
    private PlannedDriveDayPicker mDayPicker;
    private int mDaysFromNow = 0;
    private TextView mDestinationLabel;
    private CarpoolDrive mDrive;
    private TextView mFromLabel;
    private boolean mHasShownFirstElement = false;
    private View mHighlightedCellView;
    private boolean mIsSaving;
    private ProgressAnimation mLoadingIndicator;
    private View mLoadingIndicatorBg;
    private List<PlannedDriveOptionModel> mModels;
    private PlannedDriveRecycler mPlannedDriveRecycler;
    private RelativeLayout mSaveButton;
    private TextView mSaveLabel;
    private boolean mShouldShowCarpoolOfferDialog = false;
    private TextView mTitleLabel;
    private TextView mWhenLabel;

    class C23171 implements OnClickListener {
        C23171() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SCHEDULE_PLANNED_DRIVE_CLICK).addParam("ACTION", "CANCEL").send();
            PlannedDriveActivity.this.finish();
        }
    }

    class C23182 implements OnClickListener {
        C23182() {
        }

        public void onClick(View v) {
            PlannedDriveActivity.this.onSave();
        }
    }

    class C23193 implements OnClickListener {
        C23193() {
        }

        public void onClick(View v) {
            PlannedDriveActivity.this.startActivity(new Intent(PlannedDriveActivity.this, PlannedDriveAlternateFromActivity.class));
        }
    }

    class C23204 implements OnClickListener {
        C23204() {
        }

        public void onClick(View v) {
            PlannedDriveActivity.this.mDayPicker.show();
        }
    }

    class C23215 implements Runnable {
        C23215() {
        }

        public void run() {
            PlannedDriveActivity.this.onSave();
        }
    }

    class C23246 extends RunnableUICallback {
        private int danger;

        class C23221 implements DialogInterface.OnClickListener {
            C23221() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    PlannedDriveActivity.this.createPlannedDriveNoDanger();
                    DriveToNativeManager.getInstance().addDangerZoneStat(PlannedDriveActivity.this.mCurrentAddressItem.getLocationX().intValue(), PlannedDriveActivity.this.mCurrentAddressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                    return;
                }
                PlannedDriveActivity.this.setIsSaving(false);
                DriveToNativeManager.getInstance().addDangerZoneStat(PlannedDriveActivity.this.mCurrentAddressItem.getLocationX().intValue(), PlannedDriveActivity.this.mCurrentAddressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
            }
        }

        class C23232 implements OnCancelListener {
            C23232() {
            }

            public void onCancel(DialogInterface dialog) {
                PlannedDriveActivity.this.setIsSaving(false);
                DriveToNativeManager.getInstance().addDangerZoneStat(PlannedDriveActivity.this.mCurrentAddressItem.getLocationX().intValue(), PlannedDriveActivity.this.mCurrentAddressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, "BACK");
            }
        }

        C23246() {
        }

        public void event() {
            this.danger = DriveToNativeManager.getInstance().isInDangerZoneNTV(PlannedDriveActivity.this.mCurrentAddressItem.getLocationX().intValue(), PlannedDriveActivity.this.mCurrentAddressItem.getLocationY().intValue());
        }

        public void callback() {
            NativeManager natMgr = NativeManager.getInstance();
            if (this.danger >= 0) {
                MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(natMgr.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), natMgr.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE), false, new C23221(), natMgr.getLanguageString(DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE_BUTTON), natMgr.getLanguageString(344), -1, "dangerous_zone_icon", new C23232(), true, true);
            } else {
                PlannedDriveActivity.this.createPlannedDriveNoDanger();
            }
        }
    }

    class C23257 implements Runnable {
        C23257() {
        }

        public void run() {
            PlannedDriveActivity.this.mHighlightedCellView.setVisibility(4);
            PlannedDriveActivity.this.mLoadingIndicatorBg.setVisibility(0);
        }
    }

    class C23268 implements Runnable {
        C23268() {
        }

        public void run() {
            PlannedDriveActivity.this.mDaysFromNow = 1;
            PlannedDriveActivity.this.mDayPicker.buildDays();
            PlannedDriveActivity.this.mChangeWhenLabel.setText(PlannedDriveActivity.this.mDayPicker.getValue(PlannedDriveActivity.this.mDaysFromNow));
            PlannedDriveActivity.this.mModels.clear();
            PlannedDriveActivity.this.changedDayAutomatically = true;
            PlannedDriveActivity.this.loadData();
        }
    }

    class C23289 implements Runnable {
        C23289() {
        }

        public void run() {
            int verticalPadding = (PlannedDriveActivity.this.mPlannedDriveRecycler.getMeasuredHeight() / 2) - (PixelMeasure.dimension(C1283R.dimen.planDriveCellHeight) / 2);
            PlannedDriveActivity.this.mPlannedDriveRecycler.setPadding(0, verticalPadding, 0, verticalPadding);
            PlannedDriveActivity.this.mHasShownFirstElement = false;
            PlannedDriveActivity.this.mPlannedDriveRecycler.getAdapter().notifyDataSetChanged();
            long startTime = PlannedDriveActivity.this.mCurrentAddressItem.getStartTimeMillis();
            if (startTime != -1) {
                long daysStartTime = ((System.currentTimeMillis() / 86400000) + ((long) PlannedDriveActivity.this.mDaysFromNow)) * 86400000;
                if (startTime < daysStartTime || startTime > 86400000 + daysStartTime) {
                    startTime = -1;
                }
            }
            int scrollToIndex = 0;
            int i = 0;
            while (i < PlannedDriveActivity.this.mModels.size()) {
                if (startTime == -1) {
                    if (!PlannedDriveActivity.this.changedDayAutomatically && ((PlannedDriveOptionModel) PlannedDriveActivity.this.mModels.get(i)).getHour() >= 8) {
                        scrollToIndex = i;
                        break;
                    }
                } else if (startTime == ((PlannedDriveOptionModel) PlannedDriveActivity.this.mModels.get(i)).getTimeMillis()) {
                    scrollToIndex = i;
                }
                i++;
            }
            if (!PlannedDriveActivity.this.changedDayAutomatically && scrollToIndex < 2) {
                scrollToIndex = 2;
            }
            final int delta = ((int) PlannedDriveActivity.this.getResources().getDimension(C1283R.dimen.planDriveCellHeight)) * scrollToIndex;
            PlannedDriveActivity.this.post(new Runnable() {
                public void run() {
                    PlannedDriveActivity.this.mPlannedDriveRecycler.smoothScrollBy(0, delta);
                }
            });
            PlannedDriveActivity.this.mLoadingIndicator.stop();
            PlannedDriveActivity.this.mLoadingIndicator.setVisibility(8);
            PlannedDriveActivity.this.mLoadingIndicatorBg.setVisibility(8);
            PlannedDriveActivity.this.mHighlightedCellView.setVisibility(0);
            ViewPropertyAnimatorHelper.initAnimation(PlannedDriveActivity.this.mHighlightedCellView).scaleX(1.0f).setListener(null);
        }
    }

    public class OptionViewAdapter extends Adapter<OptionViewHolder> {
        private int mLastBoundPosition = -1;

        public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OptionViewHolder(new PlannedDriveOptionView(PlannedDriveActivity.this));
        }

        public void onViewAttachedToWindow(OptionViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            holder.resumeGraphAnimation();
        }

        public void onViewDetachedFromWindow(OptionViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.cancelPendingGraphAnimation();
        }

        public void onBindViewHolder(OptionViewHolder holder, final int position) {
            if (PlannedDriveActivity.this.mModels == null || position > PlannedDriveActivity.this.mModels.size()) {
                Logger.e(new StringBuilder().append("PlannedDrive onBindViewHolder - position ").append(position).append(", mModels is ").append(PlannedDriveActivity.this.mModels).toString() == null ? "null" : Integer.toString(PlannedDriveActivity.this.mModels.size()));
                return;
            }
            boolean fromTop = this.mLastBoundPosition <= position;
            this.mLastBoundPosition = position;
            long now = System.currentTimeMillis();
            if (PlannedDriveActivity.nextGraphRevealTime < now) {
                PlannedDriveActivity.nextGraphRevealTime = now;
            }
            if (!(!PlannedDriveActivity.this.mHasShownFirstElement || PlannedDriveGraphView.haltAllGraphAnimations || PlannedDriveActivity.this.mPlannedDriveRecycler.isAnyGraphAnimating())) {
                PlannedDriveGraphView.haltAllGraphAnimations = true;
                PlannedDriveActivity.this.mPlannedDriveRecycler.cancelGraphAnimations();
            }
            holder.bindView((PlannedDriveOptionModel) PlannedDriveActivity.this.mModels.get(position), fromTop, PlannedDriveActivity.nextGraphRevealTime - now);
            if (!PlannedDriveGraphView.haltAllGraphAnimations) {
                PlannedDriveActivity.nextGraphRevealTime += 100;
            }
            if (!PlannedDriveActivity.this.mHasShownFirstElement) {
                PlannedDriveActivity.this.mHasShownFirstElement = true;
                holder.showDetails();
            }
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    PlannedDriveActivity.this.mPlannedDriveRecycler.smoothScrollToPosition(position);
                }
            });
        }

        public int getItemCount() {
            return PlannedDriveActivity.this.mModels.size();
        }
    }

    public class OptionViewHolder extends ViewHolder {
        private PlannedDriveOptionView mOptionView;

        public OptionViewHolder(PlannedDriveOptionView itemView) {
            super(itemView);
            this.mOptionView = itemView;
        }

        public void bindView(PlannedDriveOptionModel model, boolean fromTop, long graphRevealDelay) {
            this.mOptionView.setModel(model, fromTop, graphRevealDelay);
        }

        public void showDetails() {
            this.mOptionView.showDetails();
        }

        public void animateGraph(boolean fromTop, long graphRevealDelay) {
            this.mOptionView.animateGraph(fromTop, graphRevealDelay);
        }

        public boolean isGraphAnimating() {
            return this.mOptionView.isGraphAnimating();
        }

        public void cancelPendingGraphAnimation() {
            this.mOptionView.cancelPendingGraphAnimation();
        }

        public void resumeGraphAnimation() {
            this.mOptionView.resumeGraphAnimation();
        }
    }

    public static void setNavigationAddressItem(AddressItem addressItem) {
        navigationAddressItem = addressItem;
        fromAddressItem = null;
    }

    public static void setFromAddressItem(AddressItem addressItem) {
        fromAddressItem = addressItem;
    }

    public static boolean hasNavigationAddressItem() {
        return navigationAddressItem != null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.planned_drive_activity);
        this.mTitleLabel = (TextView) findViewById(C1283R.id.lblTitle);
        this.mDestinationLabel = (TextView) findViewById(C1283R.id.lblDestination);
        this.mFromLabel = (TextView) findViewById(C1283R.id.lblFrom);
        this.mWhenLabel = (TextView) findViewById(C1283R.id.lblWhen);
        this.mCancelLabel = (TextView) findViewById(C1283R.id.lblCancel);
        this.mSaveLabel = (TextView) findViewById(C1283R.id.lblSave);
        this.mChangeFromLabel = (TextView) findViewById(C1283R.id.lblChangeFrom);
        this.mChangeWhenLabel = (TextView) findViewById(C1283R.id.lblChangeWhen);
        this.mBackButton = (ImageView) findViewById(C1283R.id.btnBack);
        this.mCancelButton = (RelativeLayout) findViewById(C1283R.id.btnCancel);
        this.mSaveButton = (RelativeLayout) findViewById(C1283R.id.btnSave);
        this.mChangeFromButton = (LinearLayout) findViewById(C1283R.id.btnChangeFrom);
        this.mChangeWhenButton = (LinearLayout) findViewById(C1283R.id.btnChangeWhen);
        this.mPlannedDriveRecycler = (PlannedDriveRecycler) findViewById(C1283R.id.plannedDriveRecycler);
        this.mDayPicker = (PlannedDriveDayPicker) findViewById(C1283R.id.plannedDriveDayPicker);
        this.mHighlightedCellView = findViewById(C1283R.id.highlightedCellView);
        this.mLoadingIndicatorBg = findViewById(C1283R.id.loadingIndicatorBg);
        this.mLoadingIndicator = (ProgressAnimation) findViewById(C1283R.id.loadingIndicator);
        this.mLoadingIndicator.hideWazer();
        this.mDayPicker.setListener(this);
        this.mPlannedDriveRecycler.setAdapter(new OptionViewAdapter());
        this.mPlannedDriveRecycler.setListener(this);
        this.mModels = new ArrayList();
        OnClickListener cancelClickListener = new C23171();
        this.mCancelButton.setOnClickListener(cancelClickListener);
        this.mBackButton.setOnClickListener(cancelClickListener);
        this.mSaveButton.setOnClickListener(new C23182());
        this.mChangeFromButton.setOnClickListener(new C23193());
        this.mChangeWhenButton.setOnClickListener(new C23204());
        this.mChangeWhenLabel.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TODAY_CAP));
        this.mFromLabel.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_FROM_LABEL));
        this.mWhenLabel.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_DAY_LABEL));
        this.mSaveLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_SET_BUTTON));
        this.mCancelLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_CANCEL_BUTTON));
        this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_TITLE));
        ((TextView) findViewById(C1283R.id.lblArriveAt)).setText(DisplayStrings.displayString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_TIME_LABEL));
        ((TextView) findViewById(C1283R.id.lblTraffic)).setText(DisplayStrings.displayString(230));
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.Light));
        }
    }

    private void onSave() {
        int pos = this.mPlannedDriveRecycler.getSelectedPosition();
        if (pos < 0 || pos >= this.mModels.size()) {
            this.delayedSaveAmount++;
            Logger.w("PlannedDrive: save clicked too soon? Doing nothing, retry " + this.delayedSaveAmount);
            if (this.delayedSaveAmount > 5) {
                Logger.e("PlannedDrive: too many retries on save, giving up");
                this.delayedSaveAmount = 0;
                return;
            }
            postDelayed(new C23215(), 200);
            return;
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SCHEDULE_PLANNED_DRIVE_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SAVE).addParam("DAYS_AHEAD", (long) this.mDaysFromNow).addParam("LOCATION", fromAddressItem == null ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_CURRENT : AnalyticsEvents.ANALYTICS_EVENT_VALUE_CHANGED).send();
        if (this.mCurrentAddressItem == null || TextUtils.isEmpty(this.mCurrentAddressItem.getMeetingId())) {
            createPlannedDrive();
        } else {
            updatePlannedDrive();
        }
        this.delayedSaveAmount = 0;
    }

    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra(SELECT_DESTINATION_FLAG, false)) {
            this.mCurrentAddressItem = null;
            navigationAddressItem = null;
            getIntent().removeExtra(SELECT_DESTINATION_FLAG);
            Intent intent = new Intent(this, PlannedDriveAlternateFromActivity.class);
            intent.putExtra(PlannedDriveAlternateFromActivity.MODE, 1);
            startActivity(intent);
        } else if (getIntent().getExtras() == null || !getIntent().getExtras().containsKey(EDIT_EXTRA)) {
            this.mCurrentAddressItem = navigationAddressItem;
            if (this.mCurrentAddressItem != null) {
                this.mCurrentAddressItem.setMeetingId(null);
                setFields();
                loadData();
            }
        } else {
            this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_NAVLIST_OPTIONS_EDIT_PLANNED_DRIVE));
            this.mCurrentAddressItem = (AddressItem) getIntent().getSerializableExtra(EDIT_EXTRA);
            setFields();
            if (this.mCurrentAddressItem.getStartTimeMillis() != -1) {
                Calendar timestamp = Calendar.getInstance(NativeManager.getInstance().getLocale());
                timestamp.setTimeInMillis(this.mCurrentAddressItem.getStartTimeMillis());
                int daysFromNow = timestamp.get(7) - Calendar.getInstance(NativeManager.getInstance().getLocale()).get(7);
                if (daysFromNow < 0) {
                    daysFromNow += 7;
                }
                this.mDayPicker.buildDays();
                onDayPicked(daysFromNow, this.mDayPicker.getValue(daysFromNow));
                return;
            }
            loadData();
        }
    }

    private void setFields() {
        if (fromAddressItem != null) {
            this.mChangeFromLabel.setText(fromAddressItem.getTitle());
        } else {
            this.mChangeFromLabel.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_CURRENT_LOCATION));
        }
        if (this.mCurrentAddressItem != null) {
            this.mDestinationLabel.setText(TextUtils.isEmpty(this.mCurrentAddressItem.getTitle()) ? this.mCurrentAddressItem.getAddress() : this.mCurrentAddressItem.getTitle());
        } else {
            finish();
        }
    }

    public void onBackPressed() {
        if (this.mDayPicker.getVisibility() == 0) {
            this.mDayPicker.hide();
        } else {
            super.onBackPressed();
        }
    }

    private void updatePlannedDrive() {
        if (!this.mIsSaving) {
            setIsSaving(true);
            DriveToNativeManager.getInstance().updatePlannedDrive(this.mCurrentAddressItem.getMeetingId(), ((PlannedDriveOptionModel) this.mModels.get(this.mPlannedDriveRecycler.getSelectedPosition())).getTimeMillis() / 1000, this);
        }
    }

    private void createPlannedDrive() {
        if (!this.mIsSaving) {
            setIsSaving(true);
            if (ConfigValues.getBoolValue(65)) {
                CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED, this.mHandler);
            }
            NativeManager.getInstance();
            NativeManager.Post(new C23246());
        }
    }

    protected void onDestroy() {
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED, this.mHandler);
        super.onDestroy();
    }

    protected boolean myHandleMessage(Message msg) {
        if (msg.what != CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED) {
            return super.myHandleMessage(msg);
        }
        NativeManager.getInstance().CloseProgressPopup();
        CarpoolDrive drive = (CarpoolDrive) msg.getData().getParcelable("CarpoolDrive");
        if (drive == null) {
            return true;
        }
        this.mShouldShowCarpoolOfferDialog = true;
        this.mDrive = drive;
        return true;
    }

    private void createPlannedDriveNoDanger() {
        DriveToNativeManager.getInstance().createPlannedDrive(this.mCurrentAddressItem.getTitle(), this.mCurrentAddressItem.getLocationX().intValue(), this.mCurrentAddressItem.getLocationY().intValue(), this.mCurrentAddressItem.getHouse(), this.mCurrentAddressItem.getStreet(), this.mCurrentAddressItem.getCity(), this.mCurrentAddressItem.getCountry(), this.mCurrentAddressItem.venueData != null ? this.mCurrentAddressItem.venueData.id : "", ((PlannedDriveOptionModel) this.mModels.get(this.mPlannedDriveRecycler.getSelectedPosition())).getTimeMillis() / 1000, this.mCurrentAddressItem.routingContext, this);
    }

    private void loadData() {
        if (this.mCurrentAddressItem != null) {
            this.mModels.clear();
            this.mPlannedDriveRecycler.getAdapter().notifyDataSetChanged();
            float scaleX = ((float) PixelMeasure.dimension(C1283R.dimen.planDriveCellHeight)) / ((float) (getResources().getDisplayMetrics().widthPixels / (isPortrait() ? 1 : 2)));
            this.mLoadingIndicator.setVisibility(0);
            this.mLoadingIndicator.start();
            if (this.mHighlightedCellView.getVisibility() == 8) {
                this.mHighlightedCellView.setVisibility(4);
                this.mHighlightedCellView.setScaleX(scaleX);
                this.mLoadingIndicatorBg.setVisibility(0);
            } else {
                ViewPropertyAnimatorHelper.initAnimation(this.mHighlightedCellView).scaleX(scaleX).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C23257()));
            }
            int current_lon = 0;
            int current_lat = 0;
            if (fromAddressItem == null) {
                Location loc = LocationFactory.getInstance().getLastLocation();
                if (loc != null) {
                    NativeLocation nLoc = LocationFactory.getNativeLocation(loc);
                    current_lon = nLoc.mLongtitude;
                    current_lat = nLoc.mLatitude;
                }
            } else {
                current_lon = fromAddressItem.getLocationX().intValue();
                current_lat = fromAddressItem.getLocationY().intValue();
            }
            DriveToNativeManager.getInstance().loadPlannedDriveOptions(this.mDaysFromNow, current_lon, current_lat, this.mCurrentAddressItem.getLocationX().intValue(), this.mCurrentAddressItem.getLocationY().intValue(), this.mCurrentAddressItem.getStreet(), this.mCurrentAddressItem.getHouse(), this.mCurrentAddressItem.getCity(), this.mCurrentAddressItem.venueData != null ? this.mCurrentAddressItem.venueData.id : "", this.mCurrentAddressItem.venueData != null ? this.mCurrentAddressItem.venueData.RoutingContext : "", this);
        }
    }

    public void onPlannedDriveOptionsLoaded(int[] etas, int[] times, boolean too_late) {
        if (too_late && !this.changedDayManually && this.mDaysFromNow == 0) {
            runOnUiThread(new C23268());
            return;
        }
        this.mModels.clear();
        this.mModels.addAll(PlannedDriveOptionModel.createOptionModels(etas, times));
        runOnUiThread(new C23289());
    }

    private void setIsSaving(boolean isSaving) {
        this.mIsSaving = isSaving;
        post(new Runnable() {
            public void run() {
                PlannedDriveActivity.this.mSaveButton.setAlpha(PlannedDriveActivity.this.mIsSaving ? CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR : 1.0f);
                if (PlannedDriveActivity.this.mIsSaving) {
                    NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
                } else {
                    NativeManager.getInstance().CloseProgressPopup();
                }
            }
        });
    }

    public void onPlannedDriveCreationSuccess() {
        setIsSaving(false);
        ConfigManager.getInstance().setConfigValueLong(194, 1 + ConfigManager.getInstance().getConfigValueLong(194));
        post(new Runnable() {

            class C23151 implements Runnable {
                C23151() {
                }

                public void run() {
                    NativeManager.getInstance().CloseProgressPopup();
                    if (AppService.getMainActivity() != null) {
                        AppService.getMainActivity().getLayoutMgr().refreshRecentsNavigationList();
                    }
                    if (PlannedDriveActivity.this.mShouldShowCarpoolOfferDialog) {
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_RIDE_REQUEST_POPUP_SHOWN).send();
                        PlannedDriveActivity.this.showCarpoolOfferDialog(PlannedDriveActivity.this.mDrive);
                        return;
                    }
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_RIDE_REQUEST_POPUP_NOT_SHOWN).addParam("REASON", AnalyticsEvents.ANALYTICS_EVENT_VALUE_NO_RIDE).send();
                    PlannedDriveActivity.this.finish();
                }
            }

            public void run() {
                long j;
                NativeManager.getInstance().OpenProgressIconPopup(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_LOCATION_SAVED_WILL_NOTIFY), "bigblue_v_icon");
                PlannedDriveActivity plannedDriveActivity = PlannedDriveActivity.this;
                Runnable c23151 = new C23151();
                if (PlannedDriveActivity.this.mShouldShowCarpoolOfferDialog) {
                    j = 1000;
                } else {
                    j = 2000;
                }
                plannedDriveActivity.postDelayed(c23151, j);
            }
        });
    }

    private void showCarpoolOfferDialog(CarpoolDrive drive) {
        String title = DisplayStrings.displayStringF(DisplayStrings.DS_OFFER_RIDE_FROM_PLAN_DRIVE_TITLE_PS, drive.getRewardString(this));
        String text1 = DisplayStrings.displayStringF(DisplayStrings.DS_OFFER_RIDE_FROM_PLAN_DRIVE_SUBTITLE_PS, drive.getRider().getGivenName());
        String text2 = DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_FROM_PLAN_DRIVE_ON_YOUR_WAY) + " ";
        if (this.mCurrentAddressItem.getType() == 1) {
            text2 = text2 + DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_TO_HOME);
        } else {
            text2 = text2 + DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_DETAILS_TO_PS, this.mCurrentAddressItem.getAddress());
        }
        View customView = LayoutInflater.from(this).inflate(C1283R.layout.carpool_offer_custom_view, null);
        ((TextView) customView.findViewById(C1283R.id.carpoolOfferTitle)).setText(text1);
        ((TextView) customView.findViewById(C1283R.id.carpoolOfferText)).setText(text2);
        ImageView image = (ImageView) customView.findViewById(C1283R.id.carpoolOfferImage);
        String imageUrl = drive.getRider().getImage();
        if (imageUrl == null || imageUrl.length() <= 0) {
            image.setVisibility(8);
        } else {
            image.setImageResource(C1283R.drawable.rs_profilepic_placeholder);
            final ImageView imageView = image;
            VolleyManager.getInstance().loadImageFromUrl(imageUrl, new VolleyManager$ImageRequestListener() {
                public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
                    imageView.setImageDrawable(new CircleFrameDrawable(bitmap, 0, 2));
                }

                public void onImageLoadFailed(Object token, long duration) {
                }
            });
        }
        final CarpoolDrive carpoolDrive = drive;
        Dialog d = MsgBox.openConfirmDialogJavaCallback(title, "", true, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_RIDE_REQUEST_POPUP_CLICKED).addParam("ACTION", which == 1 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_VIEW_RIDE : "SKIP").send();
                if (which == 1) {
                    Intent intent = new Intent(PlannedDriveActivity.this, CarpoolRideDetailsActivity.class);
                    intent.putExtra(CarpoolDrive.class.getSimpleName(), carpoolDrive);
                    AppService.getActiveActivity().startActivity(intent);
                }
                PlannedDriveActivity.this.finish();
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_FROM_PLAN_DRIVE_MORE_INFO), DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_FROM_PLAN_DRIVE_SKIP), -1, null, null, false, true, false, customView, new LayoutParams(-2, -2));
        ((TextView) d.findViewById(C1283R.id.confirmTitle)).setLines(0);
        ((TextView) d.findViewById(C1283R.id.confirmTitle)).setMaxLines(3);
        d.findViewById(C1283R.id.confirmText).setVisibility(8);
        View closeButton = d.findViewById(C1283R.id.confirmClose);
        ViewGroup buttonLayout = (ViewGroup) closeButton.getParent();
        ViewGroup.LayoutParams p = closeButton.getLayoutParams();
        buttonLayout.removeView(closeButton);
        buttonLayout.addView(closeButton, p);
        d.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                PlannedDriveActivity.this.finish();
            }
        });
    }

    public void onPlannedDriveCreationFailed() {
        setIsSaving(false);
        Toast.makeText(this, "Planned drive failed!", 0).show();
    }

    public void onPlannedDriveUpdatedSuccess() {
        setIsSaving(false);
        post(new Runnable() {

            class C23161 implements Runnable {
                C23161() {
                }

                public void run() {
                    NativeManager.getInstance().CloseProgressPopup();
                    PlannedDriveActivity.this.finish();
                }
            }

            public void run() {
                NativeManager.getInstance().OpenProgressIconPopup(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_LOCATION_SAVED_WILL_NOTIFY), "bigblue_v_icon");
                PlannedDriveActivity.this.postDelayed(new C23161(), 2000);
            }
        });
    }

    public void onPlannedDriveUpdatedFailed() {
        setIsSaving(false);
        Toast.makeText(this, "Editing drive failed!", 0).show();
    }

    public void onRecyclerScrollStop() {
    }

    public void onDayPicked(int daysFromNow, String label) {
        this.mDaysFromNow = daysFromNow;
        this.mChangeWhenLabel.setText(label);
        this.changedDayManually = true;
        this.changedDayAutomatically = false;
        loadData();
    }

    public static void setupDriveLaterButton(View buttonContainer) {
        ((TextView) buttonContainer.findViewById(C1283R.id.lblPlannedDrive)).setText(DisplayStrings.displayString(292));
    }
}
