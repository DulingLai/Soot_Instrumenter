package com.waze.main.navigate;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.NavBarManager;
import com.waze.NavBarManager.NavigationListListener;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.CanvasFont;
import com.waze.map.NativeCanvasRenderer;
import com.waze.rtalerts.RTAlertsAlertData;
import com.waze.rtalerts.RTAlertsNativeManager;
import com.waze.rtalerts.RTAlertsNativeManager.IAlertsListDataHandler;
import com.waze.rtalerts.RTAlertsNativeManager.IAlertsListDataHandler.Data;
import com.waze.strings.DisplayStrings;
import java.util.Arrays;
import java.util.Comparator;

public class NavigationListFragment extends Fragment {
    ListView mCurList;
    View mIndTab1;
    View mIndTab2;
    private ListView mNavList;
    private NavigationListItemAdapter mNavListAdapter;
    NativeManager mNm = NativeManager.getInstance();
    private View mNoReports;
    ViewPager mPager;
    private FrameLayout mReportsFrame;
    private ListView mReportsList;
    private NavigationListReportItemAdapter mReportsListAdapter;
    TextView mTvTab1;
    TextView mTvTab2;

    class C18481 implements OnPageChangeListener {
        C18481() throws  {
        }

        public void onPageScrolled(int $i0, float $f0, int positionOffsetPixels) throws  {
            if ($i0 == 0) {
                NavigationListFragment.this.mTvTab1.setAlpha(1.0f - ($f0 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
                NavigationListFragment.this.mTvTab2.setAlpha(($f0 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                NavigationListFragment.this.mIndTab1.setAlpha(1.0f - $f0);
                NavigationListFragment.this.mIndTab2.setAlpha($f0);
                NavigationListFragment.this.mCurList = NavigationListFragment.this.mNavList;
            }
            if ($i0 == 1) {
                NavigationListFragment.this.mTvTab2.setAlpha(1.0f - ($f0 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
                NavigationListFragment.this.mTvTab1.setAlpha(($f0 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                NavigationListFragment.this.mIndTab2.setAlpha(1.0f - $f0);
                NavigationListFragment.this.mIndTab1.setAlpha($f0);
                NavigationListFragment.this.mCurList = NavigationListFragment.this.mReportsList;
            }
        }

        public void onPageSelected(int $i0) throws  {
            if ($i0 == 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NAV_GUIDANCE_SHOWN, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_TURNS);
            }
            if ($i0 == 1) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NAV_GUIDANCE_SHOWN, "TYPE", "REPORTS");
            }
        }

        public void onPageScrollStateChanged(int state) throws  {
        }
    }

    class C18492 implements OnClickListener {
        C18492() throws  {
        }

        public void onClick(View v) throws  {
            NavigationListFragment.this.mPager.setCurrentItem(0);
        }
    }

    class C18503 implements OnClickListener {
        C18503() throws  {
        }

        public void onClick(View v) throws  {
            NavigationListFragment.this.mPager.setCurrentItem(1);
        }
    }

    class C18524 implements NavigationListListener {

        class C18511 implements OnGlobalLayoutListener {
            C18511() throws  {
            }

            public void onGlobalLayout() throws  {
                NavigationListFragment.this.mNavList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                NavigationListFragment.this.slideInBg(NavigationListFragment.this.getView(), NavigationListFragment.this.dropInViews(NavigationListFragment.this.mNavList), 0);
            }
        }

        C18524() throws  {
        }

        public void onComplete(NavigationItem[] $r1) throws  {
            NavigationListFragment.this.mNavListAdapter.setItems($r1);
            NavigationListFragment.this.mNavList.invalidateViews();
            NavigationListFragment.this.mNavList.getViewTreeObserver().addOnGlobalLayoutListener(new C18511());
        }
    }

    class C18545 implements IAlertsListDataHandler {

        class C18531 implements Comparator<RTAlertsAlertData> {
            C18531() throws  {
            }

            public int compare(RTAlertsAlertData $r1, RTAlertsAlertData $r2) throws  {
                return $r1.mDistance - $r2.mDistance;
            }
        }

        C18545() throws  {
        }

        public void handler(Data $r1) throws  {
            Arrays.sort($r1.mAlertsData, new C18531());
            NavigationListFragment.this.mReportsListAdapter.setItems($r1.mAlertsData);
            NavigationListFragment.this.mReportsList.invalidateViews();
            NavigationListFragment.this.mNoReports.setVisibility($r1.mAlertsData.length == 0 ? (byte) 0 : (byte) 8);
        }
    }

    class C18556 extends PagerAdapter {
        public int getCount() throws  {
            return 2;
        }

        public boolean isViewFromObject(View $r1, Object $r2) throws  {
            return $r1 == $r2;
        }

        C18556() throws  {
        }

        public Object instantiateItem(ViewGroup $r1, int $i0) throws  {
            if ($i0 == 0) {
                $r1.addView(NavigationListFragment.this.mNavList);
                return NavigationListFragment.this.mNavList;
            } else if ($i0 != 1) {
                return null;
            } else {
                $r1.addView(NavigationListFragment.this.mReportsFrame);
                return NavigationListFragment.this.mReportsFrame;
            }
        }
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle savedInstanceState) throws  {
        View $r5 = $r1.inflate(C1283R.layout.fragment_navigation_list, $r2, false);
        this.mPager = (ViewPager) $r5.findViewById(C1283R.id.fragNavListPager);
        this.mTvTab1 = (TextView) $r5.findViewById(C1283R.id.fragNavListTabs1Text);
        this.mTvTab2 = (TextView) $r5.findViewById(C1283R.id.fragNavListTabs2Text);
        this.mIndTab1 = $r5.findViewById(C1283R.id.fragNavListTabs1Ind);
        this.mIndTab2 = $r5.findViewById(C1283R.id.fragNavListTabs2Ind);
        this.mTvTab1.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_DIRECTIONS_LIST_TAB_NEXT_TURNS).toUpperCase());
        this.mTvTab2.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_DIRECTIONS_LIST_TAB_REPORTS_AHEAD).toUpperCase());
        this.mPager.addOnPageChangeListener(new C18481());
        $r5.findViewById(C1283R.id.fragNavListTabs1).setOnClickListener(new C18492());
        $r5.findViewById(C1283R.id.fragNavListTabs2).setOnClickListener(new C18503());
        NavBarManager $r14 = NativeManager.getInstance().getNavBarManager();
        this.mNavListAdapter = new NavigationListItemAdapter($r2.getContext(), $r14.getDriveOnLeft());
        this.mNavList = new ListView($r2.getContext());
        this.mNavList.setDividerHeight(0);
        this.mNavList.setAdapter(this.mNavListAdapter);
        ListView listView = this.mNavList;
        ListView $r17 = listView;
        this.mCurList = listView;
        this.mReportsListAdapter = new NavigationListReportItemAdapter($r2.getContext(), $r14.getDriveOnLeft());
        this.mReportsFrame = (FrameLayout) $r1.inflate(C1283R.layout.navigation_list_no_reports, null);
        this.mNoReports = this.mReportsFrame.findViewById(C1283R.id.navListNoReportsLayout);
        ((TextView) this.mReportsFrame.findViewById(C1283R.id.navListNoReportsText)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_DIRECTIONS_LIST_NO_REPORTS));
        this.mReportsList = (ListView) this.mReportsFrame.findViewById(C1283R.id.navListReportsList);
        this.mReportsList.setDividerHeight(0);
        this.mReportsList.setAdapter(this.mReportsListAdapter);
        $r14.getNavigationItems(new C18524());
        RTAlertsNativeManager.getInstance().getAlertsOnRouteData(new C18545());
        this.mPager.setAdapter(new C18556());
        slideInBg($r5, 180, 2000);
        return $r5;
    }

