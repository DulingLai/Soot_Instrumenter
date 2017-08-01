package com.waze;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.support.v4.content.ContextCompat;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.map.CanvasFont;
import com.waze.view.map.SpeedometerView;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.TimerTask;

public abstract class LocationSensorListener implements SensorEventListener, ILocationSensorListener {
    public static boolean bIsStartCompass = false;
    protected static LocationSensorListener mInstance = null;
    protected final long GPS_FIX_WATCH_DOG_PERIOD;
    protected final long GPS_LOCATION_WATCH_DOG_PERIOD;
    protected final long GPS_MOCK_DETECT_PERIOD;
    protected final float[] f46I;
    protected float[] f47R;
    protected final int SENSOR_MASK_ACCEL;
    protected final int SENSOR_MASK_CLEAR_CACHE;
    protected final int SENSOR_MASK_COMPASS;
    protected final int SENSOR_MASK_GYRO;
    protected final int SENSOR_MASK_RESET_GPS;
    protected final float SMOOTH_IGNORE;
    protected final float SMOOTH_RATIO;
    protected final int STATUS_GPS_AVAILABLE;
    protected final int STATUS_NETWORK_AVAILABLE;
    protected final byte STATUS_NOT_AVAILABLE;
    protected final String TAG;
    protected final double TWO_PI;
    protected float[] aValues;
    private OutputStreamWriter locationStreamWriter;
    protected float[] mAccelData;
    protected Sensor mAccelerometer;
    protected boolean mClearCache;
    protected Sensor mCompass;
    protected float[] mCompassData;
    private float mFilteredAzimuth;
    protected WatchDogTask mFixWatchDog;
    protected boolean mGpsFixSent;
    protected Sensor mGyro;
    protected float[] mGyroData;
    protected boolean mHasGps;
    protected boolean mIsavailable;
    protected long mLastGpsFixTime;
    protected Location mLastLocation;
    protected ArrayList<RunnableExecutor> mLocListeners;
    protected LocationManager mLocationManager;
    protected Sensor mMagneticField;
    protected TimerTask mMockGpsDetect;
    protected boolean mProximityAdded;
    protected boolean mResetGps;
    protected int mSatelliteNumber;
    private SensorManager mSensorManager;
    protected SpeedometerView mSpeedometer;
    protected boolean mStarted;
    protected int mStatus;
    protected ArrayList<RunnableExecutor> mStatusChangedListeners;
    protected Object mStatusChangedSync;
    protected float[] mValues;
    protected float[] m_vforientVals;
    protected boolean mbLoggedIn;
    protected boolean mbReportedMockLocation;
    protected int nCompass;
    protected float[] orientationValues;

    class C11724 implements Runnable {
        C11724() throws  {
        }

        public void run() throws  {
            LocationSensorListener.this.initSpeedometerNTV();
        }
    }

    private final class WatchDogTask extends TimerTask {

        class C11741 implements Runnable {
            C11741() throws  {
            }

            public void run() throws  {
                LocationSensorListener.this.start();
            }
        }

        private WatchDogTask() throws  {
        }

        public void run() throws  {
            Logger.m36d("WatchDogTask run");
            if (!LocationSensorListener.this.mGpsFixSent) {
                final long $l0 = System.currentTimeMillis() - LocationSensorListener.this.mLastGpsFixTime;
                if ($l0 >= 60000 || (LocationSensorListener.this.mHasGps && $l0 >= 10000)) {
                    final C11741 $r2 = new C11741();
                    C11752 $r1 = new Runnable() {
                        public void run() throws  {
                            Logger.m43w("No GPS fix for " + $l0 + " ms. and GPS is + " + LocationSensorListener.this.mHasGps + ". Restarting GPS engine. ");
                            LocationSensorListener.this.mGpsFixSent = false;
                            LocationSensorListener.this.stop();
                            NativeManager.Post($r2);
                        }
                    };
                    LocationSensorListener.this.mGpsFixSent = true;
                    NativeManager.Post($r1);
                }
            }
        }
    }

    private native void LocListenerCallbackNTV(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) throws ;

    private native void SetCompassNTV(int i) throws ;

    private native void initSpeedometerNTV() throws ;

    private native void playSpeedometerSoundNTV() throws ;

