package com.waze.navigate;

import android.os.Bundle;
import android.os.Handler;
import com.waze.NativeManager;
import com.waze.ifs.async.UpdateHandlers;
import com.waze.reports.VenueData;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import java.io.Serializable;

public class NavigateNativeManager {
    public static final int UH_CALC_ETA = TicketRoller.get(Type.Handler);
    public static final int UH_CALC_MULTI_ETA = TicketRoller.get(Type.Handler);
    public static final int UH_CARPOOL_MAP_DRAW = TicketRoller.get(Type.Handler);
    public static int UH_LOCATION_PICKER_STATE = TicketRoller.get(Type.Handler);
    public static final int UH_MAP_CENTER = TicketRoller.get(Type.Handler);
    public static final int UH_SUGGEST_ALL_PARKING = TicketRoller.get(Type.Handler);
    public static final int UH_SUGGEST_BEST_PARKING = TicketRoller.get(Type.Handler);
    private static NavigateNativeManager mInstance = new NavigateNativeManager();
    private UpdateHandlers handlers = new UpdateHandlers();

    class C21371 implements Runnable {
        C21371() throws  {
        }

        public void run() throws  {
            NavigateNativeManager.mInstance.handlers.sendUpdateMessage(NavigateNativeManager.UH_CARPOOL_MAP_DRAW, null);
        }
    }

    class C21426 implements Runnable {
        C21426() throws  {
        }

        public void run() throws  {
            NavigateNativeManager.this.ClearPreviewsNTV();
        }
    }

    public static class ParkingResult implements Serializable {
        public AddressItem ai;
        public boolean best;
        public boolean popular;
        public boolean showAsAd;
        public int walkingDistance;

