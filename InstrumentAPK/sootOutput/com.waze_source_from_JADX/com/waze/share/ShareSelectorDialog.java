package com.waze.share;

import android.content.Intent;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.FavoritesActivity;
import com.waze.navigate.HistoryActivity;
import com.waze.navigate.PublicMacros;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.user.PersonBase;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import java.util.ArrayList;
import java.util.Arrays;

public class ShareSelectorDialog extends BottomSheet {
    private static final int RQ_CODE_SET_HOME = 1001;
    private static final int RQ_CODE_SET_WORK = 1002;
    private final ActivityBase mAb;
    private final ArrayList<DialogOption> mOptions = new ArrayList();
    private final PersonBase[] mTargets = new PersonBase[1];

    class C28441 implements Runnable {
        C28441() {
        }

        public void run() {
            ShareUtility.OpenShareActivity(ShareSelectorDialog.this.mAb, ShareType.ShareType_ShareDrive, null, null, ShareSelectorDialog.this.mTargets);
        }
    }

    class C28462 implements Runnable {

        class C28451 implements DriveToGetAddressItemArrayCallback {
            C28451() {
            }

            public void getAddressItemArrayCallback(AddressItem[] ai) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "HOME");
                if (ai != null) {
                    ShareUtility.OpenShareActivity(ShareSelectorDialog.this.mAb, ShareType.ShareType_ShareSelection, null, ai[0], ShareSelectorDialog.this.mTargets);
                    return;
                }
                Intent intent = new Intent(ShareSelectorDialog.this.mAb, AddHomeWorkActivity.class);
                intent.putExtra(PublicMacros.ADDRESS_TYPE, 2);
                ShareSelectorDialog.this.mAb.startActivityForResult(intent, 1001);
            }
        }

        C28462() {
        }

        public void run() {
            DriveToNativeManager.getInstance().getHome(new C28451());
        }
    }

    class C28483 implements Runnable {

        class C28471 implements DriveToGetAddressItemArrayCallback {
            C28471() {
            }

            public void getAddressItemArrayCallback(AddressItem[] ai) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "WORK");
                if (ai != null) {
                    ShareUtility.OpenShareActivity(ShareSelectorDialog.this.mAb, ShareType.ShareType_ShareSelection, null, ai[0], ShareSelectorDialog.this.mTargets);
                    return;
                }
                Intent intent = new Intent(ShareSelectorDialog.this.mAb, AddHomeWorkActivity.class);
                intent.putExtra(PublicMacros.ADDRESS_TYPE, 4);
                ShareSelectorDialog.this.mAb.startActivityForResult(intent, 1002);
            }
        }

        C28483() {
        }

        public void run() {
            DriveToNativeManager.getInstance().getWork(new C28471());
        }
    }

    class C28494 implements Runnable {
        C28494() {
        }

        public void run() {
            ShareUtility.OpenShareActivity(ShareSelectorDialog.this.mAb, ShareType.ShareType_ShareLocation, null, null, ShareSelectorDialog.this.mTargets);
        }
    }

    class C28505 implements Runnable {
        C28505() {
        }

        public void run() {
            ShareUtility.OpenShareActivity(ShareSelectorDialog.this.mAb, ShareType.ShareType_ShareDestination, null, null, ShareSelectorDialog.this.mTargets);
        }
    }

    class C28516 implements Runnable {
        C28516() {
        }

        public void run() {
            Intent intent = new Intent(ShareSelectorDialog.this.getContext(), FavoritesActivity.class);
            intent.putExtra(PublicMacros.FriendUserDataList, new ArrayList(Arrays.asList(ShareSelectorDialog.this.mTargets)));
            ShareSelectorDialog.this.mAb.startActivityForResult(intent, 0);
        }
    }

    class C28527 implements Runnable {
        C28527() {
        }

        public void run() {
            Intent intent = new Intent(ShareSelectorDialog.this.getContext(), HistoryActivity.class);
            intent.putExtra(PublicMacros.FriendUserDataList, new ArrayList(Arrays.asList(ShareSelectorDialog.this.mTargets)));
            ShareSelectorDialog.this.mAb.startActivityForResult(intent, 0);
        }
    }

    class C28538 implements Adapter {
        C28538() {
        }

        public int getCount() {
            return ShareSelectorDialog.this.mOptions.size();
        }

        public void onConfigItem(int position, ItemDetails item) {
            ((DialogOption) ShareSelectorDialog.this.mOptions.get(position)).setItem(item);
        }

        public void onClick(int position) {
            ((DialogOption) ShareSelectorDialog.this.mOptions.get(position)).run();
        }
    }

    private class DialogOption {
        private Runnable mAction;
        private int mDsTitle;
        private int mImageResource;

        public DialogOption(int dsTitle, int imageResource, Runnable action) {
            this.mDsTitle = dsTitle;
            this.mImageResource = imageResource;
            this.mAction = action;
        }

        void setItem(ItemDetails item) {
            item.setItem(this.mDsTitle, this.mImageResource);
        }

        void run() {
            this.mAction.run();
        }
    }

    public ShareSelectorDialog(ActivityBase ab, PersonBase toWhom) {
        super(ab, DisplayStrings.DS_FRIEND_SHARE_TITLE, Mode.GRID_LARGE);
        this.mTargets[0] = toWhom;
        this.mAb = ab;
        if (NativeManager.getInstance().isNavigatingNTV()) {
            this.mOptions.add(new DialogOption(DisplayStrings.DS_FRIEND_SHARE_ETA_TITLE, C1283R.drawable.share_eta_icon, new C28441()));
        }
        this.mOptions.add(new DialogOption(DisplayStrings.DS_FRIEND_SHARE_HOME, C1283R.drawable.share_home_icon, new C28462()));
        this.mOptions.add(new DialogOption(DisplayStrings.DS_FRIEND_SHARE_WORK, C1283R.drawable.share_work_icon, new C28483()));
        this.mOptions.add(new DialogOption(DisplayStrings.DS_FRIEND_SHARE_CURRENT, C1283R.drawable.share_current_icon, new C28494()));
        if (NativeManager.getInstance().isNavigatingNTV()) {
            this.mOptions.add(new DialogOption(DisplayStrings.DS_FRIEND_SHARE_DESTINATION, C1283R.drawable.share_destination_icon, new C28505()));
        }
        this.mOptions.add(new DialogOption(DisplayStrings.DS_FRIEND_SHARE_FAVORITES, C1283R.drawable.share_favorite_icon, new C28516()));
        this.mOptions.add(new DialogOption(DisplayStrings.DS_FRIEND_SHARE_HISTORY, C1283R.drawable.share_history_icon, new C28527()));
        super.setAdapter(new C28538());
    }
}
