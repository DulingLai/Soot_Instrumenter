package com.google.android.gms.gcm;

import android.app.Service;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.gcm.nts.GcmScheduler;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class Task implements Parcelable {
    public static final int EXTRAS_LIMIT_BYTES = 10240;
    public static final int NETWORK_STATE_ANY = 2;
    public static final int NETWORK_STATE_CONNECTED = 0;
    public static final int NETWORK_STATE_UNMETERED = 1;
    protected static final long UNINITIALIZED = -1;
    private final String aro;
    private final boolean arp;
    private final boolean arq;
    private final int arr;
    private final boolean ars;
    private final RetryStrategy art;
    private final Bundle mExtras;
    private final String mTag;

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class Builder {
        protected Bundle extras;
        protected String gcmTaskService;
        protected boolean isPersisted;
        protected int requiredNetworkState;
        protected boolean requiresCharging;
        protected RetryStrategy retryStrategy = RetryStrategy.PRESET_EXPONENTIAL;
        protected String tag;
        protected boolean updateCurrent;

        public abstract Task build() throws ;

        @CallSuper
        protected void checkConditions() throws  {
            zzab.zzb(this.gcmTaskService != null, (Object) "Must provide an endpoint for this task by calling setService(ComponentName).");
            GcmNetworkManager.zzjn(this.tag);
            Task.zza(this.retryStrategy);
            if (this.isPersisted) {
                Task.zzap(this.extras);
            }
        }

        public abstract Builder setExtras(Bundle bundle) throws ;

        public abstract Builder setPersisted(boolean z) throws ;

        public abstract Builder setRequiredNetwork(int i) throws ;

        public abstract Builder setRequiresCharging(boolean z) throws ;

        public abstract Builder setRetryStrategy(RetryStrategy retryStrategy) throws ;

        public abstract Builder setService(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/GcmTaskService;", ">;)", "Lcom/google/android/gms/gcm/Task$Builder;"}) Class<? extends GcmTaskService> cls) throws ;

        public abstract Builder setServiceUnchecked(@Signature({"(", "Ljava/lang/Class", "<+", "Landroid/app/Service;", ">;)", "Lcom/google/android/gms/gcm/Task$Builder;"}) Class<? extends Service> cls) throws ;

        public abstract Builder setTag(String str) throws ;

        public abstract Builder setUpdateCurrent(boolean z) throws ;
    }

    @Deprecated
    Task(@Deprecated Parcel $r1) throws  {
        boolean $z0 = true;
        Log.e("Task", "Constructing a Task object using a parcel.");
        this.aro = $r1.readString();
        this.mTag = $r1.readString();
        this.arp = $r1.readInt() == 1;
        if ($r1.readInt() != 1) {
            $z0 = false;
        }
        this.arq = $z0;
        this.arr = 2;
        this.ars = false;
        this.art = RetryStrategy.PRESET_EXPONENTIAL;
        this.mExtras = null;
    }

    Task(Builder $r1) throws  {
        this.aro = $r1.gcmTaskService;
        this.mTag = $r1.tag;
        this.arp = $r1.updateCurrent;
        this.arq = $r1.isPersisted;
        this.arr = $r1.requiredNetworkState;
        this.ars = $r1.requiresCharging;
        this.mExtras = $r1.extras;
        this.art = $r1.retryStrategy != null ? $r1.retryStrategy : RetryStrategy.PRESET_EXPONENTIAL;
    }

    public static void zza(RetryStrategy $r0) throws  {
        if ($r0 != null) {
            int $i0 = $r0.getRetryPolicy();
            if ($i0 == 1 || $i0 == 0) {
                int $i1 = $r0.getInitialBackoffSeconds();
                int $i2 = $r0.getMaximumBackoffSeconds();
                if ($i0 == 0 && $i1 < 0) {
                    throw new IllegalArgumentException("InitialBackoffSeconds can't be negative: " + $i1);
                } else if ($i0 == 1 && $i1 < 10) {
                    throw new IllegalArgumentException("RETRY_POLICY_LINEAR must have an initial backoff at least 10 seconds.");
                } else if ($i2 < $i1) {
                    throw new IllegalArgumentException("MaximumBackoffSeconds must be greater than InitialBackoffSeconds: " + $r0.getMaximumBackoffSeconds());
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Must provide a valid RetryPolicy: " + $i0);
        }
    }

    private static boolean zzao(Object $r0) throws  {
        return ($r0 instanceof Integer) || ($r0 instanceof Long) || ($r0 instanceof Double) || ($r0 instanceof String) || ($r0 instanceof Boolean);
    }

    public static void zzap(Bundle $r0) throws  {
        if ($r0 != null) {
            Parcel $r2 = Parcel.obtain();
            $r0.writeToParcel($r2, 0);
            int $i0 = $r2.dataSize();
            if ($i0 > EXTRAS_LIMIT_BYTES) {
                $r2.recycle();
                String $r4 = String.valueOf("Extras exceeding maximum size(10240 bytes): ");
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r4).length() + 11).append($r4).append($i0).toString());
            }
            $r2.recycle();
            for (String str : $r0.keySet()) {
                if (!zzao($r0.get(str))) {
                    throw new IllegalArgumentException("Only the following extra parameter types are supported: Integer, Long, Double, String, and Boolean. ");
                }
            }
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public Bundle getExtras() throws  {
        return this.mExtras;
    }

    public int getRequiredNetwork() throws  {
        return this.arr;
    }

    public boolean getRequiresCharging() throws  {
        return this.ars;
    }

    public RetryStrategy getRetryStrategy() throws  {
        return this.art;
    }

    public String getServiceName() throws  {
        return this.aro;
    }

    public String getTag() throws  {
        return this.mTag;
    }

    public boolean isPersisted() throws  {
        return this.arq;
    }

    public boolean isUpdateCurrent() throws  {
        return this.arp;
    }

    public void toBundle(Bundle $r1) throws  {
        $r1.putString(GcmScheduler.INTENT_PARAM_TAG, this.mTag);
        $r1.putBoolean("update_current", this.arp);
        $r1.putBoolean("persisted", this.arq);
        $r1.putString("service", this.aro);
        $r1.putInt("requiredNetwork", this.arr);
        $r1.putBoolean("requiresCharging", this.ars);
        $r1.putBundle("retryStrategy", this.art.zzao(new Bundle()));
        $r1.putBundle("extras", this.mExtras);
    }

    public void writeToParcel(Parcel $r1, int i) throws  {
        byte $b1 = (byte) 1;
        $r1.writeString(this.aro);
        $r1.writeString(this.mTag);
        $r1.writeInt(this.arp ? (byte) 1 : (byte) 0);
        if (!this.arq) {
            $b1 = (byte) 0;
        }
        $r1.writeInt($b1);
    }
}
