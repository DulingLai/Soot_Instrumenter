package com.waze.navigate;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.menus.AddressItemOptionsUtil;
import com.waze.menus.AddressItemView;
import com.waze.menus.AddressItemView.AddressItemViewListener;
import com.waze.menus.SideMenuAutoCompleteRecycler.Mode;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.planned_drive.PlannedDriveActivity;
import com.waze.planned_drive.PlannedDriveAlternateFromActivity;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.user.PersonBase;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public final class FavoritesActivity extends AddressItemListActivity implements AddressItemViewListener, DriveToGetAddressItemArrayCallback, DriveToNavigateCallback {
    private ImageView mBackButton;
    private View mDraggingView;
    private TextView mEditDoneButton;
    private AddressItem[] mFavorites;
    private RecyclerView mFavoritesRecycler;
    private AddressItemView mFloatingAddressItemView;
    private PersonBase[] mFriends = null;
    private int mInitialItemId;
    private boolean mIsEditing;
    private ItemTouchHelper mItemTouchHelper;
    private Mode mMode = Mode.Normal;

    class C21181 implements OnClickListener {
        C21181() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_MENU_CLICKED).addParam("ACTION", "BACK").send();
            FavoritesActivity.this.finish();
        }
    }

    class C21192 implements OnClickListener {
        C21192() throws  {
        }

        public void onClick(View v) throws  {
            FavoritesActivity.this.toggleEditMode();
        }
    }

    class C21214 extends Callback {
        C21214() throws  {
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) throws  {
            return Callback.makeFlag(2, FavoritesActivity.this.mIsEditing ? (byte) 3 : (byte) 0);
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder $r2, ViewHolder $r3) throws  {
            int $i1 = $r2.getAdapterPosition();
            int $i2 = $r3.getAdapterPosition();
            AddressItem $r4 = FavoritesActivity.this.mFavorites[$i1 + 1];
            FavoritesActivity.this.mFavorites[$i1 + 1] = FavoritesActivity.this.mFavorites[$i2 + 1];
            FavoritesActivity.this.mFavorites[$i2 + 1] = $r4;
            FavoritesActivity.this.mFavoritesRecycler.getAdapter().notifyItemMoved($i2, $i1);
            return true;
        }

        public void onSwiped(ViewHolder viewHolder, int direction) throws  {
        }

        public void onSelectedChanged(ViewHolder $r1, int $i0) throws  {
            if (VERSION.SDK_INT >= 21) {
                if ($i0 == 2) {
                    FavoritesActivity.this.mDraggingView = $r1.itemView;
                    ViewPropertyAnimatorHelper.initAnimation(FavoritesActivity.this.mDraggingView).translationZ((float) PixelMeasure.dp(16));
                } else if ($i0 == 0 && FavoritesActivity.this.mDraggingView != null) {
                    ViewPropertyAnimatorHelper.initAnimation(FavoritesActivity.this.mDraggingView).translationZ(0.0f);
                }
            }
            if ($i0 == 2) {
                FavoritesActivity.this.mInitialItemId = FavoritesActivity.this.mFavorites[$r1.getAdapterPosition() + 1].getIntId();
            } else if ($i0 == 0 && FavoritesActivity.this.mDraggingView != null) {
                int $i1 = FavoritesActivity.this.getIndexOfItem(((AddressItemView) FavoritesActivity.this.mDraggingView).getAddressItem());
                if ($i1 > 0) {
                    int $i2 = FavoritesActivity.this.mFavorites[$i1 - 1].getIntId();
                    DriveToNativeManager.getInstance().moveFavoriteAddressItem(FavoritesActivity.this.mInitialItemId, $i2);
                    if ($i2 == -1) {
                        $i1 = FavoritesActivity.this.mFavorites[$i1 + 1].getIntId();
                        DriveToNativeManager.getInstance().moveFavoriteAddressItem($i1, FavoritesActivity.this.mInitialItemId);
                    }
                }
            }
            super.onSelectedChanged($r1, $i0);
        }
    }

    class C21225 implements Runnable {
        C21225() throws  {
        }

        public void run() throws  {
            FavoritesActivity.this.mFavoritesRecycler.getAdapter().notifyDataSetChanged();
        }
    }

    class C21236 implements Runnable {
        C21236() throws  {
        }

        public void run() throws  {
            DriveToNativeManager.getInstance().getFavorites(FavoritesActivity.this, true);
        }
    }

    class C21247 implements Runnable {
        C21247() throws  {
        }

        public void run() throws  {
            DriveToNativeManager.getInstance().getFavorites(FavoritesActivity.this, true);
        }
    }

    private class AddressItemViewHolder extends ViewHolder {
        private AddressItemView mAddressItemView;

        public AddressItemViewHolder(AddressItemView $r2) throws  {
            super($r2);
            this.mAddressItemView = $r2;
            this.mAddressItemView.setListener(FavoritesActivity.this);
            this.mAddressItemView.getMainContentView().setOnClickListener(new OnClickListener(FavoritesActivity.this) {
                public void onClick(View v) throws  {
                    FavoritesActivity.this.addressItemClicked(AddressItemViewHolder.this.mAddressItemView.getAddressItem());
                }
            });
        }

        public void bindView(AddressItem $r1) throws  {
            this.mAddressItemView.setAddressItem($r1, FavoritesActivity.this.mIsEditing);
        }
    }

    private class FavoritesAdapter extends Adapter<ViewHolder> {
        private FavoritesAdapter() throws  {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) throws  {
            AddressItemView $r2 = new AddressItemView(FavoritesActivity.this, true);
            $r2.setFavorites(true);
            return new AddressItemViewHolder($r2);
        }

        public void onBindViewHolder(ViewHolder $r1, int $i0) throws  {
            if ($r1 instanceof AddressItemViewHolder) {
                ((AddressItemViewHolder) $r1).bindView(FavoritesActivity.this.mFavorites[(FavoritesActivity.this.mIsEditing ? (byte) 1 : (byte) 0) + $i0]);
            }
        }

        public int getItemCount() throws  {
            int $i0 = 0;
            if (FavoritesActivity.this.mFavorites == null) {
                return 0;
            }
            int $i1 = FavoritesActivity.this.mFavorites.length;
            if (FavoritesActivity.this.mIsEditing) {
                $i0 = 1;
            }
            return $i1 - $i0;
        }
    }

    protected String getClickEventId() throws  {
        return AnalyticsEvents.ANALYTICS_EVENT_FAVOURITE_CLICK;
    }

    protected String getParkingContext() throws  {
        return "FAVORITES";
    }

    public void onCreate(Bundle $r1) throws  {
        String $r3;
        super.onCreate($r1);
        setContentView(C1283R.layout.favorites_activity_layout);
        if ($r1 != null) {
            this.mIsEditing = $r1.getBoolean("edit_mode", false);
        }
        if (getIntent() != null && getIntent().hasExtra(PlannedDriveAlternateFromActivity.MODE)) {
            if (getIntent().getExtras().getString(PlannedDriveAlternateFromActivity.MODE, "").equals("planned_drive_destination")) {
                this.mMode = Mode.PlannedDriveSelectDestination;
            } else {
                if (getIntent().getExtras().getString(PlannedDriveAlternateFromActivity.MODE, "").equals("planned_drive_origin")) {
                    this.mMode = Mode.PlannedDriveSelectOrigin;
                }
            }
        }
        this.mBackButton = (ImageView) findViewById(C1283R.id.btnBack);
        this.mEditDoneButton = (TextView) findViewById(C1283R.id.btnEditDone);
        this.mFavoritesRecycler = (RecyclerView) findViewById(C1283R.id.favoritesRecycler);
        this.mFloatingAddressItemView = (AddressItemView) findViewById(C1283R.id.floatingAddressItemView);
        this.mFloatingAddressItemView.setIgnoreRightPadding(true);
        Serializable $r10 = null;
        Intent $r2 = getIntent();
        if ($r2 != null && $r2.hasExtra(PublicMacros.FriendUserDataList)) {
            $r10 = $r2.getExtras().getSerializable(PublicMacros.FriendUserDataList);
        }
        if ($r10 != null && ($r10 instanceof ArrayList)) {
            ArrayList $r11 = (ArrayList) $r10;
            PersonBase[] personBaseArr = new PersonBase[$r11.size()];
            PersonBase[] $r12 = personBaseArr;
            this.mFriends = personBaseArr;
            int $i1 = 0;
            Iterator $r13 = $r11.iterator();
            while ($r13.hasNext()) {
                Object $r14 = $r13.next();
                if ($r14 instanceof PersonBase) {
                    this.mFriends[$i1] = (PersonBase) $r14;
                    $i1++;
                }
            }
        }
        if (this.mFavoritesRecycler.isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        this.mBackButton.setOnClickListener(new C21181());
        NativeManager $r18 = NativeManager.getInstance();
        TextView $r7 = this.mEditDoneButton;
        if (this.mIsEditing) {
            $r3 = $r18.getLanguageString(375);
        } else {
            $r3 = $r18.getLanguageString(381);
        }
        $r7.setText($r3);
        ((TextView) findViewById(C1283R.id.lblFavoritesTitle)).setText($r18.getLanguageString((int) DisplayStrings.DS_FAVORITES_ACTIVITY_TITLE));
        this.mEditDoneButton.setOnClickListener(new C21192());
        this.mFavoritesRecycler.setLayoutManager(new LinearLayoutManager(this) {
            public int scrollVerticallyBy(int $i0, Recycler $r1, State $r2) throws  {
                FavoritesActivity.this.onButtonsOpened(null);
                return super.scrollVerticallyBy($i0, $r1, $r2);
            }
        });
        this.mFavoritesRecycler.setAdapter(new FavoritesAdapter());
        this.mItemTouchHelper = new ItemTouchHelper(new C21214());
        this.mItemTouchHelper.attachToRecyclerView(this.mFavoritesRecycler);
        DriveToNativeManager.getInstance().getFavorites(this, true);
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.BlueDeepLight));
        }
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putBoolean("edit_mode", this.mIsEditing);
    }

    private int getIndexOfItem(AddressItem $r1) throws  {
        if (this.mFavorites != null) {
            for (int $i0 = 0; $i0 < this.mFavorites.length; $i0++) {
                if (this.mFavorites[$i0] == $r1) {
                    return $i0;
                }
            }
        }
        return -1;
    }

    private void toggleEditMode() throws  {
        this.mIsEditing = !this.mIsEditing;
        if (this.mIsEditing) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_MENU_CLICKED).addParam("ACTION", "EDIT").send();
        }
        adjustEditMode();
    }

    private void adjustEditMode() throws  {
        String $r5;
        if (this.mIsEditing) {
            this.mFavoritesRecycler.getAdapter().notifyItemRemoved(0);
        } else {
            this.mFavoritesRecycler.getAdapter().notifyItemInserted(0);
        }
        NativeManager $r3 = NativeManager.getInstance();
        TextView $r4 = this.mEditDoneButton;
        if (this.mIsEditing) {
            $r5 = $r3.getLanguageString(375);
        } else {
            $r5 = $r3.getLanguageString(381);
        }
        $r4.setText($r5);
        for (int $i0 = 0; $i0 < this.mFavoritesRecycler.getChildCount(); $i0++) {
            if (this.mFavoritesRecycler.getChildAt($i0) instanceof AddressItemView) {
                ((AddressItemView) this.mFavoritesRecycler.getChildAt($i0)).setIsEditing(this.mIsEditing);
            }
        }
        postDelayed(new C21225(), 500);
    }

    public void getAddressItemArrayCallback(AddressItem[] $r1) throws  {
        this.mFavorites = $r1;
        if (this.mFavoritesRecycler.getAdapter() != null) {
            this.mFavoritesRecycler.getAdapter().notifyDataSetChanged();
        }
        this.mEditDoneButton.setVisibility(this.mFavorites.length > 2 ? (byte) 0 : (byte) 8);
    }

    public void moreActionClicked(View $r1) throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FAVOURITE_CLICK, "ACTION", "INFO");
        AddressItem $r4 = (AddressItem) $r1.getTag(C1283R.id.addressItem);
        Intent $r2 = new Intent(this, AddressPreviewActivity.class);
        $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r4);
        if (!($r4 == null || $r4.VanueID == null || $r4.VanueID.isEmpty())) {
            $r2.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
        }
        $r2.putExtra(PublicMacros.ACTION_BUTTON, 3);
        startActivityForResult($r2, 1);
    }

    public void addressItemClicked(AddressItem $r1) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_MENU_CLICKED).addParam("ACTION", "SELECT").send();
        if (this.mFriends != null) {
            ShareUtility.OpenShareActivityOrLogin(this, ShareType.ShareType_ShareLocation, null, $r1, this.mFriends);
            return;
        }
        Integer $r5 = Integer.valueOf($r1.getType());
        Intent $r6;
        if ($r5 != null && ($r5.intValue() == 2 || $r5.intValue() == 4)) {
            $r6 = new Intent(this, AddHomeWorkActivity.class);
            $r6.putExtra(PublicMacros.ADDRESS_TYPE, $r5);
            startActivityForResult($r6, 1);
        } else if ($r5 != null && $r5.intValue() == 6) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_MENU_CLICKED).addParam("ACTION", "NEW").send();
            $r6 = new Intent(this, AddFavoriteActivity.class);
            $r6.putExtra(PublicMacros.ADDRESS_TYPE, $r5);
            startActivityForResult($r6, 1);
        } else if (this.mMode == Mode.Normal) {
            DriveToNativeManager $r9 = DriveToNativeManager.getInstance();
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FAVOURITE_CLICK, "ACTION", "NAVIGATE");
            if ($r5 != null && $r5.intValue() == 1) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "HOME");
            } else if ($r5 != null && $r5.intValue() == 3) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "WORK");
            } else if ($r5 != null && $r5.intValue() == 5) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_OTHER_FAV);
            }
            $r1.setCategory(Integer.valueOf(2));
            $r9.navigate($r1, this, false, true);
        } else if (this.mMode == Mode.PlannedDriveSelectDestination) {
            PlannedDriveActivity.setNavigationAddressItem($r1);
            finish();
        } else if (this.mMode == Mode.PlannedDriveSelectOrigin) {
            PlannedDriveActivity.setFromAddressItem($r1);
            finish();
        }
    }

    public void onShowOptionsClick(AddressItem $r1) throws  {
        AddressItemOptionsUtil.showNavItemOptions(AppService.getActiveActivity(), $r1, this);
    }

    public void onButtonsOpened(AddressItemView $r1) throws  {
        for (int $i0 = 0; $i0 < this.mFavoritesRecycler.getChildCount(); $i0++) {
            View $r3 = this.mFavoritesRecycler.getChildAt($i0);
            if ($r3 != $r1 && ($r3 instanceof AddressItemView)) {
                ((AddressItemView) $r3).closeSideButtons();
            }
        }
    }

    public void onGrabStart(AddressItem $r1) throws  {
        int $i0 = getIndexOfItem($r1);
        if ($i0 >= 0) {
            this.mItemTouchHelper.startDrag(this.mFavoritesRecycler.findViewHolderForAdapterPosition($i0 - 1));
        }
    }

    public void onRefreshed(AddressItem addressItem) throws  {
    }

    protected void onRenameFavorite(AddressItem $r1) throws  {
        NameFavoriteView.showNameFavorite(AppService.getActiveActivity(), $r1, new C21236()).setRenameMode(true);
    }

    protected void onResume() throws  {
        super.onResume();
        DriveToNativeManager.getInstance().getFavorites(this, true);
    }

    public void onDeleteActionClicked(AddressItem $r1) throws  {
        super.onDeleteActionClicked($r1);
        postDelayed(new C21247(), 200);
    }

    public void navigateCallback(int $i0) throws  {
        Log.d(Logger.TAG, "navigateCallback:rc=" + $i0);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
    }

    protected void onItemDeleted() throws  {
        DriveToNativeManager.getInstance().getFavorites(this, true);
    }

    public void onBackPressed() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_MENU_CLICKED).addParam("ACTION", "BACK").send();
        super.onBackPressed();
    }
}
