package com.waze.test;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.C1283R;
import java.util.Timer;
import java.util.TimerTask;

public class TestGps extends Activity {
    final long GPS_WD_PERIOD = 10000;
    Handler mGpsHandler = new Handler();
    LocationListener mGpsListener = new C29332();
    TimerTask mGpsWatchDog = new C29321();
    boolean mStarted = false;
    Timer mTimer = new Timer();

    class C29321 extends TimerTask {

        class C29301 implements Runnable {
            C29301() {
            }

            public void run() {
                TestGps.this.stopGPS();
            }
        }

        class C29312 implements Runnable {
            C29312() {
            }

            public void run() {
                TestGps.this.startGPS();
            }
        }

        C29321() {
        }

        public void run() {
            if (TestGps.this.mStarted) {
                TestGps.this.mGpsHandler.post(new C29301());
            } else {
                TestGps.this.mGpsHandler.post(new C29312());
            }
        }
    }

    class C29332 implements LocationListener {
        C29332() {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onLocationChanged(Location location) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.test_gps);
        startGPS();
        this.mTimer.scheduleAtFixedRate(this.mGpsWatchDog, 10000, 10000);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    private void startGPS() {
        this.mStarted = true;
        Toast.makeText(this, "Starting GPS", 0).show();
        ((LocationManager) getApplicationContext().getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION)).requestLocationUpdates("gps", 0, 0.0f, this.mGpsListener);
    }

    private void stopGPS() {
        this.mStarted = false;
        Toast.makeText(this, "Stopping GPS", 0).show();
        ((LocationManager) getApplicationContext().getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION)).removeUpdates(this.mGpsListener);
    }
}
