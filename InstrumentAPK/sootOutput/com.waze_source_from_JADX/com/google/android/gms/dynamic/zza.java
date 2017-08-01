package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.zzh;
import dalvik.annotation.Signature;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zza<T extends LifecycleDelegate> {
    private T abL;
    private Bundle abM;
    private LinkedList<zza> abN;
    private final zzf<T> abO = new C07231(this);

    /* compiled from: dalvik_source_com.waze.apk */
    class C07231 implements zzf<T> {
        final /* synthetic */ zza abP;

        C07231(zza $r1) throws  {
            this.abP = $r1;
        }

        public void zza(@Signature({"(TT;)V"}) T $r1) throws  {
            this.abP.abL = $r1;
            Iterator $r4 = this.abP.abN.iterator();
            while ($r4.hasNext()) {
                ((zza) $r4.next()).zzb(this.abP.abL);
            }
            this.abP.abN.clear();
            this.abP.abM = null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zza {
        int getState() throws ;

        void zzb(LifecycleDelegate lifecycleDelegate) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07275 implements OnClickListener {
        final /* synthetic */ Context zzals;
        final /* synthetic */ int zzbjs;

        C07275(Context $r1, int $i0) throws  {
            this.zzals = $r1;
            this.zzbjs = $i0;
        }

        public void onClick(View view) throws  {
            this.zzals.startActivity(GooglePlayServicesUtil.getGooglePlayServicesAvailabilityRecoveryIntent(this.zzbjs));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07286 implements zza {
        final /* synthetic */ zza abP;

        C07286(zza $r1) throws  {
            this.abP = $r1;
        }

        public int getState() throws  {
            return 4;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) throws  {
            this.abP.abL.onStart();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07297 implements zza {
        final /* synthetic */ zza abP;

        C07297(zza $r1) throws  {
            this.abP = $r1;
        }

        public int getState() throws  {
            return 5;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) throws  {
            this.abP.abL.onResume();
        }
    }

    private void zza(Bundle $r1, zza $r2) throws  {
        if (this.abL != null) {
            $r2.zzb(this.abL);
            return;
        }
        if (this.abN == null) {
            this.abN = new LinkedList();
        }
        this.abN.add($r2);
        if ($r1 != null) {
            if (this.abM == null) {
                this.abM = (Bundle) $r1.clone();
            } else {
                this.abM.putAll($r1);
            }
        }
        zza(this.abO);
    }

    public static void zzb(FrameLayout $r0) throws  {
        Context $r2 = $r0.getContext();
        int $i0 = GooglePlayServicesUtil.isGooglePlayServicesAvailable($r2);
        String $r3 = zzh.zzb($r2, $i0, GooglePlayServicesUtilLight.zzbq($r2));
        String $r4 = zzh.zzg($r2, $i0);
        LinearLayout $r1 = new LinearLayout($r0.getContext());
        $r1.setOrientation(1);
        $r1.setLayoutParams(new LayoutParams(-2, -2));
        $r0.addView($r1);
        TextView $r7 = new TextView($r0.getContext());
        $r7.setLayoutParams(new LayoutParams(-2, -2));
        $r7.setText($r3);
        $r1.addView($r7);
        if ($r4 != null) {
            Button $r8 = new Button($r2);
            $r8.setLayoutParams(new LayoutParams(-2, -2));
            $r8.setText($r4);
            $r1.addView($r8);
            $r8.setOnClickListener(new C07275($r2, $i0));
        }
    }

    private void zzpq(int $i0) throws  {
        while (!this.abN.isEmpty() && ((zza) this.abN.getLast()).getState() >= $i0) {
            this.abN.removeLast();
        }
    }

    public void onCreate(final Bundle $r1) throws  {
        zza($r1, new zza(this) {
            final /* synthetic */ zza abP;

            public int getState() throws  {
                return 1;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) throws  {
                this.abP.abL.onCreate($r1);
            }
        });
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle $r3) throws  {
        FrameLayout $r4 = new FrameLayout($r1.getContext());
        final FrameLayout frameLayout = $r4;
        final LayoutInflater layoutInflater = $r1;
        final ViewGroup viewGroup = $r2;
        final Bundle bundle = $r3;
        zza($r3, new zza(this) {
            final /* synthetic */ zza abP;

            public int getState() throws  {
                return 2;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) throws  {
                frameLayout.removeAllViews();
                frameLayout.addView(this.abP.abL.onCreateView(layoutInflater, viewGroup, bundle));
            }
        });
        if (this.abL != null) {
            return $r4;
        }
        zza($r4);
        return $r4;
    }

    public void onDestroy() throws  {
        if (this.abL != null) {
            this.abL.onDestroy();
        } else {
            zzpq(1);
        }
    }

    public void onDestroyView() throws  {
        if (this.abL != null) {
            this.abL.onDestroyView();
        } else {
            zzpq(2);
        }
    }

    public void onInflate(final Activity $r1, final Bundle $r2, final Bundle $r3) throws  {
        zza($r3, new zza(this) {
            final /* synthetic */ zza abP;

            public int getState() throws  {
                return 0;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) throws  {
                this.abP.abL.onInflate($r1, $r2, $r3);
            }
        });
    }

    public void onLowMemory() throws  {
        if (this.abL != null) {
            this.abL.onLowMemory();
        }
    }

    public void onPause() throws  {
        if (this.abL != null) {
            this.abL.onPause();
        } else {
            zzpq(5);
        }
    }

    public void onResume() throws  {
        zza(null, new C07297(this));
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        if (this.abL != null) {
            this.abL.onSaveInstanceState($r1);
        } else if (this.abM != null) {
            $r1.putAll(this.abM);
        }
    }

    public void onStart() throws  {
        zza(null, new C07286(this));
    }

    public void onStop() throws  {
        if (this.abL != null) {
            this.abL.onStop();
        } else {
            zzpq(4);
        }
    }

    protected void zza(FrameLayout $r1) throws  {
        zzb($r1);
    }

    protected abstract void zza(@Signature({"(", "Lcom/google/android/gms/dynamic/zzf", "<TT;>;)V"}) zzf<T> com_google_android_gms_dynamic_zzf_T) throws ;

    public T zzbix() throws  {
        return this.abL;
    }
}
