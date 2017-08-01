package com.waze.menus;

import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.NativeManager.VenueCategoryGroup;
import com.waze.ResManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.NavigateNativeManager;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;

public class CategorySelectionActivity extends ActivityBase {
    private static final int ICON_COLOR = -4534834;
    private VenueCategoryGroup[] categoryGroups;

    class C18901 extends BaseAdapter {
        public Object getItem(int position) throws  {
            return null;
        }

        public long getItemId(int position) throws  {
            return 0;
        }

        C18901() throws  {
        }

        public int getCount() throws  {
            return CategorySelectionActivity.this.categoryGroups == null ? 0 : CategorySelectionActivity.this.categoryGroups.length;
        }

        public View getView(int $i0, View $r1, ViewGroup parent) throws  {
            if (CategorySelectionActivity.this.categoryGroups == null || $i0 < 0 || $i0 >= CategorySelectionActivity.this.categoryGroups.length) {
                return null;
            }
            WazeSettingsView $r5;
            if ($r1 != null) {
                $r5 = (WazeSettingsView) $r1;
            } else {
                $r5 = r0;
                WazeSettingsView wazeSettingsView = new WazeSettingsView(CategorySelectionActivity.this);
            }
            $r5.setText(NativeManager.getInstance().getLanguageString(CategorySelectionActivity.this.categoryGroups[$i0].label));
            Drawable $r10 = ResManager.GetSkinDrawable(CategorySelectionActivity.this.categoryGroups[$i0].icon + ResManager.mImageExtension);
            Drawable $r11 = $r10;
            if ($r10 == null) {
                $r11 = CategorySelectionActivity.this.getResources().getDrawable(C1283R.drawable.list_icon_location);
            }
            if ($r11 != null) {
                $r11.mutate();
                $r11.setColorFilter(CategorySelectionActivity.ICON_COLOR, Mode.SRC_ATOP);
                $r5.setIcon($r11);
                return $r5;
            }
            $r5.setIcon(null);
            return $r5;
        }
    }

    class C18912 implements OnItemClickListener {
        C18912() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
            this = this;
            if (CategorySelectionActivity.this.categoryGroups != null && $i0 >= 0) {
                this = this;
                if ($i0 < CategorySelectionActivity.this.categoryGroups.length) {
                    this = this;
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY, CategorySelectionActivity.this.categoryGroups[$i0].id).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_EXPANDED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ROUTING, NavigateNativeManager.instance().isNavigating() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE).send();
                    String $r8 = CategorySelectionActivity.this.categoryGroups[$i0].icon + ResManager.mImageExtension;
                    this = this;
                    if (CategorySelectionActivity.this.categoryGroups[$i0].id.equals(SideMenuCategoryBar.PARKING_CATEGORY_GROUP)) {
                        NativeManager.getInstance().OpenParkingSearch();
                        return;
                    }
                    Intent $r3 = new Intent(CategorySelectionActivity.this, SearchResultsActivity.class);
                    $r3.putExtra(PublicMacros.SEARCH_CATEGORY_GROUP, CategorySelectionActivity.this.categoryGroups[$i0].id);
                    $r3.putExtra(PublicMacros.SEARCH_TITLE, CategorySelectionActivity.this.categoryGroups[$i0].label);
                    $r3.putExtra(PublicMacros.SEARCH_MODE, 2);
                    $r3.putExtra(PublicMacros.ICON, $r8);
                    CategorySelectionActivity.this.startActivityForResult($r3, null);
                }
            }
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, DisplayStrings.DS_CATEGORICAL_SEARCH_MORE_TITLE);
        ListView $r4 = (ListView) findViewById(C1283R.id.settingsValueList);
        this.categoryGroups = NativeManager.getInstance().venueProviderGetCategoryGroups();
        $r4.setAdapter(new C18901());
        $r4.setOnItemClickListener(new C18912());
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList($r4);
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }
}
