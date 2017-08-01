package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import com.google.android.gms.common.api.ResultStore;

@TargetApi(11)
/* compiled from: dalvik_source_com.waze.apk */
public class zzrk extends Fragment {
    private zzrr FR = new zzrr();

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setRetainInstance(true);
    }

    public void onDestroy() throws  {
        super.onDestroy();
        this.FR.zzz(getActivity());
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        this.FR.zzaun();
    }

    public ResultStore zzaul() throws  {
        return this.FR;
    }
}
