package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.dynamic.zzc.zza;

@SuppressLint({"NewApi"})
/* compiled from: dalvik_source_com.waze.apk */
public final class zzb extends zza {
    private Fragment abU;

    private zzb(Fragment $r1) throws  {
        this.abU = $r1;
    }

    public static zzb zza(Fragment $r0) throws  {
        return $r0 != null ? new zzb($r0) : null;
    }

    public Bundle getArguments() throws  {
        return this.abU.getArguments();
    }

    public int getId() throws  {
        return this.abU.getId();
    }

    public boolean getRetainInstance() throws  {
        return this.abU.getRetainInstance();
    }

    public String getTag() throws  {
        return this.abU.getTag();
    }

    public int getTargetRequestCode() throws  {
        return this.abU.getTargetRequestCode();
    }

    public boolean getUserVisibleHint() throws  {
        return this.abU.getUserVisibleHint();
    }

    public zzd getView() throws  {
        return zze.zzan(this.abU.getView());
    }

    public boolean isAdded() throws  {
        return this.abU.isAdded();
    }

    public boolean isDetached() throws  {
        return this.abU.isDetached();
    }

    public boolean isHidden() throws  {
        return this.abU.isHidden();
    }

    public boolean isInLayout() throws  {
        return this.abU.isInLayout();
    }

    public boolean isRemoving() throws  {
        return this.abU.isRemoving();
    }

    public boolean isResumed() throws  {
        return this.abU.isResumed();
    }

    public boolean isVisible() throws  {
        return this.abU.isVisible();
    }

    public void setHasOptionsMenu(boolean $z0) throws  {
        this.abU.setHasOptionsMenu($z0);
    }

    public void setMenuVisibility(boolean $z0) throws  {
        this.abU.setMenuVisibility($z0);
    }

    public void setRetainInstance(boolean $z0) throws  {
        this.abU.setRetainInstance($z0);
    }

    public void setUserVisibleHint(boolean $z0) throws  {
        this.abU.setUserVisibleHint($z0);
    }

    public void startActivity(Intent $r1) throws  {
        this.abU.startActivity($r1);
    }

    public void startActivityForResult(Intent $r1, int $i0) throws  {
        this.abU.startActivityForResult($r1, $i0);
    }

    public void zzac(zzd $r1) throws  {
        this.abU.registerForContextMenu((View) zze.zzae($r1));
    }

    public void zzad(zzd $r1) throws  {
        this.abU.unregisterForContextMenu((View) zze.zzae($r1));
    }

    public zzd zzbiy() throws  {
        return zze.zzan(this.abU.getActivity());
    }

    public zzc zzbiz() throws  {
        return zza(this.abU.getParentFragment());
    }

    public zzd zzbja() throws  {
        return zze.zzan(this.abU.getResources());
    }

    public zzc zzbjb() throws  {
        return zza(this.abU.getTargetFragment());
    }
}
