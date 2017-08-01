package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class AppDescription extends AbstractSafeParcelable {
    public static final AppDescriptionCreator CREATOR = new AppDescriptionCreator();
    protected static final String TAG = "GLSSession";
    private static final String gG;
    boolean gL;
    private final String iq;
    int ir;
    String is;
    String it;
    final int version;
    String zzcap;

    static {
        String $r1 = String.valueOf(AppDescription.class.getSimpleName());
        gG = new StringBuilder(String.valueOf($r1).length() + 2).append("[").append($r1).append("]").toString();
    }

    AppDescription(int $i0, int $i1, String $r1, String $r2, String $r3, boolean $z0) throws  {
        String $r5 = String.valueOf(getClass().getSimpleName());
        this.iq = new StringBuilder(String.valueOf($r5).length() + 14).append("[").append($r5).append("] %s - %s: %s").toString();
        this.version = $i0;
        this.zzcap = $r1;
        this.is = $r2;
        this.it = zzab.zzi($r3, String.valueOf(gG).concat(" callingPkg cannot be null or empty!"));
        zzab.zzb($i1 != 0, (Object) "Invalid callingUid! Cannot be 0!");
        this.ir = $i1;
        this.gL = $z0;
    }

    public AppDescription(String $r1, int $i0) throws  {
        this($r1, $i0, null, null);
    }

    public AppDescription(String $r1, int $i0, String $r2, String $r3) throws  {
        this(1, $i0, $r2, $r3, $r1, false);
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "New " + getClass().getSimpleName() + " (" + "sessiondId: " + this.zzcap + ", " + "sessiondSig: " + this.is + ", " + "callingPkg: " + this.it + ", " + "callingUid: " + this.ir + ", ");
        }
    }

    @Deprecated
    public String getCallingPackage() throws  {
        return this.it;
    }

    @Deprecated
    public int getCallingUid() throws  {
        return this.ir;
    }

    public String getPackageName() throws  {
        return this.it;
    }

    public String getSessionId() throws  {
        return this.zzcap;
    }

    public String getSessionSignature() throws  {
        return this.is;
    }

    public int getUid() throws  {
        return this.ir;
    }

    public boolean isSetupWizardInProgress() throws  {
        return this.gL;
    }

    protected void log(String $r1, int $i0) throws  {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, String.format(this.iq, new Object[]{this.zzcap, $r1, Integer.valueOf($i0)}));
        }
    }

    protected void log(String $r1, String $r2) throws  {
        if (Log.isLoggable(TAG, 2)) {
            String $r3 = $r2 == null ? "<NULL>" : $r2.isEmpty() ? "<EMPTY>" : "<MEANINFGUL>";
            Log.v(TAG, String.format(this.iq, new Object[]{this.zzcap, $r1, $r3}));
        }
    }

    protected void log(String $r1, boolean $z0) throws  {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, String.format(this.iq, new Object[]{this.zzcap, $r1, Boolean.valueOf($z0)}));
        }
    }

    public AppDescription setSetupWizardInProgress(boolean $z0) throws  {
        this.gL = $z0;
        return this;
    }

    public String toString() throws  {
        return new StringBuilder(getClass().getSimpleName()).append("<").append(this.it).append(", ").append(this.ir).append(">").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AppDescriptionCreator.zza(this, $r1, $i0);
    }
}
