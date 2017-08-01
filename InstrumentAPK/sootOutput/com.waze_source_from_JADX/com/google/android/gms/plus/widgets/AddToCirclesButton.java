package com.google.android.gms.plus.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.people.data.AudienceMember;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzzw;
import com.google.android.gms.internal.zzzx;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class AddToCirclesButton extends FrameLayout {
    private static Context Gy = null;
    protected static final String PACKAGE_IMPLEMENTATION_CLASS_NAME = "com.google.android.gms.plus.circlesbutton.AddToCirclesButtonImpl$DynamiteHost";
    public static final int SIZE_CUSTOM = 0;
    public static final int SIZE_LARGE = 3;
    public static final int SIZE_MEDIUM = 2;
    public static final int SIZE_SMALL = 1;
    public static final int TYPE_ADD = 0;
    public static final int TYPE_BLOCKED = 3;
    public static final int TYPE_FOLLOW = 1;
    public static final int TYPE_ONE_CLICK_FOLLOW = 2;
    private int aYr;
    private final zzzw bas;
    private final Context bat;
    private View kI;

    /* compiled from: dalvik_source_com.waze.apk */
    public class OnAddToCirclesClickCallback extends com.google.android.gms.internal.zzzx.zza {
        private final OnAddToCirclesClickListener bau;
        final /* synthetic */ AddToCirclesButton bav;

        public OnAddToCirclesClickCallback(AddToCirclesButton $r1, OnAddToCirclesClickListener $r2) throws  {
            this.bav = $r1;
            this.bau = $r2;
        }

        private void zzaf(Intent $r1) throws  {
            if ((this.bav.bat instanceof Activity) && $r1 != null) {
                ((Activity) this.bav.bat).startActivityForResult($r1, this.bav.aYr);
            }
        }

        public void onAddToCirclesClick(Intent $r1) throws RemoteException {
            if (this.bau != null) {
                this.bau.onAddToCirclesClick($r1);
            } else {
                zzaf($r1);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface OnAddToCirclesClickListener {
        void onAddToCirclesClick(Intent intent) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends com.google.android.gms.internal.zzzw.zza {
        private TextView mView;

        private zza() throws  {
        }

        public zzd getView() throws  {
            return zze.zzan(this.mView);
        }

        public void refreshButton() throws  {
        }

        public void setAnalyticsStartView(String str, int i) throws  {
        }

        public void setShowProgressIndicator(boolean z) throws  {
        }

        public void setSize(int i) throws  {
        }

        public void setType(int i) throws  {
        }

        public void zza(String str, String str2, AudienceMember audienceMember, String str3, zzzx com_google_android_gms_internal_zzzx) throws  {
        }

        public void zzb(zzd $r1, zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2) throws  {
            this.mView = new TextView((Context) zze.zzae($r1));
        }
    }

    public AddToCirclesButton(Context $r1) throws  {
        this($r1, null);
    }

    public AddToCirclesButton(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.bat = $r1;
        Pair $r4 = zzbw($r1);
        this.bas = (zzzw) $r4.first;
        try {
            this.bas.zzb(zze.zzan(getContext()), zze.zzan((Context) $r4.second), zze.zzan($r2));
            this.kI = (View) zze.zzae(this.bas.getView());
            addView(this.kI);
        } catch (RemoteException e) {
        }
    }

    private static Pair<zzzw, Context> zzbw(@Signature({"(", "Landroid/content/Context;", ")", "Landroid/util/Pair", "<", "Lcom/google/android/gms/internal/zzzw;", "Landroid/content/Context;", ">;"}) Context $r0) throws  {
        ReflectiveOperationException $r8;
        if (Gy == null) {
            Gy = GooglePlayServicesUtil.getRemoteContext($r0);
        }
        if (Gy != null) {
            try {
                return new Pair(com.google.android.gms.internal.zzzw.zza.zzot((IBinder) Gy.getClassLoader().loadClass(PACKAGE_IMPLEMENTATION_CLASS_NAME).newInstance()), Gy);
            } catch (ClassNotFoundException e) {
                $r8 = e;
                if ($r8 != null && Log.isLoggable("AddToCirclesButton", 3)) {
                    Log.e("AddToCirclesButton", "Can't load com.google.android.gms.plus.circlesbutton.AddToCirclesButtonImpl$DynamiteHost", $r8);
                }
                return new Pair(new zza(), $r0);
            } catch (InstantiationException e2) {
                $r8 = e2;
                Log.e("AddToCirclesButton", "Can't load com.google.android.gms.plus.circlesbutton.AddToCirclesButtonImpl$DynamiteHost", $r8);
                return new Pair(new zza(), $r0);
            } catch (IllegalAccessException e3) {
                $r8 = e3;
                Log.e("AddToCirclesButton", "Can't load com.google.android.gms.plus.circlesbutton.AddToCirclesButtonImpl$DynamiteHost", $r8);
                return new Pair(new zza(), $r0);
            }
        }
        return new Pair(new zza(), $r0);
    }

    public void configure(String $r1, String $r2, AudienceMember $r3, int $i0, String $r4) throws  {
        configure($r1, $r2, $r3, $i0, $r4, null);
    }

    public void configure(String $r1, String $r2, AudienceMember $r3, int $i0, String $r4, OnAddToCirclesClickListener $r5) throws  {
        zzab.zzag($r1);
        zzab.zzag($r3);
        zzab.zza(getContext() instanceof Activity, (Object) "The AddToCirclesButton must be placed in an Activity.");
        this.aYr = $i0;
        zzzw $r8 = this.bas;
        try {
            $r8.zza($r1, $r2, $r3, $r4, new OnAddToCirclesClickCallback(this, $r5));
        } catch (RemoteException e) {
            Log.e("AddToCirclesButton", "Add to circles button failed to configure.");
        }
    }

    public void refreshButton() throws  {
        setShowProgressIndicator(false);
        try {
            this.bas.refreshButton();
        } catch (RemoteException e) {
            Log.e("AddToCirclesButton", "Add to circles button failed to refreshButton.");
        }
    }

    public void setAnalyticsStartView(String $r1, int $i0) throws  {
        try {
            this.bas.setAnalyticsStartView($r1, $i0);
        } catch (RemoteException e) {
            Log.e("AddToCirclesButton", "Add to circles button failed to setAnalyticsStartView.");
        }
    }

    public void setShowProgressIndicator(boolean $z0) throws  {
        try {
            this.bas.setShowProgressIndicator($z0);
        } catch (RemoteException e) {
            Log.e("AddToCirclesButton", "Add to circles button failed to setShowProgressIndicator.");
        }
    }

    public void setSize(int $i0) throws  {
        try {
            this.bas.setSize($i0);
        } catch (RemoteException e) {
            Log.e("AddToCirclesButton", "Add to circles button failed to setSize.");
        }
    }

    public void setType(int $i0) throws  {
        try {
            this.bas.setType($i0);
        } catch (RemoteException e) {
            Log.e("AddToCirclesButton", "Add to circles button failed to setType.");
        }
    }
}
