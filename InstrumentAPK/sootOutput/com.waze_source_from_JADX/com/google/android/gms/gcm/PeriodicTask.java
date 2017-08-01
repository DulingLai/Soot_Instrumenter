package com.google.android.gms.gcm;

import android.app.Service;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class PeriodicTask extends Task {
    public static final Creator<PeriodicTask> CREATOR = new C07411();
    protected long mFlexInSeconds;
    protected long mIntervalInSeconds;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07411 implements Creator<PeriodicTask> {
        C07411() throws  {
        }

        public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
            return zzoj($r1);
        }

        public /* synthetic */ Object[] newArray(int $i0) throws  {
            return zzvl($i0);
        }

        public PeriodicTask zzoj(Parcel $r1) throws  {
            return new PeriodicTask($r1);
        }

        public PeriodicTask[] zzvl(int $i0) throws  {
            return new PeriodicTask[$i0];
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder extends com.google.android.gms.gcm.Task.Builder {
        private long arj = -1;
        private long ark = -1;

        public PeriodicTask build() throws  {
            checkConditions();
            return new PeriodicTask();
        }

        protected void checkConditions() throws  {
            super.checkConditions();
            if (this.arj == -1) {
                throw new IllegalArgumentException("Must call setPeriod(long) to establish an execution interval for this periodic task.");
            } else if (this.arj <= 0) {
                throw new IllegalArgumentException("Period set cannot be less than or equal to 0: " + this.arj);
            } else if (this.ark == -1) {
                this.ark = (long) (((float) this.arj) * 0.1f);
            } else if (this.ark > this.arj) {
                this.ark = this.arj;
            }
        }

        public Builder setExtras(Bundle $r1) throws  {
            this.extras = $r1;
            return this;
        }

        public Builder setFlex(long $l0) throws  {
            this.ark = $l0;
            return this;
        }

        public Builder setPeriod(long $l0) throws  {
            this.arj = $l0;
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

        public Builder setService(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/GcmTaskService;", ">;)", "Lcom/google/android/gms/gcm/PeriodicTask$Builder;"}) Class<? extends GcmTaskService> $r1) throws  {
            this.gcmTaskService = $r1.getName();
            return this;
        }

        public Builder setServiceUnchecked(@Signature({"(", "Ljava/lang/Class", "<+", "Landroid/app/Service;", ">;)", "Lcom/google/android/gms/gcm/PeriodicTask$Builder;"}) Class<? extends Service> $r1) throws  {
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
    }

    @Deprecated
    private PeriodicTask(@Deprecated Parcel $r1) throws  {
        super($r1);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = $r1.readLong();
        this.mFlexInSeconds = Math.min($r1.readLong(), this.mIntervalInSeconds);
    }

    private PeriodicTask(Builder $r1) throws  {
        super((com.google.android.gms.gcm.Task.Builder) $r1);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = $r1.arj;
        this.mFlexInSeconds = Math.min($r1.ark, this.mIntervalInSeconds);
    }

    public long getFlex() throws  {
        return this.mFlexInSeconds;
    }

    public long getPeriod() throws  {
        return this.mIntervalInSeconds;
    }

    public void toBundle(Bundle $r1) throws  {
        super.toBundle($r1);
        $r1.putLong("period", this.mIntervalInSeconds);
        $r1.putLong("period_flex", this.mFlexInSeconds);
    }

    public String toString() throws  {
        String $r1 = String.valueOf(super.toString());
        long $l0 = getPeriod();
        return new StringBuilder(String.valueOf($r1).length() + 54).append($r1).append(" period=").append($l0).append(" flex=").append(getFlex()).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeLong(this.mIntervalInSeconds);
        $r1.writeLong(this.mFlexInSeconds);
    }
}
