package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.zzc.zza;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzh extends zza {
    private Fragment abX;

    private zzh(Fragment $r1) throws  {
        this.abX = $r1;
    }

    public static zzh zza(Fragment $r0) throws  {
        return $r0 != null ? new zzh($r0) : null;
    }

    public Bundle getArguments() throws  {
        return this.abX.getArguments();
    }

    public int getId() throws  {
        return this.abX.getId();
    }

    public boolean getRetainInstance() throws  {
        return this.abX.getRetainInstance();
    }

    public String getTag() throws  {
        return this.abX.getTag();
    }

    public int getTargetRequestCode() throws  {
        return this.abX.getTargetRequestCode();
    }

    public boolean getUserVisibleHint() throws  {
        return this.abX.getUserVisibleHint();
    }

    public zzd getView() throws  {
        return zze.zzan(this.abX.getView());
    }

    public boolean isAdded() throws  {
        return this.abX.isAdded();
    }

    public boolean isDetached() throws  {
        return this.abX.isDetached();
    }

    public boolean isHidden() throws  {
        return this.abX.isHidden();
    }

    public boolean isInLayout() throws  {
        return this.abX.isInLayout();
    }

    public boolean isRemoving() throws  {
        return this.abX.isRemoving();
    }

    public boolean isResumed() throws  {
        return this.abX.isResumed();
    }

    public boolean isVisible() throws  {
        return this.abX.isVisible();
    }

    public void setHasOptionsMenu(boolean $z0) throws  {
        this.abX.setHasOptionsMenu($z0);
    }

    public void setMenuVisibility(boolean $z0) throws  {
        this.abX.setMenuVisibility($z0);
    }

    public void setRetainInstance(boolean $z0) throws  {
        this.abX.setRetainInstance($z0);
    }

    public void setUserVisibleHint(boolean $z0) throws  {
        this.abX.setUserVisibleHint($z0);
    }

    public void startActivity(Intent $r1) throws  {
        this.abX.startActivity($r1);
    }

    public void startActivityForResult(Intent $r1, int $i0) throws  {
        this.abX.startActivityForResult($r1, $i0);
    }

    public void zzac(zzd $r1) throws  {
        this.abX.registerForContextMenu((View) zze.zzae($r1));
    }

    public void zzad(zzd $r1) throws  {
        this.abX.unregisterForContextMenu((View) zze.zzae($r1));
    }

    public zzd zzbiy() throws  {
        return zze.zzan(this.abX.getActivity());
    }

    public zzc zzbiz() throws  {
        return zza(this.abX.getParentFragment());
    }

    public zzd zzbja() throws  {
        return zze.zzan(this.abX.getResources());
    }

    public zzc zzbjb() throws  {
        return zza(this.abX.getTargetFragment());
    }
}
