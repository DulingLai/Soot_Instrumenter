package com.waze.mywaze;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyStoreListItemView.MyStoreListItemListener;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyStoresActivity extends ActivityBase implements MyStoreListItemListener {
    public static final String EXTRA_SOURCE = "source";
    public static final int SOURCE_DEEP_LINK = 0;
    public static final int SOURCE_MENU = 1;
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<MyStoreModel> mMyStoreModels = new ArrayList();
    private RecyclerView mRecycler;
    private LinearLayout mZeroStateContainer;

    class C19811 implements Runnable {
        C19811() throws  {
        }

        public void run() throws  {
            final MyStoreModel[] $r3 = MyWazeNativeManager.getInstance().getAllUserStoresFromCacheNTV();
            if ($r3 != null) {
            }
            MyStoresActivity.this.runOnUiThread(new Runnable() {
                public void run() throws  {
                    MyStoresActivity.this.mMyStoreModels.clear();
                    if ($r3 != null) {
                        Collections.addAll(MyStoresActivity.this.mMyStoreModels, $r3);
                    }
                    if (MyStoresActivity.this.mMyStoreModels.size() > 0) {
                        MyStoresActivity.this.mZeroStateContainer.setVisibility(8);
                        MyStoresActivity.this.mRecycler.setVisibility(0);
                    } else {
                        MyStoresActivity.this.mZeroStateContainer.setVisibility(0);
                        MyStoresActivity.this.mRecycler.setVisibility(8);
                    }
                    MyStoresActivity.this.mRecycler.getAdapter().notifyDataSetChanged();
                }
            });
        }
    }

    class C19822 implements SimpleBottomSheetListener {
        C19822() throws  {
        }

        public void onComplete(SimpleBottomSheetValue value) throws  {
        }
    }

    private class MyStoresAdapter extends Adapter<ViewHolder> {
        private MyStoresAdapter() throws  {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int $i0) throws  {
            if ($i0 == 0) {
                View $r6 = LayoutInflater.from(MyStoresActivity.this).inflate(C1283R.layout.my_stores_header, null);
                TextView $r9 = (TextView) $r6.findViewById(C1283R.id.lblFavoriteStoresTitle);
                ((TextView) $r6.findViewById(C1283R.id.lblMyStoresHeader)).setText(DisplayStrings.displayString(DisplayStrings.DS_MY_STORES_SETTINGS_INFO));
                $r9.setText(DisplayStrings.displayString(DisplayStrings.DS_MY_STORES_SETTINGS_HEADER_TITLE));
                $r6.setLayoutParams(new LayoutParams(-1, -2));
                return new StoresHeaderViewHolder($r6);
            } else if ($i0 != 1) {
                return null;
            } else {
                MyStoreListItemView $r2 = r0;
                MyStoreListItemView myStoreListItemView = new MyStoreListItemView(MyStoresActivity.this);
                $r2.setLayoutParams(new LayoutParams(-1, PixelMeasure.dimension(C1283R.dimen.sideMenuAddressItemHeight)));
                return new StoresViewHolder($r2);
            }
        }

        public void onBindViewHolder(ViewHolder $r1, int $i0) throws  {
            boolean $z0 = true;
            if (getItemViewType($i0) == 1) {
                MyStoreModel $r5 = (MyStoreModel) MyStoresActivity.this.mMyStoreModels.get($i0 - 1);
                StoresViewHolder $r6 = (StoresViewHolder) $r1;
                if ($i0 != getItemCount() - 1) {
                    $z0 = false;
                }
                $r6.setModel($r5, $z0);
            }
        }

        public int getItemCount() throws  {
            return MyStoresActivity.this.mMyStoreModels.size() + 1;
        }

        public int getItemViewType(int $i0) throws  {
            return $i0 == 0 ? 0 : 1;
        }
    }

    private class StoresHeaderViewHolder extends ViewHolder {
        public StoresHeaderViewHolder(View $r2) throws  {
            super($r2);
        }
    }

    private class StoresViewHolder extends ViewHolder {
        private MyStoreListItemView mItemView;

        public StoresViewHolder(MyStoreListItemView $r2) throws  {
            super($r2);
            this.mItemView = $r2;
            this.mItemView.setListener(MyStoresActivity.this);
        }

        public void setModel(final MyStoreModel $r1, boolean $z0) throws  {
            this.mItemView.setModel($r1);
            this.mItemView.setSeparatorVisibility(!$z0);
            this.mItemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    MyStoresActivity.this.startSearchForBrand($r1.getId());
                }
            });
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.my_stores_layout);
        int $i0 = getIntent().getIntExtra("source", -1);
        AnalyticsBuilder $r3 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_STORES_DISPLAYED);
        if ($i0 == 0) {
            $r3.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "URL");
        } else if ($i0 == 1) {
            $r3.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_MY_STORES_SOURCE_MENU);
        }
        $r3.send();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, DisplayStrings.DS_MY_STORES_SETTINGS_TITLE);
        this.mRecycler = (RecyclerView) findViewById(C1283R.id.myStoresRecycler);
        this.mZeroStateContainer = (LinearLayout) findViewById(C1283R.id.zeroStateContainer);
        ((TextView) findViewById(C1283R.id.lblNoStoresFound)).setText(DisplayStrings.displayString(DisplayStrings.DS_MY_STORES_NO_STORES_FOUND));
        ((TextView) findViewById(C1283R.id.lblZeroStateDescription)).setText(DisplayStrings.displayString(DisplayStrings.DS_MY_STORES_SETTINGS_INFO));
        this.mRecycler.setAdapter(new MyStoresAdapter());
        this.mRecycler.setLayoutManager(new LinearLayoutManager(this, 1, false));
        onMyStoreModelsRefreshed();
        MyWazeNativeManager.getInstance().reloadAllUserStores();
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.blue_status));
        }
    }

    public void onMyStoreModelsRefreshed() throws  {
        NativeManager.Post(new C19811());
    }

    public void onOpenBottomSheet(MyStoreModel $r1) throws  {
        final MyStoreModel myStoreModel = $r1;
        SimpleBottomSheet c19833 = new SimpleBottomSheet(this, DisplayStrings.DS_MY_STORES_SETTINGS_TITLE, new SimpleBottomSheetValue[]{new SimpleBottomSheetValue(0, DisplayStrings.displayString(DisplayStrings.DS_MY_STORES_SEARCH), getResources().getDrawable(C1283R.drawable.navlist_search_icon)), new SimpleBottomSheetValue(1, DisplayStrings.displayString(DisplayStrings.DS_MY_STORES_DELETE), getResources().getDrawable(C1283R.drawable.navlist_delete)), new SimpleBottomSheetValue(2, "", null)}, new C19822(), false) {
            public void onClick(int $i0) throws  {
                super.onClick($i0);
                if ($i0 == 0) {
                    MyStoresActivity.this.startSearchForBrand(myStoreModel.getId());
                } else if ($i0 == 1) {
                    MyWazeNativeManager.getInstance().removeStoreByBrandId(myStoreModel.getId());
                }
                dismiss();
            }
        };
        c19833.setTitleStr($r1.getName());
        c19833.show();
    }

    private void startSearchForBrand(String $r1) throws  {
        Intent $r2 = new Intent(this, SearchResultsActivity.class);
        $r2.putExtra(PublicMacros.SEARCH_BRAND_ID, $r1);
        $r2.putExtra(SearchResultsActivity.CATEGORY_GROUP_SEARCH_PROVIDER, false);
        $r2.putExtra(PublicMacros.SEARCH_MODE, 2);
        AppService.getActiveActivity().startActivityForResult($r2, 1);
    }
}