    public void onResume() throws  {
        super.onResume();
        NativeCanvasRenderer.OnMainCanvasOverlayShown();
    }

    public void onPause() throws  {
        super.onPause();
        NativeCanvasRenderer.OnMainCanvasOverlayHidden();
    }

    private void slideInBg(View $r1, int $i0, int $i1) throws  {
        TranslateAnimation $r2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        $r2.setDuration((long) $i0);
        $r2.setStartOffset((long) $i1);
        $r2.setFillBefore(true);
        $r2.setInterpolator(new DecelerateInterpolator());
        $r1.findViewById(C1283R.id.fragNavListBackground).startAnimation($r2);
        $r2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        $r2.setDuration(100);
        $r2.setStartOffset((long) $i1);
        $r2.setFillBefore(true);
        $r2.setInterpolator(new DecelerateInterpolator());
        $r1.findViewById(C1283R.id.fragNavListTabs).startAnimation($r2);
    }

    private int dropInViews(ListView $r1) throws  {
        int $i0 = $r1.getChildCount();
        int $i1 = (($i0 - 1) * 20) + 10;
        int $i2 = $i1;
        for (int $i3 = 0; $i3 < $i0; $i3++) {
            View $r3 = $r1.getChildAt($i3);
            int $i4 = $r3.getTop();
            AnimationSet $r2 = new AnimationSet(true);
            $r2.addAnimation(new TranslateAnimation(0.0f, 0.0f, (float) (-$i4), 0.0f));
            $r2.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            $r2.setInterpolator(new DecelerateInterpolator());
            $r2.setDuration(300);
            long $l5 = (long) $i1;
            $r2.setStartOffset($l5);
            $r2.setFillBefore(true);
            $r3.startAnimation($r2);
            $i1 -= 20;
        }
        return $i2 < 0 ? 0 : $i2;
    }

    private void slideOutBg(int $i0, AnimationListener $r1) throws  {
        View $r2 = getView();
        if ($r2 != null) {
            TranslateAnimation $r3 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
            $r3.setDuration((long) $i0);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setFillAfter(true);
            $r3.setAnimationListener($r1);
            $r2.findViewById(C1283R.id.fragNavListBackground).startAnimation($r3);
            $r3 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
            $r3.setDuration(100);
            $r3.setStartOffset((long) ($i0 - 100));
            $r3.setFillAfter(true);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r2.findViewById(C1283R.id.fragNavListTabs).startAnimation($r3);
        }
    }

    private int flyOutViews(ListView $r1) throws  {
        if ($r1 == null) {
            return 0;
        }
        int $i1 = $r1.getChildCount();
        int $i2 = (($i1 - 1) * 20) + 50;
        int $i0 = $i2 + 200;
        for (int $i3 = 0; $i3 < $i1; $i3++) {
            View $r3 = $r1.getChildAt($i3);
            int $i4 = $r3.getTop();
            AnimationSet $r2 = new AnimationSet(true);
            $r2.addAnimation(new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-$i4)));
            $r2.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            $r2.setInterpolator(new AccelerateInterpolator());
            $r2.setDuration(200);
            long $l5 = (long) $i2;
            $r2.setStartOffset($l5);
            $r2.setFillAfter(true);
            $r3.startAnimation($r2);
            $i2 -= 20;
        }
        return $i0;
    }

    public void animateOut(AnimationListener $r1) throws  {
        slideOutBg(flyOutViews(this.mCurList), $r1);
    }
}
