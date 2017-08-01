package com.google.android.gms.gcm;

import android.app.Service;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class OneoffTask extends Task {
    public static final Creator<OneoffTask> CREATOR = new C07391();
    private final long arf;
    private final long arg;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07391 implements Creator<OneoffTask> {
        C07391() throws  {
        }

        public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
            return zzoh($r1);
        }

        public /* synthetic */ Object[] newArray(int $i0) throws  {
            return zzvj($i0);
        }

        public OneoffTask zzoh(Parcel $r1) throws  {
            return new OneoffTask($r1);
        }

        public OneoffTask[] zzvj(int $i0) throws  {
            return new OneoffTask[$i0];
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder extends com.google.android.gms.gcm.Task.Builder {
        private long arh = -1;
        private long ari = -1;

        public OneoffTask build() throws  {
            checkConditions();
            return new OneoffTask();
        }

        protected void checkConditions() throws  {
            super.checkConditions();
            if (this.arh == -1 || this.ari == -1) {
                throw new IllegalArgumentException("Must specify an execution window using setExecutionWindow.");
            } else if (this.arh >= this.ari) {
                throw new IllegalArgumentException("Window start must be shorter than window end.");
            }
        }

        public Builder setExecutionWindow(long $l0, long $l1) throws  {
            this.arh = $l0;
            this.ari = $l1;
            return this;
        }

        public Builder setExtras(Bundle $r1) throws  {
            this.extras = $r1;
            return this;
        }

        public Builder setPersisted(boolean $z0) throws  {
            this.isPersisted = $z0;
            return this;
        }

        public Builder setRequiredNetwork(int $i0) throws  {
            this.requiredNetworkState = $i0;
            return this;
        }

        public Builder setRequiresCharging(boolean $z0) throws  {
            this.requiresCharging = $z0;
            return this;
        }

        public Builder setRetryStrategy(RetryStrategy $r1) throws  {
            this.retryStrategy = $r1;
            return this;
        }

        public Builder setService(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/GcmTaskService;", ">;)", "Lcom/google/android/gms/gcm/OneoffTask$Builder;"}) Class<? extends GcmTaskService> $r1) throws  {
            this.gcmTaskService = $r1.getName();
            return this;
        }

        public Builder setServiceUnchecked(@Signature({"(", "Ljava/lang/Class", "<+", "Landroid/app/Service;", ">;)", "Lcom/google/android/gms/gcm/OneoffTask$Builder;"}) Class<? extends Service> $r1) throws  {
            this.gcmTaskService = $r1.getName();
            return this;
        }

        public Builder setTag(String $r1) throws  {
            this.tag = $r1;
            return this;
        }

        public Builder setUpdateCurrent(boolean $z0) throws  {
            this.updateCurrent = $z0;
            return this;
        }

        public Builder zzjv(String $r1) throws  {
            this.gcmTaskService = $r1;
            return this;
        }
    }

    @Deprecated
    private OneoffTask(@Deprecated Parcel $r1) throws  {
        super($r1);
        this.arf = $r1.readLong();
        this.arg = $r1.readLong();
    }

    private OneoffTask(Builder $r1) throws  {
        super((com.google.android.gms.gcm.Task.Builder) $r1);
        this.arf = $r1.arh;
        this.arg = $r1.ari;
    }

    public long getWindowEnd() throws  {
        return this.arg;
    }

    public long getWindowStart() throws  {
        return this.arf;
    }

    public void toBundle(Bundle $r1) throws  {
        super.toBundle($r1);
        $r1.putLong("window_start", this.arf);
        $r1.putLong("window_end", this.arg);
    }

    public String toString() throws  {
        String $r1 = String.valueOf(super.toString());
        long $l0 = getWindowStart();
        return new StringBuilder(String.valueOf($r1).length() + 64).append($r1).append(" windowStart=").append($l0).append(" windowEnd=").append(getWindowEnd()).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeLong(this.arf);
        $r1.writeLong(this.arg);
    }
}
