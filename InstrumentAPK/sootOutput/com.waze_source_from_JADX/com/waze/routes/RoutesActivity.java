package com.waze.routes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.SettingsNavigationActivity;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class RoutesActivity extends ActivityBase {
    public static final int SETTINGS_ACTIVITY_CODE = 1000;
    private View mIndTab1;
    private View mIndTab2;
    private NativeManager mNm;
    private ViewPager mPager;
    private TextView mTvTab1;
    private TextView mTvTab2;

    class C25691 implements OnClickListener {
        C25691() {
        }

        public void onClick(View v) {
            RoutesActivity.this.startActivityForResult(new Intent(RoutesActivity.this, SettingsNavigationActivity.class), 1000);
        }
    }

    class C25713 implements OnClickListener {
        C25713() {
        }

        public void onClick(View v) {
            RoutesActivity.this.mPager.setCurrentItem(0);
        }
    }

    class C25724 implements OnClickListener {
        C25724() {
        }

        public void onClick(View v) {
            RoutesActivity.this.mPager.setCurrentItem(1);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1283R.layout.routes);
        this.mNm = AppService.getNativeManager();
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.titleBar);
        titleBar.init((Activity) this, (int) DisplayStrings.DS_ROUTES);
        titleBar.setCloseImageResource(C1283R.drawable.navbar_settings);
        titleBar.setOnClickCloseListener(new C25691());
        this.mPager = (ViewPager) findViewById(C1283R.id.routesPager);
        this.mTvTab1 = (TextView) findViewById(C1283R.id.routesListButtonText);
        this.mTvTab2 = (TextView) findViewById(C1283R.id.routesMapButtonText);
        this.mIndTab1 = findViewById(C1283R.id.routesListButtonMarker);
        this.mIndTab2 = findViewById(C1283R.id.routesMapButtonMarker);
        this.mTvTab1.setText(this.mNm.getLanguageString(DisplayStrings.DS_ALTERNATE_ROUTES_LIST_VIEW).toUpperCase());
        this.mTvTab2.setText(this.mNm.getLanguageString(DisplayStrings.DS_ALTERNATE_ROUTES_MAP_VIEW).toUpperCase());
        final int cSelected = getResources().getColor(C1283R.color.White);
        final int cUnselected = getResources().getColor(C1283R.color.BlueDeepLight);
        this.mPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    RoutesActivity.this.mIndTab1.setAlpha(1.0f - positionOffset);
                    RoutesActivity.this.mIndTab2.setAlpha(positionOffset);
                    RoutesActivity.this.mTvTab1.setTextColor(AnimationUtils.mixColors(cSelected, cUnselected, 1.0f - positionOffset));
                    RoutesActivity.this.mTvTab2.setTextColor(AnimationUtils.mixColors(cSelected, cUnselected, positionOffset));
                }
                if (position == 1) {
                    RoutesActivity.this.mIndTab2.setAlpha(1.0f - positionOffset);
                    RoutesActivity.this.mIndTab1.setAlpha(positionOffset);
                    RoutesActivity.this.mTvTab1.setTextColor(AnimationUtils.mixColors(cSelected, cUnselected, positionOffset));
                    RoutesActivity.this.mTvTab2.setTextColor(AnimationUtils.mixColors(cSelected, cUnselected, 1.0f - positionOffset));
                }
            }

            public void onPageSelected(int position) {
                if (position == 0) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ROUTES_SCREEN_SHOWN, "TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_LIST);
                }
                if (position == 1) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ROUTES_SCREEN_SHOWN, "TYPE", "MAP");
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        findViewById(C1283R.id.routesListButton).setOnClickListener(new C25713());
        findViewById(C1283R.id.routesMapButton).setOnClickListener(new C25724());
        this.mPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new RoutesListFragment();
                }
                if (position == 1) {
                    return new RoutesMapFragment();
                }
                return null;
            }

            public int getCount() {
                return 2;
            }
        });
    }

    public void onBackPressed() {
        DriveToNativeManager.getInstance().onAlternativeRoutesClosed();
        super.onBackPressed();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            DriveToNativeManager.getInstance().onAlternativeRoutesClosed();
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