    protected native int GetLocationSensorMaskNTV() throws ;

    protected native void SatteliteListenerCallbackNTV(int i) throws ;

    public void onSensorChanged(android.hardware.SensorEvent r33) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:36:0x013d in {6, 7, 9, 11, 22, 25, 29, 31, 35, 37, 38, 39, 43, 47, 48, 49, 50, 51, 52} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r32 = this;
        r0 = r33;
        r4 = r0.values;
        if (r4 == 0) goto L_0x0188;
    L_0x0006:
        r0 = r33;
        r4 = r0.values;
        r5 = r4.length;
        r6 = 3;
        if (r5 < r6) goto L_0x0189;
    L_0x000e:
        r0 = r33;
        r7 = r0.sensor;
        r5 = r7.getType();
        switch(r5) {
            case 1: goto L_0x001b;
            case 2: goto L_0x0049;
            case 3: goto L_0x001a;
            case 4: goto L_0x003a;
            default: goto L_0x0019;
        };
    L_0x0019:
        goto L_0x001a;
    L_0x001a:
        return;
    L_0x001b:
        r0 = r33;
        r4 = r0.values;
        r0 = r32;
        r8 = r0.mAccelData;
        r6 = 0;
        r9 = 0;
        r10 = 3;
        java.lang.System.arraycopy(r4, r6, r8, r9, r10);
        r0 = r33;
        r4 = r0.values;
        r11 = r4.clone();
        r12 = r11;
        r12 = (float[]) r12;
        r4 = r12;
        r0 = r32;
        r0.aValues = r4;
        return;
    L_0x003a:
        r0 = r33;
        r4 = r0.values;
        r0 = r32;
        r8 = r0.mGyroData;
        r6 = 0;
        r9 = 0;
        r10 = 3;
        java.lang.System.arraycopy(r4, r6, r8, r9, r10);
        return;
    L_0x0049:
        r0 = r33;
        r4 = r0.values;
        r0 = r32;
        r8 = r0.mCompassData;
        r6 = 0;
        r9 = 0;
        r10 = 3;
        java.lang.System.arraycopy(r4, r6, r8, r9, r10);
        r0 = r33;
        r4 = r0.values;
        r11 = r4.clone();
        r13 = r11;
        r13 = (float[]) r13;
        r4 = r13;
        r0 = r32;
        r0.mValues = r4;
        r0 = r32;
        r4 = r0.aValues;
        if (r4 == 0) goto L_0x018a;
    L_0x006d:
        r0 = r32;
        r4 = r0.mValues;
        if (r4 == 0) goto L_0x018b;
    L_0x0073:
        r6 = 9;
        r4 = new float[r6];
        r6 = 9;
        r8 = new float[r6];
        r0 = r32;
        r14 = r0.aValues;
        r0 = r32;
        r15 = r0.mValues;
        r16 = android.hardware.SensorManager.getRotationMatrix(r4, r8, r14, r15);
        if (r16 == 0) goto L_0x018c;
    L_0x0089:
        r6 = 3;
        r8 = new float[r6];
        android.hardware.SensorManager.getOrientation(r4, r8);
        r6 = 0;
        r17 = r8[r6];
        r0 = r32;
        r0 = r0.mFilteredAzimuth;
        r18 = r0;
        r20 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r19 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1));
        if (r19 == 0) goto L_0x00ba;
    L_0x009f:
        r0 = r32;
        r0 = r0.mFilteredAzimuth;
        r18 = r0;
        r6 = 0;
        r21 = r8[r6];
        r0 = r18;
        r1 = r21;
        r0 = r0 - r1;
        r18 = r0;
        r18 = java.lang.Math.abs(r0);
        r20 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r19 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1));
        if (r19 <= 0) goto L_0x010f;
    L_0x00ba:
        r0 = r17;
        r1 = r32;
        r1.mFilteredAzimuth = r0;
    L_0x00c0:
        r0 = r32;
        r0 = r0.mFilteredAzimuth;
        r17 = r0;
        r0 = (double) r0;
        r22 = r0;
        r22 = java.lang.Math.toDegrees(r0);
        r0 = r22;
        r5 = (int) r0;
        r24 = com.waze.AppService.getAppContext();
        r25 = "window";
        r0 = r24;
        r1 = r25;
        r11 = r0.getSystemService(r1);
        r27 = r11;
        r27 = (android.view.WindowManager) r27;
        r26 = r27;
        r0 = r26;
        r28 = r0.getDefaultDisplay();
        r0 = r28;
        r29 = r0.getRotation();
        switch(r29) {
            case 1: goto L_0x017f;
            case 2: goto L_0x0182;
            case 3: goto L_0x0185;
            default: goto L_0x00f4;
        };
    L_0x00f4:
        goto L_0x00f5;
    L_0x00f5:
        r0 = r32;
        r0 = r0.nCompass;
        r29 = r0;
        if (r0 == r5) goto L_0x018d;
    L_0x00fd:
        r0 = r32;
        r0.nCompass = r5;
        r0 = r32;
        r5 = r0.nCompass;
        goto L_0x0109;
    L_0x0106:
        goto L_0x00c0;
    L_0x0109:
        r0 = r32;
        r0.setCompass(r5);
        return;
    L_0x010f:
        r0 = r32;
        r0 = r0.mFilteredAzimuth;
        r18 = r0;
        r18 = r17 - r18;
        r0 = r18;
        r0 = (double) r0;
        r22 = r0;
        r30 = -4604611780675359464; // 0xc01921fb54442d18 float:3.37028055E12 double:-6.283185307179586;
        r19 = (r22 > r30 ? 1 : (r22 == r30 ? 0 : -1));
        if (r19 >= 0) goto L_0x015c;
    L_0x0125:
        r0 = r18;
        r0 = (double) r0;
        r22 = r0;
        r30 = 4618760256179416344; // 0x401921fb54442d18 float:3.37028055E12 double:6.283185307179586;
        r0 = r22;
        r2 = r30;
        r0 = r0 + r2;
        r22 = r0;
        r0 = (float) r0;
        r18 = r0;
        goto L_0x0141;
    L_0x013a:
        goto L_0x00f5;
        goto L_0x0141;
    L_0x013e:
        goto L_0x00f5;
    L_0x0141:
        goto L_0x0145;
    L_0x0142:
        goto L_0x00f5;
    L_0x0145:
        r0 = r32;
        r0 = r0.mFilteredAzimuth;
        r17 = r0;
        r20 = 1045220557; // 0x3e4ccccd float:0.2 double:5.164075695E-315;
        r18 = r20 * r18;
        r0 = r17;
        r1 = r18;
        r0 = r0 + r1;
        r17 = r0;
        r1 = r32;
        r1.mFilteredAzimuth = r0;
        goto L_0x0106;
    L_0x015c:
        r0 = r18;
        r0 = (double) r0;
        r22 = r0;
        r30 = 4618760256179416344; // 0x401921fb54442d18 float:3.37028055E12 double:6.283185307179586;
        r19 = (r22 > r30 ? 1 : (r22 == r30 ? 0 : -1));
        if (r19 <= 0) goto L_0x0145;
    L_0x016a:
        r0 = r18;
        r0 = (double) r0;
        r22 = r0;
        r30 = 4618760256179416344; // 0x401921fb54442d18 float:3.37028055E12 double:6.283185307179586;
        r0 = r22;
        r2 = r30;
        r0 = r0 - r2;
        r22 = r0;
        r0 = (float) r0;
        r18 = r0;
        goto L_0x0145;
    L_0x017f:
        r5 = r5 + 90;
        goto L_0x013a;
    L_0x0182:
        r5 = r5 + -180;
        goto L_0x013e;
    L_0x0185:
        r5 = r5 + -90;
        goto L_0x0142;
    L_0x0188:
        return;
    L_0x0189:
        return;
    L_0x018a:
        return;
    L_0x018b:
        return;
    L_0x018c:
        return;
    L_0x018d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.LocationSensorListener.onSensorChanged(android.hardware.SensorEvent):void");
    }

    protected native void setGpsFakeStatusNTV() throws ;

    protected abstract int setLocationAccuracy(int i, Location location, NativeLocation nativeLocation) throws ;

    public LocationSensorListener() throws  {
        this.STATUS_NOT_AVAILABLE = (byte) 0;
        this.STATUS_NETWORK_AVAILABLE = 1;
        this.STATUS_GPS_AVAILABLE = 2;
        this.TAG = "LOCATION_LISTENER";
        this.GPS_FIX_WATCH_DOG_PERIOD = 60000;
        this.GPS_LOCATION_WATCH_DOG_PERIOD = 10000;
        this.GPS_MOCK_DETECT_PERIOD = 2000;
        this.SENSOR_MASK_GYRO = 1;
        this.SENSOR_MASK_ACCEL = 2;
        this.SENSOR_MASK_COMPASS = 4;
        this.SENSOR_MASK_RESET_GPS = 8;
        this.SENSOR_MASK_CLEAR_CACHE = 16;
        this.SMOOTH_RATIO = 0.2f;
        this.SMOOTH_IGNORE = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        this.TWO_PI = 6.283185307179586d;
        this.mLocListeners = new ArrayList();
        this.mStatusChangedListeners = new ArrayList();
        this.mStatusChangedSync = new Object();
        this.mStatus = 3;
        this.mHasGps = false;
        this.mSatelliteNumber = 0;
        this.mAccelData = new float[]{0.0f, 0.0f, 0.0f};
        this.mGyroData = new float[]{0.0f, 0.0f, 0.0f};
        this.mCompassData = new float[]{0.0f, 0.0f, 0.0f};
        this.mbReportedMockLocation = false;
        this.mbLoggedIn = false;
        this.mStarted = false;
        this.mIsavailable = false;
        this.mAccelerometer = null;
        this.mGyro = null;
        this.mMagneticField = null;
        this.mCompass = null;
        this.mGpsFixSent = false;
        this.mFixWatchDog = null;
        this.mMockGpsDetect = null;
        this.mLastLocation = null;
        this.mLastGpsFixTime = 0;
        this.aValues = new float[3];
        this.mValues = new float[3];
        this.m_vforientVals = new float[3];
        this.f47R = new float[16];
        this.f46I = new float[16];
        this.orientationValues = new float[3];
        this.mProximityAdded = false;
        this.nCompass = 0;
        this.mSensorManager = null;
        this.mLocationManager = null;
        this.mSpeedometer = null;
        this.mFilteredAzimuth = -1.0f;
        this.mLocationManager = (LocationManager) AppService.getAppContext().getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) throws  {
        Logger.m36d("onAccuracyChanged");
    }

    public static NativeLocation GetNativeLocation(Location $r0) throws  {
        NativeLocation $r1 = new NativeLocation();
        $r1.mLongtitude = (int) Math.round($r0.getLongitude() * 1000000.0d);
        $r1.mLatitude = (int) Math.round($r0.getLatitude() * 1000000.0d);
        $r1.mAltitude = (int) Math.round($r0.getAltitude());
        $r1.mGpsTime = (int) ($r0.getTime() / 1000);
        $r1.mSpeed = (int) ($r0.getSpeed() * 1000.0f);
        $r1.mSteering = (int) $r0.getBearing();
        $r1.mAccuracy = (int) $r0.getAccuracy();
        return $r1;
    }

    public void RegisterCompass() throws  {
        Logger.m36d("RegisterCompass");
        if (this.mMagneticField == null) {
            this.mMagneticField = this.mSensorManager.getDefaultSensor(2);
        }
        this.mSensorManager.registerListener(this, this.mMagneticField, 2);
    }

    public void UnregisterCompass() throws  {
        Logger.m36d("unRegisterCompass");
        if (this.mMagneticField != null) {
            this.mSensorManager.unregisterListener(this, this.mMagneticField);
        }
    }

    public void registerSensors() throws  {
        Logger.m36d("LocationSensorListener: registerSensors");
        if (this.mAccelerometer != null) {
            this.mSensorManager.registerListener(this, this.mAccelerometer, 3);
        }
        if (this.mGyro != null) {
            this.mSensorManager.registerListener(this, this.mGyro, 3);
        }
        if (this.mCompass != null) {
            this.mSensorManager.registerListener(this, this.mCompass, 3);
        }
    }

    public void unregisterSensors() throws  {
        Logger.m36d("LocationSensorListener: unregisterSensors");
        if (this.mSensorManager != null) {
            if (this.mAccelerometer != null) {
                this.mSensorManager.unregisterListener(this, this.mAccelerometer);
            }
            if (this.mGyro != null) {
                this.mSensorManager.unregisterListener(this, this.mGyro);
            }
            if (this.mCompass != null) {
                this.mSensorManager.unregisterListener(this, this.mCompass);
            }
            this.mSensorManager.unregisterListener(this);
        }
    }

    public boolean gpsProviderEnabled() throws  {
        boolean $z0 = false;
        if (VERSION.SDK_INT >= 19) {
            try {
                if (Secure.getInt(AppService.getAppContext().getContentResolver(), "location_mode") == 3) {
                    $z0 = true;
                }
                Logger.m36d("Location mode is high accuracy - " + $z0);
                return $z0;
            } catch (SettingNotFoundException e) {
                Logger.m38e("gpsProviderEnabled - failed to check location mode");
                return false;
            }
        }
        $z0 = this.mLocationManager.isProviderEnabled("gps");
        Logger.m36d("GPS provider is enabled - " + $z0);
        return $z0;
    }

    protected int init() throws  {
        Logger.m36d("init");
        this.mSensorManager = (SensorManager) AppService.getAppContext().getSystemService("sensor");
        int $i0 = GetLocationSensorMaskNTV();
        if (this.mSensorManager != null) {
            if (($i0 & 2) != 0) {
                this.mAccelerometer = this.mSensorManager.getDefaultSensor(1);
            }
            if (($i0 & 1) != 0) {
                this.mGyro = this.mSensorManager.getDefaultSensor(4);
            }
            if (($i0 & 4) != 0) {
                this.mCompass = this.mSensorManager.getDefaultSensor(2);
            }
        }
        setLocationAfterAppServiceStart();
        return $i0;
    }

    public void start() throws  {
        Logger.m36d("start");
        registerSensors();
        startWD();
    }

    public void stop() throws  {
        Logger.m36d("stop");
        unregisterSensors();
        stopWD();
        if (this.locationStreamWriter != null) {
            try {
                this.locationStreamWriter.close();
            } catch (IOException $r1) {
                Logger.m38e("An excpetion occurred while trying to close location file: " + $r1.getMessage());
            }
        }
    }

    public void onLogin() throws  {
        Logger.m36d("onLogin");
        this.mbLoggedIn = true;
        this.mbReportedMockLocation = false;
    }

    public void onLocationChanged(Location $r1) throws  {
        if ($r1 != null) {
            Logger.m36d("onLocationChanged: Provider: " + $r1.getProvider() + "; Lon: " + $r1.getLongitude() + "; Lat: " + $r1.getLatitude() + "; Speed: " + $r1.getSpeed() + "; Time: " + $r1.getTime());
            int $i0 = this.mStatus;
            long $l1 = System.currentTimeMillis();
            setLastLocation($r1, $l1);
            if (bIsStartCompass) {
                RegisterCompass();
                bIsStartCompass = false;
            }
            if ($r1.getProvider().equals("gps")) {
                this.mHasGps = true;
            }
            updateNativeLayer($r1, $l1, $i0);
        }
    }

    private float calcSpeedFromLocation(Location $r1, Location $r2) throws  {
        double $d0 = ($r1.getLatitude() * 3.141592653589793d) / 180.0d;
        double $d2 = ($r1.getLongitude() * 3.141592653589793d) / 180.0d;
        double $d1 = ($r2.getLatitude() * 3.141592653589793d) / 180.0d;
        double $d3 = ($r2.getLongitude() * 3.141592653589793d) / 180.0d;
        double $d4 = 6378100.0d * Math.cos($d0);
        double $d5 = $d4 * Math.cos($d2);
        $d2 = $d4 * Math.sin($d2);
        $d4 = 6378100.0d * Math.cos($d1);
        double $d6 = $d4 * Math.cos($d3);
        return (float) ((6378100.0d * Math.acos(((($d5 * $d6) + ($d2 * ($d4 * Math.sin($d3)))) + ((6378100.0d * Math.sin($d0)) * (6378100.0d * Math.sin($d1)))) / (6378100.0d * 6378100.0d))) / ((double) (($r2.getTime() - $r1.getTime()) * 1000)));
    }

    public synchronized Location getLastLocation() throws  {
        Logger.m36d("getLastLocation");
        return this.mLastLocation;
    }

    public synchronized void registerLocListener(RunnableExecutor $r1) throws  {
        Logger.m36d("registerLocListener: ");
        if (!($r1 == null || this.mLocListeners.contains($r1))) {
            this.mLocListeners.add($r1);
        }
    }

    public synchronized void unregisterLocListener(RunnableExecutor $r1) throws  {
        Logger.m36d("unregisterLocListener");
        this.mLocListeners.remove($r1);
    }

    protected void setLastLocation(Location $r1, long $l0) throws  {
        if ($r1.getProvider().equals("gps")) {
            this.mLastGpsFixTime = $l0;
        }
        this.mLastLocation = $r1;
        updateListeners();
    }

    protected synchronized void updateListeners() throws  {
        for (int $i0 = 0; $i0 < this.mLocListeners.size(); $i0++) {
            ((RunnableExecutor) this.mLocListeners.get($i0)).run();
        }
    }

    private void setLocationAfterAppServiceStart() throws  {
        Logger.m36d("setLocationAfterAppServiceStart");
        C11691 $r1 = new RunnableExecutor(AppService.getInstance()) {
            public void event() throws  {
                Location $r2 = LocationSensorListener.this.getLastLocation();
                if ($r2 != null && $r2.getTime() + 5000 >= System.currentTimeMillis()) {
                    LocationSensorListener.this.mLastGpsFixTime = System.currentTimeMillis();
                    LocationSensorListener.this.setLastLocation($r2, LocationSensorListener.this.mLastGpsFixTime);
                    LocationSensorListener.this.updateNativeLayer($r2, LocationSensorListener.this.mLastGpsFixTime, LocationSensorListener.this.mStatus);
                }
            }
        };
        if (NativeManager.IsAppStarted()) {
            $r1.run();
        } else {
            NativeManager.registerOnAppStartedEvent($r1);
        }
    }

    public boolean isLocationAvailable() throws  {
        return this.mIsavailable;
    }

    protected void updateNativeLayer(Location $r1, long $l0, int $i1) throws  {
        NativeManager $r6 = NativeManager.getInstance();
        if ($r6 != null && NativeManager.IsAppStarted()) {
            if ($r6.IsNativeThread()) {
                sendUpdateToNativeLayer($i1, $r1, $l0, this.mAccelData, this.mGyroData, this.mCompassData);
                return;
            }
            final int i = $i1;
            final Location location = $r1;
            final long j = $l0;
            $r6.PostRunnable(new Runnable() {
                public void run() throws  {
                    LocationSensorListener.this.sendUpdateToNativeLayer(i, location, j, LocationSensorListener.this.mAccelData, LocationSensorListener.this.mGyroData, LocationSensorListener.this.mCompassData);
                }
            });
        }
    }

    private void sendUpdateToNativeLayer(int $i0, Location $r1, long $l1, float[] $r2, float[] $r3, float[] $r4) throws  {
        if ($r1 != null) {
            NativeLocation $r5 = GetNativeLocation($r1);
            int $i9 = setLocationAccuracy($i0, $r1, $r5);
            LocListenerCallbackNTV($r5.mGpsTime, $r5.mLatitude, $r5.mLongtitude, $r5.mAltitude, $r5.mSpeed, $r5.mSteering, $r5.mAccuracy, $i9, (int) (System.currentTimeMillis() - $l1), $r2[0], $r2[1], $r2[2], $r3[0], $r3[1], $r3[2], $r4[0], $r4[1], $r4[2]);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean gpsConfigMissing() throws  {
        /*
        r0 = 1;
        r1 = com.waze.AppService.getActiveActivity();
        r3 = "location";
        r2 = r1.getSystemService(r3);	 Catch:{ Exception -> 0x0051 }
        r5 = r2;
        r5 = (android.location.LocationManager) r5;	 Catch:{ Exception -> 0x0051 }
        r4 = r5;
        r6 = android.os.Build.VERSION.SDK_INT;
        r7 = 19;
        if (r6 >= r7) goto L_0x0026;
    L_0x0015:
        r3 = "gps";
        r0 = r4.isProviderEnabled(r3);	 Catch:{ Exception -> 0x0051 }
        if (r0 != 0) goto L_0x0024;
    L_0x001d:
        r3 = "GPS disabled, location cannot execute";
        com.waze.Logger.m38e(r3);	 Catch:{ Exception -> 0x0051 }
        r7 = 1;
        return r7;
    L_0x0024:
        r7 = 0;
        return r7;
    L_0x0026:
        r8 = com.waze.AppService.getAppContext();	 Catch:{ SettingNotFoundException -> 0x0054 }
        r9 = r8.getContentResolver();	 Catch:{ SettingNotFoundException -> 0x0054 }
        r3 = "location_mode";
        r6 = android.provider.Settings.Secure.getInt(r9, r3);	 Catch:{ SettingNotFoundException -> 0x0054 }
        r7 = 3;
        if (r6 == r7) goto L_0x005c;
    L_0x0037:
        r7 = 1;
        if (r6 == r7) goto L_0x005c;
    L_0x003a:
        r10 = new java.lang.StringBuilder;
        r10.<init>();	 Catch:{ Exception -> 0x0051 }
        r3 = "Location mode high accuracy missing - ";
        r10 = r10.append(r3);	 Catch:{ Exception -> 0x0051 }
        r10 = r10.append(r0);	 Catch:{ Exception -> 0x0051 }
        r11 = r10.toString();	 Catch:{ Exception -> 0x0051 }
        com.waze.Logger.m36d(r11);	 Catch:{ Exception -> 0x0051 }
        return r0;
    L_0x0051:
        r12 = move-exception;
        r7 = 0;
        return r7;
    L_0x0054:
        r13 = move-exception;
        r3 = "permissionsMissing - failed to check location mode";
        com.waze.Logger.m38e(r3);	 Catch:{ Exception -> 0x0051 }
        r7 = 0;
        return r7;
    L_0x005c:
        r0 = 0;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.LocationSensorListener.gpsConfigMissing():boolean");
    }

    public static boolean permissionsMissing() throws  {
        if (AppService.getActiveActivity() == null) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(AppService.getAppContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(AppService.getAppContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            return gpsConfigMissing();
        }
        Logger.m38e("No permissions to access location, GPS location cannot execute");
        return true;
    }

    protected boolean checkPermissions() throws  {
        return !permissionsMissing();
    }

    private void setCompass(final int $i0) throws  {
        Logger.m36d("setCompass");
        NativeManager $r2 = AppService.getNativeManager();
        if ($r2 != null && NativeManager.IsAppStarted()) {
            if ($r2.IsNativeThread()) {
                SetCompassNTV($i0);
            } else {
                $r2.PostRunnable(new Runnable() {
                    public void run() throws  {
                        LocationSensorListener.this.SetCompassNTV($i0);
                    }
                });
            }
        }
    }

    protected void startWD() throws  {
        Logger.m36d("startWD");
        if (this.mFixWatchDog != null) {
            this.mFixWatchDog.cancel();
        }
        LocationSensorListener locationSensorListener = this;
        this.mFixWatchDog = new WatchDogTask();
        try {
            NativeManager.getInstance().getTimer().scheduleAtFixedRate(this.mFixWatchDog, 60000, 10000);
        } catch (IllegalStateException e) {
            this.mFixWatchDog = null;
            Logger.m43w("Cannot start watchdog. The timer was canceled");
        }
    }

    protected void stopWD() throws  {
        Logger.m36d("stopWD");
        if (this.mFixWatchDog != null) {
            this.mFixWatchDog.cancel();
            this.mFixWatchDog = null;
        }
        if (this.mMockGpsDetect != null) {
            this.mMockGpsDetect.cancel();
            this.mMockGpsDetect = null;
        }
    }

    public void initSpeedometer(SpeedometerView $r1) throws  {
        this.mSpeedometer = $r1;
        Logger.m36d("initSpeedometer - initializing speedometer");
        AppService.Post(new C11724());
    }

    public void playSpeedometerSound() throws  {
        playSpeedometerSoundNTV();
    }

    public void updateSpeedometer(final int $i0, final String $r1, final int $i1, final int $i2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                LocationSensorListener.this.mSpeedometer.updateSpeedometer($i0, $r1, $i1, $i2);
            }
        });
    }
}
