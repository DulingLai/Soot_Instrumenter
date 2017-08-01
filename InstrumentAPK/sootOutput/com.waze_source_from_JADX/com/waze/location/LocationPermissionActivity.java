package com.waze.location;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.waze.C1283R;
import com.waze.LocationSensorListener;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.install.InstallNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class LocationPermissionActivity extends ActivityBase implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {
    private static final float DISABLED_ALPHA = 0.5f;
    private static final int MY_PERMISSIONS_REQUEST = 1001;
    private static final int REQUEST_CHECK_SETTINGS = 1002;
    private boolean configMissing;
    protected LocationRequest locationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private boolean permissionMissing;
    private View tvConfigButton;
    private TextView tvConfigButtonText;
    private View tvPermissionButton;
    private TextView tvPermissionButtonText;
    private boolean viewSetUp = false;

    class C18441 implements OnClickListener {
        C18441() throws  {
        }

        public void onClick(View v) throws  {
            LocationPermissionActivity.this.reportClick("PERMISSIONS_FALLBACK");
            Intent $r2 = new Intent();
            $r2.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            $r2.setData(Uri.fromParts("package", LocationPermissionActivity.this.getPackageName(), null));
            LocationPermissionActivity.this.startActivityForResult($r2, 1002);
        }
    }

    class C18452 implements OnClickListener {
        C18452() throws  {
        }

        public void onClick(View v) throws  {
            LocationPermissionActivity.this.reportClick("PERMISSIONS");
            ActivityCompat.requestPermissions(LocationPermissionActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1001);
        }
    }

    class C18463 implements OnClickListener {
        C18463() throws  {
        }

        public void onClick(View v) throws  {
            LocationPermissionActivity.this.reportClick("CONFIG");
            LocationPermissionActivity.this.mGoogleApiClient = new Builder(LocationPermissionActivity.this).addApi(LocationServices.API).addConnectionCallbacks(LocationPermissionActivity.this).addOnConnectionFailedListener(LocationPermissionActivity.this).build();
            LocationPermissionActivity.this.mGoogleApiClient.connect();
            LocationPermissionActivity.this.locationRequest = LocationRequest.create();
            LocationPermissionActivity.this.locationRequest.setPriority(100);
            LocationPermissionActivity.this.locationRequest.setInterval(30000);
            LocationPermissionActivity.this.locationRequest.setFastestInterval(5000);
        }
    }

    class C18474 implements OnClickListener {
        C18474() throws  {
        }

        public void onClick(View v) throws  {
            LocationPermissionActivity.this.reportClick("CONFIG_FALLBACK");
            LocationPermissionActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1002);
        }
    }

    protected boolean isCloseable() throws  {
        return false;
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if (!stillNeeded()) {
            return;
        }
        if (InstallNativeManager.IsCleanInstallation() && this.permissionMissing && !this.configMissing) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1001);
            return;
        }
        setupView();
        setupButtons();
    }

    private void setupView() throws  {
        if (!this.viewSetUp) {
            setContentView(C1283R.layout.activity_location_permission);
            ((TextView) findViewById(C1283R.id.locationPermissionTitle)).setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PERMISSION_TITLE));
            ((TextView) findViewById(C1283R.id.locationPermissionExplanation)).setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PERMISSION_EXPLANATION));
            this.tvPermissionButtonText = (TextView) findViewById(C1283R.id.locationPermissionButtonText);
            this.tvConfigButtonText = (TextView) findViewById(C1283R.id.locationConfigButtonText);
            this.tvPermissionButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PERMISSION_BUTTON));
            this.tvConfigButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_ENABLE_GPS_BUTTON));
            this.tvPermissionButton = findViewById(C1283R.id.locationPermissionButton);
            this.tvConfigButton = findViewById(C1283R.id.locationConfigButton);
            this.viewSetUp = true;
            TitleBar $r4 = (TitleBar) findViewById(C1283R.id.locationPermissionTitleBar);
            $r4.setTitle(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PERMISSION_TOP_TITLE));
            $r4.setCloseVisibility(false);
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_PERMISSION_SHOWN).send();
        }
    }

    private void setupButtons() throws  {
        setupView();
        if (this.permissionMissing) {
            this.tvPermissionButton.setAlpha(1.0f);
            this.tvPermissionButton.setEnabled(true);
            findViewById(C1283R.id.locationPermissionButtonIcon).setVisibility(8);
            if (VERSION.SDK_INT < 23 || shouldShowRequestPermissionRationale("android.permission.ACCESS_FINE_LOCATION") || InstallNativeManager.IsCleanInstallation()) {
                this.tvPermissionButton.setOnClickListener(new C18452());
            } else {
                this.tvPermissionButton.setOnClickListener(new C18441());
            }
        } else {
            this.tvPermissionButton.setEnabled(false);
            this.tvPermissionButton.setAlpha(0.5f);
            findViewById(C1283R.id.locationPermissionButtonIcon).setVisibility(0);
            this.tvPermissionButton.setOnClickListener(null);
        }
        if (this.configMissing) {
            this.tvConfigButton.setAlpha(1.0f);
            this.tvConfigButton.setEnabled(true);
            findViewById(C1283R.id.locationConfigButtonIcon).setVisibility(8);
            this.tvConfigButton.setOnClickListener(new C18463());
            return;
        }
        this.tvConfigButton.setEnabled(false);
        this.tvConfigButton.setAlpha(0.5f);
        findViewById(C1283R.id.locationConfigButtonIcon).setVisibility(0);
        this.tvConfigButton.setOnClickListener(null);
    }

    private boolean stillNeeded() throws  {
        this.configMissing = LocationSensorListener.gpsConfigMissing();
        this.permissionMissing = false;
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            this.permissionMissing = true;
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_CONFIG_MISSING).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, this.configMissing ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_PERMISSION_MISSING).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, this.permissionMissing ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
        if (this.permissionMissing || this.configMissing) {
            return true;
        }
        NativeManager.getInstance().startLocation();
        closeActivity(-1);
        return false;
    }

    private void closeActivity(int $i0) throws  {
        setResult($i0);
        finish();
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_PERMISSION_CLOSED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $i0 == -1 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_OK : "CANCEL").send();
    }

    private void reportClick(String $r1) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_PERMISSION_CLICK).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $r1).send();
    }

    protected void onDestroy() throws  {
        super.onDestroy();
        if (this.mGoogleApiClient != null && this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.disconnect();
        }
    }

    public void onRequestPermissionsResult(int $i0, String[] permissions, int[] grantResults) throws  {
        switch ($i0) {
            case 1001:
                if (stillNeeded()) {
                    setupButtons();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onConnected(@Nullable Bundle bundle) throws  {
        LocationSettingsRequest.Builder $r2 = new LocationSettingsRequest.Builder().addLocationRequest(this.locationRequest);
        $r2.setAlwaysShow(true);
        LocationServices.SettingsApi.checkLocationSettings(this.mGoogleApiClient, $r2.build()).setResultCallback(this);
    }

    public void onConnectionSuspended(int i) throws  {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) throws  {
        manualSettingsFallback();
    }

    public void onResult(@NonNull LocationSettingsResult $r1) throws  {
        Status $r3 = $r1.getStatus();
        switch ($r3.getStatusCode()) {
            case 0:
                if (stillNeeded()) {
                    manualSettingsFallback();
                    this.tvConfigButton.performClick();
                    return;
                }
                NativeManager.getInstance().startLocation();
                closeActivity(-1);
                return;
            case 6:
                try {
                    $r3.startResolutionForResult(this, 1002);
                    return;
                } catch (SendIntentException e) {
                    manualSettingsFallback();
                    return;
                }
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE /*8502*/:
                manualSettingsFallback();
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
        if ($i0 == 1002 && stillNeeded()) {
            setupButtons();
        }
    }

    void manualSettingsFallback() throws  {
        this.tvConfigButton.setOnClickListener(new C18474());
    }

    public void onBackPressed() throws  {
        super.onBackPressed();
        closeActivity(0);
    }
}