        public ParkingResult(AddressItem $r1, boolean $z0, boolean $z1, int $i0, boolean $z2) throws  {
            this.ai = $r1;
            this.popular = $z0;
            this.best = $z1;
            this.walkingDistance = $i0;
            this.showAsAd = $z2;
        }
    }

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1;
        public int latitude;
        public int longitude;
    }

    private native void CenterOnPositionNTV(int i, int i2, int i3) throws ;

    private native void ClearPreviewsNTV() throws ;

    private native void InitNTV() throws ;

    private native void LoadResultsCanvasNTV(float f, float f2) throws ;

    private native void LoadRideDetailsCanvasNTV(float f, float f2, String str, int i, boolean z, boolean z2, int i2, int i3) throws ;

    private native void PreviewCanvasFocusOnNTV(int i, int i2, int i3) throws ;

    private native void SelectRouteNTV(int i) throws ;

    private native void SetParkingPoiPositionNTV(VenueData venueData, int i, int i2, VenueData venueData2, boolean z, String str, boolean z2, String str2) throws ;

    private native void SetPreviewPoiPositionNTV(int i, int i2, String str, boolean z) throws ;

    private native void StopNavigationReasonNTV(int i) throws ;

    private native void ZoomInNTV() throws ;

    private native void ZoomInOutWithRateNTV(float f) throws ;

    private native void ZoomOutNTV() throws ;

    private native void calculateETANTV(VenueData venueData, VenueData venueData2) throws ;

    private native void calculateMultiETANTV(VenueData[] venueDataArr, VenueData venueData) throws ;

    private native Position getMapCenterNTV() throws ;

    private native void locationPickerSetNTV(int i, int i2, int i3, int i4, int i5, boolean z) throws ;

    private native void locationPickerUnsetNTV() throws ;

    private native void navigateToParkingNTV(VenueData venueData, VenueData venueData2) throws ;

    private native void setRidewithMapViewNTV() throws ;

    private native void suggestParkingRequestBestParkingNTV(VenueData venueData) throws ;

    private native void suggestParkingRequestSuggestionsNTV(VenueData venueData) throws ;

    public native int calcWalkingMinutesNTV(int i) throws ;

    public native boolean isNavigating() throws ;

    public native boolean isParkingCategoryNTV(String str) throws ;

    public native boolean suggestParkingEnabled() throws ;

    public void setUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.setUpdateHandler($i0, $r1);
    }

    public void unsetUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.unsetUpdateHandler($i0, $r1);
    }

    public void onLocationPickerStateChanged(int $i0) throws  {
        this.handlers.sendUpdateMessage(UH_LOCATION_PICKER_STATE, $i0, 0);
    }

    public void onGetMapCenter(int $i0) throws  {
        this.handlers.sendUpdateMessage(UH_MAP_CENTER, $i0, 0);
    }

    public void carpoolMapOnDraw() throws  {
        NativeManager.Post(new C21371());
    }

    public static NavigateNativeManager instance() throws  {
        return mInstance;
    }

    public void PreviewCanvasFocusOn(final int $i0, final int $i1, final int $i2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.PreviewCanvasFocusOnNTV($i0, $i1, $i2);
            }
        });
    }

    public void SetPreviewPoiPosition(final int $i0, final int $i1, final String $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.SetPreviewPoiPositionNTV($i0, $i1, $r1, $z0);
            }
        });
    }

    public void SetParkingPoiPosition(final VenueData $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.SetParkingPoiPositionNTV($r1, 0, 0, null, false, null, false, null);
            }
        });
    }

    public void SetParkingPoiPosition(final VenueData $r1, final int $i0, final int $i1, final VenueData $r2, final boolean $z0, final String $r3, final boolean $z1, final String $r4) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.SetParkingPoiPositionNTV($r1, $i0, $i1, $r2, $z0, $r3, $z1, $r4);
            }
        });
    }

    public void ClearPreviews() throws  {
        NativeManager.Post(new C21426());
    }

    public void SelectRoute(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.SelectRouteNTV($i0);
            }
        });
    }

    public void LoadResultsCanvas(final float $f0, final float $f1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.LoadResultsCanvasNTV($f0, $f1);
            }
        });
    }

    public void LoadRideDetailsCanvas(final float $f0, final float $f1, final String $r1, final int $i0, final boolean $z0, final boolean $z1, final int $i1, final int $i2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.LoadRideDetailsCanvasNTV($f0, $f1, $r1, $i0, $z0, $z1, $i1, $i2);
            }
        });
    }

    private NavigateNativeManager() throws  {
        InitNTV();
    }

    public void zoomInTap() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.ZoomInNTV();
            }
        });
    }

    public void zoomOutTap() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.ZoomOutNTV();
            }
        });
    }

    public void zoomHold(final float $f0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                float $f0 = $f0;
                if ($f0 > 150.0f) {
                    $f0 = 150.0f;
                }
                if ($f0 < -150.0f) {
                    $f0 = -150.0f;
                }
                NavigateNativeManager.this.ZoomInOutWithRateNTV($f0);
            }
        });
    }

    public void getMapCenter() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Position $r3 = NavigateNativeManager.this.getMapCenterNTV();
                Bundle $r1 = new Bundle();
                $r1.putSerializable("position", $r3);
                NavigateNativeManager.mInstance.handlers.sendUpdateMessage(NavigateNativeManager.UH_MAP_CENTER, $r1);
            }
        });
    }

    public void locationPickerCanvasSet(final int $i0, final int $i1, final int $i2, final int $i3, final int $i4, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.locationPickerSetNTV($i0, $i1, $i2, $i3, $i4, $z0);
            }
        });
    }

    public void locationPickerCanvasUnset() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.locationPickerUnsetNTV();
            }
        });
    }

    public void setRidewithMapView() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.setRidewithMapViewNTV();
            }
        });
    }

    public void suggestParkingRequestBestParking(final VenueData $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.suggestParkingRequestBestParkingNTV($r1);
            }
        });
    }

    public void suggestParkingRequestBestParkingCallback(AddressItem $r1, int $i0, boolean $z0, boolean $z1) throws  {
        final AddressItem addressItem = $r1;
        final int i = $i0;
        final boolean z = $z1;
        final boolean z2 = $z0;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Bundle $r1 = new Bundle();
                $r1.putSerializable("addressItem", addressItem);
                $r1.putInt("parkingDistance", i);
                $r1.putBoolean("parkingPopular", z);
                $r1.putBoolean("more", z2);
                NavigateNativeManager.mInstance.handlers.sendUpdateMessage(NavigateNativeManager.UH_SUGGEST_BEST_PARKING, $r1);
            }
        });
    }

    public void suggestParkingRequestSuggestions(final VenueData $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.suggestParkingRequestSuggestionsNTV($r1);
            }
        });
    }

    public void suggestParkingRequestSuggestionsCallback(final ParkingResult[] $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Bundle $r1 = new Bundle();
                $r1.putSerializable("results", $r1);
                NavigateNativeManager.mInstance.handlers.sendUpdateMessage(NavigateNativeManager.UH_SUGGEST_ALL_PARKING, $r1);
            }
        });
    }

    public void calculateETA(final VenueData $r1, final VenueData $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.calculateETANTV($r1, $r2);
            }
        });
    }

    public void calculateETACallback(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Bundle $r1 = new Bundle();
                $r1.putInt("eta", $i0);
                NavigateNativeManager.mInstance.handlers.sendUpdateMessage(NavigateNativeManager.UH_CALC_ETA, $r1);
            }
        });
    }

    public void calculateMultiETA(final VenueData[] $r1, final VenueData $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.calculateMultiETANTV($r1, $r2);
            }
        });
    }

    public void calculateMultiETACallback(final String[] $r1, final int[] $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Bundle $r1 = new Bundle();
                $r1.putStringArray("ids", $r1);
                $r1.putIntArray("etas", $r2);
                NavigateNativeManager.mInstance.handlers.sendUpdateMessage(NavigateNativeManager.UH_CALC_MULTI_ETA, $r1);
            }
        });
    }

    public void navigateToParking(final VenueData $r1, final VenueData $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.navigateToParkingNTV($r1, $r2);
            }
        });
    }

    public void stopNavigationReason(final NavigateStopReason $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.StopNavigationReasonNTV($r1.ordinal());
            }
        });
    }

    public void centerOnPosition(final int $i0, final int $i1, final int $i2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NavigateNativeManager.this.CenterOnPositionNTV($i0, $i1, $i2);
            }
        });
    }
}
