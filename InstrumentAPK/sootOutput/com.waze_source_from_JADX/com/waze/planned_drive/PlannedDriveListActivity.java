package com.waze.planned_drive;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.Utils;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.menus.AddressItemOptionsUtil;
import com.waze.menus.AddressItemOptionsUtil.AddressItemOptionListener;
import com.waze.menus.AddressItemView;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.social.FacebookEventActivity;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.navigate.ParkingSearchResultsActivity;
import com.waze.navigate.PublicMacros;
import com.waze.settings.SettingsCalendarActivity;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.title.TitleBar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlannedDriveListActivity extends ActivityBase implements PlannedDriveListListener, AddressItemOptionListener {
    private static final Object CALENDAR_FLAG = new Object();
    private static final Object EMPTY_SPACE_FLAG = new Object();
    private static final Object FACEBOOK_FLAG = new Object();
    private static final int ITEM_TYPE_ADDRESS_ITEM = 0;
    private static final int ITEM_TYPE_CALENDAR = 4;
    private static final int ITEM_TYPE_EMPTY_SPACE = 2;
    private static final int ITEM_TYPE_FACEBOOK = 3;
    private static final int ITEM_TYPE_HEADER_ITEM = 6;
    private static final int ITEM_TYPE_NO_ITEMS = 5;
    private static final int ITEM_TYPE_SEPARATOR = 8;
    private static final int ITEM_TYPE_TOP_HEADER_ITEM = 7;
    private static final Object NO_ITEMS_FLAG = new Object();
    private static final Object SEPARATOR_FLAG = new Object();
    private static final Object TOP_HEADER_FLAG = new Object();
    private static final int TWO_HOURS_IN_MILLIS = 7200000;
    private List<Object> mDataSource = new ArrayList();
    private SimpleDateFormat mDateFormat;
    private boolean mFacebookSynchedSuccessfully;
    private SimpleDateFormat mHourFormat;
    private RecyclerView mRecyclerView;
    private boolean mShowCalendar;
    private boolean mShowFacebook;
    private TitleBar mTitleBar;
    private String mTodayDate;
    private String mTomorrowDate;

    class C23341 implements OnClickListener {
        C23341() {
        }

        public void onClick(View v) {
            PlannedDriveListActivity.this.startActivity(new Intent(PlannedDriveListActivity.this, SettingsCalendarActivity.class));
        }
    }

    class C23352 implements OnClickListener {
        C23352() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "ADD");
            Intent intent = new Intent(PlannedDriveListActivity.this, PlannedDriveActivity.class);
            intent.putExtra(PlannedDriveActivity.SELECT_DESTINATION_FLAG, true);
            PlannedDriveListActivity.this.startActivity(intent);
        }
    }

    class C23373 implements Runnable {
        C23373() {
        }

        public void run() {
            boolean z = true;
            final AddressItem[] addressItems = DriveToNativeManager.getInstance().getPlannedDriveEventsNTV();
            if (PlannedDriveListActivity.this.mFacebookSynchedSuccessfully) {
                PlannedDriveListActivity.this.mShowFacebook = false;
                PlannedDriveListActivity.this.mFacebookSynchedSuccessfully = false;
            } else {
                PlannedDriveListActivity plannedDriveListActivity = PlannedDriveListActivity.this;
                boolean z2 = MyWazeNativeManager.getInstance().FacebookEnabledNTV() && !MyWazeNativeManager.getInstance().getFacebookLoggedInNTV();
                plannedDriveListActivity.mShowFacebook = z2;
            }
            PlannedDriveListActivity plannedDriveListActivity2 = PlannedDriveListActivity.this;
            if (!NativeManager.getInstance().CalendarFeatureEnabled() || (!NativeManager.getInstance().CalendarAuthUndeterminedNTV() && (!NativeManager.getInstance().calendarAuthorizedNTV() || MyWazeNativeManager.getInstance().getContactLoggedInNTV()))) {
                z = false;
            }
            plannedDriveListActivity2.mShowCalendar = z;
            if (PlannedDriveListActivity.this.mDateFormat == null || PlannedDriveListActivity.this.mHourFormat == null) {
                PlannedDriveListActivity.this.mDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy", NativeManager.getInstance().getLocale());
                PlannedDriveListActivity.this.mHourFormat = new SimpleDateFormat(NativeManager.getInstance().is24HrClock() ? "HH:mm" : "hh:mm a", NativeManager.getInstance().getLocale());
            }
            PlannedDriveListActivity.this.post(new Runnable() {
                public void run() {
                    PlannedDriveListActivity.this.processDataSource(addressItems);
                }
            });
        }
    }

    class C23395 implements DriveToNavigateCallback {
        C23395() {
        }

        public void navigateCallback(int rc) {
            if (rc == 0) {
                PlannedDriveListActivity.this.finish();
            }
        }
    }

    class C23406 implements DriveToNavigateCallback {
        C23406() {
        }

        public void navigateCallback(int rc) {
            if (rc == 0) {
                PlannedDriveListActivity.this.finish();
            }
        }
    }

    private class HeaderItem {
        public String title;

        public HeaderItem(String title) {
            this.title = title;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof HeaderItem) || ((HeaderItem) obj).title == null) {
                return false;
            }
            return ((HeaderItem) obj).title.equals(this.title);
        }
    }

    private class PlannedDriveListAdapter extends Adapter<ViewHolder> {

        class C23411 implements OnClickListener {
            C23411() {
            }

            public void onClick(View v) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_EVENT_VALUE_SYNC_CAL);
                NativeManager.getInstance().CalendaRequestAccessNTV();
            }
        }

        class C23422 implements OnClickListener {
            C23422() {
            }

            public void onClick(View v) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_EVENT_VALUE_SYNC_FB);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FACEBOOK_CONNECT_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_SCREEN);
                NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
                FacebookManager.getInstance().loginWithFacebook(AppService.getActiveActivity(), FacebookLoginType.SetToken, true);
            }
        }

        private PlannedDriveListAdapter() {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;
            switch (viewType) {
                case 0:
                    itemView = LayoutInflater.from(PlannedDriveListActivity.this).inflate(C1283R.layout.planned_drive_event_item, null);
                    break;
                case 2:
                    itemView = new View(PlannedDriveListActivity.this);
                    itemView.setLayoutParams(new LayoutParams(-1, PixelMeasure.dp(32)));
                    break;
                case 3:
                    itemView = LayoutInflater.from(PlannedDriveListActivity.this).inflate(C1283R.layout.planned_drive_sync_events_item, null);
                    ((ImageView) itemView.findViewById(C1283R.id.imgIcon)).setImageResource(C1283R.drawable.list_icon_calendar_fb);
                    ((TextView) itemView.findViewById(C1283R.id.lblTitle)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_LIST_SYNC_FACEBOOK));
                    itemView.setOnClickListener(new C23422());
                    break;
                case 4:
                    itemView = LayoutInflater.from(PlannedDriveListActivity.this).inflate(C1283R.layout.planned_drive_sync_events_item, null);
                    ((ImageView) itemView.findViewById(C1283R.id.imgIcon)).setImageResource(C1283R.drawable.list_icon_calendar);
                    ((TextView) itemView.findViewById(C1283R.id.lblTitle)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_LIST_SYNC_CALENDAR));
                    itemView.setOnClickListener(new C23411());
                    break;
                case 5:
                    itemView = LayoutInflater.from(PlannedDriveListActivity.this).inflate(C1283R.layout.planned_drive_first_time_item, null);
                    ((TextView) itemView.findViewById(C1283R.id.lblFirstTimeTitle)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_LIST_EMPTY_FIRST_HEADER));
                    ((TextView) itemView.findViewById(C1283R.id.lblFirstTimeDetails)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_LIST_EMPTY_FIRST_TEXT));
                    break;
                case 6:
                    itemView = LayoutInflater.from(PlannedDriveListActivity.this).inflate(C1283R.layout.planned_drive_header_item, null);
                    break;
                case 7:
                    itemView = LayoutInflater.from(PlannedDriveListActivity.this).inflate(C1283R.layout.planned_drive_top_header_item, null);
                    ((TextView) itemView.findViewById(C1283R.id.lblHeader)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_LIST_ITEMS_HEADER));
                    break;
                case 8:
                    itemView = LayoutInflater.from(PlannedDriveListActivity.this).inflate(C1283R.layout.planned_drive_separator_item, null);
                    break;
            }
            itemView.setLayoutParams(new LayoutParams(-1, -2));
            return new PlannedDriveListViewHolder(itemView);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position) == 0) {
                int eventTypeResource;
                AddressItem addressItem = (AddressItem) PlannedDriveListActivity.this.mDataSource.get(position);
                ((TextView) holder.itemView.findViewById(C1283R.id.lblTime)).setText(PlannedDriveListActivity.this.mHourFormat.format(Long.valueOf(addressItem.getStartTimeMillis())));
                ((TextView) holder.itemView.findViewById(C1283R.id.lblName)).setText(addressItem.getTitle());
                ((TextView) holder.itemView.findViewById(C1283R.id.lblDetails)).setText(addressItem.getSecondaryTitle());
                if (addressItem.hasLocation()) {
                    ((TextView) holder.itemView.findViewById(C1283R.id.lblGo)).setText(NativeManager.getInstance().getLanguageString(409));
                } else {
                    ((TextView) holder.itemView.findViewById(C1283R.id.lblGo)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_LIST_ITEM_SET_ADDRESS));
                }
                if (isWazePlannedDrive(addressItem)) {
                    eventTypeResource = C1283R.drawable.list_icon_later_item;
                } else if (isCalendar(addressItem)) {
                    eventTypeResource = C1283R.drawable.list_icon_calendar;
                } else {
                    eventTypeResource = C1283R.drawable.list_icon_calendar_fb;
                }
                ((ImageView) holder.itemView.findViewById(C1283R.id.imgEventType)).setImageResource(eventTypeResource);
                fetchEtaIfNecessary(addressItem, holder.itemView);
                setEventItemListeners(holder.itemView, addressItem);
            } else if (getItemViewType(position) == 6) {
                ((TextView) holder.itemView.findViewById(C1283R.id.lblDate)).setText(((HeaderItem) PlannedDriveListActivity.this.mDataSource.get(position)).title);
            }
        }

        private boolean isWazePlannedDrive(AddressItem addressItem) {
            return addressItem.getType() == 9 && addressItem.getCategory().intValue() == 9;
        }

        private boolean isCalendar(AddressItem addressItem) {
            return addressItem.getType() == 11;
        }

        private void fetchEtaIfNecessary(AddressItem addressItem, View eventView) {
            long startTimeFromNowInMillis = addressItem.getStartTimeMillis() - System.currentTimeMillis();
            ProgressAnimation loader = (ProgressAnimation) eventView.findViewById(C1283R.id.etaLoader);
            ((TextView) eventView.findViewById(C1283R.id.lblETA)).setText("");
            if (startTimeFromNowInMillis > 7200000 || !isFirstPlannedDriveWithLocation(addressItem)) {
                loader.stop();
                loader.setVisibility(8);
                return;
            }
            loader.setVisibility(0);
            loader.start();
            DriveToNativeManager.getInstance().requestPlannedDriveEta(addressItem.getMeetingId(), PlannedDriveListActivity.this);
        }

        private boolean isFirstPlannedDriveWithLocation(AddressItem addressItem) {
            for (AddressItem obj : PlannedDriveListActivity.this.mDataSource) {
                if ((obj instanceof AddressItem) && obj.hasLocation()) {
                    if (obj == addressItem) {
                        return true;
                    }
                    return false;
                }
            }
            return false;
        }

        private void setEventItemListeners(View view, final AddressItem addressItem) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    PlannedDriveListActivity.this.onItemClick(addressItem);
                }
            });
            view.findViewById(C1283R.id.imgMenu).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AddressItemOptionsUtil.showNavItemOptions(AppService.getActiveActivity(), addressItem, PlannedDriveListActivity.this);
                }
            });
        }

        public int getItemCount() {
            return PlannedDriveListActivity.this.mDataSource.size();
        }

        public int getItemViewType(int position) {
            if (PlannedDriveListActivity.this.mDataSource.get(position) instanceof AddressItem) {
                return 0;
            }
            if (PlannedDriveListActivity.this.mDataSource.get(position) instanceof HeaderItem) {
                return 6;
            }
            if (PlannedDriveListActivity.this.mDataSource.get(position) == PlannedDriveListActivity.EMPTY_SPACE_FLAG) {
                return 2;
            }
            if (PlannedDriveListActivity.this.mDataSource.get(position) == PlannedDriveListActivity.FACEBOOK_FLAG) {
                return 3;
            }
            if (PlannedDriveListActivity.this.mDataSource.get(position) == PlannedDriveListActivity.CALENDAR_FLAG) {
                return 4;
            }
            if (PlannedDriveListActivity.this.mDataSource.get(position) == PlannedDriveListActivity.TOP_HEADER_FLAG) {
                return 7;
            }
            if (PlannedDriveListActivity.this.mDataSource.get(position) == PlannedDriveListActivity.SEPARATOR_FLAG) {
                return 8;
            }
            if (PlannedDriveListActivity.this.mDataSource.get(position) == PlannedDriveListActivity.NO_ITEMS_FLAG) {
                return 5;
            }
            return -1;
        }
    }

    private class PlannedDriveListViewHolder extends ViewHolder {
        public PlannedDriveListViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.planned_drive_list_layout);
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.blue_status));
        }
        this.mRecyclerView = (RecyclerView) findViewById(C1283R.id.recycler);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.setAdapter(new PlannedDriveListAdapter());
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.titleBar);
        this.mTitleBar.setTitle(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_LIST_TITLE));
        this.mTitleBar.init((Activity) this, (int) DisplayStrings.DS_FUTURE_DRIVES_LIST_TITLE, true);
        this.mTitleBar.setCloseImageResource(C1283R.drawable.navbar_settings);
        this.mTitleBar.setOnClickCloseListener(new C23341());
        ImageButton planDriveButton = (ImageButton) findViewById(C1283R.id.btnPlanDrive);
        planDriveButton.setOnClickListener(new C23352());
        if (getIntent().getBooleanExtra("search", false)) {
            planDriveButton.performClick();
        }
    }

    protected void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        NativeManager.Post(new C23373());
    }

    private void processDataSource(AddressItem[] addressItems) {
        this.mDataSource.clear();
        if (addressItems == null || addressItems.length <= 0) {
            AddressItemView.totalPlannedDrives = 0;
            this.mDataSource.add(NO_ITEMS_FLAG);
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, AnalyticsEvents.ANALYTICS_EVENT_VALUE_NO_DRIVES);
        } else {
            AddressItemView.totalPlannedDrives = addressItems.length;
            this.mDataSource.add(TOP_HEADER_FLAG);
            Set<String> eventDays = new HashSet();
            this.mTodayDate = this.mDateFormat.format(Long.valueOf(System.currentTimeMillis()));
            this.mTomorrowDate = this.mDateFormat.format(Long.valueOf(System.currentTimeMillis() + 86400000));
            for (int i = 0; i < addressItems.length; i++) {
                String eventDate = getEventFormattedDate(addressItems[i]);
                if (!eventDays.contains(eventDate)) {
                    eventDays.add(eventDate);
                    this.mDataSource.add(new HeaderItem(eventDate));
                }
                this.mDataSource.add(addressItems[i]);
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, AnalyticsEvents.ANALYTICS_EVENT_VALUE_DRIVES);
        }
        if (this.mShowFacebook || this.mShowCalendar) {
            this.mDataSource.add(EMPTY_SPACE_FLAG);
            if (this.mShowCalendar) {
                this.mDataSource.add(CALENDAR_FLAG);
            }
            if (this.mShowCalendar && this.mShowCalendar) {
                this.mDataSource.add(SEPARATOR_FLAG);
            }
            if (this.mShowFacebook) {
                this.mDataSource.add(FACEBOOK_FLAG);
            }
        }
        this.mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private String getEventFormattedDate(AddressItem addressItem) {
        String eventDate = this.mDateFormat.format(Long.valueOf(addressItem.getStartTimeMillis()));
        if (eventDate.equals(this.mTodayDate)) {
            return NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TODAY_CAP);
        }
        if (eventDate.equals(this.mTomorrowDate)) {
            return NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TOMORROW);
        }
        return eventDate;
    }

    public void onInfoActionClicked(AddressItem addressItem) {
        Intent intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
        intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
        if (!(addressItem.VanueID == null || addressItem.VanueID.isEmpty())) {
            intent.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
        }
        AppService.getActiveActivity().startActivityForResult(intent, 1);
    }

    public void onDeleteActionClicked(AddressItem addressItem) {
        DriveToNativeManager.getInstance().removedPlannedDrive(addressItem.getMeetingId(), this);
    }

    public void onPlannedDriveRemoveSuccess() {
        reloadData();
        AppService.getMainActivity().getLayoutMgr().refreshRecentsNavigationList();
    }

    public void onPlannedDriveRemoveFailed() {
    }

    public void onPlannedDriveEtaResponse(String eventId, final int driveTime) {
        for (final Object item : this.mDataSource) {
            if ((item instanceof AddressItem) && ((AddressItem) item).getMeetingId().equals(eventId)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ViewHolder holder = PlannedDriveListActivity.this.mRecyclerView.findViewHolderForAdapterPosition(PlannedDriveListActivity.this.mDataSource.indexOf(item));
                        if (holder != null) {
                            ProgressAnimation loader = (ProgressAnimation) holder.itemView.findViewById(C1283R.id.etaLoader);
                            loader.stop();
                            loader.setVisibility(8);
                            String eta = "";
                            if (Utils.getHours(driveTime) > 0) {
                                if (Utils.getMinutes(driveTime) == 0) {
                                    eta = NativeManager.getInstance().getFormattedString(DisplayStrings.DS_FUTURE_DRIVES_LIST_ITEM_DRIVE_HOURS_PS, new Object[]{Integer.valueOf(Utils.getHours(driveTime))});
                                } else {
                                    eta = NativeManager.getInstance().getFormattedString(DisplayStrings.DS_FUTURE_DRIVES_LIST_ITEM_DRIVE_HOURS_MINUTES, new Object[]{Integer.valueOf(Utils.getHours(driveTime)), Integer.valueOf(Utils.getMinutes(driveTime))});
                                }
                            } else if (driveTime >= 0) {
                                eta = NativeManager.getInstance().getFormattedString(DisplayStrings.DS_FUTURE_DRIVES_LIST_ITEM_DRIVE_MINUTES_PD, new Object[]{Integer.valueOf(Utils.getMinutes(driveTime))});
                            }
                            ((TextView) holder.itemView.findViewById(C1283R.id.lblETA)).setText(eta);
                        }
                    }
                });
                return;
            }
        }
    }

    private void onItemClick(AddressItem addressItem) {
        if (addressItem.getType() == 11) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_EVENT_VALUE_CAL_DRIVE);
        } else if (addressItem.getType() == 9 && addressItem.getCategory().intValue() == 9) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FB_DRIVE);
        } else if (addressItem.getType() == 9) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLANNED_DRIVE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "PLAN_DRIVE");
        }
        Intent intent;
        if (addressItem.getType() == 11) {
            if (addressItem.getIsValidate().booleanValue() && addressItem.hasLocation()) {
                DriveToNativeManager.getInstance().navigate(addressItem, new C23395());
                return;
            }
            intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
            AppService.getActiveActivity().startActivityForResult(intent, MainActivity.CALENDAR_REQUEST_CODE);
        } else if ((addressItem.getIsValidate().booleanValue() || addressItem.getCategory().intValue() == 9) && addressItem.hasLocation()) {
            DriveToNativeManager.getInstance().navigate(addressItem, new C23406());
        } else {
            intent = new Intent(this, FacebookEventActivity.class);
            addressItem.setStartTime(String.format("%s %s", new Object[]{getEventFormattedDate(addressItem), this.mHourFormat.format(Long.valueOf(addressItem.getStartTimeMillis()))}));
            intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
            AppService.getActiveActivity().startActivityForResult(intent, 1);
        }
    }

    public void onAddressItemOptionClicked(AddressItem addressItem, int option) {
        switch (option) {
            case 2:
                onChangeLocation(addressItem);
                return;
            case 3:
                Intent intent = new Intent(AppService.getActiveActivity(), ParkingSearchResultsActivity.class);
                intent.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, addressItem.getVenueDataForParking());
                intent.putExtra(PublicMacros.PREVIEW_PARKING_CONTEXT, "PLANNED_DRIVE");
                AppService.getActiveActivity().startActivityForResult(intent, 0);
                return;
            case 4:
                onCreatePlannedDrive(addressItem);
                return;
            case 5:
                openCalendarSettings();
                return;
            case 6:
                onInfoActionClicked(addressItem);
                return;
            case 7:
                ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareSelection, null, addressItem, null);
                return;
            case 8:
                onOpenRoutes(addressItem);
                return;
            case 11:
                verifyEventAddress(addressItem);
                return;
            case 12:
                onDeleteActionClicked(addressItem);
                return;
            case 18:
                onEditPlannedDrive(addressItem);
                return;
            default:
                return;
        }
    }

    private void verifyEventAddress(AddressItem addressItem) {
        Intent intent;
        if (addressItem.getType() == 9) {
            intent = new Intent(this, FacebookEventActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
            AppService.getActiveActivity().startActivityForResult(intent, 1);
        } else if (addressItem.getType() == 11) {
            intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
            AppService.getActiveActivity().startActivityForResult(intent, MainActivity.CALENDAR_REQUEST_CODE);
        }
    }

    private void onOpenRoutes(AddressItem addressItem) {
        NativeManager.getInstance().setShowRoutesWhenNavigationStarts(true);
        DriveToNativeManager.getInstance().navigate(addressItem, null);
        finish();
    }

    private void onCreatePlannedDrive(AddressItem addressItem) {
        PlannedDriveActivity.setNavigationAddressItem(addressItem);
        AppService.getActiveActivity().startActivity(new Intent(this, PlannedDriveActivity.class));
    }

    private void onEditPlannedDrive(AddressItem addressItem) {
        Intent intent = new Intent(this, PlannedDriveActivity.class);
        intent.putExtra(PlannedDriveActivity.EDIT_EXTRA, addressItem);
        AppService.getActiveActivity().startActivity(intent);
    }

    private void onChangeLocation(AddressItem addressItem) {
        Intent intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
        intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
        intent.putExtra(AddressPreviewActivity.OPEN_SET_LOCATION, true);
        AppService.getActiveActivity().startActivity(intent);
    }

    private void openCalendarSettings() {
        AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), SettingsCalendarActivity.class), 1);
    }

    public void onFacebookTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
        this.mFacebookSynchedSuccessfully = true;
        reloadData();
    }
}
