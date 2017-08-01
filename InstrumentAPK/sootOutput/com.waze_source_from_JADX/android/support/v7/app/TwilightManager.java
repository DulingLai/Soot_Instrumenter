package android.support.v7.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import com.abaltatech.mcp.weblink.core.WLTypes;
import java.util.Calendar;

class TwilightManager {
    private static final int SUNRISE = 6;
    private static final int SUNSET = 22;
    private static final String TAG = "TwilightManager";
    private static final TwilightState sTwilightState = new TwilightState();
    private final Context mContext;
    private final LocationManager mLocationManager;

    private static class TwilightState {
        boolean isNight;
        long nextUpdate;
        long todaySunrise;
        long todaySunset;
        long tomorrowSunrise;
        long yesterdaySunset;

        private TwilightState() throws  {
        }
    }

    TwilightManager(Context $r1) throws  {
        this.mContext = $r1;
        this.mLocationManager = (LocationManager) $r1.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
    }

    boolean isNight() throws  {
        TwilightState $r1 = sTwilightState;
        if (isStateValid($r1)) {
            return $r1.isNight;
        }
        Location $r2 = getLastKnownLocation();
        if ($r2 != null) {
            updateState($r2);
            return $r1.isNight;
        }
        Log.i(TAG, "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int $i0 = Calendar.getInstance().get(11);
        return $i0 < 6 || $i0 >= 22;
    }

    private Location getLastKnownLocation() throws  {
        Location $r1 = null;
        Location $r2 = null;
        if (PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            $r1 = getLastKnownLocationForProvider("network");
        }
        if (PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            $r2 = getLastKnownLocationForProvider("gps");
        }
        return ($r2 == null || $r1 == null) ? $r2 == null ? $r1 : $r2 : $r2.getTime() > $r1.getTime() ? $r2 : $r1;
    }

    private Location getLastKnownLocationForProvider(String $r1) throws  {
        if (this.mLocationManager != null) {
            try {
                if (this.mLocationManager.isProviderEnabled($r1)) {
                    return this.mLocationManager.getLastKnownLocation($r1);
                }
            } catch (Exception $r2) {
                Log.d(TAG, "Failed to get last known location", $r2);
            }
        }
        return null;
    }

    private boolean isStateValid(TwilightState $r1) throws  {
        return $r1 != null && $r1.nextUpdate > System.currentTimeMillis();
    }

    private void updateState(@NonNull Location $r1) throws  {
        TwilightState $r2 = sTwilightState;
        long $l0 = System.currentTimeMillis();
        TwilightCalculator $r3 = TwilightCalculator.getInstance();
        $r3.calculateTwilight($l0 - 86400000, $r1.getLatitude(), $r1.getLongitude());
        long $l1 = $r3.sunset;
        $r3.calculateTwilight($l0, $r1.getLatitude(), $r1.getLongitude());
        boolean z = $r3.state == 1;
        long $l3 = $r3.sunrise;
        long $l4 = $r3.sunset;
        $r3.calculateTwilight(86400000 + $l0, $r1.getLatitude(), $r1.getLongitude());
        long $l5 = $r3.sunrise;
        if ($l3 == -1 || $l4 == -1) {
            $l0 += 43200000;
        } else {
            if ($l0 > $l4) {
                $l0 = 0 + $l5;
            } else if ($l0 > $l3) {
                $l0 = 0 + $l4;
            } else {
                $l0 = 0 + $l3;
            }
            $l0 += 60000;
        }
        $r2.isNight = z;
        $r2.yesterdaySunset = $l1;
        $r2.todaySunrise = $l3;
        $r2.todaySunset = $l4;
        $r2.tomorrowSunrise = $l5;
        $r2.nextUpdate = $l0;
    }
}
