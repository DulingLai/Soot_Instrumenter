package com.waze.map;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.navigate.AddressItem;
import com.waze.reports.VenueData;
import com.waze.utils.PixelMeasure;
import com.waze.view.popups.DetailsPopUp;
import com.waze.view.popups.YouAreHerePopUp;

public final class MapViewWrapper extends FrameLayout {
    public static final int POPUP_VERTICAL_OFFSET_DP = 16;
    private DetailsPopUp mDetailsPopUp;
    private YouAreHerePopUp mYouAreHerePopUp;
    private MapView mapView = ((MapView) findViewById(C1283R.id.mapViewWrapperMapView));

    class C18581 implements Runnable {
        C18581() throws  {
        }

        public void run() throws  {
            MapViewWrapper.this.mYouAreHerePopUp.hide(true);
        }
    }

    class C18602 implements Runnable {

        class C18591 implements Runnable {
            C18591() throws  {
            }

            public void run() throws  {
                MapViewWrapper.this.mDetailsPopUp = null;
            }
        }

        C18602() throws  {
        }

        public void run() throws  {
            if (MapViewWrapper.this.mDetailsPopUp != null && MapViewWrapper.this.mDetailsPopUp.isShown()) {
                MapViewWrapper.this.mDetailsPopUp.hide(new C18591());
            }
        }
    }

    class C18613 implements Runnable {
        C18613() throws  {
        }

        public void run() throws  {
            MapViewWrapper.this.mDetailsPopUp = null;
        }
    }

    public MapViewWrapper(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        ((LayoutInflater) $r1.getSystemService("layout_inflater")).inflate(C1283R.layout.map_view_wrapper, this);
        TypedArray $r8 = $r1.obtainStyledAttributes($r2, C1283R.styleable.MapView);
        this.mapView.setHandleKeys($r8.getBoolean(0, true));
        this.mapView.setNativeTag($r8.getString(2));
        if (!isInEditMode()) {
            this.mapView.setRenderer();
            this.mYouAreHerePopUp = (YouAreHerePopUp) findViewById(C1283R.id.youAreHerePopUp);
        }
    }

    public void onPause() throws  {
        if (this.mDetailsPopUp != null) {
            this.mDetailsPopUp.hideNow();
        }
        if (this.mYouAreHerePopUp.isShowing()) {
            this.mYouAreHerePopUp.hide(false);
        }
        AppService.setActiveMapViewWrapper(null);
        this.mapView.onPause();
    }

    public void onResume() throws  {
        AppService.setActiveMapViewWrapper(this);
        this.mapView.onResume();
    }

    public MapView getMapView() throws  {
        return this.mapView;
    }

    public void showDetailsPopup(int $i0, int y, int $i1, String $r1, String $r2, String $r3, boolean $z0, int $i2, AddressItem $r4, int $i3, VenueData $r5, int $i4) throws  {
        YouAreHerePopUp $r8;
        if ($i2 != 1) {
            y -= PixelMeasure.dp(16);
            if (this.mDetailsPopUp == null) {
                this.mDetailsPopUp = new DetailsPopUp(getContext());
            }
            this.mDetailsPopUp.show($i0, y, $i1, $r1, $r2, $r3, $z0, $i2, $r4, $i3, $r5, $i4);
            $r8 = this.mYouAreHerePopUp;
            if ($r8.isShowing()) {
                this.mYouAreHerePopUp.hide(true);
                return;
            }
            return;
        }
        if (this.mDetailsPopUp != null && this.mDetailsPopUp.isShown()) {
            this.mDetailsPopUp.hideNow();
        }
        $r8 = this.mYouAreHerePopUp;
        YouAreHerePopUp $r82 = $r8;
        $r8.show($i0, y, $i1, $r4, $i3);
    }

    public boolean hidePopupIfShown() throws  {
        if (this.mYouAreHerePopUp.isShowing()) {
            post(new C18581());
            return true;
        } else if (this.mDetailsPopUp == null || !this.mDetailsPopUp.isShown()) {
            return false;
        } else {
            post(new C18602());
            return true;
        }
    }

    public void updateDetailsPopupContent(int $i0, String $r1, String $r2) throws  {
        if ($i0 != 1 && this.mDetailsPopUp != null && this.mDetailsPopUp.isShown()) {
            this.mDetailsPopUp.updateContent($i0, $r1, $r2);
        }
    }

    public void updateDetailsPopupInfo(int $i0, String $r1, boolean $z0) throws  {
        if (this.mDetailsPopUp != null && this.mDetailsPopUp.isShown()) {
            this.mDetailsPopUp.updateDetailsPopupInfo($i0, $r1, $z0);
        }
    }

    public void updateDetailsPopup(int $i0, int $i1) throws  {
        if (this.mYouAreHerePopUp.isShowing()) {
            this.mYouAreHerePopUp.updatePosition($i0, $i1);
            return;
        }
        $i1 -= PixelMeasure.dp(16);
        if (this.mDetailsPopUp != null && this.mDetailsPopUp.isShown()) {
            this.mDetailsPopUp.update($i0, $i1);
        }
    }

    public void closeDetailsPopup() throws  {
        if (this.mYouAreHerePopUp.isShowing() && !this.mYouAreHerePopUp.isAnimatingShow()) {
            this.mYouAreHerePopUp.hide(true);
        }
        if (this.mDetailsPopUp != null && this.mDetailsPopUp.isShown()) {
            this.mDetailsPopUp.hide(new C18613());
        }
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        return this.mapView.onKeyDown($i0, $r1);
    }

    public boolean hasDetailsPopUpInstance() throws  {
        return (this.mDetailsPopUp != null && this.mDetailsPopUp.isShown()) || (this.mYouAreHerePopUp != null && this.mYouAreHerePopUp.isShowing());
    }

    public boolean hasYouAreHerePopUpInstance() throws  {
        return this.mYouAreHerePopUp != null && this.mYouAreHerePopUp.isShowing();
    }
}
