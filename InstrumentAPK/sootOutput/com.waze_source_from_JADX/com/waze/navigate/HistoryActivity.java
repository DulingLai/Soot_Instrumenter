package com.waze.navigate;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.menus.AddressItemOptionsUtil;
import com.waze.menus.AddressItemView;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.planned_drive.PlannedDriveActivity;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.user.PersonBase;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public final class HistoryActivity extends AddressItemListActivity implements DriveToGetAddressItemArrayCallback, DriveToNavigateCallback {
    private PersonBase[] mFriends;
    private DriveToNativeManager nm;

    class C21261 implements OnItemClickListener {
        C21261() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View $r2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int arg2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long arg3) throws  {
            HistoryActivity.this.addressItemClicked($r2);
        }
    }

    private class HistoryListAdapter extends BaseAdapter {
        private AddressItem[] historyItems;

        class C21271 implements OnClickListener {
            C21271() throws  {
            }

            public void onClick(View $r1) throws  {
                HistoryActivity.this.addressItemClicked($r1);
            }
        }

        class C21282 implements OnClickListener {
            C21282() throws  {
            }

            public void onClick(View $r1) throws  {
                HistoryActivity.this.moreActionClicked($r1);
            }
        }

        public HistoryListAdapter(Context context, int textViewResourceId, AddressItem[] $r3) throws  {
            this.historyItems = $r3;
        }

        public int getCount() throws  {
            return this.historyItems.length;
        }

        public Object getItem(int $i0) throws  {
            return this.historyItems[$i0];
        }

        public long getItemId(int $i0) throws  {
            return (long) $i0;
        }

        public View getView(int $i0, View $r2, ViewGroup parent) throws  {
            AddressItemView $r5;
            AddressItem $r4 = (AddressItem) getItem($i0);
            $r4.setType(8);
            if ($r2 == null) {
                $r5 = new AddressItemView(HistoryActivity.this, true);
                $r2 = $r5;
            } else {
                $r5 = (AddressItemView) $r2;
            }
            $r5.setAddressItem($r4);
            $r5.getMainContentView().setTag(C1283R.id.addressItem, $r4);
            $r5.getMainContentView().setOnClickListener(new C21271());
            $r5.getInfoButtonIfVisible().setTag(C1283R.id.addressItem, $r4);
            $r5.getInfoButtonIfVisible().setOnClickListener(new C21282());
            return $r2;
        }
    }

    protected String getClickEventId() throws  {
        return AnalyticsEvents.ANALYTICS_EVENT_HISTORY_CLICK;
    }

    protected String getParkingContext() throws  {
        return "HISTORY";
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.history);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, 14);
        ((ListView) findViewById(C1283R.id.historyList)).setOnItemClickListener(new C21261());
        if (getIntent().getExtras() != null) {
            Serializable $r7 = getIntent().getExtras().getSerializable(PublicMacros.FriendUserDataList);
            if ($r7 != null && ($r7 instanceof ArrayList)) {
                ArrayList $r8 = (ArrayList) $r7;
                PersonBase[] personBaseArr = new PersonBase[$r8.size()];
                PersonBase[] $r9 = personBaseArr;
                this.mFriends = personBaseArr;
                int $i1 = 0;
                Iterator $r10 = $r8.iterator();
                while ($r10.hasNext()) {
                    Object $r11 = $r10.next();
                    if ($r11 instanceof PersonBase) {
                        this.mFriends[$i1] = (PersonBase) $r11;
                        $i1++;
                    }
                }
            }
        }
        this.nm = DriveToNativeManager.getInstance();
        DriveToNativeManager driveToNativeManager = this.nm;
        DriveToNativeManager $r13 = driveToNativeManager;
        driveToNativeManager.getTopTenFavorites(this);
    }

    public void getAddressItemArrayCallback(AddressItem[] $r1) throws  {
        Log.d(Logger.TAG, "got history len=" + $r1.length);
        ArrayList $r3 = new ArrayList();
        for (AddressItem $r2 : $r1) {
            if ($r2.getType() == 8) {
                $r3.add($r2);
            }
        }
        $r1 = new AddressItem[$r3.size()];
        $r3.toArray($r1);
        ((ListView) findViewById(C1283R.id.historyList)).setAdapter(new HistoryListAdapter(this, C1283R.layout.address_item, $r1));
    }

    public void moreActionClicked(View $r1) throws  {
        AddressItem $r3 = (AddressItem) $r1.getTag(C1283R.id.addressItem);
        $r3.setType(8);
        AddressItemOptionsUtil.showNavItemOptions(this, $r3, this);
    }

    public void addressItemClicked(View $r1) throws  {
        AddressItem $r3 = (AddressItem) $r1.getTag(C1283R.id.addressItem);
        if (this.mFriends != null) {
            ShareUtility.OpenShareActivityOrLogin(this, ShareType.ShareType_ShareLocation, null, $r3, this.mFriends);
        } else if ($r3.getType() != 8 || $r3.getMeetingId() == null) {
            boolean z;
            if ($r3.getCategory().intValue() != 1) {
                z = true;
            } else {
                z = false;
            }
            this.nm.navigate($r3, this, false, z);
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_TYPE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "HISTORY").send();
        } else {
            PlannedDriveActivity.setNavigationAddressItem($r3);
            NativeManager.getInstance().AutoCompletePlaceClicked($r3.getId(), $r3.VanueID, null, null, $r3.getMeetingId(), true, null, true, 0, null, null);
            AppService.getMainActivity().getLayoutMgr().closeSwipeableLayout();
            finish();
        }
    }

    public void navigateCallback(int $i0) throws  {
        Log.d(Logger.TAG, "navigateCallback:rc=" + $i0);
        if ($i0 == 0) {
            setResult(-1);
            finish();
        }
    }

    protected void onItemDeleted() throws  {
        this.nm.getTopTenFavorites(this);
    }
}
