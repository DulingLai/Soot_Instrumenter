package com.waze.menus;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.NativeManager.VenueCategoryGroup;
import com.waze.ResManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.CanvasFont;
import com.waze.navigate.NavigateNativeManager;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.utils.PixelMeasure;

public class SideMenuCategoryBar extends FrameLayout {
    private static final long ANIM_DURATION_BACKGROUND = 300;
    private static final long ANIM_DURATION_ICON = 350;
    private static final long ANIM_OFFSET_ICON = 40;
    private static final long ANIM_OFFSET_ICON_INITIAL = 120;
    private static final int ICON_COLOR = -1;
    private static int MAX_WIDTH = PixelMeasure.dp(DisplayStrings.DS_SPEED_CAM);
    public static final String PARKING_CATEGORY_GROUP = "parking";
    private static final int[] ids = new int[]{C1283R.id.sideMenuCategoryBarItem1, C1283R.id.sideMenuCategoryBarItem2, C1283R.id.sideMenuCategoryBarItem3, C1283R.id.sideMenuCategoryBarItem4, C1283R.id.sideMenuCategoryBarItem5};
    private VenueCategoryGroup[] categoryGroups;
    private boolean iconsMissing = false;

    class C19453 implements OnClickListener {
        C19453() throws  {
        }

        public void onClick(View v) throws  {
            SideMenuCategoryBar.this.closeKeyboard();
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY, "MORE").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_FEATURED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ROUTING, NavigateNativeManager.instance().isNavigating() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE).send();
            AppService.getActiveActivity().startActivityForResult(new Intent(SideMenuCategoryBar.this.getContext(), CategorySelectionActivity.class), 0);
        }
    }

    public SideMenuCategoryBar(Context $r1) throws  {
        super($r1);
        init();
    }

    public SideMenuCategoryBar(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        init();
    }

    public SideMenuCategoryBar(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        View $r7;
        View $r3 = LayoutInflater.from(getContext()).inflate(C1283R.layout.side_menu_category_bar, null);
        this.categoryGroups = NativeManager.getInstance().venueProviderGetCategoryGroups();
        if (this.categoryGroups != null) {
            int $i1 = 0;
            while ($i1 <= 3 && 0 < this.categoryGroups.length) {
                if (0 >= this.categoryGroups.length) {
                    while ($i1 <= 3) {
                        findViewById(ids[$i1]).setVisibility(8);
                        $i1++;
                    }
                } else {
                    $r7 = $r3.findViewById(ids[$i1]);
                    final String $r10 = this.categoryGroups[$i1].icon + ResManager.mImageExtension;
                    final int $i0 = $i1;
                    Drawable $r11 = ResManager.GetSkinDrawable($r10);
                    if ($r11 != null) {
                        ((ImageView) $r7.findViewById(C1283R.id.categoryItemImage)).setImageDrawable($r11);
                        setVisibility(8);
                    } else {
                        setVisibility(8);
                        this.iconsMissing = true;
                    }
                    if (this.categoryGroups[$i1].id.equals(PARKING_CATEGORY_GROUP)) {
                        $r7.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) throws  {
                                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY, SideMenuCategoryBar.this.categoryGroups[$i0].id).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_FEATURED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ROUTING, NavigateNativeManager.instance().isNavigating() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE).send();
                                NativeManager.getInstance().OpenParkingSearch();
                            }
                        });
                    } else {
                        $r7.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) throws  {
                                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY, SideMenuCategoryBar.this.categoryGroups[$i0].id).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CATEGORY_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_FEATURED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ROUTING, NavigateNativeManager.instance().isNavigating() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE).send();
                                SideMenuCategoryBar.this.closeKeyboard();
                                Intent $r2 = new Intent(SideMenuCategoryBar.this.getContext(), SearchResultsActivity.class);
                                $r2.putExtra(PublicMacros.SEARCH_CATEGORY_GROUP, SideMenuCategoryBar.this.categoryGroups[$i0].id);
                                $r2.putExtra(PublicMacros.SEARCH_TITLE, SideMenuCategoryBar.this.categoryGroups[$i0].label);
                                $r2.putExtra(PublicMacros.SEARCH_MODE, 2);
                                $r2.putExtra(PublicMacros.ICON, $r10);
                                AppService.getActiveActivity().startActivityForResult($r2, 0);
                            }
                        });
                    }
                    $i1++;
                }
            }
        }
        $r7 = $r3.findViewById(C1283R.id.sideMenuCategoryBarItem5);
        ((ImageView) $r7.findViewById(C1283R.id.categoryItemBackground)).setImageResource(C1283R.drawable.category_search_generic_background);
        ((ImageView) $r7.findViewById(C1283R.id.categoryItemImage)).setImageResource(C1283R.drawable.category_search_more_icon);
        $r7.setOnClickListener(new C19453());
        $r7 = $r3.findViewById(C1283R.id.categoryBarContainer);
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                LayoutParams $r4 = (LayoutParams) $r7.getLayoutParams();
                if (SideMenuCategoryBar.this.getWidth() > SideMenuCategoryBar.MAX_WIDTH) {
                    $r4.width = SideMenuCategoryBar.MAX_WIDTH;
                    $r4.gravity = 17;
                } else {
                    $r4.width = -1;
                }
                $r4.gravity = 49;
                $r7.setLayoutParams($r4);
            }
        });
        addView($r3);
    }

    public boolean isAvailable() throws  {
        return (this.iconsMissing || this.categoryGroups == null || this.categoryGroups.length <= 0) ? false : true;
    }

    private void closeKeyboard() throws  {
        EditTextUtils.closeKeyboard(AppService.getActiveActivity(), this);
    }

    public void appearify() throws  {
        setVisibility(0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, 0.0f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        findViewById(C1283R.id.categoryBarBackground).startAnimation(scaleAnimation);
        for (int $i0 = 0; $i0 < ids.length; $i0++) {
            View $r3 = findViewById(ids[$i0]);
            Animation scaleAnimation2 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            scaleAnimation2.setInterpolator(new OvershootInterpolator());
            scaleAnimation2.setDuration(ANIM_DURATION_ICON);
            ScaleAnimation $r1 = new ScaleAnimation(0.0f, 0.0f, 0.0f, 0.0f);
            $r1.setDuration(ANIM_OFFSET_ICON_INITIAL + (((long) $i0) * ANIM_OFFSET_ICON));
            final View view = $r3;
            final Animation animation = scaleAnimation2;
            $r1.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) throws  {
                }

                public void onAnimationEnd(Animation animation) throws  {
                    view.startAnimation(animation);
                }

                public void onAnimationRepeat(Animation animation) throws  {
                }
            });
            $r3.startAnimation($r1);
        }
    }
}
