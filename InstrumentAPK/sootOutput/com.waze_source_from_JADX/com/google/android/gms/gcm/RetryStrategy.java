package com.google.android.gms.gcm;

import android.os.Bundle;
import android.support.annotation.IntRange;
import com.google.android.gms.common.internal.zzab;
import com.waze.strings.DisplayStrings;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: dalvik_source_com.waze.apk */
public class RetryStrategy {
    public static final int MINIMUM_LINEAR_INITIAL_BACKOFF_SECONDS = 10;
    public static final RetryStrategy PRESET_EXPONENTIAL = new RetryStrategy(0, 30, DisplayStrings.DS_CUSTOM_PROMPT_200_METERS);
    public static final RetryStrategy PRESET_LINEAR = new RetryStrategy(1, 30, DisplayStrings.DS_CUSTOM_PROMPT_200_METERS);
    public static final int RETRY_POLICY_EXPONENTIAL = 0;
    public static final int RETRY_POLICY_LINEAR = 1;
    private final int arl;
    private final int arm;
    private final int arn;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private int arl;
        private int arm;
        private int arn;

        private void checkConditions() throws  {
            boolean $z0 = true;
            boolean $z1 = this.arl == 0 || this.arl == 1;
            zzab.zzb($z1, (Object) "Must provide a valid RetryPolicy.");
            if (this.arl == 0) {
                zzab.zzb(this.arm > 0, (Object) "RETRY_POLICY_EXPONENTIAL must have a positive initialBackoffSeconds.");
            } else {
                zzab.zzb(this.arm >= 10, (Object) "RETRY_POLICY_LINEAR must have an initial backoff at least 10 seconds.");
            }
            if (this.arn <= this.arm) {
                $z0 = false;
            }
            zzab.zzb($z0, (Object) "MaximumBackoffSeconds must be greater than InitialBackoffSeconds.");
        }

        public RetryStrategy build() throws  {
            checkConditions();
            return new RetryStrategy(this.arl, this.arm, this.arn);
        }

        public Builder setInitialBackoffSeconds(@IntRange(from = 1) int $i0) throws  {
            this.arm = $i0;
            return this;
        }

        public Builder setMaximumBackoffSeconds(@IntRange(from = 1) int $i0) throws  {
            this.arn = $i0;
            return this;
        }

        public Builder setRetryPolicy(int $i0) throws  {
            this.arl = $i0;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: dalvik_source_com.waze.apk */
    public @interface RetryPolicy {
    }

    private RetryStrategy(int $i0, int $i1, int $i2) throws  {
        this.arl = $i0;
        this.arm = $i1;
        this.arn = $i2;
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof RetryStrategy)) {
            return false;
        }
        RetryStrategy $r2 = (RetryStrategy) $r1;
        return $r2.arl == this.arl && $r2.arm == this.arm && $r2.arn == this.arn;
    }

    public int getInitialBackoffSeconds() throws  {
        return this.arm;
    }

    public int getMaximumBackoffSeconds() throws  {
        return this.arn;
    }

    public int getRetryPolicy() throws  {
        return this.arl;
    }

    public int hashCode() throws  {
        return (((((this.arl + 1) ^ 1000003) * 1000003) ^ this.arm) * 1000003) ^ this.arn;
    }

    public String toString() throws  {
        int $i1 = this.arl;
        int $i2 = this.arm;
        return "policy=" + $i1 + " initial_backoff=" + $i2 + " maximum_backoff=" + this.arn;
    }

    public Bundle zzao(Bundle $r1) throws  {
        $r1.putInt("retry_policy", this.arl);
        $r1.putInt("initial_backoff_seconds", this.arm);
        $r1.putInt("maximum_backoff_seconds", this.arn);
        return $r1;
    }
}
