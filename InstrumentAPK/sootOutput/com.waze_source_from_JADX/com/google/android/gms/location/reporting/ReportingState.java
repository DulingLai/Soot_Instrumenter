package com.google.android.gms.location.reporting;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzxo;

/* compiled from: dalvik_source_com.waze.apk */
public class ReportingState extends AbstractSafeParcelable {
    public static final ReportingStateCreator CREATOR = new ReportingStateCreator();
    private final int ayL;
    private final int ayM;
    private final boolean ayN;
    private final boolean ayO;
    private final int ayP;
    private final int ayQ;
    private final Integer ayR;
    private final boolean ayS;
    private final int mVersionCode;

    @Deprecated
    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Setting {
        @Deprecated
        public static final int AMBIGUOUS = -2;
        public static final int OFF = -1;
        public static final int ON = 1;
        public static final int UNDEFINED = 0;
        public static final int UNKNOWN_OFF = -3;
        public static final int UNKNOWN_ON = 99;

        private Setting() throws  {
        }

        public static boolean isOff(int $i0) throws  {
            Log.wtf("GCoreUlr", "", new UnsupportedOperationException("ReportingState.Setting is deprecated and will eventually be deleted. Use Reporting.Setting, ReportingStateRestult.isActive(), or ReportingStateRestult.isOptedIn()."));
            return com.google.android.gms.location.reporting.Reporting.Setting.isOff($i0);
        }

        public static boolean isOn(int $i0) throws  {
            Log.wtf("GCoreUlr", "", new UnsupportedOperationException("ReportingState.Setting is deprecated and will eventually be deleted. Use Reporting.Setting, ReportingStateRestult.isActive(), or ReportingStateRestult.isOptedIn()."));
            return com.google.android.gms.location.reporting.Reporting.Setting.isOn($i0);
        }

        public static int sanitize(int $i0) throws  {
            return com.google.android.gms.location.reporting.Reporting.Setting.sanitize($i0);
        }

        public static String toString(int $i0) throws  {
            return com.google.android.gms.location.reporting.Reporting.Setting.toString($i0);
        }
    }

    public ReportingState(int $i0, int $i1, int $i2, boolean $z0, boolean $z1, int $i3, int $i4, @Nullable Integer $r1, boolean $z2) throws  {
        this.mVersionCode = $i0;
        this.ayL = $i1;
        this.ayM = $i2;
        this.ayN = $z0;
        this.ayO = $z1;
        this.ayP = $i3;
        this.ayQ = $i4;
        this.ayR = $r1;
        this.ayS = $z2;
    }

    @Deprecated
    public ReportingState(@Deprecated int $i0, @Deprecated int $i1, @Deprecated boolean $z0, @Deprecated boolean $z1, @Deprecated int $i2, @Deprecated int $i3, @Nullable @Deprecated Integer $r1) throws  {
        this(3, $i0, $i1, $z0, $z1, $i2, $i3, $r1, true);
    }

    public ReportingState(int $i0, int $i1, boolean $z0, boolean $z1, int $i2, int $i3, @Nullable Integer $r1, boolean $z2) throws  {
        this(3, $i0, $i1, $z0, $z1, $i2, $i3, $r1, $z2);
    }

    public boolean canAccessSettings() throws  {
        return this.ayS;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof ReportingState)) {
            return false;
        }
        ReportingState $r2 = (ReportingState) $r1;
        return this.ayL == $r2.ayL ? this.ayM == $r2.ayM ? this.ayN == $r2.ayN ? this.ayO == $r2.ayO ? this.ayP == $r2.ayP ? this.ayQ == $r2.ayQ ? zzaa.equal(this.ayR, $r2.ayR) ? this.ayS == $r2.ayS : false : false : false : false : false : false : false;
    }

    public int getDeviceTag() throws  {
        if (this.ayR != null) {
            return this.ayR.intValue();
        }
        throw new SecurityException("Device tag restricted to approved apps");
    }

    public int getExpectedOptInResult() throws  {
        return OptInResult.sanitize(this.ayP);
    }

    public int getHistoryEnabled() throws  {
        return Setting.sanitize(this.ayM);
    }

    public int getReportingEnabled() throws  {
        return Setting.sanitize(this.ayL);
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.ayL), Integer.valueOf(this.ayM), Boolean.valueOf(this.ayN), Boolean.valueOf(this.ayO), Integer.valueOf(this.ayP), Integer.valueOf(this.ayQ), this.ayR, Boolean.valueOf(this.ayS));
    }

    public boolean isActive() throws  {
        return this.ayO;
    }

    public boolean isAllowed() throws  {
        return this.ayN;
    }

    public boolean isAmbiguous() throws  {
        return this.ayL == -2 || this.ayM == -2;
    }

    @Deprecated
    public boolean isDeferringToMaps() throws  {
        return false;
    }

    public boolean isOptedIn() throws  {
        return com.google.android.gms.location.reporting.Reporting.Setting.isOn(this.ayL) && com.google.android.gms.location.reporting.Reporting.Setting.isOn(this.ayM);
    }

    public boolean shouldOptIn() throws  {
        return !isOptedIn() && getExpectedOptInResult() == 0;
    }

    @Deprecated
    public boolean shouldOptInInsistent() throws  {
        return shouldOptIn();
    }

    @Deprecated
    public boolean shouldOptInLenient() throws  {
        return shouldOptIn();
    }

    public String toString() throws  {
        String $r2 = this.ayR != null ? zzxo.zzh(this.ayR) : "(hidden-from-unauthorized-caller)";
        int $i3 = this.ayL;
        int $i4 = this.ayM;
        boolean $z0 = this.ayN;
        boolean $z1 = this.ayO;
        int $i0 = this.ayP;
        int $i1 = this.ayQ;
        return new StringBuilder(String.valueOf($r2).length() + 261).append("ReportingState{mReportingEnabled=").append($i3).append(", mHistoryEnabled=").append($i4).append(", mAllowed=").append($z0).append(", mActive=").append($z1).append(", mExpectedOptInResult=").append($i0).append(", mExpectedOptInResultAssumingLocationEnabled=").append($i1).append(", mVersionCode=").append(this.mVersionCode).append(", mDeviceTag=").append($r2).append(", mCanAccessSettings=").append(this.ayS).append("}").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ReportingStateCreator $r2 = CREATOR;
        ReportingStateCreator.zza(this, $r1, $i0);
    }

    int zzbtn() throws  {
        return OptInResult.sanitize(this.ayQ);
    }

    Integer zzbto() throws  {
        return this.ayR;
    }
}
